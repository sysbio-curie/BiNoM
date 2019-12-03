
package fr.curie.BiNoM.celldesigner.biopax;

import javax.swing.filechooser.FileFilter;
import java.io.File;

class OWLFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File file) {
	return file.isDirectory() || file.getName().endsWith(".owl");
    }

    public String getDescription() {
	return "OWL";
    }
}
