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
package fr.curie.BiNoM.biopax;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportTask;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class BioPAXImportDialog extends JDialog implements ActionListener {

    private static final double COEF_X = 1.24, COEF_Y = 1.05;
    private static final int OPT_INDENT = 30;
    private static final java.awt.Font TITLE_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     14);
    private static final java.awt.Font BOLD_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     12);

    private static final java.awt.Font PLAIN_FONT = new java.awt.Font("times",
							     java.awt.Font.PLAIN,
							     12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    private JCheckBox reactionCB;
    // options for reaction
    // none

    private JCheckBox pathwayCB;
    // options for pathway
    private JCheckBox makeRootPathwayNodeCB;
    private JCheckBox includeNextLinksCB;
    private JCheckBox includePathwaysCB;
    private JCheckBox includeInteractionsCB;

    private JCheckBox proteinCB;
    //private JCheckBox applyLayoutCB;
    // options for proteins
    // none

    private JButton okB, cancelB;
    private File file;
    private URL url;
    private String name;

    public static BioPAXImportDialog instance;

    public static BioPAXImportDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXImportDialog();
	return instance;
    }

    // added
    public void raise(File file, String name) {
	this.file = file;
	this.url = null;
	this.name = name;
	pop();
    }

    public void raise( URL url, String name) {
	this.file = null;
	this.url = url;
	this.name = name;
	pop();
    }

    private void pop() {
	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private BioPAXImportDialog() {
	//super("BioPAX Import Dialog", new JFrame());

	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("BiNoM Parameters");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 30;
	panel.add(title, c);

	reactionCB = new JCheckBox("Reaction Network");
	reactionCB.setFont(BOLD_FONT);
	reactionCB.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 30;
	c.gridwidth = 3;
	panel.add(reactionCB, c);

	pathwayCB = new JCheckBox("Pathway Hierarchy");
	pathwayCB.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 20;
	c.gridwidth = 3;
	panel.add(pathwayCB, c);

	JPanel indentPanel = new JPanel();
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.gridwidth = 1;
	indentPanel.setPreferredSize(new Dimension(20, 1));
	panel.add(indentPanel, c);

	makeRootPathwayNodeCB = new JCheckBox("Make Root Pathway Node");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.gridwidth = 2;
	c.ipadx = 30;
	c.ipady = 5;
	c.anchor = GridBagConstraints.WEST;
	panel.add(makeRootPathwayNodeCB, c);

	includeNextLinksCB = new JCheckBox("Include Next Links");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.gridwidth = 2;
	c.ipadx = OPT_INDENT;
	c.ipady = 5;
	c.anchor = GridBagConstraints.WEST;
	panel.add(includeNextLinksCB, c);

	includePathwaysCB = new JCheckBox("Include Pathways");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.gridwidth = 2;
	c.ipadx = OPT_INDENT;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 5;
	panel.add(includePathwaysCB, c);

	includeInteractionsCB = new JCheckBox("Include Interactions");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.gridwidth = 2;
	c.ipadx = OPT_INDENT;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 5;
	panel.add(includeInteractionsCB, c);

	proteinCB = new JCheckBox("Interaction map");
	proteinCB.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.ipady = 20;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.WEST;
	panel.add(proteinCB, c);

	/*
	  applyLayoutCB = new JCheckBox("Apply Spring Layout");
	applyLayoutCB.setSelected(false);
	applyLayoutCB.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.ipady = 20;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.WEST;
	panel.add(applyLayoutCB, c);
	*/

	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int algos[] = getAlgos();
		    if (algos[0] != 0) {
		    	
		    
	    	TaskIterator t = new TaskIterator(new BioPAXImportTask(file, url, name, algos, getOptions(), true));
			Launcher.getAdapter().getTaskManager().execute(t);
		    	
			/*
			BioPAXImportTask task = new BioPAXImportTask
			    (plugin, file, url, name, algos, getOptions(), true);

			//fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
			task.run();
			*/

			setVisible(false);
		    }
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

	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(buttonPanel, c);

	getContentPane().add(panel);
	pack();
	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));
	setDefaultOptions();
	pathwayCB.addActionListener(this);
	actionPerformed(null);
    }


    private void setDefaultOptions() {
	BioPAXToCytoscapeConverter.Option option = new BioPAXToCytoscapeConverter.Option();
	makeRootPathwayNodeCB.setSelected(option.makeRootPathwayNode);
	includeNextLinksCB.setSelected(option.includeNextLinks);
	includePathwaysCB.setSelected(option.includePathways);
	includeInteractionsCB.setSelected(option.includeInteractions);
    }

    private BioPAXToCytoscapeConverter.Option getOptions() {
	BioPAXToCytoscapeConverter.Option option = new BioPAXToCytoscapeConverter.Option();
	option.makeRootPathwayNode = makeRootPathwayNodeCB.isSelected();
	option.includeNextLinks = includeNextLinksCB.isSelected();
	option.includePathways = includePathwaysCB.isSelected();
	option.includeInteractions = includeInteractionsCB.isSelected();
	return option;
    }

    private int[] getAlgos() {
	int algos[] = new int[3];

	int n = 0;

	if (reactionCB.isSelected())
	    algos[n++] = BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION;

	if (pathwayCB.isSelected())
	    algos[n++] = BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION;

	if (proteinCB.isSelected())
	    algos[n++] = BioPAXToCytoscapeConverter.INTERACTION_CONVERSION;

	return algos;
    }

    public void actionPerformed(ActionEvent e) {
	makeRootPathwayNodeCB.setEnabled(pathwayCB.isSelected());
	includeNextLinksCB.setEnabled(pathwayCB.isSelected());
	includePathwaysCB.setEnabled(pathwayCB.isSelected());
	includeInteractionsCB.setEnabled(pathwayCB.isSelected());
    }
}
