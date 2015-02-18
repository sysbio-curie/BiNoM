package fr.curie.BiNoM.pathways.scripts;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import vdaoengine.data.VDataSet;
import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import vdaoengine.utils.VSimpleFunctions;
import vdaoengine.utils.VSimpleProcedures;

import com.ibm.icu.util.StringTokenizer;

import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.utils.GMTFile;
import fr.curie.BiNoM.pathways.utils.MetaGene;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class ICAMining_BitonPaper {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			
			
			/*ICAMining_BitonPaper ibp = new ICAMining_BitonPaper();
			Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("C:/Datas/ICA/Anne/corrgraph/allcancers.xgmml"));
			gr = ibp.RemoveReciprocalEdges(gr);
			XGMML.saveToXGMML(gr, "C:/Datas/ICA/Anne/corrgraph/allcancers_nodoublons.xgmml");*/
			
			//ComputeTTestDistributionForComponentsExtremeGroups("C:/Datas/ICA/Anne/CIT.txt","C:/Datas/ICA/Anne/network_analysis/A_CIT.txt");
			/*File dir[] = new File("C:/Datas/ICA/Anne/SADP_rda_files/dat/temp1").listFiles();
			for(File f: dir){
				String pref = f.getName().substring(0,f.getName().length()-4);
					ComputeTTestDistributionForComponentsExtremeGroups("C:/Datas/ICA/Anne/SADP_rda_files/dat/temp1/"+pref+".txt","C:/Datas/ICA/Anne/SADP_rda_files/A_"+pref+".txt",pref);
			}*/
			
			
			//String folder = "C:/Datas/ICA/Anne/GSEA/results/res/";
			//String gmts = "C:/Datas/ICA/Anne/GSEA/msigdb.v4.0.symbols.gmt";

			String folder = "C:/Datas/ICA/Anne/CDK12/results/";
			String gmts = "C:/Datas/ICA/Anne/CDK12/CDK12.gmt";
			FilterGSEAResults(folder, 5, 3f, 0.01f, 1f, gmts);
			
			/*String folder = "./";
			String gmts = "msigdb.v4.0.symbols.gmt";
			gmts = args[1];
			FilterGSEAResults(folder, Integer.parseInt(args[0]), 3f, 0.01f, 1f, gmts);*/
			
			//ReformatGSEAAnotationFile("C:/Datas/ICA/Anne/results_GSEA_filtered.html");
			
			//ChirurgieAnalysis();
			
			//PrepareCellLineFile();
			//PrepareDataFilesForICA("C:/Datas/OvarianCancer/TCGA/expression/agilent/","OVCA_TCGA_AGILENT");
			//PrepareDataFilesForICA("C:/Datas/OvarianCancer/TCGA/expression/exonarray/","OVCA_TCGA_HUEX");
			
			//MakeCorrelationGraph("C:/Datas/ICA/Anne/corrgraph/recalculate/");
			//MakeCorrelationGraph("C:/Datas/OvarianCancer/TCGA/analysis/corrgraph/");
			//MakeCorrelationGraph("C:/Datas/MOSAIC/analysis/ica/ica_corrgraph/");
						
			//ProduceMetaComponents("C:/Datas/ICA/Anne/metaranking/","dataset_corr_table.txt");
			
			//ProduceMetaComponents("C:/Datas/ICA/Anne/metaranking/test2/","dataset_corr_table.txt");
			//ProduceMetaComponents("C:/Datas/ICA/Anne/metaranking/CIT/","dataset_corr_table.txt");
			
			//CalcMeanExpressionAndCompareToMaximumContributionToICs("C:/Datas/OvarianCancer/TCGA/expression/rnaseq/mRNAexpr_RNAseqRPKM_TCGA_duplOut.txt","C:/Datas/OvarianCancer/TCGA/analysis/corrgraph/S_OVRSEQ.txt",true);
			//CalcMeanExpressionAndCompareToMaximumContributionToICs("C:/Datas/OvarianCancer/TCGA/expression/affymetrix/mRNAexpr_Affy_TCGA.txt","C:/Datas/OvarianCancer/TCGA/analysis/corrgraph/S_OVAFFY.txt",false);
			
			//ExtractStressSignature("C:/Datas/ICA/Anne/GSEA/msigdb.v4.0.symbols_stress.gmt");
			
			//ComputeTTestDistributionFor2Groups("C:/Datas/ICA/Anne/CIT_analysis/chirurgie_type/CIT.txt","C:/Datas/ICA/Anne/CIT_analysis/chirurgie_type/CIT_sg4","CHIRURGIE","CYST","RTUV");
			//ComputeTTestDistributionFor2Groups("C:/Datas/ICA/Anne/CIT_analysis/chirurgie_type/CIT.txt","C:/Datas/ICA/Anne/CIT_analysis/chirurgie_type/CIT_T2","CHIRURGIE","CYST","RTUV");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Graph RemoveReciprocalEdges(Graph gr){
		gr.calcNodesInOut();
		gr = StructureAnalysisUtils.removeReciprocalEdges(gr);
		return gr;
	}
	
	public static void ComputeTTestDistributionForComponentsExtremeGroups(String dataFile, String sampleContributionFile, String pref) throws Exception{
		System.out.println("Loading data...");
		SimpleTable data = new SimpleTable();
		data.LoadFromSimpleDatFile(dataFile, true, "\t");
		System.out.println("Loaded.");
		SimpleTable atable = new SimpleTable();
		atable.LoadFromSimpleDatFile(sampleContributionFile, true, "\t");
		FileWriter fw = new FileWriter(dataFile.substring(0, dataFile.length()-4)+".ttests");
		FileWriter fw1 = new FileWriter(dataFile.substring(0, dataFile.length()-4)+".ttests_score");
		int numberOfGeneToTake1 = 10;
		int numberOfGeneToTake2 = 100;
		fw1.write(pref+numberOfGeneToTake1+"\t"+pref+numberOfGeneToTake2+"\n");
		for(int i=1;i<atable.colCount;i++){
			System.out.println("Column "+(i-1));
			float f[] = new float[atable.rowCount];
			for(int j=0;j<atable.rowCount;j++)
				f[j] = Float.parseFloat(atable.stringTable[j][i]);
			int inds[] = Utils.SortMass(f);
			int numberOfSamples = (int)((float)inds.length/10f);
			Vector<String> groupMinus = new Vector<String>();
			Vector<String> groupPlus = new Vector<String>();
			for(int k=0;k<numberOfSamples;k++){
				groupMinus.add(atable.stringTable[inds[k]][0]);
				groupPlus.add(atable.stringTable[inds[inds.length-k-1]][0]);
			}
			//float ttests[] = calcTtestDistribution(data,groupMinus,groupPlus, true);
			float ttests[] = calcTtestDistribution(data,groupMinus,groupPlus,false);
			for(int k=0;k<ttests.length;k++)
				ttests[k] = Math.abs(ttests[k]);
			inds = Utils.SortMass(ttests);
			for(int k=0;k<ttests.length;k++)
				fw.write(ttests[inds[inds.length-k-1]]+"\t");
			fw.write("\n");
			
			float score1 = 0f;
			float score2 = 0f;
			int k1 = 0;
			int k2 = 0;
			for(int k=0;k<numberOfGeneToTake1;k++){
				float t = ttests[inds[inds.length-k-1]];
				if(!Float.isNaN(t))
				if(!Float.isInfinite(t)){
						score1+=t;
						k1++;
					}
			}
			for(int k=0;k<numberOfGeneToTake2;k++){
				float t = ttests[inds[inds.length-k-1]];
				if(!Float.isNaN(t))
				if(!Float.isInfinite(t))
				{
						score2+=t;
						k2++;
				}
			}
			fw1.write((score1/k1)+"\t"+(score2/k2)+"\n");
		}
		fw.close();
		fw1.close();
	}
	
	public static float[] calcTtestDistribution(SimpleTable data, Vector<String> group1, Vector<String> group2, boolean onlyFoldChange){
		float ttests[] = new float[data.rowCount];
		for(int i=0;i<data.rowCount;i++){
			Vector<Float> vector1 = new Vector<Float>();
			Vector<Float> vector2 = new Vector<Float>();
			for(int j=1;j<data.colCount;j++){
				String field = data.fieldNames[j];
				if(group1.contains(field)) vector1.add(Float.parseFloat(data.stringTable[i][j]));
				if(group2.contains(field)) vector2.add(Float.parseFloat(data.stringTable[i][j]));
			}
			if(!onlyFoldChange)
				ttests[i] = (float)vdaoengine.utils.VSimpleFunctions.calcTTest(vector1, vector2);
			else
				ttests[i] = (float)(vdaoengine.utils.VSimpleFunctions.calcMean(vector1)-vdaoengine.utils.VSimpleFunctions.calcMean(vector2));
		}
		return ttests;
	}
	
	public static void FilterGSEAResults(String folder, int minNumberOfGenes, float scoreThreshold, float qFDRthreshold, float pFWERthreshold, String gmts) throws Exception{
		float f[] = {1f,2f};
		int ks[] = Utils.SortMass(f);
		GMTFile gmt = new GMTFile();
		//gmt.load(gmts);
		File dir = new File(folder);
		File comps[] = dir.listFiles();
		FileWriter fw = new FileWriter(folder+"results_GSEA_filtered.html");
		//for(int i=0;i<5;i++){
		for(int i=0;i<comps.length;i++){
			if(comps[i].isDirectory())if(!comps[i].getName().endsWith("filtered")){
				File fold = comps[i].listFiles()[0];
				File folder_filtered = new File(comps[i].getAbsolutePath()+"_filtered");
				folder_filtered.mkdir();
				File xlsFiles[] = fold.listFiles();
				Vector<String> goodModules = new Vector<String>();
				Vector<Float> proportions = new Vector<Float>();
				Vector<String> goodGenes = new Vector<String>();
				Vector<Float> goodGenesCounts = new Vector<Float>();
				for(int j=0;j<xlsFiles.length;j++){
					if(xlsFiles[j].getName().endsWith(".xls")){
						String module = xlsFiles[j].getName();
						if(module.startsWith("JAEGER"))
							System.out.println();
						module = module.substring(0, module.length()-4);
						if(true)/*if(gmt.setnames.contains(module))*/{
							SimpleTable tab = new SimpleTable();
							//System.out.println(xlsFiles[j].getAbsolutePath());
							tab.LoadFromSimpleDatFile(xlsFiles[j].getAbsolutePath(), true, "\t");
							if(tab.fieldNumByName("PROBE")!=-1){
								
							Vector<String> significantGenes =  new Vector<String>();
							
							// find significant genes
							for(int k=0;k<tab.rowCount;k++){
								String gene = tab.stringTable[k][tab.fieldNumByName("PROBE")];
								float score = Float.parseFloat(tab.stringTable[k][tab.fieldNumByName("RANK METRIC SCORE")]);
								String core = tab.stringTable[k][tab.fieldNumByName("CORE ENRICHMENT")];
								if(core.equals("Yes"))if(Math.abs(score)>scoreThreshold){
									significantGenes.add(gene);
								}
							}
							
							if(significantGenes.size()>=minNumberOfGenes){
								//System.out.print(module+"\t");
								String prefix = xlsFiles[j].getAbsolutePath();
								prefix = prefix.substring(0, prefix.length()-4);
								File html = new File(prefix+".html");
								String text = Utils.loadString(html.getAbsolutePath());
								
								// Extracting p,q-values
								float qFDR = 1f;
								float pFWER = 1f;
								text = Utils.replaceString(text,"<td>", "%");
								text = Utils.replaceString(text,"</td>", "%");
								text = Utils.replaceString(text,"<tr>", "%");
								text = Utils.replaceString(text,"</tr>", "%");
								StringTokenizer st = new StringTokenizer(text,"<'=>%");
								while(st.hasMoreTokens()){
									String s = st.nextToken();
									if(s.equals("FDR q-value"))
										qFDR = Float.parseFloat(st.nextToken());
									if(s.equals("FWER p-Value"))
										pFWER = Float.parseFloat(st.nextToken());
									
								}
								//System.out.println(qFDR+"\t"+pFWER+"\t"+module);
								if(qFDR<=qFDRthreshold)if(pFWER<=pFWERthreshold){
									
									BigDecimal bd = new BigDecimal(qFDR);
									bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
									qFDR = (float)bd.doubleValue();
									bd = new BigDecimal(pFWER);
									bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
									pFWER = (float)bd.doubleValue();
									
									//goodModules.add(module+"("+significantGenes.size()+"/"+tab.rowCount+"/q="+qFDR+",p="+pFWER+")");
									goodModules.add(module+"("+significantGenes.size()+"/"+tab.rowCount+"/p="+pFWER+")");
									proportions.add((float)significantGenes.size()/(float)tab.rowCount);

									st = new StringTokenizer(text,"< '=>");
									Vector<String> pngs = new Vector<String>();
									while(st.hasMoreTokens()){
										String s = st.nextToken();
										if(s.endsWith(".png"))
											pngs.add(s);
									}
									//Files.copy(html.toPath(), new File(folder_filtered.getAbsolutePath()+"/"+html.getName()).toPath(),  REPLACE_EXISTING);
									//String texttemp = Utils.loadString(html.getAbsolutePath());
									//Utils.saveStringToFile(texttemp, folder_filtered.getAbsolutePath()+"/"+html.getName());
									FileUtils.copyFile(html, new File(folder_filtered.getAbsolutePath()+"/"+html.getName()));
									for(String s: pngs){
										File png = new File(xlsFiles[j].getParent()+"/"+s);
										//Files.copy(png.toPath(), new File(folder_filtered.getAbsolutePath()+"/"+s).toPath(),  REPLACE_EXISTING);
										//texttemp = Utils.loadString(png.getAbsolutePath());
										//Utils.saveStringToFile(texttemp, folder_filtered.getAbsolutePath()+"/"+s);
										FileUtils.copyFile(png, new File(folder_filtered.getAbsolutePath()+"/"+s));
									}
									
									for(int l=0;l<significantGenes.size();l++){
										int k = goodGenes.indexOf(significantGenes.get(l));
										if(k<0){
											goodGenes.add(significantGenes.get(l));
											goodGenesCounts.add(1f);
										}else{
											goodGenesCounts.set(k, goodGenesCounts.get(k)+1f);
										}
									}
									
								}
								
							}else{
								//System.out.print(significantGenes.size()+"\t");
							}
							}
							
						}
					}
				}
				
				float props[] = new float[proportions.size()];
				for(int k=0;k<props.length;k++)
					props[k] = proportions.get(k);
				int inds[] = Utils.SortMass(props);
				System.out.print(comps[i].getName()+"\t");
				if(goodModules.size()>0){
					fw.write(comps[i].getName()+"(<a href="+folder_filtered.getName()+"/"+comps[i].getName()+"_freqgenes.html"+">freqgenes</a>)"+"&nbsp;&nbsp;&nbsp;&nbsp;");
					
					FileWriter fw1 = new FileWriter(folder_filtered+"/"+comps[i].getName()+"_freqgenes.html");
					float counts[] = new float[goodGenes.size()];
					for(int k=0;k<counts.length;k++)
						counts[k] = goodGenesCounts.get(k);
					int inds1[] = Utils.SortMass(counts);
					for(int k=0;k<counts.length;k++){
						fw1.write("<a target='_blank' href=\'http://www.genecards.org/cgi-bin/carddisp.pl?gene="+goodGenes.get(inds1[inds1.length-k-1])+"'>"+goodGenes.get(inds1[inds1.length-k-1])+"</a>&nbsp;\t"+goodGenesCounts.get(inds1[inds1.length-k-1])+"<br>\n");
					}
					fw1.close();
					
				}else{
					fw.write(comps[i].getName()+"&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				System.out.print(goodModules.size()+"\t");
				fw.write(goodModules.size()+"&nbsp;&nbsp;");
				for(int k=0;k<goodModules.size();k++){
					String module = goodModules.get(inds[inds.length-k-1]);
					System.out.print(module+"\t");
					StringTokenizer st = new StringTokenizer(module,"(");
					String module_fn = st.nextToken();
					String module_fn2 = st.nextToken();
					fw.write("<a href="+folder_filtered.getName()+"/"+module_fn+".html"+">"+module_fn+"</a>("+module_fn2+")&nbsp;&nbsp;&nbsp;");
				}
				System.out.println();
				fw.write("<br>\n");fw.flush();
			}
		}
		fw.close();
	}
	
	public static void ChirurgieAnalysis(){
		String folder = "C:/Datas/ICA/Anne/CIT_analysis/chirurgie_type/";
		String dat = "CIT";
		//String dat = "A_CIT";
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(folder+dat+".txt", true, "\t");
		Vector<String> columnsT2 = Utils.loadStringListFromFile(folder+"T2_columns");
		columnsT2.insertElementAt("GENE", 0); 
		VDataTable vt2 = VSimpleProcedures.SelectColumns(vt, columnsT2);
		for(int i=1;i<vt2.colCount;i++) vt2.fieldTypes[i] = vt.NUMERICAL;
		vt2.fieldInfo = new String[vt2.colCount][3];
		for(int i=1;i<=6;i++) vt2.fieldInfo[i][2] = "CYST";
		for(int i=7;i<vt2.colCount;i++) vt2.fieldInfo[i][2] = "RTUV";
		vt2 = VSimpleProcedures.centerTableRows(vt2, false, false);
		VDatReadWrite.saveToVDatFile(vt2, folder+dat+"_T2.dat");
		VDataTable vt2T = vt2.transposeTable("GENE");
		VDatReadWrite.saveToVDatFile(vt2T, folder+dat+"_T2_T.dat");
	}
	
	public static void ReformatGSEAAnotationFile(String fn) throws Exception{
		String fn1 = fn.substring(0, fn.length()-4)+"_formated.html";
		Vector<String> lines = Utils.loadStringListFromFile(fn);
		HashMap<String, String> m = new HashMap<String, String>();
		Vector<String> codes = new Vector<String>();
		for(String s: lines){
			StringTokenizer st = new StringTokenizer(s,"&(");
			String ss = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(ss,"_");
			String ds = st1.nextToken();
			String num = st1.nextToken();
			if(num.startsWith("V")) num = num.substring(1, num.length());
			if(num.length()==1) num="0"+num;
			String code = ds+"_"+num;
			codes.add(code);
			m.put(code, s);
		}
		Collections.sort(codes);
		FileWriter fw = new FileWriter(fn1);
		for(String s: codes){
			fw.write(m.get(s)+"\n");
		}
		fw.close();
	}
	
	public static void PrepareCellLineFile() throws Exception{
		float f[] = {1f,2f};
		int ks[] = Utils.SortMass(f);

		//String folder = "C:/Datas/ICA/Anne/cellline/broad/";
		String folder = "C:/Datas/ICA/Anne/cellline/exon_curie/";
		//String dat = "CCLE_exp";
		String dat = "CUEX";
		System.out.println("Loading");
		//VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(folder+"CCLE_exp.txt", true, "\t", true);
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(folder+"CUEX.txt", true, "\t", true);
		VDataTable vt2 = vt;
		System.out.println("Loaded");
		/*Vector<String> columnsT2 = Utils.loadStringListFromFile(folder+"urinary");
		columnsT2.insertElementAt("Description", 0); 
		for(int i=0;i<columnsT2.size();i++){
			boolean found = false;
			for(int j=0;j<vt.colCount;j++)if(vt.fieldNames[j].equals(columnsT2.get(i))) found = true;
			if(!found)
				System.out.println(columnsT2.get(i)+" NOT FOUND");
		}
		VDataTable vt2 = VSimpleProcedures.SelectColumns(vt, columnsT2);*/
		for(int i=1;i<vt2.colCount;i++) vt2.fieldTypes[i] = vt.NUMERICAL;
		//vt2.fieldInfo = new String[vt2.colCount][3];
		//for(int i=1;i<=11;i++) vt2.fieldInfo[i][2] = "F";
		//for(int i=12;i<=24;i++) vt2.fieldInfo[i][2] = "M";
		
		//VDataTable vt2 = VDatReadWrite.LoadFromVDatFile(folder+"CCLE_exp_urinary.dat");
		
		vt2 = VSimpleProcedures.centerTableRows(vt2, false, false);
		//VDatReadWrite.saveToVDatFile(vt2, folder+dat+"_urinary.dat");
		VDatReadWrite.saveToVDatFile(vt2, folder+dat+".dat");
		//VDatReadWrite.saveToSimpleDatFilePureNumerical(vt2, folder+dat+"_urinary.txt");
		VDatReadWrite.saveToSimpleDatFilePureNumerical(vt2, folder+dat+"_numerical.txt");
		//VDataTable vt2T = vt2.transposeTable("Description");
		VDataTable vt2T = vt2.transposeTable("GENE");
		//VDatReadWrite.saveToVDatFile(vt2T, folder+dat+"_urinary_T.dat");
		VDatReadWrite.saveToVDatFile(vt2T, folder+dat+"_T.dat");
		
		System.out.println("Save gene names");
		//FileWriter fw3 = new FileWriter(folder+dat+"_urinary_genenames.txt");
		FileWriter fw3 = new FileWriter(folder+dat+"_genenames.txt");
		for(int i=0;i<vt2.rowCount;i++)
			fw3.write(vt2.stringTable[i][0]+"\n");
		fw3.close();
		System.out.println("Save sample names");
		//fw3 = new FileWriter(folder+dat+"_urinary_samplenames.txt");
		fw3 = new FileWriter(folder+dat+"_samplenames.txt");
		for(int i=1;i<vt2.colCount;i++)
			fw3.write(vt2.fieldNames[i]+"\n");
		fw3.close();
		

	}
	
	public static void MakeCorrelationGraph(String folder) throws Exception{
		/*String fn1 = folder+"S_CUEX05.txt";
		String fn2 = folder+"S_CUEX15.txt";
		VDataTable vt1 = VDatReadWrite.LoadFromSimpleDatFile(fn1, true, "\t");
		VDataTable vt2 = VDatReadWrite.LoadFromSimpleDatFile(fn2, true, "\t");
		for(int i=1;i<vt1.colCount;i++) vt1.fieldTypes[i] = vt1.NUMERICAL;
		for(int i=1;i<vt2.colCount;i++) vt2.fieldTypes[i] = vt1.NUMERICAL;
		Graph gr1 = Utils.makeTableCorrelationGraph(vt1, "CUEX05", vt2, "CUEX15", 0.35f, true);
		Graph gr2 = Utils.makeTableCorrelationGraph(vt2, "CUEX15", vt1, "CUEX05", 0.35f, true);
		gr1.addNodes(gr2);
		gr1.addEdges(gr2);
		XGMML.saveToXGMML(gr1, folder+"corr_graph.xgmml");*/
		File fs[] = new File(folder).listFiles();
		Graph graph = new Graph();
		for(int i=0;i<fs.length;i++)if(fs[i].getName().endsWith(".txt"))if(fs[i].getName().startsWith("S_"))
			for(int j=0;j<fs.length;j++)if(fs[j].getName().endsWith(".txt"))if(fs[j].getName().startsWith("S_"))
			if(i!=j){
				String fn1 = fs[i].getName();
				String fn2 = fs[j].getName();
				fn1 = fn1.substring(2, fn1.length()-4);
				fn2 = fn2.substring(2, fn2.length()-4);
				System.out.println("=============================");
				System.out.println(fn1+"\tvs\t"+fn2);
				VDataTable vt1 = VDatReadWrite.LoadFromSimpleDatFile(fs[i].getAbsolutePath(), true, "\t");
				VDataTable vt2 = VDatReadWrite.LoadFromSimpleDatFile(fs[j].getAbsolutePath(), true, "\t");
				for(int k=1;k<vt1.colCount;k++) vt1.fieldTypes[k] = vt1.NUMERICAL;
				for(int k=1;k<vt2.colCount;k++) vt2.fieldTypes[k] = vt1.NUMERICAL;
				Graph gr = Utils.makeTableCorrelationGraph(vt1, fn1, vt2, fn2, 0.2f, true);
				graph.addNodes(gr);
				graph.addEdges(gr);
			}
		graph.name = "corr_graph"+(new Random()).nextInt(10000);
		XGMML.saveToXGMML(graph, folder+"corr_graph.xgmml");
	}
	
	public static void PrepareDataFilesForICA(String folder, String prefix) throws Exception{
		float f[] = {1f,2f};
		int ks[] = Utils.SortMass(f);
		VDataTable vt2 = VDatReadWrite.LoadFromSimpleDatFile(folder+prefix+".txt", true, "\t", true);
		for(int i=1;i<vt2.colCount;i++) vt2.fieldTypes[i] = vt2.NUMERICAL;
		vt2 = VSimpleProcedures.filterMissingValues(vt2, 1e-6f);
		vt2 = VSimpleProcedures.centerTableRows(vt2,false,false);
		VDatReadWrite.saveToVDatFile(vt2, folder+prefix+".dat");
		VDatReadWrite.numberOfDigitsToKeep = 3;
		VDatReadWrite.useQuotesEverywhere = false;
		VDatReadWrite.saveToSimpleDatFilePureNumerical(vt2, folder+prefix+"_numerical.txt",false);
		VDataTable vt2T = vt2.transposeTable("GENE");
		VDatReadWrite.saveToVDatFile(vt2T, folder+prefix+"_T.dat");
		
		System.out.println("Save gene names");
		FileWriter fw3 = new FileWriter(folder+prefix+"_genenames.txt");
		for(int i=0;i<vt2.rowCount;i++)
			fw3.write(vt2.stringTable[i][0]+"\n");
		fw3.close();
		System.out.println("Save sample names");
		fw3 = new FileWriter(folder+prefix+"_samplenames.txt");
		for(int i=1;i<vt2.colCount;i++)
			fw3.write(vt2.fieldNames[i]+"\n");
		fw3.close();
	}
	
	public static void ProduceMetaComponents(String folder, String correspondenceFile) throws Exception{
		Vector<String> corr = Utils.loadStringListFromFile(folder+correspondenceFile);
		HashMap<String, String> corrMap = new HashMap<String, String>(); 
		for(int i=0;i<corr.size();i++){
			StringTokenizer st = new StringTokenizer(corr.get(i),"\t");
			corrMap.put(st.nextToken(), st.nextToken());
		}
		File files[] = (new File(folder)).listFiles();
		//HashMap<String, VDataTable> tables = new HashMap<String, VDataTable>();
		HashMap<String, MetaGene> metagenes = new HashMap<String, MetaGene>();
		for(File f: files){
			if(f.getName().startsWith("S_")&&f.getName().endsWith(".txt")){
				String prefix = f.getName().substring(2,f.getName().length()-4);
				System.out.println("Loading "+prefix);
				SimpleTable t = new SimpleTable();
				t.LoadFromSimpleDatFile(f.getAbsolutePath(), true, "\t");
				for(int i=1;i<t.colCount;i++)
					t.fieldTypes[i] = t.NUMERICAL;
				Vector<MetaGene> mgv = MetaGene.decomposeTableIntoMetaGenes(t, t.fieldNames[0], prefix);
				for(int i=0;i<mgv.size();i++){
					mgv.get(i).name = prefix+(i+1);
					metagenes.put(mgv.get(i).name, mgv.get(i));
				}
			}
		}
		
		for(File f: files){
			if(f.getName().endsWith(".xgmml")){
				String cliqueName = f.getName().substring(0, f.getName().length()-6);
				System.out.println("Clique = "+cliqueName);
				Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(f.getAbsolutePath()));
				// First, find the core component
				String coreComp = "";
				Vector<String> dsets = new Vector<String>();
				for(int i=0;i<gr.Nodes.size();i++){
					Node n = gr.Nodes.get(i);
					String dataset = n.getFirstAttributeValue("labAn");
					String numComp = n.getFirstAttributeValue("indComp");
					dataset = corrMap.get(dataset);
					dsets.add(dataset);
				}
				if(dsets.indexOf("CIT")>=0){
					coreComp = "CIT"+(gr.Nodes.get(dsets.indexOf("CIT"))).getFirstAttributeValue("indComp");
				}else if(dsets.indexOf("TCGABRCAAGL")>=0){
					coreComp = "TCGABRCAAGL"+(gr.Nodes.get(dsets.indexOf("TCGABRCAAGL"))).getFirstAttributeValue("indComp");
				}else if(dsets.indexOf("TCGABREAST")>=0){
					coreComp = "TCGABREAST"+(gr.Nodes.get(dsets.indexOf("TCGABREAST"))).getFirstAttributeValue("indComp");
				}else{
					coreComp = dsets.get(0)+(gr.Nodes.get(0)).getFirstAttributeValue("indComp");
				}
				System.out.println("Core Component = "+coreComp);
				MetaGene coreMetaGene = metagenes.get(coreComp);
				
				float positiveStd = coreMetaGene.sidedStandardDeviation(+1);
				float negativeStd = coreMetaGene.sidedStandardDeviation(-1);
				if(negativeStd>positiveStd) coreMetaGene.invertSignsOfWeights();
				
				Vector<MetaGene> mgs = new Vector<MetaGene>();
				// Second, prepare a set of correlated components, excluding the core component
				for(int i=0;i<gr.Nodes.size();i++){
					Node n = gr.Nodes.get(i);
					String dataset = corrMap.get(n.getFirstAttributeValue("labAn"));
					String numComp = n.getFirstAttributeValue("indComp");
					if(!dataset.equals("???")){
						String mgname = dataset+numComp;
						if(!mgname.equals(coreMetaGene.name)){
							MetaGene m = metagenes.get(mgname);
							mgs.add(m);
						}
					}
				}
				// Now, compute the metascores
				int minOccurence = (int)(mgs.size()*0.5f);
				if(minOccurence>2)
					minOccurence = 2;
				System.out.println("minOccurence = "+minOccurence);
				Vector<MetaGene> mgs1 = coreMetaGene.standartatizeMetaGenes(mgs);
				mgs1.add(coreMetaGene);
				//if(false)
				for(MetaGene m: mgs1)
					m.normalizeWeightsToSidedZScores();
				MetaGene metameta = coreMetaGene.makeMetaGeneScoredFromMetagenes(mgs1, minOccurence, true);
				metameta.sortWeights();
				metameta.saveToFile(folder+cliqueName+"_metascore.txt");
			}
		}
	}
	
	public static void CalcMeanExpressionAndCompareToMaximumContributionToICs(String fn, String icfile, boolean takelog) throws Exception{
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(fn, true, "\t", true);
		VDataTable vtic = VDatReadWrite.LoadFromSimpleDatFile(icfile, true, "\t", true);
		vtic.makePrimaryHash(vtic.fieldNames[0]);
		for(int i=1;i<vt.colCount;i++) vt.fieldTypes[i] = vt.NUMERICAL;
		for(int i=1;i<vtic.colCount;i++) vtic.fieldTypes[i] = vt.NUMERICAL;
		VDataSet vd = VSimpleProcedures.SimplyPreparedDatasetWithoutNormalization(vt, -1);
		VDataSet vdic = VSimpleProcedures.SimplyPreparedDatasetWithoutNormalization(vtic, -1);
		FileWriter fw = new FileWriter(fn+".averages");
		fw.write("NAME\tAVERAGE\tCOMPARISON\n");
		for(int i=0;i<vd.pointCount;i++){
			String name = vt.stringTable[i][0];
			if(name.equals("ZZZ3"))
				System.out.println();
			float x[] = vd.getVector(i);
			if(takelog){
				for(int j=0;j<x.length;j++)
					x[j] = (float)Math.log10(x[j]+1);
			}
			float av = VSimpleFunctions.calcMean(x);
			String comp = "N/A";
			if(vtic.tableHashPrimary.get(name)!=null){
				int k=vtic.tableHashPrimary.get(name).get(0);
				float xic[] = vdic.getVector(k);
				float mx = -1;
				for(int l=0;l<xic.length;l++){
					xic[l] = Math.abs(xic[l]);
					if(xic[l]>mx) mx = xic[l];
				}
				comp = ""+mx;
			}
			
			fw.write(name+"\t"+av+"\t"+comp+"\n");
		}
		fw.close();
	}
	
	public static void ExtractStressSignature(String modulesFile) throws Exception{
		SetOverlapAnalysis sop = new SetOverlapAnalysis();
		sop.LoadSetsFromGMT(modulesFile);
		System.out.println(modulesFile+" loaded.");
		Vector<String> newnames = new Vector<String>();
		Vector<Vector<String>> newlists = new Vector<Vector<String>>();
		Vector<Vector<Float>> newweights = new Vector<Vector<Float>>(); 
		for(int i=0;i<sop.lists.size();i++){
			String name = sop.setnames.get(i).toLowerCase();
			if(name.contains("stress")||name.contains("hypoxi")||name.contains("heat")){
				newnames.add(sop.setnames.get(i));
				newlists.add(sop.lists.get(i));
				newweights.add(sop.listsWeights.get(i));
			}
		}
		sop.setnames = newnames;
		sop.lists = newlists;
		sop.listsWeights = newweights;
		sop.saveSetsAsGMT(modulesFile.substring(0,modulesFile.length()-4)+"_stress.gmt", Integer.MAX_VALUE, false);
		sop = new SetOverlapAnalysis();
		sop.LoadSetsFromGMT(modulesFile.substring(0,modulesFile.length()-4)+"_stress.gmt");
		Vector<String> list = sop.calcUnionOfSets();
		for(int i=0;i<list.size();i++){
			
		}
		Vector<Float> percentages = new Vector<Float>();
		Vector<Integer> counts = sop.countOccurenciesInSets(list, percentages);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i)+"\t"+counts.get(i));
		}
		for(int i=0;i<sop.sets.size();i++){
			System.out.println(sop.setnames.get(i)+"\t"+percentages.get(i));
		}
	}
	
	public static void ComputeTTestDistributionFor2Groups(String dataFile, String groupFile, String field, String value1, String value2) throws Exception{
		System.out.println("Loading data...");
		SimpleTable data = new SimpleTable();
		data.LoadFromSimpleDatFile(dataFile, true, "\t");
		System.out.println("Loaded.");
		SimpleTable atable = new SimpleTable();
		atable.LoadFromSimpleDatFile(groupFile, true, "\t");
		FileWriter fw = new FileWriter(groupFile+".ttests");

			int i = atable.fieldNumByName(field);

			Vector<String> groupMinus = new Vector<String>();
			Vector<String> groupPlus = new Vector<String>();
			for(int k=0;k<atable.rowCount;k++){
				if(atable.stringTable[k][i].equals(value1))
					groupMinus.add(atable.stringTable[k][0]);
				if(atable.stringTable[k][i].equals(value2))
					groupPlus.add(atable.stringTable[k][0]);
			}
			float ttests[] = calcTtestDistribution(data,groupMinus,groupPlus, false);
			for(int k=0;k<ttests.length;k++)
				ttests[k] = Math.abs(ttests[k]);
			int inds[] = Utils.SortMass(ttests);
			/*for(int k=0;k<ttests.length;k++)
				fw.write(ttests[inds[inds.length-k-1]]+"\t");
			fw.write("\n");*/
			for(int k=0;k<data.rowCount;k++)
				fw.write(data.stringTable[k][0]+"\t"+ttests[k]+"\n");
			
			Vector<String> columns = new Vector<String>();
			columns.add("GENE");
			for(int k=0;k<atable.rowCount;k++){
				String fname = atable.stringTable[k][0];
				columns.add(fname);
			}
			SimpleTable dat1 = data.SelectColumns(columns);
			Vector<Integer> lineNumbers = new Vector<Integer>();
			for(int k=0;k<inds.length;k++)
				lineNumbers.add(inds[inds.length-k-1]);
			dat1 = dat1.SelectRowsInOrder(lineNumbers);
			dat1.saveToSimpleTxtTabDelimited(groupFile+".txt");
			
		fw.close();
	}
	

}
