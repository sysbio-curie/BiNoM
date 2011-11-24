package fr.curie.BiNoM.pathways;

import java.util.*;
import java.io.*;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.converters.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;

import org.sbml.x2001.ns.celldesigner.*;

import vdaoengine.data.VDataTable;

public class SimpleTextInfluenceToBioPAX {

	public  BioPAX biopax = null;
	public  HashMap nameEntity = new HashMap();
	public  HashMap nameParticipant = new HashMap();
	public  HashMap nameFeatures = new HashMap();
	public  HashMap nameReactions = new HashMap();
	public  HashMap families = new HashMap();
	public  HashMap phenotypes = new HashMap();	
	public  Vector tokens = new Vector();
	public  Vector tissueTypes = new Vector();
	public  HashMap publications = new HashMap();
	
	public  HashMap userDefinedFamilies = new HashMap();
	public  HashMap mapFromTokenToFamily = new HashMap();
	public  HashMap userDefinedFamiliesExpand = new HashMap();
	public  HashMap userDefinedFamiliesNonSelectedMembers = new HashMap();
	
	public  String pub1 = "";
	public  String pub2 = "";
	public  String ChemType = "";
	public  String Delay = "";
	public  String Confidence = "";
	public  String Comments = "";
	public  String Tissue = "";
	
	private static SimpleTextInfluenceToBioPAX instance = null;
	
	public static SimpleTextInfluenceToBioPAX getInstance(){
		if(instance==null)
			instance = new SimpleTextInfluenceToBioPAX();
		return instance;
	}
	
	public static void deleteInstance(){
		instance = null;
	}
	

	public  BioPAX convertFromFile(String fileName) throws Exception{
		return convert(Utils.loadString(fileName));
	}
	
	public  void prepareFamilies(String text) throws Exception{
		SimpleTable vt = new SimpleTable();
		vt.LoadFromSimpleDatFileString(text, true, "\t");
		
		for(int i=0;i<vt.rowCount;i++){
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
		if(tokens.indexOf(left)<0) tokens.add(left);
		if(tokens.indexOf(right)<0) tokens.add(right);
		   addMolecule_(left);
		   addMolecule_(right);
		}
		
		for(int k=0;k<2;k++)
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
			   addFamily_(left);
			   addFamily_(right);
			   addPhenotype_(left);
			   addPhenotype_(right);
			}
		
