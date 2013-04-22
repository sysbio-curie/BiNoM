package fr.curie.BiNoM.pathways.utils;

import java.util.Vector;

import com.ibm.icu.util.StringTokenizer;

public class SpeciesStructure {
	
public static void main(String args[]){
	String s = "(a|pho:b|Thr5_pho)|active@compartment";
	System.out.println(decodeStructuredString(s).toString());
}

	
public class SpeciesStructureComponent{
	public String name = "";
	public Vector<SpeciesStructureComponentModification> modifications = new Vector<SpeciesStructureComponentModification>();
	public String toString(){
		String s = name;
		for(int i=0;i<modifications.size();i++){
			s+="|"+modifications.get(i).toString();
		}
		return s;
	}
}

public class SpeciesStructureComponentModification{
	public String name = "";
	public String modification_type = "";
	
	public String toString(){
		String s = modification_type;
		if(!name.equals(""))
			s=name+"_"+modification_type;
		return s;
	}
}

public Vector<SpeciesStructureComponent> components = new Vector<SpeciesStructure.SpeciesStructureComponent>(); 
public String compartment = null;
public String globalModifier = "";

public static SpeciesStructure decodeStructuredString(String structuredString){
	SpeciesStructure ss = new SpeciesStructure();
	StringTokenizer st = new StringTokenizer(structuredString,"@");
	structuredString = st.nextToken();
	if(st.hasMoreTokens())
		ss.compartment = st.nextToken();
	Vector<String> pair = new Vector<String>();
	extractGlobalModifier(structuredString,pair);
	structuredString = pair.get(0);
	ss.globalModifier = pair.get(1);
	st = new StringTokenizer(structuredString,":");
	while(st.hasMoreTokens()){
		SpeciesStructureComponent sc = (new SpeciesStructure()).new SpeciesStructureComponent();
		ss.components.add(sc);
		String s = st.nextToken();
		StringTokenizer st1 = new StringTokenizer(s,"|");
		String name = st1.nextToken();
		sc.name = name;
		while(st1.hasMoreTokens()){
			SpeciesStructureComponentModification sm = (new SpeciesStructure()).new SpeciesStructureComponentModification();
			sc.modifications.add(sm);
				String s1 = st1.nextToken();
				StringTokenizer st2 = new StringTokenizer(s1,"_");
				String mod_name = st2.nextToken();
				String mod_type = null;
				if(st2.hasMoreTokens())
					mod_type = st2.nextToken();
				else{
					mod_type = mod_name;
					mod_name = "";
				}
				sm.name = mod_name;
				sm.modification_type = mod_type;
		}
	}
	return ss;
}

public boolean isComplexSpecies(){
	return components.size()>1;
}

public String toString(){
	String s = "";
	for(int i=0;i<components.size();i++){
		s+=components.get(i).toString()+":";
	}
	if(s.endsWith(":")) s=s.substring(0, s.length()-1);
	if(!this.globalModifier.equals(""))
		s = "("+s+")|"+this.globalModifier;
	if(!this.compartment.equals(""))
		s = s+"@"+this.compartment;
	return s; 
}

public static void extractGlobalModifier(String s, Vector<String> pair){
	if(!s.startsWith("(")){
		pair.add(s);
		pair.add("");
	}else{
		int positionOFTheLastParenthesis = s.length();
		for(int i=s.length()-1;i>=0;i--){
			if(s.charAt(i)==')'){
				positionOFTheLastParenthesis = i;
				break;
			}
		}
		pair.add(s.substring(1, positionOFTheLastParenthesis));
		pair.add(s.substring(positionOFTheLastParenthesis+2, s.length()));
	}
}

}
