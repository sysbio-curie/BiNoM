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

//import cytoscape.Cytoscape;

import java.util.*;
import java.lang.reflect.*;
import javax.swing.JOptionPane;
import fr.curie.BiNoM.pathways.wrappers.*;
import com.hp.hpl.jena.rdf.model.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;

public class BioPAXClassDescFactory {

    private HashMap<String, BioPAXClassDesc> clsDescMap;
    private BioPAX biopax;

    private static final String CREATE = "create";
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";

    private static HashMap<BioPAX, BioPAXClassDescFactory> instance_map =
	new HashMap();;
    private static BioPAXClassDescFactory instance;

    private static HashMap<String, Boolean> skipMap;

    static {
	skipMap = new HashMap();

	skipMap.put("getClass", true);
	skipMap.put("addedStatement", true);
	skipMap.put("removedStatement", true);
	skipMap.put("addedStatements", true);
	skipMap.put("removedStatements", true);
	skipMap.put("getProperty", true);
	skipMap.put("setProperty", true);

	// add (add|get|remove)PARTICIPANTS*

	/*
	skipMap.put("getDATA_DASH_SOURCE", true);
	skipMap.put("addDATA_DASH_SOURCE", true);
	skipMap.put("removeDATA_DASH_SOURCE", true);
	*/
    }
    
    private BioPAXClassDescFactory(BioPAX biopax) {
	this.biopax = biopax;
	clsDescMap = new HashMap();
    }

    static public BioPAXClassDescFactory getInstance(BioPAX biopax) {
	BioPAXClassDescFactory instance = instance_map.get(biopax);
	if (instance == null) {
	    instance = new BioPAXClassDescFactory(biopax);
	    instance_map.put(biopax, instance);
	}

	return instance;
    }

    public BioPAXClassDesc getClassDesc(String name) {
	return clsDescMap.get(name);
    }

    public BioPAXClassDesc getClassDesc(Class cls) {
	BioPAXClassDesc clsDesc = clsDescMap.get(cls.getName());

	if (clsDesc != null)
	    return clsDesc;

	return buildClassDesc(cls);
    }

    public Vector<BioPAXClassDesc> getParentClassDescV(Class cls) {
	Vector<BioPAXClassDesc> parentClassDesc_v = new Vector<BioPAXClassDesc>();
	if (com.ibm.adtech.jastor.Thing.class == cls) {
	    return parentClassDesc_v;
	}

	Class superClass = cls.getSuperclass();
	if (superClass != null) {
	    if (superClass == com.ibm.adtech.jastor.ThingImpl.class) {
		parentClassDesc_v.add(getClassDesc(com.ibm.adtech.jastor.Thing.class));
	    }
	    else
		parentClassDesc_v.add(getClassDesc(superClass));
	}


	Class itfs[] = cls.getInterfaces();
	if (itfs != null) {
	    for (int n = 0; n < itfs.length; n++) {
		parentClassDesc_v.add(getClassDesc(itfs[n]));
	    }
	}

	return parentClassDesc_v;
    }

