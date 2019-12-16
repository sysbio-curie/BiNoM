# BiNoM: Biological Network Manager <img align="right" src="http://binom.curie.fr/images/binom_logo1.gif">
## Cytoscape plug-in for manipulating and analysing biological networks 

BiNoM is a Cytoscape plugin developed by the [Computational Systems Biology of Cancer](https://sysbio.curie.fr) group in [Bioinformatics Laboratory of Institut Curie (Paris)](https://science.curie.fr/recherche/biologie-interactive-des-tumeurs-immunologie-environnement/c/).

BiNoM is a Cytoscape plugin, developed to facilitate the manipulation of biological networks represented in standard systems biology formats ([SBML](https://sbml.org), [SBGN](https://www.sbgn.org), [BioPAX](https://www.biopax.org)) and to carry out studies on the network structure. BiNoM provides the user with a complete interface for the analysis of biological networks in Cytoscape environment.

## Instructions to generate Cytoscape files (*.xgmml) Knowledge graph from BioPax v2 file
``` 
cd src
make BioPAX2Cytoscape
./BioPAX2Cytoscape.sh <biopax_file> FULL_INDEX_CONVERSION
```

## Instructions to generate BioPax v2 from ACSN 2.0 (CellDesigner files)
```
cd src
make CellDesigner2BioPAX
./CellDesigner2BioPAX <celldesigner_file>
```

## Authors

- Andrei Zinovyev
- Eric Bonnet
- Eric Viara
- Nadir Sella
- Vincent Noël

## License

This project is licensed under the GNU Lesser General Public
   License - see the [LICENSE](LICENSE) file for details

