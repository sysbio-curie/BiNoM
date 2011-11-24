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

package fr.curie.BiNoM.celldesigner.biopax.propedit;

import fr.curie.BiNoM.biopax.propedit.*;

import fr.curie.BiNoM.celldesigner.lib.NetworkFactory;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;

import java.io.*;

import edu.rpi.cs.xgmml.*;
import java.io.InputStream;

import java.io.File;
import java.net.URL;
import java.lang.reflect.*;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Dimension;
import com.hp.hpl.jena.rdf.model.*;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginAction;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;

abstract public class BioPAXPropertyManager extends PluginAction {

    protected CellDesignerPlugin plugin;

    BioPAXPropertyManager(CellDesignerPlugin plugin) {
	this.plugin = plugin;
    }

    Vector getSelectedBioPAXObjects() {

	PluginModel model = plugin.getSelectedModel();

	BioPAXSourceDB bioPAXSourceDB = BioPAXSourceDB.getInstance();
	BioPAX biopax = bioPAXSourceDB.getBioPAX(model.getName());

	if (biopax == null) {
	    JOptionPane.showMessageDialog
		(new java.awt.Frame(),
		 "Warning: network is not associated with any BioPAX source file. You must first associate a BioPAX source to this network");
	    return null;
	}


	Vector bobj_v = new Vector();
	PluginListOf list = plugin.getSelectedAllNode();
	int size = list.size();
	for (int m = 0; m < size; m++) {
	    PluginSBase sbase = list.get(m);
	    String name = null;
	    if (sbase instanceof PluginSpeciesAlias) {
		name = ((PluginSpeciesAlias)sbase).getName();
	    }
	    // must continue
	    /*
	    else if (sbase instanceof ..) {
	    }
	    */

	    if (name == null) {
		continue;
	    }

	    String uri = bioPAXSourceDB.getURI(name);
	    if (uri == null) {
		continue;
	    }

	    String uri_arr[] = uri.split(NetworkFactory.ATTR_SEP);
	    for (int n = 0; n < uri_arr.length; n++) {
		BioPAXObject bobj = BioPAXObjectFactory.getInstance().getObject(uri_arr[n], biopax);
		if (bobj != null) {
		    bobj_v.add(bobj);
		}
		else {
		    System.out.println("ERROR: " + uri_arr[n]);
		}
	    }
	}

	return bobj_v;
    }
}
