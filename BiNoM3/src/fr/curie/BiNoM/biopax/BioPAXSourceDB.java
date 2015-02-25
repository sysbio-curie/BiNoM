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

package fr.curie.BiNoM.biopax;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class BioPAXSourceDB {
    private static BioPAXSourceDB instance;

    private HashMap<Object, BioPAX> network_bw = new java.util.HashMap();

    public static BioPAXSourceDB getInstance() {
	if (instance == null) {
	    instance = new BioPAXSourceDB();
	}

	return instance;
    }

    private BioPAXSourceDB() {
    }

    public boolean isBioPAXNetwork(Object network) {
	return network_bw.get(network) != null;
    }

    public BioPAX getBioPAX(Object network) {
    	return (BioPAX)network_bw.get(network);
    }

    public void setBioPAX(Object network, BioPAX biopax) {
	network_bw.put(network, biopax);
    }

    public Vector<Object> getAssociatedNetworks(BioPAX biopax) {
	Vector<Object> v = new Vector();
	Iterator iter = network_bw.entrySet().iterator();

	while (iter.hasNext()) {
	    Map.Entry entry = (Map.Entry)iter.next();
	    if (biopax == entry.getValue()) {
		v.add((Object)entry.getKey());
	    }
	}

	return v;
    }

    private HashMap<Object, String> base_uri_map = new HashMap();

    public void setURI(Object base, String uri) {
	base_uri_map.put(base, uri);
    }

    public String getURI(Object base) {
	return base_uri_map.get(base);
    }
}
    
