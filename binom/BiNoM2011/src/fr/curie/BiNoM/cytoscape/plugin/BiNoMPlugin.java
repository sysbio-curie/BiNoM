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

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.plugin;

import java.util.*;
import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import giny.model.Node;
import giny.view.NodeView;

import cytoscape.plugin.CytoscapePlugin;
import cytoscape.util.CytoscapeAction;
import cytoscape.view.CytoscapeDesktop;
import cytoscape.Cytoscape;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.visual.*;
import cytoscape.data.Semantics;
import cytoscape.view.CyMenus;
import javax.swing.*;

import fr.curie.BiNoM.cytoscape.ain.*;
import fr.curie.BiNoM.cytoscape.biopax.*;
import fr.curie.BiNoM.cytoscape.biopax.propedit.*;
import fr.curie.BiNoM.biopax.propedit.*;
import fr.curie.BiNoM.cytoscape.sbml.*;
import fr.curie.BiNoM.cytoscape.celldesigner.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.cytoscape.analysis.*;
import fr.curie.BiNoM.cytoscape.utils.*;
import fr.curie.BiNoM.cytoscape.nestmanager.*;
import fr.curie.BiNoM.cytoscape.netwop.*;
import fr.curie.BiNoM.cytoscape.biopax.query.*;

import javax.swing.event.*;

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
    JMenuItem modifyCellDesignerNotesMenuItem;
    JMenuItem createNeighborhoodSetsMenuItem;
    JMenuItem mergeNetworksAndFilterMenuItem;

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
        JMenu binomIOMenu = new JMenu("BiNoM I/O");
	plugInMenu.add(binomIOMenu);

        JMenuItem menuItem = new JMenuItem
	    ("Import BioPAX 3 Document from file...");
        binomIOMenu.add(menuItem);
        menuItem.addActionListener(new BioPAXImportFromFile());

        menuItem = new JMenuItem
	    ("Import BioPAX 3 Document from URL...");
        binomIOMenu.add(menuItem);
        menuItem.addActionListener(new BioPAXImportFromURL());

        //binomIOMenu.addSeparator();

        binomIOMenu.addSeparator();

        menuItem = new JMenuItem
	    ("Import CellDesigner Document from file...");
        binomIOMenu.add(menuItem);
        menuItem.addActionListener(new CellDesignerImportFromFile());

        // this has never been implemented
