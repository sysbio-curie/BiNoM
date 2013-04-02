package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;

public class testRemoveEditPoints {

	public static void main(String[] args) {
		try{
			
			String file = "c:/datas/binomtest/testAND1";
			//String file = "c:/datas/binomtest/M-Phase2";
			//String file = "c:/datas/binomtest/mergedDraft_rew_mrn";
			//String file = "C:/Datas/Basal/DNArepair_CCmap_layout work_15122010/Map Layots work versions/CellCycle_DNArepair_New_15_12_2010_1";
			//String file = "C:/Datas/Basal/DNArepair_CCmap_layout work_15122010/Map Layots work versions/BER_NER_MMR_MASTER";
			
			SbmlDocument cd1 = CellDesigner.loadCellDesigner(file+".xml");
			removeEditPoints(cd1);
			assignColorToReactions(cd1,"ffcccccc");
			CellDesigner.saveCellDesigner(cd1,file+"_rep.xml");
			System.exit(0);

			for(int i=0;i<cd1.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			//for(int i=0;i<32;i++){
				ReactionDocument.Reaction r = cd1.getSbml().getModel().getListOfReactions().getReactionArray(i);
				
				String reactionType = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
				
				System.out.println((i+1)+") Reaction "+r.getId()+" "+reactionType);
				if(r.getAnnotation().getCelldesignerConnectScheme()!=null)
					r.getAnnotation().unsetCelldesignerConnectScheme();
				if(reactionType.equals("DISSOCIATION")||reactionType.equals("HETERODIMER_ASSOCIATION")){
					String ep = Utils.getValue(r.getAnnotation().getCelldesignerEditPoints());
					StringTokenizer st =  new StringTokenizer(ep," ");
					ep = st.nextToken();
					ep = "0.5,0.5";
					Utils.setValue(r.getAnnotation().getCelldesignerEditPoints(), ep);
					r.getAnnotation().getCelldesignerEditPoints().setNum0("0");
					r.getAnnotation().getCelldesignerEditPoints().setNum1("0");
					r.getAnnotation().getCelldesignerEditPoints().setNum2("0");
					
				}else
				if(r.getAnnotation().getCelldesignerEditPoints()!=null)
					r.getAnnotation().unsetCelldesignerEditPoints();
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
					CelldesignerBaseReactantDocument.CelldesignerBaseReactant cbr = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
					if(cbr.getCelldesignerLinkAnchor()!=null)
						cbr.unsetCelldesignerLinkAnchor();
				}
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
					CelldesignerBaseProductDocument.CelldesignerBaseProduct cbr = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
					if(cbr.getCelldesignerLinkAnchor()!=null)
						cbr.unsetCelldesignerLinkAnchor();
				}
				if(r.getAnnotation().getCelldesignerListOfModification()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
					CelldesignerModificationDocument.CelldesignerModification cmd = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String type = cmd.getType();
					System.out.println(type);
					if(type.contains("BOOLEAN_LOGIC")){
						Utils.setValue(cmd.getEditPoints(),"100,100");
					}else
					if(cmd.getCelldesignerLinkTarget()!=null){
						cmd.getCelldesignerLinkTarget().setCelldesignerLinkAnchor(null);
						cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().getPosition().toString();
						cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().setPosition(null);
					}
						if(cmd.getEditPoints()!=null)
							cmd.setEditPoints(null);
				}
			}
			
			CellDesigner.saveCellDesigner(cd1,file+"_rep.xml");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void removeEditPoints(SbmlDocument cd1) {
		try{

			HashMap<String,Pair> aliasPositions = new HashMap<String,Pair>();  
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
				CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias ca = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
				float x = Float.parseFloat(ca.getCelldesignerBounds().getX());
				float y = Float.parseFloat(ca.getCelldesignerBounds().getY());
				aliasPositions.put(ca.getId(), new Pair(x,y));
			}
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias ca = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				float x = Float.parseFloat(ca.getCelldesignerBounds().getX());
				float y = Float.parseFloat(ca.getCelldesignerBounds().getY());
				aliasPositions.put(ca.getId(), new Pair(x,y));
			}			
			
