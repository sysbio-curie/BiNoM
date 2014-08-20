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

function nv1() {
	$("#datatable_input").css("display", "none");
	$("#right_tabs").css("height", "100%");
}

function nv2() {
	$("#datatable_input").css("display", "block");
	$("#right_tabs").css("height", "59.5%");
}


function display_dialog(title, header, msg, win)
{
	var dialog = $("#info_dialog", win.document);
	dialog.html("<br/><div style='text-align: center; font-weight: bold'>" + header + "</div><br/><div style='text-align: vertical-center'>" + "<br/>" + msg.replace(new RegExp("\n", "g"), "<br/>") + "</div>");
	dialog.dialog({
		autoOpen: false,
		width: 430,
		height: 350,
		modal: false,
		title: title,
		buttons: {
			"OK": function() {
				$(this).dialog("close");
			}
		}
	});
	dialog.dialog("open");
}

function warning_dialog(header, msg, win)
{
	display_dialog('Warning', header, msg, win);
}

var CANCEL_CLOSES = false;

$(function() {
	$('body').append("<div id='foo'></div>");

	var OPEN_DRAWING_EDITOR = true;

	$("#right_tabs").tabs();

	function build_datatable_import_dialog() {
		var select = $("#dt_import_type_select");
		select.html(get_biotype_select("dt_import_type", true));
	}

	build_datatable_import_dialog();

	var name = $("#dt_import_name");
	var file = $("#dt_import_file");
	var url = $("#dt_import_url");
	var type = $("#dt_import_type");
	var status = $("#dt_import_status");
	var import_display_markers = $("#dt_import_display_markers");
	var import_display_barplot = $("#dt_import_display_barplot");
	var import_display_heatmap = $("#dt_import_display_heatmap");
	var sample_file = $("#dt_sample_file");

	if (OPEN_DRAWING_EDITOR) {
		$("#display_heatmap").html("Display Heatmap (will open heatmap editor after import)");
		$("#display_barplot").html("Display Barplot (will open barplot editor after import)");
	} else {
		$("#display_heatmap").html("Display Heatmap (up to " +  DEF_OVERVIEW_HEATMAP_SAMPLE_CNT + " samples)");
		$("#display_barplot").html("Display Barplot (up to " +  DEF_OVERVIEW_BARPLOT_SAMPLE_CNT + " samples)");
	}

	var win = window;

	function error_message(error, add) {
		var html = add ? status.html() + "<br/>" : "";
		status.html(html + "<span class=\"error-message\">" + error + "</span>");
	}

	function status_message(message, add) {
		var html = add ? status.html() + "<br/>" : "";
		status.html(html + "<span class=\"status-message\">" + message + "</span>");
	}

	$("#search_dialog").dialog({
		autoOpen: false,
		width: 450,
		height: 840,
		modal: false,
		buttons: {
			"Search": function() {
				var patterns = $("#search_dialog_patterns").val();
				var eq_all = $("#search_dialog_match_eq_all").attr("checked") == "checked";
				var eq_any = $("#search_dialog_match_eq_any").attr("checked") == "checked";
				var neq_any = $("#search_dialog_match_neq_any").attr("checked") == "checked";
				var neq_all = $("#search_dialog_match_neq_all").attr("checked") == "checked";
				var mode_word = $("#search_dialog_pattern_mode_word").attr("checked") == "checked";
				var mode_regex = $("#search_dialog_pattern_mode_regex").attr("checked") == "checked";
				var in_labels = $("#search_dialog_search_in_labels").attr("checked") == "checked";
				var in_tags = $("#search_dialog_search_in_tags").attr("checked") == "checked";
				var in_annots = $("#search_dialog_search_in_annots").attr("checked") == "checked";
				var in_all = $("#search_dialog_search_in_all").attr("checked") == "checked";
				var all_classes = $("#search_dialog_class_all").attr("checked") == "checked";
				var all_classes_but_included = $("#search_dialog_class_all_but_included").attr("checked") == "checked";
				var all_classes_included = $("#search_dialog_class_all_included").attr("checked") == "checked";
				var select_classes = $("#search_dialog_class_select").attr("checked") == "checked";
				var search = "";
				var op;

				if (eq_any || neq_any) {
					search = patterns.replace(new RegExp("[ \t\n;]+", "g"), ","); 
				} else if (eq_all || neq_all) {
					search = patterns.replace(new RegExp("[\t\n;,]+", "g"), " "); 
				}
				search += " /";
				if (eq_any || eq_all) {
					search += "op=eq;";
				} else {
					search += "op=neq;";
				}
				if (mode_word) {
					search += "token=word;"
				} else {
					search += "token=regex;"
				}
				$("#search_dialog_search_in :selected").each(function(i, selected) {
					search += "in=" + $(selected).val() + ";";
				});
									    
				if (all_classes) {
					search += "class=all"
				} else if (all_classes_but_included) {
					search += "class=all_but_included"
				} else if (all_classes_included) {
					search += "class=all_included"
				} else {
					$("#search_dialog_class_choose :selected").each(function(i, selected) {
						search += (i ? "," : "class=") + $(selected).val();
					});
				}
				nv_perform("nv_find_entities", window, search);
				/*
				$("#right_tabs", window.document).tabs("option", "active", 1);
				navicell.mapdata.findJXTree(window, search, false, 'subtree', {div: $("#result_tree_contents", window.document).get(0)});
				*/
			},

			"OK": function() {
				$(this).dialog("close");
			}
		}
	});
	
	/*
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
	*/

	$("#dt_import_dialog" ).dialog({
		autoOpen: false,
		width: 550,
		height: 900,
		modal: false,
		buttons: {
			"Import": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				var error = "";
				if (!name.val() && type.val() != DATATABLE_LIST) {
					error = "Missing Name"
				}
				if (!file.val() && !url.val().trim()) {
					if (error) {
						error += ", ";
					} else {
						error = "Missing ";
					}
					error += "File or URL";
				}
				if (type.val() == "_none_") {
					if (error) {
						error += ", ";
					} else {
						error = "Missing ";
					}
					error += "Type";
				}
				if (file.val() && url.val().trim()) {
					if (error) {
						error += "<br/>";
					}
					error += "Cannot specify a File and an URL";
				}

				if (error) {
					error_message(error);
					return;
				}

				setTimeout(function() {
					error_message("");
					status_message("Importing...");
					var file_elem = (file ? file.get()[0].files[0] : null);
					nv_perform("nv_import_datatables", window, type.val().trim(), name.val().trim(), file_elem, url.val().trim(),
							     {status_message: status_message,
							      error_message: error_message,
							      open_drawing_editor: true,
							      import_display_markers: import_display_markers.attr('checked'),
							      import_display_barplot: import_display_barplot.attr('checked'),
							      import_display_heatmap: import_display_heatmap.attr('checked')});
					/*
					var dt_desc_list = [];
					var ready = get_datable_desc_list(type.val(), name.val().trim(), file_elem, url.val().trim(), dt_desc_list);

					ready.then(function() {
						var msg_cnt = 0;
						if (!dt_desc_list.length) {
							error_message("No Datatables Imported");
							return;
						}
						for (var idx in dt_desc_list) {
							var dt_desc = dt_desc_list[idx];
							var datatable = navicell.dataset.readDatatable(dt_desc.type, dt_desc.name, dt_desc.file_elem, dt_desc.url, window);
							if (datatable.error) {
								var error_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Failed</span></div>";
								error_str += "<table>";
								error_str += "<tr><td>Datatable</td><td>" + dt_desc.name + "</td></tr>";
								error_str += "<tr><td>Error</td><td>" + datatable.error + "</td></tr>";
								error_str += "</table>";
								error_message(error_str, msg_cnt++);
							} else {
								datatable.ready.then(function(my_datatable) {
									if (my_datatable.error) {
										var error_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Failed</span></div>";
										error_str += "<table>";
										error_str += "<tr><td>Datatable</td><td>" + my_datatable.name + "</td></tr>";
										error_str += "<tr><td>Error</td><td>" + my_datatable.error + "</td></tr>";
										error_str += "</table>";
										error_message(error_str, msg_cnt++);
									} else {
										var status_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Successful</span></div>";
										status_str += "<table>";
										status_str += "<tr><td>Datatable</td><td>" + my_datatable.name + "</td></tr>";
										status_str += "<tr><td>Samples</td><td>" + my_datatable.getSampleCount() + "</td></tr>\n";
										var opener = window;
										var nnn = 0;
										while (opener && opener.document.map_name) {
											status_str += "<tr><td>Genes mapped on " + opener.document.map_name + "</td><td>" + my_datatable.getGeneCountPerModule(opener.document.map_name) + "</td></tr>";
											opener = opener.opener;
											if (nnn++ > 4) {
												console.log("too many level of hierarchy !");
												break;
											}
										}
										status_str += "</table>";
										status_message(status_str, msg_cnt++);
										
										navicell.annot_factory.refresh();
										var display_graphics;
										if (import_display_barplot.attr('checked')) {
											var barplotConfig = drawing_config.getEditingBarplotConfig();
											barplotConfig.setDatatableAt(0, datatable);
											if (OPEN_DRAWING_EDITOR) {
												$("#barplot_editing").html(EDITING_CONFIGURATION);
												$("#barplot_editor_div").dialog("open");
											} else {
												$("#drawing_config_chart_type").val('Barplot');
												drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
												barplot_sample_action('all_samples', DEF_OVERVIEW_BARPLOT_SAMPLE_CNT);
												
												barplot_editor_apply(drawing_config.getBarplotConfig());
												barplot_editor_apply(drawing_config.getEditingBarplotConfig());
												barplot_editor_set_editing(false);

												max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

												display_graphics = true;
											}
										} else if (import_display_heatmap.attr('checked')) {
											var heatmapConfig = drawing_config.getEditingHeatmapConfig();
											var datatable_cnt = mapSize(navicell.dataset.datatables);
											for (var idx = 0; idx < datatable_cnt; ++idx) {
												if (!heatmapConfig.getDatatableAt(idx)) {
													break;
												}
											}
											heatmapConfig.setDatatableAt(idx, datatable);
											if (OPEN_DRAWING_EDITOR) {
												$("#heatmap_editing").html(EDITING_CONFIGURATION);
												$("#heatmap_editor_div").dialog("open");
											} else {
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
										var display_markers = import_display_markers.attr('checked');
										my_datatable.display(win.document.navicell_module_name, win, display_graphics, display_markers);
										drawing_editing(false);
										update_status_tables();
									}
								});
							}
						}
					})
					*/
				}, DISPLAY_TIMEOUT);
			},

			Clear: function() {
				name.val("");
				file.val("");
				url.val("");
				type.val("");
				status_message("");
			},

			OK: function() {
				$(this).dialog('close');
			}
		}});

	$("#dt_status_tabs").dialog({
		autoOpen: false,
		width: 800,
		height: 680,
		modal: false
	});

	$("#drawing_config_div").dialog({
		autoOpen: false,
		width: 420,
		height: 750,
		modal: false,

		buttons: {
			"Apply": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
				for (var num = 1; num <= GLYPH_COUNT; ++num) {
					drawing_config.setDisplayGlyphs(num, $("#drawing_config_glyph_display_" + num).attr("checked"));
				}
				var map_staining = $("#drawing_config_map_staining_display").attr("checked");
				drawing_config.setDisplayMapStaining(map_staining);
				navicell.getMapTypes(module).setMapTypeByMapStaining(map_staining);
				drawing_config.apply();
			},

			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				if (drawing_config.displayCharts()) {
					$("#drawing_config_chart_display").attr("checked", "checked");
					$("#drawing_config_chart_type").val(drawing_config.displayCharts());
				} else {
					$("#drawing_config_chart_display").removeAttr("checked");
				}
				$("#drawing_config_old_marker").val(drawing_config.displayOldMarkers());
				for (var num = 1; num <= GLYPH_COUNT; ++num) {
					if (drawing_config.displayGlyphs(num)) {
						$("#drawing_config_glyph_display_" + num).attr("checked", "checked");
					} else {
						$("#drawing_config_glyph_display_" + num).removeAttr("checked");
					}
				}
				if (drawing_config.displayDLOsOnAllGenes()) {
					$("#drawing_config_display_all").attr("checked", "checked");
					$("#drawing_config_display_selected").removeAttr("checked");
				} else {
					$("#drawing_config_display_all").removeAttr("checked");
					$("#drawing_config_display_selected").attr("checked", "checked");
				}
				drawing_editing(false);
				if (CANCEL_CLOSES) {
					$(this).dialog('close');
				}
			},
			"OK": function() {
				$(this).dialog('close');
			}
		}
	});

	$("#import_dialog").button().click(function() {
		if (!OPEN_DRAWING_EDITOR) {
			var module = get_module();
			var drawing_config = navicell.getDrawingConfig(module);
			var heatmap_sample_cnt = drawing_config.getHeatmapConfig().getSampleOrGroupCount();
			if (heatmap_sample_cnt) {
				var cnt = heatmap_sample_cnt > DEF_OVERVIEW_HEATMAP_SAMPLE_CNT ? heatmap_sample_cnt : DEF_OVERVIEW_HEATMAP_SAMPLE_CNT;
				$("#display_heatmap").html("Display Heatmap (up to " +  cnt + " samples)");
			}
		}
		$("#dt_import_dialog").dialog("open");
	});

	$("#dt_sample_annot").dialog({
		autoOpen: false,
		width: 800,
		height: 750,
		modal: false,
		buttons: {
			Apply: function() {
				var annots = navicell.annot_factory.annots_per_name;
				var annot_ids = [];
				var checkeds = [];
				for (var annot_name in annots) {
					var annot = navicell.annot_factory.getAnnotation(annot_name);
					var checkbox = $("#cb_annot_" + annot.id);
					var checked = checkbox.attr("checked");

					annot_ids.push(annot.id);
					annot.setIsGroup(checked);
				}
				navicell.group_factory.buildGroups();
				update_status_tables({style_only: true, annot_ids: annot_ids, no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true});
				$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'>" + mapSize(navicell.group_factory.group_map) + "</span> groups of samples: groups are listed in My Data / Groups tab</span>");

				group_editing(false);
			},

			Cancel: function() {
				var annots = navicell.annot_factory.annots_per_name;
				for (var annot_name in annots) {
					var annot = navicell.annot_factory.getAnnotation(annot_name);
					if (annot.isGroup()) {
						$("#cb_annot_" + annot.id).attr("checked", "checked");
					} else {
						$("#cb_annot_" + annot.id).removeAttr("checked");
					}
				}
				group_editing(false);
			},

			OK: function() {
				$(this).dialog("close");
			}
		}
	});


	$("#import_annot_status").button().click(function() {
		$("#dt_sample_annot").dialog("open");
	});

	$("#dt_status_tabs").tabs();

	$("#status_tabs").button().click(function() {
		if (navicell.dataset.datatableCount()) {
			$("#dt_status_tabs").dialog("open");
		}
	});

	$("#drawing_config").button().click(function() {
		if (true || navicell.dataset.datatableCount()) {
			$("#drawing_config_div").dialog("open");
		}
	});

	if (DATATABLE_HAS_TABS) {
		$("#dt_datatable_tabs").dialog({
			autoOpen: false,
			width: 850,
			height: 550,
			modal: false
		});

		$("#dt_datatable_tabs").tabs();

		$("#datatable_status").button().click(function() {
			if (navicell.dataset.datatableCount()) {
				$("#dt_datatable_tabs").dialog("open");
			}
		});
	}
	update_sample_annot_table(window.document);

	$("#heatmap_editor_div").dialog({
		autoOpen: false,
		width: 850,
		height: 580,
		modal: false,
		buttons: {
			"Apply": function() {
				nv_perform("nv_heatmap_editor_perform", window, "apply", true);

				/*
				var msg = get_heatmap_config_message(true);
				if (msg) {
					warning_dialog("Apply cannot be performed", msg, window);
					return;
				}
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				$("#drawing_config_chart_type").val('Heatmap');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				heatmap_editor_apply(drawing_config.getHeatmapConfig());
				heatmap_editor_apply(drawing_config.getEditingHeatmapConfig());
				drawing_config.getHeatmapConfig().shrink();
				drawing_config.getEditingHeatmapConfig().shrink();
				update_heatmap_editor(window.document);
				heatmap_editor_set_editing(false, undefined, document.map_name);
				$("#drawing_config_chart_display").attr("checked", true);
				$("#drawing_config_chart_type").val('Heatmap');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
				drawing_config.apply();
				*/
			},
			
			"Cancel": function() {
				nv_perform("nv_heatmap_editor_perform", window, "cancel");

				/*
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.getEditingHeatmapConfig().cloneFrom(drawing_config.getHeatmapConfig());
				update_heatmap_editor(window.document);
				heatmap_editor_set_editing(false, undefined, document.map_name);
				if (CANCEL_CLOSES) {
					$(this).dialog('close');
				}
				*/
			},

			"OK": function() {
				nv_perform("nv_heatmap_editor_perform", window, "close");

				//$(this).dialog('close');
			}
		}
	});

	$("#barplot_editor_div" ).dialog({
		autoOpen: false,
		width: 750,
		height: 600,
		modal: false,
		buttons: {
			"Apply": function() {
				var msg = get_barplot_config_message(true);
				if (msg) {
					warning_dialog("Apply cannot be performed", msg, window);
					return;
				}
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);

				$("#drawing_config_chart_type").val('Barplot');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				barplot_editor_apply(drawing_config.getBarplotConfig());

				barplot_editor_apply(drawing_config.getEditingBarplotConfig());
				drawing_config.getBarplotConfig().shrink();
				drawing_config.getEditingBarplotConfig().shrink();
				update_barplot_editor(window.document);
				barplot_editor_set_editing(false);

				$("#drawing_config_chart_display").attr("checked", true);
				$("#drawing_config_chart_type").val('Barplot');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				drawing_config.apply();
			},
			
			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.getEditingBarplotConfig().cloneFrom(drawing_config.getBarplotConfig());
				update_barplot_editor(window.document);
				barplot_editor_set_editing(false);
				if (CANCEL_CLOSES) {
					$(this).dialog('close');
				}
			},

			"OK": function() {
				$(this).dialog('close');
			}

		}
	});


	for (var num = 1; num <= GLYPH_COUNT; ++num) {
		$("#glyph_editor_div_" + num).data('num', num).dialog({
			autoOpen: false,
			width: 750,
			height: 718,
			modal: false,

			buttons: {
				"Apply": function() {
					var num = $(this).data('num');
					var msg = get_glyph_config_message(num, true);
					if (msg) {
						warning_dialog("Apply cannot be performed", msg, win);
						return;
					}
					var module = get_module();
					var drawing_config = navicell.getDrawingConfig(module);
					glyph_editor_apply(num, drawing_config.getGlyphConfig(num));
					glyph_editor_apply(num, drawing_config.getEditingGlyphConfig(num));
					update_glyph_editors(window.document);

					$("#drawing_config_glyph_display_" + num).attr("checked", true);
					drawing_config.setDisplayGlyphs(num, true);
					glyph_editor_set_editing(num, false);
					drawing_config.apply();
				},
				
				"Cancel": function() {
					var module = get_module();
					var drawing_config = navicell.getDrawingConfig(module);
					var num = $(this).data('num');
					drawing_config.getEditingGlyphConfig(num).cloneFrom(drawing_config.getGlyphConfig(num));
					update_glyph_editors(window.document);
					glyph_editor_set_editing(num, false);
					if (CANCEL_CLOSES) {
						$(this).dialog('close');
					}
				},
				"OK": function() {
					$(this).dialog('close');
				}

			}
		});
	}

	$("#map_staining_editor_div").dialog({
		autoOpen: false,
		width: 750,
		height: 660,
		modal: false,

		buttons: {
			"Apply": function() {
				var msg = get_map_staining_config_message(true);
				if (msg) {
					warning_dialog("Apply cannot be performed", msg, window);
					return;
				}
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				map_staining_editor_apply(drawing_config.getMapStainingConfig());
				map_staining_editor_apply(drawing_config.getEditingMapStainingConfig());
				update_map_staining_editor(window.document);
				$("#drawing_config_map_staining_display").attr("checked", true);
				drawing_config.setDisplayMapStaining(true);
				navicell.getMapTypes(module).setMapTypeByMapStaining(true);
				map_staining_editor_set_editing(false);
				drawing_config.apply(window);
			},

			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.getEditingMapStainingConfig().cloneFrom(drawing_config.getMapStainingConfig());
				update_map_staining_editor(window.document);
				map_statining_editor_set_editing(false);
				if (CANCEL_CLOSES) {
					$(this).dialog('close');
				}
			},
			"OK": function() {
				$(this).dialog('close');
			}
		}
	});

	$("#command-dialog").dialog({
		autoOpen: false,
		width: 1030,
		height: 770,
		modal: false,

		buttons: {
			"Execute": function() {
				var cmd = $("#command-exec").val().trim();
				//window.eval(cmd);
				nv_decoder(cmd);
				$("#command-exec").val("");
			},

			"Clear": function() {
				$("#command-exec").val("");
			},

			"OK": function() {
				$(this).dialog('close');
			}
		}
	});
});

