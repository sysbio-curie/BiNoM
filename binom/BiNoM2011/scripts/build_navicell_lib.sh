#!/bin/sh
#
# build_navicell_lib.sh
#
# Eric Viara Institut Curie December 2013
#

if [ $# != 1 ]; then echo "usage: $0 TARFILE"; exit 1; fi

tarfile=$1

dir=$(dirname $0)
echo $dir | grep "^\." > /dev/null
if [ $? = 0 ]; then dir=$(pwd)/$dir; fi

tmpdir=/tmp/build_navicell_lib.$$
trap "echo | rm -rf $tmpdir" 0 1 2 3

tmplibdir=$tmpdir/lib
mkdir -p $tmplibdir

tmpicondir=$tmpdir/map_icons
mkdir -p $tmpicondir

cd $dir/../navicell_lib

find . -print | grep -v "\.svn" | cpio -pdm $tmplibdir

cd $dir/../src/javascript_lib

find . -follow -print | grep -v "\.svn" | cpio -pdm $tmplibdir

cd $dir/../navicell_icons

find . -print | grep -v "\.svn" | cpio -pdm $tmpicondir

echo $tarfile | grep "gz$" > /dev/null

if [ $? = 0 ]; then
    opts=cfz
else 
    echo $tarfile | grep "tar$" > /dev/null
    if [ $? = 0 ]; then
	opts=cf
    else
	tarfile=${tarfile}.tgz
	opts=cfz
    fi
fi

cd $tmpdir

tar $opts $tarfile lib map_icons

echo "navicell lib tarfile: " $tarfile

