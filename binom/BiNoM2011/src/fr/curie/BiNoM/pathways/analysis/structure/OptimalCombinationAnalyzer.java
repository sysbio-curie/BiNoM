package fr.curie.BiNoM.pathways.analysis.structure;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Optimal Combinations of intervention strategies for network analysis (OCSANA) class.
 * 
 * @author ebo
 *
 */
public class OptimalCombinationAnalyzer {

	public int[][] pathMatrix;
	public int pathMatrixNbRow;
	public int pathMatrixNbCol;
	public ArrayList<String> pathMatrixNodeList;
	public ArrayList<String> exceptionNode = new ArrayList<String>();
	public ArrayList<String> hitSetSizeOne = new ArrayList<String>();
	
	/**
	 * Constructor
	 */
	public OptimalCombinationAnalyzer() {
		// nothing here for the moment!
	}
	
	public void searchHitSetSizeOne() {
		
		HashSet<Integer> skipCol = new HashSet<Integer>();
		
		// check columns having only '1' values
		for (int i=0;i<pathMatrixNbCol;i++) {
			int sum=0;
			for (int j=0;j<pathMatrixNbRow;j++) {
				sum += pathMatrix[j][i];
			}
			if (sum == pathMatrixNbRow) {
				skipCol.add(i);
				hitSetSizeOne.add(pathMatrixNodeList.get(i));
			}
		}
		
		// build new data if necessary
		if (skipCol.size()>0) {
			int nbCol = pathMatrixNbCol - skipCol.size();
			int[][] tmp = new int[pathMatrixNbRow][nbCol];
			ArrayList<String> l = new ArrayList<String>();

			for (int j=0;j<pathMatrixNbCol;j++) {
				if (!skipCol.contains(j))
					l.add(pathMatrixNodeList.get(j));
			}
			
			// build new pathMatrix
			int ctCol = 0;
			for (int i=0;i<pathMatrixNbRow;i++) {
				ctCol = 0;
				for (int j=0;j<pathMatrixNbCol;j++) {
					if (!skipCol.contains(j)) {
						tmp[i][ctCol] = pathMatrix[i][j];
						ctCol++;
					}
				}
			}
			
			// replace original data
			pathMatrix = tmp;
			pathMatrixNbCol = ctCol;
			pathMatrixNodeList = l;
			
		}
	}
	
	/**
	 * Check for rows having a single 1, these are exceptions, i.e. input nodes with a direct link to an output node.
	 */
	public void checkRows() {
		
		HashSet<Integer> skipRow = new HashSet<Integer>();
		HashSet<Integer> skipCol = new HashSet<Integer>();
		
		// find rows having only one 1
		for (int i=0;i<pathMatrixNbRow;i++) {
			int sum=0;
			for (int j=0;j<pathMatrixNbCol;j++) {
				sum += pathMatrix[i][j];
			}
			
			if (sum == 1) {
				skipRow.add(i);
				for (int j=0;j<pathMatrixNbCol;j++) {
					if (pathMatrix[i][j] == 1) {
						skipCol.add(j);
						break;
					}
				}	
			}
		}
		
		if (skipRow.size()>0) {
			int nbCol = pathMatrixNbCol - skipCol.size();
			int nbRow = pathMatrixNbRow - skipRow.size();
			int[][] tmp = new int[nbRow][nbCol];
			ArrayList<String> l = new ArrayList<String>();

			// build exception node list and new list of pathMatrix nodes
			for (int j=0;j<pathMatrixNbCol;j++) {
				if (!skipCol.contains(j))
					l.add(pathMatrixNodeList.get(j));
				else
					exceptionNode.add(pathMatrixNodeList.get(j));
			}
			
			// build new pathMatrix
			int ctRow = 0;
			int ctCol = 0;
			for (int i=0;i<pathMatrixNbRow;i++) {
				if (!skipRow.contains(i)) {
					ctCol = 0;
					for (int j=0;j<pathMatrixNbCol;j++) {
						if (!skipCol.contains(j)) {
							tmp[ctRow][ctCol] = pathMatrix[i][j];
							ctCol++;
						}
					}
					ctRow++;
				}
			}
			
			// replace original data
			pathMatrix = tmp;
			pathMatrixNbRow = ctRow;
			pathMatrixNbCol = ctCol;
			pathMatrixNodeList = l;
		}
	}
	
	
	public void printPathMatrix() {
		
		// print col labels
		for (int i=0;i<pathMatrixNbCol;i++)
			System.out.print(pathMatrixNodeList.get(i)+" ");
		System.out.println();
		
		// print data
		for (int i=0;i<pathMatrixNbRow;i++) {
			for (int j=0;j<pathMatrixNbCol;j++) {
				System.out.print(pathMatrix[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	
}
