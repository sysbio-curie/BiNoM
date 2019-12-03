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
package fr.curie.BiNoM.biopax.propedit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class BioPAXPropertyEditFrame extends JDialog {

    static Font boldFont = new Font("SansSerif", Font.BOLD, 12);

    public static final int UPDATE = 0x1;
    public static final int REMOVE = 0x2;
    public static final int ADD = 0x4;
    public static final int REMOVE_ADD = REMOVE|ADD;

    BioPAXPropertyEditFrame(BioPAXObject bobj, BioPAXAttrDesc attrDesc, Object value, int action) {

	Container mainPanel = getContentPane();
	mainPanel.setLayout(new BorderLayout());

	JPanel titlePanel = new JPanel();
	JLabel label;

	int label_cnt = 0;
	titlePanel.add(new JLabel());
	label_cnt++;

	label = new JLabel("Class " + BioPAXPropertyUtils.className(attrDesc.getClassDesc().getName()));
	label.setFont(boldFont);
	label.setForeground(attrDesc.getClassDesc().getColor());
	titlePanel.add(label);
	label_cnt++;

	titlePanel.add(new JLabel());
	label_cnt++;

	label = new JLabel("Updating " + BioPAXPropertyUtils.getCURI(bobj.getObject(), bobj.getBioPAX()));
	label.setFont(boldFont);
	titlePanel.add(label);
	label_cnt++;

	label = new JLabel("Attribute " + attrDesc.getName());
	label.setFont(boldFont);
	titlePanel.add(label);
	label_cnt++;

	label = new JLabel("Type " + attrDesc.getElementType());
	label.setFont(boldFont);
	titlePanel.add(label);
	label_cnt++;

	titlePanel.add(new JLabel());
	label_cnt++;

	label = new JLabel(attrDesc.getEditPanelAction(value != null));

	label.setFont(boldFont);
	titlePanel.add(label);
	label_cnt++;

	titlePanel.add(new JLabel());
	label_cnt++;

	titlePanel.setLayout(new GridLayout(label_cnt, 1));

	BioPAXAttrDesc.EditPanel editPanel = attrDesc.makeEditPanel(bobj, value, action, this);

	JPanel buttonPanel = new JPanel();
	Vector<Component> extraCompV = editPanel.getExtraComponents();
	for (int n = 0; n < extraCompV.size(); n++) {
	    buttonPanel.add(extraCompV.get(n));
	}

	if (extraCompV.size() > 0) {
	    buttonPanel.add(new JLabel("               "));
	}

	Component actionComp = editPanel.getActionComponent();
	if (actionComp != null) {
	    buttonPanel.add(actionComp);
	}

	JButton cancelButton = new JButton("Cancel");
	buttonPanel.add(cancelButton);
	cancelButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	mainPanel.add(titlePanel, BorderLayout.NORTH);
	if (editPanel != null && editPanel.getPanel() != null) {
	    mainPanel.add(editPanel.getPanel(), BorderLayout.CENTER);
	}
	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

	setSize(new Dimension(600, 500));
    }
}
