package fr.curie.BiNoM.pathways.utils;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument.CelldesignerGene;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument.CelldesignerProtein;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument.CelldesignerRNA;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class ExtractGMTFromCellDesigner {
	
	public boolean useProteinNameIfHUGOisntFound = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			String fn = args[0];
			String fngmt = fn+".gmt";
			ExtractGMTFromCellDesigner ecd = new ExtractGMTFromCellDesigner();
			SbmlDocument cd = CellDesigner.loadCellDesigner(fn);
			ecd.extractGMTFromCellDesigner(cd, fngmt);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	  public HashMap<String, Vector<String>>  extractGMTFromCellDesigner(SbmlDocument cd, String fn){
		  HashMap<String, Vector<String>> groups = new HashMap<String, Vector<String>>();
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			  CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);  
			  if(protein.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(protein.getCelldesignerNotes());
				  processAnnotation(Utils.getValue(protein.getName()), notes, groups);
			  }
		  }
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			  CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);  
			  if(gene.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(gene.getCelldesignerNotes());
				  processAnnotation(gene.getName(), notes, groups);
			  }
		  }
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			  CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);  
			  if(rna.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(rna.getCelldesignerNotes());
				  processAnnotation(rna.getName(), notes, groups);
			  }
		  }
		  Iterator<String> it = groups.keySet().iterator();
		  try{
		  FileWriter fw = new FileWriter(fn);
		  while(it.hasNext()){
			  String module = it.next();
			  Vector<String> gnames = groups.get(module);
			  fw.write(module+"\tna\t");
			  for(int i=0;i<gnames.size();i++)
				  fw.write(gnames.get(i)+"\t");
			  fw.write("\n");
		  }
		  fw.close();
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return groups;
	  }
	  
	  public void processAnnotation(String name, String notes, HashMap<String, Vector<String>> groups){
		  Vector<String> hugos = Utils.getTagValues(notes, "HUGO");
		  Vector<String> cc_phase = Utils.getTagValues(notes, "CC_PHASE");		  
		  Vector<String> layer = Utils.getTagValues(notes, "LAYER");		  
		  Vector<String> pathway = Utils.getTagValues(notes, "PATHWAY");		  		  
		  Vector<String> checkpoints = Utils.getTagValues(notes,"CHECKPOINT");
		  Vector<String> modules = Utils.getTagValues(notes,"MODULE");
		  Vector<String> module_names = new Vector<String>();
		  module_names.addAll(cc_phase);
		  module_names.addAll(pathway);
		  module_names.addAll(layer);
		  module_names.addAll(checkpoints);
		  module_names.addAll(modules);		
		  if(useProteinNameIfHUGOisntFound){
			  if(hugos.size()==0)
				  if(name!=null)
					  hugos.add(name);
		  }
		  for(int i=0;i<module_names.size();i++){
			  String module = module_names.get(i);
			  Vector<String> names = groups.get(module);
			  if(names==null) { names = new Vector<String>(); groups.put(module, names); }
			  for(int j=0;j<hugos.size();j++)if(!names.contains(hugos.get(j))) names.add(hugos.get(j));
		  }
	  }	  
	

}
