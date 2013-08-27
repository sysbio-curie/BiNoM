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


function mapSize(map) {
	var size = 0;
	for (var obj in map) {
		size++;
	}
	return size;
}

function array_push_all(arr, to_append) {
	for (var nn = 0; nn < to_append.length; ++nn) {
		arr.push(to_append[nn]);
	}
}

if (!Number.MAX_NUMBER) {
	Number.MAX_NUMBER = 4000000000; // not the correct value, but ok for our purposes
}

if (!Number.MIN_NUMBER) {
	Number.MIN_NUMBER = -4000000000; // not the correct value, but ok for our purposes
}

//
// Mapdata class
//
// Encapsulate all module entities, including map positions
//

function Mapdata() {
}

Mapdata.prototype = {
	// Mapdata for each module
	module_mapdata: {},

	// Hashmap from hugo name to entity information (including positions)
	hugo_map: {},

	// Returns the size of the hugo map
	entityCount: function() {
		return mapSize(this.hugo_map);
	},

	// Adds a module mapdata: fills the hugo_map hashmap
	addModuleMapdata: function(module_name, module_mapdata) {

		this.module_mapdata[module_name] = module_mapdata;

		for (var ii = 0; ii < module_mapdata.length; ++ii) {
			var entities = module_mapdata[ii].entities;
			for (var jj = 0; jj < entities.length; ++jj) {
				var entity_map = entities[jj];
				var hugo_arr = entity_map['hugo'];
				if (hugo_arr.length > 0) {
					for (var kk = 0; kk < hugo_arr.length; ++kk) {
						var hugo = hugo_arr[kk];
						if (!this.hugo_map[hugo]) {
							this.hugo_map[hugo] = {};
						}
						this.hugo_map[hugo][module_name] = entity_map;
					}
				}
			}
		}
	},

	getClass: function() {return "Mapdata";}
};

//
// Dataset class
//
// Main object gathering all data information
//

function Dataset(name) {
	this.name = name;

	this.genes = {};
	this.genes_id = {};
	this.gene_id = 1;

	this.datatable_id = 1;
	this.datatables = {};
	this.datatables_id = {};

	this.sample_id = 1;
	this.samples = {};
	this.samples_id = {};

	this.modifs_id = {};
}

Dataset.prototype = {
	name: "",
	datatables: {},
	datatables_id: {},
	genes: {},
	samples: {},

	geneCount: function() {
		return mapSize(this.genes);
	},

	sampleCount: function() {
		return mapSize(this.samples);
	},

	datatableCount: function() {
		return mapSize(this.datatables);
	},

	readDatatable: function(biotype_name, name, file) {
		var datatable =  new Datatable(this, biotype_name, name, file, this.datatable_id++);
		if (!datatable.error) {
			this.addDatatable(datatable);
		}
		return datatable;
	},

	addDatatable: function(datatable) {
		this.datatables[datatable.name] = datatable;
		this.datatables_id[datatable.getId()] = datatable;
	},

	getDatatableByName: function(name) {
		return this.datatables[name];
	},

	getDatatableById: function(id) {
		return this.datatables_id[id];
	},

	removeDatatable: function(datatable) {
		if (this.datatables[datatable.name] == datatable) {
			console.log("should remove " + datatable.name);
			for (var sample_name in datatable.sample_index) {
				var sample = this.samples[sample_name];
				if (!--sample.refcnt) {
					delete this.samples[sample_name];
				}
			}
			for (var gene_name in datatable.gene_index) {
				var gene = this.genes[gene_name];
				if (!--gene.refcnt) {
					delete this.genes[gene_name];
				}
			}
			delete this.datatables[datatable.name];
		}
	},

	getGeneInfoByModifId: function(module_name, modif_id) {
		console.log("getGeneInfoByModifId module_name: " + module_name + " " + navicell_module_name);
		if (this.modifs_id[module_name]) {
			return this.modifs_id[module_name][modif_id];
		}
		return null;
	},

	syncModifs: function() {
		console.log("syncModifs starting " + mapSize(this.genes));
		this.modifs_id = {};
		for (var jj = 0; jj < navicell.module_names.length; ++jj) {
			var module_name = navicell.module_names[jj];
			this.modifs_id[module_name] = {};
			for (var gene_name in this.genes) {
				var gene = this.genes[gene_name];
				var entity_map = this.genes[gene_name].entity_map;
				var entity = entity_map[module_name];
				if (entity) {
					var modif_arr = entity.modifs;
					if (modif_arr) {
						for (var nn = 0; nn < modif_arr.length; ++nn) {
							var modif = modif_arr[nn];
							var positions = modif.positions;
							var arrpos = [];
							if (positions) {
								for (var kk = 0; kk < positions.length; ++kk) {
									arrpos.push({id : modif.id, p : new google.maps.Point(positions[kk].x, positions[kk].y), gene_name: gene_name});
								}
							}
							this.modifs_id[module_name][modif.id] = [gene, arrpos];
						}
					}
				}
			}
			console.log("syncModifs " + module_name + " " + mapSize(this.modifs_id[module_name]));
		}
	},

	getSample: function(sample_name) {
		return this.samples[sample_name];
	},
	
	getSampleById: function(sample_id) {
		return this.samples_id[sample_id];
	},
	
	// behaves as a sample factory
	addSample: function(sample_name) {
		if (!this.samples[sample_name]) {
			var sample = new Sample(sample_name, this.sample_id++);
			this.samples[sample_name] = sample;
			this.samples_id[sample.id] = sample;
		} else {
			this.samples[sample_name].refcnt++;
		}
		return this.samples[sample_name];
	},

	getGeneByName: function(gene_name) {
		return this.genes[gene_name];
	},
	
	getGeneById: function(gene_id) {
		return this.genes_id[gene_id];
	},
	
	// behaves as a gene factory
	addGene: function(gene_name, entity_map) {
		if (!this.genes[gene_name]) {
			var gene = new Gene(gene_name, entity_map, this.gene_id++);
			this.genes[gene_name] = gene;
			this.genes_id[gene.getId()] = gene;
		} else {
			this.genes[gene_name].refcnt++;
		}
		return this.genes[gene_name];
	},

	updateDatatable: function(datatable_name, new_datatable_name, new_datatable_type) {
		if (!this.datatables[datatable_name]) {
			return true;
		}
		if (datatable_name == new_datatable_name) {
			var datatable = this.datatables[datatable_name];
			datatable.biotype = navicell.biotype_factory.getBiotype(new_datatable_type);
			return true;
		}
		if (this.datatables[new_datatable_name]) {
			return false;
		}					
		var datatable = this.datatables[datatable_name];
		delete this.datatables[datatable_name];
		//datatable.name = new_datatable_name;
		datatable.setName(new_datatable_name);
		datatable.biotype = navicell.biotype_factory.getBiotype(new_datatable_type);
		this.datatables[new_datatable_name] = datatable;
		if (datatable.displayStepConfig) {
			datatable.displayStepConfig.update();
		}
		return true;
	},

	drawDLO: function(context, scale, gene_name, topx, topy) {
		var size = 2;
		console.log("Drawing " + gene_name);
		context.fillStyle = 'rgba(100, 30, 100, 1)';
		context.fillRect(topx, topy, (size+2)*scale, size*scale);
		//var gene = this.getGeneByName(gene_name);
	},

	getClass: function() {return "Dataset";}
};

