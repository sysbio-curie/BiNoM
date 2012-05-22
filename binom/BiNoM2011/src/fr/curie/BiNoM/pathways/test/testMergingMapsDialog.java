package fr.curie.BiNoM.pathways.test;

import java.io.File;

import javax.swing.JFrame;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;
import fr.curie.BiNoM.pathways.utils.MergingMapsSelectFilesDialog;

public class testMergingMapsDialog {

	public static void main(String[] args) {
		JFrame w = new JFrame();
		
		MergingMapsDialog mp = new MergingMapsDialog(w,"Merge Cell Designer maps",true);
		mp.setVisible(true);
		
//		MergingMapsSelectFilesDialog sf = new MergingMapsSelectFilesDialog(w, "Merging Maps Step 1", true);
//		sf.setVisible(true);
//		
//		if (sf.fileList.size()>=2) {
//			// do something!
//		}
		
		w.dispose();
	}	
}
