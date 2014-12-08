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

var MAX_SCREEN_WIDTH = 2500;
var MAX_SCREEN_HEIGHT = 2000;
var ESP_LATLNG = 0.01;
var CENTER_HIGHLIGHT_SHAPE = true;
var CENTER_HIGHLIGHT_CIRCLE = !CENTER_HIGHLIGHT_SHAPE;

USGSOverlay.prototype = new google.maps.OverlayView();

function overlay_init(map) {
	overlay = new USGSOverlay(map);
}

function USGSOverlay(map) {
	this.win = window;
	this.map_ = map;
	this.div_ = null;
	this.setMap(map);
	this.arrpos = [];

	// test
	// this.map_.getDiv().style.opacity = "0.4"; // this.map_.getDiv().style.filter  = 'alpha(opacity=60)' // for IE
	// works but not ok for our purpose. Look at:
	// google: javascript copy canvas to image
	// google: opaque png
}

function is_included(box, clicked_boundbox) {
	return box[0] >= clicked_boundbox[0] && box[0]+box[2] <= clicked_boundbox[0]+clicked_boundbox[2] &&
		box[1] >= clicked_boundbox[1] && box[1]+box[3] <= clicked_boundbox[1]+clicked_boundbox[3];
}

function click_node(overlay, node, mode, center, clicked_boundbox) {
	var overlayProjection = overlay.getProjection();
	var mapProjection = overlay.map_.getProjection();

	console.log("click_node: " + mode);
	if (mode == 'toggle') {
		var is_checked = node.isChecked();
		if (is_checked) {
			node.checkSubtree(JXTree.UNCHECKED);
		} else {
			node.checkSubtree(JXTree.CHECKED);
			node.openSupertree(JXTree.OPEN);
		}
	} else if (mode == 'select') {
		node.checkSubtree(JXTree.CHECKED);
		node.openSupertree(JXTree.OPEN);
	} else if (mode == 'unselect') {
		node.checkSubtree(JXTree.UNCHECKED);
	}

	is_checked = node.isChecked();
	var clickmap_tree_node = node.getUserData().clickmap_tree_node;

	var latlng;
	if (clicked_boundbox) {
		if (!clicked_boundbox.gpt) {
				clicked_boundbox.gpt = new google.maps.Point(clicked_boundbox[0], clicked_boundbox[2]);
		}
		latlng = mapProjection.fromPointToLatLng(clicked_boundbox.gpt);
	} else {
		latlng = null;
	}

	console.log("clickmap_tree_node: " + clickmap_tree_node);
	if (clickmap_tree_node) {
		$.each(clickmap_tree_node.markers, function() {
			var diff_lat;
			var diff_lng;
			if (latlng) {
				diff_lat = Math.abs(latlng.lat() - this.getPosition().lat());
				diff_lng = Math.abs(latlng.lng() - this.getPosition().lng());
			}
			if (!latlng || (diff_lat <= ESP_LATLNG && diff_lng <= ESP_LATLNG)) {
				if (mode == "select") {
					if (overlay.win.nv_open_bubble) {
						bubble_open(this);
					}
					if (center) {
						var bounds = new google.maps.LatLngBounds();
						extend(bounds, this);
						overlay.win.map.setCenter(bounds.getNorthEast());
					}
				} else if (mode == "unselect") {
					bubble_close(this);
				} else if (mode == "toggle") {
					if (is_checked) {
						if (overlay.win.nv_open_bubble) {
							bubble_open(this);
						}
						if (center) {
							var bounds = new google.maps.LatLngBounds();
							extend(bounds, this);
							overlay.win.map.setCenter(bounds.getNorthEast());
						}
					} else {
						bubble_close(this);
					}
				}
			}
		});
	}
}

