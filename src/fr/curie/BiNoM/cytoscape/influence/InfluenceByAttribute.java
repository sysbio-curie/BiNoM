package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Influence by Active Nodes from ACTIV_IN to result attribute result in ACTIV_OUT
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceByAttribute  extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Influence by Active Nodes as Attribute";
	final String activInAttr="ACTIV_IN";
	final String activOutAttr="ACTIV_OUT";
	public void actionPerformed(ActionEvent e){	
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		String[] attrNames=Cytoscape.getNodeAttributes().getAttributeNames();
		int i;
		for(i=0;i<attrNames.length;i++) if(activInAttr.equals(attrNames[i])) break;		
		if(!((i<attrNames.length)&&(Cytoscape.getNodeAttributes().getType(attrNames[i]))==CyAttributes.TYPE_FLOATING)){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Floating Point Attribute "+activInAttr+" must be created",title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		srcDialog=new ArrayList<Integer>();
		double[] activIn=new double[wgs.nodes.size()];
		for(int s=0;s<wgs.nodes.size();s++){
			Double activ=Cytoscape.getNodeAttributes().getDoubleAttribute(wgs.nodes.get(s).getIdentifier(),activInAttr);
			if(activ!=null){
				activIn[s]=activ;
				srcDialog.add(s);
			}else activIn[s]=0.0;
		}	
		updatePathModel();
		updateFade();
		double[] activOut;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			activOut=cpt.activityFromIn(fade,srcDialog,activIn);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			activOut=cpt.activityFromIn(fade,srcDialog,activIn);
		}
		JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Results in Created Attribute "+activOutAttr+" (parameters in window title)",addTitle(title),JOptionPane.INFORMATION_MESSAGE);
		for(int t=0;t<wgs.nodes.size();t++) Cytoscape.getNodeAttributes().setAttribute(wgs.nodes.get(t).getIdentifier(),activOutAttr,activOut[t]);
	}
}

