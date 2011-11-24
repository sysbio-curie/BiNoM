package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;

public class testChangeProteinSize {

	public static void main(String[] args) {
		try{
			
			//SbmlDocument cd1 = CellDesigner.loadCellDesigner("c:/datas/basal/BER_NER.xml");
			SbmlDocument cd1 = CellDesigner.loadCellDesigner("C:/Datas/Basal/190410/CC_DNArepair_19_04_2010_1.xml");
			
			HashMap<String,String> speciesTypes = new HashMap<String,String>();
			for(int i=0;i<cd1.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
				SpeciesDocument.Species sp = cd1.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String type = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				speciesTypes.put(sp.getId(), type);
			}
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
				CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
				String type = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				speciesTypes.put(csp.getId(), type);
			}
			
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
				String id = csa.getSpecies();
				String type = speciesTypes.get(id);
				System.out.println(id+"\t"+type);
				if(type.equals("PROTEIN")){
					csa.getCelldesignerBounds().setH("40");
					csa.getCelldesignerBounds().setW("80");
				}
			}
			
			System.out.println("Saving");
			CellDesigner.saveCellDesigner(cd1, "C:/Datas/Basal/190410/CC_DNArepair_19_04_2010_2.xml");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