function event_ckmap(e, e2, type, overlay) {
	var x = e.pixel.x;
	var y = e.pixel.y;
	var event = (overlay.win.event ? overlay.win.event : overlay.event);
	if (type == 'mouseup' && event) {
		console.log("alt " + event.altKey + " " + event.ctrlKey + " " + event.shiftKey);
	}
	var button = (event ? event.button : -1);

	var overlayProjection = overlay.getProjection();
	var mapProjection = overlay.map_.getProjection();
	var scale = Math.pow(2, overlay.map_.zoom);
	var div = overlay.div_;

	var found = false;
	var module = overlay.win.document.navicell_module_name;

	var jxtree = navicell.mapdata.getJXTree(module);
	var modif_map = navicell.mapdata.getModifMap(module);
	var clicked_node = null;
	var clicked_boundbox = null;
	var clicked_latlng = null;
	var clicked_center_box = null;
	for (var id in modif_map) {
		var boxes = modif_map[id];
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
				if (type == 'click' || type == 'mouseup') {
					if (type != 'mouseup' || button == 2) {
						var said = box[4];
						console.log("click ID " + id + " said=" + said + " in " + module + " " + button);
						var node = jxtree.getNodeByUserId(id);
						if (node) {
							if (clicked_boundbox) {
								if (!is_included(box, clicked_boundbox)) {
									continue;
								}
							}
							clicked_boundbox = box;
							clicked_node = node;
							clicked_latlng = latlng;
							if (type == 'mouseup') {
								clicked_center_box = [pix.x, bw, pix.y, bh];
							}
						}
					}
				} else if (type == 'mouseover') {
					found = true;
					break;
				}
			}
		}
		if (found) {
			break;
		}
	}

	if (type == 'click') {
		console.log("clicked_node: " + clicked_node);
	}
	//overlay.clicked_center_box = clicked_center_box;
	if (clicked_node) {
		var o_open_bubble = overlay.win.nv_open_bubble;
		overlay.win.nv_open_bubble = (type == 'click'); // center ??
		var mode = (type == 'click' ? 'toggle' : 'select');
		if (event && !event.ctrlKey) {
			nv_perform("nv_uncheck_all_entities", overlay.win);
		}
		nv_perform("nv_select_entity", overlay.win, clicked_node.user_id, mode, false, clicked_boundbox);
		overlay.win.nv_open_bubble = o_open_bubble;
		if (type == 'mouseup') {
			overlay.clicked_center_box = clicked_center_box;
			overlay.win.map.setCenter(clicked_latlng); // just testing
		}
	}

	var map_name = overlay.map_.map_name;
	var cursor = found ? 'pointer' : 'default';
	if (overlay.cursor != cursor) {
		overlay.map_.setOptions({draggableCursor: cursor, draggingCursor: 'move'});
		overlay.cursor = cursor;
	}
	if (type == 'click' && !navicell.getDrawingConfig(module).displayDLOsOnAllGenes()) {
		overlay.draw(module); // new: warning to performance !
	}
}

USGSOverlay.prototype.onAdd = function() {

	function simpleBindShim(thisArg, func) {
		return function() { func.apply(thisArg); };
	}

	google.maps.event.addListener(this.getMap(), 'center_changed', simpleBindShim(this, this.draw));

	var overlay = this;

	this.getMap().getDiv().onclick = function(event) {
		overlay.event = event;
		//console.log("onclick: " + event.button);
	}

	this.getMap().getDiv().onmouseup = function(event) {
		overlay.event = event;
		//console.log("onmouseup: " + event.button);
	}

	google.maps.event.addListener(this.getMap(), 'mouseup', function(e, e2) {
		event_ckmap(e, e2, 'mouseup', overlay);
	});

	google.maps.event.addListener(this.getMap(), 'click', function(e, e2) {
		var x = e.pixel.x;
		var y = e.pixel.y;
		var doc = overlay.win.document;
		var module = overlay.win.document.navicell_module_name;
		console.log("click at " + x + " " + y + " module " + module);
		for (var nn = 0; nn < overlay.boundBoxes.length; ++nn) {
			var box = overlay.boundBoxes[nn][0];
			if (x >= box[0] && x <= box[0]+box[2] && y >= box[1] && y <= box[1]+box[3]) {
				//console.log("found : x=" + box[0] + ", w=" + box[2] + ", y=" + box[1] + ", h=" + box[3]);
				var gene_name = overlay.boundBoxes[nn][1];
				var m_gene_names = "";
				var modif_id = overlay.boundBoxes[nn][4];
				var gene_id;
				var info = navicell.dataset.getGeneInfoByModifId(module, modif_id);
				if (info) {
					var genes = info[0];
					if (genes.length > 1) {
						for (var nn = 0; nn < genes.length; ++nn) {
							if (nn > 0) {
								m_gene_names += ", ";
							}
							m_gene_names += genes[nn].name;
						}
						m_gene_names += " (" + modif_id + ")";
						gene_name = m_gene_names;
						gene_id = "";
					} else {
						gene_id = navicell.dataset.getGeneByName(gene_name).id;
					}
				}
				var type = overlay.boundBoxes[nn][2];
				var hint = overlay.boundBoxes[nn][3];
				console.log("click on: " + gene_name + " " + gene_id + " " + type + " " + modif_id);
				if (type == "heatmap") {
					$("#heatmap_select_gene", doc).val(gene_id);
					if (m_gene_names) {
						$("#heatmap_select_m_genes", doc).html(m_gene_names);
					} else {
						$("#heatmap_select_m_genes", doc).html("");
					}

					$("#heatmap_select_modif_id", doc).html(modif_id);
					$("#heatmap_editor_div", doc).dialog("open");
					update_heatmap_editor(doc);
				} else if (type == "barplot") {
					$("#barplot_select_gene", doc).val(gene_id);
					if (m_gene_names) {
						$("#barplot_select_m_genes", doc).html(m_gene_names);
					} else {
						$("#barplot_select_m_genes", doc).html("");
					}

					$("#barplot_select_modif_id", doc).html(modif_id);
					$("#barplot_editor_div", doc).dialog("open");
					update_barplot_editor(doc);
				} else if (type == "glyph") {
					$("#glyph_select_gene_" + hint, doc).val(gene_id);
					$("#glyph_editor_div_" + hint, doc).dialog("open");
					update_glyph_editor(doc, null, hint);
				}
				break;
			}
		}
		event_ckmap(e, e2, 'click', overlay);
	});

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
			event_ckmap(e, e2, 'mouseover', overlay);
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

	if (draw_canvas.getContext) {
		this.context = draw_canvas.getContext('2d');
		this.context.save();
	}

	var panes = this.getPanes();
	panes.overlayLayer.appendChild(div);

	this.draw();
}

