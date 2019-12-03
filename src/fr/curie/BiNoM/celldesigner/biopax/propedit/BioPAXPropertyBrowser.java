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
package fr.curie.BiNoM.celldesigner.biopax.propedit;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.biopax.propedit.*;

import fr.curie.BiNoM.pathways.wrappers.*;

import java.awt.event.ActionEvent;
import java.util.*;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginModel;

public class BioPAXPropertyBrowser extends BioPAXPropertyManager {

    public BioPAXPropertyBrowser(CellDesignerPlugin plugin) {
	super(plugin);
    }

    public void myActionPerformed(ActionEvent e) {
	PluginModel model = plugin.getSelectedModel();

	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(model.getName());
	BioPAXClassDescFactory.getInstance(biopax).makeClassesDesc(biopax);

	Vector bobj_v = getSelectedBioPAXObjects();
	
	if (bobj_v == null) {
	    return;
	}

	BioPAXPropertyBrowserFrame browserFrame = BioPAXPropertyBrowserFrame.getEditInstance();

	int size = bobj_v.size();
	for (int n = 0; n < size; n++) {
	    browserFrame.addTabbedPane((BioPAXObject)bobj_v.get(n), biopax, false);
	}

	browserFrame.epilogue();
	browserFrame.setVisible(true);
    }
}
