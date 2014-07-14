package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.utils.*;
import fr.curie.BiNoM.pathways.utils.WeightGraphStructure;

/** 
 * Common dialogs to input data
 * - reach and compute fade
 * - score threshold
 * - model type
 * - sources and targets for influence with preselected nodes
 * - only sources for reach area
 * Common display functions for arrays
 *  
 * @author Daniel.Rovera@curie.fr
 */
public class ModelMenuUtils extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	protected ArrayList<Integer> srcDialog;
	protected ArrayList<Integer> tgtDialog;
	final String preselectAttrib="PRESELECTED";
	final int notSelected=0;
	final int selectedAsSrc=1;
	final int selectedAsTgt=2;
	final int selectAsSrcTgt=3;
	boolean ifMultiPath=false;
	final String pathModelAttrib="PATH_MODEL";
	Double reach;
	final double reachDefault=5.0;
	final String reachAttrib="INFLUENCE_REACH";
	double fade;
	Double threshold;
	final double thresholdDefault=0.05;
	final String thresholdAttrib="SCORE_THRESHOLD";	
	final public static String errorWeigth="Influence weigth attribute not rigthly updated\r\nCannot display influence";
	protected String addTitle(String title){
		title=title+"/"+reach+"/";
		if(ifMultiPath) title=title+"MultiPath"; else title=title+"MonoPath";
		return title;
	}
	protected void updatePathModel(){
		String model=Cytoscape.getNetworkAttributes().getStringAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),pathModelAttrib);
		ifMultiPath=false;
		if(model==null){
			if(!inputPathModel()) Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),pathModelAttrib,"MONO");				
		}else if(model.equals("MULTI")) ifMultiPath=true; 
	}
	protected boolean inputPathModel(){
		AlternativeDialog dialog=new AlternativeDialog(Cytoscape.getDesktop(),ChooseModelType.title,"Click on choosen path exploring mode","Mono Path Mode","Multi Path Mode");
		int option=dialog.getOption();
		if(option==1){
			ifMultiPath=false;
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),pathModelAttrib,"MONO");
			return true;
		}
		if(option==2){
			ifMultiPath=true;
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),pathModelAttrib,"MULTI");
			return true;
		}
		return false;
	}
	protected void updateFade(){
		reach=Cytoscape.getNetworkAttributes().getDoubleAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib);
		if(reach==null) inputReach();	
		fade=Math.exp(Math.log(0.05)/reach);
	}
	protected void inputReach(){
		String input=JOptionPane.showInputDialog(Cytoscape.getDesktop(),"Input the number of paths beyond the signal is under 5%","Parameter of signal fading",JOptionPane.QUESTION_MESSAGE);
		try{
			reach=Double.valueOf(input);
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib,reach);
		}
		catch(Exception e){
			reach=reachDefault;
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Wrong input, default value "+reach,"Warning",JOptionPane.ERROR_MESSAGE);
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib,reachDefault);			
		}	
	}
	protected int maxDepth(){
		return (reach.intValue()*2+1);
	}
	protected void updateThreshold(){
		threshold=Cytoscape.getNetworkAttributes().getDoubleAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),thresholdAttrib);
		if(threshold==null) inputThreshold();	
	}
	protected void inputThreshold(){
		String input=JOptionPane.showInputDialog(Cytoscape.getDesktop(),"Input the score threshold TS\r\nIf activity<-TS, as inhibited\r\nIf activity>+TS, as activated","Threshold for computing score",JOptionPane.QUESTION_MESSAGE);
		try{
			threshold=Math.abs(Double.valueOf(input));
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),thresholdAttrib,threshold);
		}
		catch(Exception e){
			threshold=thresholdDefault;
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Wrong input, default value "+threshold,"Warning",JOptionPane.ERROR_MESSAGE);
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),thresholdAttrib,thresholdDefault);
		}	
	}
	protected void getAllSrcAllTgt(WeightGraphStructure wgs){
		srcDialog=new ArrayList<Integer>();
		tgtDialog=new ArrayList<Integer>();
		for(int i=0;i<wgs.nodes.size();i++) srcDialog.add(i);
		for(int i=0;i<wgs.nodes.size();i++) tgtDialog.add(i);	
	}
	protected void getSrcAllTgt(WeightGraphStructure wgs,String title){
		srcDialog=new ArrayList<Integer>();
		tgtDialog=new ArrayList<Integer>();
		for(int i=0;i<wgs.nodes.size();i++) tgtDialog.add(i);
		String[] nodeNames=new String[wgs.nodes.size()];
		for(int i=0;i<wgs.nodes.size();i++) nodeNames[i]=wgs.nodes.get(i).getIdentifier();		
		ArrayList<String> selection=new ArrayList<String>();
		ListDialog listBox=new ListDialog(Cytoscape.getDesktop(),title,"Select Start Nodes",nodeNames);
		listBox.launchDialog(selection);		
		for(String nodeId:selection) srcDialog.add(wgs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
	}
	int[] copy(ArrayList<Integer> from){
		if(from.isEmpty()) return null;
		else{
			int[] to=new int[from.size()];
			for(int i=0;i<from.size();i++) to[i]=from.get(i); 
			return to;
		}
	}
	protected void getSrcTgt(WeightGraphStructure wgs,String title){
		ArrayList<Integer> preSrc=new ArrayList<Integer>();
		ArrayList<Integer> preTgt=new ArrayList<Integer>();
		String[] attrNames=Cytoscape.getNodeAttributes().getAttributeNames();
		int i;
		for(i=0;i<attrNames.length;i++) if(preselectAttrib.equals(attrNames[i])) break;		
		if((i<attrNames.length)&&(Cytoscape.getNodeAttributes().getType(attrNames[i])==CyAttributes.TYPE_INTEGER)){
			for(int n=0;n<wgs.nodes.size();n++){
				Integer s=Cytoscape.getNodeAttributes().getIntegerAttribute(wgs.nodes.get(n).getIdentifier(),preselectAttrib);			
				if(s!=null) switch(s){
				case selectedAsSrc:preSrc.add(n);break;
				case selectedAsTgt:preTgt.add(n);break;
				case selectAsSrcTgt:preSrc.add(n);preTgt.add(n);
				}		
			}
		}
		srcDialog=new ArrayList<Integer>();
		tgtDialog=new ArrayList<Integer>();
		String[] nodeNames=new String[wgs.nodes.size()];
		int n=0;for(CyNode node:wgs.nodes) nodeNames[n++]=node.getIdentifier();		
		ArrayList<String> selectNode1=new ArrayList<String>();
		ArrayList<String> selectNode2=new ArrayList<String>();
		TwoListDialog dialog=new TwoListDialog(Cytoscape.getDesktop(),title,"Select nodes as","Source","Target",nodeNames,nodeNames);
		dialog.launchDialog(copy(preSrc),copy(preTgt),selectNode1,selectNode2);
		for(String nodeId:selectNode1) srcDialog.add(wgs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
		for(String nodeId:selectNode2) tgtDialog.add(wgs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
	}
	String matrixToTxt(WeightGraphStructure wgs,Object[][] matrix){
		StringBuffer txt=new StringBuffer();
		for(int s=0;s<srcDialog.size();s++) {
			txt.append("\t");
			txt.append(wgs.nodes.get(srcDialog.get(s)).getIdentifier());
		}
		txt.append("\r\n");
		for(int t=0;t<tgtDialog.size();t++){
			txt.append(wgs.nodes.get(tgtDialog.get(t)).getIdentifier());
			for(int s=0;s<srcDialog.size();s++){
				txt.append("\t");
				txt.append(matrix[tgtDialog.get(t)][srcDialog.get(s)]);
			}
			txt.append("\r\n");
		}
		return txt.toString();
	}
	String matrixToList(WeightGraphStructure wgs,Double[][] matrix){
		StringBuffer txt=new StringBuffer("Source\tTarget\tInfluence\r\n");;
		for(int s=0;s<srcDialog.size();s++)for(int t=0;t<tgtDialog.size();t++){			
			txt.append(wgs.nodes.get(srcDialog.get(s)).getIdentifier());txt.append("\t");
			txt.append(wgs.nodes.get(tgtDialog.get(t)).getIdentifier());txt.append("\t");
			if(matrix[tgtDialog.get(t)][srcDialog.get(s)].isNaN()) txt.append(0.0);
			else txt.append(matrix[tgtDialog.get(t)][srcDialog.get(s)]);
			txt.append("\r\n");
		}
		return txt.toString();
	}
	String matrixToFormatTxt(WeightGraphStructure wgs,Double[][] matrix,String decFormatLibel){
		DecimalFormat decFormat;
		if(decFormatLibel==null) decFormat=null; else decFormat=new DecimalFormat(decFormatLibel);
		StringBuffer txt=new StringBuffer();
		for(int s=0;s<srcDialog.size();s++) {
			txt.append("\t");
			txt.append(wgs.nodes.get(srcDialog.get(s)).getIdentifier());
		}
		txt.append("\r\n");
		for(int t=0;t<tgtDialog.size();t++){
			txt.append(wgs.nodes.get(tgtDialog.get(t)).getIdentifier());
			for(int s=0;s<srcDialog.size();s++){
				txt.append("\t");
				if(matrix[tgtDialog.get(t)][srcDialog.get(s)].isNaN()){
					if(decFormat==null) txt.append(0.0);else txt.append("nc");
				}else{
					if(decFormat==null) txt.append(matrix[tgtDialog.get(t)][srcDialog.get(s)]);else txt.append(decFormat.format(matrix[tgtDialog.get(t)][srcDialog.get(s)]));
				}
			}
			txt.append("\r\n");
		}
		return txt.toString();
	}
	public void actionPerformed(ActionEvent e){}
}
