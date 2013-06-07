package fr.curie.BiNoM.pathways.coloring;

import java.awt.Color;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Vector;

import fr.curie.BiNoM.pathways.utils.Utils;

public class Matrix2Color {

	public static float threshold_zvalue = 2f;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			// we have a 2D-matrix with genes in first dimension and samples in second dimension
			float matrix[][] = loadMatrixFromFile("c:/datas/Neuroblastoma/miRNA.txt");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static float getPositiveThreshold(float matrix[][]){
		float thresh = 1;
		return thresh;
	}
	
	public static float getPositiveThreshold(float numbers[]){
		float thresh = 1;
		return thresh;
	}
	
	public static float getNegativeThreshold(float matrix[][]){
		float thresh = 1;
		return thresh;
	}
	
	public static float getNegativeThreshold(float numbers[]){
		float thresh = 1;
		return thresh;
	}
	
	
	
	/*public static 
	
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
	
	*/
	
	public static float[][] loadMatrixFromFile(String fn) throws Exception{
		LineNumberReader ln = new LineNumberReader(new FileReader(fn));
		Vector<Vector<Float>> numbers = new Vector<Vector<Float>>();
		String s = null;
		int maxnum = 0;
		while((s=ln.readLine())!=null){
			String fs[] = s.split("\t");
			Vector<Float> v = new Vector<Float>();
			if(s.length()>maxnum)
				maxnum = s.length();
			for(String val: fs){
				float vf = Float.NaN;
				try{
					vf = Float.parseFloat(val);
				}catch(Exception e){
				}
				v.add(vf);
			}
			numbers.add(v);
		}
		float res[][] = new float[numbers.size()][maxnum];
		for(int i=0;i<res.length;i++) for(int j=0;j<maxnum;j++) res[i][j] = Float.NaN; 
		
		for(int i=0;i<numbers.size();i++){
			Vector<Float> v = numbers.get(i);
			for(int j=0;j<v.size();j++)
				res[i][j] = v.get(j);
		}
		return res;
	}
	

}
