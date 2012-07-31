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

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

/**
 * Class used to export a part of CellDesigner file, using only species and reactions
 * visualized in the current Cytoscape network 
  */
public class CytoscapeToCellDesignerConverter {
    public static void main(String[] args) {

	try{



	}catch(Exception e){
	    e.printStackTrace();
	}


    }

    public static void copyLayout(SbmlDocument sbml,GraphDocument gr){

	HashMap aliases = new HashMap();
	for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
	    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	    aliases.put(csp.getId(),csp);
	}

	for(int i=0;i<gr.getGraph().getNodeArray().length;i++){
	    GraphicNode n = gr.getGraph().getNodeArray(i);
	    //System.out.print(n.getId()+":\t");
	    if(XGMML.getAttValue(n,"CELLDESIGNER_SPECIES")!=null){
		//System.out.print("Species\t"+XGMML.getAttValue(n,"CELLDESIGNER_SPECIES"));
		String alias = XGMML.getAttValue(n,"CELLDESIGNER_ALIAS");
		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csp = (CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias)aliases.get(alias);
		if(csp!=null){
		    csp.getCelldesignerBounds().setX(""+n.getGraphics().getX());
		    csp.getCelldesignerBounds().setY(""+n.getGraphics().getY());
		}
	    }
	    if(XGMML.getAttValue(n,"CELLDESIGNER_REACTION")!=null){
		//System.out.print("Reaction\t"+XGMML.getAttValue(n,"CELLDESIGNER_REACTION"));
	    }
	}
    }

    public static void filterIDs(SbmlDocument sbml,GraphDocument gr){

	Vector species = new Vector();
	Vector speciesAliases = new Vector();
	Vector reactions = new Vector();
	Vector degraded = new Vector();

	for(int i=0;i<gr.getGraph().getNodeArray().length;i++){
	    GraphicNode n = gr.getGraph().getNodeArray(i);
	    if(XGMML.getAttValue(n,"CELLDESIGNER_SPECIES")!=null){
		species.add(XGMML.getAttValue(n,"CELLDESIGNER_SPECIES"));
		speciesAliases.add(XGMML.getAttValue(n,"CELLDESIGNER_ALIAS"));
	    }
	    if(XGMML.getAttValue(n,"CELLDESIGNER_REACTION")!=null){
		reactions.add(XGMML.getAttValue(n,"CELLDESIGNER_REACTION"));
	    }
	}

	for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
	    SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    String cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
	    if(cl.equals("DEGRADED"))
		degraded.add(sp.getId());
	}

	//filterIDs(sbml, species, speciesAliases, reactions, degraded);
        filterIDsCompleteReactions(sbml, species, speciesAliases, reactions, degraded);
    }

    public static void filterIDs(SbmlDocument sbml, Vector species, Vector speciesAliases, Vector reactions, Vector degraded) {

	Vector entities = new Vector();

	// Remove reactions
	int k = 0;
	while(k<sbml.getSbml().getModel().getListOfReactions().getReactionArray().length){
	    ReactionDocument.Reaction rr = sbml.getSbml().getModel().getListOfReactions().getReactionArray(k);

	    //check if products/reactants are all available
	    boolean defined = true;
	    if(rr.getListOfProducts()!=null)
		for(int i=0;i<rr.getListOfProducts().getSpeciesReferenceArray().length;i++){
		    String sp = rr.getListOfProducts().getSpeciesReferenceArray(i).getSpecies();
		    if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
		}
	    if(rr.getListOfReactants()!=null)
		for(int i=0;i<rr.getListOfReactants().getSpeciesReferenceArray().length;i++){
		    String sp = rr.getListOfReactants().getSpeciesReferenceArray(i).getSpecies();
		    if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
		}
	    if(rr.getListOfModifiers()!=null)
		for(int i=0;i<rr.getListOfModifiers().getModifierSpeciesReferenceArray().length;i++){
		    String sp = rr.getListOfModifiers().getModifierSpeciesReferenceArray(i).getSpecies();
		    if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
		}

	    if((!defined)||(reactions.indexOf(rr.getId())<0)){
		System.out.println("Removing "+rr.getId());
		for(int i=0;i<rr.getListOfProducts().getSpeciesReferenceArray().length;i++){
		    SpeciesReferenceDocument.SpeciesReference sr = rr.getListOfProducts().getSpeciesReferenceArray(i);
		    if(degraded.indexOf(sr.getSpecies())>=0)
			degraded.remove(degraded.indexOf(sr.getSpecies()));
		}
		sbml.getSbml().getModel().getListOfReactions().removeReaction(k);
	    }else{
		k++;
	    }
	}



	// Remove species aliases
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length){
	    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(k);
	    String cspa = csp.getComplexSpeciesAlias();
	    if((speciesAliases.indexOf(csp.getId())<0)&&(degraded.indexOf(csp.getSpecies())<0)&&(speciesAliases.indexOf(cspa))<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(k);
	    }else{
		k++;
	    }
	}
	// Remove complex species aliases
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length){
	    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(k);
	    if(speciesAliases.indexOf(cspa.getId())<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().removeCelldesignerComplexSpeciesAlias(k);
	    }else{
		k++;
	    }
	}
	// Remove included species aliases
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length){
	    CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(k);
	    String scp = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
	    if(species.indexOf(scp)<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().removeCelldesignerSpecies(k);
	    }else{
		k++;
	    }
	}
	// Remove species
	k = 0;
	while(k<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length){
	    SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(k);
	    if((species.indexOf(sp.getId())<0)&&(degraded.indexOf(sp.getId()))<0){
		sbml.getSbml().getModel().getListOfSpecies().removeSpecies(k);
	    }else{
		k++;
	    }
	}
	// Remove entity references

	for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
	    SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    String cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
	    if(species.indexOf(sp.getId())>=0){
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null)
		    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
		if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null)
		    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()));
		if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null)
		    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()));
		if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical()!=null)
		    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical()));
		if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null)
		    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()));
		if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("COMPLEX")){
                  for(int kk=0;kk<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;kk++){
                    CelldesignerSpeciesDocument.CelldesignerSpecies csp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(kk);
                    String compsp = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
                    if(compsp.equals(sp.getId())){
                      CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity cid = csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
                      if(cid.getCelldesignerProteinReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerProteinReference()));
                      if(cid.getCelldesignerGeneReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerGeneReference()));
                      if(cid.getCelldesignerAntisensernaReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerAntisensernaReference()));
                      if(cid.getCelldesignerHypothetical()!=null) entities.add(Utils.getValue(cid.getCelldesignerHypothetical()));
                      if(cid.getCelldesignerRnaReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerRnaReference()));
                    }
                  }
		}
	    }
	}

	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length){
	    String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(k).getId();
	    if(entities.indexOf(id)<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(k);
		System.out.println("Removing "+id);
	    }else k++;
	}
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length){
	    String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(k).getId();
	    if(entities.indexOf(id)<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().removeCelldesignerAntisenseRNA(k);
		System.out.println("Removing "+id);
	    }else k++;
	}
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length){
	    String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(k).getId();
	    if(entities.indexOf(id)<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(k);
		System.out.println("Removing "+id);
	    }else k++;
	}
	k = 0;
	while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length){
	    String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(k).getId();
	    if(entities.indexOf(id)<0){
		sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(k);
		System.out.println("Removing "+id);
	    }else k++;
	}

    }


    public static void filterIDsCompleteReactions(SbmlDocument sbml, Vector species, Vector speciesAliases, Vector reactions, Vector degraded) {

        Vector entities = new Vector();

        // Remove reactions
        int k = 0;
//        System.out.print("Removing reactions : ");
        CellDesignerToCytoscapeConverter.takenaliases = new HashMap();
        if(sbml.getSbml().getModel().getListOfReactions()!=null)
        while(k<sbml.getSbml().getModel().getListOfReactions().getReactionArray().length){
            ReactionDocument.Reaction rr = sbml.getSbml().getModel().getListOfReactions().getReactionArray(k);

            //we complete reactions
            boolean defined = true;
            if(reactions.indexOf(rr.getId())>=0){
            if(rr.getListOfProducts()!=null)
                for(int i=0;i<rr.getListOfProducts().getSpeciesReferenceArray().length;i++){
                    String sp = rr.getListOfProducts().getSpeciesReferenceArray(i).getSpecies();
                    //if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
                    species.add(sp);
                    String alias = CellDesignerToCytoscapeConverter.getSpeciesAliasInReaction(rr,sp,"product");
                    speciesAliases.add(alias);
                }
            if(rr.getListOfReactants()!=null)
                for(int i=0;i<rr.getListOfReactants().getSpeciesReferenceArray().length;i++){
                    String sp = rr.getListOfReactants().getSpeciesReferenceArray(i).getSpecies();
                    //if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
                    species.add(sp);
                    String alias = CellDesignerToCytoscapeConverter.getSpeciesAliasInReaction(rr,sp,"reactant");
                    speciesAliases.add(alias);
                }
            if(rr.getListOfModifiers()!=null)
                for(int i=0;i<rr.getListOfModifiers().getModifierSpeciesReferenceArray().length;i++){
                    String sp = rr.getListOfModifiers().getModifierSpeciesReferenceArray(i).getSpecies();
                    //if((species.indexOf(sp)<0)&&(degraded.indexOf(sp)<0)) defined = false;
                    species.add(sp);
                    String alias = CellDesignerToCytoscapeConverter.getSpeciesAliasInReaction(rr,sp,"modifier");
                    speciesAliases.add(alias);
                }
            }

            if((!defined)||(reactions.indexOf(rr.getId())<0)){
//                System.out.print(" "+rr.getId());
                for(int i=0;i<rr.getListOfProducts().getSpeciesReferenceArray().length;i++){
                    SpeciesReferenceDocument.SpeciesReference sr = rr.getListOfProducts().getSpeciesReferenceArray(i);
                    if(degraded.indexOf(sr.getSpecies())>=0)
                        degraded.remove(degraded.indexOf(sr.getSpecies()));
                }
                sbml.getSbml().getModel().getListOfReactions().removeReaction(k);
            }else{
                k++;
            }
        }
//        System.out.println();



        // Remove species aliases
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length){
            CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(k);
            String cspa = csp.getComplexSpeciesAlias();
            if((speciesAliases.indexOf(csp.getId())<0)&&(degraded.indexOf(csp.getSpecies())<0)&&(speciesAliases.indexOf(cspa))<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(k);
            }else{
                k++;
            }
        }
        // Remove complex species aliases
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length){
            CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(k);
            if(speciesAliases.indexOf(cspa.getId())<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().removeCelldesignerComplexSpeciesAlias(k);
            }else{
                k++;
            }
        }
        // Remove included species aliases
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length){
            CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(k);
            String scp = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
            if(species.indexOf(scp)<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().removeCelldesignerSpecies(k);
            }else{
                k++;
            }
        }
        // Remove species
        k = 0;
        while(k<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length){
            SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(k);
            if((species.indexOf(sp.getId())<0)&&(degraded.indexOf(sp.getId()))<0){
                sbml.getSbml().getModel().getListOfSpecies().removeSpecies(k);
            }else{
                k++;
            }
        }
        // Remove entity references

        for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
            SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
            String cl = "";
            if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
            	cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
            if(species.indexOf(sp.getId())>=0)if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null)
                    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null)
                    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()));
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null)
                    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()));
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical()!=null)
                    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical()));
                if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null)
                    entities.add(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()));
                if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("COMPLEX")){
                  for(int kk=0;kk<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;kk++){
                    CelldesignerSpeciesDocument.CelldesignerSpecies csp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(kk);
                    String compsp = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
                    if(compsp.equals(sp.getId())){
                      CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity cid = csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
                      if(cid.getCelldesignerProteinReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerProteinReference()));
                      if(cid.getCelldesignerGeneReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerGeneReference()));
                      if(cid.getCelldesignerAntisensernaReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerAntisensernaReference()));
                      if(cid.getCelldesignerHypothetical()!=null) entities.add(Utils.getValue(cid.getCelldesignerHypothetical()));
                      if(cid.getCelldesignerRnaReference()!=null) entities.add(Utils.getValue(cid.getCelldesignerRnaReference()));
                    }
                  }
                }
            }
        }

        k = 0;
 //       System.out.print("Removing entities : ");
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length){
            String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(k).getId();
            if(entities.indexOf(id)<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(k);
 //               System.out.print(" "+id);
            }else k++;
        }
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length){
            String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(k).getId();
            if(entities.indexOf(id)<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().removeCelldesignerAntisenseRNA(k);
//                System.out.print(" "+id);
            }else k++;
        }
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length){
            String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(k).getId();
            if(entities.indexOf(id)<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(k);
 //               System.out.print(" "+id);
            }else k++;
        }
        k = 0;
        if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
        while(k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length){
            String id = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(k).getId();
            if(entities.indexOf(id)<0){
                sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(k);
 //               System.out.print(" "+id);
            }else k++;
        }
