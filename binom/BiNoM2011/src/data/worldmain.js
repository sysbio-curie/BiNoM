function latlng_marker(map, lat2, lng2)
{
	var pos = new google.maps.LatLng(lat2, lng2);
	var marker = new google.maps.Marker({
	    position: pos,
	    map: map,
	    title: "" + pos
	});
	var infowindow = new google.maps.InfoWindow({
		content: " at " + pos
	});
	google.maps.event.addListener(marker, 'click', function() {
		infowindow.open(map, marker);
	});
	return pos;
}

function make_marker(map, x, y, projection)
{
	var point = new google.maps.Point(x, y);
	var pos = projection.fromPointToLatLng(point);
	var marker = new google.maps.Marker({
	    position: pos,
	    map: map,
	    title: "" + point + " " + pos
	});
	var infowindow = new google.maps.InfoWindow({
		content: projection.projection_name + " " + point + " " + pos
	});
	google.maps.event.addListener(marker, 'click', function() {
		infowindow.open(map, marker);
	});
	return pos;
}

//see http://forevermore.net/articles/photo-zoom/
//Setup the new type of projection
function EuclideanProjection() {
	var EUCLIDEAN_RANGE = 256;
	this.pixelOrigin_ = new google.maps.Point(EUCLIDEAN_RANGE / 2, EUCLIDEAN_RANGE / 2);
	this.pixelsPerLonDegree_ = EUCLIDEAN_RANGE / 360;
	this.pixelsPerLonRadian_ = EUCLIDEAN_RANGE / (2 * Math.PI);
	this.scaleLat = 2;	// Height
	this.scaleLng = 1;	// Width
	this.offsetLat = 0;	// Height
	this.offsetLng = 0;	// Width
};
 
EuclideanProjection.prototype.fromLatLngToPoint = function(latLng, opt_point) {
	//var me = this;
	
	var point = opt_point || new google.maps.Point(0, 0);
	
	var origin = this.pixelOrigin_;
	point.x = (origin.x + (latLng.lng() + this.offsetLng ) * this.scaleLng * this.pixelsPerLonDegree_);
	// NOTE(appleton): Truncating to 0.9999 effectively limits latitude to
	// 89.189.  This is about a third of a tile past the edge of the world tile.
	point.y = (origin.y + (-1 * latLng.lat() + this.offsetLat ) * this.scaleLat * this.pixelsPerLonDegree_);
	return point;
};
 
EuclideanProjection.prototype.fromPointToLatLng = function(point) {
	var me = this;
	
	var origin = me.pixelOrigin_;
	var lng = (((point.x - origin.x) / me.pixelsPerLonDegree_) / this.scaleLng) - this.offsetLng;
	var lat = ((-1 *( point.y - origin.y) / me.pixelsPerLonDegree_) / this.scaleLat) - this.offsetLat;
	return new google.maps.LatLng(lat , lng, true);
};

EuclideanProjection.prototype.projection_name = "Euclidean";

function osm(min_zoom, max_zoom, width, height, Euclidean)
{
	var lat_ = 90;
	var lng_ = 180;
	lat_ = 50;
	lng_ = 50;
	
	function ClickMapProjection()
	{
		// http://code.google.com/apis/maps/documentation/javascript/examples/map-projection-simple.html
		// see http://forevermore.net/articles/photo-zoom/
	};
	
	ClickMapProjection.prototype.fromPointToLatLng = function(point, noWrap) {
		var y = point.y;
		var x = point.x;
		var lng = -x / width * (2 * lng_) + lng_;
		var lat = y / height * (2 * lat_) - lat_;
		var r = new google.maps.LatLng(lat, lng, noWrap);
		return r;
	};
	ClickMapProjection.prototype.fromLatLngToPoint = function(latLng)
	{
		var x = -(latLng.lng() - lng_) / (2 * lng_) * width;
		var y = (latLng.lat() + lat_) / (2 * lat_) * height;
		var r = new google.maps.Point(x, y);
		return r;
	}
	ClickMapProjection.prototype.projection_name = "Clickmap";
	
	var id = "foo";

	var element = document.getElementById("map");

	var map = new google.maps.Map(element, {
		center : new google.maps.LatLng(0, 0),
		disableDefaultUI: true,
		zoomControl: true,
		overviewMapControl: false,
		zoom : 0,
		mapTypeId : id
	});
	
	var map_type = new google.maps.ImageMapType({
		getTileUrl: function(coord, zoom) {
			var ntiles = 1 << zoom;
			if (coord.y < 0 || coord.y >= ntiles)
				return null;
			if (coord.x < 0 || coord.x >= ntiles)
				return null;
			return zoom + "/" + coord.x + "_" + coord.y + ".png";
		},
		tileSize : new google.maps.Size(width, height),
		maxZoom : max_zoom,
		minZoom : min_zoom
	});
	
	if (Euclidean)
		map_type.projection = new EuclideanProjection();
	else
		map_type.projection = new ClickMapProjection();
	map.mapTypes.set(id, map_type);
	    
	var zero = make_marker(map, 0, 0, map_type.projection);
	var lr = make_marker(map, width, height, map_type.projection);
	make_marker(map, 0, 100, map_type.projection);
	make_marker(map, 100, 0, map_type.projection);
	make_marker(map, 100, height + 35, map_type.projection);
	make_marker(map, 70, 256 + 55, map_type.projection);

	if (width > 256)
	{
		var gap = 24;
		var y = -gap + 1;
		var x;
		for (x = 247; x < 271; x += 3)
			make_marker(map, x, y += gap, map_type.projection);
		make_marker(map, 320, 40, map_type.projection);
	}
	
	if (width > 230)
		make_marker(map, 230, 40, map_type.projection);
	make_marker(map, 100, 100, map_type.projection);

	return map;
}
