package fr.curie.BiNoM.pathways.analysis.structure;


/**
 * Simple class to store Omega score data for optimal cut sets
 * elementary nodes.
 * 
 * @author ebonnet
 *
 */
public class OmegaScoreData implements Comparable<OmegaScoreData> {
	
	/**
	 * Node ID
	 */
	public String nodeId;
	
	/**
	 * Ocsana overall omega score.
	 */
	public double score;

	public OmegaScoreData(String id, double score) {
		this.nodeId = id; 
		this.score = score;
	}

	// sort by decreasing score value
	public int compareTo(OmegaScoreData b) {
		
		OmegaScoreData m = b;

		if(this.score < m.score) {
			return 1;
		} else if(this.score == m.score) {
			return 0;
		} else {
			return -1;
		}
	}
}
