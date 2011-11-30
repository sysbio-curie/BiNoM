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

function start_map(min_zoom, max_zoom, tile_width, tile_height, xshift, yshift, width, height)
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

	var map = new google.maps.Map(element, {
		center : new google.maps.LatLng(10, 10),
		disableDefaultUI: true,
		zoomControl: true,
		overviewMapControl: true,
		overviewMapControlOptions :
		{
			opened: true
		},
		zoom : 0,
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
	
	map_type.projection = new ClickMapProjection();
	map.mapTypes.set(id, map_type);
	
	var bounds = new google.maps.LatLngBounds();
	bounds.extend(map_type.projection.fromPointToLatLng(new google.maps.Point(xshift + width, yshift + height)));
	bounds.extend(map_type.projection.fromPointToLatLng(new google.maps.Point(xshift, yshift)));
//	bounds.extend(p1);
//	bounds.extend(make_marker(map, xshift + width, yshift + height));
	map.fitBounds(bounds);
	return { map : map, projection : map_type.projection};
	
	var lat = 90;
	var lng = 180;
	    
	var bounds = new google.maps.LatLngBounds();
	bounds.extend(new google.maps.LatLng(-lat, -lng));
	bounds.extend(new google.maps.LatLng(lat, lng));
	map.fitBounds(bounds);
	return map;
	make_marker(map, lat - 0.1, lng);
	make_marker(map, -lat, lng);
	make_marker(map, 0, lng);
	make_marker(map, 0, 0);
	make_marker(map, lat - 0.1, 0);
	make_marker(map, -lat, 0);
	return map;
}

function start_right_hand_panel(selector, source, map, projection, max_zoom, xshift, yshift)
{
	var filter = ".modification";
	var z = 1 << max_zoom;
	$(selector).jstree({
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
		plugins : [ "themes", "xml_data", "ui", "checkbox" ]
	}).bind("uncheck_node.jstree", function(event, data) {
		var f = function(index, element)
		{
			element.markers.forEach(function(i) { i.setVisible(false); });
		};
		$(this).jstree("get_unchecked",data.args[0],true).filter(filter).each(f);
		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
	}).bind("check_node.jstree", function(event, data) {
		var f = function(index, element) {
			if (element.markers != null)
				element.markers.forEach(function(i) { i.setVisible(true); });
			else
			{
				element.markers = Array();
				$(element).attr("position").split(" ").forEach(
						function(item)
						{
							var xy = item.split(";");
							var p = new google.maps.Point(xy[0] / z + xshift, xy[1] / z + yshift);
							var marker = new google.maps.Marker
							(
									{
										position: projection.fromPointToLatLng(p),
										map: map,
										title: "id " + element.id + " " + item + " " + (xy[0] / z) + " " + (xy[1] / z)
									}
							);
							var infowindow = new google.maps.InfoWindow({
								content: $(element).attr("id") + " at " + xy
							});
							google.maps.event.addListener(marker, 'click', function() {
								infowindow.open(map, marker);
							});
							element.markers.push(marker);
						}
				);
			}
		};
		$(this).jstree("get_checked",data.args[0],true).filter(filter).each(f);

		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
	});

};

function clickmap_start(map_name, selector, source, min_zoom, max_zoom, tile_width, tile_height, xshift, yshift, width, height) {
	var map = start_map(min_zoom, max_zoom, tile_width, tile_height, xshift, yshift, width, height);
	start_right_hand_panel(selector, source, map.map, map.projection, max_zoom, xshift, yshift);
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
