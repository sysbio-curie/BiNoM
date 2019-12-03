package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cytoscape.*;
import cytoscape.util.FileUtil;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.wrappers.*;


public class CreateSetIntersectionGraph implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			Integer opt = j.showOpenDialog(Cytoscape.getDesktop());
			if(opt!=-1){
				//printSetIntersectionsInFolder("C:/Datas/acsn/naming/");
				try{
				SetOverlapAnalysis.printSetIntersectionsInFolder(j.getSelectedFile().getAbsolutePath());
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

}
