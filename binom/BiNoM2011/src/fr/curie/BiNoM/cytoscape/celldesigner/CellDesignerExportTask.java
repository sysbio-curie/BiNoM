/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.celldesigner;

import fr.curie.BiNoM.cytoscape.lib.*;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import java.io.*;

import cytoscape.task.ui.JTaskConfig;

import edu.rpi.cs.xgmml.*;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;
import java.io.InputStream;

import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.HashMap;

import java.io.File;
import java.net.URL;
import giny.view.NodeView;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.*;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;

import com.lowagie.text.Entities;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerImportTask;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;

import fr.curie.BiNoM.pathways.utils.Pair;
import fr.curie.BiNoM.pathways.utils.Utils;

import javax.swing.JOptionPane;

public class CellDesignerExportTask implements Task {
    private TaskMonitor taskMonitor;
    private CyNetwork cyNetwork;
    private File file;
    private static java.util.HashMap network_bw = new java.util.HashMap();
    private CellDesignerExportToFileDialog.CellDesignerExportToFileOptions options = null;    
    

    public CellDesignerExportTask(File file, CellDesignerExportToFileDialog.CellDesignerExportToFileOptions _options) {
    	this.file = file;
    	this.options = _options;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Export CellDesigner " + file.getPath();
    }

    public CyNetwork getCyNetwork() {
	return cyNetwork;
    }

