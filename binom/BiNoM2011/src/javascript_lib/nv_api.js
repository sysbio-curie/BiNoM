/**
 * Eric Viara (Sysra), $Id$
 *
 * Copyright (C) 2013-2014 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
 * 
 * BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * BiNoM Cytoscape plugin is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */


// NaviCell API

var nv_open_bubble = false;
var nv_decoding = false;

// Utility functions
function add_to_datatable_desc_list(dt_desc_list, data) {
	var lines = data.split(LINE_BREAK_REGEX);
	for (var ii = 0; ii < lines.length; ++ii) {
		var args = lines[ii].trim().split("\t");
		if (args.length != 3) {
			continue;
		}
		dt_desc_list.push({url: args[0], name: args[1], type: args[2]}); 
	}
}

function get_datatable_desc_list(type, name, file_elem, url, dt_desc_list, async) {
	for (var key in file_elem) {
		console.log("file_elem [" + key + "] [" + file_elem[key] + "]");
	}
	var ready = $.Deferred();
	if (type == DATATABLE_LIST) {
		if (url) {
			$.ajax(url,
			       {
				       async: async,
				       dataType: 'text',
				       success: function(data) {
					       add_to_datatable_desc_list(dt_desc_list, data);
					       ready.resolve();
				       },
				       
				       error: function() {
					       ready.resolve();
				       }
			       }
			      );
		} else {
			var reader = new FileReader();
			reader.readAsBinaryString(file_elem);
			reader.onload = function() { 
				var data = reader.result;
				add_to_datatable_desc_list(dt_desc_list, data);
				ready.resolve();
			}
			reader.onerror = function() {
				ready.resolve();
			}
		}
	} else {
		dt_desc_list.push({type: type, name: name, file_elem: file_elem, url: url});
		ready.resolve();
	}
	return ready;
}

function nv_win(win)
{
	if (!win) {
		return window;
	}
	if (typeof win == "string") {
		// in this case, win is module
		return get_win(win);
	}
	return win;
}

// API

function nv_open_module(win, module_id, ids)
{
	console.log("module: [" + module + "] " + ids.length);
	var url;
	if (module_id.match('/index.html')) {
		url = module_id;
	} else {
		var module = navicell.mapdata.getModuleDescriptions()[module_id];
		if (!module) {
			throw "module " + module_id + " not found";
		}
		url = module.url ? module.url : module;
	}
	win.show_map_and_markers(url, ids ? ids : []);
	return null;
}

function nv_get_command_history(win)
{
	return $("#command-history", win.document).val();
}

//var ESCAPE_LINE_BREAK = new RegExp("\\n", "g");
var ESCAPE_LINE_BREAK = /\\n/g;
//var ESCAPE_QUOTE = new RegExp('\\"', "g");
var ESCAPE_QUOTE = /\\"/g;
//var ESCAPE_TAB = new RegExp('\\t', "g");
var ESCAPE_TAB = /\\t/g;

function nv_execute_commands(win, cmd)
{
	return win.nv_decode(cmd);
}

// ------------------------------------------------------------------------------------------------------------------
// nv_find_entities
//
// @params
//
//    win: window
//    search: search string
//
// @example
//
//    nv_find_entities(window, "AK.*");
// ------------------------------------------------------------------------------------------------------------------

function nv_find_entities(win, search, open_bubble)
{
	win = nv_win(win);
	navicell.mapdata.findJXTree(win, search, false, 'subtree', {div: $("#result_tree_contents", win.document).get(0)}, open_bubble);
	$("#right_tabs", win.document).tabs("option", "active", 1);
	return null;
}

function nv_select_entity(win, id, mode, center, clicked_boundbox)
{
	var module = get_module(win);
	var jxtree = navicell.mapdata.getJXTree(module);
	var node = jxtree.getNodeByUserId(id);
	if (node) {
		win.click_node(win.overlay, node, mode, center, clicked_boundbox);
	}
	return null;
}

function nv_set_zoom(win, zoom)
{
	win.map.setZoom(zoom);
	return null;
}

function nv_uncheck_all_entities(win)
{
	win.uncheck_all_entities(win);
	return null;
}

function nv_scroll(win, xoffset, yoffset)
{
	var map = win.map;
	//console.log("nv_scroll " + xoffset + " " + yoffset);

	var center = map.getCenter();
	var scrolled_center;

	if (xoffset == "CENTER") {
		scrolled_center = win.map_ori_center;
	} else if (xoffset == "W_CENTER") {
		scrolled_center = new google.maps.LatLng(win.map_ori_center.lat(), win.map_ori_bounds.getNorthEast().lng());
	} else if (xoffset == "E_CENTER") {
		scrolled_center = new google.maps.LatLng(win.map_ori_center.lat(), win.map_ori_bounds.getSouthWest().lng());
	} else if (xoffset == "S_CENTER") {
		scrolled_center = new google.maps.LatLng(win.map_ori_bounds.getNorthEast().lat(), win.map_ori_center.lng());
	} else if (xoffset == "N_CENTER") {
		scrolled_center = new google.maps.LatLng(win.map_ori_bounds.getSouthWest().lat(), win.map_ori_center.lng());
	} else if (xoffset == "SW_CENTER") {
		scrolled_center = win.map_ori_bounds.getNorthEast();
	} else if (xoffset == "NE_CENTER") {
		scrolled_center = win.map_ori_bounds.getSouthWest();
	} else if (typeof xoffset == "number" && typeof yoffset == "number") {
		scrolled_center = new google.maps.LatLng(center.lat()+yoffset, center.lng()+xoffset, true);
	} else {
		scrolled_center = null;
	}

	if (scrolled_center != null) {
		map.setCenter(scrolled_center);
		console.log("has scrolled to center [" + scrolled_center + "]");
	}
	return null;
}

