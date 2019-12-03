package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory;
import fr.curie.BiNoM.pathways.biopax.publicationXref;
import fr.curie.BiNoM.pathways.utils.SubnetworkProperties;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.*;
import edu.rpi.cs.xgmml.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;



public class ProduceClickableMap {
	
	public HashMap<String,Vector<Place>> placeMap = new HashMap<String,Vector<Place>>(); 
	public SbmlDocument cd = null;
	public GraphDocument graph = null;
	public HashMap species = new HashMap();
	public HashMap speciesAliases = new HashMap();
	public HashMap speciesInReactions = new HashMap();
	public HashMap speciesSBML = new HashMap();
	public HashMap speciesEntities = new HashMap();
	public HashMap entities = new HashMap();
	
	public HashMap<String, Vector<String>> module_species = new HashMap<String, Vector<String>>();
	public HashMap<String, Vector<String>> module_proteins = new HashMap<String, Vector<String>>();
	public HashMap<String, Vector<String>> module_protein_names = new HashMap<String, Vector<String>>();
	
	public HashMap<String, String> module_names = new HashMap<String, String>();
	
	public HashMap pathways = new HashMap();
	
	public HashMap<String, String> speciesModularModuleNameMap = new HashMap<String, String>();
	
	public int linewidth = 3;
	public float scale = 1f;
	
	public static String scriptFile = "";
	
	public String name = "";
	public String title = "";	
	public String path = ""; 
	public String subfolder = "pages";
	public String partialfolder = "partial";
	public String modulefolder = "modules";
	
	public String script = "";
	public String menu = "";
	public String frame = "";
	
	public float scaleSmall = 0.7f;
	public float scaleTiny = 0.5f;
	
	
	public static void main(String args[]){
		try{
			
			ProduceClickableMap clMap = new ProduceClickableMap();
			clMap.name = "dnarepair";
			clMap.title = "DNA repair ";
			//clMap.path = "c:/datas/binomtest/testClickableMapa/";
			clMap.path = "C:/Datas/Basal/DNARepairMap2/";
			clMap.subfolder = "pages";
			clMap.loadCellDesigner(clMap.path+clMap.name+".xml");
			//clMap.printAnnotation(clMap.cd);
			clMap.createClickableMap();
			System.exit(0);
			
			//String name = "M-Phase2";
			//String name = "test1";
			//String name = "modules";
			//String name = "RB_ER_EGFR_s";
			//String name = "100526_fixed";
			//String name = "mito_100526";
			//String name = "ERK_grieco_4.0.1_small";
			//String name = "100603_small";
			//String name = "caspase_regulation";
			//String name = "dnarepair_small";
			//String name = "test";
			//String name = "phenotypes";
			String name = "S_phase";
			
			//String path = "c:/datas/rbmapstest/";
			///String path = "c:/datas/binomtest/testClickableMap/";
			//String path = "c:/datas/binomtest/RB_EGFR_ER/";
			//String path = "c:/datas/rbmaps/";
			//String path = "c:/datas/rbmaps/modulemap/";
			//String path = "C:/Datas/Simon/caspase_regulation/";
			//String path = "C:/Datas/Basal/DNARepairMap/";
			String path = "C:/Datas/Basal/DNARepairMap/"; 
			
			String subfolder = "pages";
			subfolder = name;
			
			//decomposeIntoModulesByTagValue(path+name+".xml","CHECKPOINT",path);
			//System.exit(0);
			
			//scriptFile = "C:/Datas/Basal/DNARepairMap/script.html";
			File f = new File(path+subfolder);
			f.mkdir();
			
			clMap.loadCellDesigner(path+name+".xml");
			
			
			//clMap.readModules(path+"modules_species.gmt", path+"modules_proteins.gmt", path+"broad_netpath.gmt");
			CellDesignerToCytoscapeConverter.createSpeciesMap(clMap.cd.getSbml());
			
			clMap.findAllPlacesInCellDesigner();
			clMap.updateStandardNames();			
			clMap.generatePages(path, subfolder, "modules", name, "maps");
			String mapString = clMap.generateMapFile(path, name, subfolder);
			/*String mapStringFull = Utils.loadString(path+"mapfull");
			String mapStringSmall = Utils.loadString(path+"mapsmall");
			String mapStringTiny = Utils.loadString(path+"maptiny");
			String mapStringScript = Utils.loadString(path+"mapscript");
			mapStringFull = Utils.replaceString(mapStringFull, "\"pages/", "\"../pages/");
			mapStringSmall = Utils.replaceString(mapStringSmall, "\"pages/", "\"../pages/");
			mapStringTiny = Utils.replaceString(mapStringTiny, "\"pages/", "\"../pages/");*/
			//clMap.makeModuleMapFiles(path+"modules/",mapStringFull, mapStringSmall, mapStringTiny, mapStringScript);
			clMap.fullListOfEntities(path+subfolder+"/"+name+"_list.html");
			//clMap.fullListOfModules(path+"modules/"+name+"_modulelist.html");
			
			//clMap.generatePartialMaps("c:/datas/rbmaps/", "rb", "maps");
			
			//clMap.makeCytoscapeMaps("c:/datas/rbmaps/modules/xgmml/","pages","c:/datas/rbmaps/modules/sizes.txt");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadGraph(String fn) throws Exception{
		graph = XGMML.loadFromXMGML(fn);
	}

	public void loadCellDesigner(String fn) throws Exception{
		cd = CellDesigner.loadCellDesigner(fn);
		CellDesigner.entities = CellDesigner.getEntities(cd);
	}
	
	public void findAllPlacesInCellDesigner(){
		System.out.println("Finding places in CellDesigner: "+this.name);
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			Place place = new Place();
			place.id = spa.getId();
			place.x = Float.parseFloat(spa.getCelldesignerBounds().getX());
			place.y = Float.parseFloat(spa.getCelldesignerBounds().getY());
			place.positionx = place.x;
			place.positiony = place.y;
			place.width = Float.parseFloat(spa.getCelldesignerBounds().getW());
			place.height = Float.parseFloat(spa.getCelldesignerBounds().getH());
			place.type = place.RECTANGLE;
			place.sbmlid = spa.getSpecies(); 
			
			Object obj = CellDesigner.entities.get(spa.getSpecies());
			//System.out.println(obj.getClass().getName().toLowerCase());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(spa.getSpecies());
				String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
				place.label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd); //spa.getSpecies();

			Vector<Place> vp = placeMap.get(place.id);
			if(vp==null) vp = new Vector<Place>();
			vp.add(place);
			placeMap.put(place.id,vp);
			species.put(spa.getSpecies(), spa);
			
			Vector aliases = (Vector)speciesAliases.get(spa.getSpecies());
			if(aliases==null) aliases = new Vector();
			aliases.add(spa.getId());
			speciesAliases.put(spa.getSpecies(),aliases);
			
			Vector v = (Vector)speciesEntities.get(spa.getSpecies());
			if(v==null) v = new Vector();
			v.add(getEntity(spa.getSpecies()));
			speciesEntities.put(spa.getSpecies(),v);
			
			}
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			Place place = new Place();
			place.id = cspa.getId();
			place.x = Float.parseFloat(cspa.getCelldesignerBounds().getX());
			place.y = Float.parseFloat(cspa.getCelldesignerBounds().getY());
			place.positionx = place.x;
			place.positiony = place.y;
			place.width = Float.parseFloat(cspa.getCelldesignerBounds().getW());
			place.height = Float.parseFloat(cspa.getCelldesignerBounds().getH());
			place.type = place.RECTANGLE;
			place.sbmlid = cspa.getSpecies();

			try{
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(cspa.getSpecies());
			String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
			place.label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd); //spa.getSpecies();

			
			Vector<Place> vp = placeMap.get(place.id);
			if(vp==null) vp = new Vector<Place>();
			vp.add(place);
			placeMap.put(place.id,vp);
			species.put(cspa.getSpecies(), cspa);
			
			Vector aliases = (Vector)speciesAliases.get(cspa.getSpecies());
			if(aliases==null) aliases = new Vector();
			aliases.add(cspa.getId());
			speciesAliases.put(cspa.getSpecies(),aliases);
		
