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
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import java.lang.reflect.*;
import java.awt.Color;

public class BioPAXClassDesc {

	private Class cls; // really necessary ?
	private String name;
	private Vector<BioPAXClassDesc> parentClsDesc_v;
	private Vector<BioPAXClassDesc> childrenDesc_v;
	private Vector<BioPAXAttrDesc> attrDesc_v;
	private BioPAX biopax;
	private int maxdepth;

	private static Class owlFactCls = biopax_DASH_level3_DOT_owlFactory.class;
	private static Class clsParams[] = new Class[1];
	private static Object args[] = new Object[1];

	private Color color;

	static int color_round = 0;
	static Color colors[];

	static {
		clsParams = new Class[1];
		clsParams[0] = com.hp.hpl.jena.rdf.model.Model.class;

		int NN = 5;
		colors = new Color[3 * NN];
		int m = 0;

		for (int n = 0; n < NN; n++) {
			colors[m++] = new Color((int)((n+1) * 255./(NN+1)), 0, 0);
		}

		for (int n = 0; n < NN; n++) {
			colors[m++] = new Color(0, (int)((n+1) * 255./(NN+1)), 0);
		}

		for (int n = 0; n < NN; n++) {
			colors[m++] = new Color(0, 0, (int)((n+1) * 255./(NN+1)));
		}
	}

	public BioPAXClassDesc(BioPAX biopax, Class cls) {
		this.biopax = biopax;
		this.cls = cls;
		this.name = cls.getName();
		this.childrenDesc_v = new Vector<BioPAXClassDesc>();
		this.maxdepth = -1;

		updateColor();
	}

	public void updateColor() {
		if (color != null)
			return;

		List list = getInstanceList(true);
		if (list != null && list.size() > 0) {
			if (color_round >= colors.length)
				color_round = 0;
			setColor(colors[color_round++]);
		}
	}

	public void setParentClassDescV(Vector<BioPAXClassDesc> parentClsDesc_v) {
		this.parentClsDesc_v = parentClsDesc_v;
		Iterator<BioPAXClassDesc> iter = parentClsDesc_v.iterator();
		while (iter.hasNext()) {
			iter.next().addChild(this);
		}
	}

	public void addChild(BioPAXClassDesc child) {
		this.childrenDesc_v.add(child);
	}

	public String getName() {
		return name;
	}

	public void setAttrDescV(Vector<BioPAXAttrDesc> attrDesc_v) {
		this.attrDesc_v = attrDesc_v;
		Collections.sort(this.attrDesc_v, new AttrComp());
	}

	public Vector<BioPAXAttrDesc> getAttrDescV() {
		return attrDesc_v;
	}

	public Vector<BioPAXClassDesc> getParentClassDescV() {
		return parentClsDesc_v;
	}

	public Vector<BioPAXClassDesc> getChildrenClassDescV() {
		return childrenDesc_v;
	}

	public String getCanonName() {
		return BioPAXPropertyUtils.className(name);
	}

	public String toString() {
		String s = name + " {\n";
		Iterator<BioPAXAttrDesc> iter = attrDesc_v.iterator();

		while (iter.hasNext()) {
			BioPAXAttrDesc attrDesc = iter.next();
			s += "    " + attrDesc.toString() + ";\n";
		}

		return s + "}";
	}

	public Class getJClass() {
		return cls;
	}

