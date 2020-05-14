package fr.curie.BiNoM.pathways.utils.voronoicell;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.CelldesignerClassDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument.Species;
import org.apache.xmlbeans.SimpleValue;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class GenerateVoronoiCellsForMap {
	
	private class vEdge{
		Pnt x1 = null;
		Pnt x2 = null;
		
		public vEdge(){
		}
		
		public vEdge(Pnt _x1, Pnt _x2){
			x1 = _x1;
			x2 = _x2;
		}
		public float length(){
			float vx1 = (float)x1.coord(0);
			float vy1 = (float)x1.coord(1);
			float vx2 = (float)x2.coord(0);
			float vy2 = (float)x2.coord(1);
			return (float)Math.sqrt((vx1-vx2)*(vx1-vx2)+(vy1-vy2)*(vy1-vy2));
		}
		
		public boolean equals(vEdge e){
			boolean res = false;
			if((e.x1.coord(0)-x1.coord(0))*(e.x1.coord(0)-x1.coord(0))+(e.x1.coord(1)-x1.coord(1))*(e.x1.coord(1)-x1.coord(1))+(e.x2.coord(0)-x2.coord(0))*(e.x2.coord(0)-x2.coord(0))+(e.x2.coord(1)-x2.coord(1))*(e.x2.coord(1)-x2.coord(1))<1e-6f) res = true;
			if((e.x1.coord(0)-x2.coord(0))*(e.x1.coord(0)-x2.coord(0))+(e.x1.coord(1)-x2.coord(1))*(e.x1.coord(1)-x2.coord(1))+(e.x2.coord(0)-x1.coord(0))*(e.x2.coord(0)-x1.coord(0))+(e.x2.coord(1)-x1.coord(1))*(e.x2.coord(1)-x1.coord(1))<1e-6f) res = true;
			return res;
		}
		
	}
	
	private class vAngle{
		vEdge edge1 = null;
		vEdge edge2 = null;
		
		public vAngle(vEdge e1, vEdge e2){
			edge1 = e1;
			edge2 = e2;
		}
		
		public Vector<Pnt> cutAngle(float maxlength){
			//Vector<vEdge> edges = new Vector<vEdge>();
			Vector<Pnt> res = new Vector<Pnt>();
			float frac1 = edge1.length()/maxlength;
			float frac2 = edge2.length()/maxlength;
			float x1 = (float)(edge1.x1.coord(0)+frac1*(edge1.x2.coord(0)-edge1.x1.coord(0)));
			float y1 = (float)(edge1.x1.coord(1)+frac1*(edge1.x2.coord(1)-edge1.x1.coord(1)));
			float x2 = (float)(edge2.x2.coord(0)+frac2*(edge2.x1.coord(0)-edge2.x2.coord(0)));
			float y2 = (float)(edge2.x2.coord(1)+frac2*(edge2.x1.coord(1)-edge2.x2.coord(1)));
			res.add(new Pnt(x1,y1));
			res.add(new Pnt(x2,y2));
			return res;
			/*vEdge e1 = new vEdge(edge1.x1,new Pnt(x1,y1));
			vEdge e2 = new vEdge(new Pnt(x1,y1),new Pnt(x2,y2));
			vEdge e3 = new vEdge(new Pnt(x2,y2),edge2.x2);
			edges.add(e1);
			edges.add(e2);
			edges.add(e3);
			return edges;*/
		}
	}
	
	SbmlDocument sbml = null;
	String folder = null;
	fr.curie.BiNoM.pathways.analysis.structure.Graph cdgraph = null;
	fr.curie.BiNoM.pathways.analysis.structure.Graph dlgraph = null;
	int width = 0;
	int height = 0;
	public Vector<Pnt> points = null;
	public Vector<String> aliases = new Vector<String>();
	public Vector<Vector<String>> neighbours = new Vector<Vector<String>>();
	Triangle initialTriangle = null;
	Triangulation dt = null;
	public Vector<Pnt[]> polygons = new Vector<Pnt[]>();
	public float maximalDist = 800f;
	public float maximalDistAsFractionOfSize = 0.02f;
	
	public boolean writeFiles = true;
	
	public static void main(String[] args) {
		try{
			
			
			String fn = "C:/Datas/BiNoMTest/VoronoiCell/acsn_master.xml";
			//String fn = "C:/Datas/BiNoMTest/VoronoiCell/dnarepair_master.xml";
			//String fn = "C:/Datas/BiNoMTest/VoronoiCell/survival_master.xml";
			//String fn = "C:/Datas/BiNoMTest/VoronoiCell/dnarepair_FANCONI.xml";
			//String fn = "C:/Datas/BiNoMTest/VoronoiCell/merged_master.xml";
			//String fn = "C:/Datas/BiNoMTest/VoronoiCell/test.xml";
			//fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.ImagesInfo scales = new fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.ImagesInfo(height, height, height, height, height, height, height, height, writeFiles);
			//getVoronoiCellsForCellDesignerMap();
			//System.out.println(getVoronoiCellsForCellDesignerMap("C:/Datas/BiNoMTest/VoronoiCell/M-Phase2.xml"));
			
			String s = getVoronoiCellsForCellDesignerMap(fn, null);
			//System.out.println(s);
			System.exit(0);
			
			GenerateVoronoiCellsForMap gvc = new GenerateVoronoiCellsForMap();
			//gvc.loadMap("C:/Datas/BiNoMTest/VoronoiCell/M-Phase2.xml");
			//gvc.loadMap("C:/Datas/BiNoMTest/VoronoiCell/merged_master.xml");
			gvc.loadMap(fn); 
			//gvc.testPoints("C:/Datas/BiNoMTest/VoronoiCell/");
			gvc.calcTriangles();
			gvc.calcVoronoiCells();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static String getVoronoiCellsForCellDesignerMap(String fn, fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.ImagesInfo scales){
		String descr = "";
		try{
		GenerateVoronoiCellsForMap gvc = new GenerateVoronoiCellsForMap();	
		gvc.writeFiles = false;
		gvc.loadMap(fn);
		gvc.calcTriangles();
		gvc.calcVoronoiCells();
        for(int k=0;k<gvc.points.size();k++)if(!gvc.aliases.get(k).equals("NONE"))if(gvc.neighbours.get(k)!=null){
        	Pnt[] pol = gvc.polygons.get(k);
        	descr+=gvc.aliases.get(k);
        	for(int i=0;i<pol.length;i++) {
        		descr+="\t"+scales.getX(pol[i].coord(0))+"\t"+scales.getY(pol[i].coord(1));
			}
        	//if(gvc.neighbours.get(k)==null){
        	//	System.out.println(gvc.aliases.get(k)+" - neighbours list is null");
        	//}
        	for(int i=0;i<gvc.neighbours.get(k).size();i++) {
        		descr+="\t"+gvc.neighbours.get(k).get(i);
        	}
        	descr+="\n";
        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return descr;
	}
	public boolean checkNode(HashMap<String, Species> list_of_species, String id) {

		Species sp = list_of_species.get(id);
		
		// Degradations and Phenotypes
		// Check if the class is one of the autorized ones, not if it's a forbidden one 
		// First four + complexes
		if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null && sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass() != null) {
			CelldesignerClassDocument.CelldesignerClass name = sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass();
			if (
				((SimpleValue) name).getStringValue().compareTo("PROTEIN") == 0 
			 || ((SimpleValue) name).getStringValue().compareTo("GENE") == 0
			 || ((SimpleValue) name).getStringValue().compareTo("RNA") == 0
			 || ((SimpleValue) name).getStringValue().compareTo("ANTISENSE_RNA") == 0
			 || ((SimpleValue) name).getStringValue().compareTo("SIMPLE_MOLECULE") == 0
			) {
				return true;
			}
		}
		
		return false;
	}
	
	public void loadMap(String fn) throws Exception{
		CellDesigner cd = new CellDesigner();
		sbml = cd.loadCellDesigner(fn);
		CellDesigner.entities = CellDesigner.getEntities(sbml);
		width = Integer.parseInt(sbml.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		height = Integer.parseInt(sbml.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		/*cdgraph = XGMML.convertXGMMLToGraph(CellDesignerToCytoscapeConverter.getXGMMLGraph("test", sbml.getSbml()));
		//XGMML.saveToXGMML(cdgraph, fn+".xgmml");
		int nspecies = 0;
		for(int i=0;i<cdgraph.Nodes.size();i++){
			Node n = cdgraph.Nodes.get(i);
			if(n.getFirstAttribute("CELLDESIGNER_SPECIES")!=null)
				if(!n.getFirstAttribute("CELLDESIGNER_SPECIES").equals("")){
					nspecies++;
				}
		}*/
		
		HashMap<String, Species> list_of_species = new HashMap<String, Species>();
		if (sbml.getSbml().getModel().getListOfSpecies() != null)
		for (int i=0; i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray(); i++) {
			Species t_species = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			list_of_species.put(t_species.getId(), t_species);
		}

		HashSet<String> includedSpecies = new HashSet<String>();
		if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies cs = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			includedSpecies.add(cs.getId());
		}
		
		cdgraph = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String species = csa.getSpecies();
			if(!includedSpecies.contains(species) && checkNode(list_of_species, species)){
			Node n = cdgraph.getCreateNode(csa.getId());
			n.x = Float.parseFloat(csa.getCelldesignerBounds().getX());
			n.y = Float.parseFloat(csa.getCelldesignerBounds().getY());
			n.w = Float.parseFloat(csa.getCelldesignerBounds().getW());
			n.h = Float.parseFloat(csa.getCelldesignerBounds().getH());
			n.setAttributeValueUnique("CELLDESIGNER_SPECIES", species, Attribute.ATTRIBUTE_TYPE_STRING);
			n.setAttributeValueUnique("CELLDESIGNER_ALIAS", csa.getId(), Attribute.ATTRIBUTE_TYPE_STRING);
			}
		}
		
		if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			String species = csa.getSpecies();
			if(!includedSpecies.contains(species) && checkNode(list_of_species, species)){
			Node n = cdgraph.getCreateNode(csa.getId());
			n.x = Float.parseFloat(csa.getCelldesignerBounds().getX());
			n.y = Float.parseFloat(csa.getCelldesignerBounds().getY());
			n.w = Float.parseFloat(csa.getCelldesignerBounds().getW());
			n.h = Float.parseFloat(csa.getCelldesignerBounds().getH());
			n.setAttributeValueUnique("CELLDESIGNER_SPECIES", species, Attribute.ATTRIBUTE_TYPE_STRING);
			n.setAttributeValueUnique("CELLDESIGNER_ALIAS", csa.getId(), Attribute.ATTRIBUTE_TYPE_STRING);
			}
		}
		
		
		//points = new float[nspecies][2];
		points = new Vector<Pnt>();
		aliases = new Vector<String>();
		Random r = new Random();
		for(int i=0;i<cdgraph.Nodes.size();i++){
			Node n = cdgraph.Nodes.get(i);
			String species = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
			if(n.Id.startsWith("p53"))
				System.out.println(n.Id+"\t"+species);
			if(species!=null)
				if(!species.equals("")){
					/*points[i][0] = n.x;
					points[i][1] = n.y;*/
					float x = n.x+n.w/2f+r.nextFloat();
					float y = n.y+n.h/2f+r.nextFloat();
					points.add(new Pnt(x, y));
					String alias = n.getFirstAttributeValue("CELLDESIGNER_ALIAS");
					//if(species.equals("s_mpk1_s787")){
					//	System.out.println("For species "+species+" CELLDESIGNER_ALIAS = "+alias);
					//}
					aliases.add(alias);
				}
		}
		folder = (new File(fn)).getParentFile().getAbsolutePath();
		
		//System.out.println("Loaded...");
		//System.out.println(folder);
		
		//maximalDist = width*maximalDistAsFractionOfSize;
		
		// Write fictitious points to avoid large polygons
		int currentNumberOfPoints = points.size();
		for(float x=1;x<width;x+=maximalDist)
			for(float y=1;y<height;y+=maximalDist)if((x==1f)||(y==1f)||(!checkClosePoints(x,y,currentNumberOfPoints))){
				points.add(new Pnt(x,y));
				aliases.add("NONE");
			}
		for(float x=1;x<width;x+=maximalDist){
				points.add(new Pnt(x,height-1));
				aliases.add("NONE");
			}
		for(float y=1;y<height;y+=maximalDist){
			points.add(new Pnt(width-1,y));
			aliases.add("NONE");
		}
		
		
		writePoints(folder);
	}
	
	public boolean checkClosePoints(float x, float y, int numberOfPointsToCheck){
		boolean res = false;
		for(int i=0;i<numberOfPointsToCheck;i++){
			float dist = (new vEdge(points.get(i),new Pnt(x,y))).length();
			if(dist<maximalDist){
				res = true; break;
			}
		}
		return res;
	}
	
	public void writePoints(String folder) throws Exception{
		if(writeFiles){
		FileWriter fw = new FileWriter(folder+"/points.txt");
		//for(int i=0;i<points.length;i++)
		//	fw.write(points[i][0]+"\t"+points[i][1]+"\n");
		for(int i=0;i<points.size();i++)
			fw.write(points.get(i).coord(0)+"\t"+points.get(i).coord(1)+"\n");
		fw.close();
		}
	}
	
	public void calcTriangles() throws Exception{
        initialTriangle = new Triangle(new Pnt(2*width,height), new Pnt(0,height), new Pnt(0,-height));
        dt = new Triangulation(initialTriangle);
        for(int i=0;i<points.size();i++){
        	//dt.delaunayPlace(new Pnt(points[i][0],points[i][1]));
        	dt.delaunayPlace(points.get(i));
        }
        if(writeFiles){
        FileWriter fw = new FileWriter(folder+"/triangles_names.txt");
        FileWriter fw1 = new FileWriter(folder+"/triangles.txt");
        for(Triangle t: dt.triGraph.nodeSet()){
        	fw.write(t.toString()+"\n");
        	fw1.write(t.get(0).coord(0)+"\t"+t.get(0).coord(1)+"\t");
        	fw1.write(t.get(1).coord(0)+"\t"+t.get(1).coord(1)+"\t");
        	fw1.write(t.get(2).coord(0)+"\t"+t.get(2).coord(1)+"\n");
        }
        fw.close();
        fw1.close();
        }
        //System.out.println("After adding 3 points, we have a " + dt);
        //Triangle.moreInfo = true;
        //System.out.println("Triangles: " + dt.triGraph.nodeSet());
	}
	
	public void calcVoronoiCells() throws Exception{
		/*
		 *         HashSet<Pnt> done = new HashSet<Pnt>(initialTriangle);
        for (Triangle triangle : dt)
            for (Pnt site: triangle) {
                if (done.contains(site)) continue;
                done.add(site);
                List<Triangle> list = dt.surroundingTriangles(site, triangle);
                Pnt[] vertices = new Pnt[list.size()];
                int i = 0;
                for (Triangle tri: list)
                    vertices[i++] = tri.getCircumcenter();
                draw(vertices, withFill? getColor(site) : null);
                if (withSites) draw(site);
            }
		 */
        HashSet<Pnt> done = new HashSet<Pnt>(initialTriangle);
        polygons.clear();
        neighbours.clear();
        for(int k=0;k<points.size();k++)if(!aliases.get(k).equals("NONE")){
        	polygons.add(null);
        	neighbours.add(null);
        }
        for (Triangle triangle : dt){
        	//System.out.println("TRIANGLE: "+triangle+"\t"+triangle.get(0)+"\t"+triangle.get(1)+"\t"+triangle.get(2));
            for (Pnt site: triangle) {
            	int index = points.indexOf(site);
            	//System.out.println("SITE: "+site);
            	if(index!=-1)if(aliases.get(index).equals("NONE")) continue;
                if (done.contains(site)) continue;
                done.add(site);
                List<Triangle> list = dt.surroundingTriangles(site, triangle);
            	Vector<String> v = new Vector<String>();
                for(int k=0;k<list.size();k++){
                	//System.out.println(list.get(k)+"\t"+list.get(k).get(0)+"\t"+list.get(k).get(1)+"\t"+list.get(k).get(2));
                	for(Pnt n: list.get(k)){
                		int indn = points.indexOf(n);
                		if(indn!=index)if(indn>=0){
                			String alias = aliases.get(indn);
                			if(!v.contains(alias))
                				v.add(alias);
                		}
                	}
                }
            	neighbours.set(index, v);
                Pnt[] vertices = new Pnt[list.size()];
                int i = 0;
                for (Triangle tri: list)
                    vertices[i++] = tri.getCircumcenter();
                //polygons.add(vertices);
                polygons.set(index, vertices);
                /*System.out.println("===================");
                for(int k=0;k<vertices.length;k++)
                	System.out.println(vertices[k]);
                System.out.println("===================");*/
            }
        }
		//
        if(true)
        	rescalePolygons();
        if(writeFiles){
        FileWriter fw1 = new FileWriter(folder+"/vc.txt");
        for(Pnt[] pol: polygons){
        	for(int i=0;i<pol.length;i++)
        		fw1.write(pol[i].coord(0)+"\t"+pol[i].coord(1)+"\t");
        	fw1.write("\n");
        }
        
        fw1.close();
        
        fw1 = new FileWriter(folder+"/vc_alias.txt");
        FileWriter fw2 = new FileWriter(folder+"/vc_names.txt");
        for(int k=0;k<points.size();k++)if(!aliases.get(k).equals("NONE")){
        	Pnt[] pol = polygons.get(k);
        	fw1.write(aliases.get(k)+"\t");
        	fw2.write(aliases.get(k)+"\n");
        	for(int i=0;i<pol.length;i++)
        		fw1.write(pol[i].coord(0)+"\t"+pol[i].coord(1)+"\t");
        	for(int i=0;i<neighbours.get(k).size();i++){
        		fw1.write(neighbours.get(k).get(i)+"\t");
        	}
        	fw1.write("\n");
        }
        fw2.close();
        fw1.close();   
        }
	}
	
	public void rescalePolygons(){
		for(int i=0;i<points.size();i++)if(!aliases.get(i).equals("NONE")){
			Pnt c = points.get(i);
			//System.out.println(c.coord(0)+"\t"+c.coord(1));
			Pnt polygon[] = polygons.get(i);
			if (polygon == null) {
				System.err.println("warning: voronoi polygon is null at " + i+", alias = "+aliases.get(i));
				continue;
			}
				Pnt polygon_rescaled[] = rescaledPolygon(polygon, c);
				polygons.set(i, polygon_rescaled);
		}
	}
	
	public Pnt[] rescaledPolygon(Pnt polygon[], Pnt c){
		
		for(int i=0;i<polygon.length;i++){
			Pnt p = polygon[i];
			float x = (float)p.coord(0);
			float y = (float)p.coord(1);
			if((x<0)||(y<0)||(x>width)||(y>height)){
				if(x<0) x=0;
				if(x>width) x = width;
				if(y<0) y=0;
				if(y>height) y = height;
				Pnt newp = new Pnt(x,y);
				polygon[i] = newp;
		}}
		
		
		//Vector<vAngle> edges = new Vector<vAngle>();
		Vector<Pnt> plgn = new Vector<Pnt>();
		for(int k=0;k<polygon.length;k++){	
			Pnt p_central = polygon[k];
			Pnt p_prev = null;
			Pnt p_next = null;
			if(k>0) p_prev = polygon[k-1]; else p_prev = polygon[polygon.length-1];
			if(k<polygon.length-1) p_next = polygon[k+1]; else p_next = polygon[0];
			float length = (new vEdge(p_central,c)).length();
			plgn.add(p_central);
			/*if(length<=maximalDist){
				plgn.add(p_central);
			}else{
				vAngle angle = new vAngle(new vEdge(p_prev,p_central), new vEdge(p_central,p_next));
				Vector<Pnt> pnts = angle.cutAngle(maximalDist);
				plgn.add(pnts.get(0));
				plgn.add(pnts.get(1));
			}*/
		}
				
		Pnt polygon_rescaled[] = new Pnt[plgn.size()];
		for(int i=0;i<plgn.size();i++)
			polygon_rescaled[i] = plgn.get(i);
		return polygon_rescaled;
	}
	
	
	public void testPoints(String folder) throws Exception{
		/*points = new float[2][2];
		points[0][0] = width/2f;
		points[0][1] = height/2f;
		points[1][0] = 100f;
		points[1][1] = 200f;*/
		points = new Vector<Pnt>();
		points.add(new Pnt(width/2f,height/2f));
		points.add(new Pnt(100f,200f));
		writePoints(folder);
	}

}
