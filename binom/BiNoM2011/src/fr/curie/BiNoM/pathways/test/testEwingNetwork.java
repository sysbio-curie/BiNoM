package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.converters.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import org.sbml.x2001.ns.celldesigner.*;

import vdaoengine.data.*;

public class testEwingNetwork {
	
	public static BioPAX biopax = null;
	public static HashMap nameEntity = new HashMap();
	public static HashMap nameParticipant = new HashMap();
	public static HashMap nameFeatures = new HashMap();
	public static HashMap nameReactions = new HashMap();
	public static HashMap families = new HashMap();
	public static HashMap phenotypes = new HashMap();	
	public static Vector molecules = new Vector();
	public static Vector tissueTypes = new Vector();
	public static HashMap publications = new HashMap();
	
	public static String pub1 = "";
	public static String pub2 = "";
	public static String ChemType = "";
	public static String Delay = "";
	public static String Confidence = "";
	public static String Comments = "";
	public static String Tissue = "";
	

	public static void main(String[] args) {
		try{

		/*String prefix = "c:/datas/ewing/annotlink";
		BioPAXToCytoscapeConverter conv = new BioPAXToCytoscapeConverter();
		BioPAXToCytoscapeConverter.Graph gr = conv.convert(conv.REACTION_NETWORK_CONVERSION, prefix+".owl", (new BioPAXToCytoscapeConverter.Option()));
        XGMML.saveToXMGML(gr.graphDocument,prefix+".xgmml");
		System.exit(0);*/

		System.out.println(biopax.namespaceString);
			
		VDataTable vt = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("c:/datas/ewing/annotlinks.xls", true, "\t");
		
		biopax = new BioPAX();
		
		for(int i=0;i<vt.rowCount;i++){
			String tt = vt.stringTable[i][vt.fieldNumByName("Tissue")];
			tt = tt.trim();
			if(tissueTypes.indexOf(tt)<0)
				tissueTypes.add(tt);
			
		String link = vt.stringTable[i][vt.fieldNumByName("Link")];
		String inftype = "unknown";
		String left = "";
		String right = "";
		if(link.indexOf("->")>=0){
			inftype = "activation";
			left = link.substring(0, link.indexOf("->"));
			right = link.substring(link.indexOf("->")+2,link.length());
		}
		if(link.indexOf("-|")>=0){
			inftype = "inhibition";
			left = link.substring(0, link.indexOf("-|"));
			right = link.substring(link.indexOf("-|")+2,link.length());
		}
		left = left.trim();
		right = right.trim();
		if(molecules.indexOf(left)<0) molecules.add(left);
		if(molecules.indexOf(right)<0) molecules.add(right);
		   addMolecule(left);
		   addMolecule(right);
		}
		//for(int i=0;i<tissueTypes.size();i++)
		//	System.out.println((String)tissueTypes.get(i));
		Collections.sort(molecules);
		for(int i=0;i<molecules.size();i++)
			System.out.println((String)molecules.get(i));
		
		// Now add families and phenotypes
		for(int i=0;i<vt.rowCount;i++){
		String link = vt.stringTable[i][vt.fieldNumByName("Link")];
		String left = "";
		String right = "";
		if(link.indexOf("->")>=0){
			left = link.substring(0, link.indexOf("->"));
			right = link.substring(link.indexOf("->")+2,link.length());
		}
		if(link.indexOf("-|")>=0){
			left = link.substring(0, link.indexOf("-|"));
			right = link.substring(link.indexOf("-|")+2,link.length());
		}
		left = left.trim();
		right = right.trim();
		   addFamily(left);
		   addFamily(right);
		   addPhenotype(left);
		   addPhenotype(right);
		}
		
		Iterator it = families.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector members = (Vector)families.get(key);
			for(int i=0;i<members.size();i++)
				System.out.print((String)members.get(i)+"\t");
			System.out.println();
		}
		
		// Now add reactions
		for(int i=0;i<vt.rowCount;i++){
			String tt = vt.stringTable[i][vt.fieldNumByName("Tissue")];
			tt = tt.trim();
			if(tissueTypes.indexOf(tt)<0)
				tissueTypes.add(tt);
		    String link = vt.stringTable[i][vt.fieldNumByName("Link")];
		    pub1 = vt.stringTable[i][vt.fieldNumByName("ReviewRef")];
		    pub2 = vt.stringTable[i][vt.fieldNumByName("ExperimentRef")];
		    ChemType = vt.stringTable[i][vt.fieldNumByName("ChemType")];
		    Delay = vt.stringTable[i][vt.fieldNumByName("Delay")];
		    Confidence = vt.stringTable[i][vt.fieldNumByName("Confidence")];
		    Comments = vt.stringTable[i][vt.fieldNumByName("Comments")];
		    Tissue = vt.stringTable[i][vt.fieldNumByName("Tissue")];
		    addReaction("reaction"+(i+1),link);
		}
		
		biopax.saveToFile("c:/datas/ewing/annotlink.owl", biopax.biopaxmodel);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void addMolecule(String name) throws Exception{
		if(name.startsWith("[")){
			addPhenotype(name);
		}else
		if(name.startsWith("(")){
			if(name.indexOf(":")>=0)
				addComplex(name.substring(1,name.length()-1));
		}else
		if(name.indexOf("^")>=0){
			String pname = name.substring(0,name.indexOf("^"));
			String feature = name.substring(name.indexOf("^")+1,name.length());
			addMolecule(pname);
			if(nameParticipant.get(name+"_")==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				sequenceFeature feat = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+feature, biopax.biopaxmodel);
				part.addSEQUENCE_DASH_FEATURE_DASH_LIST(feat);
				protein p = (protein)nameEntity.get(pname);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(name+"_", part);
			}
			if(nameReactions.get(pname+"->"+name)==null){
				biochemicalReaction r = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+"mod_"+pname+"_"+feature, biopax.biopaxmodel);
				r.setNAME(pname+"->"+name);
				r.setSHORT_DASH_NAME(pname+"->"+name);
				r.addLEFT((sequenceParticipant)nameParticipant.get(pname+"_"));
				r.addRIGHT((sequenceParticipant)nameParticipant.get(name+"_"));
				nameReactions.put(pname+"->"+name, r);
			}
		}
		else{
			//String uri = biopax.biopaxFileString+name;				
			if(nameEntity.get(name)==null){
				protein p = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+name, biopax.biopaxmodel);
				nameEntity.put(name, p);
				p.setNAME(name);
				p.setSHORT_DASH_NAME(name);
				if(molecules.indexOf(name)<0) molecules.add(name);
			}
			if(nameParticipant.get(name)==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				protein p = (protein)nameEntity.get(name);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(name+"_", part);
			}
		}
	}
	
	public static void addComplex(String name) throws Exception{
		StringTokenizer st = new StringTokenizer(name,":");
		complex cmp = (complex)nameEntity.get(name);
		
		if(cmp==null){
			cmp = biopax_DASH_level2_DOT_owlFactory.createcomplex(biopax.namespaceString+name, biopax.biopaxmodel);
			cmp.setNAME(name);
			cmp.setSHORT_DASH_NAME(name);
		}
		
		complexAssembly assembly = biopax_DASH_level2_DOT_owlFactory.createcomplexAssembly(biopax.namespaceString+"assembly_"+name, biopax.biopaxmodel);
		if(nameParticipant.get(name+"_")==null){
			sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
			part.setPHYSICAL_DASH_ENTITY(cmp);
			nameParticipant.put(name+"_", part);
		}
		
		assembly.addRIGHT((sequenceParticipant)nameParticipant.get(name+"_"));
		
		while(st.hasMoreTokens()){
			String part = st.nextToken();
			addMolecule(part);
			assembly.addLEFT((sequenceParticipant)nameParticipant.get(part+"_"));
			String compname = part+"_from_"+name;
			if(nameParticipant.get(compname)==null){
				sequenceParticipant pt = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+compname,biopax.biopaxmodel);
				protein p = (protein)nameEntity.get(part);
				pt.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(compname, pt);
			}
			cmp.addCOMPONENTS((sequenceParticipant)nameParticipant.get(compname));
			assembly.addLEFT((sequenceParticipant)nameParticipant.get(compname));
		}
	}
	
	public static void addReaction(String id, String link) throws Exception{
		StringBuffer pleft = new StringBuffer();
		StringBuffer pright = new StringBuffer();
		StringBuffer activation = new StringBuffer();
		extractReactionParts(link,pleft,pright,activation);
		String left = pleft.toString();
		String right = pright.toString();
		Vector memleft = new Vector();
		Vector memright = new Vector();
		System.out.println(link);
		if(left.startsWith("(")&&(left.indexOf(":")<0)){
			memleft = (Vector)families.get(left);
			if(memleft.size()==0)
				memleft.add(left);
		}else
			memleft.add(left);
		if(right.startsWith("(")&&(right.indexOf(":")<0)){
			memright = (Vector)families.get(right);
			if(memright.size()==0)
				memright.add(right);
		}else
			memright.add(right);
		for(int i=0;i<memleft.size();i++){
			String sleft = (String)memleft.get(i);
			for(int j=0;j<memright.size();j++){
				String sright = (String)memright.get(j);
				String slink = sleft+activation.toString()+sright;
				if(sright.startsWith("["))
					addPathwayInfluence(id+i+"_"+j,slink);
				else
					addElementaryReaction(id+i+"_"+j,slink);
			}
		}
	}
	
	public static void addPathwayInfluence(String id, String link) throws Exception{
		StringBuffer pleft = new StringBuffer();
		StringBuffer pright = new StringBuffer();
		StringBuffer activation = new StringBuffer();
		extractReactionParts(link,pleft,pright,activation);
		String left = pleft.toString();
		String right = pright.toString();
		biochemicalReaction pn = (biochemicalReaction)nameReactions.get(right);
		if(pn==null){
			pn = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+right, biopax.biopaxmodel);
			pn.setNAME(right);
			pn.setSHORT_DASH_NAME(right);
			nameReactions.put(right,pn);
	    }
		if(nameReactions.get(link)==null){
		control cntrl = biopax_DASH_level2_DOT_owlFactory.createcontrol(biopax.namespaceString+link, biopax.biopaxmodel);
		cntrl.setNAME(link);
		cntrl.setSHORT_DASH_NAME(link);
		if(activation.toString().equals("->"))
			cntrl.setCONTROL_DASH_TYPE("ACTIVATION");
		if(activation.toString().equals("-|"))
			cntrl.setCONTROL_DASH_TYPE("INHIBITION");
		if(left.startsWith("("))
			left = left.substring(1,left.length()-1);
		cntrl.setCONTROLLER((sequenceParticipant)nameParticipant.get(left+"_"));
		cntrl.setCONTROLLED(pn);
		nameReactions.put(link,cntrl);
		}
	}
	
	public static void extractReactionParts(String link, StringBuffer pleft, StringBuffer pright, StringBuffer activation){
		String inftype = "unknown";
		String left = "";
		String right = "";
		if(link.indexOf("->")>=0){
			inftype = "activation";
			left = link.substring(0, link.indexOf("->"));
			right = link.substring(link.indexOf("->")+2,link.length());
			activation.append("->");
		}
		if(link.indexOf("-|")>=0){
			inftype = "inhibition";
			left = link.substring(0, link.indexOf("-|"));
			right = link.substring(link.indexOf("-|")+2,link.length());
			activation.append("-|");
		}
		left = left.trim();
		right = right.trim();
		pleft.append(left);
		pright.append(right);
	}
	
	
	public static void addElementaryReaction(String id, String link) throws Exception{
		biochemicalReaction r = null;
		String inftype = "unknown";
		String left = "";
		String right = "";
		if(nameReactions.get(link)==null){
			r = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+id, biopax.biopaxmodel);
			r.setNAME(link);
			nameReactions.put(link,r);
		if(link.indexOf("->")>=0){
			inftype = "activation";
			left = link.substring(0, link.indexOf("->"));
			right = link.substring(link.indexOf("->")+2,link.length());
		}
		if(link.indexOf("-|")>=0){
			inftype = "inhibition";
			left = link.substring(0, link.indexOf("-|"));
			right = link.substring(link.indexOf("-|")+2,link.length());
		}
		left = left.trim();
		right = right.trim();
		if(left.startsWith("("))
			left = left.substring(1,left.length()-1);
		if(right.startsWith("("))
			right = right.substring(1,right.length()-1);
		if(nameParticipant.get(left+"_")!=null)
			r.addLEFT((sequenceParticipant)nameParticipant.get(left+"_"));
		if(nameParticipant.get(right+"_")!=null)
			r.addRIGHT((sequenceParticipant)nameParticipant.get(right+"_"));
		
		// now properties
		addPublication(r,pub1);
		addPublication(r,pub2);
		
		r.addCOMMENT("EFFECT: "+inftype);
		
		r.addCOMMENT("CHEMTYPE: "+ChemType);
		r.addCOMMENT("DELAY: "+Delay);
		r.addCOMMENT("CONFIDENCE: "+Confidence);
		
		r.addCOMMENT("TISSUE: "+Tissue);
		r.addCOMMENT("COMMENT: "+Comments);
		
		/*public static String ChemType = "";
		public static String Delay = "";
		public static String Confidence = "";
		public static String Comments = "";
		public static String Tissue = "";*/

	    }
	}
	
	public static void addPublication(biochemicalReaction r, String pub) throws Exception{
		if(pub.length()>2){
			StringTokenizer st = new StringTokenizer(pub,":(");
			String pmid = st.nextToken(); pmid = st.nextToken();
			PublicationXref publ = (PublicationXref)publications.get("pubmed_"+pmid);
			if(publ==null){
				publ = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+"pubmed_"+pmid, biopax.biopaxmodel);
				publ.setDB("Pubmed");
				publ.setID(pmid);
			}
			r.addXREF(publ);
		}
	}
	
	public static void addPhenotype(String name) throws Exception{
		String sname = "";
		if(name.startsWith("[")){
			sname = name.substring(1,name.length()-1);
			pathway pthw = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+sname,biopax.biopaxmodel);
			pthw.setNAME(sname);
			pthw.setSHORT_DASH_NAME(sname);
			phenotypes.put(sname, pthw);
		}
	}
	
	public static void addFamily(String name) throws Exception{
		String sname = "";
		if(name.startsWith("(")){
			sname = name.substring(1,name.length()-1);
		Vector members = new Vector();
		if(sname.indexOf(",")>=0){
			StringTokenizer st = new StringTokenizer(sname,",");
			while(st.hasMoreTokens()){
				members.add(st.nextToken());
			}
			families.put(name, members);
			for(int i=0;i<members.size();i++)
				addMolecule((String)members.get(i));
			if(members.size()==0)
				addMolecule(sname);		
		}
		if(sname.indexOf(".")>=0){
			findAllMembers(sname,members);
			families.put(name, members);
			for(int i=0;i<members.size();i++)
				addMolecule((String)members.get(i));
			if(members.size()==0)
				addMolecule(sname);		
		}
		}
	}
	
	public static void findAllMembers(String sname, Vector members) throws Exception{
		if(sname.startsWith(".")){
			String part = sname.substring(1,sname.length());
			for(int i=0;i<molecules.size();i++){
				String mol = (String)molecules.get(i);
				if(mol.endsWith(part))
					members.add(mol);
			}
		}
		if(sname.endsWith(".")){
			String part = sname.substring(0,sname.length()-1);
			for(int i=0;i<molecules.size();i++){
				String mol = (String)molecules.get(i);
				if(mol.startsWith(part))
					members.add(mol);
			}
		}
	}

}
