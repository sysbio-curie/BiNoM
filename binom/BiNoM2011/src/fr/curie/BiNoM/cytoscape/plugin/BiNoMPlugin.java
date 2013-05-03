/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package fr.curie.BiNoM.cytoscape.plugin;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.plugin.CytoscapePlugin;
import cytoscape.view.CyMenus;
import cytoscape.view.CyNetworkView;
import cytoscape.view.CytoscapeDesktop;
import cytoscape.visual.VisualStyle;
import fr.curie.BiNoM.biopax.propedit.BioPAXToggleNamingService;
import fr.curie.BiNoM.cytoscape.ain.ImportFromSimpleTextInfluenceFile;
import fr.curie.BiNoM.cytoscape.analysis.CalcCentrality;
import fr.curie.BiNoM.cytoscape.analysis.ClusterNetworks;
import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponents;
import fr.curie.BiNoM.cytoscape.analysis.ConvertToUndirectedGraph;
import fr.curie.BiNoM.cytoscape.analysis.ConvertReactionNetworkToEntityNetwork;
import fr.curie.BiNoM.cytoscape.analysis.CycleDecomposition;
import fr.curie.BiNoM.cytoscape.analysis.EdgesFromOtherNetwork;
import fr.curie.BiNoM.cytoscape.analysis.ExcludeIntermediateNodes;
import fr.curie.BiNoM.cytoscape.analysis.ExtractReactionNetwork;
import fr.curie.BiNoM.cytoscape.analysis.ConvertReactionNetworkToEntityNetworkTask;
import fr.curie.BiNoM.cytoscape.analysis.ExtractSubnetwork;
import fr.curie.BiNoM.cytoscape.analysis.LinearizeNetwork;
import fr.curie.BiNoM.cytoscape.analysis.MaterialComponents;
import fr.curie.BiNoM.cytoscape.analysis.MonoMolecularReactionsAsEdges;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.PathAnalysis;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.PruneGraph;
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
import fr.curie.BiNoM.cytoscape.celldesigner.CSMLImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerAssociateSource;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportToFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerImportFromFile;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.celldesigner.MergingMaps;
import fr.curie.BiNoM.cytoscape.celldesigner.ProduceNaviCellMapFiles;
import fr.curie.BiNoM.cytoscape.celldesigner.checkCellDesignerFile;
import fr.curie.BiNoM.cytoscape.celldesigner.colorCellDesignerProteins;
import fr.curie.BiNoM.cytoscape.celldesigner.extractCellDesignerNotes;
import fr.curie.BiNoM.cytoscape.celldesigner.modifyCellDesignerNotes;
import fr.curie.BiNoM.cytoscape.celldesigner.pathwayStainingCellDesigner;
import fr.curie.BiNoM.cytoscape.lib.VisualStyleFactory;
import fr.curie.BiNoM.cytoscape.nestmanager.ClusterByShortPath;
import fr.curie.BiNoM.cytoscape.nestmanager.CreateConnectionsBetweenNests;
import fr.curie.BiNoM.cytoscape.nestmanager.CreateNestNetwork;
import fr.curie.BiNoM.cytoscape.nestmanager.DestroyUnusedNetworksAsNest;
import fr.curie.BiNoM.cytoscape.nestmanager.FindCommonNodes;
import fr.curie.BiNoM.cytoscape.nestmanager.InterOf2SelectedNests;
import fr.curie.BiNoM.cytoscape.nestmanager.ListComponents;
import fr.curie.BiNoM.cytoscape.nestmanager.ListNodesByNest;
import fr.curie.BiNoM.cytoscape.nestmanager.MergeSelectedNests;
import fr.curie.BiNoM.cytoscape.nestmanager.ModuleVisualStyle;
import fr.curie.BiNoM.cytoscape.nestmanager.NestInNodeAttribute;
import fr.curie.BiNoM.cytoscape.nestmanager.PackInNestNode;
import fr.curie.BiNoM.cytoscape.nestmanager.RecreateLostConnectionsInsideNests;
import fr.curie.BiNoM.cytoscape.netwop.DoubleNetworkDifference;
import fr.curie.BiNoM.cytoscape.netwop.NetworksUpdate;
import fr.curie.BiNoM.cytoscape.sbml.SBMLExportToFile;
import fr.curie.BiNoM.cytoscape.utils.AboutBox;
import fr.curie.BiNoM.cytoscape.utils.CopySelectedNodesAndEdges;
import fr.curie.BiNoM.cytoscape.utils.CreateSetIntersectionGraph;
import fr.curie.BiNoM.cytoscape.utils.ListAllNodes;
import fr.curie.BiNoM.cytoscape.utils.ListAllReactions;
import fr.curie.BiNoM.cytoscape.utils.MergeNetworksAndFilter;
import fr.curie.BiNoM.cytoscape.utils.NodesAndEdgesClipboard;
import fr.curie.BiNoM.cytoscape.utils.PasteNodesAndEdges;
import fr.curie.BiNoM.cytoscape.utils.SelectDownstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.SelectEdgesBetweenSelectedNodes;
import fr.curie.BiNoM.cytoscape.utils.SelectUpstreamNeighbours;
import fr.curie.BiNoM.cytoscape.utils.ShowClipboardContents;


