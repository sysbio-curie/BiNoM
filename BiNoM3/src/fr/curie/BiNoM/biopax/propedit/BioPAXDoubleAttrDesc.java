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
import java.util.Vector;

public class BioPAXDoubleAttrDesc extends BioPAXAttrDesc {

    private Method getMth;
    private Method setMth;
    private Object getArgs[] = new Object[0];
    private Object setArgs[] = new Object[1];

    public BioPAXDoubleAttrDesc(BioPAXClassDesc clsDesc, String name, Vector<CMethod> getMthV, Vector<CMethod> setMthV) {
	super(clsDesc, name);

	getMth = getOne(getMthV, "BioPAXDoubleAttrDesc", "get");
	setMth = getOne(setMthV, "BioPAXDoubleAttrDesc", "set");
    }

    public Double getValue(BioPAXObject obj) {
	try {
	    if (getMth == null)
		return null;
	    return (Double)getMth.invoke(obj.getObject(), getArgs);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void setValue(BioPAXObject obj, Double value) {
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

    public String toString() {
	return "double " + getName();
    }

    public String getElementType() {
	return "Double";
    }

    public String getStringValue(BioPAXObject obj) {
	Object value = getValue(obj);
	return value != null ? value.toString() : "";
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
}

