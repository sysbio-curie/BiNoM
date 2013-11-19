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

//var MAX_SCREEN_WIDTH = 1920;
//var MAX_SCREEN_HEIGHT = 1200;
var MAX_SCREEN_WIDTH = 2500;
var MAX_SCREEN_HEIGHT = 2000;

USGSOverlay.prototype = new google.maps.OverlayView();

function overlay_init(map) {
	overlay = new USGSOverlay(map);
}

function USGSOverlay(map) {
	this.map_ = map;
	this.div_ = null;
	this.setMap(map);
	this.arrpos = [];
}

function is_included(box, clicked_boundbox) {
	return box[0] >= clicked_boundbox[0] && box[0]+box[2] <= clicked_boundbox[0]+clicked_boundbox[2] &&
		box[1] >= clicked_boundbox[1] && box[1]+box[3] <= clicked_boundbox[1]+clicked_boundbox[3];
}

function event_ckmap(e, e2, type) {
	var button = window.event.button; // TBD: window is not correct, depends on tab
	var x = e.pixel.x;
	var y = e.pixel.y;

	var overlayProjection = overlay.getProjection();
	var mapProjection = overlay.map_.getProjection();
	var scale = Math.pow(2, overlay.map_.zoom);
	var div = overlay.div_;

	var found = false;
	var module_name = window.document.navicell_module_name;
	var jxtree = navicell.mapdata.getJXTree(module_name);
	var ckmap = navicell.mapdata.getCKMap(module_name);
	var clicked_node = null;
	var clicked_boundbox = null;
	for (var id in ckmap) {
		var boxes = ckmap[id];
		for (var kk = 0; kk < boxes.length; ++kk) {
			var box = boxes[kk];
			if (!box.gpt) {
				box.gpt = new google.maps.Point(box[0], box[2]);
			}
			var latlng = mapProjection.fromPointToLatLng(box.gpt);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			var bx = pix.x - div.left;
			var by = pix.y - div.top;
			var bw = box[1]*scale;
			var bh = box[3]*scale;
			if (x >= bx && x <= bx+bw && y >= by && y <= by+bh) {
				if (type == 'click') {
					//console.log("click ID " + id);
					var node = jxtree.getNodeByUserId(id);
					if (node) {
						if (clicked_boundbox) {
							if (!is_included(box, clicked_boundbox)) {
								continue;
							}
						}
						clicked_boundbox = box;
						clicked_node = node;
						/*
						var is_checked = node.isChecked();
						if (is_checked) {
							node.checkSubtree(JXTree.UNCHECKED);
						} else {
							node.checkSubtree(JXTree.CHECKED);
							node.openSupertree(JXTree.OPEN);
						}
						is_checked = node.isChecked();
						var clickmap_tree_node = node.getUserData().clickmap_tree_node;
						if (clickmap_tree_node) {
							$.each(clickmap_tree_node.markers, function() {
								console.log("clicking: " + this.context.id + " vs. " + id);
								if (this.context.id == id) {
									if (is_checked) {
										bubble_open(this);
									} else {
										bubble_close(this);
									}
								}
							});
						}
						//found = true;
						//break;
						*/
					}
				} else if (type == 'mouseover') {
					found = true;
					break;
				} else if (type == 'mouseup') {
					if (button == 2) {
						console.log("right button");
					}
				}
			}
		}
		if (found) {
			break;
		}
	}

	if (clicked_node) {
		var node = clicked_node;
		var is_checked = node.isChecked();
		if (is_checked) {
			node.checkSubtree(JXTree.UNCHECKED);
		} else {
			node.checkSubtree(JXTree.CHECKED);
			node.openSupertree(JXTree.OPEN);
		}
		is_checked = node.isChecked();
		var clickmap_tree_node = node.getUserData().clickmap_tree_node;
		if (clickmap_tree_node) {
			$.each(clickmap_tree_node.markers, function() {
				//console.log("clicking: " + this.context.id + " vs. " + id);
				if (is_checked) {
					bubble_open(this);
				} else {
					bubble_close(this);
				}
			});
		}
	}

	var map_name = overlay.map_.map_name;
	var cursor = found ? 'pointer' : 'default';
	if (overlay.cursor != cursor) {
		overlay.map_.setOptions({draggableCursor: cursor, draggingCursor: 'move'});
		overlay.cursor = cursor;
	}
}

