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

function JXTree(datatree, div) {
	this.label_map = {};
	this.node_cnt = 0;
	this.state_changed = null;
	this.open_changed = null;
	if (datatree) {
		this.root = new JXTreeNode(this, datatree.label, datatree.left_label, datatree.right_label, null, datatree.udata);
		this.buildNodes(this.root, datatree.children);
		console.log("nodes built");
		this.root.buildHTML(div);
		console.log("html built");
	} else {
		this.root = null;
	}
}

JXTree.UNCHECKED = 0;
JXTree.CHECKED = 1;
JXTree.UNDETERMINED = 2;

JXTree.prototype = {
	buildNodes: function(root_node, children_data) {
		if (!children_data) {
			return;
		}
		for (var nn = 0; nn < children_data.length; ++nn) {
			var child_data = children_data[nn];
			var child_node = new JXTreeNode(this, child_data.label, child_data.left_label, child_data.right_label, root_node, child_data.udata);
			this.buildNodes(child_node, child_data.children);
		}
	},

	newNode: function() {
		this.node_cnt++;
	},

	getNodeCount: function() {
		return this.node_cnt;
	},

	onStateChange: function(action) {
		this.state_changed = action;
	},

	onOpenChange: function(action) {
		this.open_changed = action;
	},

	search: function(pattern, action) {
		var regex = new RegExp(pattern, "i");
		var nodes = [];
		for (var label in this.label_map) {
			if (label.match(regex)) {
				nodes.push(this.label_map[label]);
			}
		}
		/*
		this.root.hideSubtree();
		for (var nn = 0; nn < nodes.length; ++nn) {
			var node = nodes[nn];
			node.setDisplay(true);
			node.showSupertree(true);
			node.setChecked(true);
		}
		*/
		console.log("search: " + pattern + " " + nodes.length);
		if (action == 'select') {
			this.root.uncheckSubtree();
			for (var nn = 0; nn < nodes.length; ++nn) {
				var node = nodes[nn];
				node.check();
			}
		}
		if (action == 'subtree') {
		}
		return nodes.length;
	}
};

function JXTreeNode(jxtree, label, left_label, right_label, parent, user_data) {
	this.jxtree = jxtree;
	this.label = label;
	this.jxtree.label_map[label] = this;
	this.left_label = left_label;
	this.right_label = right_label;
	this.parent = parent;
	if (parent) {
		parent.addChild(this);
	}
	this.user_data = user_data;
	this.children = [];
	this.state = JXTree.UNCHECKED;
	this.open = false;

	this.elem_node = null;
	this.elem_checkbox = null;
	this.elem_label = null;

	this.jxtree.newNode();
}

