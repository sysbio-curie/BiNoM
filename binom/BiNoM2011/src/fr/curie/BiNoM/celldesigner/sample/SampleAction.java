package fr.curie.BiNoM.celldesigner.sample;

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
import java.util.Vector;
import edu.rpi.cs.xgmml.GraphDocument;

public class SampleAction extends PluginAction {
    SamplePluginDialog dialog;
    SamplePlugin plugin;
	
    static final String BIOPAX_NODE_TYPE = "BIOPAX_NODE_TYPE";
    static final String BIOPAX_REACTION = "BIOPAX_REACTION";

    public SampleAction(SamplePlugin _plugin){
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

	    /*
	    Vector components = StructureAnalysisUtils.getConnectedComponents(grdoc);

	    System.out.println(""+BioPAXUtilities.numberOfStatements(graph.biopax.biopaxmodel)+"\t"+BioPAXUtilities.numberOfStatements(graph.biopax.model));
	
	    for(int i=0;i<components.size();i++){
		GraphDocument gri = (GraphDocument)components.get(i);
		BioPAX biopax = new BioPAX();		
		biopax.model = BioPAXUtilities.extractFromModel(graph.biopax.model,gri);
		System.out.println(""+BioPAXUtilities.numberOfStatements(biopax.model));
		BioPAXUtilities.saveModel(biopax.model,prefix+"_"+(i+1)+".owl",BioPAX.biopaxString);
	    }

	    */

	    if (model == null) {
		model = plugin.getSelectedModel();
	    }

	    edu.rpi.cs.xgmml.GraphicGraph grf = graph.graphDocument.getGraph();
	    edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
	    edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();

	    for (int n = 0; n < nodes.length; n++) {
		edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
		PluginSpecies species = new PluginSpecies(PluginSpeciesSymbolType.PROTEIN , (String)node.getLabel());
		edu.rpi.cs.xgmml.AttDocument.Att attrs[] = node.getAttArray();

		for (int j = 0; j < attrs.length; j++) {
		    edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
		    String attrValue = attr.getValue();
		    if (attrValue != null) {
			System.out.println(attr.getLabel() + " -> " + attr.getValue());
		    }
		}

		model.addSpecies(species);
		species.getSpeciesAlias(0).setFramePosition(40+n*10, 40+n*30);
		species.getSpeciesAlias(0).setFrameSize(8 * node.getLabel().length(), 30);
		plugin.notifySBaseAdded(species);
	    }

	    for (int n = 0; n < edges.length; n++) {
		edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];
		System.out.println("edge " + edge.getLabel());
		edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();

		for (int j = 0; j < attrs.length; j++) {
		    edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
		    String attrValue = attr.getValue();
		    if (attrValue != null) {
			System.out.println(attr.getLabel() + " -> " + attr.getValue());
		    }
		}
	    }

	} catch(Exception e) {
	    e.printStackTrace();
	}

    }

    public void myActionPerformed(ActionEvent e) {
	test();

	if (model == null) {
	    model = plugin.getSelectedModel();
	}

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
	dialog = new SamplePluginDialog(plugin);
	dialog.pack();
	dialog.toFront();
	dialog.setAlwaysOnTop(true);
	dialog.setVisible(true);
	*/
    }
}