// ------------------------------------------------------------------------------------------------------------------
// nv_import_datatables
//
// @params
//   win: window
//   type: datatable type
//   name: datatable name
//   file_elem: file element coming from a file dialog
//   url: URL
//   params: parameters
//
// @examples
//
//    nv_import_datatables(window, "Protein expression data", "BAF", null, "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_BAF.txt");
//    nv_import_datatables(window, "Discrete Copy number data", "CopyNumber", null, "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_CopyNumber.txt");
//    nv_import_datatables(window, "Protein expression data", "Expression", null, "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_Expression_neg.txt");
// OR:
// navicell launched from file://
//    nv_import_datatables(window, "Datatable list", "", null, "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/datatable_list.txt");
// navicell launched from http://
//    nv_import_datatables(window, "Datatable list", "", null, "http://navicell-dev2.curie.fr/nv2.1/cancer_cell_line_broad/datatable_list_url.txt");
//
// with params:
//    nv_import_datatables(window, "Datatable list", "", null, "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/datatable_list.txt", {import_display_markers: false, import_display_barplot: true});
//
// ------------------------------------------------------------------------------------------------------------------

function nv_import_datatables(win, type, name, file_elem, url, params)
{
	win = nv_win(win);
	var module = get_module(win);
	var drawing_config = navicell.getDrawingConfig(module);
	var dt_desc_list = [];
	var async = params && params.async != undefined && !nv_decoding ? params.async : false;
	var ready = get_datatable_desc_list(type, name, file_elem, url, dt_desc_list, async);

	var error_message = params ? params.error_message : null;
	var status_message = params ? params.status_message : null;

	ready.then(function() {
		var msg_cnt = 0;
		if (!dt_desc_list.length) {
			if (error_message) {
				error_message("No Datatables Imported");
			} else {
				win.console.log("nv_import_datatables error: No Datatables Imported");
			}
			return;
		}
		for (var idx in dt_desc_list) {
			var dt_desc = dt_desc_list[idx];
			var datatable = navicell.dataset.readDatatable(dt_desc.type, dt_desc.name, dt_desc.file_elem, dt_desc.url, win, async);
			if (datatable.error) {
				var error_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Failed</span></div>";
				error_str += "<table>";
				error_str += "<tr><td>Datatable</td><td>" + dt_desc.name + "</td></tr>";
				error_str += "<tr><td>Error</td><td>" + datatable.error + "</td></tr>";
				error_str += "</table>";
				if (error_message) {
					error_message(error_str, msg_cnt++);
				} else {
					win.console.log("nv_import_datatables error: " + error_str);
				}
			} else {
				datatable.ready.then(function(my_datatable) {
					if (my_datatable.error) {
						var error_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Failed</span></div>";
						error_str += "<table>";
						error_str += "<tr><td>Datatable</td><td>" + my_datatable.name + "</td></tr>";
						error_str += "<tr><td>Error</td><td>" + my_datatable.error + "</td></tr>";
						error_str += "</table>";
						if (error_message) {
							error_message(error_str, msg_cnt++);
						} else {
							win.console.log("nv_import_datatables error: " + error_str);
						}
					} else {
						var status_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Successful</span></div>";
						status_str += "<table>";
						status_str += "<tr><td>Datatable</td><td>" + my_datatable.name + "</td></tr>";
						status_str += "<tr><td>Samples</td><td>" + my_datatable.getSampleCount() + "</td></tr>\n";
						var opener = win;
						var nnn = 0;
						while (opener && opener.document.map_name) {
							status_str += "<tr><td>Genes mapped on " + opener.document.map_name + "</td><td>" + my_datatable.getGeneCountPerModule(opener.document.map_name) + "</td></tr>";
							opener = opener.opener;
							if (nnn++ > 4) {
								win.console.log("too many level of hierarchy !");
								break;
							}
						}
						status_str += "</table>";
						if (status_message) {
							status_message(status_str, msg_cnt++);
						} else {
							win.console.log("nv_import_datatables notice: " + status_str);
						}
						navicell.annot_factory.refresh();
						var display_graphics = false;
						if (params) {
							if (params.import_display_barplot) {
								var barplotConfig = drawing_config.getEditingBarplotConfig();
								barplotConfig.setDatatableAt(0, datatable);
								if (params.open_drawing_editor) {
									$("#barplot_editing", win.document).html(EDITING_CONFIGURATION);
									$("#barplot_editor_div", win.document).dialog("open");
								} else {
									$("#drawing_config_chart_display", win.document).attr("checked", 'checked')
									$("#drawing_config_chart_type", win.document).val('Barplot');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display", win.document).attr("checked"), $("#drawing_config_chart_type", win.document).val());
									barplot_sample_action('all_samples', DEF_OVERVIEW_BARPLOT_SAMPLE_CNT);
									
									barplot_editor_apply(drawing_config.getBarplotConfig());
									barplot_editor_apply(drawing_config.getEditingBarplotConfig());
									barplot_editor_set_editing(false);

									max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

									display_graphics = true;
								}
							} else if (params.import_display_heatmap) {
								var heatmapConfig = drawing_config.getEditingHeatmapConfig();
								var datatable_cnt = mapSize(navicell.dataset.datatables);
								for (var idx = 0; idx < datatable_cnt; ++idx) {
									if (!heatmapConfig.getDatatableAt(idx)) {
										break;
									}
								}
								heatmapConfig.setDatatableAt(idx, datatable);
								if (params.open_drawing_editor) {
									$("#heatmap_editing", win.document).html(EDITING_CONFIGURATION);
									$("#heatmap_editor_div", win.document).dialog("open");
								} else {
									$("#drawing_config_chart_display", win.document).attr("checked", 'checked')
									$("#drawing_config_chart_type", win.document).val('Heatmap');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display", win.document).attr("checked"), $("#drawing_config_chart_type", win.document).val());
									if (!drawing_config.getHeatmapConfig().getSampleOrGroupCount()) {
										win.heatmap_sample_action_perform('all_samples', DEF_OVERVIEW_HEATMAP_SAMPLE_CNT);
									} else {
										win.heatmap_sample_action_perform('pass');
									}

									win.heatmap_editor_apply(drawing_config.getHeatmapConfig());
									win.heatmap_editor_apply(drawing_config.getEditingHeatmapConfig());
									win.heatmap_editor_set_editing(false);

									win.max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;

									display_graphics = true;
								}
							} else {
								display_graphics = false;
							}

						}
						var display_markers = params ? params.import_display_markers : true;
						my_datatable.display(win.document.navicell_module_name, win, display_graphics, display_markers);
						drawing_editing(false);
						update_status_tables();
					}
				});
			}
		}
	});
	return null;
}

