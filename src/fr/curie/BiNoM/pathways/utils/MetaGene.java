package fr.curie.BiNoM.pathways.utils;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Vector;

public class MetaGene {
	public String name = "";
	public Vector<String> genes = new Vector<String>();
	public Vector<Float> weights = new Vector<Float>();

	public void add(String gene, float weight){
		int k = genes.indexOf(gene);
		if(k>=0){
			weights.set(k, weight);
		}else{
			genes.add(gene);
			weights.add(weight);
		}
	}
	
	public float sidedStandardDeviation(int side){
		float std = 0f;
		Vector<Float> vals = new Vector<Float>();
		for(int i=0;i<weights.size();i++){
			if(side*weights.get(i)>0){
				vals.add(weights.get(i));
				vals.add(-weights.get(i));
			}
		}
		float valsf[] = new float[vals.size()];
		for(int i=0;i<vals.size();i++) valsf[i] = vals.get(i);
		if(valsf.length>1)
			std = Utils.calcStandardDeviation(valsf);
		return std;
	}
	
	public void normalizeWeightsToSidedZScores(){
		float positiveStdDev = sidedStandardDeviation(+1);
		float negativeStdDev = sidedStandardDeviation(-1);
		if(positiveStdDev==0) positiveStdDev = 1f;
		if(negativeStdDev==0) negativeStdDev = 1f;
		for(int i=0;i<weights.size();i++)
			if(weights.get(i)>0)
				weights.set(i, weights.get(i)/positiveStdDev);
			else
				weights.set(i, weights.get(i)/negativeStdDev);
		
	}
	
	public void invertSignsOfWeights(){
		for(int i=0;i<weights.size();i++)
				weights.set(i, -weights.get(i));
	}

	public void ignoreWeightSigns(){
		for(int i=0;i<weights.size();i++)
			if(weights.get(i)<0)
				weights.set(i, -weights.get(i));
	}
	
	public static float correlationTwoMetagenes(MetaGene m1, MetaGene m2){
		float corr = 0f;
		Vector<String> commonNames = new Vector<String>();
		for(int i=0;i<m1.genes.size();i++)
			if(m2.genes.contains(m1.genes.get(i)))
				if(!commonNames.contains(m1.genes.get(i)))
					commonNames.add(m1.genes.get(i));
		float m1f[] = new float[commonNames.size()];
		float m2f[] = new float[commonNames.size()];
		for(String n: commonNames){
			float f1 = m1.weights.get(m1.genes.indexOf(n));
			float f2 = m2.weights.get(m2.genes.indexOf(n));
			m1f[commonNames.indexOf(n)] = f1;
			m2f[commonNames.indexOf(n)] = f2;
		}
		corr = Utils.calcCorrelationCoeff(m1f, m2f);
		return corr;
	}
	
	/*
	 * The metagenes are compared with the current one by correlation and flipped if necessary
	 * The resulting vector DOES NOT contain the current one 
	 */
	public Vector<MetaGene> standartatizeMetaGenes(Vector<MetaGene> metagenes){
		Vector<MetaGene> res = new Vector<MetaGene>();
		for(int i=0;i<metagenes.size();i++){
			MetaGene mg = metagenes.get(i);
			float corr = correlationTwoMetagenes(this, mg);
			//System.out.println("Correlation "+name+"<>"+mg.name+" = "+corr);
			if(corr<0)
				mg.invertSignsOfWeights();
			res.add(mg);
		}
		return res;
	}
	
	public static MetaGene makeMetaGeneScoredFromMetagenes(Vector<MetaGene> metagenes, int minOccurenceOfGene, boolean useMedian){
		MetaGene metametagene = new MetaGene();
		Vector<String> allnames = new Vector<String>();
		for(int i=0;i<metagenes.size();i++){
			MetaGene mg = metagenes.get(i);
			for(int j=0;j<mg.genes.size();j++){
				String gene = mg.genes.get(j);
				int found = 0;
				for(MetaGene mgt: metagenes){
					if(mgt.genes.indexOf(gene)>=0)
						found++;
				}
			if(found>=minOccurenceOfGene){
				if(!allnames.contains(gene))
					allnames.add(gene);
			}
			}
		}
		
		for(String gene: allnames){
			Vector<Float> values = new Vector<Float>();
			for(MetaGene m: metagenes){
				if(m.genes.indexOf(gene)>=0){
					values.add(m.weights.get(m.genes.indexOf(gene)));
					/*if(gene.equals("TYMP")){
						System.out.println("TYMP in "+m.name+": "+m.weights.get(m.genes.indexOf(gene)));
					}
					if(gene.equals("IFIT1")){
						System.out.println("TYMP in "+m.name+": "+m.weights.get(m.genes.indexOf(gene)));
					}*/
				}
			}
			float vf[] = new float[values.size()];
			for(int i=0;i<values.size();i++) vf[i] = values.get(i);
			float w = Utils.calcMean(vf);
			if(useMedian)
				w = Utils.calcMedian(vf);
			metametagene.add(gene,w);
		}
		return metametagene;
	}
	
	public static Vector<MetaGene> decomposeTableIntoMetaGenes(SimpleTable t, String fieldForName, String prefixName){
		Vector<MetaGene> mgv = new Vector<MetaGene>();
		for(int i=0;i<t.colCount;i++){
			if(t.fieldTypes[i]==t.NUMERICAL){
				MetaGene mg = new MetaGene();
				mg.name = prefixName+t.fieldNames[i];
				for(int j=0;j<t.rowCount;j++)
					mg.add(t.stringTable[j][t.fieldNumByName(fieldForName)], Float.parseFloat(t.stringTable[j][i]));
				mgv.add(mg);
			}
		}
		return mgv;
	}
	
	public void sortWeights(){
		float w[] = new float[weights.size()];
		for(int i=0;i<weights.size();i++) w[i] = weights.get(i);
		int inds[] = Utils.SortMass(w);
		Vector<String> newgenes = new Vector<String>();
		Vector<Float> newweights = new Vector<Float>();
		for(int i=0;i<inds.length;i++){
			newgenes.add(genes.get(inds[inds.length-i-1]));
			newweights.add(weights.get(inds[inds.length-i-1]));
		}
		genes = newgenes;
		weights = newweights;
	}

	public void sortNames(){
		Vector<String> newgenes = new Vector<String>();
		Vector<Float> newweights = new Vector<Float>();
		for(String s: genes) newgenes.add(s);
		Collections.sort(newgenes);
		for(String s: newgenes){
			newweights.add(weights.get(genes.indexOf(s)));
		genes = newgenes;
		weights = newweights;
		}
	}
	
	
	public void saveToFile(String fn) throws Exception{
		FileWriter fw = new FileWriter(fn);
		for(int i=0;i<genes.size();i++)
			fw.write(genes.get(i)+"\t"+weights.get(i)+"\n");
		fw.close();
	}
	

}



