package src.fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.Semantics;
import cytoscape.util.CytoscapeAction;
/**
 *  List edges from initial network linking nodes in nests
 *  Result in a text box
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ListEdgesLinkingNests extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="List Edges Linking Modules";
	final String srcNotInNest="\t_Src_Out_Modules";
	final String tgtNotInNest="\t_Tgt_Out_Modules";
	final String inside="_Inside_Module:\t";
	StringBuffer text;
	private CyNetwork currentNW,referenceNW;
	private HashMap<CyNode,HashSet<CyNode>> nodeToNests;
	public void actionPerformed(ActionEvent e){				
		currentNW=Cytoscape.getCurrentNetwork();
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		String selected=NestUtils.selectOneNetwork(networks,title,"Select a network as reference");		
		if (selected==null) return; else referenceNW=networks.get(selected);
		text=new StringBuffer("Edge\tInteraction\tSrc_Module\tTgt_Module\r\n");	
		nodeToNests=NestUtils.doNodeToNests(currentNW);
		for(CyEdge edge:NestUtils.getEdgeList(referenceNW)){
			String estr=edge.getIdentifier()+"\t"+Cytoscape.getEdgeAttributes().getStringAttribute(edge.getIdentifier(),Semantics.INTERACTION);
			HashSet<CyNode> srcNests=nodeToNests.get(edge.getSource());			
			HashSet<CyNode> tgtNests=nodeToNests.get(edge.getTarget());
			if((srcNests!=null)&(tgtNests!=null)){			
				for(CyNode srcNest:srcNests)for(CyNode tgtNest:tgtNests){
					text.append(estr);text.append("\t");
					if(srcNest==tgtNest){
						text.append(inside);
						text.append(tgtNest.getIdentifier());	
					}else{
						text.append(srcNest.getIdentifier());text.append("\t");
						text.append(tgtNest.getIdentifier());					
					}
					text.append("\r\n");
				}
				continue;
			}	
			if((srcNests==null)&(tgtNests!=null)){
				for(CyNode tgtNest:tgtNests){
					text.append(estr);
					text.append(srcNotInNest);text.append("\t");
					text.append(tgtNest.getIdentifier());text.append("\r\n");	
				}
				continue;				}
			if((srcNests!=null)&(tgtNests==null)){
				for(CyNode srcNest:srcNests){
					text.append(estr);text.append("\t");
					text.append(srcNest.getIdentifier());
					text.append(tgtNotInNest);text.append("\r\n");					
				}
				continue;
			}
			if((srcNests==null)&(tgtNests==null)){
				text.append(estr);text.append(srcNotInNest);text.append(tgtNotInNest);text.append("\r\n");					
			}
		}
		new TextBox(Cytoscape.getDesktop(),title,text.toString()).setVisible(true);		
	}
}
