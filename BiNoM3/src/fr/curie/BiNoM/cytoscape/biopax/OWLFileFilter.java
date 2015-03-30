package fr.curie.BiNoM.cytoscape.biopax;

import java.io.File;

public class OWLFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".owl");
    }

    public String getDescription() {
	return "OWL";
    }
}
