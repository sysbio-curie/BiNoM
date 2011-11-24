#!/bin/sh
#
# $Id$
#
# Eric Viara for Institut Curie (c) 2006

# exemple :
# sh compile-plugin.sh EVPlugin *.java

if [ $# -lt 2 ]; then
  echo "usage: $0 <plugin> <sources>"
  exit 1
fi

plugin=$1
shift
sources="$*"

CYTOSCAPE=/home/viara/projects/cytoscape-v2.6.1-bin

JARLIB=$CYTOSCAPE/lib
JARPLUGINS=$CYTOSCAPE/plugins

clspath=$CYTOSCAPE/lib/concurrent.jar:$CYTOSCAPE/lib/cytoscape-geom-spacial.jar:$CYTOSCAPE/lib/glf.jar:$CYTOSCAPE/lib/freehep-swing-2.0.3.jar:$CYTOSCAPE/lib/piccolo.jar:$CYTOSCAPE/lib/freehep-graphicsio-java-2.1.1.jar:$CYTOSCAPE/lib/jnlp.jar:$CYTOSCAPE/lib/colt.jar:$CYTOSCAPE/lib/biojava-1.4.jar:$CYTOSCAPE/lib/cytoscape-render-immed.jar:$CYTOSCAPE/lib/saaj-api.jar:$CYTOSCAPE/lib/jsr181-api.jar:$CYTOSCAPE/lib/jsr173_1.0_api.jar:$CYTOSCAPE/lib/resolver.jar:$CYTOSCAPE/lib/junit.jar:$CYTOSCAPE/lib/freehep-util-2.0.2.jar:$CYTOSCAPE/lib/cytoscape-util-intr.jar:$CYTOSCAPE/lib/cytoscape-graph-fixed.jar:$CYTOSCAPE/lib/ding.jar:$CYTOSCAPE/lib/jaxws-api.jar:$CYTOSCAPE/lib/stax-ex.jar:$CYTOSCAPE/lib/violinstrings-1.0.2.jar:$CYTOSCAPE/lib/tclib.jar:$CYTOSCAPE/lib/looks-2.1.4.jar:$CYTOSCAPE/lib/freehep-graphicsio-ps-2.1.1.jar:$CYTOSCAPE/lib/itext-2.0.4.jar:$CYTOSCAPE/lib/fing.jar:$CYTOSCAPE/lib/jaxb-api.jar:$CYTOSCAPE/lib/commons-cli-1.x-cytoscape-custom.jar:$CYTOSCAPE/lib/freehep-graphicsio-2.1.1.jar:$CYTOSCAPE/lib/FastInfoset.jar:$CYTOSCAPE/lib/cytoscape-cruft-obo.jar:$CYTOSCAPE/lib/swingx-2006_10_27.jar:$CYTOSCAPE/lib/freehep-graphics2d-2.1.1.jar:$CYTOSCAPE/lib/freehep-xml-2.1.1.jar:$CYTOSCAPE/lib/saaj-impl.jar:$CYTOSCAPE/lib/freehep-jas-plotter-2.2.jar:$CYTOSCAPE/lib/cytoscape-render-stateful.jar:$CYTOSCAPE/lib/cytoscape-render-export.jar:$CYTOSCAPE/lib/streambuffer.jar:$CYTOSCAPE/lib/cytoscape-graph-dynamic.jar:$CYTOSCAPE/lib/jhall.jar:$CYTOSCAPE/lib/jaxws-tools.jar:$CYTOSCAPE/lib/undo.support.jar:$CYTOSCAPE/lib/swing-layout-1.0.1.jar:$CYTOSCAPE/lib/i4jruntime.jar:$CYTOSCAPE/lib/activation.jar:$CYTOSCAPE/lib/jdom-1.0.jar:$CYTOSCAPE/lib/com-nerius-math-xform.jar:$CYTOSCAPE/lib/jsr250-api.jar:$CYTOSCAPE/lib/jaxb-impl.jar:$CYTOSCAPE/lib/phoebe.jar:$CYTOSCAPE/lib/wizard.jar:$CYTOSCAPE/lib/jaxws-rt.jar:$CYTOSCAPE/lib/freehep-io-2.0.2.jar:$CYTOSCAPE/lib/sjsxp.jar:$CYTOSCAPE/lib/cytoscape-task.jar:$CYTOSCAPE/lib/l2fprod-common-all.jar:$CYTOSCAPE/lib/freehep-export-2.1.1.jar:$CYTOSCAPE/lib/http.jar:$CYTOSCAPE/lib/coltginy.jar:$CYTOSCAPE/lib/giny.jar:$CYTOSCAPE/lib/freehep-graphicsio-svg-2.1.1.jar:$CYTOSCAPE/lib/cytoscape-geom-rtree.jar:$CYTOSCAPE/plugins/cpath2.jar:$CYTOSCAPE/plugins/TableImport.jar:$CYTOSCAPE/plugins/filters.jar:$CYTOSCAPE/plugins/SBMLReader.jar:$CYTOSCAPE/plugins/AutomaticLayout.jar:$CYTOSCAPE/plugins/filter.jar:$CYTOSCAPE/plugins/browser.jar:$CYTOSCAPE/plugins/GraphMerge.jar:$CYTOSCAPE/plugins/yLayouts.jar:$CYTOSCAPE/plugins/biopax.jar:$CYTOSCAPE/plugins/quick_find.jar:$CYTOSCAPE/plugins/cPath.jar:$CYTOSCAPE/plugins/linkout.jar:$CYTOSCAPE/plugins/psi_mi.jar:$CYTOSCAPE/plugins/ManualLayout.jar:$CYTOSCAPE/plugins/CytoscapeEditor.jar

JARDIR=$CYTOSCAPE/pathways-lib

clspath2=$JARDIR/cd3.jar:$JARDIR/commons-beanutils.jar:$JARDIR/commons-cli.jar:$JARDIR/commons-dbcp-1.1.jar:$JARDIR/commons-digester-1.7.jar:$JARDIR/commons-logging.jar:$JARDIR/commons-pool-1.1.jar:$JARDIR/dom4j.jar:$JARDIR/jastor-1.0.3.jar:$JARDIR/sax.jar:$JARDIR/VDAOEngine.jar:$JARDIR/xbean.jar:$JARDIR/xgmml.jar:$JARDIR/jena/antlr.jar:$JARDIR/jena/icu4j_3_4.jar:$JARDIR/jena/icu4j.jar:$JARDIR/jena/iri.jar:$JARDIR/jena/jakarta-oro-2.0.5.jar:$JARDIR/jena/jena.jar:$JARDIR/jena/rdf-api-2001-01-19.jar:$JARDIR/jena/xml-apis.jar:$JARDIR/BioPAXToCytoscapeConverter.jar:$JARDIR/CellDesignerToCytoscapeConverter.jar:$JARDIR/jena/xercesImpl.jar

#clspath3=../cytoscape-jar/cytoscape-v2.5.1.jar:../cytoscape-jar/cytoscape-v2.6.0.jar:../cytoscape-jar/libs/jena.jar:../cytoscape-jar/libs/binomlibs.jar:../cytoscape-jar/libs/xbeans_jastor.jar:../cytoscape-jar/cytoscape-v2.4.0.jar:../cytoscape-jar/cytoscape-v2.3.1.jar

clspath3=../cytoscape-jar/cytoscape-v2.6.0.jar:../cytoscape-jar/libs/jena.jar:../cytoscape-jar/libs/binomlibs.jar:../cytoscape-jar/libs/xbeans_jastor.jar

CDHOME=/home/viara/projects/CellDesigner4.0.1

#clspath2=/tmp/mkjar.jar

set -e
CLASSPATH=$clspath:$clspath2:$clspath3:$CYTOSCAPE/cytoscape.jar:. $HOME/jdk1.5.0_16/bin/javac -d classes $sources

cd classes; jar cvf ../$plugin.jar .

exit 0
CLASSPATH=$clspath:$clspath2:$CYTOSCAPE/cytoscape.jar:. javadoc -d ../doc/javadoc \
               fr.curie.BiNoM.pathways.test \
               fr.curie.BiNoM.pathways.utils \
               fr.curie.BiNoM.pathways.biopax \
               fr.curie.BiNoM.pathways.wrappers \
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
               fr.curie.BiNoM.cytoscape.plugin \
               -overview fr/curie/overview.html




