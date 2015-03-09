package fr.curie.BiNoM.cytoscape.analysis;


import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ActionEvent;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;

import Main.Launcher;
import fr.curie.BiNoM.pathways.utils.WeightGraphStructure;
/**
 * Match nodes and strong connected components by attribute in table
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class SCCinAttribute  extends AbstractCyAction {	
	private static final long serialVersionUID = 1L;
	final static String title="Display SCC in Attribute";
	final String nodeColumnName="SCC";
	public SCCinAttribute(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
        setPreferredMenu(Launcher.appName + ".Analysis");
	}
	public void actionPerformed(ActionEvent e) {		
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CyNetwork network=applicationManager.getCurrentNetwork();
		WeightGraphStructure gs=new WeightGraphStructure(network);
		ArrayList<HashSet<Integer>> scc=(new SCCinTable(gs)).SCC();
		if(network.getDefaultNodeTable().getColumn(nodeColumnName)==null) 
			network.getDefaultNodeTable().createColumn(nodeColumnName,String.class,false);
		
		int sccNb=0;
		for(int i=0;i<scc.size();i++){
			if(scc.get(i).size()>1){
				sccNb++;
				for(int n:scc.get(i)) network.getRow(gs.nodes.get(n)).set(nodeColumnName,"SCC"+sccNb);
			}else{
				network.getRow(gs.nodes.get(scc.get(i).iterator().next())).set(nodeColumnName,"NotInSCC");
			}
		}	
	}
}