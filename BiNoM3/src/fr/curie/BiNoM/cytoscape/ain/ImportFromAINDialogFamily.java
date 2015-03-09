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
package fr.curie.BiNoM.cytoscape.ain;


import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import fr.curie.BiNoM.pathways.SimpleTextInfluenceToBioPAX;
import java.util.*;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class ImportFromAINDialogFamily extends JDialog {

    private static final double COEF_X = 1.2, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JList familyList;
    private JList memberList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    //public static ImportFromAINDialogFamily instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    
    public int result = -1; // do not continue, if negative
    public HashMap tempFamilies = new HashMap();
    public Vector newFamilies = new Vector();
    
    private JRadioButton expandRB;
    private JRadioButton collapseRB;
    private JButton collapseAll;
    private JButton expandAll;
    private JButton selectAll;
    private JButton selectDefault;
    private JButton unselectAll;
    private JButton addNewFamily;
    
    private boolean blockMemberUpdate = false;
    
    
    //public SimpleTextInfluenceToBioPAX converter = null;

    /*public static ImportFromAINDialogFamily getInstance() {
	if (instance == null)
	    instance = new ImportFromAINDialogFamily(null,"Family definition",true);
	return instance;
    }*/

    public ImportFromAINDialogFamily(JFrame frame, String mess, boolean modal) {
    	
    super(frame, mess, modal);
    	
    //JOptionPane.showMessageDialog(Cytoscape.getDesktop(), "In ImportFromAINDialogFamily constructor");
    	
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Defining families");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 40;
	panel.add(title, c);
	
	JLabel label;

	label = new JLabel("Family");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	familyList = new JList();
	scrollPane1 = new JScrollPane(familyList);
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	//panel.add(familyList, c);
	panel.add(scrollPane1, c);
	
	JPanel pnl1 = new JPanel();
	c = new GridBagConstraints();
	c.gridx = 2;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pnl1, c);

	collapseAll = new JButton("Collapse all");
	c = new GridBagConstraints();
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.WEST;
	pnl1.add(collapseAll, c);
	collapseAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			(SimpleTextInfluenceToBioPAX.getInstance()).userDefinedFamiliesExpand.clear();
			setButtons((String)familyList.getSelectedValue());
		}
	    });
	
	expandAll = new JButton("Expand all");
	c.gridx = 0;
	c.gridy = 1;
	c.anchor = GridBagConstraints.WEST;
	pnl1.add(expandAll, c);
	expandAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			java.util.Iterator itt = (SimpleTextInfluenceToBioPAX.getInstance()).userDefinedFamilies.keySet().iterator();
			while(itt.hasNext()){
				String fn = (String)itt.next();
				(SimpleTextInfluenceToBioPAX.getInstance()).userDefinedFamiliesExpand.put(fn, new Boolean(true));
			}
			setButtons((String)familyList.getSelectedValue());
		}
	    });
	
	
	addNewFamily = new JButton("Add new family...");
	c.gridx = 0;
	c.gridy = 1;
	c.anchor = GridBagConstraints.WEST;
	pnl1.add(addNewFamily, c);
	addNewFamily.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String newf = JOptionPane.showInputDialog("Input new family name in the form \'(FN.)\' or \'(M1,M2)\'");
			if(newFamilies.indexOf(newf)<0){
				newFamilies.add(newf);
			try{
			SimpleTextInfluenceToBioPAX.getInstance().addFamilies(newFamilies);
			}catch(Exception ee){
				ee.printStackTrace();
			}
			Vector v = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.get(newf);
			Vector nv = new Vector();
			for(int i=0;i<v.size();i++) nv.add(v.get(i));
			tempFamilies.put(newf, nv);
			fillContent();
			}
		}
	    });	
	
	
	familyList.addListSelectionListener(new ListSelectionListener(){
	    public void valueChanged(ListSelectionEvent e) {
	    	String familyName = (String)familyList.getSelectedValue();
	    	selectFamily(familyName);
	    	setButtons((String)familyList.getSelectedValue());
	    }		
	}
	);
	
	y++;
	
	y = GraphicUtils.addHSepPanel(panel, 0, y, 3, 1);

	label = new JLabel("Members");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 3;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	memberList = new JList();
	scrollPane2 = new JScrollPane(memberList);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 3;
	c.anchor = GridBagConstraints.WEST;
	//c.fill = GridBagConstraints.HORIZONTAL;
	//c.weightx = 1.0;
	//panel.add(netwUpdateList, c);WEST;
	panel.add(scrollPane2, c);
	
	JPanel pnl = new JPanel();
	c = new GridBagConstraints();
	c.gridx = 2;
	c.gridy = y + 3;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pnl, c);
	
	c = new GridBagConstraints();	
	expandRB = new JRadioButton("Expand");
	c.gridwidth = 2;
	c.gridheight = 3;
	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.WEST;
	pnl.add(expandRB, c);
	expandRB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fn = (String)familyList.getSelectedValue();
			SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.put(fn,new Boolean(true));
		}
	    });	
	
	collapseRB = new JRadioButton("Collapse");
	c.gridx = 0;
	c.gridy = 1;
	c.anchor = GridBagConstraints.WEST;
	pnl.add(collapseRB, c);
	collapseRB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fn = (String)familyList.getSelectedValue();
			SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.remove(fn);
		}
	    });
	
	y+=4;
	
	pnl = new JPanel();
	c = new GridBagConstraints();
	c.gridx = 2;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pnl, c);	
	
	selectAll = new JButton("Select all");
	c = new GridBagConstraints();
	c.gridwidth = 2;
	c.gridheight = 3;
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	pnl.add(selectAll, c);
	selectAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fn = (String)familyList.getSelectedValue();
			Vector v = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.get(fn);
			Vector v1 = (Vector)tempFamilies.get(fn);
			v1.clear();
			for(int i=0;i<v.size();i++) v1.add(v.get(i)); 
			selectFamily(fn);
		}
	    });
	

	selectDefault = new JButton("Select default");
	c = new GridBagConstraints();
	c.gridwidth = 2;
	c.gridheight = 3;
	c.gridx = 2;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	pnl.add(selectDefault, c);	
	selectDefault.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fn = (String)familyList.getSelectedValue();
			Vector v = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.get(fn);
			Vector nv = new Vector();
			for(int i=0;i<v.size();i++){
				String mn = (String)v.get(i);
				boolean selected = true;
				if((mn.indexOf(":")>0)||(mn.indexOf("^")>0))
					selected = false;
				Vector nonselected = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesNonSelectedMembers.get(fn);
				if(nonselected.indexOf(mn)>=0)
					selected = false;
				if(selected)
					nv.add(mn);
			}
			tempFamilies.put(fn, nv);
			selectFamily(fn);
		}
	    });	
	
	unselectAll = new JButton("Unselect all");
	c = new GridBagConstraints();
	c.gridwidth = 2;
	c.gridheight = 3;
	c.gridx = 3;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	pnl.add(unselectAll, c);
	unselectAll.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fn = (String)familyList.getSelectedValue();
			Vector v = (Vector)tempFamilies.get(fn);
			v.clear();
			selectFamily(fn);
		}
	    });	
	
	
	ButtonGroup group = new ButtonGroup();
	group.add(expandRB);
	group.add(collapseRB);
	
	setButtons((String)familyList.getSelectedValue());
	

	y += 5;
	

	JPanel pad = new JPanel();
	pad.setPreferredSize(new Dimension(1, 30));
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pad, c);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    result = 1;			
		    setVisible(false);
			//return;
		}
	    });

	buttonPanel.add(okB);

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    result = -1;
		    setVisible(false);
		}
	    });

	buttonPanel.add(cancelB);


	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	
	// Fill content
	familyList.removeAll();


	String[] netwNames = NetworkUtils.getNetworkNames(EMPTY_NAME);
	Vector data = new Vector();
	java.util.Iterator it = SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.keySet().iterator();
	while(it.hasNext()){
		data.add((String)it.next());
	}
	
	Collections.sort(data);
	familyList.setListData(data);
	int len = data.size();
	if(len<5) len = 5;
	familyList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);
	familyList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	familyList.setSelectedIndex(0);
	
	int maxlen = 0;
	java.util.Iterator itt = SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.keySet().iterator();
	while(itt.hasNext()){
		String fn = (String)itt.next();
		Vector v = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.get(fn);
		if(v.size()>maxlen) maxlen = v.size();
		Vector nv = new Vector();
		for(int i=0;i<v.size();i++){
			String mn = (String)v.get(i);
			boolean selected = true;
			if((mn.indexOf(":")>0)||(mn.indexOf("^")>0))
				selected = false;
			Vector nonselected = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesNonSelectedMembers.get(fn);
			if(nonselected!=null)
				if(nonselected.indexOf(mn)>=0)
					selected = false;
			if(selected)
				nv.add(mn);
		}
		tempFamilies.put(fn, nv);
	}
	
	if(maxlen<5) maxlen = 5;
	memberList.setVisibleRowCount(maxlen < MAX_ROWS ? maxlen : MAX_ROWS);
	//memberList.setBounds(memberList.getX(), memberList.getY(), 200, memberList.getHeight());
	memberList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	selectFamily((String)familyList.getSelectedValue());
	familyList.setFixedCellWidth(200);
	memberList.setFixedCellWidth(200);
	
	memberList.addListSelectionListener(new ListSelectionListener(){
	    public void valueChanged(ListSelectionEvent e) {
	    	if(!blockMemberUpdate){
	    	String fn = (String)familyList.getSelectedValue();	    	
	    	Object vals[] = (Object[])memberList.getSelectedValues();
	    	Vector v = (Vector)tempFamilies.get(fn);
	    	v.clear();
	    	for(int i=0;i<vals.length;i++)
	    		v.add(vals[i]);
	    	}
	    }		
	}
	);
	
	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	
	
    }
    
    public void fillContent(){
    	// Fill content
    	familyList.removeAll();


    	String[] netwNames = NetworkUtils.getNetworkNames(EMPTY_NAME);
    	Vector data = new Vector();
    	java.util.Iterator it = SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.keySet().iterator();
    	while(it.hasNext()){
    		data.add((String)it.next());
    	}
    	
    	Collections.sort(data);
    	familyList.setListData(data);
    	int len = data.size();
    	familyList.setSelectedIndex(0);
    	
    	selectFamily((String)familyList.getSelectedValue());
    }
    
    public void selectFamily(String fn){
    	Vector members = (Vector)SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies.get(fn);
    	if(members!=null){
    	Collections.sort(members);   
    	blockMemberUpdate = true;    	
    	memberList.setListData(members);
    	Vector selected = (Vector)tempFamilies.get(fn);
    	if(selected!=null){
    	int indices[] = new int[selected.size()];
    	for(int i=0;i<selected.size();i++){
    		String s = (String)selected.get(i);
    		indices[i] = members.indexOf(s);
    	}
    	memberList.setSelectedIndices(indices);
    	}
    	blockMemberUpdate = false;
    	}
    }
    
    public void setButtons(String fn){
    	expandRB.setSelected(SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.get(fn)!=null);
    	collapseRB.setSelected(SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.get(fn)==null);
    }
    
}