USGSOverlay.prototype.onAdd = function() {

	function simpleBindShim(thisArg, func) {
		return function() { func.apply(thisArg); };
	}

//	google.maps.event.addListener(this.getMap(), 'bounds_changed', simpleBindShim(this, this.resize));
	google.maps.event.addListener(this.getMap(), 'center_changed', simpleBindShim(this, this.draw));

	google.maps.event.addListener(this.getMap(), 'mouseup', function(e, e2) {
		event_ckmap(e, e2, 'mouseup');
	});

	google.maps.event.addListener(this.getMap(), 'click', function(e, e2) {
		var x = e.pixel.x;
		var y = e.pixel.y;
		//console.log("click on " + x + " " + y);
		for (var nn = 0; nn < overlay.boundBoxes.length; ++nn) {
			var box = overlay.boundBoxes[nn][0];
			if (x >= box[0] && x <= box[0]+box[2] && y >= box[1] && y <= box[1]+box[3]) {
				var gene_name = overlay.boundBoxes[nn][1];
				var type = overlay.boundBoxes[nn][2];
				console.log("click on: " + gene_name + " " + navicell.dataset.getGeneByName(gene_name).id + " " + type);
				if (type == "heatmap") {
					$("#heatmap_select_gene").val(navicell.dataset.getGeneByName(gene_name).id);
					$("#heatmap_editor_div").dialog("open");
					update_heatmap_editor();
				} else if (type == "barplot") {
					$("#barplot_select_gene").val(navicell.dataset.getGeneByName(gene_name).id);
					$("#barplot_editor_div").dialog("open");
					update_barplot_editor();
				} else if (type == "glyph") {
					$("#glyph_select_gene").val(navicell.dataset.getGeneByName(gene_name).id);
					$("#glyph_editor_div").dialog("open");
					update_glyph_editor();
				}
				break;
			}
		}
		event_ckmap(e, e2, 'click');
	});

	// ....

	google.maps.event.addListener(this.getMap(), 'mousemove', function(e, e2) {
		var x = e.pixel.x;
		var y = e.pixel.y;
		var found = false;
		for (var nn = 0; nn < overlay.boundBoxes.length; ++nn) {
			var box = overlay.boundBoxes[nn][0];
			if (x >= box[0] && x <= box[0]+box[2] && y >= box[1] && y <= box[1]+box[3]) {
				found = true;
				break;
			}
		}
		var map_name = overlay.map_.map_name;
		var cursor = found ? 'pointer' : 'default';
		if (overlay.cursor != cursor) {
			overlay.map_.setOptions({draggableCursor: cursor, draggingCursor: 'move'});
			overlay.cursor = cursor;
		}

		if (!found) {
			event_ckmap(e, e2, 'mouseover');
		}
	});
	this.setMap(this.map_);

	var div = document.createElement('div');
	div.style.borderStyle = 'none';
	div.style.borderWidth = '0px';
	div.style.width = '100%';
	div.style.height = '100%';
	div.style.position = 'absolute';

	div.left = 0;
	div.top = 0;
	div.style.left = '0px';
	div.style.top = '0px';

	var draw_canvas = document.createElement('canvas');
	draw_canvas.style.left = '0px';
	draw_canvas.style.top = '0px';

	draw_canvas.width = MAX_SCREEN_WIDTH;
	draw_canvas.height = MAX_SCREEN_HEIGHT;
	draw_canvas.style.width = draw_canvas.width + 'px';
	draw_canvas.style.height = draw_canvas.height + 'px';

	draw_canvas.style.position = 'absolute';
	div.appendChild(draw_canvas);

	this.div_ = div;
	this.context = draw_canvas.getContext('2d');
	this.context.save();

	var panes = this.getPanes();
	panes.overlayLayer.appendChild(div);

	this.draw();
}

