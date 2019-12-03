package fr.curie.BiNoM.cytoscape.utils;
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

import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.analysis.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

import fr.curie.BiNoM.analysis.ClusterNetworksDialog;

public class MergeNetworksAndFilter implements ActionListener {

 private static final String EMPTY_NAME = "                       ";

 public MergeNetworksAndFilter() {
 }

 public void actionPerformed(ActionEvent e) {
	Set netwSet = Cytoscape.getNetworkSet();
	if (netwSet.size() < 2) {
	    // should raise an information dialog
	    return;
	}
	ClusterNetworksDialog dlg = ClusterNetworksDialog.getInstance(new MergeNetworksAndFilterTaskFactory());
	dlg.title.setText("Merge Networks and Filter");
	dlg.raise(NetworkUtils.getNetworkNames(EMPTY_NAME));
 }
}