function nv_prepare_import_dialog(win, filename, name, type)
{
	var doc = win.document;
	$("#dt_import_file_message", doc).css("display", "block");
	$("#dt_import_file_message", doc).html("The file '" + filename + "' has been sent by NV client, please import it:");
	$("#dt_import_type", doc).val(type);
	$("#dt_import_name", doc).val(name);
	$("#dt_import_dialog", doc ).dialog("open");
	return null;
}

function nv_get_module_list(win)
{
	var module_names = [];
	var module_descriptions = navicell.mapdata.getModuleDescriptions();
	for (var module_id in module_descriptions) {
		var module = module_descriptions[module_id];
		module_names.push({id: module.id, name: module.name, url: module.url});
	}
	return module_names;
}

function nv_get_datatable_list(win)
{
	var ret_datatables = [];
	var datatables = navicell.dataset.getDatatables();
	for (var name in datatables) {
		var datatable = datatables[name];
		ret_datatables.push([datatable.name, datatable.biotype.name]);
	}
	return ret_datatables;
}

function nv_get_datatable_gene_list(win)
{
	return navicell.dataset.getSortedGeneNames();
}

function nv_get_datatable_sample_list(win)
{
	var sample_names = [];
	for (var sample_name in navicell.dataset.getSamples()) {
		sample_names.push(sample_name);
	}
	return sample_names;
}

function nv_get_hugo_list(win)
{
	return navicell.mapdata.getHugoNames();
}

function nv_get_biotype_list(win)
{
	var ret_biotypes = [];
	var biotypes = navicell.biotype_factory.getBiotypes();
	for (biotype_name in biotypes) {
		var biotype = biotypes[biotype_name];
		ret_biotypes.push({name: biotype_name, type: biotype.type.getTypeString(), subtype: biotype.type.getSubtypeString()});
	}
	return ret_biotypes;
}

