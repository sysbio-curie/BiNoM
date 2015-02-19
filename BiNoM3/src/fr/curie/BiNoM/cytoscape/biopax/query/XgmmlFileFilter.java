package fr.curie.BiNoM.cytoscape.biopax.query;


import java.io.File;

public class XgmmlFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".xgmml");
    }

    public String getDescription() {
	return "XGmML";
    }
}
