package fr.curie.BiNoM.pathways.converters;

import java.util.*;
import java.io.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import vdaoengine.data.VDataTable;
import vdaoengine.data.io.*;

public class BioPAXIndex2Tables {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
		
		String prefix = "c:/Datas/BioBase/ver9/index/";
		String fn = "reaction_semantic_full";
		//String fn = "reaction_pathway_full";
		
		AccessionNumberTable acctable = new AccessionNumberTable();
		acctable.loadTable(prefix+"accnums_np");		
		
		/*FileWriter fw = new FileWriter(prefix+"accnums_np"); 
		Set keys = acctable.nameid.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Vector values = (Vector)acctable.nameid.get(key);
			for(int i=0;i<values.size();i++){
				String value = (String)values.get(i);
				if(key.startsWith("np")){
					fw.write("REFSEQ:"+key.toUpperCase()+"\t"+value+"\n");
				}
			//System.out.println(key+"\t"+value);
			}
		}
		fw.close();*/
		
		
		VDataTable annot = VDatReadWrite.LoadFromSimpleDatFile(prefix+"annot4.txt", true, "\t");
		annot.makePrimaryHash("Probeset");
		
		VDataTable annot1 = VDatReadWrite.LoadFromSimpleDatFile(prefix+"annot4.txt", true, "\t");
		annot1.makePrimaryHash("GeneSymbol");
		
		VDataTable annotHPRD = VDatReadWrite.LoadFromSimpleDatFile(prefix+"PROTEIN_NOMENCLATURE_mod.txt", true, "\t");
		annotHPRD.makePrimaryHashLowerCase("HUGO");
		VDataTable annotHPRD_NP = VDatReadWrite.LoadFromSimpleDatFile(prefix+"PROTEIN_NOMENCLATURE_mod.txt", true, "\t");
		for(int i=0;i<annotHPRD_NP.rowCount;i++){
			String s = annotHPRD_NP.stringTable[i][annotHPRD_NP.fieldNumByName("NP")];
			if(s!=null)
			if(s.contains("."))
				s = s.substring(0,s.length()-2);
			annotHPRD_NP.stringTable[i][annotHPRD_NP.fieldNumByName("NP")] = s;
		}
		annotHPRD_NP.makePrimaryHashLowerCase("NP");
		
		
		GraphXGMMLParser gp = new GraphXGMMLParser();
		gp.parse(prefix+fn+".xgmml");
		Graph database = gp.graph;
		
		FileWriter fwe = new FileWriter(prefix+fn+"_entities.xls");
		FileWriter fwr = new FileWriter(prefix+fn+"_connections.xls");
		FileWriter fwc = new FileWriter(prefix+fn+"_complexes.xls");
		
		fwe.write("ID\tNAME\tTYPE\tSYNONYMS\tIDS\tCOMMENT\tHUGO\tHPRD_ID\tHPRD_NAME\n");
		fwr.write("ID\tLABEL\tTYPE\tSOURCE_ID\tTARGET_ID\tSOURCE_NAME\tTARGET_NAME\tEFFECT\tCOMMENT\tREFS\n");
		fwc.write("SOURCE_ID\tTARGET_ID\tSOURCE_NAME\tTARGET_NAME\n");
		