JXTreeNode.prototype = {

	clone: function(jxtree) {
		return new JXTreeNode(jxtree, this.label, this.left_label, this_right_label, (this.parent ? this.parent.clone(jxtree) : null), this.user_data);
	},

	addChild: function(node) {
		this.children.push(node);
	},

	setOpen: function(open) {
		this.open = open;
	},

	setState: function(state) {
		if (state != this.state) {
			if (this.jxtree.state_changed) {
				var nstate = this.jxtree.state_changed(this, state);
				if (nstate) {
					state = nstate;
				}
			}
			this.state = state;
			this.setCheckboxClass();
		}
	},

	getState: function() {
		return this.state;
	},

	getUserData: function() {
		return this.user_data;
	},

	isOpened: function() {
		return this.open;
	},

	isChecked: function() {
		return this.state == JXTree.CHECKED;
	},

	isUnchecked: function() {
		return this.state == JXTree.UNCHECKED;
	},

	isUndetermined: function() {
		return this.state == JXTree.UNDETERMINED;
	},

	setDisplay: function(display) {
		$(this.elem_node).css("display", (display ? "block" : "none"));
	},

	hideSubtree: function() {
		this.setDisplay(false);
		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].hideSubtree();
		}
	},

	showSubtree: function() {
		this.setDisplay(true);
		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].showSubtree();
		}
	},

	showSupertree: function() {
		if (this.parent) {
			this.parent.setDisplay(true);
			this.parent.showSupertree();
		}
	},

	isLeaf: function() {
		return this.children.length == 0;
	},

	setCheckboxClass: function() {
		this.ins_cb_obj.removeClass("jxtree-checkbox-checked");
		this.ins_cb_obj.removeClass("jxtree-checkbox-unchecked");
		this.ins_cb_obj.removeClass("jxtree-checkbox-undetermined");

		if (this.isChecked()) {
			this.ins_cb_obj.addClass("jxtree-checkbox-checked");
		} else if (this.isUnchecked()) {
			this.ins_cb_obj.addClass("jxtree-checkbox-unchecked");
		} else if (this.isUndetermined()) {
			this.ins_cb_obj.addClass("jxtree-checkbox-undetermined");
		}
	},

	setSubtreeState: function(state) {
		this.setState(state);

		for (var nn = 0; nn < this.children.length; ++nn) {
			this.children[nn].setSubtreeState(state);
		}
	},

	updateSupertreeState: function() {
		if (this.children.length > 0) {
			var state = this.children[0].getState();
			for (var nn = 1; nn < this.children.length; ++nn) {
				if (this.children[nn].getState() != state) {
					this.setState(JXTree.UNDETERMINED);
					state = null;
					break;
				}
			}
			if (state != null) {
				this.setState(state);
			}
		}
		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	buildHTML: function(container) {
		var ul = document.createElement('ul');
		$(ul).css("padding-left", "0px");
		$(ul).addClass("jxtree");
		this.buildHTMLNodes(ul);
		container.appendChild(ul);
	},

	check: function() {
		this.setState(JXTree.CHECKED);

		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	uncheck: function() {
		this.setState(JXTree.UNCHECKED);

		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	checkSubtree: function() {
		this.setSubtreeState(JXTree.CHECKED);

		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	uncheckSubtree: function() {
		this.setSubtreeState(JXTree.UNCHECKED);

		if (this.parent) {
			this.parent.updateSupertreeState();
		}
	},

	buildHTMLNodes: function(container) {
		var li = document.createElement('li');
		var ins_oc = document.createElement('ins');
		li.appendChild(ins_oc);

		var ins_oc_obj = $(ins_oc);
		ins_oc_obj.addClass("jxtree-default");
		if (!this.isLeaf()) {
			ins_oc_obj.addClass("jxtree-open");
		} else {
			ins_oc_obj.addClass("jxtree-leaf");
		}

		var ins_cb = document.createElement('ins');
		var ins_cb_obj = $(ins_cb);
		ins_cb_obj.addClass("jxtree-default");
		ins_cb_obj.addClass("jxtree-checkbox-unchecked");

		li.appendChild(ins_cb);

		if (this.left_label) {
			var ins = document.createElement('ins');
			$(ins).html(this.left_label + "&nbsp;");
			li.appendChild(ins);
		}

		var ins_label = document.createElement('ins');
		$(ins_label).html(this.label);
		li.appendChild(ins_label);

		if (this.right_label) {
			var ins = document.createElement('ins');
			$(ins).html("&nbsp;" + this.right_label);
			li.appendChild(ins);
		}

		var node = this;
		ins_oc_obj.click(function() {
			console.log("[open/close " + node.label + "] " + node.children.length);
			node.open = !node.open;
			for (var nn = 0; nn < node.children.length; ++nn) {
				node.children[nn].setDisplay(node.open);
			}
			if (!node.isLeaf()) {
				if (node.open) {
					ins_oc_obj.removeClass("jxtree-closed");
					ins_oc_obj.addClass("jxtree-open");
				} else {
					ins_oc_obj.removeClass("jxtree-open");
					ins_oc_obj.addClass("jxtree-closed");
				}
			}
		});

		ins_cb_obj.click(function() {
			console.log("[check/uncheck " + node.label + "] " + node.children.length);
			if (node.isChecked()) {
				node.uncheckSubtree();
			} else {
				node.checkSubtree();
			}
		});

		container.appendChild(li);

		if (this.children.length) {
			var ul = document.createElement('ul');
			for (var nn = 0; nn < this.children.length; ++nn) {
				var li_child = this.children[nn].buildHTMLNodes(ul);
				ul.appendChild(li_child);
			}
			li.appendChild(ul);
		}

		this.open = true;
		this.elem_node = li;
		this.ins_cb_obj = ins_cb_obj;
		$(this.elem_node).css("display", "block");
		return this.elem_node;
	}
};
