package fr.curie.BiNoM.celldesigner.plugin;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModification;
import jp.sbi.celldesigner.plugin.PluginModificationResidue;
import jp.sbi.celldesigner.plugin.PluginProtein;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;



public class BiNoMPluginDialog extends JDialog {

    private JPanel jContentPane = null;
    private JLabel jLabelName = null;
    private JLabel jLabelId = null;
    private JLabel jLabelX = null;
    private JLabel jLabelY = null;
    private JTextField textName = null;
    private JTextField textId = null;
    private JTextField textX = null;
    private JTextField textY = null;
    private JPanel jPanel = null;
    private JButton jButtonGET = null;
    private JButton jButtonADD = null;
	
    public static BiNoMPlugin plug;

    /**
     * This is the default constructor
     */
    public BiNoMPluginDialog(BiNoMPlugin _plugin) {
	plug = _plugin;
	initialize();
    }


    public BiNoMPluginDialog(Frame arg0) throws HeadlessException {
	super(arg0);
	initialize();
    }

    public BiNoMPluginDialog(Frame arg0, boolean arg1)
	throws HeadlessException {
	super(arg0, arg1);
	initialize();
    }

    public BiNoMPluginDialog(Frame arg0, String arg1) throws HeadlessException {
	super(arg0, arg1);
	initialize();
    }

    public BiNoMPluginDialog(Frame arg0, String arg1, boolean arg2)
	throws HeadlessException {
	super(arg0, arg1, arg2);
	initialize();
    }

    public BiNoMPluginDialog(Frame arg0, String arg1, boolean arg2,
			      GraphicsConfiguration arg3) {
	super(arg0, arg1, arg2, arg3);
	initialize();
    }

    public BiNoMPluginDialog(Dialog arg0) throws HeadlessException {
	super(arg0);
	initialize();
    }

    public BiNoMPluginDialog(Dialog arg0, boolean arg1)
	throws HeadlessException {
	super(arg0, arg1);
	initialize();
    }

    public BiNoMPluginDialog(Dialog arg0, String arg1)
	throws HeadlessException {
	super(arg0, arg1);
	initialize();
    }

    public BiNoMPluginDialog(Dialog arg0, String arg1, boolean arg2)
	throws HeadlessException {
	super(arg0, arg1, arg2);
	initialize();
    }

    public BiNoMPluginDialog(Dialog arg0, String arg1, boolean arg2,
			      GraphicsConfiguration arg3) throws HeadlessException {
	super(arg0, arg1, arg2, arg3);
	initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
	this.setSize(300, 200);
	this.setTitle("Species Information");
	this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (jContentPane == null) {
	    GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
	    gridBagConstraints28.gridx = 1;
	    gridBagConstraints28.gridy = 5;
	    GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
	    gridBagConstraints27.fill = java.awt.GridBagConstraints.HORIZONTAL;
	    gridBagConstraints27.gridy = 4;
	    gridBagConstraints27.weightx = 1.0;
	    gridBagConstraints27.insets = new java.awt.Insets(5,5,5,5);
	    gridBagConstraints27.gridx = 1;
	    GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
	    gridBagConstraints26.fill = java.awt.GridBagConstraints.HORIZONTAL;
	    gridBagConstraints26.gridy = 3;
	    gridBagConstraints26.weightx = 1.0;
	    gridBagConstraints26.insets = new java.awt.Insets(5,5,5,5);
	    gridBagConstraints26.gridx = 1;
	    GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
	    gridBagConstraints25.fill = java.awt.GridBagConstraints.HORIZONTAL;
	    gridBagConstraints25.gridy = 2;
	    gridBagConstraints25.weightx = 1.0;
	    gridBagConstraints25.insets = new java.awt.Insets(5,5,5,5);
	    gridBagConstraints25.gridx = 1;
	    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
	    gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
	    gridBagConstraints4.gridy = 1;
	    gridBagConstraints4.weightx = 1.0;
	    gridBagConstraints4.insets = new java.awt.Insets(5,5,5,5);
	    gridBagConstraints4.gridx = 1;
	    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
	    gridBagConstraints3.gridx = 0;
	    gridBagConstraints3.gridy = 4;
	    jLabelY = new JLabel();
	    jLabelY.setText("Y");
	    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
	    gridBagConstraints2.gridx = 0;
	    gridBagConstraints2.gridy = 3;
	    jLabelX = new JLabel();
	    jLabelX.setText("X");
	    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
	    gridBagConstraints1.gridx = 0;
	    gridBagConstraints1.gridy = 2;
	    jLabelId = new JLabel();
	    jLabelId.setText("ID");
	    GridBagConstraints gridBagConstraints = new GridBagConstraints();
	    gridBagConstraints.gridx = 0;
	    gridBagConstraints.gridy = 1;
	    jLabelName = new JLabel();
	    jLabelName.setText("Name");
	    jContentPane = new JPanel();
	    jContentPane.setLayout(new GridBagLayout());
	    jContentPane.add(jLabelName, gridBagConstraints);
	    jContentPane.add(jLabelId, gridBagConstraints1);
	    jContentPane.add(jLabelX, gridBagConstraints2);
	    jContentPane.add(jLabelY, gridBagConstraints3);
	    jContentPane.add(getTextName(), gridBagConstraints4);
	    jContentPane.add(getTextId(), gridBagConstraints25);
	    jContentPane.add(getTextX(), gridBagConstraints26);
	    jContentPane.add(getTextY(), gridBagConstraints27);
	    jContentPane.add(getJPanel(), gridBagConstraints28);
	}
	return jContentPane;
    }

