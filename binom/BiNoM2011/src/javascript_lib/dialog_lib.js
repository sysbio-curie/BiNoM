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
	function build_datatable_import_dialog() {
		var select = $("#dt_import_type_select");
		select.html(get_biotype_select("dt_import_type", true));
	}

	build_datatable_import_dialog();

	var name = $("#dt_import_name");
	var file = $("#dt_import_file");
	var type = $("#dt_import_type");
	var status = $("#dt_import_status");
	var display_markers = $("#dt_import_display_markers");
	var sample_file = $("#dt_sample_file");

	function error_message(error) {
		status.html("<span class=\"error_message\">" + error + "</span>");
	}

	$("#dt_import_dialog" ).dialog({
		autoOpen: false,
		width: 500,
		height: 700,
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
						status.html("");

						datatable.ready.then(function() {
							status.html("Import Successful<br/>Genes: " + mapSize(datatable.gene_index) + "<br/>" + "Samples: " + mapSize(datatable.sample_index));
							navicell.annot_factory.sync();
							update_status_tables();
						});
						
						if (display_markers.attr("checked")) {
							datatable.ready.then(function() {
								datatable.display_markers(navicell.module_name, window);
							});
						}
					}

				}
			},

			Clear: function() {
				name.val("");
				file.val("");
				type.val("");
				status.html("");
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
		height: 650,
		modal: false,

		buttons: {
			"Apply": function() {
			},
			"Cancel": function() {
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

	$("#heatmap_config_div" ).dialog({
		autoOpen: false,
		width: 720,
		height: 750,
		modal: false,
		buttons: {
			"Apply": function() {
				console.log("applying");
			},
			
			"Cancel": function() {
			}
		}
	});

	$("#heatmap_editor_div" ).dialog({
		autoOpen: false,
		width: 750,
		height: 450,
		modal: false,
		buttons: {
			"Apply": function() {
				var sample_cnt = mapSize(navicell.dataset.samples);
				var group_cnt = mapSize(navicell.group_factory.group_map);
				var sample_group_cnt = sample_cnt + group_cnt;
				if (sample_group_cnt > MAX_HEATMAP_X) {
					sample_group_cnt = MAX_HEATMAP_X;
				}
				for (var idx = 0; idx < sample_group_cnt; ++idx) {
					var val = $("#heatmap_editor_gs_" + idx).val();
					if (val == "_none_") {
						navicell.heatmap_config.setSampleOrGroupAt(idx, undefined);
					} else {
						var prefix = val.substr(0, 2);
						var id = val.substr(2);
						if (prefix == 'g_') {
							console.log("group selected " + id + " at index " + idx);
							var group = navicell.group_factory.getGroupById(id);
							navicell.heatmap_config.setSampleOrGroupAt(idx, group);
						} else {
							console.log("group selected " + id + " at index " + idx);
							var sample = navicell.dataset.getSampleById(id);
							navicell.heatmap_config.setSampleOrGroupAt(idx, sample);
						}
					}
				}
				var datatable_cnt = mapSize(navicell.dataset.datatables);
				for (var idx = 0; idx < datatable_cnt; ++idx) {
					var val = $("#heatmap_editor_datatable_" + idx).val();
					if (val && val != "_none_") {
						console.log("datatable selected " + val + " at index " + idx);
						var datatable = navicell.getDatatableById(val);
						navicell.heatmap_config.setDatatableAt(idx, datatable);
					}
				}
				navicell.heatmap_config.shrink();
				update_heatmap_editor();
				heatmap_editor_set_editing(false);
			},
			
			"Clear": function() {
				navicell.heatmap_config.reset();
				update_heatmap_editor();
				heatmap_editor_set_editing(true);
			},

			"Cancel": function() {
				update_heatmap_editor();
				heatmap_editor_set_editing(false);
			}
		}
	});

});

function update_sample_status_table(doc, params) {
	var table = $("#dt_sample_status_table", doc);
	table.children().remove();
	// should use a string buffer
	var str = "<thead><tr><th>Samples&nbsp;(" + mapSize(navicell.dataset.samples) + ")</th>";

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
			if (datatable.sample_index[sample_name] !== undefined) {
				//str += "<td>" + mapSize(datatable.gene_index) + "</td>";
				str += "<td style=\"width: 100%; text-align: center\">X</td>";
			} else {
				str += "<td>&nbsp;</td>";
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

function update_group_status_table(doc, params) {
	var table = $("#dt_group_status_table", doc);
	table.children().remove();
	var str = "<thead><tr><th>Groups&nbsp;(" + mapSize(navicell.group_factory.group_map) + ")</th>";
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		str += "<th>&nbsp;" + datatable.html_name + "&nbsp;</th>";
	}
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
	var str = "<thead><tr><th>Module</th>";
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
		if (navicell.mapdata.hugo_map[gene_name][navicell.module_name]) {
			cnt++;
		}
	}
	return cnt;
}

function update_gene_status_table(doc, params) {
	var table = $("#dt_gene_status_table", doc);
	table.children().remove();
	// should use a string buffer
	var str = "<thead><tr><th>Genes&nbsp;(" + mapSize(navicell.dataset.genes) + ")</th>";
	str += "<th>In&nbsp;" + navicell.module_name + "&nbsp;(" + in_module_gene_count(navicell.module_name) + ")</th>";

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
		if (navicell.mapdata.hugo_map[gene_name][navicell.module_name]) {
			str += "<td>" + gene_name + "</td>";
		} else {
			str += "<td>&nbsp;</td>";
		}
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			if (datatable.gene_index[gene_name] !== undefined) {
				str += "<td style=\"width: 100%; text-align: center\">X</td>";
			} else {
				str += "<td>&nbsp;</td>";
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

	var tab_header = $("#dt_datatable_tabs ul");
	var tab_body = $("#dt_datatable_tabs");
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
	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		var data_div = datatable.data_div;
		tab_header.append('<li><a class="ui-button-text" href="#' + data_div.attr("id") + '">' + dt_name + '</a></li>');
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
			//str += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='show_display_config(" + datatable.getId() + ")'>preferences</a></td>";
			str += "<td style='border: none; text-decoration: underline; font-size: 9px'><a href='#' onclick='navicell.getDatatableById(" + datatable.getId() + ").showDisplayConfig()'>preferences</a></td>";
		}
		str += "</tr>";
	}
	table.append(str);
	table.tablesorter();

	tab_body.tabs("refresh");
}

