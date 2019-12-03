package fr.curie.BiNoM.celldesigner.plugin;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import jp.sbi.celldesigner.plugin.PluginAction;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginModificationResidue;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.celldesigner.lib.NetworkFactory;
import java.util.Vector;
import edu.rpi.cs.xgmml.GraphDocument;

public class BiNoMPluginAction extends PluginAction {
    BiNoMPluginDialog dialog;
    BiNoMPlugin plugin;
	
    public BiNoMPluginAction(BiNoMPlugin _plugin){
	plugin = _plugin;
    }

    static int idCount = 0;

    static PluginModel model;

    void test() {
	try{

	    String prefix = "/home/viara/projects/BiNoM/examples/M-Phase2.owl";

	    // This example reads BioPAX, creates reaction network from it, 
	    // decomposes the network into connected components,
	    // and saves each component to new BioPAX owl file and to SBML format
	
	    BioPAXToCytoscapeConverter.Graph graph = 
		BioPAXToCytoscapeConverter.convert(
						   BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION,
						   prefix,
						   "Apoptosis",
						   new BioPAXToCytoscapeConverter.Option()
						   );

	    if (model == null) {
		model = plugin.getSelectedModel();
	    }

	    NetworkFactory.createNetwork(model, plugin, graph.graphDocument);

	} catch(Exception e) {
	    e.printStackTrace();
	}
    }

    public void myActionPerformed(ActionEvent e) {
	//test();

	/*
	if (model == null) {
	    model = plugin.getSelectedModel();
	}
	*/

	/*
	System.out.println("adding one species to " + model.getName());
	PluginSpecies species = new PluginSpecies(PluginSpeciesSymbolType.PROTEIN , "BiNoM_" + ++idCount);

	model.addSpecies(species);
	species.getSpeciesAlias(0).setFramePosition(40, 40);

	PluginModificationResidue r = new PluginModificationResidue(species.getSpeciesAlias(0).getProtein());
	r.setName("bb");			
	species.getSpeciesAlias(0).getProtein().addPluginModificationResidue(r);
	plugin.notifySBaseAdded(species);
	*/

	//display();
    }

    void display() {
	model = plugin.getSelectedModel();
	System.out.println("display of " + model.getName() + " / " + plugin.getAllModels().size());

	PluginListOf protList = model.getListOfProteins();
	int protSize = protList.size();
	for (int n = 0; n < protSize; n++) {
	    System.out.println("prot #" + n + " " + protList.get(n).toSBML() + " " + protList.get(n).getNotes());
	}

	PluginListOf reactList = model.getListOfReactions();
	int reactSize = reactList.size();
	for (int n = 0; n < reactSize; n++) {
	    System.out.println("react #" + n + " " + reactList.get(n).toSBML());
	    PluginReaction react = (PluginReaction)reactList.get(n);
	    int prodCnt = react.getNumProducts();
	    int reactCnt = react.getNumReactants();
	    for (int i = 0; i < prodCnt; i++) {
		for (int j = 0; j < reactCnt; j++) {
		    System.out.println(react.getProduct(i).toSBML() + " <-> " + react.getReactant(j).toSBML());
		}
	    }
	}

	PluginListOf speciesList = model.getListOfSpecies();
	int speciesSize = speciesList.size();
	for (int n = 0; n < speciesSize; n++) {
	    System.out.println("species #" + n + " " + speciesList.get(n).toSBML() + " " + speciesList.get(n).getNotes() + " " + speciesList.get(n).getNotesString());
	}
	PluginSBase protArr[] = protList.toArray();
	PluginSBase reactArr[] = reactList.toArray();
	/*
	dialog = new BiNoMPluginDialog(plugin);
	dialog.pack();
	dialog.toFront();
	dialog.setAlwaysOnTop(true);
	dialog.setVisible(true);
	*/
    }
}
