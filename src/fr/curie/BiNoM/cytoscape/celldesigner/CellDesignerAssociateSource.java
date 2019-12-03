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
package fr.curie.BiNoM.cytoscape.celldesigner;

import Main.Launcher;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;

import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;

public class CellDesignerAssociateSource extends AbstractCyAction {

    private static CellDesignerAssociateSource instance;

    public static CellDesignerAssociateSource getInstance() {
	if (instance == null)
	    instance = new CellDesignerAssociateSource();
	return instance;
    }

    private CellDesignerAssociateSource() {
    	super("Associate CellDesigner Source...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
    }

    public void actionPerformed(ActionEvent e) {
	perform();
    }

    public boolean perform() {
    	File file = null;
    	JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new XMLFileFilter());
	
		fileChooser.setDialogTitle("Load CellDesigner File");
	
		int returnVal = fileChooser.showSaveDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fileChooser.getSelectedFile();

	        if (file == null)
	        	return false;
	        
	        TaskIterator t = new TaskIterator(new CellDesignerAssociateSourceTask(file, file.getPath()));
			Launcher.getAdapter().getTaskManager().execute(t);
		}
		else
			return false;
		
		return true;
    }
}