function update_status_tables(params) {
//	navicell.annot_factory.sync();
	navicell.group_factory.buildGroups();
	for (var map_name in maps) {
		var doc = maps[map_name].document;
		update_sample_status_table(doc, params);
		update_gene_status_table(doc, params);
		update_group_status_table(doc, params);
		update_module_status_table(doc, params);
		update_datatable_status_table(doc, params);
		update_sample_annot_table(doc, params);
		//update_heatmap_config(doc, params);
		update_heatmap_editor(doc, params);
	}
//	navicell_session.write();
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
		update_status_tables();
	}
}

function cancel_datatables() {
	for (var dt_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[dt_name];
		$("#dt_name_" + datatable.getId()).val(dt_name);
	}
}

Datatable.prototype.showDisplayConfig = function() {
	var div_id = undefined;
	if (this.displayStepConfig) {
		div_id = this.displayStepConfig.div_id;
	} else if (this.displayDiscreteConfig) {
		div_id = this.displayDiscreteConfig.div_id;
	}
	console.log("div_id " + div_id);
	if (div_id) {
		var div = $("#" + div_id);
		var datatable_id = this.getId();
		div.dialog({
			autoOpen: false,
			width: 700,
			height: 550,
			modal: false,

			buttons: {
				"Apply": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayStepConfig = datatable.displayStepConfig;
					
					var prev_value = datatable.minval;
					var error = 0;
					var step_cnt = displayStepConfig.getStepCount();
					for (var idx = 0; idx < step_cnt; ++idx) {
						var value;
						if (idx == step_cnt-1) {
							value = datatable.maxval;
						} else {
							value = $("#step_value_" + datatable_id + "_" + idx).val();
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
								value = $("#step_value_" + datatable_id + "_" + idx).val();
							}
							var color = $("#step_color_" + datatable_id + "_" + idx).val();
							var size = $("#step_size_" + datatable_id + "_" + idx).val();
							var shape = $("#step_shape_" + datatable_id + "_" + idx).val();
							displayStepConfig.setStepInfo(idx, value, color, size, shape);
						}
						datatable.refresh();
						update_heatmap_editor(); // ??
					}
				},

				"Cancel": function() {
					var datatable = navicell.getDatatableById(datatable_id);
					var displayStepConfig = datatable.displayStepConfig;
					displayStepConfig.update();
				}
			}
		});
		div.dialog("open");
	}
}

function drawing_config_chart() {
	//$("#heatmap_config_div").dialog("open");
	$("#heatmap_editor_div").dialog("open");
}

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
	var heatmapConfig = navicell.heatmap_config;

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

var MAX_HEATMAP_X = 15;

