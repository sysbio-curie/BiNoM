package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.TaskManager;

import fr.curie.BiNoM.cytoscape.nestmanager.*;
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
import fr.curie.BiNoM.cytoscape.analysis.InducedSubgraph;
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
import fr.curie.BiNoM.cytoscape.biopax.BioPAXVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.biopax.propedit.BioPAXClassTree;
import fr.curie.BiNoM.cytoscape.biopax.propedit.BioPAXPropertyBrowser;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXDisplayIndexInfo;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXGenerateIndex;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXIndexPathAnalysis;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXLoadIndex;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXSelectEntities;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXStandardQuery;
import fr.curie.BiNoM.cytoscape.biopax.query.BioPAXViewQueryLog;
import fr.curie.BiNoM.cytoscape.brf.CreateCellDesignerFileThroughBRF;
import fr.curie.BiNoM.cytoscape.brf.ExportToBRFFile;
import fr.curie.BiNoM.cytoscape.brf.ImportFromBRFFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CSMLImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerAssociateSource;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportToFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerVisualStyleDefinition;
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
import fr.curie.BiNoM.cytoscape.utils.NodesAndEdgesClipboard;
import fr.curie.BiNoM.cytoscape.utils.PasteNodesAndEdges;
import fr.curie.BiNoM.cytoscape.utils.SelectDownstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.SelectEdgesBetweenSelectedNodes;
import fr.curie.BiNoM.cytoscape.utils.SelectUpstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.ShowClipboardContents;

public class Launcher extends AbstractCySwingApp 
{
	private static CySwingAppAdapter adapter;
	public static String appName = "BiNoM";
	JMenuItem exportBioPAXMenuItem;
	JMenuItem exportCellDesignerMenuItem;
	JMenuItem exportSBMLMenuItem;
	JMenuItem saveBioPAXMenuItem;    
	JMenuItem syncBioPAXMenuItem;    
	JMenuItem associateBioPAXMenuItem;
	JMenuItem associateCellDesignerMenuItem;
	JMenuItem selectEdgesBetweendSelectedNodesMenuItem;
	JMenuItem showClipboardContentsMenuItem;
	JMenuItem setNodesAndEdgesMenuItem;
	JMenuItem addNodesAndEdgesMenuItem;
	JMenuItem pasteNodesAndEdgesMenuItem;
	JMenuItem toggleBioPAXNameMenuItem;
	JMenuItem listAllReactionsMenuItem;
	JMenuItem listAllNodesMenuItem;
	JMenuItem listAllEdgesMenuItem;
	JMenuItem colorCellDesignerProteinsMenuItem;
	JMenuItem pathwayStainingCellDesignerMenuItem;
	JMenuItem extractCellDesignerNotesMenuItem;
	JMenuItem convertReactionNetworkToEntityNetworkTask;
	JMenuItem modifyCellDesignerNotesMenuItem;	
	JMenuItem checkCellDesignerFileMenuItem;
	JMenuItem createNeighborhoodSetsMenuItem;
	JMenuItem mergeNetworksAndFilterMenuItem;
	JMenuItem produceNaviCellMenuItem;
	JMenuItem mergingMapsMenuItem;
	JMenuItem selectNodesByAttributeSubstringItem;
	JMenuItem menuItem;
	JMenuItem createCellDesignermenuItem;
	JMenuItem exportToBiNoMReactionFormatMenuItem;
	JMenuItem bioPax3ClassPropertyEditor;
	JMenuItem bioPax3ClassTree;
	JMenuItem createNeighborhoodSetsFileMenuItem;
	JMenuItem createSetIntersectionMenuItem;
	JMenuItem generateIndexMenuItem;
	JMenuItem about;
	
	JMenu structAnaMenu;
	JMenu semanticAnaMenu;

	JMenuItem [] analysisMenuItem;
	JMenuItem [] moduleManagerMenus;
	
	public Launcher(CySwingAppAdapter adapter)
	{
		super(adapter);
		this.setAdapter(adapter);
        // Add any customization here!
		analysisMenuItem = new JMenuItem[27];
		moduleManagerMenus = new JMenuItem[11];
		
		/*
		 * Input Output functions menu
		 * 
		 */
		
		JMenu binomMainMenu = getCySwingAppAdapter().getCySwingApplication().getJMenu("BiNoM");
				
		JMenu binomIOMenu = new JMenu("BiNoM I/O");
		binomMainMenu.add(binomIOMenu);
		
		JMenu bioPAXIOMenu = new JMenu("BioPAX");
		binomIOMenu.add(bioPAXIOMenu);
		
		JMenu cellDesignerIOMenu = new JMenu("CellDesigner/SBML");
		binomIOMenu.add(cellDesignerIOMenu);
		
		JMenu othersIOMenu = new JMenu("Other formats");
		binomIOMenu.add(othersIOMenu);

		menuItem = new JMenuItem
		("Import BioPAX 3 Document from file...");
		bioPAXIOMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXImportFromFile());

