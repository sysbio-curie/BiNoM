package fr.curie.BiNoM.pathways.analysis.structure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
	
	/**
	 * Hitting sets encoded as a set of BitSet objects.
	 */
	public HashSet<BitSet> hitSetSB = new HashSet<BitSet>();
	
	/**
	 * List of path matrix columns (nodes) encoded as binary (BitSet) objects.
	 */
	private ArrayList<BitSet> pathMatrixColBin = new ArrayList<BitSet>();
	
	/**
	 * List of path matrix rows, encoded as BitSet objects.
	 */
	private ArrayList<BitSet> pathMatrixRowBin = new ArrayList<BitSet>();
	
	/**
	 * Ordered list of nodes nodes with their corresponding omega scores. 
	 */
	public ArrayList<OmegaScoreData> omegaScoreList;
	
	/**
	 * List of nodes ordered by decreasing Ocsana score
	 */
	public ArrayList<String> orderedNodesByScore;
	
	/**
	 * Maximum intervention set size for partial search. 
	 */
	public long maxHitSetSize;
	
	/**
	 * Maximum number of sets for partial search.
	 */
	public long maxNbHitSet;
	
	/**
	 * A report to log events on.
	 */
	public StringBuffer report;
	
	/**
	 * System independent newline character
	 */
	private static String newline = System.getProperty("line.separator");
	
	/**
	 * flag to indicate if Berge's search is restricted, default is false  
	 */
	public boolean restrictBerge = false;
	
	/**
	 * Constructor
	 */
	public OptimalCombinationAnalyzer() {
	
	}
	
	/**
	 * Implementation of Berge's algorithm to find minimal cut sets.
	 * 
	 * pathMatrixRowBin should be set.
	 * 
	 * @author ebo
	 */
	public void mainBerge(boolean printLog) {
		
		System.out.println("Start Berge's algorithm search...");
		
		// Minimal cut set
		hitSetSB = initFirstRowBerge();
		long tic = System.currentTimeMillis();
		if (pathMatrixRowBin.size()>1) {
			hitSetSB = createCandidateSetBerge(hitSetSB, pathMatrixRowBin.get(1));
			int maxSetSize=0;
			for (int i=1;i<pathMatrixNbRow;i++) {
				if (this.restrictBerge == false) {
					hitSetSB = createCandidateSetBerge(hitSetSB, pathMatrixRowBin.get(i));
				}
				else {
					hitSetSB = createCandidateSetBergeRestrict(hitSetSB, pathMatrixRowBin.get(i));
				}
				if (printLog) {
					int pn = i+1;
					System.out.println("Analyzing path number "+pn+" / "+pathMatrixNbRow);
					if (hitSetSB.size() > maxSetSize)
						maxSetSize = hitSetSB.size();
				}
				hitSetSB = checkMinimalityBerge(hitSetSB);
			}
			if (printLog) {
				System.out.println("max CI size: "+maxSetSize);
				System.out.println("final CI size: "+hitSetSB.size());
			}
		}
		long toc = System.currentTimeMillis() - tic;
		if (report != null) {
			double t = (double) toc / 1000;
			DecimalFormat df = new DecimalFormat("#.###");
			report.append(newline+"Total timing for the search: "+df.format(t)+" sec."+newline);
		}
		System.out.println("timing: "+toc);
	}
	
	/**
	 * Check and keep only minimal sets.
	 * 
	 * @param mcs sets
	 * @return minimal sets
	 * 
	 * @author ebo
	 */
	private HashSet<BitSet> checkMinimalityBerge(HashSet<BitSet> mcs) {
		
		HashSet<BitSet> flag = new HashSet<BitSet>();
		
		for (BitSet b1 : mcs) {
			if (!flag.contains(b1)) {
				for (BitSet b2 : mcs) {
					BitSet test = (BitSet) b1.clone();
					test.and(b2);
					if (test.cardinality() == b1.cardinality() && b2.cardinality() > b1.cardinality()) {
						// b2 is a strict superset of b1, flag it
						flag.add(b2);
					}
				}
			}
		}
		
		HashSet<BitSet> ret = new HashSet<BitSet>();
		for (BitSet b : mcs)
			if (!flag.contains(b))
				ret.add(b);
		
		return(ret);
	}
	
	/**
	 * Initialize the ordered list of nodes by omega score.
	 */
	public void initOrderedNodesList() {
		orderedNodesByScore = new ArrayList<String>();
		for (OmegaScoreData osd : omegaScoreList) {
			orderedNodesByScore.add(osd.nodeId);
		}
	}
	
	/**
	 * Create new candidate set by adding nodes from new row of the path matrix.
	 * 
	 * @param mcs minimal cut set
	 * @param row new row as BitSet object
	 * @return new set
	 * 
	 * @author ebo
	 */
	private HashSet<BitSet> createCandidateSetBerge(HashSet<BitSet> mcs, BitSet row) {
		HashSet<BitSet> cand = new HashSet<BitSet>();
		for (BitSet cs : mcs) {
			/*
			 * First test if the hit set and the row are disjoint, if yes generate new hit set 
			 * by adding each node of the row to the hit set, otherwise just keep the hit set as it is,
			 * because adding nodes would just create supersets. 
			 */
			BitSet testJoint = (BitSet) cs.clone();
			testJoint.and(row);
			if (testJoint.cardinality() == 0) {
				// add each node of the row to previous hit set
				for(int i=row.nextSetBit(0); i>=0; i=row.nextSetBit(i+1)) { // loop over bits set to 1 in BitSet object
					BitSet newOne = (BitSet) cs.clone();
					newOne.set(i);
					cand.add(newOne);
				}
			}
			else {
				cand.add(cs);
			}
		}
		return(cand);
	}

	
	/**
	 * Create new candidate set by adding nodes from new row of the path matrix, while
	 * checking for a given max set size.
	 * 
	 * @param mcs minimal cut set
	 * @param row new row as BitSet object
	 * @return new set
	 * 
	 * @author ebo
	 */
	private HashSet<BitSet> createCandidateSetBergeRestrict(HashSet<BitSet> mcs, BitSet row) {
		HashSet<BitSet> cand = new HashSet<BitSet>();
		for (BitSet cs : mcs) {
			/*
			 * we only want hit sets <= maxHitSetSize 
			 */
			if (cs.cardinality() <= this.maxHitSetSize) {
				/*
				 * First test if the hit set and the row are disjoint, if yes generate new hit set 
				 * by adding each node of the row to the hit set, otherwise just keep the hit set as it is,
				 * because adding nodes would just create supersets. 
				 */
				BitSet testJoint = (BitSet) cs.clone();
				testJoint.and(row);
				if (testJoint.cardinality() == 0) {
					// add each node of the row to previous hit set
					for(int i=row.nextSetBit(0); i>=0; i=row.nextSetBit(i+1)) { // loop over bits set to 1 in BitSet object
						BitSet newOne = (BitSet) cs.clone();
						newOne.set(i);
						cand.add(newOne);
					}
				}
				else {
					cand.add(cs);
				}
			}
		}
		return(cand);
	}

	
	/**
	 * Initialization of Berge's algorithm with the first row of the path matrix.
	 * 
	 * @return set
	 * 
	 * @author ebo
	 */
	private HashSet<BitSet> initFirstRowBerge() {
		
		HashSet<BitSet> mcs = new HashSet<BitSet>();
		
		// initialize mcs with first row of the matrix
		if(mcs.size() > 0){
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
		}
		return(mcs);
	}
	
	/**
	 * Final format for hit set list.
	 * 
	 * @return list of sets (as string)
	 */
	public ArrayList<HashSet<String>> formatHitSetSB() {
		
		/*
		 * Also add hit sets of size one and exception 1 nodes, if any were found previously
		 * that is, if the lists are not empty.
		 */
		ArrayList<HashSet<String>> ret = new ArrayList<HashSet<String>>();
		for (String node : hitSetSizeOne) {
			HashSet<String> newset = new HashSet<String>();
			newset.add(node);
			for (String ex : exceptionNode) {
				newset.add(ex);
			}
			ret.add(newset);
		}
		for (BitSet bs : hitSetSB) {
			HashSet<String> newset = new HashSet<String>();
			for(int i=bs.nextSetBit(0); i>=0; i=bs.nextSetBit(i+1)) {
				newset.add(pathMatrixNodeList.get(i));
			}
			for (String ex : exceptionNode) {
				newset.add(ex);
			}
			ret.add(newset);
		}
		return(ret);
	}
	
	/**
	 * Print out nodes corresponding to the bits set in the BitSet object.
	 * 
	 * @param b
	 */
	private void printHitSet (BitSet b) {
		System.out.print("[");
		for(int i=b.nextSetBit(0); i>=0; i=b.nextSetBit(i+1)) {
			System.out.print(pathMatrixNodeList.get(i)+":");
		}
		System.out.println("]");
	}
	
	/**
	 * Print out hit set list
	 */
	private void printHitSetList() {
		for (BitSet b : hitSetSB) {
			printHitSet(b);
		}
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
	private String convertBitSetToString(BitSet b, int len) {
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
	 * Convert an array of column indices to a BitSet object representing 
	 * a row of the path matrix.
	 * 
	 * @param indices array of integers
	 * @return BitSet object
	 */
	private BitSet getBitSetRow(int[] indices) {
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
	private BitSet getBitSetRowHS(HashSet<Integer> indices) {
		BitSet ret = new BitSet(pathMatrixNbCol);
		for (int i : indices) {
			ret.set(i);
		}
		return(ret);
	}
	
	/**
	 * Search for hitting sets for set size > 2. 
	 * All combinations are generated for each set from scratch.
	 * 
	 * @param max maximum set size to search for.
	 */
	private void searchHitSetFull(int max) {
		
		System.out.println("starting full enumeration search...");
		
		int setSize = 3;
		while (setSize <= max) {
			
			// stop if setSize value is greater than the number of nodes
			if (setSize > pathMatrixNbCol) {
				System.out.println("Stop: setSize is greater than the number of nodes.");
				break;
			}
			
			System.out.println("Search for intervention set size "+ setSize);
			
			// generate all combinations and test them
			int[] indices;
			CombinationGenerator cg = new CombinationGenerator(pathMatrixNbCol, setSize);
			System.out.println("Combination set size: "+cg.getTotal());
			int ct=0;
			while(cg.hasMore()) {
				indices = cg.getNext();
				
				// check if the set is a hitting set
				BitSet b1 = new BitSet(pathMatrixNbRow);
				for (int i=0;i<indices.length;i++) {
					b1.or(pathMatrixColBin.get(indices[i]));
				}

				if (b1.cardinality() == pathMatrixNbRow) {
					// now check if the set is minimal, i.e do not overlap with any previously defined hitting set
					BitSet test = getBitSetRow(indices);
					boolean isMinimal = true;
					for (BitSet hs : hitSetSB) {
						BitSet b2 = (BitSet) hs.clone();
						b2.and(test);
						if (b2.cardinality() == hs.cardinality()) {
							isMinimal = false;
							break;
						}
					}
					if (isMinimal == true) {
						BitSet nbs = new BitSet(pathMatrixNbCol);
						for (int i=0;i<indices.length;i++) {
							nbs.set(indices[i]);
						}
						hitSetSB.add(nbs);
						ct++;
					}
				}
			}
			if (ct>0)
				System.out.println("found "+ct+ " intervention sets size "+setSize);
			setSize++;
		}
	}

	/**
	 * Search for minimal intervention sets by partial enumeration.
	 */
	public void searchHitSetPartial() {
		
		System.out.println("starting partial enumeration search...");
		System.out.println("maxHitSetSize "+maxHitSetSize+" maxNbHitSet "+maxNbHitSet);
		
		long tic = System.currentTimeMillis();
		
		/*
		 * First enumerate and test sets of size 2
		 */
		if (pathMatrixNbCol>0)
			searchHitSetSizeTwo();
		else
			return;
		
		HashMap<String, Integer> mapNodes = new HashMap<String, Integer>();
		for (int i=0;i<pathMatrixNodeList.size();i++)
			mapNodes.put(pathMatrixNodeList.get(i), i);
		
		int setSize = 3;
		
		while (setSize <= maxHitSetSize) {
			
			// stop if setSize value is greater than the number of nodes
			if (setSize > pathMatrixNbCol) {
				System.out.println("Stop: setSize is greater than the number of nodes.");
				break;
			}
			
			System.out.println("\nSearch for CI size "+ setSize);
			report.append(newline + "Search for CI size "+ setSize + newline);
			
			long nbComb =  calcCombinations(pathMatrixNbCol, setSize);
			NumberFormat form = NumberFormat.getInstance();
			form.setGroupingUsed(true);
			
			System.out.println("calculated nb of combinations: "+form.format(nbComb));
			report.append("Total nb of possible combinations: "+form.format(nbComb)+newline);
			
			int selectionSize;
			/*
			 * this is a map from the selection index to the pathMatrixNode index
			 */
			HashMap<Integer, Integer> mapIdx = new HashMap<Integer, Integer>();
			
			/*
			 * Selection procedure
			 */
			if (nbComb > maxNbHitSet) {
				
				// select high scoring nodes
				selectionSize = optimalSetSize(setSize, maxNbHitSet);
				System.out.println("selection size: "+selectionSize);
				report.append("selection size: "+selectionSize+newline);
				
				/*
				 * Stop searching if the selection size is smaller than the current set size
				 */
				if (setSize > selectionSize)
					break;
				
				for (int i=0;i<selectionSize;i++) {
					String nodeID = orderedNodesByScore.get(i);
					int nodeIndex = mapNodes.get(nodeID);
					mapIdx.put(i, nodeIndex);
				}
			}
			else {
				// no selection
				System.out.println("no selection");
				
				for (int i=0;i<pathMatrixNbCol;i++)
					mapIdx.put(i, i);
				selectionSize = pathMatrixNbCol;
			}
			
			// generate all combinations and test them
			int[] idx;
			CombinationGenerator cg = new CombinationGenerator(selectionSize, setSize);
			System.out.println("effective nb of combinations: "+form.format(cg.getTotal()));
			report.append("Tested nb of combinations: "+form.format(cg.getTotal()) + newline);
			
			int ct=0;
			while(cg.hasMore()) {
				idx = cg.getNext();
				
				// check if the set is a hitting set
				BitSet b1 = new BitSet(pathMatrixNbRow);
				for (int i=0;i<idx.length;i++) {
					b1.or(pathMatrixColBin.get(mapIdx.get(idx[i])));
				}

				if (b1.cardinality() == pathMatrixNbRow) {
					// now check if the set is minimal, i.e do not overlap with any previously defined hitting set
					
					/*
					 * Build a BitSet corresponding to a row in the pathMatrix, with all
					 * nodes of the set we are testing.
					 */
					BitSet test = new BitSet(pathMatrixNbCol);
					for (int i=0;i<idx.length;i++) {
						test.set(mapIdx.get(idx[i]));
					}
					/*
					 * Now compare this BitSet to all the previously found hitting sets, and check
					 * that there is no overlap.
					 */
					boolean isMinimal = true;
					for (BitSet hs : hitSetSB) {
						BitSet b2 = (BitSet) hs.clone();
						b2.and(test);
						if (b2.cardinality() == hs.cardinality()) {
							isMinimal = false;
							break;
						}
					}
					if (isMinimal == true) {
						/*
						 * We have a new hitting set
						 */
						BitSet nbs = new BitSet(pathMatrixNbCol);
						for (int i=0;i<idx.length;i++) {
							nbs.set(mapIdx.get(idx[i]));
						}
						hitSetSB.add(nbs);
						ct++;
					}
				}
			}
			if (ct>0) {
				System.out.println("found "+ct+ " sets size "+setSize);
				report.append("found "+ct+ " sets size " + setSize + newline);
			}
			
			setSize++;
		}
		
		long toc = System.currentTimeMillis() - tic;
		if (report != null) {
			double t = toc / 1000;
			DecimalFormat df = new DecimalFormat("#.###");
			report.append(newline);
			report.append("Total timing for the search: "+df.format(t)+" sec."+newline);
		}
		System.out.println("timing: "+toc);
	}

	
	
	/**
	 * Search for hit sets of size 2.
	 */
	private void searchHitSetSizeTwo() {
		
		System.out.println("Search for intervention sets size 2");
		
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
			BitSet nbs = new BitSet(pathMatrixNbCol);
			nbs.set(indices[0]);
			nbs.set(indices[1]);
			
			if (tmp.cardinality() == pathMatrixNbRow) {
				// we have a hitting set
				hitSetSB.add(nbs);
			}
		}
		System.out.println("found "+hitSetSB.size()+" hitting sets");
	}
	
	/**
	 * Select hit sets that have a size below a certain threshold.
	 * 
	 * @param maxSize size threshold
	 */
	private void selectHitSetSize(int maxSize) {
		HashSet<BitSet> n = new HashSet<BitSet>();
		for (BitSet b : hitSetSB) {
			if (b.cardinality() <= maxSize) {
				n.add(b);
			}
		}
		hitSetSB = n;
	}
	
	/**
	 * Search for hit sets of size 1. If any is found, then 
	 * remove the corresponding columns (nodes) from the path matrix.
	 */
	public void searchHitSetSizeOne() {
		
		System.out.println("Search for intervention set size 1");
		
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
			
			// remove hit set size one nodes from the list of nodes ordered by score
			for (int i =0;i<hitSetSizeOne.size();i++) {
				int idx = orderedNodesByScore.indexOf(hitSetSizeOne.get(i));
				if (idx >= 0) 
					orderedNodesByScore.remove(idx);
			}
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
			
			// remove exception nodes from the list of nodes ordered by score
			for (int i =0;i<exceptionNode.size();i++) {
				int idx = orderedNodesByScore.indexOf(exceptionNode.get(i));
				if (idx >= 0) 
					orderedNodesByScore.remove(idx);
			}
			
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
	private void printPathMatrix() {
		
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
	private void savePathMatrix(String dataFileName, String nodeListFileName) {
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
	 * @author ebo
	 * 
	 * @param n the total number of elements
	 * @param k the size of the subset
	 * @return BigInteger the number of combinations
	 */
	private long calcCombinations(int n, int k) {
		BigInteger num = Utils.factorial(n);
		BigInteger den = Utils.factorial(k);
		den = den.multiply(Utils.factorial(n-k));
		return num.divide(den).longValue();
	}
	
	/**
	 * Calculate the maximum set size so that the number of combination 
	 * of size setSize are below a cutoff value.
	 * 
	 * @author ebo
	 * 
	 * @param int setSize - current hit set size
	 * @param long maxNbSet - maximum number of sets cutoff value
	 * @return int maxSetSize - maximum selection set size
	 */
	private int optimalSetSize(int setSize, long maxNbSet) {
		
		int n = setSize;
		int k = setSize;
		long comb = 0;
		while(comb < maxNbSet) {
			n++;
			comb = calcCombinations(n,k);
		}
		n--;
		return(n);
	}

	/**
	 * Saving hit sets to a file.
	 * 
	 * @param filename
	 */
	private void saveHitSetSB(String filename) {
		System.out.println("Saving hit sets to file "+filename+"...");
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
			for (BitSet b : hitSetSB) {
				String str = "";
				for(int i=b.nextSetBit(0); i>=0; i=b.nextSetBit(i+1)) {
					str = str + pathMatrixNodeList.get(i) + "\t";
				}
				str = str.trim();
				bf.write(str+"\n");
			}
			bf.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
