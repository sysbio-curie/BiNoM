package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.*;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.impl.*;
import org.sbml.x2001.ns.celldesigner.*;

import java.io.FileWriter;
import java.util.*;
import java.io.*;

public class mergingMaps {

	public static void main(String[] args) {
		try{
			
			
//			String prefix = "c:/datas/eregfrrb/test/test";
//			String prefix2 = "c:/datas/eregfrrb/test/test1";
			
			// file 1 and 2 names
			String prefix = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1";
			String prefix2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/a2";
			
			// load file 1 in a string
			String file = Utils.loadString(prefix+".xml");
			
			// add a prefix to all IDs in this file
			file = addPrefixToIds(file,"rb_");
			
			// write content in file1_id.xml
			FileWriter fw = new FileWriter(prefix+"_id.xml");
			fw.write(file);
			fw.close();
			
			// add '_id' to file1 prefix
			prefix = prefix+"_id";

			// load file1_id in SbmlDocument object
			SbmlDocument cd = CellDesigner.loadCellDesigner(prefix+".xml");
			System.out.println("Loaded.");
			
			// print some stats on species and reactions contained in file 1
			countAll(cd);
			
			// load file 2 as SbmlDocument object
			SbmlDocument cd2 = CellDesigner.loadCellDesigner(prefix2+".xml");
			
			// define and write maps of common things to file(s)
			produceCandidateMergeLists(cd,cd2,prefix+"_subs");
			
			mergeDiagrams(cd,cd2);
			CellDesigner.saveCellDesigner(cd, prefix+"_mrg.xml");
			Vector<String> subs = Utils.loadStringListFromFile(prefix+"_subs.txt");
			Vector<String> subsP = Utils.loadStringListFromFile(prefix+"_subsP.txt");			
			rewireDiagram(cd,subs,subsP);			
			CellDesigner.saveCellDesigner(cd, prefix+"_rew.xml");

			

			
			//System.out.println(Utils.replaceStringChar("Vector<String> proteinNames = new Vector<String>();", "Str", "*"));
			//System.exit(0);
			
			//String prefix = "c:/datas/eregfrrb/rbmap_mod_scl1";
			//String prefix2 = "c:/datas/eregfrrb/test1_id";
			
			//String prefix = "c:/datas/eregfrrb/a_id";
			//String prefix2 = "c:/datas/eregfrrb/b";
			
			//String prefix = "c:/datas/eregfrrb/rbpathway_090908";			
			//String prefix = "c:/datas/eregfrrb/rbmap_mod_id";
			//String prefix = "c:/datas/eregfrrb/er_egfr";
			
			//String prefix = "c:/datas/eregfrrb/testMerge";
			
			//String prefix = "c:/datas/eregfrrb/ER_EGFR_crosstalk_080808_cym310";
			//String prefix = "c:/datas/eregfrrb/mergedDraft_rew";
			//String prefix = "c:/datas/eregfrrb/mergedDraft_rew_mrn";
			//String prefix = "c:/datas/eregfrrb/testP";
			//String prefix = "c:/datas/eregfrrb/testEF_mrg";
			//String prefix2 = "c:/datas/eregfrrb/testEF1_id";
			//String prefix2 = "c:/datas/eregfrrb/rbmap_mod_id1";
			
			//String prefix = "c:/datas/eregfrrb/testr_spl";
			
			/*String file = Utils.loadString(prefix+".xml");
			file = addPrefixToIds(file,"rb_");
			FileWriter fw = new FileWriter(prefix+"_id.xml");
			fw.write(file);
			fw.close();
			System.exit(0);*/
			
			/*System.out.println("Loading "+prefix+"...");
			SbmlDocument cd = CellDesigner.loadCellDesigner(prefix+".xml");
			System.out.println("Loaded.");
			
			countAll(cd);*/
			
			//SbmlDocument cd2 = CellDesigner.loadCellDesigner(prefix2+".xml");
			//mergeDiagrams(cd,cd2);
			
			//addMRNAs(cd);
			//CellDesigner.saveCellDesigner(cd, prefix+"_spl.xml");
			//flipDiagram(cd);
			//scaleDiagram(cd,1.5f);
			
			//Vector<String> geneList = Utils.loadStringListFromFile("c:/datas/eregfrrb/commongenes.txt");
			//listSpeciesForGeneList(cd,geneList);
			
			//checkSpeciesAliases(cd);
			
			//Vector<String> subs = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsRBMap.txt");
			//Vector<String> subs = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTest1.txt");
			//Vector<String> subsP = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsRBMapP.txt");
			//subsP.clear();
			//Vector<String> subsP = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTable2.txt");
			
			//Vector<String> subs = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTable.txt");
			//Vector<String> subsP = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTableP.txt");
			//subsP.clear();
			
			//Vector<String> subs = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTest1.txt");
			//Vector<String> subsP = Utils.loadStringListFromFile("c:/datas/eregfrrb/subsTest1P.txt");

			
			//rewireDiagram(cd,subs,subsP);
			
			//checkAllTranslations(cd);
			
			//CellDesigner.saveCellDesigner(cd, prefix+"_rew.xml");
			//CellDesigner.saveCellDesigner(cd, prefix+"_mrg.xml");
			//CellDesigner.saveCellDesigner(cd, prefix+"_spl.xml");
			//CellDesigner.saveCellDesigner(cd, prefix+"_mod.xml");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void addMRNAs(SbmlDocument cd){

		Vector<String> feedbacks = new Vector<String>();
		int numberOfGenes = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
		
		Vector<String> proteinNames = new Vector<String>();
		Vector<String> proteinIds = new Vector<String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			proteinNames.add(Utils.getValue(prot.getName()).trim());
			proteinIds.add(prot.getId());
		}
		
		HashMap<String,String> protNameHUGO = new HashMap<String,String>();
		HashMap<String,String> protHUGOName = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String hugo = extractHUGO(Utils.getValue(protein.getCelldesignerNotes()));
			protNameHUGO.put(Utils.getValue(protein.getName()), hugo);
			protHUGOName.put(hugo, Utils.getValue(protein.getName()));
			if(hugo==null)
				hugo="";
			proteinNames.add(hugo);
			proteinIds.add(protein.getId());
		}
		HashMap<String,String> geneNameHUGO = new HashMap<String,String>();
		HashMap<String,String> geneHUGOName = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String hugo = extractHUGO(Utils.getValue(gene.getCelldesignerNotes())); 
			geneNameHUGO.put(gene.getName(), hugo);
			geneHUGOName.put(hugo, gene.getName());
		}		
		
		//for(int i=0;i<numberOfGenes;i++){
		int i=0;
		while(i<numberOfGenes){
			 CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			 String gid = gene.getId();
			 //System.out.println(gid);
			 String sid = null;
			 for(int j=0;j<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();j++){
				 SpeciesDocument.Species species = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(j);
				 if(Utils.getValue(species.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("GENE"))
					 if(Utils.getValue(species.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()).equals(gid)){
						 sid = species.getId();
					 }
			 }
			 System.out.println("Gene "+gene.getName()+", GID="+gid+" , SID="+sid);
			 
			 //finding the regulations of the gene
			 Vector<ReactionDocument.Reaction> regulations = new Vector<ReactionDocument.Reaction>();
			 for(int j=0;j<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();j++){
				 ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(j);
				 if(reaction.getListOfProducts()!=null){
					 for(int k=0;k<reaction.getListOfProducts().sizeOfSpeciesReferenceArray();k++){
						 SpeciesReferenceDocument.SpeciesReference spr = reaction.getListOfProducts().getSpeciesReferenceArray(k);
						 if(spr.getSpecies().equals(sid)){
							 //System.out.println(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()));
							 if(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("CATALYSIS")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("INHIBITION")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("UNKNOWN_TRANSITION")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("TRANSCRIPTIONAL_ACTIVATION")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("TRANSCRIPTIONAL_INHIBITION")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("TRANSLATIONAL_ACTIVATION")||
							    Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).equals("TRANSLATIONAL_INHIBITION")){
								    regulations.add(reaction);
								    System.out.println("Regulated by "+Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType())+" - "+reaction.getId());
							 }
						 }
					 }
				 }
			 }			 
			 
			 int numberOfAliases = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;
			 for(int j=0;j<numberOfAliases;j++){
				 CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(j);
		    	 if(cas.getSpecies().equals(sid)){
		    		 
					 XmlString xs = XmlString.Factory.newInstance();
		    		 
					 
					 float x = Float.parseFloat(cas.getCelldesignerBounds().getX());
					 float y = Float.parseFloat(cas.getCelldesignerBounds().getY());
					 float w = Float.parseFloat(cas.getCelldesignerBounds().getW());
					 float h = Float.parseFloat(cas.getCelldesignerBounds().getH());
					 cas.getCelldesignerBounds().setX(""+(x+0f*Float.parseFloat(cas.getCelldesignerBounds().getW())/2));					 
					 cas.getCelldesignerBounds().setW(""+(0.8f*w));

					 CelldesignerRNADocument.CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA();
					 rna.setId("r"+gid); rna.setName(gene.getName()); rna.setType("RNA");
					 if(gene.getCelldesignerNotes()!=null){
						 String comment = Utils.getValue(gene.getCelldesignerNotes()).trim();
						 String hugo =extractHUGO(comment);
						 if(hugo!=null){
							 rna.addNewCelldesignerNotes();
  							 xs.setStringValue("<&html><&body>HUGO:"+hugo+"<&/body><&/html>");
							 rna.getCelldesignerNotes().set(xs);
						 }
					 }
					 
					 
					 CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias rnaAlias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias();
					 CelldesignerBoundsDocument.CelldesignerBounds bounds = rnaAlias.addNewCelldesignerBounds();
					 bounds.setH("25");
					 bounds.setW(""+(w));
					 bounds.setY(""+(Float.parseFloat(cas.getCelldesignerBounds().getY())+0.5f*(h-25)));
					 bounds.setX(""+(x+1.5f*Float.parseFloat(cas.getCelldesignerBounds().getW())));
					 rnaAlias.setId("sr_"+gid);
					 rnaAlias.setSpecies("sp_"+gid);
					 xs.setStringValue("inactive");
					 rnaAlias.addNewCelldesignerActivity().set(xs);
					 rnaAlias.addNewCelldesignerView().setState(CelldesignerViewDocument.CelldesignerView.State.USUAL);
					 CelldesignerUsualViewDocument.CelldesignerUsualView uv = rnaAlias.addNewCelldesignerUsualView();
					 CelldesignerInnerPositionDocument.CelldesignerInnerPosition ip = uv.addNewCelldesignerInnerPosition();
					 ip.setX("0.0"); ip.setY("0.0");
					 CelldesignerSingleLineDocument.CelldesignerSingleLine sl = uv.addNewCelldesignerSingleLine();
					 sl.setWidth(CelldesignerSingleLineDocument.CelldesignerSingleLine.Width.X_1_0);
					 CelldesignerPaintDocument.CelldesignerPaint paint = uv.addNewCelldesignerPaint();
					 paint.setScheme(CelldesignerPaintDocument.CelldesignerPaint.Scheme.COLOR);
					 xs.setStringValue("ff66ff66");
					 paint.setColor(xs);
					 CelldesignerBoxSizeDocument.CelldesignerBoxSize cbs = uv.addNewCelldesignerBoxSize();
					 cbs.setWidth(bounds.getW()); cbs.setHeight(bounds.getH());
					 
					 SpeciesDocument.Species species = cd.getSbml().getModel().getListOfSpecies().addNewSpecies();
					 xs = XmlString.Factory.newInstance();
					 xs.setStringValue(gene.getName());
					 species.setName(xs);
					 species.setId("sp_"+gid);
					 species.setCompartment("default");
					 CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity csi = species.addNewAnnotation().addNewCelldesignerSpeciesIdentity();
					 xs = XmlString.Factory.newInstance();
					 xs.setStringValue("RNA");					 
					 csi.addNewCelldesignerClass().set(xs);
					 xs.setStringValue("r"+gid);
					 csi.addNewCelldesignerRnaReference().set(xs);
					 xs.setStringValue("inside");
					 species.getAnnotation().addNewCelldesignerPositionToCompartment().set(xs);
					 
					 // inserting transcription
					 ReactionDocument.Reaction transcription = cd.getSbml().getModel().getListOfReactions().addNewReaction();
					 transcription.setReversible("false");
					 transcription.setId("tr_"+gid);
					 SpeciesReferenceDocument.SpeciesReference srr = transcription.addNewListOfReactants().addNewSpeciesReference();
					 xs.setStringValue(cas.getId());  srr.setSpecies(sid); srr.addNewAnnotation().addNewCelldesignerAlias().set(xs);
					 SpeciesReferenceDocument.SpeciesReference srp = transcription.addNewListOfProducts().addNewSpeciesReference();
					 xs.setStringValue("sr_"+gid);  srp.setSpecies("sp_"+gid); srp.addNewAnnotation().addNewCelldesignerAlias().set(xs);
					 AnnotationDocument.Annotation tran = transcription.addNewAnnotation();
					 xs.setStringValue("TRANSCRIPTION");
					 tran.addNewCelldesignerReactionType().set(xs);
					 CelldesignerBaseReactantDocument.CelldesignerBaseReactant reactant = tran.addNewCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
					 xs.setStringValue(sid); reactant.setSpecies(xs); reactant.setAlias(cas.getId());
					 reactant.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.E);
					 CelldesignerBaseProductDocument.CelldesignerBaseProduct product = tran.addNewCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
					 xs.setStringValue("sp_"+gid); product.setSpecies(xs); product.setAlias("sr_"+gid);
					 product.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.W);
					 
					 // re-inserting regulation on transcription
					 transcription.addNewListOfModifiers();
					 for(int k=0;k<regulations.size();k++){
						 ModifierSpeciesReferenceDocument.ModifierSpeciesReference msr = transcription.getListOfModifiers().addNewModifierSpeciesReference();
					 	 msr.setSpecies(regulations.get(k).getListOfReactants().getSpeciesReferenceArray(0).getSpecies());
					 	 //xs.setStringValue(regulations.get(k).getListOfReactants().getSpeciesReferenceArray(0).getAnnotation().getCelldesignerAlias().toString()); 
					 	 msr.addNewAnnotation().addNewCelldesignerAlias().set(regulations.get(k).getListOfReactants().getSpeciesReferenceArray(0).getAnnotation().getCelldesignerAlias());
					 }
					 transcription.getAnnotation().addNewCelldesignerListOfModification();
					 for(int k=0;k<regulations.size();k++){
						 CelldesignerModificationDocument.CelldesignerModification mod = transcription.getAnnotation().getCelldesignerListOfModification().addNewCelldesignerModification();
						 CelldesignerBaseReactantDocument.CelldesignerBaseReactant react = regulations.get(k).getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(0);
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("CATALYSIS"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.CATALYSIS);
							 mod.setType("CATALYSIS");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("INHIBITION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.INHIBITION);
							 mod.setType("INHIBITION");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("TRANSCRIPTIONAL_ACTIVATION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.CATALYSIS);
							 mod.setType("TRANSCRIPTIONAL_ACTIVATION");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("TRANSCRIPTIONAL_INHIBITION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.INHIBITION);
							 mod.setType("TRANSCRIPTIONAL_INHIBITION");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("TRANSLATIONAL_ACTIVATION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.CATALYSIS);
							 mod.setType("TRANSLATIONAL_ACTIVATION");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("TRANSLATIONAL_INHIBITION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.INHIBITION);
							 mod.setType("TRANSLATIONAL_INHIBITION");
						 if(Utils.getValue(regulations.get(k).getAnnotation().getCelldesignerReactionType()).equals("UNKNOWN_TRANSITION"))
							 //mod.setType(CelldesignerModificationDocument.CelldesignerModification.Type.UNKNOWN_CATALYSIS);
							 mod.setType("UNKNOWN_TRANSITION");
						 mod.setModifiers(regulations.get(k).getListOfReactants().getSpeciesReferenceArray(0).getSpecies());
						 String modAlias = Utils.getValue(regulations.get(k).getListOfReactants().getSpeciesReferenceArray(0).getAnnotation().getCelldesignerAlias());
						 mod.setAliases(modAlias);
						 xs.setStringValue("-1,3"); mod.setTargetLineIndex(xs);
						 xs.setStringValue("0.99,0.01"); mod.setEditPoints(xs);
						 mod.addNewCelldesignerConnectScheme().setConnectPolicy("square");
						 /*System.out.println("Coord of target = "+x+","+y);
						 System.out.println("Coord of modifier = "+getAlias(cd,modAlias).getCelldesignerBounds().getX()+","+getAlias(cd,modAlias).getCelldesignerBounds().getY());
						 float xrp = x-Float.parseFloat(getAlias(cd,modAlias).getCelldesignerBounds().getX());
						 float yrp = y-Float.parseFloat(getAlias(cd,modAlias).getCelldesignerBounds().getY());
						 float xp = (float)(-1.5140e-3*xrp+(-3.0613e-3)*yrp);
						 float yp = (float)(0.4813e-3*xrp+3.4752e-3*yrp);
						 System.out.println("xrp="+xrp+" yrp="+yrp+" xp="+xp+" yp="+yp);
						 xs.setStringValue(xp+","+yp+" 0.9512077133589009,0.0664492424540093"); mod.setEditPoints(xs);*/
						 //System.out.println(Utils.getValue(regulations.get(k).getNotes()));
						 if(regulations.get(k).getNotes()!=null){						 
							 if(transcription.getNotes()==null) transcription.addNewNotes();
							 String s = Utils.getValue(transcription.getNotes()).trim();
							 xs.setStringValue(s+"\n"+Utils.getValue(regulations.get(k).getNotes()).trim());
							 transcription.getNotes().set(xs);
						 }
					 }
					 
					 // Now insert the translation 'reactions', if there are corresponding proteins
					 int ic = 1;
					 boolean proteinFound = false;
					 if(proteinNames.indexOf(gene.getName().trim())>=0) proteinFound = true;
					 String geneHugo = geneNameHUGO.get(gene.getName());
					 if(proteinNames.indexOf(geneHugo)>=0) proteinFound = true;
					 if(proteinFound){
						 System.out.println("Protein "+gene.getName()+" found.");
						 for(int k=0;k<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();k++){
							 SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(k);
							 if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
								 String name = CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, false, "", cd.getSbml());
								 String pid = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
								 boolean found = false;
								 if(proteinNames.indexOf(gene.getName())>=0)
									 if(pid.equals(proteinIds.get(proteinNames.indexOf(gene.getName())))) found = true;
								 geneHugo = geneNameHUGO.get(gene.getName());
								 if(proteinNames.indexOf(geneHugo)>=0)
									 if(pid.equals(proteinIds.get(proteinNames.indexOf(geneHugo)))) found = true;
								 if(found){
									 	if(name.indexOf("|")<0){
									 		 for(int n=0;n<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();n++){
									 			 CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spalias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(n);
									 			 if(spalias.getSpecies().equals(sp.getId())){
									 				 System.out.println("Connect to species "+name+" id="+sp.getId()+" alias="+spalias.getId());
									 				 ReactionDocument.Reaction translation = cd.getSbml().getModel().getListOfReactions().addNewReaction();
									 				 translation.setReversible("false");
									 				 translation.setId("trl_"+gid+ic);
									 				 srr = translation.addNewListOfReactants().addNewSpeciesReference();
									 				 xs.setStringValue("sr_"+gid);  srr.setSpecies("sp_"+gid); srr.addNewAnnotation().addNewCelldesignerAlias().set(xs);
									 				 srp = translation.addNewListOfProducts().addNewSpeciesReference();
									 				 xs.setStringValue(spalias.getId());  srp.setSpecies(sp.getId()); srp.addNewAnnotation().addNewCelldesignerAlias().set(xs);
									 				 tran = translation.addNewAnnotation();
									 				 xs.setStringValue("TRANSLATION");
									 				 tran.addNewCelldesignerReactionType().set(xs);
									 				 reactant = tran.addNewCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
									 				 xs.setStringValue("sp_"+gid); reactant.setSpecies(xs); reactant.setAlias("sr_"+gid);
									 				 reactant.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.S);
									 				 product = tran.addNewCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
									 				 xs.setStringValue(sp.getId()); product.setSpecies(xs); product.setAlias(spalias.getId());
									 				 product.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.N);
									 				 ic++;
									 			 }
									 		 }
									 		feedbacks.add("Feedback on "+gene.getName());
									 	}
								 }
							 }
						 }
					 }
					 
					 
					 
				 }
			 }
			 
			 //System.out.println(""+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray()+" "+i);
			 i++;
			 // Removing all incorrect regulations for the gene
			 for(int k=0;k<regulations.size();k++){
				 int count = 0; 
				 while(count<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray()){
					 if(cd.getSbml().getModel().getListOfReactions().getReactionArray(count).getId().equals(regulations.get(k).getId()))
						 { cd.getSbml().getModel().getListOfReactions().removeReaction(count); break; } 
					 else
						 count++;
				 }
			 }
		}
		
		for(i=0;i<feedbacks.size();i++){
			System.out.println(feedbacks.get(i));
		}

		
	}
	
	public static CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias getAlias(SbmlDocument cd, String id){
		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(a.getId().equals(id))
				alias = a;
		}
		return alias;
	}
	
	public static SpeciesDocument.Species getSpecies(SbmlDocument cd, String id){
		SpeciesDocument.Species sp = null;
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species a = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}
	

	public static CelldesignerProteinDocument.CelldesignerProtein getProtein(SbmlDocument cd, String id){
		CelldesignerProteinDocument.CelldesignerProtein sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}
	
	public static CelldesignerGeneDocument.CelldesignerGene getGene(SbmlDocument cd, String id){
		CelldesignerGeneDocument.CelldesignerGene sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}
	
	public static CelldesignerRNADocument.CelldesignerRNA getRNA(SbmlDocument cd, String id){
		CelldesignerRNADocument.CelldesignerRNA sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}
	
	public static CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias getRNAAliasBySpeciesId(SbmlDocument cd, String id){
		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(a.getSpecies().equals(id))
				sp = a;
		}
		return sp;
	}
	
	
	
	public static void flipDiagram(SbmlDocument cd){
		int modelHeight =  Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		int modelWidth =  Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX()); 
		
		Vector<String> included = new Vector<String>();
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			included.add(sp.getId());
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
			float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
			float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
			float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
			if(included.indexOf(alias.getSpecies())<0){
				alias.getCelldesignerBounds().setY(""+(modelHeight-posy-h));			
				alias.getCelldesignerBounds().setX(""+(modelWidth-posx-w));
			}else{
				alias.getCelldesignerBounds().setY(""+(modelHeight-posy-h-10));			
				alias.getCelldesignerBounds().setX(""+(modelWidth-posx-w));
			}
		}
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
			float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
			float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
			alias.getCelldesignerBounds().setY(""+(modelHeight-posy-h));
			float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
			alias.getCelldesignerBounds().setX(""+(modelWidth-posx-w));
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++){
			CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
			float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
			float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
			float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
			alias.getCelldesignerBounds().setY(""+(modelHeight-posy-h));
			float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
			alias.getCelldesignerBounds().setX(""+(modelWidth-posx-w));
			/*if(alias.getCelldesignerPoint()!=null){
				posy = Float.parseFloat(alias.getCelldesignerPoint().getY());
				alias.getCelldesignerBounds().setY(""+(modelHeight-posy));
				posx = Float.parseFloat(alias.getCelldesignerPoint().getX());
				alias.getCelldesignerBounds().setX(""+(modelWidth-posx));
			}*/
		}
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
				CelldesignerBaseReactantDocument.CelldesignerBaseReactant rlink = reaction.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
				if(rlink.getCelldesignerLinkAnchor()==null)
					System.out.println("Reaction with null LinkAnchor in Reactants="+reaction.getId());
				else{
				CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position = rlink.getCelldesignerLinkAnchor().getPosition();
				position = flipPosition(position);
				rlink.getCelldesignerLinkAnchor().setPosition(position);
				}
			}
			if(reaction.getAnnotation().getCelldesignerListOfReactantLinks()!=null)
			for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfReactantLinks().sizeOfCelldesignerReactantLinkArray();j++){
				CelldesignerReactantLinkDocument.CelldesignerReactantLink rlink = reaction.getAnnotation().getCelldesignerListOfReactantLinks().getCelldesignerReactantLinkArray(j);
				if(rlink.getCelldesignerLinkAnchor()==null)
					System.out.println("Reaction with null LinkAnchor in Reactants="+reaction.getId());
				else{
				CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position = rlink.getCelldesignerLinkAnchor().getPosition();
				position = flipPosition(position);
				rlink.getCelldesignerLinkAnchor().setPosition(position);
				}
			}
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
				CelldesignerBaseProductDocument.CelldesignerBaseProduct plink = reaction.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);				
				if(plink.getCelldesignerLinkAnchor()==null)
					System.out.println("Reaction with null LinkAnchor in Products="+reaction.getId());
				else{
				CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position = plink.getCelldesignerLinkAnchor().getPosition();
				position = flipPosition(position);
				plink.getCelldesignerLinkAnchor().setPosition(position);
				}
			}
			if(reaction.getAnnotation().getCelldesignerListOfProductLinks()!=null)
			for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfProductLinks().sizeOfCelldesignerProductLinkArray();j++){
				CelldesignerProductLinkDocument.CelldesignerProductLink rlink = reaction.getAnnotation().getCelldesignerListOfProductLinks().getCelldesignerProductLinkArray(j);
				if(rlink.getCelldesignerLinkAnchor()==null)
					System.out.println("Reaction with null LinkAnchor in Reactants="+reaction.getId());
				else{
				CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position = rlink.getCelldesignerLinkAnchor().getPosition();
				position = flipPosition(position);
				rlink.getCelldesignerLinkAnchor().setPosition(position);
				}
			}
			if(reaction.getAnnotation().getCelldesignerListOfModification()!=null)
			for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
				CelldesignerModificationDocument.CelldesignerModification plink = reaction.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
				//System.out.println(plink.toString());
				if(plink.getCelldesignerLinkTarget()!=null)
				if(plink.getCelldesignerLinkTarget().getCelldesignerLinkAnchor()==null)
					System.out.println("Reaction with null LinkAnchor in Products="+reaction.getId());
				else{
				CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position = plink.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().getPosition();
				position = flipPosition(position);
				plink.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().setPosition(position);
				}
			}
		}
	}
	
	public static CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum flipPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum position){
		CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.Enum ret = position;
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.N)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.S;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.S)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.N;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.E)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.W;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.W)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.E;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NE)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SW;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SW)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NE;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NW)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SE;
		else
		if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SE)
			ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NW;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NNE)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SSW;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SSW)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NNE;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NNW)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SSE;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.SSE)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.NNW;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.ENE)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.WSW;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.WSW)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.ENE;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.ESE)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.WNW;
		else
			if(position==CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.WNW)
				ret = CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.ESE;
		
		return ret;
	}
	
	public static void scaleDiagram(SbmlDocument cd, float factor){
		int modelHeight =  Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		int modelWidth =  Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+(int)(modelWidth*factor));
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(""+(int)(modelHeight*factor));

		HashMap<String,CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexSpeciesAliases = new HashMap<String,CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			complexSpeciesAliases.put(alias.getId(), alias);
		}

		Vector<String> included = new Vector<String>();
		Vector<Float> includedXrel = new Vector<Float>();		
		Vector<Float> includedYrel = new Vector<Float>();
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String calias = alias.getComplexSpeciesAlias();
			if(calias!=null){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cal = complexSpeciesAliases.get(calias);
				included.add(alias.getId());
				includedXrel.add(Float.parseFloat(alias.getCelldesignerBounds().getX())-Float.parseFloat(cal.getCelldesignerBounds().getX()));
				includedYrel.add(Float.parseFloat(alias.getCelldesignerBounds().getY())-Float.parseFloat(cal.getCelldesignerBounds().getY()));
			}
		}
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			//included.add(sp.getId());
		}
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
			float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
			float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
			alias.getCelldesignerBounds().setY(""+(posy*factor));
			float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
			alias.getCelldesignerBounds().setX(""+(posx*factor));
		}		
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String calias = alias.getComplexSpeciesAlias();
			if(calias!=null){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cal = complexSpeciesAliases.get(calias);
				float posx = Float.parseFloat(cal.getCelldesignerBounds().getX());				
				float posy = Float.parseFloat(cal.getCelldesignerBounds().getY());
				int k = included.indexOf(alias.getId());
				float relx = includedXrel.get(k);
				float rely = includedYrel.get(k);
				//System.out.println(relx+"\t"+rely);
				alias.getCelldesignerBounds().setX(""+(posx+relx));
				alias.getCelldesignerBounds().setY(""+(posy+rely));			
			}else{
				float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
				float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
				float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
				float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
				alias.getCelldesignerBounds().setX(""+(posx*factor));				
				alias.getCelldesignerBounds().setY(""+(posy*factor));			
			}
		}
		

		
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++){
			CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
			float w = Float.parseFloat(alias.getCelldesignerBounds().getW());;
			float h = Float.parseFloat(alias.getCelldesignerBounds().getH());;
			float posy = Float.parseFloat(alias.getCelldesignerBounds().getY());
			alias.getCelldesignerBounds().setY(""+(posy*factor));
			float posx = Float.parseFloat(alias.getCelldesignerBounds().getX());
			alias.getCelldesignerBounds().setX(""+(posx*factor));
			alias.getCelldesignerBounds().setW(""+(w*factor));
			alias.getCelldesignerBounds().setH(""+(h*factor));
		}
		
	}
	
	
	public static String extractHUGO(String name){
		String res = null;
		if(name!=null){
		StringTokenizer st = new StringTokenizer(name," >:;\r\n");
		while(st.hasMoreTokens()){
			String ss = st.nextToken();
			if(ss.toLowerCase().equals("hugo")){
			      if(st.hasMoreTokens()){
			        res = st.nextToken();
			}			
		}
		}
	   }
	return res;
	}
	
	public static void listSpeciesForGeneList(SbmlDocument cd, Vector<String>geneList){
		
		//System.out.println(geneList.indexOf("CCNB1"));
		
		Vector<CelldesignerProteinDocument.CelldesignerProtein> proteins = new Vector<CelldesignerProteinDocument.CelldesignerProtein>();
		Vector<CelldesignerGeneDocument.CelldesignerGene> genes = new Vector<CelldesignerGeneDocument.CelldesignerGene>();
		Vector<CelldesignerRNADocument.CelldesignerRNA> rnas = new Vector<CelldesignerRNADocument.CelldesignerRNA>();
		
		//for(int i=0;i<geneList.size();i++){
			//System.out.println("Gene "+geneList.get(i));
			for(int j=0;j<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();j++){
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(j);
				String sid = alias.getSpecies();
				SpeciesDocument.Species sp = getSpecies(cd,sid);
				if(sp!=null){
				String cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if(cl.equals("PROTEIN")){
					CelldesignerProteinDocument.CelldesignerProtein protein = getProtein(cd,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
					String found = null;
					String name = Utils.getValue(protein.getName());
					String hugoname = extractHUGO(Utils.getValue(protein.getCelldesignerNotes()));
					//System.out.println(name+"\t"+hugoname);
					//if(geneList.get(i).equals(name))
					if(geneList.indexOf(name)>=0)
						found = name;
					//if(geneList.get(i).equals(hugoname))
					if(geneList.indexOf(hugoname)>=0)
						found = hugoname;
					if(found!=null){
						System.out.println(alias.getId()+"\t"+sp.getId()+"\t"+sp.getCompartment()+"\t"+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd.getSbml()));
						if(proteins.indexOf(protein)<0) proteins.add(protein);
					}
				}
				if(cl.equals("GENE")){
					CelldesignerGeneDocument.CelldesignerGene gene = getGene(cd,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()));
					String found = null;
					String name = gene.getName();
					String hugoname = extractHUGO(Utils.getValue(gene.getCelldesignerNotes()));
					if(geneList.indexOf(name)>=0)
						found = name;
					if(geneList.indexOf(hugoname)>=0)
						found = hugoname;
					if(found!=null){
						System.out.println(alias.getId()+"\t"+sp.getId()+"\t"+sp.getCompartment()+"\t"+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd.getSbml()));
						if(genes.indexOf(gene)<0) genes.add(gene);
					}
				}
				if(cl.equals("RNA")){
					CelldesignerRNADocument.CelldesignerRNA rna = getRNA(cd,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()));
					String found = null;
					String name = rna.getName();
					String hugoname = extractHUGO(Utils.getValue(rna.getCelldesignerNotes()));
					if(geneList.indexOf(name)>=0)
						found = name;
					if(geneList.indexOf(hugoname)>=0)
						found = hugoname;
					if(found!=null){
						System.out.println(alias.getId()+"\t"+sp.getId()+"\t"+sp.getCompartment()+"\t"+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd.getSbml()));
						if(rnas.indexOf(rna)<0) rnas.add(rna);
					}
				}
				}
			}
			
		System.out.println("Proteins:");
		for(int i=0;i<proteins.size();i++)
			System.out.println(proteins.get(i).getId()+"\t"+Utils.getValue(proteins.get(i).getName()));
		System.out.println("Genes:");
		for(int i=0;i<genes.size();i++)
			System.out.println(genes.get(i).getId()+"\t"+genes.get(i).getName());
		System.out.println("RNAs:");
		for(int i=0;i<rnas.size();i++)
			System.out.println(rnas.get(i).getId()+"\t"+rnas.get(i).getName());
		
		//}
	}
	
	public static void rewireDiagram(SbmlDocument cd, Vector<String> subs, Vector<String> subsP){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		XmlString xs = XmlString.Factory.newInstance();
		HashMap<String,String> aliasMap = new HashMap<String,String>();
		HashMap<String,String> speciesMap = new HashMap<String,String>();
		Vector<String> subsAliases = new Vector<String>();
		Vector<String> subsSpecies = new Vector<String>();
		for(int i=0;i<subs.size();i++){
			String s = subs.get(i); 
			StringTokenizer st = new StringTokenizer(s,"\t");
			String ato = st.nextToken(); String sto = st.nextToken(); st.nextToken(); st.nextToken();
			String afrom = st.nextToken(); String sfrom = st.nextToken(); st.nextToken(); st.nextToken();
			aliasMap.put(afrom,ato);
			speciesMap.put(sfrom, sto);
			subsAliases.add(afrom);
			subsSpecies.add(sfrom);
		}
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		int numberOfReactions = 0;
		if(cd.getSbml().getModel().getListOfReactions()!=null){
		numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String reactionString = getReactionString(reaction,cd,false,true);
			//String reactionStringRealNames = getReactionString(reaction,cd,true,true);
			//if(i==(int)(0.02*i)*50)
			//	System.out.print(i+"/"+numberOfReactions+" ");
			for(int j=0;j<reaction.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null)
					reaction.getListOfReactants().getSpeciesReferenceArray(j).setSpecies(nspid);
				String al = Utils.getValue(reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
				CelldesignerBaseReactantDocument.CelldesignerBaseReactant cr = reaction.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
				String spid = Utils.getValue(cr.getSpecies());
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					xs.setStringValue(nspid);
					cr.setSpecies(xs);
				}
				String al = cr.getAlias();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAlias(nal);
				}
			}
			for(int j=0;j<reaction.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null)
					reaction.getListOfProducts().getSpeciesReferenceArray(j).setSpecies(nspid);
				String al = Utils.getValue(reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
				CelldesignerBaseProductDocument.CelldesignerBaseProduct cr = reaction.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
				String spid = Utils.getValue(cr.getSpecies());
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					xs.setStringValue(nspid);
					cr.setSpecies(xs);
				}
				String al = cr.getAlias();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAlias(nal);
				}
			}
			if(reaction.getListOfModifiers()!=null)
			for(int j=0;j<reaction.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
				String spid = reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).setSpecies(nspid);
					SpeciesDocument.Species nsp = getSpecies(cd,nspid);
				}
				String al = Utils.getValue(reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			if(reaction.getAnnotation().getCelldesignerListOfModification()!=null)
			for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
				CelldesignerModificationDocument.CelldesignerModification cr = reaction.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
				String spid = cr.getModifiers();
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					cr.setModifiers(nspid);
					cr.getCelldesignerLinkTarget().setSpecies(nspid);
				}
				String al = cr.getAliases();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAliases(nal);
					cr.getCelldesignerLinkTarget().setAlias(nal);
				}
			}			
			
			String reactionAfter = getReactionString(reaction,cd,false,true);
			if(!reactionString.equals(reactionAfter)){
			//	System.out.println("Reaction "+reaction.getId()+" rewired:");
			//else
				System.out.println("Reaction "+reaction.getId()+" rewired:");
				System.out.println("Before "+reactionString+" ("+getReactionString(reaction,cd,true,true)+")");
				System.out.println("After  "+reactionAfter+" ("+getReactionString(reaction,cd,true,true)+")");
				System.out.println("");
			}
		}}
		// Now remove species and aliases
		int i=0; int numberOfSpecies = cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray(); 
		while(i<numberOfSpecies){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(subsSpecies.indexOf(sp.getId())>=0){
				numberOfSpecies--;
				System.out.println("Species "+sp.getId()+" removed ("+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, false, "", cd.getSbml())+")");
				cd.getSbml().getModel().getListOfSpecies().removeSpecies(i);				
			}else{
				i++;
			}
		}
		i=0; int numberOfAliases = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray(); 
		while(i<numberOfAliases){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias al = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(subsAliases.indexOf(al.getId())>=0){
				System.out.println("Alias "+al.getId()+" (species "+al.getSpecies()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(i);
				numberOfAliases--;
			}else{
				i++;
			}
		}
		// Now find redundant reactions (same reactants, same products), make them unique and combine the modifiers
		System.out.println();
		if(cd.getSbml().getModel().getListOfReactions()!=null){
		i=0; numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
		
		HashMap<ReactionDocument.Reaction,String> reactionStringMap = new HashMap<ReactionDocument.Reaction,String>();
		//HashMap<ReactionDocument.Reaction,String> reactionStringRealNameMap = new HashMap<ReactionDocument.Reaction,String>();		
		for(i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			//Date t = new Date();
			String reactionString = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,false,false);
			//System.out.println("1) Spend "+((new Date()).getTime()-t.getTime()));
			//t = new Date();
			//String reactionStringRealName = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,true,true);
			//System.out.println("2) Spend "+((new Date()).getTime()-t.getTime()));
			reactionStringMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionString);
			//reactionStringRealNameMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionStringRealName);
			//if(i==(int)(0.02*i)*50)
			//	System.out.print(i+"/"+numberOfReactions+" ");
		}
		
		i=0;
		while(i<numberOfReactions){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			//if(i==(int)(0.02*i)*50)
			//	System.out.print(i+"/"+numberOfReactions+" ");
			boolean modified = false;
			//String reactionString = getReactionString(reaction,cd,false,false);
			//String reactionStringRealNames = getReactionString(reaction,cd,true,true);
			String reactionString = reactionStringMap.get(reaction);
			//String reactionStringRealNames = reactionStringRealNameMap.get(reaction);
			int j=i+1; 
			while(j<numberOfReactions){
				ReactionDocument.Reaction reactiontest = cd.getSbml().getModel().getListOfReactions().getReactionArray(j);
				//String reactionTestString = getReactionString(reactiontest,cd,false,false);
				//String reactionTestStringRealNames = getReactionString(reactiontest,cd,true,true);
				String reactionTestString = reactionStringMap.get(reactiontest);
				//String reactionTestStringRealNames = reactionStringRealNameMap.get(reactiontest);
				if(reactionString.equals(reactionTestString)){
					modified = true;
					System.out.println();
					System.out.println("Reactions "+reaction.getId()+" and "+reactiontest.getId()+" are found redundant:");
					System.out.println(reaction.getId()+": "+getReactionString(reactiontest,cd,true,true));
					System.out.println(reactiontest.getId()+": "+getReactionString(reactiontest,cd,true,true));
					if(reactiontest.getListOfModifiers()!=null)
					for(int k=0;k<reactiontest.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();k++){
						if(reaction.getListOfModifiers()==null) reaction.addNewListOfModifiers();
						ModifierSpeciesReferenceDocument.ModifierSpeciesReference mod = reaction.getListOfModifiers().addNewModifierSpeciesReference();
						mod.set(reactiontest.getListOfModifiers().getModifierSpeciesReferenceArray(k));
					}
					if(reactiontest.getAnnotation().getCelldesignerListOfModification()!=null)
					for(int k=0;k<reactiontest.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();k++){
						if(reaction.getAnnotation().getCelldesignerListOfModification()==null)
							reaction.getAnnotation().addNewCelldesignerListOfModification();
						CelldesignerModificationDocument.CelldesignerModification mod = reaction.getAnnotation().getCelldesignerListOfModification().addNewCelldesignerModification();
						mod.set(reactiontest.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k));
					}
					cd.getSbml().getModel().getListOfReactions().removeReaction(j);
					numberOfReactions--;
				}else
					j++;
			}
			i++;
			if(modified){
				System.out.println("Reaction "+reaction.getId()+" rewired:");
				System.out.println(getReactionString(reaction,cd,true,true));
			}
			//System.out.println();
		}}
		
		HashMap<String,String> idMap = new HashMap<String,String>();
		Vector<String> subsIds = new Vector<String>();
		for(i=0;i<subsP.size();i++){
			String s = subsP.get(i); 
			StringTokenizer st = new StringTokenizer(s,"\t");
			String ato = st.nextToken(); st.nextToken(); 
			String afrom = st.nextToken(); 
			idMap.put(afrom,ato);
			//System.out.println(afrom+"->"+ato);
			subsIds.add(afrom);
		}
		// Deal with redundant proteins, genes and rnas
		System.out.println();
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					xs.setStringValue(Utils.getValue(getProtein(cd,idMap.get(pr)).getName())); csp.setName(xs);
					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
					xs.setStringValue(getGene(cd,idMap.get(pr)).getName()); csp.setName(xs);
					System.out.println("Changed gene reference in "+csp.getId()+" from "+pr+" ("+getGene(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getGene(cd,idMap.get(pr)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
					xs.setStringValue(getRNA(cd,idMap.get(pr)).getName()); csp.setName(xs);
					System.out.println("Changed rna reference in "+csp.getId()+" from "+pr+" ("+getRNA(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getRNA(cd,idMap.get(pr)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}			
		}
		for(i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species csp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String pr = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true));
					System.out.println();
				}
			}			
		}
		i=0; int numberOfProteins = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
		while(i<numberOfProteins){
			CelldesignerProteinDocument.CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			if(subsIds.indexOf(protein.getId())>=0){
				String pto = idMap.get(protein.getId());
				CelldesignerProteinDocument.CelldesignerProtein proteinto = getProtein(cd,pto);
				if(protein.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(protein.getCelldesignerNotes()).trim();
					if(proteinto.getCelldesignerNotes()==null)
						proteinto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(proteinto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					proteinto.getCelldesignerNotes().set(xs);
				}
				if(protein.getCelldesignerListOfModificationResidues()!=null){
					if(proteinto.getCelldesignerListOfModificationResidues()==null)
						proteinto.addNewCelldesignerListOfModificationResidues();
				for(int j=0;j<protein.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++)
					proteinto.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue().set(protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j));
				}
				System.out.println("Protein "+protein.getId()+" ("+Utils.getValue(protein.getName())+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(i);
				numberOfProteins--;
			}else i++;
		}
		i=0; int numberOfGenes = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
		while(i<numberOfGenes){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			if(subsIds.indexOf(gene.getId())>=0){
				String pto = idMap.get(gene.getId());
				CelldesignerGeneDocument.CelldesignerGene geneto = getGene(cd,pto);
				if(geneto==null)
					System.out.println("Substitution not found for "+gene.getId()+" ("+pto+")");
				if(gene.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(gene.getCelldesignerNotes()).trim();
					if(geneto.getCelldesignerNotes()==null)
						geneto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(geneto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					geneto.getCelldesignerNotes().set(xs);
				}
				System.out.println("Gene "+gene.getId()+" ("+gene.getName()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(i);
				numberOfGenes--;
			}else i++;
		}
		i=0; int numberOfRNAs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
		while(i<numberOfRNAs){
			CelldesignerRNADocument.CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if(subsIds.indexOf(rna.getId())>=0){
				String pto = idMap.get(rna.getId());
				CelldesignerRNADocument.CelldesignerRNA rnato = getRNA(cd,pto);
				if(rna.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(rna.getCelldesignerNotes()).trim();
					if(rnato.getCelldesignerNotes()==null)
						rnato.addNewCelldesignerNotes();
					String commentto = Utils.getValue(rnato.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					rnato.getCelldesignerNotes().set(xs);
				}
				System.out.println("RNA "+rna.getId()+" ("+rna.getName()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(i);
				numberOfRNAs--;
				//i++;
			}else i++;
		}		
	}
	
	public static String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames, boolean addModifiers){
		  String reactionString = "";
		  String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		  ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
		  if(!addModifiers) lm = null;
		  
		  Vector<String> listReactants = new Vector<String>();
		  Vector<String> listProducts = new Vector<String>();
		  for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
			  listReactants.add(r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies());
		  for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++)
			  listProducts.add(r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies());
		  Collections.sort(listReactants);
		  Collections.sort(listProducts);
		  
		  for(int j=0;j<listReactants.size();j++){
		    String s = listReactants.get(j);
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
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
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
    		reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+=" - ";
		    }
		  }}
		  String reaction="-";
		  if(rtype.toLowerCase().indexOf("transcription")>=0)
			  reaction="--";
		  if(rtype.toLowerCase().indexOf("unknown")>=0)
			  reaction+="?";
		  if(rtype.toLowerCase().indexOf("inhibition")>=0)
			  reaction+="|";
		  else
			  reaction+=">";
		  if(rtype.toLowerCase().indexOf("transport")>=0)
			  reaction="-t->";
		  reaction = " "+reaction+" ";
		  reactionString +=reaction;
		  for(int j=0;j<listProducts.size();j++){
		    String s = listProducts.get(j);
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
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
	
	public static String addPrefixToIds(String text, String prefix){
		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		for(int i=0;i<ids.size();i++)
			System.out.print(ids.get(i)+" ");
		System.out.println("\n"+ids.size());
		for(int i=0;i<ids.size();i++)if(!ids.get(i).equals("default")){
			if(i==(int)(0.02f*i)*50)
				System.out.print(i+" ");
			text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
			text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
	    }
		System.out.println();
		return text;
	}
	
	public static void mergeDiagrams(SbmlDocument cd, SbmlDocument cd2){
		// Compartments
		for(int i=0;i<cd2.getSbml().getModel().getListOfCompartments().sizeOfCompartmentArray();i++)
			if(!cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i).getId().equals("default"))
				cd.getSbml().getModel().getListOfCompartments().addNewCompartment().set(cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().addNewCelldesignerCompartmentAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i));
		// Proteins, Genes, RNA
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().addNewCelldesignerGene().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i));
		// Species and Reactions
		for(int i=0;i<cd2.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++)
			cd.getSbml().getModel().getListOfSpecies().addNewSpecies().set(cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray(i));
		if(cd2.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++)
			cd.getSbml().getModel().getListOfReactions().addNewReaction().set(cd2.getSbml().getModel().getListOfReactions().getReactionArray(i));
		// Included, simple and complex Aliases
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i));
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i));
	}
	
	public static void checkAllTranslations(SbmlDocument cd){
		
		XmlString xs = XmlString.Factory.newInstance();
		
		Vector<String> proteinNames = new Vector<String>();
		Vector<String> proteinIds = new Vector<String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			proteinNames.add(Utils.getValue(prot.getName()).trim());
			proteinIds.add(prot.getId());
		}
		
		HashMap<String,String> protNameHUGO = new HashMap<String,String>();
		HashMap<String,String> protHUGOName = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String hugo = extractHUGO(Utils.getValue(protein.getCelldesignerNotes()));
			protNameHUGO.put(Utils.getValue(protein.getName()), hugo);
			protHUGOName.put(hugo, Utils.getValue(protein.getName()));
			if(hugo==null)
				hugo="";
			proteinNames.add(hugo);
			proteinIds.add(protein.getId());
		}
		HashMap<String,String> rnaNameHUGO = new HashMap<String,String>();
		HashMap<String,String> rnaHUGOName = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String hugo = extractHUGO(Utils.getValue(gene.getCelldesignerNotes())); 
			rnaNameHUGO.put(gene.getName(), hugo);
			rnaHUGOName.put(hugo, gene.getName());
		}		
		
		CellDesigner.entities = CellDesigner.getEntities(cd);
		HashMap<String,ReactionDocument.Reaction> reactionStringMap = new HashMap<String,ReactionDocument.Reaction>();
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			String reactionString = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,false,false);
			reactionStringMap.put(reactionString,cd.getSbml().getModel().getListOfReactions().getReactionArray(i));
		}
		
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp1 = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias sp1cal = getRNAAliasBySpeciesId(cd,sp1.getId());
			if(sp1cal==null){
				//System.out.println("Alias NOT FOUND for the species "+sp1.getId()+" ("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,sp1.getId(), true, true)+")");
			}
			else{
			String sp1alias = sp1cal.getId();
			if(sp1.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
				String rnaid = Utils.getValue(sp1.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
				CelldesignerRNADocument.CelldesignerRNA rna = getRNA(cd,rnaid);
				boolean proteinFound = false;
				if(proteinNames.indexOf(rna.getName().trim())>=0) proteinFound = true;
				String geneHugo = rnaNameHUGO.get(rna.getName());
				if(proteinNames.indexOf(geneHugo)>=0) proteinFound = true;
				if(proteinFound){

					 System.out.println("Protein "+rna.getName()+" found.");
					 for(int k=0;k<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();k++){
						 SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(k);
						 int ic=0;
						 if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
							 String name = CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, false, "", cd.getSbml());
							 String pid = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
							 boolean found = false;
							 if(proteinNames.indexOf(rna.getName())>=0)
								 if(pid.equals(proteinIds.get(proteinNames.indexOf(rna.getName())))) found = true;
							 geneHugo = rnaNameHUGO.get(rna.getName());
							 if(proteinNames.indexOf(geneHugo)>=0)
								 if(pid.equals(proteinIds.get(proteinNames.indexOf(geneHugo)))) found = true;
							 if(found){
								 	if(name.indexOf("|")<0){
								 		 for(int n=0;n<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();n++){
								 			 CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spalias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(n);
								 			 if(spalias.getSpecies().equals(sp.getId())){
								 				 String rstring = sp1.getId()+" -> "+sp.getId();
								 				 if(reactionStringMap.get(rstring)==null){
								 				 System.out.println("Connect to species "+name+" id="+sp.getId()+" alias="+spalias.getId());
								 				 ReactionDocument.Reaction translation = cd.getSbml().getModel().getListOfReactions().addNewReaction();
								 				 translation.setReversible("false");
								 				 translation.setId("trl_"+rnaid+ic);
								 				 SpeciesReferenceDocument.SpeciesReference srr = translation.addNewListOfReactants().addNewSpeciesReference();
								 				 xs.setStringValue(sp1alias);  srr.setSpecies(sp1.getId()); srr.addNewAnnotation().addNewCelldesignerAlias().set(xs);
								 				 SpeciesReferenceDocument.SpeciesReference srp = translation.addNewListOfProducts().addNewSpeciesReference();
								 				 xs.setStringValue(spalias.getId());  srp.setSpecies(sp.getId()); srp.addNewAnnotation().addNewCelldesignerAlias().set(xs);
								 				 AnnotationDocument.Annotation tran = translation.addNewAnnotation();
								 				 xs.setStringValue("TRANSLATION");
								 				 tran.addNewCelldesignerReactionType().set(xs);
								 				 CelldesignerBaseReactantDocument.CelldesignerBaseReactant reactant = tran.addNewCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
								 				 xs.setStringValue(sp1.getId()); reactant.setSpecies(xs); reactant.setAlias(sp1alias);
								 				 reactant.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.S);
								 				 CelldesignerBaseProductDocument.CelldesignerBaseProduct product = tran.addNewCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
								 				 xs.setStringValue(sp.getId()); product.setSpecies(xs); product.setAlias(spalias.getId());
								 				 product.addNewCelldesignerLinkAnchor().setPosition(CelldesignerLinkAnchorDocument.CelldesignerLinkAnchor.Position.N);
								 				 ic++;
								 				 }else
								 					 System.out.println("Translation reaction "+rstring+"("+reactionStringMap.get(rstring).getId()+")"+" already exists"+" ("+getReactionString(reactionStringMap.get(rstring),cd,true,true)+")");
								 			 }
								 		 }
								 	}
							 }
						 }
					 }
				 }					
					
				}
			}
		}
		
	}
	
	public static void checkSpeciesAliases(SbmlDocument cd){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		Vector<String> speciesIds = new Vector<String>(); 
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(speciesIds.indexOf(sp.getId())<0)
				speciesIds.add(sp.getId());
		}
		Vector<String> includedSpeciesIds = new Vector<String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			if(includedSpeciesIds.indexOf(csp.getId())<0)
				includedSpeciesIds.add(csp.getId());
		}
		Vector<String> speciesAliases = new Vector<String>(); 
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(speciesIds.indexOf(csa.getSpecies())<0)
				if(includedSpeciesIds.indexOf(csa.getSpecies())<0){
					System.out.println("For alias "+csa.getId()+" there is no species defined.");
				}
			speciesAliases.add(csa.getId());
		}
		Vector<String> complexSpeciesAliases = new Vector<String>(); 
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			if(speciesIds.indexOf(csa.getSpecies())<0)
				if(includedSpeciesIds.indexOf(csa.getSpecies())<0){
					System.out.println("For alias "+csa.getId()+" there is no species defined.");
				}
			complexSpeciesAliases.add(csa.getId());
		}
		
		int numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
		for(int i=0;i<numberOfReactions;i++){
			//cd.getSbml().getModel().getListOfReactions().removeReaction(0);
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			System.out.println(""+(i+1)+"/"+numberOfReactions+" Reaction "+reaction.getId());
			for(int j=0;j<reaction.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				checkSpecies(reaction,spid,speciesIds,includedSpeciesIds);
				String al = null;
				if(reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation()!=null)
					al = Utils.getValue(reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				else
					System.out.println("No annotation for reactant "+spid+"("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,spid, true, true)+") in reaction "+reaction.getId()+" "+getReactionString(reaction,cd,true,true));
				checkAliases(reaction,al,speciesAliases,complexSpeciesAliases);
			}
			for(int j=0;j<reaction.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				checkSpecies(reaction,spid,speciesIds,includedSpeciesIds);
				String al = null;
				if(reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation()!=null)
					al = Utils.getValue(reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				else
					System.out.println("No annotation for product "+spid+"("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,spid, true, true)+") in reaction "+reaction.getId()+" "+getReactionString(reaction,cd,true,true));
				checkAliases(reaction,al,speciesAliases,complexSpeciesAliases);
			}
			
		}
	}
	
	public static void checkSpecies(ReactionDocument.Reaction reaction, String id, Vector speciesIds, Vector includedSpeciesIds){
		if(speciesIds.indexOf(id)<0)
			if(includedSpeciesIds.indexOf(id)<0)
				System.out.println("In reaction "+reaction.getId()+" species "+id+" is not found anywhere.");
	}
	public static void checkAliases(ReactionDocument.Reaction reaction, String id, Vector speciesAliases, Vector complexSpeciesAliases){
		if(speciesAliases.indexOf(id)<0)
			if(complexSpeciesAliases.indexOf(id)<0)
				System.out.println("In reaction "+reaction.getId()+" alias "+id+" is not found anywhere.");
	}	
	
	public static void countAll(SbmlDocument cd){
		
		HashMap<String,Integer> rt = new HashMap<String,Integer>(); //God, what's this????
		
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			Integer num = rt.get(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).trim());
			if(num==null) 
				num = new Integer(0);
			num = new Integer(num+1);
			rt.put(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).trim(), num);
		}
		
		Set keys = rt.keySet(); 
		Iterator<String> it = keys.iterator();
		Vector<String> types = new Vector<String>();
		while(it.hasNext()) {
			types.add(it.next()); 
			Collections.sort(types);
		}
		
		for(int i=0;i<types.size();i++)
			System.out.println(types.get(i)+"\t"+rt.get(types.get(i)));
		
		rt = new HashMap<String,Integer>();
		
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			Integer num = rt.get("SPECIES_"+Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
			if(num==null) num = new Integer(0);
			num = new Integer(num+1);
			rt.put("SPECIES_"+Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()), num);
		}
		
		keys = rt.keySet(); 
		it = keys.iterator(); 
		types = new Vector<String>(); 
		while(it.hasNext()) { 
			types.add(it.next()); 
			Collections.sort(types);
		}
		
		for(int i=0;i<types.size();i++)
			System.out.println(types.get(i)+"\t"+rt.get(types.get(i)));
		
		System.out.println("PROTEIN\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray());
		System.out.println("GENE\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray());
		System.out.println("RNA\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray());
	}
	
	public static void produceCandidateMergeLists(SbmlDocument cd, SbmlDocument cd2, String prefix) throws Exception{
		
		// map of protein names to IDs for file1
		HashMap<String,String> proteinNames = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			proteinNames.put(Utils.getValue(p.getName()), p.getId());
			//System.out.println(Utils.getValue(p.getName())+"\t"+p.getId());
		}
		
		// get file1 entities
		CellDesigner.entities = CellDesigner.getEntities(cd);

		// map of species ID to species for file1 
		HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species.put(sp.getId(), sp);
			//System.out.println(sp.getId());
		}
		
		// map of unique species Cytoscape name to id (including aliases)
		HashMap<String,String> speciesIds = new HashMap<String,String>(); 
		HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>> speciesAliases = new HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>>(); // From species IDs to all corresponding aliases 
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		c2c.sbml = cd;
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spId = cas.getSpecies();
			String id = cas.getId();
			SpeciesDocument.Species sp = species.get(spId);
			if(sp!=null){
			String spName = c2c.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), spId, Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd);
			speciesIds.put(spName, spId);
			Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> vsa = speciesAliases.get(spId);
			if(vsa==null){
				vsa = new Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
			}
			vsa.add(cas);
			speciesAliases.put(spId, vsa);
			}
		}

		// find proteins sharing same name, save them to "P" file
		FileWriter fw = new FileWriter(prefix+"P.txt");
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String name = Utils.getValue(p.getName());
			if(proteinNames.containsKey(name)){
				//fw.write(p.getId()+"\t"+name+"\t"+proteinNames.get(name)+"\t"+name+"\n");
				fw.write(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name+"\n");
			}
		}
		fw.close();
		
		// map species ID -> species obj
		CellDesigner.entities = CellDesigner.getEntities(cd2);
		HashMap<String,SpeciesDocument.Species> species2 = new HashMap<String,SpeciesDocument.Species>();
		for(int i=0;i<cd2.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species2.put(sp.getId(), sp);
		}
		
		// cell designer to cytoscape converter
		c2c = new CellDesignerToCytoscapeConverter();
		c2c.sbml = cd2;
		
		// write a map of common species (?)
		fw = new FileWriter(prefix+".txt");
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spId = cas.getSpecies();
			String id = cas.getId();
			SpeciesDocument.Species sp = species2.get(spId);
			if(sp!=null){
			String spName = c2c.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), spId, Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd2);
			if(speciesIds.containsKey(spName)){
				String id1 = speciesIds.get(spName); 
				Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> vsa = speciesAliases.get(id1);
				if(vsa==null)
					System.out.println("Vector of aliases is not found for "+id1);
				fw.write(vsa.get(0).getId()+"\t"+id1+"\t"+species.get(id1).getCompartment()+"\t"+spName+"\t"+cas.getId()+"\t"+spId+"\t"+sp.getCompartment()+"\t"+spName+"\n");
				//fw.write(cas.getId()+"\t"+spId+"\t"+sp.getCompartment()+"\t"+spName+"\t"+vsa.get(0).getId()+"\t"+id1+"\t"+species.get(id1).getCompartment()+"\t"+spName+"\n");
			}}
		}
		fw.close();
	}
	

}