			Vector v = (Vector)speciesEntities.get(cspa.getSpecies());
			if(v==null) v = new Vector();
			Vector ve = getEntitiesInComplex(cspa.getSpecies());
			for(int k=0;k<ve.size();k++){
					Entity ent = (Entity)ve.get(k);
					boolean found = false;
					if(ent!=null)
					for(int kk=0;kk<v.size();kk++){
						Entity e = (Entity)v.get(kk);
						if(e!=null)if(e.id!=null){
						//System.out.println(e.id+"\t"+ent.id);
						if(e.id.equals(ent.id))
							found = true;
						}
					}
					if(!found)
						v.add(ent);
			}
			speciesEntities.put(cspa.getSpecies(),v);
		}catch(Exception e){
			System.out.println(place.sbmlid+" is not species in findAllPlacesInCellDesigner/elldesignerListOfComplexSpeciesAliases");
		}}
		// Now species comments
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			speciesSBML.put(sp.getId(), sp);
		}
		// Now reactions
		if(cd.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			if(r.getListOfReactants()!=null)
			for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfProducts()!=null)
			for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfModifiers()!=null)
			for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
				String spid = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			
			generatePlacesForReaction(r);
			
		}
		
	}
	
	public void generatePages(String path, String folder, String modulefolder, String prname, String mapfolder) throws Exception{
		//FileWriter fw = new FileWriter(path+"\"+folder+".html");
		// First, species
		Iterator it = species.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			Object obj = species.get(id);
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+id+".html");
			String name = id;
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(id);
			String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
			name =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd); //spa.getSpecies();
			
			Vector aliases = (Vector)speciesAliases.get(id);
			Vector<Place> places = placeMap.get((String)aliases.get(0));
			Place place = places.get(0);
			
			fw.write("<html><body>");
			
			fw.write("<h3>Chemical species ("+id+")</h3>\n");
			StringTokenizer st = new StringTokenizer(name,"@");
			fw.write("<b><font color=blue>"+st.nextToken()+"</font></b>\n");
			fw.write("<br><font size=-1><a href='"+id+".html' onClick='scale=top.menu.document.forms[\"coords\"].scale.value;top.map.window.scrollTo("+(int)(place.positionx)+"*scale-getSize().width/2,"+(int)(place.positiony)+"*scale-getSize().height/2)'><img alt='center map on species' border=0 src='center.gif'/> (center map)</a></font><br>");
			//fw.write("<small><a href='"+id+".html' onClick='top.map.document.map.src=\"./"+mapfolder+"/"+id+".png\";top.map.window.scrollTo("+(int)(place.positionx)+"-getSize().width/2,"+(int)(place.positiony)+"-getSize().height/2)'>(partial map)</a></small> - ");
			//fw.write("<small><a href='"+id+".html' onClick='top.map.document.map.src=\""+prname+".png\";top.map.window.scrollTo("+(int)(place.positionx)+"-getSize().width/2,"+(int)(place.positiony)+"-getSize().height/2)'>(full map)</a></small>");
			//fw.write("<small><a href='"+id+".html' onClick='top.map.location.href=\"../"+prname+".html\";top.map.window.scrollTo("+(int)(place.positionx)+"-getSize().width/2,"+(int)(place.positiony)+"-getSize().height/2)'>(full map)</a></small>");
			//fw.write("<br><small><a href='' onClick='top.map.window.scrollTo("+(int)(place.positionx)+","+(int)(place.positiony)+")'>(center on it)</a></small>");
			//fw.write("<br><small><a href='' onClick=\"alert(''+getSize('map').width);\">(center on it)</a></small>");
			//fw.write("<br><small><a href='' onClick=\"alert('?');\">(center on it)</a></small>");
			
			fw.write("<hr><font color=red>Participates in reactions:</font><br>\n");
			fw.write("<p><font size=-1 FACE='Courier New'>\n");
			Vector v = (Vector)speciesInReactions.get(sp.getId());
			if(v!=null)
				for(int i=0;i<v.size();i++){
					ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(i);
					String reactionString = getReactionString(r, cd, true,true);
					fw.write("<a href='"+r.getId()+".html'>("+(i+1)+")</a> "+reactionString+"<br>\n");
				}
			fw.write("</font>\n");

			v = (Vector)speciesEntities.get(sp.getId());
			fw.write("<hr><font color=red>Related entities:</font><br>\n");
			fw.write("<ol>\n");
			if(v!=null)
			for(int i=0;i<v.size();i++){
				Entity ent = (Entity)v.get(i);
				if(ent!=null)
				if(!ent.cls.equals("DEGRADED")||!ent.cls.equals("UNKNOWN")){
					fw.write("<li><a href='"+ent.id+".html'>"+ent.label+"</a>");
				}
			}
			fw.write("</ol>\n");
			
			if(sp.getNotes()!=null)
				//if(sp.getNotes().getHtml()!=null)
				{
					String comment = Utils.getValue(sp.getNotes());
					comment = Utils.replaceString(comment, "Notes by CellDesigner", "");
					comment = checkCommentForXREFS(comment);
					fw.write("<hr><font color=red>Comments:</font><br>\n");
					fw.write("<p>"+comment+"\n");
				}
			

			fw.write("<hr><font color=red>In modules:</font><br>\n");
			
			Vector mnames = getModuleName(module_species, id);
			fw.write("<ol>\n");
			for(int i=0;i<mnames.size();i++){
				String mname = (String)mnames.get(i);
				String mnamec = correctName(mname);
				fw.write("<li>Module: <a href='../"+modulefolder+"/"+subfolder+"/"+mnamec+".html'>"+module_names.get(mname)+"</a>\n");
			}
			fw.write("</ol>\n");
			
			fw.write("\n\n<script>\n");
			fw.write("function getSize() {\n");
			fw.write("var result = {height:0, width:0};\n");

			fw.write("if (parseInt(navigator.appVersion)>3) {\n");
			fw.write("if (navigator.appName==\"Netscape\") {\n");
			fw.write("result.width = top.map.innerWidth-16;\n");
			fw.write("result.height = top.map.innerHeight-16;\n");
			fw.write("}\n");
			fw.write("if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n");
			fw.write("result.width = top.map.document.body.offsetWidth-20;\n");
			fw.write("result.height = top.map.document.body.offsetHeight-20;\n");
			fw.write("}\n");
			fw.write("}\n");
			fw.write("return result;\n");
		    fw.write("}\n");
			fw.write("</script>\n\n");

			fw.write("</body></html>"); 
			
			fw.close();
		}
		// Generate entities
		// proteins
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+prot.getId()+".html");
			fw.write("<h3>Protein ("+prot.getId()+")</h3>\n");

			Entity ent = (Entity)entities.get(prot.getId()); 
			if(ent==null){
				System.out.println("PROTEIN NOT FOUND in writing pages: "+Utils.getText(prot.getName())+"("+prot.getId()+") from "+entities.size());
			}else{
			if(ent.standardName.equals(ent.label))
				fw.write("<b><font color=blue>"+prot.getName()+"</font></b>\n");
			else
				fw.write("<b><font color=blue>"+prot.getName()+" ("+ent.standardName+")</font></b>\n");
			
			//fw.write("<br><small><a href='"+prot.getId()+".html' onClick='top.map.document.map.src=\"./"+mapfolder+"/"+prot.getId()+".png\";'>(partial map)</a></small>");
			//fw.write("<small><a href='"+prot.getId()+".html' onClick='top.map.document.map.src=\""+prname+".png\";'>(full map)</a></small>");
			//fw.write("<small><a href='../"+prname+".html' target='map'>(full map)</a></small>");

			//fw.write("<br>(<a href=\"../"+partialfolder+"/"+prot.getId()+".html\" target=\"map\">global l.</a>,<a href=\"../"+partialfolder+"/"+prot.getId()+"_c.html\" target=\"map\">compact</a>)");
			fw.write("<br>(<a href=\"../"+partialfolder+"/"+prot.getId()+"_c.html\" target=\"map\">compact</a>)");
			fw.write("<hr><font color=red>Represented by species:</font><br>\n");

			for(int j=0;j<ent.species.size();j++){
				//System.out.println(ent.species.get(j).getClass().getName());
				SpeciesDocument.Species sp = (SpeciesDocument.Species)ent.species.get(j);
				String name = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,sp.getId(),true,true);
				fw.write(""+(j+1)+") <a href='"+sp.getId()+".html'>"+name+"</a><br>\n");
			}

			fw.write("<hr><font color=red>Comments:</font>\n");
			fw.write("<p>"+ent.comment+"\n");
			String name = Utils.getValue(prot.getName());
			//if(name.indexOf("*")<0){
				fw.write("<p><a href='http://www.genecards.org/cgi-bin/cardsearch.pl?search="+ent.standardName+"' target='_blank'>"+"GeneCards:"+ent.standardName+"</a>\n");
			//}
			
			fw.write("<hr><font color=red>In modules:</font><br>\n");
			
			Vector mnames = getModuleName(module_proteins, prot.getId());
			fw.write("<ol>\n");
			for(int k=0;k<mnames.size();k++){
				String mname = (String)mnames.get(k);
				String mnamec = correctName(mname);
				fw.write("<li>Module: <a href='../"+modulefolder+"/"+subfolder+"/"+mnamec+".html'>"+module_names.get(mname)+"</a>\n");
			}
			fw.write("</ol>\n");
			
			
			//fw.write("<hr><font color=red><a href="+prname+"_list.html>Show full list</a></font><br>\n");
			
			/*fw.write("<hr><font color=red>Participate in other pathways:</font><br>\n");
			
			Vector pathway_names = getModuleName(pathways, ent.standardName);
			if(pathway_names.size()==0){
				System.out.println("ENTITY NOT FOUND: "+ent.label+" ("+ent.standardName+", "+ent.id+")");
			}
			fw.write("<ol>\n");
			for(int k=0;k<pathway_names.size();k++){
				String mname = (String)pathway_names.get(k);
				if(mname.endsWith("_NP")||mname.indexOf("_NP_")>=0){
					String id = "";
					if(mname.indexOf("ALPHABETAINTEGRIN")>=0) id = "NetPath_1";
					if(mname.indexOf("AR_")>=0) id = "NetPath_2";
					if(mname.indexOf("EGFR1_")>=0) id = "NetPath_4";
					if(mname.indexOf("HEDGEHOG_")>=0) id = "NetPath_10";
					if(mname.indexOf("ID_")>=0) id = "NetPath_5";
					if(mname.indexOf("KITR_")>=0) id = "NetPath_6";
					if(mname.indexOf("NOTCH_")>=0) id = "NetPath_3";
					if(mname.indexOf("TGFBETA_")>=0) id = "NetPath_7";
					if(mname.indexOf("TNFALPHA_")>=0) id = "NetPath_9";
					if(mname.indexOf("WNT_")>=0) id = "NetPath_8";
					fw.write("<li><a href='http://www.netpath.org/pathways?path_id="+id+"' target='_blank'>"+mname+"</a>\n");
				}else
					fw.write("<li><a href='http://www.broad.mit.edu/gsea/msigdb/cards/"+mname+".html' target='_blank'>"+mname+"</a>\n");
			}
			fw.write("</ol>\n");*/
			
			
			fw.close();
			}
		}
		// genes
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+gene.getId()+".html");
			fw.write("<h3>Gene ("+gene.getId()+")</h3>\n");

			Entity ent = (Entity)entities.get(gene.getId());			
			
			if(ent!=null)if(ent.standardName!=null)if(ent.label!=null){
			if(ent.standardName.equals(ent.label))
				fw.write("<b><font color=blue>"+gene.getName()+"</font></b>\n");
			else
				fw.write("<b><font color=blue>"+gene.getName()+" ("+ent.standardName+")</font></b>\n");

			String spid = ((SpeciesDocument.Species)ent.species.get(0)).getId();
			
			Vector aliases = (Vector)speciesAliases.get(spid);
			Vector<Place> places = placeMap.get((String)aliases.get(0));
			Place place = places.get(0);
			
			fw.write("<br><small><a href='"+gene.getId()+".html' onClick='scale=top.menu.document.forms[\"coords\"].scale.value;top.map.window.scrollTo("+(int)(place.positionx)+"*scale-getSize().width/2,"+(int)(place.positiony)+"*scale-getSize().height/2)'><img alt='center map on species' border=0 src='center.gif'/> (center map)</a></small>");
			//fw.write("<br><small> - <a href='"+gene.getId()+".html' onClick='top.map.document.map.src=\"./"+mapfolder+"/"+gene.getId()+".png\";'>(partial map)</a></small> - ");
			//fw.write("<small><a href='../"+prname+".html' target='map'>(full map)</a></small>");
			//fw.write("<small><a href='"+gene.getId()+".html' onClick='top.map.document.map.src=\""+prname+".png\";'>(full map)</a></small>");

			//System.out.println("Looking for "+gene.getId());
			//System.out.println("Found "+ent.label);
			fw.write("<hr><font color=red>Regulations:</font><br>\n");
			for(int j=0;j<ent.species.size();j++){
				//System.out.println(ent.species.get(j).getClass().getName());
				SpeciesDocument.Species sp = (SpeciesDocument.Species)ent.species.get(j);
				Vector v = (Vector)speciesInReactions.get(sp.getId());
				if(v!=null)
					for(int k=0;k<v.size();k++){
						ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(k);
						String reactionString = getReactionString(r, cd, true,true);
						fw.write("<a href='"+r.getId()+".html'>("+(k+1)+")</a> "+reactionString+"<br>\n");
					}
			}
			
			//fw.write("<hr><font color=red>Participate in modules:</font><br>\n");
			
			fw.write("<hr><font color=red>Comments:</font>\n");
			fw.write("<p>"+ent.comment+"\n");
			//if(gene.getName().indexOf("*")<0){
				fw.write("<p><a href='http://www.genecards.org/cgi-bin/cardsearch.pl?search="+ent.standardName+"' target='_blank'>"+"GeneCards:"+ent.standardName+"</a>\n");
			//}

			//fw.write("<hr><font color=red><a href="+prname+"_list.html>Show full list</a></font><br>\n");
			
			fw.write("<hr><font color=red>In modules:</font><br>\n");			
			
			Vector mnames = getModuleName(module_proteins, gene.getId());
			fw.write("<ol>\n");
			for(int k=0;k<mnames.size();k++){
				String mname = (String)mnames.get(k);
				String mnamec = correctName(mname);
				fw.write("<li>Module: <a href='../"+modulefolder+"/"+subfolder+"/"+mnamec+".html'>"+module_names.get(mname)+"</a>\n");
			}
			fw.write("</ol>\n");
			
				
			/*fw.write("<hr><font color=red>Participate in other pathways:</font><br>\n");
			
			Vector pathway_names = getModuleName(pathways, ent.standardName);
			if(pathway_names.size()==0){
				System.out.println("NOT FOUND: "+ent.label+" ("+ent.standardName+", "+ent.id+")");
			}
			fw.write("<ol>\n");
			for(int k=0;k<pathway_names.size();k++){
				String mname = (String)pathway_names.get(k);
				if(mname.endsWith("_NP")||mname.indexOf("_NP_")>=0){
					String id = "";
					if(mname.indexOf("ALPHABETAINTEGRIN")>=0) id = "NetPath_1";
					if(mname.indexOf("AR_")>=0) id = "NetPath_2";
					if(mname.indexOf("EGFR1_")>=0) id = "NetPath_4";
					if(mname.indexOf("HEDGEHOG_")>=0) id = "NetPath_10";
					if(mname.indexOf("ID_")>=0) id = "NetPath_5";
					if(mname.indexOf("KITR_")>=0) id = "NetPath_6";
					if(mname.indexOf("NOTCH_")>=0) id = "NetPath_3";
					if(mname.indexOf("TGFBETA_")>=0) id = "NetPath_7";
					if(mname.indexOf("TNFALPHA_")>=0) id = "NetPath_9";
					if(mname.indexOf("WNT_")>=0) id = "NetPath_8";
					fw.write("<li><a href='http://www.netpath.org/pathways?path_id="+id+"' target='_blank'>"+mname+"</a>\n");
				}else
				fw.write("<li><a href='http://www.broad.mit.edu/gsea/msigdb/genesetCard.jsp?geneset="+mname+"' target='_blank'>"+mname+"</a>\n");
			}
			fw.write("</ol>\n");*/
			
			
			fw.write("\n\n<script>\n");
			fw.write("function getSize() {\n");
			fw.write("var result = {height:0, width:0};\n");

			fw.write("if (parseInt(navigator.appVersion)>3) {\n");
			fw.write("if (navigator.appName==\"Netscape\") {\n");
			fw.write("result.width = top.map.innerWidth-16;\n");
			fw.write("result.height = top.map.innerHeight-16;\n");
			fw.write("}\n");
			fw.write("if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n");
			fw.write("result.width = top.map.document.body.offsetWidth-20;\n");
			fw.write("result.height = top.map.document.body.offsetHeight-20;\n");
			fw.write("}\n");
			fw.write("}\n");
			fw.write("return result;\n");
		    fw.write("}\n");
			fw.write("</script>\n\n");
			
			fw.close();
		}}
		// Generate reactions
		if(cd.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+r.getId()+".html");
			
			Vector<Place> places = (Vector)placeMap.get(r.getId());
			float positionx = 0;
			float positiony = 0;
			if(places!=null){
			for(int j=0;j<places.size();j++){
				Place pl = places.get(j);
				positionx+=pl.positionx;
				positiony+=pl.positiony;
			}
			positionx/=places.size();
			positiony/=places.size();
			}
			
			fw.write("<h3>"+rtype+"("+r.getId()+")</h3>\n");
			
			fw.write("<b><font color=blue>"+getReactionString(r, cd, true,true)+"</font></b>\n");
			
			fw.write("<br><small><a href='"+r.getId()+".html' onClick='scale=top.menu.document.forms[\"coords\"].scale.value;top.map.window.scrollTo("+(int)(positionx)+"*scale-getSize().width/2,"+(int)(positiony)+"*scale-getSize().height/2)'><img alt='center map on reaction' border=0 src='center.gif'/> (center map)</a></small>");
			//fw.write("<small><a href='"+r.getId()+".html' onClick='top.map.document.map.src=\"./"+mapfolder+"/"+r.getId()+".png\";top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(partial map)</a></small> - ");
			//fw.write("<small><a href='"+r.getId()+".html' onClick='top.map.document.map.src=\""+prname+".png\";top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(full map)</a></small>");
			//fw.write("<small><a href='../"+prname+".html' target='map' onClick='top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(full map)</a></small>");
			
			//fw.write("<hr><font color=red>Species involved:</font><br>\n");
			if(r.getNotes()!=null){			
				String comment = Utils.getValue(r.getNotes());
				comment = Utils.replaceString(comment, "Notes by CellDesigner", "");
				comment = checkCommentForXREFS(comment);
				fw.write("<hr><font color=red>Comments:</font><br>\n");
				fw.write("<p>"+comment+"\n");
			}
			
			fw.write("<hr><font color=red>In modules:</font><br>\n");

			Vector mnames = getModuleName(module_species, r.getId());
			fw.write("<ol>\n");
			for(int k=0;k<mnames.size();k++){
				String mname = (String)mnames.get(k);
				String mnamec = correctName(mname);
				fw.write("<li>Module: <a href='../"+modulefolder+"/"+subfolder+"/"+mnamec+".html'>"+mname+"</a>\n");
			}
			fw.write("</ol>\n");
			
			fw.write("\n\n<script>\n");
			fw.write("function getSize() {\n");
			fw.write("var result = {height:0, width:0};\n");
			fw.write("if (parseInt(navigator.appVersion)>3) {\n");
			fw.write("if (navigator.appName==\"Netscape\") {\n");
			fw.write("result.width = top.map.innerWidth-16;\n");
			fw.write("result.height = top.map.innerHeight-16;\n");
			fw.write("}\n");
			fw.write("if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n");
			fw.write("result.width = top.map.document.body.offsetWidth-20;\n");
			fw.write("result.height = top.map.document.body.offsetHeight-20;\n");
			fw.write("}\n");
			fw.write("}\n");
			fw.write("return result;\n");
		    fw.write("}\n");
			fw.write("</script>\n\n");
			
			fw.close();
		}
		// modules
		/*Iterator keys = module_species.keySet().iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname);
			//System.out.println(mnamec);
			FileWriter fw = new FileWriter(path+"/"+modulefolder+"/"+mnamec+"_module.html");
			fw.write("<h3>Module</h3>\n");
			
			fw.write("<b><font color=blue>"+mname+"</font></b>\n");
			
			//fw.write("<br><small><a href='"+mnamec+".html' onClick='fname=\"./modules/"+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".png\";top.map.document.map.src=fname''>(CellDesigner map)</a></small> - ");
			fw.write("<br><small><a href='"+mnamec+"_module.html' onClick='fname=\""+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".html\";top.map.window.location=fname''>(CellDesigner map)</a></small> - ");
			//fw.write("<small><a href='"+mname+".html' onClick='top.map.document.map.src=\"./"+modulefolder+"/"+mname+"_cyto.png\";'>(Cytoscape map)</a></small> - ");
			fw.write("<small><a href='../"+modulefolder+"/"+mnamec+"_cyto.html' target='map'>(Cytoscape map)</a></small>");
			//fw.write("<small><a href='"+mname+".html' onClick='top.map.document.map.src=\""+prname+".png\";'>(full map)</a></small>");
			//fw.write("<small><a href='../"+prname+".html' target='map'>(full map)</a></small>");
			
			fw.write("<hr><font color=red>Entities involved:</font><br>\n");
			
			Vector v = new Vector();
			HashMap entitiesMap = new HashMap();
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
				v.add(Utils.getValue(prot.getName()));
				entitiesMap.put(Utils.getValue(prot.getName()),prot);
			}
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
				CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
				v.add(gene.getName()+" (gene)");
				entitiesMap.put(gene.getName()+" (gene)",gene);
			}
			Vector vv = (Vector)module_proteins.get(mname);
			fw.write("<ol>\n");
			for(int i=0;i<vv.size();i++){
				String ename = (String)vv.get(i);
				String eid = null;
				if(entitiesMap.get(ename)!=null){
					eid = ((CelldesignerProteinDocument.CelldesignerProtein)entitiesMap.get(ename)).getId();
				}
				if(entitiesMap.get(ename+" (gene)")!=null){
					eid = ((CelldesignerGeneDocument.CelldesignerGene)entitiesMap.get(ename+" (gene)")).getId();
				}
				//String eid = (String)entitiesMap.get(ename);
				if(eid!=null){
					fw.write("<li><a href='../"+folder+"/"+eid+".html'>"+ename+"</a>\n");
				}
			}
			fw.write("</ol>\n");
			
			//fw.write("<hr><font color=red><a href='modules.html' target='map'>Show full list of modules</a></font><br>\n");
			//fw.write("<hr><font color=red><a onClick='top.map.location.href=\"modules.html\"' href='"+prname+"_modulelist.html'>Show full list of modules</a></font><br>\n");
			
			fw.close();
		}*/

	}
	
	public void updateStandardNames(){
		HashMap names = new HashMap();
		Iterator it = entities.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Entity ent = (Entity)entities.get(key);
			if(ent.standardName!=null)
			if(!ent.standardName.equals(""))
			if(!ent.label.equals(ent.standardName)){
				names.put(ent.label, ent.standardName);
			}
			ent.standardName = ent.label;
		}
		it = entities.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Entity ent = (Entity)entities.get(key);
			if(names.get(ent.label)!=null)
				ent.standardName = (String)names.get(ent.label);
		}
	}
	
	public void fullListOfEntities(String fn) throws Exception{
		Vector v = new Vector();
		HashMap entitiesMap = new HashMap();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			v.add(Utils.getValue(prot.getName()));
			entitiesMap.put(Utils.getValue(prot.getName()),prot);
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			v.add(gene.getName()+" (gene)");
			entitiesMap.put(gene.getName()+" (gene)",gene);
		}
	    Collections.sort(v);
	    FileWriter fw = new FileWriter(fn);
	    fw.write("<h3>List of entities</h3>\n");	    
	    fw.write("<ol>\n");
	    for(int i=0;i<v.size();i++){
	    	String s = (String)v.get(i);
	    	//System.out.println(s);
	    	if(s.indexOf("gene")>=0){
	    		CelldesignerGeneDocument.CelldesignerGene gene = (CelldesignerGeneDocument.CelldesignerGene)entitiesMap.get(s);
	    		fw.write("<li><font color=green><a href="+gene.getId()+".html>"+s+"</a></font>\n");
	    	}else{
	    		CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)entitiesMap.get(s);
	    		fw.write("<li><font color=blue><a href="+prot.getId()+".html>"+s+"</a></font>\n");
	    	}
	    }
	    fw.write("</ol>\n");
	    fw.close();
	}
	
	public void fullListOfModules(String fn) throws Exception{
		Iterator keys = module_species.keySet().iterator();
		Vector v = new Vector();
		while(keys.hasNext()){
			v.add(keys.next());
		}
		Collections.sort(v);
		FileWriter fw = new FileWriter(fn);
		fw.write("<h3>List of modules</h3>\n");
		fw.write("<ol>\n");
		keys = v.iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname); 
			fw.write("<li><a href='"+mnamec+"_module.html'>Module "+mname+"</a>\n");
		}
		fw.write("</ol>\n");
		fw.close();
	}
	
	public void makeModuleMapFiles(String path, String mapStringFull, String mapStringSmall, String mapStringTiny, String mapStringScript) throws Exception{
		Iterator keys = module_species.keySet().iterator();
		Vector v = new Vector();
		while(keys.hasNext()){
			v.add(keys.next());
		}
		Collections.sort(v);
		keys = v.iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname);
			
			FileWriter fw = new FileWriter(path+mnamec+".html");
			fw.write("<html><body>\n");
			fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+mnamec+".png\">\n");
			fw.write(mapStringFull);
			fw.write(mapStringScript+"\n");
			fw.close();

			fw = new FileWriter(path+mnamec+"_small.html");
			fw.write("<html><body>\n");
			fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+mnamec+"_small.png\">\n");
			fw.write(mapStringFull);
			fw.write(mapStringScript+"\n");
			fw.close();
			
			fw = new FileWriter(path+mnamec+"_tiny.html");
			fw.write("<html><body>\n");
			fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+mnamec+"_tiny.png\">\n");
			fw.write(mapStringTiny);
			/*fw.write("\n</body><script>\n");
			
			fw.write("function showLabel(id){\n");
			fw.write("d = top.menu.document.getElementById(\"showhide\");\n");
			fw.write("d.innerHTML = '<small>'+id+'</small>';\n");
			fw.write("}\n");
			fw.write("</script></html>\n");*/
			fw.write(mapStringScript+"\n");
			fw.close();
			
		}
	}
	
	public String generateMapFile(String path, String imgname, String pagefolder) throws Exception{
		String mapString = "";
		FileWriter fw = new FileWriter(path+imgname+".html");
		fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+imgname+".png\">\n");
		mapString = "<map name=\"immapdata\" href=\"\">\n";
		fw.write("<map name=\"immapdata\" href=\"\">\n");
		
		Iterator<String> it = placeMap.keySet().iterator();
		while(it.hasNext()){
			String id = it.next();
			Vector<Place> plv = placeMap.get(id);
			for(int i=0;i<plv.size();i++){
				Place pl = plv.get(i);
				String Shape = "CIRCLE";
				if(pl.type==pl.RECTANGLE) Shape = "RECT";
				if(pl.type==pl.CIRCLE) Shape = "CIRCLE";
				if(pl.type==pl.POLY) Shape = "POLY";				
				String Label = pl.label;
				
				String coords = "";
				if(pl.type==pl.RECTANGLE){
					coords = (int)(pl.x*scale)+","+(int)(pl.y*scale)+","+(int)(pl.x*scale+pl.width*scale)+","+(int)(pl.y*scale+pl.height*scale);
				}
				if(pl.type==pl.CIRCLE){
					coords = (int)(pl.x*scale)+","+(int)(pl.y*scale)+","+(int)(pl.radius*scale);
				}
				if(pl.type==pl.POLY)
					coords = pl.coords;
				
				Label = Utils.replaceString(Label, ">", "&gt;");
				Label = Utils.replaceString(Label, "<", "&lt;");
				
				// This is temprorarily done to remove compartment name
				StringTokenizer st = new StringTokenizer(Label,"@");
				Label = st.nextToken();
				
				//fw.write("<area shape\""+Shape+"\" alt=\""+Label+"\" coords=\""+coords+"\" onMouseOver=\"\" onMouseOut=\"\" href=\"\">\n");
				mapString+="<area shape=\""+Shape+"\" alt=\""+Label+"\" coords=\""+coords+"\" onMouseClick=\"\" onMouseOver=\"showLabel('"+Label+"')\" href=\""+pagefolder+"/"+pl.sbmlid+".html\" target='info' />\n";
				fw.write("<area shape=\""+Shape+"\" alt=\""+Label+"\" coords=\""+coords+"\" onMouseClick=\"\" onMouseOver=\"showLabel('"+Label+"')\" href=\""+pagefolder+"/"+pl.sbmlid+".html\" target='info' />\n");
			}
		}
		
		mapString+="</map>\n";
		fw.write("</map>\n");
		
		fw.write("\n"+script+"\n");
		
		// Script code
		
		/*fw.write("\n\n<script>\nfunction showId(id){\n");
		fw.write("//top.info.document.open('"+pagefolder+"/'+id+'.html');\n");
		/*fw.write("top.info.document.close();\n");
		fw.write("top.info.document.write('<HTML><BODY>');\n");
	    fw.write("top.info.document.write('<h3>Species <b>'+id+'</b></h3>');\n");
	    
	    fw.write("top.info.document.write('<p><font color=red>Participates in reactions:');\n");
	    fw.write("top.info.document.write('<ol>');\n");
	    fw.write("top.info.document.write('</ol></font><hr>');\n");

	    fw.write("top.info.document.write('<p><font color=green>Entities:');\n");
	    fw.write("top.info.document.write('<ol>');\n");
	    fw.write("top.info.document.write('</ol></font><hr>');\n");
	    
	    fw.write("top.info.document.write('<p><font color=blue>Participate in other pathways:');\n");
	    fw.write("top.info.document.write('<ol>');\n");
	    fw.write("top.info.document.write('</ol></font>');\n");

	    fw.write("top.info.document.write('</BODY></HTML>');\n");*/
	    //fw.write("}</script>\n");*/
				
		
		fw.close();
		return mapString;
	}
	
	
	private class Place{
		String id;
		String label;
		String sbmlid;
		float x = 0;
		float y = 0;
		float centerx = 0;
		float centery = 0;
		float positionx = 0;
		float positiony = 0;
		float width = 0;
		float height = 0;
		float radius = 0;
		int type = 0;
		String coords = "";
		public int RECTANGLE = 0;
		public int CIRCLE = 1;
		public int POLY = 2;
		Vector<String> Annotations = new Vector<String>();
	}
	
	public class Entity{
		String id;
		String label;
		String standardName;
		String cls;
		String comment = "";
		Vector species = new Vector();
	}
	
	private Entity getEntity(String id){
		Entity ent = new Entity();
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(sp.getId().equals(id)){
				ent.cls = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if(ent.cls.equals("PROTEIN")){
					ent.id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
					ent.label = getEntityLabel(ent.id);
					if(entities.get(ent.id)!=null)
						ent = (Entity)entities.get(ent.id);
					ent.comment = getEntityComment(ent);;
					//System.out.println("Protein id "+ent.id);
				}else
				if(ent.cls.equals("GENE")){
					ent.id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
					ent.label = getEntityLabel(ent.id);
					if(entities.get(ent.id)!=null)
						ent = (Entity)entities.get(ent.id);
					ent.comment = getEntityComment(ent);;
				}else
				if(ent.cls.equals("RNA")){
						ent.id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
						ent.label = getEntityLabel(ent.id);
						if(entities.get(ent.id)!=null)
							ent = (Entity)entities.get(ent.id);
						ent.comment = getEntityComment(ent);;
				}else
				if(ent.cls.equals("ANTISENSE_RNA")){
					ent.id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
					ent.label = getEntityLabel(ent.id);
					if(entities.get(ent.id)!=null)
						ent = (Entity)entities.get(ent.id);
					ent.comment = getEntityComment(ent);;
				}else
					if(ent.cls.equals("DRUG")){
						ent.id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
						ent.label = getEntityLabel(ent.id);
						if(entities.get(ent.id)!=null)
							ent = (Entity)entities.get(ent.id);
						ent.comment = getEntityComment(ent);;
					}else					
				if(ent.cls.equals("SIMPLE_MOLECULE")){
					ent = null;
				}else
				if(ent.cls.equals("ION")){
					ent = null;
				}else					
				if(ent.cls.equals("UNKNOWN")){
						/*ent.id = sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical().xmlText();
						ent.label = getEntityLabel(ent.id);
						if(entities.get(ent.id)!=null)
							ent = (Entity)entities.get(ent.id);
						ent.comment = getEntityComment(ent.id);;*/
					    ent = null;
				}else
    			if(ent.cls.equals("DEGRADED")){
						ent.id = sp.getId();
				}else
    			//if(ent.cls.equals("COMPLEX")){
				//		ent.id = sp.getId();
				//}else
    			if(ent.cls.equals("PHENOTYPE")){
						ent.id = sp.getId();
						ent.label = Utils.getValue(sp.getName());
				}else
					System.out.println("Class not found in getEntity: "+ent.cls+" for "+sp.getId());
				//if(ent.id.equals("p13"))
				//	System.out.println(Utils.getValue(sp.getName()));
				if(ent!=null)
					ent.species.add(sp);
			}
		}
		if(ent!=null){
			//System.out.println("Put "+ent.id);
			entities.put(ent.id, ent);
		}
		return ent;
	}
	
	private Vector getEntitiesInComplex(String id){
		Vector v = new Vector();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			String cid = Utils.getValue(sp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
			//System.out.println(id+"\t"+cid);
			if(cid.equals(id)){
				Entity ent = new Entity();
				ent.cls = Utils.getValue(sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if(ent.cls.equals("PROTEIN")){
					ent.id = Utils.getValue(sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
					ent.label = getEntityLabel(ent.id);
					if(entities.get(ent.id)!=null)
						ent = (Entity)entities.get(ent.id);
					ent.comment = getEntityComment(ent);;
					//System.out.println("Protein id "+ent.id);
				}else
				if(ent.cls.equals("GENE")){
					ent.id = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().xmlText();
					ent.label = getEntityLabel(ent.id);
					ent.comment = getEntityComment(ent);;
				}else
				if(ent.cls.equals("SIMPLE_MOLECULE")){
						//ent.id = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().xmlText();
						//ent.label = getEntityLabel(ent.id);
						//ent.comment = getEntityComment(ent);;
						ent = null;
				}else
				if(ent.cls.equals("ION")){
						//ent.id = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().xmlText();
						//ent.label = getEntityLabel(ent.id);
						//ent.comment = getEntityComment(ent);;
						ent = null;
				}else					
				if(ent.cls.equals("UNKNOWN")){
						/*ent.id = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerHypothetical().xmlText();
						ent.label = getEntityLabel(ent.id);
						ent.comment = getEntityComment(ent.id);;*/
					    ent = null;
				}else
    			if(ent.cls.equals("DEGRADED")){
						ent.id = sp.getId();
				}else
	    			if(ent.cls.equals("COMPLEX")){
						
				}else
    			if(ent.cls.equals("PHENOTYPE")){
						ent.id = sp.getId();
						ent.label = Utils.getValue(sp.getName());
				}else
					System.out.println("Class not found in getEntity (getEntitiesInComplex): "+ent.cls+" for "+sp.getId());
				if(ent!=null){
					entities.put(ent.id, ent);
					ent.species.add(CellDesigner.entities.get(id));
					v.add(ent);
				}
			}
		}
		return v;
	}
	
	private String getEntityLabel(String id){
		String res = "";
		for(int j=0;j<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();j++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(j);
			if(prot.getId().equals(id)){
				res = Utils.getValue(prot.getName());
			}
		}
		for(int j=0;j<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();j++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(j);
			if(gene.getId().equals(id)){
				res = gene.getName();
			}
		}		
		return res;
	}
	
	private String getEntityComment(Entity ent){
		String res = "";
		for(int j=0;j<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();j++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(j);
			if(prot.getId().equals(ent.id)){
				if(prot.getCelldesignerNotes()!=null)
				   res = Utils.getValue(prot.getCelldesignerNotes());
				   res = Utils.replaceString(res, "Notes by CellDesigner", "");
				   ent.standardName = getStandardName(res);
				   if(ent.standardName.equals(""))
					   ent.standardName = ent.label;
				   res = checkCommentForXREFS(res);
			}
		}
		for(int j=0;j<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();j++){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(j);
			if(gene.getId().equals(ent.id)){
				//if(gene.getCelldesignerNotes()!=null)
				//   res = Utils.getValue(gene.get);
				//   res = Utils.replaceString(res, "Notes by CellDesigner", "");
				//   res = checkCommentForXREFS(res);
			}
		}	
		return res;
	}
	
	
	public String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames, boolean insertLinks){
		  String reactionString = "";
		  String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		  ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
		  for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
		    String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    	if(insertLinks)
		    		reactionString+="<a href='"+r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfReactants().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  if(lm!=null){
		  reactionString+=" - ";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
		    	if(insertLinks)		    	
		    		reactionString+="<a href='"+r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+=" - ";
		    }
		  }}
		  String reaction="-";
		  if(rtype.toLowerCase().indexOf("transcription")>=0)
			  reaction="--";
		  if(rtype.toLowerCase().indexOf("unknown")>=0)
			  reaction+="?";
		  if(rtype.toLowerCase().indexOf("inhibition")>=0)
			  reaction+="|";
		  else
			  reaction+=">";
		  if(rtype.toLowerCase().indexOf("transport")>=0)
			  reaction="-t->";
		  reaction = " "+reaction+" ";
		  reactionString +=reaction;
		  for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
		    String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
		    	if(insertLinks)		  
		    		reactionString+="<a href='"+r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfProducts().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  /*if(lm!=null){
		  reactionString+="+";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }}*/
		  return reactionString;
		}
	
	public static String checkCommentForXREFS(String comment){
		String res = "";
			  Vector refs = new Vector();
			  try{
				  
			  String dbid = "";
			  StringTokenizer st = new StringTokenizer(comment," >:;\r\n");
			  String s = "";
			  while(st.hasMoreTokens()){
			    String ss = st.nextToken();
			    //System.out.println(ss);
			   if(ss.toLowerCase().equals("pmid")){
			      if(st.hasMoreTokens()){
			        dbid = st.nextToken();
			        res+="<a target='_blank' href='http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&Cmd=ShowDetailView&TermToSearch="+dbid+"'>PMID:"+dbid+"</a> ";
			      }
			    }else
			    if(ss.toLowerCase().equals("hugo")){
				      if(st.hasMoreTokens()){
				        dbid = st.nextToken();
				        res+="<a target='_blank' href='http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&dopt=full_report&term="+dbid+"'>HUGO:"+dbid+"</a> ";
				      }
				    }else
			    if(ss.toLowerCase().equals("uniprot")){
				      while(st.hasMoreTokens()){
				        dbid = st.nextToken();
				        if(dbid.length()!=6){
				        	res+=dbid+" ";
				        	break;
				        }else
				        	res+="<a target='_blank' href='http://www.expasy.org/uniprot/"+dbid+"'>UNIPROT:"+dbid+"</a> ";
				      }
				    }
			    else
			    	res+=ss+" ";
			  }
			  
			  }catch(Exception e){
			    e.printStackTrace();
			  }
		return res;
	}
	
	public static String getStandardName(String comment){
		String res = "";
			  Vector refs = new Vector();
			  try{
				  
			  String dbid = "";
			  StringTokenizer st = new StringTokenizer(comment," >:;\r\n");
			  String s = "";
			  while(st.hasMoreTokens()){
			    String ss = st.nextToken();
			    //System.out.println(ss);
			    if(ss.toLowerCase().equals("hugo")){
				      if(st.hasMoreTokens()){
				        dbid = st.nextToken();
				        res+=dbid;
				      }
				    }
			  }
			  }catch(Exception e){
			    e.printStackTrace();
			  }
		return res;
	}
	
	
	public void generatePlacesForReaction(ReactionDocument.Reaction r){
		
		String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		//System.out.println(r.getId()+"\t"+rtype);
		
		Vector<Place> startPoints = new Vector<Place>();
		Vector<Place> endPoints = new Vector<Place>();
		Vector<Place> modPoints = new Vector<Place>();
		
		//System.out.println(r.getId());
		
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();i++){
			CelldesignerBaseReactantDocument.CelldesignerBaseReactant react = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				startPoints.add(point);
			}else{
				String spid = Utils.getValue(react.getSpecies());
				System.out.println("ERROR: no places found for alias "+alias+", species "+spid+" ("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd.getSbml(),spid,true,true)+")");
			}
		}
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();i++){
			CelldesignerBaseProductDocument.CelldesignerBaseProduct react = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				endPoints.add(point);
			}else{
				System.out.println("Place is not found for alias "+alias+" in generatePlacesForReaction");
			}
		}
		/*for(int i=0;i<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();i++){
			CelldesignerModificationDocument.CelldesignerModification cdm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(i);
			String alias = cdm.getAliases();
			Vector<Place> v = placeMap.get(alias);
			cdm.ge
		}*/
		
		
		Vector v = new Vector();
		
		if(rtype.equals("STATE_TRANSITION")||rtype.equals("TRANSPORT")||rtype.equals("TRANSCRIPTIONAL_INHIBITION")||rtype.equals("TRANSCRIPTIONAL_ACTIVATION")||rtype.equals("UNKNOWN_TRANSITION")||rtype.equals("KNOWN_TRANSITION_OMITTED")||rtype.equals("TRANSLATIONAL_INHIBITION")||rtype.equals("UNKNOWN_CATALYSIS")||rtype.equals("TRANSLATION")||rtype.equals("TRANSCRIPTION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			
			if(r.getAnnotation().getCelldesignerEditPoints()==null){
				Place place = new Place();
				place.id = r.getId();
				place.sbmlid = r.getId();
				place.label = getReactionString(r, cd, true, false);
				place.type = place.POLY;
				place.coords="";
				place.coords+=""+(int)(startPoints.get(0).x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale-linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale+linewidth)+",";			
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale-linewidth);
				place.positionx = 0.5f*(startPoints.get(0).x*scale+endPoints.get(0).x*scale);
				place.positiony = 0.5f*(startPoints.get(0).y*scale+endPoints.get(0).y*scale);
				v.add(place);
			}else{
				Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
				addPolyLine(startPoints.get(0).x, startPoints.get(0).y, endPoints.get(0).x, endPoints.get(0).y, points, r, v);
				/*points.add(endPoints.get(0));
				Place current = new Place();
				current.x = startPoints.get(0).x;
				current.y = startPoints.get(0).y;
				for(int j=0;j<points.size();j++){
					Place place = new Place();
					place.id = r.getId();
					place.sbmlid = r.getId();
					place.label = getReactionString(r, cd, true, false);
					place.type = place.POLY;
					if(j==points.size()-1){
						place.x = points.get(j).x;
						place.y = points.get(j).y;
					}
					else{
						place.x = startPoints.get(0).x+axis1.x*points.get(j).x+axis2.x*points.get(j).y;
						place.y = startPoints.get(0).y+axis1.y*points.get(j).x+axis2.y*points.get(j).y;
					}
					place.coords="";
					place.coords+=""+(int)(current.x-3)+","+(int)(current.y-3)+",";
					place.coords+=""+(int)(place.x-3)+","+(int)(place.y-3)+",";
					place.coords+=""+(int)(place.x+3)+","+(int)(place.y+3)+",";
					place.coords+=""+(int)(current.x+3)+","+(int)(current.y+3);
					current.x = place.x;
					current.y = place.y;
					v.add(place);
				}*/
			}
		}}else
		if(rtype.equals("HETERODIMER_ASSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>1)if(endPoints.size()>1){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(1).x,startPoints.get(1).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);			
		}}else
		if(rtype.equals("DISSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(1).x,endPoints.get(1).y,ep,r,v);			
		}}else
			System.out.println("In generatePlacesForReaction "+rtype+" not found for "+r.getId());
		
		if(v.size()>0)
			placeMap.put(r.getId(), v);
	}
	
	public void addPolyLine(float x1, float y1, float x2, float y2, Vector<Place> editpoints, ReactionDocument.Reaction r, Vector v){
		x1 = x1*scale;
		y1 = y1*scale;
		x2 = x2*scale;
		y2 = y2*scale;
		Place axis1 = new Place();
		Place axis2 = new Place();
		axis1.x = x2-x1;
		axis1.y = y2-y1;
		axis2.x = -axis1.y;
		axis2.y = axis1.x;
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(x1-linewidth)+","+(int)(y1-linewidth)+",";
		int n = 1;
		place.positionx+=x1;
		place.positiony+=y1;
		for(int i=0;i<editpoints.size();i++){
			float x = x1+axis1.x*editpoints.get(i).x+axis2.x*editpoints.get(i).y;
			float y = y1+axis1.y*editpoints.get(i).x+axis2.y*editpoints.get(i).y;
			place.coords+=""+(int)(x-linewidth)+","+(int)(y-linewidth)+",";
			place.positionx+=x;
			place.positiony+=y;
			n++;
		}
		place.coords+=""+(int)(x2-linewidth)+","+(int)(y2-linewidth)+",";
		place.coords+=""+(int)(x2+linewidth)+","+(int)(y2+linewidth)+",";
		place.positionx+=x2;
		place.positiony+=y2;
		n++;
		for(int i=editpoints.size()-1;i>=0;i--){
			place.coords+=""+(int)(x1+axis1.x*editpoints.get(i).x+axis2.x*editpoints.get(i).y+linewidth)+","+(int)(y1+axis1.y*editpoints.get(i).x+axis2.y*editpoints.get(i).y+linewidth)+",";
		}
		place.coords+=""+(int)(x1+linewidth)+","+(int)(y1+linewidth);
		place.positionx/=n;
		place.positiony/=n;
		v.add(place);
	}
	
	public Place getAbsolutePosition(float origx, float origy, Place axis1, Place axis2, float x, float y){
		Place place = new Place();
		place.x = origx+axis1.x*x+axis2.x*y;
		place.y = origy+axis1.y*x+axis2.y*y;
		return place;
	}
	
	public void addLineRelativeRelative(float origx, float origy, Place axis1, Place axis2, float x1, float y1, float x2, float y2, ReactionDocument.Reaction r, Vector v){
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1-linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1-linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x2+axis2.x*y2-linewidth)+","+(int)(origy+axis1.y*x2+axis2.y*y2-linewidth)+",";		
		place.coords+=""+(int)(origx+axis1.x*x2+axis2.x*y2+linewidth)+","+(int)(origy+axis1.y*x2+axis2.y*y2+linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1+linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1+linewidth);		
		v.add(place);
	}
	public void addLineRelativeAbsolute(float origx, float origy, Place axis1, Place axis2, float x1, float y1, float x2, float y2, ReactionDocument.Reaction r, Vector v){
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1-linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1-linewidth)+",";
		place.coords+=""+(int)(x2-linewidth)+","+(int)(y2-linewidth)+",";		
		place.coords+=""+(int)(x2+linewidth)+","+(int)(y2+linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1+linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1+linewidth);		
		v.add(place);
	}
	
	public Vector<Place> readEditPoints(String editS){
		Vector<Place> res = new Vector<Place>();
		StringTokenizer st = new StringTokenizer(editS," ,");
		while(st.hasMoreTokens()){
			Place pl = new Place();
			pl.x = Float.parseFloat(st.nextToken());
			pl.y = Float.parseFloat(st.nextToken());
			res.add(pl);
		}
		return res;
	}
	
	public void getCoordAxes(Vector<Place> starts, Vector<Place> ends, Place x1, Place x2,ReactionDocument.Reaction r){
		String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		if(rtype.equals("STATE_TRANSITION")||rtype.equals("TRANSPORT")||rtype.equals("TRANSCRIPTIONAL_INHIBITION")||rtype.equals("TRANSCRIPTIONAL_ACTIVATION")||rtype.equals("UNKNOWN_TRANSITION")||rtype.equals("KNOWN_TRANSITION_OMITTED")||rtype.equals("TRANSLATIONAL_INHIBITION")||rtype.equals("UNKNOWN_CATALYSIS")){		
		//if(rtype.equals("STATE_TRANSITION")||rtype.equals("TRANSPORT")||){
			x1.x = ends.get(0).x-starts.get(0).x;
			x1.y = ends.get(0).y-starts.get(0).y;
			x2.x = -x1.y;
			x2.y = x1.x;
			float x = -x1.y;
			float y = x1.x;
			//x2.x = x2.x/(float)Math.sqrt(x*x+y*y);
			//x2.y = x2.y/(float)Math.sqrt(x*x+y*y);
		}
		if(rtype.equals("HETERODIMER_ASSOCIATION")){
			x1.x = starts.get(1).centerx-starts.get(0).centerx;
			x1.y = starts.get(1).centery-starts.get(0).centery;
			x2.x = ends.get(0).centerx-starts.get(0).centerx;
			x2.y = ends.get(0).centery-starts.get(0).centery;
		}
		if(rtype.equals("DISSOCIATION")){
			x1.x = ends.get(0).centerx-starts.get(0).centerx;
			x1.y = ends.get(0).centery-starts.get(0).centery;
			x2.x = ends.get(1).centerx-starts.get(0).centerx;
			x2.y = ends.get(1).centery-starts.get(0).centery;
		}
	}
	
	public Place getAnchorPosition(Place place, String anchor){
		Place pl = new Place();
		if(anchor==null){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y+0.5f*place.height;
		}else{
		if(anchor.equals("E")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.5f*place.height;
		}
		if(anchor.equals("ENE")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.25f*place.height;
		}
		if(anchor.equals("NE")){
			pl.x = place.x+place.width;
			pl.y = place.y;
		}
		if(anchor.equals("ESE")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.75f*place.height;
		}
		if(anchor.equals("SE")){
			pl.x = place.x+place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("W")){
			pl.x = place.x;
			pl.y = place.y+0.5f*place.height;
		}
		if(anchor.equals("WNW")){
			pl.x = place.x;
			pl.y = place.y+0.25f*place.height;
		}
		if(anchor.equals("NW")){
			pl.x = place.x;
			pl.y = place.y;
		}
		if(anchor.equals("WSW")){
			pl.x = place.x;
			pl.y = place.y+0.75f*place.height;
		}
		if(anchor.equals("SW")){
			pl.x = place.x;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("N")){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("NNW")){
			pl.x = place.x+0.25f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("NNE")){
			pl.x = place.x+0.75f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("S")){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("SSW")){
			pl.x = place.x+0.25f*place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("SSE")){
			pl.x = place.x+0.75f*place.width;
			pl.y = place.y+place.height;
		}}
		return pl;
	}
	
	public void generatePartialMaps(String path, String pathname, String folder) throws Exception{
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			Object obj = CellDesigner.entities.get(spa.getSpecies());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){			
				species.put(spa.getSpecies(), spa);
			}
			Vector v = (Vector)speciesEntities.get(spa.getSpecies());
			if(v==null) v = new Vector();
			v.add(getEntity(spa.getSpecies()));
			speciesEntities.put(spa.getSpecies(),v);
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			Object obj = CellDesigner.entities.get(cspa.getSpecies());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){			
				species.put(cspa.getSpecies(), cspa);
			}
			Vector v = (Vector)speciesEntities.get(cspa.getSpecies());
			if(v==null) v = new Vector();
			v.add(getEntitiesInComplex(cspa.getSpecies()));
			speciesEntities.put(cspa.getSpecies(),v);
		}
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			if(r.getListOfReactants()!=null)
			for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfProducts()!=null)
			for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfModifiers()!=null)
			for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
				String spid = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
		}
		
		// for species
		Iterator it = species.keySet().iterator();
		int kk=0;
		for(int j=0;j<0;j++){
		//while(it.hasNext()){
			String id = (String)it.next();
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(id);
		    System.out.println(""+(kk++)+" "+Utils.getValue(sp.getName()));
			
			if(!Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED")){
			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
						
			Vector v = (Vector)speciesInReactions.get(sp.getId());
			if(v!=null)
				for(int i=0;i<v.size();i++){
					ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(i);
					reactions.add(r.getId());
				}
			
			System.out.println(reactions.size());
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();
			HashMap hm = CellDesigner.entities;
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+sp.getId()+".xml");
			CellDesigner.entities = hm;
			}
		}
		// for reactions
		//for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
		for(int i=0;i<0;i++){
			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			reactions.add(r.getId());
			HashMap hm = CellDesigner.entities;
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();			
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+r.getId()+".xml");
			CellDesigner.entities = hm;
		}
		// for proteins
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			Entity ent = (Entity)entities.get(prot.getId());
			if(ent!=null){

			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
			System.out.println((i+1)+"\t"+ent.label+"\t"+ent.id+"\t"+ent.species.size());			
			for(int j=0;j<ent.species.size();j++){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)ent.species.get(j);
				Vector v = (Vector)speciesInReactions.get(sp.getId());
				if(v!=null)
					for(int k=0;k<v.size();k++){
						ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(k);
						reactions.add(r.getId());
					}
			}
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();
			HashMap hm = CellDesigner.entities;
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+ent.id+".xml");
			CellDesigner.entities = hm;
			}else{
				System.out.println("=============================================");				
				System.out.println("ERROR: Protein id="+prot.getId()+" NOT FOUND!");
				System.out.println("=============================================");
			}
		}
	}
	
	public void readModules(String fn_species, String fn_proteins, String fn_pathways) throws Exception{
		LineNumberReader lr = new LineNumberReader(new FileReader(fn_species));
		String s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			module_species.put(mname, v);
			Collections.sort(v);
		}
		lr.close();
		lr = new LineNumberReader(new FileReader(fn_proteins));
		s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			module_proteins.put(mname, v);
			Collections.sort(v);
		}
		lr.close();
		lr = new LineNumberReader(new FileReader(fn_pathways));
		s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			pathways.put(mname, v);
			Collections.sort(v);
		}
		lr.close();		
	}
	
	public Vector getModuleName(HashMap modules, String id){
		Vector res = new Vector();
		Set keys = modules.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Vector v = (Vector)modules.get(key);
			if(v.indexOf(id)>=0){
				res.add(key);
			}
		}
		Collections.sort(res);
		return res;
	}
	
	public void makeCytoscapeMaps(String path, String pagefolder, String sizesfile) throws Exception{
		
		HashMap sizes = new HashMap();
		LineNumberReader lr = new LineNumberReader(new FileReader(sizesfile));
		String s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String name = st.nextToken();
			Place pl = new Place();
			pl.x = Float.parseFloat(st.nextToken());
			pl.y = Float.parseFloat(st.nextToken());
			pl.width = Float.parseFloat(st.nextToken());
			sizes.put(name, pl);
		}
		
		File f = new File(path);
		File modules[] = f.listFiles();
		for(int i=0;i<modules.length;i++){
			if(modules[i].getName().endsWith(".xgmml")){
				String name = modules[i].getName().substring(0,modules[i].getName().length()-6);
				if(name.endsWith(".xml"))
					name = name.substring(0,name.length()-4);
				GraphDocument grDoc = XGMML.loadFromXMGML(modules[i].getAbsolutePath());
				Graph graph = XGMML.convertXGMMLToGraph(grDoc);
				double minx = Double.POSITIVE_INFINITY;
				double miny = Double.POSITIVE_INFINITY;
				double maxx = -Double.POSITIVE_INFINITY;
				double maxy = -Double.POSITIVE_INFINITY;
				
				for(int j=0;j<grDoc.getGraph().getNodeArray().length;j++){
					GraphicNode n = grDoc.getGraph().getNodeArray()[j];
					if(n.getGraphics().getX()<minx) minx = n.getGraphics().getX();
					if(n.getGraphics().getY()<miny) miny = n.getGraphics().getY();
					if(n.getGraphics().getX()+n.getGraphics().getW()>maxx) maxx = n.getGraphics().getX()+n.getGraphics().getW();
					if(n.getGraphics().getY()+n.getGraphics().getH()>maxy) maxy = n.getGraphics().getY()+n.getGraphics().getH();
				}
				FileWriter fw = new FileWriter(path+"../"+name+"_cyto.html");
				fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+name+"_cyto.png\"><map name=\"immapdata\" href=\"\">\n");
				fw.write("<map name=\"immapdata\" href=\"\">\n");
				
				float imw = (float)(maxx-minx); float imh = (float)(maxy-miny);
				float shiftx = 0f;
				Place pl = (Place)sizes.get(name);
				if(pl!=null){
					imw = pl.x;
					imh = pl.y;
					shiftx = pl.width;
				}
				
				for(int j=0;j<grDoc.getGraph().getNodeArray().length;j++){
					GraphicNode n = grDoc.getGraph().getNodeArray()[j];
					double x = shiftx+(n.getGraphics().getX()-minx)*imw/(maxx-minx);
					double y = (n.getGraphics().getY()-miny)*imh/(maxy-miny);
					double w = n.getGraphics().getW();
					double h = n.getGraphics().getH();
					Vector v = ((Node)graph.Nodes.get(j)).getAttributesWithSubstringInName("CELLDESIGNER_SPECIES");
					String id = ((Attribute)v.get(0)).value;
					if(id==null){
						v = ((Node)graph.Nodes.get(j)).getAttributesWithSubstringInName("CELLDESIGNER_REACTION");
						id = ((Attribute)v.get(0)).value;
					}

					String coords = (int)x+","+(int)y+","+(int)(x+w)+","+(int)(y+h);
					fw.write("<area shape=\"RECT\" alt=\""+n.getLabel()+"\" coords=\""+coords+"\" href=\"../"+pagefolder+"/"+id+".html\" target='info' />\n");
				}
				fw.write("</map>\n");
				fw.close();
			}
		}
	}
	
	public static String correctName(String name){
		  name = Utils.replaceString(name," ","_");
		  name = Utils.replaceString(name,"*","_");
		  name = Utils.replaceString(name,"-","_");
		  name = Utils.replaceString(name,"[","_");
		  name = Utils.replaceString(name,"]","_");
		  name = Utils.replaceString(name,"__","_");
		  name = Utils.replaceString(name,"__","_");
		  name = Utils.replaceString(name,":","_");
		  name = Utils.replaceString(name,"/","_");
		  if(name.endsWith("_"))
		    name = name.substring(0,name.length()-1);
		  if(name.startsWith("_"))
			    name = name.substring(1,name.length());

		  byte mc[] = name.getBytes();
		  StringBuffer sb = new StringBuffer(name);
		  for(int i=0;i<mc.length;i++)
		    //System.out.println(name.charAt(i)+"\t"+mc[i]);
		    if(mc[i]<=0)
		      sb.setCharAt(i,'_');
		  return sb.toString();
		}
	
	public static void decomposeIntoModulesByTagValue(String filename, String tagName, String path) throws Exception{
		Vector species = new Vector();
		Vector speciesAliases = new Vector();
		Vector reactions = new Vector();
		Vector degraded = new Vector();
		
		SbmlDocument map = CellDesigner.loadCellDesigner(filename);
		CellDesigner.entities = CellDesigner.getEntities(map);
		
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		Graph graph = XGMML.convertXGMMLToGraph(c2c.getXGMMLGraph("temp", map.getSbml()));
		
		//XGMML.saveToXGMML(XGMML.convertGraphToXGMML(graph),filename+".xgmml");
		
		HashMap<String, Vector<String>> nodesList = new HashMap<String,Vector<String>>();
		HashMap<String, Vector<String>> speciesList = new HashMap<String,Vector<String>>();
		HashMap<String, Vector<String>> aliasesList = new HashMap<String,Vector<String>>();
		
		
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			Vector<String> v = n.getAttributeValues(tagName);
			for(int j=0;j<v.size();j++){
				StringTokenizer st = new StringTokenizer(v.get(j),"@@");
				while(st.hasMoreTokens()){
					String key = st.nextToken();
					key = Utils.replaceString(key, "/", "_");
					key = Utils.replaceString(key, "-", "_");
					Vector<String> vals = speciesList.get(key);
					if(vals==null) vals = new Vector<String>();
					String id = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
					if(id!=null){
						vals.add(id);
						speciesList.put(key, vals);
					}
					Vector<String> aliases = aliasesList.get(key);
					if(aliases==null) aliases = new Vector<String>();
					id = n.getFirstAttributeValue("CELLDESIGNER_ALIAS");
					if(id!=null){
						aliases.add(id);
						aliasesList.put(key, aliases);
					}
					Vector<String> nodes = nodesList.get(key);
					if(nodes==null) nodes = new Vector<String>();
					nodes.add(n.Id);
					nodesList.put(key, nodes);
				}
			}
		}
		
				
		Iterator<String> it = speciesList.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			SubnetworkProperties snp = new SubnetworkProperties();
			snp.subnetwork = new Graph();
			Vector<String> nodes = nodesList.get(key);
			//for(int i=0;i<nodes.size();i++)
			//	System.out.println(key+"\t"+nodes.get(i));
			for(int i=0;i<nodes.size();i++)
				snp.subnetwork.addNode(graph.getNode(nodes.get(i)));
			snp.subnetwork.addConnections(graph);
			snp.addFirstNeighbours(snp.subnetwork,graph,true);
			reactions.clear(); 
			for(int i=0;i<snp.subnetwork.Nodes.size();i++){
				Node n = snp.subnetwork.Nodes.get(i);
				String id = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
				if(id!=null){
					//System.out.println("REACTION +"+id);
					reactions.add(id);
				}
			}
			SbmlDocument cd = CellDesigner.loadCellDesigner(filename);
			CellDesigner.entities = CellDesigner.getEntities(cd);
			species = speciesList.get(key);
			speciesAliases = aliasesList.get(key);
			CytoscapeToCellDesignerConverter.filterIDsCompleteReactions
				(cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(cd, path+"/"+key+".xml");
		}
		
		/*HashMap<String, Vector<String>> entityList = new HashMap<String,Vector<String>>();
		
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein protein = map.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String notes = Utils.getValue(protein.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(protein.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = map.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String notes = Utils.getValue(gene.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(gene.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA rna = map.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String notes = Utils.getValue(rna.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(rna.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = map.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				Vector<String> ents = entityList.get(id);
				if(ents.size()>0)
				for(int j=0;j<)
			}
		}*/
	}
	
	public void createClickableMap(){
		try{

			/*CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
			findAllPlacesInCellDesigner();
			updateStandardNames();

			createModuleMaps();

			String dirP = "pages";
			CellDesigner.entities = CellDesigner.getEntities(cd);
			System.out.println("Generating pages...");
			generatePages(path, dirP, "modules", name, "maps");
			return;*/
			
			/*makeScriptString();
			makeMenu(); 
			FileWriter fw = new FileWriter(path+"menu.html"); fw.write(menu); fw.close();
			makeFrame();
					   fw = new FileWriter(path+"index.html"); fw.write(frame); fw.close();*/
			
			String dirPages = "pages"; 
			String dirXmls  = "xmls";
			
			File f = new File(path+dirPages);
			f.mkdir();
			f = new File(path+modulefolder);
			f.mkdir();			
			
			System.out.println("Loading CellDesigner...");
			loadCellDesigner(path+name+".xml");
			//clMap.readModules(path+"modules_species.gmt", path+"modules_proteins.gmt", path+"broad_netpath.gmt");			
			// Generate scaled xmls - small and tiny
			CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
			findAllPlacesInCellDesigner();
			updateStandardNames();

			convertXMLsInFolderToImageMaps(path,subfolder);
			
			createModuleMaps();

			CellDesigner.entities = CellDesigner.getEntities(cd);
			System.out.println("Generating pages...");
			generatePages(path, dirPages, "modules", name, "maps");
			//String mapString = generateMapFile(path, name, dirPages);
			fullListOfEntities(path+dirPages+"/"+name+"_list.html");
			
			f = new File(path+partialfolder);
			f.mkdir();
			
			CellDesigner.entities = CellDesigner.getEntities(cd);
			generatePartialMaps(path, name, partialfolder);
			convertXMLsInFolderToImageMaps(path+partialfolder+"/","../"+subfolder);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void makeScriptString(){
		script = "<script>\n";
		script+="window.onscroll = saveCoordsTiny;\n";
		script+="window.onload = scrollToSavedTiny;\n";
		script+="var disableSaving = 0;\n";
		script+="function showLabel(id){\n";
		script+="d = top.menu.document.getElementById(\"showhide\");\n";
		script+="d.innerHTML = '<small>'+id+'</small>';\n";
		script+="}\n";
		script+="function saveCoordsTiny()\n";
		script+="{\n";
		script+="var scrollX, scrollY;\n";
		script+="if(document.all)\n";
		script+="{\n";
		script+="if (!document.documentElement.scrollLeft)\n";
		script+="scrollX = document.body.scrollLeft;\n";
		script+="else\n";
		script+="scrollX = document.documentElement.scrollLeft;\n";
		script+="if (!document.documentElement.scrollTop)\n";
		script+="scrollY = document.body.scrollTop;\n";
		script+="else\n";
		script+="scrollY = document.documentElement.scrollTop;\n";
		script+="}\n";   
		script+="else\n";
		script+="{\n";
		script+="scrollX = window.pageXOffset;\n";
		script+="scrollY = window.pageYOffset;\n";
		script+="}\n";
		script+="if(scrollX>0)if(scrollY>0)if(disableSaving==0){\n";
		script+="top.menu.document.forms['coords'].xCoordHolder.value = scrollX/0.3;\n";
		script+="top.menu.document.forms['coords'].yCoordHolder.value = scrollY/0.3;\n";
		script+="}\n";
		script+="}\n";
		script+="function scrollToSavedTiny(){\n";
		script+="disableSaving = 1;\n";
		script+="window.scrollTo(top.menu.document.forms['coords'].xCoordHolder.value*0.3,top.menu.document.forms['coords'].yCoordHolder.value*0.3);\n";
		script+="top.menu.document.forms['coords'].scale.value = 1;\n";
		script+="top.menu.document.forms['coords'].suffix.value = \"_small\";\n";
		script+="disableSaving = 0;\n";
		script+="}\n";
		script+="function getSize() {\n";
		script+="var result = {height:0, width:0};\n";
		script+="if (parseInt(navigator.appVersion)>3) {\n";
		script+="if (navigator.appName==\"Netscape\") {\n";
		script+="result.width = top.map.innerWidth-16;\n";
		script+="result.height = top.map.innerHeight-16;\n";
		script+="}\n";
		script+="if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n";
		script+="result.width = top.map.document.body.offsetWidth-20;\n";
		script+="result.height = top.map.document.body.offsetHeight-20;\n";
		script+="}\n";
		script+="}\n";
		script+="return result;\n";
		script+="}\n";
		script+="</script>\n";
	}
	
	public void makeMenu(){
		menu = "<html>\n";
		menu+= "<body>\n";
		menu+= "<b>"+title+" pathway diagram:</b> (<a href=\"diagram_help.html\" target=\"info\">help</a>)<b>:</b> \n"; 
		menu+= "<font size=+1><a href=\""+name+".html\" target=\"map\">DETAILED</a></font> -\n"; 
		menu+= "<font size=-1><a href=\""+name+"_compressed.html\" target=\"map\">COMPRESSED</a></font> -\n"; 
		menu+= "<font size=+0><a href=\""+name+"_structural.html\" target=\"map\">StRuCtUrAl</a></font> - \n"; 
		menu+= "<font size=+0><a href=\""+name+"_modular.html\" target=\"map\"><b>modular</b></a></font>\n";
		menu+= "&nbsp;&nbsp;\n"; 
		menu+= "<b>Panel:</b>\n"; 
		menu+= "<a href=\"pages/"+name+"_list.html\" target=\"info\">all entities</a>\n"; 
		menu+= "&nbsp;&nbsp;\n";
		menu+= "<b>Element:</b>\n"; 
		menu+= "<span id=\"showhide\"></span>\n";
		menu+= "<form id='coords'>\n";
		menu+= "<INPUT type=\"hidden\" id=\"xCoordHolder\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"yCoordHolder\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"scale\" value=\"1\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"suffix\" value=\"\">\n";
		menu+= "</form>\n";
		menu+= "<script>\n";
		menu+= "window.onload = checkBrowser;\n";
		menu+= "function checkBrowser(){if (navigator.appName.indexOf(\"Microsoft\")>=0) {alert(\"WARNING: The map does not work correctly with Internet Explorer browser!\n Please use Firefox or Google Chrome instead.\");}}\n\n";
		menu+= "function getCoordsX()\n";
		menu+= "{\n";
		menu+= "return document.forms['coords'].xCoordHolder.value;\n";
		menu+= "}\n";
		menu+= "function getCoordsY()\n";
		menu+= "{\n";
		menu+= "return document.forms['coords'].yCoordHolder.value;\n";
		menu+= "}\n";
		menu+= "</script>\n";
		menu+= "</body>\n";
		menu+= "</html>\n";		
	}
	
	public void makeFrame(){
		frame = "<html>\n";
		frame+="<frameset rows=\"40,100%\">\n";
		frame+="<frame src=\"menu.html\" name=\"menu\">\n";
		frame+="<FRAMESET COLS=\"80%,20%\">\n"; 
		frame+="<FRAME SRC=\""+name+".html\" NAME=\"map\">\n";
		frame+="<FRAME SRC=\"pages/"+name+"_list.html\"\n";
		frame+="NAME=\"info\">\n";
		frame+="</FRAMESET>\n"; 
		frame+="</FRAMESET>\n"; 
		frame+="</html>\n";
	}
	
	public void convertXMLsInFolderToImageMaps(String folder,String pathToPages) throws Exception{
		System.out.println("Creating image maps in folder "+folder+"...");
		File f = new File(folder);
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++)if(files[i].getName().toLowerCase().endsWith("xml")){
			ProduceClickableMap clMap = new ProduceClickableMap();
			clMap.name = files[i].getName().substring(0,files[i].getName().length()-4);
			clMap.path = folder;
			File fh = new File(clMap.path+clMap.name+".html");
			if(!fh.exists()){
				clMap.loadCellDesigner(files[i].getAbsolutePath());
				clMap.findAllPlacesInCellDesigner();
				clMap.makeScriptString();
				String mapString = clMap.generateMapFile(clMap.path, clMap.name, pathToPages);
				System.out.println(files[i].getName()+"\t"+mapString.length());
			}
		}
	}
	
	public void createModuleMaps() throws Exception{
		File fd = new File(this.path+"/"+this.modulefolder);
		fd.mkdir();
		fd = new File(this.path+"/"+this.modulefolder+"/"+this.subfolder);
		fd.mkdir();
		File f = new File(this.path+"modules.txt");
		if(f.exists()){
			// read map of module names
			Vector<String> vs = Utils.loadStringListFromFile(f.getAbsolutePath());
			for(int i=0;i<vs.size();i++){
				StringTokenizer st = new StringTokenizer(vs.get(i),"\t");
				speciesModularModuleNameMap.put(st.nextToken(), st.nextToken());
			}
			// correct modular imagemap (change species ids to module names)
			String modularMapFileName = path+name+"_modular.html";
			String modularMap = Utils.loadString(modularMapFileName);
			Iterator<String> keys = speciesModularModuleNameMap.keySet().iterator();
			while(keys.hasNext()){
				String species = keys.next(); String moduleName = speciesModularModuleNameMap.get(species);
				modularMap = Utils.replaceString(modularMap,this.subfolder+"/"+species+".html",this.modulefolder+"/"+this.subfolder+"/"+moduleName+".html");
			}
			FileWriter fw = new FileWriter(modularMapFileName);
			fw.write(modularMap+"\n");
			fw.close();
			// read all xmls and fill module_species and module_proteins
			keys = speciesModularModuleNameMap.keySet().iterator();
			while(keys.hasNext()){
				String species = keys.next(); String moduleName = speciesModularModuleNameMap.get(species);
				SbmlDocument cd = CellDesigner.loadCellDesigner(this.path+"/"+this.modulefolder+"/"+moduleName+"_c.xml");
				Vector<String> allspecies = new Vector<String>();
				Vector<String> allproteins = new Vector<String>(); 
				Vector<String> allproteinnames = new Vector<String>();
				for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
					SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
					allspecies.add(sp.getId());
				}
				for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
					CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
					allproteins.add(p.getId());
					allproteinnames.add(Utils.getValue(p.getName()));
				}
				for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
					CelldesignerGeneDocument.CelldesignerGene g = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					allproteins.add(g.getId());
					allproteinnames.add(g.getName());
				}
				module_species.put(moduleName, allspecies);
				module_proteins.put(moduleName, allproteins);
				module_protein_names.put(moduleName, allproteinnames);
			}
			// get names of modules from their ids
			SbmlDocument cd = CellDesigner.loadCellDesigner(this.path+"/"+name+"_modular.xml");
			for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
				SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String moduleId = speciesModularModuleNameMap.get(sp.getId());
				module_names.put(moduleId, Utils.getValue(sp.getName()));
			}
			// generate module description pages
			generateModulePages();
			// generate image maps for modules
			convertXMLsInFolderToImageMaps(path+modulefolder+"/","../"+subfolder);
		}else{
			System.out.println("No file modules.txt found! CAN NOT create modules!");
		}
	}
	
	public void generateModulePages() throws Exception{
		Iterator keys = module_species.keySet().iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname);
			FileWriter fw = new FileWriter(path+"/"+this.modulefolder+"/"+this.subfolder+"/"+mnamec+".html");
			fw.write("<h3>Module</h3>\n");
			
			fw.write("<b><font color=blue>"+module_names.get(mname)+"</font></b>\n");
			
			//fw.write("<br><small><a href='../"+mnamec+".html' target='map' onClick='fname=\""+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".html\";top.map.window.location=fname''>(g.layout)</a></small> - ");
			fw.write("<br><small><a href='../"+mnamec+"_c.html' target='map'>(compact)</a></small>");
			
			fw.write("<hr><font color=red>Entities involved:</font><br>\n");
			
			/*Vector v = new Vector();
			HashMap entitiesMap = new HashMap();
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
				v.add(Utils.getValue(prot.getName()));
				entitiesMap.put(Utils.getValue(prot.getName()),prot);
			}
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
				CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
				v.add(gene.getName()+" (gene)");
				entitiesMap.put(gene.getName()+" (gene)",gene);
			}*/
			Vector<String> vv = module_proteins.get(mname);
			Vector<String> vvn = module_protein_names.get(mname);
			fw.write("<ol>\n");
			for(int i=0;i<vv.size();i++){
				String ename = vvn.get(i);
				String eid = vv.get(i);
				/*if(entitiesMap.get(ename)!=null){
					eid = ((CelldesignerProteinDocument.CelldesignerProtein)entitiesMap.get(ename)).getId();
				}
				if(entitiesMap.get(ename+" (gene)")!=null){
					eid = ((CelldesignerGeneDocument.CelldesignerGene)entitiesMap.get(ename+" (gene)")).getId();
				}*/
				if(eid!=null){
					fw.write("<li><a href='../../"+subfolder+"/"+eid+".html'>"+ename+"</a>\n");
				}
			}
			fw.write("</ol>\n");
			fw.close();
		}		
	}
	
	public void printAnnotation(SbmlDocument cd){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		updateStandardNames();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			if(annot!=null){			
				System.out.println("### "+p.getId());
				System.out.println("### "+Utils.getValue(p.getName()));
				System.out.println(annot);
				System.out.println();
			}
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			if(annot!=null){			
				System.out.println("### "+p.getId());
				System.out.println("### "+p.getName());
				System.out.println(annot);
				System.out.println();
			}
		}		
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String annot = Utils.getValue(r.getNotes());
			if(annot!=null){			
				System.out.println("### "+r.getId());
				System.out.println("### "+this.getReactionString(r, cd, true, false));
				System.out.println(annot);
				System.out.println();
			}
		}
	}
	
	public void importAnnotations(SbmlDocument cd, String annotations) throws Exception{
		CellDesigner.entities = CellDesigner.getEntities(cd);
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		updateStandardNames();
		LineNumberReader lr = new LineNumberReader(new StringReader(annotations));
		String s = null;
		String currentEntity = null;
		String currentAnnotation = null;
		while((s=lr.readLine())!=null){
			if(s.startsWith("###")){
				if(currentEntity!=null){
					// import annotation text for a current entity
					CellDesigner.entities.get(currentEntity);
				}
				StringTokenizer st = new StringTokenizer(s,"\t");
				st.nextToken(); currentEntity = st.nextToken(); currentAnnotation = "";
				lr.readLine();
			}else{
				currentAnnotation+=s+"\n";
			}
		}
	}
	
	
	public void detectSectionsInAnnotations(){
		//for(int i=0)
	}
	
	
}