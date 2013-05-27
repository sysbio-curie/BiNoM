/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
 */

package fr.curie.BiNoM.pathways.wrappers;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
//import com.hp.hpl.jena.util.*;
//import com.hp.hpl.jena.shared.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;


@SuppressWarnings("unchecked") 
/**
 * BioPAX wrapper with some utilities implemented
 * 
 * @author Andrei Zinovyev
 * @author ebonnet
 * 
 * N.B: The Jastor generated classes for biopax3, for some mysterious reason, 
 * fail to give access to properties for some biopax3 objects, such as displayName.
 * 
 * The kludge here is to replace displayName by name in the biopax3 big string,
 * right after loading the file in memory.
 * 
 * See loadBioPAX and loadBioPAXFromString.
 */
public class BioPAX {


	public static String reactionNodeTypes[] = {
		"interaction",
		"physicalInteraction",
		"control",
		"catalysis",
		"modulation",
		"conversion",
		"transport",
		"biochemicalReaction",
		"transportWithBiochemicalReaction",
		"complexAssembly"
	};

	public static String reactionEdgeTypes[] = {
		"ACTIVATION",
		"CATALYSIS_UNKNOWN",
		"CATALYSIS_ACTIVATION",
		"CATALYSIS_INHIBITION",
		"MODULATION_UNKNOWN",
		"MODULATION_ACTIVATION",
		"MODULATION_INHIBITION",
		"CONTROL_UNKNOWN",
		"CONTROL_ACTIVATION",
		"CONTROL_INHIBITION",
		"INHIBITION"
	};

	/*public static String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
  public static String biopaxFileString = "http://www.biopax.org/release/biopax-level2.owl";
  public static String namespaceString = "http://bioinfo.curie.fr#";
  public static String namespaceFileString = "http://bioinfo.curie.fr";*/

	/**
	 * Standard BioPAX v.3 prefix
	 */
	public static String biopaxString = "http://www.biopax.org/release/biopax-level3.owl#";
	/**
	 * Standard BioPAX v.3 prefix
	 */
	public static String biopaxFileString = "http://www.biopax.org/release/biopax-level3.owl";
	/**
	 * Standard BioPAX v.3 prefix
	 */
	public static String namespaceString = "http://www.biopax.org/release/biopax-level3.owl#";
	/**
	 * Standard BioPAX v.3 prefix
	 */
	public static String namespaceFileString = "http://www.biopax.org/release/biopax-level3.owl#";
	/**
	 * Standard BioPAX v.3 prefix
	 */
	public static String importString = "http://www.biopax.org/Downloads/Level3v1.0/biopax-level3.owl";
	
	public static String baseString  = "http://sysbio.curie.fr";

	/**
	 * not yet used
	 */
	//public static String modelnamespaceString = "http://www.ihes.fr/~zinovyev/biopax/biopaxmodel.owl#";
	/**
	 * not yet used
	 */
	//public static String modelnamespaceFileString = "http://www.ihes.fr/~zinovyev/biopax/biopaxmodel.owl";

	/**
	 * Some identifier for this BioPAX
	 */
	public static String idName = "";

	/**
	 * BioPAX OWL as com.hp.hpl.jena.ontology.OntModel 
	 */
	public OntModel biopaxmodel = null;
	/**
	 * BioPAX OWL as com.hp.hpl.jena.rdf.Model
	 */
	public Model model = null;
	/**
	 * com.hp.hpl.jena.ontology.Ontology object 
	 */
	public Ontology ontology = null;


	// Extra things

	/**
	 * Map from cellular location names to openControlledVocabulary terms 
	 */
	HashMap<String, CellularLocationVocabulary> compartments = new HashMap();
	HashMap<String, ControlledVocabulary> terms = new HashMap();


	public BioPAX(){
		//this(biopaxString,namespaceString,modelnamespaceString);
		this(biopaxString,namespaceString,null);
	}

	/**
	 * Non-standard constructor with some prefixes
	 * @param _biopaxString
	 * @param _namespaceString
	 * @param _modelnamespaceString
	 */
	public BioPAX(String _biopaxString, String _namespaceString, String _modelnamespaceString){
		biopaxString = _biopaxString;
		namespaceString = _namespaceString;
		//modelnamespaceString = _modelnamespaceString;
		namespaceFileString = _namespaceString.substring(0,_namespaceString.length()-1);
		//modelnamespaceFileString = _modelnamespaceString.substring(0,_modelnamespaceString.length()-1);
		//biopaxmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
		biopaxmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF,null);
		biopaxmodel.setNsPrefix("bp",biopaxString);
		biopaxmodel.setNsPrefix("xsd","http://www.w3.org/2001/XMLSchema#");
		biopaxmodel.setNsPrefix("",baseString+"#");
		ontology = biopaxmodel.createOntology("");
		Resource imprt = biopaxmodel.getResource(biopaxFileString);
		ontology.setImport(imprt);

