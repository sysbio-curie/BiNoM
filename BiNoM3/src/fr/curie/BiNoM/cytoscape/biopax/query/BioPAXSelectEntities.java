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
package fr.curie.BiNoM.cytoscape.biopax.query;

import java.awt.event.ActionEvent;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphQueryEngine;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import Main.Launcher;

public class BioPAXSelectEntities extends AbstractCyAction {

    public BioPAXSelectEntities() {
    	super("Select Entities",
        		Launcher.getAdapter().getCyApplicationManager(),
            "none",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM BioPAX 3 Query");
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("BioPAXSelectEntities");
	//BioPAXSelectEntitiesDialog.getInstance().raise(null);
    	BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
    	if(beng==null){
    		JOptionPane.showMessageDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "No query can be performed. Load BioPAX index first.");
    	}else
    	(new BioPAXSelectEntitiesDialog()).raise(null);
    	
//		if(beng.query.result.Nodes.size()==0)
//			JOptionPane.showMessageDialog(Cytoscape.getDesktop(), "No entities was found. It is possible that the database uses different entity names.\nYou can generate the full list of all entities names \nin the Plugins/BiNoM BioPAX query/Display Index Info dialog");

    }
}

