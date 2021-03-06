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
import java.net.URL;
import java.io.InputStream;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import fr.curie.BiNoM.biopax.BioPAXImportDialog;

public class BioPAXImportFromURL extends AbstractCyAction {
	
	String urlStr;
	
	public BioPAXImportFromURL() {
		super("Import BioPAX 3 Document From URL",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}



	@Override
	public void actionPerformed(ActionEvent e) {		
		// TODO Auto-generated constructor stub
		urlStr =  JOptionPane.showInputDialog
	    (Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
	     "URL for BioPAX Document: ");

        if (urlStr == null || urlStr.trim().length() == 0)
	    return;
        
        try {
		    URL url = new URL(urlStr);
		    InputStream is = url.openStream();
		    BioPAXImportDialog.getInstance().raise(url, urlStr);
		    is.close();
		}
		catch(Exception ee) {
		    JOptionPane.showMessageDialog
			(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "Cannot open URL " + urlStr);
		}
	}
}
