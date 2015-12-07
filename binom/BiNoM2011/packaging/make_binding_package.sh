#!/bin/sh
#
# NaviCell
#
# make_binding_package.sh
#
# Eric Viara copyright (c) Institut Curie 2015
#

if [ $# != 1 ]; then echo "usage: $0 TARGET_DIR"; exit 1; fi

echo $1 | grep "^/" > /dev/null
if [ $? != 0 ]; then echo "$0: TARGET_DIR must be an absolute path"; exit 1; fi

target_dir=$1
topdir=${target_dir}/navicell/bindings

if [ -d $topdir ]; then echo "$0: TARGET_DIR $topdir already exists"; exit 1; fi

mydir=$(pwd)

set -e 

mkdir -p $topdir/python

export topdir

# python

(
    cd ../src/python_api
    find curie curie.navicell.html setup.py nvpy  README.txt  MANIFEST MANIFEST.in  | grep -v "\.svn" | cpio -pdmv $topdir/python
)

export https_proxy=http://www-cache.curie.fr:3128
for pack in JNaviCell RNaviCell
do
    git clone "https://github.com/sysbio-curie/$pack" $topdir/$pack
    echo | rm -r $topdir/$pack/.git
done

package=bindings

cd ${target_dir}

tar cfz navicell_${package}.tgz navicell/${package}

rm -rf ${target_dir}/navicell

echo
echo "tar file is $(pwd)/navicell_${package}.tgz"
