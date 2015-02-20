package Main;

import java.util.Collection;
import java.util.Iterator;


import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import fr.curie.BiNoM.biopax.propedit.BioPAXToggleNamingService;
import fr.curie.BiNoM.cytoscape.ain.ImportFromSimpleTextInfluenceFile;
import fr.curie.BiNoM.cytoscape.analysis.CalcCentralityDirected;
import fr.curie.BiNoM.cytoscape.analysis.CalcCentralityUndirected;
import fr.curie.BiNoM.cytoscape.analysis.ClusterNetworks;
import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponents;
import fr.curie.BiNoM.cytoscape.analysis.ConvertReactionNetworkToEntityNetwork;
import fr.curie.BiNoM.cytoscape.analysis.ConvertToUndirectedGraph;
import fr.curie.BiNoM.cytoscape.analysis.CycleDecomposition;
import fr.curie.BiNoM.cytoscape.analysis.EdgesFromOtherNetwork;
import fr.curie.BiNoM.cytoscape.analysis.ExcludeIntermediateNodes;
import fr.curie.BiNoM.cytoscape.analysis.ExtractReactionNetwork;
import fr.curie.BiNoM.cytoscape.analysis.ExtractSubnetwork;
import fr.curie.BiNoM.cytoscape.analysis.LinearizeNetwork;
import fr.curie.BiNoM.cytoscape.analysis.MaterialComponents;
import fr.curie.BiNoM.cytoscape.analysis.MonoMolecularReactionsAsEdges;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.PathAnalysis;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.PruneGraph;
import fr.curie.BiNoM.cytoscape.analysis.SCCinAttribute;
import fr.curie.BiNoM.cytoscape.analysis.StronglyConnectedComponents;
import fr.curie.BiNoM.cytoscape.analysis.createNeighborhoodSets;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXAssociateSource;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXExportToFile;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportFromFile;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportFromURL;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXSaveAssociated;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXSyncNetworks;
import fr.curie.BiNoM.cytoscape.biopax.propedit.BioPAXClassTree;
import fr.curie.BiNoM.cytoscape.biopax.propedit.BioPAXPropertyBrowser;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXDisplayIndexInfo;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXGenerateIndex;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXIndexPathAnalysis;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXLoadIndex;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXSelectEntities;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXStandardQuery;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXViewQueryLog;
import fr.curie.BiNoM.cytoscape.brf.ExportToBRFFile;
import fr.curie.BiNoM.cytoscape.brf.ImportFromBRFFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CSMLImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerAssociateSource;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportToFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.ColorCellDesignerProteins;
import fr.curie.BiNoM.cytoscape.celldesigner.MergingMaps;
import fr.curie.BiNoM.cytoscape.celldesigner.ProduceNaviCellMapFiles;
import fr.curie.BiNoM.cytoscape.celldesigner.checkCellDesignerFile;
import fr.curie.BiNoM.cytoscape.celldesigner.extractCellDesignerNotes;
import fr.curie.BiNoM.cytoscape.celldesigner.modifyCellDesignerNotes;
import fr.curie.BiNoM.cytoscape.celldesigner.pathwayStainingCellDesigner;
import fr.curie.BiNoM.cytoscape.netwop.DoubleNetworkDifference;
import fr.curie.BiNoM.cytoscape.netwop.NetworksUpdate;
import fr.curie.BiNoM.cytoscape.sbml.SBMLExportToFile;
import fr.curie.BiNoM.cytoscape.utils.AboutBox;
import fr.curie.BiNoM.cytoscape.utils.CopySelectedNodesAndEdgesFalse;
import fr.curie.BiNoM.cytoscape.utils.CopySelectedNodesAndEdgesTrue;
import fr.curie.BiNoM.cytoscape.utils.CreateSetIntersectionGraph;
import fr.curie.BiNoM.cytoscape.utils.ListAllNodes;
import fr.curie.BiNoM.cytoscape.utils.ListAllReactions;
import fr.curie.BiNoM.cytoscape.utils.ListEdges;
import fr.curie.BiNoM.cytoscape.utils.MergeNetworksAndFilter;
import fr.curie.BiNoM.cytoscape.utils.PasteNodesAndEdges;
import fr.curie.BiNoM.cytoscape.utils.SelectDownstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.SelectEdgesBetweenSelectedNodes;
import fr.curie.BiNoM.cytoscape.utils.SelectNodesByAttributeSubstring;
import fr.curie.BiNoM.cytoscape.utils.SelectUpstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.ShowClipboardContents;

