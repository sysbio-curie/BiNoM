package fr.curie.BiNoM.pathways.utils;

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import fr.curie.BiNoM.cytoscape.celldesigner.extractCellDesignerNotesDialog;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.*;

import java.io.*;
import java.util.*;

public class ModifyCellDesignerNotes {

	public SbmlDocument sbmlDoc = null;
	public String comments = null;
	public String moduleGMTFileName = null;
	GMTFile gmtFile = null;
	public boolean useHUGOIdsForModuleIdentification = true;
	public boolean overwriteModuleSection = true;
	public boolean useTypedNames = false;
	
	public boolean allannotations = true;
	//public boolean insertTemplateForEntitiesReactions = true;
	//public boolean insertTemplateForSpecies = true;
	public boolean formatAnnotation	= true;
	
	public boolean guessIdentifiers = false;
	public boolean removeEmptySections = true;
	public boolean removeInvalidTags = true;
	public boolean moveNonannotatedTextToReferenceSection = false;
	
	public boolean insertMapsTagBeforeModules = false;
	
	public boolean markMissingInformation = false;
	
	public boolean verbose = false;
	
	public boolean generateReadableNamesForReactionsAndSpecies = true;
	
	public boolean spreadReactionRefsToSpecies = false;
	
	/*
	 * Prefix to be used for ids if needed
	 */
	public String prefix = "";
	
	Vector<String> keys = new Vector<String>();
	Vector<String> noteAdds = new Vector<String>();
	HashMap<String,Vector<String>> species2ReactionIds = new HashMap<String,Vector<String>>(); 
	HashMap<String,String> reactionId2Annotation = new HashMap<String,String>();
	
	ModifyCellDesignerNotes synchronizingObject = null;
	
	public String tags[] = {"HUGO","HGNC","PUBMED","ENTREZ","UNIPROT","GENECARDS","PMID","PATHWAY","MODULE","LAYER","NAME","ALT_NAME","CHEBI","KEGGCOMPOUND","CAS"};

	
	/**
	 * @param args
	 */
	
