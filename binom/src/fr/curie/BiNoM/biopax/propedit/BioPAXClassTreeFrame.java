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
import fr.curie.BiNoM.biopax.propedit.*;

import java.io.*;

import edu.rpi.cs.xgmml.*;
import java.io.InputStream;

import java.io.File;
import java.net.URL;
import giny.view.NodeView;
import java.lang.reflect.*;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import javax.swing.tree.*;
import javax.swing.*;

public class BioPAXClassTreeFrame extends JFrame implements TreeSelectionListener, MouseListener {

    static private BioPAXClassTreeFrame instance;
    static private Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static private final int DEFAULT_WIDTH = 800;
    static private final int DEFAULT_HEIGHT = 900;
    static private Font boldFont = new Font("SansSerif", Font.BOLD, 12);
    static private Font italicFont = new Font("SansSerif", Font.ITALIC, 12);
    static private Font plainFont = new Font("SansSerif", Font.PLAIN, 12);

    private JPanel treePane, listPane, rightPane;
    private JSplitPane splitPane;

    private BioPAX biopax;
    private JTree jtree;

    private boolean display_all;
    private JLabel whatDisplayed;
    private JButton displayAll;
    private JComboBox clsDescCB;
    private JButton newB;

    JScrollPane leftSPane, rightSPane;

