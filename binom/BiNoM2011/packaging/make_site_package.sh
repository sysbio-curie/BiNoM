#!/bin/sh
#
# NaviCell
#
# make_site_package.sh
#
# Eric Viara copyright (c) Institut Curie 2015
#

if [ $# != 1 ]; then echo "usage: $0 TARGET_DIR"; exit 1; fi

echo $1 | grep "^/" > /dev/null
if [ $? != 0 ]; then echo "$0: TARGET_DIR must be an absolute path"; exit 1; fi

target_dir=$1
topdir=${target_dir}/navicell/site

if [ -d $topdir ]; then echo "$0: TARGET_DIR $topdir already exists"; exit 1; fi

mydir=$(pwd)

set -e 

cgibinroot=$topdir/cgi-bin
docroot=$topdir/docroot
nv_topdir=$docroot/navicell

mkdir -p $docroot/data
mkdir -p $nv_topdir
mkdir $nv_topdir/maps
mkdir $nv_topdir/demo

export topdir nv_topdir docroot

(
    set -e
    cd ../scripts
    tmpfile=/tmp/navicell_lib.$$.tgz
    trap "rm -f $tmpfile" 0 1 2 3

    NOEXT=1 sh build_navicell_lib.sh $tmpfile

    cd $nv_topdir
    tar xvfz ${tmpfile}
)

(cd ../navicell_demo; find demo | egrep -v '~$|\.svn|ACSN' | cpio -pdmv $nv_topdir/)
(cd ../navicell_data_examples; find . | egrep -v '~$|\.svn' | cpio -pdmv $docroot/data)

mkdir -p $cgibinroot

cp ../src/analysis_cgi/gene_enrichment/* $cgibinroot/
cp ../src/phplib/nv_proxy.php $cgibinroot/
rm -f $cgibinroot/*~

