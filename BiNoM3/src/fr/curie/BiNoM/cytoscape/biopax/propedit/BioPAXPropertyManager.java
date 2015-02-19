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

package fr.curie.BiNoM.cytoscape.biopax.propedit;

import fr.curie.BiNoM.biopax.propedit.*;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.wrappers.*;

import java.util.*;

import javax.swing.JOptionPane;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import Main.Launcher;

public class BioPAXPropertyManager {

	BioPAXPropertyManager() {
	}

	Vector getSelectedBioPAXObjects() {

		CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();


		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(network);
		if (biopax == null) {
			JOptionPane.showMessageDialog
			(null, /*Cytoscape.getDesktop(),*/
					"Warning: network is not associated with any BioPAX source file. You must first associate a BioPAX source to this network");
			return null;
		}

		Vector bobj_v = new Vector();
		
		for (Iterator i = CyTableUtil.getNodesInState(network,"selected",true).iterator(); i.hasNext(); ) {
			CyNode node = (CyNode) i.next();
			
			String uri_arr[] = getURIs(node);
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

		for (Iterator i = CyTableUtil.getEdgesInState(network,"selected",true).iterator(); i.hasNext(); ) {

			CyEdge edge = (CyEdge) i.next();
			String uri_arr[] = getURIs(edge);
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

	public static String[] getURIs(CyNode node) {
		//CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
		
		 Iterator<CyColumn> columnsIterator = Launcher.findNetworkofNode(node.getSUID()).getDefaultNodeTable().getColumns().iterator();
     	
		   
		    	
    	String uri = Launcher.findNetworkofNode(node.getSUID()).getRow(node).get("BIOPAX_URI", String.class); 	
		//String uri = nodeAttrs.getStringAttribute(node.getIdentifier(), "BIOPAX_URI");
		if (uri == null || uri.length() == 0) {
			return new String[0];
		}

		return uri.split(NetworkFactory.ATTR_SEP);
	}

	public static String[] getURIs(CyEdge edge) {
		//CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
		String uri = Launcher.findNetworkofNode(edge.getSource().getSUID()).getRow(edge).get("BIOPAX_URI", String.class); 	
		//String uri = edgeAttrs.getStringAttribute(edge.getIdentifier(), "BIOPAX_URI");
		if (uri == null || uri.length() == 0) {
			return new String[0];
		}

		return uri.split(NetworkFactory.ATTR_SEP);
	}
}
