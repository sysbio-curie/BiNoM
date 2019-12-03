package fr.curie.BiNoM.cytoscape.celldesigner;

import java.io.File;

public class XMLFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".xml");
    }

    public String getDescription() {
	return "XML";
    }
}