    	for(int i=0;i<database.Nodes.size();i++){
    		String type = "-";
    		String entity = "-";
    		Node n = ((Node)database.Nodes.get(i));
    		if(n.getAttributesWithSubstringInName("NODE_TYPE")!=null){
    			if(((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).size()>0)
    				type = ((Attribute)((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).get(0)).value;
    		}
    		
    		String id = ((Attribute)((Vector)n.getAttributesWithSubstringInName("BIOPAX_URI")).get(0)).value;
    		id = Utils.cutUri(id); if(id.endsWith("e")) id = id.substring(0, id.length()-1);
    		
			if(type.toLowerCase().equals("protein")||type.toLowerCase().equals("dna")||type.toLowerCase().equals("physicalentity")){
				Vector v = n.getAttributesWithSubstringInName("_SPECIES");
				if((v==null)||(v.size()==0)){
					if(type.equals("dna")){
						String ss = n.NodeLabel;
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						fwe.write(id+"\t"+ss+"\t"+"gene"+"\t");
					}
					if(type.equals("protein")){
						String ss = n.NodeLabel;
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						fwe.write(id+"\t"+ss+"\t"+"protein"+"\t");
					}else
					if(type.toLowerCase().equals("physicalentity")){
						String ss = n.NodeLabel;
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						fwe.write(id+"\t"+ss+"\t"+"family"+"\t");
					}
					v = n.getAttributesWithSubstringInName("SYNONYM");
					String synonyms = "";
					for(int k=0;k<v.size();k++){
						String ss = ((Attribute)v.get(k)).value;
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						synonyms+=ss+";";
					}
					Vector<Attribute> vsynonyms = n.getAttributesWithSubstringInName("SYNONYM");
					String comments = "";
					v = n.getAttributesWithSubstringInName("XREF");
					for(int k=0;k<v.size();k++){
						String ss = ((Attribute)v.get(k)).value;
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						comments+=ss+";";
					}
					String ids = "";
		    		String hugos = "";
		    		Vector<String> vhugos = new Vector<String>(); 
		    		v = (Vector)acctable.idname.get(id);
		    		if(v!=null)
					for(int k=0;k<v.size();k++){
						String ss = ((String)v.get(k));
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						ids+=ss+";";
						Vector<Integer> vint = annot.tableHashPrimary.get(ss);
						if((vint!=null)&&(vint.size()>0)){
							for(int l=0;l<vint.size();l++){
								String hugo = annot.stringTable[vint.get(l)][annot.fieldNumByName("GeneSymbol")];
								if(vhugos.indexOf(hugo)<0)
									vhugos.add(hugo);
							}
						}
						vint = annot1.tableHashPrimary.get(ss);
						if((vint!=null)&&(vint.size()>0)){
							vhugos.clear();
							vhugos.add(ss);
						}
					}
		    		if(vsynonyms!=null){
						for(int k=0;k<vsynonyms.size();k++){
							String ss = ((Attribute)vsynonyms.get(k)).value;
							ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
							Vector<Integer> vint = annot1.tableHashPrimary.get(ss);
							if((vint!=null)&&(vint.size()>0)){
								vhugos.clear();
								vhugos.add(ss);
							}
						}
		    		}
					for(int k=0;k<vhugos.size();k++){
						String ss = ((String)vhugos.get(k));
						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
						hugos+=ss+";";
					}
					if(hugos.length()>0)
						hugos = hugos.substring(0, hugos.length()-1);

					String hprd_id = null;
					String hprd_name = null;					


					/*Vector<String> nps = new Vector<String>();
		    		v = (Vector)acctable.idname.get(id);
		    		if(v!=null)
		    			for(int k=0;k<v.size();k++){
		    				String ss = ((String)v.get(k));
		    				if(ss.startsWith("NP"))
		    					nps.add(ss);
		    			}*/
					
					
					// Search in HPRD by NP					
					// If nothing has been found then search in HPRD directly by name
					Vector<String> names = new Vector<String>();
					String name  = n.NodeLabel;
					if(type.equals("gene")) name = name.substring(1, name.length());
					names.add(name);
					if(hugos.length()>0)
						names.add(vhugos.get(0));
					for(int k=0;k<vsynonyms.size();k++)
						names.add(vsynonyms.get(k).value);
		    		v = (Vector)acctable.idname.get(id);
		    		if(v!=null)
		    			for(int k=0;k<v.size();k++)
		    				names.add(((String)v.get(k)));
					
		    		Vector<String> namesFound = new Vector<String>();
		    		Vector<String> idsFound = new Vector<String>();
		    		for(int k=0;k<names.size();k++){
		    			//if(names.get(k).toLowerCase().startsWith("np_"))
		    			//	System.out.println("NP searched "+names.get(k));
		    			Vector<Integer> vint = annotHPRD_NP.tableHashPrimary.get(names.get(k).toLowerCase());
		    			if((vint!=null)&&(vint.size()>0)){
		    				//System.out.println("...found ");
		    				for(int l=0;l<vint.size();l++){
		    					namesFound.add(annotHPRD.stringTable[vint.get(l)][annotHPRD.fieldNumByName("HUGO")]);
		    					idsFound.add(annotHPRD.stringTable[vint.get(l)][annotHPRD.fieldNumByName("ID")]);
		    				}
		    			}else{
		    			vint = annotHPRD.tableHashPrimary.get(names.get(k).toLowerCase());
		    			if((vint!=null)&&(vint.size()>0))
		    				for(int l=0;l<vint.size();l++){
		    					namesFound.add(annotHPRD.stringTable[vint.get(l)][annotHPRD.fieldNumByName("HUGO")]);
		    					idsFound.add(annotHPRD.stringTable[vint.get(l)][annotHPRD.fieldNumByName("ID")]);
		    				}
		    			}
		    		}
		    		Vector<String> namesFoundUnique = new Vector<String>();
		    		Vector<String> idFoundUnique = new Vector<String>();
		    		Vector<Integer> namesFoundUniqueCount = new Vector<Integer>();
		    		for(int k=0;k<namesFound.size();k++){
		    			int l = namesFoundUnique.indexOf(namesFound.get(k));
		    			if(l<0){
		    				namesFoundUnique.add(namesFound.get(k));
		    				namesFoundUniqueCount.add(1);
		    				idFoundUnique.add(idsFound.get(k));
		    			}else{
		    				namesFoundUniqueCount.set(l, namesFoundUniqueCount.get(l)+1);
		    			}
		    		}
		    		if(namesFoundUnique.size()==1){
		    			hprd_id = idsFound.get(0);
		    			hprd_name = namesFound.get(0);
		    		}
		    		if(namesFoundUnique.size()>1){
		    			System.out.println("Warning: found several equivalent names for: "+id);
		    			hprd_name = "";
		    			int countMax = -1;
		    			for(int m=0;m<namesFoundUnique.size();m++){
		    				System.out.println("\t"+namesFoundUnique.get(m)+"\t"+namesFoundUniqueCount.get(m));
		    				if(namesFoundUniqueCount.get(m)>countMax){
		    					countMax = namesFoundUniqueCount.get(m);
		    					hprd_name = namesFoundUnique.get(m);
		    					hprd_id = idFoundUnique.get(m);
		    				}
		    			}
		    			
		    		}
					
					if((hprd_name!=null)||(hprd_id!=null))
						fwe.write(synonyms+"\t"+ids+"\t"+comments+"\t"+hugos+"\t"+hprd_id+"\t"+hprd_name+"\n");
					else
						fwe.write(synonyms+"\t"+ids+"\t"+comments+"\t"+hugos+"\t\t\n");
				}
			}
    	}
    	
    	database.calcNodesInOut();
    	for(int i=0;i<database.Nodes.size();i++){
    		String type = "-";
    		String entity = "-";
    		Node n = ((Node)database.Nodes.get(i));
    		if(n.getAttributesWithSubstringInName("NODE_TYPE")!=null){
    			if(((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).size()>0)
    				type = ((Attribute)((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).get(0)).value;
    		}
    		String id = ((Attribute)((Vector)n.getAttributesWithSubstringInName("BIOPAX_URI")).get(0)).value;
    		id = Utils.cutUri(id); if(id.endsWith("e")) id = id.substring(0, id.length()-1);
    		Vector v = n.getAttributesWithSubstringInName("_REACTION");
    		
			if(v.size()>0){
				//System.out.println(type);
				Vector<Node> Reactants = new Vector<Node>();
				Vector<Node> Products = new Vector<Node>();
				Vector<Node> Activators = new Vector<Node>();
				Vector<Node> Inhibitors = new Vector<Node>();
				Vector<Node> References = new Vector<Node>();
				int reaction_effect = 0; 
				for(int k=0; k<n.incomingEdges.size(); k++){
					Edge e = (Edge)n.incomingEdges.get(k);
					if(e.getAttributesWithSubstringInName("TYPE").size()>0){
						type = ((Attribute)(e.getAttributesWithSubstringInName("TYPE").get(0))).value.toLowerCase();
						//System.out.println("IN:"+((Attribute)(e.getAttributesWithSubstringInName("TYPE").get(0))).value);						
						if(type.equals("left"))
							Reactants.add(e.Node1);
						if(type.contains("activation"))
							Activators.add(e.Node1);
						if(type.contains("inhibition"))
							Inhibitors.add(e.Node1);
						if(type.contains("reference"))
							References.add(e.Node1);
					}	
				}
				for(int k=0; k<n.outcomingEdges.size(); k++){
					Edge e = (Edge)n.outcomingEdges.get(k);
					if(e.getAttributesWithSubstringInName("TYPE").size()>0){
						type = ((Attribute)(e.getAttributesWithSubstringInName("TYPE").get(0))).value.toLowerCase();
						//System.out.println("IN:"+((Attribute)(e.getAttributesWithSubstringInName("TYPE").get(0))).value);						
						if(type.equals("right"))
							Products.add(e.Node2);
					}
				}
				String effect = "";
				if(n.getAttributesWithSubstringInName("EFFECT").size()>0){
					effect = ((Attribute)n.getAttributesWithSubstringInName("EFFECT").get(0)).value.toLowerCase();
					//System.out.println(effect);
					if(effect.contains("activation")||effect.contains("stabilisation")||effect.contains("expression")||effect.contains("increase"))
						reaction_effect = 1;
					if(effect.contains("inhibition")||effect.contains("cleavage")||effect.contains("degradation")||effect.contains("destabilization")||effect.contains("repression")||effect.contains("ubiquitination"))
						reaction_effect = -1;
				}
				
				int count = 1;
				//System.out.println(Reactants.size()+"\t"+Products.size());
				for(int k=0;k<Reactants.size();k++)
					for(int l=0;l<Products.size();l++){
						Node reactant = Reactants.get(k);
						Node product = Products.get(l);
						if(!reactant.Id.equals(product.Id)){
							writeConnection(fwr, n,effect,reaction_effect,reactant,product, References, count);
							count++;
						}
					}
				for(int k=0;k<Activators.size();k++)
					for(int l=0;l<Products.size();l++){
						Node reactant = Activators.get(k);
						Node product = Products.get(l);
						if(!reactant.Id.equals(product.Id)){
							writeConnection(fwr, n,"CATOF:"+effect,reaction_effect,reactant,product, References, count);
							count++;
						}
					}
				for(int k=0;k<Inhibitors.size();k++)
					for(int l=0;l<Products.size();l++){
						Node reactant = Inhibitors.get(k);
						Node product = Products.get(l);
						if(!reactant.Id.equals(product.Id)){
							writeConnection(fwr, n,"CATOF:"+effect,-reaction_effect,reactant,product, References, count);
							count++;
						}
					}				
				
			}
    	}

    	System.out.println("Listing complexes...");
    	for(int i=0;i<database.Nodes.size();i++){
    		String type = "-";
    		String entity = "-";
    		Node n = ((Node)database.Nodes.get(i));
    		if(n.getAttributesWithSubstringInName("NODE_TYPE")!=null){
    			if(((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).size()>0)
    				type = ((Attribute)((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).get(0)).value;
    		}
    		String id = ((Attribute)((Vector)n.getAttributesWithSubstringInName("BIOPAX_URI")).get(0)).value;
    		id = Utils.cutUri(id); if(id.endsWith("e")) id = id.substring(0, id.length()-1);
    		//System.out.println(type);
    		if(type.toLowerCase().equals("complex")){
    			for(int j=0;j<n.incomingEdges.size();j++){
    				Edge e = (Edge)n.incomingEdges.get(j);
    				String etype = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
    				if(etype.equals("CONTAINS")){
    					String compid = Utils.cutUri(e.Node1.getFirstAttributeValue("BIOPAX_URI"));
    					if(compid.endsWith("e")) compid = compid.substring(0, compid.length()-1);
    					fwc.write("COM_"+id+"\t"+compid+"\t"+n.Id+"\t"+e.Node1.Id+"\n");
    				}
    			}
    		}
    	}
		
    	
    	fwe.close();
    	fwr.close();
    	fwc.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void writeConnection(FileWriter fw, Node n, String effect,int reaction_effect, Node reactant, Node product, Vector<Node> References, int count) throws Exception{
		//fwr.write("ID\tLABEL\tTYPE\tSOURCE_ID\tTARGET_ID\tSOURCE_NAME\tTARGET_NAME\tEFFECT\tCOMMENT\tREFS\n");
		String uri = Utils.cutUri(((Attribute)n.getAttributesWithSubstringInName("URI").get(0)).value);
		String reactant_uri = Utils.cutUri(((Attribute)reactant.getAttributesWithSubstringInName("URI").get(0)).value);
		String product_uri = Utils.cutUri(((Attribute)product.getAttributesWithSubstringInName("URI").get(0)).value);
		if(reactant_uri.endsWith("e")) reactant_uri = reactant_uri.substring(0, reactant_uri.length()-1);
		if(product_uri.endsWith("e")) product_uri = product_uri.substring(0, product_uri.length()-1);
		String id = uri+"."+count;
		String refs = "";
		for(int i=0;i<References.size();i++)
			refs+=References.get(i).Id+";";
		if(refs.length()>0) refs = refs.substring(0,refs.length()-1);
		String connector = ".>";
		if(reaction_effect==1)
			connector = "->";
		if(reaction_effect==-1)
			connector = "-|";
		String rid = reactant.Id;
		if(rid.contains("@")) rid = rid.substring(0, rid.indexOf("@"));
		String pid = product.Id;
		if(pid.contains("@")) pid = pid.substring(0, pid.indexOf("@"));
		String connection_label = rid+connector+pid;
		fw.write(id+"\t"+connection_label+"\t"+connector+"\t"+reactant_uri+"\t"+product_uri+"\t"+rid+"\t"+pid+"\t"+effect+"\t"+n.Id+"\t"+refs+"\n");
	}

}
