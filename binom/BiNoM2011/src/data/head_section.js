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
var maps = Object();

function open_map2(blog_name, name)
{
	maps[name] = window.open("/maps/" + blog_name + "/" + name, "map_" + name);
	return maps[name];
}

function is_map_open(map_name)
{
	return maps[map_name] && !maps[map_name].closed;
}

function show_map_and_markers(blog_name, map_name, ids)
{
	var f = function()
	{
		maps[map_name].cellpublisher.marker_links._show_markers(ids);
	}
	if (is_map_open(map_name))
	{
		f();
	}
	else
	{
		maps[map_name] = open_map2(blog_name, map_name);
		maps[map_name].addEventListener('load', f, true);
	}
}
