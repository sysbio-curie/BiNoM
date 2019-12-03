package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import org.sbml.x2001.ns.celldesigner.*;

import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.test.ProduceClickableMap.Entity;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class testRbModules extends ProduceClickableMap {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		try{
			testRbModules rbMod = new testRbModules(); 

			//rbMod.showModuleTableInCelldesigner("c:/datas/rbmaps/rbmodules/RB_modules.xml","c:/datas/rbmaps/rbmodules/data/bcpublic/");			
			//System.exit(0);
			
			rbMod.loadCellDesigner("c:/datas/rbmaps/rb.xml");
			rbMod.findAllPlacesInCellDesigner();
			rbMod.updateStandardNames();
			
			//rbMod.makeModuleFiles("c:/datas/rbmaps/modules/xgmml");
			
			//rbMod.readModules("c:/datas/rbmaps/modules_species.gmt", "c:/datas/rbmaps/modules_proteins.gmt", "c:/datas/rbmaps/c2.v2.symbols.gmt");
			rbMod.readModules("c:/datas/rbmaps/modules_species.gmt", "c:/datas/rbmaps/rbmodules/modules_proteins_c2.gmt", "c:/datas/netpath/netpath.gmt");
			//rbMod.correctNamesInModules();			
			//rbMod.makeModuleMaps("c:/datas/rbmaps/", "rb", "modules");
			
			//rbMod.calcPathwayOverlap();
			rbMod.calcModulesOverlap();
			//rbMod.listNotFound();
			//rbMod.printIntersection();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void makeModuleFiles(String path) throws Exception{
		
		File f = new File(path);
		FileWriter fw = new FileWriter(path+"/modules_species.gmt");
		FileWriter fw1 = new FileWriter(path+"/modules_proteins.gmt");		
		File modules[] = f.listFiles();
		for(int i=0;i<modules.length;i++){
			if(modules[i].getName().endsWith(".xgmml")){
				Vector prot_names = new Vector();				
				String name = modules[i].getName().substring(0,modules[i].getName().length()-6);
				if(name.endsWith(".xml"))
					name = name.substring(0,name.length()-4);
				fw.write(name+"\tna\t");
				fw1.write(name+"\tna\t");
				GraphDocument grDoc = XGMML.loadFromXMGML(modules[i].getAbsolutePath());
				Graph graph = XGMML.convertXGMMLToGraph(grDoc);
				for(int j=0;j<graph.Nodes.size();j++){
					Node n = (Node)graph.Nodes.get(j);
					String reaction = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
					if(reaction!=null) fw.write(reaction+"\t");
					String species = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
					if(species!=null){
						
						System.out.println(species);
						
						fw.write(species+"\t");
						Vector v = (Vector)speciesEntities.get(species);
						if(v==null)
							System.out.println("NOT FOUND");
						else
						for(int k=0;k<v.size();k++){
							String pname = ((ProduceClickableMap.Entity)v.get(k)).label;
							if(prot_names.indexOf(pname)<0)
								prot_names.add(pname);
						}
					}
				}
				for(int k=0;k<prot_names.size();k++)
					fw1.write((String)prot_names.get(k)+"\t");
				
				fw.write("\n");
				fw1.write("\n");
			}
		}
		fw.close();
		fw1.close();
	}
	
	public void makeModuleMaps(String path, String pathname, String folder) throws Exception{
		
		Iterator keys = module_species.keySet().iterator();
		while(keys.hasNext()){
			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
			
			String mname = (String)keys.next();
			Vector v = (Vector)module_species.get(mname);
			
			for(int i=0;i<v.size();i++){
				reactions.add((String)v.get(i));
			}

			HashMap hm = CellDesigner.entities;
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();			
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+mname+".xml");
			CellDesigner.entities = hm;
			
		}
		
	}
	
	public void correctNamesInModules(){
		
		HashMap map = new HashMap();
		
		Iterator ents = entities.keySet().iterator();
		while(ents.hasNext()){
			Entity ent = (Entity)entities.get(ents.next());
			if(ent.label!=null)
				if(ent.standardName!=null)
			if(!ent.label.equals(ent.standardName))
				map.put(ent.label, ent.standardName);
		}
		
		Iterator modules = module_proteins.keySet().iterator();
		while(modules.hasNext()){
			String mname = (String)modules.next();
		Vector v = (Vector)module_proteins.get(mname);
		for(int i=0;i<v.size();i++){
			String gene = (String)v.get(i);
			if(map.get(gene)!=null)
				v.set(i, (String)map.get(gene));
		}
		}
	}
	
	public void listNotFound(){
		Vector notfound = new Vector();
		Iterator modules = module_proteins.keySet().iterator();
		while(modules.hasNext()){
			String mname = (String)modules.next();
		Vector v = (Vector)module_proteins.get(mname);
		for(int i=0;i<v.size();i++){
			String gene = (String)v.get(i);
			Vector mods = getModuleName(pathways, gene);
			if(mods.size()==0) 
				if(notfound.indexOf(gene)<0)
					notfound.add(gene);
		}
		}
		Collections.sort(notfound);
		for(int i=0;i<notfound.size();i++){
			System.out.println((String)notfound.get(i));
		}
	}
	
	public void calcModulesOverlap(){
		Vector counts = new Vector();
		Vector module_sizes = new Vector();
		Iterator modules = module_proteins.keySet().iterator();
		System.out.print("PATHWAY\tSIZE\t");

		while(modules.hasNext()){
			String mname = (String)modules.next();
			System.out.print(mname+"\t"+mname+"_ov\t"+mname+"_p\t");
			HashMap count = new HashMap();
			Iterator it = pathways.keySet().iterator();
			while(it.hasNext()){
				String pname = (String)it.next();
				count.put(pname, new Integer(0));
			}
			counts.add(count);
			int module_size = 0;
			Vector v = (Vector)module_proteins.get(mname);
			for(int i=0;i<v.size();i++){
				String gene = (String)v.get(i);
				Vector mods = getModuleName(pathways, gene);
				if(mods.size()!=0) module_size++;
			}
			module_sizes.add(new Integer(module_size));
		}
		System.out.println();
		
		Iterator it = module_proteins.keySet().iterator();
		int k = 0;
		while(it.hasNext()){
			String mname = (String)it.next();
			HashMap count = (HashMap)counts.get(k); k++;
			Vector pgenes = (Vector)module_proteins.get(mname);
			for(int i=0;i<pgenes.size();i++){
				String gene = (String)pgenes.get(i);
				Vector pathws = getModuleName(pathways, gene);
				for(int j=0;j<pathws.size();j++){
					String pname = (String)pathws.get(j);
					Integer c = (Integer)count.get(pname);
					count.put(pname, new Integer(c+1));
				}
			}
		}

		Iterator itpath = pathways.keySet().iterator();
		while(itpath.hasNext()){
			String pname = (String)itpath.next();
			Vector pgenes = (Vector)pathways.get(pname);
			System.out.print(pname+"\t"+pgenes.size()+"\t");
		
			it = module_proteins.keySet().iterator();
			k = 0;
			while(it.hasNext()){
				String mname = (String)it.next();
				HashMap count = (HashMap)counts.get(k);  
				Integer c = (Integer)count.get(pname);
				int module_size = ((Integer)module_sizes.get(k)).intValue(); 
				k++;
				System.out.print(module_size+"\t"+c.intValue()+"\t0\t");
			}
			System.out.println();
		}
		
	}
	
	
	public void calcPathwayOverlap(){
		HashMap pathwayCountsProteins = new HashMap();
		HashMap pathwayCountsGenes = new HashMap();
		HashMap pathwayCountsTotal = new HashMap();
		HashMap proteinNames = new HashMap();
		Iterator it = pathways.keySet().iterator();
		int numproteins = 0;
		int numgenes = 0;
		Set allgenes = new HashSet();
		
		while(it.hasNext()){
			String mname = (String)it.next();
			pathwayCountsProteins.put(mname, new Integer(0));
			pathwayCountsGenes.put(mname, new Integer(0));
			pathwayCountsTotal.put(mname, new Integer(0));
			proteinNames.put(mname,new Vector());
			Vector vv = (Vector)pathways.get(mname);
			for(int i=0;i<vv.size();i++)
				allgenes.add((String)vv.get(i));
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			Entity ent = (Entity)entities.get(prot.getId());
			Vector v = getModuleName(pathways, ent.standardName);
			if(v.size()>0) numproteins++;
			for(int j=0;j<v.size();j++){
				String mname = (String)v.get(j);
				Integer count = (Integer)pathwayCountsProteins.get(mname);
				count = new Integer(count.intValue()+1);
				pathwayCountsProteins.put(mname,count);
				count = (Integer)pathwayCountsTotal.get(mname);
				count = new Integer(count.intValue()+1);
				pathwayCountsTotal.put(mname,count);
				Vector names  = (Vector)proteinNames.get(mname);
				names.add(ent.standardName);
			}
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			Entity ent = (Entity)entities.get(gene.getId());			
			Vector v = getModuleName(pathways, ent.standardName);
			if(v.size()>0) numgenes++;
			for(int j=0;j<v.size();j++){
				String mname = (String)v.get(j);
				Integer count = (Integer)pathwayCountsGenes.get(mname);
				count = new Integer(count.intValue()+1);
				pathwayCountsGenes.put(mname,count);
				Vector names  = (Vector)proteinNames.get(mname);
				if(names.indexOf(ent.standardName)<0){
					count = (Integer)pathwayCountsTotal.get(mname);
					count = new Integer(count.intValue()+1);
					pathwayCountsTotal.put(mname,count);
					names.add(ent.standardName);
				}
			}
		}
		
		System.out.println("Number of proteins = "+numproteins);
		System.out.println("Number of genes = "+numgenes);
		System.out.println("Number proteins+genes = "+(numgenes+numproteins));
		System.out.println("Total genes in all pathways = "+(allgenes.size()));
		
		System.out.println("PATHWAY\tSIZE\tPROTEINS\tGENES\tTOTAL");		
		
		it = pathwayCountsProteins.keySet().iterator();
		while(it.hasNext()){
			String mname = (String)it.next();
			int proteins = ((Integer)pathwayCountsProteins.get(mname)).intValue();
			int genes = ((Integer)pathwayCountsGenes.get(mname)).intValue();
			int total = ((Integer)pathwayCountsTotal.get(mname)).intValue();
			System.out.println(mname+"\t"+((Vector)pathways.get(mname)).size()+"\t"+proteins+"\t"+genes+"\t"+total);
		}
	}
	
	public void printIntersection(){
		Iterator it = pathways.keySet().iterator();
		HashMap names = new HashMap();
		
		while(it.hasNext()){
			String mname = (String)it.next();
			names.put(mname, new Vector());
		}
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			Entity ent = (Entity)entities.get(prot.getId());
			Vector v = getModuleName(pathways, ent.standardName);
			if(v.size()>0) 
			for(int j=0;j<v.size();j++){
				String mname = (String)v.get(j);
				Vector nms = (Vector)names.get(mname);
				nms.add(ent.standardName);
			}
		}
		
		it = names.keySet().iterator();
		while(it.hasNext()){
			String mname = (String)it.next();
			Vector v = (Vector)names.get(mname);
			System.out.print(mname+"\t");
			Collections.sort(v);
			for(int i=0;i<v.size();i++)
				System.out.print((String)v.get(i)+"\t");
			System.out.println();
		}
		
		
	}
	
	public static void showModuleTableInCelldesigner(String fn, String activityfile) throws Exception{
		SbmlDocument sbmlDoc = CellDesigner.loadCellDesigner(fn);
		
	}

	
}
