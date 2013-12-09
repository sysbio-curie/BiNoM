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


if (!window.console) {
	window.console = new function()
	{
		this.log = function(str) {};
		this.dir = function(str) {};
	};
}

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
			$("#result_tree_header", win.document).html(res_jxtree.found + " elements matching \"" + to_find + "\"");

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
			$("img.blogfromright").click(open_blog_click);
			$("img.mapmodulefromright").click(open_module_map_click);
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
				console.log("to_find_str [" + to_find_str + "]");
				hints.case_sensitive = true;
				mapdata.searchFor(win, to_find_str, hints.div);
				//mapdata.findJXTreeContinue(jxtree, to_find_str, no_ext, win, action, hints, module_name);
				mapdata.findJXTreeContinue(win, to_find_str, no_ext, action, hints);
				/*
				var user_find = mapdata.useJXTreeSimpleFind(jxtree);
				res_jxtree = jxtree.find(to_find_str, action, hints);
				mapdata.restoreJXTreeFind(jxtree, user_find);
				*/
			} else {
				mapdata.searchFor(win, to_find, hints.div);
				setTimeout(function() {
					//mapdata.findJXTreeContinue(jxtree, to_find, no_ext, win, action, hints, module_name);
					mapdata.findJXTreeContinue(win, to_find, no_ext, action, hints);
				}, 20);
				/*
				  res_jxtree = jxtree.find(to_find, action, hints);
				*/
			}

			return;

			/*
			if (res_jxtree == null) {
				if (hints.error || hints.help) {
					var dialog = $("#info_dialog", win.document);
					var msg = hints.error ? hints.error : hints.help;
					var title = hints.error ? "Searching Error" : "Search Help";
					dialog.html("<div style='text-align: vertical-center'><h3>" + title + "</h3>" + msg.replace(new RegExp("\n", "g"), "<br>") + "</div>");
					dialog.dialog({
						resizable: true,
						width: 430,
						height: 660,
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
				console.log("here");
				$("#result_tree_header", win.document).html(res_jxtree.found + " elements matching \"" + to_find + "\"");

				uncheck_all_entities();

				res_jxtree.context = {};

				tree_context_prologue(res_jxtree.context);
				$.each(res_jxtree.getRootNodes(), function() {
					this.checkSubtree(JXTree.CHECKED);
					this.showSubtree(JXTree.OPEN);
				});
				tree_context_epilogue(res_jxtree.context);

				time_cnt++;
				mapdata.module_res_jxtree[module_name] = res_jxtree;
			}
			*/
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

	readDatatable: function(biotype_name, name, file) {
		var datatable = new Datatable(this, biotype_name, name, file, this.datatable_id++);
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
	
	// behaves as a gene factory
	addGene: function(gene_name, hugo_module_map) {
		if (!this.genes[gene_name]) {
			var gene = new Gene(gene_name, hugo_module_map, this.gene_id++);
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
		} else if (datatable.displayDiscreteConfig) {
			datatable.displayDiscreteConfig.update();
		}
		return true;
	},

	drawDLO: function(overlay, context, scale, gene_name, topx, topy) {
		var size = 2;
		//console.log("Drawing " + gene_name);
		//context.fillStyle = 'rgba(100, 30, 100, 1)';
		//context.fillRect(topx, topy, (size+2)*scale, size*scale);
		var bound = null;
		if (navicell.drawing_config.displayGlyphs()) {
			bound = draw_glyph(overlay, context, scale, gene_name, topx, topy);
		}
		if (bound) {
			//topx += (bound[0] - topx + bound[2] + 2);
			topx = bound[0] + bound[2] - 6;
		}
		if (navicell.drawing_config.displayHeatmaps()) {
			draw_heatmap(overlay, context, scale, gene_name, topx, topy);
		}
		if (navicell.drawing_config.displayBarplots()) {
			draw_barplot(overlay, context, scale, gene_name, topx, topy);
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

function step_color_count_change(id) {
	var datatable = navicell.dataset.datatables_id[id];
	if (datatable) {
		var value = $("#step_config_count_" + id).val();
		//console.log("value: " + value);
		datatable.displayStepConfig.setStepCount(value);
	}
}

function DisplayDiscreteConfig(datatable) {
	this.datatable = datatable;
	this.values = [];
	this.values_idx = {};
	for (var value in datatable.getDiscreteValues()) {
		this.values.push(value);
	}
	this.values.sort();
	for (var idx = 0; idx < this.values.length; ++idx) {
		var value = this.values[idx];
		this.values_idx[value] = idx;
	}
	this.buildDiv();
	this.buildValues();
	this.setDefaults();
}

DisplayDiscreteConfig.prototype = {
	
	buildDiv: function() {
		var id = this.datatable.getId();
		var div_editing_id = "discrete_config_editing_" + id;
		var div_id = "discrete_config_" + id;
		var html = "<div class='discrete-config' id='" + div_id + "'>\n";
		html += "<h3 id='discrete_config_title_" + id + "'></h3>";
		html += "<div id='" + div_editing_id + "' class='discrete-config-editing'>\n</div>";
		html += "<table class='discrete-config-table' id='discrete_config_table_" + id + "'>";
		html += "</table>";

		html += "</div>";
		this.div_id = div_id;
		$('body').append(html);
	},

	buildValues: function() {
		var size = this.values.length;
		this.colors = new Array(size);
		this.sizes = new Array(size);
		this.shapes = new Array(size);
		this.update();
	},

	getValueAt: function(idx) {
		return this.values[idx];
	},

	getValueCount: function() {
		return this.values.length;
	},

	setValueInfo: function(idx, color, size, shape) {
		if (idx < this.colors.length) {
			console.log("setting discrete at " + idx + " " + color + " " + size + " " + shape);
			this.colors[idx] = color;
			this.sizes[idx] = size;
			this.shapes[idx] = shape;
		}
	},

	setDefaults: function() {
		//this.setValueInfo(0, "F7FF19", 4, 0);
		this.setValueInfo(0, "FFE4B5", 4, 0);
		this.setValueInfo(1, "D6ECFF", 6, 1);
		//this.setValueInfo(2, "19FF57", 8, 2);
		this.setValueInfo(2, "B3FF4F", 8, 2);
		//this.setValueInfo(3, "6421FF", 10, 3);
		this.setValueInfo(3, "8F57FF", 10, 3);
		//this.setValueInfo(4, "E64515", 12, 4);
		this.setValueInfo(4, "E65EC2", 12, 4);
		this.setValueInfo(5, "CC0044", 14, 5);
		this.update();
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
		var idx = this.values_idx[value];
		if (idx != undefined) {
			return this.colors[idx];
		}
		return '';
	},

	getSize: function(value) {
		var idx = this.values_idx[value];
		if (idx != undefined) {
			return this.sizes[idx];
		}
		return '';
	},

	getShape: function(value) {
		var idx = this.values_idx[value];
		if (idx != undefined) {
			return this.shapes[idx];
		}
		return '';
	},

	update: function() {
		var id = this.datatable.getId();
		var table = $("#discrete_config_table_" + id);
		table.children().remove();
		var html = "<thead>";
		html += "<th>Values</th>";
		html += "<th>Color</th>";
		html += "<th>Size</th>";
		html += "<th>Shape</th>";
		html += "</thead><tbody>";
		var step_cnt = this.values.length;
		for (var idx = 0; idx < step_cnt; idx++) {
			html += "<tr>";
			var value = this.getValueAt(idx);
			if (value == '') {
				value = "<span style='font-style: italic;'>empty</span>";
			}
			html += "<td>" + value + "</td>";
			html += "<td><input id='discrete_config_" + id + "_" + idx + "' value='" + this.getColorAt(idx) + "' class='color' onchange='display_discrete_config_set_editing(" + id + ", true)'></input></td>";
			//html += "<td><input class='color' value='" + "000000" + "'></input></td>";
			html += "<td><select id='discrete_size_" + id + "_" + idx + "' onchange='display_discrete_config_set_editing(" + id + ", true)'>";
			html += "<option value='_none_'>Choose a size</option>";
			var selsize = this.getSizeAt(idx);
			//var maxsize = DISCRETE_MAX_SIZE/2;
			var maxsize = this.getValueCount()*DISCRETE_SIZE_COEF;
			for (var size = 0; size < maxsize; size += 1) {
				var size2 = DISCRETE_SIZE_COEF*(size+2);
				html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
			}
			html += "</select></td>";
			html += "<td><select id='discrete_shape_" + id + "_" + idx + "' onchange='display_discrete_config_set_editing(" + id + ", true)''>";
			html += "<option value='_none_'>Choose a shape</option>";
			var selshape = this.getShapeAt(idx);
			for (var shape in navicell.shapes) {
				html += "<option value='" + shape + "' " + (shape == selshape ? "selected" : "") + ">" + navicell.shapes[shape] + "</option>";
			}
			html += "</select></td>";
			html += "</tr>\n";
		}

		html += "</tbody>";
		table.append(html);

		var title = $("#discrete_config_title_" + id);
		title.html("Datatable " + this.datatable.name + ": Display Configuration (Discrete)");

		jscolor.init();
	}
};

function DisplayStepConfig(datatable) {
	this.datatable = datatable;
	var step_cnt = 5;
	this.buildDiv(step_cnt);
	this.setStepCount(step_cnt);
	this.setDefaults(step_cnt);
}

var STEP_MAX_SIZE = 14.;
var DISCRETE_SIZE_COEF = 2.;

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
		//console.log("step_cnt: " + step_cnt + " step: " + step + " " + this.datatable.minval + " " + this.datatable.maxval);
		//var step_cnt_1 = step_cnt-1;
		for (var nn = 0; nn < step_cnt-1; ++nn) {
			var value = this.datatable.minval + (nn+1)*step;
			value = parseInt(value*100)/100;
			this.values.push(value);
		}
		this.values.push(this.datatable.maxval);
		/*
		for (var nn = 0; nn < this.values.length; ++nn) {
			console.log("values[" + nn + "] = " + this.values[nn]);
		}
		*/
		this.colors = new Array(step_cnt);
		this.sizes = new Array(step_cnt);
		this.shapes = new Array(step_cnt);
		this.update();
		this.datatable.refresh();
		
		// AZ code
		var avg = (this.datatable.minval + this.datatable.maxval)/2;
		var dt_values = this.datatable.getValues();
		var posthr = getPositiveThreshold(dt_values, avg);
		var negthr = getNegativeThreshold(dt_values, avg);
		//console.log("avg: " + avg + " " + posthr + " " + negthr);
	},

	setStepInfo: function(idx, value, color, size, shape) {
		//console.log("setting at " + idx + " " + value + " (== " + this.values[idx+1] + ") " + color + " " + size + " " + shape);
		if (value != Number.MIN_NUMBER) {
			this.values[idx+1] = value;
		}
		this.colors[idx] = color;
		this.sizes[idx] = size;
		this.shapes[idx] = shape;
	},

	setDefaults: function(step_cnt) {
		if (step_cnt == 5) {
			//this.setStepInfo(0, Number.MIN_NUMBER, "F7FF19", 4, 0);
			this.setStepInfo(0, Number.MIN_NUMBER, "FFE4B5", 4, 0);
			this.setStepInfo(1, Number.MIN_NUMBER, "D6ECFF", 6, 1);
			//this.setStepInfo(2, Number.MIN_NUMBER, "19FF57", 8, 2);
			this.setStepInfo(2, Number.MIN_NUMBER, "B3FF4F", 8, 2);
			//this.setStepInfo(3, Number.MIN_NUMBER, "6421FF", 10, 3);
			this.setStepInfo(3, Number.MIN_NUMBER, "8F57FF", 10, 3);
			//this.setStepInfo(4, Number.MIN_NUMBER, "E64515", 12, 4);
			this.setStepInfo(4, Number.MIN_NUMBER, "E65EC2", 12, 4);
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
		var table = $("#step_config_table_" + id);
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
				html += "<td><input type='text' id='step_value_" + id + "_" + idx + "' value='" + this.getValueAt(idx) + "' onchange='display_step_config_set_editing(" + id + ", true)'></input></td>";
			}
			html += "<td><input id='step_config_" + id + "_" + idx + "' value='" + this.getColorAt(idx) + "' class='color' onchange='display_step_config_set_editing(" + id + ", true)'></input></td>";
			html += "<td><select id='step_size_" + id + "_" + idx + "' onchange='display_step_config_set_editing(" + id + ", true)'>";
			html += "<option value='_none_'>Choose a size</option>";
			var selsize = this.getSizeAt(idx);
			var maxsize = STEP_MAX_SIZE/2;
			for (var size = 2; size < maxsize; size += 1) {
				var size2 = 2*size;
				html += "<option value='" + size2 + "' " + (size2 == selsize ? "selected" : "") + ">" + size2 + "</option>";
			}
			html += "</select></td>";
			html += "<td><select id='step_shape_" + id + "_" + idx + "' onchange='display_step_config_set_editing(" + id + ", true)''>";
			html += "<option value='_none_'>Choose a shape</option>";
			var selshape = this.getShapeAt(idx);
			for (var shape in navicell.shapes) {
				html += "<option value='" + shape + "' " + (shape == selshape ? "selected" : "") + ">" + navicell.shapes[shape] + "</option>";
			}
			html += "</select></td>";
			html += "</tr>\n";
		}
		//html += "<tr><td>Maximum " + this.datatable.maxval + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>\n";
		html += "</tbody>";
		table.append(html);

		var count = $("#step_config_count_" + id);

		var title = $("#step_config_title_" + id);
		title.html("Datatable " + this.datatable.name + ": Display Configuration (Continuous)");

		jscolor.init();
	},

	buildDiv: function(step_cnt) {
		var id = this.datatable.getId();
		var div_editing_id = "step_config_editing_" + id;
		var div_id = "step_config_" + id;
		var html = "<div class='step-config' id='" + div_id + "'>\n";
		html += "<h3 id='step_config_title_" + id + "'></h3>";
		html += "<div id='" + div_editing_id + "' class='step-config-editing'></div>\n";
		html += "<table class='step-config-table' id='step_config_table_" + id + "'>";
		html += "</table>";

		html += "<BR/><BR/>Step Count <select id='step_config_count_" + id + "' onchange='step_color_count_change(" + id + ")'>";
		for (var step = 3; step < 12; ++step) {
			html += "<option value='" + step + "' " + (step == step_cnt ? "selected" : "") + ">" + step + "</option>";
		}
		html += "</select>";
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
	}

};