function nv_heatmap_editor_perform(win, command, arg1, arg2)
{
	win = nv_win(win);
	var module = get_module(win);
	var drawing_config = navicell.getDrawingConfig(module);
	var heatmap_config = drawing_config.getHeatmapConfig();

	if (command == "open") {
		$("#heatmap_editor_div", win.document).dialog("open");
	} else if (command == "close") {
		$("#heatmap_editor_div", win.document).dialog("close");
	} else if (command == "select_datatable") {
		var idx = arg1;

		// if arg2 begins with a # it is number, otherwise a datatable name or id ?
		var is_datatable_num = arg2[0] == '#'
		if (is_datatable_num || !is_int(arg2)) {
			var selected_datatable = (is_datatable_num ? parseInt(arg2.substring(1)) : arg2);
			var datatable_num = 0;
			var datatables = navicell.dataset.datatables;
			for (var datatable_name in navicell.dataset.datatables) {
				if (selected_datatable == datatable_num || selected_datatable == datatable_name) {
					var datatable = datatables[datatable_name];
					$("#heatmap_editor_datatable_" + idx, win.document).val(datatable.getId());
					heatmap_editor_set_editing(true, idx, win.document.map_name);
					return;
				}
				datatable_num++;
			}
			$("#heatmap_editor_datatable_" + idx, win.document).val(-1);
			heatmap_editor_set_editing(true, undefined, win.document.map_name);
		} else {
			$("#heatmap_editor_datatable_" + idx, win.document).val(arg2);
			heatmap_editor_set_editing(true, idx, win.document.map_name);
		}
	} else if (command == "select_sample") {
		var idx = arg1;
		var is_sample_num = arg2[0] == '#'
		if (is_sample_num || !is_int(arg2)) {
			var selected_sample = (is_sample_num ? parseInt(arg2.substring(1)) : arg1);
			var sample_names = get_sample_names();
			for (var sample_num in sample_names) {
				var sample_name = sample_names[sample_num];
				if (selected_sample == sample_num || selected_sample == sample_name) {
					var sample = navicell.dataset.samples[sample_name];
					$("#heatmap_editor_gs_" + idx, win.document).val("s_" + sample.getId());
					heatmap_editor_set_editing(true, undefined, win.document.map_name);
					return;
				}
			}
			$("#heatmap_editor_gs_" + idx, win.document).val(-1);
			heatmap_editor_set_editing(true, undefined, win.document.map_name);
		} else {
			$("#heatmap_editor_gs_" + idx, win.document).val(arg2);
			heatmap_editor_set_editing(true, undefined, win.document.map_name);
		}
	} else if (command == "apply") {
		var interactive = arg1;
		var msg = get_heatmap_config_message(true);
		if (msg) {
			if (interactive) {
				warning_dialog("Apply cannot be performed", msg, win);
			} else {
				throw "nv_heatmap_editor_perform: apply cannot be performed";
			}
			return;
		}
		$("#drawing_config_chart_type", win.document).val('Heatmap');
		drawing_config.setDisplayCharts($("#drawing_config_chart_display", win.document).attr("checked"), $("#drawing_config_chart_type", win.document).val());

		heatmap_editor_apply(drawing_config.getHeatmapConfig());
		heatmap_editor_apply(drawing_config.getEditingHeatmapConfig());
		drawing_config.getHeatmapConfig().shrink();
		drawing_config.getEditingHeatmapConfig().shrink();
		update_heatmap_editor(win.document);
		heatmap_editor_set_editing(false, undefined, win.document.map_name);
		$("#drawing_config_chart_display", win.document).attr("checked", true);
		$("#drawing_config_chart_type", win.document).val('Heatmap');
		drawing_config.setDisplayCharts($("#drawing_config_chart_display", win.document).attr("checked"), $("#drawing_config_chart_type", win.document).val());
		drawing_config.apply();
	} else if (command == "clear_samples" || command == "all_samples" || command == "all_groups" || command == "from_barplot") {
		heatmap_sample_action_perform(command, 0, win);
	} else if (command == "set_transparency") {
		var value = arg1;
		heatmap_config.getSlider().slider("value", value);
		heatmap_config.setTransparency(value);
	} else if (command == "cancel") {
		drawing_config.getEditingHeatmapConfig().cloneFrom(drawing_config.getHeatmapConfig());
		update_heatmap_editor(win.document);
		heatmap_editor_set_editing(false, undefined, win.document.map_name);
		nv_heatmap_editor_perform(win, "close");
		//$("#heatmap_editor_div", win.document).dialog("close");
	} else {
		throw "nv_heatmap_editor_perform: unknown command \"" + command + "\"";
	}
	// must add:
	// apply
	// cancel
	// select_sample

	// select_datatables (arg1 and arg2: array of same length expected)
	// select_samples (arg1 and arg2: array of same length expected)
	return null;
}

