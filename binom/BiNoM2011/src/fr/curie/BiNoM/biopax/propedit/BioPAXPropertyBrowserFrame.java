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

import fr.curie.BiNoM.pathways.wrappers.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class BioPAXPropertyBrowserFrame extends JFrame {

    static private BioPAXPropertyBrowserFrame edit_instance, browse_instance;

    static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static final int DEFAULT_WIDTH = 800;
    static final int DEFAULT_HEIGHT = 900;

    private JTabbedPane tabbedPane;
    private boolean display_all = false;
    private boolean edit_mode = false;

    private JLabel whatDisplayed;
    private JButton displayAll, edit;
    private Vector<Component> browseStack;
    private int browseCursor;
    private JButton backward, forward;
    private boolean browse_only;

    static Font italicFont = new Font("SansSerif", Font.ITALIC, 12);
    static Font plainFont = new Font("SansSerif", Font.PLAIN, 12);
    static Font boldFont = new Font("SansSerif", Font.BOLD, 12);

    private BioPAXPropertyBrowserFrame(boolean browse_only) {
	super("BioPAX Property " + (browse_only ? "Browser" : "Editor"));

	this.browse_only = browse_only;
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel();

	whatDisplayed = new JLabel();
	whatDisplayed.setFont(italicFont);
	buttonPanel.add(whatDisplayed);

	displayAll = new JButton("");
	displayAll.setFont(plainFont);
	buttonPanel.add(displayAll);
	displayAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setDisplayAll(!getDisplayAll());
		}
	    });

	backward = new JButton("<<");
	backward.setFont(plainFont);
	buttonPanel.add(backward);
	backward.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (backwardEnabled()) {
			--browseCursor;
			registerChange(false);
			tabbedPane.setSelectedComponent(browseStack.get(browseCursor));
		    }
		}
	    });

	forward = new JButton(">>");
	forward.setFont(plainFont);
	buttonPanel.add(forward);
	forward.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (forwardEnabled()) {
			++browseCursor;
			registerChange(false);
			tabbedPane.setSelectedComponent(browseStack.get(browseCursor));
		    }
		}
	    });

	JButton close = new JButton("Close current tab");
	close.setFont(plainFont);
	buttonPanel.add(close);
	close.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    tabbedPane.remove(tabbedPane.getSelectedIndex());
		    //--browseCursor;
		}
	    });

	JButton closeAll = new JButton("Close all tabs");
	closeAll.setFont(plainFont);
	buttonPanel.add(closeAll);
	closeAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    tabbedPane.removeAll();
		    resetStack();
		}
	    });

	if (!browse_only) {
	    edit = new JButton("");
	    edit.setFont(plainFont);
	    buttonPanel.add(edit);
	    edit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			setEditMode(!getEditMode());
		    }
		});
	}

	tabbedPane = new JTabbedPane();
	tabbedPane.setFont(boldFont);

	tabbedPane.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    if (isChangeRegistered()) {
			if (browseCursor >= 0) {
			    browseStack.setSize(browseCursor+1);
			}
			Component comp = tabbedPane.getSelectedComponent();
			browseStack.add(comp);
			browseCursor = browseStack.size()-1;
		    }
		    registerChange(true);
		    backforwStates();
		}
	    });

	tabbedPane.addContainerListener(new ContainerListener() {

		public void componentAdded(ContainerEvent e) {
		    recomputeStack();
		}

		public void componentRemoved(ContainerEvent e) {
		    recomputeStack();
		}
	    });

	mainPanel.add(buttonPanel, BorderLayout.NORTH);
	mainPanel.add(tabbedPane, BorderLayout.CENTER);
	tabbedPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT - 50));
	if (browse_only)
	    mainPanel.setBackground(Color.ORANGE);
	setContentPane(mainPanel);

	setEditMode(false);
	setDisplayAll(false);
	resetStack();
	
	Dimension size = getSize();
	setLocation((screenSize.width - size.width) / 2,
		    (screenSize.height - size.height) / 2);
	setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    static public BioPAXPropertyBrowserFrame getEditInstance() {
	if (edit_instance == null)
	    edit_instance = new BioPAXPropertyBrowserFrame(false);
	return edit_instance;
    }

    static public BioPAXPropertyBrowserFrame getBrowseInstance() {
	if (browse_instance == null)
	    browse_instance = new BioPAXPropertyBrowserFrame(true);
	return browse_instance;
    }

    public void addTabbedPane(BioPAXObject bobj, BioPAX biopax, boolean register) {
	String curi = bobj.getCURI();
	int tabCount = tabbedPane.getTabCount();
	for (int n = 0; n < tabCount; n++) {
	    if (tabbedPane.getTitleAt(n).equals(curi)) {
		tabbedPane.setSelectedIndex(n);
		return;
	    }
	}

	BioPAXPropertyBrowserPanel browserPane =
	    new BioPAXPropertyBrowserPanel(bobj, biopax, display_all, edit_mode, this);

	if (browse_only)
	    browserPane.setBackground(Color.ORANGE);

	registerChange(register);

	tabbedPane.addTab(curi, browserPane);
	tabbedPane.setSelectedComponent(browserPane);

	setTabColor(tabbedPane.getTabCount() - 1, bobj.getClassDesc().getColor());
    }

    String getDisplayButtonLabel() {
	return display_all ? "Display valid attributes" : 
	    "Display all attributes";
    }

    String getDisplayLabel() {
	return display_all ? "All attributes displayed     " :
	    "Valid attributes displayed      ";
    }

    public void setDisplayAll(boolean display_all) {
	this.display_all = display_all;
	whatDisplayed.setText(getDisplayLabel());
	displayAll.setLabel(getDisplayButtonLabel());
	recompute();
    }

    boolean getDisplayAll() {
	return display_all;
    }

    String getEditButtonLabel() {
	return edit_mode ? "Display" : "Edit";
    }

    public void setEditMode(boolean edit_mode) {
	this.edit_mode = edit_mode;
	if (edit != null) {
	    edit.setLabel(getEditButtonLabel());
	    recompute();
	}
    }

    boolean getEditMode() {
	return edit_mode;
    }

    void setTabColor(int n, Color color) {
	//tabbedPane.setBackgroundAt(n, color);
	tabbedPane.setForegroundAt(n, color);
    }

    void recompute() {
	int tabCount = tabbedPane.getTabCount();
	for (int n = 0; n < tabCount; n++) {
	    BioPAXPropertyBrowserPanel browsePane =
		(BioPAXPropertyBrowserPanel)tabbedPane.getComponentAt(n);
	    setTabColor(n, browsePane.getBioPAXObject().getClassDesc().getColor());
	    browsePane.init(display_all, edit_mode);
	}
    }

    private boolean registerChange;;

    private void registerChange(boolean registerChange) {
	this.registerChange = registerChange;
    }

    private boolean isChangeRegistered() {
	return registerChange;
    }

    void recomputeStack() {
	Vector<Component> torm = new Vector();
	int tabCount = tabbedPane.getTabCount();
	Iterator<Component> iter = browseStack.iterator();

	for (int m = 0; iter.hasNext(); m++) {
	    Component comp = iter.next();
	    boolean found = false;
	    for (int n = 0; n < tabCount; n++) {
		if (comp == tabbedPane.getComponentAt(n)) {
		    found = true;
		    break;
		}
	    }

	    if (!found) {
		torm.add(comp);
	    }
	}

	if (torm.size() > 0) {
	    resetStack();
	}

	iter = torm.iterator();
	while (iter.hasNext()) {
	    browseStack.remove(iter.next());
	}	

	backforwStates();
    }

    private void resetStack() {
	browseStack = new Vector();
	browseCursor = browseStack.size()-1;
	epilogue();
	backforwStates();
    }

    private boolean backwardEnabled() {
	return browseCursor >= 1 && browseStack.size() > browseCursor - 1;
    }

    public boolean forwardEnabled() {
	return browseCursor >= 0 && browseStack.size() > browseCursor + 1;
    }
    
    public void backforwStates() {
	backward.setEnabled(backwardEnabled());
	forward.setEnabled(forwardEnabled());
    }

    public void epilogue() {
	if (browseCursor < 0) {
	    Component comp = tabbedPane.getSelectedComponent();
	    if (comp != null) {
		browseStack.add(comp);
		browseCursor = browseStack.size()-1;
	    }
	}
    }
}