//
// Datatable class
//
// Encapsulate datatable contents (but only for genes existing in map) and type
//

if (!String.prototype.trim) {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g,'');
	}
}

function make_button(name, id, onclick) {
	return "<input type='button' style='-moz-border-radius: 4px; border-radius: 4px; font-size: small' class='ui-widget ui-button ui-dialog-buttonpane ui-button-text ui-button-text-only ui-state-default ui-widget-content' id='" + id + "' value='" + name + "'onclick='" + onclick + "'></input>";
}

function is_empty_value(value) {
	if (!value) {
		return true;
	}
	var tvalue = value.trim().toUpperCase();
	return tvalue == '' || tvalue == '_' || tvalue == '-' || tvalue == 'NA' || tvalue == 'N/A';
}

function step_color_count_change(id) {
	var datatable = navicell.dataset.datatables_id[id];
	if (datatable) {
		var value = $("#step_color_count_" + id).val();
		console.log("value: " + value);
		datatable.displayStepConfig.setStepCount(value);
	}
}

function DisplayStepConfig(datatable) {
	this.datatable = datatable;
	var step_cnt = 5;
	this.buildDiv(step_cnt);
	this.setStepCount(step_cnt);
	this.setDefaults(step_cnt);
}

DisplayStepConfig.prototype = {
	datatable: null,
	values: [], // numeric step values
	colors: [], // rgb values
	sizes: [], // pixel values
	shapes: [], // shape types

	setStepCount: function(step_cnt) {
		//step_cnt++;
		if (step_cnt+3 == this.values.length) {
			return;
		}
		step_cnt *= 1.;
		this.values = [];
		this.values.push(this.datatable.minval);
		var step = (this.datatable.maxval - this.datatable.minval)/(step_cnt+1);
		console.log("step_cnt: " + step_cnt + " step: " + step + " " + this.datatable.minval + " " + this.datatable.maxval);
		//var step_cnt_1 = step_cnt-1;
		for (var nn = 0; nn < step_cnt-1; ++nn) {
			var value = this.datatable.minval + (nn+1)*step;
			value = parseInt(value*100)/100;
			this.values.push(value);
		}
		this.values.push(this.datatable.maxval);
		for (var nn = 0; nn < this.values.length; ++nn) {
			console.log("values[" + nn + "] = " + this.values[nn]);
		}
		this.colors = new Array(step_cnt);
		this.sizes = new Array(step_cnt);
		this.shapes = new Array(step_cnt);
		this.update();
		this.datatable.refresh();
	},

	setStepInfo: function(idx, value, color, size, shape) {
		console.log("setting at " + idx + " " + value + " (== " + this.values[idx+1] + ") " + color + " " + size + " " + shape);
		if (value != Number.MIN_NUMBER) {
			this.values[idx+1] = value;
		}
		this.colors[idx] = color;
		this.sizes[idx] = size;
		this.shapes[idx] = shape;
	},

	setDefaults: function(step_cnt) {
		if (step_cnt == 5) {
			this.setStepInfo(0, Number.MIN_NUMBER, "F7FF19", 4, 0);
			this.setStepInfo(1, Number.MIN_NUMBER, "D6ECFF", 6, 1);
			this.setStepInfo(2, Number.MIN_NUMBER, "19FF57", 8, 2);
			this.setStepInfo(3, Number.MIN_NUMBER, "6421FF", 10, 3);
			this.setStepInfo(4, Number.MIN_NUMBER, "E64515", 12, 4);
			this.update();
		}
	},

	getStepIndex: function(value) {
		value *= 1.;
		var len = this.values.length;
		for (var nn = 1; nn < len; ++nn) {
			if (value < this.values[nn]) {
				return nn-1;
			}
		}
		return len-1;
	},

	getStepCount: function() {
		return this.values.length-1;
	},

	getMinValue: function() {
		return this.values[0];
	},

	getMaxValue: function() {
		return this.values[this.values.length-1];
	},

	getValueAt: function(idx) {
		return this.values[idx+1];
	},

	getColorAt: function(idx) {
		return this.colors[idx];
	},

	getSizeAt: function(idx) {
		return this.sizes[idx];
	},

	getShapeAt: function(idx) {
		return this.shapes[idx];
	},

	getColor: function(value) {
		return this.colors[this.getStepIndex(value)];
	},

	getSize: function(value) {
		return this.sizes[this.getStepIndex(value)];
	},

	getShape: function(value) {
		return this.shapes[this.getStepIndex(value)];
	},

	update: function() {
		var id = this.datatable.getId();
		var table = $("#step_color_table_" + id);
		table.children().remove();
		var html = "<thead>";
		html += "<th>Less than</th>";
		html += "<th>Color</th>";
		html += "<th>Size</th>";
		html += "<th>Shape</th>";
		html += "</thead><tbody>";
		html += "<tr><td>" + this.datatable.minval + " (minimum)</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>\n";
		var step_cnt = this.getStepCount();
		for (var idx = 0; idx < step_cnt; idx++) {
			html += "<tr>";
			if (idx == step_cnt-1) {
				html += "<td>" + this.datatable.maxval + " (maximum)</td>";
			} else {
				html += "<td><input type='text' id='step_value_" + id + "_" + idx + "' value='" + this.getValueAt(idx) + "'></input></td>";
			}
			html += "<td><input id='step_color_" + id + "_" + idx + "' value='" + this.getColorAt(idx) + "' class='color'></input></td>";
			//html += "<td><input class='color' value='" + "000000" + "'></input></td>";
			html += "<td><select id='step_size_" + id + "_" + idx + "'>";
			html += "<option value='_none_'>Choose a size</option>";
			var selsize = this.getSizeAt(idx);
			for (var size = 2; size < 7; size += 1) {
				var size2 = 2*size;
				html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
			}
			html += "</select></td>";
			html += "<td><select id='step_shape_" + id + "_" + idx + "'>";
			html += "<option value='_none_'>Choose a shape</option>";
			var selshape = this.getShapeAt(idx);
			for (var shape in ["Square", "Rectangle", "Triangle", "Circle", "Hexagon"]) {
				html += "<option value='" + shape + "' " + (shape == selshape ? "selected" : "") + ">" + shape + "</option>";
			}
			html += "</select></td>";
			html += "</tr>\n";
		}
		//html += "<tr><td>Maximum " + this.datatable.maxval + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>\n";
		html += "</tbody>";
		table.append(html);

		var count = $("#step_color_count_" + id);

		var title = $("#step_color_title_" + id);
		title.html(this.datatable.name + " Preferences");

		jscolor.init();
	},

	buildDiv: function(step_cnt) {
		var id = this.datatable.getId();
		var div_id = "step_color_" + id;
		var html = "<div class='step-color' id='" + div_id + "'>\n";
		html += "<h3 id='step_color_title_" + id + "'></h3>";
		html += "<table class='step-color-table' id='step_color_table_" + id + "'>";
		/*
		html += "<thead>";
		html += "<th>Less than</th>";
		html += "<th>Color</th>";
		html += "<th>Size</th>";
		html += "<th>Shape</th>";
		html += "</thead><tbody>";
		html += "<tr><td>Minimum " + this.datatable.minval + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>\n";
		for (var idx = 0; idx < step_cnt; idx++) {
			html += "<tr>";
			html += "<td><input type='text' id='step_value_" + id + "_" + idx + "' value='" + this.getValueAt(idx) + "'></input></td>";
			//html += "<td><input id='step_color_" + id + "_" + idx + "' value='" + this.getColorAt(idx) + "' class='color'></input></td>";
			html += "<td><input class='color' value='" + "000000" + "'></input></td>";
			html += "<td><select id='step_size_" + id + "_" + idx + "'>";
			html += "<option value='_none_'>Choose a size</option>";
			var selsize = this.getSizeAt(idx);
			for (var size = 2; size < 7; size += 1) {
				var size2 = 2*size;
				html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
			}
			html += "</select></td>";
			html += "<td><select id='step_shape_" + id + "_" + idx + "'>";
			html += "<option value='_none_'>Choose a shape</option>";
			var selshape = this.getShapeAt(idx);
			for (var shape in ["Square", "Rectangle", "Triangle", "Circle", "Hexagon"]) {
				html += "<option value='" + shape + "' " + (shape == selshape ? "selected" : "") + ">" + shape + "</option>";
			}
			html += "</select></td>";
			html += "</tr>\n";
		}
		html += "<tr><td>Maximum " + this.datatable.maxval + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>\n";
		html += "</tbody>";
		*/
		html += "</table>";

		html += "<BR/><BR/>Step Count <select id='step_color_count_" + id + "' onchange='step_color_count_change(" + id + ")'>";
		for (var step = 3; step < 12; ++step) {
			html += "<option value='" + step + "' " + (step == step_cnt ? "selected" : "") + ">" + step + "</option>";
		}
		html += "</select>";

		/*
		html += "<div class='buttonpane'>";
		html += "<table border='0'>";
		html += "<tr><td style='width: 85%; cellspacing: 20px'>&nbsp;</td><td>";
		html += make_button("Cancel", "step_cancel_" + id, "step_cancel(" + id + ")");
		html += "</td><td>";
		html += make_button("Apply", "step_apply_" + id, "step_apply(" + id + ")");
		html += "</td></tr>";
		html += "</table></div>";
		*/

		html += "</div>";

		this.div_id = div_id;
		$('body').append(html);
	},

	getClass: function() {
		return "DisplayStepConfig";
	}
};