	public java.util.List getInstanceList(boolean strict) {
		java.util.List list = getInstanceList_p(strict);
		if (list == null)
			return null;

		java.util.Iterator iter = list.iterator();
		java.util.Vector rlist = new java.util.Vector();

		while (iter.hasNext()) {
			com.ibm.adtech.jastor.Thing thing = (com.ibm.adtech.jastor.Thing)iter.next();
			try {
				com.ibm.adtech.jastor.Thing rthing = biopax_DASH_level3_DOT_owlFactory.getThing(thing.uri(), biopax.model);
				if (rthing.getClass() == thing.getClass()) {
					rlist.add(thing);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		Collections.sort(rlist, new ThingComp());

		return rlist;
	}

	private java.util.List getInstanceList_p(boolean strict) {
		if (strict) {
			String mthName = "getAll" + BioPAXPropertyUtils.className(this.getName());
			try {
				Method mth = owlFactCls.getMethod(mthName, clsParams);
				if (mth != null) {
					args[0] = biopax.model;
					return (java.util.List)mth.invoke(null, args);
				}
			} catch(NoSuchMethodException e) {
				//e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}	    

		java.util.List list = getInstanceList(true);
		if (list != null) {
			Iterator<BioPAXClassDesc> iter = childrenDesc_v.iterator();

			while (iter.hasNext()) {
				java.util.List l = iter.next().getInstanceList(false);
				if (l != null) {
					list.addAll(l);
				}
			}
		}

		return list;
	}

	void dumpHierarchy() {
		dumpHierarchy("", maxdepth);
	}

	void dumpHierarchy(String indent, int depth) {
		if (depth != maxdepth) {
			return;
		}

		System.out.print(indent + (getJClass().isInterface() ? "interface " : "class ") + getName() + " {" + maxdepth + "}");

		java.util.List sl = getInstanceList(true);
		java.util.List fl = getInstanceList(false);
		System.out.println(" [" + (sl != null ? sl.size() : 0) + " [" +
				(fl != null ? fl.size() : 0) + "] ]");

		Iterator<BioPAXClassDesc> iter = getChildrenClassDescV().iterator();
		while (iter.hasNext()) {
			iter.next().dumpHierarchy(indent + "\t", depth+1);
		}
	}

	abstract public static class HierarchyScanner {

		abstract public void push(BioPAXClassDesc clsDesc);
		abstract public void pop();
	}

	public void scanHierarchy(HierarchyScanner scanner) {
		scanHierarchy(maxdepth, scanner);
	}

	public void scanHierarchy(int depth, HierarchyScanner scanner) {
		if (depth != maxdepth) {
			return;
		}

		Iterator<BioPAXClassDesc> iter = getChildrenClassDescV().iterator();

		scanner.push(this);
		while (iter.hasNext()) {
			iter.next().scanHierarchy(depth+1, scanner);
		}
		scanner.pop();
	}

	int getMaxDepth() {
		return maxdepth;
	}

	static class AttrComp implements Comparator {

		public int compare(Object o1, Object o2) {
			BioPAXAttrDesc attr1 = (BioPAXAttrDesc)o1;
			BioPAXAttrDesc attr2 = (BioPAXAttrDesc)o2;
			return attr1.getName().compareTo(attr2.getName());
		}
	}

	static class ClassComp implements Comparator {

		public int compare(Object o1, Object o2) {
			BioPAXClassDesc cls1 = (BioPAXClassDesc)o1;
			BioPAXClassDesc cls2 = (BioPAXClassDesc)o2;
			return cls1.getName().compareTo(cls2.getName());
		}
	}

	class ThingComp implements Comparator {

		public int compare(Object o1, Object o2) {
			com.ibm.adtech.jastor.Thing th1 = (com.ibm.adtech.jastor.Thing)o1;
			com.ibm.adtech.jastor.Thing th2 = (com.ibm.adtech.jastor.Thing)o2;
			return th1.uri().toUpperCase().compareTo(th2.uri().toUpperCase());
		}
	}

	public void epilogue() {
		Collections.sort(childrenDesc_v, new ClassComp());
	}

	public static void sort(Vector<BioPAXClassDesc> clsDesc_v) {
		Collections.sort(clsDesc_v, new ClassComp());
	}

	public void computeDepth(int _maxdepth) {
		if (_maxdepth > maxdepth) {
			maxdepth = _maxdepth;
		}

		Iterator<BioPAXClassDesc> iter = childrenDesc_v.iterator();

		while (iter.hasNext()) {
			iter.next().computeDepth(maxdepth+1);
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public BioPAX getBioPAX() {
		return biopax;
	}
}
