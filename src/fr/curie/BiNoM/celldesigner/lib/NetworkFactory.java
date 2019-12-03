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
package fr.curie.BiNoM.celldesigner.lib;

import java.io.*;
import edu.rpi.cs.xgmml.GraphDocument;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import jp.sbi.celldesigner.plugin.PluginAction;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginModificationResidue;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;
import jp.sbi.celldesigner.plugin.util.PluginReactionSymbolType;
import jp.sbi.celldesigner.plugin.*;
import jp.sbi.celldesigner.*;

import java.util.*;

import org.biojava.bio.program.homologene.OrthoPairSet.Iterator;

import com.ibm.icu.util.StringTokenizer;

public class NetworkFactory {

    public static final String ATTR_SEP = "@@@";

    static final String BIOPAX_NODE_TYPE = "BIOPAX_NODE_TYPE";
    static final String BIOPAX_REACTION = "BIOPAX_REACTION";
    static final String BIOPAX_SPECIES = "BIOPAX_SPECIES";
    static final String BIOPAX_URI = "BIOPAX_URI";
    static final String BIOPAX_EDGE_TYPE = "BIOPAX_EDGE_TYPE";
    static final String BIOPAX_EDGE_ID = "BIOPAX_EDGE_ID";
    static final String RIGHT_EDGE_TYPE = "RIGHT";
    static final String LEFT_EDGE_TYPE = "LEFT";
    static final String CATALYSIS_EDGE_TYPE = "CATALYSIS_ACTIVATION";

    private static void createSpecies(PluginModel model,
				       CellDesignerPlugin plugin,
				       edu.rpi.cs.xgmml.GraphicNode nodes[],
				       Vector<edu.rpi.cs.xgmml.GraphicNode> reactionNode_v,
				       HashMap<String, String> id_map) {

	for (int n = 0; n < nodes.length; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
	    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = node.getAttArray();
	    
	    String node_type = null;
	    int uri_cnt = 1;
	    String reaction = null;
	    String species = null;
	    String notes = "";
	    String uri = "";

	    for (int j = 0; j < attrs.length; j++) {
		edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
		String attrValue = attr.getValue();
		if (attrValue != null) {
		    if (attr.getLabel().equals(BIOPAX_NODE_TYPE)) {
			node_type = attrValue;
		    }
		    else if (attr.getLabel().equals(BIOPAX_REACTION)) {
			reaction = attrValue;
		    }
		    else if (attr.getLabel().equals(BIOPAX_URI)) {
			String tag = "uri";
			if (uri_cnt != 1) {
			    tag += uri_cnt;
			}
			notes += "<" + tag + ">" + attrValue + "</" + tag + ">\n";
			uri_cnt++;
			if (uri.length() > 0) {
			    uri += ATTR_SEP;
			}
			
			uri += attrValue;
		    }
		    else if (attr.getLabel().equals(BIOPAX_SPECIES)) {
			species = attrValue;
		    }
		    else {
			notes += "<" + attr.getLabel() + ">" + attrValue + "</" + attr.getLabel() + ">\n";
		    }
		}
	    }

	    if (reaction != null) {
		reactionNode_v.add(node);
		continue;
	    }

	    String ptype;

	    if (node_type.equalsIgnoreCase("protein")) {
		ptype = PluginSpeciesSymbolType.PROTEIN;
	    }
	    else if (node_type.equalsIgnoreCase("complex")) {
		ptype = PluginSpeciesSymbolType.COMPLEX;
	    }
	    else if (node_type.equalsIgnoreCase("dna")) {
			ptype = PluginSpeciesSymbolType.GENE;
		}
	    else if (node_type.equalsIgnoreCase("rna")) {
			ptype = PluginSpeciesSymbolType.RNA;
		}
	    else if (node_type.equalsIgnoreCase("smallMolecule")) {
			ptype = PluginSpeciesSymbolType.SIMPLE_MOLECULE;
		}	    
	    else {
		System.out.println("unknown type " + node_type);
		ptype = PluginSpeciesSymbolType.PROTEIN;
	    }

	    PluginSpecies pspecies = createNewSpecies(ptype,node.getLabel());
	    
	    //pspecies.
	    
	    if (notes.length() > 0) {
		// this does not work !!
		pspecies.setNotes(notes);
	    }

	    model.addSpecies(pspecies);
	    pspecies.getSpeciesAlias(0).setFramePosition(40+n*10, 40+n*40);
	    pspecies.getSpeciesAlias(0).setFrameSize(8 * node.getLabel().length(), 30);
	    plugin.notifySBaseAdded(pspecies);
	    id_map.put((String)node.getLabel(), pspecies.getId());

	    //	    BioPAXSourceDB.getInstance().setURI(species, uri);
	    BioPAXSourceDB.getInstance().setURI(node.getLabel(), uri);
	}
    }

