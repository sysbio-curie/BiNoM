package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;
import cytoscape.CyNode;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import giny.view.NodeView;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.*;


public class PathConsistencyAnalyzerDialog extends JDialog{

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JList sourceList;
    private JList targetList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static final int SCROLL_WIDTH = 380;
    private static final int SCROLL_HEIGHT = 180;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static PathConsistencyAnalyzerDialog instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    private JCheckBox directedGraphCB;
    private JRadioButton shortestPathRB;
    private JRadioButton suboptimalShortestPathRB;
    private JRadioButton allPathRB;
    
    private JButton advancedB;
    private JButton infTableB;
    
    private JComboBox ActivityAttrbuteCB;
    private JButton updateActivityAttributeB;
    
    private JCheckBox limitationRadius;
    private JTextField searchRadius;
    
    private JPanel panel; 
    private JPanel panelAct;
    
    private Vector activeNodes;
    private Vector dataActive;
    private Vector targetNodes;
    private Vector dataTarget;
    
    public DataPathConsistencyAnalyzer analyzer = null;
    
    public int result = -1;

    private class NodeComparator implements Comparator {
    	public int compare (Object o1, Object o2) {
    		Node n1 = (Node) o1;
    		Node n2 = (Node) o2;
    		return n1.Id.compareTo(n2.Id);
    	}
    }
    
    public PathConsistencyAnalyzerDialog(JFrame frame, String mess, boolean modal){
    	super(frame, mess, modal);
    	createElements();
    }

    public void fillTheData() {

	dataTarget = new Vector();
	targetNodes = new Vector();
	
	Vector v = GraphUtils.determineRealValuedAttributes(analyzer.graph);
	for(int i=0;i<v.size();i++){
	    String atn = (String)v.get(i);
	    ActivityAttrbuteCB.addItem(atn);
	    if(atn.toLowerCase().equals("activity"))
		ActivityAttrbuteCB.setSelectedIndex(i);
	}
	
	Iterator it = analyzer.graph.Nodes.iterator();
	while(it.hasNext()){
	    Node n = (Node)it.next();
	    dataTarget.add(n.Id);
	    targetNodes.add(n);
	}
	
	Collections.sort(dataTarget);
	Collections.sort(targetNodes, new NodeComparator());
	
	fillActiveNodesList();

	analyzer.getPathwayNodes();
    
	int indicesTarget[] = new int[analyzer.pathwayNodes.size()];
	for(int i=0;i<indicesTarget.length;i++){
	    String id = analyzer.pathwayNodes.get(i).Id;
	    int k =-1;
	    for(int j=0;j<analyzer.graph.Nodes.size();j++)
		if(((Node)analyzer.graph.Nodes.get(j)).Id.equals(id))
		    k = j;
	    indicesTarget[i] = k;
	}
    

	targetList.setListData(dataTarget);
	targetList.setVisibleRowCount(sourceList.getVisibleRowCount());
	targetList.setSelectedIndices(indicesTarget);
	
	setSize(900, 750);
	
	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
    }