function nv_map_staining_editor_perform(win, command, arg1, arg2)
{
	win = nv_win(win);
	var module = get_module(win);
	var drawing_config = navicell.getDrawingConfig(module);
	var map_staining_config = drawing_config.getMapStainingConfig();

	var map_staining_editor = $("#map_staining_editor_div", win.document);
	if (command == "open") {
		map_staining_editor.dialog("open");
	} else if (command == "close") {
		map_staining_editor.dialog("close");
	} else if (command == "select_datatable") {
		// TBD: action to be called from mouse event (update_map_staining_editor)
		var is_datatable_num = arg1[0] == '#'
		if (is_datatable_num || !is_int(arg1)) {
			var selected_datatable = (is_datatable_num ? parseInt(arg1.substring(1)) : arg1);
			var datatable_num = 0;
			var datatables = navicell.dataset.datatables;
			for (var datatable_name in navicell.dataset.datatables) {
				if (selected_datatable == datatable_num || selected_datatable == datatable_name) {
					var datatable = datatables[datatable_name];
					$("#map_staining_editor_datatable_color", win.document).val(datatable.getId());
					map_staining_editor_set_editing(true, "color", win.document.map_name);
					return;
				}
				datatable_num++;
			}
			$("#map_staining_editor_datatable_color", win.document).val(-1);
			map_staining_editor_set_editing(true, "color", win.document.map_name);
		} else {
			$("#map_staining_editor_datatable_color", win.document).val(arg1);
			map_staining_editor_set_editing(true, "color", win.document.map_name);
		}
	} else if (command == "select_sample") {
		// TBD: action to be called from mouse event (update_map_staining_editor)
		var is_sample_num = arg1[0] == '#'
		if (is_sample_num || !is_int(arg1)) {
			var selected_sample = (is_sample_num ? parseInt(arg1.substring(1)) : arg1);
			var sample_names = get_sample_names();
			for (var sample_num in sample_names) {
				var sample_name = sample_names[sample_num];
				if (selected_sample == sample_num || selected_sample == sample_name) {
					var sample = navicell.dataset.samples[sample_name];
					$("#map_staining_editor_gs", win.document).val("s_" + sample.getId());
					map_staining_editor_set_editing(true, "color", win.document.map_name);
					return;
				}
			}
			$("#map_staining_editor_gs", win.document).val(-1);
			map_staining_editor_set_editing(true, "color", win.document.map_name);
		} else {
			$("#map_staining_editor_gs", win.document).val(arg1);
			map_staining_editor_set_editing(true, "color", win.document.map_name);
		}
	} else if (command == "apply") {
		var interactive = arg1;
		var msg = get_map_staining_config_message(true);
		if (msg) {
			if (interactive) {
				warning_dialog("Apply cannot be performed", msg, win);
			} else {
				throw "nv_map_staining_editor_perform: apply cannot be performed";
			}
			return;
		}
		map_staining_editor_apply(drawing_config.getMapStainingConfig());
		map_staining_editor_apply(drawing_config.getEditingMapStainingConfig());
		update_map_staining_editor(win.document);
		$("#drawing_config_map_staining_display").attr("checked", true);
		drawing_config.setDisplayMapStaining(true);
		navicell.getMapTypes(module).setMapTypeByMapStaining(true);
		map_staining_editor_set_editing(false, "color", win.document.map_name);
		drawing_config.apply(win);
	} else if (command == "set_transparency") {
		// TBD: action to be called from mouse event (update_map_staining_editor)
		var value = arg1;
		map_staining_config.getSlider().slider("value", value);
		map_staining_config.setTransparency(value);
	} else if (command == "cancel") {
		drawing_config.getEditingMapStainingConfig().cloneFrom(drawing_config.getMapStainingConfig());
		update_map_staining_editor(win.document);
		map_staining_editor_set_editing(false, "color", win.document.map_name);
		map_staining_editor.dialog("close");
	} else {
		throw "nv_map_staining_editor_perform: unknown command \"" + command + "\"";
	}
}

function nv_drawing_config_perform(win, command, arg1, arg2)
{
	win = nv_win(win);
	var module = get_module(win);
	var drawing_config = navicell.getDrawingConfig(module);
	var drawing_config_dialog = $("#drawing_config_div", win.document);

	if (command == "open") {
		drawing_config_dialog.dialog("open");
	} else if (command == "close") {
		drawing_config_dialog.dialog("close");
	} else if (command == "select_map_staining") {
		// TBD: action to be called from mouse event (onchange on drawing_config_map_staining_display)
		var checked = arg1;
		drawing_config.setDisplayMapStaining(checked);
		if (checked) {
			$("#drawing_config_map_staining_display", win.document).attr("checked", "checked");
		} else {
			$("#drawing_config_map_staining_display", win.document).removeAttr("checked");
		}
	} else if (command == "select_heatmap") {
		// TBD: action to be called from mouse event (onchange on drawing_config_map_char*)
		var checked = arg1;
		if (checked) {
			$("#drawing_config_map_chart_display", win.document).attr("checked", "checked");
			drawing_config.displayCharts(checked, "Heatmap");
			$("#drawing_config_chart_type", win.document).val(drawing_config.displayCharts());
		} else {
			$("#drawing_config_map_chart_display", win.document).removeAttr("checked");
		}
	} else if (command == "select_barplot") {
		// TBD: action to be called from mouse event (onchange on drawing_config_map_char*)
		var checked = arg1;
		if (checked) {
			drawing_config.displayCharts(checked, "Barplot");
			$("#drawing_config_map_chart_display", win.document).attr("checked", "checked");
			$("#drawing_config_chart_type", win.document).val(drawing_config.displayCharts());
		} else {
			$("#drawing_config_map_chart_display", win.document).removeAttr("checked");
		}
	} else if (command == "apply") {
		drawing_config.setDisplayCharts($("#drawing_config_chart_display", win.document).attr("checked"), $("#drawing_config_chart_type", win.document).val());
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			drawing_config.setDisplayGlyphs(num, $("#drawing_config_glyph_display_" + num, win.document).attr("checked"));
		}
		var map_staining = $("#drawing_config_map_staining_display", win.document).attr("checked");
		drawing_config.setDisplayMapStaining(map_staining);
		navicell.getMapTypes(module).setMapTypeByMapStaining(map_staining);
		drawing_config.apply();
	} else if (command == "cancel") {
		if (drawing_config.displayCharts()) {
			$("#drawing_config_chart_display", win.document).attr("checked", "checked");
			$("#drawing_config_chart_type", win.document).val(drawing_config.displayCharts());
		} else {
			$("#drawing_config_chart_display", win.document).removeAttr("checked");
		}
		$("#drawing_config_old_marker", win.document).val(drawing_config.displayOldMarkers());
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			if (drawing_config.displayGlyphs(num)) {
				$("#drawing_config_glyph_display_" + num).attr("checked", "checked");
			} else {
				$("#drawing_config_glyph_display_" + num).removeAttr("checked");
			}
		}
		if (drawing_config.displayDLOsOnAllGenes()) {
			$("#drawing_config_display_all", win.document).attr("checked", "checked");
			$("#drawing_config_display_selected", win.document).removeAttr("checked");
		} else {
			$("#drawing_config_display_all", win.document).removeAttr("checked");
			$("#drawing_config_display_selected", win.document).attr("checked", "checked");
		}
		drawing_editing(false);
		drawing_config_dialog.dialog("close");
	} else {
		throw "nv_drawing_config_perform: unknown command \"" + command + "\"";
	}
}