		menuItem = new JMenuItem
		("Import BioPAX 3 Document from URL...");
		bioPAXIOMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXImportFromURL());

		bioPAXIOMenu.addSeparator();  

		menuItem = new JMenuItem
		("Import CellDesigner Document from file...");
		cellDesignerIOMenu.add(menuItem);
		menuItem.addActionListener(new CellDesignerImportFromFile());

		cellDesignerIOMenu.addSeparator();  

		menuItem = new JMenuItem
		("Import CSML Document from file...");
		othersIOMenu.add(menuItem);
		menuItem.addActionListener(new CSMLImportFromFile());

		menuItem = new JMenuItem
		("Import influence network from AIN file...");
		othersIOMenu.add(menuItem);
		menuItem.addActionListener(new ImportFromSimpleTextInfluenceFile());
		
		menuItem = new JMenuItem
		("Import network from BiNoM reaction format file ...");
		othersIOMenu.add(menuItem);
		menuItem.addActionListener(new ImportFromBRFFile());
		

		othersIOMenu.addSeparator();

		exportBioPAXMenuItem = new JMenuItem("Export current network to BioPAX 3...");
		bioPAXIOMenu.add(exportBioPAXMenuItem);
		exportBioPAXMenuItem.addActionListener(new BioPAXExportToFile());
		
		bioPAXIOMenu.addSeparator();  

		exportCellDesignerMenuItem = new JMenuItem("Export current network to CellDesigner...");
		cellDesignerIOMenu.add(exportCellDesignerMenuItem);
		exportCellDesignerMenuItem.addActionListener(new CellDesignerExportToFile());
		
		exportSBMLMenuItem = new JMenuItem("Export current network to SBML...");
		cellDesignerIOMenu.add(exportSBMLMenuItem);
		exportSBMLMenuItem.addActionListener(new SBMLExportToFile());

		createCellDesignermenuItem = new JMenuItem("Create CellDesigner file from the current network...");
		cellDesignerIOMenu.add(createCellDesignermenuItem);
		createCellDesignermenuItem.addActionListener(new CreateCellDesignerFileThroughBRF());
		
		cellDesignerIOMenu.addSeparator();  
		exportToBiNoMReactionFormatMenuItem = new JMenuItem("Export current network to BiNoM reaction format...");
		othersIOMenu.add(exportToBiNoMReactionFormatMenuItem);
		exportToBiNoMReactionFormatMenuItem.addActionListener(new ExportToBRFFile());
      

		associateBioPAXMenuItem = new JMenuItem("Associate BioPAX 3 Source...");
		bioPAXIOMenu.add(associateBioPAXMenuItem);
		associateBioPAXMenuItem.addActionListener(BioPAXAssociateSource.getInstance());

		saveBioPAXMenuItem = new JMenuItem("Save whole associated BioPAX 3 as...");
		bioPAXIOMenu.add(saveBioPAXMenuItem);
		saveBioPAXMenuItem.addActionListener(new BioPAXSaveAssociated());
		
		bioPAXIOMenu.addSeparator(); 

		associateCellDesignerMenuItem = new JMenuItem("Associate CellDesigner Source...");
		cellDesignerIOMenu.add(associateCellDesignerMenuItem);
		associateCellDesignerMenuItem.addActionListener(CellDesignerAssociateSource.getInstance());

		cellDesignerIOMenu.addSeparator();

		colorCellDesignerProteinsMenuItem = new JMenuItem("Color CellDesigner proteins...");
		cellDesignerIOMenu.add(colorCellDesignerProteinsMenuItem);
		colorCellDesignerProteinsMenuItem.addActionListener(new ColorCellDesignerProteins());

		pathwayStainingCellDesignerMenuItem = new JMenuItem("Stain CellDesigner map...");
		cellDesignerIOMenu.add(pathwayStainingCellDesignerMenuItem);
		pathwayStainingCellDesignerMenuItem.addActionListener(new pathwayStainingCellDesigner());
		
		
		extractCellDesignerNotesMenuItem = new JMenuItem("Extract CellDesigner notes...");
		cellDesignerIOMenu.add(extractCellDesignerNotesMenuItem);
		extractCellDesignerNotesMenuItem.addActionListener(new extractCellDesignerNotes());

		modifyCellDesignerNotesMenuItem = new JMenuItem("Modify CellDesigner notes...");
		cellDesignerIOMenu.add(modifyCellDesignerNotesMenuItem);
		modifyCellDesignerNotesMenuItem.addActionListener(new modifyCellDesignerNotes());
		
		checkCellDesignerFileMenuItem = new JMenuItem("Check CellDesigner file...");
		cellDesignerIOMenu.add(checkCellDesignerFileMenuItem);
		checkCellDesignerFileMenuItem.addActionListener(new checkCellDesignerFile());
		
        othersIOMenu.addSeparator();
        
		listAllReactionsMenuItem = new JMenuItem("List all reactions...");
		othersIOMenu.add(listAllReactionsMenuItem);
		listAllReactionsMenuItem.addActionListener(new ListAllReactions());

		listAllNodesMenuItem = new JMenuItem("List all nodes...");
		othersIOMenu.add(listAllNodesMenuItem);
		listAllNodesMenuItem.addActionListener(new ListAllNodes());
		
		listAllEdgesMenuItem = new JMenuItem("List all edges as SIF...");
		othersIOMenu.add(listAllEdgesMenuItem);
		listAllEdgesMenuItem.addActionListener(new ListEdges());
		
        othersIOMenu.addSeparator();


        produceNaviCellMenuItem = new JMenuItem("Produce NaviCell map files...");
        cellDesignerIOMenu.add(produceNaviCellMenuItem);
        produceNaviCellMenuItem.addActionListener(new ProduceNaviCellMapFiles());	
		
        mergingMapsMenuItem = new JMenuItem("Merging CellDesigner map files...");
        cellDesignerIOMenu.add(mergingMapsMenuItem);
        mergingMapsMenuItem.addActionListener(new MergingMaps());	
        
        int i = 0;
        
        createNeighborhoodSetsFileMenuItem = new JMenuItem("Create neighborhood sets file...");
		othersIOMenu.add(createNeighborhoodSetsFileMenuItem);
		createNeighborhoodSetsFileMenuItem.addActionListener(new createNeighborhoodSets());
		
		createSetIntersectionMenuItem = new JMenuItem("Create set intersection graph in a folder...");
		createSetIntersectionMenuItem.addActionListener(new CreateSetIntersectionGraph());
		othersIOMenu.add(createSetIntersectionMenuItem);
		
		/*
		 * BioPAX 3 utils
		 */
		
		bioPax3ClassPropertyEditor = new JMenuItem
		("BioPAX 3 Property Editor...");
		bioPAXIOMenu.add(bioPax3ClassPropertyEditor);
		bioPax3ClassPropertyEditor.addActionListener(new BioPAXPropertyBrowser());

		bioPax3ClassTree = new JMenuItem
		("BioPAX 3 Class Tree...");
		bioPAXIOMenu.add(bioPax3ClassTree);
		bioPax3ClassTree.addActionListener(new BioPAXClassTree());

		toggleBioPAXNameMenuItem = new JMenuItem
		("Toggle BioPAX 3 Naming Service");
		bioPAXIOMenu.add(toggleBioPAXNameMenuItem);
		toggleBioPAXNameMenuItem.addActionListener(new BioPAXToggleNamingService());

		syncBioPAXMenuItem = new JMenuItem("Synchronize networks with BioPAX 3...");
		bioPAXIOMenu.add(syncBioPAXMenuItem);
		syncBioPAXMenuItem.addActionListener(new BioPAXSyncNetworks());
		
		bioPAXIOMenu.addSeparator();

		binomIOMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) { }
			public void menuDeselected(MenuEvent e) { }

			public void menuSelected(MenuEvent e) {
				CyNetwork net = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
				toggleBioPAXNameMenuItem.setText(BioPAXToggleNamingService.getNamingServiceMode() ? "Use Simplified URI Names..." : "Use Naming Service Names...");
				
				exportBioPAXMenuItem.setEnabled(net!=null);
				associateBioPAXMenuItem.setEnabled(net!=null);
				saveBioPAXMenuItem.setEnabled(net!=null);
				exportCellDesignerMenuItem.setEnabled(net!=null);
				exportSBMLMenuItem.setEnabled(net!=null);
				createCellDesignermenuItem.setEnabled(net!=null);
				associateCellDesignerMenuItem.setEnabled(net!=null);
				listAllReactionsMenuItem.setEnabled(net!=null);
				listAllNodesMenuItem.setEnabled(net!=null);
				listAllEdgesMenuItem.setEnabled(net!=null);
				exportToBiNoMReactionFormatMenuItem.setEnabled(net!=null);
				bioPax3ClassTree.setEnabled(net!=null);
				bioPax3ClassPropertyEditor.setEnabled(net!=null);
				toggleBioPAXNameMenuItem.setEnabled(net!=null);
				syncBioPAXMenuItem.setEnabled(net!=null);
				createNeighborhoodSetsFileMenuItem.setEnabled(net!=null);
				createSetIntersectionMenuItem.setEnabled(net!=null);
			}
		});
		
		
		
		/*
		 * Analysis functions menu
		 */

       
		structAnaMenu = new JMenu("Graph Algorithms");
		binomMainMenu.add(structAnaMenu);
		
		semanticAnaMenu = new JMenu("Graph Semantic Methods");
		binomMainMenu.add(semanticAnaMenu);

		analysisMenuItem[i] = new JMenuItem("Get Connected Components...");
		analysisMenuItem[i].addActionListener(new ConnectedComponents());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		analysisMenuItem[i] = new JMenuItem("Get Strongly Connected Components...");
		analysisMenuItem[i].addActionListener(new StronglyConnectedComponents());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Display SCC in Attribute...");
		analysisMenuItem[i].addActionListener(new SCCinAttribute());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Prune Graph...");
		analysisMenuItem[i].addActionListener(new PruneGraph());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		analysisMenuItem[i] = new JMenuItem("Get Life Cycle Components...");
		analysisMenuItem[i].addActionListener(new MaterialComponents());
		semanticAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Get Cycle Decomposition...");
		analysisMenuItem[i].addActionListener(new CycleDecomposition());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Path Analysis...");
		analysisMenuItem[i].addActionListener(new PathAnalysis());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Extract subnetwork...");
		analysisMenuItem[i].addActionListener(new ExtractSubnetwork());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		analysisMenuItem[i] = new JMenuItem("Extract induced graph...");
		analysisMenuItem[i].addActionListener(new InducedSubgraph());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		JMenu centralityMenu = new JMenu("Calc centrality...");
		structAnaMenu.add(centralityMenu);

		analysisMenuItem[i] = new JMenuItem("Inbetweenness undirected...");
		CalcCentralityUndirected ccu = new CalcCentralityUndirected();
		analysisMenuItem[i].addActionListener(ccu);
		centralityMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Inbetweenness directed...");
		CalcCentralityDirected ccd = new CalcCentralityDirected();
		analysisMenuItem[i].addActionListener(ccd);
		centralityMenu.add(analysisMenuItem[i]);
		i++;

		structAnaMenu.addSeparator();

		analysisMenuItem[i] = new JMenuItem("Cluster Networks...");
		analysisMenuItem[i].addActionListener(new ClusterNetworks());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		structAnaMenu.addSeparator();
		analysisMenuItem[i] = new JMenuItem("Mono-molecular react.to edges...");
		analysisMenuItem[i].addActionListener(new MonoMolecularReactionsAsEdges());
		semanticAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("\'Linearize\' network...");
		analysisMenuItem[i].addActionListener(new LinearizeNetwork());
		semanticAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Exclude intermediate nodes...");
		analysisMenuItem[i].addActionListener(new ExcludeIntermediateNodes());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Extract Reaction Network...");
		analysisMenuItem[i].addActionListener(new ExtractReactionNetwork());
		semanticAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Convert Reaction 2 Entity Network...");
		analysisMenuItem[i].addActionListener(new ConvertReactionNetworkToEntityNetwork());
		semanticAnaMenu.add(analysisMenuItem[i]);
		i++;

		structAnaMenu.addSeparator();
		
		analysisMenuItem[i] = new JMenuItem("Path Influence Quantification analysis...");
		analysisMenuItem[i].addActionListener(new PathConsistencyAnalyzer());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		structAnaMenu.addSeparator();
		
		analysisMenuItem[i] = new JMenuItem("OCSANA analysis...");
		analysisMenuItem[i].addActionListener(new OptimalCutSetAnalyzer());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		structAnaMenu.addSeparator();
		
		/*
		 * Utilities menu
		 */

		//JMenu binomUtilsMenu = new JMenu("BiNoM Utilities");

		//binomMainMenu.add(binomUtilsMenu);

		analysisMenuItem[i] = new JMenuItem("Select Edges between Selected Nodes...");
		structAnaMenu.add(analysisMenuItem[i]);
		analysisMenuItem[i].addActionListener(new SelectEdgesBetweenSelectedNodes());
		analysisMenuItem[i].setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
		i++;

		analysisMenuItem[i] = new JMenuItem("Select upstream neighbours...");
		analysisMenuItem[i].addActionListener(new SelectUpstreamNeighbours());
		structAnaMenu.add(analysisMenuItem[i]);
		analysisMenuItem[i].setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_8, java.awt.event.InputEvent.CTRL_MASK));
		i++;
		
		analysisMenuItem[i] = new JMenuItem("Select downstream neighbours...");
		analysisMenuItem[i].addActionListener(new SelectDownstreamNeighbours());
		structAnaMenu.add(analysisMenuItem[i]);
		analysisMenuItem[i].setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_9, java.awt.event.InputEvent.CTRL_MASK));		
		i++;

		analysisMenuItem[i] = new JMenuItem("Double Network Differences...");
		analysisMenuItem[i].addActionListener(new DoubleNetworkDifference());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Update Networks...");
		analysisMenuItem[i].addActionListener(new NetworksUpdate());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i] = new JMenuItem("Update connections from other network...");
		analysisMenuItem[i].addActionListener(new EdgesFromOtherNetwork());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		analysisMenuItem[i] = new JMenuItem("Remove multiple edges...");
		analysisMenuItem[i].addActionListener(new ConvertToUndirectedGraph());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;

		analysisMenuItem[i]  = new JMenuItem("Merge Networks and Filter by Frequency...");
		analysisMenuItem[i].addActionListener(new MergeNetworksAndFilter());
		structAnaMenu.add(analysisMenuItem[i]);
		i++;
		
		structAnaMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				CyNetwork net = getAdapter().getCyApplicationManager().getCurrentNetwork();
				for(int i=0;i<analysisMenuItem.length;i++)
					analysisMenuItem[i].setEnabled(net!=null);		
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub			
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub			
			}
		});
			
		/*
		 * BioPAX 3 query menu
		 */
		
		JMenu binomBioPAXQueryMenu = new JMenu("BioPAX 3 Indexing");

		bioPAXIOMenu.add(binomBioPAXQueryMenu);

		generateIndexMenuItem = new JMenuItem("Generate Index...");
		binomBioPAXQueryMenu.add(generateIndexMenuItem);
		generateIndexMenuItem.addActionListener(new BioPAXGenerateIndex());

		JMenuItem loadIndexMenuItem = new JMenuItem("Load Index...");
		binomBioPAXQueryMenu.add(loadIndexMenuItem);
		loadIndexMenuItem.addActionListener(new BioPAXLoadIndex());

		JMenuItem displayIndexInfoMenuItem = new JMenuItem("Display Index Info...");
		binomBioPAXQueryMenu.add(displayIndexInfoMenuItem);
		displayIndexInfoMenuItem.addActionListener(new BioPAXDisplayIndexInfo());

		binomBioPAXQueryMenu.addSeparator();

		JMenuItem selectEntitiesMenuItem = new JMenuItem("Select Entities...");
		binomBioPAXQueryMenu.add(selectEntitiesMenuItem);
		selectEntitiesMenuItem.addActionListener(new BioPAXSelectEntities());

		JMenuItem standardQueryMenuItem = new JMenuItem("Standard Query...");
		binomBioPAXQueryMenu.add(standardQueryMenuItem);
		standardQueryMenuItem.addActionListener(new BioPAXStandardQuery());

		JMenuItem pathIndexAnalysisItem = new JMenuItem("Index Path Analysis...");
		binomBioPAXQueryMenu.add(pathIndexAnalysisItem);
		pathIndexAnalysisItem.addActionListener(new BioPAXIndexPathAnalysis());

		JMenuItem viewQueryLogMenuItem = new JMenuItem("View Query Log...");
		binomBioPAXQueryMenu.add(viewQueryLogMenuItem);
		viewQueryLogMenuItem.addActionListener(new BioPAXViewQueryLog());
		
		binomBioPAXQueryMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) { }
			public void menuDeselected(MenuEvent e) { }

			public void menuSelected(MenuEvent e) {
				CyNetwork net = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
				
				generateIndexMenuItem.setEnabled(net!=null);

			}
		});

		

