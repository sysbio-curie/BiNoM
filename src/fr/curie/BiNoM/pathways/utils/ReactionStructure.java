package fr.curie.BiNoM.pathways.utils;

import java.util.Vector;

public class ReactionStructure {
	
	public static void main(String args[]){
		ReactionStructure rs = decodeStructuredString("A + TP53|pho -.B -> C");
		System.out.println(rs);
	}
	
	public class Regulator{
		public String name = null;
		public String type = "CATALYSIS";
	}
	
	public static String[] possibleRegulatorSymbols = {"-.","-|","-.|","-*","-)","-","-","-|","-","-|"};
	public static String[] possibleRegulatorTypes = {"UNKNOWN_CATALYSIS","INHIBITION","UNKNOWN_INHIBITION","MODULATION","PHYSICAL_STIMULATION","CATALYSIS","TEMPLATEREACTIONREGULATION_ACTIVATION","TEMPLATEREACTIONREGULATION_INHIBITION","CATALYSIS_ACTIVATION","CATALYSIS_INHIBITION"};
	
	public static String[] possibleReactionSymbols = {"->","-->","-?>","-+>","-|>","-/>", "-..>","-.>","-:>","-=>","-.>"}; 
	public static String[] possibleReactionTypes = {"STATE_TRANSITION","KNOWN_TRANSITION_OMMITED","UNKNOWN_TRANSITION","POSITIVE_INFLUENCE","NEGATIVE_INFLUENCE","TRANSPORT", "TRANSCRIPTION", "TRANSLATION","HETERODIMER_ASSOCIATION", "DISSOCIATION","TemplateReaction"};	
	
	public String reactionType = "STATE_TRANSITION"; 
	public Vector<String> reactants = new Vector<String>();
	public Vector<String> products = new Vector<String>();
	public Vector<Regulator> regulators = new Vector<Regulator>();
	
	public String toString(){
		String reactionSymbol = possibleReactionSymbols[indexOf(possibleReactionTypes,reactionType)];
		String left = "";
		String right = "";
		for(int i=0;i<reactants.size()-1;i++)
			left+=reactants.get(i)+"+";
		if(reactants.size()>0) left+=reactants.get(reactants.size()-1);
		
		for(int i=0;i<regulators.size();i++)
			left+=possibleRegulatorSymbols[indexOf(possibleRegulatorTypes,regulators.get(i).type)]+regulators.get(i).name;
		
		for(int i=0;i<products.size()-1;i++)
			right+=products.get(i)+"+";
		if(products.size()>0) right+=products.get(products.size()-1);
		
		return left+reactionSymbol+right;
	}
	
	public static ReactionStructure decodeStructuredString(String structuredString){
		Vector<String> declaredSpecies = new Vector<String>();
		return decodeStructuredString(structuredString, declaredSpecies);
	}
	
