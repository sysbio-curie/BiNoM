#
# Makefile for CellDesigner plugin
#
# Eric Viara for Institut Curie (c) copyright 2008
#

CDHOME = /home/viara/projects/CellDesigner4.0.1
#BINOMHOME = /home/viara/projects/BiNoM/src/classes
BINOMHOME = /home/viara/projects/BiNoM/src/BiNoM.jar

JARDIR=$(CDHOME)/pathways-lib

clspath=$(JARDIR)/cd3.jar:$(JARDIR)/commons-beanutils.jar:$(JARDIR)/commons-cli.jar:$(JARDIR)/commons-dbcp-1.1.jar:$(JARDIR)/commons-digester-1.7.jar:$(JARDIR)/commons-logging.jar:$(JARDIR)/commons-pool-1.1.jar:$(JARDIR)/dom4j.jar:$(JARDIR)/jastor-1.0.3.jar:$(JARDIR)/sax.jar:$(JARDIR)/VDAOEngine.jar:$(JARDIR)/xbean.jar:$(JARDIR)/xgmml.jar:$(JARDIR)/jena/antlr.jar:$(JARDIR)/jena/icu4j_3_4.jar:$(JARDIR)/jena/icu4j.jar:$(JARDIR)/jena/iri.jar:$(JARDIR)/jena/jakarta-oro-2.0.5.jar:$(JARDIR)/jena/jena.jar:$(JARDIR)/jena/rdf-api-2001-01-19.jar:$(JARDIR)/jena/xml-apis.jar:$(JARDIR)/BioPAXToCytoscapeConverter.jar:$(JARDIR)/CellDesignerToCytoscapeConverter.jar:$(JARDIR)/jena/xercesImpl.jar

CLASSPATH = $(CDHOME)/lib/sbmlj.jar:$(CDHOME)/exec/celldesigner.jar:$(BINOMHOME):$(clspath)

JAVAC = CLASSPATH=$(CLASSPATH) $(HOME)/jdk1.5.0_16/bin/javac -d classes 

JARFILE = CellDesignerPlugin.jar

all:
	@$(JAVAC) plugin/*.java lib/*.java biopax/*.java analysis/*.java biopax/propedit/*.java ../lib/*.java
	(cd classes; jar cvf $(JARFILE) `find . -name \*.class`)

install: all
	cp classes/$(JARFILE) $(CDHOME)/plugin/

clean:
	rm -f `find . -name \*.class` $(JARFILE)
