package fr.curie.BiNoM.pathways.scripts;

import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class ACSNPhosphoDataMapping {

	SimpleTable table = null;
	Graph graph = null;
	SetOverlapAnalysis sop = null;
	Vector<String> modifications = new Vector<String>();
	Vector<String> modifications_hugos = new Vector<String>();	
	String folder = null;
	boolean filterForPhosphorylations = true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			ACSNPhosphoDataMapping apdm = new ACSNPhosphoDataMapping();
			
			apdm.folder = "C:/Datas/acsn/analysis/paperJan2014/Phosphoproteome_data/analysis/";
			
			apdm.table = new SimpleTable();
			apdm.table.LoadFromSimpleDatFile(apdm.folder+"phospho_lung.txt",true,"\t");
			apdm.table.createIndex("GENE");
			
			apdm.sop = new SetOverlapAnalysis();
			apdm.sop.LoadSetsFromGMT(apdm.folder+"acsn_master.xml.gmt");
			
			apdm.graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(apdm.folder+"acsn_master.xgmml"));
			
			apdm.mapAllModifications();
			apdm.mapData();
			
			//SbmlDocument sbml = apdm.colorCellDesignerMap(apdm.folder+"survival_MAPK",apdm.folder+"modifications_freq_filtered.txt");
			//apdm.colorCellDesignerMap(apdm.folder+"acsn_master",apdm.folder+"modifications_freq_filtered.txt");
			//CellDesigner.saveCellDesigner(sbml, apdm.folder+"survival_MAPK_colored.xml");

			//SbmlDocument sbml = apdm.colorCellDesignerMap(apdm.folder+"emtcellmotility_CELL_MATRIX_ADHESIONS",apdm.folder+"modifications_freq_filtered.txt");
			//apdm.colorCellDesignerMap(apdm.folder+"acsn_master",apdm.folder+"modifications_freq_filtered.txt");
			//CellDesigner.saveCellDesigner(sbml, apdm.folder+"emtcellmotility_CELL_MATRIX_ADHESIONS_colored.xml");
			 			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void mapAllModifications(){
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			if(!n.Id.equals("")){
			StringTokenizer st = new StringTokenizer(n.Id,"@");
			String name = "";
			name = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(name,":");
			while(st1.hasMoreTokens()){
				String mod = st1.nextToken();
				if(!modifications.contains(mod))
					modifications.add(mod);
			}}
		}
		Collections.sort(modifications);
		for(String s: modifications){
			StringTokenizer st = new StringTokenizer(s,"|");
			String name = st.nextToken();
			String mods = "";
			while(st.hasMoreTokens()){
				String mod = st.nextToken();
				mod = Utils.replaceString(mod, "'","");
				if(filterForPhosphorylations){
					if(mod.endsWith("pho")||mod.endsWith("_pho"))
						mods+="|"+mod;
				}else{
					mods+="|"+mod;
				}
			}
			int k = sop.setnames.indexOf(name);
			if(k!=-1){
				HashSet<String> hugos = sop.sets.get(k);
				for(String hugo: hugos){
					String mod = hugo+mods;
					if((!filterForPhosphorylations)||(!mods.equals("")))
					if(!modifications_hugos.contains(mod))
						modifications_hugos.add(mod);
				}
			}
		}
		Collections.sort(modifications_hugos);
		try{
			FileWriter fw = new FileWriter(folder+"modifications_hugos.txt");
			for(String s: modifications_hugos)
				fw.write(s+"\n");
			fw.close();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void mapData() throws Exception{
		Vector<String> commonHugos = new Vector<String>();
		FileWriter fw = new FileWriter(folder+"modifications_freq.txt");
		for(String s: modifications_hugos){
			StringTokenizer st = new StringTokenizer(s,"|");
			String hugo = st.nextToken();
			if(table.index.get(hugo)!=null){
				Vector<String> modsMap = new Vector<String>();
				while(st.hasMoreTokens()){
					String ss = st.nextToken();
					if(!modsMap.contains(ss))
					  modsMap.add(ss);
				}
				Vector<String> modsData = new Vector<String>();
				Vector<Float> freqData = new Vector<Float>();
				Vector<Integer> row = table.index.get(hugo);
				for(int k:row){
					modsData.add(table.stringTable[k][table.fieldNumByName("RESIDUE")]);
					freqData.add(Float.parseFloat(table.stringTable[k][table.fieldNumByName("FREQ")]));
				}
				// Now, compute the frequency by matching residues
				float freq = 0f;
				boolean found = false;
				for(String res: modsMap){
					// Joker situation
					if(res.equals("pho")){
						for(float f: freqData)
							freq+=f;
							found = true;	
					}else{
						res = Utils.replaceString(res, "Ser", "S");
						res = Utils.replaceString(res, "Thr", "T");
						res = Utils.replaceString(res, "Tyr", "Y");
						res = Utils.replaceString(res, "_pho", "");
						String resLetter = res.substring(0, 1);
						String resNumber = res.substring(1, res.length()).trim();
						for(int j=0;j<modsData.size();j++){
							String resData = modsData.get(j);
							resData = Utils.replaceString(resData, "-p", "");
							String resLetterData = resData.substring(0, 1);
							String resNumberData = resData.substring(1, resData.length());
							if(resLetterData.equals(resLetter))if(resNumberData.equals(resNumber)){
								freq+=freqData.get(j);
								found=true;
							}
						}
					}
				}
				if(found){
					System.out.println(s+"\t"+freq);
					fw.write(s+"\t"+freq+"\n");
					if(freq>1e-10)
						if(!commonHugos.contains(hugo))
							commonHugos.add(hugo);
				}else{
					System.out.println(s+"\tNOTFOUND");
					fw.write(s+"\tNOTFOUND\n");
				}
			}
		}
		fw.close();
		System.out.println(commonHugos.size()+" genes were mapped by modifications");
		Collections.sort(commonHugos);
		for(String s:commonHugos)
			System.out.print(s+", ");
	}
	
	public SbmlDocument colorCellDesignerMap(String xmlFile, String valueTable){
		SbmlDocument sbml = CellDesigner.loadCellDesigner(xmlFile+".xml");
		SimpleTable table = new SimpleTable();
		table.LoadFromSimpleDatFile(valueTable, true, "\t");
		CellDesigner.entities = CellDesigner.getEntities(sbml);
		CellDesignerToCytoscapeConverter.createSpeciesMap(sbml.getSbml());
		HashMap<String,Float> values = new HashMap<String, Float>();
		HashMap<String,String> idname = new HashMap<String, String>();
		for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String compartment = sp.getCompartment();
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = sp.getAnnotation().getCelldesignerSpeciesIdentity();
			if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
				String spname = CellDesignerToCytoscapeConverter.getSpeciesName(spi, sp.getId(), Utils.getValue(sp.getName()), compartment, true, false, "", sbml);
				idname.put(sp.getId(), spname);
				//System.out.println(spname);
				if(spname!=null)if(!spname.equals(""))if(!spname.contains(":")){
					Float val = matchValue(spname, table);
					if(val!=null){
						Float value = values.get(spname);
						if(value==null){ value = val; values.put(sp.getId(), value); }
						if(val>value)
							values.put(sp.getId(), value);
					}
				}
			}
		}
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies sp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			//String compartment = sp.getCompartment();
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity spi = sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
			if(spi!=null){
				String spname = CellDesignerToCytoscapeConverter.getSpeciesName(spi, sp.getId(), Utils.getValue(sp.getName()), null, true, false, "", sbml);
				idname.put(sp.getId(), spname);
				//System.out.println(spname);
				if(spname!=null)if(!spname.equals(""))if(!spname.contains(":")){
					Float val = matchValue(spname, table);
					if(val!=null){
						Float value = values.get(spname);
						if(value==null){ value = val; values.put(sp.getId(), value); }
						if(val>value)
							values.put(sp.getId(), value);
					}
				}
			}
		}
		float vmax = -Float.MAX_VALUE;
		float vmin = Float.MAX_VALUE;
		for(String s: values.keySet()){
			System.out.println(idname.get(s)+"\t"+values.get(s));
			if(values.get(s)>vmax) vmax = values.get(s);
			if(values.get(s)<vmin) vmin = values.get(s);
		}
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spid = cas.getSpecies();
			cas.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ffffffff");
			if(values.containsKey(spid)){
				float x = values.get(spid);
				int rc = (int)(55f+200f*(x-vmin)/(vmax-vmin));
				String rs = Integer.toHexString(255-rc);
				if(rs.length()==1) rs="0"+rs;
				cas.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ffff"+rs+rs);
				XmlString str = XmlString.Factory.newInstance();
				str.setStringValue("30");
				cas.getCelldesignerFont().setSize(str);
			}else{
				XmlString str = XmlString.Factory.newInstance();
				str.setStringValue("20");
				cas.getCelldesignerFont().setSize(str);
			}
		}
		return sbml;
	}
	
	public Float matchValue(String spname, SimpleTable valueTable){
		Float value = null;
		StringTokenizer st = new StringTokenizer(spname,"|");
		String name = st.nextToken();
		Vector<String> mods = new Vector<String>();
		while(st.hasMoreTokens())
			mods.add(st.nextToken());
		//System.out.println(spname);
		int k = sop.setnames.indexOf(name);
		if(k!=-1){
			HashSet<String> hugos = sop.sets.get(k);
			for(String hugo: hugos){
				for(int i=0;i<valueTable.rowCount;i++){
					String modification = valueTable.stringTable[i][0];
					StringTokenizer st1 = new StringTokenizer(modification,"|");
					String valName = st1.nextToken();
					if(valName.equals(hugo)){
						//System.out.println(spname+"\t"+modification);
						Vector<String> valMods = new Vector<String>();
						while(st1.hasMoreTokens()){
							valMods.add(st1.nextToken());
						}
						boolean matchModifications = false;
						for(int j=0;j<mods.size();j++)
							for(int l=0;l<valMods.size();l++){
								if(mods.get(j).equals(valMods.get(l)))
									matchModifications = true;
							}
						if(matchModifications){
							//System.out.println(spname+"\t"+valueTable.stringTable[i][0]+"\t"+valueTable.stringTable[i][2]);
							value = Float.parseFloat(valueTable.stringTable[i][2]);
						}
					}
				}
			}
		}
		return value;
	}

}
