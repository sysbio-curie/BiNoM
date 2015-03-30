package fr.curie.BiNoM.cytoscape.celldesigner;

import java.io.File;

public class PNGFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".png");
    }

    public String getDescription() {
	return "PNG";
    }
}