function HeatmapConfig() {
	this.reset();
}

HeatmapConfig.prototype = {

	reset: function() {
		this.datatables = [];
		this.samples_or_groups = [];
	},

	shrink: function() {
		var new_samples_or_groups = []
		var samples_or_groups_map = {};
		for (var idx = 0; idx < this.samples_or_groups.length; idx++) {
			var sample_or_group = this.samples_or_groups[idx];
			if (sample_or_group && !samples_or_groups_map[sample_or_group.getId()]) {
				new_samples_or_groups.push(sample_or_group);
				samples_or_groups_map[sample_or_group.getId()] = true;
			}
		}
		this.samples_or_groups = new_samples_or_groups;

		var new_datatables = []
		var datatables_map = []
		for (var idx = 0; idx < this.datatables.length; idx++) {
			var datatable = this.datatables[idx];
			if (datatable && !datatables_map[datatable.getId()]) {
				new_datatables.push(datatable);
				datatables_map[datatable.getId()] = true;
			}
		}
		this.datatables = new_datatables;
	},

	setDatatableAt: function(idx, datatable) {
		if (idx >= this.datatables.length) {
			this.datatables.length = idx+1;
		}
		this.datatables[idx] = datatable;
	},

	getDatatableAt: function(idx) {
		if (idx >= this.datatables.length) {
			return undefined;
		}
		return this.datatables[idx];
	},

	setSampleOrGroupAt: function(idx, sample_or_group) {
		if (idx >= this.samples_or_groups.length) {
			this.samples_or_groups.length = idx+1;
		}
		this.samples_or_groups[idx] = sample_or_group;
	},

	getSampleOrGroupAt: function(idx) {
		if (idx >= this.samples_or_groups.length) {
			return undefined;
		}
		return this.samples_or_groups[idx];
	},

	getGroupAt: function(idx) {
		var sample_or_group = this.getSampleOrGroupAt(idx);
		return sample_or_group && sample_or_group.isGroup() ? sample_or_group : undefined;
	},

	getSampleAt: function(idx) {
		var sample_or_group = this.getSampleOrGroupAt(idx);
		return sample_or_group && sample_or_group.isSample() ? sample_or_group : undefined;
	}
};

