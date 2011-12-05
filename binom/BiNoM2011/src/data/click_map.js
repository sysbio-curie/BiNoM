/**
  *    Stuart Pook, Copyright (C) 2011 Institut Curie
  *
  *    This program is free software: you can redistribute it and/or modify
  *    it under the terms of the GNU General Public License as published by
  *    the Free Software Foundation, either version 3 of the License, or
  *    (at your option) any later version.
  *
  *    This program is distributed in the hope that it will be useful,
  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *    GNU General Public License for more details.
  *
  *    You should have received a copy of the GNU General Public License
  *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  *    $Id$
  */

function make_marker(map, pos)
{
	var marker = new google.maps.Marker({
	    position: pos,
	    map: map,
	    title: " ",// + pos.lat + " " + pos.lng,
	    zIndex: 0
	});
	return pos;
}
var filter = ".modification";

var jtree;
var map;
var projection

function show_markers(markers)
{
	console.log(markers);
	markers.forEach
	(
		function(i)
		{
			var element = $("li#" + i).filter(".posttranslational")[0];
			console.log(element, $(element).attr("id"), "show_markers");
			get_markers_for_modification(element, projection, map);
//			console.log(element.attr("id"), markers.length);
			element.markers.forEach
			(
				function(i)
				{
					i.setVisible(false);
				}
			);
			element.markers.forEach
			(
				function(i)
				{
					i.setVisible(true);
					i.setAnimation(google.maps.Animation.DROP);
				}
			);
			jQuery.jstree._reference(jtree).check_node(element);
			element.slaves.each
			(
				function(i)
				{
					jQuery.jstree._reference(jtree).check_node(this);
				}
			);
		}
	);
}

function start_map(min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift)
{
	var lat_ = 90;
	var lng_ = 180;
	lat_ = 50;
	lng_ = 50;
	function ClickMapProjection()
	{
		// http://code.google.com/apis/maps/documentation/javascript/examples/map-projection-simple.html
	};
	ClickMapProjection.prototype.fromPointToLatLng = function(point, noWrap) {
		var y = point.y;
		var x = point.x;
		var lng = -x / tile_width * (2 * lng_) + lng_;
		var lat = y / tile_height * (2 * lat_) - lat_;
		var r = new google.maps.LatLng(lat, lng, noWrap);
		return r;
	};
	ClickMapProjection.prototype.fromLatLngToPoint = function(latLng)
	{
		var x = -(latLng.lng() - lng_) / (2 * lng_) * tile_width;
		var y = (latLng.lat() + lat_) / (2 * lat_) * tile_height;
		var r = new google.maps.Point(x, y);
		return r;
	}
	
	var id = "ClickMap";

	var element = document.getElementById("map");

	map = new google.maps.Map(element, {
        copyright_owner: 'Institut Curie',
		center : new google.maps.LatLng(10, 10),
		disableDefaultUI: true,
		zoomControl: true,
		overviewMapControl: true,
		overviewMapControlOptions :
		{
			opened: true
		},
		zoom : min_zoom,
		mapTypeId : id
	});
	
//	console.log(width + " " +  height);
	
	var map_type = new google.maps.ImageMapType({
		getTileUrl: function(coord, zoom) {
			var ntiles = 1 << zoom;
			if (coord.y < 0 || coord.y >= ntiles)
				return null;
			if (coord.x < 0 || coord.x >= ntiles)
				return null;
			return "tiles/" + zoom + "/" + coord.x + "_" + coord.y + ".png";
		},
		tileSize : new google.maps.Size(tile_width, tile_height),
		maxZoom : max_zoom,
		minZoom : min_zoom
	});
	
	projection = new ClickMapProjection();
	map_type.projection = projection;
	map.mapTypes.set(id, map_type);
	
	var bounds = new google.maps.LatLngBounds();
	bounds.extend(map_type.projection.fromPointToLatLng(new google.maps.Point(xshift + width, yshift + height)));
	bounds.extend(map_type.projection.fromPointToLatLng(new google.maps.Point(xshift, yshift)));
	map.fitBounds(bounds);
	return { map : map, projection : map_type.projection};
}

