package org.cytoscape.sample;
import java.util.Random;

import vdaoengine.analysis.PCAMethod;
import vdaoengine.data.VDataSet;
import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import vdaoengine.utils.VSimpleProcedures;

public class PCALayout {
	
	public VDataSet dataset = null;
	public float geneProjections[][] = null; // This is used to make the layout
	public float attributeContributions[][] = null; // This is not needed for layout but just in case
	public float explainedVariation[] = null;
	
	/*public static void main(String[] args) {
		int numberOfPoints = 100; // 100 points
		float matrix[][] = new float[numberOfPoints][10]; // 10 dimensions
		Random r = new Random();
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[0].length;j++)
				matrix[i][j] = r.nextFloat();
		// Let us add some missing values
		matrix[10][3] = Float.NaN;
		matrix[15][0] = Float.NaN;		
		matrix[51][5] = Float.NaN;				
		
		//PCALayout pca = new PCALayout();
		//pca.makeDataSet(matrix);
		//pca.loadDataSet("c:/datas/test/iris1.txt");
		//pca.computePCA();
		
		System.out.println("POINT\tX\tY");
		for(int i=0;i<numberOfPoints;i++)
			System.out.println(i+"\t"+pca.geneProjections[i][0]+"\t"+pca.geneProjections[i][1]);
		System.out.println("Variance explained by PC1 = "+pca.explainedVariation[0]);
		System.out.println("Variance explained by PC2 = "+pca.explainedVariation[1]);		
	}*/
	
	public void makeDataSet(float matrix[][]){
		dataset = new VDataSet();
		dataset.coordCount = matrix[0].length;
		dataset.pointCount = matrix.length;
		dataset.massif = matrix.clone();
		// hasGaps is set to true to warn that there are some missing values
		dataset.hasGaps = true;
		
		dataset.calcStatistics();
	}
	
	// this can load a dataset from simple tab-delimited text file with numbers and a header
	public void loadDataSet(String fn){
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(fn, true, "\t");
		dataset = VSimpleProcedures.SimplyPreparedDataset(vt, -1);
	}
	
	public void computePCA(){
		
		PCAMethod pca = new PCAMethod();
		pca.setDataSet(dataset);
		pca.calcBasis(10);

		attributeContributions = new float[dataset.coordCount][10];
		for(int j=0;j<10;j++) for(int i=0;i<dataset.coordCount;i++) attributeContributions[i][j] = (float)pca.getBasis().basis[j][i];
		
		VDataSet vdprojected = pca.getProjectedDataset();
		geneProjections = vdprojected.massif;
		
		explainedVariation = new float[10];
		double disp[] = pca.calcDispersionsRelative(dataset.simpleStatistics.totalDispersion*dataset.simpleStatistics.totalDispersion);
		explainedVariation[0] = (float)disp[0];
		explainedVariation[1] = (float)disp[1];
		explainedVariation[2] = (float)disp[2];
		explainedVariation[3] = (float)disp[3];
		explainedVariation[4] = (float)disp[4];
		explainedVariation[5] = (float)disp[5];
		explainedVariation[6] = (float)disp[6];
		explainedVariation[7] = (float)disp[7];
		explainedVariation[8] = (float)disp[8];
		explainedVariation[9] = (float)disp[9];
	
	
		
	}

}