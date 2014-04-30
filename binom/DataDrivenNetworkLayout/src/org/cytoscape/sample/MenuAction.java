package org.cytoscape.sample;
import java.awt.event.ActionEvent;
import java.lang.Object;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyColumn;
import org.cytoscape.io.write.* ;
import org.cytoscape.io.CyFileFilter;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.EdgeBendVisualProperty;
import org.cytoscape.view.presentation.property.values.Bend;
import org.cytoscape.view.presentation.property.values.LineType;
import org.cytoscape.work.Tunable;

import java.text.DecimalFormat;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MenuAction extends AbstractCyAction {
	private final CyAppAdapter adapter;

	public MenuAction(CyAppAdapter adapter) {
		super("Data Driven Layout",
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());
		this.adapter = adapter;
		setPreferredMenu("Layout");
		
	}

	public void actionPerformed(ActionEvent e) {
		
		
		final CyApplicationManager manager = adapter.getCyApplicationManager();
		final CyNetworkView networkView = manager.getCurrentNetworkView();
		final CyNetwork network = networkView.getModel();

		CyNetwork mynetwork = manager.getCurrentNetwork();
		CyTable mytable = mynetwork.getDefaultNodeTable();

		
		
		
		//general network description
		
		Double height = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_HEIGHT );
		//System.out.println("height= "+height);
		Double width = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_WIDTH );
		//System.out.println("width= "+ width);
		Double scale = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_SCALE_FACTOR );
		//System.out.println("scale= "+ scale);

		/*Double centerX = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION);
		System.out.println("centerX= "+centerX);
		Double centerY = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION);
		System.out.println("centerY= "+centerY);
		*/

		//int count = mytable.getRowCount();

		//all nodes of the network
		List<CyNode> nodes = mynetwork.getNodeList();
		//System.out.println(nodes);
		
		//Create a set with all column names
		Set<String> set_attributes = new HashSet<String>();
		for (CyNode node : mynetwork.getNodeList()) {
			CyRow row = mynetwork.getRow(node);
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
		for (CyNode node : mynetwork.getNodeList()) {
			CyRow row = mynetwork.getRow(node);
			Map<String,Object> values = row.getAllValues();
			String myname = mynetwork.getRow(node).get("name", String.class);
			System.out.println(myname);
			//System.out.println(ct2);
			for (String col: values.keySet()){
				if (map_index.containsKey(col)){
					int index2=map_index.get(col);
					//System.out.println(index2);
					

					Object value = mynetwork.getRow(node).getAllValues().get(col);
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

		List<CyNode> mynewnodelist = mynetwork.getNodeList();
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
				{System.out.println(nnode);
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
	
		//System.out.println("POINT\tX\tY");
		for(int i=0;i<numberOfPoints;i++){
			

			
			//check only 2 PC
			int index = 0;
			if (d.pc1.isSelected()){index++;}
			if (d.pc2.isSelected()){index++;}
			if (d.pc3.isSelected()){index++;}
			if (d.pc4.isSelected()){index++;}
			if (d.pc5.isSelected()){index++;}
			if (d.pc6.isSelected()){index++;}
			if (d.pc7.isSelected()){index++;}
			if (d.pc8.isSelected()){index++;}
			if (d.pc9.isSelected()){index++;}
			if (d.pc10.isSelected()){index++;}
			//System.out.println(index);
			if (index!=2){
				JOptionPane.showMessageDialog(null, "Chose exacly 2 principal components","ERROR", JOptionPane.WARNING_MESSAGE);
				}
			
			
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
			//nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, x);
			//nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, y);

			

			//Double x2 = nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			//Double y2 = nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			//System.out.println("x="+x2 + "y="+ y2);

			//System.out.println(i+"\t"+pca.geneProjections[i][0]+"\t"+pca.geneProjections[i][1]);
			//System.out.println("Variance explained by PC1 = "+pca.explainedVariation[0]);
			//System.out.println("Variance explained by PC2 = "+pca.explainedVariation[1]);


			//CyNode node = mynewnodelist.get(i);
			

			//View<CyNode> nodeView1 =  networkView.getNodeView(node) ;

			//Double x3 = nodeView1.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			//Double y3 = nodeView1.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			//System.out.println("x="+x3 + "y="+ y3);
		
			
			//JOptionPane.showMessageDialog(null,pca.explainedVariation ,"REPORT", JOptionPane.INFORMATION_MESSAGE);
		
		
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
		
		
		ArrayList<Double> arrayListF = new ArrayList<Double>();

		Double maxX = Collections.max(arrayListX);
		Double minX = Collections.min(arrayListX);
		//System.out.println("max X= "+ maxX + "minX= "+ minX);

		Double sumX = maxX +Math.abs(minX);
		//System.out.println(sumX);


		Double factorX =(width/(scale+1))/sumX;
		//System.out.println("factorX= " + factorX);
		arrayListF.add(factorX);

		Double maxY = Collections.max(arrayListY);
		Double minY = Collections.min(arrayListY);
		//System.out.println("max Y= "+ maxY+"minY= "+ minY);


		Double sumY = maxY +Math.abs(minY);
		//System.out.println(sumY);

		Double factorY =(height/(scale+1))/sumY;
		//System.out.println("factorY= " + factorY);

		arrayListF.add(factorY);
		Double factor = Collections.min(arrayListF);
		//System.out.println("X" + arrayListX);
		//System.out.println("Y"+ arrayListY);
		
		//System.out.println(numberOfPoints);
		for(int i=0;i<numberOfPoints;i++){
			//System.out.println(mynewnodelist);
			CyNode node = mynewnodelist.get(i);
			View<CyNode> nodeViewf =  networkView.getNodeView(node) ;
			
			double x = arrayListX.get(i) *factor;
			double y = arrayListY.get(i)*factor;
			
			nodeViewf.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, x);
			nodeViewf.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, y);
		}
		
	
		for(int i=0;i<2;i++){
		for (int node : delnodes)
		{		
			CyNode delnode = nodes.get(node);
			String delnodename = mynetwork.getRow(delnode).get("name", String.class);
			//System.out.println("empty node name: " + delnodename);
			List<CyNode> neighbors = mynetwork.getNeighborList(delnode, CyEdge.Type.ANY);
			ArrayList<Double> nx = new  ArrayList<Double>();
			ArrayList<Double> ny = new  ArrayList<Double>();
			for (CyNode neighnode : neighbors)
			 {
				View<CyNode> nodeViewN =  networkView.getNodeView(neighnode) ;
				String mynameneig = mynetwork.getRow(neighnode).get("name", String.class);
				//System.out.println("neighbor: " + mynameneig);
				Double xD = nodeViewN.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
				Double yD = nodeViewN.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
				nx.add(xD);
				ny.add(yD);
		}
			View<CyNode> nodeViewD =  networkView.getNodeView(delnode) ;
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
			View<CyNode> nodeViewDist =  networkView.getNodeView(dnode);
			Double xnDist = nodeViewDist.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			Double ynDist = nodeViewDist.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			List<CyNode> neighborsDist = mynetwork2.getNeighborList(dnode, CyEdge.Type.ANY);
			//System.out.println(neighborsDist);
			ArrayList<Double> edgel = new  ArrayList<Double>();
			
			for (CyNode neighnodeDist : neighborsDist){
				
				View<CyNode> nodeViewDistneig =  networkView.getNodeView(neighnodeDist) ;
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
		networkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, 0.0);
		networkView.setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, 0.0);

		//networkView.setVisualProperty(BasicVisualLexicon.EDGE_BEND,EdgeBendVisualProperty.DEFAULT_EDGE_BEND);
		networkView.updateView();
		
		
		


	}

 
}

