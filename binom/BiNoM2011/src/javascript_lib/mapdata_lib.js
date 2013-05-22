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
}

Dataset.prototype = {
	name: "",
	datatables: {},
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

	addDatatable: function(datatable) {
		this.datatables[datatable.name] = datatable;
	},

	addSample: function(sample_name) {
		if (!this.samples[sample_name]) {
			this.samples[sample_name] = new Sample(sample_name);
		}
	},

	addGene: function(gene_name, entity_map) {
		if (!this.genes[gene_name]) {
			this.genes[gene_name] = new Gene(gene_name, entity_map);
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
		datatable.name = new_datatable_name;
		datatable.biotype = navicell.biotype_factory.getBiotype(new_datatable_type);
		this.datatables[new_datatable_name] = datatable;
		return true;
	},

	getClass: function() {return "Dataset";}
};

//
// Datatable class
//
// Encapsulate datatable contents (but only for genes existing in map) and type
//

function Datatable(dataset, biotype_name, name, file) {
	if (dataset.datatables[name]) {
		this.error = "datatable " + name + " already exists";
		return;
	}
	this.dataset = dataset;
	this.biotype = navicell.biotype_factory.getBiotype(biotype_name);

	this.name = name;

	this.gene_index = {};
	this.sample_index = {};
	this.data = [];

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
				datatable.data[gene_nn][sample_nn] = value;
			}
			++gene_nn;
		}

		dataset.addDatatable(datatable);

		//console.log("done: " + dataset.datatableCount() + " " + dataset.geneCount() + " " + dataset.sampleCount());
		ready.resolve();
	}

	reader.onerror = function(e) {  // If anything goes wrong
		console.log("Error", e);    // Just log it
	};
}

Datatable.prototype = {
	dataset: null,
	biotype: null,
	name: "",
	gene_index: {},
	sample_index: {},
	data: [],
	ready: null,

	display_markers: function(module_name) {
		var id_arr = [];
		//		for (var gene_name in this.dataset.genes) {
		for (var gene_name in this.gene_index) {
			var entity_map = this.dataset.genes[gene_name].entity_map;
			var entity = entity_map[module_name];
			if (entity) {
				var modif_arr = entity.modifs;
				if (modif_arr) {
					for (var nn = 0; nn < modif_arr.length; ++nn) {
						id_arr.push(modif_arr[nn].id);
					}
				}
			}
		}
		show_markers(id_arr);
	},

	getClass: function() {return "Datatable";}
};

//
// Annotation class
//

function Annotation(name) {
	this.name = name;
}

Annotation.prototype = {
	name: "",
	is_group: true,
	desc: "",

	setIsGroup: function(is_group) {
		this.is_group = is_group;
	},

	getClass: function() {return "Annotation";}
};

//
// Sample class
//

function Sample(name) {
	this.name = name;
}

Sample.prototype = {
	name: "",
	annots: {},

	addAnnotValue: function(annot, value) {
		this.annots[annot] = value;
	},

	getClass: function() {return "Sample";}
};

//
// Gene class
//

function Gene(name, entity_map) {
	this.name = name;
	this.entity_map = entity_map;
}

Gene.prototype = {
	name: "",
	entity_map: {},

	getClass: function() {return "Gene";}
};

//
// Group class
//

function Group(annot, value) {
	this.annot = annot;
	this.value = value;
	this.name = annot.name + ": " + value;
}

Group.prototype = {
	annot: null,
	value: null,
	name: "",
	samples: [],

	addSample: function(sample) {
		samples.push(sample);
	},

	getClass: function() {return "Group";}
};

//
// GroupFactory class
//

function GroupFactory() {
}

GroupFactory.prototype = {
	group_map: {},

	buildName: function(annot, value) {
		return annot + ": " + value;
	},

	getGroup: function(annot, value) {
		var name = this.buildName(annot, value);
		if (!this.group_map[name]) {
			this.group_map[name] = new Group(annot, value);
		}
		return this.group_map[name];
	},

	getClass: function() {return "GroupFactory";}
};

function Biotype(name, type) {
	this.name = name;
	this.type = type;
}

Biotype.prototype = {
	name: "",
	type: null
};

function BiotypeType(type) {
	this.type = type;
}

BiotypeType.prototype = {
	type: 0,

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

function BiotypeFactory() {
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
// Main objects instantiation
//

var navicell = {}; // namespace

navicell.mapdata = new Mapdata();
navicell.dataset = new Dataset("navicell");
navicell.group_factory = new GroupFactory();
navicell.biotype_factory = new BiotypeFactory();

navicell.CONTINUOUS = 1;
navicell.DISCRETE = 2;
navicell.SET = 3;

navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("Copy number data mRNA, microRNA", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: methylation profiles", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: histone modifications", navicell.CONTINUOUS));
navicell.biotype_factory.addBiotype(new Biotype("Polymorphism data: SNPs", navicell.DISCRET));
navicell.biotype_factory.addBiotype(new Biotype("Mutation data: gene re-sequencing", navicell.DISCRETE));
navicell.biotype_factory.addBiotype(new Biotype("Interaction data", navicell.SET));
navicell.biotype_factory.addBiotype(new Biotype("Set data", navicell.SET));

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
