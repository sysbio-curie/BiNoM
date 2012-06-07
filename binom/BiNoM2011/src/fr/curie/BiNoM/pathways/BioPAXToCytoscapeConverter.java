/*
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

package fr.curie.BiNoM.pathways;


import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import java.io.*;
import java.util.*;
import org.sbml.x2001.ns.celldesigner.*;
import edu.rpi.cs.xgmml.*;

/**
 * <p>Converts BioPAX (represented by BioPAX object) to XGMML (represented by GraphDocument object)
 *
 */
@SuppressWarnings("unchecked")
public class BioPAXToCytoscapeConverter extends BioPAXToSBMLConverter {

	/**
	 * Mode of conversion: from BioPAX to full index (see BiNoM documentation)
	 */
	public static final int FULL_INDEX_CONVERSION = 0;
	/**
	 * Mode of conversion: from BioPAX to Reaction Network BioPAX interface
	 */
	public static final int REACTION_NETWORK_CONVERSION = 1;
	/**
	 * Mode of conversion: from BioPAX to Pathway Structure BioPAX interface
	 */
	public static final int PATHWAY_STRUCTURE_CONVERSION = 2;
	/**
	 * Mode of conversion: from BioPAX to Protein-protein interaction BioPAX interface
	 */
	public static final int PROTEIN_PROTEIN_INTERACTION_CONVERSION = 3;


	public Vector<String> pathwaysAdded = null;

	/**
	 * <p>Little auxiliary container for BioPAX and GraphDocument object
	 *
	 */
	public static class Graph {
		public GraphDocument graphDocument;
		public BioPAX biopax;

		Graph(GraphDocument graphDocument, BioPAX biopax) {
			this.graphDocument = graphDocument;
			this.biopax = biopax;
		}
	}

	/**
	 * <p>Set of conversion options
	 */
	public static class Option {

		public boolean makeRootPathwayNode;
		public boolean includeNextLinks;
		public boolean includePathways;
		public boolean includeInteractions;
		public boolean distinguishCompartments;
		public boolean distinguishModifications;

		public Option() {

			makeRootPathwayNode = false;
			includeNextLinks = false;
			includePathways = true;
			includeInteractions = true;
			distinguishCompartments = false;
			distinguishModifications = false;
		}
	}

	/**
	 * <p>Convert from InputStream and return fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter.Graph
	 */
	public static Graph convert(int algo, InputStream is, String name,
			Option option)
	throws Exception {
		BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
		b2c.biopax.loadBioPAX(is);
		return convert(algo, b2c, name, option);
	}

	/**
	 * <p>Convert using BioPAXToCytoscapeConverter and return fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter.Graph
	 */
	public static Graph convert(int algo, BioPAXToCytoscapeConverter b2c,
			String name, Option option) 
	throws Exception {

		if(b2c.biopax.model==null)
			b2c.biopax.model = b2c.biopax.biopaxmodel;

		if (algo == FULL_INDEX_CONVERSION) {
			BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
			return new Graph(XGMML.convertGraphToXGMML(bgms.mapBioPAXToGraph(b2c.biopax)),b2c.biopax);
		}

		if (algo == REACTION_NETWORK_CONVERSION) {
			b2c.populateSbml();
			return new Graph(b2c.getXGMMLGraph(name, b2c.sbmlDoc),
					b2c.biopax);
		}

		if (algo == PATHWAY_STRUCTURE_CONVERSION) {
			b2c.bpnm.generateNames(b2c.biopax, false);
			return new Graph(b2c.getXGMMLPathwayGraph(name, b2c.biopax, option),
					b2c.biopax);
		}

		if (algo == PROTEIN_PROTEIN_INTERACTION_CONVERSION) {
			b2c.bpnm.generateNames(b2c.biopax, false);
			return new Graph(b2c.getXGMMLProteinGraph(name, b2c.biopax, option),
					b2c.biopax);
		}

		return null;
	}

	/**
	 * <p>A map from BioPAX file key to static set of BioPAX objects
	 * <p>It is kept to accelerate BioPAX access in case of frequent calls
	 * <p>for import of the same file 
	 */
	public static HashMap<String, BioPAX> biopax_map = new HashMap();

