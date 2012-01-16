package fr.curie.BiNoM.pathways.analysis.structure;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

import fr.curie.BiNoM.pathways.utils.CombinationGenerator;
import fr.curie.BiNoM.pathways.utils.Utils;


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
	
	public ArrayList<HashSet<Integer>> hitSet = new ArrayList<HashSet<Integer>>();
	
	public ArrayList<BitSet> hitSetBin = new ArrayList<BitSet>();
	
	/**
	 * List of sets that are not hitting sets
	 */
	public ArrayList<HashSet<Integer>> seedSetList = new ArrayList<HashSet<Integer>>();
	
	/**
	 * List of path matrix columns (nodes) encoded as binary (BitSet) objects.
	 */
	public ArrayList<BitSet> pathMatrixColBin = new ArrayList<BitSet>();
	
	public HashSet<Integer> hitSetNodeList = new HashSet<Integer>();
	
	// berge
	public ArrayList<BitSet> pathMatrixRowBin = new ArrayList<BitSet>();
	
	/**
	 * Constructor
	 */
	public OptimalCombinationAnalyzer() {
		// nothing here for the moment!
	}
	
	public void mainBerge() {
		
		HashSet<BitSet> mcs = initFirstRowBerge();
		mcs = createCandidateSetBerge(mcs, pathMatrixRowBin.get(1));
		for (int i=1;i<pathMatrixNbRow;i++) {
			mcs = createCandidateSetBerge(mcs, pathMatrixRowBin.get(i));
			mcs = checkMinimalityBerge(mcs);
		}
		
		for (BitSet b : mcs)
			System.out.println(convertBitSetToString(b,pathMatrixNbCol));
	}
	
	public HashSet<BitSet> checkMinimalityBerge(HashSet<BitSet> mcs) {
		
		for (BitSet b : mcs)
			System.out.println(convertBitSetToString(b,pathMatrixNbCol));
		System.out.println("//");
		
		HashSet<BitSet> flag = new HashSet<BitSet>();
		
		for (BitSet b1 : mcs) {
			for (BitSet b2 : mcs) {
				BitSet test = (BitSet) b1.clone();
				test.and(b2);
				if (test.cardinality() == b1.cardinality() && b2.cardinality() > b1.cardinality()) {
					// b2 is a strict superset of b1, flag  for remove
					flag.add(b2);
					//System.out.println(convertBitSetToString(b1,pathMatrixNbCol));
					//System.out.println(convertBitSetToString(b2,pathMatrixNbCol));
					//System.out.println("//");
				}
			}
		}
		
//		for (BitSet b : mcs) {
//			if (!flag.contains(b))
//			System.out.println(convertBitSetToString(b,pathMatrixNbCol));
//		}
//		System.out.println("//");
		
		HashSet<BitSet> ret = new HashSet<BitSet>();
		for (BitSet b : mcs)
			if (!flag.contains(b))
				ret.add(b);
		
//		for (BitSet b : ret)
//			System.out.println(convertBitSetToString(b,pathMatrixNbCol));
		
		return(ret);

	}
	
	public HashSet<BitSet> createCandidateSetBerge(HashSet<BitSet> mcs, BitSet row) {
		HashSet<BitSet> cand = new HashSet<BitSet>();
		for (BitSet cs : mcs) {
			// add each node of the row to previous hit set
			for(int i=row.nextSetBit(0); i>=0; i=row.nextSetBit(i+1)) { // loop over bits set to 1 in BitSet object
				BitSet newOne = (BitSet) cs.clone();
				newOne.set(i);
				cand.add(newOne);
			}
		}
		return(cand);
	}
	
	public HashSet<BitSet> initFirstRowBerge() {
		
		HashSet<BitSet> mcs = new HashSet<BitSet>();
		
		// initialize mcs with first row of the matrix
		BitSet firstRow = pathMatrixRowBin.get(0);
		for (int i=0;i<pathMatrixNbCol;i++) {
			BitSet tmp = new BitSet(pathMatrixNbCol);
			tmp.set(i);
			tmp.and(firstRow);
			if (tmp.cardinality() == 1) {
				BitSet b = new BitSet(pathMatrixNbCol);
				b.set(i);
				mcs.add(b);
			}
		}
		return(mcs);
	}
	/**
	 * convert path matrix rows to BitSet objects
	 */
	public void convertPathMatrixRowToBinary() {
		for (int i=0;i<pathMatrixNbRow;i++) {
			BitSet b = new BitSet(pathMatrixNbCol);
			for (int j=0;j<pathMatrixNbCol;j++) {
				if (pathMatrix[i][j] == 1)
					b.set(j);
			}
			pathMatrixRowBin.add(b);
		}
	}
	/**
	 * Convert the path matrix columns (nodes) to a list of binary (BitSet) objects. 
	 */
	public void convertPathMatrixColToBinary() {
		for (int i=0;i<pathMatrixNbCol;i++) {
			pathMatrixColBin.add(new BitSet(pathMatrixNbRow));
			for (int j=0;j<pathMatrixNbRow;j++) {
				if (pathMatrix[j][i] == 1)
					pathMatrixColBin.get(i).set(j);
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
	public void addHitSetBin(HashSet<Integer> set) {
		hitSetBin.add(new BitSet(pathMatrixNbCol));
		for (int i : set) {
			hitSetBin.get(hitSetBin.size()-1).set(i);
		}
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
	 * Convert a HashSet of column indices to a BitSet object representing 
	 * a row of the path matrix.
	 * 
	 * @param HashSet of integers
	 * @return BitSet object
	 */
	public BitSet getBitSetRowHS(HashSet<Integer> indices) {
		BitSet ret = new BitSet(pathMatrixNbCol);
		for (int i : indices) {
			ret.set(i);
		}
		return(ret);
	}
	
	/**
	 * Search for hitting sets for set size > 2. 
	 * All combinations are generated for each set.
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
			
			// stop if setSize value is greater than the number of nodes
			if (setSize > pathMatrixNbCol) {
				System.out.println("Stop: setSize is greater than the number of nodes.");
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
				//BitSet b1 = (BitSet) pathMatrixBin.get(indices[0]).clone();
				BitSet b1 = new BitSet(pathMatrixNbRow);
				for (int i=0;i<indices.length;i++) {
					b1.or(pathMatrixColBin.get(indices[i]));
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
//						hitSet.add(new HashSet<Integer>());
//						int index = hitSet.size()-1;
//						for (int i=0;i<indices.length;i++) {
//							hitSet.get(index).add(indices[i]);
//							hitSetNodeList.add(indices[i]);
//						}
//						addHitSetBin(hitSet.get(index));
//						ct++;
						
						HashSet<Integer> novel = new HashSet<Integer>();
						for (int i=0;i<indices.length;i++) {
							novel.add(indices[i]);
							hitSetNodeList.add(indices[i]);
						}
						addHitSetBin(novel);
						hitSet.add(novel);
						ct++;
						for (int i : novel)
							System.out.print(pathMatrixNodeList.get(i)+":");
						System.out.println();
					}
				}
			}
			if (ct>0)
				System.out.println("found "+ct+ " hit sets size "+setSize);
			setSize++;
		}
	}

	public void searchHitSetOpt(int max) {
		
		int setSize = 3;
		while (setSize <= max) {
			
			// stop if all elementary nodes are covered
			if (hitSetNodeList.size() == pathMatrixNbCol) {
				setSize--;
				System.out.println("Stop: all elementary nodes are covered. set size = "+setSize);
				break;
			}
			// stop if we don't have seed sets 
			if (seedSetList.size() == 0) {
				setSize--;
				System.out.println("Stop: seed set list equal zero.");
				break;
			}
			
			System.out.println();
			System.out.println("Search for hit set size "+ setSize);
			
			// generate combinations from previous set
			System.out.println("Generating new combination set...");
			System.out.println("seed set size: "+seedSetList.size());
			
			Long tic = System.currentTimeMillis();
			ArrayList<HashSet<Integer>> newList = new ArrayList<HashSet<Integer>>();
			int cpt = 0;
			for (HashSet<Integer> set : seedSetList) {
				// add new node to previous set
				for (int i=0;i<pathMatrixNbCol;i++) {
					if (!set.contains(i)) {
						HashSet<Integer> tmp = new HashSet<Integer>();
						for (int j : set) {
							tmp.add(j);
						}
						tmp.add(i);
						if (!newList.contains(tmp))
							newList.add(tmp);
					}
					cpt++;
				}
			}
			Long toc = System.currentTimeMillis() - tic;
			
			System.out.println("Nb combinations: "+calcCombinations(pathMatrixNbCol, setSize));
			System.out.println("Nb enumerations: " + cpt);
			System.out.println("time enumeration: " + toc);
			
			// reset seed list
			seedSetList = new ArrayList<HashSet<Integer>>();
			
			System.out.println("enumeration set size: "+newList.size());
			int ct=0;
			tic = System.currentTimeMillis();
			for (HashSet<Integer> hs : newList) {
				// check for true hitting set
				BitSet b1 = new BitSet(pathMatrixNbRow);
				Iterator<Integer> it = hs.iterator();
				while(it.hasNext()) {
					int i = it.next();
					b1.or(pathMatrixColBin.get(i));
				}

				if (b1.cardinality() == pathMatrixNbRow) {
					// now check if the set is minimal, i.e do not overlap with any previous hitting set
					BitSet test = getBitSetRowHS(hs);
					boolean isMinimal = true;
					for (BitSet bs : hitSetBin) {
						BitSet b2 = (BitSet) bs.clone();
						b2.and(test);
						if (b2.cardinality() == bs.cardinality()) {
							isMinimal = false;
							break;
						}
					}
					if (isMinimal == true) {
						hitSet.add(hs);
						for (int i : hs) {
							hitSetNodeList.add(i);
						}
						addHitSetBin(hs);
						for (int i : hs)
							System.out.print(pathMatrixNodeList.get(i)+":");
						System.out.println();
						ct++;
					}
				}
				else {
					// add set to seed list
					if (!seedSetList.contains(hs))
						seedSetList.add(hs);
				}
			}
			toc = System.currentTimeMillis() - tic;
			System.out.println("time hitset + minimal: "+toc);
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
		System.out.println("Search set size: "+cg.getTotal());
		while(cg.hasMore()) {
			indices = cg.getNext();
			/*
			 * check if the combination is a hitting set. This is done by doing a logical OR 
			 * between the two nodes BitSet vectors. If all paths are hit, then every position 
			 * should be set to 1.
			 */
			BitSet tmp = (BitSet) pathMatrixColBin.get(indices[0]).clone();
			tmp.or(pathMatrixColBin.get(indices[1]));
			if (tmp.cardinality() == pathMatrixNbRow) {
				// we have a hitting set
				hitSet.add(new HashSet<Integer>());
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
				seedSetList.add(new HashSet<Integer>());
				seedSetList.get(seedSetList.size()-1).add(indices[0]);
				seedSetList.get(seedSetList.size()-1).add(indices[1]);
			}
		}
		
		// print out sets
		for (int i=0;i<hitSet.size();i++) {
			HashSet<Integer> tmp = hitSet.get(i);
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
	
	/**
	 * Calculate the number of combinations of k size for a set of n elements.
	 * 
	 * @author ebonnet
	 * 
	 * @param n the total number of elements
	 * @param k the size of the subset
	 * @return BigInteger the number of combinations
	 */
	public long calcCombinations(int n, int k) {
		BigInteger num = Utils.factorial(n);
		BigInteger den = Utils.factorial(k);
		den = den.multiply(Utils.factorial(n-k));
		return num.divide(den).longValue();
	}
}