public class Launcher extends AbstractCySwingApp 
{
	private static CySwingAppAdapter adapter;
	public static String appName = "BiNoM";
	
	public Launcher(CySwingAppAdapter adapter)
	{
		super(adapter);
		this.setAdapter(adapter);
        // Add any customization here!
		
		// input output
		adapter.getCySwingApplication().addAction(new BioPAXImportFromFile());
		adapter.getCySwingApplication().addAction(new BioPAXImportFromURL());
		adapter.getCySwingApplication().addAction(new CellDesignerImportFromFile());
		adapter.getCySwingApplication().addAction(new CSMLImportFromFile());
		adapter.getCySwingApplication().addAction(new ImportFromSimpleTextInfluenceFile());
		adapter.getCySwingApplication().addAction(new ImportFromBRFFile());
		
		adapter.getCySwingApplication().addAction(new BioPAXExportToFile());
		adapter.getCySwingApplication().addAction(new CellDesignerExportToFile());
		adapter.getCySwingApplication().addAction(new SBMLExportToFile());
		adapter.getCySwingApplication().addAction(new ExportToBRFFile());
		
		
		adapter.getCySwingApplication().addAction(BioPAXAssociateSource.getInstance());
		adapter.getCySwingApplication().addAction(new BioPAXSaveAssociated());
		adapter.getCySwingApplication().addAction(CellDesignerAssociateSource.getInstance());
		adapter.getCySwingApplication().addAction(new ListAllReactions());
		adapter.getCySwingApplication().addAction(new ListAllNodes());
		adapter.getCySwingApplication().addAction(new ListEdges());

		
		adapter.getCySwingApplication().addAction(new ColorCellDesignerProteins());
		adapter.getCySwingApplication().addAction(new pathwayStainingCellDesigner());
		adapter.getCySwingApplication().addAction(new extractCellDesignerNotes());
		adapter.getCySwingApplication().addAction(new modifyCellDesignerNotes());
		adapter.getCySwingApplication().addAction(new checkCellDesignerFile());
		
		adapter.getCySwingApplication().addAction(new ProduceNaviCellMapFiles());
		adapter.getCySwingApplication().addAction(new MergingMaps());
//		
		
		
		
		//analysis
		adapter.getCySwingApplication().addAction(new ConnectedComponents());
		adapter.getCySwingApplication().addAction(new StronglyConnectedComponents());
		
		adapter.getCySwingApplication().addAction(new SCCinAttribute());
		
		adapter.getCySwingApplication().addAction(new PruneGraph());
		adapter.getCySwingApplication().addAction(new MaterialComponents());
		adapter.getCySwingApplication().addAction(new CycleDecomposition());
		adapter.getCySwingApplication().addAction(new PathAnalysis());
		adapter.getCySwingApplication().addAction(new ExtractSubnetwork());
		adapter.getCySwingApplication().addAction(new CalcCentralityUndirected());
		adapter.getCySwingApplication().addAction(new CalcCentralityDirected());	
		adapter.getCySwingApplication().addAction(new ClusterNetworks());	
		adapter.getCySwingApplication().addAction(new MonoMolecularReactionsAsEdges());
		adapter.getCySwingApplication().addAction(new LinearizeNetwork());
		adapter.getCySwingApplication().addAction(new ExcludeIntermediateNodes());
		adapter.getCySwingApplication().addAction(new ExtractReactionNetwork());
		adapter.getCySwingApplication().addAction(new ConvertReactionNetworkToEntityNetwork());
		adapter.getCySwingApplication().addAction(new PathConsistencyAnalyzer());
		adapter.getCySwingApplication().addAction(new OptimalCutSetAnalyzer());
		adapter.getCySwingApplication().addAction(new createNeighborhoodSets());	
		
		
		//BioPAX 3 utils menu
		adapter.getCySwingApplication().addAction(new BioPAXPropertyBrowser());
		adapter.getCySwingApplication().addAction(new BioPAXClassTree());	
		adapter.getCySwingApplication().addAction(new BioPAXToggleNamingService());
		adapter.getCySwingApplication().addAction(new BioPAXSyncNetworks());
		
		
		// BioPAX 3 query menu
		adapter.getCySwingApplication().addAction(new BioPAXGenerateIndex());
		adapter.getCySwingApplication().addAction(new BioPAXLoadIndex());
		adapter.getCySwingApplication().addAction(new BioPAXDisplayIndexInfo());
		adapter.getCySwingApplication().addAction(new BioPAXSelectEntities());
		adapter.getCySwingApplication().addAction(new BioPAXStandardQuery());
		adapter.getCySwingApplication().addAction(new BioPAXIndexPathAnalysis());
		adapter.getCySwingApplication().addAction(new BioPAXViewQueryLog());
		
		
		// Utilities menu
		adapter.getCySwingApplication().addAction(new SelectEdgesBetweenSelectedNodes());
		adapter.getCySwingApplication().addAction(new SelectUpstreamNeighbours());
		adapter.getCySwingApplication().addAction(new SelectDownstreamNeighbours());
		adapter.getCySwingApplication().addAction(new DoubleNetworkDifference());
		adapter.getCySwingApplication().addAction(new NetworksUpdate());// da sistemare
		adapter.getCySwingApplication().addAction(new EdgesFromOtherNetwork());
		adapter.getCySwingApplication().addAction(new ConvertToUndirectedGraph());
		adapter.getCySwingApplication().addAction(new MergeNetworksAndFilter());
		adapter.getCySwingApplication().addAction(new SelectNodesByAttributeSubstring());
		
		adapter.getCySwingApplication().addAction(new CopySelectedNodesAndEdgesTrue());
		adapter.getCySwingApplication().addAction(new CopySelectedNodesAndEdgesFalse());
		
		adapter.getCySwingApplication().addAction(new PasteNodesAndEdges());
		adapter.getCySwingApplication().addAction(new ShowClipboardContents());
		
		adapter.getCySwingApplication().addAction(new CreateSetIntersectionGraph());
		
		
		//about BiNoM
		adapter.getCySwingApplication().addAction(new AboutBox());
		
		
		
		
	}
	
