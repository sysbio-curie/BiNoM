
package org.cytoscape.sample;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.lang.Object;
import java.awt.Component;

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

public class TransitionalLayout extends AbstractCyAction {
	//public static  Double p;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public CyAppAdapter adapter;
public Map<String,List<Double>> currNetMap=null;
public Map<String,List<Double>> adPcaNetMap=null;
private static TransitionalLayout instance;
	public TransitionalLayout(CySwingAppAdapter adapter){
		// Add a menu item
		super("Transitional Layout",
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());
		this.adapter = adapter;
		setPreferredMenu("Layout.Intelligent Layout");
instance=this;
System.out.println("transitionalL");
	}
	
	public static TransitionalLayout getInstance(){return instance;}


	

	public void actionPerformed(ActionEvent g) {
		
		final CyApplicationManager manager = adapter.getCyApplicationManager();

		final CyNetworkManager netmanager = adapter.getCyNetworkManager();
		
		CyNetworkView currNetworkView = manager.getCurrentNetworkView();
		System.out.println("actionP");

		CyNetwork currNetwork = manager.getCurrentNetwork();
		//System.out.println("current network" + myrefnetwork);
		//CyTable myreftable = myrefnetwork.getDefaultNodeTable();
		List<CyNode> currNodes = currNetwork.getNodeList();

		currNetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, 0.0);
		currNetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, 0.0);
		
		//Map<String,List<Double>> currNetMap = new HashMap<String,List<Double>>();
		currNetMap=new HashMap<String,List<Double>>();
		for (CyNode refnode: currNodes ){
			String currName = currNetwork.getRow(refnode).get("name", String.class);
			View<CyNode> currNodeView=  currNetworkView.getNodeView(refnode) ;
			List<Double> currXY = new ArrayList<Double>();
			Double xref = currNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			Double yref = currNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			currXY.add(xref);
			currXY.add(yref);
			currNetMap.put(currName, currXY);
		}
		
		
		// iterate and display values
					/*System.out.println("Fetching Keys and corresponding [Multiple] Values n");
					int i =0;
					for (Map.Entry<String, List<Double>> entry : currNetMap.entrySet()) {
						i++;
						String key = entry.getKey();
						List<Double> values = entry.getValue();
						System.out.println("Key = " + key);
						System.out.println("Values = " + values + i);
					}*/
		
	//MenuAction.actionPerformed();	would be better to just call all the script
	
		
		//final CyNetworkReader reader = readermanager.getReader(arg0, arg1)
	


		

		
		
		
		//general network description
		
		Double height = currNetworkView.getVisualProperty(BasicVisualLexicon.NETWORK_HEIGHT );
		//System.out.println("height= "+height);
		Double width = currNetworkView.getVisualProperty(BasicVisualLexicon.NETWORK_WIDTH );
		//System.out.println("width= "+ width);
		Double scale = currNetworkView.getVisualProperty(BasicVisualLexicon.NETWORK_SCALE_FACTOR );
		//System.out.println("scale= "+ scale);

		
		
		//all nodes of the network
		List<CyNode> nodes = currNetwork.getNodeList();
		//System.out.println(nodes);
		
		//Create a set with all column names
		Set<String> set_attributes = new HashSet<String>();
		for (CyNode node : currNetwork.getNodeList()) {
			CyRow row = currNetwork.getRow(node);
			Map<String,Object> values = row.getAllValues();
		
			for (String col: values.keySet()){
				
					set_attributes.add(col);
					//System.out.println(col);
				
			}
			
		}
		
			//A list with all column names
			ArrayList<String> myList = new ArrayList<String>();
			for (String col: set_attributes){
					if (col=="SUID"||col=="selected"||col=="name"||col=="shared name"){}
					else{
				
					myList.add(col);
					//System.out.println(col);
				
					}}
			
			//a window for the user to select columns needed in analysis
			SelectColumnsDialog d = new SelectColumnsDialog(new JFrame(),"Select column names",true);
			d.setDialogData(myList);
			d.setVisible(true);

			
			
		//re-creation of attributes matrix
		float [][] matrix = new float [nodes.size()] [d.myselList.size()] ; 
		Map<String,Integer> map_index = new HashMap<String,Integer>();
		int ct = 0;
		for (String att : d.myselList){
			map_index.put(att,ct);
			ct++;
		}
		//System.out.println(map_index);
		//System.out.println(d.myselList);

		int ct2=0;
		for (CyNode node : currNetwork.getNodeList()) {
			CyRow row = currNetwork.getRow(node);
			Map<String,Object> values = row.getAllValues();
			String myname = currNetwork.getRow(node).get("name", String.class);
			System.out.println(myname);
			//System.out.println(ct2);
			for (String col: values.keySet()){
				if (map_index.containsKey(col)){
					int index2=map_index.get(col);
					//System.out.println(index2);
					

					Object value = currNetwork.getRow(node).getAllValues().get(col);
					//System.out.print(value);
					
					//check if attributes are numerical values 
					if (value instanceof String||value instanceof Boolean){
						JOptionPane.showMessageDialog(null, "Columns must contain only numerical values","ERROR", JOptionPane.WARNING_MESSAGE);
						
					}
					
					//missing values are replaced by 0
					if (value == null){
						value=0.0;

					}
					
					
					Float fvalue = ((Double) value).floatValue();
					//System.out.print(fvalue);


					//System.out.print(ct2);
					//System.out.print(index2);
					//System.out.print(" ");

					// FINAL MATRIX
					matrix[ct2][index2]=fvalue; }
				//System.out.println(value);

			}
			ct2++;
		}


		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < d.myselList.size(); j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}


		/*Matrix newmatrix = new Matrix(matrix);
		System.out.println(newmatrix);
		float[][] redmatrix = newmatrix.removeRowsWithValue(0.2);

		for (int i = 0; i < redmatrix[0].length; i++) {
			for (int j = 0; j < redmatrix.length; j++) {
				System.out.print(redmatrix[i][j] + " ");
			}
			System.out.print("\n");
		}*/	


		// detele row that have too much missing values
		float[][] data = matrix;
		double treshold= 0.2;
		ArrayList<Integer> delnodes = new  ArrayList<Integer>();
		/* Use an array list to track of the rows we're going to want to 
	               keep...arraylist makes it easy to grow dynamically so we don't 
	               need to know up front how many rows we're keeping */
		List<float[]> rowsToKeep = new ArrayList<float[]>(data.length);

		List<CyNode> mynewnodelist = currNetwork.getNodeList();
		int nnode = 0;
		int nnode2=0;
		for(float[] row : data)
		{
			/* If you download Apache Commons, it has built-in array search
	                      methods so you don't have to write your own */



			boolean found = false;
			double sum0=0;
			
			//System.out.println(nnode);
			for(float testValue : row)
			{
				/* Using == to compares doubles is generally a bad idea 
	                               since they can be represented slightly off their actual
	                               value in memory */

				if(testValue == 0){
					sum0++;
				}
				double rate=sum0/data[0].length;	

				if(rate>treshold)	
				{//System.out.println(nnode);
					found = true;
					//System.out.println("found"+nnode);
					//System.out.println("found"+nnode2);
					delnodes.add(nnode);
					
					mynewnodelist.remove(nnode2);
					nnode2=nnode2-1;
					//System.out.println(mynewnodelist);
					break;
				}
			}

			/* if we didn't find our value in the current row, 
	                      that must mean its a row we keep */
			if(!found)
			{
				rowsToKeep.add(row);
				//System.out.println(row);
			}nnode++;
			nnode2++;
			
		}
		//System.out.println(delnodes);
		/* now that we know what rows we want to keep, make our 
	               new 2D array with only those rows */
		data = new float[rowsToKeep.size()][];
		for(int i=0; i < rowsToKeep.size(); i++)
		{
			data[i] = rowsToKeep.get(i);

		}

		/*for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.print("\n");
		}*/

		int numberOfPoints = mynewnodelist.size();	

		//double center data
		if (d.dcenter.isSelected()){//center data code
			}
		
		//PCA analysis on matrix without missing values
		
			ArrayList<Double> arrayListX = new ArrayList<Double>();
			ArrayList<Double> arrayListY = new ArrayList<Double>();
		if (d.pcaButton.isSelected()){
			
			
			PCALayout pca = new PCALayout();
			if (data[0].length<10){pca.pcNumber = data[0].length;}
			else {pca.pcNumber = 10;}
			System.out.print("pcNumber= "+ pca.pcNumber);
			System.out.print("data[0].length= "+ data[0].length);
			pca.makeDataSet(data);
			pca.computePCA();
			
			
			
			
		
			int a2 =0;
			int a3 =0;
			int a4 =0;
			int a5 =0;
			int a6 =0;
			int a7 =0;
			int a8 =0;
			int a9 =0;
			//check only 2 PC
			int index = 0;
			int pcCount =0;
			if (d.pc1.isSelected()){index++; pcCount=1;}
			if (d.pc2.isSelected()){index++;pcCount=2;}
			if (d.pc3.isSelected()){index++;pcCount=3;}
			if (d.pc4.isSelected()){index++;pcCount=4;}
			if (d.pc5.isSelected()){index++;pcCount=5;}
			if (d.pc6.isSelected()){index++;pcCount=6;}
			if (d.pc7.isSelected()){index++;pcCount=7;}
			if (d.pc8.isSelected()){index++;pcCount=8;}
			if (d.pc9.isSelected()){index++;pcCount=9;}
			if (d.pc10.isSelected()){index++;pcCount=10;}
			//System.out.println(index);
			if (index!=2){
				JOptionPane.showMessageDialog(null, "Choose exacly 2 principal components","ERROR", JOptionPane.WARNING_MESSAGE);
				}
			else if (data[0].length<2){
				JOptionPane.showMessageDialog(null, "Load data first","ERROR", JOptionPane.WARNING_MESSAGE);
			}
			else if (pcCount>data[0].length){
				JOptionPane.showMessageDialog(null, "You can compute as many principal components as colums in your data set, not more!","ERROR", JOptionPane.WARNING_MESSAGE);
				}else{
			//System.out.println("POINT\tX\tY");
			for(int i=0;i<numberOfPoints;i++){
				

				
		
				
				if (d.pc1.isSelected()){
					
						double x = pca.geneProjections[i][0];
						arrayListX.add(x);
				}
				if (d.pc2.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a2==1)){
						a2=1;
						double x = pca.geneProjections[i][1];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][1];
					arrayListY.add(y);}
				}
				if (d.pc3.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a3==1)){
						a3=1;
						double x = pca.geneProjections[i][2];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][2];
					arrayListY.add(y);}
				}
				if (d.pc4.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a4==1)){
						
						a4=1;
						double x = pca.geneProjections[i][3];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][3];
					arrayListY.add(y);}
				}
				if (d.pc5.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a5==1)){
						a5=1;
						double x = pca.geneProjections[i][4];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][4];
					arrayListY.add(y);}
				}
				if (d.pc6.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a6==1)){
						a6=1;
						double x = pca.geneProjections[i][5];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][5];
					arrayListY.add(y);}
				}
				if (d.pc7.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a7==1)){
						a7=1;
						double x = pca.geneProjections[i][6];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][6];
					arrayListY.add(y);}
				}
				if (d.pc8.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a8==1)){
						a8=1;
						double x = pca.geneProjections[i][7];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][7];
					arrayListY.add(y);}
				}
				if (d.pc9.isSelected()){
					if((i==0 && arrayListX.size()<1) ||  ((i>0)&& a9==1)){
						a9=1;
						double x = pca.geneProjections[i][8];
						arrayListX.add(x);}
					else {double y = pca.geneProjections[i][8];
					arrayListY.add(y);}
				}
				if (d.pc10.isSelected()){
					double y = pca.geneProjections[i][9];
					arrayListY.add(y);
				}
				
			
			
			}
			
			
			String sortie ="";
			sortie += " PC\t% of variance";
			 int b=0;
			for (double i : pca.explainedVariation ){
				b++;
				DecimalFormat df = new DecimalFormat("#.00");
				String c = df.format(i*100);
				//i = (double)Math.round(i * 1000) / 1000;
				sortie+= "\n PC " + b + "\t" + c + "%";}
			
		JTextArea aireSortie = new JTextArea (7,10);
		aireSortie.setText (sortie);
		
		JOptionPane.showMessageDialog(null,aireSortie,"Rapport", JOptionPane.INFORMATION_MESSAGE);
		
			//JOptionPane.showMessageDialog(null,,"REPORT", JOptionPane.INFORMATION_MESSAGE);
			}
			
			}
		else if (d.nlpcaButton.isSelected()){
			PCALayout nlpca = new PCALayout();
			nlpca.makeDataSet(data);
			nlpca.computeNonlinearPCALayout();
			for(int i=0;i<numberOfPoints;i++){
			double x = nlpca.geneProjections[i][0];
			arrayListX.add(x);
			double y = nlpca.geneProjections[i][1];
			arrayListY.add(y);
			}
			
			String sortie ="";
			sortie += " PC\t% of variance";
			 
				double pofv = nlpca.explainedVariation[0];
				DecimalFormat df = new DecimalFormat("#.00");
				String c = df.format(pofv*100);
				//i = (double)Math.round(i * 1000) / 1000;
				sortie+= "\n Explained variation \t" + c + "%";
			
		JTextArea aireSortie = new JTextArea (7,10);
		aireSortie.setText (sortie);
		
		JOptionPane.showMessageDialog(null,aireSortie,"Rapport", JOptionPane.INFORMATION_MESSAGE);
			
			
	}
		ArrayList<Double> arrayListF = new ArrayList<Double>();

		Double maxX = Collections.max(arrayListX);
		Double minX = Collections.min(arrayListX);
		//System.out.println("max X= "+ maxX + "minX= "+ minX);

		Double sumX = maxX +Math.abs(minX);
		//System.out.println(sumX);


		Double factorX =(width/(scale))/sumX;
		//System.out.println("factorX= " + factorX);
		arrayListF.add(factorX);

		Double maxY = Collections.max(arrayListY);
		Double minY = Collections.min(arrayListY);
		//System.out.println("max Y= "+ maxY+"minY= "+ minY);


		Double sumY = maxY +Math.abs(minY);
		//System.out.println(sumY);

		Double factorY =(height/(scale))/sumY;
		//System.out.println("factorY= " + factorY);

		arrayListF.add(factorY);
		Double factor = Collections.min(arrayListF);
		//System.out.println("X" + arrayListX);
		//System.out.println("Y"+ arrayListY);
		
		//System.out.println(numberOfPoints);
		for(int i=0;i<numberOfPoints;i++){
			//System.out.println(mynewnodelist);
			CyNode node = mynewnodelist.get(i);
			View<CyNode> nodeViewf =  currNetworkView.getNodeView(node) ;
			
			double x = arrayListX.get(i) *factor;
			double y = arrayListY.get(i)*factor;
			
			nodeViewf.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, x);
			nodeViewf.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, y);
		}
		
	
		for(int i=0;i<2;i++){
		for (int node : delnodes)
		{		
			CyNode delnode = nodes.get(node);
			//String delnodename = mynetwork.getRow(delnode).get("name", String.class);
			//System.out.println("empty node name: " + delnodename);
			List<CyNode> neighbors = currNetwork.getNeighborList(delnode, CyEdge.Type.ANY);
			ArrayList<Double> nx = new  ArrayList<Double>();
			ArrayList<Double> ny = new  ArrayList<Double>();
			for (CyNode neighnode : neighbors)
			 {
				View<CyNode> nodeViewN =  currNetworkView.getNodeView(neighnode) ;
				//String mynameneig = mynetwork.getRow(neighnode).get("name", String.class);
				//System.out.println("neighbor: " + mynameneig);
				Double xD = nodeViewN.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
				Double yD = nodeViewN.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
				nx.add(xD);
				ny.add(yD);
		}
			View<CyNode> nodeViewD =  currNetworkView.getNodeView(delnode) ;
			Average avx = new Average(nx);
			//System.out.println(nx + "mean = "+ avx.mean);
			nodeViewD.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, avx.mean);
			Average avy = new Average(ny);
			nodeViewD.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, avy.mean);
			//System.out.println(ny + "mean = "+ avy.mean);

			
		}
		}
		CyNetwork mynetwork2 = manager.getCurrentNetwork();
		
		for (CyNode dnode : mynetwork2.getNodeList()){ 
			//System.out.println(dnode);
			//String mynamednode = mynetwork.getRow(dnode).get("name", String.class);
			//System.out.println(mynamednode);
			View<CyNode> nodeViewDist =  currNetworkView.getNodeView(dnode);
			Double xnDist = nodeViewDist.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			Double ynDist = nodeViewDist.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			List<CyNode> neighborsDist = mynetwork2.getNeighborList(dnode, CyEdge.Type.ANY);
			//System.out.println(neighborsDist);
			ArrayList<Double> edgel = new  ArrayList<Double>();
			
			for (CyNode neighnodeDist : neighborsDist){
				
				View<CyNode> nodeViewDistneig =  currNetworkView.getNodeView(neighnodeDist) ;
				Double xneDist = nodeViewDistneig.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
				Double yneDist = nodeViewDist.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
				Double edist = Math.sqrt(Math.pow(xnDist-xneDist,2)+Math.pow(ynDist-yneDist,2));
				edgel.add(edist);
			}
			//System.out.println("edges lengths: "+edgel);
			if (edgel.isEmpty()){}
			else{int minIndex = edgel.indexOf(Collections.min(edgel));
			Double smedge = edgel.get(minIndex);
			//System.out.println("smallest edge: "+smedge);
			if (smedge<1.0){
				
			ArrayList<Double>  listr = new ArrayList<Double>();
			Double Max = 3.0;
			Double Min = 1.0;
			Double random = Min + (int)(Math.random() * ((Max - Min) + 1));
			listr.add(random);
			Double Max2 = -1.0;
			Double Min2 = -3.0;
			Double random2 = Min2 + (int)(Math.random() * ((Max2 - Min2) + 1));
			listr.add(random2);
			
			Random r = new Random();
			Double rr = listr.get(r.nextInt(listr.size()));
			
			nodeViewDist.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, xnDist+rr);
			//System.out.println("old location "+xnDist +"deplacement" + rr);
			nodeViewDist.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, ynDist+rr);
			//System.out.println("old location "+ynDist +"deplacement" + rr);
			}}
		}
		
			
			
		
		
		
		//networkView.setVisualProperty(BasicVisualLexicon.NETWORK_WIDTH,1000.0 );
		//double scaleFactor = 130;
		currNetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, 0.0);
		currNetworkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, 0.0);

		//networkView.setVisualProperty(BasicVisualLexicon.EDGE_BEND,EdgeBendVisualProperty.DEFAULT_EDGE_BEND);
		//networkView.updateView();
		
		
		


	
		//networkView.fitContent();//miraculous!!!!!!!!!!
		
        CyNetworkView pcaNetworkView = manager.getCurrentNetworkView();
		

		CyNetwork pcaNetwork = manager.getCurrentNetwork();
		List<CyNode> pcaNodes = pcaNetwork.getNodeList();
		
		
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





			for (CyNode pcaNode:pcaNodes){

				String pcaName = pcaNetwork.getRow(pcaNode).get("name", String.class);

				for (Map.Entry<String, List<Double>> entry : currNetMap.entrySet()) {
					String key = entry.getKey();

					if (key.equals(pcaName)){
						//System.out.println("KEY" +key);
						//System.out.println("adname" +myadname);
						List<Double> values = entry.getValue();
						Double xref = values.get(0);
						Double yref = values.get(1);
						View<CyNode> pcaNodeView=  pcaNetworkView.getNodeView(pcaNode) ;

						Double xad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
						Double yad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
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
				for (CyNode pcaNode:pcaNodes){
					View<CyNode> pcaNodeView=  pcaNetworkView.getNodeView(pcaNode) ;

					Double xad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
					Double yad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
					//m0 - no reflection
					//System.out.println("no ref at " + values.get(1)+ "degrees");
					Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
					Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rx);
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, ry);
				}
			}
			//System.out.println("Key = " + key);
			//System.out.println("Values = " + values);
			if (values.get(0)==2){
				// convert them to radians
				Double rRad = Math.toRadians(values.get(1));
				for (CyNode pcaNode:pcaNodes){
					View<CyNode> pcaNodeView=  pcaNetworkView.getNodeView(pcaNode) ;

					Double xad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
					Double yad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
					//rotation
					Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
					Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
					//mx - reflexion x
					//System.out.println("ref x " + values.get(1)+ "degrees");
					Double rxmx = rx;
					Double rymx = -ry;
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmx);
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymx);
				}
			}

			if (values.get(0)==3){
				// convert them to radians
				Double rRad = Math.toRadians(values.get(1));
				for (CyNode pcaNode:pcaNodes){
					View<CyNode> pcaNodeView=  pcaNetworkView.getNodeView(pcaNode) ;

					Double xad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
					Double yad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
					//rotation
					Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
					Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
					//mx - reflexion y
					//System.out.println("ref y " + values.get(1)+ "degrees");
					Double rxmy = -rx;
					Double rymy = ry;
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmy);
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymy);
				}
			}
			if (values.get(0)==4){
				// convert them to radians
				Double rRad = Math.toRadians(values.get(1));
				for (CyNode pcaNode:pcaNodes){
					View<CyNode> pcaNodeView=  pcaNetworkView.getNodeView(pcaNode) ;

					Double xad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
					Double yad = pcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
					//rotation
					Double rx = xad*Math.cos(rRad)-yad*Math.sin(rRad);
					Double ry = xad*Math.sin(rRad)+yad*Math.cos(rRad);
					//mxy - center symmetry
					//System.out.println("center symmetry " + values.get(1)+ "degrees");
					Double rxmxy = -rx;
					Double rymxy = -ry;
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, rxmxy);
					pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, rymxy);
				}
			}
		}
		
		
		
		CyNetworkView adPcaNetworkView = manager.getCurrentNetworkView();
		

		CyNetwork adPcaNetwork = manager.getCurrentNetwork();
		List<CyNode> adPcaNodes = adPcaNetwork.getNodeList();

		//Map<String, List<Double>> adPcaNetMap = new HashMap<String, List<Double>>();
		adPcaNetMap=new HashMap<String,List<Double>>();
		for (CyNode adPcanode: adPcaNodes ){
			String adPcaName = adPcaNetwork.getRow(adPcanode).get("name", String.class);
			View<CyNode> adPcaNodeView=  pcaNetworkView.getNodeView(adPcanode) ;
			List<Double> adPcaXY = new ArrayList<Double>();
			Double xref = adPcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			Double yref = adPcaNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			adPcaXY.add(xref);
			adPcaXY.add(yref);
			adPcaNetMap.put(adPcaName, adPcaXY);
		}
		
		
		System.out.println("Fetching Keys and corresponding [Multiple] Values n");
		int i =0;
		for (Map.Entry<String, List<Double>> entry : adPcaNetMap.entrySet()) {
			i++;
			String key = entry.getKey();
			List<Double> values = entry.getValue();
			System.out.println("Key = " + key);
			System.out.println("Values = " + values + i);
		}
		
		
		

		
		//calculate dist btw 1st layout and PCA
		
		
		doMyLayout (0.99);
		//slider
		SliderPCA slider= new SliderPCA();
        slider.createAndShowGUI();
      
	
		
		
		
		
		adPcaNetworkView.updateView();
		
		adPcaNetworkView.fitContent();
		
	}
	
	
	

