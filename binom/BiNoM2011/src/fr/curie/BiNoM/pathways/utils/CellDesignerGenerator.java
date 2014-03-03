package fr.curie.BiNoM.pathways.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.impl.xb.xsdschema.AnnotationDocument;
import org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation;
import org.sbml.x2001.ns.celldesigner.CelldesignerActivityDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAnnotationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisensernaReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseProductDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseReactantDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoundsDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoundsDocument.CelldesignerBounds;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoxSizeDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBriefViewDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerClassDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerEditPointsDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerInnerPositionDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationResidueDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerPaintDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProductLinkDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerReactantLinkDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRnaReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.ListOfModifiersDocument;
import org.sbml.x2001.ns.celldesigner.ListOfProductsDocument;
import org.sbml.x2001.ns.celldesigner.ListOfReactantsDocument;
import org.sbml.x2001.ns.celldesigner.ModifierSpeciesReferenceDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument.Species;
import org.sbml.x2001.ns.celldesigner.SpeciesReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerPaintDocument.CelldesignerPaint;
import org.sbml.x2001.ns.celldesigner.CelldesignerSingleLineDocument.CelldesignerSingleLine;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerUsualViewDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerViewDocument.CelldesignerView;
import org.sbml.x2001.ns.celldesigner.CompartmentDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;


