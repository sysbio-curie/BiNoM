#!/bin/sh
#
# NaviCell
#
# make_navicell_package.sh
#
# Eric Viara copyright (c) Institut Curie 2015
#

if [ $# != 1 ]; then echo "usage: $0 TARGET_DIR"; exit 1; fi

echo $1 | grep "^/" > /dev/null
if [ $? != 0 ]; then echo "$0: TARGET_DIR must be an absolute path"; exit 1; fi

target_dir=$1

if [ -d $target_dir ]; then echo "$0: TARGET_DIR $target_dir already exists"; exit 1; fi

version=2.1

mkdir $target_dir

mydir=$(pwd)
set -e
sh ${mydir}/make_factory_package.sh $target_dir
sh ${mydir}/make_site_package.sh $target_dir
sh ${mydir}/make_binding_package.sh $target_dir

cd ${target_dir}

tarfile=navicell.${version}.tgz 

tar cvfz ${tarfile} navicell

rm -rf navicell

echo navicell tar file is ${target_dir}/${tarfile}