function import_annot_file()
{
	var sample_file = $("#dt_sample_file");
	var status = $("#dt_sample_annot_status");
	var file_elem = sample_file.get()[0];
	if (!$(file_elem).val()) {
		status.html("<span class=\"error-message\">No file given</span>");
	} else {
		navicell.annot_factory.readfile(file_elem.files[0], function(file) {status.html("<span class=\"error-message\">Cannot read file " + $(file_elem).val() + "</span>");});
		navicell.annot_factory.ready.then(function() {
			if (navicell.annot_factory.sample_read > 0) {
				status.html("<span class=\"status-message\">" + navicell.annot_factory.sample_annotated + " samples annotated</span>");
			} else {
				status.html("<span class=\"error-message\">Missing samples: " + navicell.annot_factory.missing + "<br>No samples annotated, may be something wrong in the file.</span>");
			}
			sample_file.val("");
			if (navicell.annot_factory.sample_annotated) {
				update_status_tables({no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true});
			}
		});
	}
}

var DEF_OVERVIEW_HEATMAP_SAMPLE_CNT = 5;
var DEF_OVERVIEW_BARPLOT_SAMPLE_CNT = 5;
var DEF_MAX_HEATMAP_SAMPLE_CNT = 25;
var DEF_MAX_BARPLOT_SAMPLE_CNT = 25;

var max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;
var max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

function add_transparency_slider_labels(obj)
{
	obj.slider().
		append("<label class='slider-label' style='left: 0%'>Solid</label>").
		append("<label class='slider-label' style='right: 0%'>Transparent</label>");
}

function heatmap_editor_apply(heatmap_config)
{
	heatmap_config.reset(); // ??
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;
	if (sample_group_cnt > max_heatmap_sample_cnt) {
		sample_group_cnt = max_heatmap_sample_cnt;
	}
	var doc = window.document;
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		var val = $("#heatmap_editor_gs_" + idx, doc).val();
		if (val == "_none_") {
			heatmap_config.setSampleOrGroupAt(idx, undefined);
		} else {
			var prefix = val.substr(0, 2);
			var id = val.substr(2);
			if (prefix == 'g_') {
				var group = navicell.group_factory.getGroupById(id);
				heatmap_config.setSampleOrGroupAt(idx, group);
			} else {
				var sample = navicell.dataset.getSampleById(id);
				heatmap_config.setSampleOrGroupAt(idx, sample);
			}
		}
	}
	var datatable_cnt = mapSize(navicell.dataset.datatables);
	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var val = $("#heatmap_editor_datatable_" + idx, doc).val();
		if (val == "_none_") {
			heatmap_config.setDatatableAt(idx, undefined);
		} else {
			var datatable = navicell.getDatatableById(val);
			heatmap_config.setDatatableAt(idx, datatable);
		}
	}
	heatmap_config.setSize($("#heatmap_editor_size", doc).val());
	heatmap_config.setScaleSize($("#heatmap_editor_scale_size", doc).val());
	heatmap_config.setTransparency(heatmap_config.getSlider().slider("value"));
}

function barplot_editor_apply(barplot_config)
{
	barplot_config.reset(); // ??
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;
	if (sample_group_cnt > max_barplot_sample_cnt) {
		sample_group_cnt = max_barplot_sample_cnt;
	}
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		var val = $("#barplot_editor_gs_" + idx).val();
		if (!val) {
			return; // means not ready
		}
		if (val == "_none_") {
			barplot_config.setSampleOrGroupAt(idx, undefined);
		} else {
			var prefix = val.substr(0, 2);
			var id = val.substr(2);
			if (prefix == 'g_') {
				var group = navicell.group_factory.getGroupById(id);
				barplot_config.setSampleOrGroupAt(idx, group);
			} else {
				var sample = navicell.dataset.getSampleById(id);
				barplot_config.setSampleOrGroupAt(idx, sample);
			}
		}
	}
	var datatable_cnt = mapSize(navicell.dataset.datatables);
	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var val = $("#barplot_editor_datatable_" + idx).val();
		if (val == "_none_") {
			barplot_config.setDatatableAt(idx, undefined);
		} else {
			var datatable = navicell.getDatatableById(val);
			barplot_config.setDatatableAt(idx, datatable);
		}
	}
	barplot_config.setHeight($("#barplot_editor_height").val());
	barplot_config.setWidth($("#barplot_editor_width").val());
	barplot_config.setScaleSize($("#barplot_editor_scale_size").val());
	barplot_config.setTransparency(barplot_config.getSlider().slider("value"));
}

function glyph_editor_apply(num, glyph_config)
{
	var val = $("#glyph_editor_gs_" + num).val();
	if (val == "_none_") {
		glyph_config.setSampleOrGroup(undefined);
	} else {
		var prefix = val.substr(0, 2);
		var id = val.substr(2);
		if (prefix == 'g_') {
			var group = navicell.group_factory.getGroupById(id);
			glyph_config.setSampleOrGroup(group);
		} else {
			var sample = navicell.dataset.getSampleById(id);
			glyph_config.setSampleOrGroup(sample);
		}
	}

	val = $("#glyph_editor_datatable_shape_" + num).val();
	if (val == "_none_") {
		glyph_config.setShapeDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setShapeDatatable(datatable);
	}

	val = $("#glyph_editor_datatable_color_" + num).val();
	if (val == "_none_") {
		glyph_config.setColorDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setColorDatatable(datatable);
	}

	val = $("#glyph_editor_datatable_size_" + num).val();
	if (val == "_none_") {
		glyph_config.setSizeDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setSizeDatatable(datatable);
	} 

	glyph_config.setSize($("#glyph_editor_size_" + num).val());
	glyph_config.setScaleSize($("#glyph_editor_scale_size_" + num).val());
	glyph_config.setTransparency(glyph_config.getSlider().slider("value"));
}

function download_samples() {
	var str = ",#Genes for sample in datatable\n";
	str += "Samples (" + mapSize(navicell.dataset.samples) + ")";

	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		str += "," + datatable.html_name;
	}
	str += "\n";
	for (var sample_name in navicell.dataset.samples) {
		if (sample_name == "") {
			continue;
		}
		str +=  sample_name;
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			var cnt = datatable.getGeneCount(sample_name);
			if (cnt >= 0) {
				str += "," + cnt;
			} else {
				str += "," + "-";
			}
		}
		str += "\n";
	}

	download_csv($("#download_samples"), str, "samples");
}

function update_sample_status_table(doc, params) {
	var table = $("#dt_sample_status_table", doc);
	table.children().remove();
	// should use a string buffer
	var str = "<thead>";
	str += "<tr><td>&nbsp</td><td colspan='" + mapSize(navicell.dataset.datatables) + "'>&nbsp;#Genes&nbsp;for&nbsp;sample&nbsp;in&nbsp;datatable&nbsp;</td</tr>";
	str += "<tr><th>Samples&nbsp;(" + mapSize(navicell.dataset.samples) + ")</th>";

	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		str += "<th>&nbsp;" + datatable.html_name + "&nbsp;</th>";
	}
	str += "</tr></thead>";
	str += "<tbody>\n";
	for (var sample_name in navicell.dataset.samples) {
		if (sample_name == "") {
			continue;
		}
		str += "<tr><td>" + sample_name + "</td>";
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			var cnt = datatable.getGeneCount(sample_name);
			if (cnt >= 0) {
				str += "<td style=\"width: 100%; text-align: center\">" + cnt + "</td>";
			} else {
				str += "<td style=\"width: 100%; text-align: center\">-</td>";
			}
		}
		str += "</tr>";
	}
	
	str += "</tbody>";
	table.append(str);
	table.tablesorter();
}

