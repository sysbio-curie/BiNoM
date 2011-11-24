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
package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import edu.rpi.cs.xgmml.GraphDocument;

public class ConnectedComponents implements ActionListener {

    public void actionPerformed(ActionEvent e) {

	GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument
	    (Cytoscape.getCurrentNetwork());

	CyNetworkView view = Cytoscape.getCurrentNetworkView();
	ConnectedComponentsTask task = new ConnectedComponentsTask
	    (graphDocument,
	     Cytoscape.getCurrentNetworkView().getVisualStyle());
	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
    }
}
