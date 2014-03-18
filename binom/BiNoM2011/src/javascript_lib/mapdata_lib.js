/*
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

var GLYPH_COUNT = 5;

if (!window.console) {
	window.console = new function()
	{
		this.log = function(str) {};
		this.dir = function(str) {};
	};
}

function mapSize(map) {
	var size = 0;
	for (var key in map) {
		size++;
	}
	return size;
}

function mapValues(map) {
	var values = [];
	for (var key in map) {
		values.push(map[key]);
	}
	return values;
}

function mapKeys(map) {
	var keys = [];
	for (var key in map) {
		keys.push(key);
	}
	return keys;
}

function mapDisplay(map, msg) {
	if (!msg) {
		msg = '';
	} else {
		msg += ' ';
	}
	console.log("Map " + msg + "{");
	for (var key in map) {
		console.log("  " + key + " => " + map[key]);
	}
	console.log("}");
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

var INPUT_SEPS = ["\t", ";", ",", " "];
var COLOR_SIZE_CONFIG = "color_size";
var EDITING_CONFIGURATION = "configuration not saved...";
//var NO_SAMPLE = "<span style='font-style: italic; font-size: smaller'>[NO&nbsp;SAMPLE]</span>";
var NO_SAMPLE = "<span style='font-style: italic; font-size: smaller'>[empty]</span>";
//var GENE_SET = "<span style='font-style: italic; font-size: smaller'>[GENE]</span>";
var GENE_SET = "[GENE]";
var INVALID_VALUE = '* INVALID *';

function load_info(url, module_name)
{
	navicell.mapdata.info_ready[module_name] = new $.Deferred();
	var SIMULATE_HEAVY_LOAD_INFO = false;
	if (SIMULATE_HEAVY_LOAD_INFO) {
		for (var nn = 0; nn < 15; ++nn) {
			$.ajax(url,
			       {
				       async: true,
				       dataType: 'json',
				       cache: false,
				       success: function(data) {
					       console.log("navicell: info [" + url + "] loaded #" + nn);
				       }
			       });
		}
	}

	$.ajax(url,
	       {
		       async: true,
		       dataType: 'json',
		       
		       success: function(data) {
			       console.log("navicell: info [" + url + "] loaded !");
			       navicell.mapdata.addInfo(module_name, data);
		       },
			       
		       error: function() {
			       console.log("navicell: error loading [" + url + "] jxtree");
		       }
	       }
	      );
}

//
// Mapdata class
//
// Encapsulate all module entities, including map positions
//

function RGBColor(red, green, blue) {
	this.red = red;
	this.green = green;
	this.blue = blue;
}

RGBColor.prototype = {
	setRGB: function(red, green, blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	},

	getRed: function() {
		return this.red;
	},

	getGreen: function() {
		return this.green;
	},

	getBlue: function() {
		return this.blue;
	},

	toHex: function(cc) {
		var str = cc.toString(16).substr(0, 2);
		while (str.length < 2) {
			str = "0" + str;
		}
		return str.toUpperCase();
	},

	getRGBValue: function() {
		return this.toHex(this.red) + this.toHex(this.green) + this.toHex(this.blue);
	}
};

function color_gradient(color1, color2, steps) {
	var steps_1 = (steps > 1 ? steps*1. - 1 : 1);
	var gradients = [];
        for (ii = 0; ii < steps; ii++) {
		var ratio = ii/steps_1;
		var red = color2.getRed() * ratio + color1.getRed() * (1 - ratio);
		var green = color2.getGreen() * ratio + color1.getGreen() * (1 - ratio);
		var blue = color2.getBlue() * ratio + color1.getBlue() * (1 - ratio);
		gradients.push(new RGBColor(red&255, green&255, blue&255));
	}
	return gradients;
}

var jxtree_mapfun_map = {};

jxtree_mapfun_map['label'] = function(datanode) {
	if (datanode.name) {
		return datanode.name;
	}
	if (!datanode.label) {
		console.log("NULL LABEL " + datanode + " " + mapSize(datanode));
		for (var key in datanode) {
			console.log(key + " -> " + datanode[key].length);
		}
		return datanode["class"];
	}
	return datanode.label;
}

jxtree_mapfun_map['left_label'] = function(datanode) {
	var left_label = datanode.left_label;
	if (left_label) {
		if (left_label.indexOf("blog:") == 0) {
			var alt = left_label.substr(5);
			left_label = "<a href='#'><img align=\"top\" class=\"blogfromright\" border=\"0\" src=\"../../../map_icons/misc/blog.png\" alt=\"" + alt + "\"/></a>";
		}
	}
	return left_label;
}

jxtree_mapfun_map['right_label'] = function(datanode) {
	return datanode.right_label;
}

jxtree_mapfun_map['data'] = function(datanode) {
	if (datanode.data) {
		return datanode.data;
	}
	if (datanode.id) {
		return {id: datanode.id, cls: datanode["class"], modifs: datanode.modifs, included: datanode.a};
	}
	if (datanode.name) {
		return {id: datanode.name, cls: datanode["class"], modifs: datanode.modifs, included: datanode.a};
	}
	return null;
}

jxtree_mapfun_map['children'] = function(datanode) {
	if (datanode.entities)  {
		return datanode.entities;
	}
	if (datanode.modifs) {
		return datanode.modifs;
	}
	if (datanode.children) {
		return datanode.children;
	}
	if (datanode.modules) {
		return datanode.modules;
	}
	if (datanode.maps) {
		return datanode.maps;
	}
	return datanode.children;
}

jxtree_mapfun_map['id'] = function(datanode) {
	return datanode.id;
}

function jxtree_mapfun(datanode, field) {
	var mapfun = jxtree_mapfun_map[field];
	return mapfun ? mapfun(datanode) : null;
}

function jxtree_get_node_class(node, included) {
	var data = node.getUserData();
	if (data && data.cls) {
		return data.cls + (included ? ":INCLUDED" : "");
	}
	if (node.getParent()) {
		return jxtree_get_node_class(node.getParent(), included || data.included);
	}
	return null;
}

// TBD: 2013-10-29: find function to modify !
// searching perharps not in the good place, and, at least, jxtree find is not correct with nodes
// must compare to jstree searching !

function jxtree_user_find(matcher, node) {
	var data = node.getUserData();
	if (data && data.id) {
		if ((matcher.search_in & JXTreeMatcher.IN_ANNOT) != 0) {
			var info = navicell.mapdata.getInfo(node.jxtree.module_name, data.id);
			if (info) {
				if (matcher.match(info)) {
					return true;
				}
			}
		}
		if ((matcher.search_in & JXTreeMatcher.IN_TAG) != 0) {
			var tag_map = navicell.mapdata.getTagMap(node.jxtree.module_name, data.id);
			if (tag_map) {
				for (var tag in tag_map) {
					if (matcher.match(tag)) {
						return true;
					}
				}
			}
		}
	}
	return false;
}

function jxtree_user_find_id(matcher, node) {
        var data = node.getUserData();
        if (data && data.id) {
                if (matcher.match(data.id)) {
                        return true;
                }
        }
        return false;
}

function jxtree_user_find_regex(matcher, regex, node) {
	var data = node.getUserData();
	if (data && data.id) {
		if ((matcher.search_in & JXTreeMatcher.IN_ANNOT) != 0) {
			var info = navicell.mapdata.getInfo(node.jxtree.module_name, data.id);
			if (info) {
				if (info.match(regex)) {
					return true;
				}
			}
		}
		if ((matcher.search_in & JXTreeMatcher.IN_TAG) != 0) {
			var tag_map = navicell.mapdata.getTagMap(node.jxtree.module_name, data.id);
			if (tag_map) {
				for (var tag in tag_map) {
					if (tag.match(regex)) {
						return true;
					}
				}
			}
		}
	}
	return false;
}

function jxtree_user_find_id_regex(matcher, regex, node) {
        var data = node.getUserData();
        if (data && data.id) {
		if (data.id.match(regex)) {
                        return true;
                }
        }
        return false;
}

function Mapdata(to_load_count) {
	this.to_load_count = to_load_count;

	this.is_ready = false;
	this.ready = {};
	this.is_ready = {};
	this.straight_data = {};
	this.info_ready = {};
	this.deferred_module_bubble = {};
	this.module_postid = {};
//	this.reset();
}

//var CLEAN_HTML_REGEX = new RegExp("<[^<>]+>", "g");
//var CLEAN_HTML_REGEX_NBSP = new RegExp("&nbsp;", "g");
var CLEAN_HTML_REGEX = new RegExp("<[^<>]+>|&nbsp;", "g");
//var TAG_REGEX = new RegExp("\\w+:\\w+", "g");
//var TAG_REGEX = new RegExp("\\w+:\\w+|/\\w+", "g");
var TAG_REGEX = new RegExp(">\\w+:\\w+</a>|&nbsp;(\\w| )+&nbsp;", "g");
var TAG_CLEAN_REGEX = new RegExp("</a>|&nbsp;|>", "g");
//var LINE_BREAK_REGEX = /[\r\n]+/;
var LINE_BREAK_REGEX = /\r\n?|\n/;

var time_cnt = 0;

Mapdata.prototype = {
	// Mapdata for each module
	module_mapdata: {},
	module_mapdata_by_id: {},
	module_info: {},
	module_tag_map: {},
	module_bubble: {},
	module_jxtree: {},
	module_res_jxtree: {},
	module_classes: {},
	module_ckmap: {},
	class_list: {},

	// Hashmap from hugo name to entity information (including positions)
	hugo_map: {},

	getPostModuleLink: function(postid) {
		return this.module_postid[postid];
		/*
		var alt = this.module_postid[postid];
		if (alt) {
			return "<a href='#'><img align=\"top\" class=\"blogfromright\" border=\"0\" src=\"../../../map_icons/misc/blog.png\" alt=\"" + alt + "\"/></a>";
		}
		return null;
		*/
	},

	getJXTree: function(module_name) {
		return this.module_jxtree[module_name];
	},

	getResJXTree: function(module_name) {
		return this.module_res_jxtree[module_name];
	},

	useJXTreeSimpleFind: function(jxtree) {
		var user_find = jxtree.user_find;
//		jxtree.userFind(jxtree_user_find_id);
		jxtree.userFind(jxtree_user_find_id_regex);
		return user_find;
	},

	restoreJXTreeFind: function(jxtree, user_find) {
		jxtree.userFind(user_find);
	},

	// no effect... should fixed that
	searchFor: function(win, what, div) {
		if (what.trim() == "/?") {
			return;
		}
		$("#result_tree_header", win.document).html("Searching for \"" + what + "\"...");
		if (div) {
			$(div, win.document).css("display", "none");
		}
	},

	//findJXTreeContinue: function(jxtree, to_find, no_ext, win, action, hints, module_name) {
	findJXTreeContinue: function(win, to_find, no_ext, action, hints) {
		var module_name = win.document.navicell_module_name;
		var mapdata = this;
		var jxtree = mapdata.module_jxtree[module_name];

		if (no_ext) {
			var user_find = mapdata.useJXTreeSimpleFind(jxtree);
			res_jxtree = jxtree.find(to_find, action, hints);
			mapdata.restoreJXTreeFind(jxtree, user_find);
		} else {
			res_jxtree = jxtree.find(to_find, action, hints);
		}

		if (res_jxtree == null) {
			if (hints.error || hints.help) {
				var dialog = $("#info_dialog", win.document);
				var msg = hints.error ? hints.error : hints.help;
				var title = hints.error ? "Searching Error" : "Search Help";
				dialog.html("<div style='text-align: vertical-center'><h3>" + title + "</h3>" + msg.replace(new RegExp("\n", "g"), "<br>") + "</div>");
				dialog.dialog({
					resizable: true,
					width: 430,
					height: 750,
					modal: true,
					title: title,
					buttons: {
						"OK": function() {
							$(this).dialog("close");
						}
					}
				});
			}
			return;
		}

		if (action == 'subtree' && res_jxtree) {
			if (!no_ext) {
				$("#result_tree_header", win.document).html(res_jxtree.found + " elements matching \"" + to_find + "\"");
			}

			uncheck_all_entities(win);

			res_jxtree.context = {win: win};

			tree_context_prologue(res_jxtree.context);
			$.each(res_jxtree.getRootNodes(), function() {
				this.checkSubtree(JXTree.CHECKED);
				this.showSubtree(JXTree.OPEN);
			});
			tree_context_epilogue(res_jxtree.context);

			if (hints.div) {
				$(hints.div, win.document).css("display", "block");
			}

			time_cnt++;
			mapdata.module_res_jxtree[module_name] = res_jxtree;
			$("img.blogfromright", win.document).click(open_blog_click);
			$("img.mapmodulefromright", win.document).click(open_module_map_click);
		}
	},

	findJXTree: function(win, to_find, no_ext, action, hints) {
		var module_name = win.document.navicell_module_name;
		var mapdata = this;
		if (!hints) {
			hints = {};
		}
		hints.class_list = this.class_list;
		this.whenInfoReady(module_name, function() {
			var jxtree = mapdata.module_jxtree[module_name];
			var res_jxtree = null;
			// so ?
			if (no_ext) {
				var to_find_str = "^(";
				for (var nn = 0; nn < to_find.length; ++nn) {
					if (nn) {
						to_find_str += '|';
					}
					to_find_str += to_find[nn];
				}
				to_find_str += ")$";
				//console.log("to_find_str [" + to_find_str + "]");
				hints.case_sensitive = true;
				mapdata.searchFor(win, to_find_str, hints.div);
				//mapdata.findJXTreeContinue(jxtree, to_find_str, no_ext, win, action, hints, module_name);
				mapdata.findJXTreeContinue(win, to_find_str, no_ext, action, hints);
			} else {
				mapdata.searchFor(win, to_find, hints.div);
				setTimeout(function() {
					//mapdata.findJXTreeContinue(jxtree, to_find, no_ext, win, action, hints, module_name);
					mapdata.findJXTreeContinue(win, to_find, no_ext, action, hints);
				}, 20);
			}

			return;
		});
	},

	addInfo: function(module_name, info) {
		this.module_bubble[module_name] = info;
		var info_map = {};
		var tag_map = {};
		for (var id in info) {
			var info_id = info[id];
			info_map[id] = info_id.replace(CLEAN_HTML_REGEX, " ");
			// tag detection is fragile as it is based on the fuzzy syntax of information;
			// on the other hand, information is automatically generated by the factory,
			// so I guess it should work as long as info generation is not modified in factory
			var arr = info_id.match(TAG_REGEX);
			if (arr && arr.length) {
				var tmap = {};
				for (var nn = 0; nn < arr.length; ++nn) {
					var tag = arr[nn].replace(TAG_CLEAN_REGEX, "");
					//console.log("tag [" + tag + "]");
					tmap[tag] = 1;
				}
				tag_map[id] = tmap;
			}
		}
		this.module_info[module_name] = info_map;
		this.module_tag_map[module_name] = tag_map;
		this.info_ready[module_name].resolve();
	},

	getInfo: function(module_name, id) {
		var info = this.module_info[module_name];
		if (info) {
			return info[id];
		}
		return null;
	},

	getTagMap: function(module_name, id) {
		var tag_map = this.module_tag_map[module_name];
		if (tag_map) {
			return tag_map[id];
		}
		return null;
	},

	getClass: function(module_name, id) {
		var classes = this.module_classes[module_name];
		if (classes) {
			return classes[id];
		}
		return null;
	},

	getBubble: function(module_name, id) {
		var bubble = this.module_bubble[module_name];
		if (bubble) {
			return bubble[id];
		}
		return null;
	},

	isReady: function(module_name) {
		return this.is_ready[module_name];
	},

	whenReady: function(module_name, f) {
		var ready = this.ready[module_name];
		if (!ready) {
			console.log("ARG for [" + module_name + "]");
		}
		ready.then(f);
	},

	whenInfoReady: function(module_name, f) {
		var ready = this.info_ready[module_name];
		ready.then(f);
	},

	buildEntityTreeWhenReady: function(win, div_name, projection, whenloaded) {
		var map = win.map;
		var module_name = map.map_name;
		win.console.log("buildEntityTreeWhenReady module_name: " + module_name);
		if (this.isReady(module_name)) {
			this.buildEntityTree(win, div_name, projection, whenloaded);
		} else {
			var mapdata = this;
			this.whenReady(module_name, function() {
				mapdata.buildEntityTree(win, div_name, projection, whenloaded);
				console.log("TREE is ready");
			});
		}
	},

	buildEntityTree: function(win, div_name, projection, whenloaded) {
		//var module_map = this.module_mapdata[module_name];
		var map = win.map;
		var module_name = map.map_name;
		var data = this.straight_data[module_name];
		win.console.log("building tree for " + module_name + " " + div_name + " " + $(div_name, win.document).get(0));

		var datatree;
		//if (module_name == "master") {
		win.console.log("children: " + mapSize(data));
		if (module_name.match(/master$/)) {
			//if (!this.maps_modules) {
			//this.maps_modules = data[data.length-1];
			//data.pop();
			//}
			//datatree = [this.maps_modules, {name: 'Entities', children: data}];
			// warning: I think this leads to a bug...
			var maps_modules = data[data.length-1];
			var cls = maps_modules["class"];
			if (cls == "MAP" || cls == "MODULE") {
				data.pop();
				datatree = [maps_modules, {name: 'Entities', children: data}];
			} else {
				datatree = {name: 'Entities', children: data};
			}
		} else {
			datatree = {name: 'Entities', children: data};
		}

		var jxtree = new JXTree(win.document, datatree, $(div_name, win.document).get(0), jxtree_mapfun);
		$.each(jxtree.getRootNodes(), function() {
			this.toggleOpen();
		});

		//jxtree.userFind(jxtree_user_find);
		jxtree.userFind(jxtree_user_find_regex);
		win.console.log("JXTREE count: " + jxtree.getNodeCount());

		jxtree.context = {win: win};

		var mapdata = this;
		jxtree.onClickBefore(function(node, checked) {
			win.tree_node_click_before(node.jxtree.context, checked);
		});

		jxtree.onClickAfter(function(node, checked) {
			win.tree_node_click_after(node.jxtree.context, checked);
			overlay.draw(module_name);
		});

		this.deferred_module_bubble[module_name] = {};
		this.module_classes[module_name] = {};

		jxtree.checkStateChanged(function(node, state) {
			var data = node.getUserData();
			//console.log("checking node: " + (data ? data.id : null));
			if (data && data.id) {
				var checked = state == JXTree.CHECKED;
				if (!data.clickmap_tree_node) {
					var info = mapdata.getMapdataById(module_name, data.id);
					if (info && info.positions) {
						//win.console.log("state changed: " + node.label + " " + data.id + " " + jxtree_get_node_class(node) + " " + map.map_name);
						var cls = jxtree_get_node_class(node);
						data.clickmap_tree_node = new win.ClickmapTreeNode(map, module_name, data.id, cls, node.label, info.positions, mapdata);
						mapdata.module_classes[module_name][data.id] = cls;
					} else {
						//console.log("no info for [" + module_name + "][" + data.id + "]");
					}
				}					
				//console.log((checked ? "checked " : "unchecked") + data.id);
				win.tree_node_state_changed(node.jxtree.context, data.clickmap_tree_node, checked);
			}
		});
		jxtree.module_name = module_name;
		this.module_jxtree[module_name] = jxtree;

		whenloaded();

		this.whenInfoReady(module_name, function() {
			var deferred_module_bubble = mapdata.deferred_module_bubble[module_name];
			for (var id in deferred_module_bubble) {
				var bubble_list = deferred_module_bubble[id];
				if (bubble_list) {
					var bubble = mapdata.getBubble(module_name, id);
					for (var nn = 0; nn < bubble_list.length; ++nn) {
						bubble_list[nn].setContent("<div class=\"info_window\">" + bubble + "</div>");
					}
				}
			}
				
		});
	},

	setBubbleContent: function(bubble, module_name, data_id) {
		var bubble_content = this.getBubble(module_name, data_id);
		if (bubble_content) {
			bubble.setContent("<div class=\"info_window\">" + bubble_content + "</div>");
		} else {
			bubble.setContent("Loading data...");
			console.log("LOADING DATA [" + module_name + "][" + data_id + "]");
			if (!this.deferred_module_bubble[module_name][data_id]) {
				this.deferred_module_bubble[module_name][data_id] = [];
			}
			this.deferred_module_bubble[module_name][data_id].push(bubble);
		}
	},

	// Returns the size of the hugo map
	entityCount: function() {
		return mapSize(this.hugo_map);
	},

	getMapdataById: function(module_name, modif_id) {
		return this.module_mapdata_by_id[module_name][modif_id];
	},

	// Adds a module mapdata: fills the hugo_map hashmap
	addModuleMapdata: function(module_name, module_mapdata) {

		this.module_mapdata[module_name] = module_mapdata;
		this.module_mapdata_by_id[module_name] = {};

		var ckmap = {};
		for (var ii = 0; ii < module_mapdata.length; ++ii) {
			var maps = module_mapdata[ii].maps;
			var modules = module_mapdata[ii].modules;
			var entities = module_mapdata[ii].entities;
			var postinf = module_mapdata[ii]["postinf"];
			if (module_mapdata[ii]["class"]) {
				this.class_list[module_mapdata[ii]["class"]] = true;
			}
			if (postinf) {
				//console.log("GOT up level post information: " + postinf);
				postinf = postinf.split(" ");
				this.module_postid[postinf[0]] = postinf[1];
			}
			//console.log("modules " + modules + " " + entities + " " + module_mapdata[ii]["class"]);
			//console.log(module_mapdata[ii]["class"]);
			if (maps) {
				//console.log("FOUND maps: " + maps.length);
				for (var jj = 0; jj < maps.length; ++jj) {
					var map = maps[jj];
					//console.log("map.id " + map.id);
					this.module_mapdata_by_id[module_name][map.id] = map;
					var map_modules = map.modules;
					if (map_modules) {
						//console.log("  FOUND map_modules: " + map_modules.length);
						for (var kk = 0; kk < map_modules.length; ++kk) {
							var module = map_modules[kk];
							//console.log("  module.id " + module.id);
							this.module_mapdata_by_id[module_name][module.id] = module;
							//console.log("setting info for [" + module_name + "][" + module.id + "]");
						}
					}
				}
			} else if (modules) {
				//console.log("FOUND modules: " + modules.length);
				for (var jj = 0; jj < modules.length; ++jj) {
					var module = modules[jj];
					//console.log("module.id " + module.id);
					if (module.postinf) {
						//console.log("GOT post information: " + module.postinf);
						var postinf = module.postinf.split(" ");
						this.module_postid[postinf[0]] = postinf[1];
					}
					this.module_mapdata_by_id[module_name][module.id] = module;
				}
			} else if (entities) {
				for (var jj = 0; jj < entities.length; ++jj) {
					var entity_map = entities[jj];
					var hugo_arr = entity_map['hugo'];
					if (hugo_arr && hugo_arr.length > 0) {
						for (var kk = 0; kk < hugo_arr.length; ++kk) {
							var hugo = hugo_arr[kk];
							if (!this.hugo_map[hugo]) {
								this.hugo_map[hugo] = {};
							}
							if (!this.hugo_map[hugo][module_name]) {
								this.hugo_map[hugo][module_name] = [];
							}
							this.hugo_map[hugo][module_name].push(entity_map);
						}
					}
					var modif_arr = entity_map.modifs;
					if (modif_arr) {
						for (var kk = 0; kk < modif_arr.length; ++kk) {
							var modif = modif_arr[kk];
							var o_modif = this.module_mapdata_by_id[module_name][modif.id];
							/*
							if (modif.positions && o_modif && o_modif.positions) {
								console.log("WARNING: should compare positions " + ckmap[modif.id] + " " + modif.positions[0].x + " " + o_modif.positions[0].x);
							}
							*/
							this.module_mapdata_by_id[module_name][modif.id] = modif;
							if (modif.positions) {
								ckmap[modif.id] = [];
								for (var ll = 0; ll < modif.positions.length; ++ll) {
									var pos = modif.positions[ll];
									if (pos.w && pos.h) {
										var box = [pos.x, pos.w, pos.y, pos.h];
										ckmap[modif.id].push(box);
									}
								}
							}
						}
					}
				}
			} else {
				console.log("no entities neither modules");
			}
		}
	
		console.log("module map added " + module_name);
		this.module_ckmap[module_name] = ckmap;
		return !--this.to_load_count;
	},

	getCKMap: function(module_name) {
		return this.module_ckmap[module_name];
	},

	load_mapdata: function(url, module_name) {
		this.ready[module_name] = new $.Deferred();
		this.ready[module_name].then(function() {
			mapdata.is_ready[module_name] = true;
		});

		navicell.module_names.push(module_name);
		var mapdata = this;
		$.ajax(url,
		       {
			       async: true,
			       dataType: 'json',
			       
			       success: function(data) {
				       mapdata.straight_data[module_name] = data;
				       console.log("navicell: " + module_name + " data loaded");
				       if (mapdata.addModuleMapdata(module_name, data)) {
					       //console.log("now all is ready");
					       //mapdata.ready.resolve();
				       }
				       mapdata.ready[module_name].resolve();
			       },
			       
			       error: function() {
				       console.log("navicell: error loading [" + url + "] mapdata");
			       }
		       }
		      );
	},

	getClass: function() {return "Mapdata";}
};