function get_markers_for_modification(element, projection, map)
{
	if (element.markers == null)
	{
		var id = $(element).attr("id");
		var position = $(element).attr("position");
		if (position == null)
		{
			var element2 = $("li#" + id).filter(".posttranslational")[0];
			get_markers_for_modification(element2, projection, map);
			console.log("position is null", $(element).attr("id"), element.slaves.length);
		}
		else
		{
			element.markers = Array();
			position.split(" ").forEach
			(
				function(item)
				{
					var xy = item.split(";");
					var p = new google.maps.Point(xy[0], xy[1]);
					//$(element).find("a").filter("TextNode").each(function(i, v){console.log(v);});
					var name = jtree.jstree("get_text", element, "en");
	//				$(element).find("a").contents().filter(function(){ return this.nodeType != 1; }).each(function(i, v){ name = v.textContent;});
	//				$(element).find("a").contents().filter(function(){ return this.nodeType != 1; }).wrap("<b/>");
	//				$(element).find("a").filter("TextNode").each(function(i, v){console.log(v);});
					var marker = new google.maps.Marker
					(
							{
								position: projection.fromPointToLatLng(p),
								map: map,
								title: name + " (" + id + ")",
								visible: false
							}
					);
					google.maps.event.addListener
					(
						marker, 'click', function()
						{
							if (element.bubble == null)
							{
		//						var ln = data.inst.get_text(element, 'ln');
								var ln = jtree.jstree("get_text", element, "ln");
								element.bubble = new google.maps.InfoWindow
								(
									{
										content: ln,
										maxWidth: 350
									}
								);
							}
							element.bubble.open(map, marker);
						}
					);
					element.markers.push(marker);
				}
			);
			element.slaves = $("li#" + id).filter(".associated");
			element.slaves.each
			(
				function()
				{
					var s = element.slaves.not(this).add(element);
					this.slaves = s;
					this.markers = element.markers;
				}
			);
			
			console.log($(element).attr("id"), "created", element.markers.length);
		}
	}
	return element.markers;
}

function start_right_hand_panel(selector, source, map, projection)
{
	jtree = $(selector).jstree({
		"themes" : {
			"theme" : "default",
			"dots" : false,
			"icons" : false
		},
		core : { "animation" : 200 },
		"xml_data" : {
			"ajax" : {
				"url" : source
			},
			"xsl" : "nest"
		},
		"languages" : [ "en", "ln" ],
		"checkbox" :
		{
			"checked_parent_open" : false
		},
		plugins : [ "themes", "xml_data", "ui", "checkbox", "languages" ],
		html_titles : true
	}).bind("uncheck_node.jstree", function(event, data) {
		var f = function(index, element)
		{
			element.markers.forEach(function(i) { i.setVisible(false); });
			element.slaves.each
			(
				function()
				{
					jQuery.jstree._reference(jtree).uncheck_node(this);
				}
			);
		};
		$(this).jstree("get_unchecked",data.args[0],true).filter(filter).each(f);
		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
	}).bind("check_node.jstree", function(event, data) {
		console.log("check_node.jstree", data.args[0]);
		var f = function(index, element)
		{
			get_markers_for_modification(element, projection, map);
			
			element.markers.forEach
			(
					function(i)
					{
						if (!i.getVisible())
						{
							i.setVisible(true);
							i.setAnimation(google.maps.Animation.DROP);
						}
					}
				);
			element.slaves.each
			(
				function(i)
				{
					jQuery.jstree._reference(jtree).check_node(this);
				}
			);
		};
		jtree.jstree("get_checked", data.args[0], true).filter(filter).each(f);

		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
	});

};

function clickmap_start(map_name, selector, source, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift) {
	var map = start_map(min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift);
	start_right_hand_panel(selector, source, map.map, map.projection);
	return;
	initialize(min_zoom);
	return;
	var settingsCustomMap = {
	        copyright_owner: 'Institut Curie',
	        minZoom : min_zoom,
	        maxZoom: clickmap_maxzoom,
	        div: 'map'
	};
	var customMap = cellpublisher.customMap.createCustomMap(settingsCustomMap);
	cellpublisher.map = customMap;
	return;
	var settingsMarkers = {
	        xml: 'markers.xml',
	        map: customMap,
	        maxZoom: clickmap_maxzoom
	}
	var markers = cellpublisher.markers.createMarkers(settingsMarkers);
	cellpublisher.marker_links.create_side_bar(markers);
	cellpublisher.marker_checkboxes.activate_checkboxes();
	
	setInterval(function() {
		if (window.opener) { window.opener.maps[map_name] = window };
	}, 100);
}