USGSOverlay.prototype.resize = function() {
	if (this.div_ == null) {
		//this.onAdd();
		return;
	}
	var overlayProjection = this.getProjection();
	var ne = overlayProjection.fromLatLngToDivPixel(this.map_.getBounds().getSouthWest());
	var sw = overlayProjection.fromLatLngToDivPixel(this.map_.getBounds().getNorthEast());

	var width = ne.x - sw.x;
	var height = sw.y - ne.y;

	var div = this.div_;
	if (div.width != width || div.height != height) {
		div.width = width;
		div.height = height;
		div.style.width = width + 'px';
		div.style.height = height + 'px';
	}

	var left = sw.x;
	var top = ne.y;
	if (div.left != left || div.top != top) {
		div.left = left;
		div.top = top;
		div.style.left = left + 'px';
		div.style.top = top + 'px';
	}
}

USGSOverlay.prototype.draw = function(module_name) {

	if (this.div_ == null) {
		return;
	}
	this.resize();

	this.context.clearRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
	this.boundBoxes = [];

	//console.log("displayDLOs: " + navicell.drawing_config.displayDLOs());
	if (!navicell.drawing_config.displayDLOs()) {
		return;
	}
	// may add another condition, such as navicell.drawing_config.blablabla
	if (module_name && navicell.drawing_config.displayDLOsOnAllGenes()) {
		this.arrpos = navicell.dataset.getArrayPos(module_name);
	}
	var arrpos = this.arrpos;
	if (arrpos && arrpos.length) {
		//console.log("drawing " + arrpos.length);
		var div = this.div_;
		var overlayProjection = this.getProjection();
		var mapProjection = this.map_.getProjection();
		var scale = Math.pow(2, this.map_.zoom);

		//console.log("drawing " + arrpos.length + " points");
		for (var nn = 0; nn < arrpos.length; ++nn) {
			//var latlng = map.getProjection().fromPointToLatLng(arrpos[nn].p);
			//console.log("drawing: " + arrpos[nn].gene_name + " " + arrpos[nn].p.x + " " + arrpos[nn].p.y);
			var latlng = mapProjection.fromPointToLatLng(arrpos[nn].p);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			navicell.dataset.drawDLO(this, this.context, scale, arrpos[nn].gene_name, pix.x-div.left, pix.y-div.top);
		}
		
		/*
		var latlng0 = mapProjection.fromPointToLatLng(arrpos[0].p);
		var pix0 = overlayProjection.fromLatLngToDivPixel(latlng0);
		this.context.strokeStyle = 'orange';
		this.context.lineWidth = 1;
		for (var nn = 1; nn < arrpos.length; ++nn) {
			var latlng = mapProjection.fromPointToLatLng(arrpos[nn].p);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			this.context.beginPath();
			this.context.moveTo(pix0.x-div.left, pix0.y-div.top);
			this.context.lineTo(pix.x-div.left, pix.y-div.top);
			this.context.stroke();
		}
		*/
	}
}

USGSOverlay.prototype.reset = function() {
	this.arrpos = [];
}

USGSOverlay.prototype.addBoundBox = function(box, gene_name, chart_type) {
	this.boundBoxes.push([box, gene_name, chart_type]);
}

USGSOverlay.prototype.remove = function(rm_arrpos) {
	//console.log("trying to remove: " + rm_arrpos.length + " " + this.arrpos.length);
	if (!rm_arrpos.length) {
		return;
	}
	/*
	for (var jj = 0; jj < rm_arrpos.length; ++jj) {
		console.log("rm_pos " + rm_arrpos[jj].id);
	}
	for (var jj = 0; jj < this.arrpos.length; ++jj) {
		console.log("pos " + this.arrpos[jj].id);
	}
	*/
	var arrpos = [];
	for (var ii = 0; ii < this.arrpos.length; ++ii) {
		var pos = this.arrpos[ii];
		var keep = 1;
		for (var jj = 0; jj < rm_arrpos.length; ++jj) {
			if (rm_arrpos[jj].id == pos.id) {
				keep = 0;
				break;
			}
		}
		if (keep) {
			arrpos.push(pos);
		}
	}
	this.arrpos = arrpos;
}

USGSOverlay.prototype.onRemove = function() {
	this.div_.parentNode.removeChild(this.div_);
	this.div_ = null;
}