// 2013-10-28: seems to be obsolete
function mapdata_display_markers(module_name, win, hugo_names)
{
	var id_arr = [];
	var arrpos = [];
	for (var nn = 0; nn < hugo_names.length; ++nn) {
		var hugo_module_map = navicell.mapdata.hugo_map[hugo_names[nn]];
		if (!hugo_module_map) {
			console.log("gene " + hugo_names[nn] + " not found");
			continue;
		}
		var entity_map_arr = hugo_module_map[module_name];
		if (!entity_map_arr) {
			console.log("gene " + hugo_names[nn] + " empty");
			continue;
		}
		for (var ii = 0; ii < entity_map_arr.length; ++ii) {
			var entity_map = entity_map_arr[ii];
			var modif_arr = entity_map.modifs;
			if (modif_arr) {
				for (var kk = 0; kk < modif_arr.length; ++kk) {
					var modif = modif_arr[kk];
					// >> getting positions
					var positions = modif.positions;
					if (positions) {
						id_arr.push(modif.id);
					}
					// << getting positions
				}
			}
		}
	}
	win.show_markers(id_arr);
}

//
// Dataset class
//
// Main object gathering all data information
//

function JXTreeScanner(module_name) {
	this.module_name = module_name;
	this.arrpos = [];
}

JXTreeScanner.prototype = {
	scanNode: function(node) {
		if (node.isChecked()) {
			var data = node.getUserData();
			if (data && data.id) {
				var pos = navicell.dataset.getGeneInfoByModifId(this.module_name, data.id);
				if (pos) {
					array_push_all(this.arrpos, pos[1]);
				}
			}
		}
	},

	getArrayPos: function() {
		//console.log("jxtreescanner: " + this.arrpos.length);
		return this.arrpos;
	}
};

