package org.cytoscape.sample;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.io.write.* ;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.io.CyFileFilter;
import java.awt.event.ActionEvent;

public class MenuAction extends AbstractCyAction {
    private final CyAppAdapter adapter;

    public MenuAction(CyAppAdapter adapter) {
        super("DataDrivenLayout",
            adapter.getCyApplicationManager(),
            "network",
            adapter.getCyNetworkViewManager());
        this.adapter = adapter;
        setPreferredMenu("Layout");
    }
    
    public void actionPerformed(ActionEvent e) {
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = networkView.getModel();
        
        CyNetwork mynetwork = manager.getCurrentNetwork();
        CyTable mytable = mynetwork.getDefaultNodeTable();
        
        int count = mytable.getRowCount();
        System.out.print(count);
        for (CyNode node : network.getNodeList()) {
            if (network.getNeighborList(node, CyEdge.Type.ANY).isEmpty())
                networkView.getNodeView(node).setVisualProperty(
                    BasicVisualLexicon.NODE_VISIBLE, false);
        }

     
    }
    
}
