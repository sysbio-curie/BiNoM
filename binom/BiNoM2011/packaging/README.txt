
Building packages
-----------------

Requirements
- must be on a unix computer
- needs Java 1.6 or later
- needs svn

cd workspace/BiNoM2011
svn update # or svn co svn+ssh://svn.curie.fr/U900/research/software-dvt/binom/trunk/binom/BiNoM2011


cd packaging

Let /scratch/NAVICELL_PUB being the target directory:

1. building factory package

sh make_factory_package.sh /scratch/NAVICELL_PUB

list of created directories:
/scratch/NAVICELL_PUB/navicell/factory
/scratch/NAVICELL_PUB/navicell/factory/examples
/scratch/NAVICELL_PUB/navicell/factory/examples/rbe2f_src
/scratch/NAVICELL_PUB/navicell/factory/lib
/scratch/NAVICELL_PUB/navicell/factory/data
/scratch/NAVICELL_PUB/navicell/factory/scripts

2. building site package

sh make_site_package.sh /scratch/NAVICELL_PUB

Notice:
  At this step, we can:
  a. build a cellcyle map
  b. manipulate rbe2f with navicell using the file:// protocol

3. building binding package

sh make_bindings_package.sh /scratch/NAVICELL_PUB
