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

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.lang.reflect.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class BioPAXAttrDescProto {

    public enum Type {
	tUNKNOWN,
	tSTRING,
	tINTEGER,
	tDOUBLE,
	tOBJECT,
	tLIST
    };

    public enum SubType {
	tOfUNKNOWN,
	tOfSTRING,
	tOfINTEGER,
	tOfDOUBLE,
	tOfOBJECT,
    };

    private Type type = Type.tUNKNOWN;
    private SubType subtype = SubType.tOfUNKNOWN;

    private String name;
    private Vector<BioPAXAttrDesc.CMethod> setMthV, getMthV, addMthV, rmvMthV;

    //private Method setMth, getMth, addMth, removeMth;
    private BioPAXClassDesc clsDesc;

    BioPAXAttrDescProto(BioPAXClassDesc clsDesc, String name, Type type, SubType subtype) {
	this.clsDesc = clsDesc;
	this.name = name;
	this.type = type;
	this.subtype = subtype;

	this.setMthV = new Vector();
	this.getMthV = new Vector();
	this.addMthV = new Vector();
	this.rmvMthV = new Vector();
    }

    public Type getType() {
	return type;
    }

    public SubType getSubType() {
	return subtype;
    }

    public void setSubType(SubType subtype) {
	this.subtype = subtype;
    }

    public void addSetMethod(Method mth, Class clsobj) {
	setMthV.add(new BioPAXAttrDesc.CMethod(mth, clsobj));
    }

    public void addGetMethod(Method mth, Class clsobj) {
	getMthV.add(new BioPAXAttrDesc.CMethod(mth, clsobj));
    }

    public void addAddMethod(Method mth, Class clsobj) {
	addMthV.add(new BioPAXAttrDesc.CMethod(mth, clsobj));
    }

    public void addRmvMethod(Method mth, Class clsobj) {
	rmvMthV.add(new BioPAXAttrDesc.CMethod(mth, clsobj));
    }

    /*
    void setSetMethod(Method mth) {
	setMth = mth;
    }

    void setAddMethod(Method mth) {
	addMth = mth;
    }

    void setGetMethod(Method mth) {
	getMth = mth;
    }

    void setRemoveMethod(Method mth) {
	removeMth = mth;
    }
    */

    static final String WARNING = "CLASS_WARNING: ";

    /*
    void setClassObj(Class clsObj) {

	if (this.clsObj != null && this.clsObj != clsObj) {
	    System.err.println(WARNING + clsDesc.getCanonName() + "." + name + " CLSOBJ DIFFERS: " + clsObj.getName() + " vs. " + this.clsObj.getName());
	}

	this.clsObj = clsObj;
    }
    */

    public BioPAXAttrDesc makeAttrDesc(BioPAX biopax, BioPAXClassDescFactory clsDescFact) {

	if (type == Type.tSTRING) {
	    if (getMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
	    if (setMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " SET METHOD IS EMPTY");
	    return new BioPAXStringAttrDesc(clsDesc, name, getMthV, setMthV);
	}

	if (type == Type.tDOUBLE) {
	    if (getMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
	    if (setMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " SET METHOD IS EMPTY");
	    return new BioPAXDoubleAttrDesc(clsDesc, name, getMthV, setMthV);
	}

	if (type == Type.tINTEGER) {
	    if (getMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
	    if (setMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " SET METHOD IS EMPTY");
	    return new BioPAXIntegerAttrDesc(clsDesc, name, getMthV, setMthV);
	}

	if (type == Type.tOBJECT) {
	    /*
	    if (clsObj == null) {
		System.err.println(WARNING + clsDesc.getCanonName() + "." + name + " OBJ_CLASS IS EMPTY");
	    }
	    */
	    if (getMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
	    if (setMthV.size() == 0)
		System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " SET METHOD IS EMPTY");
	    /*
	    if (clsObj == null) {
		return null;
	    }
	    */
	    return new BioPAXObjectAttrDesc(clsDesc, name, getMthV, setMthV);
	}

	if (type == Type.tLIST) {
	    if (subtype == SubType.tOfSTRING) {
		if (getMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
		if (addMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " ADD METHOD IS EMPTY");
		if (rmvMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " REMOVE METHOD IS EMPTY");
		return new BioPAXStringListAttrDesc(clsDesc, name, getMthV, addMthV, rmvMthV);
	    }

	    if (subtype == SubType.tOfDOUBLE) {
		if (getMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
		if (addMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " ADD METHOD IS EMPTY");
		if (rmvMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " REMOVE METHOD IS EMPTY");
		return new BioPAXDoubleListAttrDesc(clsDesc, name, getMthV, addMthV, rmvMthV);
	    }

	    if (subtype == SubType.tOfINTEGER) {
		if (getMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
		if (addMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " ADD METHOD IS EMPTY");
		if (rmvMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " REMOVE METHOD IS EMPTY");
		return new BioPAXIntegerListAttrDesc(clsDesc, name, getMthV, addMthV, rmvMthV);
	    }

	    if (subtype == SubType.tOfOBJECT) {
		/*
		if (clsObj == null) {
		    System.err.println(WARNING + clsDesc.getCanonName() + "." + name + " OBJ_CLASS IS EMPTY");
		}
		*/
		if (getMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " GET METHOD IS EMPTY");
		if (addMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " ADD METHOD IS EMPTY");
		if (rmvMthV.size() == 0)
		    System.out.println(WARNING + clsDesc.getCanonName() + "." + name + " REMOVE METHOD IS EMPTY");

		/*
		if (clsObj == null) {
		    return null;
		}
		*/
		return new BioPAXObjectListAttrDesc(clsDesc, name, getMthV, addMthV, rmvMthV);
	    }
	}

	System.err.println(WARNING + clsDesc.getCanonName() + "." + name + " no type found for " + type);
	return null;
    }
}