	public class AnnotationSection{
		public String name = null;
		public String content = "";
		public String toString(){
			String s = "";
			//s+="SECTION: "+name+"\n";
			//s+=content+"\n";
			if(!removeEmptySections||(!content.equals(""))){
				s+=name+"_begin:\n";
				s+=content+"\n";
				s+=name+"_end\n";
			}
			return s;
		}
	}
	public static AnnotationSection getSectionByName(Vector<AnnotationSection> v, String name){
		AnnotationSection ann = null;
		for(int i=0;i<v.size();i++)
			if(v.get(i).name!=null)
				if(v.get(i).name.equals(name))
					ann = v.get(i);
		return ann;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
		//Vector<String> ids = Utils.guessProteinIdentifiers("Cdc2");
		//for(int i=0;i<ids.size();i++)
		//	System.out.println(ids.get(i));
		//System.exit(0);
			
		ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
		//mn.spreadReactionRefsToSpecies = true;
		//String nameCD = "C:/Datas/BinomTest/Annotation/acsn/mTOR_nucleus_zoom_3"; 
		//String nameCD = "C:/Datas/BinomTest/Annotation/acsn/test";
		//String nameCD = "C:/Datas/BinomTest/Annotation/acsn/test1_master";
		//String nameCD = "C:/Datas/Binomtest/annotation/gmt/apoptosis_v1_names";
		//String nameCD = "C:/Datas/Binomtest/annotation/apoptosis_v1_names";
		//String nameCD = "C:/Datas/acsn/repository/5_Release_xmls/dnarepair_v2_names";
		//String nameCD = "C:/Datas/NaviCell/test/merged/merged_master";
		//String nameCD = "C:/Datas/NaviCell/testNaviCellSuperMode/test/merged_testmap";
		//String nameCD = "C:/Datas/NaviCell/maps/egfr_src/master";
	    //String nameCD = "C:/Datas/NaviCell/maps/dnarepair_src/master";
		//String nameCD = "c:/datas/binomtest/test_master";
		//String nameNotes = "C:/Datas/Binomtest/annotation/comm_temp.txt";
		
		//extractCellDesignerNotesDialog dialog = new extractCellDesignerNotesDialog(); 
		//dialog.raise();
		
		String nameCD = "C:/Datas/BinomTest/Annotation/gmt1/pcd_master";
		//String nameCD = "C:/Datas/BinomTest/Annotation/test";
		
		mn.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		//mn.moduleGMTFileName = "C:/Datas/Binomtest/annotation/gmt/apoptosis_modules.gmt";
		mn.moduleGMTFileName = "C:/Datas/Binomtest/annotation/gmt1/pcd_modules_all.gmt";
		//mn.moduleGMTFileName = "C:/Datas/Binomtest/annotation/test.gmt";
		mn.overwriteModuleSection = true;
		mn.useTypedNames = true;
		mn.useHUGOIdsForModuleIdentification = false;
		//mn.guessIdentifiers = true;
		String s = mn.exportCellDesignerNotes();
		System.out.println(s);
		//mn.comments = Utils.loadString(nameCD+"_notes.txt");
		//mn.ModifyCellDesignerNotes();
		Utils.saveStringToFile(s, nameCD+"_notes.txt");
		mn.comments = s;
		mn.ModifyCellDesignerNotes();
		CellDesigner.saveCellDesigner(mn.sbmlDoc, nameCD+"_notes.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void automaticallyProcessNotes(){
		try{
			comments = exportCellDesignerNotes();
			ModifyCellDesignerNotes();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void ModifyCellDesignerNotes() throws Exception{
		//Vector<String> keys = new Vector<String>();
		//Vector<String> noteAdds = new Vector<String>();
		LineNumberReader lr = new LineNumberReader(new StringReader(comments));
		String s = null;
		String ks = null;
		String annot = null;
		
		while((s=lr.readLine())!=null){
			/*StringTokenizer st = new StringTokenizer(s,"\t");
			keys.add(st.nextToken());
			noteAdds.add(st.nextToken());*/
			if(s.startsWith("###")){
				if(annot!=null){
					annot = Utils.cutFirstLastNonVisibleSymbols(annot);
					noteAdds.add(annot);
				}
				ks = s.substring(4, s.length()).trim(); lr.readLine();
				keys.add(ks);
				annot = new String("");
			}else
			if(ks!=null){
				annot+=s+"\n";
			}
		}
		annot = Utils.cutFirstLastNonVisibleSymbols(annot);
		noteAdds.add(annot);
		
		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
				  //System.out.println(key);
				  //System.out.println(note);
			}
			/*for(int j=0;j<keys.size();j++){
				if(note.contains(keys.get(j))){
					note = note+" "+noteAdds.get(keys.indexOf(keys.get(j)));
					p.setCelldesignerNotes(null);
					  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
					  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
					  XmlString xs = XmlString.Factory.newInstance();
					  xs.setStringValue(note);
					  b.set(xs);
				}
			}*/
		}
		

		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
		}		

		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}

		}
		
		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}

		}		

		if(sbmlDoc.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r =  sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String key = r.getId();
			String note = "";
			if(r.getNotes()!=null)
				note = Utils.getValue(r.getNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				r.setNotes(null);
				  NotesDocument.Notes pnotes = r.addNewNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
		}

		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species r =  sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String key = r.getId();
			String note = "";
			if(r.getNotes()!=null)
				note = Utils.getValue(r.getNotes());
			if(keys.contains(key)){
				note = noteAdds.get(keys.indexOf(key));
				r.setNotes(null);
				  NotesDocument.Notes pnotes = r.addNewNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
		}
		
		}		
		
	
	
	public String exportCellDesignerNotes() throws Exception{
		StringBuffer annotations = new StringBuffer();
		CellDesigner.entities = CellDesigner.getEntities(sbmlDoc);
		CellDesignerToCytoscapeConverter.createSpeciesMap(sbmlDoc.getSbml());
		
		if(spreadReactionRefsToSpecies)
			createSpecies2ReactionIdsMap();

		if(moduleGMTFileName!=null){
			gmtFile = new GMTFile();
			gmtFile.load(moduleGMTFileName);
		}
		
		int numberOfProteins = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
		System.out.println("Processing proteins ("+numberOfProteins+")");
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			if(verbose)
				System.out.print(""+(i+1)+"/"+numberOfProteins+":");
			CelldesignerProteinDocument.CelldesignerProtein p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+Utils.getValue(p.getName())+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+p.getId()+"/"+Utils.getValue(p.getName()));
				if(formatAnnotation)
					annot = processAnnotations(annot,Utils.getValue(p.getName()),p.getId(),reqsections,guessIdentifiers);
				annotations.append(annot+"\n\n");
			}
		}
		int numberOfGenes = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
		System.out.println("Processing genes ("+numberOfGenes+")");		
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			if(verbose)
				System.out.print(""+(i+1)+"/"+numberOfGenes+":");
			CelldesignerGeneDocument.CelldesignerGene p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+p.getName()+" ### g"+p.getName()+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+p.getId()+"/"+p.getName());
				if(formatAnnotation){
					String name = p.getName();
					if(useTypedNames)
						name="g"+name;
					annot = processAnnotations(annot,name,p.getId(),reqsections,guessIdentifiers);
				}
				annotations.append(annot+"\n\n");
			}
		}		
		int numberOfRNAs = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
		System.out.println("Processing RNAs ("+numberOfRNAs+")");		
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			if(verbose)
				System.out.print(""+(i+1)+"/"+numberOfRNAs+":");
			CelldesignerRNADocument.CelldesignerRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+p.getName()+" ### r"+p.getName()+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+p.getId()+"/"+p.getName());
				if(formatAnnotation){
					String name = p.getName();
					if(useTypedNames)
						name="r"+name;
					annot = processAnnotations(annot,name,p.getId(),reqsections,guessIdentifiers);
				}
				annotations.append(annot+"\n\n");
			}
		}		
		int numberOfAntisenseRNAs = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();
		System.out.println("Processing asRNAs ("+numberOfAntisenseRNAs+")");		
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
			if(verbose)
				System.out.print(""+(i+1)+"/"+numberOfAntisenseRNAs+":");
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+p.getName()+" ### ar"+p.getName()+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+p.getId()+"/"+p.getName());
				if(formatAnnotation){
					String name = p.getName();
					if(useTypedNames)
						name="ar"+name;
					annot = processAnnotations(annot,name,p.getId(),reqsections,guessIdentifiers);
				}
				annotations.append(annot+"\n\n");
			}
		}		
		
		if(sbmlDoc.getSbml().getModel().getListOfReactions()!=null){
		System.out.println("Processing reactions ("+sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray()+")");
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String annot = Utils.getValue(r.getNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+r.getId()+"\n");
				if(generateReadableNamesForReactionsAndSpecies)
					annotations.append("### "+CellDesignerToCytoscapeConverter.getReactionString(r, sbmlDoc, true)+"\n");
				else
					annotations.append("### "+r.getId()+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+r.getId());
				if(formatAnnotation)
					annot = processAnnotations(annot,null,r.getId(),reqsections,false);
				annotations.append(annot+"\n\n");
				reactionId2Annotation.put(r.getId(), annot);
			}
		}}
		
		System.out.println("Processing species ("+sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray()+")");
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String annot = Utils.getValue(sp.getNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			
			StringBuffer nonannotated = new StringBuffer();
			Vector<AnnotationSection> secs = new Vector<AnnotationSection>(); 
			
			if((!annotationEmpty)||(allannotations)){			
				String entName = null;
				String spName = null;
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
					if(generateReadableNamesForReactionsAndSpecies){
						entName = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),sbmlDoc);
						spName = CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entName, sp.getCompartment(), true, true, "", sbmlDoc);
					}else
						spName = entName;
				}else{
					entName = Utils.getValue(sp.getName());
					spName = entName;
				}
				
				annotations.append("### "+sp.getId()+"\n");
				annotations.append("### "+spName+"\n");
				String reqsections[] = {"Identifiers","Maps_Modules","References"};
				if(verbose)
					System.out.println("Processing "+sp.getId()+"/"+spName);
				boolean degraded = false;
				//System.out.println(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
				if(sp.getAnnotation()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED"))
						degraded = true;
				if(formatAnnotation)if(!degraded)
					//annot = processAnnotations(annot,Utils.getValue(sp.getName()),sp.getId(),reqsections, false, nonannotated, secs);
					annot = processAnnotations(annot,spName,sp.getId(),reqsections, false, nonannotated, secs);
				if(spreadReactionRefsToSpecies){
					annot = spreadReactionReferencesToSpecies(sp.getId(),annot);
					secs = divideInSections(annot);
				}
				
			
			if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
			String cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			
			// Inserting complex name and re-name them
			if(cl.equals("COMPLEX")){
				/*Vector<AnnotationSection> secs = divideInSections(annot);
				AnnotationSection IdentifierSection = getSectionByName(secs,"Identifiers"); 
				if(IdentifierSection==null){
					IdentifierSection = new AnnotationSection();
					IdentifierSection.name = "Identifiers";
					secs.add(IdentifierSection);
				}*/
				if(getSectionByName(secs,"Identifiers")!=null){
				String identifiers = getSectionByName(secs,"Identifiers").content;
				//if(identifiers.trim().equals("")){
					entName = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),sbmlDoc);
					spName = CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entName, sp.getCompartment(), true, true, "", sbmlDoc);					
					Vector<String> names = BiographUtils.extractProteinNamesFromNodeName(spName);
					Collections.sort(names);
					String name = "";
					for(int k=0;k<names.size();k++) name+=names.get(k)+":"; if(name.length()>0) name = name.substring(0,name.length()-1);
					getSectionByName(secs,"Identifiers").content = "NAME:"+name;
					String oldname = Utils.getValue(sp.getName());
					if(!(oldname.startsWith("(")&&oldname.endsWith(")"))){
						XmlString xs = XmlString.Factory.newInstance();
						xs.setStringValue(name);
						sp.setName(xs);
					}
					annot = ""; 
					for(int k=1;k<secs.size();k++) 
						annot+=secs.get(k); 
					//annot+=secs.get(0); 
				//} // if the identifiers were empty
				}
			}// end complex processing
			
			
			}
			
			annotations.append(annot+"\n\n");
		}

			
		}
		return annotations.toString();
	}
	
	public String processAnnotations(String annot, String entityName, String id, String reqsections[], boolean _guessIdentifiers){
		StringBuffer bf = new StringBuffer();
		Vector<AnnotationSection> secs = new Vector<AnnotationSection>(); 
		return processAnnotations(annot, entityName, id, reqsections, _guessIdentifiers, bf, secs);
	}
	
	public String processAnnotations(String annot, String entityName, String id, String reqsections[], boolean _guessIdentifiers, StringBuffer _newnonannotated, Vector<AnnotationSection> _secs){
		if(annot==null)
			annot = "";
		String annp = annot;
		annp = Utils.cutFirstLastNonVisibleSymbols(annot);		
		// Extract sections
		Vector<AnnotationSection> secs1 = divideInSections(annp);
		//System.out.println(annp);
		//for(int i=0;i<secs.size();i++)
		//	System.out.println(secs.get(i));
		// Rename sections if needed
		for(int i=0;i<secs1.size();i++){
			if(secs1.get(i).name!=null){
				if(secs1.get(i).name.equals("Generic"))
					secs1.get(i).name = "Identifiers";
				if(secs1.get(i).name.equals("Localisation"))
					secs1.get(i).name = "Maps_Modules";
				if(secs1.get(i).name.equals("Maps_Module"))
					secs1.get(i).name = "Maps_Modules";
			}
			secs1.get(i).content = Utils.cutFirstLastNonVisibleSymbols(secs1.get(i).content);
		}
		// Make all sections in the corresponding order
		Vector<AnnotationSection> secs = new Vector<AnnotationSection>();
		AnnotationSection nosection = new AnnotationSection();
		nosection.name = null;
		nosection.content = secs1.get(0).content;
		secs.add(nosection);
		for(int i=0;i<reqsections.length;i++){
			String sectionToMake = reqsections[i];
				AnnotationSection as = new AnnotationSection();
				as.name = sectionToMake;
				secs.add(as);
				for(int j=1;j<secs1.size();j++)
					if(secs1.get(j).name.equals(as.name))
						as.content = secs1.get(j).content; 
		}
		// Extract information from the default section
		String nonannotated = secs.get(0).content;
		nonannotated = Utils.replaceString(nonannotated, "PUBMED:", "PMID:");
		nonannotated = Utils.replaceString(nonannotated, "PATHWAY:", "MODULE:");		
		String newnonannotated = new String(nonannotated);
		int k=0;
		StringTokenizer st = new StringTokenizer(nonannotated,"\t\n ,;.");
		Vector<String> alltags = new Vector<String>();
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			for(int i=0;i<tags.length;i++)
				if(s.startsWith(tags[i]+":")){
					if((!tags[i].equals("PMID"))||(!moveNonannotatedTextToReferenceSection)){
						if(!alltags.contains(s))
							alltags.add(s);
						newnonannotated = Utils.replaceString(newnonannotated, s+"\t", "");
						newnonannotated = Utils.replaceString(newnonannotated, s+"\n", "");
						newnonannotated = Utils.replaceString(newnonannotated, s+" ", "");					
						newnonannotated = Utils.replaceString(newnonannotated, s+",", "");		
						newnonannotated = Utils.replaceString(newnonannotated, s+";", "");	
						newnonannotated = Utils.replaceString(newnonannotated, s+".", "");		
						newnonannotated = Utils.replaceString(newnonannotated, s, "");
					}
				}
		}
		for(int i=0;i<alltags.size();i++){
			String s = alltags.get(i);
			if(s.startsWith("PUBMED:"))
				s = "PMID:"+s.substring(8,s.length());
			if(s.startsWith("PATHWAY:"))
				s = "MODULE:"+s.substring(8,s.length());

			if(s.startsWith("PMID:")){
				if(!moveNonannotatedTextToReferenceSection){
				AnnotationSection sec = getSectionByName(secs,"References");
				sec.content+=s+" ";
				}
			}else
			if(s.startsWith("MODULE:")){
				AnnotationSection sec = getSectionByName(secs,"Maps_Modules");
				sec.content+=s+" ";
			}else
			if(s.startsWith("LAYER:")){
				AnnotationSection sec = getSectionByName(secs,"Maps_Modules");
				sec.content+=s+" ";
			}else
			{
				AnnotationSection sec = getSectionByName(secs,"Identifiers");
				sec.content+=s+" ";
			}
		}
		if(moveNonannotatedTextToReferenceSection){	
			//newnonannotated = "";
			AnnotationSection sec = getSectionByName(secs,"References");
			sec.content+=newnonannotated;
			newnonannotated = "";
		}
		//Process existing sections and eliminate undefined tags
		if(removeInvalidTags)
		for(int i=1;i<secs.size();i++){
			AnnotationSection sec = secs.get(i);
			String text = sec.content;
			st = new StringTokenizer(text,"\t\n ,;.");
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				for(int j=0;j<tags.length;j++)
					if(s.startsWith(tags[j]+":")){
						String value = s.substring(tags[j].length()+1, s.length());
						if(value.toLowerCase().equals("")||value.toLowerCase().equals("name")){
						text = Utils.replaceString(text, s+"\t", "");
						text = Utils.replaceString(text, s+"\n", "");
						text = Utils.replaceString(text, s+" ", "");					
						text = Utils.replaceString(text, s+",", "");		
						text = Utils.replaceString(text, s+";", "");	
						text = Utils.replaceString(text, s+".", "");		
						text = Utils.replaceString(text, s, "");
						text = Utils.replaceString(text, "\n\n", "\n");
						text = Utils.replaceString(text, "\n\n", "\n");
						text = Utils.replaceString(text, "\n\n", "\n");
						sec.content = Utils.cutFirstLastNonVisibleSymbols(text);
						}
						}
			}
		}
		
		// Try to fill empty identifiers
		if(_guessIdentifiers){
			//if(entityName.equals("_beta_TrCP*"))
				fillIdentifiers(getSectionByName(secs,"Identifiers"),untypeName(entityName));
			// if HUGO tag is not defined, mark with @@@ mark
			String text = this.getSectionByName(secs, "Identifiers").content;
			st = new StringTokenizer(text,"\t\n .,;");
			boolean hugofound = false;
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				if(s.startsWith("HUGO:")){
					String value = s.substring(5, s.length());
					if((!value.equals(""))&&(!value.toLowerCase().equals("name")))
						hugofound = true;
				}
			}
			if(markMissingInformation)
			if(!hugofound)
				getSectionByName(secs, "Identifiers").content = "@@@ "+getSectionByName(secs, "Identifiers").content;
		}
		annp = "";
		
		// Re-assign modules names from gmtfile
		if(moduleGMTFileName!=null)
			addModuleNames(secs, entityName, id);
		
		if(insertMapsTagBeforeModules)
			insertMapsTagBeforeModuleTag(secs);
		
		if(synchronizingObject!=null){
			synchronizeSections(id, secs);
		}
		
		
		for(int i=1;i<secs.size();i++){
			annp+=secs.get(i).toString();
		}
		if(markMissingInformation)
			if(!Utils.cutFirstLastNonVisibleSymbols(newnonannotated).equals(""))
				annp+="@@@\n"+newnonannotated;
		_newnonannotated.append(newnonannotated);
		for(int i=0;i<secs.size();i++) _secs.add(secs.get(i));
		
		annp = guessPMIDIds(annp);
		//annp = Utils.replaceString(annp, "OR\n", "");
		
		
		
		return annp;
	}
	
	public void fillIdentifiers(AnnotationSection identifiers, String entityName){
		String annotation = Utils.cutFirstLastNonVisibleSymbols(identifiers.content);
		
		boolean goToInternet = true;
		
		int numberOfLines = 1;
		for(int i=0;i<annotation.length();i++){
			if(annotation.charAt(i)=='\n') numberOfLines++;
		}
		if(numberOfLines>1)
			goToInternet = false;
		
		
		// now take the best HUGO name candidate
		// now extract all HUGOs name candidates
		/*String nameCandidate =  null;
		if(annotation.contains("HUGO:")){
			String text = annotation.substring(annotation.indexOf("HUGO:")+5, annotation.length());
			StringTokenizer st = new StringTokenizer(text,"\t, \n;.");
			nameCandidate = st.nextToken();
			if(nameCandidate.toLowerCase().equals("NAME")) 
				nameCandidate = null;
		}else
		if(annotation.contains("HGNC:")){
			String text = annotation.substring(annotation.indexOf("HGNC:")+5, annotation.length());
			StringTokenizer st = new StringTokenizer(text,"\t, \n;.");
			nameCandidate = st.nextToken();
			if(nameCandidate.toLowerCase().equals("NAME")) 
				nameCandidate = null;
			try{
			if(nameCandidate!=null)
				nameCandidate = Utils.convertHGNC2HUGOthrowInternet(nameCandidate);
			}catch(Exception e){
				System.out.println("ERROR: Problem with converting "+nameCandidate+" to HUGO");
			}
		}else{
		if(entityName==null) goToInternet = false;
		else{
		if(!entityName.endsWith("*"))
			nameCandidate = entityName;
		else{
			nameCandidate = entityName.substring(0, entityName.length()-1);
			if(nameCandidate.startsWith("h"))
				nameCandidate = nameCandidate.substring(1, nameCandidate.length());
		}}
		}*/
		StringTokenizer st = new StringTokenizer(annotation,"\t\n ,;.");
		Vector<String> allhugos = new Vector<String>();
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			if(s.startsWith("HUGO:")){
				s = s.substring(5,s.length());
				if(!s.equals("NAME"))
				if(!allhugos.contains(s))
					allhugos.add(s);
			}
			if(s.startsWith("HGNC:")){
				s = s.substring(5,s.length());
				if(!s.equals("NAME")){				
				try{				
				s = Utils.convertHGNC2HUGOthrowInternet(s);
				}catch(Exception e){
					System.out.println("ERROR: Problem with converting "+s+" to HUGO");
				}}
				if(!allhugos.contains(s))
					allhugos.add(s);
			}
		}
		if(allhugos.size()==0){
			String name = null;
			if(entityName==null) goToInternet = false;
			else{
			if(!entityName.endsWith("*"))
				name = entityName;
			else{
				name = entityName.substring(0, entityName.length()-1);
				if(name.startsWith("h"))
					name = name.substring(1, name.length());
			}}
			if(name!=null)
				allhugos.add(name);
		}
		
		
		
		if(goToInternet)
		if(allhugos.size()>0){

			// comment if you want to conserve the previous content
			identifiers.content = "";

			for(int k=0;k<allhugos.size();k++){
				String nameCandidate = allhugos.get(k);

				String app_name = "";
				String prev_sym = "";
				String idents = "";				
				
			try{
				Vector<String> ids = Utils.guessProteinIdentifiers(nameCandidate);
				String key = "app_name";
				Vector<String> v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					app_name+=v.get(i).substring(key.length()+1,v.get(i).length())+"\n";
				key = "prev_sym";
				v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					prev_sym+=v.get(i).substring(key.length()+1,v.get(i).length())+"\n";
				key = "HUGO";
				v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					idents+=v.get(i)+" ";
				key = "HGNC";
				v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					idents+=v.get(i)+" ";
				key = "ENTREZ";
				v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					idents+=v.get(i)+" ";
				key = "UNIPROT";
				v = findStringInVectorBySubstring(ids, key+":");
				for(int i=0;i<v.size();i++)if(v.get(i).length()>key.length()+1)
					idents+=v.get(i)+" ";

				if(prev_sym.length()<3) 
					prev_sym = "";
				//identifiers.content = identifiers.content+app_name+prev_sym+idents+"\n";
				identifiers.content = identifiers.content+app_name+idents+"\n";
				System.out.println(identifiers.content);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		identifiers.content = Utils.cutFirstLastNonVisibleSymbols(identifiers.content);
			
		}
	}
		
	public static Vector<String> findStringInVectorBySubstring(Vector<String> v,String s){
		Vector<String> res = new Vector<String>();
		for(int i=0;i<v.size();i++)
			if(v.get(i).indexOf(s)>=0)
				res.add(v.get(i));
		return res;
	}
	
	public Vector<AnnotationSection> divideInSections(String annot){
		Vector<AnnotationSection> secs = new Vector<AnnotationSection>();
		StringTokenizer st = new StringTokenizer(annot,"\n\t ");
		AnnotationSection nosection = new AnnotationSection();
		nosection.name = null;
		secs.add(nosection);
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			if(s.endsWith("_begin:")){
				String name = s.substring(0, s.length()-7);
				boolean found = false;
				for(int i=1;i<secs.size();i++)
					if(secs.get(i).name.equals(name))
						found = true;
				if(!found){
					AnnotationSection ansec = new AnnotationSection();
					ansec.name = name;
					secs.add(ansec);
				}
			}
		}
		int k=0;
		StringBuffer buf = new StringBuffer(annot);
		AnnotationSection currentSection = secs.get(0);
		while(k<buf.length()){
			AnnotationSection sectionFound = null;
			for(int i=1;i<secs.size();i++){
				int sectionNameLength = secs.get(i).name.length()+7;
				if(k+sectionNameLength<buf.length()){
					if(buf.substring(k, k+sectionNameLength).equals(secs.get(i).name+"_begin:")){
						sectionFound = secs.get(i);
						k+=secs.get(i).name.length()+7;
					}
				}
			}
			if(sectionFound!=null) 
				currentSection = sectionFound;
			currentSection.content+=buf.charAt(k);
			k++;
			//for(int i=1;i<secs.size();i++){
			if(currentSection.name!=null){
				int sectionNameLength = currentSection.name.length()+4;
				if(k+sectionNameLength<=buf.length()){
					if(buf.substring(k, k+sectionNameLength).equals(currentSection.name+"_end")){
						currentSection = secs.get(0);
						k+=sectionNameLength;
					}
				}
			}
			//}
		}
		
		// ProcessSections: remove repetitive and empty lines
		for(int i=0;i<secs.size();i++){
			String text = secs.get(i).content;
			String newText = "";
			try{
			LineNumberReader lr = new LineNumberReader(new StringReader(text));
			String s = null;
			Vector<String> lines = new Vector<String>(); 
			Vector<String> hashlines = new Vector<String>();
			while((s=lr.readLine())!=null){
				if(!Utils.cutFirstLastNonVisibleSymbols(s).equals(""))
					if(Utils.cutFirstLastNonVisibleSymbols(s).length()>2){
						String hash = s.toLowerCase();
						hash = Utils.replaceString(hash, " ", "");
						hash = Utils.replaceString(hash, ",", "");
						hash = Utils.replaceString(hash, ";", "");
						hash = Utils.replaceString(hash, ".", "");
						hash = Utils.replaceString(hash, "\t", "");
						hash = Utils.replaceString(hash, "(", "");
						hash = Utils.replaceString(hash, ")", "");
						hash = Utils.replaceString(hash, "*", "");
						hash = Utils.replaceString(hash, "-", "");
						hash = Utils.replaceString(hash, "'", "");
						hash = Utils.replaceString(hash, ":", "");
						if(!hashlines.contains(hash)){
							lines.add(s);
							hashlines.add(hash);
						}
					}
			}
			for(int j=0;j<lines.size();j++)
				newText+=lines.get(j)+"\n";
			secs.get(i).content = newText;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return secs;
	}
	
	
	public static String guessPMIDIds(String annot){
		//StringBuffer res = new StringBuffer();
		StringTokenizer st = new StringTokenizer(annot,"., \t\n;");
		Vector<String> intnumbers = new Vector<String>();
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			//System.out.println(s);
			if(s.length()>6)
			if(Utils.isIntegerNumber(s)){
				if(!intnumbers.contains(s))
					intnumbers.add(s);
			}
		}
		for(int i=0;i<intnumbers.size();i++){
			annot = Utils.replaceString(annot, intnumbers.get(i), "*********************");
			annot = Utils.replaceString(annot, "*********************", "PMID:"+intnumbers.get(i));
		}
		return annot;
	}
	
	public void addModuleNames(Vector<AnnotationSection> secs, String entityName, String id){
		AnnotationSection identifiers = getSectionByName(secs, "Identifiers");
		AnnotationSection moduleSection = getSectionByName(secs, "Maps_Modules");
		Vector<String> hugos = Utils.getTagValues(identifiers.content, "HUGO");
		Vector<String> moduleNames = gmtFile.getListOfSets(entityName);
		//if(entityName.contains("ATG3:CFLAR"))
		//	System.out.println();
		// Fix for the case when a complex species without compartment is indicated in gmt
		if((entityName!=null)&&(moduleNames.size()==0)&&entityName.contains("@")&&entityName.contains(":")){
			StringTokenizer st = new StringTokenizer(entityName,"@");
			moduleNames = gmtFile.getListOfSets(st.nextToken());
		}
		Vector<String> moduleNamesbyId = gmtFile.getListOfSets(id);
		if(moduleNamesbyId!=null)
			for(int i=0;i<moduleNamesbyId.size();i++)
				moduleNames.add(moduleNamesbyId.get(i));
		if(useHUGOIdsForModuleIdentification){
			for(int i=0;i<hugos.size();i++){
				Vector<String> mns = gmtFile.getListOfSets(hugos.get(i));
				for(int j=0;j<mns.size();j++)
				if(!moduleNames.contains(mns.get(j)))
					moduleNames.add(mns.get(j));
			}
		}
		if(overwriteModuleSection)
			moduleSection.content = "";
		else{
			if(!moduleSection.content.endsWith("\n"))
				moduleSection.content+="\n";
		}
		for(int i=0;i<moduleNames.size();i++) 
			moduleSection.content += "MODULE:"+moduleNames.get(i)+"\n";
		if(moduleSection.content.length()>0)
			moduleSection.content = moduleSection.content.substring(0, moduleSection.content.length()-1);
	}

	
	public void insertMapsTagBeforeModuleTag(Vector<AnnotationSection> secs){
		AnnotationSection moduleSection = getSectionByName(secs, "Maps_Modules");
		Vector<String> modules = Utils.getTagValues(moduleSection.content, "MODULE");
		
		Vector<String> existingModules = new Vector<String>();
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray();i++){
			CelldesignerLayerDocument.CelldesignerLayer layer = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i);
			String layerName = Utils.getValue(layer.getName());
			if(!existingModules.contains(layerName))
				existingModules.add(layerName);
		}
		
		String id = sbmlDoc.getSbml().getModel().getId();
		moduleSection.content = "";
		for(int i=0;i<modules.size();i++)
			if(existingModules.contains(modules.get(i)))
				moduleSection.content+="MAP:"+id+" / MODULE:"+modules.get(i)+"\n";
		if(moduleSection.content.length()>0)
			moduleSection.content = moduleSection.content.substring(0, moduleSection.content.length()-1);
	}
	
	public void synchronizeAnnotations(ModifyCellDesignerNotes global) throws Exception{
		synchronizingObject = global;
		comments = exportCellDesignerNotes();
		ModifyCellDesignerNotes();
	}
	
	public void synchronizeSections(String id, Vector<AnnotationSection> secs){
		int k = synchronizingObject.keys.indexOf(id);
		if(k!=-1){
			String global_note = synchronizingObject.noteAdds.get(k);
			Vector<AnnotationSection> global_sections = synchronizingObject.divideInSections(global_note);
			if(getSectionByName(global_sections,"Maps_Modules")!=null){
				String global_modules = getSectionByName(global_sections,"Maps_Modules").content;
				AnnotationSection modules = getSectionByName(secs,"Maps_Modules");
				if(modules!=null)
					modules.content = Utils.cutFirstLastNonVisibleSymbols(global_modules);
			}
		}else{
			System.out.println("WARNING: "+id+" is not found in the global map!");
		}
	}
	
	public void createSpecies2ReactionIdsMap(){
		boolean temp = CellDesignerToCytoscapeConverter.verbose;
		CellDesignerToCytoscapeConverter.verbose = false;
		Graph graph = XGMML.convertXGMMLToGraph(CellDesignerToCytoscapeConverter.getXGMMLGraph("", sbmlDoc.getSbml()));
		CellDesignerToCytoscapeConverter.verbose = temp;
		graph.calcNodesInOut();
		HashMap<String, String> id2name = new HashMap<String, String>();
		HashMap<String, String> name2id = new HashMap<String, String>();
		Vector<String> spids = new Vector<String>();
		for(Node n: graph.Nodes){
			if(n.getFirstAttributeValue("CELLDESIGNER_SPECIES")!=null){
				String spid = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
				if(!spid.equals("")){
					id2name.put(spid, n.Id);
					name2id.put(n.Id, spid);
					spids.add(spid);
				}
			}
			if(n.getFirstAttributeValue("CELLDESIGNER_REACTION")!=null){
				String rid = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
				if(!rid.equals("")){
					id2name.put(rid, n.Id);
					name2id.put(n.Id, rid);
				}
			}
		}
		
		for (String spid : spids) {
			Node n = graph.getNode(id2name.get(spid));
			Vector<String> reids = new Vector<String>();
			if (n != null) {
				for(Edge e: n.incomingEdges) {
					String reid = name2id.get(e.Node1.Id);
					if(!reids.contains(reid))
						reids.add(reid);
				}
				for(Edge e: n.outcomingEdges) {
					String reid = name2id.get(e.Node2.Id);
					if(!reids.contains(reid))
						reids.add(reid);
				}
			species2ReactionIds.put(spid, reids);
			}
		}
	}
	
	public String spreadReactionReferencesToSpecies(String id, String annot){
		String newAnnot = "";
		Vector<AnnotationSection> secs = divideInSections(annot);
		if(secs.size()==0)
			secs.add(new AnnotationSection());
		AnnotationSection References = getSectionByName(secs,"References");
		if(References==null){
			References = new AnnotationSection();
			References.content = "";
			References.name = "References";
			secs.add(References);
		}
		References.content = Utils.cutFirstLastNonVisibleSymbols(References.content);
		Vector<String> reactions = species2ReactionIds.get(id);
		if(reactions!=null){
			for(String rid: reactions){
				String reactionAnnotation = reactionId2Annotation.get(rid);
				if(reactionAnnotation!=null){
					Vector<AnnotationSection> reactionSecs = divideInSections(reactionAnnotation);
					AnnotationSection ReactionReference = getSectionByName(reactionSecs,"References");
					if(ReactionReference!=null){
						String refText = Utils.cutFirstLastNonVisibleSymbols(ReactionReference.content);
						if(!References.content.contains(refText))
						if(!refText.equals("")){
							if(!References.content.equals(""))
								References.content+="\n";
							String mid = sbmlDoc.getSbml().getModel().getId();
							if(refText.contains("\n"))
								References.content+=prefix+rid+"(MAP:"+mid+"):\n"+refText;
								//References.content+="\n"+refText;
							else
								References.content+=prefix+rid+":(MAP:"+mid+") "+refText;
								//References.content+=refText;
						
						}
					}
				}
			}
		}
		for(int i=1;i<secs.size();i++)
			newAnnot+=secs.get(i).toString();
		newAnnot+=secs.get(0).toString();
		// make one more processing for removing possible repetitions
		secs = divideInSections(newAnnot);
		newAnnot = "";
		for(int i=1;i<secs.size();i++)
			newAnnot+=secs.get(i).toString();
		newAnnot+=secs.get(0).toString();
		return newAnnot;
	}
	
	
	
	
	
	
	/*
	private void importAnnotations(SbmlDocument cd, String annotations) throws Exception{
		CellDesigner.entities = CellDesigner.getEntities(cd);
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		updateStandardNames();
		LineNumberReader lr = new LineNumberReader(new StringReader(annotations));
		String s = null;
		String currentEntity = null;
		String currentAnnotation = null;
		while((s=lr.readLine())!=null){
			if(s.startsWith("###")){
				if(currentEntity!=null){
					// import annotation text for a current entity
					CellDesigner.entities.get(currentEntity);
				}
				StringTokenizer st = new StringTokenizer(s,"\t");
				st.nextToken(); currentEntity = st.nextToken(); currentAnnotation = "";
				lr.readLine();
			}else{
				currentAnnotation+=s+"\n";
			}
		}
	}
	*/
	
	

	public static String untypeName(String name){
		Vector<String> v = BiographUtils.extractProteinNamesFromNodeName(name);
		return v.get(0);
	}
}
