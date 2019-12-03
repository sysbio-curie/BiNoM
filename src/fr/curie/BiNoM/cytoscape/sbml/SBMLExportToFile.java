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

import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

public class SBMLExportToFile implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        CyFileFilter bioPaxFilter = new CyFileFilter();

        bioPaxFilter.addExtension("xml");
        bioPaxFilter.setDescription("SBML files");

	for (;;) {
	    File file = FileUtil.getFile
		("Save SBML File", FileUtil.SAVE, new CyFileFilter[]{bioPaxFilter});

	    if (file != null) {
		String path = file.getAbsolutePath();

		int idx = path.lastIndexOf('.');
		if (idx >= 0)
		    path = path.substring(0, idx);
		
		file = new File(path + ".xml");

		if (file.exists()) {
		    int r = JOptionPane.showConfirmDialog
			(Cytoscape.getDesktop(), "File " + file.getAbsolutePath() +
			 " already exists. Do you want to overwrite it?");
		    if (r == JOptionPane.CANCEL_OPTION)
			return;
		    if (r == JOptionPane.NO_OPTION) {
			continue;
		    }
		}

		try {
		    FileOutputStream os = new FileOutputStream(file);
		    os.close();
		    SBMLExportTask task = new SBMLExportTask(file);
		    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
		}
		catch(Exception ee) {
		    JOptionPane.showMessageDialog
			(Cytoscape.getDesktop(),
			 "Cannot open file " + file.getAbsolutePath() + " for writing");
		}
		break;
	    }
        }
    }
}
