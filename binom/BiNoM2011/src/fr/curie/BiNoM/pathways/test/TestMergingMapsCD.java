package fr.curie.BiNoM.pathways.test;

import java.util.HashMap;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;

import edu.rpi.cs.xgmml.AttDocument;
import edu.rpi.cs.xgmml.GraphDocument;
import edu.rpi.cs.xgmml.GraphicNode;
import fr.curie.BiNoM.pathways.utils.Pair;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class TestMergingMapsCD {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		/*
		 * replace text test
		 */

		//		//String fileName = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml";
		//		String fileName = "/bioinfo/users/ebonnet/test.xml";
		//		//String fileName = "/bioinfo/users/ebonnet/Binom/mergeMaps/mTOR_scratch_v31.xml";
		//		SbmlDocument cd = CellDesigner.loadCellDesigner(fileName);
		//		String text = cd.toString();
		//		String prefix  = "rz_";
		//		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		//		for(int i=0;i<ids.size();i++)
		//			if (!ids.get(i).equals("default") && !ids.get(i).startsWith("rb_")) {
		//				System.out.println(i+" => "+ids.get(i));
		//				text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
		//				text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
		//			}
		//		SbmlDocument res = CellDesigner.loadCellDesignerFromText(text);
		//		CellDesigner.saveCellDesigner(res, "/bioinfo/users/ebonnet/res.xml");


		String fn = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml";
		SbmlDocument cd = CellDesigner.loadCellDesigner(fn);
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			//System.out.println(sp.get);
		}

	}




	public static void modifyPositionOfSpecies(SbmlDocument cd, GraphDocument gr){

		HashMap<String,Pair> coordinates = new HashMap<String,Pair>();
		double maxx = 0;
		double maxy = 0;
		double minx = Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;

		/*
		 * get max x and y from graphdocument
		 */
		for(int i=0;i<gr.getGraph().getNodeArray().length;i++){
			GraphicNode n = gr.getGraph().getNodeArray(i);
			String alias = ""; 
			for(int j=0;j<n.getAttArray().length;j++){
				AttDocument.Att att = n.getAttArray(j);
				if(att.getName().equals("CELLDESIGNER_ALIAS"))
					alias = att.getValue();
			}
			double x = n.getGraphics().getX();
			double y = n.getGraphics().getY();
			if(!alias.equals("")){
				coordinates.put(alias, new Pair(x,y));
				if(x>maxx) maxx = x;
				if(y>maxy) maxy = y;
				if(x<minx) minx = x;
				if(y<miny) miny = y;				
			}
		}

		int width = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		int height = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());

		width=width-200;
		height=height-200;

		/*
		 * modify coordinates for species aliases and store them in a hash
		 */
		HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases = new HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			// get id for the alias
			String alid = cdal.getId();
			// get coordinate pair for the alias
			Pair p = coordinates.get(alid);
			if(p!=null){
				// get actual values
				double x = (((Double)p.o1).doubleValue());
				double y = (((Double)p.o2).doubleValue());
				// shift coordinates 
				if(minx<0) 
					x=x-minx;
				if(miny<0) 
					y=y-miny;
				// set coordinates for the element 
				cdal.getCelldesignerBounds().setX(""+x);
				cdal.getCelldesignerBounds().setY(""+y);
			}
			aliases.put(cdal.getSpecies(), cdal);
		}

		// store species included in complexes 
		HashMap<String,Vector<CelldesignerSpecies>> complexes = new HashMap<String,Vector<CelldesignerSpecies>>();
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
				CelldesignerSpecies si = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
				if(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies()!=null){
					String csid = Utils.getText(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
					Vector<CelldesignerSpecies> v = complexes.get(csid);
					if(v==null)	v = new Vector<CelldesignerSpecies>();
					v.add(si);
					complexes.put(csid, v);
				}
			}

		
		if(maxx==minx) { minx = -(double)width/2; maxx = (double)width/2; }
		if(maxy==miny) { miny = -(double)width/2; maxy = (double)width/2; }
		
		/*
		 * modify coordinates for complex species aliases
		 */
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				String alid = cdal.getId();
				String alspid = cdal.getSpecies();
				Pair p = coordinates.get(alid);
				double initx = Double.parseDouble(cdal.getCelldesignerBounds().getX());
				double inity = Double.parseDouble(cdal.getCelldesignerBounds().getY());
				if(p!=null){
					double x = (int)(((Double)p.o1).doubleValue());
					double y = (int)(((Double)p.o2).doubleValue());
					if(minx<0) x=x-minx;
					if(miny<0) y=y-miny;
					cdal.getCelldesignerBounds().setX(""+x);
					cdal.getCelldesignerBounds().setY(""+y);
					Vector<CelldesignerSpecies> v = complexes.get(alspid);
					if(v!=null){
						for(int j=0;j<v.size();j++){
							CelldesignerSpecies cs = v.get(j);
							//System.out.println("\t"+cs.getId());
							CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spal = aliases.get(cs.getId());
							double xx = Double.parseDouble(spal.getCelldesignerBounds().getX());
							double yy = Double.parseDouble(spal.getCelldesignerBounds().getY());
							spal.getCelldesignerBounds().setX(""+(xx-initx+x));
							spal.getCelldesignerBounds().setY(""+(yy-inity+y));					
						}
					}
				}
			}
	}

}
