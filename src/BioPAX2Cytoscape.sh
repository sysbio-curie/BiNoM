#!/bin/bash
java -cp ./classes:../pathways-lib/jena.jar:../pathways-lib/cd4.jar:../pathways-lib/xbean.jar:../pathways-lib/xgmml.jar:../pathways-lib/jastor-1.0.3.jar:../pathways-lib/VDAOEngine.jar:../pathways-lib/xercesImpl.jar:../pathways-lib/giny.jar:../pathways-lib/cytoscape270.jar fr.curie.BiNoM.pathways.converters.BioPAX2Cytoscape $@
