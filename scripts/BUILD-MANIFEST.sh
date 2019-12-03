#!/bin/sh
#
# BUILD-MANIFEST.sh
#
# Programme to build new manifest for cytoscape.jar
#

if [ $# != 1 ]
then
    echo "usage: $0 <version>"
    exit 1
fi

version=$1
set -e
cd $HOME/projects/cytoscape-v${version}-bin

if [ ! -r cytoscape-orig.jar ]; then cp cytoscape.jar cytoscape-orig.jar; fi

jar xvf cytoscape-orig.jar META-INF/MANIFEST.MF
mv META-INF/MANIFEST.MF MANIFEST-orig.MF
dos2unix MANIFEST-orig.MF

awk 'NF > 1 {print}' MANIFEST-orig.MF > MANIFEST.MF

if [ ! -r pathways-lib ]; then ln -s ../BiNoM/lib pathways-lib; fi
if [ ! -r plugins/BiNoM.jar ]; then (cd plugins; ln -s ../../BiNoM/src/BiNoM.jar); fi

for f in `find pathways-lib -follow -name \*.jar`
do
  echo " " $f >> MANIFEST.MF
done

cp cytoscape-orig.jar cytoscape.jar
jar uvmf MANIFEST.MF cytoscape.jar 
