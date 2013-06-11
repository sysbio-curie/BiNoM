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
	this.genes = {};
	this.datatable_id = 1;
	this.datatables = {};
	this.datatables_id = {};
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

	getSample: function(sample_name) {
		return this.samples[sample_name];
	},
	
	// behaves as a sample factory
	addSample: function(sample_name) {
		if (!this.samples[sample_name]) {
			this.samples[sample_name] = new Sample(sample_name);
		} else {
			this.samples[sample_name].refcnt++;
		}
		return this.samples[sample_name];
	},

	// behaves as a gene factory
	addGene: function(gene_name, entity_map) {
		if (!this.genes[gene_name]) {
			this.genes[gene_name] = new Gene(gene_name, entity_map);
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
		return true;
	},

	getClass: function() {return "Dataset";}
};

//
// Datatable class
//
// Encapsulate datatable contents (but only for genes existing in map) and type
//

// TBD datatable id management
function Datatable(dataset, biotype_name, name, file, datatable_id) {
	if (dataset.datatables[name]) {
		this.error = "datatable " + name + " already exists";
		return;
	}
	this.error = "";
	this.id = datatable_id;
	this.dataset = dataset;
	this.biotype = navicell.biotype_factory.getBiotype(biotype_name);

	this.setName(name);

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

		//dataset.addDatatable(datatable);

		//console.log("done: " + dataset.datatableCount() + " " + dataset.geneCount() + " " + dataset.sampleCount());
		ready.resolve();
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
		var pos_arr = [];
		for (var gene_name in this.gene_index) {
			var entity_map = this.dataset.genes[gene_name].entity_map;
			var entity = entity_map[module_name];
			if (entity) {
				var modif_arr = entity.modifs;
				if (modif_arr) {
					for (var nn = 0; nn < modif_arr.length; ++nn) {
						var modif = modif_arr[nn];
						id_arr.push(modif.id);
						// >> getting positions
						var positions = modif.positions;
						if (positions) {
							for (var kk = 0; kk < positions.length; ++kk) {
								pos_arr.push({id : modif.id, p : new google.maps.Point(positions[kk].x, positions[kk].y)});
							}
						}
						// << getting positions
					}
				}
			}
		}
		//console.log("pos_arr: " + pos_arr.length);
		_window.show_markers(id_arr);
	},

	setName: function(name) {
		this.name = name;
		this.html_name = name.replace(/ /g, "&nbsp;");
	},

	getId: function() {return this.id;},

	getClass: function() {return "Datatable";}
};

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
			console.log("reading annot file");
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
					//annot_factory.sample_annotated++;
				}
				/*
				var sample = navicell.dataset.getSample(sample_name);
				if (sample) {
					for (var annot_nn = 0; annot_nn < annot_cnt; ++annot_nn) {
						var annot_value = line[annot_nn+1];
						var annot_name = annots[annot_nn].name;
						sample.addAnnotValue(annot_name, annot_value);
						//navicell.group_factory.getGroup(annot_name, annot_value);
					}
					annot_factory.sample_annotated++;
				}
				*/
				annot_factory.sample_read++;
			}
			//navicell.group_factory.buildGroups();
			annot_factory.sample_annotated = annot_factory.sync();
			ready.resolve();
			console.log("annot_cnt: " + annot_cnt);
			console.log("sample_cnt: " + sample_cnt);
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

function Sample(name) {
	this.name = name;
	this.annots = {};
	this.refcnt = 1;
	this.groups = {};
}

Sample.prototype = {
	name: "",
	annots: {},
	groups: {},

	addAnnotValue: function(annot_name, value) {
		//console.log("sample: " + this.name + " add annot value: '" + annot + "' '" + value + "'");
		this.annots[annot_name] = value;
	},

	getGroup_old: function(annot_name) {
		var annot_value = this.annots[annot_name];
		if (annot_value != undefined) {
			return navicell.group_factory.getGroup(annot_name, annot_value);
		}
		return null;
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

	getClass: function() {return "Sample";}
};

//
// Gene class
//

function Gene(name, entity_map) {
	this.name = name;
	this.entity_map = entity_map;
	this.refcnt = 1;
}

Gene.prototype = {
	name: "",
	entity_map: {},

	getClass: function() {return "Gene";}
};

//
// Group class
//

/*
function Group(annot_name, value) {
	this.annot_name = annot_name;
	this.value = value;
	this.name = navicell.group_factory.buildName(annot_name, value);
	this.html_name = '<span class="group_name">' + annot_name.replace(/ /g, '&nbsp;') + ':</span>&nbsp;<span class="group_value">' + value.replace(/ /g, '&nbsp;')  + '</span>';
}

Group.prototype = {
	annot_name: null,
	value: null,
	name: "",
	html_name: "",

	getClass: function() {return "Group";}
};
*/

function Group(annots, values) {
	this.annots = annots;
	this.values = values;
	this.name = navicell.group_factory.buildName(annots, values);
	this.html_name = "";
	for (var nn = 0; nn < annots.length; ++nn) {
		this.html_name += (this.html_name.length > 0 ? "<br>" : "") + '<span class="group_name">' + annots[nn].replace(/ /g, '&nbsp;') + ':</span>&nbsp;<span class="group_value">' + values[nn].replace(/ /g, '&nbsp;')  + '</span>';
	}
}

Group.prototype = {
	annots: [],
	values: [],
	name: "",
	html_name: "",

	getClass: function() {return "Group";}
};


//
// GroupFactory class
//

function GroupFactory() {
}

GroupFactory.prototype = {
	group_map: {},

	buildName_old: function(annot_name, value) {
		return annot_name + ": " + value;
	},

	addGroup_old: function(annot_name, value) {
		var group_name = this.buildName(annot_name, value);
		if (!this.group_map[group_name]) {
			this.group_map[group_name] = new Group(annot_name, value);
		}
	},

	buildGroups_old: function() {
		this.group_map = {};
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			for (var annot_name in sample.annots) {
				var annot = navicell.annot_factory.annots_per_name[annot_name];
				if (annot.is_group) {
					this.addGroup(annot_name, sample.annots[annot_name]);
				}
			}
		}
	},

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
			this.group_map[group_name] = new Group(group_annots, group_values);
		}
		return this.group_map[group_name];
	},

	getGroup: function(group_annots, group_values) {
		var group_name = this.buildName(group_annots, group_values);
		return this.group_map[group_name];
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

//var navicell_session = new NavicellSession("navicell");
//navicell_session.reset();

function navicell_init() {
	var _navicell = {}; // namespace

	_navicell.mapdata = new Mapdata();
	_navicell.dataset = new Dataset("navicell");
	_navicell.group_factory = new GroupFactory();
	_navicell.biotype_factory = new BiotypeFactory();
	_navicell.annot_factory = new AnnotationFactory();

	_navicell.CONTINUOUS = 1;
	_navicell.DISCRETE = 2;
	_navicell.SET = 3;

	_navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Copy number data mRNA, microRNA", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: methylation profiles", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: histone modifications", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Polymorphism data: SNPs", _navicell.DISCRET));
	_navicell.biotype_factory.addBiotype(new Biotype("Mutation data: gene re-sequencing", _navicell.DISCRETE));
	_navicell.biotype_factory.addBiotype(new Biotype("Interaction data", _navicell.SET));
	_navicell.biotype_factory.addBiotype(new Biotype("Set data", _navicell.SET));

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
