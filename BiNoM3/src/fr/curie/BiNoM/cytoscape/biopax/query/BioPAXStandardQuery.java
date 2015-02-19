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
import Main.Launcher;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import java.awt.event.ActionEvent;

import fr.curie.BiNoM.pathways.utils.BioPAXGraphQueryEngine;

public class BioPAXStandardQuery extends AbstractCyAction {

    public BioPAXStandardQuery() {
    	super("Standard Query",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM BioPAX 3 Query");
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("BioPAXStandardQuery");
    	BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
    	if(beng==null){
    		JOptionPane.showMessageDialog(null, /*Cytoscape.getDesktop()*/ "No query can be performed. Load BioPAX index first.");
    	}else{
    		if(Launcher.getAdapter().getCyNetworkManager().getNetworkSet().size()==0)
    			JOptionPane.showMessageDialog(null, /*Cytoscape.getDesktop()*/ "No query can be performed. Select some entities first.");
    		else{
    			//System.out.println("Number of networks = "+Cytoscape.getNetworkSet().size());
    			BioPAXStandardQueryDialog.getInstance().raise(null);
    		}
    	}
    }
}

