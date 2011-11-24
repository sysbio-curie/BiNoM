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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

public class CellDesignerExportToFile implements ActionListener {

    public void actionPerformed(ActionEvent e) {
	CyNetwork network = Cytoscape.getCurrentNetwork();
	if (!CellDesignerSourceDB.getInstance().isCellDesignerNetwork(network)) {
	    int r = JOptionPane.showConfirmDialog
		(Cytoscape.getDesktop(),
		 "Warning: network is not associated with any CellDesigner source file. You must first associate a CellDesigner source to this network");

	    if (r == JOptionPane.CANCEL_OPTION ||
		r == JOptionPane.NO_OPTION)
		return;

	    if (!CellDesignerAssociateSource.getInstance().perform())
		return;
	}


        CyFileFilter celldesignerFilter = new CyFileFilter();

        celldesignerFilter.addExtension("xml");
        celldesignerFilter.setDescription("CellDesigner files");

	for (;;) {
	    File file = FileUtil.getFile
		("Save CellDesigner File", FileUtil.SAVE, new CyFileFilter[]{celldesignerFilter});

	    if (file == null)
		break;

	    if (file != null) {
	    	CellDesignerExportToFileDialog.getInstance().raise(file);
	    	break;
	    }
        }
    }
}