	/** key could be based on: filename + date + size
	 * or md5(filecontents);
	 *  
	 */
	public static String getFileKey(String file) {
		File f = new File(file);
		String fkey = file;
		if(f.exists()){
			fkey+="_"+f.lastModified();
			fkey+="_"+f.length();
		}
		return fkey;
	}
	/**
	 * <p>Convert using file name and return fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter.Graph
	 */
	public static Graph convert(int algo, String file, String name, Option option) throws Exception {

		String fileKey = getFileKey(file);
		BioPAX biopax = biopax_map.get(fileKey);

		if (biopax == null) {
			Graph graph =  convert(algo, new FileInputStream(file), name, option);
			biopax_map.put(fileKey, graph.biopax);
			return graph;
		}

		BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
		b2c.biopax = biopax;
		return convert(algo, b2c, name, option);
	}

	/**
	 * <p>Convert from file (specified by path) and return fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter.Graph
	 */
	public static Graph convert(int algo, String file, Option option)
	throws Exception {
		return convert(algo, file, file, option);
	}

	/**
	 * <p>Implementation of extracting Reaction Network interface
	 * (requires first conversion from BioPAX to SBML,
	 * from which the interface is converted).
	 * Also nees biopaxReactions, independentSpeciesIds, species,
	 * controls maps valid (they are filled by BioPAXToSBMLConverter.populateSbml()
	 * parent method )
	 */
	public GraphDocument getXGMMLGraph(String name, SbmlDocument sbml) throws Exception{

		GraphDocument gr = GraphDocument.Factory.newInstance();
		GraphicGraph grf = gr.addNewGraph();
		grf.setLabel(name);
		grf.setName(name);
		HashMap NodeIDs = new HashMap();
		HashMap EdgeIDs = new HashMap();


		for (int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++) {
			SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String id = sp.getId();
			String nam = "";
			if (species.get(id)!=null)if (((SpeciesDocument.Species)species.get(id)).getName()!=null)
				nam = ((SpeciesDocument.Species)species.get(id)).getName().getStringValue();
			if ((nam!=null)&&(!nam.startsWith("null"))) {
				GraphicNode n1 = grf.addNewNode();
				n1.setId(nam);
				n1.setLabel(nam);
				n1.setName(nam);
				BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(Utils.cutUri(id));
				if (part!=null) {
					Utils.addAttribute(n1,"BIOPAX_SPECIES","BIOPAX_SPECIES",id,ObjectType.STRING);
					if (part.type!=null) {
						Utils.addAttribute(n1,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",part.type,ObjectType.STRING);
					} else {
						Utils.addAttribute(n1,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","entity",ObjectType.STRING);
					}
					for(int k=0;k<part.sinonymSpecies.size();k++){
						Utils.addAttribute(n1,"BIOPAX_URI","BIOPAX_URI",((PhysicalEntity)part.sinonymSpecies.get(k)).uri(),ObjectType.STRING);
					}
				}
				NodeIDs.put(id,n1);
			}
		}


		for (int i=0;i<sbml.getSbml().getModel().getListOfReactions().getReactionArray().length;i++) {

			ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String rtype = "biochemicalReaction";
			Interaction react = null;
			if (reactions!=null)if (bioPAXreactions.get(r.getId())!=null) {
				react = (Interaction)bioPAXreactions.get(r.getId());
				rtype = react.getClass().getName();
				rtype = Utils.replaceString(rtype,"Impl","");
				rtype = Utils.replaceString(rtype,"fr.curie.BiNoM.pathways.biopax.","");
			}

			GraphicNode nreact = (GraphicNode)NodeIDs.get(r.getId());	    
			if (nreact == null) {
				nreact = grf.addNewNode();
				nreact.setId(r.getId());
				nreact.setLabel(r.getId());
				nreact.setName(r.getId());
				Utils.addAttribute(nreact,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",rtype,ObjectType.STRING);
				Utils.addAttribute(nreact,"BIOPAX_REACTION","BIOPAX_REACTION",r.getId(),ObjectType.STRING);
				if(bioPAXreactions.get(r.getId())!=null){
					Utils.addAttribute(nreact,"BIOPAX_URI","BIOPAX_URI",((Entity)bioPAXreactions.get(r.getId())).uri(),ObjectType.STRING);
					BioPAXGraphMapper.setReactionEffectAttribute((Interaction)bioPAXreactions.get(r.getId()), nreact, "EFFECT");
				}
				NodeIDs.put(r.getId(),nreact);
			}

			if (r.getListOfReactants()!=null)
				for (int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++) {
					System.out.println("REACTION "+r.getId());
					String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
					String nam = "";
					if (species.get(id)!=null)if (((SpeciesDocument.Species)species.get(id)).getName()!=null)
						nam = ((SpeciesDocument.Species)species.get(id)).getName().getStringValue();
					if ((nam!=null)&&(!nam.startsWith("null"))) {

						GraphicNode n1 = (GraphicNode)NodeIDs.get(id);
						GraphicNode n2 = (GraphicNode)NodeIDs.get(r.getId());
						if (n2 == null) {
							n2 = grf.addNewNode();
							n2.setId(r.getId());
							n2.setLabel(r.getId());
							n2.setName(r.getId());
							Utils.addAttribute(n2,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",rtype,ObjectType.STRING);
							Utils.addAttribute(n2,"BIOPAX_REACTION","BIOPAX_REACTION",r.getId(),ObjectType.STRING);
							if(bioPAXreactions.get(r.getId())!=null){
								Utils.addAttribute(n2,"BIOPAX_URI","BIOPAX_URI",((Entity)bioPAXreactions.get(r.getId())).uri(),ObjectType.STRING);
								BioPAXGraphMapper.setReactionEffectAttribute((Interaction)bioPAXreactions.get(r.getId()), n2, "EFFECT");			    		
							}
							NodeIDs.put(r.getId(),n2);
						}
						String eid = n1.getId()+" (LEFT) "+n2.getId();
						if(EdgeIDs.get(eid)==null){
							GraphicEdge e = grf.addNewEdge();
							e.setId(eid);
							EdgeIDs.put(eid, e);
							e.setLabel("LEFT");
							e.setSource(n1.getId());
							e.setTarget(n2.getId());
							Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","LEFT",ObjectType.STRING);
							Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n1.getId()+"(LEFT)"+n2.getId(),ObjectType.STRING);
						}
					}
				}
			if (r.getListOfProducts()!=null)
				for (int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++) {
					String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
					String nam = "";
					if (species.get(id)!=null)if (((SpeciesDocument.Species)species.get(id)).getName()!=null)
						nam = ((SpeciesDocument.Species)species.get(id)).getName().getStringValue();
					if ((nam!=null)&&(!nam.startsWith("null"))) {

						GraphicNode n2 = (GraphicNode)NodeIDs.get(id);
						GraphicNode n1 = (GraphicNode)NodeIDs.get(r.getId());
						if (n1 == null) {
							n1 = grf.addNewNode();
							n1.setId(r.getId());
							n1.setLabel(r.getId());
							n1.setName(r.getId());
							Utils.addAttribute(n1,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",rtype,ObjectType.STRING);
							Utils.addAttribute(n1,"BIOPAX_REACTION","BIOPAX_REACTION",r.getId(),ObjectType.STRING);
							if(bioPAXreactions.get(r.getId())!=null){
								Utils.addAttribute(n1,"BIOPAX_URI","BIOPAX_URI",((Entity)bioPAXreactions.get(r.getId())).uri(),ObjectType.STRING);
								BioPAXGraphMapper.setReactionEffectAttribute((Interaction)bioPAXreactions.get(r.getId()), n1, "EFFECT");		    		
							}
							NodeIDs.put(r.getId(),n1);
						}
						String eid = n1.getId()+" (RIGHT) "+n2.getId();
						if(EdgeIDs.get(eid)==null){
							GraphicEdge e = grf.addNewEdge();
							EdgeIDs.put(eid, e);
							e.setId(eid);
							e.setLabel("RIGHT");
							e.setSource(n1.getId());
							e.setTarget(n2.getId());
							Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","RIGHT",ObjectType.STRING);
							Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n1.getId()+"(RIGHT)"+n2.getId(),ObjectType.STRING);
						}
					}
				}
			if (r.getListOfModifiers()!=null)
				for (int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++) {
					String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
					String nam = "";
					if (species.get(id)!=null)if (((SpeciesDocument.Species)species.get(id)).getName()!=null)
						nam = ((SpeciesDocument.Species)species.get(id)).getName().getStringValue();
					if ((nam!=null)&&(!nam.startsWith("null"))) {

						GraphicNode n1 = (GraphicNode)NodeIDs.get(id);
						GraphicNode n2 = (GraphicNode)NodeIDs.get(r.getId());
						if (n2 == null) {
							n2 = grf.addNewNode();
							n2.setId(r.getId());
							n2.setLabel(r.getId());
							n2.setName(r.getId());
							Utils.addAttribute(n2,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",rtype,ObjectType.STRING);
							Utils.addAttribute(n2,"BIOPAX_REACTION","BIOPAX_REACTION",r.getId(),ObjectType.STRING);
							if(bioPAXreactions.get(r.getId())!=null){
								Utils.addAttribute(n2,"BIOPAX_URI","BIOPAX_URI",((Entity)bioPAXreactions.get(r.getId())).uri(),ObjectType.STRING);
								BioPAXGraphMapper.setReactionEffectAttribute((Interaction)bioPAXreactions.get(r.getId()), n2, "EFFECT");		    		
							}
							NodeIDs.put(r.getId(),n2);
						}
						String mtyp = "CONTROLLER";
						if (controls!=null) {
							String cid = r.getId()+"_"+id;
							Control cont = (Control)controls.get(cid);
							if(cont!=null){
								mtyp = cont.getClass().getName();
								mtyp = Utils.replaceString(mtyp,"Impl","");
								mtyp = Utils.replaceString(mtyp,"fr.curie.BiNoM.pathways.biopax.","");
								String mtypct = cont.getControlType();
								if((mtypct==null)||(mtypct.equals(""))||(mtypct.toLowerCase().equals("nil")))
									mtypct = "unknown";
								if(mtypct.indexOf("ACTIVATION")>=0) mtypct = "ACTIVATION";
								if(mtypct.indexOf("INHIBITION")>=0) mtypct = "INHIBITION";
								mtyp+="_"+mtypct;
								mtyp = mtyp.toUpperCase();
							}else{
								System.out.println("Control is not found "+cid);
							}
						}
						String eid = n1.getId()+" ("+mtyp+") "+n2.getId();
						if(EdgeIDs.get(eid)==null){
							GraphicEdge e = grf.addNewEdge();
							e.setId(eid);
							EdgeIDs.put(eid, e);
							e.setLabel(mtyp);
							e.setSource(n1.getId());
							e.setTarget(n2.getId());
							Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE",mtyp,ObjectType.STRING);
							Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",eid,ObjectType.STRING);
							if(controls!=null){
								String cid = r.getId()+"_"+id;
								Control cont = (Control)controls.get(cid);
								Utils.addAttribute(e,"BIOPAX_URI","BIOPAX_URI",cont.uri(),ObjectType.STRING);    						
							}
						}
					}
				}
		}
		return gr;
	}

	/**
	 * <p>Implementation of extracting Pathway Structure interface
	 * (requires first conversion from BioPAX to SBML,
	 * from which the interface is converted).
	 * Also nees biopaxReactions, independentSpeciesIds, species,
	 * controls and other maps valid (they are filled by BioPAXToSBMLConverter.populateSbml()
	 * parent method )
	 */
	public GraphDocument getXGMMLPathwayGraph(String name, BioPAX biopax,
			Option option) throws Exception {

		HashMap pathways = new HashMap();
		HashMap interactions = new HashMap();
		HashMap pathwaySteps = new HashMap();
		HashMap nodes = new HashMap();
		HashMap edges = new HashMap();	

		List pl = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
		for (int i=0;i<pl.size();i++) {
			Pathway p = (Pathway)pl.get(i);
			pathways.put(p.uri(),p);
		}
		
		pl = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
		for (int i=0;i<pl.size();i++) {
			PathwayStep p = (PathwayStep)pl.get(i);
			pathwaySteps.put(p.uri(),p);
		}
		
		pl = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalPathwayStep(biopax.model);
		for (int i=0;i<pl.size();i++) {
			PathwayStep p = (PathwayStep)pl.get(i);
			pathwaySteps.put(p.uri(),p);
		}
		
		pl = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		pl = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
		for (int i=0;i<pl.size();i++) { interactions.put(((Entity)pl.get(i)).uri(),pl.get(i));   }
		
		GraphDocument gr = GraphDocument.Factory.newInstance();
		GraphicGraph grf = gr.addNewGraph();
		grf.setLabel(name+"_pathways");
		grf.setName(name+"_pathways");

		GraphicNode n = null;

		if (option.includePathways) {

			if (option.makeRootPathwayNode) {
				n = grf.addNewNode();
				n.setId("ROOT:"+name);
				n.setName("ROOT:"+name);
				n.setLabel("ROOT:"+name);
				nodes.put("ROOT:"+name,n);
			}
			pathwaysAdded = new Vector<String>();
			pl = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
			for (int i=0;i<pl.size();i++) {
				Pathway p = (Pathway)pl.get(i);
				GraphicNode n1 = addPathwayNode(grf,p,pathways,interactions,pathwaySteps,nodes, edges, option);
				if (option.makeRootPathwayNode) {
					if(edges.get(n.getId()+" ("+"CONTAINS"+") "+n1.getId())==null){
						GraphicEdge e = grf.addNewEdge();
						e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
						edges.put(n.getId()+" ("+"CONTAINS"+") "+n1.getId(), e);
						e.setLabel("CONTAINS");
						e.setSource(n.getId());
						e.setTarget(n1.getId());
						Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
						Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"CONTAINS"+") "+n1.getId(),ObjectType.STRING);
					}
				}
			}
		} 

		if (option.includeNextLinks) {
			List stl = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
			List tmp = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalPathwayStep(biopax.model);
			for (int i=0;i<tmp.size();i++)
				stl.add(tmp.get(i));
			
			for (int i=0;i<stl.size();i++) {
				PathwayStep ps = (PathwayStep)stl.get(i);
				n = (GraphicNode)nodes.get(ps.uri());
				Vector uris = Utils.getPropertyURIs(ps,"nextStep");
				for (int j=0;j<uris.size();j++) {
					String s = (String)uris.elementAt(j);
					PathwayStep ps1 = (PathwayStep)pathwaySteps.get(s);
					GraphicNode n1 = (GraphicNode)nodes.get(ps1.uri());
					if((n!=null)&&(n1!=null)){		    
						if(edges.get(n.getId()+" ("+"NEXT"+") "+n1.getId())==null){
							GraphicEdge e = grf.addNewEdge();
							edges.put(n.getId()+" ("+"NEXT"+") "+n1.getId(), e);
							e.setId(n.getId()+" ("+"NEXT"+") "+n1.getId());
							e.setLabel("NEXT");
							e.setSource(n.getId());
							e.setTarget(n1.getId());
							Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","NEXT",ObjectType.STRING);
							Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"NEXT"+") "+n1.getId(),ObjectType.STRING);
						}
					}else{
						if(n==null)
							System.out.println("Next link is not defined: "+Utils.cutUri(ps.uri())+"(null) NEXT "+Utils.cutUri(s));
						else
							System.out.println("Next link is not defined: "+Utils.cutUri(ps.uri())+" NEXT "+Utils.cutUri(s)+"(null)");
					}
				}
			}}

		return gr;
	}

	/**
	 *  Adds a pathway node to Pathway Structure interface 
	 */
	public GraphicNode addPathwayNode(GraphicGraph grf, Pathway p, HashMap pathways,HashMap interactions,HashMap pathwaySteps, HashMap nodes, HashMap edges, Option option) throws Exception{
		GraphicNode n = (GraphicNode)nodes.get(p.uri());
		if (n == null) {
			String name = bpnm.getNameByUri(p.uri());
			if (nodes.get(name)!=null) {
				System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(p.uri()));
				name = name+"("+Utils.cutUri(p.uri())+")";
			}
			n = grf.addNewNode();
			n.setId(name);
			n.setName(name);
			n.setLabel(name);
			nodes.put(p.uri(),n);
			nodes.put(name,n);
			Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","Pathway",ObjectType.STRING);
			Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);	    
		}

		pathwaysAdded.add(p.uri());

		//Vector uris = Utils.getPropertyURIs(p,"PATHWAY-COMPONENTS");
		Vector<String> uris = Utils.getPropertyURIs(p, "pathwayOrder");
		
		for (int i=0;i<uris.size();i++) {
			String s = (String)uris.elementAt(i);
			if (pathways.containsKey(s)) {
				if(pathwaysAdded.indexOf(s)<0){
					GraphicNode n1 = addPathwayNode(grf,(Pathway)pathways.get(s),pathways,interactions,pathwaySteps,nodes,edges, option);
					if(edges.get(n.getId()+" ("+"CONTAINS"+") "+n1.getId())==null){
						GraphicEdge e = grf.addNewEdge();
						edges.put(n.getId()+" ("+"CONTAINS"+") "+n1.getId(), e);
						e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
						e.setLabel("CONTAINS");
						e.setSource(n.getId());
						e.setTarget(n1.getId());
						Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
						Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"CONTAINS"+") "+n1.getId(),ObjectType.STRING);
					}
				}
			}
			if (pathwaySteps.containsKey(s)) {
				GraphicNode n1 = addPathwayStepNode(grf,(PathwayStep)pathwaySteps.get(s),pathways,interactions,pathwaySteps,nodes,edges, option);
				if(edges.get(n.getId()+" ("+"CONTAINS"+") "+n1.getId())==null){
					GraphicEdge e = grf.addNewEdge();
					edges.put(n.getId()+" ("+"CONTAINS"+") "+n1.getId(), e);
					e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
					e.setLabel("CONTAINS");
					e.setSource(n.getId());
					e.setTarget(n1.getId());
					Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
					Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"CONTAINS"+") "+n1.getId(),ObjectType.STRING);
				}
			}
			if (interactions.containsKey(s)) {
				GraphicNode n1 = addInteractionNode(grf,(Interaction)interactions.get(s),pathways,interactions,pathwaySteps,nodes);
				if(edges.get(n.getId()+" ("+"CONTAINS"+") "+n1.getId())==null){
					GraphicEdge e = grf.addNewEdge();
					edges.put(n.getId()+" ("+"CONTAINS"+") "+n1.getId(), e);
					e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
					e.setLabel("CONTAINS");
					e.setSource(n.getId());
					e.setTarget(n1.getId());
					Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
					Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"CONTAINS"+") "+n1.getId(),ObjectType.STRING);
				}
			}
		}
		return n;
	}

	/**
	 *  Adds a 'pathway step' node to Pathway Structure interface 
	 */
	public GraphicNode addPathwayStepNode(GraphicGraph grf, PathwayStep ps, HashMap pathways,HashMap interactions,HashMap pathwaySteps, HashMap nodes, HashMap edges, Option option) throws Exception{
		GraphicNode n = (GraphicNode)nodes.get(ps.uri());
		if (n == null) {
			n = grf.addNewNode();
			n.setId(bpnm.getNameByUri(ps.uri()));
			n.setName(bpnm.getNameByUri(ps.uri()));
			nodes.put(ps.uri(),n);
			Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","PathwayStep",ObjectType.STRING);
			Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",ps.uri(),ObjectType.STRING);
		}
		Vector uris = Utils.getPropertyURIs(ps,"stepProcess");
		
		for (int i=0;i<uris.size();i++) {
			String s = (String)uris.elementAt(i);
			if (option.includePathways)
				if (pathways.containsKey(s)) {
					GraphicNode n1 = addPathwayNode(grf,(Pathway)pathways.get(s),pathways,interactions,pathwaySteps,nodes,edges, option);
					if(edges.get(n.getId()+" ("+"STEP"+") "+n1.getId())==null){
						GraphicEdge e = grf.addNewEdge();
						edges.put(n.getId()+" ("+"STEP"+") "+n1.getId(), e);
						e.setId(n.getId()+" ("+"STEP"+") "+n1.getId());
						e.setLabel("STEP");
						e.setSource(n.getId());
						e.setTarget(n1.getId());
						Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","STEP",ObjectType.STRING);
						Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"STEP"+") "+n1.getId(),ObjectType.STRING);
					}
				}
			if (option.includeInteractions)
				if (interactions.containsKey(s)) {
					GraphicNode n1 = addInteractionNode(grf,(Interaction)interactions.get(s),pathways,interactions,pathwaySteps,nodes);
					if(edges.get(n.getId()+" ("+"STEP"+") "+n1.getId())==null){
						GraphicEdge e = grf.addNewEdge();
						edges.put(n.getId()+" ("+"STEP"+") "+n1.getId(), e);
						e.setId(n.getId()+" ("+"STEP"+") "+n1.getId());
						e.setLabel("STEP");
						e.setSource(n.getId());
						e.setTarget(n1.getId());
						Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","STEP",ObjectType.STRING);
						Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"STEP"+") "+n1.getId(),ObjectType.STRING);
					}
				}
		}
		return n;
	}

	/**
	 *  Adds a 'interaction' node to Pathway Structure interface 
	 */
	public GraphicNode addInteractionNode(GraphicGraph grf, Interaction in, HashMap pathways,HashMap interactions,HashMap pathwaySteps, HashMap nodes) throws Exception{
		GraphicNode n = (GraphicNode)nodes.get(in.uri());
		if (n == null) {
			String name = bpnm.getNameByUri(in.uri());
			if (nodes.get(name)!=null) {
				System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(in.uri()));
				name = name+"("+Utils.cutUri(in.uri())+")";
			}
			n = grf.addNewNode();
			n.setId(name);
			n.setName(name);
			n.setLabel(name);
			nodes.put(in.uri(),n);
			nodes.put(name,n);
			String iname = in.getClass().getName();
			iname = Utils.replaceString(iname,"Impl","");
			iname = Utils.replaceString(iname,"fr.curie.BiNoM.pathways.biopax.","");
			Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",iname,ObjectType.STRING);
			Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",in.uri(),ObjectType.STRING);
		}
		return n;
	}

	/**
	 * <p>Implementation of extracting Protein Interaction interface
	 * (requires first conversion from BioPAX to SBML,
	 * from which the interface is converted).
	 * Also need biopaxReactions, independentSpeciesIds, species,
	 * controls and other maps valid (they are filled by BioPAXToSBMLConverter.populateSbml()
	 * parent method )
	 */
	public GraphDocument getXGMMLProteinGraph(String name, BioPAX biopax, Option option) throws Exception {
		
		GraphDocument gr = GraphDocument.Factory.newInstance();
		GraphicGraph grf = gr.addNewGraph();
		grf.setLabel(name+"_complexes");
		grf.setName(name+"_complexes");

		HashMap proteins = new HashMap();
		HashMap genes = new HashMap();
		HashMap complexes = new HashMap();
		HashMap nodes = new HashMap();
		HashMap edges = new HashMap();

		List pl = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
		for (int i=0;i<pl.size();i++) {
			Protein p = (Protein)pl.get(i);
			proteins.put(p.uri(),p);
			if (!option.distinguishCompartments){
				String pnm = null;
				if(p.getEntityReference()!=null){
					pnm = bpnm.getNameByUri(p.getEntityReference().uri());
					proteins.put(p.getEntityReference().uri(),p.getEntityReference());
				}else{
					pnm = bpnm.getNameByUri(p.uri());
				}
				if (!nodes.containsKey(pnm)) {
					GraphicNode n = grf.addNewNode();
					n.setId(pnm); n.setName(pnm); n.setLabel(pnm);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","protein",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
					//System.out.println("Added "+pnm);
					nodes.put(pnm,n);
					nodes.put(p.uri(),n);
				} 
			}
		}
		pl = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
		for (int i=0;i<pl.size();i++) {
			Dna p = (Dna)pl.get(i);
			proteins.put(p.uri(),p);
			if (!option.distinguishCompartments){
				String pnm = null;
				if(p.getEntityReference()!=null){
					pnm = bpnm.getNameByUri(p.getEntityReference().uri());
					proteins.put(p.getEntityReference().uri(),p.getEntityReference());
				}else{
					pnm = bpnm.getNameByUri(p.uri());
				}
				if (!nodes.containsKey(pnm)) {
					GraphicNode n = grf.addNewNode();
					n.setId(pnm); n.setName(pnm); n.setLabel(pnm);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","protein",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
					//System.out.println("Added "+pnm);
					nodes.put(pnm,n);
					nodes.put(p.uri(),n);
				} 
			}
		}
		pl = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
		for (int i=0;i<pl.size();i++) {
			Rna p = (Rna)pl.get(i);
			proteins.put(p.uri(),p);
			if (!option.distinguishCompartments){
				String pnm = null;
				if(p.getEntityReference()!=null){
					pnm = bpnm.getNameByUri(p.getEntityReference().uri());
					proteins.put(p.getEntityReference().uri(),p.getEntityReference());
				}else{
					pnm = bpnm.getNameByUri(p.uri());
				}
				if (!nodes.containsKey(pnm)) {
					GraphicNode n = grf.addNewNode();
					n.setId(pnm); n.setName(pnm); n.setLabel(pnm);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","protein",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
					//System.out.println("Added "+pnm);
					nodes.put(pnm,n);
					nodes.put(p.uri(),n);
				} 
			}
		}
		pl = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
		for (int i=0;i<pl.size();i++) {
			SmallMolecule p = (SmallMolecule)pl.get(i);
			proteins.put(p.uri(),p);
			if (!option.distinguishCompartments){
				String pnm = null;
				if(p.getEntityReference()!=null){
					pnm = bpnm.getNameByUri(p.getEntityReference().uri());
					proteins.put(p.getEntityReference().uri(),p.getEntityReference());
				}else{
					pnm = bpnm.getNameByUri(p.uri());
				}
				if (!nodes.containsKey(pnm)) {
					GraphicNode n = grf.addNewNode();
					n.setId(pnm); n.setName(pnm); n.setLabel(pnm);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","protein",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
					//System.out.println("Added "+pnm);
					nodes.put(pnm,n);
					nodes.put(p.uri(),n);
				} 
			}
		}
		
		/*pl = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(biopax.model);
		for (int i=0;i<pl.size();i++) {
			PhysicalEntity p = (PhysicalEntity)pl.get(i);
			if(!proteins.containsKey(p.uri())){
			if (!option.distinguishCompartments){
				String pnm = null;
				pnm = bpnm.getNameByUri(p.uri());
				if (!nodes.containsKey(pnm)) {
					GraphicNode n = grf.addNewNode();
					n.setId(pnm); n.setName(pnm); n.setLabel(pnm);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","protein",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
					nodes.put(pnm,n);
					System.out.println("Added "+pnm);
					nodes.put(p.uri(),n);
					proteins.put(p.uri(),p);
				} 
			}}
		}*/	
		
		/*Iterator<String> it = nodes.keySet().iterator();
		while(it.hasNext())
			System.out.println("Node:"+it.next());
		*/
		
		pl = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
		for (int i=0;i<pl.size();i++) {
			Complex cmplx = (Complex)pl.get(i);
			if (!option.distinguishCompartments) {
				String cnm = bpnm.getNameByUri(cmplx.uri());
				GraphicNode n = grf.addNewNode();
				//cnm = getGenericComplexName(cnm);
				n.setId(cnm); n.setName(cnm); n.setLabel(cnm);
				Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","complex",ObjectType.STRING);
				Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",cmplx.uri(),ObjectType.STRING);
				nodes.put(cnm,n);
				nodes.put(cmplx.uri(),n);
				complexes.put(cmplx.uri(),cmplx);
			}
		}		

		Iterator inn = nodes.keySet().iterator();
		while(inn.hasNext()) {
			String uri = (String)inn.next();
			GraphicNode n = (GraphicNode)nodes.get(uri);
			if (complexes.containsKey(uri)) {
				Complex cm = (Complex)complexes.get(uri);
				Iterator<PhysicalEntity> itc = cm.getComponent();
				while(itc.hasNext()) {
					PhysicalEntity pep = itc.next();
					if(pep!=null){
						String pepuri = pep.uri();
						pep = (PhysicalEntity)proteins.get(pepuri);
						System.out.println(pepuri);
						System.out.println(pep.getClass().getName());
						if(pep instanceof Protein) pepuri = ((Protein)pep).getEntityReference().uri();
						if(pep instanceof Rna) pepuri = ((Rna)pep).getEntityReference().uri();
						if(pep instanceof Dna) pepuri = ((Dna)pep).getEntityReference().uri();
						if(pep instanceof SmallMolecule) pepuri = ((SmallMolecule)pep).getEntityReference().uri();
						String nodeName = bpnm.getNameByUri(pepuri);
						GraphicNode n1 = (GraphicNode)nodes.get(nodeName);
						if (n1==null)
							System.out.println("COMPLEX COMPONENT IS NOT INCLUDED: "+Utils.cutUri(pep.uri()));
						else {
							String id = n.getId()+" (CONTAINS) "+n1.getId();
							if(edges.get(id)==null){
								GraphicEdge ee = grf.addNewEdge();
								ee.setId(id);
								ee.setLabel("CONTAINS");
								ee.setSource(n1.getId()); ee.setTarget(n.getId());
								Utils.addAttribute(ee,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
								Utils.addAttribute(ee,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+" ("+"CONTAINS"+") "+n1.getId(),ObjectType.STRING);
								edges.put(id,ee);
							}
						}
					}
				}
			}
		}

		HashMap knownInteractions = new HashMap();
		List il = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));
		il = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
		for (int i=0;i<il.size();i++) knownInteractions.put(((Interaction)il.get(i)).uri(),(Interaction)il.get(i));

		il = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
		for (int j=0;j<il.size();j++) {
			MolecularInteraction pi = (MolecularInteraction)il.get(j);
			if (!knownInteractions.containsKey(pi.uri())) {
				Vector v = Utils.getPropertyURIs(pi,"participant");
				if (v.size()>0) {
					GraphicNode n = grf.addNewNode();
					String pepuri = pi.uri();
					if(pi instanceof Protein) pepuri = ((Protein)pi).getEntityReference().uri();
					if(pi instanceof Rna) pepuri = ((Rna)pi).getEntityReference().uri();
					if(pi instanceof Dna) pepuri = ((Dna)pi).getEntityReference().uri();
					if(pi instanceof SmallMolecule) pepuri = ((SmallMolecule)pi).getEntityReference().uri();
					String intn = bpnm.getNameByUri(pepuri);
					n.setId(intn); n.setName(intn); n.setLabel(intn);
					Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","physicalInteraction",ObjectType.STRING);
					Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",pi.uri(),ObjectType.STRING);
					nodes.put(intn,n);
					nodes.put(pi.uri(),n);

					for (int i=0;i<v.size();i++) {
						String ur = (String)v.get(i);
						GraphicNode n1 = (GraphicNode)nodes.get(ur);
						if (n1==null) {
							System.out.println("ENTITY NOT FOUND ON THE GRAPH: "+Utils.cutUri(ur));
						} else {
							String id = n.getId()+" (physicalInteraction) "+n1.getId();
							if(edges.get(id)==null){
								GraphicEdge ee = grf.addNewEdge();
								ee.setId(id);
								ee.setLabel("physicalInteraction");
								ee.setSource(n1.getId()); ee.setTarget(n.getId());
								Utils.addAttribute(ee,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","physycalInteraction",ObjectType.STRING);
								Utils.addAttribute(ee,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",id,ObjectType.STRING);
								edges.put(id,ee);
							}
						}
					}

				}
			}
		}

		return gr;
	}
}
