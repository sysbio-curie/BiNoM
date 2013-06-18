package fr.curie.BiNoM.pathways.utils;

import java.util.HashMap;

import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.impl.xb.xsdschema.AnnotationDocument;
import org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation;
import org.sbml.x2001.ns.celldesigner.CelldesignerActivityDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoundsDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoxSizeDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBriefViewDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerClassDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerInnerPositionDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerPaintDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinReferenceDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerPaintDocument.CelldesignerPaint;
import org.sbml.x2001.ns.celldesigner.CelldesignerSingleLineDocument.CelldesignerSingleLine;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerUsualViewDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerViewDocument.CelldesignerView;
import org.sbml.x2001.ns.celldesigner.CompartmentDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;


import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class CellDesignerGenerator {

	/**
	 * @param args
	 */
	
	SbmlDocument cd = null;
	HashMap<String,CelldesignerProteinDocument.CelldesignerProtein> proteins = new HashMap<String,CelldesignerProteinDocument.CelldesignerProtein>();
	float defaultWidth = 80;
	float defaultHeight = 40;
	String usualViewColor = "ffccffcc";
	String briefViewColor = "3fff0000";
	String usualViewColor_forComplex = "fff7f7f7";
	String briefViewColor_forComplex = "fff7f7f7";
	
	
	HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
	HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies> celldesigner_species = new HashMap<String,CelldesignerSpeciesDocument.CelldesignerSpecies>();	
	HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> speciesAliases = new HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();		
	HashMap<String,CompartmentDocument.Compartment> compartments = new HashMap<String,CompartmentDocument.Compartment>();
	
	
	
	public static void main(String[] args) {
		try{
		CellDesignerGenerator cg = new CellDesignerGenerator();
		cg.createNewCellDesignerFile("Benchmark1",1000,1500);
		
		cg.createSpeciesFromString("s1", "A|Thr239_pho", "A@nucleus", 100, 100);
		cg.createSpeciesFromString("s2", "B:C", "B:C@nucleus", 200, 100);
		cg.createSpeciesFromString("s3", "B:C:D", "B:C:D@nucleus", 400, 100);
		cg.createSpeciesFromString("s4", "A:B:C:D", "A:B:C:D@nucleus", 200, 600);
		cg.createSpeciesFromString("s5", "A:B:C:D:E:F:G:T:S", "A:B:C:D:E:F:G:T:S@nucleus", 700, 400);

		
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
	
	
	public SpeciesDocument.Species createSpeciesFromString(String id, String name, String structuredString, int x, int y){
		SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().addNewSpecies();
		sp.setId(id);
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);		
		sp.setName(xs);
		species.put(id, sp);
		
		SpeciesStructure ss = SpeciesStructure.decodeStructuredString(structuredString);
		createNewCompartment(ss.compartment, ss.compartment);
		
		if(ss.components.size()>1){
			// Create complex
			for(int i=0;i<ss.components.size();i++){
				SpeciesStructure.SpeciesStructureComponent ssm = ss.components.get(i);
				String proteinId = "pr_"+ssm.name;
				createNewProtein(proteinId, ssm.name);
				String celldesignerSpeciesId = Utils.correctName(ssm.toString());
				String complexId = Utils.correctName(ss.toString());				
				createCelldesignerSpecies(celldesignerSpeciesId, id, ssm.name, proteinId, "PROTEIN");

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
				
				float dx = defaultWidth/8f;
				float dy = defaultHeight/4f;
				
				int col = (int)(1f*i/h1);
				int row = i - col*w1;
				dx+=defaultWidth*col;
				dy+=defaultHeight*row;
				
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = createSpeciesAlias(celldesignerSpeciesId+"_in_"+id+"_alias",celldesignerSpeciesId,x+dx,y+dy);
				csa.setComplexSpeciesAlias(id);
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
			createNewProtein("pr_"+ssm.name, ssm.name);
			Annotation annot = sp.addNewAnnotation();
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = annot.addNewCelldesignerSpeciesIdentity();
			CelldesignerClassDocument.CelldesignerClass cc = spi.addNewCelldesignerClass();
			xs = XmlString.Factory.newInstance(); xs.setStringValue("PROTEIN");
			cc.set(xs);
			CelldesignerProteinReferenceDocument.CelldesignerProteinReference pr = spi.addNewCelldesignerProteinReference();
			xs = XmlString.Factory.newInstance(); xs.setStringValue("pr_"+ssm.name);			
			pr.set(xs);
			
			sp.setCompartment(ss.compartment);
			
			createSpeciesAlias(id+"_alias",id,x,y);
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
	
	public CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias createComplexSpeciesAlias(String id, String sp_id, SpeciesStructure ss, int x, int y){
	
	CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias();
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
	w = defaultWidth*w1+defaultWidth/4f;
	h = defaultHeight*h1+defaultHeight;
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
	
	
	
	return csa;
	}
	
	
	public CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias createSpeciesAlias(String id, String sp_id, float x, float y){
		CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias alias = speciesAliases.get(id); 
		if(alias==null){
		alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias();
		alias.setId(id);
		alias.setSpecies(sp_id);
			CelldesignerActivityDocument.CelldesignerActivity aa = alias.addNewCelldesignerActivity();
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue("inactive");		
			aa.set(xs);
			CelldesignerBoundsDocument.CelldesignerBounds ab = alias.addNewCelldesignerBounds();
			ab.setX(""+x); ab.setY(""+y);
			ab.setH(""+defaultHeight); ab.setW(""+defaultWidth);
		alias.addNewCelldesignerView().setState(CelldesignerView.State.USUAL);
			CelldesignerUsualViewDocument.CelldesignerUsualView uv = alias.addNewCelldesignerUsualView();
				CelldesignerBoxSizeDocument.CelldesignerBoxSize bs = uv.addNewCelldesignerBoxSize();
				bs.setWidth(""+this.defaultWidth); bs.setHeight(""+this.defaultHeight);
				CelldesignerPaintDocument.CelldesignerPaint p = uv.addNewCelldesignerPaint();
				p.setScheme(CelldesignerPaint.Scheme.COLOR);
				xs = XmlString.Factory.newInstance(); xs.setStringValue(this.usualViewColor);
				p.setColor(xs);
				CelldesignerInnerPositionDocument.CelldesignerInnerPosition ip = uv.addNewCelldesignerInnerPosition();
				ip.setX("0"); ip.setY("0");
				uv.addNewCelldesignerSingleLine().setWidth("1");
			CelldesignerBriefViewDocument.CelldesignerBriefView bv = alias.addNewCelldesignerBriefView();
				bs = bv.addNewCelldesignerBoxSize();
				bs.setWidth(""+this.defaultWidth); bs.setHeight(""+this.defaultHeight);
				ip = bv.addNewCelldesignerInnerPosition();
				ip.setX("0"); ip.setY("0");
				bv.addNewCelldesignerSingleLine().setWidth("1");
				p = bv.addNewCelldesignerPaint();
				p.setScheme(CelldesignerPaint.Scheme.COLOR);
				xs = XmlString.Factory.newInstance(); xs.setStringValue(this.usualViewColor);
				p.setColor(xs);
				
				speciesAliases.put(id, alias);
		}
		return alias;
	}
	
	public CelldesignerProteinDocument.CelldesignerProtein createNewProtein(String id, String name){
		CelldesignerProteinDocument.CelldesignerProtein protein = proteins.get(id);
		if(protein==null){
			protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein();
			protein.setId(id);
			XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);
			protein.setName(xs);
			proteins.put(id, protein);
		}
		return protein;
	}
	
	public void createCelldesignerSpecies(String id, String complex_id, String name, String entity_id, String classname){
		if(celldesigner_species.get(id)==null){
		CelldesignerSpeciesDocument.CelldesignerSpecies cs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies();
		cs.setId(id);
		XmlString xs = XmlString.Factory.newInstance(); xs.setStringValue(name);				
		cs.setName(xs);
		cs.addNewCelldesignerAnnotation();
		xs = XmlString.Factory.newInstance(); xs.setStringValue(complex_id);
		cs.getCelldesignerAnnotation().addNewCelldesignerComplexSpecies().set(xs);
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity csi = cs.getCelldesignerAnnotation().addNewCelldesignerSpeciesIdentity();
		xs = XmlString.Factory.newInstance(); xs.setStringValue(classname);		
		csi.addNewCelldesignerClass().set(xs);
		if(classname.equals("PROTEIN")){
			xs = XmlString.Factory.newInstance(); xs.setStringValue(entity_id);		
			csi.addNewCelldesignerProteinReference().set(xs);
		}
		celldesigner_species.put(id, cs);
		}
	}
	

}
