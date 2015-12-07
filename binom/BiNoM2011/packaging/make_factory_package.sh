#!/bin/sh
#
# NaviCell
#
# make_factory_package.sh
#
# Eric Viara copyright (c) Institut Curie 2015
#

if [ $# != 1 ]; then echo "usage: $0 TARGET_DIR"; exit 1; fi

echo $1 | grep "^/" > /dev/null
if [ $? != 0 ]; then echo "$0: TARGET_DIR must be an absolute path"; exit 1; fi

target_dir=$1
topdir=${target_dir}/navicell/factory

export topdir

if [ -d $topdir ]; then echo "$0: TARGET_DIR $topdir already exists"; exit 1; fi

mydir=$(pwd)

set -e 

map_example=cellcycle

mkdir -p $topdir
mkdir $topdir/lib
mkdir $topdir/data
mkdir $topdir/scripts
mkdir $topdir/examples
mkdir $topdir/examples/${map_example}_src

(
  cd ..
  sh nv_compile.sh
  cp lib/*.jar $topdir/lib/
)

(
  cd ../src
  find data |  egrep -v '~$|\.svn' | cpio -pdmv $topdir
)

(
  cd $topdir/examples
  tar xfz $mydir/${map_example}_src.tgz
)

cp ../scripts/run_factory.sh $topdir/scripts/
cp build_${map_example}_map.sh $topdir/examples/
cp ${map_example}_xrefs.txt $topdir/examples/

