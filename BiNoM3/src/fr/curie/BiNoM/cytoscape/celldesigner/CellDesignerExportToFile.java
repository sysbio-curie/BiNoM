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
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;

import fr.curie.BiNoM.cytoscape.biopax.OWLFileFilter;


public class CellDesignerExportToFile extends AbstractCyAction {
	
	public CellDesignerExportToFile(){
		super("Export Current Network to CellDesigner...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM I/O");
	}

    public void actionPerformed(ActionEvent e) {
	CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	if (!CellDesignerSourceDB.getInstance().isCellDesignerNetwork(network)) {
	    int r = JOptionPane.showConfirmDialog
		(null, /*Cytoscape.getDesktop(),*/
		 "Warning: network is not associated with any CellDesigner source file. You must first associate a CellDesigner source to this network");

	    if (r == JOptionPane.CANCEL_OPTION ||
		r == JOptionPane.NO_OPTION)
		return;

	    if (!CellDesignerAssociateSource.getInstance().perform())
		return;
	}

	File file;
	JFileChooser fileChooser = new JFileChooser();
	
	fileChooser.setDialogTitle("Save CellDesigner File");

	JFrame frame = new JFrame();
	
	int userSelection = fileChooser.showSaveDialog(frame);
	
	if (userSelection == JFileChooser.APPROVE_OPTION) {
		file = fileChooser.getSelectedFile();
		System.out.println("Save as file: " + file.getAbsolutePath());
	}
	else
		file = null;

	    if (file != null) {
	    	CellDesignerExportToFileDialog.getInstance().raise(file);
	    }
    }
}
