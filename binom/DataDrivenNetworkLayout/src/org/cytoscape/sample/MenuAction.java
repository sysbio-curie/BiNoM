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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class MenuAction extends AbstractCyAction {
    private final CyAppAdapter adapter;

    public MenuAction(CyAppAdapter adapter) {
        super("DDL",
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
		Set<String> keys = values.keySet(); //all colon names



		System.out.println(count);
		//System.out.println(mynode);
		//System.out.println(name);
		//System.out.println(keys);

		Set<String> set_attributes = new HashSet<String>();
		for (CyNode node : mynetwork.getNodeList()) {
			//if (network.getNeighborList(node, CyEdge.Type.ANY).isEmpty())
			//networkView.getNodeView(node).setVisualProperty(
			//BasicVisualLexicon.NODE_VISIBLE, false);
			for (String col: values.keySet()){
				if (col.startsWith("EW")){
					set_attributes.add(col);
					//System.out.println(col);
				}
			}
			//String myname = mynetwork.getRow(node).get("name", String.class);
			//System.out.println(myname);
		}
		Object [ ] [ ] matrix = new Object [mytable.getRowCount()] [set_attributes.size()] ; 
		Map<String,Integer> map_index = new HashMap<String,Integer>();
		int ct = 0;
		for (String att : set_attributes){
			map_index.put(att,ct);
			ct++;
		}
		//System.out.println(map_index);
		//System.out.println(set_attributes.size());

		int ct2=0;
		for (CyNode node : mynetwork.getNodeList()) {
			ct2++;
			//System.out.println(ct2);
			for (String col: values.keySet()){
				if (map_index.containsKey(col)){
					int index2=map_index.get(col);
					//System.out.println(index2);
					Object value = mynetwork.getRow(node).getAllValues().get(col);
					if (value == null){
						value=0;
					}
					//System.out.println(ct2);
					//System.out.println(index2);
					matrix[ct2][index2]=value; 
					//System.out.println(value);
				}
			}
		}
		//System.out.println(matrix);
	}
}