    private static void createReactions(PluginModel model,
					CellDesignerPlugin plugin,
					edu.rpi.cs.xgmml.GraphicEdge edges[],
					Vector<edu.rpi.cs.xgmml.GraphicNode> reactionNode_v,
					HashMap<String, String> id_map) {

	HashMap<String, Vector<edu.rpi.cs.xgmml.GraphicEdge> > sourceEdge_v = new HashMap();
	HashMap<String, Vector<edu.rpi.cs.xgmml.GraphicEdge> > targetEdge_v = new HashMap();

	for (int n = 0; n < edges.length; n++) {
	    edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];

	    Vector v = sourceEdge_v.get(edge.getSource());
	    if (v == null) {
		v = new Vector();
	    }

	    v.add(edge);

	    sourceEdge_v.put(edge.getSource(), v);

	    v = targetEdge_v.get(edge.getTarget());
	    if (v == null) {
		v = new Vector();
	    }

	    v.add(edge);

	    targetEdge_v.put(edge.getTarget(), v);
	}

	int reactionNode_size = reactionNode_v.size();

	for (int n = 0; n < reactionNode_size; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = reactionNode_v.get(n);
	    Vector<edu.rpi.cs.xgmml.GraphicEdge> source_v = sourceEdge_v.get(node.getId());
	    Vector<edu.rpi.cs.xgmml.GraphicEdge> target_v = targetEdge_v.get(node.getId());
	    if (source_v == null && target_v == null) {
		continue;
	    }

	    Vector<PluginSpecies> reactant_v = new Vector();
	    Vector<PluginSpecies> product_v = new Vector();
	    Vector<PluginSpecies> modifier_v = new Vector();

	    if (source_v != null) {
		for (int m = 0; m < source_v.size(); m++) {
		    edu.rpi.cs.xgmml.GraphicEdge edge = source_v.get(m);
		    PluginSpecies pspecies = model.getSpecies(id_map.get(edge.getTarget()));
		    assert pspecies != null;
		    String type = getEdgeType(edge);
		    if (type.equalsIgnoreCase(RIGHT_EDGE_TYPE)) {
			product_v.add(pspecies);
		    }
		    else if (type.equalsIgnoreCase(LEFT_EDGE_TYPE)) {
			reactant_v.add(pspecies);
		    }
		    else if (type.equalsIgnoreCase(CATALYSIS_EDGE_TYPE)) {
			modifier_v.add(pspecies);
		    }
		    else {
			assert false;
		    }
		}
	    }

	    if (target_v != null) {
		for (int m = 0; m < target_v.size(); m++) {
		    edu.rpi.cs.xgmml.GraphicEdge edge = target_v.get(m);
		    PluginSpecies pspecies = model.getSpecies(id_map.get(edge.getSource()));
		    assert pspecies != null;
		    String type = getEdgeType(edge);
		    if (type.equalsIgnoreCase(RIGHT_EDGE_TYPE)) {
			product_v.add(pspecies);
		    }
		    else if (type.equalsIgnoreCase(LEFT_EDGE_TYPE)) {
			reactant_v.add(pspecies);
		    }
		    else if (type.equalsIgnoreCase(CATALYSIS_EDGE_TYPE)) {
			modifier_v.add(pspecies);
		    }
		    else {
			assert false;
		    }
		}
	    }

	    PluginReaction reaction = new PluginReaction();

	    reaction.setReversible(false);
	    if (reactant_v.size() > 1) {
		reaction.setReactionType(PluginReactionSymbolType.HETERODIMER_ASSOCIATION);
	    }
	    else if (product_v.size() > 1) {
		reaction.setReactionType(PluginReactionSymbolType.DISSOCIATION);
	    }
	    else {
		reaction.setReactionType(PluginReactionSymbolType.STATE_TRANSITION);
	    }

	    assert product_v.size() + reactant_v.size() + modifier_v.size() > 0;

	    for (int j = 0; j < product_v.size(); j++) {
		PluginSpeciesReference nref = new PluginSpeciesReference(reaction, product_v.get(j).getSpeciesAlias(0));
		reaction.addProduct(nref);
	    }

	    for (int j = 0; j < reactant_v.size(); j++) {
		PluginSpeciesReference nref = new PluginSpeciesReference(reaction, reactant_v.get(j).getSpeciesAlias(0));
		reaction.addReactant(nref);
	    }

	    for (int j = 0; j < modifier_v.size(); j++) {
		PluginModifierSpeciesReference nref = new PluginModifierSpeciesReference(reaction, modifier_v.get(j).getSpeciesAlias(0));
		nref.setModificationType(PluginReactionSymbolType.TRANSPORT);
		reaction.addModifier(nref);
	    }

	    model.addReaction(reaction);
	    System.out.println("REACTION " + node.getId());
	    plugin.notifySBaseAdded(reaction);
	}
    }

    private static String getEdgeType(edu.rpi.cs.xgmml.GraphicEdge edge) {
	edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();
	    
	for (int j = 0; j < attrs.length; j++) {
	    edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
	    if (attr.getLabel().equals(BIOPAX_EDGE_TYPE)) {
		return attr.getValue();
	    }
	}

	return null;
    }

    public static void createNetwork
	(PluginModel model,
	 CellDesignerPlugin plugin,
	 GraphDocument graphDocument) throws Exception{

	edu.rpi.cs.xgmml.GraphicGraph grf = graphDocument.getGraph();

	edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
	
	HashMap<String,Integer> compartmentNames = getCompartmentNames(nodes);
	createCompartments(compartmentNames,model,plugin);
	
	
	HashMap<String, String> id_map = new HashMap();
	Vector<edu.rpi.cs.xgmml.GraphicNode> reactionNode_v = new Vector();
	createSpecies(model, plugin, nodes, reactionNode_v, id_map);

	edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();
	createReactions(model, plugin, edges, reactionNode_v, id_map);
    }
    
    public static HashMap<String,Integer> getCompartmentNames(edu.rpi.cs.xgmml.GraphicNode nodes[]){
    	HashMap<String,Integer> names = new HashMap<String,Integer>();
    	for(int i=0;i<nodes.length;i++){
    		edu.rpi.cs.xgmml.GraphicNode n = nodes[i];
    		String name = n.getName();
    		StringTokenizer st = new StringTokenizer(name,"@");
    		st.nextToken();
    		if(st.hasMoreTokens()){
    			String compname = st.nextToken();
    			if(!compname.toLowerCase().contains("unknonwn")){
    				Integer count = names.get(compname);
    				if(count==null) count = new Integer(0);
    				names.put(compname, count+1);
    			}
    		}
    	}
    	return names;
    }
    
    public static void createCompartments(HashMap<String,Integer> names, PluginModel model, CellDesignerPlugin plugin){
    	Set<String> set  = names.keySet();
    	java.util.Iterator<String> it = set.iterator();
    	while(it.hasNext()){
    		String compname = it.next();
    		int count = names.get(compname);
    		PluginCompartment compartment = new PluginCompartment("SQUARE");
    		compartment.setName(compname);
    		model.addCompartment(compartment);
    	}
    }
    
    public static PluginSpecies createNewSpecies(String ptype, String label){
    	PluginSpecies pspecies = null;
    	StringTokenizer st = new StringTokenizer(label,"@");
    	String strLabel = st.nextToken();
    	if(ptype.equals("protein")){
    		pspecies = createSimpleSpecies(ptype,strLabel); 
    	}else
    	if(ptype.equals("complex")){
    		pspecies = createComplexSpecies(ptype,strLabel);
    	}
    	return pspecies;
    }
    
    public static PluginSpecies createSimpleSpecies(String ptype, String label){
    	PluginSpecies pspecies = null;
		StringTokenizer st = new StringTokenizer(label,"|");
		String nameBase = st.nextToken();
		Vector<String> modifications = new Vector<String>();
		while(st.hasMoreTokens()) modifications.add(st.nextToken());
		pspecies = new PluginSpecies(ptype,nameBase);
    	return pspecies;
    }
    
    public static PluginSpecies createComplexSpecies(String ptype, String label){
    	PluginSpecies pspecies = null;
    	pspecies = new PluginSpecies(ptype,label);
    	return pspecies;
    }
    
    public static HashMap<String,PluginProtein> createProteins(edu.rpi.cs.xgmml.GraphicNode nodes[]){
    	HashMap<String,PluginProtein> proteinMap = new HashMap<String,PluginProtein>();
    	return proteinMap;
    }
    
    
}


/*
  for (int n = 0; n < edges.length; n++) {
  edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];
  System.out.println("edge #" + n + " " + edge.getId() + " " +
  edge.getLabel() + " " +
  edge.getSource() + " " +
  edge.getTarget());

  edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();
	    
  String edge_type = null;
  String edge_id = null;
  String uri = null;

  for (int j = 0; j < attrs.length; j++) {
  edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
  String attrValue = attr.getValue();
  if (attrValue != null) {
  //System.out.println(attr.getLabel() + " -> " + attr.getValue());
  if (attr.getLabel().equals(BIOPAX_EDGE_TYPE)) {
  edge_type = attrValue;
  }
  else if (attr.getLabel().equals(BIOPAX_EDGE_ID)) {
  edge_id = attrValue;
  }
  else if (attr.getLabel().equals(BIOPAX_URI)) {
  uri = attrValue;
  }
  else {
  System.out.println("unknown attribute: " + attr.getLabel() + " -> " + attr.getValue());
  }
  }
  }
  }
*/
