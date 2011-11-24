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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class BioPAXObjectAttrDesc extends BioPAXAttrDesc {

    private BioPAXClassDesc objClsDesc;
    private Vector<BioPAXClassDesc> objClsDescV;

    private Vector<CMethod> setMthV;
    private Vector<CMethod> getMthV;
    private Object getArgs[] = new Object[0];
    private Object setArgs[] = new Object[1];

    public BioPAXObjectAttrDesc(BioPAXClassDesc clsDesc, String name, Vector<CMethod> getMthV, Vector<CMethod> setMthV) {
	super(clsDesc, name);

	// for now
	this.objClsDesc = BioPAXClassDescFactory.getInstance(clsDesc.getBioPAX()).getClassDesc(getMthV.get(0).clsobj);
	this.getMthV = getMthV;
	this.setMthV = setMthV;
	objClsDescV = makeObjectClasses(clsDesc.getBioPAX(), getMthV);
    }

    public com.ibm.adtech.jastor.Thing getValue(BioPAXObject obj) {
	try {
	    if (getMthV.size() == 1)
		return (com.ibm.adtech.jastor.Thing)getMthV.get(0).mth.invoke(obj.getObject(), getArgs);

	    for (int n = 0; n < getMthV.size(); n++) {
		com.ibm.adtech.jastor.Thing thing = (com.ibm.adtech.jastor.Thing)
		    getMthV.get(n).mth.invoke(obj.getObject(), getArgs);
		if (thing != null)
		    return thing;
	    }

	    return null;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void setValue(BioPAXObject obj, Object value) {
	try {
	    com.ibm.adtech.jastor.Thing thing = BioPAXObjectFactory.getInstance().convert(value, obj.getBioPAX());
	    setArgs[0] = thing;
	    for (int n = 0; n < setMthV.size(); n++) {
		if (setMthV.get(n).clsobj.isInstance(thing) || thing == null) {
		    setMthV.get(n).mth.invoke(obj.getObject(), setArgs);
		    return;
		}
	    }
	    System.out.println("SET METHOD ERROR");
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }

    boolean isObjectValue() {
	return true;
    }

    public BioPAXClassDesc getObjectClassDesc() {
	return objClsDesc;
    }

    public String toString() {
	return "Object<" + getElementType() + "> " + getName();
    }

    public String getElementType() {
	return BioPAXPropertyUtils.className(objClsDesc.getName());
    }

    public String getStringValue(BioPAXObject obj) {
	return null;
    }

    public com.ibm.adtech.jastor.Thing getObjectValue(BioPAXObject obj) {
	return getValue(obj);
    }

    public java.util.Iterator getStringIterator(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getObjectIterator(BioPAXObject obj) {
	return null;
    }

    public EditPanel makeEditPanel(BioPAXObject bobj, Object value, int action, Component frame) {
	return makeEditPanelPerform(this, objClsDescV, bobj, value, action, frame);
    }

    static class ObjectItem {

	com.ibm.adtech.jastor.Thing obj;
	BioPAX biopax;
	private static final String INDENT = "  ";
	static boolean display_classes = false;

	ObjectItem() {
	    this(null, null);
	}

	ObjectItem(com.ibm.adtech.jastor.Thing obj, BioPAX biopax) {
	    this.obj = obj;
	    this.biopax = biopax;
	}

	public String toString() {
	    String extra = (display_classes && obj != null ? " [" + BioPAXPropertyUtils.className(obj.getClass().getName()) + "]" : "");
	    return INDENT + (obj == null ? "NULL" : BioPAXPropertyUtils.getCURI(obj, biopax)) + extra;
	}

	public boolean equals(Object obj) {
	    if (!(obj instanceof ObjectItem))
		return false;
	    return obj.toString().equals(toString());
	}

	static boolean displayClasses() {
	    return display_classes;
	}

	static void displayClasses(boolean _display_classes) {
	    display_classes = _display_classes;
	}

	static void updateButton(JButton b) {
	    b.setText(displayClasses() ? "No Class" : "Display Class");
	}
    }

    public static EditPanel makeEditPanelPerform(BioPAXAttrDesc attrDesc, Vector<BioPAXClassDesc> objClsDescV, BioPAXObject bobj, Object value, int action, Component frame) {

	JScrollPane scrollPane = new JScrollPane();
	Vector listData = new Vector();
	for (int n = 0; n < objClsDescV.size(); n++) {
	    Iterator iter = objClsDescV.get(n).getInstanceList(false).iterator();;
	    while (iter.hasNext()) {
		listData.add(new ObjectItem((com.ibm.adtech.jastor.Thing)iter.next(), bobj.getBioPAX()));
	    }
	}

	if (value != null) {
	    listData.add(new ObjectItem());
	}

	JList list = new JList();
	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	list.setListData(listData);
	scrollPane.setViewportView(list);

	if (value != null) {
	    list.setSelectedValue(new ObjectItem((com.ibm.adtech.jastor.Thing)value, bobj.getBioPAX()), true);
	}

	JButton okButton = new JButton(action == BioPAXPropertyEditFrame.ADD ? "Add Value" : "Update Value");
	okButton.addActionListener(new UpdateValueListener(attrDesc, value, list, bobj, bobj.getBioPAX(), action, frame));

	EditPanel editPanel = new EditPanel(scrollPane, okButton);
	JButton browseButton = new JButton("Browse Object");
	browseButton.addActionListener(new BrowseObjectListener(list, bobj.getBioPAX()));

	JButton displayClassButton = new JButton();
	displayClassButton.addActionListener(new DisplayClassListener(list));
	ObjectItem.updateButton(displayClassButton);

	editPanel.addExtraComp(browseButton);
	editPanel.addExtraComp(displayClassButton);
	return editPanel;
    }

    public String getMetaType() {
	return "Reference";
    }

    static class DActionListener {

	protected JList list;
	protected BioPAX biopax;

	protected DActionListener(JList list, BioPAX biopax) {
	    this.list = list;
	    this.biopax = biopax;
	}
    }

    static class BrowseObjectListener extends DActionListener implements ActionListener {

	BrowseObjectListener(JList list, BioPAX biopax) {
	    super(list, biopax);
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
	    ObjectItem oitem = (ObjectItem)list.getSelectedValue();
	    
	    if (oitem != null) {
		BioPAXObject bobj = BioPAXObjectFactory.getInstance().getObject(oitem.obj, biopax);
		BioPAXPropertyBrowserFrame.getBrowseInstance().addTabbedPane(bobj, biopax, true);
		BioPAXPropertyBrowserFrame.getBrowseInstance().setVisible(true);
	    }
	}
    }

    static class DisplayClassListener implements ActionListener {

	JList list;

	DisplayClassListener(JList list) {
	    this.list = list;
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
	    ObjectItem.displayClasses(!ObjectItem.displayClasses());
	    list.repaint();
	    ObjectItem.updateButton((JButton)e.getSource());
	}
    }

    static class UpdateValueListener extends DActionListener implements ActionListener {

	private BioPAXAttrDesc attrDesc;
	private Object oldValue;
	private BioPAXObject bobj;
	private Component frame;
	private int action;

	UpdateValueListener(BioPAXAttrDesc attrDesc, Object oldValue, JList list, BioPAXObject bobj, BioPAX biopax, int action, Component frame) {
	    super(list, biopax);
	    this.attrDesc = attrDesc;
	    this.oldValue = oldValue;
	    this.bobj = bobj;
	    this.action = action;
	    this.frame = frame;
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
	    ObjectItem oitem = (ObjectItem)list.getSelectedValue();
	    
	    if (oitem != null) {
		if (action == BioPAXPropertyEditFrame.REMOVE_ADD) {
		    attrDesc.removeValue(bobj, oldValue);
		    if (oitem.obj != null) {
			attrDesc.addValue(bobj, oitem.obj);
		    }
		}
		else if (action == BioPAXPropertyEditFrame.UPDATE) {
		    /*
		    if (oitem.obj == null) {
			attrDesc.removeValue(bobj, oldValue);
		    }
		    else */ {
			attrDesc.setValue(bobj, oitem.obj);
		    }
		}
		else if (action == BioPAXPropertyEditFrame.ADD) {
		    if (oitem.obj != null) {
			attrDesc.addValue(bobj, oitem.obj);
		    }
		}

		BioPAXPropertyUtils.syncFrames();
		frame.setVisible(false);
	    }
	}
    }

    static Vector<BioPAXClassDesc> makeObjectClasses(BioPAX biopax, Vector<CMethod> mthV) {
	Vector<BioPAXClassDesc> v = new Vector();

	for (int n = 0; n < mthV.size(); n++) {
	    v.add(BioPAXClassDescFactory.getInstance(biopax).getClassDesc(mthV.get(n).clsobj));
	}

	return v;
    }
}