    private BioPAXClassDesc buildClassDesc(Class cls) {

	BioPAXClassDesc clsDesc = new BioPAXClassDesc(biopax, cls);

	BioPAXAttrDescProtoFactory protoFact = new BioPAXAttrDescProtoFactory(clsDesc, this);

	if (clsDescMap.get(cls.getName()) != null) {
	    System.out.println("oups already found " + cls.getName());
	}

	clsDescMap.put(cls.getName(), clsDesc); // prevents from recursion

	clsDesc.setParentClassDescV(getParentClassDescV(cls));

	Method mths[] = cls.getMethods();
	for (int n = 0; n < mths.length; n++) {
	    Method mth = mths[n];
	    String name = mth.getName();
	    String qname = cls.getName() + "." + name;
	    Class clsParams[] = mth.getParameterTypes();

	    if (skipMethodName(name))
		continue;

	    Class acls = clsParams.length == 0 ? null : clsParams[0];

	    BioPAXAttrDescProto attrDescP = null;

	    if (name.startsWith(ADD)) {
		String attr = name.substring(ADD.length());
		if (skipClass(acls)) {
		    continue;
		}
		if ((attr = makeAttrName(attr)) == null) {
		    continue;
		}

		if (acls == String.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfSTRING);
		    attrDescP.addAddMethod(mth, acls);
		}
		else if (acls == Double.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfDOUBLE);
		    attrDescP.addAddMethod(mth, acls);
		}
		else {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfOBJECT);
		    attrDescP.addAddMethod(mth, acls);
		}
	    }
	    else if (name.startsWith(SET)) {
		String attr = name.substring(SET.length());
		if (skipClass(acls)) {
		    continue;
		}
		if ((attr = makeAttrName(attr)) == null) {
		    continue;
		}

		if (acls == String.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tSTRING);
		    attrDescP.addSetMethod(mth, acls);
		}
		else if (acls == Integer.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tINTEGER);
		    attrDescP.addSetMethod(mth, acls);
		}
		else if (acls == Double.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tDOUBLE);
		    attrDescP.addSetMethod(mth, acls);
		}
		else if (acls == Iterator.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST);
		    System.out.println("ZARBI !!!");
		    attrDescP.addSetMethod(mth, acls);
		}
		else {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tOBJECT);
		    attrDescP.addSetMethod(mth, acls);
		}
	    }

	    if (name.startsWith(REMOVE)) {
		if (clsParams.length == 0)
		    continue;

		String attr = name.substring(REMOVE.length());
		if (skipClass(acls)) {
		    continue;
		}
		if ((attr = makeAttrName(attr)) == null) {
		    continue;
		}

		if (acls == String.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfSTRING);
		    attrDescP.addRmvMethod(mth, acls);
		}
		else if (acls == Integer.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfINTEGER);
		    attrDescP.addRmvMethod(mth, acls);
		}
		else if (acls == Double.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfDOUBLE);
		    attrDescP.addRmvMethod(mth, acls);
		}
		else {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST, BioPAXAttrDescProto.SubType.tOfOBJECT);
		    attrDescP.addRmvMethod(mth, acls);
		}
	    }

	    if (name.startsWith(GET)) {
		String attr = name.substring(GET.length());
		if ((attr = makeAttrName(attr)) == null)
		    continue;

		acls = mth.getReturnType();

		if (acls == String.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tSTRING);
		    attrDescP.addGetMethod(mth, acls);
		}
		else if (acls == Integer.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tINTEGER);
		    attrDescP.addGetMethod(mth, acls);
		}
		else if (acls == Double.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tDOUBLE);
		    attrDescP.addGetMethod(mth, acls);
		}
		else if (acls == Iterator.class) {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tLIST);
		    attrDescP.addGetMethod(mth, acls);
		}
		else {
		    attrDescP = protoFact.get(qname, attr, BioPAXAttrDescProto.Type.tOBJECT);
		    attrDescP.addGetMethod(mth, acls);
		}
	    }
	}

	if (protoFact.hasError()) {
	    JOptionPane.showMessageDialog(new javax.swing.JFrame(),
					  "---------- Property Editor: INTERNAL ERROR on " + cls.getName() + " ----------\n" +
					  protoFact.getErrorMessage());
	    clsDescMap.remove(cls.getName());
	    return null;
	}

	clsDesc.setAttrDescV(protoFact.makeAttrDescV(biopax));

	return clsDesc;
    }

    void printMethods(Class cls) {
	Method mths[] = cls.getMethods();
	System.out.println("class " + cls.getName() + " {");
	for (int n = 0; n < mths.length; n++) {
	    String name = mths[n].getName();
	    Class clsParams[] = mths[n].getParameterTypes();
	    if (skipMethodName(name))
		continue;

	    if (name.startsWith(GET)) {
		String attr = name.substring(3);
		indent();
		System.out.println(mths[n].getReturnType().getName() + " accessor for " + attr + " [" + name + "]");
	    }

	    if (name.startsWith(ADD)) {
		String attr = name.substring(3);
		indent();
		System.out.println((clsParams.length > 0 ? clsParams[0].getName() : "VOID") + " adder for " + attr + " [" + name + "]");
	    }
	    else if (name.startsWith(SET)) {
		String attr = name.substring(3);
		indent();
		System.out.println((clsParams.length > 0 ? clsParams[0].getName() : "VOID") + " modifier for " + attr + " [" + name + "]");
	    }

	    if (name.startsWith(REMOVE)) {
		String attr = name.substring(6);
		indent();
		System.out.println((clsParams.length > 0 ? clsParams[0].getName() : "VOID") + " remover for " + attr + " [" + name + "]");
	    }

	}
	System.out.println("}\n");
    }

    private static void indent() {
	System.out.print("\n    ");
    }

    private static boolean skipMethodName(String name) {
	return skipMap.get(name) != null;
    }

    public void makeClassesDesc(BioPAX biopax) {
	if (biopax == null)
	    return;

	ResIterator iter = biopax.model.listSubjects();

	while (iter.hasNext()) {

	    Resource res = iter.nextResource();

	    try {
		com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(res.getURI(), biopax.model);
		
		getClassDesc(BioPAXPropertyUtils.getClass(thing.getClass()));
	    }
	    catch(Exception e) {
		e.printStackTrace();
		continue;
	    }
	}

	BioPAXClassDesc thingClsDesc = getClassDesc("com.ibm.adtech.jastor.Thing");
	thingClsDesc.computeDepth(0);

	Iterator it = clsDescMap.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry entry = (Map.Entry)it.next();
	    ((BioPAXClassDesc)entry.getValue()).epilogue();
	}
    }

    private static String makeAttrName(String name) {
	int idx = name.indexOf("_as");

	if (idx < 0) {
	    String upname = name.toUpperCase();
	    return name.equals(upname) ? name : null;
	}

	String attr = name.substring(0, idx);
	String clsname = name.substring(idx+3);
	//System.out.println(attr + " :: " + clsname);
	return attr;
    }

    private static boolean skipClass(Class cls) {
	return cls == null || cls.getName().startsWith("com.hp."); // kludge ?
    }
}