// TBD datatable id management
function Datatable(dataset, biotype_name, name, file, datatable_id) {
	if (dataset.datatables[name]) {
		this.error = "datatable " + name + " already exists";
		return;
	}
	this.minval = Number.MAX_NUMBER;
	this.maxval = Number.MIN_NUMBER;
	this.error = "";
	this.id = datatable_id;
	this.dataset = dataset;
	this.biotype = navicell.biotype_factory.getBiotype(biotype_name);

	this.setName(name);

	this.gene_index = {};
	this.sample_index = {};
	this.data = [];

	var tab_body = $("#dt_datatable_tabs");
	tab_body.append("<div id='dt_datatable_id" + this.id + "'><div class='switch-view-div'>" + make_button("", "switch_view_" + this.id, "switch_view(" + this.id + ")") + "</div><table id='dt_datatable_table_id" + this.id + "' class='tablesorter datatable_table'></table></div>");
	this.data_div = $("#dt_datatable_id" + this.id);
	this.data_table = $("#dt_datatable_table_id" + this.id);
	this.switch_button = $("#switch_view_" + this.id);
	//this.switch_button.addClass('switch-button');
	this.switch_button.css('font-size', '10px');
	this.switch_button.css('background', 'white');
	this.switch_button.css('color', 'darkblue');

	var reader = new FileReader();
	reader.readAsBinaryString(file);

	var datatable = this;

	var ready = this.ready = $.Deferred(reader.onload);
	reader.onload = function() { 
		var dataset = datatable.dataset;

		var text = reader.result;

		var lines = text.split("\n");
		var gene_length = lines.length;

		var line = lines[0].split("\t");
		var sample_cnt = line.length-1;
		for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
			var sample_name = line[sample_nn+1];
			if (sample_name.length > 1) {
				dataset.addSample(sample_name);
				datatable.sample_index[sample_name] = sample_nn;
				//console.log("adding sample [" + sample_name + "] " + sample_name.length);
			}
		}
		
		for (var gene_nn = 0, gene_jj = 1; gene_jj < gene_length; ++gene_jj) {
			var line = lines[gene_jj].split("\t");
			var gene_name = line[0];
			if (!navicell.mapdata.hugo_map[gene_name]) {
				continue;
			}
			dataset.addGene(gene_name, navicell.mapdata.hugo_map[gene_name]);

			datatable.gene_index[gene_name] = gene_nn;
			datatable.data[gene_nn] = [];
			for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
				var value = line[sample_nn+1];
				//datatable.data[gene_nn][sample_nn] = value;
				datatable.setData(gene_nn, sample_nn, value);
			}
			++gene_nn;
		}

		datatable.epilogue();
		ready.resolve();
		dataset.syncModifs();
	}

	reader.onerror = function(e) {  // If anything goes wrong
		this.error = e.toString();
		console.log("Error", e);    // Just log it
	}
}

