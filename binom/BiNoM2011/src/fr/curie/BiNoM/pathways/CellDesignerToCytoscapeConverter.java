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
package fr.curie.BiNoM.pathways;

import java.io.*;
import java.util.*;
import java.io.File;
import java.awt.*;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter.BioPAXSpecies;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.sbml.x2001.ns.celldesigner.CelldesignerNotesDocument.CelldesignerNotes;
import org.sbml.x2001.ns.celldesigner.NotesDocument.Notes;
import org.apache.xmlbeans.*;

import edu.rpi.cs.xgmml.*;
import edu.rpi.cs.xgmml.AttDocument.Att;

/**
 * Direct converter from CellDesigner to Cytoscape.
 *  
 */
public class CellDesignerToCytoscapeConverter {

	  /**
	   * Name of CellDesigner file
	   */  	
    String filename = "";
    /**
     * Java xml-beans mapping of CellDesigner file
     */  
    public org.sbml.x2001.ns.celldesigner.SbmlDocument sbml = null;
    /**
     * Auxiliary BiNoM wrapper of CellDesigner
     */
    public CellDesigner celldesigner = new CellDesigner();

    /**
     * Map from a key in the form reaction_id+"_"+species_alias_id
     * to ReactionDocument.Reaction object
     */
    public static HashMap takenaliases = null;

    /**
     * Check if importing degradation reactions is needed
     */
    boolean considerDegradationReactions = false;
    /**
     * If checked, the compartment name will appear in all species names,
     * otherwide the compartment name will be omited for that compartment
     * containing majority of the species
     */
    public static boolean alwaysMentionCompartment = false;
    
    public static HashMap<String,SpeciesDocument.Species> speciesMap = null;
    public static HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies> includedSpeciesMap = null;
    public static HashMap<String,Vector<CelldesignerSpeciesDocument.CelldesignerSpecies>> complexSpeciesMap = null;
    public static HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>> speciesAliasMap = null;
    public static HashMap<String,Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>> complexSpeciesAliasMap = null;
    
	public static String defcomp_id = "default";
	public static Vector comp_ids = new Vector();
	public static Vector comp_ids_n = new Vector();
    

    public static boolean addSuffixForMultipleAliases = true;
    

    /**
     * Simple container for GraphDocument and SbmlDocument objects  
     */
    public static class Graph {
	public GraphDocument graphDocument;
	public SbmlDocument sbml;

	Graph(GraphDocument graphDocument, SbmlDocument sbml) {
	    this.graphDocument = graphDocument;
	    this.sbml = sbml;
	}
    }

    /**
     * Principal conversion function used from outside 
     */
    public static Graph convert(String file) {
	CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();

	System.out.println("Loading file...");
	c2c.sbml = CellDesigner.loadCellDesigner(file);
	System.out.println("Loaded.");


	CellDesigner.entities = CellDesigner.getEntities(c2c.sbml);
	//BipartiteGraph bgr = c2c.celldesigner.getBipartiteGraph(c2c.sbml.getSbml());
	//BiographUtils.mapClassesToNodeProps(bgr);

	return new Graph(getXGMMLGraph(file, c2c.sbml.getSbml()), c2c.sbml);
    }

    /**
     * The converter itself
     */
  public static GraphDocument getXGMMLGraph(String name, org.sbml.x2001.ns.celldesigner.SbmlDocument.Sbml sbml){

	createSpeciesMap(sbml);
	  
    takenaliases = new HashMap();

    GraphDocument gr = GraphDocument.Factory.newInstance();
    GraphicGraph grf = gr.addNewGraph();
    grf.setLabel(name);
    grf.setName(name);
    HashMap NodeIDs = new HashMap();
    HashMap EdgeIDs = new HashMap();

    HashMap species = getSpecies(sbml);
    

    for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
    	
    	//System.out.println(new Date());
    	
    	//Date tm = new Date();    	
        SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
        Vector aliases = findAllAliasesForSpecies(sbml,sp.getId());
        
        String lab = convertSpeciesToName(sbml,sp.getId(),true,true);
        //System.out.println("convertSpeciesToName time = "+((new Date()).getTime()-tm.getTime()));
        
        System.out.println((i+1)+"/"+sbml.getModel().getListOfSpecies().getSpeciesArray().length+": "+lab+"\t"+sp.getId());
        
        if((lab!=null)&&(!lab.startsWith("null"))){
        	
          if(aliases.size()==0)
        	  aliases.add(lab);
        	
          for(int j=0;j<aliases.size();j++){
          String alias = (String)aliases.elementAt(j);

          String labs = convertSpeciesToName(sbml,sp.getId(),true,true,false,alias);
          
          GraphicNode n1 = (GraphicNode)NodeIDs.get(sp.getId()+"_"+alias);
          if(addSuffixForMultipleAliases)
        	  labs += getSuffixForMultipleAliases(sbml,sp.getId(),alias);
          
          if(n1==null){
            n1 = grf.addNewNode();
            //n1.setId(sp.getId()+"_"+alias);
            n1.setId(labs);
            n1.setLabel(labs);
            n1.setName(labs);
            String species_type = "unknown"; 
            if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()!=null){
            	species_type = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
                Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",species_type,ObjectType.STRING);
            }
            Utils.addAttribute(n1,"CELLDESIGNER_SPECIES","CELLDESIGNER_SPECIES",sp.getId(),ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_ALIAS","CELLDESIGNER_ALIAS",alias,ObjectType.STRING);

            boolean annotateSpeciesFromEntityAnnotation = true;
            if(sp.getNotes()!=null){
            	Vector<Vector<String>> atts = extractAttributesFromNotes(sp.getNotes());
            	// Here some exceptions for ACSN: if species already annotated by MODULES then it is not annotated from entity
            	for(int k=0;k<atts.size();k++){
            		if(atts.get(k).get(0).equals("MODULE"))
            			annotateSpeciesFromEntityAnnotation = false;
            		Utils.addAttributeUniqueNameConcatenatedValues(n1, atts.get(k).get(0), atts.get(k).get(0), atts.get(k).get(1), ObjectType.STRING);
            	}
            }
            
            if(annotateSpeciesFromEntityAnnotation){
            if(species_type.equals("PROTEIN")){
            	String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
            	CelldesignerProteinDocument.CelldesignerProtein protein = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get(id);
            	if(protein.getCelldesignerNotes()!=null){
                	Vector<Vector<String>> atts = extractAttributesFromNotes(protein.getCelldesignerNotes());
                	for(int k=0;k<atts.size();k++){
                		Utils.addAttributeUniqueNameConcatenatedValues(n1, atts.get(k).get(0), atts.get(k).get(0), atts.get(k).get(1), ObjectType.STRING);
                	}
                	if(Utils.getFirstAttribute(n1, "HUGO")==null)
                		if(!Utils.getText(protein.getName()).contains("*"))
                			Utils.addAttribute(n1, "HUGO", "HUGO", Utils.getText(protein.getName()), ObjectType.STRING);
            	}
            	if(protein.getCelldesignerNotes()==null)
            		if(!Utils.getText(protein.getName()).contains("*"))
            			Utils.addAttribute(n1, "HUGO", "HUGO", Utils.getText(protein.getName()), ObjectType.STRING);
            }
            if(species_type.equals("COMPLEX")){
            	Vector<CelldesignerProteinDocument.CelldesignerProtein> proteinsInComplex = getProteinsInComplex(sbml,sp.getId());
            	Vector<Vector<String>> all_atts = new Vector<Vector<String>>(); 
            	for(int kk=0;kk<proteinsInComplex.size();kk++){
                	CelldesignerProteinDocument.CelldesignerProtein protein = proteinsInComplex.get(kk);
                	if(protein.getCelldesignerNotes()!=null){
                    	Vector<Vector<String>> atts = extractAttributesFromNotes(protein.getCelldesignerNotes());
                    	for(int kkk=0;kkk<atts.size();kkk++)
                    		all_atts.add(atts.get(kkk));
                    	if(!Utils.getText(protein.getCelldesignerNotes()).contains("HUGO:"))
                    		if(!Utils.getText(protein.getName()).contains("*")){
                    			Vector<String> att = new Vector<String>();
                    			att.add("HUGO"); att.add(Utils.getText(protein.getName()));
                    			all_atts.add(att);
                    		}
                	}
                	if(protein.getCelldesignerNotes()==null)
                		if(!Utils.getText(protein.getName()).contains("*")){
                			Vector<String> att = new Vector<String>();
                			att.add("HUGO"); att.add(Utils.getText(protein.getName()));
                			all_atts.add(att);
                		}
            	}
            	for(int k=0;k<all_atts.size();k++){
            		Utils.addAttributeUniqueNameConcatenatedValues(n1, all_atts.get(k).get(0), all_atts.get(k).get(0), all_atts.get(k).get(1), ObjectType.STRING);
            	}
            }
            if(species_type.equals("GENE")){
               	String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
               	CelldesignerGeneDocument.CelldesignerGene gene = (CelldesignerGeneDocument.CelldesignerGene)CellDesigner.entities.get(id);
               	if(gene.getCelldesignerNotes()!=null){
                   	Vector<Vector<String>> atts = extractAttributesFromNotes(gene.getCelldesignerNotes());
                   	for(int k=0;k<atts.size();k++){
                   		Utils.addAttributeUniqueNameConcatenatedValues(n1, atts.get(k).get(0), atts.get(k).get(0), atts.get(k).get(1), ObjectType.STRING);
                   	}
            }}
            if(species_type.equals("RNA")){
               	String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
               	CelldesignerRNADocument.CelldesignerRNA rna = (CelldesignerRNADocument.CelldesignerRNA)CellDesigner.entities.get(id);
               	if(rna.getCelldesignerNotes()!=null){
                   	Vector<Vector<String>> atts = extractAttributesFromNotes(rna.getCelldesignerNotes());
                   	for(int k=0;k<atts.size();k++){
                   		Utils.addAttributeUniqueNameConcatenatedValues(n1, atts.get(k).get(0), atts.get(k).get(0), atts.get(k).get(1), ObjectType.STRING);
                   	}
            }}
            if(species_type.equals("ANTISENSE_RNA")){
               	String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
               	CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = (CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA)CellDesigner.entities.get(id);
               	if(arna.getCelldesignerNotes()!=null){
                   	Vector<Vector<String>> atts = extractAttributesFromNotes(arna.getCelldesignerNotes());
                   	for(int k=0;k<atts.size();k++){
                   		Utils.addAttributeUniqueNameConcatenatedValues(n1, atts.get(k).get(0), atts.get(k).get(0), atts.get(k).get(1), ObjectType.STRING);
                   	}
            }}
            }
            	
            
            setSpeciesPositionForXGMML(alias,n1,sbml);
            NodeIDs.put(sp.getId()+"_"+alias,n1);
          }
          }
        }
      }// end end species  

