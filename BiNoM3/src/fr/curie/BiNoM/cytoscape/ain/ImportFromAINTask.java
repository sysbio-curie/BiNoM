package fr.curie.BiNoM.cytoscape.ain;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.*;
import org.cytoscape.model.CyNetwork;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import java.io.*;
import edu.rpi.cs.xgmml.*;
import java.util.*;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.wrappers.*;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class ImportFromAINTask implements Task {
    private TaskMonitor taskMonitor;
    private String AINName;
    public String text;
    private CyNetwork cyNetwork;
    private BioPAX biopax;
    private File file;
    private Vector constitutiveReactions;
    //private URL url;
    private static java.util.HashMap network_bw = new java.util.HashMap();

    public ImportFromAINTask(File _file, String _text, Vector _creact) {
    	file = _file;
    	text = _text;
    	constitutiveReactions = _creact;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "AIN: Import AIN " + file.getName();
    }

    public CyNetwork getCyNetwork() {
	return cyNetwork;
    }

    public void run() {
	try {
		
		//biopax = SimpleTextInfluenceToBioPAX.convertFromFile(file.getAbsolutePath());
		
		SimpleTextInfluenceToBioPAX.getInstance().makeBioPAX(text, constitutiveReactions);
		
		BioPAXToCytoscapeConverter b2s = new BioPAXToCytoscapeConverter();
		b2s.biopax = SimpleTextInfluenceToBioPAX.getInstance().biopax;
		
		BioPAXToCytoscapeConverter.Graph graph = b2s.convert
		(b2s.REACTION_NETWORK_CONVERSION,b2s,
		biopax.idName,
		new BioPAXToCytoscapeConverter.Option());

	    if (graph != null) {		
		
		Graph graphDoc = XGMML.convertXGMMLToGraph(graph.graphDocument);
	    Graph grres = BiographUtils.ShowMonoMolecularReactionsAsEdges(graphDoc);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);

		CyNetwork cyNetwork = NetworkFactory.createNetwork
		    ("AIN_"+file.getName().substring(0, file.getName().length()-4),
		     grDoc,
		     BioPAXVisualStyleDefinition.getInstance(),
		     true, // applyLayout
		     taskMonitor);
	

	    if (cyNetwork != null) {
	    	BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, graph.biopax);
	    }		
	    
	    
	    
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error importing AIN file " +
				  AINName + ": " + e);
	}
    }

}