function nv_now()
{
	var date = new Date();
	var month = (date.getMonth()+1) + "";
	if (month.length == 1) {
		month = "0" + month;
	}
	var day = date.getDate() + "";
	if (day.length == 1) {
		day = "0" + day;
	}
	return {date: date.toLocaleTimeString() + " " + date.getFullYear() + "-" + month + "-" + day, timestamp: date.getTime()};
}

function nv_record(win, to_encode) {
	var history = $("#command-history", win.document).val();
	var now = to_encode.now;
	var cmd = to_encode.cmd;
	var last = cmd[cmd.length-1];
	$("#command-history", win.document).val((history ? history + "\n\n" : "") + "## " + now.date + "\n" + cmd);
}

var nv_handlers = {
	"nv_open_module": nv_open_module,
	"nv_find_entities": nv_find_entities,
	"nv_select_entity": nv_select_entity,
	"nv_set_zoom": nv_set_zoom,
	"nv_uncheck_all_entities": nv_uncheck_all_entities,
	"nv_scroll": nv_scroll,

	"nv_import_datatables": nv_import_datatables,
	"nv_prepare_import_dialog": nv_prepare_import_dialog,
	"nv_heatmap_editor_perform": nv_heatmap_editor_perform,
	"nv_map_staining_editor_perform": nv_map_staining_editor_perform,
	"nv_drawing_config_perform": nv_drawing_config_perform,

	"nv_get_module_list": nv_get_module_list,
	"nv_get_datatable_list": nv_get_datatable_list,
	"nv_get_datatable_gene_list": nv_get_datatable_gene_list,
	"nv_get_datatable_sample_list": nv_get_datatable_sample_list,
	"nv_get_hugo_list": nv_get_hugo_list,
	"nv_get_biotype_list": nv_get_biotype_list,

	"nv_get_command_history": nv_get_command_history,
	"nv_execute_commands": nv_execute_commands
};

// nv_perform("nv_import_datatables", window, "Datatable list", "", "", "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/datatable_list.txt", {import_display_markers: false, import_display_barplot: true});

function nv_perform(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6)
{
	var action = nv_handlers[action_str];
	if (!action) {
		win.console.log("nv_perform: error no action \"" + action_str + "\"");
		return;
	}
	var ret = action(win, arg1, arg2, arg3, arg4, arg5, arg6);
	nv_record(win, nv_encode_action(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6));
	return ret;
}

//
// encode/decode
//

var nv_CMD_MARK = "@COMMAND";

function nv_push_arg(args, arg)
{
	if (arg != undefined) {
		args.push(arg);
	}
}

function nv_encode_data(data)
{
	return JSON.stringify(data);
}

function nv_encode_action(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6)
{
	var map = {};
	var args = [];
	nv_push_arg(args, arg1);
	nv_push_arg(args, arg2);
	nv_push_arg(args, arg3);
	nv_push_arg(args, arg4);
	nv_push_arg(args, arg5);
	nv_push_arg(args, arg6);
	var now = nv_now();
	map["action"] = action_str;
	map["module"] = get_module(win);
	map["args"] = args;
	map["timestamp"] = now.timestamp;
	return {now: now, cmd: nv_CMD_MARK + " " + JSON.stringify(map)};
}

function nv_get_url(cmd)
{
	if (cmd.trim().length == 0) {
		return;
	}
	var url = window.location.href;
	console.log("CMD [" + cmd + "]");
	console.log("CMD2 [" + cmd.replace(COMMENT_REGEX, "") + "]");
	console.log("CMD3 [" + cmd.replace(COMMENT_REGEX, "").replace(LINE_BREAK_REGEX_G, "") + "]");
	var data = cmd.replace(COMMENT_REGEX, "").replace(LINE_BREAK_REGEX_G, "");
	var url = window.location.href;
	var idx = url.lastIndexOf('/');
	url = url.substr(0, idx);
	console.log("DATA [" + data + "]");
	var geturl = url + "/index.php?command=" + escape(data);
	var posturl = url + "/launcher.php";
	var text = "GET URL<br/>" + geturl + "<br/><br/>" + "POST URL<br/>" + posturl + "<br/>POST data<br/>" + data;
	display_dialog("URL", "", text, window);



}

