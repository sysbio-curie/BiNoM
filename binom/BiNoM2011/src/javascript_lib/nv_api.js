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

function get_datable_desc_list(type, name, file_elem, url, dt_desc_list) {
	var ready = $.Deferred();
	if (type == DATATABLE_LIST) {
		if (url) {
			$.ajax(url,
			       {
				       async: true,
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

// API

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

function nv_find_entities(win, search)
{
	navicell.mapdata.findJXTree(win, search, false, 'subtree', {div: $("#result_tree_contents", win.document).get(0)});
	$("#right_tabs", win.document).tabs("option", "active", 1);
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
	var module = win.document.navicell_module_name;
	var drawing_config = navicell.getDrawingConfig(module);
	var dt_desc_list = [];
	var ready = get_datable_desc_list(type.trim(), name.trim(), file_elem, url.trim(), dt_desc_list);

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
			var datatable = navicell.dataset.readDatatable(dt_desc.type, dt_desc.name, dt_desc.file_elem, dt_desc.url, win);
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
									$("#barplot_editing").html(EDITING_CONFIGURATION);
									$("#barplot_editor_div").dialog("open");
								} else {
									$("#drawing_config_chart_display").attr("checked", 'checked')
									$("#drawing_config_chart_type").val('Barplot');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
									barplot_sample_action('allsamples', DEF_OVERVIEW_BARPLOT_SAMPLE_CNT);
									
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
									$("#heatmap_editing").html(EDITING_CONFIGURATION);
									$("#heatmap_editor_div").dialog("open");
								} else {
									$("#drawing_config_chart_display").attr("checked", 'checked')
									$("#drawing_config_chart_type").val('Heatmap');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
									if (!drawing_config.getHeatmapConfig().getSampleOrGroupCount()) {
										heatmap_sample_action('allsamples', DEF_OVERVIEW_HEATMAP_SAMPLE_CNT);
									} else {
										heatmap_sample_action('pass');
									}

									heatmap_editor_apply(drawing_config.getHeatmapConfig());
									heatmap_editor_apply(drawing_config.getEditingHeatmapConfig());
									heatmap_editor_set_editing(false);

									max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;

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
}

function nv_heatmap_editor_perform(win, command, arg1, arg2)
{
	if (command == "open") {
		$("#heatmap_editor_div", win.document).dialog("open");
	} else if (command == "close") {
		$("#heatmap_editor_div", win.document).dialog("close");
	} else if (command == "select_datatable") {
		var idx = arg1;
		var selected_datatable = arg2;
		var datatable_num = 0;
		var datatables = navicell.dataset.datatables;
		for (var datatable_name in navicell.dataset.datatables) {
			if (selected_datatable == datatable_num) {
				var datatable = datatables[datatable_name];
				$("#heatmap_editor_datatable_" + idx, win.document).val(datatable.getId());
				heatmap_editor_set_editing(true, idx, win.document.map_name);
				return;
			}
			datatable_num++;
		}
		$("#heatmap_editor_datatable_" + idx, win.document).val(-1);
		heatmap_editor_set_editing(true, idx, win.document.map_name);
	} else if (command == "select_sample") {
		var idx = arg1;
		var selected_sample = arg2;
		var sample_names = get_sample_names();
		for (var sample_num in sample_names) {
			var sample_name = sample_names[sample_num];
			if (selected_sample == sample_num) {
				var sample = navicell.dataset.samples[sample_name];
				$("#heatmap_editor_gs_" + idx, win.document).val("s_" + sample.getId());
				heatmap_editor_set_editing(true, undefined, win.document.map_name);
				return;
			}
		}
		$("#heatmap_editor_gs_" + idx, win.document).val(-1);
		heatmap_editor_set_editing(true, undefined, win.document.map_name);
	} else if (command == "apply") {
		var interactive = arg1;
		var msg = get_heatmap_config_message(true);
		if (msg) {
			if (interactive) {
				warning_dialog("Apply cannot be performed", msg, win);
			} else {
				win.console.log("Apply cannot be performed", msg, win);
			}
			return;
		}
		var module = get_module(win);
		var drawing_config = navicell.getDrawingConfig(module);
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
	} else if (command == "cancel") {
		var module = get_module(win);
		var drawing_config = navicell.getDrawingConfig(module);
		drawing_config.getEditingHeatmapConfig().cloneFrom(drawing_config.getHeatmapConfig());
		update_heatmap_editor(win.document);
		heatmap_editor_set_editing(false, undefined, win.document.map_name);
		nv_heatmap_editor_perform(win, "close");
		//$("#heatmap_editor_div", win.document).dialog("close");
	} else {
		win.console.log("nv_heatmap_editor_perform: unknown command \"" + command + "\"");
	}
	// must add:
	// apply
	// cancel
	// select_sample

	// select_datatables (arg1 and arg2: array of same length expected)
	// select_samples (arg1 and arg2: array of same length expected)
}

/*
nv_heatmap_editor_perform(window, "open");

nv_heatmap_editor_perform(window, "select_datatable", 0, 1);
nv_heatmap_editor_perform(window, "select_datatable", 1, 2);
nv_heatmap_editor_perform(window, "select_datatable", 2, 2);

nv_heatmap_editor_perform(window, "select_sample", 2, 3);
nv_heatmap_editor_perform(window, "select_sample", 0, 0);

nv_heatmap_editor_perform(window, "apply");

// unselect samples
nv_heatmap_editor_perform(window, "select_sample", 0, -1);
nv_heatmap_editor_perform(window, "select_sample", 2, -1);

*/
