package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.nestmanager.NestUtils;
import fr.curie.BiNoM.cytoscape.utils.ComboBoxes;
/**
 * Update Influence Attribute WEIGHT at +1 or -1
 * from an attribute, generally interaction
 * 3 possible value for attribute
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class UpdateInfluenceAttrib extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	final public static String title="Update Weigth Influence Attribute";
	String attribName="WEIGHT";
	String[] label={"Attribute to use for influence",
			"Attribute 1 for activation",
			"Attribute 2 for activation",
			"Attribute 3 for activation",
			"Attribute 1 for inhibition",
			"Attribute 2 for inhibition",
			"Attribute 3 for inhibition"};
	String nothing="Nothing";
	String selected="interaction";
	CyNetwork network;
	ArrayList<String> fields;	
	ArrayList<ArrayList<String>> datas;
	ArrayList<String> data;
	void fillAttributesValues(){
		 fields=new ArrayList<String>();	
		 datas=new ArrayList<ArrayList<String>>();
		 CyAttributes attrib=Cytoscape.getEdgeAttributes();
		 String[] attrNames=attrib.getAttributeNames();
		 for(int i=0;i<attrNames.length;i++) if(attrib.getType(attrNames[i])==CyAttributes.TYPE_STRING){
			 fields.add(attrNames[i]);
			 datas.add(new ArrayList<String>());
		 }
		 for(int i=0;i<fields.size();i++){
			 for(CyEdge edge:NestUtils.getEdgeList(network)){
				 String attrStr=attrib.getStringAttribute(edge.getIdentifier(),fields.get(i));
				 if(!datas.get(i).contains(attrStr)&&(attrStr!=null)) datas.get(i).add(attrStr); 
			 }
		 }
		 for(int i=0;i<datas.size();i++){
			 Collections.sort(datas.get(i));
			 datas.get(i).add(0,nothing);
		 }
	}
	int update(){
		CyAttributes attrib=Cytoscape.getEdgeAttributes();
		Double unknown=new Double(0.0);
		Double activ=new Double(1.0);
		Double inhib=new Double(-1.0);
		int unknownNb=0;
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			String attrStr=attrib.getStringAttribute(edge.getIdentifier(),data.get(0));
			int ai=data.indexOf(attrStr);
			if(ai==-1){
				unknownNb++;
				attrib.setAttribute(edge.getIdentifier(),attribName,unknown);
			}
			else{
				if(ai<((label.length+1)/2)) attrib.setAttribute(edge.getIdentifier(),attribName,activ);
				else attrib.setAttribute(edge.getIdentifier(),attribName,inhib);			 
			}
		}
		return unknownNb;
	}
	public void actionPerformed(ActionEvent e){
		network=Cytoscape.getCurrentNetwork();
		fillAttributesValues();
		data=new ArrayList<String>();
		ComboBoxes dialog=new ComboBoxes(Cytoscape.getDesktop(),title,label,fields,selected,datas);
		if(dialog.launchDialog(data)){
			int unknown=update();
			if(unknown!=0) JOptionPane.showMessageDialog(Cytoscape.getDesktop(),unknown+" edges with unknown influence",
					unknown+" edges taken as disconnected in influence evaluation",JOptionPane.WARNING_MESSAGE);
		}
	}
}