function annot_set_group_old(annot_id, doc) {
	var checkbox = $("#cb_annot_" + annot_id);
	var checked = checkbox.attr("checked");
	var annot = navicell.annot_factory.getAnnotationPerId(annot_id);

	annot.setIsGroup(checked);
}

function is_annot_group(annot_id) {
	var annot = navicell.annot_factory.getAnnotationPerId(annot_id);
	return annot.is_group ? "checked" : "";
}

function update_sample_annot_table_style(doc, params) {
	var annot_ids = params.annot_ids;
	var checked = params.checked;

	for (var idx in annot_ids) {
		var annot_id = annot_ids[idx];
		var checkbox = $("#cb_annot_" + annot_id);
		var checked = checkbox.attr("checked");
		var td_tochange = $("#dt_sample_annot_table .annot_" + annot_id, doc);
		if (checked) {
			td_tochange.removeClass('non_group_annot');
			td_tochange.addClass('group_annot');
		} else {
			td_tochange.addClass('non_group_annot');
			td_tochange.removeClass('group_annot');
		}
	}
}

function update_sample_annot_table(doc, params) {
	if (params && params.style_only) {
		update_sample_annot_table_style(doc, params);
		return;
	}
	var annots = navicell.annot_factory.annots_per_name;
	var annot_cnt = mapSize(annots);
	var table = $("#dt_sample_annot_table", doc);
	table.children().remove();
	var annotated_sample_cnt = 0;
	for (var sample_name in navicell.dataset.samples) {
		var sample = navicell.dataset.samples[sample_name];
		if (sample.hasAnnots()) {
			annotated_sample_cnt++;
		}
	}
	if (annot_cnt != 0 && annotated_sample_cnt != 0) {
		var str = "<thead>";
		if (annot_cnt) {
			str += "<tr><td>&nbsp;</td><td colspan='" + annot_cnt + "' style='text-align: left; font-style: italic; font-weight: bold; font-size: smaller;'>&nbsp;&nbsp;&nbsp;&nbsp;Check boxes to build groups</td></tr>\n";
		}
		str += '<tr><td style="text-align: right; font-size: smaller; font-style: italic;">' + '&nbsp;' + '</td>';
		for (var annot_name in annots) {
			var annot = navicell.annot_factory.getAnnotation(annot_name);
			var annot_id = annot.id;
			str += "<td style='text-align: center;'><input id='cb_annot_" + annot_id + "' type='checkbox' " + is_annot_group(annot_id) + " onchange='group_editing(true)'></input></td>";
		}
		str += "</tr>";
		str += "<tr><th>Samples (" + annotated_sample_cnt + ")</th>";
		if (0 == annot_cnt) {
		} else {
			for (var annot_name in annots) {
				str += "<th>&nbsp;" + annot_name.replace(/ /g, "&nbsp;") + "&nbsp;</th>";
			}
		}
		str += "</tr></thead>";
		str += "<tbody>";
		if (0 == mapSize(navicell.dataset.samples)) {
			str += "<tr><td>&nbsp;</td><tr>";
		}
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			if (!sample.hasAnnots()) {
				continue;
			}
			str += "<tr>";
			str += "<td>" + sample_name + "</td>";
			for (var annot_name in annots) {
				var annot = navicell.annot_factory.getAnnotation(annot_name);
				var annot_value = sample.annots[annot_name];
				str += "<td class='" + (annot.is_group ? "group_annot" : " non_group_annot") + " annot_" + annot.id + "'>";
				if (annot_value) {
					str += annot_value;
				} else {
					str += "&nbsp;";
				}
				str += "</td>";
			}
			str += "</tr>";
		}
		str += "</tbody>";
		table.append(str);
		table.tablesorter();
		$("#dt_sample_annot").dialog("option", "width", 800);
		$("#dt_sample_annot").dialog("option", "height", 750);
		$("#dt_sample_annot_table_div", doc).css("display", "block");
	} else {
		$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'></span>No groups: import an annotation file to build groups</span>");
		$("#dt_sample_annot").dialog("option", "width", 600);
		$("#dt_sample_annot").dialog("option", "height", 350);
		$("#dt_sample_annot_table_div", doc).css("display", "none");
	}
}

function download_groups() {
	var str = "Groups (" + mapSize(navicell.group_factory.group_map) + ")";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		str += "," + datatable.html_name + ",";
	}
	str += "\n";
	for (var datatable_name in navicell.dataset.datatables) {
		str += ",#Samples,Samples";
	}
	str += "\n";
	var sample_names_per_datatable = {};
	var sample_cnt_per_datatable = {};
	var maxcnt_per_groups = {}
	var datatable_cnt = 0;
	for (var datatable_name in navicell.dataset.datatables) {
		datatable_cnt++;
	}

	for (var group_name in navicell.group_factory.group_map) {
		var group = navicell.group_factory.group_map[group_name];
		sample_names_per_datatable[group_name] = [];
		sample_cnt_per_datatable[group_name] = [];
		var maxcnt = 0;
		for (var datatable_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[datatable_name];
			var cnt = 0;
			var sample_names = [];
			for (var sample_name in navicell.dataset.samples) {
				var sample = navicell.dataset.samples[sample_name];
				if (datatable.sample_index[sample_name] != undefined) {
					for (var sample_group_name in sample.groups) {
						if (sample_group_name == group_name) {
							sample_names.push(sample_name);
							cnt++;
						}
					}
				}
			}
			sample_names.sort();
			sample_names_per_datatable[group_name].push(sample_names);
			sample_cnt_per_datatable[group_name].push(cnt);
			if (cnt > maxcnt) {
				maxcnt = cnt;
			}
		}
		maxcnt_per_groups[group_name] = maxcnt;
	}

	for (var group_name in navicell.group_factory.group_map) {
		var group = navicell.group_factory.group_map[group_name];
		var maxcnt = maxcnt_per_groups[group_name];
		var group_cnt = sample_cnt_per_datatable[group_name];
		var group_sample_names = sample_names_per_datatable[group_name];
		for (var nn = 0; nn < maxcnt; nn++) {
			str += group.name;
			for (var jj = 0; jj < datatable_cnt; jj++) {
				var cnt = group_cnt[jj];
				var sample_names = group_sample_names[jj];
				str += "," + cnt;
				if (nn >= cnt) {
					str += ",-";
				} else {
					str += "," + sample_names[nn];
				}
			}
			str += "\n";
		}
	}
	download_csv($("#download_groups"), str, "groups");
}

function update_group_status_table(doc, params) {
	var table = $("#dt_group_status_table", doc);
	table.children().remove();
	var str = "<thead><tr><th>Groups&nbsp;(" + mapSize(navicell.group_factory.group_map) + ")</th>";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		str += "<td colspan='2'>&nbsp;" + datatable.html_name + "&nbsp;</td>";
	}
	str += "<tr><td>&nbsp;</td>";
	for (var datatable_name in navicell.dataset.datatables) {
		str += "<th>&nbsp;#Samples&nbsp;</th><th>Samples</th>";
	}
	str += "</tr>";
	str += "</thead>";
	str += "<tbody>";
	for (var group_name in navicell.group_factory.group_map) {
		var group = navicell.group_factory.group_map[group_name];
		str += "<tr>";
		str += "<td>" + group.html_name + "</td>";
		for (var datatable_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[datatable_name];
			str += "<td style='text-align: center'>";
			var cnt = 0;
			var sample_names = [];
			for (var sample_name in navicell.dataset.samples) {
				var sample = navicell.dataset.samples[sample_name];
				if (datatable.sample_index[sample_name] != undefined) {
					for (var sample_group_name in sample.groups) {
						if (sample_group_name == group_name) {
							sample_names.push(sample_name);
							cnt++;
						}
					}
				}
			}
			str += cnt + "</td>";

			sample_names.sort();
			str += "<td><div style='max-height: 200px; overflow: auto'>";
			for (var sample_idx in sample_names) {
				str += sample_names[sample_idx] + "</br>";
				
			}
			str += "</div></td>";
		}
		str += "</tr>";
	}
	str += "</tbody>";
	table.append(str);
	table.tablesorter();
}

function update_module_status_table(doc, params) {
	var table = $("#dt_module_status_table", doc);
	table.children().remove();
	var str = "<thead>";
	str += "<tr><td>&nbsp</td><td colspan='" + mapSize(navicell.dataset.datatables) + "'>&nbsp;#Genes&nbsp;in&nbsp;</td</tr>";
	str += "<tr><th>Module</th>";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		str += "<th>&nbsp;" + datatable.html_name + "&nbsp;</th>";
	}
	str += "</thead>";

	str += "<tbody>";
	for (var module_name in navicell.mapdata.module_mapdata) {
		str += "<tr><td>" + module_name + "</td>";
		for (var datatable_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[datatable_name];
			var gene_cnt = 0;
			for (var gene_name in datatable.gene_index) {
				var hugo_entry = navicell.mapdata.hugo_map[gene_name];
				if (hugo_entry && hugo_entry[module_name]) {
					gene_cnt++;
				}
			}
			str += "<td>" + gene_cnt + "</td>";
		}
		str += "</tr>";
	}
	str += "</tbody>";

	table.append(str);
	table.tablesorter();
}

function in_module_gene_count(module_name) {
	var cnt = 0;
	for (var gene_name in navicell.dataset.genes) {
		if (navicell.mapdata.hugo_map[gene_name][module_name]) {
			cnt++;
		}
	}
	return cnt;
}

function download_genes() {
	var opener = document.win;
	var module_stack = [];
	while (opener && opener.document.map_name) {
		module_stack.push(opener.document.map_name);
		opener = opener.opener;
	}

	var str = "Genes in";
	for (var ll = 0; ll < module_stack.length; ll++) {
		str += ",";
	}

	var size = mapSize(navicell.dataset.datatables);
	str += "#Samples for gene in datatable";
	for (var ll = 0; ll < size-1; ll++) {
		str += ",";
	}
	str += "\n";
	for (var nn = 0; nn < module_stack.length; ++nn) {
		var module_name = module_stack[nn];
		str += (nn>0?",":"") + module_name + " (" + in_module_gene_count(module_stack[nn]) + ")";
	}

	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		str += "," + datatable.html_name;
	}
	str += "\n";
	for (var gene_name in navicell.dataset.genes) {
		if (gene_name == "") {
			continue;
		}
		str += gene_name;
		for (var nn = module_stack.length-2; nn >= 0; --nn) {
			var module_name = module_stack[nn];
			if (navicell.mapdata.hugo_map[gene_name][module_name]) {
				str += "," + gene_name;
			} else {
				str += ",";
			}
		}
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			var cnt = datatable.getSampleCount(gene_name);
			if (cnt >= 0) {
				str += "," + cnt;
			} else {
				str += ",-";
			}
		}
		str += "\n";
	}	
	download_csv($("#download_genes"), str, "genes");
}

function update_gene_status_table(doc, params) {
	var table = $("#dt_gene_status_table", doc);
	table.children().remove();
	var str = "<thead>";
	var opener = doc.win;
	var module_stack = [];
	while (opener && opener.document.map_name) {
		module_stack.push(opener.document.map_name);
		opener = opener.opener;
	}
	str += "<tr><td colspan='" + (module_stack.length) + "'>Genes&nbsp;in</td><td colspan='" + mapSize(navicell.dataset.datatables) + "'>&nbsp;#Samples&nbsp;for&nbsp;gene&nbsp;in&nbsp;datatable&nbsp;</td</tr>";
	str += "<tr>";
	for (var nn = module_stack.length-1; nn >= 0; --nn) {
		var module_name = module_stack[nn];
		str += "<th>" + module_name + "&nbsp;(" + in_module_gene_count(module_stack[nn]) + ")</th>";
	}

	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		str += "<th>&nbsp;" + datatable.html_name + "&nbsp;</th>";
	}
	str += "</tr></thead>";
	str += "<tbody>\n";
	for (var gene_name in navicell.dataset.genes) {
		if (gene_name == "") {
			continue;
		}
		str += "<tr><td>" + gene_name + "</td>";
		for (var nn = module_stack.length-2; nn >= 0; --nn) {
			var module_name = module_stack[nn];
			if (navicell.mapdata.hugo_map[gene_name][module_name]) {
				str += "<td>" + gene_name + "</td>";
			} else {
				str += "<td>&nbsp;</td>";
			}
		}
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			var cnt = datatable.getSampleCount(gene_name);
			if (cnt >= 0) {
				str += "<td style=\"width: 100%; text-align: center\">" + cnt + "</td>";
			} else {
				str += "<td style=\"width: 100%; text-align: center\">-</td>";
			}
		}
		str += "</tr>";
	}	
	str += "</tbody>";
	table.append(str);
	table.tablesorter();
}

function download_csv(obj, csv, name) {
	obj.attr("download", name + ".csv");
	obj.attr("href", 'data:text/csv;charset=utf-8,' + escape(csv));
}

function download_datatable_data(id) {
	var datatable = navicell.getDatatableById(id);
	var csv = datatable.makeDataTable_genes_csv(get_module());
	download_csv($("#dt_download_data_" + datatable.getId()), csv, datatable.name);
	/*
	var obj = $("#dt_download_data_" + datatable.getId());
	obj.attr("download", datatable.name + ".csv");
	obj.attr("href", 'data:text/csv;charset=utf-8,' + escape(csv));
	*/

        //window.open('data:text/csv;charset=utf-8,' + escape(csv));
        //window.open('data:text/plain;charset=utf-8,' + escape(csv));
	//window.open('data:application/x-excel;charset=utf-8,' + escape(csv));
}

