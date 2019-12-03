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

map_example1=cellcycle
map_example2=simple_map
map_example3=simple_modular

mkdir -p $topdir
mkdir $topdir/lib
mkdir $topdir/data
mkdir $topdir/scripts
mkdir $topdir/examples
mkdir $topdir/maps
mkdir $topdir/maps/${map_example1}_src
mkdir $topdir/examples/${map_example2}_src
mkdir $topdir/examples/${map_example3}_src

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
  cd $topdir/maps
  tar xfz $mydir/${map_example1}_src.tgz
  cd $topdir/examples
  tar xfz $mydir/${map_example2}_src.tgz
  tar xfz $mydir/${map_example3}_src.tgz
)

cp ../scripts/run_factory.sh $topdir/scripts/
cp build_${map_example1}_map.sh $topdir/maps/
cp build_${map_example1}_map.bat $topdir/maps/
cp build_${map_example2}_map.sh $topdir/examples/
cp build_${map_example2}_map.bat $topdir/examples/
cp build_${map_example3}_map.sh $topdir/examples/
cp build_${map_example3}_map.bat $topdir/examples/
cp xrefs.txt $topdir/examples/
cp xrefs.txt $topdir/maps/

