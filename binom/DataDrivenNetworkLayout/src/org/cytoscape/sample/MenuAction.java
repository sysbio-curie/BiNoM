package org.cytoscape.sample;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyColumn;
import org.cytoscape.io.write.* ;
import org.cytoscape.io.CyFileFilter;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import java.util.*;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        
        List<CyNode> nodes = mynetwork.getNodeList();
        CyNode mynode = nodes.get(50);
        CyRow row = mynetwork.getRow(mynode);
        String name = mynetwork.getRow(mynode).get("name", String.class);
        List<CyRow> mylistrows = mytable.getAllRows(); 
        Collection<CyColumn> mycolumns = mytable.getColumns();
        Map<String,Object> values = row.getAllValues();
        //CyTable mycytable=row.getTable(); 
        System.out.println(count);
        System.out.println(mynode);
        System.out.println(name);
        System.out.println(values);
        for (CyNode node : network.getNodeList()) {
            if (network.getNeighborList(node, CyEdge.Type.ANY).isEmpty())
                networkView.getNodeView(node).setVisualProperty(
                    BasicVisualLexicon.NODE_VISIBLE, false);
        }

     
    }
    
}