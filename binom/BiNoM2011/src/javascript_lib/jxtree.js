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

function JXTreeMatcher(pattern, hints) {
	pattern = pattern.trim();

	if (!hints) {
		hints = {};
	}
	this.hints = hints;
	this.search_user_find = !hints.no_search_user_find;
	this.search_label = !hints.no_search_label;
	this.case_sensitive = !hints.case_sensitive ? "i" : "";

	var cls_patterns = pattern.split("/");
	if (cls_patterns.length == 2) {
		pattern = cls_patterns[0].trim();
		var cls_only_pattern = cls_patterns[1].trim().toUpperCase();
		if (cls_only_pattern[0] == '!') {
			this.cls_only_is_not = true;
			cls_only_pattern = cls_only_pattern.substr(1);
			this.cls_only = cls_only_pattern.split("&");
		} else {
			this.cls_only = cls_only_pattern.split("|");
		}
	}

	var patterns = pattern.split(",");
	if (patterns.length > 1) {
		this.and_search = false;
		this.or_search = true;
	} else {
		patterns = pattern.split(/[ \t]+/);
		this.and_search = true;
		this.or_search = false;
	}

	this.regex_arr = [];
	for (var nn = 0; nn < patterns.length; ++nn) {
		this.regex_arr.push(new RegExp(patterns[nn].trim(), this.case_sensitive));
	}
	//console.log("AND: " + this.and_search + " OR: " + this.or_search);
}

JXTreeMatcher.prototype = {
	
	match: function(str) {
		var match_cnt = 0;
		for (var nn = 0; nn < this.regex_arr.length; ++nn) {
			if (str.match(this.regex_arr[nn])) {
				match_cnt++;
				if (this.or_search) {
					return true;
				}
			} else {
				if (this.and_search) {
					return false;
				}
			}
		}
		return match_cnt > 0;
	}
};

var jxtree_mute = true;

function JXTree(_document, datatree, div, mapfun) {
	if (!_document) {
		_document = document;
	}
	this.document = _document;
	this.mapfun = mapfun;
	this.label_map = {};
	this.node_id = 0;
	this.check_state_changed = null;
	this.open_state_changed = null;
	this.on_click_before = null;
	this.on_click_after = null;
	this.user_find = null;
	this.div = div;
	this.node_map = {};
	this.node_user_map = {};
	this.roots = [];
	if (datatree) {
		this.buildFromData(datatree, div);
	}
}

JXTree.UNCHECKED = 4;
JXTree.CHECKED = 5;
JXTree.UNDETERMINED = 6;
JXTree.CLOSED = 10;
JXTree.OPEN = 11;

