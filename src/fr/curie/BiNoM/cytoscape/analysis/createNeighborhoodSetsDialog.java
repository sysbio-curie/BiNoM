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
package fr.curie.BiNoM.cytoscape.analysis;


import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.FileWriter;
import java.io.InputStream;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.GraphUtils;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;

import fr.curie.BiNoM.cytoscape.biopax.OWLFileFilter;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.lib.GraphicUtils;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;

import Main.Launcher;



public class createNeighborhoodSetsDialog extends JDialog {
	
	public Graph network;
	public Vector selectedNodes = new Vector();

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JList sourceList;
    private JList targetList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static final int SCROLL_WIDTH = 380;
    private static final int SCROLL_HEIGHT = 180;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static createNeighborhoodSetsDialog instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    private JCheckBox goUpstreamCB;
    private JCheckBox goDownstreamCB;
    private JCheckBox checkHUGOatt;    
    private JTextField searchRadius;
    private JTextField minimumNumberOfGenes;

    public static createNeighborhoodSetsDialog getInstance() {
	if (instance == null)
	    instance = new createNeighborhoodSetsDialog();
	return instance;
    }

    public void raise() {

	Vector data = new Vector();
	List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
    
	setSize(400, 300);

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private createNeighborhoodSetsDialog() {
	super(new JFrame(), "Create Neighborhood Sets files");

	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;

	JLabel label;

	int y = 0;
	int x = 0;

	for (int gx = 0; gx < 5; gx += 2) {
	    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
	}

	x = 1;
	label = new JLabel("Create Neighborhood Sets files");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 20;	
	c.gridwidth = 6;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);


	goUpstreamCB = new JCheckBox();
	goUpstreamCB.setText("Look upstream");
	goUpstreamCB.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(goUpstreamCB, c);

	goDownstreamCB = new JCheckBox();
	goDownstreamCB.setText("Look downstream");
	goDownstreamCB.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x+1;
	c.gridy = y;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(goDownstreamCB, c);
	y++;
	
	label = new JLabel("Search radius");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	searchRadius = new JTextField(4);
	searchRadius.setText("3");
	c = new GridBagConstraints();
	c.gridx = x+1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(searchRadius, c);
	y+=2;
	
	label = new JLabel("Minimum number of genes  ");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	minimumNumberOfGenes = new JTextField(4);
	minimumNumberOfGenes.setText("10");
	c = new GridBagConstraints();
	c.gridx = x+1;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(minimumNumberOfGenes, c);
	y+=2;
	
	label = new JLabel("Get name from HUGO attribute");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	
	checkHUGOatt = new JCheckBox();
	checkHUGOatt.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x+1;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(checkHUGOatt, c);
	y++;
	
	
	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try{
				
				//Graph graph = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork()));
				Graph graph = network;
				Vector<String> selected = new Vector<String>();
				List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
				Iterator it = nodes.iterator();
				while(it.hasNext())
				    selected.add(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork().getRow((CyNode)it.next()).get(CyNetwork.NAME, String.class));
				
				System.out.println("selected ="+selected.size());
				HashMap<String,Vector<String>> geneSets = BiographUtils.getNeighborhoodSets(graph, selected, goUpstreamCB.isSelected(), goDownstreamCB.isSelected(), Integer.parseInt(searchRadius.getText()), Integer.parseInt(minimumNumberOfGenes.getText()),checkHUGOatt.isSelected());
	    		Set sets = geneSets.keySet();
	    		System.out.println("size = "+sets.size());
	    		
	    		JFileChooser fileChooser = new JFileChooser();
	    		
				fileChooser.setFileFilter(new GMTFileFilter());
			
				fileChooser.setDialogTitle("Save GMT File");
			
				JFrame frame = new JFrame();
				
				int userSelection = fileChooser.showSaveDialog(frame);
				
				File file;
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					
					if(!file.getAbsolutePath().endsWith(".gmt"))
						file = new File(file.getAbsolutePath() + ".gmt");
					
					System.out.println("Save as file: " + file.getAbsolutePath());
				}
				else
					file = null;

//			    CyFileFilter indexFilter = new CyFileFilter();
//
//			    indexFilter.addExtension("gmt");
//			    indexFilter.setDescription("GMT files");
//	    		
//				
//			    File file = FileUtil.getFile
//				("Save GMT File", FileUtil.SAVE, new CyFileFilter[]{indexFilter});
//			    toFront();
	    		
	    		
			    if (file != null) {
			    	String fn = file.getAbsolutePath();
			    	if(!fn.toLowerCase().endsWith(".gmt"))
			    		fn+=".gmt";
			    	try{
			    		FileWriter fw = new FileWriter(fn);
			    		Set set = geneSets.keySet();
			    		Iterator<String> iterator = set.iterator();
			    		while(iterator.hasNext()){
			    			String key = iterator.next();
			    			Vector<String> pnames = geneSets.get(key);
			    			fw.write(GraphUtils.correctId(key)+"\tna\t");
			    			for(int i=0;i<pnames.size();i++)
			    				fw.write(pnames.get(i)+"\t");
			    			fw.write("\n");
			    		}
			    		fw.close();
			    	}catch(Exception eee){
			    		JOptionPane.showMessageDialog(null, /*Cytoscape.getDesktop(),*/ "Error: "+eee.getMessage());
			    		eee.printStackTrace();
			    	}
			    }

				
			}catch(Exception ee){
				JOptionPane.showMessageDialog(null, /*Cytoscape.getDesktop(),*/ "Error: "+ee.getMessage());
			    ee.printStackTrace();
			}
			setVisible(false);
			return;
		    }
		});

	buttonPanel.add(okB);

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	buttonPanel.add(cancelB);

	/*
	  c = new GridBagConstraints();
	  c.ipady = 30;
	  c.gridx = 0;
	  c.gridy = y;
	  c.gridwidth = 3;
	  c.anchor = GridBagConstraints.CENTER;

	  panel.add(buttonPanel, c);
	*/

	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
