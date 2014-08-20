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

function get_datatable_desc_list(type, name, file_elem, url, dt_desc_list, async) {
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
	win = nv_win(win);
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
	win = nv_win(win);
	var module = win.document.navicell_module_name;
	var drawing_config = navicell.getDrawingConfig(module);
	var dt_desc_list = [];
	var async = params && params.async != undefined ? params.async : false;
	console.log("ASYNC : " + async);
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
									$("#barplot_editing").html(EDITING_CONFIGURATION);
									$("#barplot_editor_div").dialog("open");
								} else {
									$("#drawing_config_chart_display").attr("checked", 'checked')
									$("#drawing_config_chart_type").val('Barplot');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
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
									$("#heatmap_editing").html(EDITING_CONFIGURATION);
									$("#heatmap_editor_div").dialog("open");
								} else {
									$("#drawing_config_chart_display").attr("checked", 'checked')
									$("#drawing_config_chart_type").val('Heatmap');
									drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
									if (!drawing_config.getHeatmapConfig().getSampleOrGroupCount()) {
										heatmap_sample_action('all_samples', DEF_OVERVIEW_HEATMAP_SAMPLE_CNT);
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
		if (arg2[0] == '#') {
			var selected_datatable = parseInt(arg2.substring(1));
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
			heatmap_editor_set_editing(true, undefined, win.document.map_name);
		} else {
			$("#heatmap_editor_datatable_" + idx, win.document).val(arg2);
			heatmap_editor_set_editing(true, idx, win.document.map_name);
		}
	} else if (command == "select_sample") {
		var idx = arg1;
		if (arg2[0] == '#') {
			var selected_sample = parseInt(arg2.substring(1));
			// if arg2 begins with a # it is number, otherwise a sample name or id ?
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
				win.console.log("Apply cannot be performed", msg, win);
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
		heatmap_sample_action(command, 0, win);
	} else if (command == "set_slider_value") {
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
		win.console.log("nv_heatmap_editor_perform: unknown command \"" + command + "\"");
	}
	// must add:
	// apply
	// cancel
	// select_sample

	// select_datatables (arg1 and arg2: array of same length expected)
	// select_samples (arg1 and arg2: array of same length expected)
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
	return date.toLocaleTimeString() + " " + date.getFullYear() + "-" + month + "-" + day; 
}

function nv_record(win, cmd) {
	var history = $("#command-history", win.document).val();
	var last = cmd[cmd.length-1];
	$("#command-history", win.document).val((history ? history + "\n\n" : "") + "## " + nv_now() + "\n" + cmd);
}

var nv_handlers = {
	"nv_find_entities": nv_find_entities,
	"nv_import_datatables": nv_import_datatables,
	"nv_heatmap_editor_perform": nv_heatmap_editor_perform
};

// nv_perform("nv_import_datatables", window, "Datatable list", "", "", "file:///bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/datatable_list.txt", {import_display_markers: false, import_display_barplot: true});

function nv_perform(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6)
{
	var action = nv_handlers[action_str];
	if (!action) {
		win.console.log("nv_perform: error no action \"" + action_str + "\"");
		return;
	}
	action(win, arg1, arg2, arg3, arg4, arg5, arg6);
	nv_record(win, nv_encoder(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6));
}

//
// encoder/decoder
//

var nv_CMD_MARK = "|@@|";
var nv_STRING_MARK = "S";
var nv_NUMBER_MARK = "N";
var nv_BOOL_MARK = "B";
var nv_UNK_MARK = "U";
var nv_MAP_MARK = "M";
var nv_KEY_VAL = "|::|";
var nv_ARG_SEP = "|--|";

var nv_type_markers = {
	"string" : nv_STRING_MARK,
	"number" : nv_NUMBER_MARK,
	"boolean" : nv_BOOL_MARK,
	"object" : nv_MAP_MARK
};
	
// coder
function nv_encode_arg(arg, reentrant)
{
	var type_marker = nv_type_markers[typeof arg];
	if (!type_marker) {
		console.log("nv_encoder: type " + (typeof arg) + " " + arg + " not supported");
		//return nv_ARG_SEP + nv_UNK_MARK;
		return nv_ARG_SEP + nv_STRING_MARK;
	}
	var str = (reentrant ? "" : nv_ARG_SEP) + type_marker;
	if (type_marker == nv_MAP_MARK) {
		var has_key = false;
		for (var key in arg) {
			var val = arg[key];
			if (typeof val != 'function') {
				if (typeof val != 'object') { // object within object not supported
					str += (has_key ? nv_KEY_VAL : "") + key + nv_KEY_VAL + nv_encode_arg(val, true);
					has_key = true;
				}
			}
		}
	}
	else {
		str += arg;
	}
	return str;
}

function nv_decode_arg(arg)
{
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
		var key_val_arr = arg.split(nv_KEY_VAL);
		for (var idx3 = 0; idx3 < key_val_arr.length; idx3 += 2) {
			var key = key_val_arr[idx3];
			map[key] = nv_decode_arg(key_val_arr[idx3+1]);
		}
		return map;
	}
	return null;
}

function nv_encoder(action_str, win, arg1, arg2, arg3, arg4, arg5, arg6)
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
		str += nv_encode_arg(arg);
	}
	return str;
}

//
// to be moved to nv_decoder.js ?
//
// setTimeout(function() {nv_decoder("|@@|nv_heatmap_editor_perform|--||--|Sopen|@@|nv_heatmap_editor_perform|--|S|--|Sselect_datatable|--|N0|--|S#1|@@|nv_heatmap_editor_perform|--|S|--|Sselect_datatable|--|N2|--|S#2|@@|nv_heatmap_editor_perform|--|S|--|Sselect_sample|--|N0|--|S#0|@@|nv_heatmap_editor_perform|--|S|--|Sselect_sample|--|N1|--|S#2|@@|nv_heatmap_editor_perform|--|S|--|Sapply|@@|nv_find_entities|--|S|--|SAK.*");}, 2000);
//
var COMMENT_REGEX = new RegExp("##.*(\r\n?|\n)", "g");

// decoder
function nv_decoder(str)
{
	str = str.replace(COMMENT_REGEX, "");
	//console.log("STR [" + str + "]");
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
			console.log("nv_decoder: unknown module " + module);
			continue;
		}
		for (var idx2 = 2; idx2 < args.length; ++idx2) {
			args[idx2] = nv_decode_arg(args[idx2]);
		}
		nv_perform(action_str, win, args[2], args[3], args[4], args[5], args[6], args[7]);
	}
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

nv_heatmap_editor_perform(window, "set_slider_value", 80);
nv_heatmap_editor_perform(window, "apply");

// unselect samples
nv_heatmap_editor_perform(window, "select_sample", 0, -1);
nv_heatmap_editor_perform(window, "select_sample", 2, -1);

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
*/
