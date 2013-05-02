package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.*;
import edu.rpi.cs.xgmml.*;

public class testCellDesigner {

	public static void main(String[] args) {
		try{
			
			/*SbmlDocument cd6 = CellDesigner.loadCellDesigner("C:/Datas/ClickableMap/2011_10_05_Model/2011_10_05_Model_Mistakes/2011_10_05_Model_master_mistakes_OVAL.xml");
			if(cd6.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers()!=null)
			for(int i=0;i<cd6.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray();i++){
				CelldesignerLayerDocument.CelldesignerLayer layer = cd6.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i);
				System.out.println("Layer Id="+layer.getId()+" Name="+Utils.getValue(layer.getName()));
				if(layer.getCelldesignerListOfSquares()!=null)
				for(int j=0;j<layer.getCelldesignerListOfSquares().sizeOfCelldesignerLayerCompartmentAliasArray();j++){
					CelldesignerLayerCompartmentAliasDocument.CelldesignerLayerCompartmentAlias square = layer.getCelldesignerListOfSquares().getCelldesignerLayerCompartmentAliasArray(j);
					System.out.println(Utils.getValue(square.getType())+" -> "+square.getCelldesignerBounds().getX()+","+square.getCelldesignerBounds().getY()+","+square.getCelldesignerBounds().getW()+","+square.getCelldesignerBounds().getH());
				}
				if(layer.getCelldesignerListOfFreeLines()!=null)
				for(int j=0;j<layer.getCelldesignerListOfFreeLines().sizeOfCelldesignerLayerFreeLineArray();j++){
					CelldesignerLayerFreeLineDocument.CelldesignerLayerFreeLine fl = layer.getCelldesignerListOfFreeLines().getCelldesignerLayerFreeLineArray(j);
					System.out.println("Line "+fl.getCelldesignerBounds().getSx()+","+fl.getCelldesignerBounds().getSy()+","+fl.getCelldesignerBounds().getEx()+","+fl.getCelldesignerBounds().getEy()+",");
				}
			}
			System.exit(0);
			
			SbmlDocument cd5 = CellDesigner.loadCellDesigner("c:/datas/rbmaps1/rb25.xml");
			//SbmlDocument cd5 = CellDesigner.loadCellDesigner("c:/datas/binomtest/testNotes.xml");
			moveSpeciesNotesToProteinNotes(cd5);
			CellDesigner.saveCellDesigner(cd5, "c:/datas/rbmaps1/rb25_c.xml");
			//CellDesigner.saveCellDesigner(cd5, "c:/datas/binomtest/testNotes_c.xml");
			System.exit(0);*/
			
			
			//SbmlDocument cd4 = CellDesigner.loadCellDesigner("c:/datas/binomtest/M-Phase2.xml");
			//SbmlDocument cd4 = CellDesigner.loadCellDesigner("c:/datas/binomtest/test_infl.xml");
			//SbmlDocument cd4 = CellDesigner.loadCellDesigner("c:/datas/louvard/Notch_p53_29_deg.xml");
			//SbmlDocument cd4 = CellDesigner.loadCellDesigner("c:/datas/binomtest/TestPos.xml");
			//GraphDocument gr = (XGMML.loadFromXMGML("c:/datas/binomtest/TestPos.xgmml"));
			//GraphDocument gr = (XGMML.loadFromXMGML("c:/datas/binomtest/M-Phase2.xgmml"));
			//GraphDocument gr = (XGMML.loadFromXMGML("c:/datas/binomtest/test.xgmml"));
			//modifyPositionOfSpecies(cd4,gr);
			Utils.printUsedMemory();
			Date date = new Date();
			SbmlDocument cd4 = CellDesigner.loadCellDesigner("c:/datas/binomtest/mapmerging/merged18maps.xml");
			Utils.printUsedMemory();
			System.out.println(((new Date()).getTime()-date.getTime())/1000f+" secs");
			int pn = cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
			int gn = cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
			int rn = cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
			int arn = cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();
			int sn = cd4.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();
			int ren = cd4.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
			
			System.out.println(pn+" proteins, "+gn+" genes, "+rn+" RNAs, "+arn+" antisense RNAs, "+sn+" species, "+ren+" reactions");
			for(int i=0;i<cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
				String note = Utils.getValue(cd4.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i).getCelldesignerNotes());
			}
			Utils.printUsedMemory();
			System.exit(0);
			
			
			addHypotheticalInfluences(cd4);
			
			CellDesigner.saveCellDesigner(cd4, "c:/datas/louvard/Notch_p53_29_deg1.xml");
			System.exit(0);
			
			
			//SbmlDocument cd3 = CellDesigner.loadCellDesigner("c:/datas/simon/100609/100609.xml");
			SbmlDocument cd3 = CellDesigner.loadCellDesigner("c:/datas/basal/190410/CC_DNArepair_19_04_2010.xml");
			checkInconsistenciesInNames(cd3);
			//CellDesigner.saveCellDesigner(cd3, "c:/datas/simon/100609/100609_correct.xml");			
			CellDesigner.saveCellDesigner(cd3, "c:/datas/basal/190410/CC_DNArepair_19_04_2010_corrected.xml");
			System.exit(0);
			
			SbmlDocument cd2 = CellDesigner.loadCellDesigner("c:/datas/binomtest/RB_EGFR_ER/RB_ER_EGFR.xml");
			System.out.println("loaded");
			test.ScaleSizes(cd2, 0.6f,false);
			test.RemoveResidueNames(cd2);
			test.RemoveNames(cd2);
			CellDesigner.saveCellDesigner(cd2, "c:/datas/binomtest/RB_EGFR_ER/RB_ER_EGFR_s.xml");
			System.out.println("saved");			
			System.exit(0);
			
			// For Laurence
			SbmlDocument cd1 = CellDesigner.loadCellDesigner("c:/datas/calamar/rb_reduced_2.xml");
			SbmlDocument cdt = CellDesigner.loadCellDesigner("c:/datas/calzone/paper/target_genes.xml");
			HashMap comments = new HashMap();
			
			HashMap objects = CellDesigner.getAllObjectsHash(cdt);
			HashMap objects1 = CellDesigner.getAllObjectsHash(cd1);
			
			/*for(int i=0;i<cdt.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
				ReactionDocument.Reaction r = cdt.getSbml().getModel().getListOfReactions().getReactionArray(i);
				String type = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
				if(type.startsWith("TRANSCRIPTIONAL")||type.startsWith("UNKNOWN"))
				if(r.getListOfReactants().getSpeciesReferenceArray().length==1)
				if(r.getListOfProducts().getSpeciesReferenceArray().length==1){
					String id1 = r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies();
					SpeciesDocument.Species sp1 = (SpeciesDocument.Species)objects.get(id1);
					String id2 = r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies();
					SpeciesDocument.Species sp2 = (SpeciesDocument.Species)objects.get(id2);
					String key = Utils.getValue(sp1.getName())+"->"+Utils.getValue(sp2.getName());
					if(r.getNotes()!=null)
					//if(r.getNotes().getHtml()!=null)
						{
						String note = Utils.getText(r.getNotes()).trim();
						System.out.println(key+" : "+note);
						comments.put(key, note);
					}
				}
			}*/
			
			// Load E2F.txt file
			LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/calzone/paper/E2F_new290607.txt"));
			String s = null;
			int count=0;
			while((s=lr.readLine())!=null){
				//System.out.println((++count));
				StringTokenizer st = new StringTokenizer(s,"\t");
				String sp1 = st.nextToken();
				String sp2 = st.nextToken();
				String key = sp1.trim()+"->"+sp2.trim();
				try{
				String comm = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(comm," ");
				String comment = "";
				while(st1.hasMoreTokens()){
					comment+="PMID: "+st1.nextToken()+"\n";
				}
				comments.put(key.trim(),comment);
				}catch(Exception e){
					
				}
			}
			
			HashMap allKeys = new HashMap();
			
			int found = 0;
			for(int i=0;i<cd1.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
				ReactionDocument.Reaction r = cd1.getSbml().getModel().getListOfReactions().getReactionArray(i);
				String type = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
				if(type.startsWith("TRANSCRIPTIONAL")||type.startsWith("UNKNOWN"))
				if(r.getListOfReactants().getSpeciesReferenceArray().length==1)
				if(r.getListOfProducts().getSpeciesReferenceArray().length==1){
					String id1 = r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies();
					SpeciesDocument.Species sp1 = (SpeciesDocument.Species)objects1.get(id1);
					String id2 = r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies();
					SpeciesDocument.Species sp2 = (SpeciesDocument.Species)objects1.get(id2);
					if((sp1!=null)&&(sp2!=null)){
					String key = (Utils.getValue(sp1.getName()).trim()+"->"+Utils.getValue(sp2.getName()).trim()).trim();
					allKeys.put(key, r);
					System.out.println(r.getId()+"\t"+key);
					if(comments.get(key)!=null){
						System.out.println((++found)+") For "+r.getId()+" "+key+" found \n");
						if(r.getNotes()!=null)
							//if(r.getNotes().getHtml()!=null)
								{
								String note = Utils.getText(r.getNotes()).trim();
								//System.out.println(key+" : "+note);
								NotesDocument.Notes notes = r.addNewNotes();
								HtmlDocument.Html html = notes.addNewHtml();
								BodyDocument.Body body = html.addNewBody();
								String comment = (String)comments.get(key);
								StringTokenizer st = new StringTokenizer(comment,"\n"); 
								String finalComment = note;
								while(st.hasMoreTokens()){
									String text = st.nextToken().trim();
									if(finalComment.indexOf(text)<0)
										finalComment+=text+"\n";
								}
								Utils.setValue(body,finalComment.trim());
							}else{
								NotesDocument.Notes notes = r.addNewNotes();
								HtmlDocument.Html html = notes.addNewHtml();
								BodyDocument.Body body = html.addNewBody();
								Utils.setValue(body,(String)comments.get(key));
							}
						System.out.println(Utils.getText(r.getNotes()));
					}
					}
				}
			}

			Vector notFoundinTxt = new Vector();
			Vector notFoundinFile = new Vector();

			count =0;
			for(Iterator it=comments.keySet().iterator();it.hasNext();){
				String key = (String)it.next();
				//if(key.equals("E2F4->UXT"))
					//System.out.println("Comment key "+key+" "+key.length());
				if(allKeys.get(key)==null)
					System.out.println((++count)+") "+key+" not found in file");
			}
			count =0;
			for(Iterator it=allKeys.keySet().iterator();it.hasNext();){
				String key = (String)it.next();
				if(comments.get(key)==null)
					System.out.println((++count)+") "+key+" not found in text");
			}

			//System.out.println("Check "+allKeys.get("E2F4->UXT"));			
			
			System.out.println("Saving");
			CellDesigner.saveCellDesigner(cd1, "c:/datas/calzone/paper/280607prime_.xml");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void checkInconsistenciesInNames(SbmlDocument cd){
		// first create a map of entities names
		HashMap<String,String> hm = CellDesigner.getEntitiesNames(cd);
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies cs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			String name = Utils.getValue(cs.getName());
			String id = "";
			String correctName = "";
			if(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null)
				id = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
			if(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null)
				id = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
			if(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null)
				id = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
			if(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null)
				id = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
			if(Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("SIMPLE_MOLECULE"))
				correctName = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerName());
			if(Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("ION"))
				correctName = Utils.getText(cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerName());
			
			if(correctName.equals(""))
				correctName = hm.get(id);
			if(!name.equals(correctName)){ 
				System.out.println("ERROR in included species "+cs.getId()+", name found "+name+", should be "+correctName);
				XmlString xs = XmlString.Factory.newInstance();
				xs.setStringValue(correctName);
				cs.setName(xs);
			}
		}
		
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species cs = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String name = Utils.getValue(cs.getName());
			String id = "";
			String correctName = "";
			if(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null)
				id = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
			if(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null)
				id = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
			if(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null)
				id = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
			if(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null)
				id = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
			if(Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("SIMPLE_MOLECULE"))
				correctName = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerName());
			if(Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("ION"))
				correctName = Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerName());
			
			if(correctName.equals(""))
				correctName = hm.get(id);
			if(!Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("COMPLEX"))
			if(!Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE"))
			if(!Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED"))
			if(!Utils.getText(cs.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DRUG"))
			if(!name.equals(correctName)) 
				System.out.println("ERROR in species "+cs.getId()+", name found "+name+", should be "+correctName);
		}
		
	}
	
	public static void modifyPositionOfSpecies(SbmlDocument cd, GraphDocument gr){
		HashMap<String,Pair> coordinates = new HashMap<String,Pair>();
		double maxx = 0;
		double maxy = 0;
		double minx = Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		
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
			//System.out.println(alias+"\t"+x+"\t"+y);
		}
		int width = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		int height = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		width=width-200;
		height=height-200;
		HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases = new HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String alid = cdal.getId();
			Pair p = coordinates.get(alid);
			if(p!=null){
				double x = (((Double)p.o1).doubleValue());
				double y = (((Double)p.o2).doubleValue());
				//x = (int)((double)width*x/(maxx-minx));
				//y = (int)((double)height*y/(maxy-miny));
				if(minx<0) x=x-minx;
				if(miny<0) y=y-miny;
				cdal.getCelldesignerBounds().setX(""+x);
				cdal.getCelldesignerBounds().setY(""+y);
			}
			aliases.put(cdal.getSpecies(), cdal);
		}
		
		HashMap<String,Vector<CelldesignerSpecies>> complexes = new HashMap<String,Vector<CelldesignerSpecies>>(); 
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpecies si = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			//System.out.println("Included species "+si.getId());
			if(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies()!=null){
				String csid = Utils.getText(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
				//System.out.println("\tComplex species "+csid);
				Vector<CelldesignerSpecies> v = complexes.get(csid);
				if(v==null)	v = new Vector<CelldesignerSpecies>();
				v.add(si);
				complexes.put(csid, v);
			}
		}

		if(maxx==minx) { minx = -(double)width/2; maxx = (double)width/2; }
		if(maxy==miny) { miny = -(double)width/2; maxy = (double)width/2; }
		//System.out.println("Width="+width+",Height="+height);
		//System.out.println("maxx="+maxx+",maxy="+maxy);
		//System.out.println("minx="+minx+",miny="+miny);
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
			//System.out.println("In complex "+alid+" ("+x+","+y+")");			
				//x = ((double)width*x/(maxx-minx));
				//y = ((double)height*y/(maxy-miny));
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
	
	public static void moveSpeciesNotesToProteinNotes(SbmlDocument cd){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			System.out.println("Species "+i+"/"+cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray());
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
			String pid = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
			if(sp.getNotes()!=null){
				String s = Utils.getValue(sp.getNotes());
				s = Utils.replaceString(s, "\n\n", "");
				//System.out.println("s = "+s);
				if(CellDesigner.entities!=null){
				CelldesignerProteinDocument.CelldesignerProtein p = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get(pid);
				String ps = "";
				if(p.getCelldesignerNotes()!=null)
					ps = Utils.getValue(p.getCelldesignerNotes());
				ps = Utils.replaceString(ps, "\n\n", "");
				ps=ps+s;
				//System.out.println("psfinal = "+ps);
				sp.unsetNotes();
				p.unsetCelldesignerNotes();
				//sp.setNotes(null);
				//p.setCelldesignerNotes(null);
				Utils.setValue(p.addNewCelldesignerNotes().addNewHtml().addNewBody(),ps);
				}
			}
			}
			if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
				String pid = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
				if(sp.getNotes()!=null){
					String s = Utils.getValue(sp.getNotes());
					s = Utils.replaceString(s, "\n\n", "");
					//System.out.println("s = "+s);
					if(CellDesigner.entities!=null){
					CelldesignerGeneDocument.CelldesignerGene p = (CelldesignerGeneDocument.CelldesignerGene)CellDesigner.entities.get(pid);
					String ps = "";
					if(p.getCelldesignerNotes()!=null)
						ps = Utils.getValue(p.getCelldesignerNotes());
					ps = Utils.replaceString(ps, "\n\n", "");
					ps=ps+s;
					//System.out.println("psfinal = "+ps);
					sp.unsetNotes();
					p.unsetCelldesignerNotes();
					Utils.setValue(p.addNewCelldesignerNotes().addNewHtml().addNewBody(),ps);
					}
				}
				}
		}
	}
	
	public static void addHypotheticalInfluences(SbmlDocument cd4){
		
		Vector<String> newids = new Vector<String>();
		
		for(int i=0;i<cd4.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd4.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String type = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
			
			Vector<String> listOfReactantSpecies = new Vector<String>();
			Vector<String> listOfReactantAliases = new Vector<String>();
			for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
				listOfReactantSpecies.add(r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies());
			for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
				listOfReactantAliases.add(Utils.getValue(r.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias()));

			Vector<String> listOfProductSpecies = new Vector<String>();
			Vector<String> listOfProductAliases = new Vector<String>();
			for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++)
				listOfProductSpecies.add(r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies());
			for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++)
				listOfProductAliases.add(Utils.getValue(r.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias()));
			
			HashMap<String, String> speciesTypes = new HashMap<String, String>();
			for(int j=0;j<cd4.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();j++){
				SpeciesDocument.Species sp = cd4.getSbml().getModel().getListOfSpecies().getSpeciesArray(j);
				speciesTypes.put(sp.getId(), Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
			}
			
			if(type.equals("HETERODIMER_ASSOCIATION")){
				System.out.println(r.getId()+" / "+listOfReactantSpecies.size()+" / "+listOfReactantAliases.size());
				for(int j=0;j<listOfReactantSpecies.size();j++)
					for(int k=0;k<listOfReactantSpecies.size();k++)if(k!=j){
						String id = "re_"+listOfReactantSpecies.get(j)+"_"+listOfReactantSpecies.get(k);
						if(id.equals("re_s90_s21"))
								System.out.println();
						if(newids.contains(id))
							id = id+"_";
						newids.add(id);
						addTransition(cd4, id, "NEGATIVE_INFLUENCE", "ff00aa00", listOfReactantSpecies.get(j), listOfReactantAliases.get(j), listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
					}
			}else{

				// Check if one of the products is a phenotype
				boolean isPhenotype = false;
				for(int j=0;j<listOfProductSpecies.size();j++)
					if(speciesTypes.get(listOfProductSpecies.get(j)).equals("PHENOTYPE"))
						isPhenotype = true;
				
				if(!isPhenotype){
				if(r.getListOfModifiers()!=null)
				for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
					ModifierSpeciesReferenceDocument.ModifierSpeciesReference msr = r.getListOfModifiers().getModifierSpeciesReferenceArray(j);
					String mod_species = msr.getSpecies();
					String mod_alias = Utils.getValue(msr.getAnnotation().getCelldesignerAlias());
					
					int sign_of_influence = 0;
					String typeMod = "";
					for(int k=0; k<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();k++){
						CelldesignerModificationDocument.CelldesignerModification cm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k);
						if(cm.getModifiers().equals(mod_species)){
							typeMod = cm.getType();
						}
					}
					if(typeMod.equals("CATALYSIS")) sign_of_influence = +1;
					if(typeMod.equals("INHIBITION")) sign_of_influence = -1;
					if(typeMod.equals("UNKNOWN_CATALYSIS")) sign_of_influence = +1;
					if(typeMod.equals("UNKNOWN_INHIBITION")) sign_of_influence = -1;
					if(typeMod.equals("PHYSICAL_STIMULATION")) sign_of_influence = +1;
										
					if(sign_of_influence!=0)
					for(int k=0;k<listOfReactantSpecies.size();k++){
						String reactType = speciesTypes.get(listOfReactantSpecies.get(k));
						if(!reactType.equals("GENE")){
						String id = "re_"+mod_species+"_"+listOfReactantSpecies.get(k);
						if(sign_of_influence!=0){
							if(newids.contains(id))
								id = id+"_";
							newids.add(id);
						}
						if(sign_of_influence==-1)
							addTransition(cd4, id, "POSITIVE_INFLUENCE", "ffaa0000", mod_species, mod_alias, listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
						if(sign_of_influence==+1)
							addTransition(cd4, id, "NEGATIVE_INFLUENCE", "ff00aa00", mod_species, mod_alias, listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
						}
					}
				}	
				}
				
			}
		}
	}
	
	public static void addTransition(SbmlDocument cd4, String id, String type, String color, String species1, String alias1, String species2, String alias2){
		ReactionDocument.Reaction r = cd4.getSbml().getModel().getListOfReactions().addNewReaction();
		
		r.setId(id);

		AnnotationDocument.Annotation an = r.addNewAnnotation();
		XmlString xs = XmlString.Factory.newInstance();
		xs.setStringValue(type);
		an.addNewCelldesignerReactionType().set(xs);
				
		
		SpeciesReferenceDocument.SpeciesReference spr = r.addNewListOfProducts().addNewSpeciesReference();
		spr.setSpecies(species2);
		xs = XmlString.Factory.newInstance();
		xs.setStringValue(alias2);
		spr.addNewAnnotation().addNewCelldesignerAlias().set(xs);

		spr = r.addNewListOfReactants().addNewSpeciesReference();
		spr.setSpecies(species1);
		xs = XmlString.Factory.newInstance();
		xs.setStringValue(alias1);
		spr.addNewAnnotation().addNewCelldesignerAlias().set(xs);		
		
		CelldesignerBaseReactantDocument.CelldesignerBaseReactant cbr = an.addNewCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
		xs = XmlString.Factory.newInstance();
		xs.setStringValue(species1);
		cbr.setSpecies(xs);
		cbr.setAlias(alias1);

		CelldesignerBaseProductDocument.CelldesignerBaseProduct cbp = an.addNewCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
		xs = XmlString.Factory.newInstance();
		xs.setStringValue(species2);
		cbp.setSpecies(xs);
		cbp.setAlias(alias2);
		
		CelldesignerLineDocument.CelldesignerLine cl = an.addNewCelldesignerLine();
		cl.setWidth("1.0");
		cl.setColor(color);
		
	}

}
