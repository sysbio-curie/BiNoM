package fr.curie.BiNoM.cytoscape.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.analysis.EdgesFromOtherNetworkTask;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import fr.curie.BiNoM.pathways.utils.BioPAXNamingService;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import giny.view.NodeView;

public class SelectNodesByAttributeSubstringDialog extends JDialog{
	
	public static void main(String args[]){
		try{
		SelectNodesByAttributeSubstringDialog d = new SelectNodesByAttributeSubstringDialog();
		Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("C:/Datas/BinomTest/apoptosis_RN.xgmml"));
		d.pop(gr);
		System.out.println("Finished");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    private static final double COEF_X = 1.24, COEF_Y = 1.05;

    private static final java.awt.Font TITLE_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     14);
    private static final java.awt.Font BOLD_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     12);

    private static final java.awt.Font STD_BOLD_FONT = new java.awt.Font("times",
									 java.awt.Font.BOLD,
									 10);
    private static final java.awt.Font STD_FONT = new java.awt.Font("times",
								    java.awt.Font.PLAIN,
								    10);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    JComboBox listOfAtributes = null;
    JTextField subString = null;
    
    Graph _graph = null;    
    
    public void pop(Graph gr) {
    	
    	_graph = gr;

    	build();

    	setLocation((screenSize.width - getSize().width) / 2,
                        (screenSize.height - getSize().height) / 2);
    	setVisible(true);
        }
    
    
    private void build() {
    	
    	setTitle("Select the attribute name and substring");
    	
    	getContentPane().removeAll();

    	JPanel panel = new JPanel(new GridBagLayout());
    	
    	GridBagConstraints c;
    	
    	JLabel lab1 = new JLabel("Attribute Name");
    	c = new GridBagConstraints();
    	c.gridx = 0;
    	c.gridy = 0;
    	//c.ipadx = 10;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(lab1,c);
    	
    	listOfAtributes = new JComboBox();
    	c = new GridBagConstraints();
    	c.gridx = 0;
    	c.gridy = 1;
    	//c.ipadx = 10;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(listOfAtributes,c);
    	
    	final Vector<String> names = getAllAttributeNames();
    	for(int i=0;i<names.size();i++)
    		listOfAtributes.addItem(names.get(i));
   
    	JLabel lab2 = new JLabel("Substring");
    	c = new GridBagConstraints();
    	c.gridx = 2;
    	c.gridy = 0;
    	//c.ipadx = 10;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(lab2,c);

    	subString = new JTextField(10);
    	//subString.setText("                         ");
    	c = new GridBagConstraints();
    	c.gridx = 2;
    	c.gridy = 1;
    	//c.ipadx = 10;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(subString,c);
    	
    	JPanel buttonPanel = new JPanel();
    	JButton okB = new JButton("OK");
    	
    	buttonPanel.add(okB);
    	okB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Vector<String> nodes = getNodesWithAttributeSubstring(names.get(listOfAtributes.getSelectedIndex()),subString.getText().trim());
    			
    			for(int i=0;i<nodes.size();i++)
    				System.out.println(nodes.get(i));
    			
    			CyNetwork network = Cytoscape.getCurrentNetwork();
    			CyNetworkView view = Cytoscape.getCurrentNetworkView();
    		    
    			for (Iterator i = view.getNodeViewsIterator(); i.hasNext(); ) {
    			    NodeView nView = (NodeView)i.next();
    			    CyNode node = (CyNode)nView.getNode();
    			    nView.setSelected(nodes.contains(node.getIdentifier()));
    			}
    			
    			view.redrawGraph(true, false);
    			
    			
    		    setVisible(false);
    		}
    	    });
    	

    	JButton cancelB = new JButton("Cancel");

    	cancelB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    		    setVisible(false);
    		}
    	    });

    	buttonPanel.add(cancelB);
    	
    	getContentPane().add(panel, BorderLayout.NORTH);    	
    	getContentPane().add(buttonPanel, BorderLayout.SOUTH);    	
    	
    	pack();
    	
    	Dimension size = getSize();
    	setSize(new Dimension((int)(400),
			      (int)(150)));
    	
    }
    
    private Vector<String> getAllAttributeNames(){
    	Vector<String> attrs = new Vector<String>();
    	for(int i=0;i<_graph.Nodes.size();i++){
    		Node n = _graph.Nodes.get(i);
    		for(int j=0;j<n.Attributes.size();j++)
    			if(!attrs.contains(((Attribute)n.Attributes.get(j)).name))
    				attrs.add(((Attribute)n.Attributes.get(j)).name);
    	}
    	Collections.sort(attrs);
    	return attrs;
    }
    
    private Vector<String> getNodesWithAttributeSubstring(String attname, String s){
    	Vector<String> nodeIds = new Vector<String>();
    	for(int i=0;i<_graph.Nodes.size();i++){
    		Node n = _graph.Nodes.get(i);
    		for(int j=0;j<n.Attributes.size();j++)
    			if(((Attribute)n.Attributes.get(j)).name.equals(attname))
    				if(((Attribute)n.Attributes.get(j)).value.contains(s))
    					nodeIds.add(n.Id);
    	}
    	return nodeIds;
    }

    
	
}

