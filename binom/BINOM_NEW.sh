#!/bin/sh
#
# BINOM_NEW.sh
#
# 7 Nov 2008
#
# Eric Viara for Institut Curie
#

find BiNoM-cytoscape-2.6-svn -follow -newer BINOM_TIMESTAMP \( \! -type d -a \! -name \*.class -a \! -name \*.jar -a \! -name '*~' -a \! -name \*-bak -a \! -name compile-plugin.sh \)
