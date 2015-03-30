package fr.curie.BiNoM.cytoscape.celldesigner;

import java.io.File;

public class GMTFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".gmt");
    }

    public String getDescription() {
	return "GMT";
    }
}