function show_datatable_data(id) {
	var datatable = navicell.getDatatableById(id);
	var div = datatable.getDataMatrixDiv(get_module());
	if (datatable.showingDataIsHuge()) {
		var body = $(body, document);
		var dt_show_data = $("#dt_show_data_" + id, document);
		var ocursor = body.css("cursor");
		var ocursor2 = dt_show_data.css("cursor");
		body.css("cursor", "wait");
		dt_show_data.css("cursor", "wait");
		setTimeout(function() {
			datatable.refresh(window);
			div.dialog("open");
			body.css("cursor", ocursor);
			dt_show_data.css("cursor", ocursor2);
		}, DISPLAY_TIMEOUT);
	} else {
		datatable.refresh(window);
		div.dialog("open");
	}
}

function show_datatable_markers(id) {
	var datatable = navicell.getDatatableById(id);
	if (datatable.showingMarkersIsHuge()) {
		var body = $(body, document);
		var dt_show_markers = $("#dt_show_markers_" + id, document);
		var ocursor = body.css("cursor");
		var ocursor2 = dt_show_markers.css("cursor");
		body.css("cursor", "wait");
		dt_show_markers.css("cursor", "wait");
		setTimeout(function() {
			datatable.display(window.document.navicell_module_name, window, false, true);
			body.css("cursor", ocursor);
			dt_show_markers.css("cursor", ocursor2);
		}, DISPLAY_TIMEOUT);
	} else {
		datatable.display(window.document.navicell_module_name, window, false, true);
	}
}

function update_datatable_status_table(doc, params) {

	var table = $("#dt_datatable_status_table", doc);
	var update_label = $("#dt_datatable_status_update_label", doc);
	var update_checkbox = $("#dt_datatable_status_update", doc);
	if (doc != window.document) {
		update_checkbox.attr("checked", false);
	}
	var update = true;
	var support_remove = true;

	table.children().remove();

	var tab_body = $("#dt_datatable_tabs", doc);
	var tab_header = $("#dt_datatable_tabs ul", doc);
	tab_header.children().remove();
	tab_header.append('<li><a class="ui-button-text" href="#dt_datatable_status">General</a></li>');

	var str = "<thead><tr>";
	if (support_remove && update) {
		str += "<th>Remove</th>";
	}
	str += "<th>Datatable</th>";
	str += "<th>Type</th>";
	str += "<th>Genes</th>";
	str += "<th>Samples</th>";
	str += "<th>Display</th>";
	str += "</tr></thead>";

	str += "<tbody>";
	var tabnum = 1;
	var onchange = 'datatable_management_set_editing(true)';
	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		$("#dt_data_dialog_title_" + datatable.id).html("<span style='font-style: italic;'>" + datatable.name + "</span> Datatable");
		var data_div = datatable.data_div;
		if (DATATABLE_HAS_TABS) {
			if (data_div) {
				tab_header.append('<li><a class="ui-button-text" href="#' + data_div.attr("id") + '">' + dt_name + '</a></li>');
				datatable.setTabNum(tabnum++);
			}
		}
		str += "<tr>";
		if (update) {
			if (support_remove) {
				str += "<td><input id=\"dt_remove_" + datatable.getId() + "\" type=\"checkbox\"></td>";
			}
			str += "<td><input id=\"dt_name_" + datatable.getId() + "\" type=\"text\" value=\"" + datatable.name + "\" onkeydown='" + onchange + "' onchange='" + onchange + "'/></td>";
			str += "<td>" + get_biotype_select("dt_type_" + datatable.getId(), false, datatable.biotype.name, onchange) + "</td>";
		} else {
			str += "<td>&nbsp;" + datatable.name + "&nbsp;</td>";
			str += "<td style='min-width: 170px'>&nbsp;" + datatable.biotype.name + "&nbsp;</td>";
		}
		str += "<td>" + mapSize(datatable.gene_index) + "</td>";
		str += "<td>" + datatable.getSampleCount() + "</td>";
		str += "<td style='border: none; text-decoration: underline; font-size: 11px'><a id='dt_show_markers_" + datatable.getId() + "' href='#' onclick='show_datatable_markers(" + datatable.getId() + ")'>gene&nbsp;markers</a><br/>";
		str += "<a id='dt_download_data_" + datatable.getId() + "' href='#' onclick='download_datatable_data(" + datatable.getId() + ")'>download&nbsp;data</a></td>";
		str += "</tr>";
	}
	table.append(str);
	table.tablesorter();

	if (DATATABLE_HAS_TABS) {
		tab_body.tabs("refresh");
	}
	navicell.DTStatusMustUpdate = false;
}

function update_status_tables(params) {
	for (var map_name in maps) {
		var doc = maps[map_name].document;
		if (params && params.doc && params.doc != doc) {
			continue;
		}
		var win = doc.win;
		if (!params || !params.no_sample_status_table) {
			win.update_sample_status_table(doc, params);
		}
		if (!params || !params.no_gene_status_table) {
			win.update_gene_status_table(doc, params);
		}
		if (!params || !params.no_group_status_table) {
			win.update_group_status_table(doc, params);
		}
		if (!params || !params.no_module_status_table) {
			win.update_module_status_table(doc, params);
		}
		if (!params || !params.no_datatable_status_table) {
			win.update_datatable_status_table(doc, params);
		}
		if (!params || !params.no_sample_annot_table) {
			win.update_sample_annot_table(doc, params);
		}

		win.update_heatmap_editor(doc, params);
		win.update_barplot_editor(doc, params);
		win.update_glyph_editors(doc, params);
		win.update_map_staining_editor(doc, params);
	}
	//navicell_session.write();
}

function update_glyph_editors(doc, params) {
	for (var num = 1; num <= GLYPH_COUNT; ++num) {
		doc.win.update_glyph_editor(doc, params, num);
	}
}

function get_biotype_select(id, include_none, value, onchange) {
	var str = '<select id="' + id + '"';
	if (onchange) {
		str += " onchange='" + onchange + "'";
	}
	str += '">';
	if (include_none) {
		str += '<option value="_none_"></option>';
	}
	for (var biotype_name in navicell.biotype_factory.biotypes) {
		var biotype = navicell.biotype_factory.biotypes[biotype_name];
		var selected = biotype.name == value ? ' selected' : '';
		str += '<option value="' + biotype.name + '"' + selected + '>' + biotype.name + '</option>';
	}
	var selected = DATATABLE_LIST == value ? ' selected' : '';
	str += '<option value="' + DATATABLE_LIST + '"' + selected + '>' + DATATABLE_LIST + '</option>';
	str += "</select>";
	return str;
}

function update_datatables() {
	var update_cnt = 0;
	var remove_cnt = 0;
	var doc = window.document;
	for (var dt_name in navicell.dataset.datatables) {
		// TBD: do not support space in names: check all selector
		var datatable = navicell.dataset.datatables[dt_name];
		var dt_id = datatable.getId();
		var dt_name_elem = $("#dt_name_" + dt_id, doc);
		var dt_type_elem = $("#dt_type_" + dt_id, doc);
		var dt_remove_elem = $("#dt_remove_" + dt_id, doc);
		var new_dt_name = dt_name_elem.val();
		var new_dt_type = dt_type_elem.val();
		var new_dt_remove = dt_remove_elem.attr('checked');
		if (new_dt_remove) {
			navicell.dataset.removeDatatable(datatable);
			remove_cnt++;
		} else {
			if ((new_dt_name && dt_name !== new_dt_name) ||
			    new_dt_type !==  datatable.biotype.name) {
				if (!navicell.dataset.updateDatatable(window, dt_name, new_dt_name, new_dt_type)) {
					dt_name_elem.val(dt_name);
					dt_type_elem.val(datatable.biotype.name);
					
				} else {
					update_cnt++;
				}
				
			}
		}

	}
	if (remove_cnt) {
		var drawing_config = navicell.getDrawingConfig(get_module());
		drawing_config.getHeatmapConfig().syncDatatables();
		drawing_config.getEditingHeatmapConfig().syncDatatables();
		drawing_config.getBarplotConfig().syncDatatables();
		drawing_config.getEditingBarplotConfig().syncDatatables();
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			drawing_config.getGlyphConfig(num).syncDatatables();
			drawing_config.getEditingGlyphConfig(num).syncDatatables();
		}
	}
	if (update_cnt || remove_cnt) {
		update_datatable_status_table(doc, {force: true});
		update_status_tables();
	}
	if (remove_cnt) {
		clickmap_refresh(true);
	}
	datatable_management_set_editing(false);
}

function cancel_datatables() {
	update_datatable_status_table(window.document, {force: true});
	datatable_management_set_editing(false);
	$("#dt_datatable_tabs").dialog("close");
}

Datatable.prototype.showDisplayConfig = function(doc, what) {
	var div;
	var module = get_module_from_doc(doc);
	var displayConfig = this.getDisplayConfig(module);
	if (displayConfig) {
		div = displayConfig.getDiv(what);
	}
	if (div) {
		var datatable_id = this.getId();
		var width;
		if (what == COLOR_SIZE_CONFIG) {
			width = this.biotype.isUnorderedDiscrete() ? 760 : 500;
		} else if (what == 'color') {
			width = this.biotype.isUnorderedDiscrete() ? 700 : 440;
		} else if (what == 'shape') {
			width = 400;
		} else {
			width = 400;
		}
		div.dialog({
			autoOpen: false,
			width: width,
			height: 670,
			modal: false,

			buttons: {
				"Apply": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayContinuousConfig = datatable.displayContinuousConfig[module];
					var displayUnorderedDiscreteConfig = datatable.displayUnorderedDiscreteConfig[module];
					var active = div.tabs("option", "active");
					var tabname = DisplayContinuousConfig.tabnames[active];
					if (displayContinuousConfig) {
						var prev_value = datatable.minval;
						var error = 0;
						var step_cnt = displayContinuousConfig.getStepCount(what, tabname);
						if (displayContinuousConfig.has_empty_values) {
							step_cnt++;
						}
						var use_gradient = displayContinuousConfig.use_gradient[what];
						for (var idx = 0; idx < step_cnt; ++idx) {
							var value;
							if (idx == step_cnt-1 && !use_gradient) {
								value = datatable.maxval;
							} else {
								value = $("#step_value_" + what + '_' + datatable_id + "_" + idx, doc).val();
							}
							value *= 1.;
							if (value <= prev_value) {
								error = 1;
								break;
							}
							prev_value = value;
						}
						
						if (!error) {
							for (var idx = 0; idx < step_cnt; ++idx) {
								var value;
								if (idx == step_cnt-1 && !use_gradient) {
									value = datatable.maxval;
								} else {
									var id = "#step_value_" + tabname + '_' + what + '_' + datatable_id + "_" + idx;
									value = $(id, doc).val();
									if (!value) {
										value = $(id, doc).text();
									}
								}
								var color = $("#step_config_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								var size = $("#step_size_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								var shape = $("#step_shape_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								displayContinuousConfig.setStepInfo(what, tabname, idx, value, color, size, shape);
							}
							DisplayContinuousConfig.setEditing(datatable_id, false, what, doc.win);
						}
					}
					if (displayUnorderedDiscreteConfig) {
						var value_cnt = displayUnorderedDiscreteConfig.getValueCount();
						if (tabname == 'group') {
							value_cnt++;
						}
						var advanced = displayUnorderedDiscreteConfig.advanced;
						for (var idx = 0; idx < value_cnt; ++idx) {
							var cond = $("#discrete_cond_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var color = $("#discrete_color_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var size = $("#discrete_size_"  + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var shape = $("#discrete_shape_"  + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							if (!advanced && idx > 0) {idx = value_cnt-1;}
							displayUnorderedDiscreteConfig.setValueInfo(what, tabname, idx, color, size, shape, cond);
						}
						DisplayUnorderedDiscreteConfig.setEditing(datatable_id, false, what, doc.win);
					}
					doc.win.clickmap_refresh(true);
					update_status_tables({no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true, no_group_status_table: true, no_sample_annot_table: true, doc: doc});
				},

				"Cancel": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayContinuousConfig = datatable.displayContinuousConfig[module];
					var displayUnorderedDiscreteConfig = datatable.displayUnorderedDiscreteConfig[module];
					var active = div.tabs("option", "active");
					var tabname = DisplayContinuousConfig.tabnames[active];
					if (displayContinuousConfig) {
						displayContinuousConfig.update();
						DisplayContinuousConfig.setEditing(datatable_id, false, what, doc.win);
					}
					if (displayUnorderedDiscreteConfig) {
						displayUnorderedDiscreteConfig.update();
						DisplayUnorderedDiscreteConfig.setEditing(datatable_id, false, what, doc.win);
					}
					if (CANCEL_CLOSES) {
						$(this).dialog('close');
					}
				},
				"OK": function() {
					$(this).dialog('close');
				}
			}
		});

		div.dialog("open");
	}
}

function datatable_management_set_editing(val) {
	$("#dt_datatable_status_editing").html(val ? 'changes not saved...' : "");
}


// TBD: should be a method DisplayUnorderedDiscreteConfig
function display_discrete_config_set_editing(datatable_id, val, what, tabname) {
	$("#discrete_config_editing_" + tabname + '_' + what + '_' + datatable_id).html(val ? EDITING_CONFIGURATION : "");
}

function drawing_config_chart() {
	var doc = window.document;
	var val = $("#drawing_config_chart_type", doc).val();
	if (val == "Heatmap") {
		$("#heatmap_editor_div", doc).dialog("open");
	} else if (val == "Barplot") {
		$("#barplot_editor_div", doc).dialog("open");
	}
}

function drawing_config_glyph(num) {
	var doc = window.document;
	$("#glyph_editor_div_" + num, doc).dialog("open");
}

function drawing_config_map_staining() {
	var doc = window.document;
	$("#map_staining_editor_div", doc).dialog("open");
}

function drawing_editing(val) {
	$("#drawing_editing").html(val ? EDITING_CONFIGURATION : "");
}

function group_editing(val) {
	$("#group_editing").html(val ? EDITING_CONFIGURATION : "");
}

function heatmap_editor_set_editing(val, idx, map_name) {
	var doc = (map_name && maps ? maps[map_name].document : null);
	$("#heatmap_editing", doc).html(val ? EDITING_CONFIGURATION : "");
	if (val) {
		var module = get_module_from_doc(doc);
		var drawing_config = navicell.getDrawingConfig(module);
		heatmap_editor_apply(drawing_config.getEditingHeatmapConfig());
		doc.win.update_heatmap_editor(doc, null, drawing_config.getEditingHeatmapConfig());
	}
	if (idx != undefined) {
		if ($("#heatmap_editor_datatable_" + idx, doc).val() != '_none_') {
			$("#heatmap_editor_datatable_config_" + idx, doc).removeClass("zz-hidden");	
		} else {
			$("#heatmap_editor_datatable_config_" + idx, doc).addClass("zz-hidden");	
		}
	}
	var msg = get_heatmap_config_message(false);
	$("#heatmap_editor_msg_div").html(msg);
}

