package fr.curie.BiNoM.pathways.coloring;

import java.awt.Color;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Vector;

import fr.curie.BiNoM.pathways.utils.Utils;

public class Matrix2Color {

	public static float threshold_zvalue = 2f;
	public static Color missingValueColor = new Color(0f,0f,0f);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			// we have a 2D-matrix with genes in first dimension and samples in second dimension
			float matrix[][] = loadMatrixFromFile("c:/datas/Neuroblastoma/test/miRNA.txt");
			System.out.println("Positive threshold = "+getPositiveThreshold(matrix));
			System.out.println("Negative threshold = "+getNegativeThreshold(matrix));			
			
			float pt = getPositiveThreshold(matrix);
			float nt = getNegativeThreshold(matrix);			
			float number = 0.5f;
			Color c = colorForNumber(number,pt,nt);
			System.out.println(""+number+" => "+c.getRed()+","+c.getGreen()+","+c.getBlue());
			number = -2f;
			c = colorForNumber(number,pt,nt);
			System.out.println(""+number+" => "+c.getRed()+","+c.getGreen()+","+c.getBlue());
			number = -0.8f;
			c = colorForNumber(number,pt,nt);
			System.out.println(""+number+" => "+c.getRed()+","+c.getGreen()+","+c.getBlue());			
			number = -5f;
			c = colorForNumber(number,pt,nt);
			System.out.println(""+number+" => "+c.getRed()+","+c.getGreen()+","+c.getBlue());			
			number = +100f;
			c = colorForNumber(number,pt,nt);
			System.out.println(""+number+" => "+c.getRed()+","+c.getGreen()+","+c.getBlue());			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static float getPositiveThreshold(float matrix[][]){
		float numbers[] =  matrix2Vector(matrix);
		return getPositiveThreshold(numbers);
	}
	
	public static float getPositiveThreshold(float numbers[]){
		float thresh = 1;
		Vector<Float> positives = new Vector<Float>();
		for(int i=0;i<numbers.length;i++)if(!Float.isNaN(numbers[i])){
			if(numbers[i]>0){ positives.add(numbers[i]); positives.add(-numbers[i]); }
		}
		float positives1[] = new float[positives.size()]; for(int i=0;i<positives.size();i++) positives1[i] = positives.get(i);		
		float stdpositive = Utils.calcStandardDeviation(positives1);
		return stdpositive;
	}
	
	public static float getNegativeThreshold(float matrix[][]){
		float numbers[] = matrix2Vector(matrix);
		return getNegativeThreshold(numbers);
	}
	
	public static float getNegativeThreshold(float numbers[]){
		float thresh = 1;
		Vector<Float> negatives = new Vector<Float>();
		for(int i=0;i<numbers.length;i++)if(!Float.isNaN(numbers[i])){
			if(numbers[i]<0){ negatives.add(-numbers[i]); negatives.add(numbers[i]); }
		}
		float negatives1[] = new float[negatives.size()]; for(int i=0;i<negatives.size();i++) negatives1[i] = negatives.get(i);		
		float stdnegative = Utils.calcStandardDeviation(negatives1);
		return stdnegative;
	}
	
	public static float[] matrix2Vector(float matrix[][]){
		float res[] = new float[matrix.length*matrix[0].length];
		int k = 0;
		for(int i=0;i<matrix.length;i++)for(int j=0;j<matrix[i].length;j++)
			res[k++] = matrix[i][j];
		return res;
	}
	
	public static Color colorForNumber(float number, float positive_thresh, float negative_thresh){
		Color c = missingValueColor;
		if(!Float.isNaN(number)){
		if(number>0){
			if(number/positive_thresh>=threshold_zvalue) c = new Color(1f,0f,0f);
			if(number/positive_thresh<threshold_zvalue) c = new Color(1f,1f-number/positive_thresh/threshold_zvalue,1f-number/positive_thresh/threshold_zvalue);				
		}else{
			if(-number/negative_thresh>=threshold_zvalue) c = new Color(0f,1f,0f);
			if(-number/negative_thresh<threshold_zvalue) c = new Color(1f+number/negative_thresh/threshold_zvalue,1f,1f+number/negative_thresh/threshold_zvalue);				
		}}
		return c;
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
