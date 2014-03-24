/**
 * Eric Viara (Sysra), $Id$
 *
 * Copyright (C) 2013 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
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

$(function() {
	/*
	window.console.log("TRYING TO execute this function on module " + get_module() + " " + navicell.isModuleInit(get_module()));
	console.trace();
	if (navicell.isModuleInit(get_module())) {
		return;
	}
	navicell.setModuleInit(get_module());
	*/

	$('body').append("<div id='foo'></div>");

	var OPEN_DRAWING_EDITOR = true;
	// call nv1() to hide navicell 2 features
	//nv1();

	$("#right_tabs").tabs();

	function build_datatable_import_dialog() {
		var select = $("#dt_import_type_select");
		select.html(get_biotype_select("dt_import_type", true));
	}

	build_datatable_import_dialog();

	var name = $("#dt_import_name");
	var file = $("#dt_import_file");
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

	function error_message(error) {
		status.html("<span class=\"error-message\">" + error + "</span>");
	}

	function status_message(message) {
		status.html("<span class=\"status-message\">" + message + "</span>");
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
				/*
				console.log("PATTERNS [" + patterns + "]");
				console.log("eq_all [" + eq_all + "]");
				console.log("eq_any [" + eq_any + "]");
				console.log("neq_all [" + neq_all + "]");
				console.log("mode_word [" + mode_word + "]");
				console.log("mode_regex [" + mode_regex + "]");
				console.log("in_labels [" + in_labels + "]");
				console.log("in_tags [" + in_tags + "]");
				console.log("in_annots [" + in_annots + "]");
				console.log("in_all [" + in_all + "]");
				console.log("all_classes [" + all_classes + "]");
				console.log("all_classes_but [" + all_classes_but_included + "]");
				console.log("all_classes_included [" + all_classes_included + "]");
				console.log("select_classes [" + select_classes + "]");
				*/
				var search = "";
				var op;
				if (eq_any || neq_any) {
					search = patterns.replace(new RegExp("[ \t\n]+", "g"), ","); 
				} else if (eq_all || neq_all) {
					search = patterns.replace(new RegExp("[\t\n]+", "g"), " "); 
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
				console.log("searching for " + search);
				//$("body", window.document).css("cursor", "wait");
				$("#right_tabs", window.document).tabs("option", "active", 1);
				navicell.mapdata.findJXTree(window, search, false, 'subtree', {div: $("#result_tree_contents", window.document).get(0)});
				console.log("found !");
				//$("body", window.document).css("cursor", 'auto');
			},

			"Cancel": function() {
				$(this).dialog("close");
			}
		}
	});

	$("#dt_import_dialog" ).dialog({
		autoOpen: false,
		width: 450,
		height: 760,
		modal: false,
		buttons: {
			"Import": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				var error = "";
				if (!name.val()) {
					error = "Missing Name"
				}
				if (!file.val()) {
					if (error) {
						error += ", ";
					} else {
						error = "Missing ";
					}
					error += "File";
				}
				if (type.val() == "_none_") {
					if (error) {
						error += ", ";
					} else {
						error = "Missing ";
					}
					error += "Type";
				}

				if (error) {
					error_message(error);
				} else {
					var file_elem = file.get()[0];
					var datatable = navicell.dataset.readDatatable(type.val(), name.val(), file_elem.files[0], window);
					if (datatable.error) {
						error_message(datatable.error);
					} else {
						status_message("Importing...");

						datatable.ready.then(function() {
							if (datatable.error) {
								error_message(datatable.error);
							} else {
								console.log("hierarchy #1: " + window.document.navicell_module_name);
								var status_str = "<div align='center'><span style='text-align: center; font-weight: bold'>Import Successful</span></div>";
								status_str += "<table>";
								status_str += "<tr><td>Samples</td><td>" + datatable.getSampleCount() + "</td></tr>\n";
								var opener = window;
								var nnn = 0;
								while (opener && opener.document.map_name) {
									status_str += "<tr><td>Genes mapped on " + opener.document.map_name + "</td><td>" + datatable.getGeneCountPerModule(opener.document.map_name) + "</td></tr>";
									opener = opener.opener;
									if (nnn++ > 4) {
										console.log("too many level of hierarchy !");
										break;
									}
								}
								status_str += "</table>";
								status_message(status_str);
								
								//navicell.annot_factory.sync();
								//navicell.annot_factory.readannots(null, null);
								navicell.annot_factory.refresh();
								var display_graphics;
								if (import_display_barplot.attr('checked')) {
									var barplotConfig = drawing_config.editing_barplot_config;
									barplotConfig.setDatatableAt(0, datatable);
									if (OPEN_DRAWING_EDITOR) {
										$("#barplot_editing").html(EDITING_CONFIGURATION);
										$("#barplot_editor_div").dialog("open");
									} else {
										$("#drawing_config_chart_type").val('Barplot');
										drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
										barplot_sample_action('allsamples', DEF_OVERVIEW_BARPLOT_SAMPLE_CNT);
							
										barplot_editor_apply(drawing_config.barplot_config);
										barplot_editor_apply(drawing_config.editing_barplot_config);
										barplot_editor_set_editing(false);

										max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

										display_graphics = true;
									}
								} else if (import_display_heatmap.attr('checked')) {
									var heatmapConfig = drawing_config.editing_heatmap_config;
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
										if (!drawing_config.heatmap_config.getSampleOrGroupCount()) {
											heatmap_sample_action('allsamples', DEF_OVERVIEW_HEATMAP_SAMPLE_CNT);
										} else {
											heatmap_sample_action('pass');
										}

										heatmap_editor_apply(drawing_config.heatmap_config);
										heatmap_editor_apply(drawing_config.editing_heatmap_config);
										heatmap_editor_set_editing(false);

										max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;

										display_graphics = true;
									}
								} else {
									display_graphics = false;
								}
								var display_markers = import_display_markers.attr('checked');
								datatable.display(win.document.navicell_module_name, win, display_graphics, display_markers);
								drawing_editing(false);
								update_status_tables();
							}
						});
					}
					
				}
			},

			Clear: function() {
				name.val("");
				file.val("");
				type.val("");
				status_message("");
			},

			Cancel: function() {
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
//		height: 830,
		height: 680,
		modal: false,

		buttons: {
			"Apply": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.setDisplayMarkers($("#drawing_config_marker_display").attr("checked"));
				drawing_config.setDisplayOldMarkers($("#drawing_config_old_marker").val());
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
				//jQuery.jstree._reference(jtree).jstree("check_all");
				//jtree.jstree("check_all");
				//jtree.jstree("uncheck_node", $("li.navicell"));
				//jtree.jstree("uncheck_node", $(".jstree-checked"));
				for (var num = 1; num <= GLYPH_COUNT; ++num) {
					drawing_config.setDisplayGlyphs(num, $("#drawing_config_glyph_display_" + num).attr("checked"));
				}
				drawing_config.setDisplayDLOsOnAllGenes($("#drawing_config_display_all").attr("checked"));
				drawing_config.sync();
				set_old_marker_mode($("#drawing_config_old_marker").val());
				clickmap_refresh(true);
				drawing_editing(false);
			},

			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				$("#drawing_config_marker_display").attr("checked", drawing_config.displayMarkers());
				$("#drawing_config_chart_display").attr("checked", drawing_config.displayCharts() ? true : false);
				if (drawing_config.displayCharts()) {
					$("#drawing_config_chart_type").val(drawing_config.displayCharts());
				}
				$("#drawing_config_old_marker").val(drawing_config.displayOldMarkers());
				$("#drawing_config_display_all").attr("checked", drawing_config.displayDLOsOnAllGenes());
				drawing_editing(false);
				$(this).dialog('close');
			}
		}
	});

	$("#import_dialog").button().click(function() {
		if (!OPEN_DRAWING_EDITOR) {
			var module = get_module();
			var drawing_config = navicell.getDrawingConfig(module);
			var heatmap_sample_cnt = drawing_config.heatmap_config.getSampleOrGroupCount();
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
			"Import Annotation File": function() {
				var status = $("#dt_sample_annot_status");
				var file_elem = sample_file.get()[0];
				if (!$(file_elem).val()) {
					status.html("<span class=\"error-message\">No file given</span>");
				} else {
					navicell.annot_factory.readfile(file_elem.files[0], function(file) {status.html("<span class=\"error-message\">Cannot read file " + $(file_elem).val() + "</span>");});
					navicell.annot_factory.ready.then(function() {
						if (navicell.annot_factory.sample_read > 0) {
							status.html("<span class=\"status-message\">" + navicell.annot_factory.sample_read + " samples read<br/>" + navicell.annot_factory.sample_annotated + " samples annotated</span>");
						} else {
							status.html("<span class=\"error-message\">Missing samples: " + navicell.annot_factory.missing + "<br>No samples annotated, may be something wrong in the file.</span>");
						}
						sample_file.val("");
						update_status_tables({no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true});
					});
				}
			},
			Cancel: function() {
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
		// use tabs for datatable management
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
	//
	update_sample_annot_table(window.document);

	$("#heatmap_editor_div").dialog({
		autoOpen: false,
		width: 750,
		height: 500,
		modal: false,
		buttons: {
			"Apply": function() {
				// heatmap_editor
				// automatically change default configuration
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				$("#drawing_config_chart_type").val('Heatmap');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				heatmap_editor_apply(drawing_config.heatmap_config);
				// instead of calling this function again, it
				// should be better to copy heatmap_config to
				// editing_heatmap_config
				heatmap_editor_apply(drawing_config.editing_heatmap_config);
				drawing_config.heatmap_config.shrink();
				drawing_config.editing_heatmap_config.shrink();
				update_heatmap_editor(window.document);
				//update_status_tables();
				heatmap_editor_set_editing(false, undefined, document.map_name);
				// setting drawing configuration dialog
				$("#drawing_config_chart_display").attr("checked", true);
				$("#drawing_config_chart_type").val('Heatmap');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
				drawing_editing(false);

				clickmap_refresh(true);
			},
			
			/*
			"Clear": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				//drawing_config.heatmap_config.reset();
				drawing_config.editing_heatmap_config.reset();
				update_status_tables();
				heatmap_editor_set_editing(true, undefined, document.map_name);
			},
			*/

			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.editing_heatmap_config.cloneFrom(drawing_config.heatmap_config);
				update_heatmap_editor(window.document);
				//update_status_tables();
				heatmap_editor_set_editing(false, undefined, document.map_name);
				$(this).dialog('close');
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
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				// barplot_editor

				// automatically change default configuration
				$("#drawing_config_chart_type").val('Barplot');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				barplot_editor_apply(drawing_config.barplot_config);
				// instead of calling this function again, it
				// should be better to copy barplot_config to
				// editing_barplot_config
				barplot_editor_apply(drawing_config.editing_barplot_config);
				drawing_config.barplot_config.shrink();
				drawing_config.editing_barplot_config.shrink();
				update_barplot_editor(window.document);
				//update_status_tables();
				barplot_editor_set_editing(false);

				// setting drawing configuration dialog
				$("#drawing_config_chart_display").attr("checked", true);
				$("#drawing_config_chart_type").val('Barplot');
				drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());

				drawing_editing(false);

				clickmap_refresh(true);
			},
			
			/*
			"Clear": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				//drawing_config.barplot_config.reset();
				drawing_config.editing_barplot_config.reset();
				update_status_tables();
				barplot_editor_set_editing(true);
			},
			*/

			"Cancel": function() {
				var module = get_module();
				var drawing_config = navicell.getDrawingConfig(module);
				drawing_config.editing_barplot_config.cloneFrom(drawing_config.barplot_config);
				update_barplot_editor(window.document);
				//update_status_tables();
				barplot_editor_set_editing(false);
				$(this).dialog('close');
			}
		}
	});


	for (var num = 1; num <= GLYPH_COUNT; ++num) {
		$("#glyph_editor_div_" + num).data('num', num).dialog({
			autoOpen: false,
			width: 750,
			height: 660,
			modal: false,

			buttons: {
				"Apply": function() {
					var num = $(this).data('num');
					// glyph_editor
					var module = get_module();
					var drawing_config = navicell.getDrawingConfig(module);
					glyph_editor_apply(num, drawing_config.getGlyphConfig(num));
					// instead of calling this function again, it
					// should be better to copy glyph_config to
					// editing_glyph_config
					glyph_editor_apply(num, drawing_config.getEditingGlyphConfig(num));
					update_glyph_editors(window.document);
					//update_status_tables();

					// setting drawning configuration dialog
					$("#drawing_config_glyph_display_" + num).attr("checked", true);
					drawing_config.setDisplayGlyphs(num, true);
					drawing_editing(false);

					glyph_editor_set_editing(num, false);
					clickmap_refresh(true);
				},
				
				"Clear": function() {
					var module = get_module();
					var drawing_config = navicell.getDrawingConfig(module);
					var num = $(this).data('num');
					drawing_config.getGlyphConfig(num).reset();
					update_glyph_editors(window.document);
					//update_status_tables();
					glyph_editor_set_editing(num, true);
				},

				"Cancel": function() {
					var module = get_module();
					var drawing_config = navicell.getDrawingConfig(module);
					var num = $(this).data('num');
					drawing_config.getEditingGlyphConfig(num).cloneFrom(drawing_config.getGlyphConfig(num));
					update_glyph_editors(window.document);
					//update_status_tables();
					glyph_editor_set_editing(num, false);
					$(this).dialog('close');
				}
			}
		});
	}
});

