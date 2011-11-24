#!/bin/sh
###########################################################################
#
# CellDesigner4.0.1.sh - CellDesigner version 4.0.1 for Linux
#
# Copyright (c) 2008 The Systems Biology Institute. All rights reserved.
#
###########################################################################

APP_NAME="CellDesigner4.0.1"

BINOM_CLASS_PATH="pathways-lib/cd3.jar:\
pathways-lib/commons-beanutils.jar:\
pathways-lib/commons-cli.jar:\
pathways-lib/commons-dbcp-1.1.jar:\
pathways-lib/commons-digester-1.7.jar:\
pathways-lib/commons-logging.jar:\
pathways-lib/commons-pool-1.1.jar:\
pathways-lib/dom4j.jar:\
pathways-lib/jastor-1.0.3.jar:\
pathways-lib/jena/antlr.jar:\
pathways-lib/jena/concurrent.jar:\
pathways-lib/jena/icu4j_3_4.jar:\
pathways-lib/jena/icu4j.jar:\
pathways-lib/jena/iri.jar:\
pathways-lib/jena/jakarta-oro-2.0.5.jar:\
pathways-lib/jena/jena.jar:\
pathways-lib/jena/jsr173_1.0_api.jar:\
pathways-lib/jena/junit.jar:\
pathways-lib/jena/rdf-api-2001-01-19.jar:\
pathways-lib/jena/xercesImpl.jar:\
pathways-lib/jena/xml-apis.jar:\
pathways-lib/MathMLIO.jar:\
pathways-lib/sax.jar:\
pathways-lib/sbml.jar:\
pathways-lib/VDAOEngine.jar:\
pathways-lib/xbean.jar:\
pathways-lib/xgmml.jar:\
pathways-lib/BiNoM.jar
"

CLASS_PATH="
exec/celldesigner.jar:\
exec/autolayout_yobf.jar:\
exec/yObf.jar:\
lib/avalon-framework-4.1.4.jar:\
lib/batik.jar:\
lib/browserlauncher.jar:\
lib/freehep-export-2.0.3.jar:\
lib/freehep-graphics2d-2.0.jar:\
lib/freehep-graphicsio-2.0.jar:\
lib/freehep-graphicsio-ps-2.0.jar:\
lib/freehep-io-2.0.1.jar:\
lib/freehep-swing-2.0.2.jar:\
lib/freehep-util-2.0.1.jar:\
lib/itext-1.4.6.jar:\
lib/jai_codec.jar:\
lib/jai_core.jar:\
lib/jcommon-1.0.0-pre2.jar:\
lib/jeuclid-2.0.jar:\
lib/jfreechart-1.0.0-pre2.jar:\
lib/mlibwrapper_jai.jar:\
lib/MRJAdapter.jar:\
lib/openide-lookup-1.9-patched-1.0.jar:\
lib/sbmlj.jar:\
lib/SBWCore.jar:\
lib/xercesImpl.jar:\
lib/xml-apis.jar:\
lib/SOSlib.jar:\
lib/copasi.jar:\
lib/copasi_gui.jar:\
$BINOM_CLASS_PATH
"

JAR_ARGS=-sbwmodule

CD_PREFIX="/home/viara/projects/CellDesigner4.0.1"

MAIN_CLASS=jp.sbi.celldesigner.Application

JAVA="${CD_PREFIX}/jre/bin/java -enableassertions"
#JAVA="$HOME/jre1.6.0_10/bin/java -server"
VM_ARGS="-Xms32M -Xmx512M -Djava.library.path=."

if [ ! -d "${CD_PREFIX}" ]; then
   echo "Error!! ${CD_PREFIX}: No such directory."
   exit 1
fi

cd "${CD_PREFIX}"

${JAVA} ${VM_ARGS} -cp ${CLASS_PATH} ${MAIN_CLASS} ${JAR_ARGS}  

exit 0
