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
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory;

import java.awt.*;
import javax.swing.*;
import java.lang.reflect.*;

public class BioPAXPropertyUtils {

    private static boolean naming_service_mode = true;

    public static class ObjectLabel extends JLabel {

	private String uri;

	ObjectLabel(String uri, String name) {
	    super(name);
	    this.uri = uri;
	}

	ObjectLabel(String name) {
	    super(name);
	    this.uri = null;
	}

	public String getURI() {
	    return uri;
	}
    }

    static class EditLabel extends JLabel {

	private Object value;
	private BioPAXObject bobj;
	private BioPAXAttrDesc attrDesc;

	EditLabel(String name, BioPAXObject bobj, BioPAXAttrDesc attrDesc, Object value) {
	    super(name);
	    this.bobj = bobj;
	    this.attrDesc = attrDesc;
	    this.value = value;
	}

	BioPAXObject getObject() {return bobj;}

	BioPAXAttrDesc getAttrDesc() {return attrDesc;}

	Object getValue() {return value;}
    }

    static class AddValueLabel extends EditLabel {

	AddValueLabel(BioPAXObject bobj, BioPAXAttrDesc attrDesc) {
	    super("add", bobj, attrDesc, null);
	}
    }

    static class UpdateValueLabel extends EditLabel {

	UpdateValueLabel(BioPAXObject bobj, BioPAXAttrDesc attrDesc, Object value) {
	    super("update", bobj, attrDesc, value);
	}
    }

    static class RemoveValueLabel extends EditLabel {

	RemoveValueLabel(BioPAXObject bobj, BioPAXAttrDesc attrDesc, Object value) {
	    super("remove", bobj, attrDesc, value);
	}
    }

    static class RemoveObjectLabel extends EditLabel {

	RemoveObjectLabel(BioPAXObject bobj) {
	    super("remove object", bobj, null, null);
	}
    }

    public static String className(String name) {
	int idx = name.lastIndexOf(".");
	if (idx >= 0) {
	    name = name.substring(idx + 1);
	}

	if (name.endsWith("Impl")) {
	    name = name.substring(0, name.length() - 4);
	}

	return name;
    }

    public static GridBagConstraints makeGridBagConstraints(int x, int y, int width, int height) {
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridwidth = width;
	c.gridheight = height;
	c.ipadx = 10;
	c.fill = GridBagConstraints.NONE;
	c.anchor = GridBagConstraints.WEST;
	c.weightx = 0.;
	c.weighty = 0.;
	return c;
    }

    public static GridBagConstraints makeGridBagConstraints(int x, int y) {
	return makeGridBagConstraints(x, y, 1, 1);
    }


    public static Class getClass(Object robj) {
	return getClass(robj.getClass());
    }

    public static Class getClass(Class cls) {
	if (!cls.isInterface()) { // always this case
	    Class itfs[] = cls.getInterfaces();
	    for (int n = 0; n < itfs.length; n++) {
		if (cls.getName().startsWith(itfs[n].getName())) {
		    return itfs[n];
		}
	    }	    
	}
	return cls;
    }

    public static String getCURI(com.ibm.adtech.jastor.Thing obj, BioPAX biopax) {
	String uri = obj.uri();
	if (uri == null) {
	    System.out.println("URI is null for " + obj);
	    return "";
	}

	if (naming_service_mode) {
	    return BioPAXNamingServiceManager.getNamingService(biopax).getNameByUri(uri);
	}

	int idx = uri.lastIndexOf("#");
	if (idx >= 0)
	    return uri.substring(idx+1);
	return uri;
    }

    public static void setNamingServiceMode(boolean _naming_service_mode) {
	if (naming_service_mode != _naming_service_mode) {
	    naming_service_mode = _naming_service_mode;

	    syncFrames();
	}
    }

    public static void toggleNamingServiceMode() {
	setNamingServiceMode(!naming_service_mode);
    }

    public static boolean getNamingServiceMode() {
	return naming_service_mode;
    }

    public static void syncFrames() {
	BioPAXClassTreeFrame.getInstance().recompute();
	BioPAXPropertyBrowserFrame.getEditInstance().recompute();
	BioPAXPropertyBrowserFrame.getBrowseInstance().recompute();
    }
}
