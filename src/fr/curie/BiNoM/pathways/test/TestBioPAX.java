package fr.curie.BiNoM.pathways.test;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;


import java.io.*;

public class TestBioPAX {

  public static String biopaxString = "";
  public static String namespaceString = "";
  public static String namespaceFileString = "";

  public static void main(String[] args) {

    /*OntModel biopaxmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);

    // Declare the variables which contain the URIs of the namespaces
    biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
    namespaceString = "http://curie.fr/celldesigner.owl#";
    biopaxmodel.setNsPrefix("bp",biopaxString );
    biopaxmodel.setNsPrefix("",namespaceString);

    //String namespaceFileString = "http://somewhere/testpathway.owl";
    String namespaceFileString = "http://www.curie.fr/celldesigner.owl";
    Ontology ont = biopaxmodel.createOntology(namespaceFileString);

    String biopaxFileString = "http://www.biopax.org/release/biopax-level2.owl";
    ont.addImport(biopaxmodel.createResource(biopaxFileString));
    biopaxmodel.addLoadedImport(biopaxFileString);

    //String IndividualURI=namespaceString + IndividualName;
    //String classURI=biopaxString + classname;

    String individualName = "pathway1";
    String individualClass = "pathway";
    createBiopaxIndividual(individualName,individualClass,biopaxmodel);

    // Add the property "NAME" to the pathway individual
    String propertyName = "NAME";
    String pathwayName = "testpathway";
    addBiopaxProperty(pathwayName,propertyName,individualName,biopaxmodel);

     // Declare the string arrays with the protein data
     String[] proteinName = { "Enamelysin","Metalloelastase","Collagenase" };
     String[] proteinDB = { "SWISS-PROT","RefSeq","SWISS-PROT" };
     String[] proteinID = { "P023244", "NP_304845","P308934" };
     // Create and individual in the class protein for every protein
     for (int i = 0; i < proteinName.length; i++) {
     // Create the name of the individual
     individualName = "Protein" + i;
     individualClass = "protein";
     createBiopaxIndividual(individualName,individualClass,biopaxmodel);
     }

      // Create an individual in the class “unificationXref” for every protein
      for (int i = 0; i < proteinName.length; i++) {
      // Create the name of the individual
      individualName = "Xref" + i;
      individualClass = "unificationXref";
      createBiopaxIndividual(individualName,individualClass,biopaxmodel);
      }

       // Add the property “NAME” to every protein
       propertyName = "NAME";
       for(int i = 0; i < proteinName.length; i++) {
       // Create the name of the individual
       individualName = "Protein" + i;
       // Add the properties to the Protein individuals
       addBiopaxProperty(proteinName[i],propertyName,individualName,biopaxmodel);
       }

       // Create an individual in #unificationXref for every protein
       for (int i = 0; i < proteinName.length; i++) {
          // Add the properties to the Xref individuals
          individualName = "Protein" + i;
          propertyName = "DB";
          addBiopaxProperty(proteinDB[i],propertyName,individualName,biopaxmodel);
          propertyName = "ID";
          addBiopaxProperty(proteinID[i],propertyName,individualName,biopaxmodel);
       }

       saveToFile("testpathway.owl",biopaxmodel);*/

    OntModel biopaxmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);

    biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
    namespaceString = "http://curie.fr/celldesigner.owl#";
    biopaxmodel.setNsPrefix("bp",biopaxString );
    biopaxmodel.setNsPrefix("",namespaceString);

    //String namespaceFileString = "http://somewhere/testpathway.owl";
    String namespaceFileString = "http://www.curie.fr/celldesigner.owl";
    Ontology ont = biopaxmodel.createOntology(namespaceFileString);


// First start with some example data
String pathwayName = "testpathway";
String[] proteinName = {
"Enamelysin","Metalloelastase","Collagenase" };
String[] proteinDB = { "SWISS-PROT","RefSeq","SWISS-PROT" };
String[] proteinID = { "P023244", "NP_304845","P308934" };
String individualName;
String individualClass;
String propertyName;
String linkedresourceName;
// Create an individual in #pathway for the testpathway
individualName = "pathway1";

individualClass = "pathway";
createBiopaxIndividual(individualName,individualClass,biopaxmodel);
// Add the property "NAME" to the pathway individual
propertyName = "NAME";
addBiopaxProperty(pathwayName,propertyName,individualName,biopaxmodel);
// Create an individual in #unificationXref for every protein
for(int i = 0; i < proteinName.length; i++) {
// Create the name of the individual
individualName = "Xref" + i;
individualClass = "unificationXref";
createBiopaxIndividual(individualName,individualClass,biopaxmodel);
// Add the properties to the Xref individuals
propertyName = "DB";
addBiopaxProperty(proteinDB[i],propertyName,individualName,biopaxmodel);
propertyName = "ID";
addBiopaxProperty(proteinID[i],propertyName,individualName,biopaxmodel);
// Create and individual in #protein for every protein
}
for (int i = 0; i < proteinName.length; i++) {
// Create the name of the individual
individualName = "Protein" + i;
individualClass = "protein";
createBiopaxIndividual(individualName,individualClass,biopaxmodel);
// Add the properties to the Protein individuals
propertyName = "NAME";
addBiopaxProperty(proteinName[i],propertyName,individualName,biopaxmodel);
// Now the property XREF has to be set (linked to theunificationXref of this protein)
propertyName = "XREF";
linkedresourceName = "Xref"+i;
addBiopaxLinkedProperty(propertyName,linkedresourceName,individualName,biopaxmodel);
}

saveToFile("testpathway.owl",biopaxmodel);

  }

  public static void createBiopaxIndividual(String individualName, String individualClass, OntModel model) {
    // Get the resource of the class where the individual has to be placed
    Resource res = model.getResource(biopaxString+individualClass);
    // Add the individual to the class
    model.createIndividual(namespaceString + individualName,res);
 }

  public static void addBiopaxProperty(String propertyLiteral, String propertyName, String individualName, OntModel model) {
  // Get the individual for which the property has to be added
  Individual ind = model.getIndividual(namespaceString+individualName);
  // Get the property
  Property prop = model.getProperty(biopaxString+propertyName);
  // Add the literal to the property
  ind.addProperty(prop,propertyLiteral);
  }

  public static void addBiopaxLinkedProperty(String propertyName, String linkedresourceName, String individualName, OntModel model){
  // Get the individual for which the property has to be added
  Individual ind = model.getIndividual(namespaceString+individualName);
  // Get the property
  Property prop = model.getProperty(biopaxString+propertyName);
  // Get the resource which has to be added to the property
  Resource res = model.getResource(biopaxString+linkedresourceName);
  //Resource res = model.getResource(namespaceString+linkedresourceName);
  // Link the properties
  ind.addProperty(prop,res);
  }

  public static void saveToFile(String fileName, OntModel model){
  // Declare the filename
  //String fileName = "testpathway.owl";
  // create an outputstream for the file to be saved
  PrintWriter out = null;
  try {
  out = new PrintWriter(new FileOutputStream(fileName,false));
  RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
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

}