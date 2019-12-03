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
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import fr.curie.BiNoM.biopax.BioPAXImportDialog;
import fr.curie.BiNoM.cytoscape.biopax.OWLFileFilter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class BioPAXImportFromFile implements ActionListener{
		
	File file;


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new OWLFileFilter());
	
		fileChooser.setDialogTitle("Load BioPAX File");
			
		int returnVal = fileChooser.showOpenDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fileChooser.getSelectedFile();
		    try {
			FileInputStream is = new FileInputStream(file);
			is.close();
			
			
			BioPAXImportDialog.getInstance().raise(file, file.getName());
			
		    }
		    catch(Exception ee) {
			JOptionPane.showMessageDialog
			    (Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
			     "Cannot open file " + file.getAbsolutePath() + " for reading");
		    }
	    }
	}
}