//		selectNodesByAttributeSubstringItem = new JMenuItem("Select nodes by substring in attribute");
//		selectNodesByAttributeSubstringItem.addActionListener(new SelectNodesByAttributeSubstring());
//		binomUtilsMenu.add(selectNodesByAttributeSubstringItem);
		
		JMenu edit = getCySwingAppAdapter().getCySwingApplication().getJMenu("Edit");
		
		JMenu clipboardMenu = new JMenu("BiNoM Network Clipboard");

		edit.add(clipboardMenu);

		setNodesAndEdgesMenuItem = new JMenuItem("Copy Selected Nodes and Edges to Clipboard...");
		clipboardMenu.add(setNodesAndEdgesMenuItem);
		setNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdgesTrue());
		setNodesAndEdgesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));


		addNodesAndEdgesMenuItem = new JMenuItem("Add Selected Nodes and Edges to Clipboard...");
		clipboardMenu.add(addNodesAndEdgesMenuItem);
		addNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdgesFalse());
		addNodesAndEdgesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));


		pasteNodesAndEdgesMenuItem = new JMenuItem("Paste Nodes and Edges from Clipboard...");
		clipboardMenu.add(pasteNodesAndEdgesMenuItem);
		pasteNodesAndEdgesMenuItem.addActionListener(new PasteNodesAndEdges());
		pasteNodesAndEdgesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));

		showClipboardContentsMenuItem = new JMenuItem("Show Clipboard Contents...");
		clipboardMenu.add(showClipboardContentsMenuItem);
		showClipboardContentsMenuItem.addActionListener(new ShowClipboardContents());
		showClipboardContentsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));


		clipboardMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) { }
			public void menuDeselected(MenuEvent e) { }

			public void menuSelected(MenuEvent e) {
				CyNetworkView view= Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();

//				selectEdgesBetweendSelectedNodesMenuItem.setEnabled
//				(view != null && CyTableUtil.getNodesInState(getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() != 0);

				setNodesAndEdgesMenuItem.setEnabled
				(view != null &&
						(CyTableUtil.getNodesInState(getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() != 0 ||
								(CyTableUtil.getEdgesInState(getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() != 0)));

				addNodesAndEdgesMenuItem.setEnabled
				(view != null &&
						(CyTableUtil.getNodesInState(getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() != 0 ||
								(CyTableUtil.getEdgesInState(getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() != 0)));

				showClipboardContentsMenuItem.setEnabled(!NodesAndEdgesClipboard.getInstance().isEmpty());
				pasteNodesAndEdgesMenuItem.setEnabled(!NodesAndEdgesClipboard.getInstance().isEmpty());
			}
		});
		
		
		
		/*
		 * Module manager menu functions
		 */
		
		structAnaMenu.addSeparator();
		
			
		JMenu binomNestManagerMenu = new JMenu("Module Manager");
		structAnaMenu.add(binomNestManagerMenu);

		i = 0;
		moduleManagerMenus[i]=new JMenuItem(CreateModuleNetwork.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new CreateModuleNetwork());
		i++;
		
		moduleManagerMenus[i]=new JMenuItem(CreateEdgesBetweenModulesAllFromRef.title+">"+CreateEdgesBetweenModulesAllFromRef.subTitle);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new CreateEdgesBetweenModulesAllFromRef());
		i++;
		
		moduleManagerMenus[i]=new JMenuItem(CreateEdgesBetweenModulesDistinctEdges.title+">"+CreateEdgesBetweenModulesDistinctEdges.subTitle);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new CreateEdgesBetweenModulesDistinctEdges());
		i++;

		moduleManagerMenus[i]=new JMenuItem(PackInModules.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new PackInModules());
		i++;

