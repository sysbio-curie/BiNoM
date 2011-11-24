package fr.curie.BiNoM.pathways.converters;

import fr.curie.BiNoM.cytoscape.ain.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import java.util.*;

import javax.swing.JFrame;

public class SimpleTextInfluence2BioPAX {
	
	public static ImportFromAINDialogFamily dialog;
	public static ImportFromAINDialogConstitutiveReactions dialog1;
	
	public static void main(String args[]){
		try{
			
		SimpleTextInfluenceToBioPAX converter = SimpleTextInfluenceToBioPAX.getInstance();
		//converter.prepareFamilies(Utils.loadString("c:/datas/binomtest/text/annotlinks.txt"));
		//String text = Utils.loadString("c:/datas/binomtest/rennes/simple.txt");
		//String text = Utils.loadString("c:/datas/binomtest/text/annotlinks.txt");
		//String text = Utils.loadString("c:/datas/binomtest/text/annotlinks2binom.txt");
		//String text = Utils.loadString("c:/datas/binomtest/text/e2f.txt");
		//String text = Utils.loadString("c:/datas/binomtest/text/annotlinks2binom.txt");
		//String fname = "c:/docs/newbinom/temp";
		String fname = "c:/datas/ewing/pathanalysis/test";
		String text = Utils.loadString(fname+".txt");
		//String text = Utils.loadString("c:/datas/binomtest/text/test4.txt");
		text = converter.preprocessText(text);
		converter.prepareFamilies(text);
		
		System.out.println("\nTokens:");
		for(int i=0;i<converter.tokens.size();i++)
			System.out.println((String)converter.tokens.get(i));

		System.out.println("\nPhenotypes:");
		Iterator it = converter.phenotypes.keySet().iterator();
		while(it.hasNext())
			System.out.println((String)it.next());
		
		System.out.println("\nEntities:");
		it = converter.nameEntity.keySet().iterator();
		while(it.hasNext())
			System.out.println((String)it.next());
		
		System.out.println("\nParticipants:");
		it = converter.nameParticipant.keySet().iterator();
		while(it.hasNext())
			System.out.println((String)it.next());

		System.out.println("\nFamilies:");
		it = converter.families.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector members = (Vector)converter.families.get(key);
			for(int i=0;i<members.size();i++)
				System.out.print((String)members.get(i)+"\t");
			System.out.println();
		}

		/*Vector mems = (Vector)converter.families.get("(MAPK.)");
		mems.clear();
		mems.add("(MAPK1,MAPK2)");
		mems.add("(MAPK8,MAPK9,MAPK10)");
		mems = (Vector)converter.userDefinedFamilies.get("(MAPK.)");
		mems.clear();
		mems.add("(MAPK1,MAPK2)");
		mems.add("(MAPK8,MAPK9,MAPK10)");
		converter.userDefinedFamiliesExpand.put("(MAPK.)",new Boolean(true));
		//converter.userDefinedFamiliesExpand.put("(MAPK8,MAPK9,MAPK10)",new Boolean(true));
		//converter.userDefinedFamiliesExpand.put("(MAPK1,MAPK2)",new Boolean(true));
		converter.userDefinedFamiliesExpand.put("(CDK4,CDK6)",new Boolean(true));*/

		Vector mems = (Vector)converter.userDefinedFamilies.get("(E2F.)");
		mems.clear();
		mems.add("(E2F1,E2F2,E2F3)");
		mems.add("(E2F4,E2F5)");
		converter.userDefinedFamiliesExpand.put("(E2F.)",new Boolean(true));
		
		/*mems = (Vector)converter.userDefinedFamilies.get("(E2F1,E2F2,E2F3)");
		mems.clear();
		mems.add("E2F1");
		mems.add("E2F2");
		mems.add("E2F3");
		mems.add("(E2F1,E2F2)");*/		
		
		System.out.println("\nFamilies1:");
		it = converter.families.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector members = (Vector)converter.families.get(key);
			for(int i=0;i<members.size();i++)
				System.out.print((String)members.get(i)+"\t");
			System.out.println();
		}
		
		JFrame window = new JFrame();
		dialog = new ImportFromAINDialogFamily(window,"Defing families",true);
		dialog.setVisible(true);
		
		SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies = dialog.tempFamilies;
		
		converter.checkUserFamiliesForConsistency();
		
		System.out.println("\nUser defined families:");
		it = converter.userDefinedFamilies.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector members = (Vector)converter.userDefinedFamilies.get(key);
			for(int i=0;i<members.size();i++)
				System.out.print((String)members.get(i)+"\t");
			System.out.println();
		}
		
		/*System.out.println("\nMAPK.");
		Vector v = converter.entitiesForToken("(MAPK.)");
		for(int i=0;i<v.size();i++)
			System.out.println(v.get(i));
		System.out.println("\nMAPK8,MAPK9,MAPK10");
		v = converter.entitiesForToken("(MAPK8,MAPK9,MAPK10)");
		for(int i=0;i<v.size();i++)
			System.out.println(v.get(i));
		System.out.println("\nMAPK1,MAPK2");
		v = converter.entitiesForToken("(MAPK1,MAPK2)");
		for(int i=0;i<v.size();i++)
			System.out.println(v.get(i));*/
		
		HashMap reactionBackLinks = new HashMap(); 
		Vector reactions = converter.reactionList(text,reactionBackLinks);
		System.out.println("\nReactions:");
		for(int i=0;i<reactions.size();i++)
			System.out.println(""+(i+1)+")"+(String)reactions.get(i));
		
		Vector creactions = converter.constitutiveReactions();
		System.out.println("\nConstitutive reactions:");
		for(int i=0;i<creactions.size();i++)
			System.out.println(""+(i+1)+")"+(String)creactions.get(i));
		
		JFrame window1 = new JFrame();
		dialog1 = new ImportFromAINDialogConstitutiveReactions(window1,"Defing families",true);
		dialog1.pop();
		
		
		converter.makeBioPAX(text, creactions);
		
		converter.biopax.saveToFile(fname+".owl", converter.biopax.biopaxmodel);
		
		
		/*BioPAX biopax = converter.convertFromFile("c:/datas/binomtest/text/test.txt");
		biopax.saveToFile("c:/datas/binomtest/text/test.owl", biopax.biopaxmodel);
		

		BioPAXToCytoscapeConverter b2s = new BioPAXToCytoscapeConverter();
		b2s.biopax = biopax;
		
		BioPAXToCytoscapeConverter.Graph graph = b2s.convert
		(b2s.REACTION_NETWORK_CONVERSION,b2s,
		biopax.idName,
		new BioPAXToCytoscapeConverter.Option());*/
		
	
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
