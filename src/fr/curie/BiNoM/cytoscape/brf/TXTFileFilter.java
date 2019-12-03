package fr.curie.BiNoM.cytoscape.brf;

import java.io.File;

public class TXTFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".txt");
    }

    public String getDescription() {
	return "TXT";
    }
}

