package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;



public class CreateSetIntersectionGraph extends AbstractCyAction{
	
	public CreateSetIntersectionGraph(){
		super("Create set intersection graph in a folder",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM Utilities");
	}

		public void actionPerformed(ActionEvent e) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			Integer opt = j.showOpenDialog(null /*Cytoscape.getDesktop()*/);
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