    private BioPAXClassTreeFrame() {
	super("BioPAX Class Tree");

	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel();

	treePane = new JPanel(new BorderLayout());
	listPane = new JPanel(new GridBagLayout());

	rightPane = new JPanel(null);
	rightPane.add(listPane);

	leftSPane = new JScrollPane();
	rightSPane = new JScrollPane();

	leftSPane.setViewportView(treePane);
	rightSPane.setViewportView(rightPane);

	mainPanel.add(buttonPanel, BorderLayout.NORTH);

	whatDisplayed = new JLabel();
	whatDisplayed.setFont(italicFont);
	buttonPanel.add(whatDisplayed);

	displayAll = new JButton("");
	displayAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setDisplayAll(!getDisplayAll());
		}
	    });

	buttonPanel.add(displayAll);

	buttonPanel.add(new JLabel("               "));

	newB = new JButton("New instance");
	newB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    String clsDescName = (String)clsDescCB.getSelectedItem();
		    String guri = generateURI(clsDescName);
		    String uri =  JOptionPane.showInputDialog
			(new JFrame(), //Cytoscape.getDesktop(),
			 "URI for the new " + clsDescName + " instance",
			 guri);
		    
		    if (uri == null || uri.length() == 0) {
			abortURI(guri);
			return;
		    }

		    BioPAXObject bobj = BioPAXObjectFactory.getInstance().createObject(clsDescName, uri, biopax);
		    if (bobj != null) {
			bobj.getClassDesc().updateColor();
			System.out.println("new instance of " + bobj.getClassDesc());
			try {
			    if (bobj.getObject() instanceof fr.curie.BiNoM.pathways.biopax.entity) {
				BioPAXNamingServiceManager.getNamingService(biopax).putEntity((fr.curie.BiNoM.pathways.biopax.entity)bobj.getObject());
			    }
			    else if (bobj.getObject() instanceof fr.curie.BiNoM.pathways.biopax.utilityClass) {
				BioPAXNamingServiceManager.getNamingService(biopax).putUtilityClass((fr.curie.BiNoM.pathways.biopax.utilityClass)bobj.getObject());
			    }
			    else {
				System.err.println("UNKNOWN CLASS FOR NAMING SERVICE : " + bobj.getObject().getClass().getName());
			    }
			}
			catch(Exception ex) {
			    ex.printStackTrace();
			}
			BioPAXPropertyBrowserFrame instance = BioPAXPropertyBrowserFrame.getEditInstance();
			instance.setEditMode(true);
			instance.setDisplayAll(false);
			instance.addTabbedPane(bobj, biopax, true);
			instance.setVisible(true);
			instance.toFront();
			recompute();
		    }
		}
	    });

	buttonPanel.add(newB);
	clsDescCB = new JComboBox(new String[]{"   "});
	buttonPanel.add(clsDescCB);

	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	splitPane.setLeftComponent(leftSPane);
	splitPane.setRightComponent(rightSPane);

	splitPane.setDividerLocation(300);

	mainPanel.add(splitPane, BorderLayout.CENTER);

	setContentPane(mainPanel);
	setDisplayAll(false);

	Dimension size = getSize();
	setLocation((screenSize.width - size.width) / 2,
		    (screenSize.height - size.height) / 2);
	setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public void setBioPAX(BioPAX biopax, String name) {
	if (this.biopax == biopax) {
	    return;
	}

	this.biopax = biopax;

	BioPAXClassDescFactory.getInstance(biopax).makeClassesDesc(biopax);
	BioPAXClassDesc thingClsDesc = BioPAXClassDescFactory.getInstance(biopax).getClassDesc("com.ibm.adtech.jastor.Thing");

	//thingClsDesc.dumpHierarchy();

	HScanner scanner = new HScanner();
	thingClsDesc.scanHierarchy(scanner);

	clsDescCB.removeAllItems();
	Vector<BioPAXClassDesc> clsDescV = scanner.getClassDescV();

	BioPAXClassDesc.sort(clsDescV);

	for (int n = 0; n < clsDescV.size(); n++) {
	    clsDescCB.addItem(clsDescV.get(n).getCanonName());
	}

	jtree = new JTree(scanner.getRootNode());
	jtree.getSelectionModel().setSelectionMode
	    (TreeSelectionModel.SINGLE_TREE_SELECTION);

	treePane.removeAll();
	treePane.add(jtree, BorderLayout.WEST);
	jtree.setBackground(treePane.getBackground());

	jtree.setCellRenderer(new CellRenderer());

	for (int n = 0; n < jtree.getRowCount(); n++)
	    jtree.expandRow(n);

	jtree.addTreeSelectionListener(this);
	listPane.removeAll();

	setTitle("BioPAX Class Tree: " + name);
    }

    class CellRenderer implements TreeCellRenderer {

	static final int COLOR_WIDTH = 8;
	static final int COLOR_HEIGHT = COLOR_WIDTH;

	public Component getTreeCellRendererComponent(JTree tree,
						      Object value,
						      boolean selected,
						      boolean expanded,
						      boolean leaf,
						      int row,
						      boolean hasFocus) {
	    Node node = (Node)value;
	    BioPAXClassDesc clsDesc = node.clsDesc;
	    JLabel label = new JLabel(BioPAXPropertyUtils.className(clsDesc.getName()) + getInfo(clsDesc));

	    label.setBackground(treePane.getBackground());

	    if (selected) {
		label.setForeground(Color.BLUE);
	    }
	    else {
		label.setForeground(Color.BLACK);
	    }

	    Color color = clsDesc.getColor();
	    if (color == null)
		color = tree.getBackground();

	    JPanel panel = new JPanel();
	    JPanel colorPanel = new JPanel();
	    
	    colorPanel.setPreferredSize(new Dimension(COLOR_WIDTH, COLOR_HEIGHT));
	    colorPanel.setBackground(clsDesc.getColor());
	    colorPanel.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
	    panel.add(colorPanel);
	    panel.add(label);
	    
	    return panel;
	}
    }

    static String getInfo(BioPAXClassDesc clsDesc) {
	String info = " (";
	java.util.List list = clsDesc.getInstanceList(true);
	return info + (list == null ? 0 : list.size()) + ")";
    }

    static class Node extends DefaultMutableTreeNode {

	BioPAXClassDesc clsDesc;

	Node(BioPAXClassDesc clsDesc) {
	    super(clsDesc);
	    this.clsDesc = clsDesc;
	}
    }

    static class HScanner extends BioPAXClassDesc.HierarchyScanner {

	private Stack<DefaultMutableTreeNode> stack;
	private DefaultMutableTreeNode rootNode;
	private Vector<BioPAXClassDesc> clsDesc_v;

	HScanner() {
	    stack = new Stack();
	    clsDesc_v = new Vector();
	    rootNode = null;
	}

	public void push(BioPAXClassDesc clsDesc) {
	    Node curNode = new Node(clsDesc);

	    if (stack.size() > 0) {
		DefaultMutableTreeNode parentNode = stack.peek();
		parentNode.add(curNode);
	    }
	    else {
		rootNode = curNode;
	    }

	    stack.push(curNode);

	    if (!clsDesc.getCanonName().equals("Thing")) {
		clsDesc_v.add(clsDesc);
	    }
	}

	public void pop() {
	    stack.pop();
	}

	public DefaultMutableTreeNode getRootNode() {
	    return rootNode;
	}

	public Vector<BioPAXClassDesc> getClassDescV() {
	    return clsDesc_v;
	}
    }

    static public BioPAXClassTreeFrame getInstance() {
	if (instance == null)
	    instance = new BioPAXClassTreeFrame();
	return instance;
    }

    public void valueChanged(TreeSelectionEvent e) {
	if (jtree == null)
	    return;

	TreePath path = jtree.getSelectionPath();
	if (path == null)
	    return;

	Node node = (Node)path.getLastPathComponent();
	java.util.List list = node.clsDesc.getInstanceList(!display_all);
	clsDescCB.setSelectedItem(node.clsDesc.getCanonName());
	listPane.removeAll();
	if (list != null) {
	    Iterator iter = list.iterator();
	    int y = 0;
	    GridBagConstraints c;

	    JPanel p = new JPanel();
	    p.setPreferredSize(new Dimension(20, 20));
	    c = BioPAXPropertyUtils.makeGridBagConstraints(0, y++, 3, 1);
	    listPane.add(p, c);

	    p = new JPanel();
	    p.setPreferredSize(new Dimension(20, 1));
	    c = BioPAXPropertyUtils.makeGridBagConstraints(0, y, 1, 1);
	    listPane.add(p, c);

	    int max_rows = fr.curie.BiNoM.lib.Utils.getGridBagLayoutMaxRows();
	    max_rows -= 2;

	    boolean last = false;
	    for (int n = 0; iter.hasNext(); n++) {
		if (y == max_rows) {
		    last = true;
		}

		com.ibm.adtech.jastor.Thing thing = (com.ibm.adtech.jastor.Thing)iter.next();
		JLabel label = new JLabel(last ? "..." : BioPAXPropertyUtils.className(thing.getClass().getName()));
		label.setFont(boldFont);
		Class cls = BioPAXPropertyUtils.getClass(thing.getClass());
		BioPAXClassDesc clsDesc = BioPAXClassDescFactory.getInstance(biopax).getClassDesc(cls);
		label.setForeground(clsDesc.getColor());
		c = BioPAXPropertyUtils.makeGridBagConstraints(1, y);
		c.ipadx = 20;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		listPane.add(label, c);
		    
		if (last) {
		    label = new JLabel("...");
		    label.setForeground(Color.RED);
		}
		else {
		    label.addMouseListener(new ColorChooser(clsDesc));

		    label = new BioPAXPropertyUtils.ObjectLabel(thing.uri(), BioPAXPropertyUtils.getCURI(thing, biopax));
		    label.setForeground(Color.BLUE);

		    label.addMouseListener(this);
		}

		c = BioPAXPropertyUtils.makeGridBagConstraints(2, y);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.ipadx = 20;

		y++;
		    
		listPane.add(label, c);
		if (last)
		    break;
	    }

	    p = new JPanel();
	    p.setPreferredSize(new Dimension(1, 10));
	    c = BioPAXPropertyUtils.makeGridBagConstraints(0, y, 3, 1);
	    c.fill = GridBagConstraints.VERTICAL;
	    c.weighty = 1.0;
	    listPane.add(p, c);
	}

	listPane.validate();
	rightPane = new JPanel(new GridBagLayout());
	GridBagConstraints c = BioPAXPropertyUtils.makeGridBagConstraints(0, 0);
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1.0;
	c.weighty = 1.0;
	rightPane.add(listPane, c);

	rightSPane.setViewportView(rightPane);
    }

    void setDisplayAll(boolean display_all) {
	this.display_all = display_all;
	whatDisplayed.setText(getDisplayLabel());
	displayAll.setLabel(getDisplayButtonLabel());
	valueChanged(null);
    }

    String getDisplayButtonLabel() {
	return display_all ? "Display strict instances" : 
	    "Display sub instances";
    }

    boolean getDisplayAll() {
	return display_all;
    }

    String getDisplayLabel() {
	return display_all ? "Sub instances displayed     " :
	    "Strict instances displayed      ";
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
	BioPAXPropertyUtils.ObjectLabel l = (BioPAXPropertyUtils.ObjectLabel)e.getSource();
	if (l == null) {
	    System.out.println("STRANGE: l is null");
	}

	String uri = l.getURI();
	if (uri != null) {
	    BioPAXObject bobj = BioPAXObjectFactory.getInstance().getObject(uri, biopax);
	    
	    BioPAXPropertyBrowserFrame instance = BioPAXPropertyBrowserFrame.getEditInstance();
	    instance.addTabbedPane(bobj, biopax, true);
	    instance.setVisible(true);
	    instance.toFront();
	}
    }

    class ColorChooser extends MouseAdapter {

	BioPAXClassDesc clsDesc;

	ColorChooser(BioPAXClassDesc clsDesc) {
	    this.clsDesc = clsDesc;
	}

	public void mouseReleased(MouseEvent e) {
	    Color color = JColorChooser.showDialog((Component)e.getSource(), "Class " + clsDesc.getName() + " color", clsDesc.getColor());

	    if (color != null) {
		clsDesc.setColor(color);
		BioPAXPropertyUtils.syncFrames();
	    }
	}
    }

    public void recompute() {
	valueChanged(null);
	repaint();
    }

    // should be in BioPAXNamingService
    static int URI_ID = 1;

    static String generateURI(String clsName) {
	return clsName + "_" + URI_ID++;
    }

    static void abortURI(String uri) {
	--URI_ID;
    }
}