//		menuItem=new JMenuItem(ClusterByShortPath.title);
//		binomNestManagerMenu.add(menuItem);
//		menuItem.addActionListener(new ClusterByShortPath());

		binomNestManagerMenu.addSeparator();		

		moduleManagerMenus[i]=new JMenuItem(ListNodes.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new ListNodes());
		i++;
		
		moduleManagerMenus[i]=new JMenuItem(ListEdgesLinkingModules.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new  ListEdgesLinkingModules());
		i++;

		moduleManagerMenus[i]=new JMenuItem(FindCommonNodes.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new FindCommonNodes());
		i++;

		moduleManagerMenus[i]=new JMenuItem(ModuleInNodeAttribute.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new ModuleInNodeAttribute());
		i++;

		moduleManagerMenus[i]=new JMenuItem(ListComponents.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new ListComponents());
		i++;

		binomNestManagerMenu.addSeparator();		

		moduleManagerMenus[i]=new JMenuItem(MergeSelectedModules.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new MergeSelectedModules());	
		i++;

		moduleManagerMenus[i]=new JMenuItem(InterOfSelectedModules.title);
		binomNestManagerMenu.add(moduleManagerMenus[i]);
		moduleManagerMenus[i].addActionListener(new InterOfSelectedModules());      
		
		
		
		binomNestManagerMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				CyNetwork net = getAdapter().getCyApplicationManager().getCurrentNetwork();
				for(int i=0;i<moduleManagerMenus.length;i++)
					moduleManagerMenus[i].setEnabled(net!=null);		
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub			
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub			
			}
		});
			


        
		about = new JMenuItem("About BiNoM...");
		about.addActionListener(new AboutBox());
		binomMainMenu.add(about);
        
        
     //  ########################################################

