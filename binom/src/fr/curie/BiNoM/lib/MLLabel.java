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
package fr.curie.BiNoM.lib;

import java.io.*;
import java.sql.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MLLabel extends JPanel {

    private static int MAX_LINES = -1;
    private String text;
    private int splitLen = 0;
    private Vector<JLabel> label_v = new Vector();

    public MLLabel() {
	this("", 0);
    }

    public MLLabel(String text) {
	this(text, 0);
    }

    public MLLabel(int splitLen) {
	this("", splitLen);
    }

    public MLLabel(String text, int splitLen) {
	this.splitLen = splitLen;
	setText(text);
    }

    public void setText(String text) {
	String lines[] = getLines(text, splitLen);
	int line_length;
	boolean overflow;
	if (MAX_LINES > 0 && lines.length > MAX_LINES) {
	    line_length = MAX_LINES; 
	    overflow = true; 
	}
	else {
	    line_length = lines.length;
	    overflow = false;
	}

	removeAll();

	setLayout(new GridLayout(line_length + (overflow ? 1 : 0), 1));

	for (int i = 0; i < line_length; i++)
	    add(makeLabel(lines[i]));
	
	if (overflow)
	    add(makeLabel("[...]"));
    }

    public String getText() {
	return text;
    }

    public void setFont(Font font) {
	if (label_v == null)
	    return;
	Iterator<JLabel> iter = label_v.iterator();
	while (iter.hasNext()) {
	    JLabel label = iter.next();
	    label.setFont(font);
	}
    }

    public void setForeground(Color color) {
	if (label_v == null)
	    return;
	Iterator<JLabel> iter = label_v.iterator();
	while (iter.hasNext()) {
	    JLabel label = iter.next();
	    label.setForeground(color);
	}
    }

    public void setBackground(Color color) {
	if (label_v == null)
	    return;
	Iterator<JLabel> iter = label_v.iterator();
	while (iter.hasNext()) {
	    JLabel label = iter.next();
	    label.setBackground(color);
	}
    }

    public void removeAll() {
	super.removeAll();
	label_v = new Vector();
    }

    private static String [] getLines(String text) {
	return getLines(text, 0);
    }

    private static String [] getLines(String text, int splitWidth) {
	StringTokenizer st = new StringTokenizer(text, "\n");
	Vector v = new Vector();
	while (st.hasMoreTokens()) {
	    String s = st.nextToken();
	    String ns = splitText(s, splitWidth);
	    if (ns.equals(s))
		v.addElement(s);
	    else {
		String nlines[] = getLines(ns);
		for (int i = 0; i < nlines.length; i++)
		    v.addElement(nlines[i]);
	    }
	}

	int n = v.size();
	String lines[] = new String[n];
	for (int i = 0; i < n; i++)
	    lines[i] = (String)v.elementAt(i);
	return lines;
    }

    private static char CHAR_SEPS[] = new char[]{' ', '\t', '|', '&'};

    private static int getIndexSep(String text) {
	
	for (int n = 0; n < CHAR_SEPS.length; n++) {
	    int idx = text.lastIndexOf(CHAR_SEPS[n]);
	    if (idx >= 0)
		return idx;
	}

	return -1;
    }

    private static String splitText(String text, int splitWidth) {
	if (splitWidth <= 0)
	    return text;

	String reste = text;
	String text_temp;
	String rs = "";
	int idx = 0;

	if (text.length() > splitWidth) {
	    while (reste.length() > splitWidth) {
		text_temp = reste.substring(0, splitWidth);
		idx = getIndexSep(text_temp);

		if (idx < 0)
		    break;

		text_temp = text_temp.substring(0, idx).trim();
		rs = rs + text_temp + "\n";
		reste = reste.substring(idx, reste.length()).trim();
	    }

	    rs = rs + reste;
	}
	else
	    rs = text;
    
	return rs;
    }

    private JLabel makeLabel(String line) {
	JLabel label = new JLabel(line);
	label.setBackground(getBackground());
	label.setForeground(getForeground());
	label.setFont(getFont());
	label_v.add(label);
	return label;
    }
}
