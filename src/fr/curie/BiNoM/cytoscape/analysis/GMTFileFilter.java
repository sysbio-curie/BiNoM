package fr.curie.BiNoM.cytoscape.analysis;

import java.io.File;

public class GMTFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".gnt");
    }

    public String getDescription() {
	return "GMT";
    }
}
