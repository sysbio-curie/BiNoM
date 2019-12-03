/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
 */
package fr.curie.BiNoM.cytoscape.biopax;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;

import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;
import Main.Launcher;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class BioPAXExportToFile extends AbstractCyAction{
	
	File file = null;
	boolean merge;
	
	public BioPAXExportToFile(){
		super("Export Current Network to BioPAX 3...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}
		
		
		

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String options[] = new String[]{"Merge", "Overwrite", "Cancel"};
		
		while(file == null){
	
			JFileChooser fileChooser = new JFileChooser();

			fileChooser.setFileFilter(new OWLFileFilter());
		
			fileChooser.setDialogTitle("Save BioPAX File");
		
			int userSelection = fileChooser.showOpenDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				
				if(!file.getAbsolutePath().endsWith(".owl"))
					file = new File(file.getAbsolutePath() + ".owl");
				
				System.out.println("Save as file: " + file.getAbsolutePath());
			}
			else
				file = null;
			
			if(file.exists()){
				int r = JOptionPane.showOptionDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), file.getName() + " already exists. Do you want to merge or overwrite information in it?", 
						"Confirmation Dialog", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	
				if (r == JOptionPane.CANCEL_OPTION)
					return;
	
				if (r == JOptionPane.YES_OPTION)
					merge = true;
			}
	
			if (!merge) {
				
		
				try {
					FileOutputStream os;					
					os = new FileOutputStream(file);
					os.close();
					
					TaskIterator t = new TaskIterator(new BioPAXExportTask(file, merge));
					Launcher.getAdapter().getTaskManager().execute(t);
				}
				catch(Exception ee) {
					JOptionPane.showMessageDialog
					(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
							"Cannot open file " + file.getAbsolutePath() + " for writing");
					break;
				}
			}		
		}
	}
}
