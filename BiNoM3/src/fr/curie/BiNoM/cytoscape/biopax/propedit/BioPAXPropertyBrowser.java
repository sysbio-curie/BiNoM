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

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.biopax.propedit.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import java.awt.event.ActionEvent;
import java.util.*;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import Main.Launcher;

public class BioPAXPropertyBrowser extends AbstractCyAction {
	
	public BioPAXPropertyBrowser(){
		super("BioPAX 3 Property Editor...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM BioPAX 3 Utils[4]");
	}

    public void actionPerformed(ActionEvent e) {
		CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(network);
		BioPAXClassDescFactory.getInstance(biopax).makeClassesDesc(biopax);
	
		BioPAXPropertyManager b = new BioPAXPropertyManager();
		Vector bobj_v = b.getSelectedBioPAXObjects();
		
		if (bobj_v == null) {
		    return;
		}
	
		BioPAXPropertyBrowserFrame browserFrame = BioPAXPropertyBrowserFrame.getEditInstance();
	
		for (int n = 0; n < bobj_v.size(); n++) {
		    browserFrame.addTabbedPane((BioPAXObject)bobj_v.get(n), biopax, false);
		}
	
		browserFrame.epilogue();
		browserFrame.setVisible(true);
    }
}
