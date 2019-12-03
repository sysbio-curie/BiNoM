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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;

public class BioPAXExportToFile implements ActionListener {

    public void actionPerformed(ActionEvent e) {
    	
	CyNetwork network = Cytoscape.getCurrentNetwork();
	
	if (!BioPAXSourceDB.getInstance().isBioPAXNetwork(network) &&
	    !CellDesignerSourceDB.getInstance().isCellDesignerNetwork(network)) {
	    int r = JOptionPane.showConfirmDialog
		(Cytoscape.getDesktop(),
		 "Warning: network is not associated with any BioPAX source file. You must first associate a BioPAX source to this network");

	    if (r == JOptionPane.CANCEL_OPTION ||
		r == JOptionPane.NO_OPTION)
		return;
	    
	    if (!BioPAXAssociateSource.getInstance().perform())
		return;
	}
        CyFileFilter bioPaxFilter = new CyFileFilter();

        bioPaxFilter.addExtension("owl");
        bioPaxFilter.setDescription("BioPAX files");

	for (;;) {
	    File file = FileUtil.getFile
		("Save BioPAX File", FileUtil.SAVE, new CyFileFilter[]{bioPaxFilter});

	    if (file == null)
		break;
	    
	    if (file != null) {
		String path = file.getAbsolutePath();

		if (path.lastIndexOf(".owl") < 0)
		    file = new File(path + ".owl");

		boolean merge = false;

		if (file.exists()) {
		    String options[] = new String[]{"Merge", "Overwrite", "Cancel"};
		    int r = JOptionPane.showOptionDialog
			(Cytoscape.getDesktop(), "File " + file.getAbsolutePath() +
			 " already exists. Do you want to merge or overwrite information in it?", "Confirmation Dialog", JOptionPane.YES_NO_CANCEL_OPTION,
			 JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		    if (r == JOptionPane.CANCEL_OPTION)
			return;

		    if (r == JOptionPane.YES_OPTION)
			merge = true;
		}
		
		if (!merge) {
			System.out.println("Rewriting "+file.getAbsolutePath()+"...");
		    try {
			FileOutputStream os = new FileOutputStream(file);
			os.close();
		    }
		    catch(Exception ee) {
			JOptionPane.showMessageDialog
			    (Cytoscape.getDesktop(),
			     "Cannot open file " + file.getAbsolutePath() + " for writing");
			break;
		    }
		}

		BioPAXExportTask task = new BioPAXExportTask(file, merge);
		fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
		break;
	    }
        }
    }
}
