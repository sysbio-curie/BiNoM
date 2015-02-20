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

import java.awt.event.ActionEvent;

import Main.Launcher;

import org.cytoscape.application.swing.AbstractCyAction;

import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import edu.rpi.cs.xgmml.GraphDocument;

public class ConnectedComponents extends AbstractCyAction{
	
	public ConnectedComponents(){
		super("Get Connected Components",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".Analysis[2]");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		ConnectedComponentsTask task = new ConnectedComponentsTask(graphDocument, Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle());
		fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
	}
}
