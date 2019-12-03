#!/bin/sh
#
# mergejar
#

pwd=`pwd`
jarfiles="$*"

dir=mkjar
cd /tmp
echo | /bin/rm -rf $dir
mkdir $dir
cd $dir

for jf in $jarfiles
do
#  echo JARFILE $jf
  jar xf $pwd/$jf
done

echo | /bin/rm -rf META-INF
jarfile=/tmp/BiNoM_full.jar
jar cf $jarfile .

echo
echo JAR file is $jarfile
 