    /**
     * This method initializes textName	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTextName() {
	if (textName == null) {
	    textName = new JTextField();
	}
	return textName;
    }

    /**
     * This method initializes textId	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTextId() {
	if (textId == null) {
	    textId = new JTextField();
	}
	return textId;
    }

    /**
     * This method initializes jTextField	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTextX() {
	if (textX == null) {
	    textX = new JTextField();
	}
	return textX;
    }

    /**
     * This method initializes textY	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTextY() {
	if (textY == null) {
	    textY = new JTextField();
	}
	return textY;
    }

    /**
     * This method initializes jPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanel() {
	if (jPanel == null) {
	    jPanel = new JPanel();
	    jPanel.add(getJButtonGET(), null);
	    jPanel.add(getJButtonADD(), null);
	}
	return jPanel;
    }

    /**
     * This method initializes jButtonGET	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonGET() {
	if (jButtonGET == null) {
	    jButtonGET = new JButton();
	    jButtonGET.setText("GET");
	    jButtonGET.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent e) {
			getSelectedSpecies();
		    }
		});
	}
	return jButtonGET;
    }

    /**
     * get information on selected species
     *
     */
    private void getSelectedSpecies() {
	//get selected Species		
	PluginListOf listOf = plug.getSelectedSpeciesNode();

	if(listOf != null){
	    //get PluginSpeciesAlias
	    PluginSpeciesAlias alias = (PluginSpeciesAlias)listOf.get(0);

	    if(! (alias instanceof PluginSpeciesAlias)){
		JOptionPane.showMessageDialog(
					      this, "No species selected!!","error",
					      JOptionPane.ERROR_MESSAGE
					      );
		return;
	    }
	    //get position
	    double pos_x = alias.getX();
	    double pos_y = alias.getY();
			
	    //get Species
	    PluginSpecies sp = alias.getSpecies();
			
	    //Show species information
	    textName.setText(sp.getName());
	    textId.setText(sp.getId());
	    textX.setText(String.valueOf(pos_x).toString());
	    textY.setText(String.valueOf(pos_y).toString());
	}
	else{
	    JOptionPane.showMessageDialog(
					  this, "No species selected!!","error",
					  JOptionPane.ERROR_MESSAGE
					  );
	}
		
    }
	
    /**
     * This method initializes jButtonADD	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonADD() {
	if (jButtonADD == null) {
	    jButtonADD = new JButton();
	    jButtonADD.setText("ADD");
	    jButtonADD.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent e) {
			addSpecies();
		    }
		});
	}
	return jButtonADD;
    }

    /**
     *  Add new Species
     * 
     */
    private void addSpecies() {
	// (1) get selected model
	PluginModel model = plug.getSelectedModel();
		
	//get name of Species
	String name = textName.getText();
		
	// (2) create PluginSBase
	PluginSBase sbase 
	    = new PluginSpecies(PluginSpeciesSymbolType.PROTEIN_GENERIC, name);			

	// (3) set position
	String x = textX.getText();
	String y = textY.getText();
		
	try{
	    double pos_x = Double.valueOf(x).doubleValue();
	    double pos_y = Double.valueOf(y).doubleValue();
		
	    PluginSpecies species = (PluginSpecies)sbase;
			
	    PluginSpeciesAlias psa = species.getSpeciesAlias(0);
	    psa.setFramePosition(pos_x,pos_y);
		
	    // (4) set species into model
	    model.addSpecies(species);
	    plug.notifySBaseAdded((PluginSBase)species);

	    /*
	    //Add residue
	    PluginProtein protein = psa.getProtein();
	    PluginModificationResidue residue = new PluginModificationResidue(protein);
	    residue.setName("P");
	    protein.addPluginModificationResidue(residue);
	    plug.notifySBaseChanged(residue);
			
	    //add modification
	    PluginModification m = new PluginModification();
	    m.setResidue(residue.getId());
	    m.setState(PluginModification.STATE_PHOSPHORYLATED);
	    psa.getModifications().append(m);
			
	    plug.notifySBaseChanged(psa);
	    */
			
	    textX.setText(null);
	    textY.setText(null);
		
	} catch(NumberFormatException e){
	    JOptionPane.showMessageDialog(
					  this, "Input number!!","error",
					  JOptionPane.ERROR_MESSAGE
					  );
			
	}
    }
}
