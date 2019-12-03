///*
//   BiNoM Cytoscape Plugin
//   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE
//
//   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
//   modify it under the terms of the GNU Lesser General Public
//   License as published by the Free Software Foundation; either
//   version 2.1 of the License, or (at your option) any later version.
//
//   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//   Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public
//   License along with this library; if not, write to the Free Software
//   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
//*/
//
///*
//  BiNoM authors:
//	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
//	Eric Viara : http://www.sysra.com/viara
//	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
//*/
//package fr.curie.BiNoM.cytoscape.lib;
//
//import cytoscape.task.Task;
//import cytoscape.task.ui.JTaskConfig;
//import cytoscape.Cytoscape;
//
//public class TaskManager {
//
//    public static void executeTask(Task task) {
//        //  Configure JTask
//        JTaskConfig config = new JTaskConfig();
//        config.setAutoDispose(false);
//        config.displayStatus(true);
//        config.displayTimeElapsed(true);
//        config.displayCloseButton(true);
//        //config.setOwner(Cytoscape.getDesktop());
//
//        //  Execute Task via TaskManager
//        //  This automatically pops-open a JTask Dialog Box.
//        //  This method will block until the JTask Dialog Box
//        //  is disposed.
//        System.out.println("Executing task");
//        
//        boolean success = cytoscape.task.util.TaskManager.executeTask(task, config);
//    }
//}