function heatmap_editor_set_editing(val) {
	$("#heatmap_editing").html(val ? "editing..." : "");
	$("#gene_choice").css("visibility", val ? "hidden" : "visible");
	if (val) {
		/*$("#heatmap_editor_table tbody tr td").attr('style', '');*/
		$("#heatmap_editor_table td").attr('style', '');
	}
	if (0) {
		var buttons = $("#heatmap_editor_div").parent().find(".ui-button-text");
		buttons.each(function() {
			var button  = $(this);
			var text = button.text();
			if (text == "Cancel" ) {
				if (val) {
					//button.parent().removeClass("zz-hidden");
					/*button.parent().removeAttr("disabled");*/
					button.parent().removeClass(".ui-state-disabled");
					button.removeClass(".ui-state-disabled");
				} else {
					/*button.parent().attr("disabled", "disabled");*/
					button.parent().addClass(".ui-state-disabled");
					button.addClass(".ui-state-disabled");
				}
			}
		});
	}
}

function update_heatmap_editor(doc, params) {
	var heatmapConfig = navicell.heatmap_config;
	var table = $("#heatmap_editor_table");
	var sel_gene_id = $("#select_gene").val();
	var sel_gene = sel_gene_id ? navicell.dataset.getGeneById(sel_gene_id) : null;

	//var datatable_cnt = mapSize(navicell.dataset.datatables);
	var sample_cnt = mapSize(navicell.dataset.samples);
	var group_cnt = mapSize(navicell.group_factory.group_map);
	var sample_group_cnt = sample_cnt + group_cnt;

	console.log("update_heatmap_editor: " + sel_gene);
	if (sample_group_cnt > MAX_HEATMAP_X) {
		sample_group_cnt = MAX_HEATMAP_X;
	}
	table.children().remove();
	var html = "";
	html += "<tbody>";
	html += "<tr><td style='font-weight: bold; font-size: smaller; text-align: center'>Datatables</td>";
	for (var idx = 0; idx < sample_group_cnt; ++idx) {
		html += "<td style='border: 0px'><select id='heatmap_editor_gs_" + idx + "' onchange='heatmap_editor_set_editing(true)'>\n";
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

	var datatable_cnt = 0;
	for (var datatable_name in navicell.dataset.datatables) {
		var datatable = navicell.dataset.datatables[datatable_name];
		if (datatable.displayStepConfig) {
			datatable_cnt++;
		}
	}

	for (var idx = 0; idx < datatable_cnt; ++idx) {
		var sel_datatable = heatmapConfig.getDatatableAt(idx);
		html += "<tr>";
		html += "<td><select id='heatmap_editor_datatable_" + idx + "' onchange='heatmap_editor_set_editing(true)'>\n";
		html += "<option value='_none_'>Choose a datatable</option>\n";
		for (var datatable_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[datatable_name];
			if (datatable.displayStepConfig) {
				var selected = sel_datatable && sel_datatable.getId() == datatable.getId() ? " selected": "";
				html += "<option value='" + datatable.getId() + "'" + selected + ">" + datatable.name + "</option>";
			}
		}
		html += "</select></td>";
		if (sel_gene && sel_datatable) {
			var gene_name = sel_gene.name;
			console.log("this case " + gene_name);
			for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
				var sel_group = heatmapConfig.getGroupAt(idx2);
				var sel_sample = heatmapConfig.getSampleAt(idx2);
				if (sel_sample) {
					console.log("sel_sample at " + idx2 + " " + sel_sample.name);
				}
				if (sel_sample) {
					var value = sel_datatable.getValue(sel_sample.name, gene_name);
					var style = sel_datatable.getStyle(value);
					console.log("value: " + value + " style:" + style);
					/*html += "<td " + style + ">&nbsp;</td>";*/
					html += "<td " + style + ">" + value + "</td>";
				} else {
					html += "<td>&nbsp;</td>";
				}
			}
		} else {
			for (var idx2 = 0; idx2 < sample_group_cnt; ++idx2) {
				html += "<td>&nbsp;</td>";
			}
		}
		html += "</tr>\n";
	}

	html += "</tbody>";

	table.append(html);
	table.tablesorter();

	html = "Apply this configuration to gene:&nbsp;";
	html += "<select id='select_gene' onchange='update_heatmap_editor()'>\n";
	/*html += "<select id='select_gene'>\n";*/
	html += "<option value='_none_'></option>\n";
	for (var gene_name in navicell.dataset.genes) {
		var gene = navicell.dataset.genes[gene_name];
		var selected = sel_gene && sel_gene.getId() == gene.getId() ? " selected": "";
		html += "<option value='" + navicell.dataset.genes[gene_name].getId() + "'" + selected + ">" + gene_name + "</option>\n";
	}
	html += "</select>";
	//console.log("SELECT GENE [" + html + "]");
	$("#gene_choice").html(html);
	//console.log("SELECT GENE2: " + $("#dt_import_type_select").length);
	//$("#select_gene").html(html);
}
