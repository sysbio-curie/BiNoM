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
package fr.curie.BiNoM.cytoscape.biopax.query;

import cytoscape.CyNetwork;
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
import javax.swing.JSeparator;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.cytoscape.biopax.propedit.*;

import java.io.File;
import java.net.URL;
import java.util.*;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.netwop.*;
import fr.curie.BiNoM.lib.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import edu.rpi.cs.xgmml.*;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;

public class BioPAXSelectEntitiesDialog extends JFrame {

    private BioPAX biopax;
    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    private JTextArea textArea = null;
    private JRadioButton currentNetworkRB = null;    
    private JRadioButton listOfNamesRB = null;    

    private JRadioButton selectAllTypeRB = null;    
    private JRadioButton selectOfTypeRB = null;    
    private JRadioButton selectExceptTypeRB = null;

    private JRadioButton outputCurrentNetworkRB = null;    
    private JRadioButton outputNewNetworkRB = null;    
    
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    private static final java.awt.Font PLAIN_FONT = new java.awt.Font
	("times", java.awt.Font.PLAIN, 12);
    private static final java.awt.Font ITALIC_FONT = new java.awt.Font
	("times", java.awt.Font.ITALIC, 11);
    
    private JList listOfTypes = null;

    private static final double COEF_X = 2, COEF_Y = 1.10;


    public BioPAXSelectEntitiesDialog() {
        super("Select Entities from the Index");
    
	JLabel label;
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;
	int x = 0;

	for (int gx = 0; gx < 5; gx += 2) {
	    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
	}

	x = 1;
	label = new JLabel("Select entities from the Index");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 20;	
	c.gridwidth = 4;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	label = new JLabel("Input");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 3;
	c.weightx = 0.0;
	c.ipady = 40;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	x += 2;

	ButtonGroup group = new ButtonGroup();

	currentNetworkRB = new JRadioButton("From current network");
	group.add(currentNetworkRB);

	currentNetworkRB.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(currentNetworkRB, c);
	y++;

	listOfNamesRB = new JRadioButton("From list of names (ids)");
	group.add(listOfNamesRB);
	listOfNamesRB.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(listOfNamesRB, c);
	y++;

	listOfNamesRB.setSelected(true);

	listOfNamesRB.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    textArea.setEnabled(listOfNamesRB.isSelected());
		}
	    });

	listOfNamesRB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    textArea.setEnabled(listOfNamesRB.isSelected());
		    textArea.setVisible(listOfNamesRB.isSelected());
		}		
	    });
   
	textArea = new JTextArea();
	textArea.setColumns(50);
	textArea.setRows(10);
	textArea.setLineWrap(true);
	//textArea.setPreferredSize(new Dimension(200, 100));
	JScrollPane jpane = new JScrollPane(textArea);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	//c.weightx = 1.0;
	//c.weighty = 1.0;
	c.fill = GridBagConstraints.NONE;
	panel.add(jpane, c);
	y++;
    
	label = new JLabel("Note: in names use '_' instead of spaces");
	label.setFont(ITALIC_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);
	y++;
    
	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	x = 1;
	label = new JLabel("Output");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.ipady = 40;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	x += 2;

	group = new ButtonGroup();
	outputCurrentNetworkRB = new JRadioButton("Output in the current network");
	group.add(outputCurrentNetworkRB);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(outputCurrentNetworkRB, c);
	y++;

	outputNewNetworkRB = new JRadioButton("Output in a new network");
	group.add(outputNewNetworkRB);
	outputNewNetworkRB.setSelected(true);    
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(outputNewNetworkRB, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    BioPAXGraphQuery query = new BioPAXGraphQuery();
                    if(listOfNamesRB.isSelected()){
                    	String text = textArea.getText();
                    	StringTokenizer st = new StringTokenizer(text,"\n,; ");
                    	Vector names = new Vector();
                    	Vector xrefs = new Vector();
                    	while(st.hasMoreTokens()){
			    names.add(st.nextToken());
			    xrefs.add(new Vector());
                    	}
                    	query = BioPAXGraphQuery.convertListOfNamesToQuery(names, xrefs);
                    }else{
			GraphDocument grDoc = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
			query.input = XGMML.convertXGMMLToGraph(grDoc);
			//for(int i=0;i<query.input.Nodes.size();i++)
			//	   System.out.println("Query node : "+((fr.curie.BiNoM.pathways.analysis.structure.Node)query.input.Nodes.get(i)).Id);
                    }
                    int itype = 0, otype = 0;
                    if(currentNetworkRB.isSelected()) itype = BioPAXSelectEntitiesTask.INPUT_CURRENT_NETWORK;
                    if(listOfNamesRB.isSelected()) itype = BioPAXSelectEntitiesTask.INPUT_LISTOF_NAMES;
                    if(outputCurrentNetworkRB.isSelected()) otype = BioPAXSelectEntitiesTask.OUTPUT_CURRENT_NETWORK;
                    if(outputNewNetworkRB.isSelected()) otype = BioPAXSelectEntitiesTask.OUTPUT_NEW_NETWORK;
                    BioPAXSelectEntitiesTask task = new BioPAXSelectEntitiesTask(query,itype,otype);
                    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
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

	getContentPane().setLayout(new BorderLayout());

	jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    public static BioPAXSelectEntitiesDialog instance;

    public static BioPAXSelectEntitiesDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXSelectEntitiesDialog();
	return instance;
    }

    public void raise(BioPAX biopax) {
	this.biopax = biopax;

	setSize(new Dimension(650, 500));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }
}


/*selectAllTypeRB = new JRadioButton("Select all entities types");    
  selectOfTypeRB = new JRadioButton("Select entities of types");    
  selectExceptTypeRB = new JRadioButton("Select entities except these types");    
  ButtonGroup group1 = new ButtonGroup();
  group1.add(selectAllTypeRB);
  group1.add(selectOfTypeRB);
  group1.add(selectExceptTypeRB);
  c.gridy = y++;
  panel.add(selectAllTypeRB,c);
  c.gridy = y++;
  panel.add(selectOfTypeRB,c);
  c.gridy = y++;
  panel.add(selectExceptTypeRB,c);
    
  Vector entities = new Vector();
  Vector numbers = new Vector();
  if(BioPAXIndexRepository.getInstance()!=null)
  if(BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine()!=null)
  BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine().countEntities(entities, numbers);
  Object types[] = entities.toArray();
    
  listOfTypes = new JList();
  listOfTypes.setListData(types);
  int len = types.length;
  listOfTypes.setVisibleRowCount(len < 10 ? len : 10);
  JScrollPane jScrollPane2 = new JScrollPane(listOfTypes);
  c.gridy = y++;
  panel.add(listOfTypes,c);*/
    
/*JSeparator sep1 = new JSeparator();
  sep1.setBackground(Color.gray);
  c.gridy = y++;
  panel.add(sep1,c);*/

