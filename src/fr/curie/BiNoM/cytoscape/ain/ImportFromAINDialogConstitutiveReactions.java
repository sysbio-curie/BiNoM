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

package fr.curie.BiNoM.cytoscape.ain;

import cytoscape.Cytoscape;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;

import java.util.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.pathways.*;

public class ImportFromAINDialogConstitutiveReactions extends JDialog{
	
 public int result = -1; 
 public Vector constitutiveReactions;
 public Vector checkBoxes = new Vector();

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

 private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

 private HashMap addNodes, addEdges, addEdgeNodes;
 private JButton selectdefB;

 private JButton okB, cancelB;

  public ImportFromAINDialogConstitutiveReactions(JFrame frame, String mess, boolean modal) {
	 super(frame, mess, modal);
  }

	
 static class Component {
	JCheckBox cb;
	JComponent component;
	boolean isSelectable;

	Component(JCheckBox cb, JComponent component, boolean isSelectable) {
	    this.cb = cb;
	    this.component = component;
	    this.isSelectable = isSelectable;
	    if (!isSelectable)
		cb.setEnabled(false);
	}

	JComponent getComponent() {return component;}

	boolean isSelected() {
	    if (isSelectable)
		return cb.isSelected();
	    return false;
	}

	void setSelected(boolean select) {
	    if (isSelectable)
		cb.setSelected(select);
	}
 }

 private Component makeCheckBox(boolean adding, String id) {

	/*
	JLabel label = new JLabel(id);
	JCheckBox cb;
	if (adding) {
	    cb = new JCheckBox(addingMessage(adding) + id);
	}
	else {
	    cb = new JCheckBox();
	    label.setForeground(java.awt.Color.GRAY);
	}

	JPanel panel = new JPanel();

	panel.add(cb);
	panel.add(label);

	return new Component(cb, panel, adding);
	*/
	JCheckBox cb = new JCheckBox(id);
	return new Component(cb, cb, adding);
 }


 public void pop() {
	build();

	setLocation((screenSize.width - getSize().width) / 2,
                 (screenSize.height - getSize().height) / 2);
	setVisible(true);
 }

 private void build() {
	 
	constitutiveReactions = SimpleTextInfluenceToBioPAX.getInstance().constitutiveReactions();
	int nmodifications = 0;
	int ncomplexformations = 0;
	for(int i=0;i<constitutiveReactions.size();i++){
		String r = (String)constitutiveReactions.get(i);
		if(r.indexOf("+")>=0)
			ncomplexformations++;
		else
			nmodifications++;
	}
	 
	getContentPane().removeAll();

	JPanel panel = new JPanel(new GridBagLayout());
	JScrollPane scrollPanel = new JScrollPane(panel);

	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Select constitutive reactions to add");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 4;
	c.ipady = 30;
	panel.add(title, c);

	JLabel info = new JLabel(""+nmodifications+" modifications, "+ncomplexformations+" complex formations");

	info.setFont(STD_BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 30;
	c.gridwidth = 4;
	panel.add(info, c);

	y++;

	JButton selectB = new JButton("Select All");

	selectB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent _e) {
		    selectAll(true);
		}
	    });
				  
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 1;
	panel.add(selectB, c);

	JButton deselectB = new JButton("Deselect All");

	deselectB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent _e) {
		    selectAll(false);
		}
	    });
				  
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 1;
	panel.add(deselectB, c);
	
	y++;
	
	
	for(int i=0;i<constitutiveReactions.size();i++){
		JCheckBox cb = (JCheckBox)(makeCheckBox(true, (String)constitutiveReactions.get(i))).component;
	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.gridwidth = 2;
	    c.anchor = GridBagConstraints.WEST;
	    //c.ipady = 1;
	    panel.add(cb, c);
	    checkBoxes.add(cb);
	}
	selectAll(true);


	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");
	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		    result = 1;
		    constitutiveReactions.clear();
			 for(int i=0;i<checkBoxes.size();i++){
				 JCheckBox cb = (JCheckBox)checkBoxes.get(i);
				 if(cb.isSelected())
					 constitutiveReactions.add(cb.getText());
			 }		    
		}
	    });
	

	buttonPanel.add(okB);

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		    result = -1;
		}
	    });

	buttonPanel.add(cancelB);

	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	JPanel mainPanel = new JPanel(new BorderLayout());

	//mainPanel.add(controllPanel, BorderLayout.NORTH);
	mainPanel.add(scrollPanel, BorderLayout.CENTER);
	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

	//panel.add(buttonPanel, c);

	getContentPane().add(mainPanel);
	pack();
	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));
 }



 private void selectAll(boolean select) {
	 for(int i=0;i<checkBoxes.size();i++){
		 JCheckBox cb = (JCheckBox)checkBoxes.get(i);
		 cb.setSelected(select);
	 }
 }

}