public class BiNoMPlugin extends CytoscapePlugin {

	static int time_count = 1;
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

	private void initVisualStyles() {
		CyNetworkView networkView = Cytoscape.getCurrentNetworkView();
		VisualStyle vizsty;

		vizsty = VisualStyleFactory.getInstance().create(BioPAXVisualStyleDefinition.getInstance());
		networkView.applyVizmapper(vizsty);

		vizsty = VisualStyleFactory.getInstance().create(CellDesignerVisualStyleDefinition.getInstance());
		networkView.applyVizmapper(vizsty);

		ModuleVisualStyle.create();
	}
	
	public BiNoMPlugin() {

		CytoscapeDesktop desktop = Cytoscape.getDesktop();
		CyMenus cyMenus = desktop.getCyMenus();

		initVisualStyles();

		JMenu plugInMenu = cyMenus.getOperationsMenu();
		JMenu binomMainMenu = new JMenu("BiNoM 2.3");
		plugInMenu.add(binomMainMenu);
		
		/*
		 * Input Output functions menu
		 */
		JMenu binomIOMenu = new JMenu("BiNoM I/O");
		binomMainMenu.add(binomIOMenu);

		JMenuItem menuItem = new JMenuItem
		("Import BioPAX 3 Document from file...");
		binomIOMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXImportFromFile());

