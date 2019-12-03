package fr.curie.BiNoM.pathways.utils.acsn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument.CelldesignerModification;
import org.sbml.x2001.ns.celldesigner.CelldesignerSingleLineDocument.CelldesignerSingleLine;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.ModifierSpeciesReferenceDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import vdaoengine.utils.Utils;

import fr.curie.BiNoM.pathways.CellDesignerColorProteins;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.GMTFile;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class MakeCanonicalView {

	/**
	 * @param args
	 */
	
	Vector<String> listOfSpeciesToKeep = new Vector<String>();
	Vector<String> listOfReactionsToKeep = new Vector<String>();
	
	public String fileNameIn = "";
	public String fileNameOut = "";
	public String fileNameReferenceGraph = "";
	public String fileNameSpeciesToKeep = "";
	public String fileNameReactionsToKeep = "";
	
	public String fileNameGeneList = "";
	
	public Graph reactionGraph = null;
	public Graph referenceGraph = null;
	public GMTFile gmtFile = null;
	
	public boolean select = false;
	public boolean makenamelayer = false;
	public boolean makesubmap = false;
	
	
	public SbmlDocument cd = null;
	
	public int fontSize = 30;
	
	public float scale = 1f;
	public float scaleShape = 4f;
	
	public Vector<String> geneList = new Vector<String>();
	public Vector<String> geneTypes = new Vector<String>();
	
	public Vector<String> aliasesToKeep = new Vector<String>();
	public Vector<String> selectedIds = new Vector<String>();
	public HashMap<String, String> nameType = new HashMap<String, String>();
	public HashSet<String> foundGenes = new HashSet<String>();
	public HashSet<String> foundEntities = new HashSet<String>();
	public HashSet<String> mappedEntities = new HashSet<String>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			MakeCanonicalView mcv = new MakeCanonicalView();
			
			for(int i=0;i<args.length;i++){
				if(args[i].equals("--in"))
					mcv.fileNameIn = args[i+1];
				if(args[i].equals("--out"))
					mcv.fileNameOut = args[i+1];
				if(args[i].equals("--species"))
					mcv.fileNameSpeciesToKeep = args[i+1];
				if(args[i].equals("--reactions"))
					mcv.fileNameReactionsToKeep = args[i+1];
				if(args[i].equals("--select"))
					mcv.select = true;
				if(args[i].equals("--makenamelayer"))
					mcv.makenamelayer = true;
				if(args[i].equals("--makesubmap"))
					mcv.makesubmap = true;
				if(args[i].equals("--genelist"))
					mcv.fileNameGeneList = args[i+1];
				if(args[i].equals("--fontsize"))
					mcv.fontSize = Integer.parseInt(args[i+1]);
				if(args[i].equals("--scale"))
					mcv.scale = Float.parseFloat(args[i+1]);
				if(args[i].equals("--scaleshape"))
					mcv.scaleShape = Float.parseFloat(args[i+1]);
				if(args[i].equals("--referencegraph"))
					mcv.fileNameReferenceGraph = args[i+1];
				
			}
			
			if(mcv.fileNameOut.equals("")){
				mcv.fileNameOut = mcv.fileNameIn.substring(0,mcv.fileNameIn.length()-4)+"_cv.xml";
			}

			if(!mcv.fileNameSpeciesToKeep.equals(""))
				mcv.loadSpecies();
			if(!mcv.fileNameReactionsToKeep.equals(""))
				mcv.loadReactions();
			
			mcv.loadCellDesigner();
			
			if(mcv.makenamelayer)
				mcv.makeNameLayer();
			
			if(mcv.select)
				mcv.selectSpeciesAndReactions();
			
			if(mcv.makesubmap)
				mcv.makeSubMap();
			
			mcv.saveCellDesigner();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void loadCellDesigner(){
		cd = CellDesigner.loadCellDesigner(fileNameIn);
	}
	
	public void loadSpecies(){
		listOfSpeciesToKeep = Utils.loadStringListFromFile(fileNameSpeciesToKeep);
	}
	
	public void makeReactionGraph(){
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		c2c.addSuffixForMultipleAliases = true;
		c2c.sbml = cd;
		CellDesigner.entities = CellDesigner.getEntities(c2c.sbml);
		reactionGraph = XGMML.convertXGMMLToGraph(c2c.getXGMMLGraph(cd.getSbml().getModel().getId(), c2c.sbml.getSbml()));
	}
	
	public void makeGMTEntityHUGO(){
		String fn = fileNameIn+".gmt";
		CellDesignerColorProteins.createAttributeTable(null, cd, null, fn);
		gmtFile = new GMTFile();
		gmtFile.load(fn);
	}
	
	public void loadReactions(){
		listOfReactionsToKeep = Utils.loadStringListFromFile(fileNameReactionsToKeep);
	}
	
	public void saveCellDesigner(){
		CellDesigner.saveCellDesigner(cd,fileNameOut);
	}
	
	
	public void selectSpeciesAndReactions(){
		
		
		// scale first the whole map
		float mw = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		float mh = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+(int)(mw/(1f*scale)));
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(""+(int)(mh/(1f*scale)));
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++){
			CelldesignerCompartmentAlias ca = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
			if(ca.getCelldesignerBounds()!=null){
				float cw = Float.parseFloat(ca.getCelldesignerBounds().getW());
				float ch = Float.parseFloat(ca.getCelldesignerBounds().getH());
				float cx = Float.parseFloat(ca.getCelldesignerBounds().getX());
				float cy = Float.parseFloat(ca.getCelldesignerBounds().getY());
				ca.getCelldesignerBounds().setW(""+cw/(1f*scale));
				ca.getCelldesignerBounds().setH(""+ch/(1f*scale));
				ca.getCelldesignerBounds().setX(""+cx/(1f*scale));
				ca.getCelldesignerBounds().setY(""+cy/(1f*scale));
			}
		}
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			if(p.getCelldesignerListOfModificationResidues()!=null)
			while(p.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray()>0)
				p.getCelldesignerListOfModificationResidues().removeCelldesignerModificationResidue(0);
		}
		
		HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexAliasMap = new HashMap<String, CelldesignerComplexSpeciesAlias>();
		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAlias ccs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			XmlString xs = XmlString.Factory.newInstance();
			xs.setStringValue("inactive");
			ccs.getCelldesignerActivity().set(xs);
			complexAliasMap.put(ccs.getId(), ccs);
		}

		
		HashSet<String> setOfUsedAliases = new HashSet<String>(); 
		
		Vector<ReactionDocument.Reaction> reactions = new Vector<ReactionDocument.Reaction>();
		for(ReactionDocument.Reaction r : cd.getSbml().getModel().getListOfReactions().getReactionArray())
			reactions.add(r);
		int k=0;
		for(int i=0;i<reactions.size();i++){
			String id = reactions.get(i).getId();
			if(listOfReactionsToKeep.contains(id)){
				ReactionDocument.Reaction r = reactions.get(i);
				r.getAnnotation().getCelldesignerLine().setWidth("8");
				if(r.getAnnotation().getCelldesignerBaseProducts()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++)
					setOfUsedAliases.add(r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j).getAlias());
				if(r.getAnnotation().getCelldesignerListOfProductLinks()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfProductLinks().sizeOfCelldesignerProductLinkArray();j++)
					setOfUsedAliases.add(r.getAnnotation().getCelldesignerListOfProductLinks().getCelldesignerProductLinkArray(j).getAlias());
				if(r.getAnnotation().getCelldesignerBaseReactants()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++)
					setOfUsedAliases.add(r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j).getAlias());
				if(r.getAnnotation().getCelldesignerListOfReactantLinks()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfReactantLinks().sizeOfCelldesignerReactantLinkArray();j++)
					setOfUsedAliases.add(r.getAnnotation().getCelldesignerListOfReactantLinks().getCelldesignerReactantLinkArray(j).getAlias());
				
				if(r.getAnnotation().getCelldesignerListOfModification()!=null){
					int counter = 0;
					Vector<String> removedModifiers = new Vector<String>();
					Vector<CelldesignerModification> modifs = new Vector<CelldesignerModification>();
					for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
						modifs.add(r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j));
					}
					
				for(int j=0;j<modifs.size();j++){
					String type = modifs.get(j).getType();
					boolean remove = true;
					String modifiers = "";
					String aliases = "";
					if(!type.contains("BOOLEAN")){
						modifiers = modifs.get(j).getModifiers();
						aliases = modifs.get(j).getAliases();
						if(listOfSpeciesToKeep.contains(modifiers)||listOfSpeciesToKeep.contains(aliases)){ 
							setOfUsedAliases.add(modifiers);
							remove = false;
						}
					}
					if(remove){
						removedModifiers.add(modifiers);
						r.getAnnotation().getCelldesignerListOfModification().removeCelldesignerModification(j-counter);
						counter++;
					}
				}
				
				Vector<ModifierSpeciesReferenceDocument.ModifierSpeciesReference> refs = new Vector<ModifierSpeciesReferenceDocument.ModifierSpeciesReference>();
				int counter1 = 0;
				for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++)
					refs.add(r.getListOfModifiers().getModifierSpeciesReferenceArray(j));
				for(int j=0;j<refs.size();j++){
					if(removedModifiers.contains(refs.get(j).getSpecies())){
						r.getListOfModifiers().removeModifierSpeciesReference(j-counter1);
						counter1++;
					}
				}
				}
				
			}else{
				//cd.getSbml().getModel().getListOfReactions().getReactionArray(i).setId("");
				cd.getSbml().getModel().getListOfReactions().removeReaction(i-k);
				k++;
				//cd.getSbml().getModel().getListOfReactions().getReactionArray(i).set(null);
			}
		}
		
		int counter = 0;
		Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases =  new Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			aliases.add(csa);
		}
		
		for(int i=0;i<aliases.size();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = aliases.get(i);
			XmlString xs1 = XmlString.Factory.newInstance();
			xs1.setStringValue("inactive");
			csa.getCelldesignerActivity().set(xs1);
			
			String id = csa.getId();
			String spid = csa.getSpecies();
			String complexspid = csa.getComplexSpeciesAlias();
			boolean keep = false;
			if(this.listOfSpeciesToKeep.contains(id)){
				k = listOfSpeciesToKeep.indexOf(spid);
				keep = true;
				if(k>=0)
					listOfSpeciesToKeep.remove(k);
			}else{
				if(this.listOfSpeciesToKeep.contains(spid)){
					keep = true;
				}
			}
			if(complexspid!=null){
				CelldesignerComplexSpeciesAlias ccs = complexAliasMap.get(complexspid);
				if(ccs!=null){
					String cspid = ccs.getSpecies();
					if(this.listOfSpeciesToKeep.contains(ccs.getId())){
						k = listOfSpeciesToKeep.indexOf(cspid);
						keep = true;
						if(k>=0)
							listOfSpeciesToKeep.remove(k);
					}else{
						if(this.listOfSpeciesToKeep.contains(cspid)){
							keep = true;
						}
					}
				}
			}
			
			
			if(keep){
				
				float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
				float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
				float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
				float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
				csa.getCelldesignerBounds().setH(""+h*scaleShape);
				csa.getCelldesignerBounds().setW(""+w*scaleShape);
				
				if(complexspid!=null){
					CelldesignerComplexSpeciesAlias ccsa = complexAliasMap.get(complexspid);
					float cx = Float.parseFloat(ccsa.getCelldesignerBounds().getX());
					float cy = Float.parseFloat(ccsa.getCelldesignerBounds().getY());
					float rx = x-cx;
					float ry = y-cy;
					csa.getCelldesignerBounds().setX(""+(x+rx-w-15)/(1f*scale));
					csa.getCelldesignerBounds().setY(""+(y+ry-h-15)/(1f*scale));
				}else{
					csa.getCelldesignerBounds().setX(""+(x-w/2f)/(1f*scale));
					csa.getCelldesignerBounds().setY(""+(y-h/2f)/(1f*scale));
				}
				
				XmlString xs = XmlString.Factory.newInstance();
				xs.setStringValue(""+fontSize);
				csa.getCelldesignerFont().setSize(xs);
				if(csa.getCelldesignerUsualView().getCelldesignerSingleLine()==null)
					csa.getCelldesignerUsualView().setCelldesignerSingleLine(null);
				else
					csa.getCelldesignerUsualView().addNewCelldesignerSingleLine().setWidth("5");
				
			}else{
				
				if(!setOfUsedAliases.contains(id)){
					cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(i-counter);
					counter++;
				}else{
					float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
					float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
					float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
					float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
					csa.getCelldesignerBounds().setH("1");
					csa.getCelldesignerBounds().setW("1");
					csa.getCelldesignerBounds().setX(""+(x+w/2f)/(1f*scale));
					csa.getCelldesignerBounds().setY(""+(y+h/2f)/(1f*scale));
					XmlString xs = XmlString.Factory.newInstance();
					xs.setStringValue("8");
					csa.getCelldesignerFont().setSize(xs);
				}
			}
			
		}
		
		counter = 0;
		Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexAliases = new Vector<CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			complexAliases.add(csa);
		}
		
		for(int i=0;i<complexAliases.size();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = complexAliases.get(i);
			String id = csa.getId();
			String spid = csa.getSpecies();
			boolean keep = false;
			if(this.listOfSpeciesToKeep.contains(id)){
				k = listOfSpeciesToKeep.indexOf(spid);
				keep = true;
				if(k>=0)
					listOfSpeciesToKeep.remove(k);
			}else{
				if(this.listOfSpeciesToKeep.contains(spid)){
					keep = true;
				}
			}
			if(keep){
				float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
				float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
				float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
				float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
				csa.getCelldesignerBounds().setH(""+h*scaleShape);
				csa.getCelldesignerBounds().setW(""+w*scaleShape);
				csa.getCelldesignerBounds().setX(""+(x-w/scaleShape)/(1f*scale));
				csa.getCelldesignerBounds().setY(""+(y-h/scaleShape)/(1f*scale));
				XmlString xs = XmlString.Factory.newInstance();
				xs.setStringValue(""+fontSize);
				csa.getCelldesignerFont().setSize(xs);
				if(csa.getCelldesignerUsualView().getCelldesignerSingleLine()==null)
					csa.getCelldesignerUsualView().setCelldesignerSingleLine(null);
				else
					csa.getCelldesignerUsualView().addNewCelldesignerSingleLine().setWidth("5");
				
			}else{
				
				if(!setOfUsedAliases.contains(id)){
					cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().removeCelldesignerComplexSpeciesAlias(i-counter);
					counter++;
				}else{
					float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
					float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
					float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
					float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
					csa.getCelldesignerBounds().setH("1");
					csa.getCelldesignerBounds().setW("1");
					csa.getCelldesignerBounds().setX(""+(x+w/2f)/(1f*scale));
					csa.getCelldesignerBounds().setY(""+(y+h/2f)/(1f*scale));
					XmlString xs = XmlString.Factory.newInstance();
					xs.setStringValue("8");
					csa.getCelldesignerFont().setSize(xs);
				}
			}
		}		
		
		
		
	}
	
    public void detectSimpleSpeciesFromGeneList(){

		// Now we will find all "simple" species corresponding to geneList
		for(String s: geneList){
			Vector<String> sets = gmtFile.findSet(s);
			if(sets.size()>0)
				foundGenes.add(s);
			for(String name: sets){
				int k = geneList.indexOf(s);
				if(!name.startsWith("g")&&!name.startsWith("r")){
					nameType.put(name, geneTypes.get(k));
					foundEntities.add(name);
				}
				//System.out.println("FOUND: "+s+" in "+name);
			}
		}
		
		for(int i=0;i<reactionGraph.Nodes.size();i++){
			Node n = reactionGraph.Nodes.get(i);
			String id = n.Id;
			String nms[] = id.split("@");
			if(nameType.get(nms[0])!=null){
				String alias = n.getFirstAttributeValue("CELLDESIGNER_ALIAS");
				aliasesToKeep.add(alias);
				selectedIds.add(id);
				mappedEntities.add(nms[0]);
				//System.out.println("FOUND in graph: "+id);
			}
		}
		System.out.println(foundGenes.size()+" genes and "+foundEntities.size()+" entities are found, "+mappedEntities.size()+" are put onto the map.");
    	
    	
    }
	
	
	public void makeNameLayer(){
		makeGMTEntityHUGO();
		makeReactionGraph();
		loadGeneList();
		detectSimpleSpeciesFromGeneList();
		makeNameLayerImage(selectedIds, nameType);
	}
	
	public void makeNameLayerImage(Vector<String> geneIds, HashMap<String, String> nameType){
		int w = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		int h = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		w/=1f*scale;
		h/=1f*scale;
		BufferedImage mergedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = mergedImage.createGraphics();
		g.setBackground(new Color(1f,1f,1f));
		g.clearRect(0,0,mergedImage.getWidth(),mergedImage.getHeight());
		for(String id: geneIds){
			Font font = new Font("SansSerif", Font.BOLD, fontSize);
			g.setFont(font);
			Node n = reactionGraph.getNode(id);
			String nms[] = id.split("@");
			g.setColor(new Color(0f,0f,0f));
			if(nameType.get(nms[0])!=null){
				String type = nameType.get(nms[0]);
				if(type.toLowerCase().equals("oncogene"))
					g.setColor(new Color(1f,0f,0f));
				if(type.toLowerCase().equals("tsg"))
					g.setColor(new Color(0f,0f,1f));
			}
			// Some text processing
			boolean show = true;
			if(nms[0].contains("cleaved")) show = false;
			nms[0] = Utils.replaceString(nms[0], "_beta_", "b");
			nms[0] = Utils.replaceString(nms[0], "*", "");
			nms[0] = Utils.replaceString(nms[0], "family", "");
			if(nms[0].length()>20) show = false;
			nms[0] = nms[0].split("_")[0];
			if(show)
				g.drawString(nms[0], (n.x-n.w)/(1f*scale), (n.y+n.h)/(1f*scale));
			//System.out.println(id+"\t"+nms[0]+"\t"+n.x+"\t"+n.y);
		}
		g.dispose();
		
		try{
			ImageIO.write(mergedImage, "PNG", new File(fileNameIn+".names.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void makeSubMap(){
		makeGMTEntityHUGO();
		makeReactionGraph();
		loadGeneList();
		detectSimpleSpeciesFromGeneList();
		
		listOfSpeciesToKeep.clear();
		listOfReactionsToKeep.clear();
		for(String s: aliasesToKeep)
			listOfSpeciesToKeep.add(s);
		
		// now we add everything which is found in the reference graph
		loadReferenceGraph();
		if(referenceGraph!=null){
			for(Node n: referenceGraph.Nodes){
				String alias = n.getFirstAttributeValue("CELLDESIGNER_ALIAS");
				if(alias!=null)if(!alias.equals(""))
					listOfSpeciesToKeep.add(alias);
				String reaction = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
				if(reaction!=null)if(!reaction.equals(""))
					listOfReactionsToKeep.add(reaction);
			}
		}
		
		selectSpeciesAndReactions();
	}
	
	
	public void loadGeneList(){
		Vector<String> list = Utils.loadStringListFromFile(this.fileNameGeneList);
		for(String s: list){
			String name = "";
			String type = "";
			String ss[] = s.split("\t");
			name = ss[0];
			if(ss.length>1)
				type = ss[1];
			geneList.add(name);
			geneTypes.add(type);
		}
	}
	
	public void loadReferenceGraph(){
		if(fileNameReferenceGraph!=null){
			try{
			referenceGraph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(fileNameReferenceGraph));
			}catch(Exception e){
				referenceGraph = null;
			}
	}
	}
	
	

}