JXTree.prototype = {
	buildFromData: function(datatree, div) {
		if (datatree.length) {
			/*
			for (var nn = 0; nn < datatree.length; ++nn) {
				var root = this.buildRoot(datatree[nn], div);
				this.addRoot(root);
			}
			*/
			var jxtree = this;
			$.each(datatree, function() {
				var root = jxtree.buildRoot(this, div);
				jxtree.addRoot(root);
			});
		} else {
			var root = this.buildRoot(datatree, div);
			this.addRoot(root);
		}
	},

	buildRoot: function(datatree, div) {
		var root = null;
		var mapfun = this.mapfun;
		if (!mapfun) {
			root = new JXTreeNode(this, datatree.label, datatree.left_label, datatree.right_label, datatree.data, datatree.id);
			this.buildNodes(root, datatree.children);
		} else {
			root = new JXTreeNode(this, mapfun(datatree, 'label'), mapfun(datatree, 'left_label'), mapfun(datatree, 'right_label'), mapfun(datatree, 'data'), mapfun(datatree, 'id'));
			this.buildNodes(root, mapfun(datatree, 'children'));
		}
		if (div) {
			root.buildHTML(div);
			root.show(true);
		}
		return root;
	},

	buildNodes: function(root_node, children_data) {
		if (!children_data) {
			return;
		}
		for (var nn = 0; nn < children_data.length; ++nn) {
			var child_data = children_data[nn];
			var child_node;
			var mapfun = this.mapfun;
			if (!mapfun) {
				child_node = new JXTreeNode(this, child_data.label, child_data.left_label, child_data.right_label, child_data.data, child_data.id);
			} else {
				child_node = new JXTreeNode(this, mapfun(child_data, 'label'), mapfun(child_data, 'left_label'), mapfun(child_data, 'right_label'), mapfun(child_data, 'data'), mapfun(child_data, 'id'));
			}
			root_node.addChild(child_node);
			this.buildNodes(child_node, mapfun(child_data, 'children'));
		}
	},

	newNode: function(node) {
		var node_id = ++this.node_id;
		node.setId(node_id);
		this.node_map[node_id] = node;
		if (node.user_id) {
			this.node_user_map[node.user_id] = node;
		}
		return node_id;
	},

	complete: function(datatree, div) {
		if (!this.div) {
			this.div = div;
		}
		if (!this.roots) {
			if (datatree) {
				this.buildFromData(datatree, this.div);
			}
		}
		// WARNING: one should keep this, more or less
		if (this.roots && this.div) {
			for (var nn = 0; nn < this.roots.length; ++nn) {
				var root = this.roots[nn];
				if (!root.nd_elem) {
					root.buildHTML(this.div);
					root.show(true);
				}
			}
		}
	},

	addRoot: function(root) {
		this.roots.push(root);
	},

	clone: function(node_map) {
		var cloned_jxtree = new JXTree(this.document);
		/*
		for (var nn = 0; nn < this.roots.length; ++nn) {
			var root = this.roots[nn].cloneSubtree(jxtree, node_map);
			jxtree.addRoot(root);
		}
		*/
		var jxtree = this;
		$.each(this.roots, function() {
			//var root = jxtree.roots[nn].cloneSubtree(cloned_jxtree, node_map);
			var root = this.cloneSubtree(cloned_jxtree, node_map);
			if (root) {
				cloned_jxtree.addRoot(root);
			}
		});
		return cloned_jxtree;
	},

	getRootNodes: function() {
		return this.roots;
	},

	getNodeCount: function() {
		return this.node_id;
	},

	onClickBefore: function(action) {
		this.on_click_before = action;
	},

	onClickAfter: function(action) {
		this.on_click_after = action;
	},

	userFind: function(action) {
		this.user_find = action;
	},

	checkStateChanged: function(action) {
		this.check_state_changed = action;
	},

	openStateChanged: function(action) {
		this.open_state_changed = action;
	},

	getNodeByUserId: function(user_id) {
		return this.node_user_map[user_id];
	},

	// should replace action and div by a extensible map named 'hints'
	find: function(pattern, action, hints) {
		pattern = pattern.trim();
		//var regex = new RegExp(pattern, "i");
		var matcher = new JXTreeMatcher(pattern, hints);
		var nodes = [];
		if (pattern) {
			var user_find = this.user_find;
			for (var id in this.node_map) {
				var node = this.node_map[id];
				if (node.isLeaf()) {
					//if (node.matchRegex(regex)) {
					if (node.match(matcher)) {
						nodes.push(node);
					}
				}
			}
		}
		console.log("find [" + pattern + "] -> " + nodes.length + " (" + this.getNodeCount() + ")");
		if (action == 'select') {
			for (var nn = 0; nn < this.roots.length; ++nn) {
				this.roots[nn].checkSubtree(JXTree.UNCHECKED);
			}
			for (var nn = 0; nn < nodes.length; ++nn) {
				var node = nodes[nn];
				node.checkSubtree(JXTree.CHECKED);
				node.openSupertree(JXTree.OPEN);
			}
		}
		if (action == 'subtree') {
			var node_map = {};
			for (var nn = 0; nn < nodes.length; ++nn) {
				node_map[nodes[nn].getId()] = true;
			}
			var jxsubtree = this.clone(node_map);
			if (hints && hints.div) {
				$(hints.div).html("");
				jxsubtree.complete(null, hints.div);
				for (var nn = 0; nn < jxsubtree.roots.length; ++nn) {
					jxsubtree.roots[nn].openSubtree(JXTree.OPEN);
				}
			}
			jxsubtree.userFind(this.user_find);
			jxsubtree.checkStateChanged(this.check_state_changed);
			jxsubtree.openStateChanged(this.open_state_changed);
			jxsubtree.onClickBefore(this.on_click_before);
			jxsubtree.onClickAfter(this.on_click_after);
			jxsubtree.found = nodes.length;
			return jxsubtree;
		}
		return nodes.length;
	}
};

function JXTreeNode(jxtree, label, left_label, right_label, user_data, user_id) {
	this.jxtree = jxtree;
	this.label = label;
	this.jxtree.label_map[label] = this;
	this.left_label = left_label;
	this.right_label = right_label;
	this.user_data = user_data;
	this.user_id = user_id;
	this.children = [];
	this.check_state = JXTree.UNCHECKED;
	this.open_state = JXTree.CLOSED;

	this.nd_elem = null; // li node
	this.cb_elem = null; // checkbox
	this.oc_elem = null; // open/close icon

	this.jxtree.newNode(this);
}