// 2013-09-03 TBD: must add a doc argument: but as this function is called
// from a string evaluation (onchange='step_display_config(...)'), I propose
// to give the doc_idx and get the doc value from an associative array.
// => should maintain an associative array: doc_idx -> doc
// + attribute doc.doc_idx
function heatmap_step_display_config(idx, map_name) {
	var doc = (map_name && maps ? maps[map_name].document : null);
	var val = $("#heatmap_editor_datatable_" + idx, doc).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			datatable.showDisplayConfig(doc, 'color');
		}
	}
}

function heatmap_sample_action(action, cnt, win) {
	if (!win) {
		win = window;
	}
	var doc = win.document;
	var module = get_module();
	var drawing_config = navicell.getDrawingConfig(module);
	if (action == "clear_samples") {
		drawing_config.getEditingHeatmapConfig().reset(true);
	} else if (action == "all_samples") {
		drawing_config.getEditingHeatmapConfig().reset(true);
		max_heatmap_sample_cnt = drawing_config.getEditingHeatmapConfig().setAllSamples();
	} else if (action == "all_groups") {
		drawing_config.getEditingHeatmapConfig().reset(true);
		max_heatmap_sample_cnt = drawing_config.getEditingHeatmapConfig().setAllGroups();
	} else if (action == "from_barplot") {
		var barplot_config = drawing_config.getBarplotConfig();
		if (barplot_config.getSampleOrGroupCount()) {
			drawing_config.getEditingHeatmapConfig().reset(true);
			max_heatmap_sample_cnt = drawing_config.getEditingHeatmapConfig().setSamplesOrGroups(barplot_config.getSamplesOrGroups());
		}
	} else if (action == "pass") {
		// nop
	}

	if (cnt) {
		max_heatmap_sample_cnt = cnt;
	}
	max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;
	$("#heatmap_editing", doc).html(EDITING_CONFIGURATION);
	update_heatmap_editor(doc);
	var msg = get_heatmap_config_message(false);
	$("#heatmap_editor_msg_div", doc).html(msg);
}

function get_group_names() {
	var group_names = [];
	for (var group_name in navicell.group_factory.group_map) {
		group_names.push(group_name);
	}
	if (group_names.length) {
		group_names.sort();
	}
	return group_names;
}

function get_sample_names() {
	var sample_names = [];
	for (var sample_name in navicell.dataset.samples) {
		sample_names.push(sample_name);
	}
	sample_names.sort();
	return sample_names;
}

// TBD: class HeatmapEditor
function update_heatmap_editor(doc, params, heatmapConfig) {
	var module = get_module_from_doc(doc);
	var drawing_config = navicell.getDrawingConfig(module);
	if (!heatmapConfig) {
		if (!drawing_config) {
			console.log("module: " + module);
		}
		heatmapConfig = drawing_config.getEditingHeatmapConfig();
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#heatmap_editor_div", doc);
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none; font-size: x-small";
	var table = $("#heatmap_editor_table", doc);
	var sel_gene_id = $("#heatmap_select_gene", doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	if (sample_group_cnt > max_heatmap_sample_cnt) {
		sample_group_cnt = max_heatmap_sample_cnt;
	}
	table.children().remove();
	var html = "";
	html += "<tbody>";
	html += "<tr>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "heatmap_clear_samples", "heatmap_sample_action(\"clear_samples\")") + "&nbsp;&nbsp;&nbsp;";
	html += "</td><td colspan='1' style='" + empty_cell_style + "'>";

	html += make_button("All samples", "heatmap_all_samples", "heatmap_sample_action(\"all_samples\")");
	html += "&nbsp;&nbsp;";
	if (group_cnt) {
		html += "&nbsp;&nbsp;" + make_button("All groups", "heatmap_all_groups", "heatmap_sample_action(\"all_groups\")");
	}
	html += "</td>";
	html += "</tr>";
	if (drawing_config.getBarplotConfig().getSampleOrGroupCount()) {
		var label = (group_cnt ? "Samples and Groups" : "Samples") + " from Barplot";
		html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>";
		html += make_button(label, "heatmap_from_barplot", "heatmap_sample_action(\"from_barplot\")");
		html += "</td></tr>";
	}
	html += "<tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td></tr>";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>Datatables</td>";

	var select_title = group_cnt ? 'Select a sample or group' : 'Select a sample';
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='heatmap_editor_gs_" + idx + "' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>\n";
		html += "<option value='_none_'>" + select_title + "</option>\n";
		var sel_group = heatmapConfig.getGroupAt(idx);
		var sel_sample = heatmapConfig.getSampleAt(idx);
		var group_names = get_group_names();
		for (var group_idx in group_names) {
			var group_name = group_names[group_idx];
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>";
		}
		var sel_sample = heatmapConfig.getSampleAt(idx);
		var sample_names = get_sample_names();
		for (var sample_idx in sample_names) {
			var sample_name = sample_names[sample_idx];
			var sample = navicell.dataset.samples[sample_name];
			var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
			html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample_name + "</option>";
		}
		html += "</select>";
		html += "</td>";
	}

	html += "</tr>\n";

	var datatable_cnt = mapSize(navicell.dataset.datatables);

	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var sel_datatable = heatmapConfig.getDatatableAt(idx);
		html += "<tr>";
		html += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='heatmap_step_display_config(" + idx + ",\"" + map_name + "\")'><span id='heatmap_editor_datatable_config_" + idx + "' class='" + (sel_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";
		html += "<td><select id='heatmap_editor_datatable_" + idx + "' onchange='heatmap_editor_set_editing(true," + idx + ", \"" + map_name + "\")'>\n";
		html += "<option value='_none_'>Select a datatable</option>\n";
		for (var datatable_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[datatable_name];
			var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
			html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>";
		}
		html += "</select></td>";
		if (sel_gene && sel_datatable) {
			var displayConfig = sel_datatable.getDisplayConfig(module);
			var gene_name = sel_gene.name;
			for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
				var sel_group = heatmapConfig.getGroupAt(idx2);
				var sel_sample = heatmapConfig.getSampleAt(idx2);
				var style = undefined;
				var value = undefined;
				if (sel_sample) {
					style = displayConfig.getHeatmapStyleSample(sel_sample.name, gene_name);
					value = displayConfig.getColorSampleValue(sel_sample.name, gene_name);
				} else if (sel_group) {
					style = displayConfig.getHeatmapStyleGroup(sel_group, gene_name);
					value = displayConfig.getColorGroupValue(sel_group, gene_name);
				}
				if (style != undefined && value != undefined && value != INVALID_VALUE) {
					if (!value) {
						value = 'NA';
					}
					html += "<td class='heatmap_cell' " + style + ">" + value + "</td>";
				} else {
					value = (sel_group || sel_sample ? 'undefined' : '&nbsp;');
					html += "<td style='text-align: center;' class='heatmap_cell'>" + value + "</td>";
				}
			}
		} else {
			for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
				html += "<td class='heatmap_cell'>&nbsp;</td>";
			}
		}
		html += "</tr>\n";
	}

	html += "</tbody>";

	table.append(html);

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size</span></td>";
	var size = heatmapConfig.getSize();

	html += "<td><select id='heatmap_editor_size' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>";
	html += "<option value='1'" + (size == 1 ? " selected" : "") +">Tiny</option>";
	html += "<option value='2'" + (size == 2 ? " selected" : "") +">Small</option>";
	html += "<option value='4'" + (size == 4 ? " selected" : "") +">Medium</option>";
	html += "<option value='6'" + (size == 6 ? " selected" : "") +">Large</option>";
	html += "<option value='8' " + (size == 8 ? " selected" : "") +">Very Large</option>";
	html += "</select><td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td style='text-align: center; padding-left: 50px; padding-right: 50px' class='slider-title'>Heatmap Transparency</td>";
	html == "</tr>";

	var scale_size = heatmapConfig.getScaleSize();
	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='heatmap_editor_scale_size' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>\n";
	html += "<option value='0'" + (scale_size == 0 ? " selected" : "") +">Do not depend on scale</option>\n";
	html += "<option value='1'" + (scale_size == 1 ? " selected" : "") +">Depend on scale</option>\n";
	html += "<option value='2'" + (scale_size == 2 ? " selected" : "") +">Depend on sqrt(scale)</option>\n";
	html += "<option value='3'" + (scale_size == 3 ? " selected" : "") +">Depend on sqrt(scale)/2</option>\n";
	html += "<option value='4'" + (scale_size == 4 ? " selected" : "") +">Depend on sqrt(scale)/3</option>\n";
	html += "<option value='5'" + (scale_size == 5 ? " selected" : "") +">Depend on sqrt(scale)/4</option>\n";
	html += "</select><td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td><div id='heatmap_editor_slider_div'></div></td>";
	html += "</tr></table>";

	$("#heatmap_editor_size_div", doc).html(html);

	var slider = $("#heatmap_editor_slider_div", doc);
	slider.slider({
		slide: function( event, ui ) {
			$("#heatmap_editing", doc).html(EDITING_CONFIGURATION);
		}
	});

	slider.slider("value", heatmapConfig.getTransparency());
	add_transparency_slider_labels(slider);

	drawing_config.getEditingHeatmapConfig().setSlider(slider);
	drawing_config.getHeatmapConfig().setSlider(slider);

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='heatmap_select_gene' onchange='update_heatmap_editor(window.document, null, navicell.getDrawingConfig(\"" + module + "\").getEditingHeatmapConfig())'>\n";
	html += "<option value='_none_'></option>\n";
	var sorted_gene_names = navicell.dataset.getSortedGeneNames();
	for (var idx in sorted_gene_names) {
		var gene_name = sorted_gene_names[idx];
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";

	$("#heatmap_gene_choice", doc).html(html);

        $("#heatmap_clear_samples").css("font-size", "10px");
        $("#heatmap_all_samples").css("font-size", "10px");
        $("#heatmap_all_groups").css("font-size", "10px");
}

function draw_heatmap(module, overlay, context, scale, gene_name, topx, topy)
{
	if (!module) {
		module = get_module();
	}
	var drawing_config = navicell.getDrawingConfig(module);
	var heatmapConfig = drawing_config.getHeatmapConfig();
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	if (sample_group_cnt > max_heatmap_sample_cnt) {
		sample_group_cnt = max_heatmap_sample_cnt;
	}

	var datatable_cnt = mapSize(navicell.dataset.datatables);

	var scale2 = heatmapConfig.getScale(scale);
	var size = heatmapConfig.getSize()*2;
	var cell_w = size*scale2;
	var cell_h = size*scale2;

	topx += 12; // does not depend on scale
	topy -= cell_h * datatable_cnt + 3;

	context.globalAlpha = slider2alpha(heatmapConfig.getTransparency());

	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var sel_datatable = heatmapConfig.getDatatableAt(idx);
		if (!sel_datatable) {
			topy += cell_h;
		}
	}

	var start_y = topy;
	var start_x = topx;

	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var sel_datatable = heatmapConfig.getDatatableAt(idx);
		if (!sel_datatable) {
			continue;
		}
		var displayConfig = sel_datatable.getDisplayConfig(module);
		start_x = topx;
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = heatmapConfig.getGroupAt(idx2);
			var sel_sample = heatmapConfig.getSampleAt(idx2);
			var bg = undefined;
			var value = undefined;
			if (sel_sample) {
				bg = displayConfig.getColorSample(sel_sample.name, gene_name);
				value = displayConfig.getColorSampleValue(sel_sample.name, gene_name);
			} else if (sel_group) {
				bg = displayConfig.getColorGroup(sel_group, gene_name);
				value = displayConfig.getColorGroupValue(sel_group, gene_name);
			}
			if (bg != undefined && value != undefined && value != INVALID_VALUE) {
				var fg = getFG_from_BG(bg);
				context.fillStyle = "#" + bg;
				fillStrokeRect(context, start_x, start_y, cell_w, cell_h);
			}
			start_x += cell_w;
		}
		start_y += cell_h;
	}
	overlay.addBoundBox([topx, topy, start_x-topx, start_y-topy], gene_name, "heatmap");
}

function barplot_editor_set_editing(val, idx) {
	$("#barplot_editing").html(val ? EDITING_CONFIGURATION : "");
	var module = get_module_from_doc(window.document);
	var drawing_config = navicell.getDrawingConfig(module);
	if (val) {
		barplot_editor_apply(drawing_config.getEditingBarplotConfig());
		window.update_barplot_editor(window.document, null, drawing_config.getEditingBarplotConfig());
	}
	if (idx != undefined) {
		if ($("#barplot_editor_datatable_" + idx).val() != '_none_') {
			$("#barplot_editor_datatable_config_" + idx).removeClass("zz-hidden");	
		} else {
			$("#barplot_editor_datatable_config_" + idx).addClass("zz-hidden");	
		}
	}
	var msg = get_barplot_config_message(false);
	$("#barplot_editor_msg_div").html(msg);
}

// 2013-09-03 TBD: must add a doc argument: but as this function is called
// from a string evaluation (onchange='step_display_config(...)'), I propose
// to give the doc_idx and get the doc value from an associative array.
// => should maintain an associative array: doc_idx -> doc
// + attribute doc.doc_idx
function barplot_step_display_config(idx, map_name) {
	var val = $("#barplot_editor_datatable_" + idx).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			var doc = (map_name && maps ? maps[map_name].document : null);
			datatable.showDisplayConfig(doc, !datatable.biotype.isContinuous() ? COLOR_SIZE_CONFIG : 'color');
		}
	}
}

