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
package fr.curie.BiNoM.cytoscape.sbml;

import Main.Launcher;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.cytoscape.application.swing.AbstractCyAction;
import fr.curie.BiNoM.cytoscape.celldesigner.XMLFileFilter;

public class SBMLExportToFile extends AbstractCyAction {
	
	public SBMLExportToFile(){
		super("Export Current Network to SBML...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}

    public void actionPerformed(ActionEvent e) {
    	
    	File file;
//        CyFileFilter bioPaxFilter = new CyFileFilter();
//
//        bioPaxFilter.addExtension("xml");
//        bioPaxFilter.setDescription("SBML files");

        JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new XMLFileFilter());
	
		fileChooser.setDialogTitle("Save SBML File");
	
		JFrame frame = new JFrame();
		
		int userSelection = fileChooser.showSaveDialog(frame);
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			
			if(!file.getAbsolutePath().endsWith(".xml"))
				file = new File(file.getAbsolutePath() + ".xml");
			
			System.out.println("Save as file: " + file.getAbsolutePath());
		}
		else
			file = null;
    	

		if (file.exists()) {
		    int r = JOptionPane.showConfirmDialog
			(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "File " + file.getAbsolutePath() +
			 " already exists. Do you want to overwrite it?");
		    if (r == JOptionPane.CANCEL_OPTION)
			return;
		}

		if(file != null){
			try {
				FileOutputStream os = new FileOutputStream(file);
			    os.close();
			    SBMLExportTask task = new SBMLExportTask(file);
			    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
			}
			catch(Exception ee) {
			    JOptionPane.showMessageDialog
				(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
				 "Cannot open file " + file.getAbsolutePath() + " for writing");
			}
		}
    }
}
