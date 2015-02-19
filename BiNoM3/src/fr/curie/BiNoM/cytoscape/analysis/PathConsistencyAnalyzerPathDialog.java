package fr.curie.BiNoM.cytoscape.analysis;


import Main.Launcher;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.lib.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.util.*;
import java.text.*;


public class PathConsistencyAnalyzerPathDialog extends JDialog {


	    private static final double COEF_X = 1.24, COEF_Y = 1.10;
	    private static final int MAX_ROWS = 20;
	    private JList pathwayList;
	    private JList activeNodeList;
	    private JList pathList;

	    private static final java.awt.Font TITLE_FONT = new java.awt.Font
		("times", java.awt.Font.BOLD, 14);

	    private static final java.awt.Font BOLD_FONT = new java.awt.Font
		("times", java.awt.Font.BOLD, 12);

	    private static final int SCROLL_WIDTH = 350;
	    private static final int SCROLL_HEIGHT = 180;
	    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

	    private JButton okB, cancelB, reportB, makeTargetNetworkB, makeNodeNetworkB, makePathNetworkB, significanceTestB;
	    private JButton selectAllPathB, showMostSignificantB, DesInconsistentNodesB, DesInconsistentPathsB, DesInactivePathsB;
	    private JTextField pathActivityThresh;
	    private JScrollPane scrollPane;
	    
	    private JCheckBox useSignAlgebra;
	    private JCheckBox useAllnodesAttributes;

	    
	    public DataPathConsistencyAnalyzer analyzer = null;
	    
	    public int result = -1;
	    
	    public JLabel labelTargetInfo;
	    public JLabel labelNodeInfo;
	    public JLabel labelPathInfo;
	    
	    private JPanel panel;
	    
	    //public Vector<Vector<String>> selectedActiveNodes;
	    //public Vector<Vector<Vector<String>>> selectedPath;

	    public PathConsistencyAnalyzerPathDialog(JFrame frame, String mess, boolean modal){
	    	super(frame, mess, modal);
	    	try{
	    	createElements();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }

	    public void fillTheData() {
	    	
	    	Vector targetData = new Vector();
	    	for(int i=0;i<analyzer.pathwayNodes.size();i++){
	    		targetData.add(((Node)analyzer.pathwayNodes.get(i)).Id);
	    	}
	    	
	    	pathwayList.setListData(targetData);
	    	pathwayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    	
	    	pathwayList.setSelectedIndex(0);
	    	
	    	/*selectedActiveNodes = new Vector<Vector<String>>();
	    	
	    	for(int i=0;i<analyzer.EnrichedNodePaths.size();i++){
	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(i);
	    		Vector<String> nodeNames = new Vector<String>();
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    			   nodeNames.add(((Node)analyzer.EnrichedNodes.get(j)).Id);
	    			}
	    		}
	    		selectedActiveNodes.add(nodeNames);
	    	}*/
	    	
	    	//selectedPath = new Vector<Vector<Vector<String>>>();
	    	
	    	setSize(760, 600);
	    	setLocation((screenSize.width - getSize().width) / 2,
	                    (screenSize.height - getSize().height) / 2);
	    }

	    /*
	     * Creates all elements
	     */
	    private void createElements() throws Exception{

		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c;

		JLabel label;

		int y = 0;
		int x = 0;

		for (int gx = 0; gx < 5; gx += 2) {
		    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
		}

		x = 1;
		label = new JLabel("Path Influence Quantification Analyzer: Step 2, path analysis");
		label.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 0.0;
		c.ipady = 20;	
		c.gridwidth = 6;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);
		y++;

		y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);
		
		
		label = new JLabel("Target nodes");
		label.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		label = new JLabel("Active nodes");
		label.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		label = new JLabel("Paths");
		label.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		y+=2;
		
		pathwayList = new JList();
		pathwayList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(pathwayList);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH/2, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		pathwayList.addListSelectionListener(new ListSelectionListener(){
		    public void valueChanged(ListSelectionEvent e) {
		    	fillActiveNodesList();
		    	updateLabels();		  
		    	if(activeNodeList.getSelectedIndex()<0){
		    		System.out.println("Removing items from pathList");
		    		pathList.removeAll();
		    	}
		    }		
		}
		);
		