var RSP_ID = 1;
var SERVER_TRACE = 1;

function nv_rsp(url, data) {
	if (SERVER_TRACE) {
		console.log("nv_rsp: " + url + "  " + data + " " + RSP_ID++);
	}
	$.ajax(url,
	       {
		       async: true,
		       dataType: 'text',
		       type: 'POST',
		       data: {data: nv_encode_data(data)},
		       success: function(ret) {
			       if (SERVER_TRACE) {
				       console.log("response has been sucesfully send [" + ret + "]");
			       }
		       },
		       
		       error: function(e) {
			       console.log("sending response failure");
		       }
	       }
	      );
}

function nv_rcv(base_url, url) {
	if (SERVER_TRACE) {
		console.log("nv_rcv: " + url);
	}
	var rsp_url = base_url + "&mode=srv2cli&perform=rsp";
	$.ajax(url,
	       {
		       async: true,
		       dataType: 'text',
		       success: function(cmd) {
			       if (SERVER_TRACE) {
				       console.log("received [" + cmd + "]");
			       }
			       if (cmd.trim().length > 0) {
				       try {
					       cmd = cmd.replace(ESCAPE_LINE_BREAK, "\n").replace(ESCAPE_QUOTE, '"').replace(ESCAPE_TAB, '\t');
					       //console.log("CMD [" + cmd + "]");
					       var data = nv_decode(cmd);
					       var rspdata = {status: 0, data: (data ? data : '')};

					       if (SERVER_TRACE) {
						       console.log("returning data " + rspdata);
					       }
					       nv_rsp(rsp_url, rspdata);
				       } catch(e) {
					       console.log("nv_rcv exception: " + e);
					       nv_rsp(rsp_url, {status: 1, data: e.toString()});
				       }
			       }
			       nv_rcv(base_url, url);
		       },
		       
		       error: function(e) {
			       console.log("error DECODING " + e);
		       }
	       }
	      );
}

function nv_server(win, id) {
	var href = window.location.href;
	var idx = href.indexOf('/navicell/');
	var base_url = href.substr(0, idx) + "/cgi-bin/nv_protocol.php?id=" + id;
	var url = base_url + "&mode=cli2srv&perform=rcv&block=on";
	nv_rcv(base_url, url);
}

//
// to be moved to nv_decode.js ?
//

var COMMENT_REGEX = new RegExp("##.*(\r\n?|\n)", "g");

// decode
function nv_decode(str)
{
	var o_decoding = nv_decoding;
	nv_decoding = true;
	var ret = null;

	str = str.replace(COMMENT_REGEX, "");
	var action_arr = str.split(nv_CMD_MARK);
	for (var idx in action_arr) {
		var action = action_arr[idx].trim();
		if (action.length == 0) {
			continue;
		}
		var action_map = JSON.parse(action);
		var action_str = action_map.action;
		var module = action_map.module;
		var args = action_map.args;
		var win = module ? get_win(module) : window;
		if (!win) {
			console.log("nv_decode: unknown module " + module);
			continue;
		}
		ret = nv_perform(action_str, win, args[0], args[1], args[2], args[3], args[4], args[5]);
	}

	nv_decoding = o_decoding;
	return ret;
}

/*
nv_heatmap_editor_perform(window, "open");

nv_heatmap_editor_perform(window, "select_datatable", 0, '#0');
nv_heatmap_editor_perform(window, "select_datatable", 1, '#1');
nv_heatmap_editor_perform(window, "select_datatable", 2, '#2');

nv_heatmap_editor_perform(window, "select_sample", 0, '#0');
nv_heatmap_editor_perform(window, "select_sample", 1, '#4');
nv_heatmap_editor_perform(window, "select_sample", 2, '#3');
nv_heatmap_editor_perform(window, "select_sample", 3, '#6');

nv_heatmap_editor_perform(window, "set_transparency", 80);
nv_heatmap_editor_perform(window, "apply");

// unselect samples
nv_heatmap_editor_perform(window, "select_sample", 0, -1);
nv_heatmap_editor_perform(window, "select_sample", 2, -1);


nv_open_module(window, "SQUARE");
nv_heatmap_editor_perform("OVAL", "open");
nv_heatmap_editor_perform("OVAL", "select_datatable", 0, '#0');
nv_heatmap_editor_perform("20111118modelc:master", "select_datatable", 1, '#2');
*/

/*
|@@|nv_import_datatables|--|S20111118modelc:master|--|SDatatable list|--|S|--|S|--|Sfile:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/datatable_list.txt|--|Mimport_display_markers|::|Bfalse|::|import_display_barplot|::|Btrue

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sopen

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sselect_datatable|--|N0|--|S#1

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sselect_datatable|--|N2|--|S#2

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sselect_sample|--|N0|--|S#0

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sselect_sample|--|N1|--|S#2

## 6:04:36 PM 2014-08-20
|@@|nv_heatmap_editor_perform|--|S20111118modelc:master|--|Sapply

## 6:04:36 PM 2014-08-20
|@@|nv_find_entities|--|S20111118modelc:master|--|SAK.*

|@@|nv_open_module|--|S|--|SSQUARE
|@@|nv_open_module|--|S|--|SOVAL
*/

