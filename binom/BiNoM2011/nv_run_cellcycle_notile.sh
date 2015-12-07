#!/bin/bash
#
# Navicell cellcycle with no tile computing
#
# nv_run.sh
#
# Eric Viara Institut Curie copyright (c) February 2015
#

JAVAOPTS=-Xmx6000M sh nv_run.sh --config ~/projects/navicell/assembly/cellcycle_src/config --destination ~/projects/navicell/tmp/maps/cellcycle --verbose --nv2 --notile
