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
function initialize() {
	var tileOptions = {
			  getTileUrl: function(coord, zoom) {
				  return "tiles/" + zoom + "_" + coord.x + "_" + coord.y + ".png";
			  },
			  tileSize: new google.maps.Size(256, 256),
			  isPng: true
			};

	var myMapType = new google.maps.ImageMapType(tileOptions);
	
	
    var latlng = new google.maps.LatLng(-34.397, 150.644);
    var myOptions = {
      zoom: 8,
      center: latlng,
      mapTypeId: "ClickMap"
    };
    var map = new google.maps.Map(document.getElementById("map"),
        myOptions);
    map.mapTypes.set("ClickMap", myMapType);
  }

function make_marker(map, lat, lng)
{
	var marker = new google.maps.Marker({
	    position: new google.maps.LatLng(lat, lng),
	    map: map,
	    title: lat + ", " + lng,
	    zIndex: 0
	});
}

var fromPointToLatLng;

function osm(min_zoom, max_zoom, width, height) {
	
	function ClickMapProjection()
	{
		// http://code.google.com/apis/maps/documentation/javascript/examples/map-projection-simple.html
	};
	
	fromPointToLatLng = function(point, noWrap) {
		var y = point.y;
		var x = point.x;
		return new google.maps.LatLng(y / height * (2 * lat) - lat, x / width * (2 * lng) - lng, noWrap);
	};
	ClickMapProjection.prototype.fromLatLngToPoint = function(latLng)
	{
		return new google.maps.Point((latLng.lng() + lng) / (2 * lng) * width, (latLng.lat() + lat) / (2 * lat) * height);
	}
	ClickMapProjection.prototype.fromPointToLatLng = function(point, noWrap) {
		return fromPointToLatLng(point, noWrap);
	};
	
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
		tileSize : new google.maps.Size(width, height),
		maxZoom : max_zoom,
		minZoom : min_zoom
	});
	
	map_type.projection = new ClickMapProjection();

	map.mapTypes.set(id, map_type);
	
	var lat = 90;
	var lng = 180;
	    
	var bounds = new google.maps.LatLngBounds();
	bounds.extend(new google.maps.LatLng(-lat, 0));
	bounds.extend(new google.maps.LatLng(lat, 2 * lng));
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

function start_right_hand_panel(selector, source, map, max_zoom) {
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
			console.log("--", " ", element.id);
			element.markers.forEach(function(i) { i.setVisible(false); });
		};
//		if (data.args[0].parentNode.parentNode.id == "")
		$(this).jstree("get_unchecked",data.args[0],true).filter(filter).each(f);
		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
//		console.log("- " + data.args[0].parentNode.parentNode.id);
	}).bind("check_node.jstree", function(event, data) {
//		console.log(event.type + ' checked l=' +  data.args.length + ' ' + data.args[0].parentNode.id + ' href=' + data.rslt.obj.attr('href') + " " + data.inst.get_text(data.rslt.obj));

		var f = function(index, element) {
			console.log("++" + index + " " + $(element).attr("id"));
			if (element.markers != null)
				element.markers.forEach(function(i) { i.setVisible(true); });
			else
			{
				element.markers = Array();
				$(element).attr("position").split(" ").forEach(
						function(item)
						{
							var xy = item.split(";");
//							console.log("create new marker for", element, xy[0] / z, xy[1] / z, z);
							var p = new google.maps.Point(xy[0] / z, xy[1] / z);
							var marker = new google.maps.Marker
							(
									{
										position: fromPointToLatLng(p),
										map: map,
										title: "id " + element.id + " " + item + " " + (xy[0] / z) + " " + (xy[1] / z)
									}
							);
							var infowindow = new google.maps.InfoWindow({
								content: "hello" // + " this is " + $(element).attr("id") + " at " + xy
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
//		console.log("+  '" + data.args[0].parentNode.parentNode.id + "' '" + data.args[0].parentNode.parentNode.className + "'");
	});

};

function clickmap_start(map_name, min_zoom, max_zoom, width, height, selector, source) {
	var map = osm(min_zoom, max_zoom, width, height);
	start_right_hand_panel(selector, source, map, max_zoom);
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
