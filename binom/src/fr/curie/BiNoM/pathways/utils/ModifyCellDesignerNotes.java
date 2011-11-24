package fr.curie.BiNoM.pathways.utils;

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.utils.*;

import java.io.*;
import java.util.*;

public class ModifyCellDesignerNotes {

	public SbmlDocument sbmlDoc = null;
	public String comments = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
		String nameCD = "c:/datas/binomtest/components40";
		String nameNotes = "c:/datas/basal/comm_temp.txt";
		mn.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		mn.comments = Utils.loadString(nameNotes);
		mn.ModifyCellDesignerNotes();
		
		CellDesigner.saveCellDesigner(mn.sbmlDoc, nameCD+"_notes.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void ModifyCellDesignerNotes() throws Exception{
		
		Vector<String> keys = new Vector<String>();
		Vector<String> noteAdds = new Vector<String>();
		LineNumberReader lr = new LineNumberReader(new StringReader(comments));
		String s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			keys.add(st.nextToken());
			noteAdds.add(st.nextToken());
		}
		
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = note+" "+noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
			for(int j=0;j<keys.size();j++){
				if(note.contains(keys.get(j))){
					note = note+" "+noteAdds.get(keys.indexOf(keys.get(j)));
					p.setCelldesignerNotes(null);
					  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
					  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
					  XmlString xs = XmlString.Factory.newInstance();
					  xs.setStringValue(note);
					  b.set(xs);
				}
			}
		}

		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = note+" "+noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
			for(int j=0;j<keys.size();j++){
				if(note.contains(keys.get(j))){
					note = note+" "+noteAdds.get(keys.indexOf(keys.get(j)));
					p.setCelldesignerNotes(null);
					  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
					  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
					  XmlString xs = XmlString.Factory.newInstance();
					  xs.setStringValue(note);
					  b.set(xs);
				}
			}
		}		

		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA p = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String key = p.getId();
			String note = "";
			if(p.getCelldesignerNotes()!=null)
				note = Utils.getValue(p.getCelldesignerNotes());
			if(keys.contains(key)){
				note = note+" "+noteAdds.get(keys.indexOf(key));
				p.setCelldesignerNotes(null);
				  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
			for(int j=0;j<keys.size();j++){
				if(note.contains(keys.get(j))){
					note = note+" "+noteAdds.get(keys.indexOf(keys.get(j)));
					p.setCelldesignerNotes(null);
					  CelldesignerNotesDocument.CelldesignerNotes pnotes = p.addNewCelldesignerNotes();
					  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
					  XmlString xs = XmlString.Factory.newInstance();
					  xs.setStringValue(note);
					  b.set(xs);
				}
			}
		}		

		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r =  sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String key = r.getId();
			String note = "";
			if(r.getNotes()!=null)
				note = Utils.getValue(r.getNotes());
			if(keys.contains(key)){
				note = note+" "+noteAdds.get(keys.indexOf(key));
				r.setNotes(null);
				  NotesDocument.Notes pnotes = r.addNewNotes();
				  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
				  XmlString xs = XmlString.Factory.newInstance();
				  xs.setStringValue(note);
				  b.set(xs);
			}
			for(int j=0;j<keys.size();j++){
				if(note.contains(keys.get(j))){
					note = note+" "+noteAdds.get(keys.indexOf(keys.get(j)));
					r.setNotes(null);
					  NotesDocument.Notes pnotes = r.addNewNotes();
					  BodyDocument.Body b = pnotes.addNewHtml().addNewBody();
					  XmlString xs = XmlString.Factory.newInstance();
					  xs.setStringValue(note);
					  b.set(xs);
				}
			}
		}		
		
		
	}
}