function barplot_sample_action(action, cnt) {
	var module = get_module_from_doc(window.document);
	var drawing_config = navicell.getDrawingConfig(module);
	if (action == "clear_samples") {
		drawing_config.getEditingBarplotConfig().reset(true);
	} else if (action == "all_samples") {
		drawing_config.getEditingBarplotConfig().reset(true);
		max_barplot_sample_cnt = drawing_config.getEditingBarplotConfig().setAllSamples();
	} else if (action == "all_groups") {
		drawing_config.getEditingBarplotConfig().reset(true);
		max_barplot_sample_cnt = drawing_config.getEditingBarplotConfig().setAllGroups();
	} else if (action == "from_heatmap") {
		var heatmap_config = drawing_config.getHeatmapConfig();
		var cnt = heatmap_config.getSampleOrGroupCount();
		if (cnt) {
			drawing_config.getEditingBarplotConfig().reset(true);
			max_barplot_sample_cnt = drawing_config.getEditingBarplotConfig().setSamplesOrGroups(heatmap_config.getSamplesOrGroups());
		}
	}

	if (cnt) {
		max_barplot_sample_cnt = cnt;
	}
	max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

	$("#barplot_editing").html(EDITING_CONFIGURATION);
	update_barplot_editor(window.document);
	var msg = get_barplot_config_message(false);
	$("#barplot_editor_msg_div").html(msg);
}

// TBD: class BarplotEditor
function update_barplot_editor(doc, params, barplotConfig) {
	var module = get_module_from_doc(doc);
	var drawing_config = navicell.getDrawingConfig(module);
	if (!barplotConfig) {
		barplotConfig = drawing_config.getEditingBarplotConfig();
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#barplot_editor_div");
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var table = $("#barplot_editor_table", doc);
	var sel_gene_id = $("#barplot_select_gene", doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	if (sample_group_cnt > max_barplot_sample_cnt) {
		sample_group_cnt = max_barplot_sample_cnt;
	}
	table.children().remove();
	var html = "";
	html += "<tbody>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "barplot_clear_samples", "barplot_sample_action(\"clear_samples\")") + "&nbsp;&nbsp;&nbsp;";
	html += "</td><td colspan='1' style='" + empty_cell_style + "'>";
	html += make_button("All samples", "barplot_all_samples", "barplot_sample_action(\"all_samples\")") + "&nbsp;&nbsp;&nbsp;";
	if (group_cnt) {
		html += "<td style='" + empty_cell_style + "'>" + make_button("All groups", "barplot_all_groups", "barplot_sample_action(\"all_groups\")");
	}
	html += "</td></tr>";
	if (drawing_config.getHeatmapConfig().getSampleOrGroupCount()) {
		var label = (group_cnt ? "Samples and Groups" : "Samples") + " from Heatmap";
		html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>";
		html += make_button(label, "barplot_from_heatmap", "barplot_sample_action(\"from_heatmap\")");
		html += "</td></tr>";
	}
	html += "<tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td>";
	var idx = 0;
	var sel_datatable = barplotConfig.getDatatableAt(idx);
	html += "<tr>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	var MAX_BARPLOT_HEIGHT = 100.;
	if (sel_gene && sel_datatable) {
		var displayConfig = sel_datatable.getDisplayConfig(module);
		var gene_name = sel_gene.name;
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = barplotConfig.getGroupAt(idx2);
			var sel_sample = barplotConfig.getSampleAt(idx2);
			var style = undefined, height = undefined;
			if (sel_sample) {
				style = displayConfig.getBarplotStyleSample(sel_sample.name, gene_name);
				height = "height: " + displayConfig.getBarplotSampleHeight(sel_sample.name, gene_name, MAX_BARPLOT_HEIGHT) + "px;";
			} else if (sel_group) {
				style = displayConfig.getBarplotStyleGroup(sel_group, gene_name);
				height = "height: " + displayConfig.getBarplotGroupHeight(sel_group, gene_name, MAX_BARPLOT_HEIGHT) + "px;";
			}
			if (style != undefined) {
				style += height + "width: 100%;";
				html += "<td style='" + height + "width: 100%; vertical-align: bottom;'><table style='" + height + "width: 100%'><tr>";
				html += "<td class='barplot_cell' " + style + ">&nbsp;</td>";
				html += "</tr></table></td>";
			} else {
				html += "<td class='barplot_cell'>&nbsp;</td>";
			}
		}
	} else {
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			html += "<td class='barplot_cell'>&nbsp;</td>";
		}
	}
	html += "</tr>\n";

	html += "<tr>\n";
	html += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='barplot_step_display_config(" + idx + ", \"" + map_name + "\")'><span id='barplot_editor_datatable_config_" + idx + "' class='" + (sel_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";

	html += "<td><select id='barplot_editor_datatable_" + idx + "' onchange='barplot_editor_set_editing(true," + idx + ")'>\n";
	html += "<option value='_none_'>Select a datatable</option>\n";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
		html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>";
	}
	html += "</select></td>";

	if (sel_gene && sel_datatable) {
		var config = sel_datatable.biotype.isUnorderedDiscrete() ? COLOR_SIZE_CONFIG : 'color';
		var gene_name = sel_gene.name;
		var displayConfig = sel_datatable.getDisplayConfig(module);
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = barplotConfig.getGroupAt(idx2);
			var sel_sample = barplotConfig.getSampleAt(idx2);
			var value = undefined;
			if (sel_sample) {
				value = displayConfig.getColorSizeSampleValue(sel_sample.name, gene_name);
			} else if (sel_group) {
				value = displayConfig.getColorSizeGroupValue(sel_group, gene_name);
			}
			if (value != undefined && value != INVALID_VALUE) {
				var style = "style='text-align: center;'";
				if (!value) {
					value = 'NA';
				}
				html += "<td class='barplot_cell' " + style + ">" + value + "</td>";
			} else {
				value = (sel_group || sel_sample ? 'undefined' : '&nbsp;');
				html += "<td style='text-align: center;' class='barplot_cell'>" + value + "</td>";
			}
		}
	} else {
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			html += "<td class='barplot_cell'>&nbsp;</td>";
		}
	}

	html += "</tr>\n";

	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	var select_title = group_cnt ? 'Select a group or sample' : 'Select a sample';
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='barplot_editor_gs_" + idx + "' onchange='barplot_editor_set_editing(true)'>\n";
		html += "<option value='_none_'>" + select_title + "</option>\n";
		var sel_group = barplotConfig.getGroupAt(idx);
		var sel_sample = barplotConfig.getSampleAt(idx);
		var group_names = get_group_names();
		for (var group_idx in group_names) {
			var group_name = group_names[group_idx];
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>";
		}
		var sel_sample = barplotConfig.getSampleAt(idx);
		var sample_names = get_sample_names();
		for (var sample_idx in sample_names) {
			var sample_name = sample_names[sample_idx];
			var sample = navicell.dataset.samples[sample_name];
			var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
			html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample_name + "</option>";
		}
		html += "</select>";
		html += "</td>";
	}

	html += "</tr>\n";

	html += "</tbody>";

	table.append(html);

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size</span></td>";
	var height = barplotConfig.getHeight();

	html += "<td><select id='barplot_editor_height' onchange='barplot_editor_set_editing(true)'>";
	html += "<option value='1'" + (height == 1 ? " selected" : "") +">Tiny Height</option>";
	html += "<option value='2'" + (height == 2 ? " selected" : "") +">Small Height</option>";
	html += "<option value='4'" + (height == 4 ? " selected" : "") +">Medium Height</option>";
	html += "<option value='6'" + (height == 6 ? " selected" : "") +">Large Height</option>";
	html += "<option value='8' " + (height == 8 ? " selected" : "") +">Very Large Height</option>";
	html += "</select><td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td style='text-align: center; padding-left: 50px; padding-right: 50px' class='slider-title'>Barplot Transparency</td>";
	html += "</tr>";

	var width = barplotConfig.getWidth();

	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='barplot_editor_width' onchange='barplot_editor_set_editing(true)'>";
	html += "<option value='1'" + (width == 1 ? " selected" : "") +">Tiny Width</option>";
	html += "<option value='2'" + (width == 2 ? " selected" : "") +">Small Width</option>";
	html += "<option value='4'" + (width == 4 ? " selected" : "") +">Medium Width</option>";
	html += "<option value='6'" + (width == 6 ? " selected" : "") +">Large Width</option>";
	html += "<option value='8' " + (width == 8 ? " selected" : "") +">Very Large Width</option>";
	html += "</select><td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td><div id='barplot_editor_slider_div'></div></td>";
	html += "</tr>";

	var scale_size = barplotConfig.getScaleSize();
	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='barplot_editor_scale_size' onchange='barplot_editor_set_editing(true)'>\n";
	html += "<option value='0'" + (scale_size == 0 ? " selected" : "") +">Do not depend on scale</option>\n";
	html += "<option value='1'" + (scale_size == 1 ? " selected" : "") +">Depend on scale</option>\n";
	html += "<option value='2'" + (scale_size == 2 ? " selected" : "") +">Depend on sqrt(scale)</option>\n";
	html += "<option value='3'" + (scale_size == 3 ? " selected" : "") +">Depend on sqrt(scale)/2</option>\n";
	html += "<option value='4'" + (scale_size == 4 ? " selected" : "") +">Depend on sqrt(scale)/3</option>\n";
	html += "<option value='5'" + (scale_size == 5 ? " selected" : "") +">Depend on sqrt(scale)/4</option>\n";
	html += "</select><td>";
	html += "</tr></table>";

	$("#barplot_editor_size_div", doc).html(html);

	var slider = $("#barplot_editor_slider_div", doc);
	slider.slider({
		slide: function( event, ui ) {
			$("#barplot_editing", doc).html(EDITING_CONFIGURATION);
		}
	});

	slider.slider("value", barplotConfig.getTransparency());
	add_transparency_slider_labels(slider);
	drawing_config.getEditingBarplotConfig().setSlider(slider);
	drawing_config.getBarplotConfig().setSlider(slider);

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='barplot_select_gene' onchange='update_barplot_editor(window.document, null, navicell.getDrawingConfig(\"" + module + "\").getEditingBarplotConfig())'>\n";
	html += "<option value='_none_'></option>\n";
	var sorted_gene_names = navicell.dataset.getSortedGeneNames();
	for (var idx in sorted_gene_names) {
		var gene_name = sorted_gene_names[idx];
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";
	$("#barplot_gene_choice", doc).html(html);

        $("#barplot_clear_samples").css("font-size", "10px");
        $("#barplot_all_samples").css("font-size", "10px");
        $("#barplot_all_groups").css("font-size", "10px");
}

function draw_barplot(module, overlay, context, scale, gene_name, topx, topy)
{
	if (!module) {
		module = get_module();
	}
	var drawing_config = navicell.getDrawingConfig(module);
	var barplotConfig = drawing_config.getBarplotConfig();
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	if (sample_group_cnt > max_barplot_sample_cnt) {
		sample_group_cnt = max_barplot_sample_cnt;
	}

	var datatable_cnt = mapSize(navicell.dataset.datatables);

	var scale2 = barplotConfig.getScale(scale);
	var width = barplotConfig.getWidth()*1.5;
	var height = barplotConfig.getHeight()*1.5;
	var cell_w = width*scale2;
	var cell_h = height*scale2;

	topx += 12; // does not depend on scale
	var maxy = cell_h*4;
	var start_x = topx;
	var start_y = topy - 3;
	var idx = 0;
	var sel_datatable = barplotConfig.getDatatableAt(idx);
	if (!sel_datatable) {
		return;
	}
	context.globalAlpha = slider2alpha(barplotConfig.getTransparency());
	var displayConfig = sel_datatable.getDisplayConfig(module);
	for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
		var sel_group = barplotConfig.getGroupAt(idx2);
		var sel_sample = barplotConfig.getSampleAt(idx2);
		var bg = undefined;
		var height = undefined;
		if (sel_sample) {
			bg = displayConfig.getColorSizeSample(sel_sample.name, gene_name);
			height = displayConfig.getBarplotSampleHeight(sel_sample.name, gene_name, maxy);
		} else if (sel_group) {
			bg = displayConfig.getColorSizeGroup(sel_group, gene_name);
			height = displayConfig.getBarplotGroupHeight(sel_group, gene_name, maxy);
		}
		if (bg != undefined && height != undefined) {
			var fg = getFG_from_BG(bg);
			context.fillStyle = "#" + bg;
			fillStrokeRect(context, start_x, start_y-height, cell_w, height);
		}
		start_x += cell_w;
	}

	overlay.addBoundBox([topx, start_y-maxy, start_x-topx, maxy], gene_name, "barplot");
}

function datatable_select(id, onchange, sel_datatable) {
	var html = "<select id='" + id + "' onchange='" + onchange + "'>";

	html += "<option value='_none_'>Select a datatable</option>\n";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
		html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>\n";
	}
	return html + "</select>";
}

function sample_or_group_select(id, onchange, sel_group, sel_sample) {
	var html = "<select id='" + id + "' onchange='" + onchange + "'>";
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var select_title = group_cnt ? 'Select a group or sample' : 'Select a sample';
	html += "<option value='_none_'>" + select_title + "</option>\n";
	var group_names = get_group_names();
	if (group_names.length) {
		group_names.sort();
		for (var idx in group_names) {
			var group_name = group_names[idx];
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>\n";
		}
	}
	var sample_names = get_sample_names();
	for (var idx in sample_names) {
		var sample_name = sample_names[idx];
		var sample = navicell.dataset.samples[sample_name];
		var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
		html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample_name + "</option>\n";
	}

	return html + "</select>";
}

function get_glyph_config_message(num, head) {
	var msg_cont = head ? "<br/>&nbsp;&nbsp;" : "<br/>";
	var msg_beg = head ? ("You must:<br/>" + msg_cont) : "";
	var msg = "";

	if ($("#glyph_editor_gs_" + num).val() == "_none_") {
		var group_cnt = mapSize(navicell.group_factory.group_map);
		msg = msg_beg + (group_cnt ? 'select a sample or group' : 'select a sample');
	}
	if ($("#glyph_editor_datatable_shape_" + num).val() == "_none_") {
		msg += (msg ? msg_cont : msg_beg);
		msg += "select a datatable to be used for Shape";
	}
	if ($("#glyph_editor_datatable_color_" + num).val() == "_none_") {
		msg += (msg ? msg_cont : msg_beg);
		msg += "select a datatable to be used for Color";
	}
	if ($("#glyph_editor_datatable_size_" + num).val() == "_none_") {
		msg += (msg ? msg_cont : msg_beg);
		msg += "select a datatable to be used for Size";
	}
	return msg;
}

