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

import java.util.*;
import java.lang.reflect.*;

public class BioPAXIntegerListAttrDesc extends BioPAXAttrDesc {

    private Method addMth;
    private Method getMth;
    private Method rmvMth;
    private Object getArgs[] = new Object[0];
    private Object setArgs[] = new Object[1];

    public BioPAXIntegerListAttrDesc(BioPAXClassDesc clsDesc, String name, Vector<CMethod> getMthV, Vector<CMethod> addMthV, Vector<CMethod> rmvMthV) {
	super(clsDesc, name);
	getMth = getOne(getMthV, "BioPAXIntegerListAttrDesc", "get");
	addMth = getOne(addMthV, "BioPAXIntegerListAttrDesc", "add");
	rmvMth = getOne(rmvMthV, "BioPAXIntegerListAttrDesc", "rmv");
    }

    public Iterator getValue(BioPAXObject obj) {
	try {
	    if (getMth == null)
		return null;
	    return (Iterator)getMth.invoke(obj.getObject(), getArgs);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public String toString() {
	return "list<integer> " + getName();
    }

    public String getElementType() {
	return "Integer";
    }

    public String getStringValue(BioPAXObject obj) {
	return null;
    }

    public com.ibm.adtech.jastor.Thing getObjectValue(BioPAXObject obj) {
	return null;
    }

    public java.util.Iterator getStringIterator(BioPAXObject obj) {
	return getValue(obj);
    }

    public java.util.Iterator getObjectIterator(BioPAXObject obj) {
	return null;
    }
}
