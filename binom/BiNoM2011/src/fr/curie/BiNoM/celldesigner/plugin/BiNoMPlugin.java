package fr.curie.BiNoM.celldesigner.plugin;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;

import fr.curie.BiNoM.celldesigner.biopax.BioPAXImportFromFile;
import fr.curie.BiNoM.celldesigner.biopax.propedit.BioPAXClassTree;
import fr.curie.BiNoM.celldesigner.biopax.propedit.BioPAXPropertyBrowser;

import fr.curie.BiNoM.celldesigner.analysis.ClusterNetworks;
import fr.curie.BiNoM.celldesigner.analysis.ConnectedComponents;
import fr.curie.BiNoM.celldesigner.analysis.CycleDecomposition;

import fr.curie.BiNoM.celldesigner.analysis.ExtractReactionNetwork;
import fr.curie.BiNoM.celldesigner.analysis.LinearizeNetwork;
import fr.curie.BiNoM.celldesigner.analysis.MaterialComponents;

import fr.curie.BiNoM.celldesigner.analysis.MonoMolecularReactionsAsEdges;
import fr.curie.BiNoM.celldesigner.analysis.PruneGraph;
import fr.curie.BiNoM.celldesigner.analysis.StronglyConnectedComponents;
import fr.curie.BiNoM.celldesigner.analysis.ExcludeIntermediateNodes;
import fr.curie.BiNoM.celldesigner.analysis.ModularView;

public class BiNoMPlugin extends CellDesignerPlugin {

    PluginMenuItem exportBioPAXMenuItem;
    PluginMenuItem exportCellDesignerMenuItem;
    PluginMenuItem exportSBMLMenuItem;
    PluginMenuItem saveBioPAXMenuItem;    
    PluginMenuItem syncBioPAXMenuItem;    
    PluginMenuItem associateBioPAXMenuItem;
    PluginMenuItem associateCellDesignerMenuItem;
    PluginMenuItem selectEdgesBetweendSelectedNodesMenuItem;
    PluginMenuItem showClipboardContentsMenuItem;
    PluginMenuItem setNodesAndEdgesMenuItem;
    PluginMenuItem addNodesAndEdgesMenuItem;
    PluginMenuItem pasteNodesAndEdgesMenuItem;
    PluginMenuItem toggleBioPAXNameMenuItem;
    PluginMenuItem listAllReactionsMenuItem;
    PluginMenuItem listAllNodesMenuItem;
    PluginMenuItem produceNaviCellMenuItem;