//		// input output
//		adapter.getCySwingApplication().addAction(new BioPAXImportFromFile());
//		adapter.getCySwingApplication().addAction(new BioPAXImportFromURL());
//		adapter.getCySwingApplication().addAction(new CellDesignerImportFromFile());
//		adapter.getCySwingApplication().addAction(new CSMLImportFromFile());
//		adapter.getCySwingApplication().addAction(new ImportFromSimpleTextInfluenceFile());
//		adapter.getCySwingApplication().addAction(new ImportFromBRFFile());
//		
//		adapter.getCySwingApplication().addAction(new BioPAXExportToFile());
//		adapter.getCySwingApplication().addAction(new CellDesignerExportToFile());
//		adapter.getCySwingApplication().addAction(new CreateCellDesignerFileThroughBRF());
//		
//		adapter.getCySwingApplication().addAction(new SBMLExportToFile());
//		adapter.getCySwingApplication().addAction(new ExportToBRFFile());
//		
//		
//		adapter.getCySwingApplication().addAction(BioPAXAssociateSource.getInstance());
//		adapter.getCySwingApplication().addAction(new BioPAXSaveAssociated());
//		adapter.getCySwingApplication().addAction(CellDesignerAssociateSource.getInstance());
//		adapter.getCySwingApplication().addAction(new ListAllReactions());
//		adapter.getCySwingApplication().addAction(new ListAllNodes());
//		adapter.getCySwingApplication().addAction(new ListEdges());
//
//		
//		adapter.getCySwingApplication().addAction(new ColorCellDesignerProteins());
//		adapter.getCySwingApplication().addAction(new pathwayStainingCellDesigner());
//		adapter.getCySwingApplication().addAction(new extractCellDesignerNotes());
//		adapter.getCySwingApplication().addAction(new modifyCellDesignerNotes());
//		adapter.getCySwingApplication().addAction(new checkCellDesignerFile());
//		
//		adapter.getCySwingApplication().addAction(new ProduceNaviCellMapFiles());
//		adapter.getCySwingApplication().addAction(new MergingMaps());
//		
//		
//		//analysis
//		adapter.getCySwingApplication().addAction(new ConnectedComponents());
//		adapter.getCySwingApplication().addAction(new StronglyConnectedComponents());
//		
//		adapter.getCySwingApplication().addAction(new SCCinAttribute());
//		
//		adapter.getCySwingApplication().addAction(new PruneGraph());
//		adapter.getCySwingApplication().addAction(new MaterialComponents());
//		adapter.getCySwingApplication().addAction(new CycleDecomposition());
//		adapter.getCySwingApplication().addAction(new PathAnalysis());
//		adapter.getCySwingApplication().addAction(new ExtractSubnetwork());
//		adapter.getCySwingApplication().addAction(new InducedSubgraph());
//		adapter.getCySwingApplication().addAction(new CalcCentralityUndirected());
//		adapter.getCySwingApplication().addAction(new CalcCentralityDirected());	
//		adapter.getCySwingApplication().addAction(new ClusterNetworks());	
//		adapter.getCySwingApplication().addAction(new MonoMolecularReactionsAsEdges());
//		adapter.getCySwingApplication().addAction(new LinearizeNetwork());
//		adapter.getCySwingApplication().addAction(new ExcludeIntermediateNodes());
//		adapter.getCySwingApplication().addAction(new ExtractReactionNetwork());
//		adapter.getCySwingApplication().addAction(new ConvertReactionNetworkToEntityNetwork());
//		adapter.getCySwingApplication().addAction(new PathConsistencyAnalyzer());
//		adapter.getCySwingApplication().addAction(new OptimalCutSetAnalyzer());
//		adapter.getCySwingApplication().addAction(new createNeighborhoodSets());	
//		
//		
//		//BioPAX 3 utils menu
//		adapter.getCySwingApplication().addAction(new BioPAXPropertyBrowser());
//		adapter.getCySwingApplication().addAction(new BioPAXClassTree());	
//		adapter.getCySwingApplication().addAction(new BioPAXToggleNamingService());
////		adapter.getCySwingApplication().addAction(new BioPAXSyncNetworks());
//		
//		
//		// BioPAX 3 query menu
//		adapter.getCySwingApplication().addAction(new BioPAXGenerateIndex());
//		adapter.getCySwingApplication().addAction(new BioPAXLoadIndex());
//		adapter.getCySwingApplication().addAction(new BioPAXDisplayIndexInfo());
//		adapter.getCySwingApplication().addAction(new BioPAXSelectEntities());
//		adapter.getCySwingApplication().addAction(new BioPAXStandardQuery());
//		adapter.getCySwingApplication().addAction(new BioPAXIndexPathAnalysis());
//		adapter.getCySwingApplication().addAction(new BioPAXViewQueryLog());
//		
//		
//		// Utilities menu
//		adapter.getCySwingApplication().addAction(new SelectEdgesBetweenSelectedNodes());
//		adapter.getCySwingApplication().addAction(new SelectUpstreamNeighbours());
//		adapter.getCySwingApplication().addAction(new SelectDownstreamNeighbours());
//		adapter.getCySwingApplication().addAction(new DoubleNetworkDifference());
//		adapter.getCySwingApplication().addAction(new NetworksUpdate());
//		adapter.getCySwingApplication().addAction(new EdgesFromOtherNetwork());
//		adapter.getCySwingApplication().addAction(new ConvertToUndirectedGraph());
//		adapter.getCySwingApplication().addAction(new MergeNetworksAndFilter());
//		
//		adapter.getCySwingApplication().addAction(new CopySelectedNodesAndEdgesTrue());
//		adapter.getCySwingApplication().addAction(new CopySelectedNodesAndEdgesFalse());
//		
//		adapter.getCySwingApplication().addAction(new PasteNodesAndEdges());
//		adapter.getCySwingApplication().addAction(new ShowClipboardContents());
//		
//		adapter.getCySwingApplication().addAction(new CreateSetIntersectionGraph());
		
		
//		//about BiNoM
//		adapter.getCySwingApplication().addAction(new AboutBox());
		
		fr.curie.BiNoM.cytoscape.lib.VisualStyleFactory f = new fr.curie.BiNoM.cytoscape.lib.VisualStyleFactory();
		f.create(BioPAXVisualStyleDefinition.getInstance());
		f.create(CellDesignerVisualStyleDefinition.getInstance());
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
	
	public static CyNetwork findNetworkofEdge(long id){
		Iterator i = Launcher.getAdapter().getCyNetworkManager().getNetworkSet().iterator();
		while(i.hasNext()){
			CyNetwork net = (CyNetwork) i.next();
			Iterator edgeIter = net.getEdgeList().iterator();
			while(edgeIter.hasNext()){
				if(((CyEdge)edgeIter.next()).getSUID()==id)
					return net;		
			}		
		}
		return null;
	}
	
	public static CyNode findNodeWithName(CyNetwork net, String name){
		Iterator i = net.getNodeList().iterator();
		while(i.hasNext()){
			CyNode node = (CyNode)i.next();
			if(net.getRow(node).get(CyNetwork.NAME, String.class).compareTo(name) == 0)
				return node;
		}
		
		return null;
	}
	
	
	
	public static CyEdge findEdgeWithName(CyNetwork net, String name){
		Iterator i = net.getEdgeList().iterator();
		while(i.hasNext()){
			CyEdge edge = (CyEdge)i.next();
			if(net.getRow(edge).get(CyNetwork.NAME, String.class).compareTo(name) == 0)
				return edge;
		}
		
		return null;
	}
	
