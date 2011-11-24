package fr.curie.BiNoM.pathways.test;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import java.io.*;
import java.util.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;;

public class testBioPAX1 {
  public static void main(String[] args) {

    try{

    String namespaceString = "http://www.biopax.org/release/biopax-level2.owl#";
    String importString = "http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl";
    String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
    
    
    //Model source1 = BioPAXUtilities.loadModel("c:/datas/binomtest/path.owl",namespaceString,importString);
    //NodeIterator iter = source1.listObjects();
    /*BioPAX biopax = new BioPAX();
    biopax.loadBioPAX("c:/datas/binomtest/m-phase2.owl");
    ResIterator iter = biopax.model.listSubjects();
    while(iter.hasNext()){
    	Resource res = iter.nextResource();
    	com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(res.getURI(), biopax.model);
    	System.out.println(res.getURI()+"\t"+thing.getClass().getName());    	
    }*/
	System.exit(0);

    Vector uris = new Vector();
    //uris.add("XN000001969");
    //uris.add("XN000023971");
    //uris.add("XN000023966");
    //uris.add("XN000023949");
    //uris.add("XN000023986");
    //uris.add("XN000004163");
    //uris.add("XN000023969");
    //uris.add("XN000024118");
    //uris.add("XN000024135");
    //uris.add("XN000023942");
    //uris.add("XN000023943");
    //uris.add("XN000023944");
    //uris.add("XN000023950");
    //uris.add("XN000023983");



    //uris.add("XN000068563");
    //uris.add("XN000001232");
    //uris.add("XN000000705");

    //Model source = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/reaction_semantic.owl",namespaceString,importString);
    //Model res = BioPAXUtilities.extractURIwithAllLinks(source,uris,namespaceString,importString);
    //BioPAXUtilities.saveModel(res,"c:/datas/biobase/biopax/G1.owl",biopaxString);

    //System.exit(0);
    
    Model source = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/reaction_pathway_updated.owl",namespaceString,importString);
    Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/biobase/testRb/rb_final.xgmml"));
    Vector v = new Vector(); v.add("XN000024100");
    Model res = BioPAXUtilities.extractFromModel(source, v, namespaceString,importString);
    //Model res = BioPAXUtilities.extractFromModel(source, gr, namespaceString,importString);
    BioPAXUtilities.saveModel(res, "c:/datas/biobase/testRB/rb_final.owl", biopaxString);
    System.exit(0);
    
    

    String prefix = "c:/datas/biobase/biopax/reaction_pathway";
    //String prefix = "c:/datas/biobase/biopax/G1";
    System.out.println("Loading resources...");
    //Model main = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/G1.owl",namespaceString,importString);
    Model main = BioPAXUtilities.loadModel(prefix+".owl",namespaceString,importString);
    System.out.println("Molecule...");
    Model molecules = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/molecule.owl",namespaceString,importString);
    System.out.println("Gene...");
    Model genes = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/gene.owl",namespaceString,importString);
    System.out.println("Reference...");
    Model publications = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/reference.owl",namespaceString,importString);
    System.out.println("Loaded...");
    Utils.printUsedMemory();

    BioPAXMerge bm = new BioPAXMerge();
    bm.mainfile = main;
    bm.referenceFiles.add(genes);
    bm.referenceFiles.add(molecules);
    bm.referenceFiles.add(publications);

    bm.updateMainfileWithReferences();
    //System.out.println("Final version :");
    //bm.printStatements();
    BioPAXUtilities.saveModel(bm.mainfile,prefix+"_updated.owl",biopaxString);

    System.exit(0);



    /*String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
    String biopaxFileString = "http://www.biopax.org/release/biopax-level2.owl";
    String namespaceString = "http://www.biopax.org/release/biopax-level2.owl#";
    String namespaceFileString = "http://www.biopax.org/release/biopax-level2.owl#";
    String importString = "http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl";


    Model source = BioPAXUtilities.loadModel("notch.owl",namespaceString,importString);
    //Model res = ModelFactory.createDefaultModel();
    OntModel res = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
    res.setNsPrefix("",namespaceString);
    Ontology _ontology = res.createOntology("");
    Resource _imprt = res.getResource(importString);
    _ontology.setImport(_imprt);

    BioPAXUtilities.copyURIwithAllLinks(biopaxString,"Notch_1_ligand_bound_fragment_Notch_ligand_complex__plasma_membrane_1",source,res);
    BioPAXUtilities.saveModel(res,"example.owl",biopaxString);


    System.exit(0);*/


    Model model_in = ModelFactory.createDefaultModel();
    model_in.read(new FileInputStream("Notch.owl"),"");

    OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
    model.setNsPrefix("",namespaceString);
    Ontology ontology = model.createOntology("");
    Resource imprt = model.getResource("http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl");
    ontology.setImport(imprt);

    com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(biopaxString+"Notch_1_ligand_bound_fragment_Notch_ligand_complex__plasma_membrane_1",model_in);
    //model.add(thing.listStatements());
    //copyResource(model_in,model,thing);
    copyURIwithAllLinks(biopaxString,"Notch_1_ligand_bound_fragment_Notch_ligand_complex__plasma_membrane_1",model_in,model);
    //copyURIwithAllLinks(biopaxString,"Notch_receptor_binds_with_a_ligandStep",model_in,model);
    copyURIwithAllLinks(biopaxString,"metallopeptidase_activity_of_ADAM_10_metalloprotease__Zn_cofactor___plasma_membrane_",model_in,model);


    String fileName = "example.owl";
    PrintWriter out = new PrintWriter(new FileOutputStream(fileName,false));
    RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
    writer.setProperty("xmlbase",biopaxString) ;
    writer.write(model,out,biopaxString);



    // loading BioPAX
    /*BioPAX bp = new BioPAX();
    Date tm = new Date();
    bp.loadBioPAX("c:/datas/biopax/reactomerel19/apoptosis.owl");
    System.out.println("Time spent "+((new Date()).getTime()-tm.getTime()));
    Utils.printUsedMemory();

    // take the first protein
    List el = biopax_DASH_level2_DOT_owlFactory.getAllprotein(bp.model);
    protein p = (protein)el.get(0);

    // example of using naming service
    BioPAXNamingService bns = new BioPAXNamingService();
    bns.generateNames(bp,false);
    System.out.println("protein URI = "+p.uri());
    System.out.println("protein name = "+bns.getNameByUri(p.uri()));

    // Now get it by uri
    // In the application you convert BIOPAX_NODE_ID to uri by adding prefix bp.namespaceString:
    // uri = bp.namespaceString+id
    String uri = p.uri();
    com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(uri,bp.model);
    // print the content of the object, you can learn the type from .toString() or .listStatements()
    System.out.println(thing.toString());
    */


    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void copyURIwithAllLinks(String namespace, String uri, Model modelfrom, Model modelto) throws Exception{
     HashMap uris = new HashMap();
     com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(namespace+uri,modelfrom);
     com.ibm.adtech.jastor.Thing thingto = biopax_DASH_level2_DOT_owlFactory.getThing(namespace+uri,modelto);
     //if(thingto==null){
     //System.out.println(thing.uri());
     //modelto.add(thing.listStatements());
     copyResourceRecursive(namespace,modelfrom,modelto,thing,uris);
     //}
  }

  public static void copyResourceRecursive(String namespace, Model modelfrom, Model modelto, com.ibm.adtech.jastor.Thing obj, HashMap uris) throws Exception{
    List sl = obj.listStatements();
    modelto.add(sl);
    uris.put(obj.uri(),obj);
    for(int i=0;i<sl.size();i++){
      Statement st = (Statement)sl.get(i);
      RDFNode nod = st.getObject();
      //System.out.print(st.getPredicate().toString()+"\t");
      if(nod.isURIResource()){
        //System.out.println("URIResource: "+nod.toString());
        com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(nod.toString(),modelfrom);
        if(thing!=null)if(uris.get(thing.uri())==null){
           copyResourceRecursive(namespace,modelfrom,modelto,thing,uris);
        }
      }
    }

  }


}