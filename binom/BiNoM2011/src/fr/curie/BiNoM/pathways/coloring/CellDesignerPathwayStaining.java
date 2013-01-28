package fr.curie.BiNoM.pathways.coloring;

import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument.CelldesignerGene;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument.CelldesignerProtein;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument.CelldesignerRNA;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class CellDesignerPathwayStaining {
	
	
	/**
	 * @param args
	 */
	
	public float width = 0;
	public float height = 0;
	public float gridStepX = 25;
	public float gridStepY = 25;
	float windowSizeX = 200;
	float windowSizeT = 200;
	
	public float infradius = 0.01f;
	public float thresholdGradient = 2f;
	public boolean normalizeColumnValues = true; 
	public boolean useModuleDefinitionsFromCellDesignerFile = true;
	public boolean useProteinNameIfHUGOisntFound = true;
	
	public SimpleTable table = null;
	
	public BufferedImage staining = null;
	
	public Vector<ColorPoint> aliaspoints = new Vector<ColorPoint>();
	public Vector<ColorPoint> regularpoints = new Vector<ColorPoint>();
	
	HashMap<String, Vector<String>> groupNameToProteinList = new HashMap<String, Vector<String>>();
	HashMap<String, Vector<String>> proteinToGroupList = new HashMap<String, Vector<String>>();

	HashMap<String,Color> groupColors = new HashMap<String,Color>();
	
	SbmlDocument cd = null;

	
	
	public class ColorPoint {
		float x = 0;
		float y = 0;
		Vector<String> groups = new Vector<String>();
		Color color = new Color(0f, 0f, 0f);
	}
		
	public static void main(String[] args) {
		try{
			
			String prefix = "c:/datas/colormaps/";
				
			CellDesignerPathwayStaining cm = new CellDesignerPathwayStaining();
			
			//cm.run(prefix+"rbe2f.xml", prefix+"rbe2f.png", prefix+"data.txt", prefix+"modules_proteins_c2_lc.gmt");
			//cm.run(prefix+"rbe2f.xml", null, prefix+"data.txt", prefix+"modules_proteins_c2_lc.gmt");
			//cm.run(prefix+"rbe2f.xml", null, prefix+"expression.txt", null);
			//cm.run(prefix+"rbe2f.xml", null, null, null);
			//cm.run(prefix+"rbe2f.xml", prefix+"rbe2f.png", null, null);
			//cm.run(prefix+"rbe2f.xml", null, null, prefix+"modules_proteins_c2_lc.gmt");
			//cm.run(prefix+"rbe2f.xml", prefix+"rbe2f.png", null, prefix+"modules_proteins_c2_lc.gmt");
			//cm.run(prefix+"M-Phase2.xml", prefix+"M-Phase2.png", null, null);
			cm.run(prefix+"dnarepair.xml", null, null, null);
			System.exit(0);
			
			
			//cm.loadMap(prefix+"rbe2f.xml");
			cm.loadMap(prefix+"dnarepair.xml");
			//cm.extractGMTFromCellDesigner(prefix+"dnarepair_modules.gmt");
			//System.exit(0);
			//cm.loadMap(prefix+"test1.xml");
			//cm.loadGroupsOfProteins(prefix+"modules_proteins_c2_lc.gmt");
			cm.loadGroupsOfProteins(prefix+"dnarepair_modules.gmt");
			//cm.loadGroupsOfProteins(prefix+"dnarepair_proteins.gmt");			
			//cm.loadGroupsOfProteins(prefix+"proteins.gmt");
			cm.createPoints();
			cm.makeRegularPoints();

			//cm.assignRandomColorsToGroups(true);

			//cm.assignColorsToGroupsFromTable("c:/datas/colormaps/diffgenes_basal_lum.txt","symbol","t_test_Basal_Luminaux");
			//cm.assignColorsToGroupsFromTable("c:/datas/colormaps/module_activities.txt","symbol","activity");
			//cm.assignColorsToGroupsFromTable("c:/datas/colormaps/module_activities_patients.txt","symbol","506");
			//cm.assignColorsToGroupsFromTable("c:/datas/breastcancer/IVOIRE/cghTable.txt","HUGO","11155_10090");
			cm.loadDataTable("c:/datas/breastcancer/IVOIRE/summary_cgh_noXYM.txt","HUGO");
			cm.assignColorsToGroupsFromTable("SUM_ALT");			
			 
			
			cm.assignColorsToPoints(cm.regularpoints);
			
			cm.paintStaining(prefix+"test.png", prefix+"dnarepair_s11895.png");
			
			cm.writeListOfPoints(cm.regularpoints, prefix+"coords.txt",prefix+"labels.txt",prefix+"colors.txt");
			cm.writeListOfPoints(cm.aliaspoints, prefix+"coords1.txt",prefix+"labels1.txt",null);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(String CellDesignerFileName, String PngFileName, String DataTableFileName, String ProteinGroupFileName){

		loadMap(CellDesignerFileName);
		
		if(ProteinGroupFileName==null){
			if(useModuleDefinitionsFromCellDesignerFile){
				String fn = CellDesignerFileName.substring(0,CellDesignerFileName.length()-4);
				if((new File(fn+".gmt").exists()))
					fn = fn+"_new.gmt";
				else
					fn = fn+".gmt";
				HashMap<String, Vector<String>> groups = extractGMTFromCellDesigner(fn);
				if(groups.size()!=0)
					ProteinGroupFileName = fn;
			}
		}
		
		if(ProteinGroupFileName==null){
			createPoints();
			loadGroupsOfProteins(ProteinGroupFileName);
		}else{
			loadGroupsOfProteins(ProteinGroupFileName);
			createPoints();	
		}
		if(DataTableFileName!=null){
			loadDataTable(DataTableFileName);
			makeRegularPoints();
			for(int i=1;i<table.fieldNames.length;i++){
				assignColorsToGroupsFromTable(table.fieldNames[i]);
				assignColorsToPoints(regularpoints);
				paintStaining(CellDesignerFileName.substring(0,CellDesignerFileName.length()-4)+"_"+Utils.correctName(table.fieldNames[i])+".png",PngFileName);
			}
		}else{
			makeRegularPoints();
			if(ProteinGroupFileName==null) assignRandomColorsToGroups(false);
			assignColorsToPoints(regularpoints);
			paintStaining(CellDesignerFileName.substring(0,CellDesignerFileName.length()-4)+"_staining.png",PngFileName);
		}
	}
	
	public void loadMap(String fn){
		cd = CellDesigner.loadCellDesigner(fn);
		CellDesigner.entities = CellDesigner.getEntities(cd);
		width = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		height = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());		
	}
	
	public void determineSizeOftheMap(){
		width = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeX());
		height = Integer.parseInt(cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().getSizeY());
	}
	
	public void loadGroupsOfProteins(String fn){
		if(fn==null){
			Vector<String> allproteins = new Vector<String>();
			for(int i=0;i<aliaspoints.size();i++){
				ColorPoint point = aliaspoints.get(i);
				for(int j=0;j<point.groups.size();j++){
					if(!allproteins.contains(point.groups.get(j)))
						allproteins.add(point.groups.get(j));
				}
			}
			Collections.sort(allproteins);
			System.out.println("TOTAL NUMBER OF PROTEINS = "+allproteins.size());
			for(int i=0;i<allproteins.size();i++){
				System.out.println(allproteins.get(i));
				Vector<String> v = new Vector<String>();
				v.add(allproteins.get(i));
				groupNameToProteinList.put(allproteins.get(i), v);
				proteinToGroupList.put(allproteins.get(i), v);
			}
		}else{
		try{
			LineNumberReader lr = new LineNumberReader(new FileReader(fn));
			String s = null;
			Vector<String> allproteins = new Vector<String>();
			while((s=lr.readLine())!=null){
				StringTokenizer st = new StringTokenizer(s,"\t");
				String groupName = st.nextToken();
				String colorString = st.nextToken();
				Vector<String> proteins = new Vector<String>();
				while((st.hasMoreTokens())){
					String protein = st.nextToken();
					proteins.add(protein);
					if(!allproteins.contains(protein))
						allproteins.add(protein);	
				}
				//System.out.println(groupName+" CONTAIN "+proteins.size()+" PROTEINS");				
				groupNameToProteinList.put(groupName, proteins);
				groupColors.put(groupName, getColorFromString(colorString));
			}
			Collections.sort(allproteins);
			System.out.println("TOTAL NUMBER OF PROTEINS:"+allproteins.size());
			for(int i=0;i<allproteins.size();i++){
				String protein = allproteins.get(i);
				Vector<String> groups = new Vector<String>();	
				Iterator<String> it = groupNameToProteinList.keySet().iterator();
				while(it.hasNext()){
					String group = it.next();
					Vector<String> names = groupNameToProteinList.get(group);
					if(names.contains(protein))
						if(!groups.contains(group))
							groups.add(group);
				}
				Collections.sort(groups);
				//System.out.println(protein+" PARTICIPATES IN "+groups.size()+" GROUPS");
				proteinToGroupList.put(protein, groups);
				System.out.println(protein+"\tna\t"+protein);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}}
	}
	
	public void createPoints(){
		for(int i=0; i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAlias alias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(!(CellDesigner.entities.get(alias.getSpecies())).getClass().getName().contains("CelldesignerSpeciesDocument")){
			String spclass = Utils.getValue(((SpeciesDocument.Species)CellDesigner.entities.get(alias.getSpecies())).getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			if(!spclass.equals("DEGRADED")){
				System.out.print(alias.getId()+":");
				Vector<String> hugos = getSpeciesHugos(alias);
				ColorPoint cp = new ColorPoint();
				cp.x = Float.parseFloat(alias.getCelldesignerBounds().getX())+Float.parseFloat(alias.getCelldesignerBounds().getW())*0.5f;
				cp.y = height - (Float.parseFloat(alias.getCelldesignerBounds().getY())+Float.parseFloat(alias.getCelldesignerBounds().getH())*0.5f);
				addGroupToPoint(cp,hugos);
				if(cp.groups.size()>0)
					aliaspoints.add(cp);
			}}}
		for(int i=0; i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAlias calias = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				System.out.print(calias.getId()+":");
				Vector<String> hugos = getSpeciesHugos(calias);
				ColorPoint cp = new ColorPoint();
				cp.x = Float.parseFloat(calias.getCelldesignerBounds().getX())+Float.parseFloat(calias.getCelldesignerBounds().getW())*0.5f;
				cp.y = height - (Float.parseFloat(calias.getCelldesignerBounds().getY())+Float.parseFloat(calias.getCelldesignerBounds().getH())*0.5f);
				addGroupToPoint(cp,hugos);
				if(cp.groups.size()>0)
					aliaspoints.add(cp);
			}
	}
	
	public void addGroupToPoint(ColorPoint cp, Vector<String> hugos){
		Vector<String> groups = new Vector<String>();
		for(int j=0;j<hugos.size();j++){
			Vector<String> g = proteinToGroupList.get(hugos.get(j));
			if(g!=null){
			for(int k=0;k<g.size();k++)
				if(!groups.contains(g.get(k)))
					groups.add(g.get(k));
			}else{
				if(proteinToGroupList.size()==0)
					if(!groups.contains(hugos.get(j)))
						groups.add(hugos.get(j));
			}
		}
		Collections.sort(groups);
		cp.groups = groups;
		System.out.print("\tGROUP:");
		for(int j=0;j<groups.size();j++)
			System.out.print(groups.get(j)+"\t");
		System.out.println();
	}
	
	public Vector<String> getSpeciesHugos(CelldesignerSpeciesAlias alias){
		Vector<String> hugos = new Vector<String>();
		String spid = alias.getSpecies();
		System.out.println(spid+"\t");
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(spid);
			hugos = getHugosFromCellDesignerIdentity(sp.getAnnotation().getCelldesignerSpeciesIdentity());
		System.out.print("HUGO: ");
		for(int i=0;i<hugos.size();i++)
			System.out.print(hugos.get(i)+"\t");
		System.out.print("\n");
		return hugos;
	}
	
	public Vector<String> getSpeciesHugos(CelldesignerComplexSpeciesAlias calias){
		Vector<String> hugos = new Vector<String>();
		String spid = calias.getSpecies();
		System.out.println(spid+"\tCOMPLEX");
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(spid);
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
				CelldesignerSpecies cis = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
				if(Utils.getValue(cis.getCelldesignerAnnotation().getCelldesignerComplexSpecies()).equals(sp.getId())){
					Vector<String> hugos1 = getHugosFromCellDesignerIdentity(cis.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity());
					for(int k=0;k<hugos1.size();k++)
						if(!hugos.contains(hugos1.get(k)))
							hugos.add(hugos1.get(k));
				}
			}
		Collections.sort(hugos);
		System.out.print("HUGO: ");
		for(int i=0;i<hugos.size();i++)
			System.out.print(hugos.get(i)+"\t");
		System.out.print("\n");
		return hugos;
	}
	
	public Vector<String> getHugosFromCellDesignerIdentity(CelldesignerSpeciesIdentity csi){
		Vector<String> hugos = new Vector<String>();
		String spclass = Utils.getValue(csi.getCelldesignerClass());
		String annotation = null;
		String name = null;
		if(spclass.equals("PROTEIN")){
			CelldesignerProteinDocument.CelldesignerProtein p = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get(Utils.getValue(csi.getCelldesignerProteinReference()));
			//System.out.println(p.getId());
			//System.out.println(p.getCelldesignerNotes());
			if(p.getCelldesignerNotes()!=null)
				annotation = Utils.getText(p.getCelldesignerNotes());
			name = ""+Utils.getValue(p.getName());
			if(name==null) System.out.println("Name = null for protein "+p.getId());			
			//System.out.println(annotation);
		}
		if(spclass.equals("GENE")){
			CelldesignerGeneDocument.CelldesignerGene p = (CelldesignerGeneDocument.CelldesignerGene)CellDesigner.entities.get(Utils.getValue(csi.getCelldesignerGeneReference()));
			//System.out.println(p.getId());
			if(p.getCelldesignerNotes()!=null)
				annotation = Utils.getText(p.getCelldesignerNotes());
			name = ""+p.getName();			
			if(name==null) System.out.println("Name = null for gene "+p.getId());			
			//System.out.println(p.getId()); System.out.println(annotation);
		}
		if(spclass.equals("RNA")){
			CelldesignerRNADocument.CelldesignerRNA p = (CelldesignerRNADocument.CelldesignerRNA)CellDesigner.entities.get(Utils.getValue(csi.getCelldesignerRnaReference()));
			if(p.getCelldesignerNotes()!=null)
				annotation = Utils.getText(p.getCelldesignerNotes());
			name = ""+p.getName();
			if(name==null) System.out.println("Name = null for rna "+p.getId());
			//System.out.println(p.getId()); System.out.println(annotation);			
		}
		if(annotation!=null){
			hugos = getTagValues(annotation,"HUGO");
		}
		if(hugos.size()==0)
			if(this.useProteinNameIfHUGOisntFound)
				if(name!=null)
					hugos.add(name);
		return hugos;
	}
	
	public Vector<String> getTagValues(String notes, String tag){
		Vector<String> hugos = new Vector<String>();
		StringTokenizer st = new StringTokenizer(notes," ,;\n");
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			if(s.startsWith(tag+":")){
				StringTokenizer st1 = new StringTokenizer(s,":");
				st1.nextToken();
				try{
				if(st1.hasMoreTokens()){
				String hugo = st1.nextToken();
				if(!hugos.contains(hugo))
					hugos.add(hugo);
				}
				}catch(Exception e){
					System.out.println("ERROR in "+s);
					e.printStackTrace();
				}
			}
		}
		return hugos;
	}
	
	public void writeListOfPoints(Vector<ColorPoint> points, String fncoordinates, String fnlabels, String fncolors){
		try{
			FileWriter fwc = new FileWriter(fncoordinates);
			for(int i=0;i<points.size();i++)
				fwc.write(points.get(i).x+"\t"+points.get(i).y+"\n");
			fwc.close();
			FileWriter fwl = new FileWriter(fnlabels);
			for(int i=0;i<points.size();i++){
				Vector<String> groups = points.get(i).groups;
				String label = "";
				for(int j=0;j<groups.size();j++)
					label+=groups.get(j)+",";
				int cut = -1;
				if(label.length()==0) cut=0;
				fwl.write(label.substring(0,label.length()+cut)+"\n");
			}
			fwl.close();
			if(fncolors!=null){
			FileWriter fwcl = new FileWriter(fncolors);
			for(int i=0;i<points.size();i++)
				fwcl.write((float)points.get(i).color.getRed()/256f+"\t"+(float)points.get(i).color.getGreen()/256f+"\t"+(float)points.get(i).color.getBlue()/256f+"\n");
			fwcl.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void makeRegularPoints(){
		for(float x=0;x<width;x+=gridStepX)
			for(float y=0;y<height;y+=gridStepY){
				int imin = -1;
				float distmin = Float.MAX_VALUE;
				for(int i=0;i<aliaspoints.size();i++){
					float px = aliaspoints.get(i).x;
					float py = aliaspoints.get(i).y;
					float dist = (px-x)*(px-x)/width/width+(py-y)*(py-y)/height/height;
					if(dist<distmin){
						distmin = dist;
						imin = i;
					}
				}
				ColorPoint p = new ColorPoint();
				p.x = x;
				p.y = y;
				if(distmin<infradius){
					p.groups = aliaspoints.get(imin).groups;
				}
				regularpoints.add(p);
			}
	}
	
	public void assignRandomColorsToGroups(boolean redgreen){
		Random r = new Random();	
		Iterator<String> it = groupNameToProteinList.keySet().iterator();
		if(!redgreen){
		while(it.hasNext()){
			String key = it.next();
			Color color = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
			groupColors.put(key, color);
		}
		}else{
			float randoms[] = new float[groupNameToProteinList.size()];
			for(int k=0;k<randoms.length;k++)
				randoms[k] = 2*r.nextFloat()-1;
			Vector<Color> colors = makeRedGreenColorFromNumbers(randoms, thresholdGradient);
			int k=0;
			while(it.hasNext()){
				String key = it.next();
				groupColors.put(key, colors.get(k++));				
			}
		}
	}
	
	public Color getColorFromString(String colorString){
		Color res = null;
		Random r = new Random();	
		res = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());		
		//if(colorString.startsWith("[")&&colorString.endsWith("]")){
			try{
			StringTokenizer st  = new StringTokenizer(colorString,"([]) ;,");
			String redf = st.nextToken();
			String greenf = st.nextToken();
			String bluef = st.nextToken();
			res = new Color(Float.parseFloat(redf),Float.parseFloat(greenf),Float.parseFloat(bluef));
			}catch(Exception e){
				e.printStackTrace();
			}
		//}
		return res;
	}
	
	public Vector<Color> makeRedGreenColorFromNumbers(float numbers[], float threshold_zvalue){
		Vector<Color> colors = new Vector<Color>();
		Vector<Float> negatives = new Vector<Float>();
		Vector<Float> positives = new Vector<Float>();
		for(int i=0;i<numbers.length;i++)if(!Float.isNaN(numbers[i])){
			if(numbers[i]<0){ negatives.add(numbers[i]); negatives.add(-numbers[i]); }
			if(numbers[i]>0){ positives.add(numbers[i]); positives.add(-numbers[i]); }
		}
		float negatives1[] = new float[negatives.size()]; for(int i=0;i<negatives.size();i++) negatives1[i] = negatives.get(i);
		float positives1[] = new float[positives.size()]; for(int i=0;i<positives.size();i++) positives1[i] = positives.get(i);		
		float stdpositive = Utils.calcStandardDeviation(positives1);
		float stdnegative = Utils.calcStandardDeviation(negatives1);
		if(!normalizeColumnValues){
			stdpositive = 1;
			stdnegative = 1;
		}
		for(int i=0;i<numbers.length;i++){
			if(!Float.isNaN(numbers[i])){
			if(numbers[i]>0){
				if(numbers[i]/stdpositive>=threshold_zvalue) colors.add(new Color(1f,0f,0f));
				if(numbers[i]/stdpositive<threshold_zvalue) colors.add(new Color(1f,1f-numbers[i]/stdpositive/threshold_zvalue,1f-numbers[i]/stdpositive/threshold_zvalue));				
			}else{
				if(-numbers[i]/stdnegative>=threshold_zvalue) colors.add(new Color(0f,1f,0f));
				if(-numbers[i]/stdnegative<threshold_zvalue) colors.add(new Color(1f+numbers[i]/stdnegative/threshold_zvalue,1f,1f+numbers[i]/stdnegative/threshold_zvalue));				
			}
			}else{
				colors.add(new Color(0f,0f,0f));
			}
		}
		return colors;
	}
	
	public void assignColorsToPoints(Vector<ColorPoint> points){
		for(int i=0;i<points.size();i++){
			Vector<String> groups = points.get(i).groups;
			float cr = 0f;
			float cg = 0f;
			float cb = 0f;
			int num = 0;
			for(int j=0;j<groups.size();j++)
				if((groupColors.get(groups.get(j)).getRed()!=0)||(groupColors.get(groups.get(j)).getGreen()!=0)||(groupColors.get(groups.get(j)).getBlue()!=0)){
				cr+=(float)groupColors.get(groups.get(j)).getRed()/256f;
				cg+=(float)groupColors.get(groups.get(j)).getGreen()/256f;
				cb+=(float)groupColors.get(groups.get(j)).getBlue()/256f;
				num++;
			}
			points.get(i).color = new Color(cr/(float)num,cg/(float)num,cb/(float)num);
		}
	}
	
	  public void loadDataTable(String fn){
			table = new SimpleTable();
			table.LoadFromSimpleDatFile(fn, true, "\t");
			table.createIndex(table.fieldNames[0]);
	  }
	
	
	  public void loadDataTable(String fn, String keyField){
			table = new SimpleTable();
			table.LoadFromSimpleDatFile(fn, true, "\t");
			table.createIndex(keyField);
	  }
	
	  public void assignColorsToGroupsFromTable(String numericFeature){
			float values[] = new float[groupNameToProteinList.size()];
			int in = table.fieldNumByName(numericFeature);

			Iterator<String> it = groupNameToProteinList.keySet().iterator();
			Vector<String> groupNames = new Vector<String>();
			int k=0;
			while(it.hasNext()){
				String key = it.next();
				groupNames.add(key);
				Vector<Integer> rows = table.index.get(key);
				if(rows!=null){
					float maxValue = Float.MIN_VALUE;
					for(int i=0;i<rows.size();i++)
						if(Math.abs(Float.parseFloat(table.stringTable[rows.get(i)][in]))>Math.abs(maxValue))
							maxValue = Float.parseFloat(table.stringTable[rows.get(i)][in]);
					values[k++]=maxValue;
				}else{
					values[k++]=Float.NaN;
				}
			}
			
			try{
				FileWriter fw = new FileWriter(table.filename+".selected");
				for(int i=0;i<groupNames.size();i++){
					fw.write(groupNames.get(i)+"\t"+values[i]+"\n");
				}
				fw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Vector<Color> colors = makeRedGreenColorFromNumbers(values, thresholdGradient);
			for(int i=0;i<groupNames.size();i++)
				groupColors.put(groupNames.get(i), colors.get(i));
	  }
	  
	  public void paintStaining(String fn, String mappng_fn){
		  staining = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g = staining.createGraphics();
		    g.setBackground(new Color(1f,1f,1f));
		    g.clearRect(0,0,staining.getWidth(),staining.getHeight());
		  //if(false)
		  Color black = new Color(0f,0f,0f);
		  for(int i=0;i<regularpoints.size();i++){
			  ColorPoint point = regularpoints.get(i);
			  if(!point.color.equals(black)){
				  g.setColor(point.color);
				  g.fillRect((int)(point.x-gridStepX/2f), (int)height-(int)(point.y+gridStepY/2f), (int)gridStepX, (int)gridStepY);
			  }
		  }
		  try{
			  
			  if(mappng_fn!=null){
				  System.out.println("Reading "+mappng_fn);
				  BufferedImage map = ImageIO.read(new File(mappng_fn));
				  Image imap = Utils.Transparency.makeColorTransparent(map, new Color(1f, 1f, 1f));
				  g.drawImage(imap,0,0,null);
				  g.dispose();
			  }
			  
			  if(fn!=null){
				  System.out.println("Saving PNG in "+fn+"...");
				  ImageIO.write(staining, "PNG", new File(fn));
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	  public HashMap<String, Vector<String>>  extractGMTFromCellDesigner(String fn){
		  HashMap<String, Vector<String>> groups = new HashMap<String, Vector<String>>();
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			  CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);  
			  if(protein.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(protein.getCelldesignerNotes());
				  processAnnotation(Utils.getValue(protein.getName()), notes, groups);
			  }
		  }
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			  CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);  
			  if(gene.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(gene.getCelldesignerNotes());
				  processAnnotation(gene.getName(), notes, groups);
			  }
		  }
		  for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			  CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);  
			  if(rna.getCelldesignerNotes()!=null){
				  String notes = Utils.getText(rna.getCelldesignerNotes());
				  processAnnotation(rna.getName(), notes, groups);
			  }
		  }
		  Iterator<String> it = groups.keySet().iterator();
		  try{
		  FileWriter fw = new FileWriter(fn);
		  while(it.hasNext()){
			  String module = it.next();
			  Vector<String> gnames = groups.get(module);
			  fw.write(module+"\tna\t");
			  for(int i=0;i<gnames.size();i++)
				  fw.write(gnames.get(i)+"\t");
			  fw.write("\n");
		  }
		  fw.close();
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return groups;
	  }
	
	  public void processAnnotation(String name, String notes, HashMap<String, Vector<String>> groups){
		  Vector<String> hugos = getTagValues(notes, "HUGO");
		  Vector<String> cc_phase = getTagValues(notes, "CC_PHASE");		  
		  Vector<String> layer = getTagValues(notes, "LAYER");		  
		  Vector<String> pathway = getTagValues(notes, "PATHWAY");		  		  
		  Vector<String> checkpoints = getTagValues(notes,"CHECKPOINT");
		  Vector<String> modules = getTagValues(notes,"MODULE");
		  Vector<String> module_names = new Vector<String>();
		  module_names.addAll(cc_phase);
		  module_names.addAll(pathway);
		  module_names.addAll(layer);
		  module_names.addAll(checkpoints);
		  module_names.addAll(modules);		
		  if(useProteinNameIfHUGOisntFound){
			  if(hugos.size()==0)
				  if(name!=null)
					  hugos.add(name);
		  }
		  for(int i=0;i<module_names.size();i++){
			  String module = module_names.get(i);
			  Vector<String> names = groups.get(module);
			  if(names==null) { names = new Vector<String>(); groups.put(module, names); }
			  for(int j=0;j<hugos.size();j++)if(!names.contains(hugos.get(j))) names.add(hugos.get(j));
		  }
	  }

}
