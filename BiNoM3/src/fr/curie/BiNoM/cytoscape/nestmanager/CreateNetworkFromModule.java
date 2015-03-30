package fr.curie.BiNoM.cytoscape.nestmanager;




import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import Main.Launcher;
/**
 *  Merge selected nests using NestMerging
 *  
 *  @author Nadir Sella
 */ 
public class CreateNetworkFromModule extends AbstractCyAction{
	
	public CreateNetworkFromModule(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="Create Network from Selected Module";
	public void actionPerformed(ActionEvent v) {
//		NestMerging merging=new NestMerging(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView());
//		if(merging.perform(ModuleUtils.getSelectedNodes(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()))) 
//			Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView().updateView();

	CyNetwork currentNetw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	String name = "";	
	
	int count = 0;
	List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
	
	if(nodes.size() > 0){
		for(CyNode node:nodes){
			if(node.getNetworkPointer() != null)
				count += 1;
		}
	}
	
	if(count == 1){
		CyNetwork netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
		org.cytoscape.view.model.CyNetworkView netView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(netw);	
		netw.getRow(netw).set(CyNetwork.NAME, name);
		Launcher.getAdapter().getCyNetworkManager().addNetwork(netw);
	
		for(CyNode node:nodes){
			if(node.getNetworkPointer() != null){
				CyNetwork netModule = node.getNetworkPointer();
				List<CyNode> nodesList = netModule.getNodeList();
				
				Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(netModule);
				Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
				CyNetworkView fromView =null;
				while(networkViewIterator.hasNext())
					fromView = networkViewIterator.next();
				
				for(CyNode nd:nodesList){
					NetworkUtils.addNodeAndReportPosition(nd, netModule, fromView, netw, netView);
				}
				
				List<CyEdge> edgesList = netModule.getEdgeList();
				
				for(CyEdge ed:edgesList){
					try {
						NetworkUtils.addEd(netw, netModule, ed);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				name = currentNetw.getRow(node).get(CyNetwork.NAME, String.class);
				
			}			
		}
		
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(currentNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
		
		netw.getRow(netw).set(CyNetwork.NAME, name);
	
		Launcher.getAdapter().getCyNetworkViewManager().addNetworkView(netView);
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);			
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, netView);
	
	
		netView.fitContent();			
		netView.updateView();
		
		
	}
	else
		JOptionPane.showMessageDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "Select only one module first");
	}
}
