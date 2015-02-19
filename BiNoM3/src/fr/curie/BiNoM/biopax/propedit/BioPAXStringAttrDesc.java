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

import java.lang.reflect.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import fr.curie.BiNoM.pathways.wrappers.*;

public class BioPAXStringAttrDesc extends BioPAXAttrDesc {

    private Method getMth;
    private Method setMth;
    private Object getArgs[] = new Object[0];
    private Object setArgs[] = new Object[1];
    private static final int ROW_CNT = 20;
    private static final int COL_CNT = 80;

    public BioPAXStringAttrDesc(BioPAXClassDesc clsDesc, String name, Vector<CMethod> getMthV, Vector<CMethod> setMthV) {
	super(clsDesc, name);

	getMth = getOne(getMthV, "BioPAXStringAttrDesc", "get");
	setMth = getOne(setMthV, "BioPAXStringAttrDesc", "set");
    }

    public String getValue(BioPAXObject obj) {
	try {
	    if (getMth == null)
		return "";
	    return (String)getMth.invoke(obj.getObject(), getArgs);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return "";
	}
    }

    public void setValue(BioPAXObject obj, Object value) {
	try {
	    if (setMth != null) {
		setArgs[0] = value;
		setMth.invoke(obj.getObject(), setArgs);
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }

    boolean isStringValue() {
	return true;
    }

    public String toString() {
	return "string " + getName();
    }

    public String getElementType() {
	return "String";
    }

    public String getStringValue(BioPAXObject obj) {
	return getValue(obj);
    }

    public com.ibm.adtech.jastor.Thing getObjectValue(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getStringIterator(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getObjectIterator(BioPAXObject obj) {
	return null;
    }

    public EditPanel makeEditPanel(BioPAXObject bobj, Object value, int action, Component frame) {
	return BioPAXStringAttrDesc.makeEditPanelPerform(this, bobj, value, action, frame);
    }

    public static EditPanel makeEditPanelPerform(BioPAXAttrDesc attrDesc, BioPAXObject bobj, Object value, int action, Component frame) {
	JTextArea textArea = new JTextArea(ROW_CNT, COL_CNT);
	textArea.setLineWrap(true);
	if (value != null) {
	    textArea.setText(value.toString());
	}

	JScrollPane scrollPane = new JScrollPane(textArea);
	JButton okButton = new JButton(action == BioPAXPropertyEditFrame.ADD ? "Add Value" : "Update Value");
	okButton.addActionListener(new UpdateValueListener(attrDesc, value, textArea, bobj, bobj.getBioPAX(), action, frame));

	return new EditPanel(scrollPane, okButton);
    }

    static class UpdateValueListener implements ActionListener {

	private BioPAXAttrDesc attrDesc;
	private Object oldValue;
	private JTextArea textArea;
	private BioPAXObject bobj;
	private BioPAX biopax;
	private int action;
	private Component frame;

	UpdateValueListener(BioPAXAttrDesc attrDesc, Object oldValue, JTextArea textArea, BioPAXObject bobj, BioPAX biopax, int action, Component frame) {

	    this.attrDesc = attrDesc;
	    this.oldValue = oldValue;
	    this.textArea = textArea;
	    this.bobj = bobj;
	    this.biopax = biopax;
	    this.action = action;
	    this.frame = frame;
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {

	    String text = textArea.getText().replaceAll("\\\n", " ");

	    if (action == BioPAXPropertyEditFrame.REMOVE_ADD) {
		attrDesc.removeValue(bobj, oldValue);
		attrDesc.addValue(bobj, text);
	    }
	    else if (action == BioPAXPropertyEditFrame.UPDATE) {
		attrDesc.setValue(bobj, text);
	    }
	    else if (action == BioPAXPropertyEditFrame.ADD) {
		attrDesc.addValue(bobj, text);
	    }

	    BioPAXPropertyUtils.syncFrames();
	    frame.setVisible(false);
	}
    }
}