Datatable.prototype = {
	dataset: null,
	biotype: null,
	name: "",
	gene_index: {},
	sample_index: {},
	data: [],
	ready: null,
	minval: null,
	maxval: null,

	// 2013-05-31
	// TBD: need methods:
	// - to get positions from an id or a set of ids (called from
	//   show_markers or from the jstree search),
	// - to get positions from a name or a set of names.
	// 
	// In the following method, we get positions sucessfully, but not from
	// an id but scanning the fill array => a map indexed by id (mind:
	// multiple id per gene) is missing.
	display_markers: function(module_name, _window) {
		console.log("display_markers: " + module_name);
		var id_arr = [];
		var arrpos = [];
		for (var gene_name in this.gene_index) {
			var entity_map = this.dataset.genes[gene_name].entity_map;
			var entity = entity_map[module_name];
			if (entity) {
				var modif_arr = entity.modifs;
				if (modif_arr) {
					for (var nn = 0; nn < modif_arr.length; ++nn) {
						var modif = modif_arr[nn];
						//id_arr.push(modif.id);
						// >> getting positions
						var positions = modif.positions;
						if (positions) {
							for (var kk = 0; kk < positions.length; ++kk) {
								arrpos.push({id : modif.id, p : new google.maps.Point(positions[kk].x, positions[kk].y), gene_name: gene_name});
							}
							id_arr.push(modif.id);
						}
						// << getting positions
					}
				}
			}
		}
		//console.log("arrpos: " + arrpos.length);
		_window.show_markers(id_arr);

		overlay.arrpos = arrpos;
		overlay.draw();
	},

	setName: function(name) {
		this.name = name;
		this.html_name = name.replace(/ /g, "&nbsp;");
	},

	getId: function() {return this.id;},

	epilogue: function() {
		if (this.biotype.isContinuous()) {
			this.displayStepConfig = new DisplayStepConfig(this);
			this.displayDiscreteConfig = null;
		} else {
			this.displayStepConfig = null;
			this.displayDiscreteConfig = null; // new DisplayDiscreteConfig(this);
		}
		this.makeGeneView();
	},

	makeGeneView: function() {
		this.current_view = "gene";
		this.data_table.children().remove();
		this.data_table.append(this.makeDataTable_genes());
		this.data_table.tablesorter();
	},

	makeSampleView: function() {
		this.current_view = "sample";
		this.data_table.children().remove();
		this.data_table.append(this.makeDataTable_samples());
		this.data_table.tablesorter();
	},

	switchView: function() {
		if (this.current_view == "gene") {
			this.makeSampleView();
		} else {
			this.makeGeneView();
		}
	},

	refresh: function() {
		if (this.current_view == "gene") {
			this.makeGeneView();
		} else {
			this.makeSampleView();
		}
	},

	getStyle: function(value) {
		if (this.displayStepConfig) {
			var color = this.displayStepConfig.getColor(value);
			if (color) {
				var rgb1 = color.substring(0, 2);
				var rgb2 = color.substring(2, 4);
				var rgb3 = color.substring(4, 6);
				rgb1 = parseInt("0x" + rgb1)/256.;
				rgb2 = parseInt("0x" + rgb2)/256.;
				rgb3 = parseInt("0x" + rgb3)/256.;
				var fg = 0.213 * rgb1 +	0.715 * rgb2 + 0.072 * rgb3 < 0.5 ? '#FFF' : '#000';
				return " style='background: #" + color + "; color: " + fg + "; text-align: center;'";
			}
		}
		return '';
	},

	makeDataTable_genes: function() {
		this.switch_button.val("Switch to Samples / Genes");
		var str = "<thead><th>Genes</th>";
		for (var sample_name in this.sample_index) {
			str += "<th>" + sample_name + "</th>";
		}
		str += "</thead>";
		str += "<tbody>";
		for (var gene_name in this.gene_index) {
			str += "<tr><td>" + gene_name + "</td>";
			for (var sample_name in this.sample_index) {
				var value = this.data[this.gene_index[gene_name]][this.sample_index[sample_name]];
				str += "<td class='datacell'" + this.getStyle(value) + ">" + value + "</td>";
			}
			str += "</tr>";
		}
		str += "</tbody>";
		return str;
	},

	getValue: function(sample_name, gene_name) {
		var gene_idx = this.gene_index[gene_name];
		var sample_idx = this.sample_index[sample_name];
		if (gene_idx != undefined && sample_idx != undefined) {
			return this.data[gene_idx][sample_idx];
			/*
			var gene_data = this.data[gene_idx];
			if (gene_data) {
				return gene_data[sample_idx];
			}
			*/
		}
		return null;
	},

	makeDataTable_samples: function() {
		this.switch_button.val("Switch to Genes / Samples");
		var str = "<thead><th>Samples</th>";
		for (var gene_name in this.gene_index) {
			str += "<th>" + gene_name + "</th>";
		}
		str += "</thead>";
		str += "<tbody>";
		for (var sample_name in this.sample_index) {
			str += "<tr><td>" + sample_name + "</td>";
			for (var gene_name in this.gene_index) {
				var value = this.data[this.gene_index[gene_name]][this.sample_index[sample_name]];
				str += "<td class='datacell'" + this.getStyle(value) + ">" + value + "</td>";
			}
			str += "</tr>";
		}
		str += "</tbody>";
		return str;
	},

	setData: function(gene_nn, sample_nn, value) {
		if (is_empty_value(value)) {
			value = '';
		} else {
			var ivalue = parseFloat(value);
			if (ivalue !== NaN) {
				if (ivalue < this.minval) {
					this.minval = ivalue;
				}
				if (ivalue > this.maxval) {
					this.maxval = ivalue;
				}
			}
		}
		this.data[gene_nn][sample_nn] = value;
	},

	/*
	showDisplayConfig: function() {
		var div_id = undefined;
		if (this.displayStepConfig) {
			div_id = this.displayStepConfig.div_id;
		} else if (this.displayDiscretConfig) {
			div_id = this.displayDiscreteConfig.div_id;
		}
		console.log("div_id " + div_id);
		if (div_id) {
			var div = $("#" + div_id);
			div.dialog({
				autoOpen: false,
				height: 550,
				width: 700,
				modal: false
			});
			div.dialog("open");
		}
	},
	*/

	getClass: function() {return "Datatable";}
};

