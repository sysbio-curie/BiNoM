package fr.curie.BiNoM.pathways.test;

import java.util.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.cytoscape.utils.*;

import edu.rpi.cs.xgmml.*;
import org.sbml.x2001.ns.celldesigner.*;

import vdaoengine.utils.Utils;
import fr.curie.BiNoM.cytoscape.analysis.*; 

public class testDialogs {

	public static void main(String[] args) {
		
		try{
			
			//String file = "c:/datas/simon/influencemap/influence3.xgmml";
			String file = "c:/datas/basal/dnarepairmap2/sphase.xgmml";
			Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(file));
			gr.calcNodesInOut();
			
			//String id = "RAD9|pho";
			String id = "CDC7";

			Node n = gr.getNode(id);
			/*Vector<Node> nodes = BiographUtils.getNodeNeighbors(gr,n,true,true,100);
			
			//Vector<String> pnames = BiographUtils.extractProteinNamesFromNodeNames(nodes);
			Vector<String> pnames = new Vector<String>();
			for(int i=0;i<nodes.size();i++){
				Node node = nodes.get(i);
				Vector<String> pnames_node = new Vector<String>(); 
				Vector<Attribute> atts = node.getAttributesWithSubstringInName("HUGO");
				for(int j=0;j<atts.size();j++){
					String val = atts.get(j).value;
					val = Utils.replaceString(val, ",", "");
					val = Utils.replaceString(val, " ", "");
					StringTokenizer st = new StringTokenizer(val,"@@");
					while(st.hasMoreTokens())
						pnames_node.add(st.nextToken());
				}
				if(pnames_node.size()==0){
					Vector<Node> nodes_node = new Vector<Node>();
					nodes_node.add(node);
					//pnames_node = BiographUtils.extractProteinNamesFromNodeNames(nodes_node);
				}
				for(int j=0;j<pnames_node.size();j++)
					if(pnames.indexOf(pnames_node.get(j))<0)
						pnames.add(pnames_node.get(j));
			}
			for(int i=0;i<pnames.size();i++)
				System.out.println(pnames.get(i));*/
			
			Vector<String> selected = new Vector<String>();
			selected.add(n.Id);
			HashMap<String,Vector<String>> sets = BiographUtils.getNeighborhoodSets(gr,selected,true,true,3,1,true, false);
			Set setsS = sets.keySet();
			Iterator<String> it = setsS.iterator();
			while(it.hasNext()){
				String name = it.next();
				Vector<String> pnames = sets.get(name);
				System.out.print(name+":\t");
				for(int i=0;i<pnames.size();i++){
					String nm = pnames.get(i);
					System.out.print(nm+"\t");
				}
				System.out.println();
			}
			
			//System.exit(0);
			
			BioPAX biopax = new BioPAX();

			createNeighborhoodSetsDialog dial = createNeighborhoodSetsDialog.getInstance();
			
			HashMap<String,Vector<String>> geneSets = BiographUtils.getNeighborhoodSets(gr, selected, true, true, 3,10,true, false);
    		//Set set = geneSets.keySet();
    		//System.out.println(set.size());
			
			dial.network = gr;
			dial.raise();
			
			
			/*String filen = "c:/datas/hprd8/test.xgmml";
			GraphDocument grdoc = XGMML.loadFromXMGML(filen);
			Vector<String> selected = new Vector<String>();
			selected.add("E2F1");
			selected.add("SP1");
			selected.add("RB1");
			selected.add("CDK2");
			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option(); 
			options.checkComponentSignificance = true;
			ExtractSubnetworkTask task = new ExtractSubnetworkTask(grdoc,selected,options,null);
		    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);*/

			
			
			//ExtractSubnetworkDialog dialog = ExtractSubnetworkDialog.getInstance();
			//dialog.raise();
			//ShowTextDialog dialog = new ShowTextDialog(); 
			//dialog.pop("Title for the dialog","Some text\nText");
			
			//biopax.loadBioPAX(file);
			
			//GraphDocument grDoc = (BioPAXToCytoscapeConverter.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, file, new BioPAXToCytoscapeConverter.Option())).graphDocument;

			//ListAllReactionsDialog dialog = new ListAllReactionsDialog();
			//dialog.pop(grDoc,null,biopax);
			
			
			/*String file = "c:/datas/binomtest/M-Phase2.xml";
			CellDesigner cd = new CellDesigner();
			org.sbml.x2001.ns.celldesigner.SbmlDocument cdsbml = cd.loadCellDesigner(file);
			
			cd.entities = cd.getEntities(cdsbml);
			GraphDocument grDoc = CellDesignerToCytoscapeConverter.getXGMMLGraph("M-Phase2", cdsbml.getSbml());

			ListAllReactionsDialog dialog = new ListAllReactionsDialog();
			//dialog.pop(grDoc,cdsbml,null);
			dialog.pop(grDoc,cdsbml,null);*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
