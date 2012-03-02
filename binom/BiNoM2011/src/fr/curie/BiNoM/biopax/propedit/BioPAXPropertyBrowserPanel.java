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

import fr.curie.BiNoM.lib.MLLabel;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class BioPAXPropertyBrowserPanel extends JScrollPane implements MouseListener {

	static Font bigBoldFont = new Font("SansSerif", Font.BOLD, 15);
	static Font boldFont = new Font("SansSerif", Font.BOLD, 12);
	static Font plainFont = new Font("SansSerif", Font.PLAIN, 12);
	static Font undefPlainFont = new Font("SansSerif", Font.ITALIC, 12);
	static Font editFont = new Font("SansSerif", Font.PLAIN, 10);
	static Font editFont2 = new Font("SansSerif", Font.PLAIN, 11);

	static BioPAXNamingService namingService = new BioPAXNamingService();
	static final int LABEL_POS = 1;
	static final int VALUE_POS = 3;
	static final int VALUE_EDIT_POS = 7;

	static final int UPDATE_POS = 3;
	static final int ADD_POS = VALUE_EDIT_POS;
	static final int REMOVE_POS = 5;

	static final int INCR = 5;
	static final int GRID_WIDTH = 7;
	static final int PAD_NORTH = 40;
	static final int PAD_WEST = 40;
	static final int PAD_BETWEEN = 20;
	static final int PAD_BETWEEN2 = 5;
	static final int PAD_VERTICAL = 8;
	static final int EPSILON = 1;

	private BioPAXObject bobj;
	private BioPAX biopax;
	private boolean display_all = false;
	private boolean edit_mode = false;
	private JPanel viewPanel;

	private static final int MAX_LENGTH = 80;

	static final String STRING_UNDEFINED = "<string undefined>";
	static final String OBJECT_UNDEFINED = "<object undefined>";
	static final String EMPTY_STRING_LIST = "<empty string list>";
	static final String EMPTY_OBJECT_LIST = "<empty object list>";
	static final Color EDIT_COLOR = new Color(0, 0, 150);
	BioPAXPropertyBrowserFrame browser_frame;

	private Component makeUpdateValueLabel(BioPAXObject bobj,
			BioPAXAttrDesc attrDesc,
			Object value) {
		JLabel l = new BioPAXPropertyUtils.UpdateValueLabel(bobj, attrDesc, value);
		l.addMouseListener(this);
		l.setFont(editFont);
		l.setForeground(EDIT_COLOR);
		return l;
	}

	private Component makeRemoveValueLabel(BioPAXObject bobj,
			BioPAXAttrDesc attrDesc,
			Object value) {
		JLabel l = new BioPAXPropertyUtils.RemoveValueLabel(bobj, attrDesc, value);
		l.addMouseListener(this);
		l.setFont(editFont);
		l.setForeground(EDIT_COLOR);
		return l;
	}

	private Component makeRemoveObjectLabel(BioPAXObject bobj) {
		JLabel l = new BioPAXPropertyUtils.RemoveObjectLabel(bobj);
		l.addMouseListener(this);
		l.setFont(editFont2);
		l.setForeground(EDIT_COLOR);
		return l;
	}

	private Component makeAddValueLabel(BioPAXObject bobj,
			BioPAXAttrDesc attrDesc) {
		JLabel l = new BioPAXPropertyUtils.AddValueLabel(bobj, attrDesc);
		l.addMouseListener(this);
		l.setFont(editFont);
		l.setForeground(EDIT_COLOR);
		return l;
	}

	private JLabel makeAttrLabel(BioPAXAttrDesc attrDesc) {
		JLabel l = new JLabel(attrDesc.getName());
		l.setFont(boldFont);
		return l;
	}

	private JComponent makeValueLabel(String value) {
		boolean undefined;
		if (value == null || value.trim().length() == 0) {
			value = STRING_UNDEFINED;
			undefined = true;
		}
		else
			undefined = false;

		JComponent l;

		if (value.length() >= MAX_LENGTH) {
			l = new MLLabel(value, MAX_LENGTH);
		}
		else {
			l = new JLabel(value);
		}

		if (undefined) {
			l.setFont(undefPlainFont);
			l.setForeground(Color.GRAY);
		}
		else {
			l.setFont(plainFont);
		}
		return l;
	}

	private JLabel makeObjectLabel(com.ibm.adtech.jastor.Thing obj, BioPAX biopax) {
		JLabel l;
		boolean undefined;
		if (obj == null) {
			l = new BioPAXPropertyUtils.ObjectLabel(OBJECT_UNDEFINED);
			undefined = true;
		}
		else {
			l = new BioPAXPropertyUtils.ObjectLabel(obj.uri(), BioPAXPropertyUtils.getCURI(obj, biopax));
			undefined = false;
		}

		if (undefined) {
			l.setFont(undefPlainFont);
			l.setForeground(Color.GRAY);
		}
		else {
			l.setForeground(Color.BLUE);
			l.setFont(plainFont);
		}
		return l;
	}

	private boolean isValid(String value) {
		if (display_all)
			return true;
		return value != null && value.trim().length() >= 0;
	}

	private boolean isValid(Object obj) {
		if (display_all)
			return true;
		return obj != null;
	}

	/**
	 * Constructor for a panel used to display BioPAX object information.
	 * 
	 * @param bobj
	 * @param biopax
	 * @param display_all
	 * @param edit_mode
	 * @param browser_frame
	 */
	public BioPAXPropertyBrowserPanel(BioPAXObject bobj, BioPAX biopax, boolean display_all, boolean edit_mode, BioPAXPropertyBrowserFrame browser_frame) {

		this.browser_frame = browser_frame;
		this.bobj = bobj;
		this.biopax = biopax;

		viewPanel = new JPanel(new BorderLayout());

		init(display_all, edit_mode);
	}

	/**
	 * Initialization of the panel. Add relevant BioPAX data information.
	 * 
	 * @param display_all
	 * @param edit_mode
	 */
	public void init(boolean display_all, boolean edit_mode) {

		viewPanel.removeAll();

		this.display_all = display_all;
		this.edit_mode = edit_mode;

		BioPAXClassDesc clsDesc = bobj.getClassDesc();

		Vector<BioPAXAttrDesc> attrDesc_v = clsDesc.getAttrDescV();

		int size = 0;
		Iterator<BioPAXAttrDesc> iter = (attrDesc_v == null ? null : attrDesc_v.iterator());

		GridBagConstraints c;

		JPanel dataPanel = new JPanel(new GridBagLayout());

		String uri = bobj.getCURI();
		addPadPanel(0, dataPanel, PAD_NORTH);
		JLabel ll;

		// add BioPAX class name
		ll = new JLabel("Class " + BioPAXPropertyUtils.className(clsDesc.getName()));
		ll.setFont(bigBoldFont);
		ll.setForeground(clsDesc.getColor());
		c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, 1, GRID_WIDTH, 1);
		dataPanel.add(ll, c);

		// add BioPAX object name
		ll = new JLabel("Object " + uri);
		ll.setFont(bigBoldFont);
		//	c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, 2, GRID_WIDTH, 1);
		c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, 2, (edit_mode ? 2 : GRID_WIDTH), 1);
		dataPanel.add(ll, c);

		if (edit_mode) {
			Component comp = makeRemoveObjectLabel(bobj);
			c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS+2, 2, GRID_WIDTH-2, 1);
			dataPanel.add(comp, c);
		}

		/*
		 * now create grid to display BioPAX properties (comment, name, etc.)
		 */
		
		c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS+1, getY(0));
		JPanel padPanel = new JPanel();
		padPanel.setPreferredSize(new Dimension(PAD_BETWEEN, EPSILON));
		dataPanel.add(padPanel, c);

		int value_pos = (edit_mode ? VALUE_EDIT_POS : VALUE_POS);

		if (!edit_mode) {
			c = BioPAXPropertyUtils.makeGridBagConstraints(VALUE_POS+1, getY(0));
			padPanel = new JPanel();
			padPanel.setPreferredSize(new Dimension(PAD_BETWEEN, EPSILON));
			dataPanel.add(padPanel, c);
		}

		c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, 0);
		padPanel = new JPanel();
		padPanel.setPreferredSize(new Dimension(EPSILON, EPSILON));
		c.weightx = 1.0;
		dataPanel.add(padPanel, c);

		c = BioPAXPropertyUtils.makeGridBagConstraints(0, 0);
		padPanel = new JPanel();
		padPanel.setPreferredSize(new Dimension(PAD_WEST, EPSILON));
		dataPanel.add(padPanel, c);

		addPadPanel(3, dataPanel, 2 * PAD_VERTICAL);

		while (iter != null && iter.hasNext()) {
			BioPAXAttrDesc attrDesc = iter.next();
			String strValue = attrDesc.getStringValue(bobj);
			com.ibm.adtech.jastor.Thing objValue = attrDesc.getObjectValue(bobj);
			java.util.Iterator strIter = attrDesc.getStringIterator(bobj);
			java.util.Iterator objIter = attrDesc.getObjectIterator(bobj);
			// 4 cases instead of 8
			//if (strValue != null) {
			
			if (attrDesc.isStringValue()) {
				if (isValid(strValue)) {
					addPadPanel(getY(size) - 1, dataPanel);
					c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
					dataPanel.add(makeAttrLabel(attrDesc), c);
					c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
					dataPanel.add(makeValueLabel(strValue), c);

					if (edit_mode) {
						c = BioPAXPropertyUtils.makeGridBagConstraints(UPDATE_POS, getY(size));
						Component update = makeUpdateValueLabel(bobj, attrDesc, attrDesc.getValue(bobj));
						dataPanel.add(update, c);
					}

					size++;
				}
			}
			else if (attrDesc.isObjectValue()) {
				if (isValid(objValue)) {
					c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
					addPadPanel(getY(size) - 1, dataPanel);
					dataPanel.add(makeAttrLabel(attrDesc), c);
					JComponent l = makeObjectLabel(objValue, biopax);
					c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
					dataPanel.add(l, c);
					l.addMouseListener(this);

					if (edit_mode) {
						c = BioPAXPropertyUtils.makeGridBagConstraints(UPDATE_POS, getY(size));
						Component update = makeUpdateValueLabel(bobj, attrDesc, objValue);
						dataPanel.add(update, c);
					}

					size++;
				}
			}
			else if (strIter != null) {
				if (display_all && (strIter == null || !strIter.hasNext())) {
					c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
					addPadPanel(getY(size) - 1, dataPanel);
					dataPanel.add(makeAttrLabel(attrDesc), c);
					c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
					JComponent l = makeValueLabel(EMPTY_STRING_LIST);
					l.setFont(undefPlainFont);
					l.setForeground(Color.GRAY);
					dataPanel.add(l, c);
					size++;
				}

				boolean dsp = false;
				while (strIter != null && strIter.hasNext()) {
					Object value = strIter.next();
					String strValue2 = value.toString();
					if (isValid(strValue2)) {
						c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
						if (dsp) {
							addPadPanel(getY(size) - 1, dataPanel, 2);
							dataPanel.add(new JLabel(""), c);
						}
						else {
							addPadPanel(getY(size) - 1, dataPanel);
							dataPanel.add(makeAttrLabel(attrDesc), c);
							dsp = true;
						}
						c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
						dataPanel.add(makeValueLabel(strValue2), c);

						if (edit_mode) {
							c = BioPAXPropertyUtils.makeGridBagConstraints(UPDATE_POS, getY(size));
							Component update = makeUpdateValueLabel(bobj, attrDesc, value);
							dataPanel.add(update, c);

							c = BioPAXPropertyUtils.makeGridBagConstraints(REMOVE_POS, getY(size));
							Component remove = makeRemoveValueLabel(bobj, attrDesc, value);
							dataPanel.add(remove, c);
						}

						size++;
					}

				}

				if (edit_mode && (dsp || display_all)) {
					c = BioPAXPropertyUtils.makeGridBagConstraints(ADD_POS, getY(size));
					Component component = makeAddValueLabel(bobj, attrDesc);
					dataPanel.add(component, c);
					size++;
				}
			}
			else if (objIter != null) {
				if (display_all && (objIter == null || !objIter.hasNext())) {
					c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
					addPadPanel(getY(size) - 1, dataPanel);
					dataPanel.add(makeAttrLabel(attrDesc), c);
					c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
					JComponent l = makeValueLabel(EMPTY_OBJECT_LIST);
					l.setFont(undefPlainFont);
					l.setForeground(Color.GRAY);
					dataPanel.add(l, c);
					size++;
				}

				boolean dsp = false;
				while (objIter != null && objIter.hasNext()) {
					com.ibm.adtech.jastor.Thing obj = (com.ibm.adtech.jastor.Thing)objIter.next();
					if (isValid(obj)) {
						c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, getY(size));
						if (dsp) {
							addPadPanel(getY(size) - 1, dataPanel, 2);
							dataPanel.add(new JLabel(""), c);
						}
						else {
							addPadPanel(getY(size) - 1, dataPanel);
							dataPanel.add(makeAttrLabel(attrDesc), c);
							dsp = true;
						}
						JComponent l = makeObjectLabel(obj, biopax);
						l.addMouseListener(this);
						c = BioPAXPropertyUtils.makeGridBagConstraints(value_pos, getY(size));
						dataPanel.add(l, c);

						if (edit_mode) {
							c = BioPAXPropertyUtils.makeGridBagConstraints(UPDATE_POS, getY(size));
							Component update = makeUpdateValueLabel(bobj, attrDesc, obj);
							dataPanel.add(update, c);

							c = BioPAXPropertyUtils.makeGridBagConstraints(REMOVE_POS, getY(size));
							Component remove = makeRemoveValueLabel(bobj, attrDesc, obj);
							dataPanel.add(remove, c);
						}

						size++;
					}
				}

				if (edit_mode && (dsp || display_all)) {
					c = BioPAXPropertyUtils.makeGridBagConstraints(ADD_POS, getY(size));
					Component component = makeAddValueLabel(bobj, attrDesc);
					dataPanel.add(component, c);
					size++;
				}
			}
		}


		viewPanel.add(dataPanel, BorderLayout.NORTH);

		setViewportView(viewPanel);
	}

	BioPAXObject getBioPAXObject() {
		return bobj;
	}

	BioPAX getBioPAX() {
		return biopax;
	}

	void addPadPanel(int y, JPanel dataPanel, int pady) {
		JPanel padPanel = new JPanel();
		padPanel.setPreferredSize(new Dimension(EPSILON, pady));
		GridBagConstraints c = BioPAXPropertyUtils.makeGridBagConstraints(LABEL_POS, y, GRID_WIDTH, 1);
		dataPanel.add(padPanel, c);
	}

	void addPadPanel(int y, JPanel dataPanel) {
		addPadPanel(y, dataPanel, PAD_VERTICAL);
	}

	int getY(int size) {
		return 2 * size + INCR;
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof BioPAXPropertyUtils.ObjectLabel) {
			BioPAXPropertyUtils.ObjectLabel l = (BioPAXPropertyUtils.ObjectLabel)e.getSource();
			String uri = l.getURI();
			if (uri != null) {
				BioPAXObject bobj = BioPAXObjectFactory.getInstance().getObject(uri, getBioPAX());

				browser_frame.addTabbedPane(bobj, getBioPAX(), true);
			}
		}
		else if (e.getSource() instanceof BioPAXPropertyUtils.EditLabel) {
			BioPAXPropertyUtils.EditLabel l = (BioPAXPropertyUtils.EditLabel)e.getSource();
			BioPAXPropertyEditFrame frame = null;

			if (e.getSource() instanceof BioPAXPropertyUtils.UpdateValueLabel) {
				frame = new BioPAXPropertyEditFrame(l.getObject(), l.getAttrDesc(), l.getValue(), BioPAXPropertyEditFrame.UPDATE);
			}
			else if (e.getSource() instanceof BioPAXPropertyUtils.RemoveValueLabel) {
				l.getAttrDesc().removeValue(l.getObject(), l.getValue());
				BioPAXPropertyUtils.syncFrames();
			}
			else if (e.getSource() instanceof BioPAXPropertyUtils.AddValueLabel) {
				frame = new BioPAXPropertyEditFrame(l.getObject(), l.getAttrDesc(), l.getValue(), BioPAXPropertyEditFrame.ADD);
			}
			else if (e.getSource() instanceof BioPAXPropertyUtils.RemoveObjectLabel) {
				String uri = l.getObject().getURI();
				System.out.println("TO IMPLEMENT: removing object: " + uri);
			}

			if (frame != null)
				frame.setVisible(true);
		}
	}
}