function Dataset(name) {
	this.name = name;

	this.genes = {};
	this.genes_id = {};
	this.gene_id = 1;
	this.sorted_gene_names = [];

	this.datatable_id = 1;
	this.datatables = {};
	this.datatables_id = {};

	this.sample_id = 1;
	this.samples = {};
	this.samples_id = {};

	this.modifs_id = {};

	this.module_arrpos = {};
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

	readDatatable: function(biotype_name, name, file, win) {
		var datatable = new Datatable(this, biotype_name, name, file, this.datatable_id++, win);
		var dataset = this;
		datatable.ready.then(function() {
			if (!datatable.error) {
				dataset.addDatatable(datatable);
			}
		});
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
		//console.log("getGeneInfoByModifId module_name: " + module_name + " " + navicell_module_name);
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
				var hugo_module_map = this.genes[gene_name].hugo_module_map;
				var entity_map_arr = hugo_module_map[module_name];
				if (!entity_map_arr) {
					continue;
				}
				for (var ii = 0; ii < entity_map_arr.length; ++ii) {
					var entity_map = entity_map_arr[ii];
					//if (entity) {
					var modif_arr = entity_map.modifs;
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
			//console.log("syncModifs " + module_name + " " + mapSize(this.modifs_id[module_name]));
		}

		this.module_arrpos = {};
		for (var jj = 0; jj < navicell.module_names.length; ++jj) {
			var module_name = navicell.module_names[jj];
			var arrpos = [];
			for (var modif_id in this.modifs_id[module_name]) {
				var gene_info = this.modifs_id[module_name][modif_id];
				array_push_all(arrpos, gene_info[1]);
			}
			this.module_arrpos[module_name] = arrpos;
		}
	},

	getArrayPos: function(module_name) {
		return this.module_arrpos[module_name];
	},

	getSelectedArrayPos: function(module_name) {
		var jxtree = navicell.mapdata.getJXTree(module_name);
		var jxtreeScanner = new JXTreeScanner(module_name);
		jxtree.scanTree(jxtreeScanner);
		return jxtreeScanner.getArrayPos();
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
	
	getSortedGeneNames: function() {
		return this.sorted_gene_names;
	},

	// behaves as a gene factory
	addGene: function(gene_name, hugo_module_map) {
		if (!this.genes[gene_name]) {
			var gene = new Gene(gene_name, hugo_module_map, this.gene_id++);
			this.genes[gene_name] = gene;
			this.genes_id[gene.getId()] = gene;
			this.sorted_gene_names = mapKeys(this.genes);
			this.sorted_gene_names.sort();
		} else {
			this.genes[gene_name].refcnt++;
		}
		return this.genes[gene_name];
	},

	updateDatatable: function(win, datatable_name, new_datatable_name, new_datatable_type) {
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
		var module = get_module(win);
		if (datatable.displayStepConfig[module]) {
			datatable.displayStepConfig[module].update();
		} else if (datatable.displayDiscreteConfig[module]) {
			datatable.displayDiscreteConfig[module].update();
		}
		return true;
	},

	drawDLO: function(module, overlay, context, scale, gene_name, topx, topy) {
		var size = 2;
		//console.log("Drawing " + gene_name);
		//context.fillStyle = 'rgba(100, 30, 100, 1)';
		//context.fillRect(topx, topy, (size+2)*scale, size*scale);
		var bound = null;
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			if (navicell.drawing_config.displayGlyphs(num)) {
				bound = draw_glyph(module, num, overlay, context, scale, gene_name, topx, topy);
				if (bound) {
//					topx = bound[0] + bound[2] - 6;
					topx = bound[0] + bound[2] - 2;
				}
			}
		}
		if (bound) {
			topx -= 6;
		}
		/*
		if (bound) {
			topx = bound[0] + bound[2] - 6;
		}
			*/
		if (navicell.drawing_config.displayHeatmaps()) {
			draw_heatmap(module, overlay, context, scale, gene_name, topx, topy);
		}
		if (navicell.drawing_config.displayBarplots()) {
			draw_barplot(module, overlay, context, scale, gene_name, topx, topy);
		}
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

function force_datatable_display(id) {
	var datatable = navicell.dataset.datatables_id[id];
	if (datatable) {
		datatable.forceDisplay();
	}
}

function DisplayStepConfig(datatable, win) {
	this.datatable = datatable;
	this.win = win;
	this.has_empty_values = datatable.hasEmptyValues();
	this.use_absval = {};
	for (var tab in DisplayStepConfig.tabnames) {
		var tabname = DisplayStepConfig.tabnames[tab];
		this.use_absval[tabname] = {};
	}
	this.group_method = {};
	this.setGroupMethod('color', Group.CONTINUOUS_AVERAGE);
	this.setGroupMethod('shape', Group.CONTINUOUS_AVERAGE);
	this.setGroupMethod('size', Group.CONTINUOUS_AVERAGE);
	var step_cnt = 5;
	this.buildDivs(step_cnt);
	this.setStepCount(step_cnt);
}

var STEP_MAX_SIZE = 36.;
var DISCRETE_SIZE_COEF = 2.;
var TABS_DIV_ID = 1;

DisplayStepConfig.prototype = {

	setStepCount: function(step_cnt) {
		this.values = {};
		this.colors = {};
		this.sizes = {};
		this.shapes = {};

		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			this.use_absval[tabname] = {};
			this.values[tabname] = {};
			this.colors[tabname] = {};
			this.sizes[tabname] = {};
			this.shapes[tabname] = {};
			this.setStepCount_config(step_cnt, 'color', tabname);
			this.setStepCount_config(step_cnt, 'shape', tabname);
			this.setStepCount_config(step_cnt, 'size', tabname);
		}
		this.datatable.refresh(this.win);
	},

	setStepCount_config: function(step_cnt, config, tabname) {
		step_cnt *= 1.;
		var keep = this.values[tabname][config] && step_cnt == this.getStepCount(config, tabname);
		this.values[tabname][config] = [];
		var values = this.values[tabname][config];
		var minval = this.getDatatableMinval(config, tabname);
		var maxval = this.getDatatableMaxval(config, tabname);
		values.push(minval);
		var step = (maxval - minval)/(step_cnt+1);
//		console.log("step_cnt: " + step_cnt + " step: " + step + " " + minval + " " + this.datatable.maxval);
		if (this.has_empty_values) {
			values.push(Number.MIN_NUMBER);
		}
		for (var nn = 0; nn < step_cnt-1; ++nn) {
			var value = minval + (nn+1)*step;
			value = parseInt(value*100)/100;
			values.push(value);
		}
		values.push(this.datatable.maxval);
		if (this.has_empty_values) {
			step_cnt++;
		}
		if (!keep) {
			this.colors[tabname][config] = new Array(step_cnt);
			this.sizes[tabname][config] = new Array(step_cnt);
			this.shapes[tabname][config] = new Array(step_cnt);
			this.setDefaults(step_cnt, config, tabname);
		}
		this.update_config(config, tabname);
		/*
		// AZ code
		var avg = (minval + maxval)/2;
		var dt_values = this.datatable.getValues();
		var posthr = getPositiveThreshold(dt_values, avg);
		var negthr = getNegativeThreshold(dt_values, avg);
		//console.log("avg: " + avg + " " + posthr + " " + negthr);
		*/
	},


	setStepInfo: function(config, tabname, idx, value, color, size, shape) {
		//console.log("setting at " + idx + " " + value + " " + color + " " + size + " shape=" + shape);
		if (value != Number.MIN_NUMBER) {
			this.values[tabname][config][idx+1] = value;
		}
		this.colors[tabname][config][idx] = color;
		this.sizes[tabname][config][idx] = size;
		this.shapes[tabname][config][idx] = shape;
	},

	setUseAbsValue: function(config, use_absval) {
		this.use_absval['sample'][config] = use_absval;
	},

	setDefaults: function(step_cnt, config, tabname) {
		var colors = color_gradient(new RGBColor(0, 255, 0), new RGBColor(255, 0, 0), step_cnt); 
		for (var ii = 0; ii < step_cnt; ++ii) {
			this.setStepInfo(config, tabname, ii, Number.MIN_NUMBER, colors[ii].getRGBValue(), 4+2*ii, ii);
		}
		//this.displayShapes(tabname);
	},

	getStepIndex: function(config, tabname, value) {
		if (value == '') {
			return 0;
		}
		var ivalue = parseFloat(value);
		if (isNaN(ivalue)) {
			return -1;
		}
		value *= 1.;
		var values = this.values[tabname][config];
		var len = values.length;
		for (var nn = 1; nn < len; ++nn) {
			if (value < values[nn]) {
				return nn-1;
			}
		}
		return len-1;
	},

	getStepCount: function(config, tabname) {
		return this.values[tabname][config].length-this.has_empty_values-1;;
	},

	getDatatableMinval: function(config, tabname) {
		if (!this.use_absval[tabname][config]) {
			return this.datatable.minval;
		}
		return this.datatable.minval_abs;
	},

	getDatatableMaxval: function(config, tabname) {
		if (!this.use_absval[tabname][config]) {
			return this.datatable.maxval;
		}
		return this.datatable.maxval_abs;
	},

	getDatatableValue: function(config, tabname, value) {
		if (value == undefined || value == '') {
			return value;
		}
		var ivalue = parseFloat(value);
		if (isNaN(ivalue)) {
			return value;
		}
		value *= 1.;
		if (!this.use_absval[tabname][config]) {
			return value;
		}
		return Math.abs(value);
	},

	setGroupMethod: function(config, group_method) {
		console.log("setting group method " + config + " " + group_method);
		this.group_method[config] = group_method;
		this.use_absval['group'][config] = 
			group_method == Group.CONTINUOUS_ABS_AVERAGE ||
			group_method == Group.CONTINUOUS_ABS_MINVAL ||
			group_method == Group.CONTINUOUS_ABS_MAXVAL;
	},

	getDatatableSampleValue: function(config, value) {
		return this.getDatatableValue(config, 'sample', value);
	},

	getDatatableGroupValue: function(config, value) {
		return this.getDatatableValue(config, 'group', value);
	},

	getValueAt: function(config, tabname, idx) {
		return this.values[tabname][config][idx+1];
	},

	getColorAt: function(config, tabname, idx) {
		return this.colors[tabname][config][idx];
	},

	getSizeAt: function(config, tabname, idx) {
		return this.sizes[tabname][config][idx];
	},

	getShapeAt: function(config, tabname, idx) {
		return this.shapes[tabname][config][idx];
	},

	getHeatmapStyleSample: function(sample_name, gene_name) {
		var color = this.getColorSample(sample_name, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return " style='text-align: center'";
	},

	getHeatmapStyleGroup: function(group, gene_name) {
		var color = this.getColorGroup(group, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return " style='text-align: center'";
	},

	getBarplotStyleSample: function(sample_name, gene_name) {
		return this.getHeatmapStyleSample(sample_name, gene_name);
	},

	getBarplotStyleGroup: function(group, gene_name) {
		return this.getHeatmapStyleGroup(group, gene_name);
	},

	_getBarplotHeight: function(tabname, value, max) {
		if (value) {
			value *= 1.;
			var minval = this.getDatatableMinval('color', tabname);
			var maxval = this.getDatatableMaxval('color', tabname);
			return max * (value-minval) / (maxval - minval);
		}
		return 0;
	},

	getBarplotSampleHeight: function(sample_name, gene_name, max) {
		var value = this.getColorSizeSampleValue(sample_name, gene_name);
		return this._getBarplotHeight('sample', value, max);
	},

	getBarplotGroupHeight: function(group, gene_name, max) {
		var value = group.getValue(this.datatable, gene_name, this.group_method['color']);
		return this._getBarplotHeight('group', value, max);
	},

	_getColor: function(value, tabname) {
		var idx = this.getStepIndex('color', tabname, value);
		if (idx < 0) {
			return undefined;
		}
		return this.colors[tabname]['color'][idx];
	},

	_getSize: function(value, tabname) {
		var idx = this.getStepIndex('size', tabname, value);
		if (idx < 0) {
			return undefined;
		}
		return this.sizes[tabname]['size'][idx];
	},

	_getShape: function(value, tabname) {
		var idx = this.getStepIndex('shape', tabname, value);
		if (idx < 0) {
			return undefined;
		}
		return this.shapes[tabname]['shape'][idx];
	},

	displayShapes: function(tabname) {
		console.log("display shapes " + tabname);
		var shapes = this.shapes[tabname]['shape'];
		for (var idx in shapes) {
			console.log("shape at " + idx + " " + shapes[idx]);
		}
	},

	makeValueInput: function(id, config, tabname, idx) {
		return "<td><input type='text' class='input-value' id='step_value_" + tabname + '_' + config + '_' + id + "_" + idx + "' value='" + this.getValueAt(config, tabname, idx) + "' onchange='DisplayStepConfig.setEditing(" + id + ", true, \"" + config + "\")'></input></td>";
	},

	makeColorInput: function(id, config, tabname, idx) {
		return "<td><input id='step_config_" + tabname + '_' + config + '_' + id + "_" + idx + "' value='" + this.getColorAt(config, tabname, idx) + "' class='color input-value' onchange='DisplayStepConfig.setEditing(" + id + ", true, \"" + config + "\")'></input></td>";
	},

	makeSelectSize: function(id, config, tabname, idx) {
		var selsize = this.getSizeAt(config, tabname, idx);
		var html = "<td><select id='step_size_" + tabname + '_' + config + '_' + id + "_" + idx + "' onchange='DisplayStepConfig.setEditing(" + id + ", true, \"" + config + "\")'>";
		var maxsize = STEP_MAX_SIZE/2;
		for (var size = 0; size < maxsize; size += 1) {
			var size2 = 2*size;
			html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
		}
		html += "</select></td>";
		return html;
	},

	makeSelectShape: function(id, config, tabname, idx) {
		var selshape = this.getShapeAt(config, tabname, idx);
		var html = "<td><select id='step_shape_" + tabname + '_' + config + '_' + id + "_" + idx + "' onchange='DisplayStepConfig.setEditing(" + id + ", true, \"" + config + "\")''>";
		if (selshape >= navicell.shapes.length) {
			selshape = navicell.shapes.length-1;
		}
		for (var shape_idx in navicell.shapes) {
			var shape = navicell.shapes[shape_idx];
			html += "<option value='" + shape_idx + "' " + (shape_idx == selshape ? "selected" : "") + ">" + shape + "</option>";
		}
		html += "</select></td>";
		return html;
	},

	makeSelectGroupMethod: function(config) {
		var datatable = this.datatable;
		var method = this.group_method[config];
		var selected;
		var str = "<select id='group_method_" + config + '_' + datatable.getId() + "' style='font-size: 70%' onchange='DisplayStepConfig.setGroupMethod(\"" + config + "\", " + datatable.getId() + ")'>\n";
		selected = (method == Group.CONTINUOUS_AVERAGE) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_AVERAGE + "'" + selected + ">Average</option>\n";
		selected = (method == Group.CONTINUOUS_MINVAL) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_MINVAL + "'" + selected + ">Min Value</option>\n";

		selected = (method == Group.CONTINUOUS_MAXVAL) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_MAXVAL + "'" + selected + ">Max Value</option>\n";

		selected = (method == Group.CONTINUOUS_ABS_AVERAGE) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_ABS_AVERAGE + "'" + selected + ">Average Absolute</option>\n";

		selected = (method == Group.CONTINUOUS_ABS_MINVAL) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_ABS_MINVAL + "'" + selected + ">Min Absolute Value</option>\n";

		selected = (method == Group.CONTINUOUS_ABS_MAXVAL) ? " selected" : "";
		str += "<option value='" + Group.CONTINUOUS_ABS_MAXVAL + "'" + selected + ">Max Absolute Value</option>\n";
		str += "</select>";
		return str;
	},

	getValue: function(config, tabname, sample_name, gene_name) {
		var value = this.datatable.getValue(sample_name, gene_name);
		return this.getDatatableValue(config, tabname, value);
	},

	getShapeSampleValue: function(sample_name, gene_name) {
		return this.getValue('shape', 'sample', sample_name, gene_name);
	},

	getShapeGroupValue: function(group, gene_name) {
		return group.getValue(this.datatable, gene_name, this.group_method['shape']);
	},

	getColorSampleValue: function(sample_name, gene_name) {
		return this.getValue('color', 'sample', sample_name, gene_name);
	},

	getColorGroupValue: function(group, gene_name) {
		return group.getValue(this.datatable, gene_name, this.group_method['color']);
	},

	getColorSizeSampleValue: function(sample_name, gene_name) {
		return this.getColorSampleValue(sample_name, gene_name);
	},

	getColorSizeGroupValue: function(group, gene_name) {
		return this.getColorGroupValue(group, gene_name);
	},

	getSizeSampleValue: function(sample_name, gene_name) {
		return this.getValue('size', 'sample', sample_name, gene_name);
	},

	getSizeGroupValue: function(sample_name, gene_name) {
		return group.getValue(this.datatable, gene_name, this.group_method['size']);
	},

	getShapeSample: function(sample_name, gene_name) {
		var value = this.getShapeSampleValue(sample_name, gene_name);
		return this._getShape(value, 'sample');
	},

	getShapeGroup: function(group, gene_name) {
		var value = this.getShapeGroupValue(group, gene_name);
		return this._getShape(value, 'group');
	},

	getColorSample: function(sample_name, gene_name) {
		var value = this.getColorSampleValue(sample_name, gene_name);
		return this._getColor(value, 'sample');
	},

	getColorGroup: function(group, gene_name) {
		var value = this.getColorGroupValue(group, gene_name);
		return this._getColor(value, 'group');
	},

	getColorSizeSample: function(sample_name, gene_name) {
		return this.getColorSample(sample_name, gene_name);
	},

	getColorSizeGroup: function(group, gene_name) {
		return this.getColorGroup(group, gene_name);
	},

	getSizeSample: function(sample_name, gene_name) {
		var value = this.getSizeSampleValue(sample_name, gene_name);
		return this._getSize(value, 'sample');
	},

	getSizeGroup: function(group, gene_name) {
		var value = this.getShapeGroupValue(group, gene_name);
		return this._getSize(value, 'group');
	},

	update: function() {
		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			this.update_config('color', tabname);
			this.update_config('shape', tabname);
			this.update_config('size', tabname);
		}
	},

	update_config: function(config, tabname) {
		var minval = this.getDatatableMinval(config, tabname);
		var maxval = this.getDatatableMaxval(config, tabname);
		var mod = config + '_';
		var id = this.datatable.getId();
		var id_suffix = tabname + '_' + mod + id 
		var doc = this.win.document;
		var table = $("#step_config_table_" + id_suffix, doc);
		table.children().remove();
		var html = "<thead>";
		html += "<th></th>";
		html += "<th>Value</th>";
		if (config == 'color') {
			html += "<th>Color</th>";
		}
		if (config == 'size') {
			html += "<th>Size</th>";
		}
		if (config == 'shape') {
			html += "<th>Shape</th>";
		}
		html += "</thead><tbody>";
		if (this.has_empty_values) {
			//html += "<tr><td></td><td style='font-size: smaller; text-align: center;'>NA</td>";
			html += "<tr><td></td><td style='font-size: smaller;'>NA</td>";
			if (config == 'color') {
				html += this.makeColorInput(id, config, tabname, 0);
			}
			if (config == 'size') {
				html += this.makeSelectSize(id, config, tabname, 0);
			}
			if (config == 'shape') {
				html += this.makeSelectShape(id, config, tabname, 0);
			}
			html += "</tr>\n";
		}
		if (false) {
			html += "<tr><td></td>";
			html += "<td>" + minval + " (minimum)</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
			html += "</tr>\n";
		}
		var step_cnt = this.getStepCount(config, tabname);
		for (var idx = 0; idx < step_cnt; idx++) {
			html += "<tr><td><span class='less-than'>Less&nbsp;than</span></td>";
			if (idx == step_cnt-1) {
				html += "<td>" + maxval + "</td>";
			} else {
				html += this.makeValueInput(id, config, tabname, idx+this.has_empty_values);
			}
			if (config == 'color') {
				html += this.makeColorInput(id, config, tabname, idx+this.has_empty_values);
			}
			if (config == 'size') {
				html += this.makeSelectSize(id, config, tabname, idx+this.has_empty_values);
			}
			if (config == 'shape') {
				html += this.makeSelectShape(id, config, tabname, idx+this.has_empty_values);
			}
			html += "</tr>\n";
		}
		html += "</tbody>";
		table.append(html);

		var table_info = $("#step_info_table_" + id_suffix, doc);
		table_info.children().remove();
		var html = "<tbody>";
		var use_absval = this.use_absval[tabname][config];
		var min_label = (use_absval ? "Min&nbsp;Abs&nbsp;Value" : "Min&nbsp;Value");
		var max_label = (use_absval ? "Max&nbsp;Abs&nbsp;Value" : "Max&nbsp;Value");
		var width = (use_absval && tabname != 'group' ? '120px' : '150px');
		html += "<tr><td style='background: #EEEEEE'>&nbsp;</td></tr>";
		html += "<tr>";
		html += "<td id='min_val_label_" + id_suffix + "'><span class='config-label'>" + min_label + "</span></td>";
		html += "<td id='min_val_" + id_suffix + "'>" + minval + "</td>";
		html += "<td width='10px'>&nbsp;</td>";
		if (tabname == 'sample') {
			if (this.datatable.minval < 0) {
				html += "<td width='" + width + "'rowspan='2'><span class='config-label'>&nbsp;&nbsp;Use&nbsp;abs&nbsp;values&nbsp;</span><input id='step_config_absval_" + id_suffix + "' type='checkbox' onchange='DisplayStepConfig.setSampleAbsval(\"" + config + "\", \"" + id + "\")'" + (use_absval ? " checked" : "") + "></input></td>"
			} else {
				html += "<td width='" + width + "'>&nbsp;</td>";
			}
		} else {
			html += "<td width='" + width + "' style='text-align: center'><span class='config-label' style='text-align: center'>Group&nbsp;Method</span></td>";
		}
		html += "</tr><tr>";
		html += "<td id='max_val_label_" + id_suffix + "'><span class='config-label'>" + max_label + "</span></td>";
		html += "<td id='max_val_" + id_suffix + "'>" + maxval + "</td>";
		if (tabname == 'group') {
			html += "<td width='10px'>&nbsp;</td>";
			html += "<td>" + this.makeSelectGroupMethod(config) + "</td>";
		}
		html += "</tr>";
		html += "</tbody>";
		table_info.append(html);

		var count = $("#step_config_count_" + id_suffix, doc);

		var title = $("#step_config_title_" + mod + id, doc);
		var Config = config.charAt(0).toUpperCase() + config.slice(1)
		title.html("<span style='font-style: italic'>" + this.datatable.name + "</span> Datatable<br><span style='font-size: smaller'>" + Config + " Configuration</span>");

		jscolor.init(this.win);
	},

	buildDivs: function(step_cnt) {
		this.div_ids = {};
		this.buildDiv(step_cnt, 'color');
		this.buildDiv(step_cnt, 'shape');
		this.buildDiv(step_cnt, 'size');
	},

	buildDiv: function(step_cnt, config) {
		var doc = this.win.document;
		var mod = config + '_';
		var id = this.datatable.getId();
		var div_id = "step_config_" + mod + id;
		var html = "<div align='center' class='step-config' id='" + div_id + "'>\n";
		html += "<h3 id='step_config_title_" + mod + id + "'></h3>";
		html += "<ul>";
		html += "<li><a class='ui-button-text' href='#step_config_sample_" + mod + id + "'>Samples</a></li>";
		html += "<li><a class='ui-button-text' href='#step_config_group_" + mod + id + "'>Groups</a></li>";
		html += "</ul>";

		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			var id_suffix = tabname + '_' + mod + id 
			var div_editing_id = "step_config_editing_" + id_suffix;
			html += "<div id='step_config_" + id_suffix + "'>";
			html += "<div style='text-align: left' id='" + div_editing_id + "' class='step-config-editing'></div>";
			html += "<h4 style='font-size: 80%'>" + DisplayStepConfig.tablabels[tabname] + "</h4>";
			html += "<table class='step-config-table' id='step_config_table_" + id_suffix + "'>";
			html += "</table>";
			html += "<table class='step-info-table' id='step_info_table_" + id_suffix + "'>";
			html += "</table>";
			
			html += "<br/><span class='config-small-label'>Step Count</span>\n";
			html += "<select style='font-size: 70%' id='step_config_count_" + id_suffix + "' onchange='DisplayStepConfig.stepCountChange(\"" + tabname + "\", \"" + config + "\", " + id + ")'>";
			for (var step = 1; step <= 10; ++step) {
				html += "<option value='" + step + "' " + (step == step_cnt ? "selected" : "") + ">" + step + "</option>";
			}
			html += "</select>";
			html += "</div>";
		}

		html += "</div>";
		this.div_ids[config] = div_id;
		$('body', doc).append(html);
		console.log("INIT_TAB " + div_id);
		$("#" + div_id, doc).tabs({beforeLoad: function( event, ui ) { event.preventDefault(); return; } }); 
	},

	getDivId: function(what) {
		return this.div_ids[what];
	},

	getClass: function() {
		return "DisplayStepConfig";
	}
};

DisplayStepConfig.stepCountChange = function(tabname, config, id) {
	var datatable = navicell.dataset.datatables_id[id];
	var module = get_module();
	var win = window;
	console.log("step_count_change(" + config + ") " + module);
	if (datatable) {
		var value = $("#step_config_count_" + tabname + '_' + config + '_' + id, win.document).val();
		datatable.getDisplayConfig(module).setStepCount_config(value, config, tabname);
		DisplayStepConfig.setEditing(datatable.id, true, config);
	}
}

DisplayStepConfig.setSampleAbsval = function(config, id) {
	var win = window;
	var checked = $("#step_config_absval_sample_" + config + '_' + id, win.document).attr("checked");
	var datatable = navicell.dataset.getDatatableById(id);
	var module = get_module();
	console.log("set_sample_absval : " + module + " step_config_absval_sample_" + config + '_' + id + " " + checked);
	if (datatable) {
		var displayStepConfig = datatable.getDisplayConfig(module);
		displayStepConfig.setUseAbsValue(config, checked == 'checked');
		var step_cnt = displayStepConfig.getStepCount(config, 'sample');
		displayStepConfig.setStepCount_config(step_cnt, config, 'sample');
		DisplayStepConfig.setEditing(id, true, config);
	}
}

DisplayStepConfig.setGroupMethod = function(config, id) {
	var datatable = navicell.getDatatableById(id);
	var win = window;
	var module = get_module();
	console.log("set_group_methodn : " + module);
	if (datatable) {
		var obj = $("#group_method_" + config + '_' + id, win.document);
		var displayStepConfig = datatable.getDisplayConfig(module);
		displayStepConfig.setGroupMethod(config, obj.val());
		var step_cnt = displayStepConfig.getStepCount(config, 'group');
		displayStepConfig.setStepCount_config(step_cnt, config, 'group');
		DisplayStepConfig.setEditing(id, true, config);
	}
}

DisplayStepConfig.setEditing = function(datatable_id, val, config, win) {
	var datatable = navicell.getDatatableById(datatable_id);
	if (!win) {
		win = window;
	}
	var module = get_module();
	console.log("setEditing : " + module);
	var div_id = datatable.getDisplayConfig(module).getDivId(config);
	if (div_id) {
		var div = $("#" + div_id, win.document);
		// kludge !
		// actually not.
		div.tabs({beforeLoad: function( event, ui ) { event.preventDefault(); return; } }); 
		var active = div.tabs("option", "active");
		var tabname = DisplayStepConfig.tabnames[active];
		if (tabname) {
			$("#step_config_editing_" + tabname + '_' + config + '_' + datatable_id, win.document).html(val ? EDITING_CONFIGURATION : "");
		}
	}
}

DisplayStepConfig.tabnames = ['sample', 'group'];
DisplayStepConfig.tablabels = {'sample' : 'Sample Configuration', 'group' :'Group Configuration'};

function DisplayDiscreteConfig(datatable, win) {
	this.datatable = datatable;
	this.win = win;
	this.values = [];
	this.values_idx = {};
	var discrete_values = datatable.getDiscreteValues();
	for (var value in discrete_values) {
		console.log("setting value [" + discrete_values[value] + "]");
		this.values.push(discrete_values[value]);
	}
	this.values.sort();
	for (var idx = 0; idx < this.values.length; ++idx) {
		var value = this.values[idx];
		this.values_idx[value] = idx;
	}
	this.buildDivs();
	this.buildValues();
	this.update();
}

DisplayDiscreteConfig.prototype = {
	
	buildDivs: function() {
		this.div_ids = {};
		this.buildDiv('color');
		this.buildDiv(COLOR_SIZE_CONFIG);
		this.buildDiv('shape');
		this.buildDiv('size');
	},

	buildDiv: function(config) {
		var mod = config + '_';
		var doc = this.win.document;
		var id = this.datatable.getId();
		var div_id = "discrete_config_" + mod + id;
		var html = "<div align='center' class='discrete-config' id='" + div_id + "'>\n";

		html += "<h3 id='discrete_config_title_" + mod + id + "'></h3>";
		html += "<ul>";
		html += "<li><a class='ui-button-text' href='#discrete_config_sample_" + mod + id + "'>Samples</a></li>";
		html += "<li><a class='ui-button-text' href='#discrete_config_group_" + mod + id + "'>Groups</a></li>";
		html += "</ul>";

		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			var id_suffix = tabname + '_' + mod + id 
			var div_editing_id = "discrete_config_editing_" + id_suffix;

			html += "<div id='discrete_config_" + id_suffix + "'>";
			html += "<div style='text-align: left' id='" + div_editing_id + "' class='discrete-config-editing'></div>";
			html += "<h4 style='font-size: 80%'>" + DisplayStepConfig.tablabels[tabname] + "</h4>";
			html += "<table class='discrete-config-table' id='discrete_config_table_" + id_suffix + "'>";
			html += "</table>";
			html += "<table class='discrete-info-table' id='discrete_info_table_" + id_suffix + "'>";
			html += "</table>";
			html += "</div>";
		}
		html += "</div>";
		this.div_ids[config] = div_id;
		$('body', doc).append(html);
		console.log("INIT_TAB " + div_id);
		$("#" + div_id, doc).tabs({beforeLoad: function( event, ui ) { event.preventDefault(); return; } }); 
	},

	getDatatableValue: function(config, value) {
		return value;
	},

	getDivId: function(what) {
		return this.div_ids[what];
	},

	buildValues: function() {
		var size = this.values.length;
		this.colors = {};
		this.sizes = {};
		this.shapes = {};
		this.conds = {};
		var configs = ['color', COLOR_SIZE_CONFIG, 'shape', 'size'];
		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			this.colors[tabname] = {};
			this.sizes[tabname] = {};
			this.shapes[tabname] = {};
			this.conds[tabname] = {};
			var incr = tabname == 'group' ? 1 : 0;
			for (var idx in configs) {
				var config = configs[idx];
				this.colors[tabname][config] = new Array(size+incr);
				this.sizes[tabname][config] = new Array(size+incr);
				this.shapes[tabname][config] = new Array(size+incr);
				this.conds[tabname][config] = new Array(size+incr);
				this.setDefaults(config, tabname);
			}
		}
		this.update();
	},

	getValueAt: function(idx) {
		return this.values[idx];
	},

	getValueCount: function() {
		return this.values.length;
	},

	setValueInfo: function(config, tabname, idx, color, size, shape, cond) {
		if (idx < this.colors[tabname][config].length) {
			this.colors[tabname][config][idx] = color;
			this.sizes[tabname][config][idx] = size;
			this.shapes[tabname][config][idx] = shape;
			this.conds[tabname][config][idx] = cond;
		}
	},

	setDefaults: function(config, tabname) {
		var step_cnt = this.getValueCount();
		var colors;
		var biotype_is_set = this.datatable.biotype.isSet();
		if (biotype_is_set) {
			colors = color_gradient(new RGBColor(0, 0, 120), new RGBColor(0, 0, 120), step_cnt);
		} else {
			colors = color_gradient(new RGBColor(0, 255, 0), new RGBColor(255, 0, 0), step_cnt);
		}
		for (var ii = 0; ii < step_cnt; ++ii) {
			this.setValueInfo(config, tabname, ii, colors[ii].getRGBValue(), ii*2+4, ii, ii == 0 ? Group.DISCRETE_IGNORE : Group.DISCRETE_GT_0);
		}
		if (tabname == 'group') {
			this.setValueInfo(config, tabname, step_cnt, "FFFFFF", 0, 0, Group.DISCRETE_IGNORE);
		}
	},

	getColorAt: function(idx, config, tabname) {
		return this.colors[tabname][config][idx];
	},

	getSizeAt: function(idx, config, tabname) {
		return this.sizes[tabname][config][idx];
	},

	getShapeAt: function(idx, config, tabname) {
		return this.shapes[tabname][config][idx];
	},

	update: function() {
		for (var tab in DisplayStepConfig.tabnames) {
			var tabname = DisplayStepConfig.tabnames[tab];
			this.update_config('color', tabname);
			this.update_config(COLOR_SIZE_CONFIG, tabname);
			this.update_config('shape', tabname);
			this.update_config('size', tabname);
		}
	},

	getValue: function(sample_name, gene_name) {
		return this.datatable.getValue(sample_name, gene_name);
	},

	getValueIndex: function(sample_name, gene_name) {
		var value = this.getValue(sample_name, gene_name);
		return this.values_idx[value];
	},

	getColorSampleValue: function(sample_name, gene_name) {
		return this.getValue(sample_name, gene_name);
	},

	getColorSizeSampleValue: function(sample_name, gene_name) {
		return this.getValue(sample_name, gene_name);
	},

	getColorSample: function(sample_name, gene_name) {
		return this.getColorAt(this.getValueIndex(sample_name, gene_name), 'color', 'sample');
	},

	getColorSizeSample: function(sample_name, gene_name) {
		return this.getColorAt(this.getValueIndex(sample_name, gene_name), COLOR_SIZE_CONFIG, 'sample');
	},

	getShapeSample: function(sample_name, gene_name) {
		return this.getShapeAt(this.getValueIndex(sample_name, gene_name), 'shape', 'sample');
	},

	getSizeSample: function(sample_name, gene_name) {
		return this.getSizeAt(this.getValueIndex(sample_name, gene_name), 'size', 'sample');
	},

	getHeatmapStyleSample: function(sample_name, gene_name) {
		var color = this.getColorSample(sample_name, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return " style='text-align: center'";
	},

	getBarplotStyleSample: function(sample_name, gene_name) {
		var color = this.getColorSizeSample(sample_name, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return " style='text-align: center'";
	},

	getBarplotSampleHeight: function(sample_name, gene_name, max) {
		var idx = this.getValueIndex(sample_name, gene_name);
		var size = this.getSizeAt(idx, COLOR_SIZE_CONFIG, 'sample') * 1.;
		var maxsize = STEP_MAX_SIZE/2;
		return max * (size/maxsize);
	},

	//----
	getAcceptedCondition: function(group, gene_name, config) {
		var conds = this.conds['group'][config];
		var id_suffix = 'group_' + config + '_' + this.datatable.getId();
		var doc = this.win.document;
		for (var idx in conds) {
			if (conds[idx]) {
				var idx2 = $("#discrete_value_" + id_suffix + "_" + idx. doc).val();
				//console.log("getAcceptedCondition: " + idx + " -> " + idx2);
				if (group.acceptCondition(this.datatable, gene_name, this.values[idx2], conds[idx])) {
					//console.log("return one " + idx);
					return idx;
				}
			}
		}
		//console.log("return else " + (conds.length-1));
		return conds.length-1;
	},

	condString: function(idx, config) {
		var conds = this.conds['group'][config];
		var cond = conds[idx];
		if (cond == Group.DISCRETE_IGNORE) {
			return 'ignore';
		}
		if (cond == Group.DISCRETE_EQ_0) {
			return '= 0';
		}
		if (cond == Group.DISCRETE_GT_0) {
			return '> 0';
		}
		if (cond == Group.DISCRETE_EQ_ALL) {
			return '= all';
		}
		return '';
	},

	getColorGroupValue: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, 'color');
		if (idx >= 0) {
			if (idx == this.values.length) {
				return "no matching condition";
			}
			var value = this.values[idx];
			if (!value) {
				value = 'NA';
			}
			return '#' + value + ' ' + this.condString(idx, 'color');
		}
		return '';
	},

	getColorSizeGroupValue: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, COLOR_SIZE_CONFIG);
		if (idx >= 0) {
			if (idx == this.values.length) {
				return "no matching condition";
			}
			var value = this.values[idx];
			if (!value) {
				value = 'NA';
			}
			return '#' + value + ' ' + this.condString(idx, COLOR_SIZE_CONFIG);
		}
		return '';
	},

	getColorGroup: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, 'color');
		if (idx >= 0) {
			return this.getColorAt(idx, 'color', 'group');
		}
		return '';
	},

	getColorSizeGroup: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, COLOR_SIZE_CONFIG);
		if (idx >= 0) {
			return this.getColorAt(idx, COLOR_SIZE_CONFIG, 'group');
		}
		return '';
	},

	getSizeGroup: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, 'color');
		if (idx >= 0) {
			return this.getColorAt(idx, 'color', 'group');
		}
		return '';
	},

	getShapeGroup: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, 'shape');
		if (idx >= 0) {
			return this.getColorAt(idx, 'shape', 'group');
		}
		return '';
	},

	getSizeGroup: function(group, gene_name) {
		var idx = this.getAcceptedCondition(group, gene_name, 'size');
		if (idx >= 0) {
			return this.getColorAt(idx, 'size', 'group');
		}
		return '';
	},

	getHeatmapStyleGroup: function(group, gene_name) {
		var color = this.getColorGroup(group, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return '';
	},

	getBarplotStyleGroup: function(group, gene_name) {
		var color = this.getColorSizeGroup(group, gene_name);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return '';
	},

	getBarplotGroupHeight: function(group, gene_name, max) {
		var idx = this.getAcceptedCondition(group, gene_name, COLOR_SIZE_CONFIG);
		if (idx >= 0) {
			var size = this.getSizeAt(idx, COLOR_SIZE_CONFIG, 'group');
			var maxsize = STEP_MAX_SIZE/2;
			return max * (size/maxsize);
		}
		return 0;
	},

	//----

	getConditionAt: function(idx, config) {
		return this.conds['group'][config][idx];
	},

	update_config: function(config, tabname) {
		var mod = config + '_';
		var id = this.datatable.getId();
		var id_suffix = tabname + '_' + mod + id 
		var doc = this.win.document;
		var table = $("#discrete_config_table_" + id_suffix, doc);
		var is_sample = tabname == 'sample';
		table.children().remove();
		var html = "<thead>";
		var prefix;
		if (is_sample) {
			html += "<th>Value</th>";
			prefix = '';
		} else {
			html += "<th colspan='2'>Condition</th>";
			prefix = '#';
		}
		if (config == 'color' || config == COLOR_SIZE_CONFIG) {
			html += "<th>Color</th>";
		}
		if (config == 'size' || config == COLOR_SIZE_CONFIG) {
			html += "<th>Size</th>";
		}
		if (config == 'shape') {
			html += "<th>Shape</th>";
		}
		html += "</thead><tbody>";
		var step_cnt = this.values.length;
		var step_cnt_1 = step_cnt + !is_sample;
		for (var idx = 0; idx < step_cnt_1; idx++) {
			html += "<tr>";
			if (idx == step_cnt) {
				html += "<td style='text-align: center; font-style: italic' colspan='2'>no matching condition</td>";
			} else {
				var value = this.getValueAt(idx, config, tabname);
				if (!is_sample) {
					html += "<td><select id='discrete_value_" + id_suffix + "_" + idx + "' style='font-size: smaller'>";
					for (var idx2 = 0; idx2 < step_cnt; idx2++) {
						var value2 = this.getValueAt(idx2, config, tabname);
						html += "<option value='" + idx2 + "' " + (value2 == value ? "selected" : "") + "><span style='font-style: italic; font-size: 60%'>" + (value2 ? value2 : "NA") + "</span></option>";
					}
					html += "</select></td>";
				} else {
					if (value == '') {
						html += "<td><span style='text-align: center'>" + prefix + "NA</span></td>";
					} else {
						html += "<td>" + prefix + value + "</td>";
					}
				}
				if (!is_sample) {
					var selcond = this.getConditionAt(idx, config);
					html += "<td><select id='discrete_cond_" + id_suffix + "_" + idx + "' style='font-size: smaller'>";
					html += "<option value='" + Group.DISCRETE_IGNORE + "' " + (selcond == Group.DISCRETE_IGNORE ? "selected" : "") + "><span style='font-style: italic; font-size: 60%'>ignore</span></option>";
					html += "<option value='" + Group.DISCRETE_EQ_0 + "' " + (selcond == Group.DISCRETE_EQ_0 ? "selected" : "") + ">= 0</option>";
					html += "<option value='" + Group.DISCRETE_GT_0 + "' " + (selcond == Group.DISCRETE_GT_0 ? "selected" : "") + ">&gt; 0</option>";
					html += "<option value='" + Group.DISCRETE_EQ_ALL + "' " + (selcond == Group.DISCRETE_EQ_ALL ? "selected" : "") + ">= all</option>";
					html += "</select></td>";
				}
			}
			if (config == 'color' || config == COLOR_SIZE_CONFIG) {
				html += "<td><input id='discrete_color_" + id_suffix + "_" + idx + "' value='" + this.getColorAt(idx, config, tabname) + "' class='color' onchange='display_discrete_config_set_editing(" + id + ", true, \"" + config + "\", \"" + tabname + "\")'></input></td>";
			}
			if (config == 'size' || config == COLOR_SIZE_CONFIG) {
				html += "<td><select id='discrete_size_" + id_suffix + "_" + idx + "' onchange='display_discrete_config_set_editing(" + id + ", true, \"" + config + "\", \"" + tabname + "\")'>";
				var selsize = this.getSizeAt(idx, config, tabname);
				if (idx == step_cnt) {
					selsize = 4;
				}
				var maxsize = this.getValueCount()*DISCRETE_SIZE_COEF+2;
				for (var size = -2; size < maxsize; size += 1) {
					var size2 = DISCRETE_SIZE_COEF*(size+2);
					html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
				}
				html += "</select></td>";
			}
			if (config == 'shape') {
				html += "<td><select id='discrete_shape_" + id_suffix + "_" + idx + "' onchange='display_discrete_config_set_editing(" + id + ", true, \"" + config + "\", \"" + tabname + "\")''>";
				var selshape = this.getShapeAt(idx, config, tabname);
				if (selshape > navicell.shapes.length) {
					selshape = navicell.shapes.length-1;
				}
				for (var shape_idx in navicell.shapes) {
					var shape = navicell.shapes[shape_idx];
					html += "<option value='" + shape_idx + "' " + (shape_idx == selshape ? "selected" : "") + ">" + shape + "</option>";
				}
				html += "</select></td>";
			}
			html += "</tr>\n";
		}

		html += "</tbody>";
		table.append(html);

		var title = $("#discrete_config_title_" + mod + id, doc);
		if (config == COLOR_SIZE_CONFIG) {
			var Config = "Color/Size";
		} else {
			var Config = config.charAt(0).toUpperCase() + config.slice(1)
		}
		title.html("<span style='font-style: italic'>" + this.datatable.name + "</span> Datatable<br><span style='font-size: smaller'>" + Config + " Configuration</span>");
		jscolor.init(this.win);
	}
};

