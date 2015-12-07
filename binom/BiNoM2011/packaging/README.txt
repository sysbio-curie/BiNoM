
Building packages
-----------------

cd workspace/BiNoM2011
svn update # or co 

cd packaging

Let /scratch/NAVICELL_PUB being the target directory:

1. building factory package

sh make_factory_package.sh /scratch/NAVICELL_PUB

list of created directories:
/scratch/NAVICELL_PUB/navicell/factory
/scratch/NAVICELL_PUB/navicell/factory/examples
/scratch/NAVICELL_PUB/navicell/factory/examples/cellcycle_src
/scratch/NAVICELL_PUB/navicell/factory/lib
/scratch/NAVICELL_PUB/navicell/factory/data
/scratch/NAVICELL_PUB/navicell/factory/scripts

2. building site package

sh make_site_package.sh /scratch/NAVICELL_PUB

Notice:
  At this step, we can:
  a. build a cellcyle map
  b. manipulate cellcycle with navicell using the file:// protocol

3. building binding package

sh make_bindings_package.sh /scratch/NAVICELL_PUB
