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
//package fr.curie.BiNoM.cytoscape.netwop;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.*;
//
//import org.cytoscape.model.CyNetwork;
//
//import Main.Launcher;
//import cytoscape.view.CyNetworkView;
//import giny.view.NodeView;
//import giny.view.EdgeView;
//import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
//import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
//
//public class TestNetworkOP implements ActionListener {
//
//    String opname;
//
//    static int OP_NUM = 1;
//
//    public TestNetworkOP(String opname) {
//    	this.opname = opname;
//    }
//
//    public void actionPerformed(ActionEvent e) {
//		Set netwSet = Launcher.getAdapter().getCyNetworkManager().getNetworkSet();
//		if (netwSet.size() < 2)
//		    return;
//	
//		CyNetwork left = null, right = null;
//		java.util.Iterator i = netwSet.iterator();
//		for (int n = 0; i.hasNext(); n++) {
//		    if (n == 0)
//			left = (CyNetwork)i.next();
//		    else if (n == 1)
//			right = (CyNetwork)i.next();
//		    else
//			break;
//		}
//	
//		NetworkOperation op1, op2;
//	
//		op1 = null;
//		op2 = null;
//	
//		if (opname.equalsIgnoreCase("union"))
//		    op1 = new NetworkUnion(left, right);
//		else if (opname.equalsIgnoreCase("intersect"))
//		    op1 = new NetworkIntersection(left, right);
//		else if (opname.equalsIgnoreCase("diff"))
//		    op1 = new NetworkDifference(right, left);
//		else if (opname.equalsIgnoreCase("diff2")) {
//		    op1 = new NetworkDifference(right, left);
//		    op2 = new NetworkDifference(left, right);
//		}
//	
//		NetworkOperation ops[] = new NetworkOperation[]{op1, op2};
//	
//		for (int n = 0; n < ops.length; n++) {
//		    NetworkOperation op = ops[n];
//		    if (op == null)
//				continue;
//		
//		    System.out.println(op.toString());
//		    boolean inplace = false;
//		    if (inplace) {
//			CyNetwork null_netw = null; //NetworkFactory.getNullEmptyNetwork();
//			CyNetwork netw = null_netw;
//			op.eval(netw);
//	
//			NetworkUtils.setNetworkContentsAndReportPositions(left, netw);
//			Iterator<org.cytoscape.view.model.CyNetworkView> viewsIterator = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(left).iterator();
//			while(viewsIterator.hasNext()){
//				org.cytoscape.view.model.CyNetworkView view = viewsIterator.next();
//				view.updateView();
//			}
//		    }
//		    else {
//				CyNetwork netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
//				netw.getRow(netw).set(CyNetwork.NAME, opname + "_" + OP_NUM);		
//				OP_NUM++;
//		
//				op.eval(netw);
//				
//				Iterator<org.cytoscape.view.model.CyNetworkView> viewsIterator = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(netw).iterator();
//				while(viewsIterator.hasNext()){
//					org.cytoscape.view.model.CyNetworkView view = viewsIterator.next();
//					view.updateView();
//				}
//		    }
//		}
//    }
//}
//