function get_heatmap_config_message(head) {
	var msg_cont = head ? "<br/>&nbsp;&nbsp;" : "<br/>";
	var msg_beg = head ? ("You must:<br/>" + msg_cont) : "";
	var msg = "";

	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;
	var datatable_cnt = mapSize(navicell.dataset.datatables);

	var found = false;
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		var val = $("#heatmap_editor_gs_" + idx).val();
		if (val != undefined && val != "_none_") {
			found = true;
			break;
		}
	}
	if (!found) {
		msg = msg_beg + (group_cnt ? 'select at least one sample or group' : 'select at least one sample');
	}

	found = false;
	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var val = $("#heatmap_editor_datatable_" + idx).val();
		if (val != undefined && val != "_none_") {
			found = true;
			break;
		}
	}
	if (!found) {
		msg += (msg ? msg_cont : msg_beg);
		msg += datatable_cnt > 1 ? "select at least one datatable" : "select a datatable";
	}
	return msg;
}

function get_barplot_config_message(head) {
	var msg_cont = head ? "<br/>&nbsp;&nbsp;" : "<br/>";
	var msg_beg = head ? ("You must:<br/>" + msg_cont) : "";
	var msg = "";

	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;
	var datatable_cnt = mapSize(navicell.dataset.datatables);

	var found = false;
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		var val = $("#barplot_editor_gs_" + idx).val();
		if (val != undefined && val != "_none_") {
			found = true;
			break;
		}
	}
	if (!found) {
		msg = msg_beg + (group_cnt ? 'select at least one sample or group' : 'select at least one sample');
	}

	found = false;
	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var val = $("#barplot_editor_datatable_" + idx).val();
		if (val != undefined && val != "_none_") {
			found = true;
			break;
		}
	}
	if (!found) {
		msg += (msg ? msg_cont : msg_beg);
		msg += "select a datatable";
	}
	return msg;
}

function get_map_staining_config_message(head) {
	var msg_cont = head ? "<br/>&nbsp;&nbsp;" : "<br/>";
	var msg_beg = head ? ("You must:<br/>" + msg_cont) : "";
	var msg = "";

	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;
	var datatable_cnt = mapSize(navicell.dataset.datatables);

	var found = false;
	var val = $("#map_staining_editor_gs").val();
	if (val != undefined && val != "_none_") {
		found = true;
	}
	if (!found) {
		msg = msg_beg + (group_cnt ? 'select a sample or group' : 'select a sample');
	}

	found = false;
	var val = $("#map_staining_editor_datatable_color").val();
	if (val != undefined && val != "_none_") {
		found = true;
	}
	if (!found) {
		msg += (msg ? msg_cont : msg_beg);
		msg += "select a datatable to be used for Color";
	}
	return msg;
}

function glyph_editor_set_editing(num, val, what) {
	$("#glyph_editing_" + num).html(val ? EDITING_CONFIGURATION : "");
	var module = get_module_from_doc(window.document);
	var drawing_config = navicell.getDrawingConfig(module);
	if (val) {
		document.win.glyph_editor_apply(num, drawing_config.getEditingGlyphConfig(num));
		document.win.update_glyph_editor(document, null, num, drawing_config.getEditingGlyphConfig(num));
	}
	if (what != undefined) {
		if ($("#glyph_editor_datatable_" + what).val() != '_none_') {
			$("#glyph_editor_datatable_config_" + what).removeClass("zz-hidden");	
		} else {
			$("#glyph_editor_datatable_config_" + what).addClass("zz-hidden");	
		}
	}
	var msg = get_glyph_config_message(num, false);
	$("#glyph_editor_msg_div_" + num).html(msg);
}

function glyph_step_display_config(what, num, map_name) {
	var val = $("#glyph_editor_datatable_" + what + "_" + num).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			var doc = (map_name && maps ? maps[map_name].document : null);
			datatable.showDisplayConfig(doc, what);
		}
	}
}

