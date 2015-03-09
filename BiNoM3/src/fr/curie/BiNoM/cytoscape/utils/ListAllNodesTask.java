package fr.curie.BiNoM.cytoscape.utils;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class ListAllNodesTask implements Task{

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(TaskMonitor taskMonitor) {
		// TODO Auto-generated method stub
		taskMonitor.setTitle(getTitle());
		
		ListAllNodesDialog dialog = new ListAllNodesDialog();
		
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());

		org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = CellDesignerSourceDB.getInstance().getCellDesigner(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		
		dialog.pop(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()),sbmlDoc,biopax);
		taskMonitor.setProgress(1);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