	public static CyNode getNodeWithName(
            final CyNetwork net, final CyTable table,
            final String colname, final Object value){
        final Collection<CyRow> matchingRows = table.getMatchingRows(colname, value);
        final String primaryKeyColname = table.getPrimaryKey().getName();
        for (final CyRow row : matchingRows)
        {
            final Long nodeId = row.get(primaryKeyColname, Long.class);
            if (nodeId == null)
                continue;
            final CyNode node = net.getNode(nodeId);
            if (node == null)
                continue;
            return node;
        }
        return null;
    }
	
	
	
	public static CyNetwork findNetworkofNode(long id){
		Iterator i = Launcher.getAdapter().getCyNetworkManager().getNetworkSet().iterator();
		while(i.hasNext()){
			CyNetwork net = (CyNetwork) i.next();
			Iterator nodeIter = net.getNodeList().iterator();
			while(nodeIter.hasNext()){
				if(((CyNode)nodeIter.next()).getSUID()==id)
					return net;		
			}		
		}
		return null;
	}
	
	
	public static CyNode findNodeWithName(CyNetwork net, String name){
		
		return null;
	}
	
	 public static CyAppAdapter getAdapter(){
	    	return adapter;
	    }
	 
	 public static CySwingAppAdapter getCySwingAppAdapter(){
	    	return adapter;
	    }

	public static void setAdapter(CySwingAppAdapter adapter) {
		Launcher.adapter = adapter;
	}
}