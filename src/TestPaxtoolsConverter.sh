#!/bin/bash
java -cp ./classes:../lib/cd4.jar:../lib/binomlibext.jar:../lib/xgmml.jar:../lib/VDAOEngine.jar:../lib/psi25-xml-1.8.3.jar:../lib/paxtools-5.1.0.jar fr.curie.BiNoM.pathways.test.TestPaxtoolsConverter $@
