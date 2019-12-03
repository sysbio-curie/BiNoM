package fr.curie.DeDaL.scripts;

import getools.scripts.ModuleActivityAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Vector;

import vdaoengine.TableUtils;
import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import vdaoengine.utils.Utils;
import vdaoengine.utils.VSimpleFunctions;
import vdaoengine.utils.VSimpleProcedures;

public class BreastCancerExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			
			/*String prefix = "c:/datas/ica/Anne/SADP_rda_files/dat/";
			String prefix1 = "c:/datas/dedal/bc_basal/";
			String fn = "TCGABREAST";*/
			
			String prefix1 = "c:/datas/dedal/bc_tcga/";
			String fn = "brca1";			
			
			// First block: processing the data
			/*VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(prefix1+fn+".txt", true, "\t",true);
			for(int i=1;i<vt.colCount;i++)
				vt.fieldTypes[i] = vt.NUMERICAL;
			VDatReadWrite.saveToVDatFile(vt, prefix1+fn+".dat");
			
			int num_genes = 3000;
			
			VDataTable vt_var = TableUtils.filterByVariation(vt, num_genes, false);
			VDatReadWrite.saveToVDatFile(vt_var, prefix1+fn+num_genes+".dat");
			
			VDataTable vt_t = vt.transposeTable("GENE");
			VDataTable vt_pca = TableUtils.PCAtable(vt_t, true);
			VDatReadWrite.saveToVDatFile(vt_t, prefix1+fn+"_t.dat");
			VDatReadWrite.saveToVDatFile(vt_pca, prefix1+fn+"_pca.dat");
			
			VDataTable vt1 = vt_var.transposeTable("GENE");
			VDatReadWrite.saveToVDatFile(vt1, prefix1+fn+"_"+num_genes+"t.dat");*/
			
			
			VDataTable vt = VDatReadWrite.LoadFromVDatFile(prefix1+fn+".dat");
			vt.makePrimaryHash("GENE");
			for(int i=1;i<vt.colCount;i++)
				vt.fieldTypes[i] = vt.NUMERICAL;
			//Vector<String> list = Utils.loadStringListFromFile(prefix1+"PC2_network_cc_genes_pn.txt");
			//Vector<String> list = Utils.loadStringListFromFile(prefix1+"bub1_network.txt");
			//Vector<String> list = Utils.loadStringListFromFile(prefix1+"erbb2_neighb.txt");
			//Vector<String> list = Utils.loadStringListFromFile(prefix1+"esr1_nei.txt");
			Vector<String> list = Utils.loadStringListFromFile(prefix1+"whole_hprd.txt");
			VDatReadWrite.useQuotesEverywhere = false;
			VDataTable vts = VSimpleProcedures.selectRowsFromList(vt, list);
			
			VDataTable annot = VDatReadWrite.LoadFromSimpleDatFile(prefix1+"annot.txt", true, "\t");
			annot.makePrimaryHash("SAMPLE");
			
			int bc = 1;
			int nbc = 1;
			Vector<Integer> basalFields = new Vector<Integer>();
			Vector<Integer> nbasalFields = new Vector<Integer>();
			for(int i=1;i<vts.colCount;i++){
				String sample = vts.fieldNames[i];
				int k = annot.tableHashPrimary.get(sample).get(0);
				String basal = annot.stringTable[k][annot.fieldNumByName("BASAL")];
				if(basal.equals("BASAL")){
					vts.fieldNames[i] = "BAS"+bc;
					basalFields.add(i);
					bc++;
				}else{
					vts.fieldNames[i] = "NBAS"+nbc;
					nbasalFields.add(i);
					nbc++;
				}
			}
			//VDatReadWrite.saveToSimpleDatFile(vts, prefix1+fn+"s.txt");
			FileWriter fw = new FileWriter(prefix1+fn+"s.txt");
			fw.write(vts.fieldNames[0]+"\t");
			for(Integer k: basalFields) fw.write(vts.fieldNames[k]+"\t");
			for(Integer k: nbasalFields) fw.write(vts.fieldNames[k]+"\t");
			fw.write("\n");
			for(int i=0;i<vts.rowCount;i++){
				fw.write(vts.stringTable[i][0]+"\t");
				for(Integer k: basalFields) fw.write(vts.stringTable[i][k]+"\t");
				for(Integer k: nbasalFields) fw.write(vts.stringTable[i][k]+"\t");
				fw.write("\n");
			}
			fw.close();
			 

			//Third block: computing average values in BASAL vs NON-BASAL
			/*VDataTable vt = VDatReadWrite.LoadFromVDatFile(prefix1+fn+".dat");
			vt.makePrimaryHash("GENE");
			VDataTable annot = VDatReadWrite.LoadFromSimpleDatFile(prefix1+"annot.txt", true, "\t");
			annot.makePrimaryHash("SAMPLE");
			FileWriter fw = new FileWriter(prefix1+"basal_averages.txt");
			fw.write("GENE\tBASAL\tNONBASAL\tDIFF\tTTEST\n");
			for(int i=0;i<vt.rowCount;i++){
				String gene = vt.stringTable[i][0];
				Vector<Float> vbasal = new Vector<Float>();
				Vector<Float> vnonbasal = new Vector<Float>();
				fw.write(gene+"\t");
				for(int j=1;j<vt.colCount;j++){
					if(!vt.stringTable[i][j].equals("@")){
					float val = Float.parseFloat(vt.stringTable[i][j]);
					String sample = vt.fieldNames[j];
					if(annot.tableHashPrimary.get(sample)==null)
						System.out.println(sample+" NOT FOUND");
					int k = annot.tableHashPrimary.get(sample).get(0);
					String basal = annot.stringTable[k][annot.fieldNumByName("BASAL")];
					if(basal.equals("BASAL")){
						vbasal.add(val);
					}else{
						vnonbasal.add(val);
					}
					}
				}
				float meanb = (float)VSimpleFunctions.calcMean(vbasal);
				float meannb = (float)VSimpleFunctions.calcMean(vnonbasal);
				float ttest = (float)VSimpleFunctions.calcTTest(vbasal, vnonbasal);
				fw.write(meanb+"\t"+meannb+"\t"+(meanb-meannb)+"\t"+ttest+"\n");
			}
			fw.close();*/
			
			// Find enriched complexes
			//VDataTable vt = VDatReadWrite.LoadFromVDatFile(prefix1+fn+".dat");
			//ModuleAnalysis(prefix1+"hprd9_complexes.gmt", prefix1, "", vt);
			//ModuleAnalysis(prefix1+"msigdb.v4.0.symbols_KEGG.gmt", prefix1, "", vt);
			 
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void ModuleAnalysis(String gmtFileName, String prefix, String dataFolder, VDataTable data) throws Exception{
		File f = new File(gmtFileName);
		String s = f.getName();
		s = s.substring(0, s.length()-4);
		File dir = new File(prefix+dataFolder+s);
		dir.mkdir();
		String datFile = prefix+dataFolder+s+"/datafc.dat";
		VDatReadWrite.saveToVDatFile(data, datFile);
		String feature = "0";
		String featureValue = "BASAL";		
		String featureDiff = "0";
		String featureValueDiff = "_;BASAL";	
		
		PrintStream console = System.out;
		File file = new File(dir.getAbsolutePath()+"/report.txt");
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		
		//String args[] = {"-typeOfModuleFile","0","-typeOfPCAUsage","1","-dataDatFileFolder",dir.getAbsolutePath()+"/","-datFile",(new File(datFile)).getName(),"-moduleFile",gmtFileName,"-hotSpotGenesZthreshold","1.0","-diffSpotGenesZthreshold","0.5","-featureNumberForAveraging",feature,"-featureValueForAveraging",featureValue,"-featureNumberForDiffAnalysis",featureDiff,"-featureValuesForDiffAnalysis",featureValueDiff,"-minimalNumberOfGenesInModule","7","-minimalNumberOfGenesInModuleFound","5"};
		String args[] = {"-typeOfModuleFile","0","-typeOfPCAUsage","1","-dataDatFileFolder",dir.getAbsolutePath()+"/","-datFile",(new File(datFile)).getName(),"-moduleFile",gmtFileName,"-hotSpotGenesZthreshold","1.0","-diffSpotGenesZthreshold","0.5","-featureNumberForAveraging",feature,"-featureValueForAveraging",featureValue,"-featureNumberForDiffAnalysis",featureDiff,"-featureValuesForDiffAnalysis",featureValueDiff,"-minimalNumberOfGenesInModule","7","-minimalNumberOfGenesInModuleFound","5","-fillMissingValues","10"};
		ModuleActivityAnalysis.main(args);
		
		System.setOut(console);
	}
	

}
