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
	var name = $("#dt_import_name");
	var file = $("#dt_import_file");
	var type = $("#dt_import_type");
	var status = $("#dt_import_status");
	var display_markers = $("#dt_import_display_markers");

	function error_message(error) {
		status.html("<span class=\"error_message\">" + error + "</span>");
	}

	$("#dt_import_dialog" ).dialog({
		autoOpen: false,
		height: 700,
		width: 380,
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
					var datatable = new Datatable(navicell.dataset, type.val(), name.val(), file_elem.files[0]);
					if (datatable.error) {
						error_message(datatable.error);
					} else {
						status.html("");

						datatable.ready.then(function() {
							status.html("Import Successful<br/>Genes: " + mapSize(datatable.gene_index) + "<br/>" + "Samples: " + mapSize(datatable.sample_index));
							update_status_tables();
						});
						
						if (display_markers.attr("checked")) {
							datatable.ready.then(function() {
								datatable.display_markers(navicell.module_name);
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

	$("#dt_sample_status").dialog({
		autoOpen: false,
		height: 700,
		width: 500,
		modal: false
	});

	$("#dt_gene_status").dialog({
		autoOpen: false,
		height: 800,
		width: 350,
		modal: false
	});

	$("#dt_datatable_status").dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: false,
		buttons: {
			"Update": function() {
				var update_cnt = 0;
				for (var dt_name in navicell.dataset.datatables) {
					var elem = $("#dt_name_" + dt_name);
					var new_dt_name = elem.val();
					if (dt_name !== new_dt_name) {
						if (!navicell.dataset.updateDatatable(dt_name, new_dt_name)) {
							elem.val(dt_name);
							
						} else {
							update_cnt++;
						}

					}
				}
				if (update_cnt) {
					update_status_tables();
				}
			},
			Cancel: function() {
				for (var dt_name in navicell.dataset.datatables) {
					$("#dt_name_" + dt_name).val(dt_name);
				}
			}
		}
	});

	function update_sample_status_table() {
		var table = $("#dt_sample_status_table");
		table.children().remove();
		// should use a string buffer
		var str = "<thead><tr><th>Samples</th>";

		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			str += "<th>&nbsp;" + datatable.name + "&nbsp;</th>";
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
		//console.log("table: " + table.html());
		//console.log("table_str: " + str);
		table.append(str);
	}

	function update_gene_status_table() {
		var table = $("#dt_gene_status_table");
		table.children().remove();
		// should use a string buffer
		var str = "<thead><tr><th>Genes</th>";

		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			str += "<th>&nbsp;" + datatable.name + "&nbsp;</th>";
		}
		str += "</tr></thead>";
		str += "<tbody>\n";
		for (var gene_name in navicell.dataset.genes) {
			if (gene_name == "") {
				continue;
			}
			str += "<tr><td>" + gene_name + "</td>";
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
	}

	function update_datatable_status_table() {
		var table = $("#dt_datatable_status_table");
		table.children().remove();

		var str = "<thead><tr>";
		str += "<th>Datatable</th>";
		str += "<th>Type</th>";
		str += "<th>Genes</th>";
		str += "<th>Samples</th>";
		str += "</tr></thead>";

		str += "<tbody>";
		for (var dt_name in navicell.dataset.datatables) {
			var datatable = navicell.dataset.datatables[dt_name];
			str += "<tr>";
			//str += "<td>&nbsp;" + datatable.name + "&nbsp;</td>";
			str += "<td><input id=\"dt_name_" + datatable.name + "\" type=\"text\" value=\"" + datatable.name + "\"/></td>";
			str += "<td>&nbsp;" + datatable.biotype + "&nbsp;</td>";
			str += "<td>" + mapSize(datatable.gene_index) + "</td>";
			str += "<td>" + mapSize(datatable.sample_index) + "</td>";
			str += "</tr>";
		}
		table.append(str);
	}

	function update_status_tables() {
		update_sample_status_table();
		update_gene_status_table();
		update_datatable_status_table();
	}

	$("#sample_status").button().click(function() {
		$("#dt_sample_status").dialog("open");
	});

	$("#import_dialog").button().click(function() {
		$("#dt_import_dialog").dialog("open");
	});

	$("#gene_status").button().click(function() {
		$("#dt_gene_status").dialog("open");
	});

	$("#datatable_status").button().click(function() {
		$("#dt_datatable_status").dialog("open");
	});
});