public void doMyLayout (Double pourcentage){
	final CyApplicationManager manager = adapter.getCyApplicationManager();

	final CyNetworkManager netmanager = adapter.getCyNetworkManager();
	
	CyNetworkView currNetworkView = manager.getCurrentNetworkView();
	System.out.println(" do layout p " + pourcentage);

	CyNetwork currNetwork = manager.getCurrentNetwork();
	//System.out.println("current network" + myrefnetwork);
	//CyTable myreftable = myrefnetwork.getDefaultNodeTable();
	List<CyNode> currNodes = currNetwork.getNodeList();

	   
			for (CyNode pcaNode:currNodes){
				
				String pcaName = currNetwork.getRow(pcaNode).get("name", String.class);
				//System.out.println("name  " + pcaName);
			for (Map.Entry<String, List<Double>> pcaEntry : adPcaNetMap.entrySet()) {
				
				String pcaKey = pcaEntry.getKey();
				
				
				// to be given by user p%
				
				for (Map.Entry<String, List<Double>> currEntry : currNetMap.entrySet()) {
					String currKey = currEntry.getKey();
					//System.out.println("pcaKey " + pcaKey);
					//System.out.println("currKey " + currKey);
					if ((pcaKey.equals(currKey)) &&  (pcaKey.equals(pcaName))) {
						//System.out.println("pcaKey " + pcaKey);
						//System.out.println("currKey " + currKey);
						List<Double> pcaValues = pcaEntry.getValue();
						Double xpca = pcaValues.get(0);
						Double ypca = pcaValues.get(1);
						List<Double> currValues = currEntry.getValue();
						Double xcurr = currValues.get(0);
						Double ycurr = currValues.get(1);
						Double edist = Math.sqrt(Math.pow(xpca-xcurr,2)+Math.pow(ypca-ycurr,2));
						Double edistSq = Math.pow(xpca-xcurr,2)+Math.pow(ypca-ycurr,2);
						//coordinates of the line to wich both nodes belongs ax+by+c=0
						/*System.out.println("xpca "+xpca +"xcurr " + xcurr );
						System.out.println("ypca "+ypca +"ycurr " + ycurr );
						Double a = ypca-ycurr;
						Double b = xcurr-xpca;
						Double c = ((xpca-xcurr)*ycurr) -((ypca-ycurr)*xcurr);
						Double z = p*edist; //real distance from curr layout 
						//there can be only 2 points distanced from curr layout belonging to the line at given distance
						Double e = Math.pow(a,2)+Math.pow(b,2);
						Double f = 2*b*c + xcurr*a*b;
						Double h = Math.pow(c,2)+ 2*a*c*xcurr +  (Math.pow(a,2)* Math.pow(xcurr,2))+(Math.pow(a,2)*Math.pow(ycurr,2))-(Math.pow(z,2)*Math.pow(a,2));
						System.out.println("a "+a +"b " + b + "c "+ c + "z "+ z +"e "+e+ "f "+f+ " h"+ h);
						Double yNew1= (-f-Math.sqrt(Math.pow(f,2)-4*e*h))/(2*e);
						Double xNew1 = (b*yNew1+c)/-a;
						Double yNew2= (-f+Math.sqrt(Math.pow(f,2)-4*e*h))/(2*e);
						Double xNew2 = (b*yNew2+c)/-a;
						System.out.println("new x1 " + xNew1+"new y1 " + yNew1+ "new x2 " + xNew2+ "new y2 " + yNew2);
						Double z2 = (1-p)*edist;//real dist from aligned pca layout
						*/
						
						//two circles with the center in the points of the old and pca layout with radius equal to demanded dist and 1-demanded dist
						//xcurr= 1.0;
						//ycurr=1.0;
						//xpca=3.0;
						//ypca=2.0;
						//edistSq=5.0;
				
						Double z = pourcentage*pourcentage*edistSq; //*
						
						Double z1 = (1-pourcentage)*(1-pourcentage)*edistSq; //*
						Double f = -(2*xpca-2*xcurr);//*
						Double h = -(Math.pow(xcurr,2)-Math.pow(xpca,2)+Math.pow(ycurr,2)-Math.pow(ypca,2)-z+z1);//*
						Double e = 2*ypca- 2*ycurr;//*
						
						Double a =  Math.pow(e,2)+Math.pow(f,2);//*
						Double b = -2*Math.pow(e,2)*xcurr-2*e*f*ycurr+2*f*h;
						Double c = Math.pow(e,2)*Math.pow(xcurr,2) + h*h-2*e*h*ycurr+Math.pow(e,2)*Math.pow(ycurr,2)-Math.pow(e,2)*z;
						
						Double delta = b*b - 4*a*c;
						//System.out.println("xpca " + xpca+"ypca " + ypca+ "xcurr " + xcurr + "ycurr " + ycurr);
						//System.out.println("pow " + Math.pow(xcurr,2));
						//System.out.println("z " + z+"z1 " + z1+ "f " + f + "h " + h+ "e "+ e);
						//System.out.println(" a " + a +" b " + b+ " c " + c );
						//System.out.println("delta" + delta);
						
						Double xNew=-b/ (2*a);
						Double yNew= (f*xNew + h )/e;
						//System.out.println(" xNew " + xNew+ " y New " + yNew + " f * xNew " + f*xNew);
						
						
						
						View<CyNode> pcaNodeView=  currNetworkView.getNodeView(pcaNode) ;	
						
						
							
							pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, xNew);
							pcaNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, yNew);
							
							
							
						
					}
				}
			}
			
			}
			currNetworkView.updateView();
			currNetworkView.fitContent();
}






}