    /**
     * 
     */
    public BiNoMPlugin(boolean fake){
    }
    
    
    public BiNoMPlugin(){
	BiNoMPluginAction action = new BiNoMPluginAction(this);
	/*
	PluginMenu menu  = new PluginMenu("BiNoM Plugin");
	item = new PluginMenuItem("Display Part of the Pathway", action);
	menu.add(item);
	addCellDesignerPluginMenu(menu);
	*/

        PluginMenu binomIOMenu = new PluginMenu("BiNoM I/O");
	addCellDesignerPluginMenu(binomIOMenu);

	BioPAXImportFromFile biopaxImportFromFile = new BioPAXImportFromFile(this);

        PluginMenuItem menuItem = new PluginMenuItem
	    ("Import BioPAX Document from file...", biopaxImportFromFile);
        binomIOMenu.add(menuItem);

        menuItem = new PluginMenuItem
	    ("Import BioPAX Document from URL...", action);
	menuItem.setEnable(false);
        binomIOMenu.add(menuItem);

        //binomIOMenu.addSeparator();

        binomIOMenu.addSeparator();

	/*
        menuItem = new PluginMenuItem
	    ("Import CellDesigner Document from file...", action);
	    menuItem.setEnable(false);
        binomIOMenu.add(menuItem);

        menuItem = new PluginMenuItem
	    ("Import CellDesigner Document from URL...", action);
	    menuItem.setEnable(false);

        binomIOMenu.add(menuItem);

        binomIOMenu.addSeparator();
	*/

        menuItem = new PluginMenuItem
	    ("Import influence network from AIN file...", action);
	menuItem.setEnable(false);
        binomIOMenu.add(menuItem);

        binomIOMenu.addSeparator();

        exportBioPAXMenuItem = new PluginMenuItem("Export current network to BioPAX...", action);
	exportBioPAXMenuItem.setEnable(false);
        binomIOMenu.add(exportBioPAXMenuItem);

        exportCellDesignerMenuItem = new PluginMenuItem("Export current network to CellDesigner...", action);
	exportCellDesignerMenuItem.setEnable(false);
        binomIOMenu.add(exportCellDesignerMenuItem);

        exportSBMLMenuItem = new PluginMenuItem("Export current network to SBML...", action);
	exportSBMLMenuItem.setEnable(false);
        binomIOMenu.add(exportSBMLMenuItem);
	
        
        binomIOMenu.addSeparator();        

        associateBioPAXMenuItem = new PluginMenuItem("Associate BioPAX Source...", action);
	associateBioPAXMenuItem.setEnable(false);
        binomIOMenu.add(associateBioPAXMenuItem);

        saveBioPAXMenuItem = new PluginMenuItem("Save whole associated BioPAX as...", action);
	saveBioPAXMenuItem.setEnable(false);
        binomIOMenu.add(saveBioPAXMenuItem);

        associateCellDesignerMenuItem = new PluginMenuItem("Associate CellDesigner Source...", action);
	associateCellDesignerMenuItem.setEnable(false);
        binomIOMenu.add(associateCellDesignerMenuItem);
        
        binomIOMenu.addSeparator();        

        listAllReactionsMenuItem = new PluginMenuItem("List all reactions...", action);
	listAllReactionsMenuItem.setEnable(false);
        binomIOMenu.add(listAllReactionsMenuItem);
        
        listAllNodesMenuItem = new PluginMenuItem("List all nodes...", action);
	listAllNodesMenuItem.setEnable(false);
        binomIOMenu.add(listAllNodesMenuItem);
                

	PluginMenu structAnaMenu = new PluginMenu("BiNoM Analysis");
	addCellDesignerPluginMenu(structAnaMenu);

	ConnectedComponents connectedComponents = new ConnectedComponents(this);
        menuItem = new PluginMenuItem("Get Connected Components...", connectedComponents);
	structAnaMenu.add(menuItem);
	
	StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(this);
	menuItem = new PluginMenuItem("Get Strongly Connected Components...", stronglyConnectedComponents);
	structAnaMenu.add(menuItem);
	
	
	PruneGraph pruneGraph = new PruneGraph(this);
        menuItem = new PluginMenuItem("Prune Graph...", pruneGraph);
	structAnaMenu.add(menuItem);

	MaterialComponents materialComponents = new MaterialComponents(this);
        menuItem = new PluginMenuItem("Get Material Components...", materialComponents);
	structAnaMenu.add(menuItem);

	CycleDecomposition cycleDecomposition = new CycleDecomposition(this);
        menuItem = new PluginMenuItem("Get Cycle Decomposition...", cycleDecomposition);
	structAnaMenu.add(menuItem);

	menuItem = new PluginMenuItem("Path Analysis...", action);
	menuItem.setEnable(false);
	structAnaMenu.add(menuItem);
	
	structAnaMenu.addSeparator();
	ModularView modularView = new ModularView(this);
	menuItem = new PluginMenuItem("Generate Modular View...", modularView);
	structAnaMenu.add(menuItem);

	ClusterNetworks clusterNetworks = new ClusterNetworks(this);
	menuItem = new PluginMenuItem("Cluster Networks...", clusterNetworks);
	structAnaMenu.add(menuItem);
	
	structAnaMenu.addSeparator();

	MonoMolecularReactionsAsEdges monoMolecularReactionsAsEdges = new MonoMolecularReactionsAsEdges(this);
	menuItem = new PluginMenuItem("Mono-molecular react.to edges...", monoMolecularReactionsAsEdges);

	structAnaMenu.add(menuItem);
		
	LinearizeNetwork linearizeNetwork = new LinearizeNetwork(this);
	menuItem = new PluginMenuItem("\'Linearize\' network...", linearizeNetwork);
	structAnaMenu.add(menuItem);

	ExcludeIntermediateNodes excludeIntermediateNodes = new ExcludeIntermediateNodes(this);
	menuItem = new PluginMenuItem("Exclude intermediate nodes...", excludeIntermediateNodes);
	structAnaMenu.add(menuItem);

	ExtractReactionNetwork extractReactionNetwork = new ExtractReactionNetwork(this);
	menuItem = new PluginMenuItem("Extract Reaction Network...", extractReactionNetwork);
	structAnaMenu.add(menuItem);
	
	structAnaMenu.addSeparator();
	menuItem = new PluginMenuItem("Path consistency analysis...", action);
	menuItem.setEnable(false);
	structAnaMenu.add(menuItem);
	
	

	PluginMenu binomBioPAXUtilsMenu = new PluginMenu("BiNoM BioPAX Utils");
        addCellDesignerPluginMenu(binomBioPAXUtilsMenu);

        menuItem = new PluginMenuItem
	    ("BioPAX Property Editor...", new BioPAXPropertyBrowser(this));
        binomBioPAXUtilsMenu.add(menuItem);

        menuItem = new PluginMenuItem
	    ("BioPAX Class Tree...", new BioPAXClassTree(this));
        binomBioPAXUtilsMenu.add(menuItem);

        toggleBioPAXNameMenuItem = new PluginMenuItem
	    ("Toggle BioPAX Naming Service", action);
	toggleBioPAXNameMenuItem.setEnable(false);
        binomBioPAXUtilsMenu.add(toggleBioPAXNameMenuItem);

        syncBioPAXMenuItem = new PluginMenuItem("Synchronize networks with BioPAX...", action);
	syncBioPAXMenuItem.setEnable(false);
        binomBioPAXUtilsMenu.add(syncBioPAXMenuItem);

	PluginMenu binomBioPAXQueryMenu = new PluginMenu("BiNoM BioPAX Query ");

	addCellDesignerPluginMenu(binomBioPAXQueryMenu);
	
        PluginMenuItem generateIndexMenuItem = new PluginMenuItem("Generate Index", action);
	generateIndexMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(generateIndexMenuItem);

        PluginMenuItem loadIndexMenuItem = new PluginMenuItem("Load Index", action);
	loadIndexMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(loadIndexMenuItem);

        PluginMenuItem displayIndexInfoMenuItem = new PluginMenuItem("Display Index Info", action);
	displayIndexInfoMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(displayIndexInfoMenuItem);
        
        binomBioPAXQueryMenu.addSeparator();

        PluginMenuItem selectEntitiesMenuItem = new PluginMenuItem("Select Entities", action);
	selectEntitiesMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(selectEntitiesMenuItem);

        PluginMenuItem standardQueryMenuItem = new PluginMenuItem("Standard Query", action);
	standardQueryMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(standardQueryMenuItem);
        
        PluginMenuItem pathIndexAnalysisItem = new PluginMenuItem("Index Path Analysis", action);
	pathIndexAnalysisItem.setEnable(false);
    	binomBioPAXQueryMenu.add(pathIndexAnalysisItem);

        PluginMenuItem viewQueryLogMenuItem = new PluginMenuItem("View Query Log", action);
	viewQueryLogMenuItem.setEnable(false);
	binomBioPAXQueryMenu.add(viewQueryLogMenuItem);

	// -------------------- Utilities

        PluginMenu binomUtilsMenu = new PluginMenu("BiNoM Utilities");

        addCellDesignerPluginMenu(binomUtilsMenu);

        selectEdgesBetweendSelectedNodesMenuItem = new PluginMenuItem("Select Edges between Selected Nodes", action);
	selectEdgesBetweendSelectedNodesMenuItem.setEnable(false);
        binomUtilsMenu.add(selectEdgesBetweendSelectedNodesMenuItem);
	//selectEdgesBetweendSelectedNodesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
 
        menuItem = new PluginMenuItem("Double Network Differences", action);
	menuItem.setEnable(false);
        //menuItem.addActionListener(new DoubleNetworkDifference());
        binomUtilsMenu.add(menuItem);

        menuItem = new PluginMenuItem("Update Networks", action);
	menuItem.setEnable(false);
        //menuItem.addActionListener(new NetworksUpdate());
        binomUtilsMenu.add(menuItem);

        menuItem = new PluginMenuItem("Update connections from other network", action);
	menuItem.setEnable(false);
        //menuItem.addActionListener(new EdgesFromOtherNetwork());
        binomUtilsMenu.add(menuItem);
        

        PluginMenu clipboardMenu = new PluginMenu("Clipboard");

	binomUtilsMenu.add(clipboardMenu);
	
	setNodesAndEdgesMenuItem = new PluginMenuItem("Copy Selected Nodes and Edges to Clipboard", action);
	setNodesAndEdgesMenuItem.setEnable(false);
        clipboardMenu.add(setNodesAndEdgesMenuItem);
        //setNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdges(true));

	addNodesAndEdgesMenuItem = new PluginMenuItem("Add Selected Nodes and Edges to Clipboard", action);
	addNodesAndEdgesMenuItem.setEnable(false);
        clipboardMenu.add(addNodesAndEdgesMenuItem);
        //addNodesAndEdgesMenuItem.addActionListener(new CopySelectedNodesAndEdges(false));

	pasteNodesAndEdgesMenuItem = new PluginMenuItem("Paste Nodes and Edges from Clipboard", action);
	pasteNodesAndEdgesMenuItem.setEnable(false);
        clipboardMenu.add(pasteNodesAndEdgesMenuItem);
        //pasteNodesAndEdgesMenuItem.addActionListener(new PasteNodesAndEdges());

	showClipboardContentsMenuItem = new PluginMenuItem("Show Clipboard Contents", action);
	showClipboardContentsMenuItem.setEnable(false);
        clipboardMenu.add(showClipboardContentsMenuItem);
        //showClipboardContentsMenuItem.addActionListener(new ShowClipboardContents());

    }
	
	
    public void addPluginMenu() {
	System.out.println("addPluginMenu");
    }

    public void SBaseAdded(PluginSBase sbase) {
	System.out.println("SBaseAdded");
    }

    public void SBaseChanged(PluginSBase sbase) {
	System.out.println("SBaseChanged");
    }

    public void SBaseDeleted(PluginSBase sbase) {
	System.out.println("SBaseDeleted");
    }

    public void modelOpened(PluginSBase sbase) {
	System.out.println("modelOpened");
    }

    public void modelSelectChanged(PluginSBase sbase) {
	System.out.println("modelSelectChanged");
    }

    public void modelClosed(PluginSBase sbase) {
	System.out.println("modelClosed");
    }
}
