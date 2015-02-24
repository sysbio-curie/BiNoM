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
package fr.curie.BiNoM.cytoscape.biopax;

import Main.Launcher;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;

public class BioPAXSyncNetworks extends AbstractCyAction {
	
	public BioPAXSyncNetworks(){
		super("Synchronize networks with BioPAX 3...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM BioPAX 3 Utils");
	}

    public void actionPerformed(ActionEvent e) {

	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
	if (biopax == null) {
	    JOptionPane.showMessageDialog(null,/*Cytoscape.getDesktop(),*/
					  "Error: network is not associated with any BioPAX source file.");
	    return;
	}

	BioPAXSyncNetworksDialog.getInstance().raise(biopax);
    }
}