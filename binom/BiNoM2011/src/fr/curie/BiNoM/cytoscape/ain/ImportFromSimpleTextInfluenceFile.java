package fr.curie.BiNoM.cytoscape.ain;

import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.JOptionPane;

import fr.curie.BiNoM.pathways.SimpleTextInfluenceToBioPAX;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.*;

public class ImportFromSimpleTextInfluenceFile implements ActionListener {
	
	public static ImportFromAINDialogFamily dialog;
	public static ImportFromAINDialogConstitutiveReactions dialogCR;

    public void actionPerformed(ActionEvent e) {
        CyFileFilter bioPaxFilter = new CyFileFilter();

        bioPaxFilter.addExtension("txt");
        bioPaxFilter.setDescription("Annotated influence network (AIN) files");

        File file = FileUtil.getFile
	    ("Load Annotated Influence Network File", FileUtil.LOAD, new CyFileFilter[]{bioPaxFilter});

	    try {        
        
		String text = Utils.loadString(file.getAbsolutePath());
		SimpleTextInfluenceToBioPAX.deleteInstance();
		text = SimpleTextInfluenceToBioPAX.getInstance().preprocessText(text);
		SimpleTextInfluenceToBioPAX.getInstance().prepareFamilies(text);
		SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.clear();


		JFrame window = new JFrame();
		dialog = new ImportFromAINDialogFamily(window,"Defining families",true);
		dialog.setVisible(true);
		
		if(dialog.result>0){

		SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies = dialog.tempFamilies;
			
		window = new JFrame();
		dialogCR = new ImportFromAINDialogConstitutiveReactions(window,"Constitutive reactions",true);
		dialogCR.pop();
		
		if(dialogCR.result>0){
			
			

        if (file != null) {
	    	ImportFromAINTask iain = new ImportFromAINTask(file,text,dialogCR.constitutiveReactions);
	    	iain.run();
        	//JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Finished");
	    }
        }
		}
	    }
	    catch(Exception ee) {
	    	ee.printStackTrace();
			JOptionPane.showMessageDialog
			    (Cytoscape.getDesktop(),
			     "Cannot open file " + file.getAbsolutePath() + " for reading\n"+ee.getMessage());
		    }
	    
    }
}