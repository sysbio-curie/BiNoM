package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.utils.GraphUtils;
import fr.curie.BiNoM.pathways.utils.Utils;


/**
 * Graphical interface for the input parameters of the Optimal Intervention Set algorithm.
 * 
 * @author eric
 *
 */
public class OptimalCutSetAnalyzerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int MAX_ROWS = 20;
	private JList sourceList;
	private JList targetList;
	private JList sideList;

	private static final int SCROLL_WIDTH = 210;
	private static final int SCROLL_HEIGHT = 180;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

	public static PathConsistencyAnalyzerDialog instance;
	private JButton okB, cancelB;
	private JScrollPane scrollPane;
	private JRadioButton shortestPathRB;
	private JRadioButton suboptimalShortestPathRB;
	private JRadioButton allPathRB;
	private JRadioButton bergeRB;
	private JRadioButton partialRB;
	private JRadioButton seedRB;

	private JCheckBox limitationRadius;
	private JTextField searchRadius;
	private JTextField maxSetNb;
	private JTextField maxSetSize;

	private JPanel panel; 

	private Vector nodeList;
	
	private JComboBox attNames;
	
	private String selectedAttributeName;
	
	private HashMap<String, String> nodeID2attribute;
	
	public DataPathConsistencyAnalyzer analyzer = null;

	public int result = -1;

	/**
	 * Constructor 
	 * 
	 * @param frame Owner of the window
	 * @param mess title
	 * @param modal boolean
	 */
	public OptimalCutSetAnalyzerDialog(JFrame frame, String mess, boolean modal){
		
		// call JDialog constructor
		super(frame, mess, modal);
		createElements();
	}

	/**
	 * Fill the data for the different elements of the interface.
	 */
	public void fillTheData() {

		nodeList = new Vector();

		// get all node names from the graph 
		Iterator it = analyzer.graph.Nodes.iterator();
		while(it.hasNext()){
			Node n = (Node)it.next();
			nodeList.add(n.Id);
		}
		
		// sort names alphabetically
		Collections.sort(nodeList);

		// fill the JLists with node names
		sourceList.setListData(nodeList);
		sourceList.setVisibleRowCount(sourceList.getVisibleRowCount());
		
		targetList.setListData(nodeList);
		targetList.setVisibleRowCount(targetList.getVisibleRowCount());
		
		sideList.setListData(nodeList);
		sideList.setVisibleRowCount(sideList.getVisibleRowCount());

		// set attribute names ComboBox
		Vector<String> attNamesVector = GraphUtils.getAllAttributeNames(analyzer.graph);
		for (int i=0;i<attNamesVector.size();i++)
			attNames.insertItemAt(attNamesVector.get(i), i);
		attNames.insertItemAt("None", 0);
		attNames.setSelectedIndex(0);

		setSize(770, 740);

		setLocation((screenSize.width - getSize().width) / 2,
				(screenSize.height - getSize().height) / 2);
	}

	/**
	 * Create all the elements of the interface.
	 */
	private void createElements() {

		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c;

		JLabel label;

		int y = 1;
		int x = 1;

		/*
		 * Attribute name selection via ComboBox
		 */
		
		JPanel attPanel = new JPanel(new FlowLayout());
		JLabel attLabel = new JLabel("Add attribute information to gene IDs");
		attPanel.add(attLabel);
		attNames = new JComboBox();
		attPanel.add(attNames);
		attNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = (String) ((JComboBox)e.getSource()).getSelectedItem();
				selectedAttributeName = name;
				setListwithAttributeName();
			}
		});
		
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(attPanel, c);
		
		y++;
		y = GraphicUtils.addHSepPanel(panel, 1, y, 5, 2);
		y++;

		/*
		 * Lists of source, target and side-effect nodes
		 */
		
		label = new JLabel("Source nodes");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		sourceList = new JList();
		sourceList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(sourceList);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y + 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,10,10);
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		label = new JLabel("Target nodes");
		c = new GridBagConstraints();
		c.gridx = x + 2;
		c.gridy = y;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		targetList = new JList();
		targetList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(targetList);
		c = new GridBagConstraints();
		c.gridx = x + 2;
		c.gridy = y + 1;
		c.weightx = 0.5;
		c.insets = new Insets(0,0,10,10);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		label = new JLabel("Side effect nodes");
		c = new GridBagConstraints();
		c.gridx = x + 4;
		c.gridy = y;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		sideList = new JList();
		sideList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(sideList);
		c = new GridBagConstraints();
		c.gridx = x + 4;
		c.gridy = y + 1;
		c.weightx = 0.5;
		c.insets = new Insets(0,0,10,10);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		
		
		/*
		 * set list buttons for source, target and side-effect nodes
		 */
		
		JButton sourceNodesButton = new JButton("set source nodes");
		JButton targetNodesButton = new JButton("set target nodes");
		JButton sideNodesButton = new JButton("set side-effect nodes");
		
		sourceNodesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame w = new JFrame();
				OptimalCutSetGenesDialog dial = new OptimalCutSetGenesDialog(w, "Set source nodes", true);
				dial.setVisible(true);
				if (dial.nodeNameArray.size()>0)
					setSelection(sourceList, dial.nodeNameArray);
				dial.dispose();
				w.dispose();
			}
		});
		
		targetNodesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame w = new JFrame();
				OptimalCutSetGenesDialog dial = new OptimalCutSetGenesDialog(w, "Set target nodes", true);
				dial.setVisible(true);
				if (dial.nodeNameArray.size()>0)
					setSelection(targetList, dial.nodeNameArray);
				dial.dispose();
				w.dispose();
			}
		});
		
		sideNodesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame w = new JFrame();
				OptimalCutSetGenesDialog dial = new OptimalCutSetGenesDialog(w, "Set side effect nodes", true);
				dial.setVisible(true);
				if (dial.nodeNameArray.size()>0)
					setSelection(sideList, dial.nodeNameArray);
				dial.dispose();
				w.dispose();		
			}
		});
		
		y+=2;
		
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel.add(sourceNodesButton, c);
		
		c = new GridBagConstraints();
		c.gridx = x+2;
		c.gridy = y;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_START;
		panel.add(targetNodesButton, c);
		
		c = new GridBagConstraints();
		c.gridx = x+4;
		c.gridy = y;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_START;
		panel.add(sideNodesButton, c);
		
		y++;
		
		y = GraphicUtils.addHSepPanel(panel, 1, y, 5, 2);
		
		y++;
		
		x = 1;

		
		/*
		 * path search methods
		 */
		y++;
		JLabel algoPL = new JLabel("Path search algorithm:");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(algoPL, c);
		
		y++;
	
		shortestPathRB = new JRadioButton();
		shortestPathRB.setText("Shortest paths");
		shortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.ALL_SHORTEST_PATHS);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(shortestPathRB, c);
		y++;

		suboptimalShortestPathRB = new JRadioButton();
		suboptimalShortestPathRB.setText("Optimal and suboptimal shortest paths");
		suboptimalShortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.SUBOPTIMAL_SHORTEST_PATHS);	
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(suboptimalShortestPathRB, c);
		y++;

		allPathRB = new JRadioButton();
		allPathRB.setText("All non-self-intersecting paths");
		allPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.ALL_PATHS);	
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
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
		limitationRadius.setSelected(false);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		p.add(limitationRadius, BorderLayout.WEST);
		
		panel.add(p, c);

		searchRadius = new JTextField(4);
		searchRadius.setText("  "+(new StructureAnalysisUtils.Option()).searchRadius);
		searchRadius.setEnabled(false);
		limitationRadius.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				searchRadius.setEnabled(limitationRadius.isSelected());
			}
		});
		p.add(searchRadius, BorderLayout.CENTER);
		
		y++;
		y = GraphicUtils.addHSepPanel(panel, 1, y, 5, 2);
		y++;
		
		/*
		 * Opt intervention set specific parameters 
		 */
		
		y++;
		JLabel algoL = new JLabel("Minimal intervention sets search algorithm:");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(algoL, c);
		
		y++;
		bergeRB = new JRadioButton();
		bergeRB.setText("Exact solution (Berge's algorithm)");
		bergeRB.setSelected(true);
		bergeRB.setActionCommand("berge");
		bergeRB.addActionListener(this);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(bergeRB, c);
		
		y++;
		partialRB = new JRadioButton();
		partialRB.setText("Approximation solution");
		partialRB.setActionCommand("enum");
		partialRB.addActionListener(this);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(partialRB, c);
		