import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.utils.SpeciesStructure.SpeciesStructureComponent;
import fr.curie.BiNoM.pathways.utils.SpeciesStructure.SpeciesStructureComponentModification;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class CellDesignerGenerator {
	
	public static int SPECIES = 0;
	public static int REACTION = 1;

	
	public class Statement{
		public String statementText = ""; 
		public int statementType = 0;
		public Vector<Attribute> attributes = new Vector<Attribute>(); 
	}
	

	/**
	 * @param args
	 */
	
	public SbmlDocument cd = null;
	HashMap<String,CelldesignerProteinDocument.CelldesignerProtein> proteins = new HashMap<String,CelldesignerProteinDocument.CelldesignerProtein>();
	HashMap<String,CelldesignerRNADocument.CelldesignerRNA> rnas = new HashMap<String,CelldesignerRNADocument.CelldesignerRNA>();
	HashMap<String,CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> antisense_rnas = new HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA>();
	HashMap<String,CelldesignerGeneDocument.CelldesignerGene> genes = new HashMap<String, CelldesignerGeneDocument.CelldesignerGene>();
	float defaultProteinWidth = 80;
	float defaultProteinHeight = 40;
	float defaultGeneWidth = 70;
	float defaultGeneHeight = 25;
	float defaultRNAWidth = 70;
	float defaultRNAHeight = 25;
	float defaultAntisenseRNAWidth = 70;
	float defaultAntisenseRNAHeight = 25;
	float defaultDegradedWidth = 30;
	float defaultDegradedHeight = 30;
	
	
	public float sparseGap = 50f; 
	public float gridStep = 10f;
	String usualViewColor_protein = "ffccffcc";
	String briefViewColor_protein = "3fff0000";
	String usualViewColor_gene = "ffffff66";
	String briefViewColor_gene = "3fff0000";
	String usualViewColor_rna = "ff00ff00";
	String briefViewColor_rna = "3fff0000";
	String usualViewColor_antisenserna = "ffff6666";
	String briefViewColor_antisenserna = "3fff0000";
	String usualViewColor_degraded = "ffffcccc";
	
	
	String usualViewColor_forComplex = "fff7f7f7";
	String briefViewColor_forComplex = "fff7f7f7";
	
	String listOfResidueModifications[] = {"phosphorylated","acetylated","ubiquitinated","methylated","hydroxylated","glycosylated","myristoylated","palmytoylated", "prenylated", "protonated", "sulfated", "don't care", "unknown"};
	
	
	HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
	HashMap<String,ReactionDocument.Reaction> reactions = new HashMap<String,ReactionDocument.Reaction>();
	HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies> celldesigner_species = new HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies>();	
	HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> speciesAliases = new HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
	HashMap<String,String> speciesId2StructuredString = new HashMap<String,String>();
	HashMap<String,CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexSpeciesAliases = new HashMap<String,CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();	
	HashMap<String,CompartmentDocument.Compartment> compartments = new HashMap<String,CompartmentDocument.Compartment>();
	
	public Vector<Statement> statements = new Vector<Statement>(); 
	
	
	public static void main(String[] args) {
		try{
		CellDesignerGenerator cg = new CellDesignerGenerator();
		cg.createNewCellDesignerFile("Benchmark1",1000,1500);
		
		/*cg.createSpeciesFromString("gB", "gB");
		cg.createSpeciesFromString("B:C", "B:C@nucleus");
		cg.createSpeciesFromString("B:C:D", "B:C:D@nucleus");
		cg.createSpeciesFromString("A:B:C:D", "A:B:C:D@nucleus");
		cg.createSpeciesFromString("A:B:C:D:E:F:G:T:S", "A:B:C:D:E:F:G:T:S@nucleus");
		cg.createSpeciesFromString("A|Thr239_pho", "A|Thr239_pho@nucleus");
		cg.createSpeciesFromString("A|Ser15_ace", "A|Ser15_ace@nucleus");
		cg.createSpeciesFromString("A|pho|Ser15_met", "A|pho|Ser15_met@nucleus");		
		cg.createSpeciesFromString("B:A|pho|Ser15_met", "B:A|pho|Ser15_met@nucleus");*/
		
		/*cg.createReactionFromString("A -> B");
		cg.createReactionFromString("B -> B|pho");
		cg.createReactionFromString("A + B|pho +C -> A:B|pho|Ser15_met:C");
		cg.createReactionFromString("A:B|pho|Ser15_met:C -> A + B + C|met");
		cg.createReactionFromString("gA -..> rA");
		cg.createReactionFromString("rA -.> A");
		cg.createReactionFromString("gB -..> rB");
		cg.createReactionFromString("rB -.> B");
		cg.createReactionFromString("A+B -|arC -> A:B|pho");*/
		
		/*cg.addReactionStatement("rA@cytoplasm -.> A@cytoplasm");
		cg.addReactionStatement("gB@nucleus -..> rB@cytoplasm");
		cg.addReactionStatement("rB@cytoplasm -.> B@cytoplasm");
		cg.addReactionStatement("A@cytoplasm+B@cytoplasm -|arC@cytoplasm -> A:B|pho@cytoplasm");
		cg.addReactionStatement("A@cytoplasm -|C@cytoplasm -> B@cytoplasm");
		
		cg.addReactionStatement("TNF -/> TNF@cytoplasm");
		cg.addReactionStatement("TP53 -TNF -> TP53|pho");*/
		
		//cg.addSpeciesStatement("MDM2");
		//cg.addReactionStatement("TP53 -MDM2 -> null");
		//cg.addReactionStatement("MDM2 -> null");
		//cg.addReactionStatement("A+B -> A:B");
		//cg.addReactionStatement("->A:A");
		//cg.addSpeciesStatement("DLC1|phosphorylated_residue_MOD:00696:microtubules@plasma_membrane");
		//cg.addReactionStatement("MOMP-+>CYTOCHROME_C");
		//cg.addSpeciesStatement("PIP3(16_0/16_0)");
		//cg.addReactionStatement("A@cytosol -/> A|active@nucleus");
		//cg.addReactionStatement("A:B@cytosol -/> A:B|active@nucleus");
		//cg.addReactionStatement("A:B:C@cytosol -/> (A:B:C)|active@nucleus");
		//cg.addSpeciesStatement("TP53|hm2|active:MDM2@nucleus");
		//cg.addReactionStatement("A+C/EBP-beta -> A:C/EBP-beta");
		//cg.addSpeciesStatement("Lamin|pho");
		//cg.addSpeciesStatement("TP53|hm8");
		cg.addSpeciesStatement("A@nucleus");
		cg.addReactionStatement("A@nucleus->B@");
		cg.processStatements();
		
		//cg.createReactionFromString("TP53 -> null");
		//cg.createReactionFromString("MDM2 -> null");

		
		CellDesigner.saveCellDesigner(cg.cd, "c:/datas/binomtest/testCreateCD.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void createNewCellDesignerFile(String id, int sizeX, int sizeY) throws Exception{
		cd = SbmlDocument.Factory.newInstance();
		cd.addNewSbml();
		cd.getSbml().addNewModel(); 
		cd.getSbml().getModel().setId(id);
		cd.getSbml().getModel().addNewAnnotation();
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue("4.0");
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerModelVersion();
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelVersion().set(xs);
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerModelDisplay();
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+sizeX);
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(""+sizeY);	
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfCompartmentAliases();
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfIncludedSpecies();
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfComplexSpeciesAliases();		
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfSpeciesAliases();		
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfGroups();		
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfProteins();				
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfGenes();				
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfRNAs();				
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfAntisenseRNAs();				
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfLayers();				
		cd.getSbml().getModel().getAnnotation().addNewCelldesignerListOfBlockDiagrams();				
		cd.getSbml().getModel().addNewListOfCompartments();		
		cd.getSbml().getModel().addNewListOfEvents();
		cd.getSbml().getModel().addNewListOfFunctionDefinitions();
		cd.getSbml().getModel().addNewListOfReactions();
		cd.getSbml().getModel().addNewListOfRules();
		cd.getSbml().getModel().addNewListOfSpecies();
		cd.getSbml().getModel().addNewListOfUnitDefinitions();
		cd.getSbml().getModel().addNewNotes();		
		
	}
	
	public SpeciesDocument.Species createSpeciesFromString(String name, String structuredString){
		return createSpeciesFromString(name, structuredString, false);
	}
	
	public SpeciesDocument.Species createSpeciesFromString(String name, String structuredString, boolean positionInCompartment){
		SpeciesDocument.Species sp = createSpeciesFromString(getNewSpeciesId(), name, structuredString, 0f, 0f);
		positionSpeciesAliasAutomatically(sp.getId()+"_alias", structuredString, positionInCompartment);
		return sp;
	}
	
	public void positionSpeciesAliasAutomatically(String aliasid, String structuredString, boolean positionInCompartment){
	
		float x[] = new float[speciesAliases.entrySet().size()];
		float y[] = new float[speciesAliases.entrySet().size()];
		float w[] = new float[speciesAliases.entrySet().size()];
		float h[] = new float[speciesAliases.entrySet().size()];
		int k=0;
		for(String s: speciesAliases.keySet()){
			x[k] = Float.parseFloat(speciesAliases.get(s).getCelldesignerBounds().getX());
			y[k] = Float.parseFloat(speciesAliases.get(s).getCelldesignerBounds().getY());
			w[k] = Float.parseFloat(speciesAliases.get(s).getCelldesignerBounds().getW());
			h[k] = Float.parseFloat(speciesAliases.get(s).getCelldesignerBounds().getH());
			k++;
		}	
		
		String compname = SpeciesStructure.decodeStructuredString(structuredString).compartment;
		float compX = 0f;
		float compY = 0f;
		float compW = 0f;
		float compH = 0f;
		int numberOfCompartments = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();
		float cX[] = new float[numberOfCompartments];
		float cY[] = new float[numberOfCompartments];
		float cW[] = new float[numberOfCompartments];
		float cH[] = new float[numberOfCompartments];
		k=0;
		for(CelldesignerCompartmentAlias cmp: cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray()){
			if(!compname.equals("default"))if(cmp.getCompartment().equals(compname)){
				compX = Float.parseFloat(cmp.getCelldesignerBounds().getX());
				compY = Float.parseFloat(cmp.getCelldesignerBounds().getY());
				compW = Float.parseFloat(cmp.getCelldesignerBounds().getW());
				compH = Float.parseFloat(cmp.getCelldesignerBounds().getH());
			}
			cX[k] = Float.parseFloat(cmp.getCelldesignerBounds().getX());
			cY[k] = Float.parseFloat(cmp.getCelldesignerBounds().getY());
			cW[k] = Float.parseFloat(cmp.getCelldesignerBounds().getW());
			cH[k] = Float.parseFloat(cmp.getCelldesignerBounds().getH());
			k++;
		}
		
		
		float sizeX = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		float sizeY = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());

		float wal = 0f;
		float hal = 0f;
		
		if(speciesAliases.get(aliasid)!=null){
			wal = Float.parseFloat(speciesAliases.get(aliasid).getCelldesignerBounds().getW());
			hal = Float.parseFloat(speciesAliases.get(aliasid).getCelldesignerBounds().getH());
		}
		if(complexSpeciesAliases.get(aliasid)!=null){
			wal = Float.parseFloat(complexSpeciesAliases.get(aliasid).getCelldesignerBounds().getW());
			hal = Float.parseFloat(complexSpeciesAliases.get(aliasid).getCelldesignerBounds().getH());
		}
		
		
		boolean positioned = false;
		for(float yf=20;yf<sizeY-20;yf+=gridStep){
			for(float xf=20;xf<sizeX-20;xf+=gridStep){
				boolean overlap = false;
				for(int i=0;i<x.length;i++){
					if((Math.abs(x[i]-xf)<w[i]+sparseGap)&&(Math.abs(y[i]-yf)<h[i]+sparseGap)){
						overlap = true;
						break;
					}
				}
				
				// Now, check if it is inside the right compartment
				if(positionInCompartment){
					if(compname.equals("default")){
						boolean insideAnyCompartment = false;
						for(int l=0;l<cX.length;l++)
							if(!Utils.checkIfOutsideRectangle(xf, yf, wal, hal, cX[l], cY[l], cW[l], cH[l], 20f))
								insideAnyCompartment = true;
						if(insideAnyCompartment)
							overlap = true;
					}else{
						if(!Utils.checkIfInsideRectangle(xf, yf, wal, hal, compX, compY, compW, compH, 20f))
							overlap = true;
					}
				}
				
				
				if(!overlap){
					if(speciesAliases.get(aliasid)!=null){
						speciesAliases.get(aliasid).getCelldesignerBounds().setX(""+xf);
						speciesAliases.get(aliasid).getCelldesignerBounds().setY(""+yf);
						speciesAliases.get(aliasid).setCompartmentAlias(compname+"_alias");
					}
					if(complexSpeciesAliases.get(aliasid)!=null){
						moveComplexSpeciesAliasTo(complexSpeciesAliases.get(aliasid), xf, yf);
						complexSpeciesAliases.get(aliasid).setCompartmentAlias(compname+"_alias");
					}
					positioned = true;
					break;
				}
			}
			if(positioned)
				break;
		}	
			
	}
	
	public void moveComplexSpeciesAliasTo(CelldesignerComplexSpeciesAlias alias, float xf, float yf){
		float currentX = Float.parseFloat(alias.getCelldesignerBounds().getX());
		float currentY = Float.parseFloat(alias.getCelldesignerBounds().getY());
		alias.getCelldesignerBounds().setX(""+xf);
		alias.getCelldesignerBounds().setY(""+yf);
		for(String als: speciesAliases.keySet()){
			CelldesignerSpeciesAlias sa = speciesAliases.get(als);
			if(sa.getComplexSpeciesAlias()!=null)
			if(sa.getComplexSpeciesAlias().equals(alias.getId())){
				float x = Float.parseFloat(sa.getCelldesignerBounds().getX());
				float y = Float.parseFloat(sa.getCelldesignerBounds().getY());
				sa.getCelldesignerBounds().setX(""+(x+xf-currentX));
				sa.getCelldesignerBounds().setY(""+(y+yf-currentY));
			}
		}
	}
	
	
	public SpeciesDocument.Species createSpeciesFromString(String name, String structuredString, float x, float y){
		return createSpeciesFromString(getNewSpeciesId(), name, structuredString, x, y);
	}
	
	public SpeciesDocument.Species createSpeciesFromString(String id, String name, String structuredString, float x, float y){
		SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().addNewSpecies();
		sp.setId(id);
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);		
		sp.setName(xs);
		species.put(id, sp);
		//species.put(name, sp);
		species.put(structuredString, sp);
		speciesId2StructuredString.put(id,structuredString);
		
		SpeciesStructure ss = SpeciesStructure.decodeStructuredString(structuredString);
		createNewCompartment(ss.compartment, ss.compartment);
		
		if(ss.components.size()>1){
			// Create complex
			for(int i=0;i<ss.components.size();i++){
				SpeciesStructure.SpeciesStructureComponent ssm = ss.components.get(i);
				String entity_id = "";
				if(ssm.entityType.equals("PROTEIN")){
					entity_id = "pr_"+ssm.name;
					entity_id = Utils.correctName(entity_id);
					createNewProtein(entity_id, ssm.labelname);
				}
				if(ssm.entityType.equals("GENE")){
					entity_id = "ge_"+ssm.name;				
					entity_id = Utils.correctName(entity_id);					
					createNewGene(entity_id, ssm.labelname);
				}
				if(ssm.entityType.equals("RNA")){
					entity_id = "rn_"+ssm.name;				
					entity_id = Utils.correctName(entity_id);					
					createNewRNA(entity_id, ssm.labelname);
				}
				if(ssm.entityType.equals("ANTISENSE_RNA")){
					entity_id = "ar_"+ssm.name;				
					entity_id = Utils.correctName(entity_id);					
					createNewAntisenseRNA(entity_id, ssm.labelname);
				}
				
				String celldesignerSpeciesId = Utils.correctName(ssm.toString());
				String complexId = Utils.correctName(ss.toString());				
				
				CelldesignerSpeciesDocument.CelldesignerSpecies cs = createCelldesignerSpecies(celldesignerSpeciesId, id, ssm.name, entity_id, ssm);
				if(cs==null)
					System.out.println(ssm.toString()+" is not created");
				//xs = XmlString.Factory.newInstance(); xs.setStringValue(complexId);
				//cs.addNewCelldesignerAnnotation().addNewCelldesignerComplexSpecies().set(xs);
				if(ssm.modifications.size()>0){
					for(SpeciesStructureComponentModification mod: ssm.modifications){
						addModificationToProtein(entity_id, mod);
					}
					for(SpeciesStructureComponentModification mod: ssm.modifications){
						setResidueModification(cs, mod);
					}
				}


				int h1 = 1;
				int w1 = 1;
				if(ss.components.size()<4){
					h1 = ss.components.size();
				}else if(ss.components.size()<9){
					h1 = Math.round(ss.components.size()/2f+0.3f);
					w1 =2;
				}else{
					h1 = Math.round(ss.components.size()/3f+0.3f);
					w1 =3;
				}
				
				float dw = 0f;
				float dh = 0f;
				if(ssm.entityType.equals("PROTEIN")){
					dw = defaultProteinWidth;
					dh = defaultProteinHeight;
				}
				if(ssm.entityType.equals("GENE")){
					dw = defaultGeneWidth;
					dh = defaultGeneHeight;
				}
				if(ssm.entityType.equals("RNA")){
					dw = defaultRNAWidth;
					dh = defaultRNAHeight;
				}
				if(ssm.entityType.equals("ANTISENSE_RNA")){
					dw = defaultAntisenseRNAWidth;
					dh = defaultAntisenseRNAHeight;
				}
				if(ssm.entityType.equals("DEGRADED")){
					dw = defaultDegradedWidth;
					dh = defaultDegradedHeight;
				}
				
				
				
				float dx = dw/8f;
				float dy = dh/4f;
				
				int col = (int)(1f*i/h1);
				int row = i - col*w1;
				dx+=dw*col;
				dy+=dh*row;
				
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = createSpeciesAlias(celldesignerSpeciesId+"_in_"+id+"_alias",celldesignerSpeciesId,ssm,x+dx,y+dy);
				csa.setComplexSpeciesAlias(id+"_alias");
			}
			Annotation annot = sp.addNewAnnotation();
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = annot.addNewCelldesignerSpeciesIdentity();
			CelldesignerClassDocument.CelldesignerClass cc = spi.addNewCelldesignerClass();
			xs = XmlString.Factory.newInstance(); xs.setStringValue("COMPLEX");
			cc.set(xs);
			xs = XmlString.Factory.newInstance(); xs.setStringValue(name);
			spi.addNewCelldesignerName().set(xs);

			sp.setCompartment(ss.compartment);			
			
			createComplexSpeciesAlias(id+"_alias",id,ss,x,y);
			
		}else{
			// Create simple species
			SpeciesStructure.SpeciesStructureComponent ssm = ss.components.get(0);

			String entity_id = "";
			if(ssm.entityType.equals("PROTEIN")){
				entity_id = "pr_"+ssm.name;
				entity_id = Utils.correctName(entity_id);				
				createNewProtein(entity_id, ssm.labelname);
			}
			if(ssm.entityType.equals("GENE")){
				entity_id = "ge_"+ssm.name;
				entity_id = Utils.correctName(entity_id);				
				createNewGene(entity_id, ssm.labelname);
			}
			if(ssm.entityType.equals("RNA")){
				entity_id = "rn_"+ssm.name;
				entity_id = Utils.correctName(entity_id);				
				createNewRNA(entity_id, ssm.labelname);
			}
			if(ssm.entityType.equals("ANTISENSE_RNA")){
				entity_id = "ar_"+ssm.name;
				entity_id = Utils.correctName(entity_id);				
				createNewAntisenseRNA(entity_id, ssm.labelname);
			}
			
			if(ssm.modifications.size()>0){
				for(SpeciesStructureComponentModification mod: ssm.modifications){
					addModificationToProtein(entity_id, mod);
				}
			}
			
			Annotation annot = sp.addNewAnnotation();
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = annot.addNewCelldesignerSpeciesIdentity();
			CelldesignerClassDocument.CelldesignerClass cc = spi.addNewCelldesignerClass();
			xs = XmlString.Factory.newInstance(); xs.setStringValue(ssm.entityType);
			cc.set(xs);
			if(ssm.entityType.equals("PROTEIN")){
				CelldesignerProteinReferenceDocument.CelldesignerProteinReference pr = spi.addNewCelldesignerProteinReference();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(Utils.correctName("pr_"+ssm.name));			
				pr.set(xs);
			}
			if(ssm.entityType.equals("GENE")){
				CelldesignerGeneReferenceDocument.CelldesignerGeneReference pr = spi.addNewCelldesignerGeneReference();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(Utils.correctName("ge_"+ssm.name));			
				pr.set(xs);
			}
			if(ssm.entityType.equals("RNA")){
				CelldesignerRnaReferenceDocument.CelldesignerRnaReference pr = spi.addNewCelldesignerRnaReference();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(Utils.correctName("rn_"+ssm.name));			
				pr.set(xs);
			}
			if(ssm.entityType.equals("ANTISENSE_RNA")){
				CelldesignerAntisensernaReferenceDocument.CelldesignerAntisensernaReference pr = spi.addNewCelldesignerAntisensernaReference();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(Utils.correctName("ar_"+ssm.name));			
				pr.set(xs);
			}
			
			
			sp.setCompartment(ss.compartment);
			
			if(ssm.modifications.size()>0){
				for(SpeciesStructureComponentModification mod: ssm.modifications){
						setResidueModification(sp, mod);
				}
			}
			
			
			createSpeciesAlias(id+"_alias",id,ssm,x,y);
		}
		
		return sp;
	}
	
	public CompartmentDocument.Compartment createNewCompartment(String id, String name){
		CompartmentDocument.Compartment compartment = compartments.get(id); 
		if(compartment==null){
			compartment = cd.getSbml().getModel().getListOfCompartments().addNewCompartment();
			compartment.setId(id);
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);		
			compartment.setName(xs);
			compartments.put(id, compartment);
		}
		return compartment;
	}
	
	public CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias createComplexSpeciesAlias(String id, String sp_id, SpeciesStructure ss, float x, float y){
	
	CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = complexSpeciesAliases.get(id);
	if(csa==null){
	csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias();
	csa.setSpecies(sp_id);
	csa.setId(id);
	XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue("inactive");
	if(ss.globalModifier.equals("active"))
		xs.setStringValue("active");
	csa.addNewCelldesignerActivity().set(xs);
		CelldesignerBoundsDocument.CelldesignerBounds cb = csa.addNewCelldesignerBounds();
		cb.setX(""+x);
		cb.setY(""+y);
	float h = 0;
	float w = 0;
	int h1 = 1;
	int w1 = 1;
	if(ss.components.size()<4){
		h1 = ss.components.size();
	}else if(ss.components.size()<9){
		h1 = Math.round(ss.components.size()/2f+0.3f);
		w1 =2;
	}else{
		h1 = Math.round(ss.components.size()/3f+0.3f);
		w1 =3;
	}
	
	
	w = defaultProteinWidth*w1+defaultProteinWidth/4f;
	h = defaultProteinHeight*h1+defaultProteinHeight;
	cb.setH(""+h);
	cb.setW(""+w);

	csa.addNewCelldesignerView().setState(CelldesignerView.State.USUAL);
	CelldesignerUsualViewDocument.CelldesignerUsualView uv = csa.addNewCelldesignerUsualView();
		CelldesignerBoxSizeDocument.CelldesignerBoxSize bs = uv.addNewCelldesignerBoxSize();
		bs.setWidth(""+w); bs.setHeight(""+h);
		CelldesignerPaintDocument.CelldesignerPaint p = uv.addNewCelldesignerPaint();
		p.setScheme(CelldesignerPaint.Scheme.COLOR);
		xs = XmlString.Factory.newInstance(); xs.setStringValue(this.usualViewColor_forComplex);
		p.setColor(xs);
		CelldesignerInnerPositionDocument.CelldesignerInnerPosition ip = uv.addNewCelldesignerInnerPosition();
		ip.setX("0"); ip.setY("0");
		uv.addNewCelldesignerSingleLine().setWidth("1");
	CelldesignerBriefViewDocument.CelldesignerBriefView bv = csa.addNewCelldesignerBriefView();
		bs = bv.addNewCelldesignerBoxSize();
		bs.setWidth(""+w); bs.setHeight(""+h);
		ip = bv.addNewCelldesignerInnerPosition();
		ip.setX("0"); ip.setY("0");
		bv.addNewCelldesignerSingleLine().setWidth("1");
		p = bv.addNewCelldesignerPaint();
		p.setScheme(CelldesignerPaint.Scheme.COLOR);
		xs = XmlString.Factory.newInstance(); xs.setStringValue(this.usualViewColor_forComplex);
		p.setColor(xs);
		complexSpeciesAliases.put(id, csa);
	}
	return csa;
	}
	
	
	public CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias createSpeciesAlias(String id, String sp_id, SpeciesStructureComponent ssm, float x, float y){
		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = speciesAliases.get(id); 
		if(alias==null){
		alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias();
		alias.setId(id);
		alias.setSpecies(sp_id);
			CelldesignerActivityDocument.CelldesignerActivity aa = alias.addNewCelldesignerActivity();
			XmlString xs = null;
			boolean active = false;
			for(SpeciesStructureComponentModification sm: ssm.modifications)
				if(sm.modification_type.equals("active"))
					active = true;
			if(!active){
				xs = XmlString.Factory.newInstance(); xs.setStringValue("inactive");
			}else{
				xs = XmlString.Factory.newInstance(); xs.setStringValue("active");
			}
			aa.set(xs);
			CelldesignerBoundsDocument.CelldesignerBounds ab = alias.addNewCelldesignerBounds();
			ab.setX(""+x); ab.setY(""+y);
			
			float dw = 0f;
			float dh = 0f;
			String color = "";
			if(ssm.entityType.equals("PROTEIN")){
				dw = defaultProteinWidth;
				dh = defaultProteinHeight;
				color = usualViewColor_protein;
			}
			if(ssm.entityType.equals("GENE")){
				dw = defaultGeneWidth;
				dh = defaultGeneHeight;
				color = usualViewColor_gene;				
			}
			if(ssm.entityType.equals("RNA")){
				dw = defaultRNAWidth;
				dh = defaultRNAHeight;
				color = usualViewColor_rna;				
			}
			if(ssm.entityType.equals("ANTISENSE_RNA")){
				dw = defaultAntisenseRNAWidth;
				dh = defaultAntisenseRNAHeight;
				color = usualViewColor_antisenserna;				
			}
			if(ssm.entityType.equals("DEGRADED")){
				dw = defaultDegradedWidth;
				dh = defaultDegradedHeight;
				color = usualViewColor_degraded;
			}
			
			
			
		ab.setH(""+dh); ab.setW(""+dw);
		alias.addNewCelldesignerView().setState(CelldesignerView.State.USUAL);
			CelldesignerUsualViewDocument.CelldesignerUsualView uv = alias.addNewCelldesignerUsualView();
				CelldesignerBoxSizeDocument.CelldesignerBoxSize bs = uv.addNewCelldesignerBoxSize();
				bs.setWidth(""+dw); bs.setHeight(""+dh);
				CelldesignerPaintDocument.CelldesignerPaint p = uv.addNewCelldesignerPaint();
				p.setScheme(CelldesignerPaint.Scheme.COLOR);
				xs = XmlString.Factory.newInstance(); xs.setStringValue(color);
				p.setColor(xs);
				CelldesignerInnerPositionDocument.CelldesignerInnerPosition ip = uv.addNewCelldesignerInnerPosition();
				ip.setX("0"); ip.setY("0");
				uv.addNewCelldesignerSingleLine().setWidth("1");
			CelldesignerBriefViewDocument.CelldesignerBriefView bv = alias.addNewCelldesignerBriefView();
				bs = bv.addNewCelldesignerBoxSize();
				bs.setWidth(""+dw); bs.setHeight(""+dh);
				ip = bv.addNewCelldesignerInnerPosition();
				ip.setX("0"); ip.setY("0");
				bv.addNewCelldesignerSingleLine().setWidth("1");
				p = bv.addNewCelldesignerPaint();
				p.setScheme(CelldesignerPaint.Scheme.COLOR);
				xs = XmlString.Factory.newInstance(); xs.setStringValue(color);
				p.setColor(xs);
				
				speciesAliases.put(id, alias);
		}
		return alias;
	}
	
	public CelldesignerProteinDocument.CelldesignerProtein createNewProtein(String id, String name){
		CelldesignerProteinDocument.CelldesignerProtein protein = null;
		protein = proteins.get(id);
		if(protein==null){
			protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein();
			protein.setId(id);
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);
			protein.setName(xs);
			proteins.put(id, protein);
		}
		return protein;
	}
	public CelldesignerGeneDocument.CelldesignerGene createNewGene(String id, String name){
		CelldesignerGeneDocument.CelldesignerGene gene = genes.get(id);
		if(gene==null){
			gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().addNewCelldesignerGene();
			gene.setId(id);
			gene.setName(name);
			genes.put(id, gene);
		}
		return gene;
	}
	public CelldesignerRNADocument.CelldesignerRNA createNewRNA(String id, String name){
		CelldesignerRNADocument.CelldesignerRNA rna = rnas.get(id);
		if(rna==null){
			rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA();
			rna.setId(id);
			rna.setName(name);
			rnas.put(id, rna);
		}
		return rna;
	}
	public CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA createNewAntisenseRNA(String id, String name){
		CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asrna = antisense_rnas.get(id);
		if(asrna==null){
			asrna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().addNewCelldesignerAntisenseRNA();
			asrna.setId(id);
			asrna.setName(name);
			antisense_rnas.put(id, asrna);
		}
		return asrna;
	}
	
	
	
	public CelldesignerSpeciesDocument.CelldesignerSpecies createCelldesignerSpecies(String id, String complex_id, String name, String entity_id, SpeciesStructure.SpeciesStructureComponent ssm){
		CelldesignerSpeciesDocument.CelldesignerSpecies cs = celldesigner_species.get(id);
		if(celldesigner_species.get(id)==null){
		cs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies();
		cs.setId(id);
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);				
		cs.setName(xs);
		cs.addNewCelldesignerAnnotation();
		xs = XmlString.Factory.newInstance(); xs.setStringValue(complex_id);
		cs.getCelldesignerAnnotation().addNewCelldesignerComplexSpecies().set(xs);
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity csi = cs.getCelldesignerAnnotation().addNewCelldesignerSpeciesIdentity();
		xs = XmlString.Factory.newInstance(); xs.setStringValue(ssm.entityType);		
		csi.addNewCelldesignerClass().set(xs);
		if(ssm.entityType.equals("PROTEIN")){
			xs = XmlString.Factory.newInstance(); xs.setStringValue(entity_id);		
			csi.addNewCelldesignerProteinReference().set(xs);
		}
		if(ssm.entityType.equals("GENE")){
			xs = XmlString.Factory.newInstance(); xs.setStringValue(entity_id);		
			csi.addNewCelldesignerGeneReference().set(xs);
		}
		if(ssm.entityType.equals("RNA")){
			xs = XmlString.Factory.newInstance(); xs.setStringValue(entity_id);		
			csi.addNewCelldesignerRnaReference().set(xs);
		}
		if(ssm.entityType.equals("ANTISENSE_RNA")){
			xs = XmlString.Factory.newInstance(); xs.setStringValue(entity_id);		
			csi.addNewCelldesignerAntisensernaReference().set(xs);
		}		
		celldesigner_species.put(id, cs);
		}
		return cs;
	}
	
	public void addModificationToProtein(String pid, SpeciesStructureComponentModification mod){
		if(!mod.modification_type.startsWith("hm"))if(!mod.modification_type.equals("active")){
		CelldesignerProteinDocument.CelldesignerProtein protein = proteins.get(pid);
		CelldesignerModificationResidueDocument.CelldesignerModificationResidue residue = null;
		int numberOfResidues = 0;
		if(protein.getCelldesignerListOfModificationResidues()==null)
			protein.addNewCelldesignerListOfModificationResidues();
		for(CelldesignerModificationResidueDocument.CelldesignerModificationResidue res : protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray()){
			String resname = Utils.getValue(res.getName());
			if(resname.equals(mod.name))
				residue = res;
			numberOfResidues++;
		}
		if(residue==null){
			CelldesignerModificationResidueDocument.CelldesignerModificationResidue res = protein.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue();
			String id = "re_"+(numberOfResidues+1);
			res.setId(id);
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(mod.name);
			res.setName(xs);
			float angle = 0.8f+(numberOfResidues)*1.5f;
			res.setAngle(""+angle);
			residue = res;
			mod.id = id;
		}else{
			mod.id = residue.getId();
		}
		}
	}
	
	public void setResidueModification(SpeciesDocument.Species sp, SpeciesStructureComponentModification mod){
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = sp.getAnnotation().getCelldesignerSpeciesIdentity();
		if(sp.getAnnotation().getCelldesignerSpeciesIdentity()==null){
			spi = sp.getAnnotation().addNewCelldesignerSpeciesIdentity();
		}
		setResidueModification(spi, mod);
	}
	
	public void setResidueModification(CelldesignerSpeciesDocument.CelldesignerSpecies sp, SpeciesStructureComponentModification mod){
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
		if(sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity()==null){
			spi = sp.getCelldesignerAnnotation().addNewCelldesignerSpeciesIdentity();
		}
		setResidueModification(spi, mod);
	}
	
	
	public void setResidueModification(CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi, SpeciesStructureComponentModification mod){
		if(!mod.modification_type.startsWith("hm"))if(!mod.modification_type.equals("active")){
		if(spi.getCelldesignerState()==null){
			spi.addNewCelldesignerState();
		}
		if(spi.getCelldesignerState().getCelldesignerListOfModifications()==null){
			spi.getCelldesignerState().addNewCelldesignerListOfModifications();
		}
		CelldesignerModificationDocument.CelldesignerModification cmod = spi.getCelldesignerState().getCelldesignerListOfModifications().addNewCelldesignerModification();
		String modificationType = "unknown";
		for(int i=0;i<listOfResidueModifications.length;i++)
			if(listOfResidueModifications[i].startsWith(mod.modification_type.toLowerCase()))
				modificationType = listOfResidueModifications[i];
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(modificationType);
		cmod.setState(xs);
		cmod.setResidue(mod.id);
		}
		if(mod.modification_type.startsWith("hm")){
			int homodimer = 1;
			try{
				homodimer = Integer.parseInt(mod.modification_type.substring(2, mod.modification_type.length()));
			}catch(Exception e){
				
			}
			if(spi.getCelldesignerState()==null){
				spi.addNewCelldesignerState();
			}
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(""+homodimer);
			spi.getCelldesignerState().addNewCelldesignerHomodimer().set(xs);
		}
	}
	
	public String getNewSpeciesId(){
		String id = "s";
		for(int i=1;i<10000;i++){
			id = "s"+i;
			if(!species.containsKey(id))
				break;
		}
		return id;
	}
	
	public String getNewReactionId(){
		String id = "s";
		for(int i=1;i<10000;i++){
			id = "re"+i;
			if(!reactions.containsKey(id))
				break;
		}
		return id;
	}
	
	
	public String getSpeciesName(String spname){
		return spname.split("@")[0];
	}
	
	public ReactionDocument.Reaction createReactionFromString(String reactionString){
		String reid = getNewReactionId();
		Vector<String> declaredSpecies = new Vector<String>(); 
		return createReactionFromString(reid, reactionString, declaredSpecies);
	}
	
	public ReactionDocument.Reaction createReactionFromString(String reactionString, Vector<String> declaredSpecies){
		String reid = getNewReactionId();
		return createReactionFromString(reid, reactionString, declaredSpecies);
	}
	
	public ReactionDocument.Reaction createReactionFromString(String reid, String reactionString, Vector<String> declaredSpecies){
		ReactionStructure rs = ReactionStructure.decodeStructuredString(reactionString, declaredSpecies);
		ReactionDocument.Reaction r = null;
		if(rs.reactants.size()>0){
		
		for(int i=0;i<rs.products.size();i++){
			SpeciesStructure ss = SpeciesStructure.decodeStructuredString(rs.products.get(i));
			if(ss.components.get(0).name.startsWith("null")){
				ss.components.get(0).name = "null_"+reid;
				rs.products.set(i, ss.toString());
			}
		}
		reactionString = rs.toString();
		
		r = cd.getSbml().getModel().getListOfReactions().addNewReaction();
		r.setReversible("false");
		r.setId(reid);
		org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation annotation = r.addNewAnnotation();
		
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(rs.reactionType);
		annotation.addNewCelldesignerReactionType().set(xs);
		
		annotation.addNewCelldesignerBaseReactants();
		annotation.addNewCelldesignerBaseProducts();
		annotation.addNewCelldesignerConnectScheme().setConnectPolicy("direct");
		
		
		if(rs.reactionType.equals("HETERODIMER_ASSOCIATION")||rs.reactionType.equals("DISSOCIATION")){
			CelldesignerEditPointsDocument.CelldesignerEditPoints cep = annotation.addNewCelldesignerEditPoints();
			xs = XmlString.Factory.newInstance(); xs.setStringValue("0.25,0.5");
			cep.set(xs);
			cep.setNum0("0");
			cep.setNum1("0");
			cep.setNum2("0");
			cep.setTShapeIndex("0");
		}
		
		int expectedNumberOfReactants = 1;
		int expectedNumberOfProducts = 1;
		if(rs.reactionType.equals("HETERODIMER_ASSOCIATION")){
			expectedNumberOfReactants = 2;
		}
		if(rs.reactionType.equals("DISSOCIATION")){
			expectedNumberOfProducts = 2;
		}
		
		
		
		if(rs.reactants.size()>0){
			ListOfReactantsDocument.ListOfReactants lr = r.addNewListOfReactants();
			for(int i=0;i<rs.reactants.size();i++){
				SpeciesDocument.Species sp = species.get(rs.reactants.get(i));
				if(sp==null){ 
					sp = createSpeciesFromString(getSpeciesName(rs.reactants.get(i)), rs.reactants.get(i));
				}
				SpeciesReferenceDocument.SpeciesReference sr = lr.addNewSpeciesReference();
				sr.setSpecies(sp.getId());
				org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation an = sr.addNewAnnotation();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(sp.getId()+"_alias");
				an.addNewCelldesignerAlias().set(xs);
				
				if(i<expectedNumberOfReactants){
					CelldesignerBaseReactantDocument.CelldesignerBaseReactant br = annotation.getCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
					xs = XmlString.Factory.newInstance(); xs.setStringValue(sp.getId());				
					br.setSpecies(xs);
					br.setAlias(sp.getId()+"_alias");
				}else{
					if(annotation.getCelldesignerListOfReactantLinks()==null) annotation.addNewCelldesignerListOfReactantLinks();
					CelldesignerReactantLinkDocument.CelldesignerReactantLink brl = annotation.getCelldesignerListOfReactantLinks().addNewCelldesignerReactantLink();
					brl.setReactant(sp.getId());
					brl.setAlias(sp.getId()+"_alias");
				}
				}
		}
		if(rs.products.size()>0){
			ListOfProductsDocument.ListOfProducts lp = r.addNewListOfProducts();
			for(int i=0;i<rs.products.size();i++){
				SpeciesDocument.Species sp = species.get(rs.products.get(i));
				if(sp==null){ 
					String name = getSpeciesName(rs.products.get(i));
					sp = createSpeciesFromString(name, rs.products.get(i));
				}
				SpeciesReferenceDocument.SpeciesReference sr = lp.addNewSpeciesReference();
				sr.setSpecies(sp.getId());
				org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation an = sr.addNewAnnotation();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(sp.getId()+"_alias");
				an.addNewCelldesignerAlias().set(xs);
				
				if(i<expectedNumberOfProducts){
					CelldesignerBaseProductDocument.CelldesignerBaseProduct bp = annotation.getCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
					xs = XmlString.Factory.newInstance(); xs.setStringValue(sp.getId());				
					bp.setSpecies(xs);
					bp.setAlias(sp.getId()+"_alias");
				}else{
					if(annotation.getCelldesignerListOfProductLinks()==null) annotation.addNewCelldesignerListOfProductLinks();
					CelldesignerProductLinkDocument.CelldesignerProductLink brl = annotation.getCelldesignerListOfProductLinks().addNewCelldesignerProductLink();
					brl.setProduct(sp.getId());
					brl.setAlias(sp.getId()+"_alias");
				}
				
				
				}
		}		
		
		if(rs.regulators.size()>0){
			ListOfModifiersDocument.ListOfModifiers lm = r.addNewListOfModifiers();
			for(int i=0;i<rs.regulators.size();i++){
				SpeciesDocument.Species sp = species.get(rs.regulators.get(i).name);
				if(sp==null){ 
					String name = getSpeciesName(rs.regulators.get(i).name);
					sp = createSpeciesFromString(name, rs.regulators.get(i).name);
				}
				ModifierSpeciesReferenceDocument.ModifierSpeciesReference sr = lm.addNewModifierSpeciesReference();
				sr.setSpecies(sp.getId());
				org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation an = sr.addNewAnnotation();
				xs = XmlString.Factory.newInstance(); xs.setStringValue(sp.getId()+"_alias");
				an.addNewCelldesignerAlias().set(xs);
				
				if(annotation.getCelldesignerListOfModification()==null) annotation.addNewCelldesignerListOfModification();
				
				CelldesignerModificationDocument.CelldesignerModification mod = annotation.getCelldesignerListOfModification().addNewCelldesignerModification();				
				mod.setModifiers(sp.getId());
				mod.setAliases(sp.getId()+"_alias");
				mod.setType(rs.regulators.get(i).type);
		}
		}
		
		reactions.put(r.getId(), r);
		}
		return r;
	}
	
	public void addReactionStatement(String statementText){
		addStatement(statementText,REACTION,"");
	}
	
	public void addSpeciesStatement(String statementText){
		addStatement(statementText,SPECIES,"");
	}
	
	
	public void addStatement(String statementText, int statementType, String attributeString){
		Statement st = new Statement();
		st.statementText = statementText;
		st.statementType = statementType;
		if(attributeString!=null)
		if(!attributeString.equals("")){
		String attributes[] = attributeString.split(";");
		for(String s: attributes){
			String pair[] = s.split(":");
			Attribute att = new Attribute(pair[0],pair[1]);
			st.attributes.add(att);
		}
		}
		statements.add(st);
	}
	
	public void processStatements(){
		// first, create a list of species per compartment
		HashMap<String, Vector<String>> compartment2SpeciesNames = new HashMap<String, Vector<String>>(); 
		HashMap<String, Float> compartmentSizes = new HashMap<String, Float>();
		HashSet<String> speciesIds = new HashSet<String>();
		Vector<String> declaredSpecies = new Vector<String>();
		for(Statement st: statements){
			if(st.statementType==SPECIES){
				System.out.println(st.statementText);
				Species sp = processStatements_addSpecies(compartment2SpeciesNames,st.statementText);
				speciesIds.add(sp.getId());
				if(!declaredSpecies.contains(st.statementText))
					declaredSpecies.add(st.statementText);
			}
			if(st.statementType==REACTION){
				System.out.println(st.statementText);
				ReactionStructure rs = ReactionStructure.decodeStructuredString(st.statementText, declaredSpecies);
				for(String s: rs.reactants){
					Species sp = processStatements_addSpecies(compartment2SpeciesNames,s);
					speciesIds.add(sp.getId());
				}
				for(String s: rs.products){
					Species sp = processStatements_addSpecies(compartment2SpeciesNames,s);
					if(sp!=null)
						speciesIds.add(sp.getId());
				}
				for(ReactionStructure.Regulator r: rs.regulators){
					Species sp = processStatements_addSpecies(compartment2SpeciesNames,r.name);
					speciesIds.add(sp.getId());
				}
			}
		}
		// second, compute the required squares of all compartments
		float totalsquare = 0f;
		for(String comp: compartment2SpeciesNames.keySet()){
			Vector<String> sps = compartment2SpeciesNames.get(comp);
			float square = 0f;
			for(String sp: sps){
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = speciesAliases.get(species.get(sp).getId()+"_alias");
				if(alias!=null)
					square+=(Float.parseFloat(alias.getCelldesignerBounds().getW())+2*sparseGap)*(Float.parseFloat(alias.getCelldesignerBounds().getH())+2*sparseGap);
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias calias = complexSpeciesAliases.get(species.get(sp).getId()+"_alias");
				if(calias!=null)
					square+=(Float.parseFloat(calias.getCelldesignerBounds().getW())+2*sparseGap)*(Float.parseFloat(calias.getCelldesignerBounds().getH())+2*sparseGap);
			}
			compartmentSizes.put(comp, square);
			totalsquare+=square;
		}
		// third, set the adequate maps size
		totalsquare*=2f;
		float width = (float)Math.sqrt(totalsquare);
		if(width<500) width = 500;
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+(int)width);
		cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(""+(int)width);
		// forth, create the compartment aliases
		for(String comp: compartment2SpeciesNames.keySet())if(!comp.equals("default")){
			float square = compartmentSizes.get(comp)*1.1f; // we add 20% for spaces
			float compsize = (float)Math.sqrt(square);
			if(compsize<150) compsize = 150;
			processStatements_addCompartmentAlias(comp,compsize);
		}
		// fourth, reposition the aliases
		for(String sid: speciesIds){
			System.out.println("Positioning "+sid);
			CelldesignerSpeciesAlias sas = speciesAliases.get(sid+"_alias");
			if(sas!=null){
				positionSpeciesAliasAutomatically(sas.getId(), speciesId2StructuredString.get(sid), true);
			}
			CelldesignerComplexSpeciesAlias cas = complexSpeciesAliases.get(sid+"_alias");
			if(cas!=null){
				positionSpeciesAliasAutomatically(cas.getId(), speciesId2StructuredString.get(sid), true);
			}
		}
		// fifth, process reactions
		for(Statement st: statements){
			if(st.statementType==REACTION){
				System.out.println(st.statementText);
				createReactionFromString(st.statementText, declaredSpecies);
			}
		}
	}
	
	public Species processStatements_addSpecies(HashMap<String, Vector<String>> compartment2SpeciesNames, String text){
		Species sp = null;
		if(!text.startsWith("null")){
		SpeciesStructure ss = SpeciesStructure.decodeStructuredString(text);
		String comp = ss.compartment;
		Vector<String> spl = compartment2SpeciesNames.get(comp);
		if(spl==null) { spl = new Vector<String>(); compartment2SpeciesNames.put(comp, spl); }
		if(!spl.contains(ss.toString()))
			spl.add(ss.toString());
			sp = species.get(text);
		if(sp==null)
			sp = createSpeciesFromString(ss.toString(false), ss.toString(), -1000f, -1000f);
		}
		return sp;
	}
	
	public void processStatements_addCompartmentAlias(String comp, float compsize){
		
		int numberOfExistingComps = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();
		
		float x[] = new float[numberOfExistingComps];
		float y[] = new float[numberOfExistingComps];
		float w[] = new float[numberOfExistingComps];
		float h[] = new float[numberOfExistingComps];
		int k=0;
		for(CelldesignerCompartmentAlias cmp: cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray()){
			x[k] = Float.parseFloat(cmp.getCelldesignerBounds().getX());
			y[k] = Float.parseFloat(cmp.getCelldesignerBounds().getY());
			w[k] = Float.parseFloat(cmp.getCelldesignerBounds().getW());
			h[k] = Float.parseFloat(cmp.getCelldesignerBounds().getH());
			k++;
		}	
		
		
		CelldesignerCompartmentAlias al = null;
		
		boolean overlap = true;
		float xf = 0f;
		float yf = 0f;
		while(overlap==true){
			
		float sizeX = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		float sizeY = Float.parseFloat(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());		
			
		for(yf=10;yf<sizeY-2*sparseGap;yf+=gridStep){
			for(xf=10;xf<sizeX-2*sparseGap;xf+=gridStep){
				overlap = false;
				for(int i=0;i<x.length;i++){
					if((Math.abs(x[i]-xf)<w[i]+sparseGap/10)&&(Math.abs(y[i]-yf)<h[i]+sparseGap/10)){
						overlap = true;
						break;
					}
					if(((x[i]+compsize)>sizeX)||((y[i]+compsize)>sizeY)){
						overlap = true;
						break;
					}
				}
				if(!overlap){
					al = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().addNewCelldesignerCompartmentAlias();					
					break;
				}
			}
			if(al!=null)
				break;
		}
		if(al==null){
			cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+(int)(sizeX+compsize));
		}
		}
		
		al.setId(comp+"_alias"); al.setCompartment(comp);
		CelldesignerBounds bounds = al.addNewCelldesignerBounds();
		bounds.setW(""+compsize); bounds.setH(""+compsize); bounds.setX(""+xf); bounds.setY(""+yf);
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue("SQUARE");
		al.addNewCelldesignerClass().set(xs);
	}
	

	
	

}