			for(int i=0;i<cd1.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			//for(int i=0;i<0;i++){
			//for(int i=0;i<32;i++){
				ReactionDocument.Reaction r = cd1.getSbml().getModel().getListOfReactions().getReactionArray(i);
				
				String reactionType = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
				
				if(r.getAnnotation().getCelldesignerConnectScheme()!=null)
					r.getAnnotation().unsetCelldesignerConnectScheme();
				if(reactionType.equals("DISSOCIATION")||reactionType.equals("HETERODIMER_ASSOCIATION")){
					System.out.println((i+1)+") Reaction "+r.getId()+" "+reactionType);
					String ep = Utils.getValue(r.getAnnotation().getCelldesignerEditPoints());
					StringTokenizer st =  new StringTokenizer(ep," ");
					ep = st.nextToken();
					ep = "0.5,0.5";
					Utils.setValue(r.getAnnotation().getCelldesignerEditPoints(), ep);
					r.getAnnotation().getCelldesignerEditPoints().setNum0("0");
					r.getAnnotation().getCelldesignerEditPoints().setNum1("0");
					r.getAnnotation().getCelldesignerEditPoints().setNum2("0");
					
				}else
				if(r.getAnnotation().getCelldesignerEditPoints()!=null)
					r.getAnnotation().unsetCelldesignerEditPoints();
				
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
					CelldesignerBaseReactantDocument.CelldesignerBaseReactant cbr = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
					if(cbr.getCelldesignerLinkAnchor()!=null)
						cbr.unsetCelldesignerLinkAnchor();
				}
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
					CelldesignerBaseProductDocument.CelldesignerBaseProduct cbr = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
					if(cbr.getCelldesignerLinkAnchor()!=null)
						cbr.unsetCelldesignerLinkAnchor();
				}
				if(r.getAnnotation().getCelldesignerListOfModification()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
					CelldesignerModificationDocument.CelldesignerModification cmd = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String type = cmd.getType();
					System.out.println(type);
					//if(cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_OR)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_AND)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_NOT)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_UNKNOWN)
					//){
					//	Utils.setValue(cmd.getEditPoints(),"0.5,0.5");
					//}else
					if(!type.contains("BOOLEAN_LOGIC")){
						if(cmd.getCelldesignerLinkTarget()!=null){
							cmd.getCelldesignerLinkTarget().setCelldesignerLinkAnchor(null);
							//cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().getPosition().toString();
							cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().setPosition(null);
						}
						if(cmd.getEditPoints()!=null){
							cmd.setEditPoints(null);
							//Utils.setValue(cmd.getEditPoints(),"0.5,0.5");
						}
					}else{
						Pair reactionCenter = getReactionCenter(r,aliasPositions);
						Vector<Pair> modifiers = new Vector<Pair>(); 
						modifiers.add(reactionCenter);
						String aliases = cmd.getAliases();
						StringTokenizer st = new StringTokenizer(aliases,",");
						while(st.hasMoreTokens()){
							String al = st.nextToken();
							modifiers.add(aliasPositions.get(al));
						}
						Pair center = calcAveragePair(modifiers);
						Utils.setValue(cmd.getEditPoints(),""+((Float)center.o1).toString()+","+((Float)center.o2).toString()+"");
						//Utils.setValue(cmd.getEditPoints(),"100,100");
					}
				}
				
				if(r.getAnnotation().getCelldesignerListOfGateMember()!=null)
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfGateMember().sizeOfCelldesignerGateMemberArray();j++){
					CelldesignerGateMemberDocument.CelldesignerGateMember cmd = r.getAnnotation().getCelldesignerListOfGateMember().getCelldesignerGateMemberArray(j);
					String type = cmd.getType();
					System.out.println(type);
					//if(cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_OR)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_AND)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_NOT)||
					//   cmd.getType().equals(CelldesignerModificationDocument.CelldesignerModification.Type.BOOLEAN_LOGIC_GATE_UNKNOWN)
					//){
					//	Utils.setValue(cmd.getEditPoints(),"0.5,0.5");
					//}else
					if(!type.contains("BOOLEAN_LOGIC")){
						if(cmd.getCelldesignerLinkTarget()!=null){
							cmd.getCelldesignerLinkTarget().setCelldesignerLinkAnchor(null);
							//cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().getPosition().toString();
							cmd.getCelldesignerLinkTarget().getCelldesignerLinkAnchor().setPosition(null);
						}
						if(cmd.getEditPoints()!=null){
							cmd.setEditPoints(null);
							//Utils.setValue(cmd.getEditPoints(),"0.5,0.5");
						}
					}else{
						Pair reactionCenter = getReactionCenter(r,aliasPositions);
						Vector<Pair> modifiers = new Vector<Pair>(); 
						modifiers.add(reactionCenter);
						String aliases = cmd.getAliases();
						StringTokenizer st = new StringTokenizer(aliases,",");
						while(st.hasMoreTokens()){
							String al = st.nextToken();
							modifiers.add(aliasPositions.get(al));
						}
						Pair center = calcAveragePair(modifiers);
						//Utils.setValue(cmd.getEditPoints(),""+((Float)center.o1).toString()+","+((Float)center.o2).toString()+"");
						Utils.setValue(cmd.getEditPoints(),"100,100");
					}
				}				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static Pair getReactionCenter(ReactionDocument.Reaction r, HashMap<String, Pair> aliasPositions){
		Pair res = null;
		Vector<Pair> participants = new Vector<Pair>(); 
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();i++)
			participants.add(aliasPositions.get(r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(i).getAlias()));
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();i++)
			participants.add(aliasPositions.get(r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(i).getAlias()));
		res = calcAveragePair(participants);
		return res;
	}
	
	public static Pair calcAveragePair(Vector<Pair> ps){
		Pair res = new Pair(0f,0f);
		for(int i=0;i<ps.size();i++){
			Float x = (Float)ps.get(i).o1;
			Float y = (Float)ps.get(i).o2;
			res.o1 = (Float)res.o1+x;
			res.o2 = (Float)res.o2+y;
		}
		res.o1 = (Float)res.o1/(float)ps.size();
		res.o2 = (Float)res.o2/(float)ps.size();
		return res;
	}
	
	public static void assignColorToReactions(SbmlDocument cd, String color){
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			r.getAnnotation().getCelldesignerLine().setColor(color);
		if(r.getAnnotation().getCelldesignerListOfModification()!=null)
			for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
				CelldesignerModificationDocument.CelldesignerModification cmd = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
				cmd.getCelldesignerLine().setColor(color);
		}
		}
	}

}
