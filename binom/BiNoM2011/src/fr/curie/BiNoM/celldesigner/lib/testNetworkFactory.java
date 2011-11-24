package fr.curie.BiNoM.celldesigner.lib;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginModel;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.biopax.*;
import fr.curie.BiNoM.celldesigner.plugin.*;
import jp.sbi.celldesigner.plugin.*;
import jp.sbi.celldesigner.*;


public class testNetworkFactory {
	
	public static void main(String args[]){
		try{
			
			String fname = "c:/datas/binomtest/casp8.owl";
			
			String as[] = new String[3];
			//as[0] = "-Djava.library.path=.";
			//as[1] = " -cp";
			//as[2] = "C:/MyPrograms/BiNoM2008/pathways-lib/CellDesigner401Jars.jar";
			//as[2] = "lib\\SBWCore.jar;exec\\autolayout_yobf.jar;exec\\celldesigner.jar;exec\\yObf.jar;lib\\avalon-framework-4.1.4.jar;lib\\batik.jar;lib\\browserlauncher.jar;lib\\freehep-export-2.0.3.jar;lib\\freehep-graphics2d-2.0.jar;lib\\freehep-graphicsio-2.0.jar;lib\\freehep-graphicsio-ps-2.0.jar;lib\\freehep-io-2.0.1.jar;lib\\freehep-swing-2.0.2.jar;lib\\freehep-util-2.0.1.jar;lib\\itext-1.4.6.jar;lib\\jai_codec.jar;lib\\jai_core.jar;lib\\jcommon-1.0.0-pre2.jar;lib\\jeuclid-2.0.jar;lib\\jfreechart-1.0.0-pre2.jar;lib\\mlibwrapper_jai.jar;lib\\MRJAdapter.jar;lib\\openide-lookup-1.9-patched-1.0.jar;lib\\sbmlj.jar;lib\\xercesImpl.jar;lib\\xml-apis.jar;lib\\SOSlib.jar;lib\\copasi.jar;lib\\copasi_gui.jar;exec\\celldesigner.jar;lib\\binom.jar;lib\\binomlibfull.jar";
			//as[2] = "\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\SBWCore.jar;exec\\autolayout_yobf.jar;exec\\celldesigner.jar;exec\\yObf.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\avalon-framework-4.1.4.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\batik.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\browserlauncher.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-export-2.0.3.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-graphics2d-2.0.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-graphicsio-2.0.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-graphicsio-ps-2.0.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-io-2.0.1.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-swing-2.0.2.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\freehep-util-2.0.1.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\itext-1.4.6.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\jai_codec.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\jai_core.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\jcommon-1.0.0-pre2.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\jeuclid-2.0.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\jfreechart-1.0.0-pre2.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\mlibwrapper_jai.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\MRJAdapter.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\openide-lookup-1.9-patched-1.0.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\sbmlj.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\xercesImpl.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\xml-apis.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\SOSlib.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\copasi.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\copasi_gui.jar;exec\\celldesigner.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\binom.jar;\"C:\\Program Files\\CellDesigner4.0.1\"\\lib\\binomlibfull.jar";
			
			
			//jp.sbi.celldesigner.util.
			
			//jp.sbi.celldesigner.Application app = jp.sbi.celldesigner.MainWindow.getLastInstance().
			
			//jp.sbi.celldesigner.util.
			
			BiNoMPlugin plugin = new BiNoMPlugin(true);
			PluginModel model = plugin.getSelectedModel();
			
			BioPAXToCytoscapeConverter.Graph graph = BioPAXToCytoscapeConverter.convert
			(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION,
					 fname,
					 "some name",
					 new BioPAXToCytoscapeConverter.Option());
			
			
			NetworkFactory.createNetwork(model, plugin, graph.graphDocument);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
