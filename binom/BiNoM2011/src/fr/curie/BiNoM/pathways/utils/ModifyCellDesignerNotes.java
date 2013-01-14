package fr.curie.BiNoM.pathways.utils;

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.utils.*;

import java.io.*;
import java.util.*;

public class ModifyCellDesignerNotes {

	public SbmlDocument sbmlDoc = null;
	public String comments = null;
	
	public boolean allannotations = true;
	//public boolean insertTemplateForEntitiesReactions = true;
	//public boolean insertTemplateForSpecies = true;
	public boolean formatAnnotation	= true;
	
	public boolean guessIdentifiers = true;
	public boolean removeEmptySections = true;
	public boolean removeInvalidTags = true;
	public boolean moveNonannotatedTextToReferenceSection = true;
	
	
	public String tags[] = {"HUGO","HGNC","PUBMED","ENTREZ","UNIPROT","GENECARDS","PMID","PATHWAY","MODULE","LAYER","NAME","ALT_NAME"};

	
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
		String nameCD = "C:/Datas/NaviCell/maps/mphase_src/M-Phase2";
		//String nameCD = "C:/Datas/NaviCell/maps/egfr_src/master";
	    //String nameCD = "C:/Datas/NaviCell/maps/dnarepair_src/master";
		//String nameCD = "c:/datas/binomtest/test_master";
		String nameNotes = "c:/datas/basal/comm_temp.txt";
		mn.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		String s = mn.exportCellDesignerNotes();
		System.out.println(s);
		//mn.comments = Utils.loadString(nameCD+"_notes.txt");
		//mn.ModifyCellDesignerNotes();
		Utils.saveStringToFile(s, nameCD+"_notes.txt");
		CellDesigner.saveCellDesigner(mn.sbmlDoc, nameCD+"_notes.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void ModifyCellDesignerNotes() throws Exception{
		Vector<String> keys = new Vector<String>();
		Vector<String> noteAdds = new Vector<String>();
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
				  System.out.println(key);
				  System.out.println(note);
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
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
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
				String reqsections[] = {"Identifiers","Modules","References"};
				System.out.println("Processing "+p.getId()+"/"+Utils.getValue(p.getName()));
				if(formatAnnotation)
					annot = processAnnotations(annot,Utils.getValue(p.getName()),reqsections,guessIdentifiers);
				annotations.append(annot+"\n\n");
			}
		}
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+p.getName()+"\n");
				String reqsections[] = {"Identifiers","Modules","References"};
				System.out.println("Processing "+p.getId()+"/"+p.getName());
				if(formatAnnotation)
					annot = processAnnotations(annot,p.getName(),reqsections,guessIdentifiers);
				annotations.append(annot+"\n\n");
			}
		}		
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				annotations.append("### "+p.getId()+"\n");
				annotations.append("### "+p.getName()+"\n");
				String reqsections[] = {"Identifiers","Modules","References"};
				System.out.println("Processing "+p.getId()+"/"+p.getName());
				if(formatAnnotation)
					annot = processAnnotations(annot,p.getName(),reqsections,guessIdentifiers);
				annotations.append(annot+"\n\n");
			}
		}		
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
				annotations.append("### "+CellDesignerToCytoscapeConverter.getReactionString(r, sbmlDoc, true)+"\n");
				String reqsections[] = {"Identifiers","Modules","References"};
				System.out.println("Processing "+r.getId());
				if(formatAnnotation)
					annot = processAnnotations(annot,null,reqsections,false);
				annotations.append(annot+"\n\n");
			}
		}
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String annot = Utils.getValue(sp.getNotes());
			boolean annotationEmpty = false;
			if((annot==null)) { annotationEmpty = true; annot="";}
			else{
				if(annot.trim().equals("")) annotationEmpty = true;
			}
			if((!annotationEmpty)||(allannotations)){			
				String entName = null;
				String spName = null;
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
					entName = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),sbmlDoc);
					spName = CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entName, sp.getCompartment(), true, true, "", sbmlDoc);
				}else{
					entName = Utils.getValue(sp.getName());
					spName = entName;
				}
				
				annotations.append("### "+sp.getId()+"\n");
				annotations.append("### "+spName+"\n");
				String reqsections[] = {"Identifiers","Modules","References"};
				System.out.println("Processing "+sp.getId()+"/"+spName);
				boolean degraded = false;
				System.out.println(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
				if(sp.getAnnotation()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED"))
						degraded = true;
				if(formatAnnotation)if(!degraded)
					annot = processAnnotations(annot,null, reqsections, false);				
				annotations.append(annot+"\n\n");
			}
		}
		return annotations.toString();
	}
	
	public String processAnnotations(String annot, String entityName, String reqsections[], boolean _guessIdentifiers){
		if(annot==null)
			annot = "";
		String annp = annot;
		annp = Utils.cutFirstLastNonVisibleSymbols(annot);		
		// Extract sections
		Vector<AnnotationSection> secs = divideInSections(annp);
		//System.out.println(annp);
		//for(int i=0;i<secs.size();i++)
		//	System.out.println(secs.get(i));
		// Rename sections if needed
		for(int i=0;i<secs.size();i++){
			if(secs.get(i).name!=null){
				if(secs.get(i).name.equals("Generic"))
					secs.get(i).name = "Identifiers";
				if(secs.get(i).name.equals("Localisation"))
					secs.get(i).name = "Modules";
				if(secs.get(i).name.equals("Module"))
					secs.get(i).name = "Modules";
			}
			secs.get(i).content = Utils.cutFirstLastNonVisibleSymbols(secs.get(i).content);
		}
		// Make new sections if not present
		for(int i=0;i<reqsections.length;i++){
			String sectionToMake = reqsections[i];
			boolean found = false;
			for(int j=1;j<secs.size();j++)
				if(secs.get(j).name.equals(sectionToMake))
					found = true;
			if(!found){
				AnnotationSection as = new AnnotationSection();
				as.name = sectionToMake;
				secs.add(as);
			}	
		}
		// Extract information from the default section
		String nonannotated = secs.get(0).content;
		nonannotated = Utils.replaceString(nonannotated, "PUBMED:", "PMID:");
		nonannotated = Utils.replaceString(nonannotated, "PATHWAY:", "MODULE:");		
		String newnonannotated = new String(nonannotated);
		if(!moveNonannotatedTextToReferenceSection){
		int k=0;
		StringTokenizer st = new StringTokenizer(nonannotated,"\t\n ,;.");
		Vector<String> alltags = new Vector<String>();
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			for(int i=0;i<tags.length;i++)
				if(s.startsWith(tags[i]+":")){
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
		for(int i=0;i<alltags.size();i++){
			String s = alltags.get(i);
			if(s.startsWith("PUBMED:"))
				s = "PMID:"+s.substring(8,s.length());
			if(s.startsWith("PATHWAY:"))
				s = "MODULE:"+s.substring(8,s.length());

			if(s.startsWith("PMID:")){
				AnnotationSection sec = getSectionByName(secs,"References");
				sec.content+=s+" ";
			}else
			if(s.startsWith("MODULE:")){
				AnnotationSection sec = getSectionByName(secs,"Modules");
				sec.content+=s+" ";
			}else
			if(s.startsWith("LAYER:")){
				AnnotationSection sec = getSectionByName(secs,"Modules");
				sec.content+=s+" ";
			}else
			{
				AnnotationSection sec = getSectionByName(secs,"Identifiers");
				sec.content+=s+" ";
			}
		}}else{
			newnonannotated = "";
			AnnotationSection sec = getSectionByName(secs,"References");
			sec.content+=nonannotated;
		}
		//Process existing sections and eliminate undefined tags
		if(removeInvalidTags)
		for(int i=1;i<secs.size();i++){
			AnnotationSection sec = secs.get(i);
			String text = sec.content;
			StringTokenizer st = new StringTokenizer(text,"\t\n ,;.");
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
			fillIdentifiers(getSectionByName(secs,"Identifiers"),entityName);
			// if HUGO tag is not defined, mark with @@@ mark
			String text = this.getSectionByName(secs, "Identifiers").content;
			StringTokenizer st = new StringTokenizer(text,"\t\n .,;");
			boolean hugofound = false;
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				if(s.startsWith("HUGO:")){
					String value = s.substring(5, s.length());
					if((!value.equals(""))&&(!value.toLowerCase().equals("name")))
						hugofound = true;
				}
			}
			if(!hugofound)
				getSectionByName(secs, "Identifiers").content = "@@@ "+getSectionByName(secs, "Identifiers").content;
		}
		annp = "";
		for(int i=1;i<secs.size();i++){
			annp+=secs.get(i).toString();
		}
		if(!Utils.cutFirstLastNonVisibleSymbols(newnonannotated).equals(""))
			annp+="@@@\n"+newnonannotated;
		return annp;
	}
	
	public void fillIdentifiers(AnnotationSection identifiers, String entityName){
		String annotation = Utils.cutFirstLastNonVisibleSymbols(identifiers.content);
		String app_name = "";
		String prev_sym = "";
		String idents = "";
		
		boolean goToInternet = true;
		
		int numberOfLines = 1;
		for(int i=0;i<annotation.length();i++){
			if(annotation.charAt(i)=='\n') numberOfLines++;
		}
		if(numberOfLines>1)
			goToInternet = false;
		
		// now take the best HUGO name candidate
		String nameCandidate =  null;
		if(annotation.contains("HUGO:")){
			String text = annotation.substring(annotation.indexOf("HUGO:")+5, annotation.length());
			StringTokenizer st = new StringTokenizer(text,"\t, \n;.");
			nameCandidate = st.nextToken();
			if(nameCandidate.toLowerCase().equals("NAME")) 
				nameCandidate = null;
		}
		
		if(entityName==null) goToInternet = false;
		else{
		if(!entityName.endsWith("*"))
			nameCandidate = entityName;
		else{
			nameCandidate = entityName.substring(0, entityName.length()-1);
			if(nameCandidate.startsWith("h"))
				nameCandidate = nameCandidate.substring(1, nameCandidate.length());
		}}
		
		if(goToInternet)
		if(nameCandidate!=null){
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
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		identifiers.content = identifiers.content+app_name+prev_sym+idents;
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
		return secs;
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
	
	
}
