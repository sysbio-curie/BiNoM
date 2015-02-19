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
import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;

import fr.curie.BiNoM.biopax.BioPAXImportDialog;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportTaskFactory;
import fr.curie.BiNoM.cytoscape.biopax.OWLFileFilter;

public class CSMLImportFromFile extends AbstractCyAction {
	
	public CSMLImportFromFile(){
		super("Import CSML Document From File",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM I/O");
	}

    public void actionPerformed(ActionEvent e) {
    	File file = null;
    	JFileChooser fileChooser = new JFileChooser();
		
		//fileChooser.setFileFilter(new XMLFileFilter());
	
		fileChooser.setDialogTitle("Load CSML File");
	
		JFrame frame = new JFrame();
		int returnVal = fileChooser.showOpenDialog(frame);
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fileChooser.getSelectedFile();	   
	    }

        if (file != null) {
            CSMLImportTask task = new CSMLImportTask(file);
            fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
        }
    }
}