function switch_view(id) {
	var datatable = navicell.dataset.datatables_id[id];
	if (datatable) {
		datatable.switchView();
	}
}

//
// Annotation class
//

function Annotation(name, id) {
	this.name = name;
	this.is_group = false;
	this.id = id;
}

Annotation.prototype = {
	name: "",
	is_group: false,
	desc: "",

	setIsGroup: function(is_group) {
		this.is_group = is_group;
	},

	getClass: function() {return "Annotation";}
};

//
// AnnotationFactory class
//

function AnnotationFactory() {
	this.annot_id = 1;
}

AnnotationFactory.prototype = {
	annots_per_name: {},
	annots_per_id: {},
	ready: null,
	sample_read: 0,
	sample_annotated: 0,
	annot_samples: {},

	addAnnotValue: function(sample_name, annot_name, annot_value) {
		if (!this.annot_samples[sample_name]) {
			this.annot_samples[sample_name] = {};
		}
		if (!this.annot_samples[sample_name][annot_name]) {
			this.annot_samples[sample_name][annot_name] = {};
		}

		this.annot_samples[sample_name][annot_name][annot_value] = true;
	},

	sync: function() {
		var annotated = 0;
		for (var sample_name in this.annot_samples) {
			var sample = navicell.dataset.getSample(sample_name);
			if (sample) {
				for (var annot_name in this.annot_samples[sample_name]) {
					for (var annot_value in this.annot_samples[sample_name][annot_name]) {
						sample.addAnnotValue(annot_name, annot_value);
						annotated++;
					}
				}
			}
		}
		return annotated;
	},

	readfile: function(file) {
		var reader = new FileReader();
		reader.readAsBinaryString(file);

		var annot_factory = this;
		this.sample_read = 0;
		this.sample_annotated = 0;
		var ready = this.ready = $.Deferred(reader.onload);
		reader.onload = function() { 
			var text = reader.result;
			var lines = text.split("\n");
			var header = lines[0].split("\t");
			var annots = [];
			for (var nn = 1; nn < header.length; ++nn) {
				annots.push(annot_factory.getAnnotation(header[nn]));
			}
			var annot_cnt = annots.length;
			var sample_cnt = lines.length-1;
			for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
				var line = lines[sample_nn+1].split("\t");
				if (line.length < 2) {
					continue;
				}
				var sample_name = line[0];
				for (var annot_nn = 0; annot_nn < annot_cnt; ++annot_nn) {
					var annot_value = line[annot_nn+1];
					var annot_name = annots[annot_nn].name;
					annot_factory.addAnnotValue(sample_name, annot_name, annot_value);
				}
				annot_factory.sample_read++;
			}
			annot_factory.sample_annotated = annot_factory.sync();
			ready.resolve();
		},
		reader.onerror = function(e) {  // If anything goes wrong
			console.log("Error", e);    // Just log it
		}
	},

	getAnnotation : function(name) {
		if (!this.annots_per_name[name]) {
			var annot = new Annotation(name, this.annot_id);
			this.annots_per_name[name] = annot;
			this.annots_per_id[this.annot_id] = annot;
			this.annot_id++;
		}
		return this.annots_per_name[name];
	},

	getAnnotationPerId : function(annot_id) {
		return this.annots_per_id[annot_id];
	},

	getAnnotCount: function() {
		return mapSize(this.annots_per_name);
	}
};

