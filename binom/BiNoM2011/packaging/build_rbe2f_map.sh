#!/bin/bash
#
# NaviCell build_rbe2f_map.sh
#
# Eric Viara Institut Curie copyright (c) November 2015
#

if [ $# != 1 ]; then
    echo "usage: $0 TARGET_DIR";
    echo "       /rbe2f will be automatically appended to TARGET_DIR (=> don't include it in TARGET_DIR)"
    if [ -d ../../site/docroot/navicell/maps ]; then
	echo "for instance: sh $0 ../../site/docroot/navicell/maps";
    fi
    exit 1;
 fi

destdir="$1"/rbe2f

if [ -d $destdir ]; then echo "$0: TARGET_DIR $destdir already exists"; exit 1; fi

mkdir $destdir

set -e
sh ../scripts/run_factory.sh --config rbe2f_src/config --xrefs rbe2f_xrefs.txt --destination $destdir --verbose $*

cd $destdir
url=$(pwd)/master/index.html

echo
echo rbe2f map has been succesfully generated
echo available at URL file://${url}
echo
echo NOTICE: to work properly, $url must be located within the navicell website directory

