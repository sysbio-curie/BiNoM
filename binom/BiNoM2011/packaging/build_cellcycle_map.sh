#!/bin/bash
#
# NaviCell build_cellcycle_map.sh
#
# Eric Viara Institut Curie copyright (c) November 2015
#

if [ $# != 1 ]; then
    echo "usage: $0 TARGET_DIR";
    echo "       /cellcycle will be automatically appended to TARGET_DIR (=> don't include it in TARGET_DIR)"
    if [ -d ../../site/docroot/navicell/maps ]; then
	echo "for instance: sh $0 ../../site/docroot/navicell/maps";
    fi
    exit 1;
 fi

destdir="$1"/cellcycle

if [ -d $destdir ]; then echo "$0: TARGET_DIR $destdir already exists"; exit 1; fi

mkdir $destdir

set -e
sh ../scripts/run_factory.sh --config cellcycle_src/config --xrefs cellcycle_xrefs.txt --destination $destdir --verbose --demo $*

cd $destdir
url=$(pwd)/master/index.html

echo
echo cellcycle map has been succesfully generated
echo available at URL file://${url}
echo
echo NOTICE: to work properly, $url must be located within the navicell website directory

