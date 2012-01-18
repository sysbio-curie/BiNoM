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

var filter = ".modification";

var jtree;
var map;
var projection;
var to_open;

var maps;
var blog_name;

var log;
if (typeof window.console != 'undefined' && typeof window.console.log != 'undefined')
{
    log = window.console.log;
    log("log active", window);
}
else
{
	log = function() {}
}

function extend(bounds, marker)
{
//	bounds.extend(marker.getPosition());
	var marker_width = 20; // should calculate this from the shape
	var marker_height = 33; // should calculate this from the shape
	
	var map = marker.getMap();
	var scale = 1 << map.getZoom();
	var xoffset = marker_width / 2 / scale
	var proj = map.getProjection();
	var point = proj.fromLatLngToPoint(marker.getPosition());
	var height_in_world_coords = marker_height / scale;
	bounds.extend(proj.fromPointToLatLng(new google.maps.Point(point.x - xoffset, point.y - height_in_world_coords)));
	bounds.extend(proj.fromPointToLatLng(new google.maps.Point(point.x + xoffset, point.y)));
}

function show_markers_ref(markers, ref)
{
	var bounds = new google.maps.LatLngBounds();
	markers.forEach
	(
		function(i)
		{
			var element = $("li#" + i).filter(".posttranslational")[0];
			get_markers_for_modification(element, projection, map);
//			console.log(element.attr("id"), markers.length);
			element.markers.forEach
			(
				function(i)
				{
					i.setVisible(false);
					extend(bounds, i);
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
			ref.check_node(element);
			element.peers.each
			(
				function(i)
				{
					ref.check_node(this);
				}
			);
		}
	);
	if (!bounds.isEmpty())
		map.panToBounds(bounds);
}

function show_markers(markers)
{
	var ref = jQuery.jstree._reference(jtree);
	show_markers_ref(markers, ref);
}

function start_map(map_elementId, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift)
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

	var element = document.getElementById(map_elementId);

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
//			
			if (coord.y < 0 || coord.y >= ntiles)
				return null;
			if (coord.x < 0 || coord.x >= ntiles)
				return null;
			
			var r = coord.x + "_" + coord.y;
//			console.log(coord, zoom, x, y, ntiles, r);
			return "tiles/" + zoom + "/" + r + ".png";
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
	//		console.log("position is null", $(element).attr("id"), element.peers.length);
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
					var name = jtree.jstree("get_text", element, "en");
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
			element.peers = $("li#" + id).filter(".associated");
			element.peers.each
			(
				function()
				{
					var s = element.peers.not(this).add(element);
					this.peers = s;
					this.markers = element.markers;
				}
			);
//			console.log($(element).attr("id"), "created", element.markers.length);
		}
	}
	return element.markers;
}

function start_right_hand_panel(selector, source, map, projection, whenloaded)
{
	jtree = $(selector)
		.bind("loaded.jstree", whenloaded)
		.jstree({
			"themes" : {
				"theme" : "default",
				"dots" : false,
				"icons" : false
			},
			core : {
				"animation" : 200,
				"initially_open" : [ "entities" ]
			},
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
				element.peers.each
				(
					function()
					{
						jQuery.jstree._reference(jtree).uncheck_node(this);
					}
				);
			};
			$(this).jstree("get_unchecked",data.args[0],true).filter(filter).each(f);
			$(data.args[0].parentNode.parentNode).filter(filter).each(f);
		}).bind
		(
			"check_node.jstree",
			function(event, data)
			{
				var bounds = new google.maps.LatLngBounds();
				var f = function(index, element)
				{
					get_markers_for_modification(element, projection, map);
					
					element.markers.forEach
					(
						function(i)
						{
							if (!i.getVisible())
							{
								extend(bounds, i);
								i.setVisible(true);
								i.setAnimation(google.maps.Animation.DROP);
							}
						}
					);
					element.peers.each
					(
						function(i)
						{
							jQuery.jstree._reference(jtree).check_node(this);
						}
					);
				};
				
				jtree.jstree("get_checked", data.args[0], true).filter(filter).each(f);
				$(data.args[0].parentNode.parentNode).filter(filter).each(f);
				if (!bounds.isEmpty())
					map.panToBounds(bounds);
			}
		);

};

function clickmap_start(blogname, map_name, panel_selector, map_selector, source, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift)
{
	log("clickmap_start", to_open);
	if (!maps)
	{
		maps = Object();
	}
	maps[map_name] = window;

	blog_name = blogname;
	var map = start_map(map_selector, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift);
	var whenready = function(e, data)
	{
		log("when ready", to_open);
		if (to_open && to_open.length > 0)
		{
			// http://stackoverflow.com/questions/3585527/why-doesnt-jstree-open-all-work-for-me
			var e = $(panel_selector).find("#entities"); // $("#entities");
			data.inst.open_all(e, false); // otherwise the tree is not checked
			show_markers_ref(to_open, data.inst);
//			data.inst.close_all(e, false); // -1 closes all nodes in the container
//			data.inst.open_node(e, false, true);
			var children = data.inst._get_children(e);
			for (var i = 0; i < children.length; i++)
				data.inst.close_all(children[i], false);
		}
		to_open = [];
		log("to_open set", to_open, to_open.length);
	};
	start_right_hand_panel(panel_selector, source, map.map, map.projection, whenready);
	var tell_opener = function()
	{
		var blog = maps[""];
		if (blog && !blog.closed)
			blog.maps = maps;
	};
	tell_opener();
	setInterval(tell_opener, 100);
}

function show_blog(postid)
{
	var map = window.open("/annotations/" + blog_name + "/index.php?p=" + postid, "blog_" + blog_name);
	maps[""] = map;
	map.focus();
}

function show_map_and_markers(map_name, ids)
{
	var map = maps[map_name];
	if (map && !map.closed)
	{
		if (!map.to_open)
			map.to_open = ids;
		else if (map.to_open.length < 1)
			map.show_markers(ids);
		else
			map.to_open.concat(ids);
		map.focus();
	}
	else
	{
		log("not open is map", map, maps);
		map = window.open("../" + map_name);
		map.to_open = ids;
		map.maps = maps;
		maps[map_name] = map;
	}
}
