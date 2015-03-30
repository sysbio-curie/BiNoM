package fr.curie.BiNoM.cytoscape.ain;

import Main.Launcher;

import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.*;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;

import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;
import fr.curie.BiNoM.pathways.SimpleTextInfluenceToBioPAX;
import fr.curie.BiNoM.pathways.utils.Utils;

public class ImportFromSimpleTextInfluenceFile extends AbstractCyAction {
	
	public ImportFromSimpleTextInfluenceFile(){
		super("Import influence network from AIN file...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}
	
	public static ImportFromAINDialogFamily dialog;
	public static ImportFromAINDialogConstitutiveReactions dialogCR;

    public void actionPerformed(ActionEvent e) {
    	
    	
//        CyFileFilter bioPaxFilter = new CyFileFilter();
//
//        bioPaxFilter.addExtension("txt");
//        bioPaxFilter.setDescription("Annotated influence network (AIN) files");
//
//        File file = FileUtil.getFile
//	    ("Load Annotated Influence Network File", FileUtil.LOAD, new CyFileFilter[]{bioPaxFilter});
    	
    	File file = null;
    	JFileChooser fileChooser = new JFileChooser();

		//fileChooser.setFileFilter(new XMLFileFilter());
	
		fileChooser.setDialogTitle("Load Annotated Influence Network File");
	
		int returnVal = fileChooser.showOpenDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());

	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fileChooser.getSelectedFile();	   
	    }

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
        	
        	TaskIterator t = new TaskIterator(new ImportFromAINTask(file,text,dialogCR.constitutiveReactions));
    		Launcher.getAdapter().getTaskManager().execute(t);

        	
	    	//ImportFromAINTask iain = new ImportFromAINTask(file,text,dialogCR.constitutiveReactions);
	    	//iain.run();
        	//JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Finished");
	    }
        }
		}
	    }
	    catch(Exception ee) {
	    	ee.printStackTrace();
			JOptionPane.showMessageDialog
			    (Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
			     "Cannot open file " + file.getAbsolutePath() + " for reading\n"+ee.getMessage());
		    }
	    
    }
}