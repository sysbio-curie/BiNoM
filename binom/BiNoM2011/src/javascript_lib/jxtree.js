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

var jxtree_mute = true;

function JXTree(_document, datatree, div) {
	if (!_document) {
		_document = document;
	}
	this.document = _document;
	this.label_map = {};
	this.node_id = 0;
	this.check_state_changed = null;
	this.open_state_changed = null;
	this.div = div;
	this.node_map = {};
	if (datatree) {
		this.root = new JXTreeNode(this, datatree.label, datatree.left_label, datatree.right_label, datatree.data);
		this.buildNodes(this.root, datatree.children);
		if (div) {
			this.root.buildHTML(div);
			this.root.show(true);
		}
	} else {
		this.root = null;
	}
}

JXTree.UNCHECKED = 4;
JXTree.CHECKED = 5;
JXTree.UNDETERMINED = 6;
JXTree.CLOSED = 10;
JXTree.OPEN = 11;

JXTree.prototype = {
	buildNodes: function(root_node, children_data) {
		if (!children_data) {
			return;
		}
		for (var nn = 0; nn < children_data.length; ++nn) {
			var child_data = children_data[nn];
			var child_node = new JXTreeNode(this, child_data.label, child_data.left_label, child_data.right_label, child_data.udata);
			root_node.addChild(child_node);
			this.buildNodes(child_node, child_data.children);
		}
	},

	newNode: function(node) {
		var node_id = ++this.node_id;
		node.setId(node_id);
		this.node_map[node_id] = node;
		return node_id;
	},

	complete: function(datatree, div) {
		if (!this.root && datatree) {
			this.root = new JXTreeNode(this, datatree.label, datatree.left_label, datatree.right_label, datatree.udata);
		}
		if (!this.div) {
			this.div = div;
		}
		if (this.root && !this.root.nd_elem) {
			if (this.div) {
				this.root.buildHTML(this.div);
				this.root.show(true);
			}
		}
	},

	clone: function(node_map) {
		var jxtree = new JXTree(this.document);
		jxtree.root = this.root.cloneSubtree(jxtree, node_map);
		return jxtree;
	},

	getRootNode: function() {
		return this.root;
	},

	getNodeCount: function() {
		return this.node_id;
	},

	checkStateChanged: function(action) {
		this.check_state_changed = action;
	},

	openStateChanged: function(action) {
		this.open_state_changed = action;
	},

	find: function(pattern, action, div) {
		var regex = new RegExp(pattern, "i");
		var nodes = [];
		for (var label in this.label_map) {
			var node = this.label_map[label];
			if (node.isLeaf() && label.match(regex)) {
				nodes.push(node);
			}
		}
		console.log("find [" + pattern + "] -> " + nodes.length);
		if (nodes.length) {
			if (action == 'select') {
				this.root.checkSubtree(true);
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
				if (div) {
					jxsubtree.complete(null, div);
					jxsubtree.root.openSubtree(JXTree.OPEN);
				}
				return jxsubtree;
			}
		}
		return nodes.length;
	}
};

function JXTreeNode(jxtree, label, left_label, right_label, user_data) {
	this.jxtree = jxtree;
	this.label = label;
	this.jxtree.label_map[label] = this;
	this.left_label = left_label;
	this.right_label = right_label;
	this.user_data = user_data;
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
		return new JXTreeNode(jxtree, this.label, this.left_label, this.right_label, null, this.user_data);
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
		if (children.length) {
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
					if (this.open_state) {
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
			node.toggleCheck();
		});
	},

	buildHTMLNodes: function(container) {
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

		if (this.left_label) {
			var ins = this.jxtree.document.createElement('ins');
			$(ins).html(this.left_label + "&nbsp;");
			li.appendChild(ins);
		}

		var ins_label = this.jxtree.document.createElement('ins');
		$(ins_label).html(this.label);
		li.appendChild(ins_label);

		if (this.right_label) {
			var ins = this.jxtree.document.createElement('ins');
			$(ins).html("&nbsp;" + this.right_label);
			li.appendChild(ins);
		}

		var node = this;
		container.appendChild(li);

		if (this.children.length) {
			var ul = this.jxtree.document.createElement('ul');
			for (var nn = 0; nn < this.children.length; ++nn) {
				var li_child = this.children[nn].buildHTMLNodes(ul);
				ul.appendChild(li_child);
			}
			li.appendChild(ul);
		}

		this.oc_elem = ins_oc_obj;
		this.cb_elem = ins_cb_obj;
		this.nd_elem = li;

		this.eventListeners();

		this.open_state = JXTree.CLOSED;
		this.show(false);

		return this.nd_elem;
	}
};
