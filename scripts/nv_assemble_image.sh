#!/bin/bash
#
# nv_assemble_images.sh
#
# navicell package
# to be applied after an export image under Google Chrome
#
# Eric Viara Institut Curie
# 11 May 2016
#

convert=convert

prog=$(basename $0)

typeset -i count
ret=$(ls nv_image_x_* | grep '(' | wc -l)
if [ $ret != 0 ]; then
    echo "$prog: containing invalid images:" $(ls nv_image_x_* | grep '(')
    exit 1
fi

img_count=$(ls nv_image_x_* | grep -v '(' | wc -l)
if [ $img_count = 0 ]; then
    echo "$prog: no navicell images found"
    exit 1
fi

NV_IMAGE_ASSEMBLY=NV_IMAGE_ASSEMBLY

count=$(echo "sqrt($img_count)" | bc) 
computed_count=$(echo "$count * $count" | bc) 
if [ $computed_count != $img_count ]; then
    echo "$prog: invalid image count $img_count: must be a square number"
    exit 1
fi

set -e
image_list_y=""

for y in $(seq $count)
do
    image_list_x=""

    for x in $(seq $count)
    do
	image_list_x="$image_list_x nv_image_x_${x}_y_${y}.png"
    done

    image_y=${NV_IMAGE_ASSEMBLY}_${y}.png
    ${convert} $image_list_x +append $image_y
    image_list_y="$image_list_y $image_y"
done

${convert} $image_list_y -append ${NV_IMAGE_ASSEMBLY}.png

echo "[image successfully generated]"
file ${NV_IMAGE_ASSEMBLY}.png

rm -f $image_list_y
