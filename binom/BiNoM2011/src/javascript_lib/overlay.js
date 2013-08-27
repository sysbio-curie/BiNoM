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

USGSOverlay.prototype.onAdd = function() {

	function simpleBindShim(thisArg, func) {
		return function() { func.apply(thisArg); };
	}

//	google.maps.event.addListener(this.getMap(), 'bounds_changed', simpleBindShim(this, this.resize));
	google.maps.event.addListener(this.getMap(), 'center_changed', simpleBindShim(this, this.draw));
	// ....

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

USGSOverlay.prototype.draw = function() {

	if (this.div_ == null) {
		return;
	}
	this.resize();

	console.log("overlay.DRAW " + this.arrpos.length);
	this.context.clearRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

	if (!navicell.drawing_config.displayDLOs()) {
		return;
	}
	var arrpos = this.arrpos;
	if (arrpos.length) {
		var div = this.div_;
		var overlayProjection = this.getProjection();
		var mapProjection = this.map_.getProjection();
		var scale = Math.pow(2, this.map_.zoom);

		//this.context.fillStyle = 'rgba(100, 30, 100, 1)';

		//console.log("drawing " + arrpos.length + " points");
		for (var nn = 0; nn < arrpos.length; ++nn) {
			//var latlng = map.getProjection().fromPointToLatLng(arrpos[nn].p);
			console.log("drawing: " + arrpos[nn].gene_name + " " + arrpos[nn].p.x + " " + arrpos[nn].p.y);
			var latlng = mapProjection.fromPointToLatLng(arrpos[nn].p);
			var pix = overlayProjection.fromLatLngToDivPixel(latlng);
			
			/*
			//var mod3 = (nn % 3);
			var mod3 = 2;
			var size = mod3+1;
			if (mod3 == 0) {
				this.context.fillStyle = 'rgba(100, 30, 100, 1)';
			} else if (mod3 == 1) {
				this.context.fillStyle = 'rgba(200, 200, 50, 1)';
			} else {
				this.context.fillStyle = 'rgba(100, 200, 100, 1)';
			}
			console.log("Drawing " + arrpos[nn].gene_name);
//			this.context.fillRect(pix.x-div.left, pix.y-div.top, size*scale, size*scale);
			//this.context.fillRect(pix.x-div.left, pix.y-div.top, (size+2)*scale, size*scale);
			*/
			navicell.dataset.drawDLO(this.context, scale, arrpos[nn].gene_name, pix.x-div.left, pix.y-div.top);
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
	console.log("resetting overlay");
	this.arrpos = [];
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
