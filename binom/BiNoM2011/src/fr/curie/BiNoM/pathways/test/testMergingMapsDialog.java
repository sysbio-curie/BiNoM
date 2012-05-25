package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;

public class testMergingMapsDialog {

	public static void main(String[] args) {
		JFrame w = new JFrame();
		
		ArrayList<File> l = new ArrayList<File>();
		l.add(new File("/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml"));
		l.add(new File("/bioinfo/users/ebonnet/Binom/mergeMaps/a2.xml"));
		l.add(new File("/bioinfo/users/ebonnet/Binom/mergeMaps/a3.xml"));
		
		MergingMapsDialog mp = new MergingMapsDialog(w,"Merge Cell Designer maps",true, l);
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
