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

import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;

import java.lang.reflect.*;

public class BioPAXObjectFactory {

    private Class owlFactCls;
    private Class clsParams[];
    private Object args[];
    static private BioPAXObjectFactory instance;

    private BioPAXObjectFactory() {
	owlFactCls = biopax_DASH_level3_DOT_owlFactory.class;
	clsParams = new Class[2];
	args = new Object[2];
	clsParams[0] = String.class;
	clsParams[1] = com.hp.hpl.jena.rdf.model.Model.class;
    }

    static public BioPAXObjectFactory getInstance() {
	if (instance == null)
	    instance = new BioPAXObjectFactory();
	return instance;
    }

    public BioPAXObject createObject(String type, String uri, BioPAX biopax) {
	try {
	    Method mth = owlFactCls.getMethod("create" + type, clsParams);
	    args[0] = uri;
	    args[1] = biopax.model;

	    BioPAXClassDescFactory clsDescFactory =
		BioPAXClassDescFactory.getInstance(biopax);
	    Object robj = mth.invoke(null, args);
	    BioPAXClassDesc clsDesc = clsDescFactory.getClassDesc(BioPAXPropertyUtils.getClass(robj));
	    return new BioPAXObject(clsDesc, robj, biopax);
	}
	catch(NoSuchMethodException ex) {
	    ex.printStackTrace();
	    return null;
	}
	catch(Exception ex) {
	    ex.printStackTrace();
	    return null;
	}
    }

    /*
    public BioPAXObject getObject(String type, String uri, BioPAX biopax) {

	try {
	    Method mth = owlFactCls.getMethod("get" + type, clsParams);
	    args[0] = uri;
	    args[1] = biopax.model;

	    Object robj = mth.invoke(null, args);

	    BioPAXClassDescFactory clsDescFactory = BioPAXClassDescFactory.getInstance(biopax);
	    BioPAXClassDesc clsDesc = clsDescFactory.getClassDesc(BioPAXPropertyUtils.getClass(robj));
	    return new BioPAXObject(clsDesc, robj, biopax);
	}
	catch(NoSuchMethodException ex) {
	    return null;
	}
	catch(Exception ex) {
	    ex.printStackTrace();
	    return null;
	}
    }
    */

    public BioPAXObject getObject(String uri, BioPAX biopax) {

	try {
		Method mth = owlFactCls.getMethod("getThing", clsParams);
		args[0] = uri;
		args[1] = biopax.model;

		Object robj = mth.invoke(null, args);
		BioPAXClassDescFactory clsDescFactory = BioPAXClassDescFactory.getInstance(biopax);
		BioPAXClassDesc clsDesc = clsDescFactory.getClassDesc(BioPAXPropertyUtils.getClass(robj));
		if (clsDesc.getCanonName().equals("Thing")) {
			return null;
		}
		return new BioPAXObject(clsDesc, robj, biopax);
	}
	catch(NoSuchMethodException ex) {
		ex.printStackTrace();
		return null;
	}
	catch(Exception ex) {
		ex.printStackTrace();
		return null;
	}
    }

    public BioPAXObject getObject(com.ibm.adtech.jastor.Thing robj, BioPAX biopax) {

	BioPAXClassDescFactory clsDescFactory =
	    BioPAXClassDescFactory.getInstance(biopax);
	BioPAXClassDesc clsDesc = clsDescFactory.getClassDesc(BioPAXPropertyUtils.getClass(robj));
	return new BioPAXObject(clsDesc, robj, biopax);
    }

    public com.ibm.adtech.jastor.Thing convert(com.ibm.adtech.jastor.Thing obj, BioPAX biopax) {
	if (obj == null)
	    return null;

	try {
	    Method mth = owlFactCls.getMethod("getThing", clsParams);
	    args[0] = obj.uri();
	    args[1] = biopax.model;
	    
	    return (com.ibm.adtech.jastor.Thing)mth.invoke(null, args);
	}
	catch(Exception ex) {
	    ex.printStackTrace();
	    return null;
	}
    }

    public com.ibm.adtech.jastor.Thing convert(Object obj, BioPAX biopax) {
	return convert((com.ibm.adtech.jastor.Thing)obj, biopax);
    }
}
