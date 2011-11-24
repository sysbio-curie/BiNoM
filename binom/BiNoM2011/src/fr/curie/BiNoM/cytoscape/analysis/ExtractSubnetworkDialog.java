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
package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.utils.SubnetworkProperties;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.*;

import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.netwop.NetworksUpdateTask;

public class ExtractSubnetworkDialog extends JDialog {

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JComboBox netwAddCB;
    private JList netwUpdateList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static ExtractSubnetworkDialog instance;
    
    private JRadioButton FirstOrderConnection;
    private JRadioButton FirstOrderConnectionsWithComplexes;
    private JRadioButton SecondOrderConnections;
    private JRadioButton AddFirstNeighbours;
    private JRadioButton ConnectByShortestPath;    
    private JCheckBox TestComponentSignificance;    
    private JCheckBox MakeConnectivityTable;
    private JComboBox NumberOfPermutations;
    
    private JCheckBox MakeSizeSignificanceTest;
    private JComboBox NumberOfPermutationsForSizeTest;

    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    
    JTextArea fixedNodeList = null;
    JTextArea sizesToTest = null;


    public static ExtractSubnetworkDialog getInstance() {
	if (instance == null)
	    instance = new ExtractSubnetworkDialog();
	return instance;
    }

    public void raise() {

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private ExtractSubnetworkDialog() {
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Extract subnetwork from selected nodes");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 40;
	panel.add(title, c);
	y++;
	
	JLabel label;

	label = new JLabel("Select method of subnetwork construction");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);
	y+=2;

	/*label = new JLabel("Network to suppress");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 1;
	c.ipadx = 10;
	c.ipady = 30;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);*/

	/*netwAddCB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netwAddCB, c);*/

	/*netwSupCB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 1;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netwSupCB, c);

	label = new JLabel("Networks to update");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 2;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	netwUpdateList = new JList();
	netwUpdateList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(netwUpdateList);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 2;
	c.anchor = GridBagConstraints.WEST;
	//c.fill = GridBagConstraints.HORIZONTAL;
	//c.weightx = 1.0;
	//panel.add(netwUpdateList, c);WEST;
	panel.add(scrollPane, c);

	y += 3;

	JPanel pad = new JPanel();
	pad.setPreferredSize(new Dimension(1, 30));
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pad, c);*/

	FirstOrderConnection = new JRadioButton();
	FirstOrderConnection.setText("First order connections");
	FirstOrderConnection.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(FirstOrderConnection, c);
	y++;
	
	FirstOrderConnectionsWithComplexes = new JRadioButton();
	FirstOrderConnectionsWithComplexes.setText("First order connections with 'NODE_TYPE=COMPLEX'");
	FirstOrderConnectionsWithComplexes.setSelected(false);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(FirstOrderConnectionsWithComplexes, c);
	y++;
	
	SecondOrderConnections = new JRadioButton();
	SecondOrderConnections.setText("Second order connections");
	SecondOrderConnections.setSelected(false);	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(SecondOrderConnections, c);
	y++;
	
	AddFirstNeighbours = new JRadioButton();
	AddFirstNeighbours.setText("Add first neighbours");
	AddFirstNeighbours.setSelected(false);	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(AddFirstNeighbours, c);
	y++;
	
	
	ConnectByShortestPath = new JRadioButton();
	ConnectByShortestPath.setText("Connect by shortest paths");
	ConnectByShortestPath.setSelected(false);	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(ConnectByShortestPath, c);
	y++;
	
	ButtonGroup group = new ButtonGroup();
	group.add(FirstOrderConnection);
	group.add(FirstOrderConnectionsWithComplexes);
	group.add(SecondOrderConnections);	
	group.add(AddFirstNeighbours);
	group.add(ConnectByShortestPath);

	
	TestComponentSignificance = new JCheckBox();
	TestComponentSignificance.setText("test connected component size significance  ");
	TestComponentSignificance.setSelected(false);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	panel.add(TestComponentSignificance, c);
	
	
	TestComponentSignificance.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(TestComponentSignificance.isSelected()) { NumberOfPermutations.setEnabled(true); fixedNodeList.setEnabled(true); }
			else { NumberOfPermutations.setEnabled(false); fixedNodeList.setEnabled(false); }
		}
	    });
	
	label = new JLabel("    number of permutations");
	c = new GridBagConstraints();
	
	c.gridx = 0;
	c.gridy = y;
	panel.add(label, c);
	
	NumberOfPermutations = new JComboBox();
	NumberOfPermutations.addItem("100");
	NumberOfPermutations.addItem("1000");
	NumberOfPermutations.addItem("10000");
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.EAST;
	panel.add(NumberOfPermutations, c);
	NumberOfPermutations.setEnabled(false);

	y++;
	
	label = new JLabel(" List of 'fixed' nodes during sampling:");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	panel.add(label, c);
	
	fixedNodeList = new JTextArea();
	fixedNodeList.setColumns(20);
	fixedNodeList.setRows(5);
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	panel.add(fixedNodeList, c);
	fixedNodeList.setEnabled(false);
	
	
	y++;
	
	
	MakeConnectivityTable = new JCheckBox();
	MakeConnectivityTable.setText("make connectivity table  ");
	MakeConnectivityTable.setSelected(false);
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(MakeConnectivityTable, c);
	
	y++;
	
	MakeSizeSignificanceTest = new JCheckBox();
	MakeSizeSignificanceTest.setText("make global size significance test  ");
	MakeSizeSignificanceTest.setSelected(false);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	panel.add(MakeSizeSignificanceTest, c);
	
	
	MakeSizeSignificanceTest.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(MakeSizeSignificanceTest.isSelected()) { NumberOfPermutationsForSizeTest.setEnabled(true); sizesToTest.setEnabled(true); }
			else { NumberOfPermutationsForSizeTest.setEnabled(false); sizesToTest.setEnabled(false); }
		}
	    });
	
	label = new JLabel("    number of permutations");
	c = new GridBagConstraints();
	
	c.gridx = 0;
	c.gridy = y;
	panel.add(label, c);
	
	NumberOfPermutationsForSizeTest = new JComboBox();
	NumberOfPermutationsForSizeTest.addItem("100");
	NumberOfPermutationsForSizeTest.addItem("1000");
	NumberOfPermutationsForSizeTest.addItem("10000");
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.EAST;
	panel.add(NumberOfPermutationsForSizeTest, c);
	NumberOfPermutationsForSizeTest.setEnabled(false);
	
	y++;
	
	label = new JLabel(" Subnetwork sizes to test:");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	panel.add(label, c);
	
	sizesToTest = new JTextArea();
	sizesToTest.setColumns(20);
	sizesToTest.setRows(5);
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	panel.add(sizesToTest, c);
	sizesToTest.setEnabled(false);

	int nga[] = {10,50,100,150,175,200,250,300,350,400,450,500,550,600,700,800,1000};
	String sizes = "";
	for(int i=0;i<nga.length;i++){
		if(nga[i]<Cytoscape.getCurrentNetwork().getNodeCount())
		     sizes+=""+nga[i]+"\n";
	}
	sizesToTest.setText(sizes);
	
	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
			Vector<String> selected = new Vector<String>();
			Set nodes = Cytoscape.getCurrentNetwork().getSelectedNodes();
			Iterator it = nodes.iterator();
			while(it.hasNext())
			    selected.add(((CyNode)it.next()).getIdentifier());
			
			if(FirstOrderConnection.isSelected()) 				options.methodOfSubnetworkExtraction = SubnetworkProperties.SIMPLY_CONNECT; 
			if(FirstOrderConnectionsWithComplexes.isSelected()) options.methodOfSubnetworkExtraction = SubnetworkProperties.SIMPLY_CONNECT_WITH_COMPLEX_NODES;
			if(SecondOrderConnections.isSelected()) 			options.methodOfSubnetworkExtraction = SubnetworkProperties.SIMPLY_CONNECT_WITH_SECOND_ORDER_CONNECTIONS;
			if(AddFirstNeighbours.isSelected()) 				options.methodOfSubnetworkExtraction = SubnetworkProperties.ADD_FIRST_NEIGHBOURS;
			if(ConnectByShortestPath.isSelected()) 				options.methodOfSubnetworkExtraction = SubnetworkProperties.CONNECT_BY_SHORTEST_PATHS;
			options.checkComponentSignificance = TestComponentSignificance.isSelected();
			options.numberOfPermutations = Integer.parseInt((String)NumberOfPermutations.getSelectedItem());
			options.makeConnectivityTable = MakeConnectivityTable.isSelected();
			options.makeSizeSignificanceTest = MakeSizeSignificanceTest.isSelected();
			options.numberOfPermutationsForSizeTest = Integer.parseInt((String)NumberOfPermutationsForSizeTest.getSelectedItem());
			options.sizesToTest = sizesToTest.getText();
		
			options.fixedNodeList = new Vector<String>();
			String text = fixedNodeList.getText();
			StringTokenizer st = new StringTokenizer(text," \t\n\r,;");
			while(st.hasMoreTokens()){
				options.fixedNodeList.add(st.nextToken());
			}
			
			ExtractSubnetworkTask task = new ExtractSubnetworkTask(graphDocument, selected, options, Cytoscape.getCurrentNetworkView().getVisualStyle());
		    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
		    
		    setVisible(false);
		}
	    });

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	buttonPanel.add(okB);
	buttonPanel.add(cancelB);

	/*
	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(buttonPanel, c);
	*/

	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
