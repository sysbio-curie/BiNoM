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

import java.awt.Component;
import java.util.Vector;
import java.lang.reflect.Method;

abstract public class BioPAXAttrDesc {

    static class CMethod {
	Method mth;
	Class clsobj;

	CMethod(Method mth, Class clsobj) {
	    this.mth = mth;
	    this.clsobj = clsobj;
	}
    }

    static class EditPanel {

	private Component panel;
	private Component actionComp ;
	private Vector<Component> extraCompV;

	EditPanel(Component panel, Component actionComp) {
	    this.panel = panel;
	    this.actionComp = actionComp ;
	    this.extraCompV = new Vector();
	}

	void addExtraComp(Component comp) {
	    extraCompV.add(comp);
	}

	EditPanel(Component panel) {
	    this(panel, null);
	}

	public Component getPanel() {
	    return panel;
	}

	public Component getActionComponent() {
	    return actionComp;
	}

	public Vector<Component> getExtraComponents() {
	    return extraCompV;
	}
    }

    private String name;
    private BioPAXClassDesc clsDesc;

    protected BioPAXAttrDesc(BioPAXClassDesc clsDesc, String name) {
	this.clsDesc = clsDesc;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public String getEditPanelAction(boolean existing_obj) {
	return existing_obj ? ("Changing " + getMetaType()) : ("Adding " + getMetaType());
    }

    public String getMetaType() {
	return getElementType() + " Value";
    }

    public BioPAXClassDesc getClassDesc() {
	return clsDesc;
    }

    boolean isStringValue() {
	return false;
    }

    boolean isObjectValue() {
	return false;
    }

    abstract public String getElementType();

    abstract public Object getValue(BioPAXObject obj);

    abstract public String getStringValue(BioPAXObject obj);

    public void setValue(BioPAXObject obj, Object value) {
    }

    public void addValue(BioPAXObject obj, Object value) {
    }

    public void removeValue(BioPAXObject obj, Object value) {
    }

    abstract public com.ibm.adtech.jastor.Thing getObjectValue(BioPAXObject obj);

    abstract public java.util.Iterator<String> getStringIterator(BioPAXObject obj);

    abstract public java.util.Iterator<com.ibm.adtech.jastor.Thing> getObjectIterator(BioPAXObject obj);

    public EditPanel makeEditPanel(BioPAXObject bobj, Object value, int action, Component frame) {
	return null;
    }

    static protected Method getOne(Vector<CMethod> mthV, String clsname, String mth) {
	if (mthV.size() == 1)
	    return mthV.get(0).mth;
	
	System.err.println("ERROR: " + clsname + " " + mth + " size: " + mthV.size());
	for (int n = 0; n < mthV.size(); n++) {
	    System.err.println(mthV.get(n).mth.getName() + " -> " + mthV.get(n).clsobj.getName());
	}
	if (mthV.size() > 1)
	    return mthV.get(0).mth;
	return null;
    }
}