//        System.out.println();

    }
    
    public static void assignColors(SbmlDocument sbml,GraphDocument gr){
    	HashMap colorTable = new HashMap();
    	for(int i=0;i<gr.getGraph().getNodeArray().length;i++){
    		GraphicNode node = gr.getGraph().getNodeArray(i);
    		if(node.getGraphics()!=null){
    			if(node.getGraphics().getFill()!=null){
    				colorTable.put(node.getId(), node.getGraphics().getFill());
    				String species = null;
    				if(Utils.getFirstAttribute(node, "CELLDESIGNER_SPECIES")!=null){
    					species = Utils.getFirstAttribute(node, "CELLDESIGNER_SPECIES").getValue();
    				}
    				if(species!=null)
    					colorTable.put(species, node.getGraphics().getFill());    				
    			}
    		}
    	}
    	assignColorsFromTableSpecies(sbml,colorTable);
    }
    
    public static void assignColorsFromTableSpecies(SbmlDocument sbml, HashMap colorTable){
    	for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
    		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
    		String species = alias.getSpecies();
    		String color = (String)colorTable.get(species);
    		if(color!=null){
    			color = Utils.replaceString(color, "#", "ff");
    			alias.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue(color);
    			alias.getCelldesignerUsualView().getCelldesignerPaint().setScheme(alias.getCelldesignerUsualView().getCelldesignerPaint().getScheme().forString("Gradation"));
    		}else{
    			alias.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ffffffff");
    		}
    	}
    	for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
    		CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias alias = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
    		String species = alias.getSpecies();
    		String color = (String)colorTable.get(species);
    		if(color!=null){
    			color = Utils.replaceString(color, "#", "ff");
    			alias.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue(color);
    			alias.getCelldesignerUsualView().getCelldesignerPaint().setScheme(alias.getCelldesignerUsualView().getCelldesignerPaint().getScheme().forString("Gradation"));
    		}else{
    			alias.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ffffffff");
    		}
    	}    	
    }
    
    public static void assignColorsFromTableReactions(SbmlDocument sbml, HashMap colorTable){
        
        /*System.out.println("The content of the colorTable:");
        Iterator<String> it = colorTable.keySet().iterator();
        while(it.hasNext()){
                String key = it.next();
                System.out.println(key+"\t-> "+colorTable.get(key));
        }*/
        
                for(int i=0;i<sbml.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
                        ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
                        String id = r.getId();
                String color = (String)colorTable.get(id);
                //System.out.println("Reaction "+id+" Color:"+color);
                if(color!=null){
                        color = Utils.replaceString(color, "#", "ff");
                        r.getAnnotation().getCelldesignerLine().setColor(color);
                }else{

                }
                        if(r.getAnnotation().getCelldesignerListOfModification()!=null)
                        for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
                                CelldesignerModificationDocument.CelldesignerModification cm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
                                String source_target = cm.getModifiers()+"%%"+id;
                                String color_edge = (String)colorTable.get(source_target);
                                //System.out.println("Edge "+source_target+" Color:"+color_edge);                              
                                if(color_edge!=null){
                                        color_edge = Utils.replaceString(color_edge, "#", "ff");
                                        cm.getCelldesignerLine().setColor(color_edge);
                                }
                        }
                }       
    }
               
}