	public static ReactionStructure decodeStructuredString(String structuredString, Vector<String> declaredSpecies){
		ReactionStructure r = new ReactionStructure();
		
		for(int i=0;i<possibleReactionSymbols.length;i++){
			if(structuredString.contains(possibleReactionSymbols[i])){
				String left = structuredString.substring(0, structuredString.indexOf(possibleReactionSymbols[i]));
				String right = structuredString.substring(structuredString.indexOf(possibleReactionSymbols[i])+possibleReactionSymbols[i].length(), structuredString.length());
				
				// encode the already known species by special symbols
				for(int k=0;k<declaredSpecies.size();k++){
					left = Utils.replaceString(left, declaredSpecies.get(k), "$"+k+"$");
					right = Utils.replaceString(right, declaredSpecies.get(k), "$"+k+"$");
				}
				
				
				String reactantString[] = left.split("\\+");
				for(int k=0;k<reactantString.length;k++){
					if(!reactantString[k].contains("-")){
						if(!reactantString[k].trim().equals("")){
							if(reactantString[k].startsWith("$")&&reactantString[k].endsWith("$"))
								r.reactants.add(reactantString[k]);
							else
								r.reactants.add(Utils.cutFirstLastNonVisibleSymbols(SpeciesStructure.decodeStructuredString(reactantString[k]).toString()));
						}
					}else{
						
						String regulators[] = reactantString[k].split("-");
						if(!regulators[0].trim().equals("")){
							if(regulators[0].startsWith("$")&&regulators[0].endsWith("$"))
								r.reactants.add(regulators[0]);
							else
								r.reactants.add(Utils.cutFirstLastNonVisibleSymbols(SpeciesStructure.decodeStructuredString(regulators[0]).toString()));
						}
						for(int j=1;j<regulators.length;j++){
							String regs = regulators[j];
							for(int l=0;l<possibleRegulatorSymbols.length;l++){
								if(("-"+regs).startsWith(possibleRegulatorSymbols[l])){
									Regulator reg = r.new Regulator();
									reg.name = Utils.cutFirstLastNonVisibleSymbols(regs.substring(possibleRegulatorSymbols[l].length()-1, regs.length()));
									reg.name = SpeciesStructure.decodeStructuredString(reg.name).toString();
									reg.type = possibleRegulatorTypes[l];
									r.regulators.add(reg);
									break;
								}
							}
						}
					}
						
				}
				
				String productsString[] = right.split("\\+");
				for(int k=0;k<productsString.length;k++){
					if(productsString[k].startsWith("$")&&productsString[k].endsWith("$"))
						r.products.add(Utils.cutFirstLastNonVisibleSymbols(productsString[k]));
					else
						r.products.add(Utils.cutFirstLastNonVisibleSymbols(SpeciesStructure.decodeStructuredString(productsString[k]).toString()));
				}
				
				r.reactionType = possibleReactionTypes[i];
				
				// little heuristics, trying to guess the complex assembly reaction
				
				if(r.reactants.size()>1)if(r.products.size()==1){
					SpeciesStructure ss = SpeciesStructure.decodeStructuredString(r.products.get(0));
					if(ss.components.size()>1)
						r.reactionType = "HETERODIMER_ASSOCIATION";
				}
				if(r.reactants.size()==1)if(r.products.size()>1){
					SpeciesStructure ss = SpeciesStructure.decodeStructuredString(r.reactants.get(0));
					if(ss.components.size()>1)
						r.reactionType = "DISSOCIATION";
				}
				
				
			break;	
			}
		}
		
		// now decode back the known species
		for(int k=0;k<declaredSpecies.size();k++){
			String sp = declaredSpecies.get(k);
			//if(sp.endsWith("@"))
			//	sp = sp.substring(0, sp.length()-1);
			String id = "$"+k+"$";
			for(int i=0;i<r.reactants.size();i++)
				r.reactants.set(i, SpeciesStructure.decodeStructuredString(Utils.replaceString(r.reactants.get(i), id, sp)).toString());
			for(int i=0;i<r.products.size();i++)
				r.products.set(i, SpeciesStructure.decodeStructuredString(Utils.replaceString(r.products.get(i), id, sp)).toString());
			for(int i=0;i<r.regulators.size();i++){
				Regulator rg = r.regulators.get(i);
				rg.name = SpeciesStructure.decodeStructuredString(Utils.replaceString(rg.name, id, sp)).toString();
				r.regulators.set(i, rg);
			}
		}		
		
		return r;
	}
	
	public static int indexOf(String set[], String word){
		int i = -1;
		for(int k=0;k<set.length;k++){
			if(set[k].equals(word))
				i = k;
		}
		return i;
	}
	
	public static String getReactionSymbol(String reactionType){
		String symbol = "->";
		for(int i=0;i<possibleReactionTypes.length;i++)
			if(possibleReactionTypes[i].toLowerCase().equals(reactionType.toLowerCase())){
				symbol = possibleReactionSymbols[i];
				break;
			}
		return symbol;
	}
	
	public static String getRegulatorSymbol(String regulatorType){
		String symbol = "-";
		for(int i=0;i<possibleRegulatorTypes.length;i++)
			if(possibleRegulatorTypes[i].toLowerCase().equals(regulatorType.toLowerCase())){
				symbol = possibleRegulatorSymbols[i];
				break;
			}
		return symbol;
	}	

}
