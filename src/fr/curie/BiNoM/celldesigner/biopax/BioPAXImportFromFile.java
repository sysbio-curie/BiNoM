
package fr.curie.BiNoM.celldesigner.biopax;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JOptionPane;

import jp.sbi.celldesigner.plugin.PluginAction;
import jp.sbi.celldesigner.plugin.CellDesignerPlugin;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.celldesigner.lib.NetworkFactory;

import javax.swing.JFileChooser;
import javax.swing.*;

import java.awt.*;
import jp.sbi.celldesigner.*;

import fr.curie.BiNoM.biopax.BioPAXImportDialog;

public class BioPAXImportFromFile extends PluginAction {

    private CellDesignerPlugin plugin;

    public BioPAXImportFromFile(CellDesignerPlugin plugin) {
	this.plugin = plugin;
    }

    public void myActionPerformed(ActionEvent e) {
    	
    	
    //String infoMessage = "";
    //Frame frms[] = jp.sbi.celldesigner.MainWindow.getFrames();
    /*for(int i=0;i<frms.length;i++)
    	infoMessage+="Frame:"+frms[i].getName()+" w:"+frms[i].getWidth()+" h:"+frms[i].getHeight()+"\n";
    	*/
    //infoMessage=jp.sbi.celldesigner.MainWindow.ModelInformationAction.LONG_DESCRIPTION;
    //for(int i=0;i<MainWindow.getLastInstance().getCurrentModel().getComponentCount();i++)
    //	infoMessage+=MainWindow.getLastInstance().getCurrentModel().getComponent(i).getClass().getCanonicalName()+":"+MainWindow.getLastInstance().getCurrentModel().getComponent(i).getName()+" w:"+MainWindow.getLastInstance().getCurrentModel().getComponent(i).getWidth()+" h:"+MainWindow.getLastInstance().getCurrentModel().getComponent(i).getHeight()+"\n";;
    //JPanel pnl = (JPanel)MainWindow.getLastInstance().getCurrentModel().getComponent(1);
    //pnl = (JPanel)pnl.getComponent(0);
    //for(int i=0;i<pnl.getComponentCount();i++)
    //	infoMessage+=pnl.getComponent(i).getClass().getCanonicalName()+":"+pnl.getComponent(i).getName()+" w:"+pnl.getComponent(i).getWidth()+" h:"+pnl.getComponent(i).getHeight()+"\n";;
    //infoMessage="Frame "++" h="+jp.sbi.celldesigner.SBModelFrame.HEIGHT+" w="+jp.sbi.celldesigner.SBModelFrame.WIDTH;
    //infoMessage = MainWindow.getLastInstance().getCurrentModel().frameTitle()+" w="+MainWindow.getLastInstance().getCurrentModel().getModelSize().getWidth()+" h="+MainWindow.getLastInstance().getCurrentModel().getModelSize().getHeight();
    //JOptionPane.showMessageDialog(null, infoMessage);
    //MainWindow.getLastInstance().getCurrentModel().getModelSize().setSize(1000, 1000);
    
    
	JFileChooser fileChooser = new JFileChooser();

	/*
	System.out.println("before new frame");

	
	jp.sbi.celldesigner.SBMLFiler sf = new jp.sbi.celldesigner.SBMLFiler(null);
	jp.fric.graphics.multiwindow.MultiWindowFrame mwf = sf.createNewFrame(new java.awt.Frame());

	System.out.println("after new frame");
	*/

	//fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	fileChooser.setFileFilter(new OWLFileFilter());

	fileChooser.setDialogTitle("Load BioPAX File");

	JFrame frame = new JFrame();
	int returnVal = fileChooser.showOpenDialog(frame);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fileChooser.getSelectedFile();
	    try {
		FileInputStream is = new FileInputStream(file);
		is.close();
		BioPAXImportDialog.getInstance().raise(new BioPAXImportTaskFactory(plugin), file, file.getName());
	    }
	    catch(Exception ee) {
		JOptionPane.showMessageDialog
		    (frame,
		     "Cannot open file " + file.getAbsolutePath() + " for reading");
	    }
        }
    }
}