    public void run() {
	try {
            CyNetwork network = Cytoscape.getCurrentNetwork();

	    SbmlDocument sbml = CellDesignerSourceDB.getInstance().getCellDesigner(network);
	    if (sbml != null) {
		// celldesigner -> celldesigner
		Vector species = new Vector();
		Vector speciesAliases = new Vector();
		Vector reactions = new Vector();
		Vector degraded = new Vector();

		findSBMLSpeciesAndReactions(sbml, species, speciesAliases,
					    reactions, degraded);
		
		if(options.putCytoscapeColorsOnSpecies)
			if(isColorChanged(network))
				assignCellDesignerColors(sbml, network);
		
		if(options.makeReactionsGrey)
			assignColorToReactions(sbml, "ff999999");
		else
			assignColorToReactions(sbml, "ff000000");

		String path = file.getAbsolutePath();

		if (path.lastIndexOf(".xml") < 0)
		    file = new File(path + ".xml");
		
		for(;;){
		
		boolean fileExists = false;
		if (file.exists()) fileExists = true;

		if (!fileExists) {
		    try {
			FileOutputStream os = new FileOutputStream(file);
			os.close();
		    }
		    catch(Exception ee) {
			JOptionPane.showMessageDialog
			    (Cytoscape.getDesktop(),
			     "Cannot open file " + file.getAbsolutePath() + " for writing");
			break;
		    }
		}

		if(fileExists&&options.mergeIfFileExists) {
                  try {
		      SbmlDocument sbmlin = CellDesigner.loadCellDesigner(file.getAbsolutePath());
                      //String err = null;
                      String err = CellDesignerToCytoscapeConverter.checkAndModifySpeciesIDs(sbmlin,sbml);
                      if(err!=null){
                        JOptionPane.showMessageDialog(Cytoscape.getDesktop(),err+"\n Merge has not been done.");
                        break;
                      }else{
                        err = CellDesignerToCytoscapeConverter.checkAndModifyEntitiesIDs(sbmlin,sbml);
                        if(err!=null){
                          JOptionPane.showMessageDialog(Cytoscape.getDesktop(),err+"\n Merge has not been done.");
                          break;
                        }else{

                        CytoscapeToCellDesignerConverter.filterIDsCompleteReactions
                            (sbml, species,
                             speciesAliases,
                             reactions,
                             degraded);

                        CellDesignerToCytoscapeConverter.mergeCellDesignerFiles(sbmlin,sbml);
                        sbml = sbmlin;
                    
                        applyCellDesignerOptions(sbml, options);
                        
    		        CellDesigner.saveCellDesigner(sbml, file.getAbsolutePath());
    		        break;
                        }
                      }
                  } catch(Exception e) {
		      System.out.println("Error in trying merging files...");
		      e.printStackTrace();
		      taskMonitor.setPercentCompleted(100);
		      taskMonitor.setStatus("Error in merging CellDesigner files " +
				  file.getAbsolutePath() + ": " + e);
                  }
                }else{
                  CytoscapeToCellDesignerConverter.filterIDsCompleteReactions
                      (sbml, species,
                       speciesAliases,
                       reactions,
                       degraded);
                  
                  applyCellDesignerOptions(sbml, options);
                  
    		  CellDesigner.saveCellDesigner(sbml, file.getAbsolutePath());
    		  break;
                }

	    }
	    }
	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error exporting CellDesigner file " +
				  file.getAbsolutePath() + ": " + e);
	}
    }

    public static void findSBMLSpeciesAndReactions(SbmlDocument sbml,
						   Vector species,
						   Vector speciesAliases,
						   Vector reactions,
						   Vector degraded) {
	CyNetworkView view = Cytoscape.getCurrentNetworkView();
	cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();

	for (Iterator i = view.getNodeViewsIterator(); i.hasNext(); ) {
	    NodeView nView = (NodeView)i.next();
	    CyNode node = (CyNode)nView.getNode();
	    Object o;

	    o = nodeAttrs.getStringAttribute(node.getIdentifier(),
					     "CELLDESIGNER_SPECIES");
	    if (o != null)
		species.add(o);

	    o = nodeAttrs.getStringAttribute(node.getIdentifier(),
					     "CELLDESIGNER_ALIAS");
	    if (o != null)
		speciesAliases.add(o);

	    o = nodeAttrs.getStringAttribute(node.getIdentifier(),
					     "CELLDESIGNER_REACTION");
	    if (o != null)
		reactions.add(o);

	}

	int cnt = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;
	for (int i = 0; i < cnt; i++) {
	    SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	    String cl = "";
	    
	    if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
	    	cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());

	    if (cl.equals("DEGRADED"))
		degraded.add(sp.getId());
	}
    }
    
    public boolean isColorChanged(CyNetwork network){
    	boolean changed = false;
    	java.util.Iterator i = network.nodesIterator();
    	cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
    	while (i.hasNext()) {
    	    CyNode node = (CyNode)i.next();
    	    String id = node.getIdentifier();
            CyNetworkView networkView = Cytoscape.getNetworkView(network.getIdentifier());
            NodeView nv = networkView.getNodeView(node);
            String nodeType = nodeAttrs.getStringAttribute(id, "CELLDESIGNER_NODE_TYPE");
            try{
                java.awt.Color colorObject = Utils.convertPaintToColor(nv.getUnselectedPaint());
                Vector map = CellDesignerVisualStyleDefinition.getInstance().getNodeColorMapping();
                for(int k=0;k<map.size();k++){
                	CellDesignerVisualStyleDefinition.ObjectMapping mapping = (CellDesignerVisualStyleDefinition.ObjectMapping)map.get(k);
                	if(mapping.getAttributeValue().equals(nodeType)){
                		if(!mapping.getMappingValue().equals(colorObject))
                			changed = true;
                	}
                	//System.out.println(mapping.getAttributeValue()+"->"+mapping.getMappingValue().toString());
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
    	}
    	return changed;
    }
    
    public void assignCellDesignerColors(SbmlDocument sbml, CyNetwork network){
    	HashMap colorTable = new HashMap();
    	java.util.Iterator i = network.nodesIterator();
    	cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
    	while (i.hasNext()) {
    	    CyNode node = (CyNode)i.next();
    	    String id = node.getIdentifier();
            CyNetworkView networkView = Cytoscape.getNetworkView(network.getIdentifier());
            NodeView nv = networkView.getNodeView(node);

            
            try{
            
            java.awt.Color colorObject = Utils.convertPaintToColor(nv.getUnselectedPaint());
            
            int rc = colorObject.getRed();
            int gc = colorObject.getGreen();
            int bc = colorObject.getBlue();
            String rcs = Integer.toHexString(rc); if(rcs.length()==1) rcs="0"+rcs;
            String gcs = Integer.toHexString(gc); if(gcs.length()==1) gcs="0"+gcs;
            String bcs = Integer.toHexString(bc); if(bcs.length()==1) bcs="0"+bcs;
            String color = "ff"+rcs+gcs+bcs;
            
            //System.out.println("NODE COLOR "+id+" : "+rcs+","+gcs+","+bcs);
 	    	String species = nodeAttrs.getStringAttribute(id, "CELLDESIGNER_SPECIES");
    	    if (species!= null)if(!species.trim().equals("")){
    	    	colorTable.put(species,color);
    	    }
    	    
            }catch(Exception e){
            	e.printStackTrace();
            }
    	    
    	    
    	}
    	CytoscapeToCellDesignerConverter.assignColorsFromTable(sbml, colorTable);
    }
    
	public void applyCellDesignerOptions(SbmlDocument sbml, CellDesignerExportToFileDialog.CellDesignerExportToFileOptions options){

	    if(options.removeLineBreakPoints){
	    	System.out.println("Removing edit points...");
	    	removeEditPoints(sbml);
	    }
		
	    if(options.changeSpeciesCoordinates){
	       	GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
	       	modifyPositionOfSpecies(sbml,gr);
	     }
	    
	    if(options.removeComplexNames){
	    	RemoveNames(sbml);
	    }
	    
	    if(options.removeResiduesNames){
	    	RemoveResidueNames(sbml);
	    }
	    
	    if(Math.abs(options.scaleFactor-1f)>1e-2){
	    	if(options.typeOfScaling == options.SCALING_POSITION)
	    		ScaleSizes(sbml, options.scaleFactor, true);
	    	if(options.typeOfScaling == options.SCALING_SHAPE)
	    		ScaleSizes(sbml, options.scaleFactor, false);
	    }
	    
	    if(options.insertHypotheticalInfluences_complexMutualInhibition){
	    	addHypotheticalInfluences(sbml,true,false);
	    }
	    if(options.insertHypotheticalInfluences_inhCatalysisProduct){
	    	addHypotheticalInfluences(sbml,false,true);
	    }
	    
	    
	}    
    
	public static void modifyPositionOfSpecies(SbmlDocument cd, GraphDocument gr){
		HashMap<String,Pair> coordinates = new HashMap<String,Pair>();
		double maxx = 0;
		double maxy = 0;
		double minx = Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		
		for(int i=0;i<gr.getGraph().getNodeArray().length;i++){
			GraphicNode n = gr.getGraph().getNodeArray(i);
			String alias = ""; 
			for(int j=0;j<n.getAttArray().length;j++){
				AttDocument.Att att = n.getAttArray(j);
				if(att.getName().equals("CELLDESIGNER_ALIAS"))
					alias = att.getValue();
			}
			double x = n.getGraphics().getX();
			double y = n.getGraphics().getY();
			if(!alias.equals("")){
				coordinates.put(alias, new Pair(x,y));
				if(x>maxx) maxx = x;
				if(y>maxy) maxy = y;
				if(x<minx) minx = x;
				if(y<miny) miny = y;				
			}
			//System.out.println(alias+"\t"+x+"\t"+y);
		}
		int width = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		int height = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		width=width-200;
		height=height-200;
		HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> aliases = new HashMap<String,CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();		
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String alid = cdal.getId();
			Pair p = coordinates.get(alid);
			if(p!=null){
				double x = (((Double)p.o1).doubleValue());
				double y = (((Double)p.o2).doubleValue());
				//x = (int)((double)width*x/(maxx-minx));
				//y = (int)((double)height*y/(maxy-miny));
				if(minx<0) x=x-minx;
				if(miny<0) y=y-miny;
				cdal.getCelldesignerBounds().setX(""+x);
				cdal.getCelldesignerBounds().setY(""+y);
			}
			aliases.put(cdal.getSpecies(), cdal);
		}
		
		HashMap<String,Vector<CelldesignerSpecies>> complexes = new HashMap<String,Vector<CelldesignerSpecies>>();
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpecies si = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			//System.out.println("Included species "+si.getId());
			if(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies()!=null){
				String csid = Utils.getText(si.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
				//System.out.println("\tComplex species "+csid);
				Vector<CelldesignerSpecies> v = complexes.get(csid);
				if(v==null)	v = new Vector<CelldesignerSpecies>();
				v.add(si);
				complexes.put(csid, v);
			}
		}

		if(maxx==minx) { minx = -(double)width/2; maxx = (double)width/2; }
		if(maxy==miny) { miny = -(double)width/2; maxy = (double)width/2; }
		//System.out.println("Width="+width+",Height="+height);
		//System.out.println("maxx="+maxx+",maxy="+maxy);
		//System.out.println("minx="+minx+",miny="+miny);
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			String alid = cdal.getId();
			String alspid = cdal.getSpecies();
			Pair p = coordinates.get(alid);
			double initx = Double.parseDouble(cdal.getCelldesignerBounds().getX());
			double inity = Double.parseDouble(cdal.getCelldesignerBounds().getY());
			if(p!=null){
			double x = (int)(((Double)p.o1).doubleValue());
			double y = (int)(((Double)p.o2).doubleValue());
			//System.out.println("In complex "+alid+" ("+x+","+y+")");			
				//x = ((double)width*x/(maxx-minx));
				//y = ((double)height*y/(maxy-miny));
				if(minx<0) x=x-minx;
				if(miny<0) y=y-miny;
				cdal.getCelldesignerBounds().setX(""+x);
				cdal.getCelldesignerBounds().setY(""+y);
			Vector<CelldesignerSpecies> v = complexes.get(alspid);
			if(v!=null){
				for(int j=0;j<v.size();j++){
					CelldesignerSpecies cs = v.get(j);
					//System.out.println("\t"+cs.getId());
					CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spal = aliases.get(cs.getId());
					double xx = Double.parseDouble(spal.getCelldesignerBounds().getX());
					double yy = Double.parseDouble(spal.getCelldesignerBounds().getY());
					spal.getCelldesignerBounds().setX(""+(xx-initx+x));
					spal.getCelldesignerBounds().setY(""+(yy-inity+y));					
				}
			}
			}
		}
	}
	
	  public static void RemoveNames(SbmlDocument sbml){
		    for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		      SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray()[i];
		      String type = Utils.getText(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
		      //System.out.print("Before "+Utils.getValue(sp.getName())+" ");
		      //sp.setName(XmlString.Factory.newInstance());
		      //System.out.println("after "+Utils.getValue(sp.getName()));
		      CelldesignerNameDocument.CelldesignerName cdn = CelldesignerNameDocument.CelldesignerName.Factory.newInstance();
		      //XmlString str = XmlString.Factory.newInstance();
		      //str.setStringValue("_");
		      //cdn.set(str);
		      //cdn.set(XmlString.Factory.newInstance());
		      if((!type.equals("SIMPLE_MOLECULE"))&&(!type.equals("ION"))&&(!type.equals("PHENOTYPE"))){
		    	  Utils.setValue(cdn, "...");
		      	  sp.getAnnotation().getCelldesignerSpeciesIdentity().setCelldesignerName(cdn);
		      }
		    }
		  }  
	
	  public static void RemoveResidueNames(SbmlDocument sbml){
		    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
		      CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
		      if(prot.getCelldesignerListOfModificationResidues()!=null){
		        for(int j=0;j<prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray().length;j++){
		          CelldesignerModificationResidueDocument.CelldesignerModificationResidue mr = prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
		          mr.setName(null);
		        }
		      }
		    }
		  }

	  public static void ScaleSizes(SbmlDocument sbml, float factor, boolean onlyDistances){
		  
		  	// first create a map of species types
		    HashMap<String,String> speciesTypes = new HashMap<String,String>();
		  	for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			      SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			      String spType = "";
			      if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
			    	  spType = Utils.getText(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			      }
			      speciesTypes.put(sp.getId(), spType);
		  	}
		  
		    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
		      CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
		      String species = csa.getSpecies();
		      String spType = "";
		      if(speciesTypes.get(species)!=null)
		    	  spType = speciesTypes.get(species); 
		      float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
		      float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
		      float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
		      float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
		      if(!onlyDistances){
		    	  if(!spType.equals("GENE"))
		    		  if(!spType.equals("PHENOTYPE")){
		    			  	csa.getCelldesignerBounds().setH(""+h*factor);
		    	  			csa.getCelldesignerBounds().setW(""+w*factor);
		    		  }
		      }
		      csa.getCelldesignerBounds().setX(""+x*factor);
		      csa.getCelldesignerBounds().setY(""+y*factor);
		    }
		    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
		      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
		      float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
		      float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
		      float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
		      float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
		      if(!onlyDistances){      
		      csa.getCelldesignerBounds().setH(""+h*factor);
		      csa.getCelldesignerBounds().setW(""+w*factor);
		      }
		      csa.getCelldesignerBounds().setX(""+x*factor);
		      csa.getCelldesignerBounds().setY(""+y*factor);
		    }
		    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
		      CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
		      System.out.println("Compartment "+csa.getId());
		      if(csa.getCelldesignerBounds()!=null){
		    	  float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
		    	  float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
		    	  csa.getCelldesignerBounds().setX(""+x*factor);
		    	  csa.getCelldesignerBounds().setY(""+y*factor);
		    	  if(csa.getCelldesignerBounds().getH()!=null)if(csa.getCelldesignerBounds().getW()!=null){
		    		  float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
		    		  float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
		    		  csa.getCelldesignerBounds().setH(""+h*factor);
		    		  csa.getCelldesignerBounds().setW(""+w*factor);
		    	  }
		      }else{
		    	  if(csa.getCelldesignerPoint()!=null){
		        	  float x = Float.parseFloat(csa.getCelldesignerPoint().getX());
		        	  float y = Float.parseFloat(csa.getCelldesignerPoint().getY());
		        	  csa.getCelldesignerPoint().setX(""+x*factor);
		        	  csa.getCelldesignerPoint().setY(""+y*factor);
		    	  }
		      }
		    }
		    for(int i=0;i<sbml.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
		        ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
		        if(r.getAnnotation().getCelldesignerListOfModification()!=null){
		        	for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
		        		CelldesignerModificationDocument.CelldesignerModification mod = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
		        		if(mod.getType().contains("BOOLEAN")){
		        			String editPoints = Utils.getValue(mod.getEditPoints());
		        			StringTokenizer st = new StringTokenizer(editPoints,","); 
		        			float x = Float.parseFloat(st.nextToken());
		        			float y = Float.parseFloat(st.nextToken());
		        			XmlString xs = XmlString.Factory.newInstance();
		        			xs.setStringValue(""+x*factor+","+y*factor);
		        			mod.setEditPoints(xs);
		        		}
		        	}
		        }
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
			if(cd.getSbml().getModel().getListOfReactions()!=null)
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
		
		
		public static void addHypotheticalInfluences(SbmlDocument cd4, boolean complexMutualInhibition, boolean inhCatalysisProduct){
			
			Vector<String> newids = new Vector<String>();
			
			for(int i=0;i<cd4.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
				ReactionDocument.Reaction r = cd4.getSbml().getModel().getListOfReactions().getReactionArray(i);
				String type = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
				
				Vector<String> listOfReactantSpecies = new Vector<String>();
				Vector<String> listOfReactantAliases = new Vector<String>();
				for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
					listOfReactantSpecies.add(r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies());
				for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
					listOfReactantAliases.add(Utils.getValue(r.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias()));

				Vector<String> listOfProductSpecies = new Vector<String>();
				Vector<String> listOfProductAliases = new Vector<String>();
				for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++)
					listOfProductSpecies.add(r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies());
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++)
					listOfProductAliases.add(Utils.getValue(r.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias()));
				
				HashMap<String, String> speciesTypes = new HashMap<String, String>();
				for(int j=0;j<cd4.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();j++){
					SpeciesDocument.Species sp = cd4.getSbml().getModel().getListOfSpecies().getSpeciesArray(j);
					speciesTypes.put(sp.getId(), Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
				}
				
				if(type.equals("HETERODIMER_ASSOCIATION")){
					if(complexMutualInhibition){
					System.out.println(r.getId()+" / "+listOfReactantSpecies.size()+" / "+listOfReactantAliases.size());
					for(int j=0;j<listOfReactantSpecies.size();j++)
						for(int k=0;k<listOfReactantSpecies.size();k++)if(k!=j){
							String id = "re_"+listOfReactantSpecies.get(j)+"_"+listOfReactantSpecies.get(k);
							if(id.equals("re_s90_s21"))
									System.out.println();
							if(newids.contains(id))
								id = id+"_";
							newids.add(id);
							addTransition(cd4, id, "NEGATIVE_INFLUENCE", "ff00aa00", listOfReactantSpecies.get(j), listOfReactantAliases.get(j), listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
						}
					}
				}else{
					if(inhCatalysisProduct){
					// Check if one of the products is a phenotype
					boolean isPhenotype = false;
					for(int j=0;j<listOfProductSpecies.size();j++)
						if(speciesTypes.get(listOfProductSpecies.get(j)).equals("PHENOTYPE"))
							isPhenotype = true;
					
					if(!isPhenotype){
					if(r.getListOfModifiers()!=null)
					for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
						ModifierSpeciesReferenceDocument.ModifierSpeciesReference msr = r.getListOfModifiers().getModifierSpeciesReferenceArray(j);
						String mod_species = msr.getSpecies();
						String mod_alias = Utils.getValue(msr.getAnnotation().getCelldesignerAlias());
						
						int sign_of_influence = 0;
						String typeMod = "";
						for(int k=0; k<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();k++){
							CelldesignerModificationDocument.CelldesignerModification cm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k);
							if(cm.getModifiers().equals(mod_species)){
								typeMod = cm.getType();
							}
						}
						if(typeMod.equals("CATALYSIS")) sign_of_influence = +1;
						if(typeMod.equals("INHIBITION")) sign_of_influence = -1;
						if(typeMod.equals("UNKNOWN_CATALYSIS")) sign_of_influence = +1;
						if(typeMod.equals("UNKNOWN_INHIBITION")) sign_of_influence = -1;
						if(typeMod.equals("PHYSICAL_STIMULATION")) sign_of_influence = +1;
											
						if(sign_of_influence!=0)
						for(int k=0;k<listOfReactantSpecies.size();k++){
							String reactType = speciesTypes.get(listOfReactantSpecies.get(k));
							if(!reactType.equals("GENE")){
							String id = "re_"+mod_species+"_"+listOfReactantSpecies.get(k);
							if(sign_of_influence!=0){
								if(newids.contains(id))
									id = id+"_";
								newids.add(id);
							}
							if(sign_of_influence==-1)
								addTransition(cd4, id, "POSITIVE_INFLUENCE", "ffaa0000", mod_species, mod_alias, listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
							if(sign_of_influence==+1)
								addTransition(cd4, id, "NEGATIVE_INFLUENCE", "ff00aa00", mod_species, mod_alias, listOfReactantSpecies.get(k), listOfReactantAliases.get(k));
							}
						}
					}	
					}
					}
				}
			}
		}
		
		public static void addTransition(SbmlDocument cd4, String id, String type, String color, String species1, String alias1, String species2, String alias2){
			ReactionDocument.Reaction r = cd4.getSbml().getModel().getListOfReactions().addNewReaction();
			
			r.setId(id);

			AnnotationDocument.Annotation an = r.addNewAnnotation();
			XmlString xs = XmlString.Factory.newInstance();
			xs.setStringValue(type);
			an.addNewCelldesignerReactionType().set(xs);
					
			
			SpeciesReferenceDocument.SpeciesReference spr = r.addNewListOfProducts().addNewSpeciesReference();
			spr.setSpecies(species2);
			xs = XmlString.Factory.newInstance();
			xs.setStringValue(alias2);
			spr.addNewAnnotation().addNewCelldesignerAlias().set(xs);

			spr = r.addNewListOfReactants().addNewSpeciesReference();
			spr.setSpecies(species1);
			xs = XmlString.Factory.newInstance();
			xs.setStringValue(alias1);
			spr.addNewAnnotation().addNewCelldesignerAlias().set(xs);		
			
			CelldesignerBaseReactantDocument.CelldesignerBaseReactant cbr = an.addNewCelldesignerBaseReactants().addNewCelldesignerBaseReactant();
			xs = XmlString.Factory.newInstance();
			xs.setStringValue(species1);
			cbr.setSpecies(xs);
			cbr.setAlias(alias1);

			CelldesignerBaseProductDocument.CelldesignerBaseProduct cbp = an.addNewCelldesignerBaseProducts().addNewCelldesignerBaseProduct();
			xs = XmlString.Factory.newInstance();
			xs.setStringValue(species2);
			cbp.setSpecies(xs);
			cbp.setAlias(alias2);
			
			CelldesignerLineDocument.CelldesignerLine cl = an.addNewCelldesignerLine();
			cl.setWidth("1.0");
			cl.setColor(color);
			
		}

    
}



