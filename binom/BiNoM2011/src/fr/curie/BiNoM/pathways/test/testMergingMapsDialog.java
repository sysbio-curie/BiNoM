package fr.curie.BiNoM.pathways.test;

import javax.swing.JFrame;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;

public class testMergingMapsDialog {

	public static void main(String[] args) {
		JFrame w = new JFrame();
		MergingMapsDialog mp = new MergingMapsDialog(w,"Merge Cell Designer maps",true);
		mp.setVisible(true);
		w.dispose();
	}	
}