JXTreeNode.prototype = {

	getId: function() {
		return this.id;
	},

	setId: function(id) {
		this.id = id;
	},

	clone: function(jxtree) {
		return new JXTreeNode(jxtree, this.label, this.left_label, this.right_label, this.user_data, this.user_id);
	},

	match: function(matcher) {
		if (matcher.cls_only) {
			var node_cls = jxtree_get_node_class(this).toUpperCase(); // BAD! should be an handler
			console.log("is_not: " + matcher.cls_only_is_not);
			if (matcher.cls_only_is_not) {
				for (var nn = 0; nn < matcher.cls_only.length; ++nn) {
					var cls_only = matcher.cls_only[nn].trim();
					if (node_cls == cls_only) {
						return false;
					}
				}
			} else {
				var found = false;
				for (var nn = 0; nn < matcher.cls_only.length; ++nn) {
					var cls_only = matcher.cls_only[nn].trim();
					if (node_cls == cls_only) {
						found = true;
						break;
					}
				}
				if (!found) {
					return false;
				}
			}
		}

		if (matcher.search_label) {
			if (matcher.match(this.label)) {
				return true;
			} 
		}

		if (matcher.search_user_find) {
			if (this.jxtree.user_find && this.jxtree.user_find(matcher, this)) {
				return true;
			}
		}
		return false;

	},

	matchRegex: function(regex) {
		var label = this.label;
		if (label.match(regex)) {
			return true;
		} 
		if (this.jxtree.user_find && this.jxtree.user_find(regex, this)) {
			return true;
		}
		return false;
	},

	cloneSubtree: function(jxtree, node_map) {
		if (this.isLeaf()) {
			if (node_map == null || node_map == undefined || node_map[this.getId()]) {
				return this.clone(jxtree);
			}
			return null;
		}
		var children = [];
		for (var nn = 0; nn < this.children.length; ++nn) {
			var child = this.children[nn].cloneSubtree(jxtree, node_map);
			if (child) {
				children.push(child);
			}
		}
		//if (children.length) {
		if (children.length || node_map == null || node_map == undefined || node_map[this.getId()]) {
			var clone = this.clone(jxtree);
			for (var nn = 0; nn < children.length; ++nn) {
				clone.addChild(children[nn]);
			}
			return clone;
		}
		return null;
	},

	addChild: function(node) {
		this.children.push(node);
		node.parent = this;
	},

	setOpenState: function(open) {
		var open_state;
		if (open == true) {
			open_state = JXTree.OPEN;
		} else if (open == false) {
			open_state = JXTree.CLOSED;
		} else {
			open_state = open;
		}

		if (this.open_state != open_state) {
			this.open_state = open_state;
			if (this.oc_elem) {
				for (var nn = 0; nn < this.children.length; ++nn) {
					this.children[nn].show(this.open_state == JXTree.OPEN);
				}
				if (!this.isLeaf()) {
					if (this.open_state == JXTree.OPEN) {
						this.oc_elem.removeClass("jxtree-closed");
						this.oc_elem.addClass("jxtree-open");
					} else {
						this.oc_elem.removeClass("jxtree-open");
						this.oc_elem.addClass("jxtree-closed");
					}
				}
			}
		}
	},

	setCheckState: function(checked) {
		var check_state;
		if (checked == true) {
			check_state = JXTree.CHECKED;
		} else if (checked == false) {
			check_state = JXTree.UNCHECKED;
		} else {
			check_state = checked;
		}

		if (check_state != this.check_state) {
			if (this.jxtree.check_state_changed) {
				var check_state_new = this.jxtree.check_state_changed(this, check_state);
				if (check_state_new) {
					check_state = check_state_new;
				}
			}
			if (check_state != this.check_state) {
				this.check_state = check_state;
				this.setCheckboxClass();
			}
		}
	},

	getCheckState: function() {
		return this.check_state;
	},

	getChildren: function() {
		return this.children;
	},

	getParent: function() {
		return this.parent;
	},

	getUserData: function() {
		return this.user_data;
	},

	openSubtree: function(open_state) {
		this.setOpenState(open_state);
		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].openSubtree(open_state);
		}
	},

	openSupertree: function(open_state) {
		if (this.parent) {
			this.parent.setOpenState(open_state);
			this.parent.openSupertree(open_state);
		}
	},

	isOpen: function() {
		return this.open_state == JXTree.OPEN;
	},

	isChecked: function() {
		return this.check_state == JXTree.CHECKED;
	},

	isUnchecked: function() {
		return this.check_state == JXTree.UNCHECKED;
	},

	isUndetermined: function() {
		return this.check_state == JXTree.UNDETERMINED;
	},

	show: function(show) {
		$(this.nd_elem).css("display", (show ? "block" : "none"));
	},

	showSubtree: function(show) {
		this.show(show);
		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].showSubtree(show);
		}
	},

	showSupertree: function(show) {
		if (this.parent) {
			this.parent.show(show);
			this.parent.showSupertree(show);
		}
	},

	isLeaf: function() {
		return this.children.length == 0;
	},

	setCheckboxClass: function() {
		if (this.cb_elem) {
			this.cb_elem.removeClass("jxtree-checkbox-checked");
			this.cb_elem.removeClass("jxtree-checkbox-unchecked");
			this.cb_elem.removeClass("jxtree-checkbox-undetermined");

			if (this.isChecked()) {
				this.cb_elem.addClass("jxtree-checkbox-checked");
			} else if (this.isUnchecked()) {
				this.cb_elem.addClass("jxtree-checkbox-unchecked");
			} else if (this.isUndetermined()) {
				this.cb_elem.addClass("jxtree-checkbox-undetermined");
			}
		}
	},

	setSubtreeState: function(check_state) {
		this.setCheckState(check_state);

		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].setSubtreeState(check_state);
		}
	},

	updateSupertreeState: function() {
		if (this.children.length > 0) {
			var check_state = this.children[0].getCheckState();
			for (var nn = 1; nn < this.children.length; ++nn) {
				if (this.children[nn].getCheckState() != check_state) {
					check_state = JXTree.UNDETERMINED;
					break;
				}
			}
			this.setCheckState(check_state);
		}
		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	buildHTML: function(container) {
		var ul = this.jxtree.document.createElement('ul');
		$(ul).css("padding-left", "0px");
		$(ul).addClass("jxtree");
		container.appendChild(ul);
		this.buildHTMLNodes(ul);
		container.appendChild(ul);
	},

	checkSubtree: function(check_state) {
		this.setSubtreeState(check_state);

		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	toggleOpen: function() {
		//console.log("[open/close " + this.label + "] " + this.children.length);
		var open_state = (this.open_state == JXTree.OPEN ? JXTree.CLOSED : JXTree.OPEN);

		if (this.jxtree.open_state_changed) {
			var open_state_new = this.jxtree.open_state_changed(this, open);
			if (open_state_new) {
				open_state = open_state_new;
			}
		}

		this.setOpenState(open_state);
	},

	toggleCheck: function() {
		//console.log("[check/uncheck " + this.label + "] " + this.children.length);
		this.checkSubtree(!this.isChecked());
	},

	eventListeners: function() {
		var node = this;

		this.oc_elem.click(function() {
			node.toggleOpen();
		});

		this.cb_elem.click(function() {
			if (node.jxtree.on_click_before) {
				node.jxtree.on_click_before(node, node.isChecked());
			}
			node.toggleCheck();
			if (node.jxtree.on_click_after) {
				node.jxtree.on_click_after(node, node.isChecked());
			}
		});
	},

	buildHTMLNodes: function(container) {
		if (!this.label) {
			return null;
		}
		var li = this.jxtree.document.createElement('li');
		var ins_oc = this.jxtree.document.createElement('ins');
		li.appendChild(ins_oc);

		var ins_oc_obj = $(ins_oc);
		ins_oc_obj.addClass("jxtree-default");
		if (!this.isLeaf()) {
			ins_oc_obj.addClass("jxtree-open");
		} else {
			ins_oc_obj.addClass("jxtree-leaf");
		}

		var ins_cb = this.jxtree.document.createElement('ins');
		var ins_cb_obj = $(ins_cb);
		ins_cb_obj.addClass("jxtree-default");
		ins_cb_obj.addClass("jxtree-checkbox-unchecked");

		li.appendChild(ins_cb);

		var label = '';
		var ins = this.jxtree.document.createElement('ins');
		if (this.left_label) {
			label += this.left_label + ' '; //"&nbsp;";
		}
		label += this.label;
		if (this.right_label) {
			label +=  ' ' + this.right_label;
		}
		$(ins).html(label);
		li.appendChild(ins);

		var node = this;
		container.appendChild(li);

		if (this.children.length) {
			var ul = this.jxtree.document.createElement('ul');
			for (var nn = 0; nn < this.children.length; ++nn) {
				var li_child = this.children[nn].buildHTMLNodes(ul);
				if (li_child) {
					ul.appendChild(li_child);
				}
			}
			li.appendChild(ul);
		}

		this.oc_elem = ins_oc_obj;
		this.cb_elem = ins_cb_obj;
		this.nd_elem = li;

		this.eventListeners();

		this.open_state = JXTree.CLOSED;
		this.oc_elem.addClass("jxtree-closed");
		//this.setOpenState(JXTree.CLOSED);
		this.show(false);

		return this.nd_elem;
	}
};