//
// Sample class
//

function Sample(name, id) {
	this.name = name;
	this.id = id;
	this.annots = {};
	this.refcnt = 1;
	this.groups = {};
}

Sample.prototype = {
	name: "",
	annots: {},
	groups: {},

	isGroup: function() {
		return false;
	},

	isSample: function() {
		return true;
	},

	addAnnotValue: function(annot_name, value) {
		//console.log("sample: " + this.name + " add annot value: '" + annot + "' '" + value + "'");
		this.annots[annot_name] = value;
	},

	clearGroups: function() {
		this.groups = {}; // or clear the map ?
	},

	addGroup: function(group) {
		this.groups[group.name] = group;
	},

	getGroup: function(group_name) {
		return this.groups[group_name];
	},

	getId: function() {
		return this.id;
	},

	getClass: function() {return "Sample";}
};

//
// Gene class
//

function Gene(name, entity_map, id) {
	this.name = name;
	this.entity_map = entity_map;
	this.refcnt = 1;
	this.id = id;
}

Gene.prototype = {
	name: "",
	entity_map: {},

	getId: function() {
		return this.id;
	},

	getClass: function() {return "Gene";}
};

//
// Group class
//

function Group(annots, values, id) {
	this.annots = annots;
	this.values = values;
	this.samples = {};
	this.id = id;
	this.name = navicell.group_factory.buildName(annots, values);
	this.html_name = "";
	for (var nn = 0; nn < annots.length; ++nn) {
		this.html_name += (this.html_name.length > 0 ? "<br>" : "") + '<span class="group_name">' + annots[nn].replace(/ /g, '&nbsp;') + ':</span>&nbsp;<span class="group_value">' + values[nn].replace(/ /g, '&nbsp;')  + '</span>';
	}
}

Group.prototype = {
	annots: [],
	values: [],
	samples: {},
	name: "",
	html_name: "",

	isGroup: function() {
		return true;
	},

	isSample: function() {
		return false;
	},

	addSample: function(sample) {
		this.samples[sample.name] = sample;
	},

	getId: function() {
		return this.id;
	},

	getClass: function() {return "Group";}
};


//
// GroupFactory class
//

function GroupFactory() {
	this.group_id = 1;
}

GroupFactory.prototype = {
	group_map: {},
	group_id_map: {},

	buildName: function(group_annots, group_values) {
		var str = "";
		for (var nn = 0; nn < group_annots.length; ++nn) {
			str += (str.length > 0 ? "; " : "") + group_annots[nn] + ": " + group_values[nn];
		}
		return str;
	},

	addGroup: function(group_annots, group_values) {
		var group_name = this.buildName(group_annots, group_values);
		if (!this.group_map[group_name]) {
			var group = new Group(group_annots, group_values, this.group_id++);
			this.group_map[group_name] = group;
			this.group_id_map[group.getId()] = group;
		}
		return this.group_map[group_name];
	},

	getGroup: function(group_annots, group_values) {
		var group_name = this.buildName(group_annots, group_values);
		return this.group_map[group_name];
	},

	getGroupById: function(group_id) {
		return this.group_id_map[group_id];
	},

	getId: function() {
		return this.id;
	},

	buildGroups: function() {
		this.group_map = {};
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			sample.clearGroups();
			var annot_arr = [];
			for (var annot_name in sample.annots) {
				annot_arr.push(navicell.annot_factory.annots_per_name[annot_name]);
			}
			if (annot_arr.length) {
				var annot_len = annot_arr.length;
				var group_annots = [];
				var group_values = [];
				for (var nn = 0; nn < annot_len; ++nn) {
					var annot = annot_arr[nn];
					if (annot.is_group) {
						group_annots.push(annot.name);
						group_values.push(sample.annots[annot.name]);
					}
				}
				
				if (group_annots.length) {
					var group = this.addGroup(group_annots, group_values);
					sample.addGroup(group);
					group.addSample(sample);
				}
			}
		}
	},

	getClass: function() {return "GroupFactory";}
};

function Biotype(name, type) {
	this.name = name;
	this.type = type;
}

Biotype.prototype = {
	name: "",
	type: null,

	isContinuous: function() {
		return this.type == navicell.CONTINUOUS;
	},

	isDiscrete: function() {
		return this.type == navicell.DISCRETE;
	},

	isSet: function() {
		return this.type == navicell.SET;
	}
};

function BiotypeType(name) {
	this.name = name;
}

function BiotypeFactory() {
	this.biotypes = {};
}

BiotypeFactory.prototype = {
	biotypes: {},

	addBiotype: function(biotype) {
		this.biotypes[biotype.name] = biotype;
	},

	getBiotype: function(biotype_name) {
		return this.biotypes[biotype_name];
	}
};