//		y++;
//		seedRB = new JRadioButton();
//		seedRB.setText("Seed based enumeration");
//		seedRB.setActionCommand("enum");
//		seedRB.addActionListener(this);
//		c = new GridBagConstraints();
//		c.gridx = x;
//		c.gridy = y;
//		c.gridwidth = 3;
//		c.anchor = GridBagConstraints.WEST;
//		panel.add(seedRB, c);
		
		ButtonGroup OCSgroup = new ButtonGroup();
		OCSgroup.add(bergeRB);
		OCSgroup.add(partialRB);
		//OCSgroup.add(seedRB);
		
		y++;
		JPanel p2 = new JPanel(new FlowLayout());
		maxSetSize = new JTextField(4);
		maxSetSize.setText("inf");
		JLabel l1 = new JLabel("Max. set size");
		p2.add(l1);
		p2.add(maxSetSize);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		panel.add(p2, c);

		y++;
		
		JPanel p3 = new JPanel(new FlowLayout());
		JLabel l2 = new JLabel("Max. Nb of (million) intervention sets");
		p3.add(l2);
		maxSetNb = new JTextField(4);
		maxSetNb.setEnabled(false);
		p3.add(maxSetNb);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		panel.add(p3, c);
		
		y = GraphicUtils.addHSepPanel(panel, 1, y, 5, 2);

		
		/*
		 * Ok and cancel buttons
		 */
		
		JPanel buttonPanel = new JPanel();
		okB = new JButton("OK");

		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get the data and launch the optimal intervention set analysis
				
				Object source_idxs[] = sourceList.getSelectedValues();
				Object target_idxs[] = targetList.getSelectedValues();
				Object side_idxs[] = sideList.getSelectedValues();
				
				if (source_idxs.length > 0 && target_idxs.length > 0) {
					
					if (nodeID2attribute != null)
						analyzer.nodeID2attribute = nodeID2attribute;
					
					for (int i=0;i<source_idxs.length;i++) {
						String[] tk = ((String)source_idxs[i]).split("::");
						analyzer.sourceNodes.add(analyzer.graph.getNodeByLabel(tk[0]));
					}
				
					for (int i=0;i<target_idxs.length;i++) {
						String[] tk = ((String)target_idxs[i]).split("::");
						analyzer.targetNodes.add(analyzer.graph.getNodeByLabel(tk[0]));
					}
				
					for (int i=0;i<side_idxs.length;i++) {
						String[] tk = ((String)side_idxs[i]).split("::");
						analyzer.sideNodes.add(analyzer.graph.getNodeByLabel(tk[0]));
					}

					// set search paths options for the analyzer
					if(shortestPathRB.isSelected()) 
						analyzer.searchPathMode = analyzer.SHORTEST_PATHS;
					if(suboptimalShortestPathRB.isSelected()) 
						analyzer.searchPathMode = analyzer.SUBOPTIMAL_SHORTEST_PATHS;
					if(allPathRB.isSelected()) 
						analyzer.searchPathMode = analyzer.ALL_PATHS;
					
					// minimal intervention set options
					if (bergeRB.isSelected())
						analyzer.ocsSearch = analyzer.OCS_BERGE;
					if(partialRB.isSelected())
						analyzer.ocsSearch = analyzer.OCS_PARTIAL;
//					if(seedRB.isSelected())
//						analyzer.ocsSearch = analyzer.OCS_SEED;
					
					
					if(limitationRadius.isSelected())
						analyzer.searchRadius = Double.parseDouble(searchRadius.getText());
					else
						analyzer.searchRadius = Double.MAX_VALUE;
					
					if (maxSetSize.getText().equalsIgnoreCase("inf"))
						analyzer.useMaxSetSize = false;
					else {
						analyzer.useMaxSetSize = true;
						try {
							analyzer.maxSetSize = Integer.parseInt(maxSetSize.getText());
							if (analyzer.maxSetSize <= 0) {
								JOptionPane.showMessageDialog(new Frame(), "Max. set size should be 'inf' or a positive integer > 0.");
								resetAnalyzer();
								return;
							}
						}
						catch (NumberFormatException  nfe) {
							JOptionPane.showMessageDialog(new Frame(), "Max. set size should be 'inf' or a positive integer > 0.");
							resetAnalyzer();
							return;
						}
					}
					
					if (maxSetNb.getText().length()>0) {
						try {
							analyzer.maxSetNb = Long.parseLong(maxSetNb.getText()) * (long) 1e+6;
							if (analyzer.maxSetNb <= 0) {
								JOptionPane.showMessageDialog(new Frame(), "Max. Nb of sets should be a positive integer > 0.");
								resetAnalyzer();
								return;
							}
						}
						catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(new Frame(), "Max. Nb of sets should be a positive integer > 0.");
							resetAnalyzer();
							return;
						}
					}
					
					OptimalCutSetAnalyzerTask task = new OptimalCutSetAnalyzerTask(analyzer);
					fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
					
					result = 1;
					setVisible(false);
					return;
				}
				else {
					// zero selection
					JOptionPane.showMessageDialog(new Frame(), "At least one source node and one target node should be selected.");
					resetAnalyzer();
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
	
	public void resetAnalyzer () {
		analyzer.sourceNodes.clear();
		analyzer.targetNodes.clear();
		analyzer.sideNodes.clear();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("berge")) {
			maxSetSize.setText("inf");
			maxSetNb.setText("");
			maxSetNb.setEnabled(false);
		}
		if (e.getActionCommand().equals("enum")) {
			maxSetSize.setText("10");
			maxSetNb.setText("50");
			maxSetNb.setEnabled(true);
		}
	}
	
	
	/**
	 * Select specific items in a JList.
	 * 
	 * @param list JList reference
	 * @param nodeNameList ArrayList of items to be selected
	 * 
	 * @author ebonnet
	 */
	private void setSelection(JList list, ArrayList<String> itemList) {
		
		// clear selection
		list.clearSelection();
		
		// select items in the list
		ArrayList<Integer> myList = new ArrayList<Integer>();
		for (int i=0;i<list.getModel().getSize();i++) {
			String elt = (String)list.getModel().getElementAt(i);
			
			// element + attribute; separator is '::'
			if (elt.indexOf("::")>=0) {
				String[] tk = elt.split("::");
				String Id = tk[0];
				String att = tk[1];
				att = Utils.replaceString(att, "@", "");

				for (int j=0;j<itemList.size();j++) {

					String item = Utils.replaceString(itemList.get(j), "@", "");

					if (Id.equals(item)) {
						myList.add(i);
						break;
					}
					else if (att.equals(item)) {
						myList.add(i);
						break;
					}
				}
			}
			// element without attribute
			else {
				for (int j=0;j<itemList.size();j++) {

					String item = Utils.replaceString(itemList.get(j), "@", "");

					if (elt.equals(item)) {
						myList.add(i);
						break;
					}
				}
			}
		}
		
		
		// set items in the list as selected
		int[] idx = new int[myList.size()];
		for (int i=0;i<myList.size();i++)
			idx[i] = myList.get(i);
		
		list.setSelectedIndices(idx);
		
	}
	
	/**
	 * Add attribute information to node IDs in the source, target and side-effect node lists.
	 * 
	 */
	public void setListwithAttributeName() {
		
		if (selectedAttributeName.equalsIgnoreCase("none"))
			return;
		
		Vector newList = new Vector();
		nodeID2attribute = new HashMap<String, String>();
		for (int i=0;i<nodeList.size();i++) {
			String str = (String)nodeList.get(i);
			if (str.length()>0) {
				Node n = analyzer.graph.getNode(str);
				String attValue = n.getFirstAttributeValue(selectedAttributeName);
				if (n.Id != null && attValue != null) {
					newList.add(n.Id+"::"+attValue);
					nodeID2attribute.put(n.Id, attValue);
				}
				else if (n.Id != null && attValue == null) {
					newList.add(n.Id);
					nodeID2attribute.put(n.Id, "");
				}
			}
		}
		
		sourceList.setListData(newList);
		sourceList.setVisibleRowCount(sourceList.getVisibleRowCount());
		
		targetList.setListData(newList);
		targetList.setVisibleRowCount(targetList.getVisibleRowCount());
		
		sideList.setListData(newList);
		sideList.setVisibleRowCount(sideList.getVisibleRowCount());
		
	}
	
}
