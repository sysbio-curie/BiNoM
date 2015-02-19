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



import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import antlr.collections.List;
import Main.Launcher;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;

import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class IndexPathAnalysisDialog extends JDialog {

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 10;
    private JList sourceList;
    private JList targetList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static final java.awt.Font PLAIN_FONT = new java.awt.Font
	("times", java.awt.Font.PLAIN, 12);

    private static final int SCROLL_WIDTH = 380;
    private static final int SCROLL_HEIGHT = 180;

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static IndexPathAnalysisDialog instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    private JCheckBox directedGraphCB;
    private JRadioButton shortestPathRB;
    private JRadioButton suboptimalShortestPathRB;
    private JRadioButton allPathRB;
    
    private JRadioButton outputCurrentNetworkRB = null;    
    private JRadioButton outputNewNetworkRB = null;    
    
    private JCheckBox limitationRadius;
    private JTextField searchRadius;

    
    public static IndexPathAnalysisDialog getInstance() {
	if (instance == null)
	    instance = new IndexPathAnalysisDialog();
	return instance;
    }

    public void raise() {

	Vector data = new Vector();
	java.util.List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
    
	Iterator<CyNode> it = nodes.iterator();

	while(it.hasNext()){
		CyNode n = it.next();
	    data.add(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork().getRow(n).get(CyNetwork.NAME, String.class));
	}
	
	boolean empty = (data.size() == 0);
	if (empty) {
	    data.add("               ");
	    data.add("               ");
	    data.add("               ");
	}

	int indices[] = new int[data.size()];
	for(int i=0;i<indices.length;i++)
	    indices[i] = i;
    
	sourceList.setListData(data);
	int len = data.size();
	sourceList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);

	if (!empty) {
	    sourceList.setSelectedIndices(indices);
	}

	targetList.setListData(data);
	targetList.setVisibleRowCount(sourceList.getVisibleRowCount());

	if (!empty) {
	    targetList.setSelectedIndices(indices);
	}
	
	setSize(700, 800);

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private IndexPathAnalysisDialog() {
	super(new JFrame(), "Index Path Analysis");

	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	
	JLabel label;

	int y = 0;
	int x = 0;

	for (int gx = 0; gx < 5; gx += 2) {
	    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
	}

	x = 1;
	label = new JLabel("Index Path Analysis");
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

	label = new JLabel("Sources");
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
	directedGraphCB = new JCheckBox();
	directedGraphCB.setText("Finding directed path");
	directedGraphCB.setSelected((new StructureAnalysisUtils.Option()).directedGraph);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(directedGraphCB, c);
	y++;

	shortestPathRB = new JRadioButton();
	shortestPathRB.setText("Find shortest paths");
	shortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.ALL_SHORTEST_PATHS);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(shortestPathRB, c);
	y++;

	suboptimalShortestPathRB = new JRadioButton();
	suboptimalShortestPathRB.setText("Find optimal and suboptimal shortest paths");
	suboptimalShortestPathRB.setSelected((new StructureAnalysisUtils.Option()).pathFindMode==StructureAnalysisUtils.Option.SUBOPTIMAL_SHORTEST_PATHS);	
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(suboptimalShortestPathRB, c);
	y++;

	allPathRB = new JRadioButton();
	allPathRB.setText("Find all non-intersecting paths");
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
	limitationRadius.setSelected((new StructureAnalysisUtils.Option()).searchRadius<Integer.MAX_VALUE);
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
	
	x = 1;
	label = new JLabel("Output");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
    
	x += 2;
	group = new ButtonGroup();
	outputCurrentNetworkRB = new JRadioButton("Output in the current network");
	group.add(outputCurrentNetworkRB);

	c = new GridBagConstraints();
	c.anchor = GridBagConstraints.WEST;
	c.gridx = x;
	c.gridy = y;

	panel.add(outputCurrentNetworkRB, c);
	y++;

	outputNewNetworkRB = new JRadioButton("Output in a new network");
	group.add(outputNewNetworkRB);

	c = new GridBagConstraints();
	c.anchor = GridBagConstraints.WEST;
	c.gridx = x;
	c.gridy = y;

	panel.add(outputNewNetworkRB, c);
	y++;
	
	outputCurrentNetworkRB.setSelected(true);    

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object source_idxs[] = sourceList.getSelectedValues();
		    Object target_idxs[] = targetList.getSelectedValues();
		    
		    if ((target_idxs.length > 0)&&(source_idxs.length > 0)) {
		    	
			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();

			Vector<String> sources = new Vector<String>();
			for(int i=0;i<source_idxs.length;i++)
			    sources.add(source_idxs[i].toString());
			Vector<String> targets = new Vector<String>();
			for(int i=0;i<target_idxs.length;i++)
			    targets.add(target_idxs[i].toString());
			
			GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		
			options.directedGraph = directedGraphCB.isSelected();
			if(shortestPathRB.isSelected()) options.pathFindMode = options.ALL_SHORTEST_PATHS;
			if(suboptimalShortestPathRB.isSelected()) options.pathFindMode = options.SUBOPTIMAL_SHORTEST_PATHS;
			if(allPathRB.isSelected()) options.pathFindMode = options.ALL_PATHS;
			if(allPathRB.isSelected()) options.pathFindMode = options.ALL_PATHS;
			options.searchRadius = Double.POSITIVE_INFINITY;
			try{
			    System.out.println("searchRadius = '"+searchRadius.getText().trim()+"'");
			    if(limitationRadius.isSelected()) options.searchRadius = Double.parseDouble(searchRadius.getText().trim());
			}catch(Exception ee){
			    ee.printStackTrace();
			}
			
			IndexPathAnalysisTask task = new IndexPathAnalysisTask
			    (sources,targets,options,
			     Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(), outputCurrentNetworkRB.isSelected());
			fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);		    

			setVisible(false);
		    
			CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
			CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();
		    
			if(task.SelectedNodes != null){
				System.out.println("size  " + task.SelectedNodes.size());
				for (Iterator i = view.getNodeViews().iterator(); i.hasNext(); ) {
					View<CyNode> nView = (View<CyNode>) i.next();
				    CyNode node = nView.getModel();
					System.out.println("name: " + network.getRow(node).get(CyNetwork.NAME, String.class));

				    nView.setVisualProperty(BasicVisualLexicon.NODE_SELECTED, task.SelectedNodes.contains(network.getRow(node).get(CyNetwork.NAME, String.class)));
				    //setSelected(task.SelectedNodes.contains(network.getRow(node).get(CyNetwork.NAME, String.class)));
				}
			}
			
			view.updateView();			
			//if (NetworksUpdateConfirmDialog.getInstance().raise(netwAdd, netwSup, idxs)) {
			//    setVisible(false);
			//}		
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
}

