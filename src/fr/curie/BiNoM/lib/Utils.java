/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.lib;

import java.util.Properties;
import java.util.Enumeration;

public class Utils {

    private static int javaRelease = 0;

    public static int getJavaRelease() {
	if (javaRelease == 0) {
	    Properties sysprops = System .getProperties();
	    for (Enumeration e = sysprops.propertyNames(); e.hasMoreElements(); ) {
		String key = (String)e.nextElement();
		if (key.equals("java.version")) {
		    String value = sysprops.getProperty(key);
		    String v[] = value.split("\\.");
		    javaRelease = v.length > 1 ? Integer.parseInt(v[1]) : 0;
		    break;
		}
	    }
	}

	return javaRelease;
    }

    private static final int VERSION5 = 5;
    private static final int VERSION5_MAX_ROWS = 512;

    public static int getGridBagLayoutMaxRows() {
	int r = getJavaRelease();
	if (r > VERSION5) {
	    return Integer.MAX_VALUE;
	}
	return VERSION5_MAX_ROWS;
    }
}