// TBD datatable id management
function Datatable(dataset, biotype_name, name, file, datatable_id) {
	var reader = new FileReader();
	var ready = this.ready = $.Deferred(reader.onload);
	if (dataset.datatables[name]) {
		this.error = "datatable " + name + " already exists";
		ready.resolve();
		return;
	}
	this.minval = Number.MAX_NUMBER;
	this.maxval = Number.MIN_NUMBER;
	this.error = "";
	this.id = datatable_id;
	this.dataset = dataset;
	this.biotype = navicell.biotype_factory.getBiotype(biotype_name);
	this.discrete_values = {};

	this.setName(name);

	this.gene_index = {};
	this.sample_index = {};
	this.data = [];

	navicell.DTStatusMustUpdate = true;

	/*
	var tab_body = $("#dt_datatable_tabs");

	tab_body.append("<div id='dt_datatable_id" + this.id + "'><div class='switch-view-div'>" + make_button("", "switch_view_" + this.id, "switch_view(" + this.id + ")") + "</div><table id='dt_datatable_table_id" + this.id + "' class='tablesorter datatable_table'></table></div>");
	this.data_div = $("#dt_datatable_id" + this.id);
	this.data_table = $("#dt_datatable_table_id" + this.id);
	this.switch_button = $("#switch_view_" + this.id);
	//this.switch_button.addClass('switch-button');
	this.switch_button.css('font-size', '10px');
	this.switch_button.css('background', 'white');
	this.switch_button.css('color', 'darkblue');
	*/

	reader.readAsBinaryString(file);

	var datatable = this;

	reader.onload = function() { 
		var dataset = datatable.dataset;

		var text = reader.result;

		var lines = text.split("\n");
		var gene_length = lines.length;

		var line = lines[0].split("\t");
		var sample_cnt = line.length-1;
		var samples_to_add = [];
		var genes_to_add = [];
		for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
			var sample_name = line[sample_nn+1];
			if (sample_name.length > 1) {
				//dataset.addSample(sample_name);
				samples_to_add.push(sample_name);
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
			//dataset.addGene(gene_name, navicell.mapdata.hugo_map[gene_name]);
			genes_to_add.push(gene_name);

			datatable.gene_index[gene_name] = gene_nn;
			datatable.data[gene_nn] = [];
			for (var sample_nn = 0; sample_nn < sample_cnt; ++sample_nn) {
				var value = line[sample_nn+1];
				//datatable.data[gene_nn][sample_nn] = value;
				var err = datatable.setData(gene_nn, sample_nn, value);
				if (err) {
					console.log("data error");
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
		datatable.epilogue();
		ready.resolve();
		dataset.syncModifs();
	}

	reader.onerror = function(e) {  // If anything goes wrong
		this.error = e.toString();
		console.log("Error", e);    // Just log it
		ready.resolve();
	}
}

var MAX_MATRIX_SIZE = 8000;

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

	forceDisplay: function() {
		$("#dt_datatable_notice_id" + this.id).remove();
		this.force_display = true;
		var tab_body = $("#dt_datatable_tabs");
		tab_body.append("<div id='dt_datatable_id" + this.id + "'><div class='switch-view-div'>" + make_button("", "switch_view_" + this.id, "switch_view(" + this.id + ")") + "</div><table id='dt_datatable_table_id" + this.id + "' class='tablesorter datatable_table'></table></div>");
		this.data_div = $("#dt_datatable_id" + this.id);
		this.data_table = $("#dt_datatable_table_id" + this.id);
		this.switch_button = $("#switch_view_" + this.id);
		this.switch_button.css('font-size', '10px');
		this.switch_button.css('background', 'white');
		this.switch_button.css('color', 'darkblue');

		this.refresh();
		for (var map_name in maps) {
			var doc = maps[map_name].document;
			update_datatable_status_table(doc, {force: true});
		}
		tab_body.tabs("option", "active", this.tabnum);
		//this.makeGeneView();
	},

	setTabNum: function(tabnum) {
		this.tabnum = tabnum;
	},

	epilogue: function() {
		var tab_body = $("#dt_datatable_tabs");
		if (true) {
		} else if (!this.isLarge()) {
			tab_body.append("<div id='dt_datatable_id" + this.id + "'><div class='switch-view-div'>" + make_button("", "switch_view_" + this.id, "switch_view(" + this.id + ")") + "</div><table id='dt_datatable_table_id" + this.id + "' class='tablesorter datatable_table'></table></div>");
			this.data_div = $("#dt_datatable_id" + this.id);
			this.data_table = $("#dt_datatable_table_id" + this.id);
			this.switch_button = $("#switch_view_" + this.id);
			//this.switch_button.addClass('switch-button');
			this.switch_button.css('font-size', '10px');
			this.switch_button.css('background', 'white');
			this.switch_button.css('color', 'darkblue');
		} else {
			tab_body.append("<div id='dt_datatable_notice_id" + this.id + "'><div class='datatable-too-large'>Notice: datatable " + this.name + " is too large (" + mapSize(this.gene_index) + "x" + mapSize(this.sample_index) + " matrix) for whole data displaying<br/><br/>" + make_button("Force displaying", "force_datatable_display_" + this.id, "force_datatable_display(" + this.id + ")") + "</div></div>");
			this.data_div = $("#dt_datatable_notice_id" + this.id);
		}

		if (this.biotype.isContinuous()) {
			// TBD: wrong: not suitable for tab sharing !
			// must have a different div per doc
			this.displayStepConfig = new DisplayStepConfig(this);
			this.displayDiscreteConfig = null;
		} else {
			this.displayStepConfig = null;
			this.displayDiscreteConfig = new DisplayDiscreteConfig(this);
			
		}
		this.makeGeneView();
	},

	getDiscreteValues: function() {
		return this.discrete_values;
	},

	isLarge: function() {
		return true;

		return !this.force_display && mapSize(this.sample_index) * mapSize(this.gene_index) > MAX_MATRIX_SIZE;
	},

	makeGeneView: function() {
		if (this.isLarge()) {
			return;
		}
		this.current_view = "gene";
		this.data_table.children().remove();
		this.data_table.append(this.makeDataTable_genes());
		this.data_table.tablesorter();
	},

	makeSampleView: function() {
		if (this.isLarge()) {
			return;
		}
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

	getDisplayConfig: function() {
		if (this.displayStepConfig) {
			return this.displayStepConfig;
		}
		return this.displayDiscreteConfig;
	},

	getBG: function(value) {
		var displayConfig = this.getDisplayConfig();
		if (displayConfig) {
			return displayConfig.getColor(value);
		}
		return '';
	},

	getFG: function(value) {
		var color = this.getBG(value);
		if (color) {
			return getFG_from_BG(color);
		}
		return '';
	},

	getStyle: function(value) {
		var color = this.getBG(value);
		if (color) {
			var fg = getFG_from_BG(color);
			return " style='background: #" + color + "; color: #" + fg + "; text-align: center;'";
		}
		return '';
	},

	makeDataTable_genes: function() {
		console.log("makedatatable !");
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

	getBarplotHeight: function(value, max) {
		if (this.biotype.isContinuous()) {
			var ivalue = parseFloat(value);
			if (!isNaN(ivalue)) {
				var ivalue = parseFloat(value);
				if (!isNaN(ivalue)) {
					return max * (ivalue-this.minval) / (this.maxval - this.minval);
				}
			}
		} else {
			var displayConfig = this.getDisplayConfig();
			var size = displayConfig.getSize(value);
			//return max*(size/DISCRETE_MAX_SIZE);
			return max*(size/(displayConfig.getValueCount()*DISCRETE_SIZE_COEF));
		}
		return 0.;
	},

	getValue: function(sample_name, gene_name) {
		var gene_idx = this.gene_index[gene_name];
		var sample_idx = this.sample_index[sample_name];
		if (gene_idx != undefined && sample_idx != undefined) {
			return this.data[gene_idx][sample_idx];
		}
		return '';
	},

	makeDataTable_samples: function() {
		console.log("makedatatable !");
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
			value = '';
		} else {
			var ivalue = parseFloat(value);
			if (!isNaN(ivalue)) {
				//console.log("ivalue: " + ivalue + " " + value);
				if (ivalue < this.minval) {
					this.minval = ivalue;
				}
				if (ivalue > this.maxval) {
					this.maxval = ivalue;
				}
			} else if (this.biotype.isContinuous()) {
				return "expected numeric value, got '" + value + "'";
			}
		}
		if (!this.biotype.isContinuous()) {
			this.discrete_values[value] = 1;
		}
		this.data[gene_nn][sample_nn] = value;
		return '';
	},

	getSampleCount: function(gene_name) {
		var gene_idx = this.gene_index[gene_name];
		if (gene_idx == undefined) {
			return -1;
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

	this.glyph_config = new GlyphConfig();
	this.editing_glyph_config = new GlyphConfig();

	this.display_glyphs = 0;
	this.display_labels = 0;

	this.display_DLOs_on_all_genes = 1;
}

DrawingConfig.prototype = {

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

	displayGlyphs: function() {
		return this.display_glyphs;
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
		return this.displayCharts() || this.displayGlyphs() || this.displayLabels();
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

	setDisplayGlyphs: function(val) {
		this.display_glyphs = val;
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
			console.log("this.display_charts: " + this.display_charts);
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

	setMethod: function(datatable, method) {
		this.methods[datatable.getId()] = method;
		update_status_tables();
		clickmap_refresh(true);
	},

	getMethod: function(datatable) {
		var method = this.methods[datatable.getId()];
		if (method == undefined) {
			if (datatable.biotype.isContinuous()) {
				method = Group.CONTINUOUS_AVERAGE;
			} else {
				var values = datatable.getDiscreteValues();
				var first_value;
				for (first_value in values) {
					break;
				}
				method = first_value + '+';
			}
			this.methods[datatable.getId()] = method;
		}
		return method;
	},

	getValue: function(datatable, gene_name) {
		var method = this.getMethod(datatable);
		if (datatable.biotype.isContinuous()) {
			if (method == Group.CONTINUOUS_AVERAGE) {
				var total_value = 0;
				var cnt = 0;
				for (var sample_name in this.samples) {
					var value = datatable.getValue(sample_name, gene_name);
					value *= 1.;
					if (value) {
						total_value += value;
						cnt++;
					}
				}
				if (cnt) {
					return total_value/cnt;
				}
				return undefined;
			}
			if (method == Group.CONTINUOUS_MINVAL || method == Group.CONTINUOUS_MAXVAL) {
				var min = Number.MAX_NUMBER;
				var max = Number.MIN_NUMBER;

				for (var sample_name in this.samples) {
					var value = datatable.getValue(sample_name, gene_name);
					value *= 1.;
					if (value < min) {
						min = value;
					}
					if (value > max) {
						max = value;
					}
				}
				return method == Group.CONTINUOUS_MINVAL ? min : max;
			}
			return undefined;
		}
		var method_len = method.length;
		var method_value = method.substring(0, method_len-1);
		var all = method.substring(method_len-1, method_len) == '@';
		var value_cnt = 0;
		var sample_cnt = 0;
		for (var sample_name in this.samples) {
			var value = datatable.getValue(sample_name, gene_name);
			if (value == method_value) {
				value_cnt++;
			}
			sample_cnt++;
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

function navicell_init() {
	var _navicell = {}; // namespace

	_navicell.module_names = [];
	//_navicell.mapdata = new Mapdata();
	_navicell.dataset = new Dataset("navicell");
	_navicell.group_factory = new GroupFactory();
	_navicell.biotype_factory = new BiotypeFactory();
	_navicell.annot_factory = new AnnotationFactory();
	_navicell.drawing_config = new DrawingConfig();

	_navicell.CONTINUOUS = new BiotypeType("continuous");
	_navicell.DISCRETE = new BiotypeType("discrete");
	_navicell.SET = new BiotypeType("set");

	_navicell.biotype_factory.addBiotype(new Biotype("mRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("microRNA expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Protein expression data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Copy number data", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: methylation profiles", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Epigenic data: histone modifications", _navicell.CONTINUOUS));
	_navicell.biotype_factory.addBiotype(new Biotype("Polymorphism data: SNPs", _navicell.DISCRETE));
	_navicell.biotype_factory.addBiotype(new Biotype("Mutation data: gene re-sequencing", _navicell.DISCRETE));
	_navicell.biotype_factory.addBiotype(new Biotype("Interaction data", _navicell.SET));
	_navicell.biotype_factory.addBiotype(new Biotype("Set data", _navicell.SET));

	_navicell.getDatatableById = function(id) {
		/*
		console.log("this.dataset: " + this.dataset.getClass() + " " + id);
		console.log("-> datatable: " + this.dataset.getDatatableById(id));
		console.log("-> datatable class: " + this.dataset.getDatatableById(id).getClass());
		*/
		return this.dataset.getDatatableById(id);
	}

	_navicell.shapes = ["Square", "Rectangle", "Triangle", "Diamond", "Hexagon", "Circle"];

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