    if(sbml.getModel().getListOfReactions()!=null)
    for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
      ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
      String rtype = "UNKNOWN_REACTION";
      if(r.getAnnotation()!=null)
        if(r.getAnnotation().getCelldesignerReactionType()!=null)
          rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());;
      System.out.println((i+1)+"/"+sbml.getModel().getListOfReactions().getReactionArray().length+": "+r.getId());
      if(r.getListOfReactants()!=null)
      for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
        String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
        String alias = getSpeciesAliasInReaction(r,id,"reactant");
        String nam = convertSpeciesToName(sbml,id,true,true,false,alias);
        if(addSuffixForMultipleAliases)
        	nam+=getSuffixForMultipleAliases(sbml,id,alias);
        if((nam!=null)&&(!nam.startsWith("null"))){

          GraphicNode n1 = (GraphicNode)NodeIDs.get(id+"_"+alias);
          if(n1==null){
            n1 = grf.addNewNode();
            //n1.setId(id+"_"+alias);
            n1.setId(nam); // Here we have to use alias if the name is doubled!!!
            n1.setLabel(nam);
            n1.setName(nam);

            SpeciesDocument.Species sp = (SpeciesDocument.Species)species.get(id);
            if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
              Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()),ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_SPECIES","CELLDESIGNER_SPECIES",id,ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_ALIAS","CELLDESIGNER_ALIAS",alias,ObjectType.STRING);

            setSpeciesPositionForXGMML(alias,n1,sbml);
            NodeIDs.put(id+"_"+alias,n1);
          }
          GraphicNode n2 = (GraphicNode)NodeIDs.get(r.getId());
          if(n2==null){
            n2 = grf.addNewNode();
            n2.setId(r.getId());
            n2.setLabel(r.getId());
            n2.setName("reaction");
            Utils.addAttribute(n2,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",rtype,ObjectType.STRING);
            Utils.addAttribute(n2,"CELLDESIGNER_REACTION","CELLDESIGNER_REACTION",r.getId(),ObjectType.STRING);
            NodeIDs.put(r.getId(),n2);
          }
          GraphicEdge e = grf.addNewEdge();
          Utils.addAttribute(e,"CELLDESIGNER_EDGE_TYPE","CELLDESIGNER_EDGE_TYPE","LEFT",ObjectType.STRING);
          Utils.addAttribute(e,"interaction","interaction","LEFT",ObjectType.STRING);
          e.setId(n1.getId()+"(LEFT)"+n2.getId());
          e.setLabel("LEFT");
          e.setSource(n1.getId());
          e.setTarget(n2.getId());

        }
      }
      if(r.getListOfProducts()!=null)
      for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
        String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
        String alias = getSpeciesAliasInReaction(r,id,"product");
                
        String nam = convertSpeciesToName(sbml,id,true,true,false,alias);
        if(addSuffixForMultipleAliases)
        	nam+=getSuffixForMultipleAliases(sbml,id,alias);
        if((nam!=null)&&(!nam.startsWith("null"))){

          GraphicNode n2 = (GraphicNode)NodeIDs.get(id+"_"+alias);
          if(n2==null){
            n2 = grf.addNewNode();
            //n2.setId(id+"_"+alias);
            n2.setId(nam);
            n2.setLabel(nam);
            n2.setName(nam);

            SpeciesDocument.Species sp = (SpeciesDocument.Species)species.get(id);
            if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
              Utils.addAttribute(n2,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()),ObjectType.STRING);
            Utils.addAttribute(n2,"CELLDESIGNER_SPECIES","CELLDESIGNER_SPECIES",id,ObjectType.STRING);
            Utils.addAttribute(n2,"CELLDESIGNER_ALIAS","CELLDESIGNER_ALIAS",alias,ObjectType.STRING);

            setSpeciesPositionForXGMML(alias,n2,sbml);
            NodeIDs.put(id+"_"+alias,n2);
          }
          GraphicNode n1 = (GraphicNode)NodeIDs.get(r.getId());
          if(n1==null){
            n1 = grf.addNewNode();
            n1.setId(r.getId());
            n1.setLabel(r.getId());
            n1.setName("reaction");
            Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",rtype,ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_REACTION","CELLDESIGNER_REACTION",r.getId(),ObjectType.STRING);
            NodeIDs.put(r.getId(),n1);
          }
          GraphicEdge e = grf.addNewEdge();
          Utils.addAttribute(e,"CELLDESIGNER_EDGE_TYPE","CELLDESIGNER_EDGE_TYPE","RIGHT",ObjectType.STRING);
          Utils.addAttribute(e,"interaction","interaction","RIGHT",ObjectType.STRING);
          e.setId(n1.getId()+"(RIGHT)"+n2.getId());
          e.setLabel("RIGHT");
          e.setSource(n1.getId());
          e.setTarget(n2.getId());

        }
      }
      if(r.getAnnotation()!=null)
      if(r.getAnnotation().getCelldesignerListOfModification()!=null)
      for(int k=0;k<r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray().length;k++){
        CelldesignerModificationDocument.CelldesignerModification modif = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k);
        String ids = modif.getModifiers();
        if(ids==null){
        	if(!modif.getType().startsWith("BOOLEAN"))
        		System.out.println("ERROR: modifiers=null for modification "+(k+1)+" in reaction "+r.getId());
        }
        else
        if(!ids.contains(",")){
        //StringTokenizer stid = new StringTokenizer(ids,",");
        //while(stid.hasMoreTokens()){
        //String id = stid.nextToken();
        String id = ids;
        String nam = convertSpeciesToName(sbml,id,true,true);
        String mtyp = "CATALYSIS";
        try{
        	mtyp = modif.getType().toString();
        }catch(Exception e){
        	//e.printStackTrace();
//        	System.out.println(modif);
        }
        String alias = getSpeciesAliasInReaction(r,id,"modifier");
        if(addSuffixForMultipleAliases)
        	nam+=getSuffixForMultipleAliases(sbml,id,alias);
        GraphicNode n1 = (GraphicNode)NodeIDs.get(id+"_"+alias);
        if(n1==null){
          n1 = grf.addNewNode();
          //n1.setId(id+"_"+alias);
          n1.setId(nam);
          n1.setLabel(nam);
          n1.setName(nam);

          SpeciesDocument.Species sp = (SpeciesDocument.Species)species.get(id);
          if(sp==null)
        	  System.out.println("ERROR: "+id+" species not found");
          else{
          if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
            Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()),ObjectType.STRING);
          }
          Utils.addAttribute(n1,"CELLDESIGNER_SPECIES","CELLDESIGNER_SPECIES",id,ObjectType.STRING);
          Utils.addAttribute(n1,"CELLDESIGNER_ALIAS","CELLDESIGNER_ALIAS",alias,ObjectType.STRING);

          setSpeciesPositionForXGMML(alias,n1,sbml);
          NodeIDs.put(id+"_"+alias,n1);
        }
        GraphicNode n2 = (GraphicNode)NodeIDs.get(r.getId());
        if(n2==null){
          n2 = grf.addNewNode();
          n2.setId(r.getId());
          n2.setLabel(r.getId());
          n2.setName("reaction");
          Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",rtype,ObjectType.STRING);
          Utils.addAttribute(n1,"CELLDESIGNER_REACTION","CELLDESIGNER_REACTION",r.getId(),ObjectType.STRING);
          NodeIDs.put(r.getId(),n2);
        }
        GraphicEdge e = grf.addNewEdge();
        Utils.addAttribute(e,"CELLDESIGNER_EDGE_TYPE","CELLDESIGNER_EDGE_TYPE",mtyp,ObjectType.STRING);
        Utils.addAttribute(e,"interaction","interaction",mtyp,ObjectType.STRING);
        e.setId(n1.getId()+"("+mtyp+")"+n2.getId());
        e.setLabel(mtyp);
        e.setSource(n1.getId());
        e.setTarget(n2.getId());

      }
      }
      
      if((r.getAnnotation()==null)||(r.getAnnotation().getCelldesignerListOfModification()==null))
      if(r.getListOfModifiers()!=null)
      for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
        String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
      	//System.out.println("IN MODIFIERS "+id);
        String alias = getSpeciesAliasInReaction(r,id,"modifier");
        String nam = convertSpeciesToName(sbml,id,true,true);
        if(addSuffixForMultipleAliases)
        	nam+=getSuffixForMultipleAliases(sbml,id,alias);
        if((nam!=null)&&(!nam.startsWith("null"))){

          GraphicNode n1 = (GraphicNode)NodeIDs.get(id+"_"+alias);
          if(n1==null){
            n1 = grf.addNewNode();
            //n1.setId(id+"_"+alias);
            n1.setId(nam);
            n1.setLabel(nam);
            n1.setName(nam);

            SpeciesDocument.Species sp = (SpeciesDocument.Species)species.get(id);
            if(sp.getAnnotation()!=null)
            if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
            if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()!=null)
            	Utils.addAttribute(n1,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()),ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_SPECIES","CELLDESIGNER_SPECIES",id,ObjectType.STRING);
            Utils.addAttribute(n1,"CELLDESIGNER_ALIAS","CELLDESIGNER_ALIAS",alias,ObjectType.STRING);

            setSpeciesPositionForXGMML(alias,n1,sbml);
            NodeIDs.put(id+"_"+alias,n1);
          }
          GraphicNode n2 = (GraphicNode)NodeIDs.get(r.getId());
          if(n2==null){
            n2 = grf.addNewNode();
            n2.setId(r.getId());
            n2.setLabel(r.getId());
            n2.setName("reaction");
            Utils.addAttribute(n2,"CELLDESIGNER_NODE_TYPE","CELLDESIGNER_NODE_TYPE",rtype,ObjectType.STRING);
            Utils.addAttribute(n2,"CELLDESIGNER_REACTION","CELLDESIGNER_REACTION",r.getId(),ObjectType.STRING);
            NodeIDs.put(r.getId(),n2);
          }
          GraphicEdge e = grf.addNewEdge();
          e.setId(n1.getId()+"(MODIFIES)"+n2.getId());
          e.setLabel("MODIFIES");
          e.setSource(n1.getId());
          e.setTarget(n2.getId());
          Utils.addAttribute(e,"CELLDESIGNER_EDGE_TYPE","CELLDESIGNER_EDGE_TYPE","MODIFIES",ObjectType.STRING);
          Utils.addAttribute(e,"interaction","interaction","MODIFIES",ObjectType.STRING);
        }
      }
    }

    takenaliases.clear();
    if(sbml.getModel().getListOfReactions()!=null)
    for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
      ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
      Vector nodes = new Vector();
      if(r.getListOfReactants()!=null)
      for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
        String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
        String alias = getSpeciesAliasInReaction(r,id,"reactant");
        GraphicNode n1 = (GraphicNode)NodeIDs.get(id+"_"+alias);
        if(n1!=null)
          nodes.add(n1);
      }
      if(r.getListOfProducts()!=null)
      for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
        String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
        String alias = getSpeciesAliasInReaction(r,id,"product");
        GraphicNode n1 = (GraphicNode)NodeIDs.get(id+"_"+alias);
        if(n1!=null)
          nodes.add(n1);
      }
      GraphicNode nr = (GraphicNode)NodeIDs.get(r.getId());
      if((nr!=null)&&(r.getAnnotation()!=null))
        setAveragePositionForXGMML(nr,nodes,NodeIDs);
    }

    return gr;
  }

  /**
   * Reads the position information for a node from CellDesigner  
   * @param spalias
   * @param nod
   * @param sbml
   */
  public static void setSpeciesPositionForXGMML(String spalias, GraphicNode nod, SbmlDocument.Sbml sbml){
    if(sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null)
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
      CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = (CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias)sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
      if(spa.getId().equals(spalias)){
        GraphicsDocument.Graphics gr = nod.addNewGraphics();
        gr.setX(Double.parseDouble(spa.getCelldesignerBounds().getX()));
        gr.setY(Double.parseDouble(spa.getCelldesignerBounds().getY()));
      }
    }
    if(sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias spa =
              (CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias)sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
      if(spa.getId().equals(spalias)){
        GraphicsDocument.Graphics gr = nod.addNewGraphics();
        gr.setX(Double.parseDouble(spa.getCelldesignerBounds().getX()));
        gr.setY(Double.parseDouble(spa.getCelldesignerBounds().getY()));
      }
    }
  }

  /**
   * Place node 'nod' into the average of 'nodes' positions
   */
  public static void setAveragePositionForXGMML(GraphicNode nod, Vector nodes, HashMap speciesNodes){
    double avx = 0;
    double avy = 0;
    double xdev = 0;
    double ydev = 0;

    for(int i=0;i<nodes.size();i++){
      GraphicNode nd = (GraphicNode)nodes.elementAt(i);
      if(nd.getGraphics()!=null){
        avx+=nd.getGraphics().getX();
        avy+=nd.getGraphics().getY();
      }
    }
    avx/=nodes.size();
    avy/=nodes.size();
    GraphicsDocument.Graphics gr = nod.addNewGraphics();
    gr.setX(avx);
    gr.setY(avy);
    if(nodes.size()<=1){
      avx = 0;
      avy = 0;
      Set keys = speciesNodes.keySet();
      Iterator it = keys.iterator();
      while(it.hasNext()){
         String id = (String)it.next();
         GraphicNode nd = (GraphicNode)speciesNodes.get(id);
         if(nd.getGraphics()!=null){
         avx+=nd.getGraphics().getX();
         avy+=nd.getGraphics().getY();
         xdev+=nd.getGraphics().getX()*nd.getGraphics().getX();
         ydev+=nd.getGraphics().getY()*nd.getGraphics().getY();
         }
      }
      avx/=speciesNodes.size();
      avy/=speciesNodes.size();
      xdev = xdev/speciesNodes.size() - avx*avx;
      ydev = ydev/speciesNodes.size() - avy*avy;
      xdev = Math.sqrt(xdev);
      ydev = Math.sqrt(ydev);
      double r = Math.sqrt((gr.getX()-avx)*(gr.getX()-avx)+(gr.getY()-avy)*(gr.getY()-avy));
      gr.setX(gr.getX()+(gr.getX()-avx)/r*xdev/4);
      gr.setY(gr.getY()+(gr.getY()-avy)/r*ydev/4);
    }
  }

  /**
   * Finds CellDesigner alias of species 'id' in the reation r
   * @param r
   * @param id
   * @param role can be 'reactant' or 'product' or 'modifier'
   * @return
   */
  public static String getSpeciesAliasInReaction(ReactionDocument.Reaction r, String id, String role){
    String alias = "";
    if(r.getAnnotation()!=null){

    if(role.equals("reactant"))
    if(r.getAnnotation().getCelldesignerBaseReactants()!=null)
    for(int i=0;i<r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray().length;i++){
      CelldesignerBaseReactantDocument.CelldesignerBaseReactant spa =
              (CelldesignerBaseReactantDocument.CelldesignerBaseReactant)r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(i);
      if(spa.getSpecies().getStringValue().equals(id))
        if(!takenaliases.containsKey(r.getId()+"_"+spa.getAlias()+"_"+role))
              alias = spa.getAlias();
    }
    if(role.equals("product"))
    if(r.getAnnotation().getCelldesignerBaseProducts()!=null)
    for(int i=0;i<r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray().length;i++){
      CelldesignerBaseProductDocument.CelldesignerBaseProduct spa =
              (CelldesignerBaseProductDocument.CelldesignerBaseProduct)r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(i);
      if(spa.getSpecies().getStringValue().equals(id))
        if(!takenaliases.containsKey(r.getId()+"_"+spa.getAlias()+"_"+role))
                alias = spa.getAlias();
    }

    if(role.equals("modifier"))
    if(r.getAnnotation().getCelldesignerListOfModification()!=null)
    for(int i=0;i<r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray().length;i++){
      CelldesignerModificationDocument.CelldesignerModification spa =
              (CelldesignerModificationDocument.CelldesignerModification)r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(i);
      if(spa.getModifiers()!=null)
      if(spa.getModifiers().equals(id))
        if(!takenaliases.containsKey(r.getId()+"_"+spa.getAliases()+"_"+role))
            alias = spa.getAliases();
    }
    if(role.equals("reactant"))
    if(r.getAnnotation().getCelldesignerListOfReactantLinks()!=null)
      for(int i=0;i<r.getAnnotation().getCelldesignerListOfReactantLinks().getCelldesignerReactantLinkArray().length;i++){
        CelldesignerReactantLinkDocument.CelldesignerReactantLink rl =
                (CelldesignerReactantLinkDocument.CelldesignerReactantLink)r.getAnnotation().getCelldesignerListOfReactantLinks().getCelldesignerReactantLinkArray(i);
        if(rl.getReactant().equals(id))
          if(!takenaliases.containsKey(r.getId()+"_"+rl.getAlias()+"_"+role))
            alias = rl.getAlias();
      }
    if(role.equals("product"))
    if(r.getAnnotation().getCelldesignerListOfProductLinks()!=null){
      for(int i=0;i<r.getAnnotation().getCelldesignerListOfProductLinks().getCelldesignerProductLinkArray().length;i++){
        CelldesignerProductLinkDocument.CelldesignerProductLink pl =
                (CelldesignerProductLinkDocument.CelldesignerProductLink)r.getAnnotation().getCelldesignerListOfProductLinks().getCelldesignerProductLinkArray(i);
        if(pl.getProduct().equals(id))
          if(!takenaliases.containsKey(r.getId()+"_"+pl.getAlias()+"_"+role))
            alias = pl.getAlias();
      }
    }
    }

    if(alias.equals("")){
      System.out.println("Reaction "+r.getId()+" alias of "+id+" is empty");
      alias = id;
    }
    takenaliases.put(r.getId()+"_"+alias+"_"+role,r);
    //System.out.println(r.getId()+"_"+alias+" "+takenaliases.size());
    return alias;
  }

  /**
   * Finds all CellDesigner species aliases
   * @param sbml
   * @param spid
   * @return
   */
  public static Vector findAllAliasesForSpecies(SbmlDocument.Sbml sbml, String spid){
    Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> v1 = new Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
    Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> v2 = new Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();    
    Vector v = new Vector();
    if(sbml.getModel().getAnnotation()!=null){
    if(sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null){
    	v1 = speciesAliasMap.get(spid);
    	v2 = complexSpeciesAliasMap.get(spid);
    	if(v1!=null) 
    		for(int i=0;i<v1.size();i++) v.add(v1.get(i).getId());
    	if(v2!=null) 
    		for(int i=0;i<v2.size();i++) v.add(v2.get(i).getId());
    	
    /*for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
      CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa =
              (CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias)sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
      if(spa.getSpecies().equals(spid))
        v.add(spa.getId());
    }
    
    if(sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias spa =
              (CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias)sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
      if(spa.getSpecies().equals(spid))
        v.add(spa.getId());
    }*/
    }
    }
    return v;
  }

  /**
   * Axillary function, adds ' (prime) to Cytoscape node name to distinguish multiple CellDesigner species aliases 
   * @param sbml
   * @param spid species id
   * @param alias
   * @return
   */
  public static String getSuffixForMultipleAliases(SbmlDocument.Sbml sbml, String spid, String alias){
    String s = "";
    Vector v = findAllAliasesForSpecies(sbml,spid);
    for(int i=0;i<v.indexOf(alias);i++)
      s+="'";
    if(v.size()>1){
    	Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases = speciesAliasMap.get(spid);
    	if(aliases!=null){
    	for(int i=0;i<aliases.size();i++){
    		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = aliases.get(i);
        	if(cas.getId().equals(alias)){
   	         if(Utils.getText(cas.getCelldesignerActivity()).equals("active"))
   	           { s+="|active"; }
        	}
    	}
    	}else{
    		
    		Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexAliases =  complexSpeciesAliasMap.get(spid);
    		if(complexAliases!=null){
    	    	for(int i=0;i<complexAliases.size();i++){
    	    		CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cas = complexAliases.get(i);
    	        	if(cas.getId().equals(alias)){
    	   	         if(Utils.getText(cas.getCelldesignerActivity()).equals("active"))
    	   	           { s+="|active"; }
    	        	}
    	    	}
    		}else{
    			System.out.println("WARNING: "+spid+"("+convertSpeciesToName(sbml,spid,true,true)+"), no aliases found");
    		}
    	}
    /*for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
    	CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
    	if(cas.getId().equals(alias)){
	         if(Utils.getText(cas.getCelldesignerActivity()).equals("active"))
	           { s+="|active"; }
    	}
    }*/
    }
    return s;
  }

  /**
   * Creates the map from species id to SpeciesDocument.Species 
   * @param sbml
   * @return
   */
  public static HashMap getSpecies(SbmlDocument.Sbml sbml){
    HashMap hm = new HashMap();
    for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
      SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
      hm.put(sp.getId(),sp);
    }
    return hm;
  }
  
  /*public static HashMap getSpeciesAliases(SbmlDocument.Sbml sbml){
	    HashMap hm = new HashMap();
	    for(int i=0;i<sbml.getModel().getAnnotation().`().length;i++){
	      SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
	      hm.put(sp.getId(),sp);
	    }
	    return hm;
	  }
   */  

  /**
   * Generates a unique name for a species speciesID
   */
  public static String convertSpeciesToName(SbmlDocument sbmlDoc, String speciesID, boolean addCompartmentName, boolean addModifications){
	   return convertSpeciesToName(sbmlDoc.getSbml(),speciesID,addCompartmentName,addModifications,false,null);
	 }
  public static String convertSpeciesToName(SbmlDocument.Sbml sbml, String speciesID, boolean addCompartmentName, boolean addModifications){
	   return convertSpeciesToName(sbml,speciesID,addCompartmentName,addModifications,false,null);
	 }
     
     /**
      * Generates a unique name for a species speciesID
      * @param sbml
      * @param speciesID
      * @param addCompartmentName
      * @param addModifications
      * @return
      */
	 public static String convertSpeciesToName(SbmlDocument.Sbml sbml, String speciesID, boolean addCompartmentName, boolean addModifications, boolean uniqueName){
	   return convertSpeciesToName(sbml,speciesID,addCompartmentName,addModifications,uniqueName,null);
	 }
	 
	 public static String convertSpeciesToName(SbmlDocument sbmlDoc, String speciesID, boolean addCompartmentName, boolean addModifications, boolean uniqueName){
		   return convertSpeciesToName(sbmlDoc.getSbml(),speciesID,addCompartmentName,addModifications,uniqueName,null);
		 }
	 
     
	 /**
	  * 
	  * @param sbml
	  * @param speciesID species SBML id
	  * @return Species name as it is written in SBML 
	  */
	 public static String getCelldesignerSpeciesName(SbmlDocument.Sbml sbml, String speciesID){
	   String res = null;
	   for(int i=0;i<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	     SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
	     if(sp.getId().equals(speciesID)){
	       res = sp.getName().getStringValue();
	       break;
	     }
	   }
	   return res;
	 }

	  /**
	   * Generates a name for a species speciesID
	   */
	 public static String convertSpeciesToName(SbmlDocument.Sbml sbml, String speciesID, boolean addCompartmentName, boolean addModifications, boolean uniqueName, String alias){
	   String s = "";

		Date time1 = new Date();
		//System.out.println((new Date().getTime())-time1.getTime());	   
	   
	   if(comp_ids_n==null)
		   createSpeciesMap(sbml);
		
	   int kmax=-1, vmax = -1;
	   for(int i=0;i<comp_ids_n.size();i++){
	     if(((Integer)comp_ids_n.elementAt(i)).intValue()>vmax)
	     { vmax = ((Integer)comp_ids_n.elementAt(i)).intValue(); kmax = i; }
	   }
	   if(kmax>=0)
	     defcomp_id = (String)comp_ids.elementAt(kmax);

	   // We check normal species
	   if(speciesMap==null){
	   for(int i=0;i<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	     SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
	     if(sp.getId().equals(speciesID)){
	       //SpeciesAnnotationDocument.SpeciesAnnotation an = sp.getSpeciesAnnotation();
	       AnnotationDocument.Annotation an = sp.getAnnotation();
	       if(an!=null){
	         CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = an.getCelldesignerSpeciesIdentity();
	         s = getSpeciesName(ident,sp.getId(),getEntityName(sp.getId(),ident,sbml),sp.getCompartment(),addModifications,addCompartmentName,defcomp_id,sbml,alias);
	       }else{
	         s = Utils.getValue(sp.getName());
	       }
	     }
	   }}else{
	   SpeciesDocument.Species sp = speciesMap.get(speciesID);
	   if(sp!=null){
       AnnotationDocument.Annotation an = sp.getAnnotation();
       if(an!=null){
         CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = an.getCelldesignerSpeciesIdentity();
         s = getSpeciesName(ident,sp.getId(),getEntityName(sp.getId(),ident,sbml),sp.getCompartment(),addModifications,addCompartmentName,defcomp_id,sbml,alias);
       }else{
         s = Utils.getValue(sp.getName());
       }}}
	   
	   // We check included species
	   if(includedSpeciesMap==null){
	   if(sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
	   for(int j=0;j<sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();j++){
	     CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
	     if(isp.getId().equals(speciesID)){
	       String compl_id = Utils.getText(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
	       String compartment = "";
	       for(int k=0;k<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();k++){
	         SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(k);
	         if(sp.getId().equals(compl_id)){
	           compartment = sp.getCompartment();
	         }
	       }
	       CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
	       s = getSpeciesName(ident,isp.getId(),getEntityName(isp.getId(),ident,sbml),compartment,addModifications,addCompartmentName,defcomp_id,sbml,alias);
	       if(uniqueName)
	         s+="_in_"+convertSpeciesToName(sbml,compl_id,true,true);
	     }
	   }}else{
		   CelldesignerSpeciesDocument.CelldesignerSpecies isp = includedSpeciesMap.get(speciesID);
		   if(isp!=null){
	       String compl_id = Utils.getText(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
	       String compartment = "";
	       for(int k=0;k<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();k++){
	         SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(k);
	         if(sp.getId().equals(compl_id)){
	           compartment = sp.getCompartment();
	         }
	       }
	       CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
	       s = getSpeciesName(ident,isp.getId(),getEntityName(isp.getId(),ident,sbml),compartment,addModifications,addCompartmentName,defcomp_id,sbml,alias);
	       if(uniqueName)
	         s+="_in_"+convertSpeciesToName(sbml,compl_id,true,true);
		   }
	   }
	   return s;
	 }

	 public static String getSpeciesName(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi, String sp_id, String sp_name, String compartment, boolean addModifications, boolean addCompartmentName, String defcomp_id, SbmlDocument sbmlDoc){
	   return getSpeciesName(spi,sp_id,sp_name,compartment,addModifications,addCompartmentName,defcomp_id,sbmlDoc.getSbml(),null);
	 }
	 
	 public static String getSpeciesName(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi, String sp_id, String sp_name, String compartment, boolean addModifications, boolean addCompartmentName, String defcomp_id, SbmlDocument.Sbml sbml){
		   return getSpeciesName(spi,sp_id,sp_name,compartment,addModifications,addCompartmentName,defcomp_id,sbml,null);
		 }
	 

	 public static String getSpeciesName(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi, String sp_id, String sp_name, String compartment, boolean addModifications, boolean addCompartmentName, String defcomp_id, SbmlDocument.Sbml sbml, String alias){
	   String s = "";
	   String cl = null;
	   
	   if(spi == null){
		   SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(sp_id);
		   s = Utils.getValue(sp.getName());
		   return s;
	   }

	   //if(an!=null)
	     cl = Utils.getText(spi.getCelldesignerClass());

	   if(cl.equals("COMPLEX")){

	     Vector v = getIncludedSpeciesInComplex(sbml,sp_id);
	     Vector parts = new Vector();
	     if(v!=null){
	     for(int k=0;k<v.size();k++){
	       CelldesignerSpeciesDocument.CelldesignerSpecies isp = (CelldesignerSpeciesDocument.CelldesignerSpecies)v.elementAt(k);
	       parts.add(getNameOfIncludedSpecies(sbml,isp,alias));
	     }
	     Collections.sort(parts);
	     for(int k=0;k<parts.size();k++){
	       s+=(String)parts.get(k);
	       if(k<parts.size()-1)
	         s+=":";
	     }

	     String active = "";
	     if((complexSpeciesAliasMap==null)||(complexSpeciesAliasMap.get(sp_id)==null))
	 		CellDesignerToCytoscapeConverter.createSpeciesMap(sbml);

	     /*for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
	       CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csc = sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
	       if(csc.getSpecies().equals(sp_id)){
	       if(csc.getCelldesignerActivity()!=null){
	    	 //System.out.println("Activity: "+Utils.getText(csc.getCelldesignerActivity()));
	         if(Utils.getText(csc.getCelldesignerActivity()).equals("active"))
	           { active="|active"; break; }
	       }
	       }
	     }*/
	     Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> cscv = complexSpeciesAliasMap.get(sp_id);
	     for(CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csc: cscv){
		       if(csc.getSpecies().equals(sp_id)){
			       if(csc.getCelldesignerActivity()!=null){
			         if(Utils.getText(csc.getCelldesignerActivity()).equals("active"))
			           { active="|active"; break; }
			       }
	     }}
	     
	     if(!active.equals(""))
	    	 s="("+s+")|"+active;
         if(spi.getCelldesignerState()!=null)if(spi.getCelldesignerState().getCelldesignerHomodimer()!=null){
        	 if(s.startsWith("("))
        		 s+="|hm"+Utils.getValue(spi.getCelldesignerState().getCelldesignerHomodimer());
        	 else
        		 s="("+s+")|hm"+Utils.getValue(spi.getCelldesignerState().getCelldesignerHomodimer());
        		 
         }}else{
  //      	 System.out.println("WARNING: No parts found in complex "+sp_id);
         }
	     

	   }else{
	   s = sp_name;
	   if(cl.equals("GENE")) s = "g"+s;
	   if(cl.equals("RNA")) s = "r"+s;
	   if(cl.equals("ANTISENSE_RNA")) s = "ar"+s;
	   String ss = null;
	   CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = spi;
	   if(addModifications)
	   if(ident!=null){
	   CelldesignerStateDocument.CelldesignerState state = ident.getCelldesignerState();
	   if(state!=null){
	   CelldesignerListOfModificationsDocument.CelldesignerListOfModifications lmodifs = ident.getCelldesignerState().getCelldesignerListOfModifications();
	   if(lmodifs!=null){
	   CelldesignerModificationDocument.CelldesignerModification modifs[] = lmodifs.getCelldesignerModificationArray();
	   for(int j=0;j<modifs.length;j++){
	     CelldesignerModificationDocument.CelldesignerModification cm = modifs[j];
	     //System.out.print("\t"+cm.getResidue()+"_"+cm.getState());
	     String resname = getNameOfModificationResidue(sbml,ident,cm.getResidue());
	     String stt = cm.getState().getStringValue();
	     stt = stt.substring(0,3);
	     if((resname==null)||(resname.trim().equals("")))
	       s+="|"+stt;
	     else
	       s+="|"+resname+"_"+stt;
	   }
	   }
	   if(state.getCelldesignerHomodimer()!=null){
	     s+="|hm"+Utils.getText(state.getCelldesignerHomodimer());
	   }

	   String active = "";
	     if((speciesAliasMap==null)||(speciesAliasMap.get(sp_id)==null))
	 		CellDesignerToCytoscapeConverter.createSpeciesMap(sbml);
	   
	   /*for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
	     CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csc = sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	     if(csc.getSpecies().equals(sp_id))
	     if(csc.getCelldesignerActivity()!=null)
	       if(Utils.getText(csc.getCelldesignerActivity()).equals("active"))
	         { active="|active"; break; }
	   }*/
	   Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> cscv = speciesAliasMap.get(sp_id);
	   for(CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csc: cscv){
		     if(csc.getSpecies().equals(sp_id))
			     if(csc.getCelldesignerActivity()!=null)
			       if(Utils.getText(csc.getCelldesignerActivity()).equals("active"))
			         { active="|active"; break; }
	   }
	   
	   s+=active;
	   
	   String structuralState = "";
	   if(state.getCelldesignerListOfStructuralStates()!=null)
	   for(int i=0;i<state.getCelldesignerListOfStructuralStates().sizeOfCelldesignerStructuralStateArray();i++){
		   CelldesignerStructuralStateDocument.CelldesignerStructuralState sState = state.getCelldesignerListOfStructuralStates().getCelldesignerStructuralStateArray(i);
		   structuralState+="|"+Utils.getValue(sState.getStructuralState());
	   }
	   s+=structuralState;

	   }
	   }
	   if(cl.equals("DEGRADED"))
	     s = null;
	   }

	   for(int j=0;j<sbml.getModel().getListOfCompartments().sizeOfCompartmentArray();j++){
	     CompartmentDocument.Compartment cp = sbml.getModel().getListOfCompartments().getCompartmentArray(j);
	     if(cp.getId().equals(compartment)){
	       if((addCompartmentName)&&(cp.getId()!=null)&&(!cp.getId().equals(defcomp_id))||(alwaysMentionCompartment&&addCompartmentName)){
	         if(cp.getName()!=null){
	           s+="@"+cp.getName().getStringValue();
	         }
	         else{
	           s+="@"+cp.getId();
	         }
	       }
	       
	     }
	   }
	   return s;
	 }

	 public static Vector getIncludedSpeciesInComplex(SbmlDocument sbmlDoc, String complexid){
	   return getIncludedSpeciesInComplex(sbmlDoc.getSbml(),complexid);
	 }

	 public static Vector getIncludedSpeciesInComplex(SbmlDocument.Sbml sbml, String complexid){
	   Vector v = complexSpeciesMap.get(complexid);
	   return v;
	 }

	 public static String getNameOfIncludedSpecies(SbmlDocument sbmlDoc,CelldesignerSpeciesDocument.CelldesignerSpecies sp,String alias){
	   return getNameOfIncludedSpecies(sbmlDoc.getSbml(),sp,alias);
	 }

	 public static String getNameOfIncludedSpecies(SbmlDocument.Sbml sbml,CelldesignerSpeciesDocument.CelldesignerSpecies sp, String alias){
	   String s = null;
	   CelldesignerStateDocument.CelldesignerState st = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState();
	   s = sp.getName().getStringValue();
	   if(st!=null){
	     //for(int i=0;i<st.getCelldesigner_listOfModifications().getCelldesigner_modifications().size();i++){
	       CelldesignerListOfModificationsDocument.CelldesignerListOfModifications lmodifs = st.getCelldesignerListOfModifications();
	       if(lmodifs!=null){
	         CelldesignerModificationDocument.CelldesignerModification modifs[] = lmodifs.getCelldesignerModificationArray();
	         for(int j=0;j<modifs.length;j++){
	           CelldesignerModificationDocument.CelldesignerModification cm = modifs[j];
	           String resname = getNameOfModificationResidue(sbml,sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity(),cm.getResidue());
	           String stt = cm.getState().getStringValue();
	           stt = stt.substring(0,3);
	           if((resname==null)||(resname.trim().equals("")))
	             s+="|"+stt;
	           else
	             s+="|"+resname+"_"+stt;
	         }
	      }
	      if(st.getCelldesignerHomodimer()!=null)if(!Utils.getText(st.getCelldesignerHomodimer()).equals("")){
	        s+="|hm"+Utils.getText(st.getCelldesignerHomodimer());
	      }
		   String structuralState = "";
		   if(st.getCelldesignerListOfStructuralStates()!=null)
		   for(int i=0;i<st.getCelldesignerListOfStructuralStates().sizeOfCelldesignerStructuralStateArray();i++){
			   CelldesignerStructuralStateDocument.CelldesignerStructuralState sState = st.getCelldesignerListOfStructuralStates().getCelldesignerStructuralStateArray(i);
			   structuralState+="|"+Utils.getValue(sState.getStructuralState());
		   }
		   s+=structuralState;
	      
	   }

	      String cs = alias;
	      Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases = speciesAliasMap.get(sp.getId());
	      if(aliases!=null)
	      for(int i=0;i<aliases.size();i++){
	    	  CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spal = aliases.get(i);
	    	  if(spal.getComplexSpeciesAlias()!=null)
		    	  if(spal.getComplexSpeciesAlias().equals(cs)){
		              //Date tm = new Date();
		    		  s+=getSuffixForMultipleAliases(sbml,spal.getSpecies(),spal.getId());
		    	      //System.out.println("Al "+(i+1)+", time = "+((new Date()).getTime()-tm.getTime()));
		    	  }
	      }
	      
	   return s;
	 }

	 public static String getNameOfModificationResidue(SbmlDocument sbmlDoc, CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident, String residueID){
	   return getNameOfModificationResidue(sbmlDoc.getSbml(),ident,residueID);
	 }

	 public static String getNameOfModificationResidue(SbmlDocument.Sbml sbml, CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident, String residueID){
	   String s = "";
	   String prot = null;
	   if(ident.getCelldesignerProteinReference()!=null)
		   prot = Utils.getText(ident.getCelldesignerProteinReference());
	   if(prot!=null){
	     for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
	       CelldesignerProteinDocument.CelldesignerProtein cpr = sbml.getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
	       if(cpr.getId().equals(prot)){
	    	 if(cpr.getCelldesignerListOfModificationResidues()!=null)
	         for(int j=0;j<cpr.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++){
	           CelldesignerModificationResidueDocument.CelldesignerModificationResidue cmr = cpr.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
	           if(cmr.getId().equals(residueID)){
	             if(cmr.getName()!=null)
	               s = cmr.getName().getStringValue();
	             else s = null;
	           }
	         }
	       }
	     }
	   }
	   return s;
	 }

	/**
	 * Function for printing the CellDesigner reactions
	 * @param r
	 * @param sbmlDoc
	 * @param realNames
	 * @return Reaction encoded as a string 
	 */
	public static String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames){
	  String reactionString = "";
	  ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
	  for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
	    String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
	    if(realNames){
	      s = convertSpeciesToName(sbmlDoc,s,true,true);
	    }
	    if((s!=null)&&(!s.startsWith("null"))){
	    reactionString+=s;
	    if(j<r.getListOfReactants().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
	    }
	  }
	  if(lm!=null){
	  reactionString+=" - ";
	  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
	    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
	    if(realNames){
	      s = convertSpeciesToName(sbmlDoc,s,true,true);
	    }
	    if((s!=null)&&(!s.startsWith("null"))){
	    reactionString+=s;
	    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
	    }
	  }}
	  reactionString+=" -> ";
	  for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
	    String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
	    if(realNames){
	      s = convertSpeciesToName(sbmlDoc,s,true,true);
	    }
	    if((s!=null)&&(!s.startsWith("null"))){
	    reactionString+=s;
	    if(j<r.getListOfProducts().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
	    }
	  }
	  /*if(lm!=null){
	  reactionString+="+";
	  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
	    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
	    if(realNames){
	      s = convertSpeciesToName(sbmlDoc,s,true,true);
	    }
	    if((s!=null)&&(!s.startsWith("null"))){
	    reactionString+=s;
	    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
	    }
	  }}*/
	  return reactionString;
	}


	public static String getEntityName(String spid, CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident, SbmlDocument sbmlDoc){
	   return getEntityName(spid,ident,sbmlDoc.getSbml());
	}

	public static String getEntityName(String spid, CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident, SbmlDocument.Sbml sbml){
	  String res = null;
	  if(ident==null){
		  //System.out.println("ident = null for "+spid);
		  SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(spid);
		  res = Utils.getValue(sp.getName());
	  }else{
	  String spcl = Utils.getText(ident.getCelldesignerClass());
	  if(spcl.equals("PROTEIN")){
	    String id = Utils.getText(ident.getCelldesignerProteinReference());
	    CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get(id);
	    try{
	    res = prot.getName().getStringValue();
	    }catch(Exception e){
	      System.out.println("ERROR: Species id = "+spid+" Protein id = "+id);
	      //e.printStackTrace();
	      //res = spid;
	    }
	  }else
	  if(spcl.equals("GENE"))
	  {
	    String id = Utils.getText(ident.getCelldesignerGeneReference());
	    CelldesignerGeneDocument.CelldesignerGene gene = (CelldesignerGeneDocument.CelldesignerGene)CellDesigner.entities.get(id);
	    res = gene.getName();
	  }else
	  if(spcl.equals("RNA"))
	  {
	    String id = Utils.getText(ident.getCelldesignerRnaReference());
	    CelldesignerRNADocument.CelldesignerRNA rna = (CelldesignerRNADocument.CelldesignerRNA)CellDesigner.entities.get(id);
	    res = rna.getName();
	  }else
	  if(spcl.equals("DEGRADED"))
	  {
	    res = "null_degraded";
	  }else
	  if(spcl.equals("ANTISENSE_RNA"))
	  {
	    String id = Utils.getText(ident.getCelldesignerAntisensernaReference());
	    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = (CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA)CellDesigner.entities.get(id);
	    res = arna.getName();
	  }else
	  if(spcl.equals("ION"))
	  {
	    Object ent = CellDesigner.entities.get(spid);
	    if(ent!=null){
	      if(ent.getClass().getName().indexOf("CelldesignerSpeciesDocument")>=0){
	        CelldesignerSpeciesDocument.CelldesignerSpecies csp = (CelldesignerSpeciesDocument.CelldesignerSpecies)CellDesigner.entities.get(spid);
	        res = csp.getName().getStringValue();
	      }else{
	        SpeciesDocument.Species sp = (SpeciesDocument.Species)ent;
	        res = sp.getName().getStringValue();
	      }
	    }else{
	        System.out.println("WARNING!!! "+spid+" not found in the entities list");
	        res = "unknown_ion";
	    }
	  }else
	  if(spcl.equals("SIMPLE_MOLECULE"))
	   {
	    Object ent = CellDesigner.entities.get(spid);
	    if(ent!=null){
	      if(ent.getClass().getName().indexOf("CelldesignerSpeciesDocument")>=0){
	        CelldesignerSpeciesDocument.CelldesignerSpecies csp = (CelldesignerSpeciesDocument.CelldesignerSpecies)CellDesigner.entities.get(spid);
	        res = csp.getName().getStringValue();
	      }else{
	        SpeciesDocument.Species sp = (SpeciesDocument.Species)ent;
	        res = sp.getName().getStringValue();
	      }
	    }else{
	        System.out.println("WARNING!!! "+spid+" not found in the entities list");
	        res = "unknown_simple_molecule";
	    }
	  }else
	  if(spcl.equals("COMPLEX"))
	  {
	    //CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias compl = (CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias)entities.get(spid);
	    //res = compl.getId();
	    //SpeciesDocument.Species sp = (SpeciesDocument.Species)entities.get(spid);
	    res = "";
	  }else
	  if(spcl.equals("UNKNOWN")||spcl.equals("PHENOTYPE")||spcl.equals("DRUG"))
	  {
		Object obj = CellDesigner.entities.get(spid);
		if(obj instanceof SpeciesDocument.Species){
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(spid);
			res = sp.getName().getStringValue();
		}
		if(obj instanceof CelldesignerSpeciesDocument.CelldesignerSpecies){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = (CelldesignerSpeciesDocument.CelldesignerSpecies)CellDesigner.entities.get(spid);
			res = sp.getName().getStringValue();
		}
	  }

	  if(res==null)
	    System.out.println("UNKNOWN ENTITY TYPE "+spcl+" !!! "+spid);
	  }
	  return res;
	}

	/**
	 * Merges two CellDesigner SbmlDocuments, the result of merging is in sbout
	 * @param sbout
	 * @param sbin
	 */
	public static void mergeCellDesignerFiles(SbmlDocument sbout, SbmlDocument sbin){ 
	  HashMap hm = CellDesigner.getAllObjectsHash(sbout);
	  //System.out.print("Merging ... ");	
	  // Compartments
	  //System.out.print("compartments ");
	  if(sbin.getSbml().getModel().getListOfCompartments()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getListOfCompartments().getCompartmentArray().length;i++){
	    CompartmentDocument.Compartment comp = sbin.getSbml().getModel().getListOfCompartments().getCompartmentArray(i);
	    if(hm.get(comp.getId())==null){
	    sbout.getSbml().getModel().getListOfCompartments().addNewCompartment();
	    sbout.getSbml().getModel().getListOfCompartments().setCompartmentArray(
	               sbout.getSbml().getModel().getListOfCompartments().getCompartmentArray().length-1,
	               comp);
	    }
	  }
	  // CompartmentAliases
	  //System.out.print("compartment aliases ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
	    CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias ca = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
	    if(hm.get(ca.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().addNewCelldesignerCompartmentAlias();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().setCelldesignerCompartmentAliasArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length-1,
	          ca);
	    }
	  }
	  // Species
	  //System.out.print("species ");
	  if(sbin.getSbml().getModel().getListOfSpecies()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
	    SpeciesDocument.Species sp = sbin.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    if(hm.get(sp.getId())==null){
	      sbout.getSbml().getModel().getListOfSpecies().addNewSpecies();
	      sbout.getSbml().getModel().getListOfSpecies().setSpeciesArray(
	          sbout.getSbml().getModel().getListOfSpecies().getSpeciesArray().length-1,
	          sp);
	    }
	  }
	  // SpeciesAliases
	  //System.out.print("species aliases ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
	    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	    if(hm.get(spa.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().setCelldesignerSpeciesAliasArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length-1,
	          spa);
	    }
	  }
	  // ComplexSpeciesAliases
	  //System.out.print("complex species aliases ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
	    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias spa = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
	    if(hm.get(spa.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().setCelldesignerComplexSpeciesAliasArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length-1,
	          spa);
	    }
	  }
	  // IncludedSpeciesAliases
	  //System.out.print("included species aliases ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;i++){
	    CelldesignerSpeciesDocument.CelldesignerSpecies spa = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
	    if(hm.get(spa.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().setCelldesignerSpeciesArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length-1,
	          spa);
	    }
	  }
	  // Reactions
	  //System.out.print("reactions ");
	  if(sbin.getSbml().getModel().getListOfReactions()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
	    ReactionDocument.Reaction r = sbin.getSbml().getModel().getListOfReactions().getReactionArray(i);
	    if(hm.get(r.getId())==null){
	      sbout.getSbml().getModel().getListOfReactions().addNewReaction();
	      sbout.getSbml().getModel().getListOfReactions().setReactionArray(
	          sbout.getSbml().getModel().getListOfReactions().getReactionArray().length-1,
	          r);
	    }
	  }
	  // Events
	  // Function definitions
	  // Unit definitions
	  // Proteins
	  //System.out.print("proteins ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
	    CelldesignerProteinDocument.CelldesignerProtein cp = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
	    if(hm.get(cp.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().setCelldesignerProteinArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length-1,
	          cp);
	    }
	  }
	  // Genes
	  //System.out.print("genes("+ngenes+") ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length;i++){
	    CelldesignerGeneDocument.CelldesignerGene gn = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
	    //System.out.print(gn.getName()+" ");
	    if(hm.get(gn.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().addNewCelldesignerGene();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().setCelldesignerGeneArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length-1,
	          gn);
	    }
	  }
	  // RNAs
	  //System.out.print("RNAs ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length;i++){
	    CelldesignerRNADocument.CelldesignerRNA cr = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
	    if(hm.get(cr.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().setCelldesignerRNAArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length-1,
	          cr);
	    }
	  }
	  // AntisenseRNAs
	  //System.out.print("aRNAs ");
	  if(sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
	  for(int i=0;i<sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length;i++){
	    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA cr = sbin.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
	    if(hm.get(cr.getId())==null){
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().addNewCelldesignerAntisenseRNA();
	      sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().setCelldesignerAntisenseRNAArray(
	          sbout.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length-1,
	          cr);
	    }
	  }
	}
	
	/**
	 * Utility function. Try to correct problems with species ids, caused by some manipulations in CellDesigner
	 * @param sb
	 * @param sbbase
	 * @return
	 */
	public static String checkAndModifySpeciesIDs(SbmlDocument sb, SbmlDocument sbbase){
	  String errString = null;

	  CellDesigner.entities = CellDesigner.getEntities(sbbase);
	  HashMap IdMapBase = MapSpeciesId(sbbase);
	  CellDesigner.entities = CellDesigner.getEntities(sb);
	  HashMap IdMap = MapSpeciesId(sb);

	  HashMap subs = new HashMap();
	  HashMap subsinv = new HashMap();

	  Iterator it = IdMap.keySet().iterator();
	  int knotf = 0;
	  int kdiff = 0;
	  while(it.hasNext()){
	     String name = (String)it.next();
	     if(!name.startsWith("$")){
	     String id = (String)IdMap.get(name);
	     String idbase = (String)IdMapBase.get(name);
	     if(idbase==null){
	       System.out.println((++knotf)+") Can't find idbase for "+name+" (id="+id+")");
	     }
	     else{
	     if(!id.equals(idbase)){
	       System.out.println((++kdiff)+") Different ids for "+name+": (id="+id+", idbase="+idbase+")");
	       subs.put(id,idbase);
	       subsinv.put(idbase,id);
	     }
	     }
	     }
	  }
	  if((kdiff==0)&&(knotf==0))
	    System.out.println("Comparing Celldesigner base and new files. No problems with ids have been found.");
	  if(knotf==0){
	    Iterator keys = subs.keySet().iterator();
	    while(keys.hasNext()){
	      String id = (String)keys.next();
	      substituteSpeciesId(sb,id,(String)subs.get(id)+"$$");
	    }
	    keys = subsinv.keySet().iterator();
	    int k = 0;
	    while(keys.hasNext()){
	      String id = (String)keys.next();
	      substituteSpeciesId(sb,id+"$$",id);
	      System.out.println((++k)+") Problem with "+(String)subsinv.get(id)+"<->"+id+" has been corrected.");
	    }
	  }else{
	    //System.out.println();
	    errString = "It is impossible to fix the problem: "+knotf+" ids is not found in the base file.\n This can happen if two completely different pathways are merged,\n but if not then this also can point to a problem.";
	  }
	  return errString;
	}


	public static String checkAndModifyEntitiesIDs(SbmlDocument sb, SbmlDocument sbbase){
	  String errString = null;

	  CellDesigner.entities = CellDesigner.getEntities(sbbase);
	  HashMap IdMapBase = MapEntitiesId(sbbase);
	  CellDesigner.entities = CellDesigner.getEntities(sb);
	  HashMap IdMap = MapEntitiesId(sb);

	  HashMap subs = new HashMap();
	  HashMap subsinv = new HashMap();

	  Iterator it = IdMap.keySet().iterator();
	  int knotf = 0;
	  int kdiff = 0;
	  while(it.hasNext()){
	     String name = (String)it.next();
	     if(!name.startsWith("$")){
	     String id = (String)IdMap.get(name);
	     String idbase = (String)IdMapBase.get(name);
	     if(idbase==null){
	       System.out.println((++knotf)+") Can't find ENTITY idbase for "+name+" (ENTITY id="+id+")");
	     }
	     else{
	     if(!id.equals(idbase)){
	       System.out.println((++kdiff)+") Different ENTITY ids for "+name+": (ENTITY id="+id+", ENTITY idbase="+idbase+")");
	       subs.put(id,idbase);
	       subsinv.put(idbase,id);
	     }
	     }
	     }
	  }
	  if((kdiff==0)&&(knotf==0))
	    System.out.println("Comparing Celldesigner base and new files. No problems with ENTITY ids have been found.");
	  if(knotf==0){
	    Iterator keys = subs.keySet().iterator();
	    while(keys.hasNext()){
	      String id = (String)keys.next();
	      substituteEntityId(sb,id,(String)subs.get(id)+"$$");
	    }
	    keys = subsinv.keySet().iterator();
	    int k = 0;
	    while(keys.hasNext()){
	      String id = (String)keys.next();
	      substituteEntityId(sb,id+"$$",id);
	      System.out.println((++k)+") Problem with ENTITY "+(String)subsinv.get(id)+"<->"+id+" has been corrected.");
	    }
	  }else{
	    //System.out.println();
	    errString = "It is impossible to fix the problem: "+knotf+" ENTITY ids is not found in the base file.\n This can happen if two completely different pathways are merged,\n but if not then this also can point to a problem.";
	  }
	  return errString;
	}


	public static HashMap MapEntitiesId(SbmlDocument sbb){
	  HashMap res = new HashMap();
	  for(int i=0;i<sbb.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	    String id = sbb.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getId();
	    CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid = sbb.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getAnnotation().getCelldesignerSpeciesIdentity();
	    String name = CellDesignerToCytoscapeConverter.getEntityName(id,spid,sbb);
	    String eid = getEntityId(spid);
	    if(eid!=null)
	    if((name!=null)&&(!name.startsWith("null"))){
	      res.put(name,eid);
	      res.put("$"+eid,name);
	    }
	  }
	  for(int i=0;i<sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
	    String id = sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i).getId();
	    CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid = sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i).getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
	    String name = CellDesignerToCytoscapeConverter.getEntityName(id,spid,sbb);
	    String eid = getEntityId(spid);
	    if(eid!=null)
	    if((name!=null)&&(!name.startsWith("null"))){
	      res.put("$"+eid,name);
	      res.put(name,eid);
	    }
	  }
	  return res;
	}

	public static String getEntityId(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid){
	  String eid = null;
	  String ecl = Utils.getValue(spid.getCelldesignerClass());
	  if(ecl.equals("PROTEIN")){
	    eid = Utils.getValue(spid.getCelldesignerProteinReference());
	  }else
	  if(ecl.equals("GENE")){
	    eid = Utils.getValue(spid.getCelldesignerGeneReference());
	  }else
	  if(ecl.equals("RNA")){
	    eid = Utils.getValue(spid.getCelldesignerRnaReference());
	  }else
	  if(ecl.equals("ANTISENSERNA")){
	    eid = Utils.getValue(spid.getCelldesignerAntisensernaReference());
	  }else
	  if(ecl.equals("HYPOTHETICAL")){
	    eid = Utils.getValue(spid.getCelldesignerHypothetical());
	  }
	  //if(eid==null) System.out.println("UNKNOWN TYPE: "+ecl);
	  return eid;
	}

	public static void setEntityId(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid, String id){
	  String eid = null;
	  String ecl = Utils.getValue(spid.getCelldesignerClass());
	  if(ecl.equals("PROTEIN")){
	    Utils.setValue(spid.getCelldesignerProteinReference(),id);
	  }else
	    if(ecl.equals("GENE")){
	      Utils.setValue(spid.getCelldesignerGeneReference(),id);
	    }else
	      if(ecl.equals("RNA")){
	        Utils.setValue(spid.getCelldesignerRnaReference(),id);
	      }else
	        if(ecl.equals("ANTISENSERNA")){
	          Utils.setValue(spid.getCelldesignerAntisensernaReference(),id);
	        }else
	          if(ecl.equals("HYPOTHETICAL")){
	            Utils.setValue(spid.getCelldesignerHypothetical(),id);
	          }
	  //if(eid==null) System.out.println("UNKNOWN TYPE: "+ecl);
	}

	public static HashMap MapSpeciesId(SbmlDocument sbb){
	  HashMap res = new HashMap();
	  for(int i=0;i<sbb.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	  }
	  for(int i=0;i<sbb.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	    String id = sbb.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getId();
	    String name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbb.getSbml(),id,true,true,true);
	    if((name!=null)&&(!name.startsWith("null"))){
	      res.put(name,id);
	      res.put("$"+id,name);
	    }
	  }
	  for(int i=0;i<sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
	    String id = sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i).getId();
	    String name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbb.getSbml(),id,true,true,true);
	    if((name!=null)&&(!name.startsWith("null"))){
	      res.put("$"+id,name);
	      res.put(name,id);
	    }
	  }
	  return res;
	}


	public static HashMap MapSpeciesAliases(SbmlDocument sbb){
	  HashMap res = new HashMap();
	  for(int i=0;i<sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
	    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias al = sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	    String alias = al.getId();
	    String id = al.getSpecies();
	    res.put(alias,id);
	  }
	  for(int i=0;i<sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
	    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias al = sbb.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
	    String alias = al.getId();
	    String id = al.getSpecies();
	    res.put(alias,id);
	  }
	  return res;
	}

	public static void substituteEntityId(SbmlDocument sb, String idold, String idnew){
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
	    CelldesignerProteinDocument.CelldesignerProtein prot = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
	    if(prot.getId().equals(idold))
	      prot.setId(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
	    CelldesignerGeneDocument.CelldesignerGene gene = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
	    if(gene.getId().equals(idold))
	      gene.setId(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
	    CelldesignerRNADocument.CelldesignerRNA Rna = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
	    if(Rna.getId().equals(idold))
	      Rna.setId(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
	    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA AntisenseRNA = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
	    if(AntisenseRNA.getId().equals(idold))
	      AntisenseRNA.setId(idnew);
	  }


	  for(int i=0;i<sb.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	    SpeciesDocument.Species sp = sb.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid = sp.getAnnotation().getCelldesignerSpeciesIdentity();
	    String eid = getEntityId(spid);
	    if(eid!=null)
	    if(eid.equals(idold))
	      setEntityId(spid,idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
	    CelldesignerSpeciesDocument.CelldesignerSpecies sp = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
	    CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spid = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
	    String eid = getEntityId(spid);
	    if(eid!=null)
	    if(eid.equals(idold))
	      setEntityId(spid,idnew);
	  }

	}

	public static void substituteSpeciesId(SbmlDocument sb, String idold, String idnew){
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
	    CelldesignerSpeciesDocument.CelldesignerSpecies sp = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
	    String id = sp.getId();
	    if(id.equals(idold))
	      sp.setId(idnew);
	    String cid = Utils.getValue(sp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
	    if(cid.equals(idold))
	      Utils.setValue(sp.getCelldesignerAnnotation().getCelldesignerComplexSpecies(),idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
	    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias sp = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	    String id = sp.getSpecies();
	    if(id.equals(idold))
	      sp.setSpecies(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
	    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias sp = sb.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
	    String id = sp.getSpecies();
	    if(id.equals(idold))
	      sp.setSpecies(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	    SpeciesDocument.Species sp = sb.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    String id = sp.getId();
	    if(id.equals(idold))
	      sp.setId(idnew);
	  }
	  for(int i=0;i<sb.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
	    ReactionDocument.Reaction r = sb.getSbml().getModel().getListOfReactions().getReactionArray(i);
	    if(r.getListOfReactants()!=null)
	    for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
	      SpeciesReferenceDocument.SpeciesReference sp = r.getListOfReactants().getSpeciesReferenceArray(j);
	      if(sp.getSpecies().equals(idold))
	        sp.setSpecies(idnew);
	    }
	    if(r.getListOfProducts()!=null)
	    for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
	      SpeciesReferenceDocument.SpeciesReference sp = r.getListOfProducts().getSpeciesReferenceArray(j);
	      if(sp.getSpecies().equals(idold))
	        sp.setSpecies(idnew);
	    }
	    if(r.getListOfModifiers()!=null)
	    for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
	      ModifierSpeciesReferenceDocument.ModifierSpeciesReference sp = r.getListOfModifiers().getModifierSpeciesReferenceArray(j);
	      if(sp.getSpecies().equals(idold))
	        sp.setSpecies(idnew);
	    }
	    if(r.getAnnotation().getCelldesignerBaseReactants()!=null)
	      for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
	         CelldesignerBaseReactantDocument.CelldesignerBaseReactant br = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
	         String id = br.getSpecies().getStringValue();
	         if(id.equals(idold)){
	           br.getSpecies().setStringValue(idnew);
	         }
	      }
	      if(r.getAnnotation().getCelldesignerBaseProducts()!=null)
	        for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
	           CelldesignerBaseProductDocument.CelldesignerBaseProduct br = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
	           String id = br.getSpecies().getStringValue();
	           if(id.equals(idold)){
	             br.getSpecies().setStringValue(idnew);
	           }
	        }
	      if(r.getAnnotation().getCelldesignerListOfModification()!=null)
	        for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
	            CelldesignerModificationDocument.CelldesignerModification cm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
	            String id = cm.getModifiers();
	            if(id.equals(idold))
	              cm.setModifiers(idnew);
	        }

	  }
	  }
	
	public static void createSpeciesMap(SbmlDocument.Sbml sbml){
		speciesMap = new HashMap<String,SpeciesDocument.Species>();
		includedSpeciesMap = new HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies>();
		complexSpeciesMap = new HashMap<String,Vector<CelldesignerSpeciesDocument.CelldesignerSpecies>>();
		speciesAliasMap = new HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>>();
		complexSpeciesAliasMap = new HashMap<String,Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>>();
		for(int i=0;i<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();i++)
			speciesMap.put(sbml.getModel().getListOfSpecies().getSpeciesArray(i).getId(), sbml.getModel().getListOfSpecies().getSpeciesArray(i));
		if(sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int j=0;j<sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();j++){
		     CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
		     includedSpeciesMap.put(isp.getId(), isp);
		}
		if(sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		   for(int j=0;j<sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();j++){
		     CelldesignerSpeciesDocument.CelldesignerSpecies csp = sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
		     String complexsp = Utils.getText(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
		     Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> v = complexSpeciesMap.get(complexsp);
		     if(v==null){
		    	 v = new Vector<CelldesignerSpeciesDocument.CelldesignerSpecies>();
		    	 complexSpeciesMap.put(complexsp, v);
		     }
		     v.add(csp);
		   }		
		  if(sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null) 
	      for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
	    	  CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spal = sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	    	  Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> v = speciesAliasMap.get(spal.getSpecies());
	    	  if(v==null){
	    		  v = new Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
	    		  speciesAliasMap.put(spal.getSpecies(),v);
	    	  }
	    	  v.add(spal);
	      }
		  if(sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null) 
		      for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
		    	  CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias spal = sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
		    	  Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> v = complexSpeciesAliasMap.get(spal.getSpecies());
		    	  if(v==null){
		    		  v = new Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		    		  complexSpeciesAliasMap.put(spal.getSpecies(),v);
		    	  }
		    	  v.add(spal);
		  }		  
	
		   defcomp_id = "default";
		   comp_ids = new Vector();
		   comp_ids_n = new Vector();
		   for(int i=0;i<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		     SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
		     if(comp_ids.indexOf(sp.getCompartment())<0){
		       comp_ids.add(sp.getCompartment());
		       comp_ids_n.add(new Integer(1));
		     }else{
		       comp_ids_n.setElementAt(new Integer(((Integer)comp_ids_n.elementAt(comp_ids.indexOf(sp.getCompartment()))).intValue()+1),comp_ids.indexOf(sp.getCompartment()));
		     }
		   }
		  
		  
	}
	
	public static Vector<Vector<String>> extractAttributesFromNotes(Notes notes){
		Vector<Vector<String>> res = new Vector<Vector<String>>();
		String text = Utils.getValue(notes);
		StringTokenizer st = new StringTokenizer(text," \n\t");
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if(token.contains(":")){
				StringTokenizer st1 = new StringTokenizer(token,":");
				try{
				if(st1.hasMoreTokens()){
				String attname = st1.nextToken().toUpperCase();
				if(st1.hasMoreTokens()){
				String attvalue = st1.nextToken().toUpperCase();
				Vector<String> v = new Vector<String>();
				v.add(attname); v.add(attvalue);
				res.add(v);
				}
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public static Vector<Vector<String>> extractAttributesFromNotes(CelldesignerNotes notes){
		Vector<Vector<String>> res = new Vector<Vector<String>>();
		String text = Utils.getValue(notes);
		StringTokenizer st = new StringTokenizer(text," \n\t");
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if(token.contains(":")){
				StringTokenizer st1 = new StringTokenizer(token,":");
				if(st1.hasMoreTokens()){
				String attname = st1.nextToken();
				if(st1.hasMoreTokens()){
				String attvalue = st1.nextToken();
				Vector<String> v = new Vector<String>();
				v.add(attname); v.add(attvalue);
				res.add(v);
				}
				}
			}
		}
		return res;
	}
	
	public static Vector<CelldesignerProteinDocument.CelldesignerProtein> getProteinsInComplex(org.sbml.x2001.ns.celldesigner.SbmlDocument.Sbml sbml, String id){
		Vector<CelldesignerProteinDocument.CelldesignerProtein> proteins = new Vector<CelldesignerProteinDocument.CelldesignerProtein>();
		Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> species = complexSpeciesMap.get(id);
		if(species!=null)
		for(int i=0;i<species.size();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = species.get(i);
			if(sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String prid = Utils.getText(sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				CelldesignerProteinDocument.CelldesignerProtein pr = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get(prid);
				proteins.add(pr);
			}
		}
		return proteins;
	}
	

}