		menuItem = new JMenuItem
		("Import BioPAX 3 Document from URL...");
		binomIOMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXImportFromURL());

		binomIOMenu.addSeparator();

		menuItem = new JMenuItem
		("Import CellDesigner Document from file...");
		binomIOMenu.add(menuItem);
		menuItem.addActionListener(new CellDesignerImportFromFile());

		binomIOMenu.addSeparator();

		menuItem = new JMenuItem
		("Import CSML Document from file...");
		binomIOMenu.add(menuItem);
		menuItem.addActionListener(new CSMLImportFromFile());

		binomIOMenu.addSeparator();

		menuItem = new JMenuItem
		("Import influence network from AIN file...");
		binomIOMenu.add(menuItem);
		menuItem.addActionListener(new ImportFromSimpleTextInfluenceFile());

		binomIOMenu.addSeparator();

		exportBioPAXMenuItem = new JMenuItem("Export current network to BioPAX 3...");
		binomIOMenu.add(exportBioPAXMenuItem);
		exportBioPAXMenuItem.addActionListener(new BioPAXExportToFile());

		exportCellDesignerMenuItem = new JMenuItem("Export current network to CellDesigner...");
		binomIOMenu.add(exportCellDesignerMenuItem);
		exportCellDesignerMenuItem.addActionListener(new CellDesignerExportToFile());

		exportSBMLMenuItem = new JMenuItem("Export current network to SBML...");
		binomIOMenu.add(exportSBMLMenuItem);
		exportSBMLMenuItem.addActionListener(new SBMLExportToFile());


		binomIOMenu.addSeparator();        

		associateBioPAXMenuItem = new JMenuItem("Associate BioPAX 3 Source...");
		binomIOMenu.add(associateBioPAXMenuItem);
		associateBioPAXMenuItem.addActionListener(BioPAXAssociateSource.getInstance());

		saveBioPAXMenuItem = new JMenuItem("Save whole associated BioPAX 3 as...");
		binomIOMenu.add(saveBioPAXMenuItem);
		saveBioPAXMenuItem.addActionListener(new BioPAXSaveAssociated());

		associateCellDesignerMenuItem = new JMenuItem("Associate CellDesigner Source...");
		binomIOMenu.add(associateCellDesignerMenuItem);
		associateCellDesignerMenuItem.addActionListener(CellDesignerAssociateSource.getInstance());

		binomIOMenu.addSeparator();        

		listAllReactionsMenuItem = new JMenuItem("List all reactions...");
		binomIOMenu.add(listAllReactionsMenuItem);
		listAllReactionsMenuItem.addActionListener(new ListAllReactions());

		listAllNodesMenuItem = new JMenuItem("List all nodes...");
		binomIOMenu.add(listAllNodesMenuItem);
		listAllNodesMenuItem.addActionListener(new ListAllNodes());

		binomIOMenu.addSeparator();

		colorCellDesignerProteinsMenuItem = new JMenuItem("Color CellDesigner proteins...");
		binomIOMenu.add(colorCellDesignerProteinsMenuItem);
		colorCellDesignerProteinsMenuItem.addActionListener(new colorCellDesignerProteins());

		pathwayStainingCellDesignerMenuItem = new JMenuItem("Stain CellDesigner map...");
		binomIOMenu.add(pathwayStainingCellDesignerMenuItem);
		pathwayStainingCellDesignerMenuItem.addActionListener(new pathwayStainingCellDesigner());
		
		
		extractCellDesignerNotesMenuItem = new JMenuItem("Extract CellDesigner notes...");
		binomIOMenu.add(extractCellDesignerNotesMenuItem);
		extractCellDesignerNotesMenuItem.addActionListener(new extractCellDesignerNotes());

		modifyCellDesignerNotesMenuItem = new JMenuItem("Modify CellDesigner notes...");
		binomIOMenu.add(modifyCellDesignerNotesMenuItem);
		modifyCellDesignerNotesMenuItem.addActionListener(new modifyCellDesignerNotes());
		
		checkCellDesignerFileMenuItem = new JMenuItem("Check CellDesigner file...");
		binomIOMenu.add(checkCellDesignerFileMenuItem);
		checkCellDesignerFileMenuItem.addActionListener(new checkCellDesignerFile());
		
        binomIOMenu.addSeparator();

        produceNaviCellMenuItem = new JMenuItem("Produce NaviCell map files...");
        binomIOMenu.add(produceNaviCellMenuItem);
        produceNaviCellMenuItem.addActionListener(new ProduceNaviCellMapFiles());	
		
        mergingMapsMenuItem = new JMenuItem("Merging CellDesigner map files...");
        binomIOMenu.add(mergingMapsMenuItem);
        mergingMapsMenuItem.addActionListener(new MergingMaps());	
		
		/*
		 * Analysis functions menu
		 */

		JMenu structAnaMenu = new JMenu("BiNoM Analysis");
		binomMainMenu.add(structAnaMenu);

		menuItem = new JMenuItem("Get Connected Components...");
		menuItem.addActionListener(new ConnectedComponents());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Get Strongly Connected Components...");
		menuItem.addActionListener(new StronglyConnectedComponents());
		structAnaMenu.add(menuItem);


		menuItem = new JMenuItem("Prune Graph...");
		menuItem.addActionListener(new PruneGraph());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Get Material Components...");
		menuItem.addActionListener(new MaterialComponents());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Get Cycle Decomposition...");
		menuItem.addActionListener(new CycleDecomposition());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Path Analysis...");
		menuItem.addActionListener(new PathAnalysis());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Extract subnetwork...");
		menuItem.addActionListener(new ExtractSubnetwork());
		structAnaMenu.add(menuItem);

		JMenu centralityMenu = new JMenu("Calc centrality...");
		structAnaMenu.add(centralityMenu);

		menuItem = new JMenuItem("Inbetweenness undirected");
		CalcCentrality ccu = new CalcCentrality();
		ccu.directed = false;
		menuItem.addActionListener(ccu);
		centralityMenu.add(menuItem);

		menuItem = new JMenuItem("Inbetweenness directed");
		CalcCentrality ccd = new CalcCentrality();
		ccd.directed = true;
		menuItem.addActionListener(ccd);
		centralityMenu.add(menuItem);

		structAnaMenu.addSeparator();

		menuItem = new JMenuItem("Cluster Networks...");
		menuItem.addActionListener(new ClusterNetworks());
		structAnaMenu.add(menuItem);

		structAnaMenu.addSeparator();
		menuItem = new JMenuItem("Mono-molecular react.to edges...");
		menuItem.addActionListener(new MonoMolecularReactionsAsEdges());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("\'Linearize\' network...");
		menuItem.addActionListener(new LinearizeNetwork());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Exclude intermediate nodes...");
		menuItem.addActionListener(new ExcludeIntermediateNodes());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Extract Reaction Network...");
		menuItem.addActionListener(new ExtractReactionNetwork());
		structAnaMenu.add(menuItem);

		menuItem = new JMenuItem("Convert Reaction 2 Entity Network...");
		menuItem.addActionListener(new ConvertReactionNetworkToEntityNetwork());
		structAnaMenu.add(menuItem);

		structAnaMenu.addSeparator();
		menuItem = new JMenuItem("Path Influence Quantification analysis...");
		menuItem.addActionListener(new PathConsistencyAnalyzer());
		structAnaMenu.add(menuItem);

		structAnaMenu.addSeparator();
		menuItem = new JMenuItem("OCSANA analysis...");
		menuItem.addActionListener(new OptimalCutSetAnalyzer());
		structAnaMenu.add(menuItem);

		structAnaMenu.addSeparator();

		createNeighborhoodSetsMenuItem = new JMenuItem("Create neighborhood sets file...");
		structAnaMenu.add(createNeighborhoodSetsMenuItem);
		createNeighborhoodSetsMenuItem.addActionListener(new createNeighborhoodSets());

		/*
		 * Module manager menu functions
		 */
		
		JMenu binomNestManagerMenu = new JMenu("BiNoM Module Manager");
		binomMainMenu.add(binomNestManagerMenu);

		menuItem=new JMenuItem(CreateNestNetwork.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new CreateNestNetwork());

		menuItem=new JMenuItem(CreateConnectionsBetweenNests.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new CreateConnectionsBetweenNests());

		menuItem=new JMenuItem(PackInNestNode.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new PackInNestNode());

		menuItem=new JMenuItem(ClusterByShortPath.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new ClusterByShortPath());

		binomNestManagerMenu.addSeparator();		

		menuItem=new JMenuItem(ListNodesByNest.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new ListNodesByNest());

		menuItem=new JMenuItem(FindCommonNodes.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new FindCommonNodes());

		menuItem=new JMenuItem(NestInNodeAttribute.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new NestInNodeAttribute());

		menuItem=new JMenuItem(ListComponents.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new ListComponents());

		binomNestManagerMenu.addSeparator();		

		menuItem=new JMenuItem(MergeSelectedNests.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new MergeSelectedNests());	

		menuItem=new JMenuItem(InterOf2SelectedNests.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new InterOf2SelectedNests());

		menuItem=new JMenuItem(RecreateLostConnectionsInsideNests.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new RecreateLostConnectionsInsideNests());

		menuItem=new JMenuItem(DestroyUnusedNetworksAsNest.title);
		binomNestManagerMenu.add(menuItem);
		menuItem.addActionListener(new DestroyUnusedNetworksAsNest());

		/*
		 * BioPAX 3 utils menu
		 */
		
		JMenu binomBioPAXUtilsMenu = new JMenu("BiNoM BioPAX 3 Utils");
		binomMainMenu.add(binomBioPAXUtilsMenu);

		menuItem = new JMenuItem
		("BioPAX 3 Property Editor...");
		binomBioPAXUtilsMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXPropertyBrowser());

		menuItem = new JMenuItem
		("BioPAX 3 Class Tree...");
		binomBioPAXUtilsMenu.add(menuItem);
		menuItem.addActionListener(new BioPAXClassTree());

		toggleBioPAXNameMenuItem = new JMenuItem
		("Toggle BioPAX 3 Naming Service");
		binomBioPAXUtilsMenu.add(toggleBioPAXNameMenuItem);
		toggleBioPAXNameMenuItem.addActionListener(new BioPAXToggleNamingService());

		syncBioPAXMenuItem = new JMenuItem("Synchronize networks with BioPAX 3...");
		binomBioPAXUtilsMenu.add(syncBioPAXMenuItem);
		syncBioPAXMenuItem.addActionListener(new BioPAXSyncNetworks());

		binomIOMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) { }
			public void menuDeselected(MenuEvent e) { }

			public void menuSelected(MenuEvent e) {
				CyNetwork cyNetwork = Cytoscape.getCurrentNetwork();
				toggleBioPAXNameMenuItem.setText(BioPAXToggleNamingService.getNamingServiceMode() ? "Use Simplified URI Names" : "Use Naming Service Names");
			}
		});

		
		/*
		 * BioPAX 3 query menu
		 */
		
		JMenu binomBioPAXQueryMenu = new JMenu("BiNoM BioPAX 3 Query ");

		binomMainMenu.add(binomBioPAXQueryMenu);

		JMenuItem generateIndexMenuItem = new JMenuItem("Generate Index");
		binomBioPAXQueryMenu.add(generateIndexMenuItem);
		generateIndexMenuItem.addActionListener(new BioPAXGenerateIndex());

		JMenuItem loadIndexMenuItem = new JMenuItem("Load Index");
		binomBioPAXQueryMenu.add(loadIndexMenuItem);
		loadIndexMenuItem.addActionListener(new BioPAXLoadIndex());

		JMenuItem displayIndexInfoMenuItem = new JMenuItem("Display Index Info");
		binomBioPAXQueryMenu.add(displayIndexInfoMenuItem);
		displayIndexInfoMenuItem.addActionListener(new BioPAXDisplayIndexInfo());

		binomBioPAXQueryMenu.addSeparator();

		JMenuItem selectEntitiesMenuItem = new JMenuItem("Select Entities");
		binomBioPAXQueryMenu.add(selectEntitiesMenuItem);
		selectEntitiesMenuItem.addActionListener(new BioPAXSelectEntities());

		JMenuItem standardQueryMenuItem = new JMenuItem("Standard Query");
		binomBioPAXQueryMenu.add(standardQueryMenuItem);
		standardQueryMenuItem.addActionListener(new BioPAXStandardQuery());

		JMenuItem pathIndexAnalysisItem = new JMenuItem("Index Path Analysis");
		binomBioPAXQueryMenu.add(pathIndexAnalysisItem);
		pathIndexAnalysisItem.addActionListener(new BioPAXIndexPathAnalysis());

		JMenuItem viewQueryLogMenuItem = new JMenuItem("View Query Log");
		binomBioPAXQueryMenu.add(viewQueryLogMenuItem);
		viewQueryLogMenuItem.addActionListener(new BioPAXViewQueryLog());

		/*
		 * Utilities menu
		 */

		JMenu binomUtilsMenu = new JMenu("BiNoM Utilities");

		binomMainMenu.add(binomUtilsMenu);

		selectEdgesBetweendSelectedNodesMenuItem = new JMenuItem("Select Edges between Selected Nodes");
		binomUtilsMenu.add(selectEdgesBetweendSelectedNodesMenuItem);
		selectEdgesBetweendSelectedNodesMenuItem.addActionListener(new SelectEdgesBetweenSelectedNodes());
		selectEdgesBetweendSelectedNodesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));


		menuItem = new JMenuItem("Select upstream neighbours");
		menuItem.addActionListener(new SelectUpstreamNeighbours());
		binomUtilsMenu.add(menuItem);
		menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_8, java.awt.event.InputEvent.CTRL_MASK));

		menuItem = new JMenuItem("Select downstream neighbours");
		menuItem.addActionListener(new SelectDownstreamNeighbours());
		binomUtilsMenu.add(menuItem);
		menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_9, java.awt.event.InputEvent.CTRL_MASK));		

		menuItem = new JMenuItem("Double Network Differences");
		menuItem.addActionListener(new DoubleNetworkDifference());
		binomUtilsMenu.add(menuItem);

		menuItem = new JMenuItem("Update Networks");
		menuItem.addActionListener(new NetworksUpdate());
		binomUtilsMenu.add(menuItem);

		menuItem = new JMenuItem("Update connections from other network");
		menuItem.addActionListener(new EdgesFromOtherNetwork());
		binomUtilsMenu.add(menuItem);
		
		menuItem = new JMenuItem("Remove multiple edges");
		menuItem.addActionListener(new ConvertToUndirectedGraph());
		binomUtilsMenu.add(menuItem);

		mergeNetworksAndFilterMenuItem  = new JMenuItem("Merge Networks and Filter by Frequency");
		mergeNetworksAndFilterMenuItem.addActionListener(new MergeNetworksAndFilter());
		binomUtilsMenu.add(mergeNetworksAndFilterMenuItem);


		JMenu clipboardMenu = new JMenu("Clipboard");

		binomUtilsMenu.add(clipboardMenu);

		setNodesAndEdgesMenuItem = new JMenuItem("Copy Selected Nodes and Edges to Clipboard");
		clipboardMenu.add(setNodesAndEdgesMenuItem);
		setNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdges(true));

		addNodesAndEdgesMenuItem = new JMenuItem("Add Selected Nodes and Edges to Clipboard");
		clipboardMenu.add(addNodesAndEdgesMenuItem);
		addNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdges(false));

		pasteNodesAndEdgesMenuItem = new JMenuItem("Paste Nodes and Edges from Clipboard");
		clipboardMenu.add(pasteNodesAndEdgesMenuItem);
		pasteNodesAndEdgesMenuItem.addActionListener(new PasteNodesAndEdges());

		showClipboardContentsMenuItem = new JMenuItem("Show Clipboard Contents");
		clipboardMenu.add(showClipboardContentsMenuItem);
		showClipboardContentsMenuItem.addActionListener(new ShowClipboardContents());

		clipboardMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) { }
			public void menuDeselected(MenuEvent e) { }

			public void menuSelected(MenuEvent e) {
				CyNetworkView view = Cytoscape.getCurrentNetworkView();

				selectEdgesBetweendSelectedNodesMenuItem.setEnabled
				(view != null && view.getSelectedNodes().size() != 0);

				setNodesAndEdgesMenuItem.setEnabled
				(view != null &&
						(view.getSelectedNodes().size() != 0 ||
								(view.getSelectedEdges().size() != 0)));

				addNodesAndEdgesMenuItem.setEnabled
				(view != null &&
						(view.getSelectedNodes().size() != 0 ||
								(view.getSelectedEdges().size() != 0)));

				showClipboardContentsMenuItem.setEnabled(!NodesAndEdgesClipboard.getInstance().isEmpty());
				pasteNodesAndEdgesMenuItem.setEnabled(!NodesAndEdgesClipboard.getInstance().isEmpty());
			}
		});
		
		binomUtilsMenu.addSeparator();
		menuItem = new JMenuItem("Create set intersection graph in a folder");
		menuItem.addActionListener(new CreateSetIntersectionGraph());
		binomUtilsMenu.add(menuItem);
		
		menuItem = new JMenuItem("About BiNoM...");
		menuItem.addActionListener(new AboutBox());
		binomMainMenu.add(menuItem);

	}

	/**
	 * Gives a description of this plugin.
	 */
	public String describe() {
		StringBuffer sb = new StringBuffer();
		sb.append("BiNoM plugin, see http://binom.curie.fr/");
		return sb.toString();
	}

}
