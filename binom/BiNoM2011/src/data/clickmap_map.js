/**
 * Stuart Pook (Sysra), $Id$
 *
 * Copyright (C) 2011-2012 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
 * 
 * BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

var filter = ".navicell";

var jtree;
var map;
var projection;
var to_open;

var maps;

// http://www.contentwithstyle.co.uk/content/make-sure-that-firebug-console-debug-doesnt-break-everything/index.html
if(!window.console)
{
	window.console = new function()
	{
		this.log = function(str) {};
		this.dir = function(str) {};
	};
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
	console.log("extend: proj", proj);
	if (typeof proj !== 'undefined')
	{
		var point = proj.fromLatLngToPoint(marker.getPosition());
		var height_in_world_coords = marker_height / scale;
		bounds.extend(proj.fromPointToLatLng(new google.maps.Point(point.x - xoffset, point.y - height_in_world_coords)));
		bounds.extend(proj.fromPointToLatLng(new google.maps.Point(point.x + xoffset, point.y)));
	}
}

function show_markers_ref(markers, ref)
{
	var bounds = new google.maps.LatLngBounds();
	$.each
	(
		markers,
		function (key, id)
		{
			var elements = $("li#" + id + filter);
//			alert("found " + elements.length + " for " + id);
			elements.each
			(
				function ()
				{
//					alert("show_markers_ref lookup " + this + " for " + id);
					get_markers_for_modification(this, projection, map);
		//			console.log(element.attr("id"), markers.length);
					this.markers.forEach
					(
						function(i)
						{
							i.setVisible(false);
							extend(bounds, i);
						}
					);
					this.markers.forEach
					(
						function(i)
						{
							i.setVisible(true);
							i.setAnimation(google.maps.Animation.DROP);
						}
					);
					ref.check_node(this);
					this.peers.each
					(
						function(i)
						{
							ref.check_node(this);
						}
					);
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
		var position = $(element).attr("position");
		if (position == null)
		{
			var cls = $(element).attr("class");
			var nid = /\bs\d+\b/.exec(cls);
			var selector = "li#" + nid;
			var idl = $(selector);
			idl.each
			(
				function ()
				{
					get_markers_for_modification(this, projection, map);
				}
			);
		}
		else
		{
			var id = $(element).attr("id");
//			alert("get_markers_for_modification create " + id);
			element.markers = Array();
			var positions = position.split(" ");
			for (var index = 0; index < positions.length; index++)
			{
				var item = positions[index];
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
			};
			element.peers = $("li." + id);
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

// http://groups.google.com/group/jstree/browse_thread/thread/7ed7cd132d2c19b

$.expr[':'].jstree_contains_plusTitle = function (a, i, m)
{
	var s = m[3].toLowerCase();
	// http://stackoverflow.com/questions/1018855/finding-elements-with-text-using-jquery
	var r = $(a).filter("a").parent().children().filter("a").filter(function(index) {
		return $(this).children().length == 2 && $(this).text().toLowerCase().match(s);
	});
	if (r.length != 0)
		return true;
	// normal jstree_contains search first (see jquery.jstree.js line 3403)
	if ((a.textContent || "").toLowerCase().indexOf(m[3].toLowerCase()) >= 0)
		return false;
	if ((a.innerText || "").toLowerCase().indexOf(m[3].toLowerCase()) >= 0)
		return false;
		// custom search within title if nothing found
	if ((a.title || "").toLowerCase().indexOf(m[3].toLowerCase()) >= 0)
		return false;
	return false;
	
}; 

function start_right_hand_panel(selector, source, map, projection, whenloaded)
{
	console.log("search setup");
//	$("#search").click(function () {
//		var t = $("#query_text").val();
//
//		console.log("about to search", selector);
//		$(selector).jstree("search", t);
//	});
	
	var tree = $(selector);
	var search_field = $('#query_text');
	var search_label = "\u2002Search\u00a0";
	
	//http://stackoverflow.com/questions/699065/submitting-a-form-on-enter-with-jquery
	search_field.keypress(function(e) {
        if(e.which == 13) {
 //           jQuery('#search').focus().click();
        	search_field.blur();
    		var t = $(this).val();
    		tree.jstree("search", t);
           
        }
    });
	search_field.val(search_label);
	search_field.focus(function(e)
	{
		// http://drupal.org/node/154137
		if ($(this).val() == search_label)
		{
			$(this).val("");
		}
    });

	jtree = tree
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
			"search" :
			{
				"search_method" : "jstree_contains_plusTitle"
			},
			plugins : [ "themes", "search", "xml_data", "ui", "checkbox", "languages" ],
			html_titles : true
		}).bind("uncheck_node.jstree", function(event, data) {
			var f = function(index, element)
			{
				$.each(element.markers, function(key, i) { i.setVisible(false); });
				element.peers.each
				(
					function()
					{
						jQuery.jstree._reference(jtree).uncheck_node(this);
					}
				);
			};
			$(this).jstree("get_unchecked",data.args[0], true).filter(filter).each(f);
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
					
					$.each(element.markers,
						function(key, i)
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
						function ()
						{
							jQuery.jstree._reference(jtree).check_node(this);
						}
					);
				};
				
				jtree.jstree("get_checked", data.args[0], true).filter(filter).each(f);
				$(data.args[0].parentNode.parentNode).filter(filter).each(f);
//				jtree.jstree("get_checked", data.args[0], true).each(f);
//				$(data.args[0].parentNode.parentNode).each(f);
				if (!bounds.isEmpty())
					map.panToBounds(bounds);
			}
		).bind("search.jstree", function (e, data) {
//			alert("Found " + data.rslt.nodes.length + " nodes matching '" + data.rslt.str + "'.");
		});
};

function open_blog_click(e)
{
	try
	{
		show_blog(e.currentTarget.alt);
	}
	catch (f)
	{
	};
	return false;
}

function open_module_map_click(e)
{
	try
	{
		show_map_and_markers(e.currentTarget.alt, []);
	}
	catch (f)
	{
	};
	return false;
}

function clickmap_start(blogname, map_name, panel_selector, map_selector, source, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift)
{
	console.log("clickmap_start", to_open);
	if (!maps)
	{
		maps = Object();
	}
	maps[map_name] = window;

	var map = start_map(map_selector, min_zoom, max_zoom, tile_width, tile_height, width, height, xshift, yshift);
	var whenready = function(e, data)
	{
		console.log("when ready", to_open);
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
		$("img.blogfromright").click(open_blog_click);
		$("img.mapmodulefromright").click(open_module_map_click);
		console.log("to_open set", to_open, to_open.length);
		
	};
	start_right_hand_panel(panel_selector, source, map.map, map.projection, whenready);
	var tell_opener = function()
	{
		var blog = maps[""];
		if (blog && !blog.closed)
		{
			console.log("tell_opener yes", blog, maps);
			blog.maps = maps;
		}
		else
			console.log("tell_opener no", maps);
	};
	tell_opener();
	setInterval(tell_opener, 1000);
}

function show_blog(postid)
{
	var blog = maps[""];
	if (typeof blog !== 'undefined' && !blog.closed)
	{
		blog.location = blog_link(postid);
	}
	else
	{
		blog = window.open(blog_link(postid));
		maps[""] = blog;
	}
	blog.focus();
}

function show_map_and_markers(map_name, ids)
{
	console.log("show_map_and_markers", map_name, ids);
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
		console.log("not open is map", map, maps);
		map = window.open("../" + map_name);
		map.to_open = ids;
		map.maps = maps;
		maps[map_name] = map;
	}
}