function HeatmapConfig() {
	this.reset();
}

HeatmapConfig.prototype = {

	reset: function(reset_sample_only) {
		this.samples_or_groups = [];
		if (!reset_sample_only) {
			this.datatables = [];
			this.setSize(4);
			this.setScaleSize(4);
		}
	},

	setAllSamples: function() {
		this.shrink();
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			this.samples_or_groups.push(sample);
		}
		return this.samples_or_groups.length;
	},

	setAllGroups: function() {
		this.shrink();
		for (var group_name in navicell.group_factory.group_map) {
			var group = navicell.group_factory.group_map[group_name];
			this.samples_or_groups.push(group);
		}
		return this.samples_or_groups.length;
	},

	cloneFrom: function(heatmap_config) {
		this.reset();
		for (var nn = 0; nn < heatmap_config.datatables.length; ++nn) {
			this.datatables.push(heatmap_config.datatables[nn]);
		}
		for (var nn = 0; nn < heatmap_config.samples_or_groups.length; ++nn) {
			this.samples_or_groups.push(heatmap_config.samples_or_groups[nn]);
		}
		this.setSize(heatmap_config.getSize());
		this.setScaleSize(heatmap_config.getScaleSize());
	},

	setSize: function(size) {
		this.size = size*1.;
	},

	setScaleSize: function(scale_size) {
		this.scale_size = scale_size*1;
	},

	getSize: function() {
		return this.size;
	},

	getScaleSize: function() {
		return this.scale_size;
	},

	getScale: function(scale) {
		if (this.scale_size == 0) {
			return 1;
		}
		if (this.scale_size == 1) {
			return scale*1;
		}
		return Math.sqrt(scale*1.)/(this.scale_size-1);
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

	getDatatableCount: function() {
		return this.datatables.length;
	},

	getSampleOrGroupCount: function() {
		return this.samples_or_groups.length;
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

//
// TBD: MUST factorize with HeatmapConfig (probably inheritance)
//

function BarplotConfig() {
	this.reset();
}

BarplotConfig.prototype = {

	reset: function(reset_sample_only) {
		this.samples_or_groups = [];
		if (!reset_sample_only) {
			this.datatables = [];
			this.setHeight(4);
			this.setWidth(4);
			this.setScaleSize(4);
		}
	},

	setAllSamples: function() {
		this.shrink();
		for (var sample_name in navicell.dataset.samples) {
			var sample = navicell.dataset.samples[sample_name];
			this.samples_or_groups.push(sample);
		}
		return this.samples_or_groups.length;
	},

	setAllGroups: function() {
		this.shrink();
		for (var group_name in navicell.group_factory.group_map) {
			var group = navicell.group_factory.group_map[group_name];
			this.samples_or_groups.push(group);
		}
		return this.samples_or_groups.length;
	},

	cloneFrom: function(barplot_config) {
		this.reset();
		for (var nn = 0; nn < barplot_config.datatables.length; ++nn) {
			this.datatables.push(barplot_config.datatables[nn]);
		}
		for (var nn = 0; nn < barplot_config.samples_or_groups.length; ++nn) {
			this.samples_or_groups.push(barplot_config.samples_or_groups[nn]);
		}
		this.setHeight(barplot_config.getHeight());
		this.setWidth(barplot_config.getWidth());
		this.setScaleSize(barplot_config.getScaleSize());
	},

	setWidth: function(width) {
		this.width = width*1.;
	},

	setHeight: function(height) {
		this.height = height*1.;
	},

	setScaleSize: function(scale_size) {
		this.scale_size = scale_size*1;
	},

	getWidth: function() {
		return this.width;
	},

	getHeight: function() {
		return this.height;
	},

	getScaleSize: function() {
		return this.scale_size;
	},

	getScale: function(scale) {
		if (this.scale_size == 0) {
			return 1.;
		}
		if (this.scale_size == 1) {
			return scale*1.;
		}
		return Math.sqrt(scale*1.)/(this.scale_size-1);
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

function GlyphConfig() {
	this.reset();
}

GlyphConfig.prototype = {

	reset: function() {
		this.sample_or_group = null;
		this.shape_datatable = null;
		this.color_datatable = null;
		this.size_datatable = null;
		this.setSize(4);
		this.setScaleSize(4);
	},

	setSize: function(size) {
		this.size = size*1.;
	},

	setScaleSize: function(scale_size) {
		this.scale_size = scale_size*1;
	},

	getSize: function() {
		return this.size;
	},

	getScaleSize: function() {
		return this.scale_size;
	},

	getScale: function(scale) {
		if (this.scale_size == 0) {
			return 1;
		}
		if (this.scale_size == 1) {
			return scale*1;
		}
		return Math.sqrt(scale*1.)/(this.scale_size-1);
	},

	setSampleOrGroup: function(sample_or_group) {
		this.sample_or_group = sample_or_group;
	},

	getSampleOrGroup: function() {
		return this.sample_or_group;
	},

	getGroup: function() {
		var sample_or_group = this.getSampleOrGroup();
		return this.sample_or_group && sample_or_group.isGroup() ? sample_or_group : undefined;
	},

	getSample: function() {
		var sample_or_group = this.getSampleOrGroup();
		return sample_or_group && sample_or_group.isSample() ? sample_or_group : undefined;
	},

	setShapeDatatable: function(datatable) {
		this.shape_datatable = datatable;
	},

	getShapeDatatable: function() {
		return this.shape_datatable;
	},

	setColorDatatable: function(datatable) {
		this.color_datatable = datatable;
	},

	getColorDatatable: function() {
		return this.color_datatable;
	},

	setSizeDatatable: function(datatable) {
		this.size_datatable = datatable;
	},

	getSizeDatatable: function() {
		return this.size_datatable;
	},

	cloneFrom: function(glyph_config) {
		this.reset();

		this.sample_or_group = glyph_config.sample_or_group;
		this.size = glyph_config.size;
		this.scale_size = glyph_config.scale_size;
		this.shape_datatable = glyph_config.shape_datatable;
		this.color_datatable = glyph_config.color_datatable;
		this.size_datatable = glyph_config.size_datatable;
	}
};

// TBD datatable id management
function Datatable(dataset, biotype_name, name, file, datatable_id, win) {
	var reader = new FileReader();
	var ready = this.ready = $.Deferred(reader.onload);
	if (dataset.datatables[name]) {
		this.error = "datatable " + name + " already exists";
		ready.resolve();
		return;
	}
	this.minval = Number.MAX_NUMBER;
	this.maxval = Number.MIN_NUMBER;
	this.minval_abs = Number.MAX_NUMBER;
	this.maxval_abs = Number.MIN_NUMBER;
	this.error = "";
	this.warning = "";
	this.id = datatable_id;
	this.dataset = dataset;
	this.biotype = navicell.biotype_factory.getBiotype(biotype_name);
	this.discrete_values_map = {};
	this.discrete_values = [];
	this.empty_value_cnt = 0;
	this.windows = {}
	this.dialogs = {}
	this.data_table_gene = {};
	this.data_table_sample = {};
	this.switch_button = {};
	this.data_matrix = {};
	this.current_view = {};
	this.displayStepConfig = {};
	this.displayDiscreteConfig = {};

	this.setName(name);

	this.gene_index = {};
	this.sample_index = {};
	this.data = [];

	navicell.DTStatusMustUpdate = true;

	reader.readAsBinaryString(file);

	var datatable = this;

	reader.onload = function() { 
		var dataset = datatable.dataset;

		var text = reader.result;

		var lines = text.split(LINE_BREAK_REGEX);
		var gene_length = lines.length;

		var firstline;
		var sample_cnt = 0;
		var sep = null;

		for (var ii = 0; ii < INPUT_SEPS.length; ++ii) {
			sep = INPUT_SEPS[ii];
			firstline = lines[0].trim().split(sep);
			sample_cnt = firstline.length-1;
			if (!firstline[firstline.length-1]) {
				--sample_cnt;
			}
			if (sample_cnt >= 1) {
				break;
			}
		}

		var biotype_is_set = datatable.biotype.isSet();
		if (sample_cnt < 1) {
			if (!biotype_is_set) {
				datatable.error = "invalid file format: tabular, comma or space separated file expected";
				ready.resolve();
				return;
			}
			firstline.push(NO_SAMPLE);
			sample_cnt = 1;
		} else {
			if (biotype_is_set) {
				datatable.error = "invalid file format: only one column expected";
				ready.resolve();
				return;
			}
		}

		var samples_to_add = [];
		var genes_to_add = [];
		for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
			var sample_name = firstline[sample_nn+1];
			if (sample_name.length > 1) {
				samples_to_add.push(sample_name);
				datatable.sample_index[sample_name] = sample_nn;
			}
		}
		
		for (var gene_nn = 0, gene_jj = 1; gene_jj < gene_length; ++gene_jj) {
			var line = lines[gene_jj].trim().split(sep);
			var line_cnt = line.length-1;
			if (!line[line.length-1]) {
				--line_cnt;
			}
			if (biotype_is_set) {
				line_cnt++;
			}
			if (line_cnt < sample_cnt) {
				datatable.warning += "line #" + (gene_jj+1) + " has less than " + sample_cnt + " samples";
			} else if (line_cnt > sample_cnt) {
				datatable.error += "line #" + (gene_jj+1) + " has more than " + sample_cnt + " samples";
				ready.resolve();
				return;

			}
			var gene_name = line[0];
			if (!navicell.mapdata.hugo_map[gene_name]) {
				continue;
			}
			genes_to_add.push(gene_name);

			datatable.gene_index[gene_name] = gene_nn;
			datatable.data[gene_nn] = [];
			for (var sample_nn = 0; sample_nn < line_cnt; ++sample_nn) {
				var value;
				if (biotype_is_set) {
					value = GENE_SET;
				} else {
					value = line[sample_nn+1];
				}
				var err = datatable.setData(gene_nn, sample_nn, value);
				if (err) {
					console.log("data error " + err);
					datatable.error = "datatable " + name + " invalid data: " + err;
					ready.resolve();
					return;
				}
			}
			++gene_nn;
		}

		// no error, so adding genes
		var has_new_samples;
		for (var nn = 0; nn < samples_to_add.length; ++nn) {
			var sample = dataset.addSample(samples_to_add[nn]);
			has_new_samples = sample.refcnt == 1;
		}

		for (var nn = 0; nn < genes_to_add.length; ++nn) {
			var gene_name = genes_to_add[nn];
			dataset.addGene(gene_name, navicell.mapdata.hugo_map[gene_name]);
		}

		if (has_new_samples) {
			navicell.group_factory.buildGroups();
		}
		datatable.epilogue(win);
		ready.resolve();
		dataset.syncModifs();
	}

	reader.onerror = function(e) {  // If anything goes wrong
		datatable.error = e.toString();
		console.log("Error", e);    // Just log it
		ready.resolve();
	}
}

var MAX_MATRIX_SIZE = 8000;

var DATATABLE_HAS_TABS = 0;

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
	display: function(module_name, win, display_graphics, display_markers) {
		if (!display_graphics && !display_markers) {
			return;
		}
		//console.log("display_markers: " + module_name);
		var id_arr = [];
		var arrpos = [];
		for (var gene_name in this.gene_index) {
			var hugo_module_map = this.dataset.genes[gene_name].hugo_module_map;
			var entity_map_arr = hugo_module_map[module_name];
			if (!entity_map_arr) {
				continue;
			}
			for (var ii = 0; ii < entity_map_arr.length; ++ii) {
				var entity_map = entity_map_arr[ii];
				var modif_arr = entity_map.modifs;
				if (modif_arr) {
					for (var nn = 0; nn < modif_arr.length; ++nn) {
						var modif = modif_arr[nn];
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
		console.log("display.arrpos: " + arrpos.length);
		if (display_markers) {
			if (navicell.mapdata.getJXTree(win.document.navicell_module_name)) {
				navicell.mapdata.findJXTree(win, id_arr, true, 'select');
				//array_push_all(overlay.arrpos, navicell.mapdata.checked_arrpos);
			} else {
				win.show_markers(id_arr);
			}
		}

		if (display_graphics) {
			//overlay.arrpos = arrpos;
		}
		overlay.draw(module_name);
	},

	setName: function(name) {
		this.name = name;
		this.html_name = name.replace(/ /g, "&nbsp;");
	},

	getId: function() {return this.id;},

	hasEmptyValues: function() {
		return this.empty_value_cnt > 0;
	},

	setTabNum: function(tabnum) {
		this.tabnum = tabnum;
	},

	declareWindow: function(win) {
		var module = get_module(win);
		if (!this.windows[module]) {
			this.windows[module] = win;
		}
	},

	makeDialogsForWindow: function(win) {
		var module = get_module(win);
		if (this.dialogs[module]) {
			return;
		}
		win.console.log("DATATABLE " + this.name + " MAKE DIALOGS for " + module);
		var doc = win.document;
		var tab_body = $("#dt_datatable_tabs", doc);
		$('body', doc).append("<div id='dt_data_dialog_" + this.id + "'><div id='dt_datatable_id" + this.id + "'><h3 style='text-align: center;'>Datatable " + this.name + "</h3><div class='switch-view-div'>" + make_button("", "switch_view_" + this.id, "switch_view(" + this.id + ")") + "</div><table id='dt_datatable_gene_table_id" + this.id + "' class='tablesorter datatable_table'></table><table id='dt_datatable_sample_table_id" + this.id + "' class='tablesorter datatable_table'></table></div></div>");
		this.data_div = $("#dt_datatable_id" + this.id, doc);
		this.data_table = $("#dt_datatable_table_id" + this.id, doc);
		this.data_table_gene[module] = $("#dt_datatable_gene_table_id" + this.id, doc);
		this.data_table_sample[module] = $("#dt_datatable_sample_table_id" + this.id, doc);
		this.switch_button[module] = $("#switch_view_" + this.id, doc);
		this.switch_button[module].css('font-size', '10px');
		this.switch_button[module].css('background', 'white');
		this.switch_button[module].css('color', 'darkblue');

		var width = this.biotype.isSet() ? 300: 900;

		this.data_matrix[module] = $("#dt_data_dialog_" + this.id, doc);

		this.data_matrix[module].dialog({
			autoOpen: false,
			width: width,
			height: 700,
			modal: false,
			buttons: {
				Cancel: function() {
					$(this).dialog('close');
				}
			}
		});

		if (this.biotype.isContinuous()) {
			this.displayStepConfig[module] = new DisplayStepConfig(this, win);
			this.displayDiscreteConfig[module] = null;
		} else if (this.biotype.isDiscrete()) {
			this.displayStepConfig[module] = null;
			this.displayDiscreteConfig[module] = new DisplayDiscreteConfig(this, win);
		} else if (this.biotype.isSet()) { // duplicated code for now
			this.displayStepConfig[module] = null;
			this.displayDiscreteConfig[module] = new DisplayDiscreteConfig(this, win);
		}
		this.current_view[module] = "gene";
		this.dialogs[module] = true;
	},

	epilogue: function(win) {
		/*
		for (var map_name in maps) {
			var doc = maps[map_name].document;
			this.makeDialogsForWindow(doc.win);
		}
		*/
		this.declareWindow(win);
		if (this.biotype.isDiscrete()) {
			this.discrete_values = mapKeys(this.discrete_values_map);
			this.discrete_values.sort();
		} else if (this.biotype.isSet()) { // duplicated code for now
			this.discrete_values = mapKeys(this.discrete_values_map);
			this.discrete_values.sort();
		}
	},

	getDiscreteValues: function() {
		return this.discrete_values;
	},

	makeGeneView: function(module) {
		this.current_view[module] = "gene";
		console.log("makeGeneView: " + module);
		if (!this.data_table_gene[module].ok) {
			this.data_table_gene[module].children().remove();
			this.data_table_gene[module].append(this.makeDataTable_genes(module));
			this.data_table_gene[module].tablesorter();
			this.data_table_gene[module].ok = true;
		}
		this.data_table_sample[module].css("display", "none");
		this.data_table_gene[module].css("display", "block");

		if (this.biotype.isSet()) {
			this.switch_button[module].css("display", "none");
		} else {
			this.switch_button[module].val("Switch to Samples / Genes");
		}
	},

	// ...
	makeSampleView: function(module) {
		this.current_view[module] = "sample";
		if (!this.data_table_sample[module].ok) {
			this.data_table_sample[module].children().remove();
			this.data_table_sample[module].append(this.makeDataTable_samples(module));
			this.data_table_sample[module].tablesorter();
			this.data_table_sample[module].ok = true;
		}
		this.data_table_sample[module].css("display", "block");
		this.data_table_gene[module].css("display", "none");
		if (this.biotype.isSet()) {
			this.switch_button[module].css("display", "none");
		} else {
			this.switch_button[module].val("Switch to Genes / Samples");
		}
	},

	switchView: function(win) {
		var module = get_module(win);
		if (this.current_view[module] == "gene") {
			this.makeSampleView(module);
		} else {
			this.makeGeneView(module);
		}
	},

	refresh: function(win) {
		var module = get_module(win);
		if (this.current_view[module] == "gene") {
			this.makeGeneView(module);
		} else if (this.current_view[module] == "sample") {
			this.makeSampleView(module);
		}
	},

	getDataMatrixDiv: function(module) {
		if (!this.dialogs[module] && this.windows[module]) {
			this.makeDialogsForWindow(this.windows[module]);
		}
		return this.data_matrix[module];
	},

	/*
	getDataDialogDivId: function(module) {
		if (!this.dialogs[module] && this.windows[module]) {
			this.makeDialogsForWindow(this.windows[module]);
		}
		return "dt_data_dialog_" + this.id;
	},
	*/
	getDisplayConfig: function(module) {
		console.log("getDisplayConfig: " + module + " " + this.dialogs[module] + " " + this.windows[module]);
		if (!this.dialogs[module] && this.windows[module]) {
			this.makeDialogsForWindow(this.windows[module]);
		}
		if (this.displayStepConfig[module]) {
			return this.displayStepConfig[module];
		}
		return this.displayDiscreteConfig[module];
	},

	makeDataTable_genes: function(module) {
		var biotype_is_set = this.biotype.isSet();
		if (biotype_is_set) {
			this.switch_button[module].val("");
		} else {
			this.switch_button[module].val("Switch to Samples / Genes");
		}
		var str = "<thead><th>Genes</th>";
		if (!biotype_is_set) {
			for (var sample_name in this.sample_index) {
				str += "<th>" + sample_name + "</th>";
			}
		}
		str += "</thead>";
		str += "<tbody>";
		for (var gene_name in this.gene_index) {
			str += "<tr><td>" + gene_name + "</td>";
			var limit = 0;
			if (!biotype_is_set) {
				for (var sample_name in this.sample_index) {
					/*
					  if (limit++ == 10) {
					  break;
					  }
					*/
					var value = this.data[this.gene_index[gene_name]][this.sample_index[sample_name]];
					//str += "<td class='datacell'" + this.getStyle(value) + ">" + value + "</td>";
					//str += "<td class='datacell'>" + value + "</td>";
					str += "<td>" + value + "</td>";
				}
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
		}
		if (sample_name == NO_SAMPLE) {
			return INVALID_VALUE;
		}
		//return '';
		return undefined;
	},

	makeDataTable_samples: function(module) {
		this.switch_button[module].val("Switch to Genes / Samples");
		var str = "<thead><th>Samples</th>";
		for (var gene_name in this.gene_index) {
			str += "<th>" + gene_name + "</th>";
		}
		str += "</thead>";
		str += "<tbody>";
		for (var sample_name in this.sample_index) {
			str += "<tr><td>" + sample_name + "</td>";
			var limit = 0;
			for (var gene_name in this.gene_index) {
				/*
				if (limit++ == 10) {
					break;
				}
				*/
				var value = this.data[this.gene_index[gene_name]][this.sample_index[sample_name]];
				//str += "<td class='datacell'" + this.getStyle(value) + ">" + value + "</td>";
				//str += "<td class='datacell'>" + value + "</td>";
				str += "<td>" + value + "</td>";
			}
			str += "</tr>";
		}
		str += "</tbody>";
		return str;
	},

	getValues: function() {
		var values = [];
		for (var sample_name in this.sample_index) {
			for (var gene_name in this.gene_index) {
				var value = this.data[this.gene_index[gene_name]][this.sample_index[sample_name]];
				values.push(value);
			}
		}
		return values;
	},

	setData: function(gene_nn, sample_nn, value) {
		if (is_empty_value(value)) {
			this.empty_value_cnt++;
			value = '';
		} else {
			var ivalue = parseFloat(value);

			if (!isNaN(ivalue)) {
				if (ivalue < this.minval) {
					this.minval = ivalue;
				}
				if (ivalue > this.maxval) {
					this.maxval = ivalue;
				}
				var ivalue_abs = Math.abs(ivalue);
				if (ivalue_abs < this.minval_abs) {
					this.minval_abs = ivalue_abs;
				}
				if (ivalue_abs > this.maxval_abs) {
					this.maxval_abs = ivalue_abs;
				}
			} else if (this.biotype.isContinuous()) {
				return "expected numeric value, got '" + value + "'";
			}
		}
		if (this.biotype.isDiscrete() || this.biotype.isSet()) {
			this.discrete_values_map[value] = 1;
		}
		this.data[gene_nn][sample_nn] = value;
		return '';
	},

	getSampleCount: function(gene_name) {
		if (!gene_name) {
			if (this.biotype.isSet()) {
				return 0;
			}
			return mapSize(this.sample_index);
		}
		var gene_idx = this.gene_index[gene_name];
		if (gene_idx == undefined) {
			return -1;
		}
		if (this.biotype.isSet()) {
			return 0;
		}
		var cnt = 0;
		for (var sample_name in this.sample_index) {
			var value = this.data[gene_idx][this.sample_index[sample_name]];
			if (value != '') {
				cnt++;
			}
		}
		
		return cnt;
	},

	getGeneCountPerModule: function(module_name) {
		if (!module_name) {
			return mapSize(this.gene_index);
		}
		var cnt = 0;
		var hugo_map = navicell.mapdata.hugo_map;
		for (var gene_name in this.gene_index) {
			if (hugo_map[gene_name][module_name]) {
				cnt++;
			}
		}
		return cnt;
	},

	getGeneCount: function(sample_name) {
		var sample_idx = this.sample_index[sample_name];
		if (sample_idx == undefined) {
			return -1;
		}
		var cnt = 0;
		for (var gene_name in this.gene_index) {
			var value = this.data[this.gene_index[gene_name]][sample_idx];
			if (value != '') {
				cnt++;
			}
		}
		
		return cnt;
	},

	getClass: function() {return "Datatable";}
};

function DrawingConfig() {
	this.display_markers = 1;
	this.display_old_markers = 1;

	this.display_charts = "Heatmap";
	this.heatmap_config = new HeatmapConfig();
	this.editing_heatmap_config = new HeatmapConfig();
	this.barplot_config = new BarplotConfig();
	this.editing_barplot_config = new BarplotConfig();
	//this.piechart_config = new PiechartConfig();

	this.glyph_configs = [];
	this.editing_glyph_configs = [];
	this.display_glyphs = [];
	for (var num = 1; num <= GLYPH_COUNT; ++num) {
		this.glyph_configs.push(new GlyphConfig());
		this.editing_glyph_configs.push(new GlyphConfig());
		this.display_glyphs.push(0);
	}

	this.display_labels = 0;

	this.display_DLOs_on_all_genes = 1;
}

DrawingConfig.prototype = {

	getGlyphConfig: function(num) {
		return this.glyph_configs[num-1];
	},

	getEditingGlyphConfig: function(num) {
		return this.editing_glyph_configs[num-1];
	},

	displayMarkers: function() {
		return this.display_markers;
	},

	displayOldMarkers: function() {
		return this.display_old_markers != "0";
	},

	displayOldMarkersWithDifferentColor: function() {
		return this.display_old_markers == "1";
	},

	displayOldMarkersWithSameColor: function() {
		return this.display_old_markers == "2";
	},

	displayCharts: function() {
		return this.display_charts;
	},

	displayAnyGlyph: function() {
		for (var num = 1; num <= GLYPH_COUNT; ++num) {
			if (this.displayGlyphs(num)) {
				return true;
			}
		}
		return false;
	},

	displayGlyphs: function(num) {
		return this.display_glyphs[num-1];
	},

	displayLabels: function() {
		return this.display_labels;
	},

	displayHeatmaps: function() {
		return this.display_charts == "Heatmap";
	},

	displayBarplots: function() {
		return this.display_charts == "Barplot";
	},

	displayDLOs: function() {
		return this.displayCharts() || this.displayAnyGlyph() || this.displayLabels();
	},

	displayDLOsOnAllGenes: function() {
		return this.display_DLOs_on_all_genes;
	},

	setDisplayDLOsOnAllGenes: function(val) {
		this.display_DLOs_on_all_genes = val;
	},

	setDisplayMarkers: function(val) {
		this.display_markers = val;
	},

	setDisplayOldMarkers: function(val) {
		this.display_old_markers = val;
	},

	setDisplayGlyphs: function(num, val) {
		this.display_glyphs[num-1] = val;
	},

	setDisplayCharts: function(val, chart_type) {
		if (!val) {
			this.display_charts = 0;
		} else {
			this.display_charts = chart_type;
		}
	},

	sync: function() {
		for (var map_name in maps) {
			var doc = maps[map_name].document;
			if (doc == window.document) {
				//continue;
			}
			if (!this.display_charts) {
				$("#drawing_config_chart_display", doc).attr("checked", false);
			} else {
				$("#drawing_config_chart_display", doc).attr("checked", true);
				$("#drawing_config_chart_type", doc).val(this.display_charts);
			}
			$("#drawing_config_marker_display", doc).attr("checked", this.display_markers);
			$("#drawing_config_old_marker", doc).val(this.display_old_markers);
		}
	}
};

function get_module(win) {
	if (!win) {
		win = window;
	}
	return win.document.navicell_module_name;
}

function switch_view(id) {
	var module = get_module();
	console.log("module: " + module);
	var datatable = navicell.dataset.datatables_id[id];
	if (datatable) {
		datatable.switchView(window);
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
	this.all_line_read = [];
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

	refresh: function() {
		if (!this.readannots(null, null)) {
			return;
		}

		var annots = this.annots_per_name;
		var annot_cnt = mapSize(annots);
		for (var annot_name in annots) {
			var annot = this.getAnnotation(annot_name);
			var annot_id = annot.id;
			var checked = $("#cb_annot_" + annot_id).attr("checked");
			annot.setIsGroup(checked);
		}
		navicell.group_factory.buildGroups();
//		$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'>" + mapSize(navicell.group_factory.group_map) + "</span> groups of samples have been built (groups are listed in Data Status / Groups tab) </span>");
		// TBD: factorize message with annot_set_group in dialoglib.js
		$("#dt_sample_annot_status").html("</br><span class=\"status-message\"><span style='font-weight: bold'>" + mapSize(navicell.group_factory.group_map) + "</span> groups of samples: groups are listed in My Data / Groups tab</span>");
	},

	readannots: function(ready, error_trigger) {
		var lines = this.all_line_read;
		if (!lines.length) {
			return 0;
		}
		var header;
		var header_cnt;

		var sep = null;
		
		this.sample_read = 0;
		this.sample_annotated = 0;

		for (var ii = 0; ii < INPUT_SEPS.length; ++ii) {
			sep = INPUT_SEPS[ii];
			header = lines[0].trim().split(sep);
			header_cnt = header.length;
			if (!header[header.length-1]) {
				--header_cnt;
			}
			if (header_cnt > 1) {
				break;
			}
		}

		if (header_cnt < 2) {
			this.error = "invalid file format";
			console.log("ERROR: " + this.error);
			if (ready) {
				ready.resolve();
			}
		}
		var annot_cnt = header_cnt - 1;
		var sample_cnt = lines.length-1;
		var missing_cnt = 0;
		this.missing = "";
		for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
			var line = lines[sample_nn+1].trim().split(sep);
			var line_cnt = line.length-1;
			if (!line[line.length-1]) {
				--line_cnt;
			}
			if (line_cnt < 2) {
				continue;
			}
			if (line_cnt > annot_cnt) {
				this.error = "line #" + (sample_nn) + ", expected " + annot_cnt + " annotations, got " + line_cnt;
				console.log("ERROR2: " + this.error);
				if (ready) {
					ready.resolve();
				}
				if (error_trigger) {
					error_trigger(file);
				}
				return 0;
			}
			var sample_name = line[0];
			if (!navicell.dataset.getSample(sample_name)) {
				if (missing_cnt < 10) {
					if (this.missing) {
						this.missing += ", ";
					}
					this.missing += sample_name;
				} else if (missing_cnt == 10) {
					this.missing += "..."
				}
				missing_cnt++;
				continue;
			}
			for (var annot_nn = 0; annot_nn < line_cnt; ++annot_nn) {
				var annot_value = line[annot_nn+1];
				var annot_name = header[annot_nn+1];
				this.addAnnotValue(sample_name, annot_name, annot_value);
			}
			//console.log("sample_read: " + this.sample_read
			this.sample_read++;
		}
		if (this.sample_read > 0) {
			for (var nn = 1; nn < header_cnt; ++nn) {
				this.getAnnotation(header[nn]);
			}
		}
		console.log("done");
		this.sample_annotated = this.sync();
		if (ready) {
			ready.resolve();
		}
		return 1;
	},

	readfile: function(file, error_trigger) {
		var reader = new FileReader();
		reader.readAsBinaryString(file);

		var annot_factory = this;
		/*
		this.sample_read = 0;
		this.sample_annotated = 0;
		*/
		var ready = this.ready = $.Deferred(reader.onload);
		reader.onload = function() { 
			var text = reader.result;
			var lines = text.split(LINE_BREAK_REGEX);
			array_push_all(annot_factory.all_line_read, lines);
			annot_factory.readannots(ready, error_trigger);
			/*
			var header;
			var header_cnt;

			var sep = null;
			
			for (var ii = 0; ii < INPUT_SEPS.length; ++ii) {
				sep = INPUT_SEPS[ii];
				header = lines[0].trim().split(sep);
				header_cnt = header.length;
				if (!header[header.length-1]) {
					--header_cnt;
				}
				if (header_cnt > 1) {
					break;
				}
			}

			if (header_cnt < 2) {
				this.error = "invalid file format";
				console.log("ERROR: " + this.error);
				ready.resolve();
			}
			var annot_cnt = header_cnt - 1;
			var sample_cnt = lines.length-1;
			var missing_cnt = 0;
			annot_factory.missing = "";
			for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
				var line = lines[sample_nn+1].trim().split(sep);
				var line_cnt = line.length-1;
				if (!line[line.length-1]) {
					--line_cnt;
				}
				if (line_cnt < 2) {
					continue;
				}
				if (line_cnt > annot_cnt) {
					this.error = "line #" + (sample_nn) + ", expected " + annot_cnt + " annotations, got " + line_cnt;
					console.log("ERROR2: " + this.error);
					ready.resolve();
					if (error_trigger) {
						error_trigger(file);
					}
					return;
				}
				var sample_name = line[0];
				if (!navicell.dataset.getSample(sample_name)) {
					if (missing_cnt < 10) {
						if (annot_factory.missing) {
							annot_factory.missing += ", ";
						}
						annot_factory.missing += sample_name;
					} else if (missing_cnt == 10) {
						annot_factory.missing += "..."
					}
					missing_cnt++;
					continue;
				}
				for (var annot_nn = 0; annot_nn < line_cnt; ++annot_nn) {
					var annot_value = line[annot_nn+1];
					var annot_name = header[annot_nn+1];
					annot_factory.addAnnotValue(sample_name, annot_name, annot_value);
				}
				//console.log("sample_read: " + annot_factory.sample_read
				annot_factory.sample_read++;
			}
			if (annot_factory.sample_read > 0) {
				for (var nn = 1; nn < header_cnt; ++nn) {
					annot_factory.getAnnotation(header[nn]);
				}
			}
			console.log("done");
			annot_factory.sample_annotated = annot_factory.sync();
			ready.resolve();
			*/
		},
		reader.onerror = function(e) {  // If anything goes wrong
			if (error_trigger) {
				error_trigger(file);
			}
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

function Gene(name, hugo_module_map, id) {
	this.name = name;
	this.hugo_module_map = hugo_module_map;
	this.refcnt = 1;
	this.id = id;
}

Gene.prototype = {
	name: "",
	hugo_module_map: {},

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
	this.methods = navicell.group_factory.getSavedMethods(this.name);
	this.html_name = "";
	for (var nn = 0; nn < annots.length; ++nn) {
		this.html_name += (this.html_name.length > 0 ? "<br>" : "") + '<span class="group_name">' + annots[nn].replace(/ /g, '&nbsp;') + ':</span>&nbsp;<span class="group_value">' + values[nn].replace(/ /g, '&nbsp;')  + '</span>';
	}
}

Group.CONTINUOUS_AVERAGE = "1";
Group.CONTINUOUS_MINVAL = "2";
Group.CONTINUOUS_MAXVAL = "3";
Group.CONTINUOUS_ABS_AVERAGE = "4";
Group.CONTINUOUS_ABS_MINVAL = "5";
Group.CONTINUOUS_ABS_MAXVAL = "6";

Group.DISCRETE_IGNORE = 0;
Group.DISCRETE_EQ_0 = 1;
Group.DISCRETE_GT_0 = 2;
Group.DISCRETE_EQ_ALL = 3;

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

	acceptCondition: function(datatable, gene_name, cond_value, cond) {
		if (cond == Group.DISCRETE_IGNORE) {
			return false;
		}
		var eq_cnt = 0;
		for (var sample_name in this.samples) {
			var value = datatable.getValue(sample_name, gene_name);
			var eq = (cond_value == value);
			if (eq) {
				if (cond == Group.DISCRETE_EQ_0) {
					return false;
				}
				if (cond == Group.DISCRETE_GT_0) {
					return true;
				}
				eq_cnt++;
			}
		}

		if (cond == Group.DISCRETE_EQ_0) {
			return eq_cnt == 0;
		}
		if (cond == Group.DISCRETE_GT_0) {
			return eq_cnt > 0;
		}
		if (cond == Group.DISCRETE_EQ_ALL) {
			return eq_cnt == this.samples.length;
		}
		return false;
	},

	getValue: function(datatable, gene_name, method) {
		if (!method) {
			method = this.getMethod(datatable);
		}
		if (datatable.biotype.isContinuous()) {
			if (method == Group.CONTINUOUS_AVERAGE || method == Group.CONTINUOUS_ABS_AVERAGE) {
				var total_value = 0;
				var total_absvalue = 0;
				var cnt = 0;
				for (var sample_name in this.samples) {
					var value = datatable.getValue(sample_name, gene_name);
					if (value == '') {
						continue;
					}
					value *= 1.;
					var absvalue = Math.abs(value);
					if (value) {
						total_value += value;
						total_absvalue += absvalue;
						cnt++;
					}
				}
				if (cnt) {
					return method == Group.CONTINUOUS_AVERAGE ? total_value/cnt : total_absvalue/cnt;
				}
				return undefined;
			}
			if (method == Group.CONTINUOUS_MINVAL || method == Group.CONTINUOUS_MAXVAL || method == Group.CONTINUOUS_ABS_MINVAL || method == Group.CONTINUOUS_ABS_MAXVAL) {
				var min = Number.MAX_NUMBER;
				var max = Number.MIN_NUMBER;
				var absmin = Number.MAX_NUMBER;
				var absmax = Number.MIN_NUMBER;
				var min_set = false;
				var max_set = false;
				var absmin_set = false;
				var absmax_set = false;
				for (var sample_name in this.samples) {
					var value = datatable.getValue(sample_name, gene_name);
					if (value == '') {
						continue;
					}
					value *= 1.;
					var absvalue = Math.abs(value);
					if (value < min) {
						min = value;
						min_set = true;
					}
					if (value > max) {
						max = value;
						max_set = true;
					}
					if (absvalue < absmin) {
						absmin = absvalue;
						absmin_set = true;
					}
					if (absvalue > absmax) {
						absmax = absvalue;
						absmax_set = true;
					}
				}
				if (method == Group.CONTINUOUS_MINVAL) {
					return min_set ? min : undefined;
				}
				if (method == Group.CONTINUOUS_MAXVAL) {
					return max_set ? max : undefined;
				}
				if (method == Group.CONTINUOUS_ABS_MINVAL) {
					return absmin_set ? absmin : undefined;
				}
				if (method == Group.CONTINUOUS_ABS_MAXVAL) {
					return absmax_set ? absmax : undefined;
				}
				return undefined;
			}
			return undefined;
		}
		var method_len = method.length;
		var method_value = method.substring(0, method_len-1);
		var mod = method.substring(method_len-1, method_len);
		var all = mod == '@';
		var not = mod == '-';
		var value_cnt = 0;
		var sample_cnt = 0;
		for (var sample_name in this.samples) {
			var value = datatable.getValue(sample_name, gene_name);
			if (value == method_value) {
				value_cnt++;
			}
			sample_cnt++;
		}

		if (not) {
			return sample_cnt != value_cnt;
		}
		if ((value_cnt > 0 && !all) || sample_cnt == value_cnt) {
			return method_value;
		}
		return undefined;
	},

	getClass: function() {return "Group";}
};


//
// GroupFactory class
//

function GroupFactory() {
	this.group_id = 1;
	this.saved_methods = {};
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

	getSavedMethods: function(group_name) {
		var methods = this.saved_methods[group_name];
		return methods ? methods : {};
	},

	buildGroups: function() {
		this.saved_methods = {};
		for (var group_name in this.group_map) {
			var group = this.group_map[group_name];
			this.saved_methods[group_name] = group.methods;
		}
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

function BiotypeType(type, subtype) {
	this.type = type;
	this.subtype = subtype;
}

BiotypeType.prototype = {

	isExpression: function() {
		return this.type == navicell.EXPRESSION;
	},

	isCopyNumber: function() {
		return this.type == navicell.COPYNUMBER;
	},

	isMutation: function() {
		return this.type == navicell.MUTATION;
	},

	isGeneList: function() {
		return this.type == navicell.GENELIST;
	},
	
	isContinuous: function() {
		return this.subtype == navicell.CONTINUOUS;
	},

	isDiscrete: function() {
		return this.subtype == navicell.DISCRETE;
	},

	isSet: function() {
		return this.subtype == navicell.SET;
	}
};

function Biotype(name, type) {
	this.name = name;
	this.type = type; // BiotypeType
}

Biotype.prototype = {
	name: "",
	type: null,

	isContinuous: function() {
		return this.type.isContinuous();
	},

	isDiscrete: function() {
		return this.type.isDiscrete();
	},

	isSet: function() {
		return this.type.isSet();
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

//
// Session class
//

if (typeof Storage != 'undefined') {
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
				  <			console.log("session init4: " + mapSize(data.dataset.genes));
				  console.log("session init4: " + data.dataset.geneCount());
				*/
				return _navicell;
			}
			return navicell_init();
		}
	}

}

//var navicell_session = new NavicellSession("navicell");
//navicell_session.reset();

var SIMPLIFY_TYPES = 1;
function navicell_init() {
	var _navicell = {}; // namespace

	_navicell.module_names = [];
	//_navicell.mapdata = new Mapdata();
	_navicell.dataset = new Dataset("navicell");
	_navicell.group_factory = new GroupFactory();
	_navicell.biotype_factory = new BiotypeFactory();
	_navicell.annot_factory = new AnnotationFactory();
	_navicell.drawing_config = new DrawingConfig();
	_navicell.module_init = {};

	_navicell.EXPRESSION = 1;
	_navicell.COPYNUMBER = 2;
	_navicell.MUTATION = 3;
	_navicell.GENELIST = 4;

	_navicell.CONTINUOUS = 10;
	_navicell.DISCRETE = 20;
	_navicell.SET = 30;

	if (SIMPLIFY_TYPES) {
		var biotypeExpr = new BiotypeType(_navicell.EXPRESSION, _navicell.CONTINUOUS);
		//_navicell.biotype_factory.addBiotype(new Biotype("mRNA, microRNA or Protein Expression data", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", biotypeExpr));
		_navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", biotypeExpr));
		_navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", biotypeExpr));
		_navicell.biotype_factory.addBiotype(new Biotype("Copy number data", new BiotypeType(_navicell.COPYNUMBER, _navicell.CONTINUOUS)));
		_navicell.biotype_factory.addBiotype(new Biotype("Mutation data", new BiotypeType(_navicell.MUTATION, _navicell.DISCRETE)));
		_navicell.biotype_factory.addBiotype(new Biotype("Gene list",  new BiotypeType(_navicell.GENELIST, _navicell.SET)));
	} else {
		_navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("Copy number data", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: methylation profiles", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: histone modifications", _navicell.CONTINUOUS));
		_navicell.biotype_factory.addBiotype(new Biotype("Polymorphism data: SNPs", _navicell.DISCRETE));
		_navicell.biotype_factory.addBiotype(new Biotype("Mutation data", _navicell.DISCRETE));
		// disconnected for now:
		// _navicell.biotype_factory.addBiotype(new Biotype("Interaction data", _navicell.SET));
		//_navicell.biotype_factory.addBiotype(new Biotype("Set data", _navicell.SET));
	}
	_navicell.getDatatableById = function(id) {
		/*
		console.log("this.dataset: " + this.dataset.getClass() + " " + id);
		console.log("-> datatable: " + this.dataset.getDatatableById(id));
		console.log("-> datatable class: " + this.dataset.getDatatableById(id).getClass());
		*/
		return this.dataset.getDatatableById(id);
	}

	_navicell.isModuleInit = function(module) {
		return _navicell.module_init[module];
	},

	_navicell.setModuleInit = function(module) {
		_navicell.module_init[module] = 1;
	},

	_navicell.declareWindow = function(win) {
		var dataset = _navicell.dataset;
		console.log("dataset: " + dataset);
		for (var datatable_name in dataset.datatables) {
			dataset.datatables[datatable_name].declareWindow(win);
		}
	},
	
	_navicell.shapes = ["Triangle", "Square", "Rectangle", "Diamond", "Hexagon", "Circle"];
//	_navicell.shapes = ["Triangle", "Square", "Diamond", "Hexagon", "Circle"];

	return _navicell;
}

function jquery_to_dom(obj) {
	var dom_objs = [];
	for (var nn = 0; nn < obj.length; nn++) {
		dom_objs.push(obj.get(nn));
	}
	return dom_objs;
}

/*
function jquery_to_dom_r(dom_objs, obj) {
	if (obj.childNodes.length == 0) {
		dom_objs.push(obj);
	} else {
		for (var jj = 0; jj < obj.childNodes.length; ++jj) {
			var child = obj.childNodes[jj];
			dom_objs.push(child);
			jquery_to_dom_r(dom_objs, child);
		}
	}
}

function jquery_to_dom(obj) {
	var dom_objs = [];
	for (var nn = 0; nn < obj.length; nn++) {
		var o = obj.get(nn);
		jquery_to_dom_r(dom_objs, o);
	}
	return dom_objs;
}
*/

function stddev(f) {
	var x = 0;
	var x2 = 0;
	for (var i=0; i < f.length; i++){
		x += f[i];
		x2 += f[i]*f[i];
	}
	x /= f.length;
	x2 /= f.length;
	return Math.sqrt((x2-x*x)*f.length/(f.length-1));
}

function getPositiveThreshold(numbers, avg) {
	var thresh = 1;
	var positives = [];
	for (var i=0; i < numbers.length; i++) {
		var num = parseFloat(numbers[i]);
		if (!isNaN(num)) {
			if (num > avg) {
				positives.push(num);
				//positives.push(2*avg - num);
				//positives.push(-num);
			}
		}
	}

	return avg + stddev(positives);
}	

function getNegativeThreshold(numbers, avg) {
	var thresh = 1;
	var negatives = [];
	for (var i=0; i < numbers.length; i++) {
		var num = parseFloat(numbers[i]);
		if (!isNaN(num)) {
			if (num < avg) {
				//negatives.push(-num);
				negatives.push(num);
				//negatives.push(2*avg - num);
			}
		}
	}
	return avg - stddev(negatives);
}
	
function getFG_from_BG(color) {
	var rgb1 = color.substring(0, 2);
	var rgb2 = color.substring(2, 4);
	var rgb3 = color.substring(4, 6);
	rgb1 = parseInt("0x" + rgb1)/256.;
	rgb2 = parseInt("0x" + rgb2)/256.;
	rgb3 = parseInt("0x" + rgb3)/256.;
	return 0.213 * rgb1 + 0.715 * rgb2 + 0.072 * rgb3 < 0.5 ? 'FFF' : '000';
}

function build_entity_tree_when_ready(win, div_name, projection, whenloaded) {
	navicell.mapdata.buildEntityTreeWhenReady(win, div_name, projection, whenloaded);
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
