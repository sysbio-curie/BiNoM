
JARDIR = ../lib

CLASSPATH = .:../lib/cd4.jar:../lib/binomlibext.jar:../lib/xgmml.jar:../lib/VDAOEngine.jar:../lib/psi25-xml-1.8.3.jar:../lib/paxtools-4.2.1-no-jena.jar
JAVAC = javac -encoding ISO-8859-1 -d classes -classpath $(CLASSPATH)

plugin:
	sh COMPILE.sh

bigjar:
	(cd ../lib; sh ../scripts/mergejar.sh `cat ../scripts/jarlist.txt`)

all_lib: BioPAXToCytoscapeConverter CellDesignerToCytoscapeConverter # pathways.jar

all_prog: BioPAX2Cytoscape BioPAX2SBML CellDesigner2BioPAX CellDesigner2Cytoscape Cytoscape2BioPAX

BioPAXToCytoscapeConverter:
	$(JAVAC) fr/curie/BiNoM/pathways/$@.java

CellDesignerToCytoscapeConverter:
	$(JAVAC) fr/curie/BiNoM/pathways/$@.java

BioPAX2Cytoscape:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

CellDesigner2Cytoscape:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

BioPAX2SBML:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

CellDesigner2BioPAX:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

CellDesigner2BioPAXPaxTools:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

Cytoscape2BioPAX:
	$(JAVAC) fr/curie/BiNoM/pathways/converters/$@.java

TestPaxtoolsConverter:
	$(JAVAC) fr/curie/BiNoM/pathways/test/TestPaxtoolsConverter.java

pathways.jar:
	(cd classes; jar cvf ../lib/$@ fr)

javadoc:
	javadoc -d ../doc/javadoc \
               fr.curie.BiNoM.pathways.test \
               fr.curie.BiNoM.pathways.utils \
               fr.curie.BiNoM.pathways.biopax \
               fr.curie.BiNoM.pathways.wrappers \
               fr.curie.BiNoM.pathways.analysis \
               fr.curie.BiNoM.pathways.analysis.structure \
               fr.curie.BiNoM.pathways.converters \
               fr.curie.BiNoM.cytoscape.celldesigner \
               fr.curie.BiNoM.cytoscape.utils \
               fr.curie.BiNoM.cytoscape.biopax \
               fr.curie.BiNoM.cytoscape.biopax.propedit \
               fr.curie.BiNoM.cytoscape.biopax.query \
               fr.curie.BiNoM.cytoscape.analysis \
               fr.curie.BiNoM.cytoscape.lib \
               fr.curie.BiNoM.cytoscape.netwop \
               fr.curie.BiNoM.cytoscape.sbml \
               fr.curie.BiNoM.cytoscape.plugin

clean:
	(cd classes; find . -name \*.class -print | xargs rm -f)
