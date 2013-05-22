package fr.curie.BiNoM.pathways.utils.acsn;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationResidueDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerNameDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class ACSNProcedures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			String xmlFileName = null;
			String xmlOutFileName = null;			
			String pngFileName1 = null;
			String pngFileName2 = null;
			String pngFileName3 = null;	
			String pngOutFileName = null;
			String mergeConfigFileName = null;
			String tableOfIDsFileName = null; 
			float transparency1 = 1f;
			float transparency2 = 1f;
			float transparency3 = 1f;			
			int fontsize = 12;
			
			boolean removeReactions = false;
			boolean mergepngs = false;
			boolean scalepng = false;
			boolean makehiddenlevel2 = false;
			boolean updateDBconnections = false;
			
			//doRemoveReactions("C:/Datas/NaviCell/test/merged/merged_master.xml");
			//doMergePngs("C:/Datas/NaviCell/test/merged/merged_master-3.png",1f,"C:/Datas/NaviCell/test/merged/merged_master_noreactions.png",0.5f,null,1f,"C:/Datas/NaviCell/test/merged/merged_master-3_1.png");
			//doMakeHiddenLevel2("C:/Datas/acsn/survival_merge/hedgehog.xml", "C:/Datas/acsn/survival_merge/hedgehog_level2.xml", 20);
			//doScalePng("C:/Datas/NaviCell/test/merged/merged_master-3_1.png","C:/Datas/NaviCell/test/merged/merged_master-2_1.png");
			//doScalePng("C:/Datas/acsn/survival_merge/hedgehog-3-1.png", "C:/Datas/acsn/survival_merge/hedgehog-2-1.png");
			updateLinks("c:/datas/acsn/assembly/merge_config","c:/datas/acsn/assembly/connectionDBTable.txt");
			
			for(int i=0;i<args.length;i++){
				if(args[i].equals("--xml"))
					xmlFileName = args[i+1];
				if(args[i].equals("--xmlout"))
					xmlOutFileName = args[i+1];				
				if(args[i].equals("--removereactions"))
					removeReactions = true;
				if(args[i].equals("--mergepngs"))
					mergepngs = true;
				if(args[i].equals("--scalepng"))
					scalepng = true;
				if(args[i].equals("--makezoomlevel2"))
					makehiddenlevel2 = true;
				
				if(args[i].equals("--png1"))
					pngFileName1 = args[i+1];
				if(args[i].equals("--png2"))
					pngFileName2 = args[i+1];
				if(args[i].equals("--png3"))
					pngFileName3 = args[i+1];
				if(args[i].equals("--pngout"))
					pngOutFileName = args[i+1];
				if(args[i].equals("--transparency1"))
					transparency1 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--transparency2"))
					transparency2 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--transparency3"))
					transparency3 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--fontsize"))
					fontsize = Integer.parseInt(args[i+1]);
				
				if(args[i].equals("--updatedbids"))
					updateDBconnections = Boolean.parseBoolean(args[i+1]);
				if(args[i].equals("--mergeconfig"))
					mergeConfigFileName = args[i+1];
				if(args[i].equals("--idfile"))
					tableOfIDsFileName = args[i+1];
				
				
			}
			
			if(removeReactions)
				doRemoveReactions(xmlFileName, xmlOutFileName);
			
			if(mergepngs)
				doMergePngs(pngFileName1,transparency1,pngFileName2,transparency2,pngFileName3,transparency3, pngOutFileName);
			
			if(scalepng)
				doScalePng(pngFileName1, pngOutFileName);
			
			if(makehiddenlevel2)
				doMakeHiddenLevel2(xmlFileName, xmlOutFileName, fontsize);
			
			if(updateDBconnections)
				updateLinks(mergeConfigFileName, tableOfIDsFileName);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void doRemoveReactions(String xmlFileName, String xmlOutFileName){
		String xmlFileNamePrefix = "";
		if(xmlFileName.endsWith(".xml"))
			xmlFileNamePrefix = xmlFileName.substring(0,xmlFileName.length()-4);
		SbmlDocument cd = CellDesigner.loadCellDesigner(xmlFileName);
		if(cd.getSbml().getModel().getListOfReactions()!=null)
			cd.getSbml().getModel().setListOfReactions(null);
		if(xmlOutFileName==null)
			CellDesigner.saveCellDesigner(cd, xmlFileNamePrefix+"_noreactions.xml");
		else
			CellDesigner.saveCellDesigner(cd, xmlOutFileName);
	}
	
	public static void doMergePngs(String pngFileName1,float transparency1,String pngFileName2,float transparency2,String pngFileName3,float transparency3, String pngOutFileName) throws Exception{
		BufferedImage map1 = ImageIO.read(new File(pngFileName1));
		Graphics2D g = map1.createGraphics();
		if(pngFileName2!=null){
			BufferedImage map2 = ImageIO.read(new File(pngFileName2));
			if(transparency2<1f)
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transparency2));
			Image map2t = Utils.Transparency.makeColorTransparent(map2, new Color(1f, 1f, 1f));
			g.drawImage(map2t, 0, 0, null);
		}
		if(pngFileName3!=null){
			BufferedImage map3 = ImageIO.read(new File(pngFileName3));
			if(transparency3<1f)
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transparency3));
			Image map3t = Utils.Transparency.makeColorTransparent(map3, new Color(1f, 1f, 1f));
			g.drawImage(map3t, 0, 0, null);		
		}
		ImageIO.write(map1, "PNG", new File(pngOutFileName));
	}
	
	public static void doMakeHiddenLevel2(String xmlFileName, String xmlOutFileName, int fontsize){
		SbmlDocument cd = CellDesigner.loadCellDesigner(xmlFileName);
		RemoveNames(cd);
		RemoveResidueNames(cd);
		assignFontSizeToAllSpecies(cd, fontsize);
		CellDesigner.saveCellDesigner(cd, xmlOutFileName);
	}
	
	public static void doScalePng(String pngFileName1, String pngOutFileName) throws Exception{
		BufferedImage map1 = ImageIO.read(new File(pngFileName1));
		int gWidth= map1.getWidth()/2;
		int gHeight= map1.getHeight()/2;
		BufferedImage im = Utils.getScaledImageSlow(map1, gWidth, gHeight);
		ImageIO.write(im, "PNG", new File(pngOutFileName));
	}
	
	
	  public static void RemoveNames(SbmlDocument sbml){
		    for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		      SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray()[i];
		      String type = Utils.getText(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
		      //System.out.print("Before "+Utils.getValue(sp.getName())+" ");
		      //sp.setName(XmlString.Factory.newInstance());
		      //System.out.println("after "+Utils.getValue(sp.getName()));
		      CelldesignerNameDocument.CelldesignerName cdn = CelldesignerNameDocument.CelldesignerName.Factory.newInstance();
		      //XmlString str = XmlString.Factory.newInstance();
		      //str.setStringValue("_");
		      //cdn.set(str);
		      //cdn.set(XmlString.Factory.newInstance());
		      if((!type.equals("SIMPLE_MOLECULE"))&&(!type.equals("ION"))&&(!type.equals("PHENOTYPE"))){
		    	  Utils.setValue(cdn, "...");
		      	  sp.getAnnotation().getCelldesignerSpeciesIdentity().setCelldesignerName(cdn);
		      }
		    }
		  }  
	
	  public static void RemoveResidueNames(SbmlDocument sbml){
		    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
		      CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
		      if(prot.getCelldesignerListOfModificationResidues()!=null){
		        for(int j=0;j<prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray().length;j++){
		          CelldesignerModificationResidueDocument.CelldesignerModificationResidue mr = prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
		          mr.setName(null);
		        }
		      }
		    }
		  }	
	
      public static void assignFontSizeToAllSpecies(SbmlDocument sbml, float fontSize){
          for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
                  CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
                  XmlString xs = XmlString.Factory.newInstance();
                  xs.setStringValue(""+(new Integer((int)(fontSize))).toString());
                  csa.getCelldesignerFont().setSize(xs);
          }
          for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
                  CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
                  XmlString xs = XmlString.Factory.newInstance();
                  xs.setStringValue(""+(new Integer((int)(fontSize))).toString());
                  csa.getCelldesignerFont().setSize(xs);
          }               
      }
      
      public static void updateLinks(String mergeConfigFile, String connectionDBTable) throws Exception{
      	Vector<String> mergeConfig = Utils.loadStringListFromFile(mergeConfigFile);
      	Vector<String> listFiles = new Vector<String>();
      	for(String s: mergeConfig){
      		String fn = s.split("\t")[0];
      		if((new File(fn)).exists())
      			listFiles.add(fn);
      	}
      	SimpleTable tab = new SimpleTable();
      	tab.LoadFromSimpleDatFile(connectionDBTable, true, "\t");
      	tab.createIndex("HUGO");
      	tab.createSecondaryIndex("UNIPROT");
      	for(String fn: listFiles){
      		StringBuffer text = new StringBuffer();
      		File f = new File(fn);
      		LineNumberReader lr = new LineNumberReader(new FileReader(f));
      		String s = null;
      		while((s=lr.readLine())!=null){
      			String scopy = s;
      			if(s.trim().startsWith("HUGO:")){
      			// Here we process the string in which id definitions are found
      				Vector<Integer> foundRowsInTable = new Vector<Integer>();
      				for(String fieldName: tab.fieldNames){
      					Vector<String> tagvalues = Utils.getTagValues(s, fieldName);
      					for(String tag: tagvalues){
      						for(int i=0;i<tab.rowCount; i++)
      							if(tab.stringTable[i][tab.fieldNumByName(fieldName)].equals(tag))
      								if(!foundRowsInTable.contains(i))
      									foundRowsInTable.add(i);
      						s = Utils.replaceString(s, fieldName+":"+tag, "");
      					}
      				}
      				s = Utils.cutFirstLastNonVisibleSymbols(s);
      				// Prepare new string prefix containing all non-empty table values
      				String prefix = "";
      				for(Integer row: foundRowsInTable){
      					for(String fieldName: tab.fieldNames){
      						if(!tab.stringTable[row][tab.fieldNumByName(fieldName)].equals("")){
      							prefix+=fieldName+":"+tab.stringTable[row][tab.fieldNumByName(fieldName)]+" ";
      						}
      					}
      				}
      				if(foundRowsInTable.size()==0)
      					s = scopy;
      				else
      					text.append(prefix+" "+s);
      			//	
      			}else{
      				text.append(s+"\n");
      			}
      		}
      		Utils.saveStringToFile(text.toString(), fn);
      	}
      }
	

}