//	 public static CyAppAdapter getAdapter(){
//	    	return adapter;
//	    }
//	 
//	 
//	 public static CySwingAppAdapter getCySwingAppAdapter(){
//	    	return adapter;
//	    }
//
//	private static void setAdapter(CySwingAppAdapter adapter) {
//		Launcher.adapter = adapter;
//	}
	
	
	public static CyAppAdapter getAdapterr(){
    	return adapter;
    }
 
 public static CySwingAppAdapter getCySwingAppAdapterr(){
    	return adapter;
    }
 
 public static adapterr getAdapter(){
    	return new adapterr();
    }
 
 public static adapterr getCySwingAppAdapter(){
    	return new adapterr();
    }

public static void setAdapter(CySwingAppAdapter adapter) {
	Launcher.adapter = adapter;
}

public static class adapterr{
	
	public CyNetworkManager getCyNetworkManager(){
		return getAdapterr().getCyNetworkManager();
	}
	public CyNetworkFactory getCyNetworkFactory(){
		return getAdapterr().getCyNetworkFactory();
	}
	public CyApplicationManager getCyApplicationManager(){
		return getAdapterr().getCyApplicationManager();
	}
	public CyNetworkViewFactory getCyNetworkViewFactory(){
		return getAdapterr().getCyNetworkViewFactory();
	}
	public CyNetworkViewManager getCyNetworkViewManager(){
		return getAdapterr().getCyNetworkViewManager();
	}
	public VisualMappingManager getVisualMappingManager(){
		return getAdapterr().getVisualMappingManager();
	}
	public TaskManager getTaskManager(){
		return getAdapterr().getTaskManager();
	}
	