var DEF_OVERVIEW_HEATMAP_SAMPLE_CNT = 5;
var DEF_OVERVIEW_BARPLOT_SAMPLE_CNT = 5;
var DEF_MAX_HEATMAP_SAMPLE_CNT = 25;
var DEF_MAX_BARPLOT_SAMPLE_CNT = 25;

var max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;
var max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

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
				//console.log("group selected " + id + " at index " + idx);
				var group = navicell.group_factory.getGroupById(id);
				heatmap_config.setSampleOrGroupAt(idx, group);
			} else {
				//console.log("sample selected " + id + " at index " + idx);
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
			//console.log("datatable selected " + val + " at index " + idx);
			var datatable = navicell.getDatatableById(val);
			heatmap_config.setDatatableAt(idx, datatable);
		}
	}
	heatmap_config.setSize($("#heatmap_editor_size", doc).val());
	heatmap_config.setScaleSize($("#heatmap_editor_scale_size", doc).val());
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
				//console.log("group selected " + id + " at index " + idx);
				var group = navicell.group_factory.getGroupById(id);
				barplot_config.setSampleOrGroupAt(idx, group);
			} else {
				//console.log("group selected " + id + " at index " + idx);
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
			//console.log("datatable selected " + val + " at index " + idx);
			var datatable = navicell.getDatatableById(val);
			barplot_config.setDatatableAt(idx, datatable);
		}
	}
	barplot_config.setHeight($("#barplot_editor_height").val());
	barplot_config.setWidth($("#barplot_editor_width").val());
	barplot_config.setScaleSize($("#barplot_editor_scale_size").val());
}

