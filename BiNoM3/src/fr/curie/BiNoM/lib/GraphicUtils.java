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

import java.awt.*;

public class GraphicUtils {

    public static int addVPadPanel(Container panel, int x, int y, int pad) {
	Panel padPanel = new Panel();
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipadx = pad;
	c.fill = GridBagConstraints.NONE;
	panel.add(padPanel, c);
	return x+1;
    }

    public static int addHPadPanel(Container panel, int x, int y, int pad) {
	Panel padPanel = new Panel();
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = pad;
	c.fill = GridBagConstraints.NONE;
	panel.add(padPanel, c);
	return y+1;
    }

    public static int addHSepPanel(Container panel, int x, int y, int width, int pad) {
	y = addHPadPanel(panel, x, y, pad);
	y = addHSepPanel(panel, x, y, width);
	return addHPadPanel(panel, x, y, pad);
    }

    public static int addHSepPanel(Container panel, int x, int y, int width) {
	Panel sepPanel = new Panel();
	sepPanel.setPreferredSize(new Dimension(1, 1));
	sepPanel.setSize(new Dimension(1, 1));
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridwidth = width;
	c.anchor = GridBagConstraints.WEST;
	c.weightx = 1.0;
	c.fill = GridBagConstraints.HORIZONTAL;
	sepPanel.setBackground(Color.BLACK);
	panel.add(sepPanel, c);
	return y+1;
    }
}