    private void createElements() {

	panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;

	JLabel label;

	int y = 0;
	int x = 0;

	for (int gx = 0; gx < 5; gx += 2) {
	    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
	}

	x = 1;
	label = new JLabel("Path Influence Quantification Analyzer: Step 1, Path generation");
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
	
	label = new JLabel("Activity attribute name");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 20;	
	c.gridwidth = 1;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	
	ActivityAttrbuteCB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = x+1;
	c.gridy = y;
	c.gridheight = 1;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(ActivityAttrbuteCB, c);

	//ActivityAttrbuteCB.addItemListener(this);

	updateActivityAttributeB = new JButton("Update list");	
	c = new GridBagConstraints();
	c.gridx = x+2;
	c.gridy = y;
	c.gridheight = 1;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(updateActivityAttributeB, c);
	
	updateActivityAttributeB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    fillActiveNodesList();
		}
	    });
	
	
	y++;
	
	label = new JLabel("Active nodes");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	
	x += 2;
	sourceList = new JList();
	sourceList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(sourceList);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
	panel.add(scrollPane, c);
	y++;

	y = GraphicUtils.addHPadPanel(panel, 0, y, 5);

	x = 1;
	label = new JLabel("Targets");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	x += 2;
	targetList = new JList();
	targetList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(targetList);

	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
	panel.add(scrollPane, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	x = 1;

	shortestPathRB = new JRadioButton();
	shortestPathRB.setText("Shortest paths");
	shortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.ALL_SHORTEST_PATHS);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(shortestPathRB, c);
	y++;
	
	advancedB = new JButton("Advanced options...");
	c = new GridBagConstraints();
	c.gridx = x+2;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(advancedB, c);
	
	infTableB = new JButton("Influence table...");
	c = new GridBagConstraints();
	c.gridx = x+2;
	c.gridy = y+1;
	c.anchor = GridBagConstraints.WEST;
	panel.add(infTableB, c);

	infTableB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
			float inf[][] = analyzer.calcAllToAllInfluences();
			String report = analyzer.printAllToAllInfluenceTable(inf);
			PathConsistencyAnalyzerReportDialog reportForm = new PathConsistencyAnalyzerReportDialog(report);
			reportForm.pop();
		}
	    });
	
	

	suboptimalShortestPathRB = new JRadioButton();
	suboptimalShortestPathRB.setText("Optimal and suboptimal shortest paths");
	suboptimalShortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.SUBOPTIMAL_SHORTEST_PATHS);	
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(suboptimalShortestPathRB, c);
	y++;

	allPathRB = new JRadioButton();
	allPathRB.setText("All non-intersecting paths");
	allPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.ALL_PATHS);	
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(allPathRB, c);
	y++;

	ButtonGroup group = new ButtonGroup();
	group.add(shortestPathRB);
	group.add(suboptimalShortestPathRB);
	group.add(allPathRB);	
	
	y = GraphicUtils.addHPadPanel(panel, 0, y, 5);

	JPanel p = new JPanel(new BorderLayout());
	limitationRadius = new JCheckBox();
	limitationRadius.setText("use finite search radius  ");
	//limitationRadius.setSelected((new StructureAnalysisUtils.Option()).searchRadius<Integer.MAX_VALUE);
	limitationRadius.setSelected(false);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	p.add(limitationRadius, BorderLayout.WEST);
	panel.add(p, c);

	searchRadius = new JTextField(4);
	searchRadius.setText("  "+(new StructureAnalysisUtils.Option()).searchRadius);
	limitationRadius.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    searchRadius.setEnabled(limitationRadius.isSelected());
		}
	    });

	p.add(searchRadius, BorderLayout.CENTER);
	
	y++;
	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object source_idxs[] = sourceList.getSelectedValues();
			Object target_idxs[] = targetList.getSelectedValues();

			if ((target_idxs.length > 0)&&(source_idxs.length > 0)) {

				analyzer.activityAttribute  = (String)ActivityAttrbuteCB.getSelectedItem();
				for(int i=0;i<source_idxs.length;i++){
					int k = dataActive.indexOf((String)source_idxs[i]);
					analyzer.EnrichedNodes.add((Node)activeNodes.get(k));
				}
				Vector temp = new Vector();
				for(int i=0;i<target_idxs.length;i++){
					int k = dataTarget.indexOf((String)target_idxs[i]);;
					temp.add((Node)targetNodes.get(k));
				}
				analyzer.pathwayNodes = temp;
				if(shortestPathRB.isSelected()) analyzer.searchPathMode = analyzer.SHORTEST_PATHS;
				if(suboptimalShortestPathRB.isSelected()) analyzer.searchPathMode = analyzer.SUBOPTIMAL_SHORTEST_PATHS;
				if(allPathRB.isSelected()) analyzer.searchPathMode = analyzer.ALL_PATHS;

				if(limitationRadius.isSelected())
					analyzer.searchRadius = Double.parseDouble(searchRadius.getText());
				else
					analyzer.searchRadius = Double.MAX_VALUE;
				
				PathConsistencyAnalyzerTask task = new PathConsistencyAnalyzerTask(analyzer);
				fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);

				result = 1;

				setVisible(false);
				return;
			}
		}
	    });

	buttonPanel.add(okB);

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	buttonPanel.add(cancelB);
	
	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void fillActiveNodesList(){
    	dataActive = new Vector<Node>();
    	activeNodes = new Vector<Node>();
    	
    	if(ActivityAttrbuteCB.getSelectedIndex()!=-1)
	    for(int i=0;i<analyzer.graph.Nodes.size();i++){
    		Node n = (Node)analyzer.graph.Nodes.get(i);
    		String attr = n.getFirstAttributeValue((String)ActivityAttrbuteCB.getItemAt(ActivityAttrbuteCB.getSelectedIndex()));
    		if(attr!=null)if(!attr.trim().equals("")){
		    dataActive.add(n.Id+"  (Act="+attr+")");
		    activeNodes.add(n);
    		}
	    }
    	
    	analyzer.AllNodesWithActivities = activeNodes;
    	
    	int indicesActive[] = new int[dataActive.size()];
    	for(int i=0;i<indicesActive.length;i++)
    	    indicesActive[i] = i;
        
    	Collections.sort(dataActive);
        
    	sourceList.setListData(dataActive);
    	int len = dataActive.size();
    	sourceList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);
        sourceList.setSelectedIndices(indicesActive);
    }
	
}