function glyph_editor_apply(num, glyph_config)
{
	//console.log("glyph_editor_apply " + num);
	var val = $("#glyph_editor_gs_" + num).val();
	if (val == "_none_") {
		glyph_config.setSampleOrGroup(undefined);
	} else {
		var prefix = val.substr(0, 2);
		var id = val.substr(2);
		if (prefix == 'g_') {
			//console.log("group selected " + id + " at index " + idx);
			var group = navicell.group_factory.getGroupById(id);
			glyph_config.setSampleOrGroup(group);
		} else {
			//console.log("group selected " + id + " at index " + idx);
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
				//if (datatable.sample_index[sample_name] !== undefined) {
				//str += "<td>" + mapSize(datatable.gene_index) + "</td>";
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

function annot_set_group(annot_id, doc) {
	var checkbox = $("#cb_annot_" + annot_id);
	var checked = checkbox.attr("checked");
	var annot = navicell.annot_factory.getAnnotationPerId(annot_id);

	annot.setIsGroup(checked);

	// 2013-09-06: moved from update_status_tables() : good idea ?
	// groups could be rebuilt on demand: if samples have been added for instance
	navicell.group_factory.buildGroups();
	update_status_tables({style_only: true, annot_id: annot_id, checked: checked, no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true});

//	$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'>" + mapSize(navicell.group_factory.group_map) + "</span> groups of samples have been built (groups are listed in Data Status / Groups tab) </span>");
	// TBD: factorize message with AnnotFactory.refresh()
	$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'>" + mapSize(navicell.group_factory.group_map) + "</span> groups of samples: groups are listed in My Data / Groups tab</span>");
}

function is_annot_group(annot_id) {
	var annot = navicell.annot_factory.getAnnotationPerId(annot_id);
	return annot.is_group ? "checked" : "";
}

function update_sample_annot_table_style(doc, params) {
	var annot_id = params.annot_id;
	var checked = params.checked;

	var td_tochange = $("#dt_sample_annot_table .annot_" + annot_id, doc);
	if (checked) {
		td_tochange.removeClass('non_group_annot');
		td_tochange.addClass('group_annot');
	} else {
		td_tochange.addClass('non_group_annot');
		td_tochange.removeClass('group_annot');
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
	if (annot_cnt != 0) {
		var str = "<thead>";
		if (annot_cnt) {
			str += "<tr><td>&nbsp;</td><td colspan='" + annot_cnt + "' style='text-align: left; font-style: italic; font-weight: bold; font-size: smaller;'>&nbsp;&nbsp;&nbsp;&nbsp;Check boxes to build groups</td></tr>\n";
		}
		str += '<tr><td style="text-align: right; font-size: smaller; font-style: italic;">' + '&nbsp;' + '</td>';
		for (var annot_name in annots) {
			var annot = navicell.annot_factory.getAnnotation(annot_name);
			var annot_id = annot.id;
			str += "<td style='text-align: center;'><input id='cb_annot_" + annot_id + "' type='checkbox' " + is_annot_group(annot_id) + " onchange='annot_set_group(\"" + annot_id + "\", window.document)'></input></td>";
		}
		str += "</tr>";
		str += "<tr class='coucou'><th>Samples (" + mapSize(navicell.dataset.samples) + ")</th>";
		if (0 == annot_cnt) {
			//str += "<th>No Annotations</th>";
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
			str += "<tr>";
			str += "<td>" + sample_name + "</td>";
			var sample = navicell.dataset.samples[sample_name];
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
	//table.tablesorter({debug: true, selectorHeaders: 'thead th'});
}

/*
function set_group_method(datatable_id, group_id) {
	var obj = $("#group_method_" + datatable_id + "_" + group_id);
	var group = navicell.group_factory.getGroupById(group_id);
	var datatable = navicell.getDatatableById(datatable_id);

	group.setMethod(datatable, obj.val());
}
*/

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

function update_gene_status_table(doc, params) {
	var table = $("#dt_gene_status_table", doc);
	table.children().remove();
	// should use a string buffer
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

/*
function show_cursor_wait(id) {
	console.log("SHOW_CURSOR_WAIT " + id);
	var body = $(body, document);
	var dt_show_data = $("#dt_show_data_" + id, document);
	body.css("cursor", "wait");
	dt_show_data.css("cursor", "wait");
}
*/

function show_datatable_data(id) {
	console.log("SHOW_DATATABLE_DATA " + id);
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
		/*
		var body = $(body, document);
		var dt_show_data = $("#dt_show_data_" + id, document);
		body.css("cursor", 'auto');
		dt_show_data.css("cursor", 'auto');
		*/
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
	/*
	if (!navicell.DTStatusMustUpdate && (!params || !params.force)) {
		return;
	}
	*/

	var table = $("#dt_datatable_status_table", doc);
	var update_label = $("#dt_datatable_status_update_label", doc);
	var update_checkbox = $("#dt_datatable_status_update", doc);
	if (doc != window.document) {
		update_checkbox.attr("checked", false);
	}
	//var update = update_checkbox.attr("checked");
	var update = true;
	var support_remove = true;

	/*
	// obsolete code: worked only with a jQuery dialog, no more with tabs
	var buttons = $("#dt_datatable_status", doc).parent().find(".ui-button-text");
	buttons.each(function() {
		var button  = $(this);
		var text = button.text();
		if (text == "Update" || text == "Cancel" ) {
			if (update) {
				button.parent().removeClass("zz-hidden");
			} else {
				button.parent().addClass("zz-hidden");
			}
		}
	});

	var buttonpane = $("#dt_datatable_buttonpane");
	if (update) {
		buttonpane.removeClass("zz-hidden");
	} else {
		buttonpane.addClass("zz-hidden");
	}

	update_label.text(update ? "Uncheck to lock edition" : "Check to edit datatables");
	*/
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
//		str += "<td>" + mapSize(datatable.sample_index) + "</td>";
		str += "<td>" + datatable.getSampleCount() + "</td>";
		/*
		if (!update) {
			str += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='navicell.getDatatableById(" + datatable.getId() + ").showDisplayConfig()'>display configuration</a></td>";
		}
		*/
		str += "<td style='border: none; text-decoration: underline; font-size: 11px'><a id='dt_show_markers_" + datatable.getId() + "' href='#' onclick='show_datatable_markers(" + datatable.getId() + ")'>gene&nbsp;markers</a><br/>";
		str += "<a id='dt_show_data_" + datatable.getId() + "' href='#' onclick='show_datatable_data(" + datatable.getId() + ")'>data&nbsp;" + (datatable.biotype.isSet() ? "list" : "matrix") + "</a></td>";
		//str += "<a id='dt_show_data_" + datatable.getId() + "' href='#' onMouseDown='show_cursor_wait(" + datatable.getId() + ")' onMouseUp='show_datatable_data(" + datatable.getId() + ")'>data&nbsp;" + (datatable.biotype.isSet() ? "list" : "matrix") + "</a></td>";
		//str += "<a id='show_datatable_" + datatable.getId() + "' href='#'>data&nbsp;" + (datatable.biotype.isSet() ? "list" : "matrix") + "</a></td>";
		str += "</tr>";
	}
	table.append(str);
	table.tablesorter();

//		$("#show_datatable_" + datatable.getId(), doc).on("mousedown", function() {

	/*
	$("#show_datatable_" + 1, doc).on("click", function() {
		console.log("MOUSE CLICK");
		$(this).css("cursor", "wait");
		$("#show_datatable_" + 1, doc).css("cursor", "wait");
		$("body", doc).css("cursor", "wait");
	});
	$("#show_datatable_" + 1, doc).on("click", function() {
		show_datatable_data(1);
	});
	*/
	if (DATATABLE_HAS_TABS) {
		tab_body.tabs("refresh");
	}
	navicell.DTStatusMustUpdate = false;
}

function update_status_tables(params) {
	//navicell.annot_factory.sync();
	//navicell.group_factory.buildGroups();
	//console.trace();
	for (var map_name in maps) {
		var doc = maps[map_name].document;
		if (params && params.doc && params.doc != doc) {
			continue;
		}
		console.log("update_status_table " + map_name);
		if (!params || !params.no_sample_status_table) {
			console.log("sample_status_table");
			update_sample_status_table(doc, params);
		}
		if (!params || !params.no_gene_status_table) {
			console.log("gene_status_table");
			update_gene_status_table(doc, params);
		}
		if (!params || !params.no_group_status_table) {
			console.log("group_status_table");
			update_group_status_table(doc, params);
		}
		if (!params || !params.no_module_status_table) {
			console.log("module_status_table");
			update_module_status_table(doc, params);
		}
		if (!params || !params.no_datatable_status_table) {
			console.log("datatable_status_table");
			update_datatable_status_table(doc, params);
		}
		if (!params || !params.no_sample_annot_table) {
			console.log("sample_annot_status_table");
			update_sample_annot_table(doc, params);
		}

		update_heatmap_editor(doc, params);
		update_barplot_editor(doc, params);
		update_glyph_editors(doc, params);
		/*
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			update_glyph_editor(doc, params, num);
		}
		*/
	}
	//navicell_session.write();
}

function update_glyph_editors(doc, params) {
	for (var num = 1; num <= GLYPH_COUNT; ++num) {
		update_glyph_editor(doc, params, num);
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
	str += "</select>";
	return str;
}

function update_datatables() {
	var update_cnt = 0;
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
			update_cnt++;
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
	if (update_cnt) {
		update_datatable_status_table(doc, {force: true});
		update_status_tables();
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
	console.log("doc " + doc + " " + module);
	var displayConfig = this.getDisplayConfig(module);
	if (displayConfig) {
		//div_id = displayConfig.getDivId(what);
		div = displayConfig.getDiv(what);
	}
	console.log("showDisplayConfig: " + div + " " + (doc ? doc.map_name : "") + " " + what + " " + displayConfig);
	//if (div_id) {
	if (div) {
		//var div = $("#" + div_id, doc);
		var datatable_id = this.getId();
		//console.log("div.length: " + div.length);
		var width;
		if (what == COLOR_SIZE_CONFIG) {
			width = 500;
		} else if (what == 'color') {
			width = 440;
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
					//console.log("module : " + module);
					var displayStepConfig = datatable.displayStepConfig[module];
					var displayDiscreteConfig = datatable.displayDiscreteConfig[module];
					var active = div.tabs("option", "active");
					var tabname = DisplayStepConfig.tabnames[active];
					if (displayStepConfig) {
						var prev_value = datatable.minval;
						var error = 0;
						var step_cnt = displayStepConfig.getStepCount(what, tabname);
						if (displayStepConfig.has_empty_values) {
							step_cnt++;
						}
						for (var idx = 0; idx < step_cnt; ++idx) {
							var value;
							if (idx == step_cnt-1) {
								value = datatable.maxval;
							} else {
								value = $("#step_value_" + what + '_' + datatable_id + "_" + idx, doc).val();
							}
							value *= 1.;
							if (value <= prev_value) {
								// should pop an error dialog
								error = 1;
								break;
							}
							prev_value = value;
						}
						
						if (!error) {
							for (var idx = 0; idx < step_cnt; ++idx) {
								var value;
								if (idx == step_cnt-1) {
									value = datatable.maxval;
								} else {
									value = $("#step_value_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								}
								var color = $("#step_config_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								var size = $("#step_size_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								var shape = $("#step_shape_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
								displayStepConfig.setStepInfo(what, tabname, idx, value, color, size, shape);
							}
							DisplayStepConfig.setEditing(datatable_id, false, what, doc.win);
						}
					}
					if (displayDiscreteConfig) {
						var value_cnt = displayDiscreteConfig.getValueCount();
						if (tabname == 'group') {
							value_cnt++;
						}
						for (var idx = 0; idx < value_cnt; ++idx) {
							var cond = $("#discrete_cond_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var color = $("#discrete_color_" + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var size = $("#discrete_size_"  + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							var shape = $("#discrete_shape_"  + tabname + '_' + what + '_' + datatable_id + "_" + idx, doc).val();
							displayDiscreteConfig.setValueInfo(what, tabname, idx, color, size, shape, cond);
						}
						DisplayDiscreteConfig.setEditing(datatable_id, false, what, doc.win);
						//display_discrete_config_set_editing(datatable_id, false, what, tabname);
					}
					//datatable.refresh();
					doc.win.clickmap_refresh(true);
					update_status_tables({no_sample_status_table: true, no_gene_status_table: true, no_module_status_table: true, no_datatable_status_table: true, no_group_status_table: true, no_sample_annot_table: true, doc: doc});
					//update_status_tables();
				},

				"Cancel": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayStepConfig = datatable.displayStepConfig[module];
					var displayDiscreteConfig = datatable.displayDiscreteConfig[module];
					var active = div.tabs("option", "active");
					var tabname = DisplayStepConfig.tabnames[active];
					if (displayStepConfig) {
						displayStepConfig.update();
						DisplayStepConfig.setEditing(datatable_id, false, what, doc.win);
					}
					if (displayDiscreteConfig) {
						displayDiscreteConfig.update();
						DisplayDiscreteConfig.setEditing(datatable_id, false, what, doc.win);
						//display_discrete_config_set_editing(datatable_id, false, what, tabname);
					}
					$(this).dialog('close');
				}
			}
		});

		//console.log("div.open");
		div.dialog("open");
	}
}

function datatable_management_set_editing(val) {
	$("#dt_datatable_status_editing").html(val ? 'changes not saved...' : "");
}


// TBD: should be a method DisplayDiscreteConfig
function display_discrete_config_set_editing(datatable_id, val, what, tabname) {
	$("#discrete_config_editing_" + tabname + '_' + what + '_' + datatable_id).html(val ? EDITING_CONFIGURATION : "");
}

function drawing_config_chart() {
	var doc = window.document;
	console.log("drawing_config_chart " + get_module() + " " + doc);
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

function drawing_editing(val) {
	$("#drawing_editing").html(val ? EDITING_CONFIGURATION : "");
}

function heatmap_editor_set_editing(val, idx, map_name) {
	//console.log("heatmap_editor_set_editing: " + map_name);
	var doc = (map_name && maps ? maps[map_name].document : null);
	$("#heatmap_editing", doc).html(val ? EDITING_CONFIGURATION : "");
	if (val) {
		var module = get_module_from_doc(doc);
		var drawing_config = navicell.getDrawingConfig(module);
		heatmap_editor_apply(drawing_config.editing_heatmap_config);
		update_heatmap_editor(doc, null, drawing_config.editing_heatmap_config);
	}
	// ok
	if (idx != undefined && $("#heatmap_editor_datatable_" + idx, doc).val() != '_none_') {
		$("#heatmap_editor_datatable_config_" + idx, doc).removeClass("zz-hidden");	
	} else {
		$("#heatmap_editor_datatable_config_" + idx, doc).addClass("zz-hidden");	
	}
}

// 2013-09-03 TBD: must add a doc argument: but as this function is called
// from a string evaluation (onchange='step_display_config(...)'), I propose
// to give the doc_idx and get the doc value from an associative array.
// => should maintain an associative array: doc_idx -> doc
// + attribute doc.doc_idx
function heatmap_step_display_config(idx, map_name) {
	var doc = (map_name && maps ? maps[map_name].document : null);
	console.log("heatmap_step_display_config: " + map_name + " " + doc);
	var val = $("#heatmap_editor_datatable_" + idx, doc).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			datatable.showDisplayConfig(doc, 'color');
		}
	}
}

function heatmap_sample_action(action, cnt) {
	//console.log("action [" + action + "]");
	var doc = window.document;
	var module = get_module();
	var drawing_config = navicell.getDrawingConfig(module);
	if (action == "clear") {
		drawing_config.editing_heatmap_config.reset(true);
		//max_heatmap_sample_cnt = 0;
	} else if (action == "allsamples") {
		max_heatmap_sample_cnt = drawing_config.editing_heatmap_config.setAllSamples();
	} else if (action == "allgroups") {
		max_heatmap_sample_cnt = drawing_config.editing_heatmap_config.setAllGroups();
	} else if (action == "from_barplot") {
		var barplot_config = drawing_config.barplot_config;
		var cnt = barplot_config.getSampleOrGroupCount();
		if (cnt) {
			max_heatmap_sample_cnt = drawing_config.editing_heatmap_config.setSamplesOrGroups(barplot_config.getSamplesOrGroups());
		}
	} else if (action == "pass") {
	}

	if (cnt) {
		max_heatmap_sample_cnt = cnt;
	}
	// 2014-02-28: reconnect this
	max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;
	$("#heatmap_editing", doc).html(EDITING_CONFIGURATION);
	update_heatmap_editor(window.document);
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
		heatmapConfig = drawing_config.editing_heatmap_config;
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#heatmap_editor_div", doc);
	var topdiv = div.parent().parent();
	//var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none; font-size: x-small";
	var table = $("#heatmap_editor_table", doc);
	var sel_gene_id = $("#heatmap_select_gene", doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	//console.log("updating heatmap_editor " + sel_gene_id + " " + (sel_gene ? sel_gene.name : "NULL") + " map_name: " + map_name);
	//var datatable_cnt = mapSize(navicell.dataset.datatables);
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	//console.log("update_heatmap_editor: " + sel_gene);
	if (sample_group_cnt > max_heatmap_sample_cnt) {
		sample_group_cnt = max_heatmap_sample_cnt;
	}
	table.children().remove();
	var html = "";
	html += "<tbody>";
	html += "<tr>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "heatmap_clear_samples", "heatmap_sample_action(\"clear\")") + "&nbsp;&nbsp;&nbsp;";
	html += "</td><td colspan='1' style='" + empty_cell_style + "'>";

	html += make_button("All samples", "heatmap_all_samples", "heatmap_sample_action(\"allsamples\")");
	html += "&nbsp;&nbsp;";
	//html += "</td>";
	if (group_cnt) {
		//html += "<td style='" + empty_cell_style + "'>" + make_button("All groups", "heatmap_all_groups", "heatmap_sample_action(\"allgroups\")") +  "</td>";
		html += "&nbsp;&nbsp;" + make_button("All groups", "heatmap_all_groups", "heatmap_sample_action(\"allgroups\")");
	}
	html += "</td>";
	html += "</tr>";
	if (drawing_config.barplot_config.getSampleOrGroupCount()) {
		var label = (group_cnt ? "Samples and Groups" : "Samples") + " from Barplot";
		html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>";
		html += make_button(label, "heatmap_from_barplot", "heatmap_sample_action(\"from_barplot\")");
		html += "</td></tr>";
	}
	html += "<tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td></tr>";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>Datatables</td>";

	var select_title = group_cnt ? 'Choose a group or sample' : 'Choose a sample';
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='heatmap_editor_gs_" + idx + "' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>\n";
		html += "<option value='_none_'>" + select_title + "</option>\n";
		var sel_group = heatmapConfig.getGroupAt(idx);
		var sel_sample = heatmapConfig.getSampleAt(idx);
		var group_names = get_group_names();
		for (var group_idx in group_names) {
			var group_name = group_names[group_idx];
			//for (var group_name in navicell.group_factory.group_map) {
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
		html += "<option value='_none_'>Choose a datatable</option>\n";
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
	//table.tablesorter();

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size</span></td>";
	var size = heatmapConfig.getSize();

	html += "<td><select id='heatmap_editor_size' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>";
	html += "<option value='1'" + (size == 1 ? " selected" : "") +">Tiny</option>";
	html += "<option value='2'" + (size == 2 ? " selected" : "") +">Small</option>";
	html += "<option value='4'" + (size == 4 ? " selected" : "") +">Medium</option>";
	html += "<option value='6'" + (size == 6 ? " selected" : "") +">Large</option>";
	html += "<option value='8' " + (size == 8 ? " selected" : "") +">Very Large</option>";
	html += "</select><td>";
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
	html += "</tr></table>";

	$("#heatmap_editor_size_div", doc).html(html);

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='heatmap_select_gene' onchange='update_heatmap_editor(window.document, null, navicell.getDrawingConfig(\"" + module + "\").editing_heatmap_config)'>\n";
	/*html += "<select id='heatmap_select_gene'>\n";*/
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
	var heatmapConfig = drawing_config.heatmap_config;
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
	//topy -= cell_h * datatable_cnt + 4;
	topy -= cell_h * datatable_cnt + 3;

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
	//console.log("boundbox: " + topx + ", " + topy + ", " + (start_x-topx) + ", " + (start_y-topy));
	overlay.addBoundBox([topx, topy, start_x-topx, start_y-topy], gene_name, "heatmap");
}

function barplot_editor_set_editing(val, idx) {
	$("#barplot_editing").html(val ? EDITING_CONFIGURATION : "");
	//$("#gene_choice").css("visibility", val ? "hidden" : "visible");
	// TBD
	var module = get_module_from_doc(window.document);
	console.log("barplot set editing " + module);
	var drawing_config = navicell.getDrawingConfig(module);
	if (val) {
		barplot_editor_apply(drawing_config.editing_barplot_config);
		update_barplot_editor(window.document, null, drawing_config.editing_barplot_config);
	}
	// ok
	if (idx != undefined && $("#barplot_editor_datatable_" + idx).val() != '_none_') {
		$("#barplot_editor_datatable_config_" + idx).removeClass("zz-hidden");	
	} else {
		$("#barplot_editor_datatable_config_" + idx).addClass("zz-hidden");	
	}
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
	console.log("action [" + action + "] " + module);
	var drawing_config = navicell.getDrawingConfig(module);
	if (action == "clear") {
		drawing_config.editing_barplot_config.reset(true);
		//max_barplot_sample_cnt = 0;
	} else if (action == "allsamples") {
		max_barplot_sample_cnt = drawing_config.editing_barplot_config.setAllSamples();
	} else if (action == "allgroups") {
		max_barplot_sample_cnt = drawing_config.editing_barplot_config.setAllGroups();
	} else if (action == "from_heatmap") {
		var heatmap_config = drawing_config.heatmap_config;
		var cnt = heatmap_config.getSampleOrGroupCount();
		if (cnt) {
			max_barplot_sample_cnt = drawing_config.editing_barplot_config.setSamplesOrGroups(heatmap_config.getSamplesOrGroups());
		}
	}

	if (cnt) {
		max_barplot_sample_cnt = cnt;
	}
	// 2014-02-28: reconnect this
	max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;

	$("#barplot_editing").html(EDITING_CONFIGURATION);
	update_barplot_editor(window.document);
}

// TBD: class BarplotEditor
function update_barplot_editor(doc, params, barplotConfig) {
	//console.log("updating barplot_editor");
	var module = get_module_from_doc(doc);
	var drawing_config = navicell.getDrawingConfig(module);
	if (!barplotConfig) {
		barplotConfig = drawing_config.editing_barplot_config;
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#barplot_editor_div");
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var table = $("#barplot_editor_table", doc);
	var sel_gene_id = $("#barplot_select_gene", doc).val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	//var datatable_cnt = mapSize(navicell.dataset.datatables);
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	//console.log("update_barplot_editor: " + sel_gene);
	if (sample_group_cnt > max_barplot_sample_cnt) {
		sample_group_cnt = max_barplot_sample_cnt;
	}
	table.children().remove();
	var html = "";
	html += "<tbody>";

//	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "barplot_clear_samples", "barplot_sample_action(\"clear\")") + "&nbsp;&nbsp;&nbsp;";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "barplot_clear_samples", "barplot_sample_action(\"clear\")") + "&nbsp;&nbsp;&nbsp;";
	html += "</td><td colspan='1' style='" + empty_cell_style + "'>";
	html += make_button("All samples", "barplot_all_samples", "barplot_sample_action(\"allsamples\")") + "&nbsp;&nbsp;&nbsp;";
	if (group_cnt) {
		html += "<td style='" + empty_cell_style + "'>" + make_button("All groups", "barplot_all_groups", "barplot_sample_action(\"allgroups\")");
	}
	html += "</td></tr>";
	if (drawing_config.heatmap_config.getSampleOrGroupCount()) {
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
		console.log("YES ? sel_datatable: " + sel_datatable + " " + sample_group_cnt);
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
	html += "<option value='_none_'>Choose a datatable</option>\n";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
		html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>";
	}
	html += "</select></td>";

	if (sel_gene && sel_datatable) {
		var config = sel_datatable.biotype.isDiscrete() ? COLOR_SIZE_CONFIG : 'color';
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
	var select_title = group_cnt ? 'Choose a group or sample' : 'Choose a sample';
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='barplot_editor_gs_" + idx + "' onchange='barplot_editor_set_editing(true)'>\n";
		html += "<option value='_none_'>" + select_title + "</option>\n";
		var sel_group = barplotConfig.getGroupAt(idx);
		var sel_sample = barplotConfig.getSampleAt(idx);
		var group_names = get_group_names();
		for (var group_idx in group_names) {
			var group_name = group_names[group_idx];
			//for (var group_name in navicell.group_factory.group_map) {
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>";
		}
		var sel_sample = barplotConfig.getSampleAt(idx);
		var sample_names = get_sample_names();
		for (var sample_idx in sample_names) {
			var sample_name = sample_names[sample_idx];
			//for (var sample_name in navicell.dataset.samples) {
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
	//table.tablesorter();

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size</span></td>";
	var height = barplotConfig.getHeight();

	html += "<td><select id='barplot_editor_height' onchange='barplot_editor_set_editing(true)'>";
	html += "<option value='1'" + (height == 1 ? " selected" : "") +">Tiny Height</option>";
	html += "<option value='2'" + (height == 2 ? " selected" : "") +">Small Height</option>";
	html += "<option value='4'" + (height == 4 ? " selected" : "") +">Medium Height</option>";
	html += "<option value='6'" + (height == 6 ? " selected" : "") +">Large Height</option>";
	html += "<option value='8' " + (height == 8 ? " selected" : "") +">Very Large Height</option>";
	html += "</select><td>";
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

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='barplot_select_gene' onchange='update_barplot_editor(window.document, null, navicell.getDrawingConfig(\"" + module + "\").editing_barplot_config)'>\n";
	/*html += "<select id='barplot_select_gene'>\n";*/
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
	//console.log("drawing barplot");
	var drawing_config = navicell.getDrawingConfig(module);
	var barplotConfig = drawing_config.barplot_config;
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

	html += "<option value='_none_'>Choose a datatable</option>\n";
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
	var select_title = group_cnt ? 'Choose a group or sample' : 'Choose a sample';
	html += "<option value='_none_'>" + select_title + "</option>\n";
	/*
	var group_names = [];
	for (var group_name in navicell.group_factory.group_map) {
		group_names.push(group_name);
	}
	*/
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

	/*
	for (var group_name in navicell.group_factory.group_map) {
		var group = navicell.group_factory.group_map[group_name];
		var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
		html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>\n";
	}
	for (var sample_name in navicell.dataset.samples) {
		var sample = navicell.dataset.samples[sample_name];
		var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
		html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample.name + "</option>\n";
	}
	*/
	return html + "</select>";
}

function glyph_editor_set_editing(num, val, what) {
	$("#glyph_editing").html(val ? EDITING_CONFIGURATION : "");
	// TBD
	var module = get_module_from_doc(window.document);
	console.log("glyph editor set editing " + module);
	var drawing_config = navicell.getDrawingConfig(module);
	if (val) {
		glyph_editor_apply(num, drawing_config.getEditingGlyphConfig(num));
		update_glyph_editor(document, null, num, drawing_config.getEditingGlyphConfig(num));
	}
	//console.log("glyph_editor_set_editing: " + val + " " + what);
	// ok
	if (what != undefined && $("#glyph_editor_datatable_" + what).val() != '_none_') {
		$("#glyph_editor_datatable_config_" + what).removeClass("zz-hidden");	
	} else {
		$("#glyph_editor_datatable_config_" + what).addClass("zz-hidden");	
	}
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

function update_glyph_editor(doc, params, num, glyphConfig) {
	//console.log("updating glyph_editor " + num);
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
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td></td>";

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
	html += "</tr></table>";

	html += "</tbody>";

	table.append(html);

	var draw_canvas = document.createElement('canvas');
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

		//console.log("#shape: " + shape + " " + navicell.shapes[shape]);
		//console.log("#color: " + color);
		//console.log("#size: " + size);
		draw_glyph_perform(context, CANVAS_W/2, CANVAS_H/2, shape, color, size, 8, true);
	} else if (context) {
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
	//console.log("draw_glyph: " + module);
	var drawing_config = navicell.getDrawingConfig(module);
	var glyphConfig = drawing_config.getGlyphConfig(num);

	topy -= 2;
	var scale2 = glyphConfig.getScale(scale);
	var g_size = glyphConfig.getSize()*scale2;
	topx += 8;

	//var start_y = topy - scale2*g_size;
	//var start_x = topx;

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
		//console.log("shape: " + shape + " " + navicell.shapes[shape]);
		//console.log("color: " + color);
		//console.log("size: " + size);
		var start_x = topx + (size*g_size)/8; // must depends on scale2 also
		//var start_y = topy - (size*g_size)/8; // must depends on scale2 also
		var start_y = topy; // must depends on scale2 also
		bound = draw_glyph_perform(context, start_x, start_y, shape, color, (size*g_size)/4, 1, false);
	}
	if (bound) {
		//console.log("bound: " + bound[0] + " " + bound[1] + " " + bound[2] + " " + bound[3]);
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

function draw_glyph_perform(context, pos_x, pos_y, shape, color, size, scale, is_middle) {
	if (!context) {
		return;
	}

	//size *= 2.;
	size *= 1.5;
	shape = navicell.shapes[shape];

	context.fillStyle = "#" + color;

	var dim = size*scale*1.;
	var dim2 = dim/2.;

	if (!is_middle) {
		pos_y -= dim2 + 1;
	}
	if (shape == 'Square') {
		fillStrokeRect(context, pos_x-dim2, pos_y-dim2, dim, dim);
		return [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Rectangle') {
		var dim_w = 2*size*scale;
		var dim_h = size*scale;
		fillStrokeRect(context, pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h);
		return [pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h];
	} else if (shape == 'Diamond') {
		context.beginPath();
		context.moveTo(pos_x-dim2, pos_y);
		context.lineTo(pos_x, pos_y-dim2);
		context.lineTo(pos_x+dim2, pos_y);
		context.lineTo(pos_x, pos_y+dim2);
		context.closePath();
		fillStroke(context);
		return [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Circle') {
		context.beginPath();
		context.arc(pos_x, pos_y, dim2, 0., Math.PI*2);
		context.closePath();
		fillStroke(context);
		return [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Triangle') {
		var side = dim/Math.sqrt(3.);
		context.beginPath();
		context.moveTo(pos_x-side, pos_y+dim2);
		context.lineTo(pos_x, pos_y-dim2);
		context.lineTo(pos_x+side, pos_y+dim2);
		context.closePath();
		fillStroke(context);
		return [pos_x-dim2, pos_y-dim2, dim, dim];
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
		return [pos_x-dim2, pos_y-dim2, dim, dim];
	}
	return null;
}
 
function show_search_dialog()
{
	$("#search_dialog").dialog("open");
}

//
// -------------------------------------------------------------------------------
//

if (0) {
function update_heatmap_config(doc, params) {
	// TBD:
	/* this presentation is absolutely not good because:
	   - this presentation is far from the final heatmap
	   - in this way, the order is not defined
	   
	   it should be as follows:
	   - a matrix of:
	      - (upper line) X == array of 'select', each one to choose a sample or a group
    	      - (left column) Y == array of 'select', each one to choose a datatable
	  - the apply button will check about duplicata
	  - must add a clear button
	  - the number of select in X == #samples + #groups
	  - the number of select in Y == #datatables

	  may add a "Simulate on gene [select a gene]"

	  ok, continue to think a little bit

	  BUT, don't delete this code, maybe used for barplot or piechart (waiting for a minimal description about these 2 charts)
	*/

	var datatable_table = $("#heatmap_config_datatable_list");
	var sample_table = $("#heatmap_config_sample_list");
	var group_table = $("#heatmap_config_group_list");
	var heatmapConfig = navicell.drawing_config.heatmap_config;

	var datatable_cnt = mapSize(navicell.dataset.datatables);
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);

	datatable_table.children().remove();
	var html = "<thead><th>&nbsp;</th><th>+/-</th></thead>";
	html += "<tbody>";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		html += "<tr>";
		html += "<td><input type='checkbox' id='heatmap_config_cb_datatable_" + datatable.getId() + "' " + (heatmapConfig.hasDatatable(datatable) ? "checked" : "") + "></input></td>";
		html += "<td>" + datatable_name + "</td>";
		html += "</tr>";
	}

	html += "</tbody>";
	datatable_table.append(html);
	datatable_table.tablesorter();

	sample_table.children().remove();

	html = "<thead><th>&nbsp;</th><th>+/-</th></thead>";
	html += "<tbody>";
	for (var sample_name in navicell.dataset.samples) {
		var sample = navicell.dataset.samples[sample_name];
		html += "<tr>";
		html += "<td><input type='checkbox' id='heatmap_config_cb_sample_" + sample.getId() + "' " + (heatmapConfig.hasSample(sample) ? "checked" : "") + "></input></td>";
		html += "<td>" + sample_name + "</td>";
		html += "</tr>";
	}

	html += "</tbody>";
	sample_table.append(html);
	sample_table.tablesorter();

	group_table.children().remove();
	html = "<thead><th>&nbsp;</th><th>+/-</th></thead>";
	html += "<tbody>";

	for (var group_name in navicell.group_factory.group_map) {
		var group = navicell.group_factory.group_map[group_name];
		html += "<tr>";
		html += "<td><input type='checkbox' id='heatmap_config_cb_group_" + group.getId() + "' " + (heatmapConfig.hasGroup(group) ? "checked" : "") + "></input></td>";
		html += "<td>" + group_name + "</td>";
		html += "</tr>";
	}

	html += "</tbody>";
	group_table.append(html);

	group_table.tablesorter();
}
}

//
// -------------------------------------------------------------------------------
//

/*
			if (0) {
				str += "<td><select id='group_method_" + datatable.getId() + "_" + group.getId() + "' onchange='set_group_method(" + datatable.getId() + "," + group.getId() + ")'>\n";
				var method = group.getMethod(datatable);
				var selected;
				if (datatable.biotype.isContinuous()) {
					selected = (method == Group.CONTINUOUS_AVERAGE) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_AVERAGE + "'" + selected + ">Average</option>\n";
					selected = (method == Group.CONTINUOUS_MINVAL) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_MINVAL + "'" + selected + ">Min Value</option>\n";

					selected = (method == Group.CONTINUOUS_MAXVAL) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_MAXVAL + "'" + selected + ">Max Value</option>\n";

					selected = (method == Group.CONTINUOUS_ABS_AVERAGE) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_ABS_AVERAGE + "'" + selected + ">Average Absolute Values</option>\n";

					selected = (method == Group.CONTINUOUS_ABS_MINVAL) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_ABS_MINVAL + "'" + selected + ">Min Absolute Value</option>\n";

					selected = (method == Group.CONTINUOUS_ABS_MAXVAL) ? " selected" : "";
					str += "<option value='" + Group.CONTINUOUS_ABS_MAXVAL + "'" + selected + ">Max Absolute Value</option>\n";
				} else {
					var values = datatable.getDiscreteValues();
					for (var idx in values) {
						var value = values[idx];
						var label = value == '' ? 'NA' : value;
						if (value == '') {
							selected = (method == value+'-') ? " selected" : "";
							str += "<option value='" + value + "-'" + selected + ">At least one non " + label + "</option>";
						}
						selected = (method == value+'+') ? " selected" : "";
						str += "<option value='" + value + "+'" + selected + ">At least one " + label + "</option>";
						selected = (method == value+'@') ? " selected" : "";
						str += "<option value='" + value + "@'" + selected + ">All are " + label + "</option>";
					}
				}
				str += "</select></td>";
			}
*/
