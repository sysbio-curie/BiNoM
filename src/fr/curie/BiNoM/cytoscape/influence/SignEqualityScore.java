package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Extract data sets from attributes : input and aim from observations
 * Root names  (...ID) of attribute have the same length
 * Check if every input set correspond to one aim set by the same number
 * Are included only nodes where input activity is different from 0
 * Compare results of computing to observations using a threshold
 * Compute score on all observations
 * Display data and results numbers and kappa
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class SignEqualityScore extends ModelMenuUtils{
	final boolean OUT_LOG=false;
	private static final long serialVersionUID = 1L;
	final public static String title="Compute Score of Data Sets";
	final String aimError="Cannot match sets to aims or attribute type is not floating\r\n";
	String inputSetID= "INPUT__SET";
	String outputAimID="OUTPUT_AIM";
	int lengthID=10;
	TreeMap<String,String> setNames;
	TreeMap<String,String> aimNames;
	WeightGraphStructure wgs;
	double[][] activIn;
	double[][] activAim;
	int[] activOk;
	int[] activNo;
	int[] inhibOk;
	int[] inhibNo;
	StringBuffer txt;
	boolean getSetData(){
		setNames=new TreeMap<String,String>();
		aimNames=new TreeMap<String,String>();
		String[] attrNames=Cytoscape.getNodeAttributes().getAttributeNames();
		for(int i=0;i<attrNames.length;i++){
			if(Cytoscape.getNodeAttributes().getType(attrNames[i])!=CyAttributes.TYPE_FLOATING) continue;
			if(attrNames[i].length()>lengthID){				
				if(attrNames[i].substring(0,lengthID).equals(inputSetID)) setNames.put(attrNames[i].substring(lengthID),attrNames[i]);
				if(attrNames[i].substring(0,lengthID).equals(outputAimID)) aimNames.put(attrNames[i].substring(lengthID),attrNames[i]);
			}		
		}
		if(setNames.isEmpty()) return false;
		Iterator<String> setIt=setNames.keySet().iterator();
		Iterator<String> aimIt=aimNames.keySet().iterator();
		while(setIt.hasNext()&aimIt.hasNext()) if(!setIt.next().equals(aimIt.next()))return false;		
		activIn=new double[setNames.keySet().size()][];			
		activAim=new double[aimNames.keySet().size()][];
		int is=0;
		for(String key:setNames.keySet()){
			activIn[is]=new double[wgs.nodes.size()];
			for(int n=0;n<wgs.nodes.size();n++){
				Double d=Cytoscape.getNodeAttributes().getDoubleAttribute(wgs.nodes.get(n).getIdentifier(),setNames.get(key));			
				if(d!=null){
					activIn[is][n]=d;
					if(d!=0.0) srcDialog.add(n);
				}else activIn[is][n]=0.0;
			}
			activAim[is]=new double[wgs.nodes.size()];
			for(int n=0;n<wgs.nodes.size();n++){
				Double d=Cytoscape.getNodeAttributes().getDoubleAttribute(wgs.nodes.get(n).getIdentifier(),aimNames.get(key));			
				if(d!=null)	activAim[is][n]=d;else activAim[is][n]=0.0;
			}
			is++;
		}
		activOk=new int[setNames.keySet().size()];
		activNo=new int[setNames.keySet().size()];
		inhibOk=new int[setNames.keySet().size()];
		inhibNo=new int[setNames.keySet().size()];		
		return true;
	}
	void displayScore(){				
		txt.append("Set\tInput Set Size\tOutput Aim Size\tSign Score\tActive Ok\tInhibit Ok\tKappa\r\n");
		int activOkSum=0;
		int activNoSum=0;
		int inhibOkSum=0;
		int inhibNoSum=0;
		Iterator<String> iter=setNames.keySet().iterator();
		for(int s=0;s<setNames.keySet().size();s++){
			stateOkNo(s);
			activOkSum=activOkSum+activOk[s];
			activNoSum=activNoSum+activNo[s];
			inhibOkSum=inhibOkSum+inhibOk[s];
			inhibNoSum=inhibNoSum+inhibNo[s];
			txt.append(iter.next());txt.append("\t");
			int setNb=0;for(int n=0;n<wgs.nodes.size();n++) if(activIn[s][n]!=0.0) setNb++;
			txt.append(setNb);txt.append("\t");
			txt.append(activOk[s]+activNo[s]+inhibOk[s]+inhibNo[s]);txt.append("\t");
			txt.append(activOk[s]+inhibOk[s]);txt.append("\t");
			txt.append(activOk[s]);txt.append("\t");
			txt.append(inhibOk[s]);txt.append("\t");
			txt.append(kappa(activOk[s],activNo[s],inhibOk[s],inhibNo[s]));txt.append("\r\n");
		}
		txt.append("\t\t");
		txt.append(activOkSum+activNoSum+inhibOkSum+inhibNoSum);txt.append("\t");
		txt.append(activOkSum+inhibOkSum);txt.append("\t");
		txt.append(activOkSum);txt.append("\t");
		txt.append(inhibOkSum);txt.append("\t");
		txt.append(kappa(activOkSum,activNoSum,inhibOkSum,inhibNoSum));
	}
	double kappa(int activOk,int activNo,int inhibOk, int inhibNo){
		double n=activOk+activNo+inhibOk+inhibNo;
		double ok=(double)(activOk+inhibOk)/n;
		double rk=(double)((activOk+activNo)*(activOk+inhibNo)+(inhibOk+activNo)*(inhibOk+inhibNo))/n/n;
		return (ok-rk)/(1.0-rk);
	}
	void stateOkNo(int set){
		activOk[set]=0;
		activNo[set]=0;
		inhibOk[set]=0;
		inhibNo[set]=0;
		double[] activOut;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			activOut=cpt.activityFromIn(fade,srcDialog,activIn[set]);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			activOut=cpt.activityFromIn(fade,srcDialog,activIn[set]);
		}		
		for(int n=0;n<wgs.nodes.size();n++){
			if(activAim[set][n]>0){
				if(activOut[n]>threshold) activOk[set]++; else activNo[set]++; 
			}else{
				if(activAim[set][n]<0){
					if(activOut[n]<-threshold) inhibOk[set]++; else inhibNo[set]++;
				}
			}
		}
		if(OUT_LOG){
			for(int n=0;n<activAim[set].length;n++){
				if(activAim[set][n]!=0){
					txt.append(wgs.nodes.get(n).getIdentifier());
					txt.append("\t");
					txt.append(activAim[set][n]);
					txt.append("\t");
					txt.append(activOut[n]);
					txt.append("\r\n");
				}
			}
		}
	}
	double getKappa(){
		int activOkSum=0;
		int activNoSum=0;
		int inhibOkSum=0;
		int inhibNoSum=0;
		for(int s=0;s<setNames.keySet().size();s++){
			stateOkNo(s);
			activOkSum=activOkSum+activOk[s];
			activNoSum=activNoSum+activNo[s];
			inhibOkSum=inhibOkSum+inhibOk[s];
			inhibNoSum=inhibNoSum+inhibNo[s];			
		}
		return kappa(activOkSum,activNoSum,inhibOkSum,inhibNoSum);
	}
	void changeWeight(int edge){};
	double weightValue(int edge){return wgs.weights.get(edge);}
	public String title(){return null;}
	void init(){
		srcDialog=new ArrayList<Integer>();
		tgtDialog=new ArrayList<Integer>();
		wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		updateThreshold();		
		txt=new StringBuffer();
		txt.append("Reach=");txt.append(reach);	
		txt.append("/ScoreThreshold=");txt.append(threshold);
		if(ifMultiPath) txt.append("/MultiPath\r\n"); else txt.append("/MonoPath\r\n");
	}
	public void actionPerformed(ActionEvent e){
		init();
		if(getSetData()) displayScore(); else txt.append(aimError);
		new TextBox(Cytoscape.getDesktop(),title,txt.toString()).setVisible(true);	
	}
}