		activeNodeList = new JList();
		activeNodeList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(activeNodeList);
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH/2, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		activeNodeList.addListSelectionListener(new ListSelectionListener(){
		    public void valueChanged(ListSelectionEvent e) {
		    	fillPathList();
		    }		
		}
		);
		

		pathList = new JList();
		pathList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(pathList);
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		pathList.addListSelectionListener(new ListSelectionListener(){
		    public void valueChanged(ListSelectionEvent e) {
		    	updateSelectedPaths();
		    	updateLabels();		    	
		    }		
		}
		);
		

		y+=2;
		makeTargetNetworkB = new JButton("Make target network...");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(makeTargetNetworkB, c);
		
		makeTargetNetworkB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
				makeTargetNetwork();
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		    });
		
		useSignAlgebra = new JCheckBox("Use sign algebra");
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(useSignAlgebra, c);
		
		useSignAlgebra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					analyzer.useSignAlgebra = useSignAlgebra.isSelected();
					updateLabels();
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		    });
		
		
		makeNodeNetworkB = new JButton("Make node network...");
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(makeNodeNetworkB, c);

		makeNodeNetworkB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
				makeActiveNodeNetwork();
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		    });
		
		
		/*makePathNetworkB = new JButton("Make path network...");
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(makePathNetworkB, c);
		
		makePathNetworkB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makePathNetwork();
			}
		    });*/

		
		//y = GraphicUtils.addHPadPanel(panel, 0, y, 3);

		y+=4;
		
		labelTargetInfo = new JLabel("Target:");
		labelTargetInfo.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(labelTargetInfo, c);
		
		y+=2;
		labelNodeInfo = new JLabel("Node:");
		labelNodeInfo.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(labelNodeInfo, c);

		y+=2;
		labelPathInfo = new JLabel("Path:");
		labelPathInfo.setFont(BOLD_FONT);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(labelPathInfo, c);
		
		//y = GraphicUtils.addHPadPanel(panel, 0, y, 5);
		y+=3;
		
		selectAllPathB = new JButton("Select all paths");		
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(selectAllPathB, c);
		
		selectAllPathB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SelectAllPaths();
		}});
		
		
		y+=2;
		
		DesInconsistentNodesB = new JButton("Filter incons.nodes");		
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(DesInconsistentNodesB, c);
		
		DesInconsistentNodesB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				DeselectInconsistentNodes();
		}});
		
		
		DesInconsistentPathsB = new JButton("Filter incons.paths");		
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(DesInconsistentPathsB, c);

		DesInconsistentPathsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				DeselectInconsistentPaths();
		}});
		
		
		DesInactivePathsB = new JButton("Filter inactive paths");		
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(DesInactivePathsB, c);
		
		DesInactivePathsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				DeselectInactivePaths();
		}});		
		
		y+=2;
		  
		final JCheckBox useAllnodesAttributes = new JCheckBox("Use all node attributes");
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(useAllnodesAttributes, c);
		
		useAllnodesAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					analyzer.useAllnodesAttributes = useAllnodesAttributes.isSelected();
					analyzer.checkedForConsistency = false;
			    	updateSelectedPaths();
			    	updateLabels();		    	
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		    });
		
		label = new JLabel("                 Path activity threshold");
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);
		
		pathActivityThresh = new JTextField();
		pathActivityThresh.setText("       "+analyzer.defaultActivityThreshold);
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(pathActivityThresh, c);
		
		
		y+=2;
		
		showMostSignificantB = new JButton("Paths activities...");		
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(showMostSignificantB, c);
		
		showMostSignificantB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String report = analyzer.generatePathActivitiesReport();
				PathConsistencyAnalyzerReportDialog reportForm = new PathConsistencyAnalyzerReportDialog(report);
				reportForm.pop();
			}
		    });
		
		significanceTestB = new JButton("Test significance...");		
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(significanceTestB, c);
		
		significanceTestB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PathConsistencyAnalyzerBootstrapDialog bootstrap = new PathConsistencyAnalyzerBootstrapDialog(null,/*Cytoscape.getDesktop()*/ "Significance analysis", true, analyzer);
				bootstrap.fillTheData();
				bootstrap.setVisible(true);
			}
		    });
		
		
		JPanel buttonPanel = new JPanel();
		
		reportB = new JButton("Get report...");

		reportB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String report = analyzer.generateReport();
				PathConsistencyAnalyzerReportDialog reportForm = new PathConsistencyAnalyzerReportDialog(report);
				reportForm.pop();
			}
		    });

		buttonPanel.add(reportB);
		
		okB = new JButton("OK");

		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = 1; 
				
				setVisible(false);
			}
		    });

		buttonPanel.add(okB);

		/*cancelB = new JButton("Cancel");

		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    setVisible(false);
			}
		    });

		buttonPanel.add(cancelB);*/


		getContentPane().setLayout(new BorderLayout());
		JScrollPane jpane = new JScrollPane(panel);
		getContentPane().add(jpane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    }
	    
	    public void fillActiveNodesList(){
	    	pathList.removeAll();
	    	activeNodeList.removeAll();
	    	Vector nodeData = new Vector();

	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(pathwayList.getSelectedIndex());
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    				nodeData.add(((Node)analyzer.EnrichedNodes.get(j)).Id);
	    			}
	    		}

	    	activeNodeList.setListData(nodeData);
	    	activeNodeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    	if(nodeData.size()>0)
	    		activeNodeList.setSelectedIndex(0);
	    	else
	    		pathList.removeAll();
	    }

	    public void fillPathList(){
	    	
	    	DecimalFormat format = new DecimalFormat("#.##");
	    	
	    	pathList.removeAll();
	    	Vector pathData = new Vector();
	    	
	    	int selectedPathway = pathwayList.getSelectedIndex();
	    	int selectedActiveNode = activeNodeList.getSelectedIndex();
	    	if(selectedPathway>=0)if(selectedActiveNode>=0){
	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(selectedPathway);
	    		int k=0;
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    				if(k==selectedActiveNode){ k=j; break;}
	    				k++;
	    			}
	    		}
    		
	    		Vector<Path> listOfPath = listOfPathways.get(k);
	    		Vector selected = new Vector();
	    		for(int i=0;i<listOfPath.size();i++){
	    			Path pth = listOfPath.get(i);
	    			if(pth.selected)
	    				selected.add(new Integer(i));
	    			float infl = analyzer.calcPathInfluence(pth);
	    			pathData.add(format.format((double)infl)+"  "+pth.label);
	    		}
	    		int inds[] = new int[selected.size()];
	    		for(int i=0;i<inds.length;i++){
	    			inds[i] = ((Integer)selected.get(i)).intValue();
	    		}
		    	pathList.setListData(pathData);
		    	pathList.setSelectedIndices(inds);
	    	}
	    }
	    
	    public void updateLabels(){
	    	DecimalFormat format = new DecimalFormat("#.##");
	    	int selectedPathway = pathwayList.getSelectedIndex();
	    	int selectedActiveNode = activeNodeList.getSelectedIndex();
	    	int selectedPath = pathList.getSelectedIndex();
	    	
	    	Node selectedNode = null;
	    	
	    	if(selectedPathway>=0){

	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(selectedPathway);
	    		int k=0;
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    				if(k==selectedActiveNode){ k=j; selectedNode=analyzer.EnrichedNodes.get(j); break; }
	    				k++;
	    			}
	    		}
	    		
	    		float pathwayInfluence = analyzer.calcPathwayInfluence(listOfPathways);
	    		if(analyzer.SA_observedTargetScores!=null){
	    			labelTargetInfo.setText("Target:  Infl= "+format.format(pathwayInfluence)+ ", pval = "+analyzer.calcPValues(analyzer.SA_observedTargetScores, analyzer.SA_targetScoreRandomDistribution)[selectedPathway]);
	    		}else
	    			labelTargetInfo.setText("Target:  Infl= "+format.format(pathwayInfluence));
	    		
	    		Vector<Path> listOfPath = listOfPathways.get(k);
	    		
		    	if(selectedActiveNode>=0){
		    		
		    		float nodeInfluence = analyzer.calcNodeInfluence(selectedNode, listOfPath, false);
		    		labelNodeInfo.setText("Node:   Activity= "+format.format(analyzer.getNodeActivity(selectedNode))+"   Infl= "+format.format(nodeInfluence)+"  AverPathLen= "+format.format(analyzer.calcAveragePathLength(listOfPath)));
		    		
			    	if(selectedPath>=0){
			    		Path p = listOfPath.get(selectedPath);
			    		float infl = analyzer.calcPathInfluence(p);
			    		labelPathInfo.setText("Path:   Len="+p.length+"   Infl="+format.format((double)infl));
			    		panel.repaint();
			    	}
		    	}
	    	}
	    }
	    
	    public void updateSelectedPaths(){
	    	int selectedPathway = pathwayList.getSelectedIndex();
	    	int selectedActiveNode = activeNodeList.getSelectedIndex();
	    	int selectedPath = pathList.getSelectedIndex();
	    	
	    	if(selectedPathway>=0){
	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(selectedPathway);
	    		int k=0;
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    				if(k==selectedActiveNode){ k=j; break; }
	    				k++;
	    			}
	    		}
	    		Vector<Path> listOfPath = listOfPathways.get(k);
	    		
	    		int sels[] = pathList.getSelectedIndices();
	    		for(int i=0;i<listOfPath.size();i++){
	    			Path p = listOfPath.get(i);
	    			p.selected = false;
	    		}
	    		for(int i=0;i<sels.length;i++){
	    			Path p = listOfPath.get(sels[i]);
	    			p.selected = true;
	    		}
	    		
	    	}
	    	
	    }
	    
	    public void makeActiveNodeNetwork() throws Exception{
	    	int selectedPathway = pathwayList.getSelectedIndex();
	    	int selectedActiveNode = activeNodeList.getSelectedIndex();
	    	int selectedPath = pathList.getSelectedIndex();
	    	Node selectedNode = null;
	    	
	    	System.out.println("IN makeActiveNodeNetwork");
	    	System.out.println(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
	    	
	    	if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
	    	if(selectedPathway>=0){
	    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(selectedPathway);
	    		int k=0;
	    		for(int j=0;j<listOfPathways.size();j++){
	    			Vector<Path> vpath = listOfPathways.get(j);
	    			if(vpath.size()>0){
	    				if(k==selectedActiveNode){ k=j; selectedNode=analyzer.EnrichedNodes.get(j); break; }
	    				k++;
	    			}
	    		}
	    		Vector<Path> listOfPath = listOfPathways.get(k);
	    		Graph graph = analyzer.makePathNetwork(listOfPath);
	    		NetworkFactory.createNetwork(selectedNode.Id+"_"+listOfPath.size(), XGMML.convertGraphToXGMML(graph), Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(), false, null);
	    	}
	    	}
	    	
	    }
	    
	    public void makeTargetNetwork() throws Exception{
	    	int selectedPathway = pathwayList.getSelectedIndex();
	    	if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
		    	if(selectedPathway>=0){
		    		Vector<Vector<Path>> listOfPathways = analyzer.EnrichedNodePaths.get(selectedPathway);
		    		Vector<Path> paths = new Vector<Path>(); 
		    		for(int j=0;j<listOfPathways.size();j++){
		    			Vector<Path> vpath = listOfPathways.get(j);
		    			for(int k=0;k<vpath.size();k++)
		    				paths.add(vpath.get(k));
		    		}
		    		Graph graph = analyzer.makePathNetwork(paths);
		    		NetworkFactory.createNetwork(((Node)analyzer.pathwayNodes.get(selectedPathway)).Id+"_"+paths.size(), XGMML.convertGraphToXGMML(graph), Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(), false, null);
		    	}
	    	}
	    }
	    
	    public void DeselectInconsistentNodes(){
			for(int i=0;i<analyzer.pathwayNodes.size();i++){
				Node pathway = (Node)analyzer.pathwayNodes.get(i);
				Vector<Vector<Path>> listOfNodes = analyzer.EnrichedNodePaths.get(i);
				for(int j=0;j<listOfNodes.size();j++){
					Vector<Path> vpath = listOfNodes.get(j);

					int consCheck = 0;
					int consistency = 1;
					for(int k=0;k<vpath.size();k++){
						Path p = vpath.get(k);
						if(p.influence!=0)if(consistency>0){
							if(consCheck==0) consCheck = p.influence>0?1:-1;
							if((p.influence>0)&&(consCheck<0)) consistency = -1;
							if((p.influence<0)&&(consCheck>0)) consistency = -1;
						}
					}

					if(consistency<0){
						System.out.println("Inconsistent:");
						for(int k=0;k<vpath.size();k++){
							Path p = vpath.get(k);
							System.out.println(pathway.Id+"\t"+p.influence+"\t"+p.label);							
							p.selected = false;
					}
					}
					
					
				}
			}
			updateSelectedPaths();
			updateLabels();
	    }
	    
	    public void DeselectInconsistentPaths(){
	    	if(!analyzer.checkedForConsistency){
	    		analyzer.sortPathsByActivities();
	    		analyzer.checkedForConsistency = true;
	    	}
			for(int i=0;i<analyzer.pathwayNodes.size();i++){
				//Node pathway = (Node)analyzer.pathwayNodes.get(i);
				Vector<Vector<Path>> listOfNodes = analyzer.EnrichedNodePaths.get(i);
				for(int j=0;j<listOfNodes.size();j++){
					Vector<Path> vpath = listOfNodes.get(j);
					for(int k=0;k<vpath.size();k++){
						Path p = vpath.get(k);
						if(p.consistency<0){
							p.selected = false;
							if(analyzer.useAllnodesAttributes)
								System.out.println("Inconsistent path (all nodes are considered): "+p.label);
							else
								System.out.println("Inconsistent path (only selected nodes are considered): "+p.label);
						}
					}
				}
			}
			updateSelectedPaths();
			updateLabels();
	    }
	    
	    public void DeselectInactivePaths(){
	    	
	    	float activityThreshold = 0.5f;
	    	try{
	    		activityThreshold = Float.parseFloat(pathActivityThresh.getText().trim());
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	pathActivityThresh.setText("   "+activityThreshold);
	    	
	    	if(!analyzer.checkedForConsistency){
	    		analyzer.sortPathsByActivities();
	    		analyzer.checkedForConsistency = true;
	    	}
			for(int i=0;i<analyzer.pathwayNodes.size();i++){
				Node pathway = (Node)analyzer.pathwayNodes.get(i);
				Vector<Vector<Path>> listOfNodes = analyzer.EnrichedNodePaths.get(i);
				for(int j=0;j<listOfNodes.size();j++){
					Vector<Path> vpath = listOfNodes.get(j);
					for(int k=0;k<vpath.size();k++){
						Path p = vpath.get(k);
						if(Math.abs(p.summaryActivity)<activityThreshold)
							p.selected = false;
					}
				}
			}
			updateSelectedPaths();
			updateLabels();	    	
	    }
	    
	    public void SelectAllPaths(){
			for(int i=0;i<analyzer.pathwayNodes.size();i++){
				Node pathway = (Node)analyzer.pathwayNodes.get(i);
				Vector<Vector<Path>> listOfNodes = analyzer.EnrichedNodePaths.get(i);
				for(int j=0;j<listOfNodes.size();j++){
					Vector<Path> vpath = listOfNodes.get(j);
					for(int k=0;k<vpath.size();k++){
						Path p = vpath.get(k);
						p.selected = true;
					}
				}
			}
			updateSelectedPaths();
			updateLabels();
	    }

}
