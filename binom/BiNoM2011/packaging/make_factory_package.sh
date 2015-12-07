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

mkdir -p $topdir
mkdir $topdir/lib
mkdir $topdir/data
mkdir $topdir/scripts
mkdir $topdir/examples
mkdir $topdir/examples/cellcycle_src

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
  tar xfz $mydir/cellcycle_src.tgz
)

cp ../scripts/run_factory.sh $topdir/scripts/
cp build_cellcycle_map.sh $topdir/examples/
cp cellcycle_xrefs.txt $topdir/examples/

package=factory

cd ${target_dir}

tar cfz navicell_${package}.tgz navicell/${package}

rm -rf ${target_dir}/navicell

echo
echo "tar file is $(pwd)/navicell_${package}.tgz"