function draw_glyph_in_canvas(module, glyphConfig, doc)
{
	var num = glyphConfig.num;
	var sel_shape_datatable = glyphConfig.getShapeDatatable();
	var sel_color_datatable = glyphConfig.getColorDatatable();
	var sel_size_datatable = glyphConfig.getSizeDatatable();
	var sel_gene_id = $("#glyph_select_gene_" + num, doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;
	var sel_group = glyphConfig.getGroup();
	var sel_sample = glyphConfig.getSample();
	if (sel_gene && sel_shape_datatable && sel_color_datatable && sel_size_datatable && (sel_group || sel_sample)) {
		var shape, color, size;
		var gene_name = sel_gene.name;
		if (sel_sample) {
			var sample_name = sel_sample.name;
			shape = sel_shape_datatable.getDisplayConfig(module).getShapeSample(sample_name, gene_name);
			color = sel_color_datatable.getDisplayConfig(module).getColorSample(sample_name, gene_name);
			size = sel_size_datatable.getDisplayConfig(module).getSizeSample(sample_name, gene_name);
		} else {
			shape = sel_shape_datatable.getDisplayConfig(module).getShapeGroup(sel_group, gene_name);
			color = sel_color_datatable.getDisplayConfig(module).getColorGroup(sel_group, gene_name);
			size = sel_size_datatable.getDisplayConfig(module).getSizeGroup(sel_group, gene_name);
		}

		var drawing_canvas = glyphConfig.getDrawingCanvas();
		if (drawing_canvas) {
			var context = drawing_canvas.getContext('2d');
			context.clearRect(0, 0, drawing_canvas.width, drawing_canvas.height);
			draw_glyph_perform(context, drawing_canvas.width/2, drawing_canvas.height/2, shape, color, size, 8, true, glyphConfig.getTransparency());
		}
		return true;
	}
	return false;
}

function update_glyph_editor(doc, params, num, glyphConfig) {
	var module = get_module_from_doc(doc);
	var drawing_config = navicell.getDrawingConfig(module);
	if (!glyphConfig) {
		glyphConfig = drawing_config.getEditingGlyphConfig(num);
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#glyph_editor_div_" + num, doc);
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var table = $("#glyph_editor_table_" + num, doc);
	var sel_gene_id = $("#glyph_select_gene_" + num, doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	table.children().remove();
	var html = "";
	html += "<tbody>";

	html += "<table><tr><td style='" + empty_cell_style + "'>";

	html += "<table cellpadding='6'><tr>";
	html += "<td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Sample or Group</span></td>";
	html += "<td colspan='2' style='" + empty_cell_style + "'>";
	var sel_group = glyphConfig.getGroup();
	var sel_sample = glyphConfig.getSample();
	html += sample_or_group_select("glyph_editor_gs_" + num, 'glyph_editor_set_editing(' + num + ', true, "sample", "' + map_name + '")', sel_group, sel_sample);
	html += "</td>";
	html += "</tr>";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td></tr>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Shape</span></td>";

	html += "<td style='" + empty_cell_style + "'>";
	var sel_shape_datatable = glyphConfig.getShapeDatatable();
	html += datatable_select("glyph_editor_datatable_shape_" + num, 'glyph_editor_set_editing(' + num + ', true, "shape", "' + map_name + '")', sel_shape_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"shape\", " + num + ", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_shape_" + num + "' class='" + (sel_shape_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";
	html += "</tr>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Color</span></td>";

	html += "<td style='" + empty_cell_style + "'>";
	var sel_color_datatable = glyphConfig.getColorDatatable();
	html += datatable_select("glyph_editor_datatable_color_" + num, 'glyph_editor_set_editing(' + num + ', true, "color", "' + map_name + '")', sel_color_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"color\", " + num + ", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_color_" + num + "' class='" + (sel_color_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";

	html += "</tr>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Size</span></td>";
	html += "<td style='" + empty_cell_style + "'>";
	var sel_size_datatable = glyphConfig.getSizeDatatable();
	html += datatable_select("glyph_editor_datatable_size_" + num, 'glyph_editor_set_editing(' + num + ', true, "size", "' + map_name + '")', sel_size_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"size\", " + num + ", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_size_" + num + "' class='" + (sel_size_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";
	html += "</tr>";

	html += "</table>";
	html += "</td>";
	var CANVAS_W = 250;
	var CANVAS_H = 250;
	html += "<td style='width: " + CANVAS_W + "px; height: " + CANVAS_H + "px' id='glyph_editor_td_canvas_" + num + "' style='background: white;'></td>";
	html += "</tr>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td></tr>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'><div style='text-align: center' class='slider-title'>Glyph Transparency</div></td></tr>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td><div id='glyph_editor_slider_div_" + num + "'></div></td></tr>";
	html += "</table>";
	html += "</tbody>";

	table.append(html);

	var slider = $("#glyph_editor_slider_div_" + num, doc);
	slider.slider({
		slide: function(event, ui) {
			$("#glyph_editing_" + num, doc).html(EDITING_CONFIGURATION);
			glyphConfig.setTransparency(ui.value);
			draw_glyph_in_canvas(module, glyphConfig, doc);
		}
	});

	slider.slider("value", glyphConfig.getTransparency());
	add_transparency_slider_labels(slider);

	var draw_canvas = doc.createElement('canvas');
	draw_canvas.style.left = '0px';
	draw_canvas.style.top = '0px';

	draw_canvas.width = CANVAS_W;
	draw_canvas.height = CANVAS_H;
	draw_canvas.style.width = draw_canvas.width + 'px';
	draw_canvas.style.height = draw_canvas.height + 'px';

	draw_canvas.style.position = 'relative';
	var context = null;
	var td = $("#glyph_editor_td_canvas_" + num, doc).get(0);
	if (td) {
		td.appendChild(draw_canvas);
		context = draw_canvas.getContext('2d');

		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	drawing_config.getEditingGlyphConfig(num).setCanvasAndSlider(draw_canvas, slider);
	drawing_config.getGlyphConfig(num).setCanvasAndSlider(draw_canvas, slider);

	if (!draw_glyph_in_canvas(module, glyphConfig, doc) && context) {
		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size on Map</span></td>";
	var size = glyphConfig.getSize();

	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='glyph_editor_size_" + num + "' onchange='glyph_editor_set_editing(" + num + ", true)'>";
	html += "<option value='1'" + (size == 1 ? " selected" : "") +">Tiny</option>";
	html += "<option value='2'" + (size == 2 ? " selected" : "") +">Small</option>";
	html += "<option value='4'" + (size == 4 ? " selected" : "") +">Medium</option>";
	html += "<option value='6'" + (size == 6 ? " selected" : "") +">Large</option>";
	html += "<option value='8' " + (size == 8 ? " selected" : "") +">Very Large</option>";
	html += "</select><td>";
	html += "</tr>";

	var scale_size = glyphConfig.getScaleSize();
	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='glyph_editor_scale_size_" + num + "' onchange='glyph_editor_set_editing(" + num + ", true)'>\n";
	html += "<option value='0'" + (scale_size == 0 ? " selected" : "") +">Do not depend on scale</option>\n";
	html += "<option value='1'" + (scale_size == 1 ? " selected" : "") +">Depend on scale</option>\n";
	html += "<option value='2'" + (scale_size == 2 ? " selected" : "") +">Depend on sqrt(scale)</option>\n";
	html += "<option value='3'" + (scale_size == 3 ? " selected" : "") +">Depend on sqrt(scale)/2</option>\n";
	html += "<option value='4'" + (scale_size == 4 ? " selected" : "") +">Depend on sqrt(scale)/3</option>\n";
	html += "<option value='5'" + (scale_size == 5 ? " selected" : "") +">Depend on sqrt(scale)/4</option>\n";
	html += "</select><td>";
	html += "</tr></table>";

	$("#glyph_editor_size_div_" + num, doc).html(html);

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='glyph_select_gene_" + num + "' onchange='update_glyph_editor(document, null, " + num + ", navicell.getDrawingConfig(\"" + module + "\").getEditingGlyphConfig( + " + num + "))'>\n";
	html += "<option value='_none_'></option>\n";
	var sorted_gene_names = navicell.dataset.getSortedGeneNames();
	for (var idx in sorted_gene_names) {
		var gene_name = sorted_gene_names[idx];
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";
	$("#glyph_gene_choice_" + num, doc).html(html);
}


function draw_glyph(module, num, overlay, context, scale, gene_name, topx, topy)
{
	if (!module) {
		module = get_module();
	}
	var drawing_config = navicell.getDrawingConfig(module);
	var glyphConfig = drawing_config.getGlyphConfig(num);

	topy -= 2;
	var scale2 = glyphConfig.getScale(scale);
	var g_size = glyphConfig.getSize()*scale2;
	topx += 8;

	var sel_group = glyphConfig.getGroup();
	var sel_sample = glyphConfig.getSample();
	var sel_shape_datatable = glyphConfig.getShapeDatatable();
	var sel_color_datatable = glyphConfig.getColorDatatable();
	var sel_size_datatable = glyphConfig.getSizeDatatable();

	var bound = null;
	if (sel_shape_datatable && sel_color_datatable && sel_size_datatable && (sel_group || sel_sample)) {
		var shape, color, size;
		if (sel_sample) {
			shape = sel_shape_datatable.getDisplayConfig(module).getShapeSample(sel_sample.name, gene_name);
			color = sel_color_datatable.getDisplayConfig(module).getColorSample(sel_sample.name, gene_name);
			size = sel_size_datatable.getDisplayConfig(module).getSizeSample(sel_sample.name, gene_name);
		} else {
			shape = sel_shape_datatable.getDisplayConfig(module).getShapeGroup(sel_group, gene_name);
			color = sel_color_datatable.getDisplayConfig(module).getColorGroup(sel_group, gene_name);
			size = sel_size_datatable.getDisplayConfig(module).getSizeGroup(sel_group, gene_name);
		}

		var start_x = topx + (size*g_size)/8; // must depends on scale2 also
		var start_y = topy; // must depends on scale2 also
		bound = draw_glyph_perform(context, start_x, start_y, shape, color, (size*g_size)/4, 1, false, glyphConfig.getTransparency());
	}
	if (bound) {
		overlay.addBoundBox(bound, gene_name, "glyph", num);
	}
	return bound;
}

var LINE_SEPARATOR_STYLE = "#000000";
var LINE_SEPARATOR_WIDTH = 1;

function fillStrokeRect(context, x, y, w, h) {
	if (!w || !h) {
		return;
	}
	context.fillRect(x, y, w, h);

	var o_strokeStyle = context.strokeStyle;
	var o_lineWidth = context.lineWidth;
	context.strokeStyle = LINE_SEPARATOR_STYLE;
	context.lineWidth = LINE_SEPARATOR_WIDTH;

	context.strokeRect(x, y, w, h);

	context.strokeStyle = o_strokeStyle;
	context.lineWidth = o_lineWidth;
}

function fillStroke(context) {
	context.fill();

	var o_strokeStyle = context.strokeStyle;
	var o_lineWidth = context.lineWidth;
	context.strokeStyle = LINE_SEPARATOR_STYLE;
	context.lineWidth = LINE_SEPARATOR_WIDTH;

	context.stroke();

	context.strokeStyle = o_strokeStyle;
	context.lineWidth = o_lineWidth;
}

function slider2alpha(transparency)
{
	return ((transparency-1) * -0.7)/99 + 1;
}

function draw_glyph_perform(context, pos_x, pos_y, shape, color, size, scale, is_middle, transparency) {
	if (!context) {
		return;
	}

	size *= 1.5;
	shape = navicell.shapes[shape];

	var globalAlpha = context.globalAlpha;
	context.globalAlpha = slider2alpha(transparency);
	context.fillStyle = "#" + color;

	var dim = size*scale*1.;
	var dim2 = dim/2.;
	var ret;

	if (!is_middle) {
		pos_y -= dim2 + 1;
	}
	if (shape == 'Square') {
		fillStrokeRect(context, pos_x-dim2, pos_y-dim2, dim, dim);
		ret = [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Rectangle') {
		var dim_w = 2*size*scale;
		var dim_h = size*scale;
		fillStrokeRect(context, pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h);
		ret = [pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h];
	} else if (shape == 'Diamond') {
		context.beginPath();
		context.moveTo(pos_x-dim2, pos_y);
		context.lineTo(pos_x, pos_y-dim2);
		context.lineTo(pos_x+dim2, pos_y);
		context.lineTo(pos_x, pos_y+dim2);
		context.closePath();
		fillStroke(context);
		ret = [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Circle') {
		context.beginPath();
		context.arc(pos_x, pos_y, dim2, 0., Math.PI*2);
		context.closePath();
		fillStroke(context);
		ret = [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Triangle') {
		var side = dim/Math.sqrt(3.);
		context.beginPath();
		context.moveTo(pos_x-side, pos_y+dim2);
		context.lineTo(pos_x, pos_y-dim2);
		context.lineTo(pos_x+side, pos_y+dim2);
		context.closePath();
		fillStroke(context);
		ret = [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Hexagon') {
		var side = dim2/2;
		context.beginPath();
		context.moveTo(pos_x-dim2, pos_y);
		context.lineTo(pos_x-dim2+side, pos_y-dim2);
		context.lineTo(pos_x+dim2-side, pos_y-dim2);
		context.lineTo(pos_x+dim2, pos_y);
		context.lineTo(pos_x+dim2-side, pos_y+dim2);
		context.lineTo(pos_x-dim2+side, pos_y+dim2);
		context.closePath();
		fillStroke(context);
		ret = [pos_x-dim2, pos_y-dim2, dim, dim];
	} else {
		ret = null;
	}

	context.globalAlpha = globalAlpha;
	return ret;
}
 
function show_search_dialog()
{
	$("#search_dialog").dialog("open");
}

function map_staining_step_display_config(what, map_name) {
	var val = $("#map_staining_editor_datatable_" + what).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			var doc = (map_name && maps ? maps[map_name].document : null);
			datatable.showDisplayConfig(doc, what);
		}
	}
}

function map_staining_editor_set_editing(val, what) {
	var module = get_module_from_doc(window.document);
	var drawing_config = navicell.getDrawingConfig(module);
	if (val) {
		map_staining_editor_apply(drawing_config.getEditingMapStainingConfig());
		update_map_staining_editor(document, null, drawing_config.getEditingMapStainingConfig());
	}
	var msg = get_map_staining_config_message(false);
	$("#map_staining_editor_msg_div").html(msg);
}

function map_staining_editor_apply(map_staining_config)
{
	var val = $("#map_staining_editor_gs").val();
	if (val == "_none_") {
		map_staining_config.setSampleOrGroup(undefined);
	} else {
		var prefix = val.substr(0, 2);
		var id = val.substr(2);
		if (prefix == 'g_') {
			var group = navicell.group_factory.getGroupById(id);
			map_staining_config.setSampleOrGroup(group);
		} else {
			var sample = navicell.dataset.getSampleById(id);
			map_staining_config.setSampleOrGroup(sample);
		}
	}

	val = $("#map_staining_editor_datatable_color").val();
	if (val == "_none_") {
		map_staining_config.setColorDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		map_staining_config.setColorDatatable(datatable);
	}
	map_staining_config.setTransparency(map_staining_config.getSlider().slider("value"));
}

function draw_map_staining_perform(context, canvas_w, canvas_h, points, color) {
	var min_x = Number.MAX_NUMBER;
	var min_y = Number.MAX_NUMBER;
	var max_x = Number.MIN_NUMBER;
	var max_y = Number.MIN_NUMBER;
	for (var kk = 0; kk < points.length; kk+=2) {
		var x = points[kk];
		if (x < min_x) {
			min_x = x;
		}
		if (x > max_x) {
			max_x = x;
		}
		var y = points[kk+1];
		if (y < min_y) {
			min_y = y;
		}
		if (y > max_y) {
			max_y = y;
		}
	}
	var margin_x = 5;
	var margin_y = 5;
	var ori_canvas_w = canvas_w;
	var ori_canvas_h = canvas_h;
	canvas_w -= 2*margin_x;
	canvas_h -= 2*margin_y;
	var ratio_w = canvas_w/(max_x - min_x);
	var ratio_h = canvas_h/(max_y - min_y);
	var delta_x = 0;
	var delta_y = 0;
	var ratio;
	if (ratio_w > ratio_h) {
		ratio = ratio_h;
		delta_x = ((max_y-min_y)-(max_x-min_x))/2;
	} else {
		ratio = ratio_w;
		delta_y = ((max_x-min_x)-(max_y-min_y))/2;
	}
	context.lineWidth = 1;
	context.globalAlpha = 0.5; // for testing
	context.fillStyle = "#" + color;

	for (var kk = 0; kk < points.length; kk+=2) {
		var x = points[kk];
		var y = points[kk+1];
		var xx  = (x-min_x+delta_x)*ratio+margin_x;
		var yy = (y-min_y+delta_y)*ratio+margin_y;
		if (kk == 1) {
			context.moveTo(xx, yy);
		} else {
			context.lineTo(xx, yy);
		}
	}
	context.closePath();
	context.stroke();
	context.fill();
}

function update_map_staining_editor(doc, params, mapStainingConfig) {
	var module = get_module_from_doc(doc);
	var drawing_config = navicell.getDrawingConfig(module);
	if (!mapStainingConfig) {
		mapStainingConfig = drawing_config.getEditingMapStainingConfig();
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#map_staining_editor_div", doc);
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var table = $("#map_staining_editor_table", doc);
	var sel_gene_id = $("#map_staining_select_gene", doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	table.children().remove();
	var html = "";
	html += "<tbody>";

	html += "<table><tr><td style='" + empty_cell_style + "'>";

	html += "<table cellpadding='6'><tr>";
	html += "<td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Sample or Group</span></td>";
	html += "<td colspan='2' style='" + empty_cell_style + "'>";
	var sel_group = mapStainingConfig.getGroup();
	var sel_sample = mapStainingConfig.getSample();
	html += sample_or_group_select("map_staining_editor_gs", 'map_staining_editor_set_editing(true, "sample", "' + map_name + '")', sel_group, sel_sample);
	html += "</td>";
	html += "</tr>";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td></td>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Color</span></td>";

	html += "<td style='" + empty_cell_style + "'>";

	var sel_color_datatable = mapStainingConfig.getColorDatatable();
	html += datatable_select("map_staining_editor_datatable_color", 'map_staining_editor_set_editing(true, "color", "' + map_name + '")', sel_color_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='map_staining_step_display_config(\"color\", \"" + map_name + "\")'><span id='map_staining_editor_datatable_config_color' class='" + (sel_color_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";

	html += "</tr>";

	html += "</table>";
	html += "</td>";
	var CANVAS_W = 250;
	var CANVAS_H = 250;
	html += "<td style='width: " + CANVAS_W + "px; height: " + CANVAS_H + "px' id='map_staining_editor_td_canvas' style='background: white;'></td>";
	html += "</tr><tr><td style='" + empty_cell_style + "'>&nbsp;</td></tr>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'><div style='text-align: center' class='slider-title'>Map Staining Transparency</div></td></tr>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td><div id='map_staining_editor_slider_div'></div></td>";
	html += "</tr></table>";

	html += "</tbody>";

	table.append(html);

	var slider = $("#map_staining_editor_slider_div", doc);
	slider.slider({
		slide: function(event, ui) {
			//console.log("ui.value: " + ui.value + " " + mapStainingConfig.getTransparency());
			//mapStainingConfig.setTransparency(ui.value);
		}
	});

	slider.slider("value", mapStainingConfig.getTransparency());
	add_transparency_slider_labels(slider);

	var draw_canvas = doc.createElement('canvas');
	draw_canvas.style.left = '0px';
	draw_canvas.style.top = '0px';

	draw_canvas.width = CANVAS_W;
	draw_canvas.height = CANVAS_H;
	draw_canvas.style.width = draw_canvas.width + 'px';
	draw_canvas.style.height = draw_canvas.height + 'px';

	draw_canvas.style.position = 'relative';

	drawing_config.getEditingMapStainingConfig().setCanvasAndSlider(draw_canvas, slider);
	drawing_config.getMapStainingConfig().setCanvasAndSlider(draw_canvas, slider);

	var context = null;
	var td = $("#map_staining_editor_td_canvas", doc).get(0);
	if (td) {
		td.appendChild(draw_canvas);
		context = draw_canvas.getContext('2d');

		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	if (sel_gene && sel_color_datatable && (sel_group || sel_sample)) {
		var color;
		var gene_name = sel_gene.name;
		if (sel_sample) {
			var sample_name = sel_sample.name;
			color = sel_color_datatable.getDisplayConfig(module).getColorSample(sample_name, gene_name);
		} else {
			color = sel_color_datatable.getDisplayConfig(module).getColorGroup(sel_group, gene_name);
		}

		var voronoi_shape_map = navicell.mapdata.getVoronoiCells(module).getShapeMap();
		var last_points = null;
		for (var shape_id in sel_gene.getShapeIds(module)) {
			var voronoi_shape = voronoi_shape_map[shape_id];
			if (voronoi_shape) {
				var points = voronoi_shape[0];
				last_points = points;
				draw_map_staining_perform(context, CANVAS_W, CANVAS_H, points, color);
			} else {
			}
		}
	} else if (context) {
		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='map_staining_select_gene' onchange='update_map_staining_editor(document, null, navicell.getDrawingConfig(\"" + module + "\").getEditingMapStainingConfig())'>\n";
	html += "<option value='_none_'></option>\n";
	var sorted_gene_names = navicell.dataset.getSortedGeneNames();
	for (var idx in sorted_gene_names) {
		var gene_name = sorted_gene_names[idx];
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";
	$("#map_staining_gene_choice", doc).html(html);
}


function draw_voronoi(module, context, div)
{
	var drawing_config = navicell.getDrawingConfig(module);
	var mapStainingConfig = drawing_config.getMapStainingConfig();
	var sel_color_datatable = mapStainingConfig.getColorDatatable();
	if (!sel_color_datatable) {
		window.console.log("no color datatable");
		return;
	}

	var sel_group = mapStainingConfig.getGroup();
	var sel_sample = mapStainingConfig.getSample();
	if (!sel_group && !sel_sample) {
		window.console.log("no sel_sample or sel_group");
		return;
	}

	var overlayProjection = overlay.getProjection();
	var mapProjection = overlay.map_.getProjection();
	var scale = Math.pow(2, overlay.map_.zoom);
	var voronoi_shape_map = navicell.mapdata.getVoronoiCells(module).getShapeMap();
	context.lineWidth = 1;
	context.globalAlpha = slider2alpha(mapStainingConfig.getTransparency());
	context.font = "normal 12px";
	context.strokeStyle = "#000000";
	for (var shape_id in voronoi_shape_map) {
		var gene_name = navicell.dataset.getGeneByShapeId(module, shape_id);
		var color;
		if (!gene_name) {
			color = "888888";
		} else {
			if (sel_sample) {
				var sample_name = sel_sample.name;
				color = sel_color_datatable.getDisplayConfig(module).getColorSample(sample_name, gene_name);
			} else {
				color = sel_color_datatable.getDisplayConfig(module).getColorGroup(sel_group, gene_name);
			}
		}
		context.fillStyle = "#" + color;
		var points = voronoi_shape_map[shape_id][0];
		context.beginPath();
		var min_x = Number.MAX_NUMBER;
		var min_y = Number.MAX_NUMBER;
		var max_x = Number.MIN_NUMBER;
		var max_y = Number.MIN_NUMBER;
		for (var kk = 0; kk < points.length; kk+=2) {
			var xx = points[kk];
			var yy = points[kk+1];
			var gpt = new google.maps.Point(xx, yy);
			var latlng = mapProjection.fromPointToLatLng(gpt);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			var bx = pix.x - div.left;
			var by = pix.y - div.top;
			if (kk == 1) {
				context.moveTo(bx, by);
			} else {
				context.lineTo(bx, by);
			}
			if (bx < min_x) {
				min_x = bx;
			}
			if (by < min_y) {
				min_y = by;
			}
			if (bx > max_x) {
				max_x = bx;
			}
			if (by > max_y) {
				max_y = by;
			}
		}
		context.closePath();
		context.stroke();
		context.fill();

		// debug mode
		//context.strokeText(shape_id, (min_x + max_x)/2-40, (min_y + max_y)/2+3);
	}		
}
