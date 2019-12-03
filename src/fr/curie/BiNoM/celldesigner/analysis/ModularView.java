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

package fr.curie.BiNoM.celldesigner.analysis;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;

import fr.curie.BiNoM.celldesigner.lib.NetworkUtils;

import fr.curie.BiNoM.analysis.ModularViewDialog;

import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginAction;
import jp.sbi.celldesigner.plugin.CellDesignerPlugin;

public class ModularView extends PluginAction {

    private CellDesignerPlugin plugin;

    private static final String EMPTY_NAME = "                       ";

    public ModularView(CellDesignerPlugin plugin) {
	this.plugin = plugin;
    }

    public void myActionPerformed(ActionEvent e) {
	PluginModel model = plugin.getSelectedModel();

	ModularViewDialog.getInstance().raise(new ModularViewTaskFactory(model, plugin), NetworkUtils.getNetworkNames(plugin, EMPTY_NAME));
    }
}