		// Family information is copied to userDefinedFamilies
		userDefinedFamilies.clear();
		Iterator it = families.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Vector members = (Vector)families.get(key);
			Vector user_members = new Vector();
			for(int i=0;i<members.size();i++){
				String member = (String)members.get(i);
				//if(member.indexOf("^")<0)
					user_members.add(member);
			}
			userDefinedFamilies.put(key, user_members);
		}
		
		// We should add to a family members that are intersecting families
		
		addIntersectingFamilies();
		
		
	}
	
	public  void addFamilies(Vector newFamilies) throws Exception{
		for(int i=0;i<newFamilies.size();i++){
			String newf = (String)newFamilies.get(i);
			if(userDefinedFamilies.get(newf)==null){
				addFamily_(newf);
				Vector v = (Vector)families.get(newf);
				Vector user_members = new Vector();
				for(int j=0;j<v.size();j++){
					user_members.add(v.get(j));
				}
				userDefinedFamilies.put(newf, user_members);
			}
		}
	}
	
	public  void makeMemberFamilyMap(){
		mapFromTokenToFamily.clear();
		Iterator it = userDefinedFamilies.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Vector members = (Vector)userDefinedFamilies.get(key);
			for(int i=0;i<members.size();i++){
				String member = (String)members.get(i);
				Vector families = (Vector)mapFromTokenToFamily.get(member);
				if(families==null) 
					families = new Vector();
				families.add(key);
				mapFromTokenToFamily.put(member, families);
			}
		}
	}
	
	public  void checkUserFamiliesForConsistency(){
		
		boolean unique = false;
		while(!unique){
			// check for uniqueness
			makeMemberFamilyMap();
			unique = true;
			Iterator it = mapFromTokenToFamily.keySet().iterator();
			while(it.hasNext()){
				String molname = (String)it.next();
				Vector families = (Vector)mapFromTokenToFamily.get(molname);
				if(families.size()>1)
					unique = false;
			}
			if(!unique)
				mergeFamilies();
		}
		
	}
	
	public void addIntersectingFamilies(){
		makeMemberFamilyMap();
		Iterator it = mapFromTokenToFamily.keySet().iterator();
		while(it.hasNext()){
			String molname = (String)it.next();
			Vector familyNames = (Vector)mapFromTokenToFamily.get(molname);
			for(int i=0;i<familyNames.size();i++){
				String familyNameFrom = (String)familyNames.get(i);
				Vector membersFrom = (Vector)(userDefinedFamilies.get(familyNameFrom));
				for(int j=0;j<familyNames.size();j++)if(i!=j){
					String familyNameTo = (String)familyNames.get(j);
					Vector membersTo = (Vector)(userDefinedFamilies.get(familyNameTo));
					Vector nonselect = (Vector)userDefinedFamiliesNonSelectedMembers.get(familyNameTo);
					if(nonselect==null)
						nonselect = new Vector();
					for(int k=0;k<membersFrom.size();k++){
						String memberName = (String)membersFrom.get(k);
						if(membersTo.indexOf(memberName)<0){
							membersTo.add(membersFrom.get(k));
							nonselect.add(membersFrom.get(k));
						}
					}
					userDefinedFamiliesNonSelectedMembers.put(familyNameTo, nonselect);
				}
			}
		}
	}
	
	public  void mergeFamilies(){
		HashMap tempMap = new HashMap();
		Iterator it = mapFromTokenToFamily.keySet().iterator();
		while(it.hasNext()){
			String molname = (String)it.next();
			Vector families = (Vector)mapFromTokenToFamily.get(molname);
			
			boolean alreadyFound = false;
			Iterator itf = tempMap.keySet().iterator();
			while(itf.hasNext()){
				String fn = (String)itf.next();
				Vector mems = (Vector)tempMap.get(fn);
				if(mems.indexOf(molname)>=0)
					alreadyFound = true;
			}
			if(alreadyFound)
				continue;
			
			String famname = "";
			if(families.size()<=1){
				famname = (String)families.get(0);
				Vector members = (Vector)tempMap.get(famname);
				if(members==null) 
					members = new Vector();
				if(members.indexOf(molname)<0)
					members.add(molname);
				tempMap.put(famname,members);
			}else{
				int maxsize = -1;
				int maxsizei = -1;
				Vector merged_members = new Vector();
				for(int i=0;i<families.size();i++){
					String fn = (String)families.get(i);
					Vector members = (Vector)userDefinedFamilies.get(fn);
					if(members.size()>maxsize){
						maxsize = members.size();
						maxsizei = i; 
					}
					for(int j=0;j<members.size();j++)
						if(merged_members.indexOf(members.get(j))<0)
							merged_members.add(members.get(j));
				}
				famname = (String)families.get(maxsizei);//+"_";
				tempMap.put(famname,merged_members);
			}
		}
		userDefinedFamilies = tempMap;
	}
	
	
	public  void addMolecule_(String name) throws Exception{
		if(name.startsWith("[")){
			addPhenotype_(name);
		}else
		if(name.startsWith("(")){
			if(name.indexOf(":")>=0)
				addComplex_(name.substring(1,name.length()-1));
			if(tokens.indexOf(name)<0) tokens.add(name);
		}else
		if(name.indexOf("^")>=0){
			String pname = name.substring(0,name.indexOf("^"));
			String feature = name.substring(name.indexOf("^")+1,name.length());
			addMolecule_(pname);
			if(nameParticipant.get(name+"_")==null){
				nameParticipant.put(name+"_", name+"_");
			}
		}
		else{
			//String uri = biopax.biopaxFileString+name;				
			if(nameEntity.get(name)==null){
				nameEntity.put(name, name);
				if(tokens.indexOf(name)<0) tokens.add(name);
			}
			if(nameParticipant.get(name)==null){
				nameParticipant.put(name+"_", name+"_");
			}
		}
	}

	public  void addComplex_(String name) throws Exception{
		StringTokenizer st = new StringTokenizer(name,":");
		complex cmp = (complex)nameEntity.get(name);
		
		if(nameParticipant.get(name+"_")==null){
			nameParticipant.put(name+"_", name+"_");
		}
		
		while(st.hasMoreTokens()){
			String part = st.nextToken();
			addMolecule_(part);
			String compname = part+"_from_"+name;
			if(nameParticipant.get(compname)==null){
				nameParticipant.put(compname, compname);
			}
		}
	}
	
	public  void addPhenotype_(String name) throws Exception{
		String sname = "";
		if(name.startsWith("[")){
			sname = name.substring(1,name.length()-1);
			phenotypes.put(sname, sname);
		}
	}	
	
	public  void addFamily_(String name) throws Exception{
		String sname = "";
		if(name.startsWith("(")&&(name.endsWith(")"))){
				sname = name.substring(1,name.length()-1);
		if(sname.indexOf(":")>0){
			StringTokenizer st = new StringTokenizer(sname,":");
			while(st.hasMoreTokens())
				addFamily_(st.nextToken());
		}
		else
		{
		Vector members = new Vector();
		if(sname.indexOf(",")>=0){
			StringTokenizer st = new StringTokenizer(sname,",");
			while(st.hasMoreTokens()){
				members.add(st.nextToken());
			}
			families.put(name, members);
			for(int i=0;i<members.size();i++)
				addMolecule_((String)members.get(i));
			if(members.size()==0)
				addMolecule_(sname);	
			//if(members.indexOf(name)<0)
			//	members.add(name);
		}
		if(sname.indexOf(".")>=0){
			findAllMembers_(sname,members);
			families.put(name, members);
			for(int i=0;i<members.size();i++)
				addMolecule_((String)members.get(i));
			if(members.size()==0)
				addMolecule_(sname);
		}
		}}else
		if(name.startsWith("("))if(name.indexOf("^")>=0){
				sname = name.substring(1,name.indexOf("^")-1);
				addFamily_("("+sname+")");
		}
		
	}

	
	public  void findAllMembers_(String sname, Vector members) throws Exception{
		if(sname.startsWith("[")) return;
		if(sname.startsWith(".")){
			String part = sname.substring(1,sname.length());
			for(int i=0;i<tokens.size();i++){
				String mol = (String)tokens.get(i);
				//if(mol.endsWith(part))
				if(!mol.equals("("+sname+")"))
					if(mol.indexOf(part)>=0)
						members.add(mol);
			}
		}
		if(sname.endsWith(".")){
			String part = sname.substring(0,sname.length()-1);
			for(int i=0;i<tokens.size();i++){
				String mol = (String)tokens.get(i);
				//if(mol.startsWith(part))
				if(!mol.equals("("+sname+")"))
					if(mol.indexOf(part)>=0)
						members.add(mol);
			}
		}
	}
	
	public  Vector reactionList(String text, HashMap reactionBackLinks){
		makeMemberFamilyMap();		
		Vector res = new Vector();
		SimpleTable vt = new SimpleTable();
		vt.LoadFromSimpleDatFileString(text, true, "\t");
		
		for(int i=0;i<vt.rowCount;i++){
			String link = vt.stringTable[i][vt.fieldNumByName("Link")];
			Vector v = makeReactionListFromLink(link);
			for(int j=0;j<v.size();j++)
				if(res.indexOf(v.get(j))<0){
					res.add(v.get(j));
					reactionBackLinks.put(v.get(j), new Integer(i));
				}
		}
		
		return res;
	}
	
	public  Vector makeReactionListFromLink(String reaction){
		Vector res = new Vector();
		StringBuffer pleft = new StringBuffer();
		StringBuffer pright = new StringBuffer();
		StringBuffer activation = new StringBuffer();
		extractReactionParts_(reaction,pleft,pright,activation);
		String left = pleft.toString();
		String right = pright.toString();
		Vector memleft = entitiesForToken(left);
		Vector memright = entitiesForToken(right);

		for(int i=0;i<memleft.size();i++){
			for(int j=0;j<memright.size();j++){
				String react = (String)memleft.get(i)+activation.toString()+(String)memright.get(j);
				res.add(react);
			}
		}
		
		return res;
	}
	
	public  Vector entitiesForToken(String token){
		Vector res = new Vector();
		
		boolean expand = false;
		if(userDefinedFamiliesExpand.get(token)!=null)
			expand = ((Boolean)userDefinedFamiliesExpand.get(token)).booleanValue();
		
		// Dealing with complexes
		if(token.indexOf(":")>=0){
			String stoken = token.substring(1, token.length()-1);
			StringTokenizer st = new StringTokenizer(stoken,":");
			Vector comps = new Vector();
			while(st.hasMoreTokens()){
				Vector v = entitiesForToken(st.nextToken());
				comps.add(v);
			}
			res = getAllComplexes(comps);
			
		}else{
		
		
		if(expand){
			if(userDefinedFamilies.get(token)!=null){
				Vector mems = (Vector)userDefinedFamilies.get(token);
				for(int i=0;i<mems.size();i++){
					Vector mms = entitiesForToken((String)mems.get(i));
					for(int j=0;j<mms.size();j++)
						res.add(mms.get(j));
				}
			}
		}else{
			if(mapFromTokenToFamily.get(token)!=null){
					Vector family = (Vector)mapFromTokenToFamily.get(token);
					if(family.size()>1)
						System.out.println("ERROR!: multiple membership  for "+token);
					
					String famname = (String)family.get(0);
					
					boolean supfam_expand = false;
					if(userDefinedFamiliesExpand.get(famname)!=null)
						supfam_expand = ((Boolean)userDefinedFamiliesExpand.get(famname)).booleanValue();

					if(!supfam_expand){
					Vector mms = entitiesForToken(famname);
					for(int j=0;j<mms.size();j++){
						String mfamname = (String)mms.get(j);
						boolean family_expand = false;
						if(userDefinedFamiliesExpand.get(mfamname)!=null)
							family_expand = ((Boolean)userDefinedFamiliesExpand.get(mfamname)).booleanValue();
						if(!family_expand)
							res.add(mfamname);
					}
					}
					
			}else
				if(userDefinedFamilies.get(token)!=null){
					res.add(token);
				}
		}//expand		
		
		if(res.size()==0)if(token.indexOf("^")>=0){
			String ntoken = token.substring(0, token.indexOf("^"));
			Vector nres = entitiesForToken(ntoken);
			for(int kk=0;kk<nres.size();kk++)
				res.add(((String)nres.get(kk))+token.substring(token.indexOf("^"),token.length()));
		}
		
		}
		
		if(res.size()==0)
			res.add(token);
		
		return res;
	}
	
	public  void extractReactionParts_(String link, StringBuffer pleft, StringBuffer pright, StringBuffer activation){
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
	
	public  Vector constitutiveReactions(){
		makeMemberFamilyMap();		
		Vector res = new Vector();
		// First, add some tokens - components of complexes
		Vector additionalTokens = new Vector();
		for(int i=0;i<tokens.size();i++){
			String name = (String)tokens.get(i);
		if(name.indexOf(":")>=0){
			String stoken = name.substring(1, name.length()-1);
			StringTokenizer st = new StringTokenizer(stoken,":");
			Vector comps = new Vector();
			while(st.hasMoreTokens()){
				Vector v = entitiesForToken(st.nextToken());
				comps.add(v);
			}
			Vector complexes = getAllComplexes(comps);
			for(int k=0;k<complexes.size();k++){
				String cmp_name = (String)complexes.get(k);
				cmp_name = cmp_name.substring(1, cmp_name.length()-1);
				StringTokenizer st1 = new StringTokenizer(cmp_name,":");
				String left = "";
				while(st1.hasMoreTokens()){
					String component = st1.nextToken();
					if(tokens.indexOf(component)<0)
						additionalTokens.add(component);
				}
			}
		}
		}
		for(int i=0;i<additionalTokens.size();i++)
			tokens.add(additionalTokens.get(i));
		//
		for(int i=0;i<tokens.size();i++){
			String name = (String)tokens.get(i);
			// modifications (unary reactions)			
			if(name.indexOf("^")>=0)if(!(name.startsWith("(")&&name.endsWith(")"))){
				String pname = name.substring(0,name.indexOf("^"));
				String reactionString = pname+"->"+name;
				Vector v = makeReactionListFromLink(reactionString);
				for(int j=0;j<v.size();j++)
					if(res.indexOf(v.get(j))<0)
						res.add(v.get(j));
			}
			// Modifications in complexes
			for(int j=0;j<tokens.size();j++)if(i!=j){
				//System.out.println("C: "+(String)tokens.get(j));
				String modname = (String)tokens.get(j);
				if(name.indexOf(modname)>=0)if(name.startsWith("("))if(name.endsWith(")"))
				if(modname.indexOf("^")>=0)if(!(modname.startsWith("(")&&modname.endsWith(")"))){
					String nonmod = modname.substring(0,modname.indexOf("^"));
					String reactionString = Utils.replaceString(name, modname, nonmod)+"->"+name;
					if(tokens.indexOf(nonmod)>=0){
					Vector v = makeReactionListFromLink(reactionString);
					for(int k=0;k<v.size();k++)
						if(res.indexOf(v.get(k))<0)
							res.add(v.get(k));
					}
				}
			}
			// complex formations
			if(name.indexOf(":")>=0){
				String stoken = name.substring(1, name.length()-1);
				StringTokenizer st = new StringTokenizer(stoken,":");
				Vector comps = new Vector();
				while(st.hasMoreTokens()){
					Vector v = entitiesForToken(st.nextToken());
					comps.add(v);
				}
				Vector complexes = getAllComplexes(comps);
				for(int k=0;k<complexes.size();k++){
					String cmp_name = (String)complexes.get(k);
					cmp_name = cmp_name.substring(1, cmp_name.length()-1);
					StringTokenizer st1 = new StringTokenizer(cmp_name,":");
					String left = "";
					while(st1.hasMoreTokens())
						left+=st1.nextToken()+"+";
					left = left.substring(0, left.length()-1);
					String s = left+"->"+cmp_name;
					if(res.indexOf(s)<0)
						res.add(s);
				}
			}
		}
		
		return res;
	}
	
	public  Vector getAllComplexes(Vector comps){
		Vector res = new Vector();
		if(comps.size()==2){
			for(int i1=0;i1<((Vector)comps.get(0)).size();i1++){
				String s = (String)((Vector)comps.get(0)).get(i1);
				for(int i2=0;i2<((Vector)comps.get(1)).size();i2++){
					res.add("("+s+":"+(String)((Vector)comps.get(1)).get(i2)+")");
				}
			}
		}if(comps.size()==3){
			for(int i1=0;i1<((Vector)comps.get(0)).size();i1++){
				String s = (String)((Vector)comps.get(0)).get(i1);
				for(int i2=0;i2<((Vector)comps.get(1)).size();i2++){
					String s1 = s+":"+(String)((Vector)comps.get(1)).get(i2);
					for(int i3=0;i3<((Vector)comps.get(2)).size();i3++){
						res.add("("+s1+":"+(String)((Vector)comps.get(2)).get(i3)+")");
					}
				}
			}
		}if(comps.size()==4){
			for(int i1=0;i1<((Vector)comps.get(0)).size();i1++){
				String s = (String)((Vector)comps.get(0)).get(i1);
				for(int i2=0;i2<((Vector)comps.get(1)).size();i2++){
					String s1 = s+":"+(String)((Vector)comps.get(1)).get(i2);
					for(int i3=0;i3<((Vector)comps.get(2)).size();i3++){
						String s2 = s1+":"+(String)((Vector)comps.get(2)).get(i3);
						for(int i4=0;i4<((Vector)comps.get(3)).size();i4++){
							res.add("("+s2+":"+(String)((Vector)comps.get(3)).get(i4)+")");
						}
					}
				}
			}
		}if(comps.size()==5){
			for(int i1=0;i1<((Vector)comps.get(0)).size();i1++){
				String s = (String)((Vector)comps.get(0)).get(i1);
				for(int i2=0;i2<((Vector)comps.get(1)).size();i2++){
					String s1 = s+":"+(String)((Vector)comps.get(1)).get(i2);
					for(int i3=0;i3<((Vector)comps.get(2)).size();i3++){
						String s2 = s1+":"+(String)((Vector)comps.get(2)).get(i3);
						for(int i4=0;i4<((Vector)comps.get(3)).size();i4++){
							String s3 = s2+":"+(String)((Vector)comps.get(3)).get(i4);
							for(int i5=0;i5<((Vector)comps.get(4)).size();i5++){
								res.add("("+s3+":"+(String)((Vector)comps.get(4)).get(i5)+")");
							}
						}
					}
				}
			}
		}
		return res;
	}
	
	public  void makeBioPAX(String text, Vector constitutiveReactions) throws Exception{
		SimpleTable vt = new SimpleTable();
		nameEntity.clear();
		nameParticipant.clear();
		nameFeatures.clear();
		nameReactions.clear();
		phenotypes.clear();	
		tissueTypes.clear();
		publications.clear();
		vt.LoadFromSimpleDatFileString(text, true, "\t");
		
		biopax = new BioPAX();
		
		for(int i=0;i<tokens.size();i++){
			Vector ents = (Vector)entitiesForToken((String)tokens.get(i));
			for(int j=0;j<ents.size();j++){
				addEntityBioPAX((String)ents.get(j));
			}
		}
		
	    for(int k=0;k<vt.fieldNames.length;k++)
	    	if(vt.fieldNames[k].endsWith(")"))
	    		if(vt.fieldNames[k].indexOf("(")>=0){
	    			int ind = vt.fieldNames[k].indexOf("(");
	    			vt.fieldNames[k] = vt.fieldNames[k].substring(0,ind);
	    		}
	    /*for(int k=0;k<vt.fieldNames.length;k++)
	    	System.out.println(vt.fieldNames[k]);*/
		
		
		for(int i=0;i<vt.rowCount;i++){
			String tt = "";
			if(vt.fieldNumByName("Tissue")>=0)
				tt = vt.stringTable[i][vt.fieldNumByName("Tissue")];
			if(tt!=null){
				tt = tt.trim();
				if(tissueTypes.indexOf(tt)<0)
					tissueTypes.add(tt);
			}
		    String link = vt.stringTable[i][vt.fieldNumByName("Link")];
		    
		    pub1 = "";
		    if(vt.fieldNumByName("ReviewRef")>=0)
		    	pub1 = vt.stringTable[i][vt.fieldNumByName("ReviewRef")];
		    pub2 = "";
		    if(vt.fieldNumByName("ExperimentRef")>=0)
		    	pub2 = vt.stringTable[i][vt.fieldNumByName("ExperimentRef")];
		    ChemType = "";
		    if(vt.fieldNumByName("ChemType")>=0)
		    	ChemType = vt.stringTable[i][vt.fieldNumByName("ChemType")];
		    Delay = "";
		    if(vt.fieldNumByName("Delay")>=0)
		    	Delay = vt.stringTable[i][vt.fieldNumByName("Delay")];
		    Confidence = "";
		    if(vt.fieldNumByName("Confidence")>=0)
		    	Confidence = vt.stringTable[i][vt.fieldNumByName("Confidence")];
		    Comments = "";
		    if(vt.fieldNumByName("Comments")>=0)
		    	Comments = vt.stringTable[i][vt.fieldNumByName("Comments")];
		    Tissue = "";
		    if(vt.fieldNumByName("Tissue")>=0)
		    	Tissue = vt.stringTable[i][vt.fieldNumByName("Tissue")];			
			Vector v = makeReactionListFromLink(link);
			for(int j=0;j<v.size();j++){
				addReactionBioPAX("re"+(i+1)+"_"+(j+1),(String)v.get(j));
			}
		}
		
		for(int i=0;i<constitutiveReactions.size();i++){
		    pub1 = "";
		    pub2 = "";
		    ChemType = "";
		    Delay = "";
		    Confidence = "";
		    Comments = "Constitutive reaction";
		    Tissue = "";			
			String link = (String)constitutiveReactions.get(i);
			addReactionBioPAX("const_re"+(i+1),link);
		}
		
	}
	
	public  void addEntityBioPAX(String name) throws Exception{
		boolean cont = true;
		if(name.startsWith("[")){
			addPhenotypeBioPAX(name);
			cont = false;
		}else
		if(name.startsWith("(")){
			if(name.indexOf(":")>=0){
				addComplexBioPAX(name.substring(1,name.length()-1));
				cont=false;
			}
		}
		if(cont)
		if(name.indexOf("^")>=0){
			String pname = name.substring(0,name.indexOf("^"));
			String feature = name.substring(name.indexOf("^")+1,name.length());
			Vector v = entitiesForToken(pname);
			pname = (String)v.get(0);
			//System.out.println(pname);
			addEntityBioPAX(pname);			
			if(nameParticipant.get(name+"_")==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				sequenceFeature feat = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+feature, biopax.biopaxmodel);
				part.addSEQUENCE_DASH_FEATURE_DASH_LIST(feat);
				protein p = (protein)nameEntity.get(pname);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(name+"_", part);
			}
		}else{
			//String uri = biopax.biopaxFileString+name;				
			if(nameEntity.get(name)==null){
				protein p = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+name, biopax.biopaxmodel);
				nameEntity.put(name, p);
				p.setNAME(name);
				p.setSHORT_DASH_NAME(name);
			}
			if(nameParticipant.get(name)==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				protein p = (protein)nameEntity.get(name);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(name+"_", part);
			}
		}
	}
	
	public  void addComplexBioPAX(String name) throws Exception{
		StringTokenizer st = new StringTokenizer(name,":");
		complex cmp = (complex)nameEntity.get(name);

		if(cmp==null){
			cmp = biopax_DASH_level2_DOT_owlFactory.createcomplex(biopax.namespaceString+name, biopax.biopaxmodel);
			cmp.setNAME(name);
			cmp.setSHORT_DASH_NAME(name);
		}
		
		if(nameParticipant.get(name+"_")==null){
			sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
			part.setPHYSICAL_DASH_ENTITY(cmp);
			nameParticipant.put(name+"_", part);
		}
		int k = 1;
		while(st.hasMoreTokens()){
			String part = st.nextToken();
			addEntityBioPAX(part);
			k++;
			String compname = part+"_from_"+name+k;
			if(nameParticipant.get(compname)==null){
				sequenceParticipant pt = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+compname,biopax.biopaxmodel);
				String feature = null;
				if(part.indexOf("^")>=0){
					feature = part.substring(part.indexOf("^")+1,part.length());					
					part = part.substring(0,part.indexOf("^"));
				}
				protein p = (protein)nameEntity.get(part);
				if(p==null)
					System.out.println("ERROR: can not find entity for "+part);
				if(feature!=null){
					sequenceFeature feat = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+feature, biopax.biopaxmodel);
					pt.addSEQUENCE_DASH_FEATURE_DASH_LIST(feat);
				}
				pt.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(compname, pt);
			}
			cmp.addCOMPONENTS((sequenceParticipant)nameParticipant.get(compname));
		}
	}
	
	public  void addPhenotypeBioPAX(String name) throws Exception{
		String sname = "";
		if(name.startsWith("[")){
			sname = name.substring(1,name.length()-1);
			/*pathway pthw = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+sname,biopax.biopaxmodel);
			pthw.setNAME(sname);
			pthw.setSHORT_DASH_NAME(sname);
			phenotypes.put(sname, pthw);*/
			if(nameEntity.get(sname)==null){
				protein p = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+sname, biopax.biopaxmodel);
				nameEntity.put(sname, p);
				p.setNAME(sname);
				p.setSHORT_DASH_NAME(sname);
				p.addCOMMENT("This is a psedo-protein representing a pathway (or phenotype) after conversion from AIN file");
				p.addCOMMENT("SHOW_TYPE: pathway");
			}
			if(nameParticipant.get(sname)==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+sname+"_",biopax.biopaxmodel);
				protein p = (protein)nameEntity.get(sname);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(sname+"_", part);
			}
		}
	}	
	
	public  void addReactionBioPAX(String id, String link) throws Exception{
		if(link.indexOf("+")>=0){
			
			String left = "";
			String right = "";
			left = link.substring(0, link.indexOf("->"));
			right = link.substring(link.indexOf("->")+2,link.length());
			
			String name = right;
			complex cmp = (complex)nameEntity.get(name);
			complexAssembly assembly = biopax_DASH_level2_DOT_owlFactory.createcomplexAssembly(biopax.namespaceString+"assembly_"+name, biopax.biopaxmodel);
			if(nameParticipant.get(name+"_")==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				part.setPHYSICAL_DASH_ENTITY(cmp);
				nameParticipant.put(name+"_", part);
			}
			assembly.addRIGHT((sequenceParticipant)nameParticipant.get(name+"_"));

			int k=1;
			StringTokenizer st = new StringTokenizer(left,"+");			
			while(st.hasMoreTokens()){
				String part = st.nextToken();
				assembly.addLEFT((sequenceParticipant)nameParticipant.get(part+"_"));
			}
			
			
		}else{
			biochemicalReaction r = null;
			String inftype = "unknown";
			String left = "";
			String right = "";
			if(nameReactions.get(link)==null){
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
			
			if(right.startsWith("[")||left.startsWith("["))
				addPathwayInfluenceBioPAX(id,link);
			else{
				
			r = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+id, biopax.biopaxmodel);
			r.setNAME(link);
			nameReactions.put(link,r);
			
			if(left.startsWith("("))if(left.indexOf(":")>=0)
				left = left.substring(1,left.length()-1);
			if(right.startsWith("("))if(right.indexOf(":")>=0)
				right = right.substring(1,right.length()-1);
			if(nameParticipant.get(left+"_")!=null)
				r.addLEFT((sequenceParticipant)nameParticipant.get(left+"_"));
			if(nameParticipant.get(right+"_")!=null)
				r.addRIGHT((sequenceParticipant)nameParticipant.get(right+"_"));
		
			// now properties
			addPublication(r,pub1);
			addPublication(r,pub2);
		
			r.addCOMMENT("EFFECT: "+inftype);
		
			if(ChemType!=null)if(!ChemType.equals(""))
				r.addCOMMENT("CHEMTYPE: "+ChemType);
			if(Delay!=null)if(!Delay.equals(""))
				r.addCOMMENT("DELAY: "+Delay);
			if(Confidence!=null)if(!Confidence.equals(""))
				r.addCOMMENT("CONFIDENCE: "+Confidence);
		
			if(Tissue!=null)if(!Tissue.equals(""))
				r.addCOMMENT("TISSUE: "+Tissue);
			if(Comments!=null)if(!Comments.equals(""))
				r.addCOMMENT("COMMENT: "+Comments);
		
			/*public  String ChemType = "";
			public  String Delay = "";
			public  String Confidence = "";
			public  String Comments = "";
			public  String Tissue = "";*/

	    }}}
	}
	
	public  void addPublicationBioPAX(biochemicalReaction r, String pub) throws Exception{
		if(pub.length()>2){
			StringTokenizer st = new StringTokenizer(pub,":(");
			String pmid = st.nextToken(); pmid = st.nextToken();
			publicationXref publ = (publicationXref)publications.get("pubmed_"+pmid);
			if(publ==null){
				publ = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+"pubmed_"+pmid, biopax.biopaxmodel);
				//publ.setID(pmid);				
				publ.setDB("Pubmed");
				publ.addCOMMENT(pub);
				/*pmid = Utils.replaceString(pmid, "(", "_");
				pmid = Utils.replaceString(pmid, ")", "_");
				pmid = Utils.replaceString(pmid, ":", "_");*/
			}
			r.addXREF(publ);
		}
	}
	
	public  void addPathwayInfluenceBioPAX(String id, String link) throws Exception{
		StringBuffer pleft = new StringBuffer();
		StringBuffer pright = new StringBuffer();
		StringBuffer activation = new StringBuffer();
		extractReactionParts(link,pleft,pright,activation);
		String left = pleft.toString();
		String right = pright.toString();
		if(left.startsWith("["))
			left = left.substring(1,left.length()-1);
		if(right.startsWith("["))
			right = right.substring(1,right.length()-1);
		addReactionBioPAX(id, left+activation+right);
		/*biochemicalReaction pn = (biochemicalReaction)nameReactions.get(right);
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
		if(left.startsWith("("))if(left.indexOf(":")>=0)
			left = left.substring(1,left.length()-1);
		cntrl.setCONTROLLER((sequenceParticipant)nameParticipant.get(left+"_"));
		cntrl.setCONTROLLED(pn);
		nameReactions.put(link,cntrl);
		}*/
	}
	
	
	// ------------------------------------
	// ------------------------------------
	// ------------------------------------	
	
	public  BioPAX convert(String text) throws Exception{
		
		text = preprocessText(text);
	
		//VDataTable vt = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("c:/datas/ewing/annotlinks.xls", true, "\t");
		SimpleTable vt = new SimpleTable();
		vt.LoadFromSimpleDatFileString(text, true, "\t");
		
		/*System.out.println(vt.rowCount+"\t"+vt.colCount);
		for(int i=0;i<vt.rowCount;i++){
			for(int j=0;j<vt.colCount;j++){
				System.out.print(vt.stringTable[i][j]+"\t");
			}
			System.out.println();
		}*/
		
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
		if(tokens.indexOf(left)<0) tokens.add(left);
		if(tokens.indexOf(right)<0) tokens.add(right);
		   addMolecule(left);
		   addMolecule(right);
		}
		//for(int i=0;i<tissueTypes.size();i++)
		//	System.out.println((String)tissueTypes.get(i));
		Collections.sort(tokens);
		for(int i=0;i<tokens.size();i++)
			System.out.println((String)tokens.get(i));
		
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
		
		return biopax;

	}
	

	public  void addMolecule(String name) throws Exception{
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
				if(tokens.indexOf(name)<0) tokens.add(name);
			}
			if(nameParticipant.get(name)==null){
				sequenceParticipant part = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+name+"_",biopax.biopaxmodel);
				protein p = (protein)nameEntity.get(name);
				part.setPHYSICAL_DASH_ENTITY(p);
				nameParticipant.put(name+"_", part);
			}
		}
	}
	
	public  void addComplex(String name) throws Exception{
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
	
	public  void addReaction(String id, String link) throws Exception{
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
	
	public  void addPathwayInfluence(String id, String link) throws Exception{
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
	
	public  void extractReactionParts(String link, StringBuffer pleft, StringBuffer pright, StringBuffer activation){
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
	
	
	public  void addElementaryReaction(String id, String link) throws Exception{
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
		
		/*public  String ChemType = "";
		public  String Delay = "";
		public  String Confidence = "";
		public  String Comments = "";
		public  String Tissue = "";*/

	    }
	}
	
	public  void addPublication(biochemicalReaction r, String pub) throws Exception{
		if(pub.length()>2){
			StringTokenizer st = new StringTokenizer(pub,":(");
			String pmid = st.nextToken(); pmid = st.nextToken();
			publicationXref publ = (publicationXref)publications.get("pubmed_"+pmid);
			if(publ==null){
				publ = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+"pubmed_"+pmid, biopax.biopaxmodel);
				publ.setDB("Pubmed");
				publ.setID(pmid);
				publ.addCOMMENT(pub);
			}
			r.addXREF(publ);
		}
	}
	
	public  void addPhenotype(String name) throws Exception{
		String sname = "";
		if(name.startsWith("[")){
			sname = name.substring(1,name.length()-1);
			pathway pthw = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+sname,biopax.biopaxmodel);
			pthw.setNAME(sname);
			pthw.setSHORT_DASH_NAME(sname);
			phenotypes.put(sname, pthw);
		}
	}
	
	public  void addFamily(String name) throws Exception{
		String sname = "";
		if(name.startsWith("(")){
			sname = name.substring(1,name.length()-1);
			if(sname.indexOf(":")>0){
				StringTokenizer st = new StringTokenizer(sname,":");
				while(st.hasMoreTokens())
					addFamily_(st.nextToken());
			}else{			
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
		}}
	}
	
	public  void findAllMembers(String sname, Vector members) throws Exception{
		if(sname.startsWith(".")){
			String part = sname.substring(1,sname.length());
			for(int i=0;i<tokens.size();i++){
				String mol = (String)tokens.get(i);
				if(mol.endsWith(part))
					members.add(mol);
			}
			//if(members.size()==0)
			//	members.add(sname);
		}
		if(sname.endsWith(".")){
			String part = sname.substring(0,sname.length()-1);
			for(int i=0;i<tokens.size();i++){
				String mol = (String)tokens.get(i);
				if(mol.startsWith(part))
					members.add(mol);
			}
			//if(members.size()==0)
			//	members.add(sname);
		}
	}
	
	public String preprocessText(String text){
		StringBuffer newtext = new StringBuffer();		
		try{
		LineNumberReader lr = new LineNumberReader(new StringReader(text));
		String s = null;
		while((s=lr.readLine())!=null){
			if(s.trim().equals("")) continue;
			if(s.trim().startsWith("#")) continue;
			newtext.append(s+"\n");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return newtext.toString();
	}
	
	
	
}