//        menuItem = new JMenuItem
//	    ("Import CellDesigner Document from URL...");
//        binomIOMenu.add(menuItem);
        
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
        
        modifyCellDesignerNotesMenuItem = new JMenuItem("Modify CellDesigner notes...");
        binomIOMenu.add(modifyCellDesignerNotesMenuItem);
        modifyCellDesignerNotesMenuItem.addActionListener(new modifyCellDesignerNotes());
        
        
       

	JMenu structAnaMenu = new JMenu("BiNoM Analysis");
	plugInMenu.add(structAnaMenu);

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
	//menuItem = new JMenuItem("Generate Modular View...");
	//menuItem.addActionListener(new ModularView());
	//structAnaMenu.add(menuItem);

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
	
	structAnaMenu.addSeparator();
	menuItem = new JMenuItem("Path Influence Quantification analysis...");
	menuItem.addActionListener(new PathConsistencyAnalyzer());
	structAnaMenu.add(menuItem);
	
	structAnaMenu.addSeparator();
	menuItem = new JMenuItem("OCSANA analysis...");
	menuItem.addActionListener(new OptimalCutSetAnalyzer());
	structAnaMenu.add(menuItem);


    structAnaMenu.addSeparator();
    menuItem = new JMenuItem("About BiNoM...");
    menuItem.addActionListener(new AboutBox());
    structAnaMenu.add(menuItem);

	
	structAnaMenu.addSeparator();
    
    createNeighborhoodSetsMenuItem = new JMenuItem("Create neighborhood sets file...");
    structAnaMenu.add(createNeighborhoodSetsMenuItem);
    createNeighborhoodSetsMenuItem.addActionListener(new createNeighborhoodSets());

    // Module manager begin
	JMenu binomNestManagerMenu = new JMenu("BiNoM module manager");
	plugInMenu.add(binomNestManagerMenu);
	
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
// Module manager end 	
	
	JMenu binomBioPAXUtilsMenu = new JMenu("BiNoM BioPAX 3 Utils");
	plugInMenu.add(binomBioPAXUtilsMenu);

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
		    /*
		      exportBioPAXMenuItem.setEnabled
		      (BioPAXSourceDB.getInstance().isBioPAXNetwork(cyNetwork) ||
		      CellDesignerSourceDB.getInstance().isCellDesignerNetwork(cyNetwork));


		      exportCellDesignerMenuItem.setEnabled
		      (CellDesignerSourceDB.getInstance().isCellDesignerNetwork(cyNetwork));

		      exportSBMLMenuItem.setEnabled
		      (BioPAXSourceDB.getInstance().isBioPAXNetwork(cyNetwork));
		    */

		}
	    });
	
	JMenu binomBioPAXQueryMenu = new JMenu("BiNoM BioPAX 3 Query ");

	plugInMenu.add(binomBioPAXQueryMenu);
	
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

	// -------------------- Utilities

        JMenu binomUtilsMenu = new JMenu("BiNoM Utilities");

        plugInMenu.add(binomUtilsMenu);

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

	/*
	  JMenu testMenu = new JMenu("Tests...");

	  binomUtilsMenu.add(testMenu);

	  menuItem = new JMenuItem("Union");
	  testMenu.add(menuItem);
	  menuItem.addActionListener(new TestNetworkOP("Union"));

	  menuItem = new JMenuItem("Intersect");
	  testMenu.add(menuItem);
	  menuItem.addActionListener(new TestNetworkOP("Intersect"));

	  menuItem = new JMenuItem("Difference");
	  testMenu.add(menuItem);
	  menuItem.addActionListener(new TestNetworkOP("Diff"));

	  menuItem = new JMenuItem("Double Difference");
	  testMenu.add(menuItem);
	  menuItem.addActionListener(new TestNetworkOP("Diff2"));
	*/
    }

    /**
     * Gives a description of this plugin.
     */
    public String describe() {
        StringBuffer sb = new StringBuffer();
        sb.append("BiNoM plugin, see http://bioinfo-out.curie.fr/projects/binom/");
        return sb.toString();
    }

    /**
     * This class gets attached to the menu item.
     */
    /*
      public class BiNoMPluginAction extends CytoscapeAction {

      public BiNoMPluginAction() {super("BiNoMPlugin");}
      public void actionPerformed(ActionEvent ae) {
      //get the network object; this contains the graph
      CyNetwork network = Cytoscape.getCurrentNetwork();
      //get the network view object
      CyNetworkView view = Cytoscape.getCurrentNetworkView();
      //can't continue if either of these is null
      if (network == null || view == null) {return;}
      //put up a dialog if there are no selected nodes
      if (view.getSelectedNodes().size() == 0) {
      JOptionPane.showMessageDialog(view.getComponent(),
      "Please select one or more nodes.");
      }

      //a container to hold the objects we're going to select
      Set nodeViewsToSelect = new HashSet();
      //iterate over every node view
      int nn = 0;

      for (Iterator i = view.getSelectedNodes().iterator(); i.hasNext(); nn++) {
      NodeView nView = (NodeView)i.next();
      //first get the corresponding node in the network
      System.out.println("step #0 " + nView +
      " width: " + nView.getWidth() +
      " shape: " + nView.getShape());
      nView.setWidth(nView.getWidth() * 2);
      System.out.println("step #1 " + nView +
      " width: " + nView.getWidth() +
      " shape: " + nView.getShape());

      if ((nn % 4) == 0)
      nView.setShape(NodeView.DIAMOND);
      else if ((nn % 4) == 1)
      nView.setShape(NodeView.RECTANGLE);
      else if ((nn % 4) == 2)
      nView.setShape(NodeView.OCTAGON);
      else
      nView.setShape(NodeView.TRIANGLE);
      nView.setXPosition(nView.getXPosition() + (nn % 3) * 50);
      nView.setYPosition(nView.getYPosition() + - (nn % 5) * 60);
      CyNode node = (CyNode)nView.getNode();
      network.setFlagged(node, true);

      boolean r = network.setNodeAttributeValue(node.getRootGraphIndex(), "Alpha" + nn, new Integer(nn));
      System.out.println("rootgraph class " +
      node.getRootGraph().getClass().getName());
      }

      if (view.getVizMapManager() != null)
      System.out.println("visual: " + view.getVizMapManager().getVisualStyle());
      System.out.println("vizmap2: " +
      Cytoscape.getDesktop().getVizMapManager());

      VisualStyle visualStyle = null;
      CalculatorCatalog calculatorCatalog = null;
      if (Cytoscape.getDesktop().getVizMapManager() != null) {
      System.out.println("visual2: " +
      Cytoscape.getDesktop().getVizMapManager().getVisualStyle());
      System.out.println("visual2: " +
      Cytoscape.getDesktop().getVizMapManager().getVisualStyle().getName());
      VisualMappingManager vmm = Cytoscape.getDesktop().getVizMapManager();
      calculatorCatalog = vmm.getCalculatorCatalog();
      //gets the currently active visual style
      VisualStyle currentStyle = vmm.getVisualStyle();
      NodeAppearanceCalculator nodeAppCalc = new NodeAppearanceCalculator(currentStyle.getNodeAppearanceCalculator());
      nodeAppCalc.setDefaultNodeShape(ShapeNodeRealizer.TRIANGLE);
      nodeAppCalc.setDefaultNodeWidth(70);
      EdgeAppearanceCalculator edgeAppCalc = new EdgeAppearanceCalculator(currentStyle.getEdgeAppearanceCalculator());
      GlobalAppearanceCalculator globalAppCalc = new GlobalAppearanceCalculator(currentStyle.getGlobalAppearanceCalculator());
      visualStyle = new VisualStyle("EV Visual",
      nodeAppCalc, edgeAppCalc, globalAppCalc);
      //view.applyVizmapper(visualStyle);

      }

      //tell the view to redraw since we've changed the selection
      nn = 0;
      for (Iterator i = view.getSelectedNodes().iterator(); i.hasNext(); nn++) {
      NodeView nView = (NodeView)i.next();
      System.out.println("step #2 " + nView + " width: " + nView.getWidth() + " shape: " + nView.getShape());

      //System.out.println("w = " + w + ": " + nView);
      if (visualStyle != null && (nn % 3) == 0)
      view.applyVizMap(nView, visualStyle);

      }
      //            view.redrawGraph(false, true);
      //((giny.view.GraphView)view).updateView();
      //view.redrawGraph(true, true);
      view.redrawGraph(true, true);
      for (Iterator i = view.getSelectedNodes().iterator(); i.hasNext(); nn++) {
      NodeView nView = (NodeView)i.next();
      System.out.println("step #3 " + nView + " width: " + nView.getWidth() + " shape: " + nView.getShape());
      double w = ((giny.view.GraphView)view).getNodeDoubleProperty(nView.getRootGraphIndex(), giny.view.GraphView.NODE_WIDTH);
      //System.out.println("x = " + w + ": " + nView);
      }

      // part 2 : network creation

      cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
      cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
      CyNetwork netw = Cytoscape.createNetwork("nt test" + time_count);
      CyNetworkView networkView = Cytoscape.createNetworkView(netw);
      VisualStyle sty = new VisualStyle("EV Visual " + time_count,
      null, null, null);
      //networkView.setVisualStyle("EV Visual " + time_count);
      networkView.setVisualStyle("Sample1");
      networkView.applyVizmapper(calculatorCatalog.getVisualStyle("Sample1"));

      CyNode pnd = null;

      NodeView anv = null;
      java.util.Random random = new java.util.Random(100);
      for (int i = 0; i < 20; i++) {
      CyNode nd = Cytoscape.getCyNode("ND" + time_count, true);
      netw.addNode(nd);
      nodeAttrs.setAttribute(nd.getIdentifier(), "Alpha1", new Integer(time_count));
      nodeAttrs.setAttribute(nd.getIdentifier(), "Alpha2", new Integer(time_count + 1));
      nodeAttrs.setAttribute(nd.getIdentifier(), "Alpha3", new Integer(time_count + 2));
      NodeView nv = networkView.getNodeView(nd);
      anv = nv;
      //nv.setXPosition(i * 10 + (i & 1) == 0 ? 40 : 0);
      //nv.setYPosition(i * 10 + (i & 1) == 0 ? -40 : 0);
      int x = random.nextInt();
      int y = random.nextInt();
      //nv.setXPosition(2 + i * 22);
      //nv.setYPosition(2 + i * 22);
      nv.setXPosition(x % 200);
      nv.setYPosition(y % 200);
      nv.setWidth(30 + ((i & 1) == 0 ? 20 : 0));
      if (pnd != null) {
      CyEdge edge = Cytoscape.getCyEdge(pnd, nd,
      Semantics.INTERACTION,
      "CONTAINS",
      true);
      edgeAttrs.setAttribute(edge.getIdentifier(), "Alpha4",
      new Integer(time_count + 10));
      edgeAttrs.setAttribute(edge.getIdentifier(), "Alpha5",
      new Integer(time_count + 12));
      netw.addEdge(edge);
      }

      pnd = nd;
      time_count++;
      }

      //networkView.applyLayout(new cytoscape.layout.SpringEmbeddedLayouter(networkView));
      //	    networkView.redrawGraph(true, true);
      networkView.redrawGraph(true, false);
      anv.setWidth(100);
      }

      private String getLastLetter(CyNetwork network, CyNode node) {
      String canonicalName = (String)network.getNodeAttributeValue(node, Semantics.CANONICAL_NAME);
      //return nothing if we can't get a valid name
      if (canonicalName == null || canonicalName.length() == 0) {return null;}
      //extract the last letter
      int length = canonicalName.length();
      String lastLetter = canonicalName.substring(length-1);
      return lastLetter;
      }
      }
    */
}
