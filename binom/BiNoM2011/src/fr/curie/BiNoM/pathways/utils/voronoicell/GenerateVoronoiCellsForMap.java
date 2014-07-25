package fr.curie.BiNoM.pathways.utils.voronoicell;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class GenerateVoronoiCellsForMap {
	
	
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
	public float maximalDist = 200f;
	
	public boolean writeFiles = true;
	
	public static void main(String[] args) {
		try{
			
			//System.out.println(getVoronoiCellsForCellDesignerMap("C:/Datas/BiNoMTest/VoronoiCell/M-Phase2.xml"));
			GenerateVoronoiCellsForMap gvc = new GenerateVoronoiCellsForMap();
			//gvc.loadMap("C:/Datas/BiNoMTest/VoronoiCell/M-Phase2.xml");
			gvc.loadMap("C:/Datas/BiNoMTest/VoronoiCell/merged_master.xml");
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
        for(int k=0;k<gvc.points.size();k++){
        	Pnt[] pol = gvc.polygons.get(k);
        	descr+=gvc.aliases.get(k);
        	for(int i=0;i<pol.length;i++) {
        		descr+="\t"+scales.getX(pol[i].coord(0))+"\t"+scales.getY(pol[i].coord(1));
		}
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
	
	
	public void loadMap(String fn) throws Exception{
		CellDesigner cd = new CellDesigner();
		sbml = cd.loadCellDesigner(fn);
		CellDesigner.entities = CellDesigner.getEntities(sbml);
		width = Integer.parseInt(sbml.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		height = Integer.parseInt(sbml.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
		cdgraph = XGMML.convertXGMMLToGraph(CellDesignerToCytoscapeConverter.getXGMMLGraph("test", sbml.getSbml()));
		//XGMML.saveToXGMML(cdgraph, fn+".xgmml");
		int nspecies = 0;
		for(int i=0;i<cdgraph.Nodes.size();i++){
			Node n = cdgraph.Nodes.get(i);
			if(n.getFirstAttribute("CELLDESIGNER_SPECIES")!=null)
				if(!n.getFirstAttribute("CELLDESIGNER_SPECIES").equals("")){
					nspecies++;
				}
		}
		//points = new float[nspecies][2];
		points = new Vector<Pnt>();
		aliases = new Vector<String>();
		for(int i=0;i<cdgraph.Nodes.size();i++){
			Node n = cdgraph.Nodes.get(i);
			if(n.getFirstAttribute("CELLDESIGNER_SPECIES")!=null)
				if(!n.getFirstAttribute("CELLDESIGNER_SPECIES").equals("")){
					/*points[i][0] = n.x;
					points[i][1] = n.y;*/
					points.add(new Pnt(n.x, n.y));
					aliases.add(n.getFirstAttributeValue("CELLDESIGNER_ALIAS"));
				}
		}
		folder = (new File(fn)).getParentFile().getAbsolutePath();
		//System.out.println(folder);
		writePoints(folder);
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
        for(int k=0;k<points.size();k++){
        	polygons.add(null);
        	neighbours.add(null);
        }
        for (Triangle triangle : dt){
        	//System.out.println("TRIANGLE: "+triangle+"\t"+triangle.get(0)+"\t"+triangle.get(1)+"\t"+triangle.get(2));
            for (Pnt site: triangle) {
            	int index = points.indexOf(site);
            	//System.out.println("SITE: "+site);
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
        for(int k=0;k<points.size();k++){
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
		for(int i=0;i<points.size();i++){
			Pnt c = points.get(i);
			Pnt polygon[] = polygons.get(i);
			for(int k=0;k<polygon.length;k++){
				Pnt p = polygon[k];
				float x = (float)p.coord(0);
				float y = (float)p.coord(1);
				float distance = (float)Math.sqrt((x-c.coord(0))*(x-c.coord(0))+(y-c.coord(1))*(y-c.coord(1)));
				if(distance>maximalDist){
					float factor = distance/maximalDist;
					x = (float)(c.coord(0)+(x-c.coord(0))/factor);
					y = (float)(c.coord(1)+(y-c.coord(1))/factor);
					Pnt newp = new Pnt(x,y);
					polygon[k] = newp;
				}else
				if((x<0)||(y<0)||(x>width)||(y>height)){
					if(x<0) x=0;
					if(x>width) x = width;
					if(y<0) y=0;
					if(y>height) y = height;
					Pnt newp = new Pnt(x,y);
					polygon[k] = newp;
				}
			}
		}
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
