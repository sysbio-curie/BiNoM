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


$(function() {
	//$("#marker_checkboxes").tabs();
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

	var win = window;

	function error_message(error) {
		status.html("<span class=\"error-message\">" + error + "</span>");
	}

	function status_message(message) {
		status.html("<span class=\"status-message\">" + message + "</span>");
	}

	$("#dt_import_dialog" ).dialog({
		autoOpen: false,
		width: 450,
		height: 780,
		modal: false,
		buttons: {
			"Import": function() {
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
					// file_elem <=> document.getElementById("dt_import_file");
					//var datatable = new Datatable(navicell.dataset, type.val(), name.val(), file_elem.files[0]);
					var datatable = navicell.dataset.readDatatable(type.val(), name.val(), file_elem.files[0]);
					// test
					//readfile(file_elem.files[0], all_maps);
					// eo test
					if (datatable.error) {
						error_message(datatable.error);
					} else {
						status_message("Importing...");

						datatable.ready.then(function() {
							if (datatable.error) {
								error_message(datatable.error);
							} else {
								status_message("Import Successful<br/>Genes: " + mapSize(datatable.gene_index) + "<br/>" + "Samples: " + mapSize(datatable.sample_index));
								navicell.annot_factory.sync();
								var display_graphics;
								if (import_display_barplot.attr('checked')) {
									$("#drawing_config_chart_type").val('Barplot');
									navicell.drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
									var barplotConfig = navicell.drawing_config.editing_barplot_config;
									barplotConfig.setDatatableAt(0, datatable);
									barplot_sample_action('allsamples');

									barplot_editor_apply(navicell.drawing_config.barplot_config);
									barplot_editor_apply(navicell.drawing_config.editing_barplot_config);
									barplot_editor_set_editing(false);

									display_graphics = true;
								} else if (import_display_heatmap.attr('checked')) {
									$("#drawing_config_chart_type").val('Heatmap');
									navicell.drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
									var heatmapConfig = navicell.drawing_config.editing_heatmap_config;
									var datatable_cnt = mapSize(navicell.dataset.datatables);
									for (var idx = 0; idx < datatable_cnt; ++idx) {
										if (!heatmapConfig.getDatatableAt(idx)) {
											break;
										}
									}
									heatmapConfig.setDatatableAt(idx, datatable);
									heatmap_sample_action('allsamples');

									heatmap_editor_apply(navicell.drawing_config.heatmap_config);
									heatmap_editor_apply(navicell.drawing_config.editing_heatmap_config);
									heatmap_editor_set_editing(false);
									display_graphics = true;
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
			}
		}});

	$("#dt_status_tabs").dialog({
		autoOpen: false,
		width: 700,
		height: 800,
		modal: false
	});

	$("#drawing_config_div").dialog({
		autoOpen: false,
		width: 420,
		height: 830,
		modal: false,

		buttons: {
			"Apply": function() {
				navicell.drawing_config.setDisplayMarkers($("#drawing_config_marker_display").attr("checked"));
				navicell.drawing_config.setDisplayOldMarkers($("#drawing_config_old_marker").val());
				navicell.drawing_config.setDisplayCharts($("#drawing_config_chart_display").attr("checked"), $("#drawing_config_chart_type").val());
				//jQuery.jstree._reference(jtree).jstree("check_all");
				//jtree.jstree("check_all");
				//jtree.jstree("uncheck_node", $("li.navicell"));
				//jtree.jstree("uncheck_node", $(".jstree-checked"));
				navicell.drawing_config.setDisplayGlyphs($("#drawing_config_glyph_display").attr("checked"));
				navicell.drawing_config.setDisplayDLOsOnAllGenes($("#drawing_config_display_all").attr("checked"));
				navicell.drawing_config.sync();
				set_old_marker_mode($("#drawing_config_old_marker").val());
				clickmap_refresh(true);
				drawing_editing(false);
			},

			"Cancel": function() {
				$("#drawing_config_marker_display").attr("checked", navicell.drawing_config.displayMarkers());
				$("#drawing_config_chart_display").attr("checked", navicell.drawing_config.displayCharts() ? true : false);
				if (navicell.drawing_config.displayCharts()) {
					$("#drawing_config_chart_type").val(navicell.drawing_config.displayCharts());
				}
				$("#drawing_config_old_marker").val(navicell.drawing_config.displayOldMarkers());
				$("#drawing_config_display_all").attr("checked", navicell.drawing_config.displayDLOsOnAllGenes());
				drawing_editing(false);
			}
		}
	});

	$("#import_dialog").button().click(function() {
		$("#dt_import_dialog").dialog("open");
	});

	$("#dt_sample_annot").dialog({
		autoOpen: false,
		width: 800,
		height: 750,
		modal: false,
		buttons: {
			Add: function() {
				var file_elem = sample_file.get()[0];
				navicell.annot_factory.readfile(file_elem.files[0]);
				navicell.annot_factory.ready.then(function() {
					var status = $("#dt_sample_annot_status");
					status.html(navicell.annot_factory.sample_read + " samples read<br/>" + navicell.annot_factory.sample_annotated + " samples annotated");
					update_status_tables();
				});
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
	//
	update_sample_annot_table(window.document);

	$("#heatmap_editor_div" ).dialog({
		autoOpen: false,
		width: 750,
		height: 500,
		modal: false,
		buttons: {
			"Apply": function() {
				// heatmap_editor
				console.log("HEATMAP_EDITOR_DIV: " + document.map_name);
				heatmap_editor_apply(navicell.drawing_config.heatmap_config);
				// instead of calling this function again, it
				// should be better to copy heatmap_config to
				// editing_heatmap_config
				heatmap_editor_apply(navicell.drawing_config.editing_heatmap_config);
				navicell.drawing_config.heatmap_config.shrink();
				navicell.drawing_config.editing_heatmap_config.shrink();
				update_status_tables();
				heatmap_editor_set_editing(false, undefined, document.map_name);
				clickmap_refresh(true);
			},
			
			"Clear": function() {
				//navicell.drawing_config.heatmap_config.reset();
				navicell.drawing_config.editing_heatmap_config.reset();
				update_status_tables();
				heatmap_editor_set_editing(true, undefined, document.map_name);
			},

			"Cancel": function() {
				navicell.drawing_config.editing_heatmap_config.cloneFrom(navicell.drawing_config.heatmap_config);
				update_status_tables();
				heatmap_editor_set_editing(false, undefined, document.map_name);
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
				// barplot_editor
				barplot_editor_apply(navicell.drawing_config.barplot_config);
				// instead of calling this function again, it
				// should be better to copy barplot_config to
				// editing_barplot_config
				barplot_editor_apply(navicell.drawing_config.editing_barplot_config);
				navicell.drawing_config.barplot_config.shrink();
				navicell.drawing_config.editing_barplot_config.shrink();
				update_status_tables();
				barplot_editor_set_editing(false);
				clickmap_refresh(true);
			},
			
			"Clear": function() {
				//navicell.drawing_config.barplot_config.reset();
				navicell.drawing_config.editing_barplot_config.reset();
				update_status_tables();
				barplot_editor_set_editing(true);
			},

			"Cancel": function() {
				navicell.drawing_config.editing_barplot_config.cloneFrom(navicell.drawing_config.barplot_config);
				update_status_tables();
				barplot_editor_set_editing(false);
			}
		}
	});


	$("#glyph_editor_div" ).dialog({
		autoOpen: false,
		width: 750,
		height: 660,
		modal: false,
		buttons: {
			"Apply": function() {
				// glyph_editor
				glyph_editor_apply(navicell.drawing_config.glyph_config);
				// instead of calling this function again, it
				// should be better to copy glyph_config to
				// editing_glyph_config
				glyph_editor_apply(navicell.drawing_config.editing_glyph_config);
				update_status_tables();
				glyph_editor_set_editing(false);
				clickmap_refresh(true);
			},
			
			"Clear": function() {
				navicell.drawing_config.glyph_config.reset();
				update_status_tables();
				glyph_editor_set_editing(true);
			},

			"Cancel": function() {
				navicell.drawing_config.editing_glyph_config.cloneFrom(navicell.drawing_config.glyph_config);
				update_status_tables();
				glyph_editor_set_editing(false);
			}
		}
	});

});

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
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		var val = $("#heatmap_editor_gs_" + idx).val();
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
		var val = $("#heatmap_editor_datatable_" + idx).val();
		if (val == "_none_") {
			heatmap_config.setDatatableAt(idx, undefined);
		} else {
			//console.log("datatable selected " + val + " at index " + idx);
			var datatable = navicell.getDatatableById(val);
			heatmap_config.setDatatableAt(idx, datatable);
		}
	}
	heatmap_config.setSize($("#heatmap_editor_size").val());
	heatmap_config.setScaleSize($("#heatmap_editor_scale_size").val());
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

function glyph_editor_apply(glyph_config)
{
	var val = $("#glyph_editor_gs").val();
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

	val = $("#glyph_editor_datatable_shape").val();
	if (val == "_none_") {
		glyph_config.setShapeDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setShapeDatatable(datatable);
	}

	val = $("#glyph_editor_datatable_color").val();
	if (val == "_none_") {
		glyph_config.setColorDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setColorDatatable(datatable);
	}

	val = $("#glyph_editor_datatable_size").val();
	if (val == "_none_") {
		glyph_config.setSizeDatatable(undefined);
	} else {
		var datatable = navicell.getDatatableById(val);
		glyph_config.setSizeDatatable(datatable);
	}

	glyph_config.setSize($("#glyph_editor_size").val());
	glyph_config.setScaleSize($("#glyph_editor_scale_size").val());
}

function update_sample_status_table(doc, params) {
	var table = $("#dt_sample_status_table", doc);
	table.children().remove();
	// should use a string buffer
	var str = "<thead>";
	str += "<tr><td>&nbsp</td><td colspan='" + mapSize(navicell.dataset.datatables) + "'>&nbsp;#Genes&nbsp;in&nbsp;</td</tr>";
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
	update_status_tables(doc, {style_only: true, annot_id: annot_id, checked: checked});

	//update_sample_annot_table_group(annot_id, checked, doc);
		/*
	var td_tochange = $("#dt_sample_annot_table .annot_" + annot_id);
	if (checked) {
		td_tochange.removeClass('non_group_annot');
		td_tochange.addClass('group_annot');
	} else {
		td_tochange.addClass('non_group_annot');
		td_tochange.removeClass('group_annot');
	}
*/
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
	var table = $("#dt_sample_annot_table", doc);
	table.children().remove();
	var str = "<thead>";
	var annots = navicell.annot_factory.annots_per_name;
	var annot_cnt = mapSize(annots);
	str += '<tr><td style="text-align: center; font-size: smaller; font-style: italic;">' + (annot_cnt ? 'Use as group' : '&nbsp;') + '</td>';
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
	//table.tablesorter({debug: true, selectorHeaders: 'thead th'});
	table.tablesorter();
}

function set_group_method(datatable_id, group_id) {
	var obj = $("#group_method_" + datatable_id + "_" + group_id);
	var group = navicell.group_factory.getGroupById(group_id);
	var datatable = navicell.getDatatableById(datatable_id);

	group.setMethod(datatable, obj.val());
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
		str += "<th>&nbsp;#Samples&nbsp;</th><td>Method</td>";
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
			str += "<td>";
			var cnt = 0;
			for (var sample_name in navicell.dataset.samples) {
				var sample = navicell.dataset.samples[sample_name];
				if (datatable.sample_index[sample_name] != undefined) {
					for (var sample_group_name in sample.groups) {
						if (sample_group_name == group_name) {
							cnt++;
						}
					}
				}
			}
			str += cnt + "</td>";

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
			} else {
				var values = datatable.getDiscreteValues();
				for (var value in values) {
					var label = value == '' ? 'empty' : value;
					selected = (method == value+'+') ? " selected" : "";
					str += "<option value='" + value + "+'" + selected + ">At least one " + label + "</option>";
					selected = (method == value+'@') ? " selected" : "";
					str += "<option value='" + value + "@'" + selected + ">All are " + label + "</option>";
				}
			}
			str += "</select></td>";
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
	var module_name = doc ? doc.navicell_module_name : "";
	table.children().remove();
	// should use a string buffer
	var str = "<thead>";
	str += "<tr><td>&nbsp</td><td>&nbsp</td><td colspan='" + mapSize(navicell.dataset.datatables) + "'>&nbsp;#Samples&nbsp;in&nbsp;</td</tr>";
	str += "<tr><th>Genes&nbsp;(" + mapSize(navicell.dataset.genes) + ")</th>";
	str += "<th>In&nbsp;" + module_name + "&nbsp;(" + in_module_gene_count(module_name) + ")</th>";

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
		if (navicell.mapdata.hugo_map[gene_name][module_name]) {
			str += "<td>" + gene_name + "</td>";
		} else {
			str += "<td>&nbsp;</td>";
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
	//console.log("table: " + table.html());
	//console.log("table_str: " + str);
	table.append(str);
	table.tablesorter();
}

function update_datatable_status_table(doc, params) {
	if (!navicell.DTStatusMustUpdate && (!params || !params.force)) {
		return;
	}
	//console.log("update_datatable_status_table");
	var table = $("#dt_datatable_status_table", doc);
	var update_label = $("#dt_datatable_status_update_label", doc);
	var update_checkbox = $("#dt_datatable_status_update", doc);
	if (doc != window.document) {
		update_checkbox.attr("checked", false);
	}
	var update = update_checkbox.attr("checked");
	var support_remove = true;

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
	table.children().remove();

	var tab_body = $("#dt_datatable_tabs");
	var tab_header = $("#dt_datatable_tabs ul");
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
	str += "</tr></thead>";

	str += "<tbody>";
	var tabnum = 1;
	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		var data_div = datatable.data_div;
		if (data_div) {
			tab_header.append('<li><a class="ui-button-text" href="#' + data_div.attr("id") + '">' + dt_name + '</a></li>');
			datatable.setTabNum(tabnum++);
		}
		str += "<tr>";
		if (update) {
			if (support_remove) {
				str += "<td><input id=\"dt_remove_" + datatable.getId() + "\" type=\"checkbox\"></td>";
			}
			str += "<td><input id=\"dt_name_" + datatable.getId() + "\" type=\"text\" value=\"" + datatable.name + "\"/></td>";
			str += "<td>" + get_biotype_select("dt_type_" + datatable.getId(), false, datatable.biotype.name) + "</td>";
		} else {
			str += "<td>&nbsp;" + datatable.name + "&nbsp;</td>";
			str += "<td style='min-width: 170px'>&nbsp;" + datatable.biotype.name + "&nbsp;</td>";
		}
		str += "<td>" + mapSize(datatable.gene_index) + "</td>";
		str += "<td>" + mapSize(datatable.sample_index) + "</td>";
		if (!update) {
			str += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='navicell.getDatatableById(" + datatable.getId() + ").showDisplayConfig()'>display configuration</a></td>";
		}
		str += "</tr>";
	}
	table.append(str);
	table.tablesorter();

	tab_body.tabs("refresh");
	navicell.DTStatusMustUpdate = false;
}

function update_status_tables(params) {
	//navicell.annot_factory.sync();
	//navicell.group_factory.buildGroups();
	for (var map_name in maps) {
		var doc = maps[map_name].document;
		update_sample_status_table(doc, params);
		update_gene_status_table(doc, params);
		update_group_status_table(doc, params);
		update_module_status_table(doc, params);
		update_datatable_status_table(doc, params);
		update_sample_annot_table(doc, params);
		update_heatmap_editor(doc, params);
		update_barplot_editor(doc, params);
		update_glyph_editor(doc, params);
	}
	//navicell_session.write();
}

function get_biotype_select(id, include_none, value) {
	var str = '<select id="' + id + '">';
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
	for (var dt_name in navicell.dataset.datatables) {
		// TBD: do not support space in names: check all selector
		var datatable = navicell.dataset.datatables[dt_name];
		var dt_id = datatable.getId();
		var dt_name_elem = $("#dt_name_" + dt_id);
		var dt_type_elem = $("#dt_type_" + dt_id);
		var dt_remove_elem = $("#dt_remove_" + dt_id);
		var new_dt_name = dt_name_elem.val();
		var new_dt_type = dt_type_elem.val();
		var new_dt_remove = dt_remove_elem.attr('checked');
		if (new_dt_remove) {
			navicell.dataset.removeDatatable(datatable);
			update_cnt++;
		} else {
			if ((new_dt_name && dt_name !== new_dt_name) ||
			    new_dt_type !==  datatable.biotype.name) {
				if (!navicell.dataset.updateDatatable(dt_name, new_dt_name, new_dt_type)) {
					dt_name_elem.val(dt_name);
					dt_type_elem.val(datatable.biotype.name);
					
				} else {
					update_cnt++;
				}
				
			}
		}

	}
	if (update_cnt) {
		var update = $("#dt_datatable_status_update");
		update.attr('checked', false);
		update.text('Unlock Datatables');
		update_datatable_status_table(null, {force: true});
		update_status_tables();
	}
}

function cancel_datatables() {
	update_datatable_status_table(null, {force: true});
}

Datatable.prototype.showDisplayConfig = function(doc) {
	var div_id = undefined;
	var displayConfig = this.getDisplayConfig();
	if (displayConfig) {
		div_id = displayConfig.div_id;
	}
	//console.log("showDisplayConfig: " + div_id + " " + (doc ? doc.map_name : ""));
	if (div_id) {
		var div = $("#" + div_id, doc);
		var datatable_id = this.getId();
		//console.log("div.length: " + div.length);
		div.dialog({
			autoOpen: false,
			width: 700,
			height: 550,
			modal: false,

			buttons: {
				"Apply": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayStepConfig = datatable.displayStepConfig;
					var displayDiscreteConfig = datatable.displayDiscreteConfig;
					if (displayStepConfig) {
						var prev_value = datatable.minval;
						var error = 0;
						var step_cnt = displayStepConfig.getStepCount();
						for (var idx = 0; idx < step_cnt; ++idx) {
							var value;
							if (idx == step_cnt-1) {
								value = datatable.maxval;
							} else {
								value = $("#step_value_" + datatable_id + "_" + idx, doc).val();
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
									value = $("#step_value_" + datatable_id + "_" + idx, doc).val();
								}
								var color = $("#step_config_" + datatable_id + "_" + idx, doc).val();
								var size = $("#step_size_" + datatable_id + "_" + idx, doc).val();
								var shape = $("#step_shape_" + datatable_id + "_" + idx, doc).val();
								displayStepConfig.setStepInfo(idx, value, color, size, shape);
							}
							display_step_config_set_editing(datatable_id, false);
						}
					}
					if (displayDiscreteConfig) {
						var value_cnt = displayDiscreteConfig.getValueCount();
						for (var idx = 0; idx < value_cnt; ++idx) {
							var color = $("#discrete_config_" + datatable_id + "_" + idx, doc).val();
							var size = $("#discrete_size_" + datatable_id + "_" + idx, doc).val();
							var shape = $("#discrete_shape_" + datatable_id + "_" + idx, doc).val();
							displayDiscreteConfig.setValueInfo(idx, color, size, shape);
						}
						display_discrete_config_set_editing(datatable_id, false);
					}
					datatable.refresh();
					update_status_tables();
					clickmap_refresh(true);
				},

				"Cancel": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayStepConfig = datatable.displayStepConfig;
					var displayDiscreteConfig = datatable.displayDiscreteConfig;
					if (displayStepConfig) {
						displayStepConfig.update();
						display_step_config_set_editing(datatable_id, false);
					}
					if (displayDiscreteConfig) {
						displayDiscreteConfig.update();
						display_discrete_config_set_editing(datatable_id, false);
					}
				}
			}
		});
		div.dialog("open");
	}
}

var EDITING_CONFIGURATION = "configuration not saved...";

function display_step_config_set_editing(datatable_id, val) {
	//console.log("display_step_config_set_editing(" + val + ") " + $("#step_config_editing_" + datatable_id).length);
	$("#step_config_editing_" + datatable_id).html(val ? EDITING_CONFIGURATION : "");
}

function display_discrete_config_set_editing(datatable_id, val) {
	$("#discrete_config_editing_" + datatable_id).html(val ? EDITING_CONFIGURATION : "");
}

function drawing_config_chart() {
	var val = $("#drawing_config_chart_type").val();
	if (val == "Heatmap") {
		$("#heatmap_editor_div").dialog("open");
	} else if (val == "Barplot") {
		$("#barplot_editor_div").dialog("open");
	}
}

function drawing_config_glyph() {
	$("#glyph_editor_div").dialog("open");
}

function drawing_editing(val) {
	$("#drawing_editing").html(val ? EDITING_CONFIGURATION : "");
}

function heatmap_editor_set_editing(val, idx, map_name) {
	//console.log("heatmap_editor_set_editing: " + map_name);
	var doc = (map_name && maps ? maps[map_name].document : null);
	$("#heatmap_editing").html(val ? EDITING_CONFIGURATION : "");
	if (val) {
		heatmap_editor_apply(navicell.drawing_config.editing_heatmap_config);
		update_heatmap_editor(doc, null, navicell.drawing_config.editing_heatmap_config);
	}
	// ok
	if (idx != undefined && $("#heatmap_editor_datatable_" + idx).val() != '_none_') {
		$("#heatmap_editor_datatable_config_" + idx).removeClass("zz-hidden");	
	} else {
		$("#heatmap_editor_datatable_config_" + idx).addClass("zz-hidden");	
	}
}

// 2013-09-03 TBD: must add a doc argument: but as this function is called
// from a string evaluation (onchange='step_display_config(...)'), I propose
// to give the doc_idx and get the doc value from an associative array.
// => should maintain an associative array: doc_idx -> doc
// + attribute doc.doc_idx
function heatmap_step_display_config(idx, map_name) {
	//console.log("heatmap_step_display_config: " + map_name);
	var doc = (map_name && maps ? maps[map_name].document : null);
	var val = $("#heatmap_editor_datatable_" + idx, doc).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			datatable.showDisplayConfig(doc);
		}
	}
}

function heatmap_sample_action(action) {
	//console.log("action [" + action + "]");
	if (action == "clear") {
		navicell.drawing_config.editing_heatmap_config.reset(true);
		max_heatmap_sample_cnt = 0;
	} else if (action == "allsamples") {
		max_heatmap_sample_cnt = navicell.drawing_config.editing_heatmap_config.setAllSamples();
	} else if (action == "allgroups") {
		max_heatmap_sample_cnt = navicell.drawing_config.editing_heatmap_config.setAllGroups();
	}
	/*if (max_heatmap_sample_cnt < DEF_MAX_HEATMAP_SAMPLE_CNT)*/ {
		max_heatmap_sample_cnt = DEF_MAX_HEATMAP_SAMPLE_CNT;
	}
	update_status_tables();
	heatmap_editor_set_editing(true, undefined, document.map_name);
}

function update_heatmap_editor(doc, params, heatmapConfig) {
	if (!heatmapConfig) {
		//heatmapConfig = navicell.drawing_config.heatmap_config;
		heatmapConfig = navicell.drawing_config.editing_heatmap_config;
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#heatmap_editor_div", doc);
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
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
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "heatmap_clear_samples", "heatmap_sample_action(\"clear\")") + "&nbsp;&nbsp;&nbsp;";
	html += make_button("All samples", "heatmap_all_samples", "heatmap_sample_action(\"allsamples\")") + "</td>";
	html += "<td style='" + empty_cell_style + "'>" + make_button("All groups", "heatmap_all_groups", "heatmap_sample_action(\"allgroups\")") +  "</td></tr>";
	html += "<tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td>";
//	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>" + make_button("All samples", "heatmap_all_samples", "heatmap_all_samples()") + "</td></tr><tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td></tr>";

	//html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>Datatables</td>";
//	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>" + make_button("Clear Samples", "heatmap_clear_samples", "heatmap_clear_samples()") + "&nbsp;" + make_button("All samples", "heatmap_all_samples", "heatmap_all_samples()") + "</td>";

	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>Datatables</td>";
	//html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='font-weight: bold; font-size: smaller; text-align: center'>" + + "</td>";

	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='heatmap_editor_gs_" + idx + "' onchange='heatmap_editor_set_editing(true, undefined, \"" + map_name + "\")'>\n";
		html += "<option value='_none_'>Choose a group or sample</option>\n";
		var sel_group = heatmapConfig.getGroupAt(idx);
		var sel_sample = heatmapConfig.getSampleAt(idx);
		for (var group_name in navicell.group_factory.group_map) {
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>";
		}
		var sel_sample = heatmapConfig.getSampleAt(idx);
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
			html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample.name + "</option>";
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
			var gene_name = sel_gene.name;
			for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
				var sel_group = heatmapConfig.getGroupAt(idx2);
				var sel_sample = heatmapConfig.getSampleAt(idx2);
				if (sel_sample) {
					var value = sel_datatable.getValue(sel_sample.name, gene_name);
					var style = sel_datatable.getStyle(value);
					html += "<td class='heatmap_cell' " + style + ">" + value + "</td>";
				} else if (sel_group) {
					var value = sel_group.getValue(sel_datatable, gene_name);
					//console.log("sel_group.getValue() -> " + value);
					if (value != undefined) {
						var style = sel_datatable.getStyle(value);
						html += "<td class='heatmap_cell' " + style + ">" + value + "</td>";
					} else {
						html += "<td class='heatmap_cell'>&nbsp;</td>";
					}
				} else {
					html += "<td class='heatmap_cell'>&nbsp;</td>";
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
	html += "<select id='heatmap_select_gene' onchange='update_heatmap_editor(null, null, navicell.drawing_config.editing_heatmap_config)'>\n";
	/*html += "<select id='heatmap_select_gene'>\n";*/
	html += "<option value='_none_'></option>\n";
	for (var gene_name in navicell.dataset.genes) {
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

function draw_heatmap(overlay, context, scale, gene_name, topx, topy)
{
	var heatmapConfig = navicell.drawing_config.heatmap_config;
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
	var start_y = topy;

	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var start_x = topx;
		var sel_datatable = heatmapConfig.getDatatableAt(idx);
		if (!sel_datatable) {
			continue;
		}
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = heatmapConfig.getGroupAt(idx2);
			var sel_sample = heatmapConfig.getSampleAt(idx2);
			if (sel_sample) {
				var value = sel_datatable.getValue(sel_sample.name, gene_name);
				//console.log("has a sample");
				//var style = sel_datatable.getStyle(value);
				var bg = sel_datatable.getBG(value);
				if (bg) {
					var fg = getFG_from_BG(bg);
					//console.log("value: " + value + " bg:" + bg + " fg: " + fg);
					context.fillStyle = "#" + bg;
					fillStrokeRect(context, start_x, start_y, cell_w, cell_h);
					//context.fillRect(start_x, start_y, cell_w, cell_h);
					//context.lineWidth = 1;
					//context.strokeRect(start_x, start_y, cell_w, cell_h);
					/*
					context.fillStyle = "#000000";
					context.lineWidth = 1;
					context.strokeWidth = 1;
					context.strokeStyle = "#000000";
					context.beginPath();
					context.moveTo(start_x, start_y);
					context.lineTo(start_x+cell_w, start_y);
					context.lineTo(start_x+cell_w, start_y+cell_h);
					context.lineTo(start_x, start_y+cell_h);
					context.closePath();
					context.stroke();
					*/
					start_x += cell_w;
				}
			} else if (sel_group) {
				var value = sel_group.getValue(sel_datatable, gene_name);
				if (value != undefined) {
					var bg = sel_datatable.getBG(value);
					//console.log("group value: " + value + " " + bg);
					if (bg) {
						var fg = getFG_from_BG(bg);
						context.fillStyle = "#" + bg;
						fillStrokeRect(context, start_x, start_y, cell_w, cell_h);
					}
				}
				start_x += cell_w;
			}
		}
		start_y += cell_h;
	}
	overlay.addBoundBox([topx, topy, start_x-topx, start_y-topy], gene_name, "heatmap");
}

function barplot_editor_set_editing(val, idx) {
	$("#barplot_editing").html(val ? EDITING_CONFIGURATION : "");
	//$("#gene_choice").css("visibility", val ? "hidden" : "visible");
	if (val) {
		barplot_editor_apply(navicell.drawing_config.editing_barplot_config);
		update_barplot_editor(null, null, navicell.drawing_config.editing_barplot_config);
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
function barplot_step_display_config(idx) {
	var val = $("#barplot_editor_datatable_" + idx).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			datatable.showDisplayConfig();
		}
	}
}

function barplot_sample_action(action) {
	//console.log("action [" + action + "]");
	if (action == "clear") {
		navicell.drawing_config.editing_barplot_config.reset(true);
		max_barplot_sample_cnt = 0;
	} else if (action == "allsamples") {
		max_barplot_sample_cnt = navicell.drawing_config.editing_barplot_config.setAllSamples();
	} else if (action == "allgroups") {
		max_barplot_sample_cnt = navicell.drawing_config.editing_barplot_config.setAllGroups();
	}
	/*if (max_barplot_sample_cnt < DEF_MAX_BARPLOT_SAMPLE_CNT)*/ {
		max_barplot_sample_cnt = DEF_MAX_BARPLOT_SAMPLE_CNT;
	}
	update_status_tables();
	barplot_editor_set_editing(true, undefined, document.map_name);
}

function update_barplot_editor(doc, params, barplotConfig) {
	//console.log("updating barplot_editor");
	if (!barplotConfig) {
		barplotConfig = navicell.drawing_config.editing_barplot_config;
	}
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

	// ++++++
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td><td style='" + empty_cell_style + "'>&nbsp;</td><td colspan='1' style='" + empty_cell_style + "'>" + make_button("Clear Samples", "barplot_clear_samples", "barplot_sample_action(\"clear\")") + "&nbsp;&nbsp;&nbsp;";
	html += make_button("All samples", "barplot_all_samples", "barplot_sample_action(\"allsamples\")") + "</td>";
	html += "<td style='" + empty_cell_style + "'>" + make_button("All groups", "barplot_all_groups", "barplot_sample_action(\"allgroups\")") +  "</td></tr>";
	html += "<tr><td style='" + empty_cell_style + " height: 10px'>&nbsp;</td>";
	// ++++++

	// -----
	var idx = 0;
	var sel_datatable = barplotConfig.getDatatableAt(idx);
	html += "<tr>";
	// ----
	// ----
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	var MAX_BARPLOT_HEIGHT = 100.;
	if (sel_gene && sel_datatable) {
		var gene_name = sel_gene.name;
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = barplotConfig.getGroupAt(idx2);
			var sel_sample = barplotConfig.getSampleAt(idx2);
			if (sel_sample) {
				var value = sel_datatable.getValue(sel_sample.name, gene_name);
				var style = sel_datatable.getStyle(value);
				//height = "height: " + (100. * (ivalue-sel_datatable.minval) / (sel_datatable.maxval - sel_datatable.minval)) + "px;";
				var height = "height: " + sel_datatable.getBarplotHeight(value, MAX_BARPLOT_HEIGHT) + "px;";
				if (height) {
					style += height + "width: 100%;";
					html += "<td style='" + height + "width: 100%; vertical-align: bottom;'><table style='" + height + "width: 100%'><tr>";
				}
				html += "<td class='barplot_cell' " + style + ">&nbsp;</td>";
				if (height) {
					html += "</tr></table></td>";
				}
			} else if (sel_group) {
				var value = sel_group.getValue(sel_datatable, gene_name);
				//console.log("sel_group.getValue() -> " + value);
				if (value != undefined) {
					var style = sel_datatable.getStyle(value);
					var height = "height: " + sel_datatable.getBarplotHeight(value, MAX_BARPLOT_HEIGHT) + "px;";
					if (height) {
						style += height + "width: 100%;";
						html += "<td style='" + height + "width: 100%; vertical-align: bottom;'><table style='" + height + "width: 100%'><tr>";
					}
					html += "<td class='barplot_cell' " + style + ">&nbsp;</td>";
					if (height) {
						html += "</tr></table></td>";
					}
				} else {
					html += "<td class='barplot_cell'>&nbsp;</td>";
				}
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
	// ----

	html += "<tr>\n";
	html += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='barplot_step_display_config(" + idx + ")'><span id='barplot_editor_datatable_config_" + idx + "' class='" + (sel_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";

	html += "<td><select id='barplot_editor_datatable_" + idx + "' onchange='barplot_editor_set_editing(true," + idx + ")'>\n";
	html += "<option value='_none_'>Choose a datatable</option>\n";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
		html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>";
	}
	html += "</select></td>";

	if (sel_gene && sel_datatable) {
		var gene_name = sel_gene.name;
		for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
			var sel_group = barplotConfig.getGroupAt(idx2);
			var sel_sample = barplotConfig.getSampleAt(idx2);
			if (sel_sample) {
				var value = sel_datatable.getValue(sel_sample.name, gene_name);
				//var style = sel_datatable.getStyle(value);
				var style = "style='text-align: center;'";
				html += "<td class='barplot_cell' " + style + ">" + value + "</td>";
			} else if (sel_group) {
				var value = sel_group.getValue(sel_datatable, gene_name);
				//console.log("sel_group.getValue() -> " + value);
				if (value != undefined) {
					//var style = sel_datatable.getStyle(value);
					var style = "style='text-align: center;'";
					html += "<td class='barplot_cell' " + style + ">" + value + "</td>";
				} else {
					html += "<td class='barplot_cell'>&nbsp;</td>";
				}
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
	// ----

	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	html += "<td style='" + empty_cell_style + "'>&nbsp;</td>";
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='barplot_editor_gs_" + idx + "' onchange='barplot_editor_set_editing(true)'>\n";
		html += "<option value='_none_'>Choose a group or sample</option>\n";
		var sel_group = barplotConfig.getGroupAt(idx);
		var sel_sample = barplotConfig.getSampleAt(idx);
		for (var group_name in navicell.group_factory.group_map) {
			var group = navicell.group_factory.group_map[group_name];
			var selected = sel_group && sel_group.getId() == group.getId() ? " selected": "";
			html += "<option value='g_" + group.getId() + "'" + selected + ">" + group.name + "</option>";
		}
		var sel_sample = barplotConfig.getSampleAt(idx);
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			var selected = sel_sample && sel_sample.getId() == sample.getId() ? " selected": "";
			html += "<option value='s_" + sample.getId() + "'" + selected + ">" + sample.name + "</option>";
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
	html += "<select id='barplot_select_gene' onchange='update_barplot_editor(null, null, navicell.drawing_config.editing_barplot_config)'>\n";
	/*html += "<select id='barplot_select_gene'>\n";*/
	html += "<option value='_none_'></option>\n";
	for (var gene_name in navicell.dataset.genes) {
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

function draw_barplot(overlay, context, scale, gene_name, topx, topy)
{
	//console.log("drawing barplot");
	var barplotConfig = navicell.drawing_config.barplot_config;
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
	//topy -= cell_h * datatable_cnt + 4;
	var maxy = cell_h*4;
	//var start_y = topy - maxy - cell_h;
	var start_x = topx;
	var start_y = topy - 3;
	var idx = 0;
	var sel_datatable = barplotConfig.getDatatableAt(idx);
	if (!sel_datatable) {
		return;
	}
	for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
		var sel_group = barplotConfig.getGroupAt(idx2);
		var sel_sample = barplotConfig.getSampleAt(idx2);
		if (sel_sample) {
			var value = sel_datatable.getValue(sel_sample.name, gene_name);
			//var style = sel_datatable.getStyle(value);
			var bg = sel_datatable.getBG(value);
			if (bg) {
				var fg = getFG_from_BG(bg);
				//console.log("value: " + value + " bg:" + bg + " fg: " + fg);
				context.fillStyle = "#" + bg;

				var height = sel_datatable.getBarplotHeight(value, maxy);
				//fillStrokeRect(context, start_x, start_y+maxy-height, cell_w, height);
				fillStrokeRect(context, start_x, start_y-height, cell_w, height);
				//context.fillRect(start_x, start_y+maxy-height, cell_w, height);
				//context.strokeRect(start_x, start_y+maxy-height, cell_w, height);
				/*
				context.fillStyle = "#000000";
				context.lineWidth = 1;
				context.strokeWidth = 1;
				context.strokeStyle = "#000000";
				context.beginPath();
				context.moveTo(start_x, start_y+maxy-height);
				context.lineTo(start_x+cell_w, start_y+maxy-height);
				context.lineTo(start_x+cell_w, start_y+maxy);
				context.lineTo(start_x, start_y+maxy);
				context.closePath();
				context.stroke();
				*/
				start_x += cell_w;
			}
		} else if (sel_group) {
			var value = sel_group.getValue(sel_datatable, gene_name);
			if (value != undefined) {
				var bg = sel_datatable.getBG(value);
				//console.log("group value: " + value + " " + bg);
				if (bg) {
					var height = sel_datatable.getBarplotHeight(value, maxy);
					var fg = getFG_from_BG(bg);
					context.fillStyle = "#" + bg;
					//context.fillRect(start_x, start_y, cell_w, cell_h);
					//fillStrokeRect(context, start_x, start_y+maxy-height, cell_w, height);
					fillStrokeRect(context, start_x, start_y-height, cell_w, height);
				}
			}
			start_x += cell_w;
		}
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
	html += "<option value='_none_'>Choose a group or sample</option>\n";
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
	return html + "</select>";
}

function glyph_editor_set_editing(val, what) {
	$("#glyph_editing").html(val ? EDITING_CONFIGURATION : "");
	if (val) {
		glyph_editor_apply(navicell.drawing_config.editing_glyph_config);
		update_glyph_editor(null, null, navicell.drawing_config.editing_glyph_config);
	}
	//console.log("glyph_editor_set_editing: " + val + " " + what);
	// ok
	if (what != undefined && $("#glyph_editor_datatable_" + what).val() != '_none_') {
		$("#glyph_editor_datatable_config_" + what).removeClass("zz-hidden");	
	} else {
		$("#glyph_editor_datatable_config_" + what).addClass("zz-hidden");	
	}
}

function glyph_step_display_config(what) {
	var val = $("#glyph_editor_datatable_" + what).val();
	if (val != '_none_') {
		var datatable = navicell.getDatatableById(val);
		if (datatable) {
			datatable.showDisplayConfig();
		}
	}
}

function update_glyph_editor(doc, params, glyphConfig) {
	//console.log("updating glyph_editor");
	if (!glyphConfig) {
		glyphConfig = navicell.drawing_config.editing_glyph_config;
	}
	var map_name = doc ? doc.map_name : "";
	var div = $("#glyph_editor_div");
	var topdiv = div.parent().parent();
	var empty_cell_style = "background: " + topdiv.css("background") + "; border: none;";
	var table = $("#glyph_editor_table", doc);
	var sel_gene_id = $("#glyph_select_gene", doc).val();
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
	html += sample_or_group_select("glyph_editor_gs", 'glyph_editor_set_editing(true, "sample", "' + map_name + '")', sel_group, sel_sample);
	html += "</td>";
	html += "</tr>";
	html += "<tr><td style='" + empty_cell_style + "'>&nbsp;</td></td>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Shape</span></td>";

	html += "<td style='" + empty_cell_style + "'>";
	var sel_shape_datatable = glyphConfig.getShapeDatatable();
	html += datatable_select("glyph_editor_datatable_shape", 'glyph_editor_set_editing(true, "shape", "' + map_name + '")', sel_shape_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"shape\", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_shape' class='" + (sel_shape_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";
	html += "</tr>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Color</span></td>";

	html += "<td style='" + empty_cell_style + "'>";
	var sel_color_datatable = glyphConfig.getColorDatatable();
	html += datatable_select("glyph_editor_datatable_color", 'glyph_editor_set_editing(true, "color", "' + map_name + '")', sel_color_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"color\", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_color' class='" + (sel_color_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";

	html += "</tr>";

	html += "<tr><td style='text-align: right; " + empty_cell_style + "'><span style='font-size: small; font-weight: bold'>Size</span></td>";
	html += "<td style='" + empty_cell_style + "'>";
	var sel_size_datatable = glyphConfig.getSizeDatatable();
	html += datatable_select("glyph_editor_datatable_size", 'glyph_editor_set_editing(true, "size", "' + map_name + '")', sel_size_datatable);
	html += "</td>";

	html += "<td style='border: none; text-decoration: underline; font-size: 9px; text-align: left;" + empty_cell_style + "'><a href='#' onclick='glyph_step_display_config(\"size\", \"" + map_name + "\")'><span id='glyph_editor_datatable_config_size' class='" + (sel_size_datatable ? "" : "zz-hidden") + "'>config</span></a></td>";
	html += "</tr>";

	html += "</table>";
	html += "</td>";
	var CANVAS_W = 250;
	var CANVAS_H = 250;
	html += "<td style='width: " + CANVAS_W + "px; height: " + CANVAS_H + "px' id='glyph_editor_td_canvas' style='background: white;'></td>";
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
	var td = $("#glyph_editor_td_canvas").get(0);
	if (td) {
		td.appendChild(draw_canvas);
		context = draw_canvas.getContext('2d');

		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	if (sel_gene && sel_shape_datatable && sel_color_datatable && sel_size_datatable && (sel_group || sel_sample)) {
		var shape_value;
		var shape;
		var color_value;
		var color;
		var size_value;
		var size;
		if (sel_sample) {
			shape_value = sel_shape_datatable.getValue(sel_sample.name, sel_gene.name);
			shape = sel_shape_datatable.getDisplayConfig().getShape(shape_value);
			color_value = sel_color_datatable.getValue(sel_sample.name, sel_gene.name);
			color = sel_color_datatable.getDisplayConfig().getColor(color_value);
			size_value = sel_size_datatable.getValue(sel_sample.name, sel_gene.name);
			size = sel_size_datatable.getDisplayConfig().getSize(size_value);
		} else {
			shape_value = sel_group.getValue(sel_shape_datatable, sel_gene.name);
			shape = sel_shape_datatable.getDisplayConfig().getShape(shape_value);
			color_value = sel_group.getValue(sel_color_datatable, sel_gene.name);
			color = sel_color_datatable.getDisplayConfig().getColor(color_value);
			size_value = sel_group.getValue(sel_size_datatable, sel_gene.name);
			size = sel_size_datatable.getDisplayConfig().getSize(size_value);
		}

		//console.log("shape: " + shape_value + " " + navicell.shapes[shape]);
		//console.log("color: " + color_value + " " + color);
		//console.log("size: " + size_value + " " + size);
		draw_glyph_perform(context, CANVAS_W/2, CANVAS_H/2, shape, color, size, 8, true);
	} else if (context) {
		context.clearRect(0, 0, CANVAS_W, CANVAS_H);
	}

	html = "<table cellspacing='5'><tr><td><span style='font-size: small; font-weight: bold'>Size on Map</span></td>";
	var size = glyphConfig.getSize();

	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='glyph_editor_size' onchange='glyph_editor_set_editing(true)'>";
	html += "<option value='1'" + (size == 1 ? " selected" : "") +">Tiny</option>";
	html += "<option value='2'" + (size == 2 ? " selected" : "") +">Small</option>";
	html += "<option value='4'" + (size == 4 ? " selected" : "") +">Medium</option>";
	html += "<option value='6'" + (size == 6 ? " selected" : "") +">Large</option>";
	html += "<option value='8' " + (size == 8 ? " selected" : "") +">Very Large</option>";
	html += "</select><td>";
	html += "</tr>";

	var scale_size = glyphConfig.getScaleSize();
	html += "<tr><td>&nbsp;</td>";
	html += "<td><select id='glyph_editor_scale_size' onchange='glyph_editor_set_editing(true)'>\n";
	html += "<option value='0'" + (scale_size == 0 ? " selected" : "") +">Do not depend on scale</option>\n";
	html += "<option value='1'" + (scale_size == 1 ? " selected" : "") +">Depend on scale</option>\n";
	html += "<option value='2'" + (scale_size == 2 ? " selected" : "") +">Depend on sqrt(scale)</option>\n";
	html += "<option value='3'" + (scale_size == 3 ? " selected" : "") +">Depend on sqrt(scale)/2</option>\n";
	html += "<option value='4'" + (scale_size == 4 ? " selected" : "") +">Depend on sqrt(scale)/3</option>\n";
	html += "<option value='5'" + (scale_size == 5 ? " selected" : "") +">Depend on sqrt(scale)/4</option>\n";
	html += "</select><td>";
	html += "</tr></table>";

	$("#glyph_editor_size_div", doc).html(html);

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='glyph_select_gene' onchange='update_glyph_editor(null, null, navicell.drawing_config.editing_glyph_config)'>\n";
	html += "<option value='_none_'></option>\n";
	for (var gene_name in navicell.dataset.genes) {
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";
	$("#glyph_gene_choice", doc).html(html);
}


function draw_glyph(overlay, context, scale, gene_name, topx, topy)
{
	//console.log("drawing glyph");
	var glyphConfig = navicell.drawing_config.glyph_config;

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
		var shape_value;
		var shape;
		var color_value;
		var color;
		var size_value;
		var size;
		if (sel_sample) {
			shape_value = sel_shape_datatable.getValue(sel_sample.name, gene_name);
			shape = sel_shape_datatable.getDisplayConfig().getShape(shape_value);
			color_value = sel_color_datatable.getValue(sel_sample.name, gene_name);
			color = sel_color_datatable.getDisplayConfig().getColor(color_value);
			size_value = sel_size_datatable.getValue(sel_sample.name, gene_name);
			size = sel_size_datatable.getDisplayConfig().getSize(size_value);
		} else {
			shape_value = sel_group.getValue(sel_shape_datatable, gene_name);
			shape = sel_shape_datatable.getDisplayConfig().getShape(shape_value);
			color_value = sel_group.getValue(sel_color_datatable, gene_name);
			color = sel_color_datatable.getDisplayConfig().getColor(color_value);
			size_value = sel_group.getValue(sel_size_datatable, gene_name);
			size = sel_size_datatable.getDisplayConfig().getSize(size_value);
		}

		//console.log("shape: " + shape_value + " " + navicell.shapes[shape]);
		//console.log("color: " + color_value + " " + color);
		//console.log("size: " + size_value + " " + size);
		var start_x = topx + (size*g_size)/8; // must depends on scale2 also
		//var start_y = topy - (size*g_size)/8; // must depends on scale2 also
		var start_y = topy; // must depends on scale2 also
		bound = draw_glyph_perform(context, start_x, start_y, shape, color, (size*g_size)/4, 1, false);
	}
	if (bound) {
		//console.log("bound: " + bound[0] + " " + bound[1] + " " + bound[2] + " " + bound[3]);
		overlay.addBoundBox(bound, gene_name, "glyph");
	}
	return bound;
}

var LINE_SEPARATOR_STYLE = "#000000";
var LINE_SEPARATOR_WIDTH = 1;

function fillStrokeRect(context, x, y, w, h) {
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
		//context.strokeRect(pos_x-dim2, pos_y-dim2, dim, dim);
		return [pos_x-dim2, pos_y-dim2, dim, dim];
	} else if (shape == 'Rectangle') {
		var dim_w = 2*size*scale;
		var dim_h = size*scale;
		fillStrokeRect(context, pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h);
		//context.strokeRect(pos_x-dim_w/2, pos_y-dim_h/2, dim_w, dim_h);
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
