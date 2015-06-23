package fr.curie.BiNoM.pathways.utils.acsn;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.BodyDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationResidueDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerNameDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.NotesDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.ModifyCellDesignerNotes;
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
			String modelnotes = "";
			String name = "";
			String fileIn = "";
			String fileOut = "";
			String folder = "";
			String bibfile = "";
			String dscoresfile = "";
			String fscoresfile = "";
			
			boolean removeReactions = false;
			boolean mergepngs = false;
			boolean scalepng = false;
			boolean makehiddenlevel2 = false;
			boolean updateDBconnections = false;
			boolean changeModelNotes = false;			
			boolean updateAnnotations = false;
			boolean changeModelName = false;
			boolean copyModelNotesToFile = false;
			boolean makeBibliographyFromFile = false;
			boolean addbibliographytonotes = false;
			boolean spreadreactionrefs = false;
			boolean insertconfidences = false;
			
			
			
			//doRemoveReactions("C:/Datas/NaviCell/test/merged/merged_master.xml");
			//doMergePngs("C:/Datas/NaviCell/test/merged/merged_master-3.png",1f,"C:/Datas/NaviCell/test/merged/merged_master_noreactions.png",0.5f,null,1f,"C:/Datas/NaviCell/test/merged/merged_master-3_1.png");
			//doMakeHiddenLevel2("C:/Datas/acsn/survival_merge/hedgehog.xml", "C:/Datas/acsn/survival_merge/hedgehog_level2.xml", 20);
			//doScalePng("C:/Datas/NaviCell/test/merged/merged_master-3_1.png","C:/Datas/NaviCell/test/merged/merged_master-2_1.png");
			//doScalePng("C:/Datas/acsn/survival_merge/hedgehog-3-1.png", "C:/Datas/acsn/survival_merge/hedgehog-2-1.png");
			//updateLinks("c:/datas/navicell/test/merge_config","c:/datas/navicell/test/connectionIDs.txt");
			//makeBibliographyFromXml("C:/Datas/acsn/assembly/cellcycle_src/cellcycle_APC.xml", "C:/Datas/acsn/assembly/cellcycle_src/cellcycle_APC_bib.txt", true);
			
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
				if(args[i].equals("--changemodelnotes"))
					changeModelNotes = true;
				if(args[i].equals("--updateannotations"))
					updateAnnotations = true;
				if(args[i].equals("--changemodelname"))
					changeModelName = true;
				if(args[i].equals("--copymodelnotestofile"))
					copyModelNotesToFile = true;
				if(args[i].equals("--makebibliographyfromxml"))
					makeBibliographyFromFile = true;
				if(args[i].equals("--addbibliographytonotes"))
					addbibliographytonotes = true;
				if(args[i].equals("--insertconfidences"))
					insertconfidences = true;
				
				
				
				if(args[i].equals("--png1"))
					pngFileName1 = args[i+1];
				if(args[i].equals("--png2"))
					pngFileName2 = args[i+1];
				if(args[i].equals("--png3"))
					pngFileName3 = args[i+1];
				if(args[i].equals("--pngout"))
					pngOutFileName = args[i+1];
				if(args[i].equals("--in"))
					fileIn = args[i+1];
				if(args[i].equals("--out"))
					fileOut = args[i+1];
				if(args[i].equals("--folder"))
					folder = args[i+1];
				
				
				if(args[i].equals("--transparency1"))
					transparency1 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--transparency2"))
					transparency2 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--transparency3"))
					transparency3 = Float.parseFloat(args[i+1]);
				if(args[i].equals("--fontsize"))
					fontsize = Integer.parseInt(args[i+1]);
				if(args[i].equals("--modelnotes"))
					modelnotes = args[i+1];
				if(args[i].equals("--name"))
					name = args[i+1];
				if(args[i].equals("--biblfile"))
					bibfile = args[i+1];
				if(args[i].equals("--dscoresfile"))
					dscoresfile = args[i+1];
				if(args[i].equals("--fscoresfile"))
					fscoresfile = args[i+1];
				
				
				if(args[i].equals("--updatedbids"))
					updateDBconnections = true;
				if(args[i].equals("--spreadreactionrefs"))
					spreadreactionrefs = true;
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
			
			//if(spreadreactionrefs)
			//	spreadReactionRefs(mergeConfigFileName);
			
			if(changeModelNotes)
				changeModelNotes(modelnotes, xmlFileName);
			
			if(updateAnnotations)
				updateAnnotations(xmlFileName, xmlOutFileName);
			
			if(changeModelName)
				changeModelName(xmlFileName, name);
			
			if(addbibliographytonotes)
				addBibliographyToNotes(xmlFileName, bibfile, mergeConfigFileName);
			
			if(copyModelNotesToFile){
				if(!folder.equals("")){
					File f = new File(folder);
					if(fileIn.equals(""))
						copyModelNotesToFile(f, fileOut);
					else	
						copyModelNotesToFile(f, fileIn, fileOut);
				}else{
					copyModelNotesToFile(xmlFileName, fileIn, fileOut);
				}
			}
			
			if(makeBibliographyFromFile){
				makeBibliographyFromXml(xmlFileName, bibfile, true);
			}
			
			if(insertconfidences){
				SbmlDocument cd = CellDesigner.loadCellDesigner(xmlFileName);
				ConnectionToDatabases.annotateCellDesignerFileWithConfidenceScores(cd, dscoresfile, fscoresfile);
				CellDesigner.saveCellDesigner(cd, xmlFileName);
			}
				
			
			
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
    	System.out.println("Updating links to databases...");
      	Vector<String> mergeConfig = Utils.loadStringListFromFile(mergeConfigFile);
      	Vector<String> listFiles = new Vector<String>();
      	for(String s: mergeConfig){
      		if(!s.contains("update")){
      		String fn = s.split("\t")[0];
      		if((new File(fn)).exists())
      			listFiles.add(fn);
      		}
      	}
      	SimpleTable tab = new SimpleTable();
      	tab.LoadFromSimpleDatFile(connectionDBTable, true, "\t");
      	tab.createIndex("HUGO");
      	tab.createSecondaryIndex("UNIPROT");
      	for(String fn: listFiles){
      		System.out.println("updating "+fn+"...");
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
      						s = Utils.replaceString(s, fieldName+":"+tag+" ,", "");
      						s = Utils.replaceString(s, fieldName+":"+tag+",", "");
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
      					text.append(prefix+"\n"+s+"\n");
      			//	
      			}else{
      				text.append(s+"\n");
      			}
      		}
      		Utils.saveStringToFile(text.toString(), fn);
      	}
      }
      
      
      /*public static void spreadReactionRefs(String mergeConfigFile) throws Exception{
    	System.out.println("Spreading reaction references to species...");
      	Vector<String> mergeConfig = Utils.loadStringListFromFile(mergeConfigFile);
      	Vector<String> listFiles = new Vector<String>();
      	for(String s: mergeConfig){
      		if(!s.contains("update")){
      		String fn = s.split("\t")[0];
      		if((new File(fn)).exists())
      			listFiles.add(fn);
      		}
      	}
      	for(String fn: listFiles){
      		System.out.println("updating "+fn+"...");
      		
      	}
      }*/
      
      
      
      
      
      public static void changeModelNotes(String modelnotes, String xmlFileName){
    	  String s = Utils.loadString(modelnotes);
    	  SbmlDocument sbml = CellDesigner.loadCellDesigner(xmlFileName);
    	  XmlString xs = XmlString.Factory.newInstance();
    	  xs.setStringValue(s);
    	  sbml.getSbml().getModel().getNotes().set(xs);
    	  CellDesigner.saveCellDesigner(sbml, xmlFileName);
      }
      
      public static void changeModelName(String xmlFileName, String name){
    	  SbmlDocument sbml = CellDesigner.loadCellDesigner(xmlFileName);
    	  sbml.getSbml().getModel().setId(name);
    	  CellDesigner.saveCellDesigner(sbml, xmlFileName);
      }      
      
      public static void updateAnnotations(String sourceFileName, String targetFileName){
  		SbmlDocument cdsource = CellDesigner.loadCellDesigner(sourceFileName);
  		SbmlDocument cdtarget = CellDesigner.loadCellDesigner(targetFileName);
  		
		ModifyCellDesignerNotes mns = new ModifyCellDesignerNotes();
		mns.generateReadableNamesForReactionsAndSpecies = false;
		mns.allannotations = true;
		mns.formatAnnotation = false;
		mns.sbmlDoc = cdsource;

		ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
		mn.generateReadableNamesForReactionsAndSpecies = false;
		mn.allannotations = true;
		mn.formatAnnotation = false;
		mn.sbmlDoc = cdtarget;
		try{
			mn.comments = mns.exportCellDesignerNotes();
			mn.ModifyCellDesignerNotes();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		CellDesigner.saveCellDesigner(cdtarget, targetFileName);
      }
      
      public static void copyModelNotesToFile(File folder, String fileIn, String fileOut){
    	  String xmlFileName = "";
    	  for(File f: folder.listFiles()){
    		  String fn = f.getAbsolutePath();
    		  if(fn.endsWith(".xml"))
    		  if(!fn.startsWith("."))
    		  if(!fn.contains("_master"))
    			  xmlFileName+=fn+";";
    	  }
    	  if(xmlFileName.length()>1)
    		  xmlFileName = xmlFileName.substring(0,xmlFileName.length()-1);
    	  copyModelNotesToFile(xmlFileName, fileIn, fileOut);
      }
      
      public static void copyModelNotesToFile(File folder, String folderOut){
    	  String xmlFileName = "";
    	  for(File f: folder.listFiles()){
    		  String fn = f.getAbsolutePath();
    		  if(fn.endsWith(".xml"))
    		  if(!fn.startsWith("."))
    		  if(!fn.contains("_master"))
    			  xmlFileName+=fn+";";
    	  }
    	  if(xmlFileName.length()>1)
    		  xmlFileName = xmlFileName.substring(0,xmlFileName.length()-1);
    	  copyModelNotesToFile(xmlFileName, folderOut);
      }
      
      
      public static void copyModelNotesToFile(String xmlFileName, String fileIn, String fileOut){
    	  String prefix = Utils.loadString(fileIn);
    	  String fns[] = xmlFileName.split(";");
    	  String s = "";    	  
    	  for(String fn: fns){
    		  SbmlDocument sbml = CellDesigner.loadCellDesigner(fn);
    		  String fn_full = fn;
    		  fn = (new File(fn)).getName();
    		  fn = fn.substring(0,fn.length()-4);
    		  String parts[] = fn.split("_");
    		  if(parts.length>1)
    		  for(int i=1;i<parts.length;i++)
    			  s+=parts[i]+"_";
    		  if(s.length()>1)
    			  s = s.substring(0,s.length()-1)+"\n";
    		  s+=Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sbml.getSbml().getModel().getNotes()))+"\n\n";
    	  }
    	  Utils.saveStringToFile(prefix+"\n\n"+s, fileOut);
      }
      
      public static void copyModelNotesToFile(String xmlFileName, String folderOut){
    	  String fns[] = xmlFileName.split(";");
    	  for(String fn: fns){
        	  String s = "";
        	  String fn_full = fn;
    		  SbmlDocument sbml = CellDesigner.loadCellDesigner(fn);
    		  fn = (new File(fn)).getName();
    		  if(!fn.startsWith(".")){
    		  fn = fn.substring(0,fn.length()-4);
    		  String parts[] = fn.split("_");
    		  if(parts.length>1)
    		  for(int i=1;i<parts.length;i++)
    			  s+=parts[i]+"_";
    		  if(s.length()>1)
    			  s = s.substring(0,s.length()-1)+"\n";
    		  String moduleName = Utils.cutFirstLastNonVisibleSymbols(s);
    		  s+=Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sbml.getSbml().getModel().getNotes()))+"\n\n";
        	  Utils.saveStringToFile(s,folderOut+moduleName+".txt");
    		  }
    	  }
      }
      
      
      
       public static void makeBibliographyFromXml(String xmlFileName, String textFileName, boolean addPMIDprefix){
    	   String bib = makeBibliographyFromXml(xmlFileName, addPMIDprefix);
    	   Utils.saveStringToFile(bib, textFileName);
       }
       
       public static String makeBibliographyFromXml(String xmlFileName, boolean addPMIDprefix){
    	   String bib = "";
    	   Vector<String> pmids = getAllPMIDsFromCellDesigner(new File(xmlFileName));
    	   int k=0;
    	   for(String pmid: pmids){
    		   ConnectionToDatabases.Citation cit = ConnectionToDatabases.convertPMIDtoCitation(pmid);
    		   System.out.println((++k)+"\tPMID:"+pmid+"\t"+cit.year+"\t"+cit.oneLineCitation()+(cit.isReview?" [REVIEW]":""));
    		   if(addPMIDprefix)
    			   bib+="PMID:"+pmid+"\t"+cit.oneLineCitation()+(cit.isReview?" [REVIEW]":"")+"\n";
    		   else
    			   bib+=pmid+"\t"+cit.oneLineCitation()+(cit.isReview?" [REVIEW]":"")+"\n";
    	   }
    	   return bib;
       }

       public static String makeBibliographyFromText(String text, boolean addPMIDprefix){
    	   String bib = "";
    	   Vector<String> pmids = getAllPMIDsFromCellDesigner(text);
    	   for(String pmid: pmids){
    		   ConnectionToDatabases.Citation cit = ConnectionToDatabases.convertPMIDtoCitation(pmid);
    		   if(cit!=null){
    		   if(addPMIDprefix)
    			   bib+="PMID:"+pmid+"\t"+cit.oneLineCitation()+"\n";
    		   else
    			   bib+=pmid+"\t"+cit.oneLineCitation()+"\n";
    		   }
    	   }
    	   return bib;
       }
       
       
       public static String makeBibliographyFromXmlUseTextFile(String xmlFileName, String bibliographyFileName, boolean addPMIDprefix){
    	   String bib = "";
    	   Vector<String> bibList = Utils.loadStringListFromFile(bibliographyFileName);
    	   Vector<String> pmidList = new Vector<String>();
    	   for(String s: bibList){
    		   StringTokenizer st = new StringTokenizer(s,"\t");
    		   pmidList.add(st.nextToken());
    	   }
    	   Vector<String> pmids = getAllPMIDsFromCellDesigner(new File(xmlFileName));
    	   for(String pmid: pmids){
    		   int k = pmidList.indexOf(pmid);
    		   if(k!=-1){
    			   if(addPMIDprefix)
    				   bib+="PMID:"+bibList.get(k)+"\n";
    			   else
    				   bib+=bibList.get(k)+"\n";
    		   }
    	   }
    	   return bib;
       }
       
       public static Vector<String> getAllPMIDsFromCellDesigner(File file){
    	   String text = "";
    	   try{
    		   text = Utils.loadString(file.getAbsolutePath());
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }	
    	   return getAllPMIDsFromCellDesigner(text);
       }
       
       public static Vector<String> getAllPMIDsFromCellDesigner(String text){
    	   Vector<String> pmids = new Vector<String>();
    	   HashSet<String> set = new HashSet<String>();
    	   try{
    		   LineNumberReader lr = new LineNumberReader(new StringReader(text));
    		   String s = null;
    		   while((s=lr.readLine())!=null){
    			   StringTokenizer st = new StringTokenizer(s,";,. \t<>()[]");
    			   while(st.hasMoreTokens()){
    				   String token = st.nextToken();
    				   if(token.startsWith("PMID:")){
    					   String pmid = Utils.cutFirstLastNonVisibleSymbols(token.substring(5, token.length()));
    					   if(pmid.contains(":"))
    						   pmid = pmid.substring(0, pmid.indexOf(":"));
    					   if(!set.contains(pmid))
    						   set.add(pmid);
    				   }
    			   }
    		   }
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }
    	   Vector<Integer> ipmids = new Vector<Integer>(); 
    	   for(String s: set){
    		   try{
    			   ipmids.add(Integer.parseInt(s));
    		   }catch(Exception e){
    			   
    		   }
    	   }
    	   Collections.sort(ipmids);
    	   pmids.clear();
    	   for(int i: ipmids)
    		   pmids.add(""+i);
    	   return pmids;
       }
       
       
       public static void insertBibliographyIntoModelNotes(String xmlFileName){
    	   SbmlDocument sbml = CellDesigner.loadCellDesigner(xmlFileName);
    	   String notes = Utils.getValue(sbml.getSbml().getModel().getNotes());
       }
       
       public static void addBibliographyToNotes(String xmlFileName, String bibfile, String mergeConfig){
    	   Vector<String> files = Utils.loadStringListFromFile(mergeConfig);
    	   for(int i=1;i<files.size();i++){
    		   StringTokenizer st = new StringTokenizer(files.get(i),"\t");
    		   String fn = st.nextToken();
    		   addBibliographyToNotes(fn,bibfile);
    	   }
       }
       
       public static void addBibliographyToNotes(String xmlFileName, String bibfile){
    	   String bib = makeBibliographyFromXmlUseTextFile(xmlFileName, bibfile, true);
    	   SbmlDocument sbml = CellDesigner.loadCellDesigner(xmlFileName);
    	   String notes = Utils.getValue(sbml.getSbml().getModel().getNotes());
    	   notes = Utils.cutFirstLastNonVisibleSymbols(notes);
    	   
    	   notes+="\n======== References ========\n"+Utils.correctIllegalCharacters(bib);

    	    sbml.getSbml().getModel().setNotes(null);
			NotesDocument.Notes pnotes = sbml.getSbml().getModel().addNewNotes();
			BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
			XmlString xs = XmlString.Factory.newInstance();
			xs.setStringValue(notes);
			b.set(xs);
			
			CellDesigner.saveCellDesigner(sbml, xmlFileName);
       }
       
      
      

}