		//imprt = biopaxmodel.getResource("http://www.biopax.org/Downloads/Level3v1.0/biopax-level3.owl");
		//imprt = biopaxmodel.getResource("http://www.biopax.org/release/biopax-level3.owl");
		//ontology.addImport(imprt);
		//imprt = null;

		//if(addBiopaxModelOntology){
			//biopaxmodel.setNsPrefix("bm",modelnamespaceString);
			//imprt = biopaxmodel.getResource(modelnamespaceFileString);
			//ontology.addImport(imprt);
		//}
	}
	/**
	 * Save an OntModel object to file
	 * @param fileName
	 * @param model
	 */
	public static void saveToFile(String fileName, OntModel model){
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(fileName,false));
			//RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
			RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
			//writer.setProperty("xmlbase",namespaceFileString) ;
			writer.setProperty("xmlbase",baseString) ;
			// Save the OWL model
			writer.write(model,out,namespaceString);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		out.close();
	}
	
	/**
	 * Save Model object to file
	 * @param fileName
	 * @param model
	 */
	public static void saveToFile(String fileName, Model model){
		// Declare the filename
		//String fileName = "testpathway.owl";
		// create an outputstream for the file to be saved
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(fileName,false));
			RDFWriter writer = model.getWriter(); //getWriter("RDF/XML-ABBREV") ;
			writer.setProperty("xmlbase",namespaceFileString) ;
			// Save the OWL model
			writer.write(model,out,namespaceString);
		}
		catch (Exception ex) {
			//JOptionPane.showMessageDialog(null,"Could not create and/or
			//save the file: "+fileName);
			ex.printStackTrace();
		}
		out.close();
	}

	/**
	 * Prints the content of the BioPAX Model object as readable text 
	 */
	public static void printDump(Model model){
		try{
			System.out.println("Proteins:");
			List l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(model);
			Iterator it = l.iterator();
			while(it.hasNext()){
				Protein p = (Protein)it.next();
				System.out.println(p.uri()+" - "+p.getName());
			}
			System.out.println("Complexes:");
			l = biopax_DASH_level3_DOT_owlFactory.getAllComplex(model);
			it = l.iterator();
			while(it.hasNext()){
				Complex p = (Complex)it.next();
				System.out.println(p.uri()+" - "+p.getName());
			}
			System.out.println("Small molecules:");
			l = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(model);
			it = l.iterator();
			while(it.hasNext()){
				SmallMolecule p = (SmallMolecule)it.next();
				System.out.println(p.uri()+" - "+p.getName());
			}
			System.out.println("Species:");
			l = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(model);
			it = l.iterator();
			while(it.hasNext()){
				PhysicalEntity p = (PhysicalEntity)it.next();
				String comm = "";
				if(p.getComment().hasNext()) {
					comm = (String)p.getComment().next();
					System.out.println(comm);
				}


				// this does not exist anymore
				/*if(p.getPHYSICAL_DASH_ENTITY()!=null)
        			System.out.println(p.uri()+" - "+p.getPHYSICAL_DASH_ENTITY().getNAME()+" comp:"+(String)p.getCELLULAR_DASH_LOCATION().getTERM().next()+" "+comm);
      			else
        			System.out.println(p.uri()+" - comp:"+(String)p.getCELLULAR_DASH_LOCATION().getTERM().next()+" "+comm);
				 */
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Adds to OWL model an openControlledVocabulary term 
	 *   
	 * @param id is the GO id
	 * @param name is the standard name for this id
	 * @return
	 * @throws Exception
	 */

	public ControlledVocabulary addGOTerm(String id, String name) throws Exception{
		String standardname = "";
		StringTokenizer st = new StringTokenizer(name," ,;");
		standardname = st.nextToken();
		UnificationXref xref = biopax_DASH_level3_DOT_owlFactory.createUnificationXref(namespaceString+"GO_"+id,biopaxmodel);
		xref.setDb("GO");
		xref.setId("GO:"+id);
		ControlledVocabulary voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(namespaceString+standardname,biopaxmodel);
		voc.addXref(xref);
		voc.addTerm(standardname);
		terms.put(standardname,voc);
		while(st.hasMoreTokens())
			terms.put(st.nextToken(),voc);
		return voc;
	}


	/**
	 * Adds to OWL model an openControlledVocabulary term
	 * @param id is the GO id
	 * @param name is the standard name for this id
	 * @throws Exception
	 */
	private void addGOCompartment(String id, String name) throws Exception{
		String standardname = "";
		StringTokenizer st = new StringTokenizer(name," ,;");
		standardname = st.nextToken();
		UnificationXref xref = biopax_DASH_level3_DOT_owlFactory.createUnificationXref(namespaceString+"GO_"+id,biopaxmodel);
		xref.setDb("GO");
		xref.setId("GO:"+id);
		CellularLocationVocabulary voc = biopax_DASH_level3_DOT_owlFactory.createCellularLocationVocabulary(namespaceString+standardname,biopaxmodel);
		voc.addXref(xref);
		voc.addTerm(standardname);
		compartments.put(standardname,voc);
		while(st.hasMoreTokens())
			compartments.put(st.nextToken(),voc);
	}


	/**
	 * Adds some standard compartments
	 *
	 */
	public void makeCompartments(){
		try{
			compartments.clear();
			addGOCompartment("0001669","acrosome");
			addGOCompartment("0015629","actin_cytoskeleton");
			addGOCompartment("0005912","adherens_junction");
			addGOCompartment("0016323","basolateral_plasma_membrane");
			addGOCompartment("0030054","cell_junction");
			addGOCompartment("0009986","cell_surface");
			addGOCompartment("0005698","centromere");
			addGOCompartment("0005813","centrosome");
			addGOCompartment("0005694","chromosome");
			addGOCompartment("0005881","cytoplasmic_microtubule");
			addGOCompartment("0005856","cytoskeleton");
			addGOCompartment("0030057","desmosome");
			addGOCompartment("0009897","external_side_of_plasma_membrane");
			addGOCompartment("0005925","focal_adhesion");
			addGOCompartment("0005794","Golgi_apparatus");
			addGOCompartment("0000139","Golgi_membrane");
			addGOCompartment("0030426","growth_cones;growth_cone");
			addGOCompartment("0005834","heterotrimeric_G_protein_complex");
			addGOCompartment("0030027","lamellipodium");
			addGOCompartment("0045121","lipid_raft");
			addGOCompartment("0016020","membrane");
			addGOCompartment("0017102","methionyl_glutamyl_tRNA_synthetase_complex");
			addGOCompartment("0005792","microsome");
			addGOCompartment("0005815","microtubule_organizing_center");
			addGOCompartment("0030496","midbody");
			addGOCompartment("0005758","mitochondrial_intermembrane_space");
			addGOCompartment("0031966","mitochondrial_membrane");
			addGOCompartment("0005741","mitochondrial_outer_membrane");
			addGOCompartment("0016604","nuclear_body");
			addGOCompartment("0031965","nuclear_membrane");
			addGOCompartment("0005640","nuclear_outer_membrane");
			addGOCompartment("0048471","perinuclear_region;perinuclear_region_of_cytoplasm");
			addGOCompartment("0005641","perinuclear_space;_nuclear_envelope_lumen");
			addGOCompartment("0016605","PML_body");
			addGOCompartment("0042734","presynaptic_membrane");
			addGOCompartment("0005791","rough_endoplasmic_reticulum");
			addGOCompartment("0001726","ruffles");
			addGOCompartment("0005803","secretory_vesicle");
			addGOCompartment("0005790","smooth_endoplasmic_reticulum");
			addGOCompartment("0005876","spindle_microtubule");
			addGOCompartment("0051233","spindle_midzone");
			addGOCompartment("0000922","spindle_pole");
			addGOCompartment("0005819","spindle");
			addGOCompartment("0001725","stress_fiber");
			addGOCompartment("0008021","synaptic_vesicle");
			addGOCompartment("0000795","synaptonemal_complex");
			addGOCompartment("0019717","synaptosome");
			addGOCompartment("0005923","tight_junction");
			addGOCompartment("0005633","ascus_lipid_droplet");
			addGOCompartment("0005737","cytoplasm");
			addGOCompartment("0005829","cytosol");
			addGOCompartment("0005769","early_endosome");
			addGOCompartment("0032009","early_phagosome");
			addGOCompartment("0005788","endoplasmic_reticulum_lumen;endoplasmic_reticulum");
			addGOCompartment("0005783","endoplasmic_reticulum;ER");
			addGOCompartment("0005768","endosome");
			addGOCompartment("0005576","extracellular_region;extracellular;extracellular_space");
			addGOCompartment("0031012","extracellular_matrix");
			addGOCompartment("0005796","Golgi_lumen;Golgi");
			addGOCompartment("0005770","late_endosome;lysosome_targeted_late_endosomes");
			addGOCompartment("0005764","lysosome");
			addGOCompartment("0030526","macrophage");
			addGOCompartment("0005739","mitochondrion;mitochondria");
			addGOCompartment("0005654","nucleoplasm");
			addGOCompartment("0005634","nucleus;gene_compartment");
			addGOCompartment("0005730","nucleolus;nucleole");
			addGOCompartment("0043226","organelle");
			addGOCompartment("0005886","plasma_membrane");
			addGOCompartment("0005764","recycling_endosome");
			addGOCompartment("0031982","vesicle");
			addGOCompartment("0000502","proteasome");			
			addGOCompartment("0008372","cellular_component_unknown;unknown;default");

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 *  
	 * @param name
	 * @return openControlledVocabulary term named name
	 */
	public CellularLocationVocabulary getCompartment(String name){
		CellularLocationVocabulary ov = null;
		if(name==null) name = "unknown";
		if(name.equals("")) name = "unknown";
		name = name.toLowerCase();
		ov = compartments.get(name);
		if(ov==null)
			ov = compartments.get("unknown");
		return ov;
	}

	/**
	 * Loads BioPAX content from file fn
	 * @param fn
	 * @throws Exception
	 */
	public void loadBioPAX(String fn) throws Exception{
		String bps = Utils.loadString(fn);
		loadBioPAXFromString(bps);
	}

	/**
	 * Loads BioPAX content from InputStream is
	 * @param input stream
	 * @throws Exception
	 */
	public void loadBioPAX(InputStream is) throws Exception{
		
		model = ModelFactory.createDefaultModel();
		
		System.out.print("Load in memory... ");
		//String bps = Utils.loadString(is);
		
		//System.out.print("replacing biopax level... ");
		//bps = Utils.replaceString(bps,"biopax-level1","biopax-level2");
		
		//System.out.print("replacing name capitals... ");
		//bps = Utils.replaceString(bps,"bp:Name","bp:NAME");
		//bps = Utils.replaceString(bps,"bp:displayName","bp:name");
		//bps = Utils.replaceString(bps,"bp:standardName","bp:name");
		
		String bps = formatBioPAX3String(Utils.loadString(is));
		
		System.out.println("reading the string... ");
		Boolean is_level3 = checkBiopaxLevel3(bps);
		if (is_level3)
			try{
				model.read(new StringReader(bps),"");
			}catch(Exception e){
				System.setErr(System.out);
				e.printStackTrace();
				throw e;
			}

	}

	/**
	 * Loads BioPAX content from String
	 * @param text
	 * @throws Exception
	 */
	public void loadBioPAXFromString(String text) throws Exception{
		model = ModelFactory.createDefaultModel();
		//System.out.print("replacing biopax level... ");
		//String bps = Utils.replaceString(text,"biopax-level1","biopax-level2");
		//System.out.print("replacing name capitals... ");
		//text = Utils.replaceString(bps,"bp:Name","bp:NAME");
		//System.setErr(System.err);
		
		//String bps = Utils.replaceString(text,"bp:displayName","bp:name");
		//bps = Utils.replaceString(bps,"bp:standardName","bp:name");
		String bps = formatBioPAX3String(text);

		System.out.println("reading the string... ");
		
		Boolean is_level3 = checkBiopaxLevel3(bps);
		if (is_level3)
			try{
				model.read(new StringReader(bps),"");
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
	}
	
	/**
	 * Check if the file is a BioPAX level3 file.
	 * 
	 * @param str BioPAX string
	 * @return boolean true or false
	 * 
	 * @author ebonnet
	 */
	private boolean checkBiopaxLevel3 (String str) {
		boolean ret = true;
		String level3_string = "http://www.biopax.org/release/biopax-level3.owl";
		Pattern r = Pattern.compile(level3_string);
		Matcher m = r.matcher(str);
		if (!m.find()){
			JOptionPane.showMessageDialog(null,"The BioPAX file must be a level 3 file.","BioPAX level warning",JOptionPane.WARNING_MESSAGE);
			ret = false;
		}
		return(ret);
	}
	
	/**
	 * Replace elements in the BioPAX3 string.
	 * 
	 * @param str BioPAX3 string
	 * @return formatted string
	 */
	private String formatBioPAX3String(String str) {
		String ret = "";
		Matcher m = Pattern.compile("bp:displayName").matcher(str);
		int count = 0;
		while(m.find())
			count++;
		System.out.println(count+" occurences of bp:displayName replaced.");
		ret = m.replaceAll("bp:name");
		return(ret);
	}
}