USGSOverlay.prototype.resize = function() {
	if (this.div_ == null) {
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

USGSOverlay.prototype.draw = function(module) {

	if (this.div_ == null) {
		return;
	}
	if (!this.context) {
		this.boundBoxes = [];
		return;
	}
	this.resize();

	this.context.clearRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
	this.boundBoxes = [];

	if (!module) {
		module = get_module();
	}

	if (this.clicked_center_box) {
		var context = this.context;
		context.fillStyle = "#FFFFFF";
		context.globalAlpha = 0.65;
		context.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
		var div = this.div_;
		var x = this.clicked_center_box[0] - div.left;
		var y = this.clicked_center_box[2] - div.top;
		var w = this.clicked_center_box[1];
		var h = this.clicked_center_box[3];
		if (CENTER_HIGHLIGHT_SHAPE) {
			//context.clearRect(x, y, w, h);
			context.clearRect(x-1, y-1, w+2, h+2);
		} else if (CENTER_HIGHLIGHT_CIRCLE) {
			var radius = w;
			if (h > radius) {
				radius = h;
			}
			/*
			if (radius < 100) {
				radius = 100;
			}
			*/
			context.save();
			context.globalAlpha = 1;
			context.beginPath();
			context.arc(x+w/2, y+h/2, radius, 0, 2*Math.PI);
			context.clip();
			context.clearRect(x - radius - 1, y - radius - 1, radius * 2 + 2, radius * 2 + 2);
			context.restore();
		}
	}

	var drawing_config = navicell.getDrawingConfig(module);
	if (!drawing_config.displayDLOs()) {
		return;
	}
	if (drawing_config.displayMapStaining()) {
		draw_voronoi(module, this.context, this.div_);
	}

	var arrpos = null;
	if (drawing_config.displayDLOsOnAllGenes()) {
		this.arrpos = navicell.dataset.getArrayPos(module);
	} else {
		this.arrpos = navicell.dataset.getSelectedArrayPos(module);
	}

	var arrpos = this.arrpos;
	if (arrpos && arrpos.length) {
		var div = this.div_;
		var overlayProjection = this.getProjection();
		var mapProjection = this.map_.getProjection();
		var scale = Math.pow(2, this.map_.zoom);

		var MARGIN = 30;
		var div_width = div.width;
		var div_height = div.height+MARGIN;
		cache_value_cnt = 0;
		no_cache_value_cnt = 0;
		for (var nn = 0; nn < arrpos.length; ++nn) {
			var latlng = mapProjection.fromPointToLatLng(arrpos[nn].p);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			var pos_x = pix.x - div.left;
			var pos_y = pix.y - div.top;
			if (pos_x > -MARGIN && pos_x < div_width &&
			    pos_y >= 0 && pos_y < div_height) {
				navicell.dataset.drawDLO(module, this, this.context, scale, arrpos[nn].id, arrpos[nn].gene_name, pix.x-div.left, pix.y-div.top);
			} else {
			}
		}
		//console.log("CACHE_VALUE_CNT " + cache_value_cnt + " " + no_cache_value_cnt);
	}
}

USGSOverlay.prototype.reset = function() {
	this.clicked_center_box = null;
	this.arrpos = [];
}

USGSOverlay.prototype.addBoundBox = function(box, gene_name, chart_type, hint, modif_id) {
	this.boundBoxes.push([box, gene_name, chart_type, hint, modif_id]);
}

USGSOverlay.prototype.remove_old = function(rm_arrpos) {
	if (!rm_arrpos.length) {
		return;
	}
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