/*
function SampleFactory() {
}

SampleFactory.prototype = {
	sample_map: {},

	getSample: function(sample_name) {
		if (!sample_map[sample_name]) {
			sample_map[sample_name] = new Sample(sample_name);
		}
		return sample_map[sample_name];
	},

	getClass: function() {return "SampleFactory";}
};

function GeneFactory() {
}

GeneFactory.prototype = {
	gene_map: {},

	getGene: function(gene_name, entity_map) {
		if (!gene_map[gene_name]) {
			gene_map[gene_name] = new Gene(gene_name, entity_map);
		}
		return gene_map[gene_name];
	},

	getClass: function() {return "GeneFactory";}
};
*/


//
// Session class
//

function Session(name) {
	this.name = name;
}

Storage.prototype.setObject = function(key, value) {
	console.log("JSON: " + JSON.stringify(value));
    this.setItem(key, JSON.stringify(value));
}

Storage.prototype.getObject = function(key) {
    var value = this.getItem(key);
    return value && JSON.parse(value);
}

Session.prototype = {
	name: "",
	data: null,

	setData: function(data) {
		//jQuery.localStorage(this.name, data);
		localStorage.setObject(this.name, data);
	},

	getData: function() {
		//return jQuery.localStorage(this.name);
		return localStorage.getObject(this.name);
	},

	exists: function() {
		//return jQuery.localStorage(this.name) !== null;
		return localStorage.getObject(this.name) !== null;
	}
		
};

function NavicellSession(name) {
	this.session = new Session(name);
}

NavicellSession.prototype = {

	reset: function() {
		this.session.setData(null);
	},

	read: function() {
		navicell = this.session.getData();
	},

	write: function() {
		this.session.setData(navicell);
	},

	init: function() {
		var _navicell = this.session.getData();
		//console.log("session init: " + _navicell);
		if (_navicell) {
			//data.dataset.prototype = Dataset.prototype;
			_navicell.dataset.geneCount = Dataset.prototype.geneCount;
			//_navicell.mapdata = Mapdata.prototype;
			/*
			console.log("session init2: " + data.dataset);
			console.log("session init3: " + data.dataset.genes);
			console.log("session init3.2: " + data.biotype_factory);
			console.log("session init3.3: " + mapSize(data.biotype_factory.biotypes));
			console.log("session init4: " + mapSize(data.dataset.genes));
			console.log("session init4: " + data.dataset.geneCount());
			*/
			return _navicell;
		}
		return navicell_init();
	}
}

function load_mapdata(url, module_name) {
	navicell.module_names.push(module_name);
	$.ajax(url,
	       {
		       async: true,
		       dataType: 'json',

		       success: function(mapdata) {
			       navicell.mapdata.addModuleMapdata(module_name, mapdata);
			       console.log("navicell: " + module_name + " mapdata loaded");
		       },

		       error: function() {
			       console.log("navicell: error loading " + module_name + " mapdata");
		       }
	       }
	      );
}

//var navicell_session = new NavicellSession("navicell");
//navicell_session.reset();

function navicell_init() {
	var _navicell = {}; // namespace

	_navicell.module_names = [];
	_navicell.mapdata = new Mapdata();
	_navicell.dataset = new Dataset("navicell");
	_navicell.group_factory = new GroupFactory();
	_navicell.biotype_factory = new BiotypeFactory();
	_navicell.annot_factory = new AnnotationFactory();
	_navicell.heatmap_config = new HeatmapConfig();

	_navicell.CONTINUOUS = new BiotypeType("continuous");
	_navicell.DISCRETE = new BiotypeType("discrete");
	_navicell.SET = new BiotypeType("set");

	_navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Copy number data mRNA, microRNA", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: methylation profiles", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: histone modifications", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Polymorphism data: SNPs", _navicell.DISCRETE));
	_navicell.biotype_factory.addBiotype(new Biotype("Mutation data: gene re-sequencing", _navicell.DISCRETE));
	_navicell.biotype_factory.addBiotype(new Biotype("Interaction data", _navicell.SET));
	_navicell.biotype_factory.addBiotype(new Biotype("Set data", _navicell.SET));

	_navicell.getDatatableById = function(id) {
		console.log("this.dataset: " + this.dataset.getClass() + " " + id);
		console.log("-> datatable: " + this.dataset.getDatatableById(id));
		console.log("-> datatable class: " + this.dataset.getDatatableById(id).getClass());
		return this.dataset.getDatatableById(id);
	}

	return _navicell;
}

var navicell;

if (typeof navicell == 'undefined') {
	navicell = navicell_init();
}

// .....................................................
// unit test
// .....................................................

/*
console.log("mapdata class: " + mapdata.getClass());

var group1 = group_factory.getGroup(new Annotation("Metrics"), "M");
var group2 = group_factory.getGroup(new Annotation("Metrics"), "normal");
var group3 = group_factory.getGroup(new Annotation("Metrics"), "M");

console.log("group1: " + group1.name);
console.log("group2: " + group2.name);
console.log("group3: " + group3.name);

console.log("group1vs2: " + (group1 == group2));
console.log("group1vs3: " + (group1 == group3));
*/
