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
import java.util.*;

public class BioPAXObjectListAttrDesc extends BioPAXAttrDesc {

    private BioPAXClassDesc objClsDesc;
    private Vector<BioPAXClassDesc> objClsDescV;
    private Vector<CMethod> addMthV;
    private Vector<CMethod> getMthV;
    private Vector<CMethod> rmvMthV;

    private Object getArgs[] = new Object[0];
    private Object updArgs[] = new Object[1];

    public BioPAXObjectListAttrDesc(BioPAXClassDesc clsDesc, String name, Vector<CMethod> getMthV, Vector<CMethod> addMthV, Vector<CMethod> rmvMthV) {
	super(clsDesc, name);
	// for now
	this.objClsDesc = BioPAXClassDescFactory.getInstance(clsDesc.getBioPAX()).getClassDesc(addMthV.get(0).clsobj);
	this.getMthV = getMthV;
	this.addMthV = addMthV;
	this.rmvMthV = rmvMthV;
	objClsDescV = BioPAXObjectAttrDesc.makeObjectClasses(clsDesc.getBioPAX(), addMthV);
    }

    public Iterator getValue(BioPAXObject obj) {
	try {
	    if (getMthV.size() == 1)
		return (Iterator)getMthV.get(0).mth.invoke(obj.getObject(), getArgs);
	    Vector v = new Vector();
	    for (int n = 0; n < getMthV.size(); n++) {
		Iterator iter = (Iterator)getMthV.get(n).mth.invoke(obj.getObject(), getArgs);
		while (iter.hasNext()) {
		    v.add(iter.next());
		}
	    }

	    return v.iterator();
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void addValue(BioPAXObject obj, Object value) {
	try {
	    com.ibm.adtech.jastor.Thing thing = BioPAXObjectFactory.getInstance().convert(value, obj.getBioPAX());
	    updArgs[0] = thing;
	    for (int n = 0; n < addMthV.size(); n++) {
		if (addMthV.get(n).clsobj.isInstance(thing)) {
		    addMthV.get(n).mth.invoke(obj.getObject(), updArgs);
		    return;
		}
	    }
	    System.out.println("ADD METHOD ERROR");
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return;
	}
    }

    public void removeValue(BioPAXObject obj, Object value) {
	try {
	    com.ibm.adtech.jastor.Thing thing = BioPAXObjectFactory.getInstance().convert(value, obj.getBioPAX());
	    updArgs[0] = thing;
	    for (int n = 0; n < rmvMthV.size(); n++) {
		if (rmvMthV.get(n).clsobj.isInstance(thing)) {
		    rmvMthV.get(n).mth.invoke(obj.getObject(), updArgs);
		    return;
		}
	    }
	    System.out.println("RMV METHOD ERROR");
	    /*
	    System.out.println(obj.getObject().getClass().getName() + " " + rmvMth.getName() + " " + value.getClass().getName() + " " + ((com.ibm.adtech.jastor.Thing)value).uri());
	    */
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return;
	}
    }

    public BioPAXClassDesc getObjectClassDesc() {
	return objClsDesc;
    }

    public String toString() {
	return "list<Object<" + getElementType() + "> > " + getName();
    }

    public String getElementType() {
	return BioPAXPropertyUtils.className(objClsDesc.getName());
    }

    public String getStringValue(BioPAXObject obj) {
	return null;
    }

    public com.ibm.adtech.jastor.Thing getObjectValue(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getStringIterator(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getObjectIterator(BioPAXObject obj) {
	return getValue(obj);
    }

    public EditPanel makeEditPanel(BioPAXObject bobj, Object value, int action, Component frame) {
	if (action == BioPAXPropertyEditFrame.UPDATE)
	    action = BioPAXPropertyEditFrame.REMOVE_ADD;
	return BioPAXObjectAttrDesc.makeEditPanelPerform(this, objClsDescV, bobj, value, action, frame);
    }

    public String getMetaType() {
	return "Reference";
    }
}