//
// OLD //
/*
var nv_STRING_MARK = "S";
var nv_NUMBER_MARK = "N";
var nv_BOOL_MARK = "B";
var nv_UNK_MARK = "U";
var nv_MAP_MARK = "M";
var nv_OBJ_MARK = "O";
var nv_LIST_MARK = "L";
var nv_KEY_VAL = "|::|";
var nv_ARG_SEP = "|--|";

var nv_type_markers = {
	"string" : nv_STRING_MARK,
	"number" : nv_NUMBER_MARK,
	"boolean" : nv_BOOL_MARK,
	"object" : nv_OBJ_MARK
};
	
function nv_encode_arg_old(arg, reentrant)
{
	var type_marker = nv_type_markers[typeof arg];
	if (!type_marker) {
		console.log("nv_encode: type " + (typeof arg) + " " + arg + " not supported");
		//return nv_ARG_SEP + nv_UNK_MARK;
		return nv_ARG_SEP + nv_STRING_MARK;
	}
	if (type_marker == nv_OBJ_MARK) {
		type_marker = (arg.length == undefined) ? nv_MAP_MARK : nv_LIST_MARK;
	}
	var str = (reentrant ? "" : nv_ARG_SEP) + type_marker;
	if (type_marker == nv_MAP_MARK || type_marker == nv_LIST_MARK) {
		//if (type_marker == nv_MAP_MARK) {console.log("encode map");} else {console.log("encode list " + arg.length);}
		var has_key = false;
		for (var key in arg) {
			var val = arg[key];
			if (typeof val != 'function') {
				if (typeof val != 'object') { // object within object not supported
					str += (has_key ? nv_KEY_VAL : "") + key + nv_KEY_VAL + nv_encode_arg_old(val, true);
					has_key = true;
				}
			}
		}
	} else {
		str += arg;
	}
	return str;
}

function nv_decode_arg_old(arg)
{
	if (!arg) {
		return null;
	}
	var type_marker = arg[0];
	arg = arg.substring(1);
	if (type_marker == nv_UNK_MARK) {
		return "";
	}
	if (type_marker == nv_NUMBER_MARK) {
		return parseFloat(arg);
	}
	if (type_marker == nv_BOOL_MARK) {
		return arg == "true" || arg == "TRUE" ? true : false;
	}
	if (type_marker == nv_STRING_MARK) {
		return arg;
	}
	if (type_marker == nv_MAP_MARK) {
		var map = {};
		console.log("receive a map");
		if (arg) {
			var key_val_arr = arg.split(nv_KEY_VAL);
			for (var idx3 = 0; idx3 < key_val_arr.length; idx3 += 2) {
				var key = key_val_arr[idx3];
				map[key] = nv_decode_arg_old(key_val_arr[idx3+1]);
			}
		}
		return map;
	}
	if (type_marker == nv_LIST_MARK) {
		var list = [];
		console.log("receive a list");
		if (arg) {
			var key_val_arr = arg.split(nv_KEY_VAL);
			console.log("length " + key_val_arr.length);
			for (var idx3 = 0; idx3 < key_val_arr.length; ++idx3) {
				list.push(nv_decode_arg_old(key_val_arr[idx3]));
			}
		}
		return list;
	}
	return null;
}

function nv_encode_old(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6)
{
	var str = nv_CMD_MARK + action_str + nv_ARG_SEP + nv_STRING_MARK + get_module(win);
	var args = [];
	args.push(arg1);
	args.push(arg2);
	args.push(arg3);
	args.push(arg4);
	args.push(arg5);
	args.push(arg6);
	
	for (var idx = 0; idx < args.length; ++idx) {
		var arg = args[idx];
		if (arg == undefined) {
			break;
		}
		str += nv_encode_arg_old(arg);
	}
	//console.log("encoding " + action_str + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5 + " " + arg6 + " => " + str);
	return str;
}

function nv_decode_old(str)
{
	var o_decoding = nv_decoding;
	nv_decoding = true;
	var ret = null;

	str = str.replace(COMMENT_REGEX, "");
	var action_arr = str.split(nv_CMD_MARK);
	for (var idx in action_arr) {
		var tmpargs = action_arr[idx].split(nv_ARG_SEP);
		if (tmpargs.length < 2) {
			continue;
		}
		var args = [];
		for (var idx2 in tmpargs) {
			args.push(tmpargs[idx2].trim());
		}
		var action_str = args[0];
		if (!action_str) {
			continue;
		}
		var module = args[1].substring(1);
		var win = module ? get_win(module) : window;
		if (!win) {
			console.log("nv_decode: unknown module " + module);
			continue;
		}
		for (var idx2 = 2; idx2 < args.length; ++idx2) {
			args[idx2] = nv_decode_arg(args[idx2]);
		}
		ret = nv_perform(action_str, win, args[2], args[3], args[4], args[5], args[6], args[7]);
	}

	nv_decoding = o_decoding;
	return ret;
}

*/
