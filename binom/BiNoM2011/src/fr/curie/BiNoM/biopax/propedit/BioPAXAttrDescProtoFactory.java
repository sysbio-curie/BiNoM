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

public class BioPAXAttrDescProtoFactory {

    HashMap<String, BioPAXAttrDescProto> attrDescMap;
    String errorMsg;
    BioPAXClassDesc clsDesc;
    BioPAXClassDescFactory clsDescFact;

    public boolean hasError() {
	return errorMsg.length() > 0;
    }

    public String getErrorMessage() {
	return errorMsg;
    }

    public BioPAXAttrDescProtoFactory(BioPAXClassDesc clsDesc, BioPAXClassDescFactory clsDescFact) {
	attrDescMap = new HashMap();
	errorMsg = "";
	this.clsDesc = clsDesc;
	this.clsDescFact = clsDescFact;
    }

    public BioPAXAttrDescProto get(String qname, String name, BioPAXAttrDescProto.Type type) {
	return get(qname, name, type, BioPAXAttrDescProto.SubType.tOfUNKNOWN);
    }

    public BioPAXAttrDescProto get(String qname, String name, BioPAXAttrDescProto.Type type, BioPAXAttrDescProto.SubType subtype) {
	BioPAXAttrDescProto attrDescP = attrDescMap.get(name);
	if (attrDescP != null) {
	    if (type != attrDescP.getType()) {
		errorMsg += qname + ": attribute " + name + " type error, expected " +
		    attrDescP.getType().toString() + ", got " + type.toString() + "\n";
	    }

	    if (attrDescP.getSubType() != BioPAXAttrDescProto.SubType.tOfUNKNOWN) {
		if (subtype != BioPAXAttrDescProto.SubType.tOfUNKNOWN &&
		    subtype != attrDescP.getSubType()) {
		    errorMsg += qname + ": attribute " + name + " subtype error, expected " +
			attrDescP.getSubType().toString() + ", got " + subtype.toString() + "\n";
		}
	    }
	    else
		attrDescP.setSubType(subtype);

	    return attrDescP;
	}

	attrDescP = new BioPAXAttrDescProto(clsDesc, name, type, subtype);
	attrDescMap.put(name, attrDescP);
	return attrDescP;
    }

    public Vector<BioPAXAttrDesc> makeAttrDescV(BioPAX biopax) {
	Vector<BioPAXAttrDesc> v = new Vector();
	Iterator<BioPAXAttrDescProto> iterator = attrDescMap.values().iterator();
	while (iterator.hasNext()) {
	    BioPAXAttrDescProto attrDescProto = iterator.next();
	    BioPAXAttrDesc attrDesc = attrDescProto.makeAttrDesc
		(biopax, clsDescFact);
	    if (attrDesc != null)
		v.add(attrDesc);
	}

	return v;
    }
}
