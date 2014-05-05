package org.cytoscape.sample;
import java.awt.event.ActionEvent;
import java.lang.Object;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.io.read.CyNetworkReader;
import org.cytoscape.io.read.CyNetworkReaderManager;
import org.cytoscape.io.write.* ;
import org.cytoscape.io.CyFileFilter;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.EdgeBendVisualProperty;
import org.cytoscape.view.presentation.property.values.Bend;
import org.cytoscape.view.presentation.property.values.LineType;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;

import java.text.DecimalFormat;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Adjust extends AbstractCyAction {
	public CyAppAdapter adapter;


	public Adjust(CySwingAppAdapter adapter){
		// Add a menu item
		super("Adjust Networks",
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());
		this.adapter = adapter;
		setPreferredMenu("Layout.Intelligent Layout");

	}


	public void actionPerformed(ActionEvent f) {


		final CyApplicationManager manager = adapter.getCyApplicationManager();

		final CyNetworkManager netmanager = adapter.getCyNetworkManager();


		//CyNetwork mynetwork = manager.getCurrentNetwork();
		//CyTable mytable = mynetwork.getDefaultNodeTable();

		//getting the list of networks in the open session
		Set<CyNetwork> setnet = netmanager.getNetworkSet();
		System.out.println("NETWORKS :"+setnet);
		//creating list of networks out of the set and list of networks' names
		ArrayList<CyNetwork> netlist = new ArrayList<CyNetwork>();
		ArrayList<String> netNames = new ArrayList<String>();

		for (CyNetwork net:setnet){
			String name =net.getRow(net).get("name", String.class);
			if (!(name.equals("null"))){

				netlist.add(net);
				//System.out.println("sel net" + name);
				netNames.add(name);
			}
		}

		//getting the reference network from user
		SelectNetworksDialog d = new SelectNetworksDialog(new JFrame(),"Options",true);
		d.setDialogData(netNames);
		d.setDialogData2(netNames);
		d.setVisible(true);
		//System.out.println("d.myselNet" + d.myselNet);
		if (d.myselList.size()!=1){
			JOptionPane.showMessageDialog(null, "Choose one reference network","ERROR", JOptionPane.WARNING_MESSAGE);

		}
		else{
			String mycurrnet = null;
			for (CyNetwork net:netlist){
				String name = net.getRow(net).get("name", String.class);
				//System.out.println("sel network" +name );
				//System.out.println("sel List" +d.myselList );
				for (String refnetSel : d.myselList){
					if (name.equals(refnetSel))
					{

						//System.out.println("match ref net" + net);
						mycurrnet = name;
						manager.setCurrentNetwork(net);

					}

				}}
			//network of reference, will be replaced by the user choice
			//manager.setCurrentNetwork(netlist.get(0));

			//manager.setCurrentNetwork(mycurrnet);
			CyNetworkView refnetworkView = manager.getCurrentNetworkView();
			//CyNetwork refnetwork = refnetworkView.getModel();

			CyNetwork myrefnetwork = manager.getCurrentNetwork();
			//System.out.println("current network" + myrefnetwork);
			//CyTable myreftable = myrefnetwork.getDefaultNodeTable();
			List<CyNode> refnodes = myrefnetwork.getNodeList();

			refnetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, 0.0);
			refnetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, 0.0);


			// create map to store data of node name + coordinates x, y
			Map<String, List<Double>> refNetMap = new HashMap<String, List<Double>>();
			for (CyNode refnode: refnodes ){
				String myrefname = myrefnetwork.getRow(refnode).get("name", String.class);
				View<CyNode> refnodeView=  refnetworkView.getNodeView(refnode) ;
				List<Double> refXY = new ArrayList<Double>();
				Double xref = refnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
				Double yref = refnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
				refXY.add(xref);
				refXY.add(yref);
				refNetMap.put(myrefname, refXY);
			}
			// iterate and display values
			/*System.out.println("Fetching Keys and corresponding [Multiple] Values n");
			int i =0;
			for (Map.Entry<String, List<Double>> entry : refNetMap.entrySet()) {
				i++;
				String key = entry.getKey();
				List<Double> values = entry.getValue();
				System.out.println("Key = " + key);
				System.out.println("Values = " + values + i);
			}*/

			//get the networks selected by user
			ArrayList<CyNetwork> adnetList = new ArrayList<CyNetwork>();
			for (CyNetwork net:netlist){
				String name =net.getRow(net).get("name", String.class);
				//System.out.println("d.myselNet" + d.myselNet);
				//System.out.println("name"+name);
				for (String selNetItem:d.myselNet){
					if ((name.equals(selNetItem))&&(!(name.equals(mycurrnet))))


					{
						//System.out.println("name match "+name);
						adnetList.add(net);
					}
				}
			}
			if (adnetList.size()==0){
				JOptionPane.showMessageDialog(null, "Choose at least one network to adjust to your reference network","ERROR", JOptionPane.WARNING_MESSAGE);

			}
			else{//adjust network after network to the ref network
				for (CyNetwork a : adnetList){ //
					//System.out.println("network  "+a);
					manager.setCurrentNetwork(a);
					//System.out.println("ad net" + a);
					CyNetworkView adnetworkView = manager.getCurrentNetworkView();
					//CyNetwork adnetwork = adnetworkView.getModel();
					//CyNetwork curNet = manager.getCurrentNetwork();
					//System.out.println("current network" + curNet);
					adnetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, 0.0);
					adnetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, 0.0);

					CyNetwork myadnetwork = manager.getCurrentNetwork();
					//CyTable myadtable = myadnetwork.getDefaultNodeTable();
					List<CyNode> adnodes = myadnetwork.getNodeList();




					Double start = Double.POSITIVE_INFINITY;
					Map<Double, List<Integer>> minDistMap = new HashMap<Double, List<Integer>>();
					List<Integer> startlist =new ArrayList<Integer>();
					startlist.add(1);
					startlist.add(1);
					minDistMap.put(start,startlist);

					//rotation
					for (int r=0;r<360; r++){



						Double eudistSum1 =0.0;	
						Double eudistSum2 = 0.0;
						Double eudistSum3 = 0.0;
						Double eudistSum4 = 0.0;





						for (CyNode adnode:adnodes){

							String myadname = myadnetwork.getRow(adnode).get("name", String.class);

							for (Map.Entry<String, List<Double>> entry : refNetMap.entrySet()) {
								String key = entry.getKey();

								if (key.equals(myadname)){
									//System.out.println("KEY" +key);
									//System.out.println("adname" +myadname);
									List<Double> values = entry.getValue();
									Double xref = values.get(0);
									Double yref = values.get(1);
									View<CyNode> adnodeView=  adnetworkView.getNodeView(adnode) ;

									Double xad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
									Double yad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
									// convert them to radians
									Double rRad = Math.toRadians(r);
									//m0 - no reflection
									Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
									Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);	

									Double eudist1 = Math.sqrt(Math.pow(xref-rx,2)+Math.pow(yref-ry,2));
									//System.out.println("eudistnode" + eudist1);
									eudistSum1 = eudistSum1+eudist1;

									//mx - reflexion x
									Double rxmx = rx;
									Double rymx = -ry;
									Double eudist2 = Math.sqrt(Math.pow(xref-rxmx,2)+Math.pow(yref-rymx,2));
									eudistSum2 = eudistSum2+eudist2;

									//mx - reflexion y
									Double rxmy = -rx;
									Double rymy = ry;
									Double eudist3 = Math.sqrt(Math.pow(xref-rxmy,2)+Math.pow(yref-rymy,2));
									eudistSum3 = eudistSum3+eudist3;

									//mxy - center symmetry
									Double rxmxy = -rx;
									Double rymxy = -ry;
									Double eudist4 = Math.sqrt(Math.pow(xref-rxmxy,2)+Math.pow(yref-rymxy,2));
									eudistSum4 = eudistSum4+eudist4;





								}

							}
						}
						//System.out.println("SUM1" +eudistSum1);
						//System.out.println("R" +r);
						//System.out.println("SET" +minDistMap.keySet());

						for (Map.Entry<Double, List<Integer>> entry : minDistMap.entrySet()) {
							Double key = entry.getKey();
							//System.out.println("KEY" +key);
							//System.out.println("SUM" +eudistSum1);
							if (eudistSum1<key){minDistMap.remove(key);
							List<Integer> list1 =new ArrayList<Integer>();
							list1.add(1);
							list1.add(r);
							minDistMap.put(eudistSum1,list1);
							//System.out.println("ENTRY" +minDistMap.entrySet());
							}}
						for (Map.Entry<Double, List<Integer>> entry : minDistMap.entrySet()) {
							Double key = entry.getKey();
							if (eudistSum2<key){minDistMap.remove(key);
							List<Integer> list2 =new ArrayList<Integer>();
							list2.add(2);
							list2.add(r);
							minDistMap.put(eudistSum2,list2);
							//System.out.println("ENTRY" +minDistMap.entrySet());
							}}
						for (Map.Entry<Double, List<Integer>> entry : minDistMap.entrySet()) {
							Double key = entry.getKey();
							if (eudistSum3<key){minDistMap.remove(key);
							List<Integer> list3 =new ArrayList<Integer>();
							list3.add(3);
							list3.add(r);
							minDistMap.put(eudistSum3,list3);
							//System.out.println("ENTRY" +minDistMap.entrySet());
							}}
						for (Map.Entry<Double, List<Integer>> entry : minDistMap.entrySet()) {
							Double key = entry.getKey();
							if (eudistSum4<key){minDistMap.remove(key);

							List<Integer> list4 =new ArrayList<Integer>();

							list4.add(4);
							list4.add(r);
							//System.out.println("LIST4"+list4);

							minDistMap.put(eudistSum4,list4);
							//System.out.println("ENTRY" +minDistMap.entrySet());
							}}


					}

					//System.out.println("Fetching Keys and corresponding [Multiple] Values n");

					for (Map.Entry<Double, List<Integer>> entry : minDistMap.entrySet()) {

						Double key = entry.getKey();
						List<Integer> values = entry.getValue();

						//values.get(0);
						if (values.get(0)==1){
							// convert them to radians
							Double rRad = Math.toRadians(values.get(1));
							for (CyNode adnode:adnodes){
								View<CyNode> adnodeView=  adnetworkView.getNodeView(adnode) ;

								Double xad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
								Double yad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
								//m0 - no reflection
								//System.out.println("no ref at " + values.get(1)+ "degrees");
								Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
								Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rx);
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, ry);
							}
						}
						//System.out.println("Key = " + key);
						//System.out.println("Values = " + values);
						if (values.get(0)==2){
							// convert them to radians
							Double rRad = Math.toRadians(values.get(1));
							for (CyNode adnode:adnodes){
								View<CyNode> adnodeView=  adnetworkView.getNodeView(adnode) ;

								Double xad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
								Double yad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
								//rotation
								Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
								Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
								//mx - reflexion x
								//System.out.println("ref x " + values.get(1)+ "degrees");
								Double rxmx = rx;
								Double rymx = -ry;
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmx);
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymx);
							}
						}

						if (values.get(0)==3){
							// convert them to radians
							Double rRad = Math.toRadians(values.get(1));
							for (CyNode adnode:adnodes){
								View<CyNode> adnodeView=  adnetworkView.getNodeView(adnode) ;

								Double xad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
								Double yad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
								//rotation
								Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
								Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
								//mx - reflexion y
								//System.out.println("ref y " + values.get(1)+ "degrees");
								Double rxmy = -rx;
								Double rymy = ry;
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmy);
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymy);
							}
						}
						if (values.get(0)==4){
							// convert them to radians
							Double rRad = Math.toRadians(values.get(1));
							for (CyNode adnode:adnodes){
								View<CyNode> adnodeView=  adnetworkView.getNodeView(adnode) ;

								Double xad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
								Double yad = adnodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
								//rotation
								Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
								Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
								//mxy - center symmetry
								//System.out.println("center symmetry " + values.get(1)+ "degrees");
								Double rxmxy = -rx;
								Double rymxy = -ry;
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmxy);
								adnodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymxy);
							}
						}
					}

					adnetworkView.updateView();
					adnetworkView.fitContent();
				}



			}
		}	
	}

}

