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

import Main.Launcher;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyTableUtil;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.analysis.ClusterNetworksDialog;

public class ExtractSubnetwork extends AbstractCyAction {

    private static final String EMPTY_NAME = "                       ";

    public ExtractSubnetwork() {
    	super("Extract Subnetwork",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".Analysis");
    }

    public void actionPerformed(ActionEvent e) {
        if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
        	if(CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size()>1)
        		ExtractSubnetworkDialog.getInstance().raise();
        	else
        		JOptionPane.showMessageDialog(new javax.swing.JFrame(),"Select several nodes first\n");
    	}
    }
}
