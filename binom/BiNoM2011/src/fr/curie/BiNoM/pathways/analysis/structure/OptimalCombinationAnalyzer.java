package fr.curie.BiNoM.pathways.analysis.structure;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

import fr.curie.BiNoM.pathways.utils.CombinationGenerator;


/**
 * Optimal Combinations of intervention strategies for network analysis (OCSANA) class.
 * 
 * @author ebo
 *
 */
public class OptimalCombinationAnalyzer {

	/**
	 * Path matrix (without target nodes). Paths are encoded as rows, nodes as columns.
	 */
	public int[][] pathMatrix;
	
	/**
	 * Path matrix number of rows
	 */
	public int pathMatrixNbRow;
	
	/**
	 * Path matrix number of columns
	 */
	public int pathMatrixNbCol;
	
	/**
	 * Node labels for path matrix (columns)
	 */
	public ArrayList<String> pathMatrixNodeList;
	
	/**
	 * Exception node list: correspond to rows of the original matrix having a single '1' value.
	 * These are input nodes directly connected to an output node.
	 */
	public ArrayList<String> exceptionNode = new ArrayList<String>();
	
	/**
	 * List of hitting sets of size one.
	 */
	public ArrayList<String> hitSetSizeOne = new ArrayList<String>();
	
	public ArrayList<ArrayList<Integer>> hitSet = new ArrayList<ArrayList<Integer>>();
	
	public ArrayList<BitSet> hitSetBin = new ArrayList<BitSet>();
	
	/**
	 * List of sets that are not hitting sets
	 */
	public ArrayList<ArrayList<Integer>> seedSetList = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * set of node indices corresponding to sets that are not hitting sets
	 */
	public HashSet<Integer> seedSetIndex = new HashSet<Integer>();
	
	/**
	 * List of path matrix columns (nodes) encoded as binary (BitSet) objects.
	 */
	public ArrayList<BitSet> pathMatrixBin = new ArrayList<BitSet>();
	
	public HashSet<Integer> hitSetNodeList = new HashSet<Integer>();
	
	/**
	 * Constructor
	 */
	public OptimalCombinationAnalyzer() {
		// nothing here for the moment!
	}
	
	/**
	 * Convert the path matrix columns (nodes) to a list of binary (BitSet) objects. 
	 */
	public void convertPathMatrixToBinary() {
		for (int i=0;i<pathMatrixNbCol;i++) {
			pathMatrixBin.add(new BitSet(pathMatrixNbRow));
			for (int j=0;j<pathMatrixNbRow;j++) {
				if (pathMatrix[j][i] == 1)
					pathMatrixBin.get(i).set(j);
			}
		}
	}
	
	/**
	 * Convert a BitSet object to a string with 0 and 1.
	 * 
	 * @param b BitSet object
	 * @param len BitSet length
	 * @return string
	 */
	public String convertBitSetToString(BitSet b, int len) {
		String ret = "";
		for (int i=0;i<len;i++) {
			if (b.get(i) == true)
				ret = ret + "1";
			else
				ret = ret + "0";
		}
		return(ret);
	}
	
	/**
	 * Add hitting set as BitSet object.
	 * @param set
	 */
	public void addHitSetBin(ArrayList<Integer> set) {
		hitSetBin.add(new BitSet(pathMatrixNbCol));
		for (int i=0;i<set.size();i++) {
			int index = set.get(i);
			hitSetBin.get(hitSetBin.size()-1).set(index);
		}
	}
	
	public void searchHitSetSizeNtest() {
		
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i=0;i<pathMatrixNbCol;i++) {
			if (!seedSetIndex.contains(i)) {
				index.add(i);
			}
		}
		
		System.out.println("hitset size two:");
		for (int i=0;i<hitSet.size();i++){
			ArrayList<Integer> tmp = hitSet.get(i);
			for (int j=0;j<tmp.size();j++)
				System.out.print(pathMatrixNodeList.get(tmp.get(j))+" ");
			System.out.println();
		}
		
		System.out.println("seed set: ");
		for (int i=0;i<seedSetList.size();i++){
			ArrayList<Integer> tmp = seedSetList.get(i);
			for (int j : tmp) {
				System.out.print(pathMatrixNodeList.get(j)+" ");
			}
			System.out.println();
		}
		
