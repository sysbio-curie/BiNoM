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
package fr.curie.BiNoM.celldesigner.lib;

import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.*;

public class NetworkUtils {

    public static String [] getNetworkNames(jp.sbi.celldesigner.plugin.CellDesignerPlugin plugin) {
	return getNetworkNames(plugin, "");
    }

    public static String [] getNetworkNames(jp.sbi.celldesigner.plugin.CellDesignerPlugin plugin, String suffix) {
	try {
	System.out.println("getNetworkNames " + plugin);
	    PluginListOf netwSet = plugin.getAllModels();

	System.out.println("netwSet : " + netwSet);
	System.out.println("netwSet.size() : " + netwSet.size());

	String netwNames[] = new String[netwSet.size()];
	for (int n = 0; n < netwSet.size(); n++) {
	    PluginModel netw = (PluginModel)netwSet.get(n);
	    System.out.println("name: " + netw.getName() + " " + netw.getId());
	    netwNames[n] = netw.getId() + suffix;
	}	
	return netwNames;

	}
	catch(Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
    public static java.awt.Dimension getCurrentModelSize(){
    	return MainWindow.getLastInstance().getCurrentModel().getModelSize();
    }
    
    public static void setCurrentModelSize(int width, int height){
    	 MainWindow.getLastInstance().getCurrentModel().getModelSize().setSize(width, height);
    }
    

}