	public CyEventHelper getCyEventHelper(){
		return getAdapterr().getCyEventHelper();
	}
	
	public VisualStyleFactory getVisualStyleFactory(){
		return getAdapterr().getVisualStyleFactory();
	}
	
	public VisualMappingFunctionFactory getVisualMappingFunctionPassthroughFactory(){
		return getAdapterr().getVisualMappingFunctionPassthroughFactory();
	}
	
	public VisualMappingFunctionFactory getVisualMappingFunctionDiscreteFactory(){
		return getAdapterr().getVisualMappingFunctionDiscreteFactory();
	}
	
	public CySwingApplication getCySwingApplication(){
		return getCySwingAppAdapterr().getCySwingApplication();
	}
	
	public CyRootNetworkManager getCyRootNetworkManager(){
		return getCySwingAppAdapterr().getCyRootNetworkManager();
	}
}
	
	public static HashMap getNodeMap(CyNetwork net){
		HashMap m =new HashMap<String, CyNode>();
		Iterator i = net.getNodeList().iterator();
		while(i.hasNext()){
			CyNode node = (CyNode)i.next();
			m.put(net.getRow(node).get(CyNetwork.NAME, String.class), node);
		}
		
		return m;
	}
	
	public static HashMap getEdgeMap(CyNetwork net){
		HashMap m =new HashMap<String, CyEdge>();
		Iterator i = net.getEdgeList().iterator();
		while(i.hasNext()){
			CyEdge edge = (CyEdge)i.next();
			m.put(net.getRow(edge).get(CyNetwork.NAME, String.class), edge);
		}
		
		return m;
	}
	
	public static boolean areInSameNetworkColelction(CyNetwork[] networks){
		CyRootNetwork networkCollection = null;
		
		for(int i = 0; i < networks.length; i++){
			CyNetwork netw = networks[i];
			if(networkCollection == null)
				networkCollection = Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(netw);
			else{
				if(Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(netw) != networkCollection)
					return false;
			}
		}	
		return true;
	}
	
	public static String noSynonymNetwork(String name){
		Set<CyNetwork> set = getAdapter().getCyNetworkManager().getNetworkSet();
		String last = "";
		HashSet<String> netNames=new HashSet<String>();
		for(CyNetwork net:set) 
			netNames.add(net.getRow(net).get(CyNetwork.NAME,String.class));
		if(netNames.contains(name)){
					
			if(name.contains(".")){
				int pos = name.lastIndexOf(".");
				last = name.substring(pos, name.length());
				name = name.substring(0, pos);
			}
			
			String ni = "_1";
					
			do {
				name +=  ni;
			}while(netNames.contains(name+ last));
		}
		return name + last;
	}
}