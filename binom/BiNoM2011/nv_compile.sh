#!/bin/bash
#
# NaviCell compilation
#
# nv_compile.sh
#
# Eric Viara - Institut Curie copyright (c) November 2015
#

bindir=tmp-bin

mkdir -p $bindir

(
    cd src

    topdir=../lib

    classpath=$topdir/binomlibext.jar:$topdir/cd4.jar:$topdir/celldesigner.jar:$topdir/CellDesigner401Jars.jar:$topdir/csml.jar:$topdir/cytoscape270.jar:$topdir/giny.jar:$topdir/imgscalr-lib-3.1-javadoc.jar:$topdir/imgscalr-lib-3.1-sources.jar:$topdir/imgscalr-lib-3.1.jar:$topdir/jwordpress/commons-collections-3.2.jar:$topdir/jwordpress/commons-configuration-1.5.jar:$topdir/jwordpress/commons-lang-2.3.jar:$topdir/jwordpress/jwordpress-0.5.1-cli.jar:$topdir/jwordpress/jwordpress-0.5.1.jar:$topdir/jwordpress/jwordpress-0.5.jar:$topdir/jwordpress/xmlrpc-client-1.1.jar:$topdir/MathMLIO.jar:$topdir/paxtools-4.2.1-no-jena.jar:$topdir/sbml.jar:$topdir/transpath.jar:$topdir/VDAOEngine.jar:$topdir/xgmml.jar

    javac -cp $classpath:.:../$bindir -d ../$bindir fr/curie/BiNoM/pathways/navicell/ProduceClickableMap.java
)

(
    cd $bindir
    jar cf ../lib/navicell.jar .
)

rm -r $bindir