		System.out.println("seedSetIndex:");
		Iterator it = seedSetIndex.iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+" ");
		}
		System.out.println();
		
		System.out.println("index:");
		for (int i=0;i<index.size();i++)
			System.out.print(i+" ");
		System.out.println();
	}
	
	/**
	 * Convert an array of column indices to a BitSet object representing 
	 * a row of the path matrix.
	 * 
	 * @param indices array of integers
	 * @return
	 */
	public BitSet getBitSetRow(int[] indices) {
		BitSet ret = new BitSet(pathMatrixNbCol);
		for (int i=0;i<indices.length;i++) {
			ret.set(indices[i]);
		}
		return(ret);
	}
	
	/**
	 * Search for hitting sets for set size > 2.
	 * 
	 * @param max maximum set size to search for.
	 */
	public void searchHitSetFull(int max) {
		
		int setSize = 3;
		while (setSize <= max) {
			
			// stop if all elementary nodes are covered
			if (hitSetNodeList.size() == pathMatrixNbCol) {
				setSize--;
				System.out.println("Stop: all elementary nodes are covered. set size = "+setSize);
				break;
			}
			System.out.println("Search for hit set size "+ setSize);
			
			// generate all combinations and test them
			int[] indices;
			CombinationGenerator cg = new CombinationGenerator(pathMatrixNbCol, setSize);
			System.out.println("Combination set size: "+cg.getTotal());
			int ct=0;
			while(cg.hasMore()) {
				indices = cg.getNext();
				
				// check if the set is a hitting set
				BitSet b1 = (BitSet) pathMatrixBin.get(indices[0]).clone();
				for (int i=1;i<indices.length;i++) {
					b1.or(pathMatrixBin.get(indices[i]));
				}

				if (b1.cardinality() == pathMatrixNbRow) {
					// now check if the set is minimal, i.e do not overlap with any previously defined hitting set
					BitSet test = getBitSetRow(indices);
					boolean isMinimal = true;
					for (BitSet hs : hitSetBin) {
						BitSet b2 = (BitSet) hs.clone();
						b2.and(test);
						if (b2.cardinality() == hs.cardinality()) {
							isMinimal = false;
							break;
						}
					}
					if (isMinimal == true) {
						hitSet.add(new ArrayList<Integer>());
						int index = hitSet.size()-1;
						for (int i=0;i<indices.length;i++) {
							hitSet.get(index).add(indices[i]);
							hitSetNodeList.add(indices[i]);
						}
						addHitSetBin(hitSet.get(index));
//						for (int i=0;i<indices.length;i++)
//							System.out.print(pathMatrixNodeList.get(indices[i])+" ");
//						System.out.println();
						ct++;
					}
				}
			}
			if (ct>0)
				System.out.println("found "+ct+ " hit sets size "+setSize);
			setSize++;
		}
	}
	
	/**
	 * Search for hit sets of size 2.
	 */
	public void searchHitSetSizeTwo() {
		
		System.out.println("Search for hit sets size 2");
		
		int[] indices;
		// generate all possible combinations of size 2
		CombinationGenerator cg = new CombinationGenerator(pathMatrixNbCol, 2);
		while(cg.hasMore()) {
			indices = cg.getNext();
			/*
			 * check if the combination is a hitting set. This is done by doing a logical OR 
			 * between the two nodes BitSet vectors. If all paths are hit, then every position 
			 * should be set to 1.
			 */
			BitSet tmp = (BitSet) pathMatrixBin.get(indices[0]).clone();
			tmp.or(pathMatrixBin.get(indices[1]));
			if (tmp.cardinality() == pathMatrixNbRow) {
				// we have a hitting set
				hitSet.add(new ArrayList<Integer>());
				// index of last element added
				int index = hitSet.size()-1;
				hitSet.get(index).add(indices[0]);
				hitSet.get(index).add(indices[1]);
				hitSetNodeList.add(indices[0]);
				hitSetNodeList.add(indices[1]);
				addHitSetBin(hitSet.get(index));
			}
			else {
				// not a hitting set, store the combination as a seed for next round
				seedSetList.add(new ArrayList<Integer>());
				seedSetList.get(seedSetList.size()-1).add(indices[0]);
				seedSetList.get(seedSetList.size()-1).add(indices[1]);
				seedSetIndex.add(indices[0]);
				seedSetIndex.add(indices[1]);
			}
		}
		
		// print out sets
		for (int i=0;i<hitSet.size();i++) {
			ArrayList<Integer> tmp = hitSet.get(i);
			for (int j : tmp)
				System.out.print(pathMatrixNodeList.get(j)+":");
			System.out.println();
		}
	}
	
	/**
	 * Search for hit sets of size 1. If any is found, then 
	 * remove the corresponding columns (nodes) from the path matrix.
	 */
	public void searchHitSetSizeOne() {
		
		System.out.println("Search for hit set size 1");
		
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
		
		//print out sets
		for (int i=0;i<hitSetSizeOne.size();i++)
			System.out.println(hitSetSizeOne.get(i));
		
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
		
		System.out.println("Search for exception 1 nodes");
		
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
			
			// print out exception nodes
			for (int i =0;i<exceptionNode.size();i++)
				System.out.println(exceptionNode.get(i));
			
			// replace original data
			pathMatrix = tmp;
			pathMatrixNbRow = ctRow;
			pathMatrixNbCol = ctCol;
			pathMatrixNodeList = l;
		}
	}
	
	/**
	 * Print out the path matrix.
	 */
	public void printPathMatrix() {
		
		System.out.println("Path matrix:");
		
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
	
	/**
	 * Save path matrix and node list to files.
	 * 
	 * @param dataFileName output file name for path matrix
	 * @param nodeListFileName output file name for node list
	 */
	public void savePathMatrix(String dataFileName, String nodeListFileName) {
		System.out.println("Saving data...");
		try {
			PrintWriter data = new PrintWriter(new FileWriter(dataFileName));
			PrintWriter node = new PrintWriter(new FileWriter(nodeListFileName));
			
			String line = "";
			for (int i=0;i<pathMatrixNbCol;i++)
				node.write(pathMatrixNodeList.get(i)+'\n');
			node.close();
			
			for (int i=0;i<pathMatrixNbRow;i++) {
				line = "";
				for (int j=0;j<pathMatrixNbCol;j++)
					line = line + pathMatrix[i][j] + " ";
				line = line.trim();
				data.write(line+"\n");
			}
			data.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
