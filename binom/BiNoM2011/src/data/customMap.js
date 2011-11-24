/**
    *    cellpublisher/customMap.js: a module to create a custom made Google map type
    *
    *		This module creates a global function cellpublisher.createCustomMap(settings).
    *	 	The function returns a GMap2 object, that contains the Google map with the custom
    *		made map as the only active map type, with mouse wheel scrolling enabled, and with
    *		a little overview window.
    *	 
    *		This module requires a "tiles" directory, where the tiles for each zoom level are stored.
    *		Each tile should have the format {Z}_{X}_{Y}.png, where {Z} is the zoom level, {x} is the
    *		"x" tile coordinate and {Y} the "y" tile coordinate.
    *
**/

/**
  *    Copyright (C) 2009 Lope A. Florez, Christoph Lammers
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
  */

// Create a namespace for the function 
var cellpublisher;
if (!cellpublisher) cellpublisher = {};
cellpublisher.customMap = {};

/**
    *	Returns a GMap2 object that contains only the customGoogleMap and navigation controls.
    *	
    *	The input to the function is the settings object that should contain the following parameters:
    *		-	copyright_owner: A string with the owner of the images that will be displayed
    *		- 	maxZoom: The maximum zoom level that is available
    *		- 	div: name of the <div> where the map will reside	 
    *
    *	If any of the parameters are missing, the function throws an error.
    * 		
**/  
cellpublisher.customMap.createCustomMap = function(settings) {  
    
	
	if (GBrowserIsCompatible()) { // If the browser is compatible with Google Maps...
        
		
		/***************************************
		*
		*	Check if all the parameters are present
		*
		****************************************/
		if (!settings) {
			throw new Error("The createCustomMap needs a settings object...");
		}
		
		with(settings) {
			if (!copyright_owner || !minZoom || !maxZoom || !div) 
				throw new Error("The createCustomMap function misses some settings...");
		}
		
		/***************************************
		*
		*        STEP 1: Configure and create the 
		*                      custom map type.
		*
		*         Every custom layer has a copyright 
		*         notice for its images, tiles of images
		*         for each zoom level, and a projection.
		*
                       ***************************************/
        
        // Set the boundaries of the copyright notice and the text concerning the copyright owner of the images
        var copyright = new GCopyright(1,   // id of the copyright
                                       new GLatLngBounds(   //Area of the map covered by this copyright notice
                                            new GLatLng(-69.28725695167886,-178.59375),
                                            new GLatLng(84.67351256610525,83.671875)
                                            ),
                                       0,   // lowest zoom level where it is visible
                                       settings.copyright_owner);  // text of the copyright (parameter of the function)
                                       
        
        var copyrightCollection = new GCopyrightCollection('Pathway Data:');
		copyrightCollection.addCopyright(copyright);
           
        // Create the tile layers based on the images in the "tiles" folder
        var tileLayers = [new GTileLayer(copyrightCollection,settings.minZoom,settings.maxZoom,
                            {
                            tileUrlTemplate: 'tiles/{Z}_{X}_{Y}.png', // name the images by this convention
                            isPng:true,
                            opacity:1.0
                            })];
        // Create the projection
        var projection = new GMercatorProjection(18);	

		// Don't repeat the world in the x direction
/*
		projection.tileBounds = [];
		var c = 1;
		
		for(var d = 0; d <= 17; d++) {
			projection.tileBounds.push(c);
			c *= 2;
		}
		
		projection.tileCheckRange = function(a,b,c){ 
					       var d=this.tileBounds[b]; 
					       if (a.y<0||a.y>=d) { 
							return false; 
					       } 
					       
					       if(a.x<0||a.x>=d){ 
							return false; 
					       } 
					       return true 
					   }
*/
        
        var customMapType = new GMapType(tileLayers, projection, "Pathway");
        
		/***************************************
		   *
		   *        STEP 2: Create a  Google Map,
		   *                       add the new map type to it, 
		   *                       remove the typical types 
		   *                       (e.g. Satellite), and activate 
		   *                       the navigation controls.
		   *
		   ***************************************/   
        
        var map = new GMap2(document.getElementById(settings.div));

        map.addMapType(customMapType);
        
        map.removeMapType(G_NORMAL_MAP);
        map.removeMapType(G_SATELLITE_MAP);
        map.removeMapType(G_HYBRID_MAP);

        map.enableScrollWheelZoom();	 
        map.addControl(new GLargeMapControl());
        map.addControl(new GOverviewMapControl(new GSize(200,200)));
        
		/***************************************
		   *
		   *        STEP 3: Set the center of the map,
		   *                       and initialize it.
		   *
		   ***************************************/
      
        var center = new GLatLng(0,0);
        map.setCenter(center, 2, customMapType);
       
		return map;
    }
    else {
        alert("Sorry, the Google Maps API is not compatible with this browser");
    }
}

