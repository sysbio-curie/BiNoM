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
	This file needs to be read by each post in the blog by code in the "Header and Footer" plugin,
	section "HTML code to be inserted in the head section of each page"
 *
 * $Id$
 */
var maps;

//http://www.clientcide.com/best-practices/dbug-a-consolelog-firebug-wrapper/
dbug = {
		firebug: false, debug: false, log: function(msg) {},
		enable: function() { if(this.firebug) this.debug = true; dbug.log = console.debug; dbug.log('enabling dbug');	},
		disable: function(){ if(this.firebug) this.debug = false; dbug.log = function(){}; }
}
if (typeof console != "undefined") { // safari, firebug
	if (typeof console.debug != "undefined") { // firebug
		dbug.firebug = true; if(window.location.href.indexOf("debug=true")>0) dbug.enable();
	}
}


function show_map_and_markers(map_name, ids)
{
	if (!maps)
	{
		maps = Object();
	}
	
	var map = maps[map_name];
	if (map && !map.closed)
	{
		dbug.log("open2", map_name, map, maps);
		
		map = maps[map_name];
		
		if (!map.to_open)
		{
			dbug.log("open missing");
			map.to_open = ids;
		}
		else if (map.to_open.length < 1)
		{
			dbug.log("open show_markers", maps);
			map.show_markers(ids);
		}
		else
		{
			dbug.log("open concat", map.to_open, ids, maps);
			map.to_open.concat(ids);
		}
		map.focus();
	}
	else
	{
		dbug.log("not open", map_name, maps);
		map = window.open("/maps/" + blog_name + "/" + map_name);
		map.to_open = ids;
		map.maps = maps;
		maps[map_name] = map;
	}
}

$(document).ready(
	function()
	{
		var f = function () {
			var map = $(this).find("span.map").attr("title");
			dbug.log("map", map);
			var ids = [];
			$(this).find("span.entity").each(function() { ids.push($(this).attr("title")); });
			dbug.log("ids", ids);
			show_map_and_markers(map, ids);
			return false;
		};
		
		$("a.show_map_and_markers").each(function(index, element) { element.onclick = f; });
	}
);
