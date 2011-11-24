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
function clickmap_start(map_name, min_zoom) {
	var settingsCustomMap = {
	        copyright_owner: 'Institut Curie',
	        minZoom : min_zoom,
	        maxZoom: clickmap_maxzoom,
	        div: 'map'
	};
	var customMap = cellpublisher.customMap.createCustomMap(settingsCustomMap);
	cellpublisher.map = customMap;
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
