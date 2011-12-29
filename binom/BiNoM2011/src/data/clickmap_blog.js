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

function show_map_and_markers(blog_name, map_name, ids)
{
	if (!maps)
	{
		maps = Object();
	}
	
	var map = maps[map_name];
	if (map && !map.closed)
	{
		log("open2", maps);
		
		map = maps[map_name];
		
		if (!map.to_open)
		{
			log("open missing");
			map.to_open = ids;
		}
		else if (map.to_open.length < 1)
		{
			log("open show_markers", maps);
			map.show_markers(ids);
		}
		else
		{
			log("open concat", maps);
			map.to_open.concat(ids);
		}
		map.focus();
	}
	else
	{
		log("not open", maps);
		map = window.open("/maps/" + blog_name + "/" + map_name);
		map.to_open = ids;
		map.maps = maps;
		maps[map_name] = map;
	}
}
