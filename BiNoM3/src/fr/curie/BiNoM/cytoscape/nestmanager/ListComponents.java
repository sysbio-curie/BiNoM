package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
/**
 * List component in current network and nests from species described as BiNoM syntax
 * Network generally converted from a CellDesigner file or URL
 * Species are identified by attribute CELLDESIGNER_SPECIES
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ListComponents extends AbstractCyAction {
	
	public ListComponents(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="List Components of Species in Network and Modules";
	static final char separator=':'; 
	final static String end="|@";
	final static String notUsed="()'";
	static String specieAttr="CELLDESIGNER_SPECIES";
	private void addComponent(String component,HashMap<String,Integer> components){
		Integer sum=components.get(component);
		if(sum==null) components.put(component,1);else components.put(component,sum+1);
	}
	private HashMap<String,Integer> listComponents(CyNetwork network){
		HashMap<String,Integer> components=new HashMap<String,Integer>();
		for(CyNode node:network.getNodeList()){
			
			
			if(network.getRow(node).get(specieAttr, String.class)==null) 
				continue;
			
			StringBuffer nn=new StringBuffer(network.getRow(node).get(CyNetwork.NAME, String.class));
			StringBuffer sb=new StringBuffer();
			Boolean ok=true;
			for(int c=0;c<nn.length();c++){
				if(nn.charAt(c)==separator){
					addComponent(new String(sb),components);
					sb=new StringBuffer();
					ok=true;
				}else{
					if(end.indexOf(nn.charAt(c))>-1) ok=false;
					if(ok&&(notUsed.indexOf(nn.charAt(c))==-1)) sb.append(nn.charAt(c));
				}	
			}
			addComponent(new String(sb),components);
		}
		return components;
	}
	private String listNetworkComponents(CyNetwork network, String title){
		HashMap<String,Integer> components=listComponents(network);
		ArrayList<Map.Entry<String,Integer>> componentList=new ArrayList<Map.Entry<String,Integer>>();
		componentList.addAll(components.entrySet());	
		Collections.sort(componentList,new Comparator<Map.Entry<String,Integer>>(){
			public int compare(Map.Entry<String,Integer> me1,Map.Entry<String,Integer> me2){return (me2.getValue()-me1.getValue());}});
		String txt="";
		for(Map.Entry<String,Integer>me:componentList)txt=txt+"\r\n"+title+"\t"+me.getKey()+"\t"+me.getValue();
		return txt;
	}
	public void actionPerformed(ActionEvent arg0){
		CyNetwork network=Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		ArrayList<CyNode> nestList=new ArrayList<CyNode>();
		for(CyNode node:NestUtils.getNodeList(network)) 
			if(node.getNetworkPointer()!=null) 
				nestList.add(node);
		String text="Network\tComponents\tInHowManySpecies";
		for(CyNode nest:nestList) text=text+listNetworkComponents(nest.getNetworkPointer(),network.getRow(nest).get(CyNetwork.NAME, String.class));
		text=text+listNetworkComponents(network,network.getRow(network).get(CyNetwork.NAME, String.class));		
		new TextBox(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),title,text).setVisible(true);			
	}
}