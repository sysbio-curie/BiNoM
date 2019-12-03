#!/bin/bash
java -cp ./classes:../lib/cd4.jar:../lib/binomlibext.jar:../lib/xgmml.jar:../lib/VDAOEngine.jar:../lib/psi25-xml-1.8.3.jar:../lib/paxtools-4.2.1-no-jena.jar fr.curie.BiNoM.pathways.test.TestPaxtoolsConverter $@
