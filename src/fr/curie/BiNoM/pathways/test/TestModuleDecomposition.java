package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.*;
import edu.rpi.cs.xgmml.*;

public class TestModuleDecomposition {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/testModule/mphase.xgmml"));
			Graph mod1 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/testModule/cycle1.xgmml"));
			Graph mod2 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/testModule/cycle2.xgmml"));
			Graph mod3 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/testModule/cycle3.xgmml"));			
			mod1.name = "cycle1";
			mod2.name = "cycle2";
			mod3.name = "cycle3";
			Vector v = new Vector();
			v.add(mod1); 
			v.add(mod2);
			v.add(mod3);
			global.metaNodes = v;
			Graph meta = BiographUtils.CollapseMetaNodes(global,true,false);
			XGMML.saveToXGMML(XGMML.convertGraphToXGMML(meta), "c:/datas/binomtest/testModule/global_meta.xgmml");
			

			/*String moduleFolder = "c:/datas/calzone/problem12/";
			Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(moduleFolder+"calzone_etal_rbpathway.xml.xgmml"));
			File dir = new File(moduleFolder+"modules");
			File f[] = dir.listFiles();
			Vector v = new Vector();
			for(int i=0;i<f.length;i++){
				GraphDocument gr = XGMML.loadFromXMGML(f[i].getAbsolutePath());
				Graph module = XGMML.convertXGMMLToGraph(gr);
				module.Edges.clear();
				module.addConnections(global);
				gr = XGMML.convertGraphToXGMML(module);
				XGMML.saveToXGMML(gr, moduleFolder+"modules1/"+f[i].getName());
			}

			System.exit(0);*/
			
			/*Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/global1.xgmml"));
			Graph mod1 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/mod1.xgmml"));
			Graph mod2 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/binomtest/mod2.xgmml"));
			mod1.name = "mod1";
			mod2.name = "mod2";
			Vector v = new Vector();
			v.add(mod1); v.add(mod2);
			global.metaNodes = v;
			Graph meta = BiographUtils.CollapseMetaNodes(global,true,false);
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(meta), "c:/datas/binomtest/global_meta.xgmml");*/
			
			
			// read modules in a folder
			/*String moduleFolder = "c:/datas/calzone/modules1/modules/";
			File dir = new File(moduleFolder);
			File f[] = dir.listFiles();
			Vector v = new Vector();
			for(int i=0;i<f.length;i++){
				GraphDocument gr = XGMML.loadFromXMGML(f[i].getAbsolutePath());
				Graph module = XGMML.convertXGMMLToGraph(gr);
				if(module.name.endsWith(".xml"))
					module.name = module.name.substring(0,module.name.length()-4);
				module.name+=" module";
				System.out.println(gr.getGraph().getLabel());
				v.add(module);
			}
			Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/310507_proteins.xml.xgmml"));
			//GraphAlgorithms.PruneGraph(global);
			global.metaNodes = v;
			Graph meta = BiographUtils.CollapseMetaNodes(global,false,false);
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(meta), "c:/datas/calzone/modules1/modular.xgmml");*/
			
			
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/2903final_notranscription.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test_p15p16_cycD.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test_CycC_RB.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test_CycEA_E2F1.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test_CycEA_E2F1_APC.xgmml"));

			/*Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/310507_proteins.xml.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/test_CycE_p27.xgmml"));

			
			// Now read modules
			Graph apc = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/APC.xml.xgmml"));
			Graph apoptosis = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/apoptosis.xml.xgmml"));
			Graph cdc25 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cdc25c.xgmml"));
			Graph cyca = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cyca.xgmml"));			
			Graph cycb = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cycb.xml.xgmml"));			
			Graph cycc = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cycc.xml.xgmml"));
			Graph cycd = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cycd.xml.xgmml"));
			Graph cyce = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cyce.xgmml"));
			Graph cych = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/cych.xml.xgmml"));			
			Graph e2f1 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/e2f1.xml.xgmml"));
			Graph e2f4 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/e2f4.xml.xgmml"));
			Graph e2f6 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/e2f6.xml.xgmml"));			
			Graph ink4 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/ink4.xgmml"));			
			//Graph p15p16 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/p15p16.xml.xgmml"));
			Graph p21 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/p21cip.xml.xgmml"));
			Graph p27 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/p27kip.xml.xgmml"));
			Graph rb = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/rb.xml.xgmml"));
			Graph wee1 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/modules/wee1.xgmml"));			
			

			apc.name = "APC module";
			apoptosis.name = "Apoptosis  module";
			cdc25.name = "Cdc25c  module";
			cyca.name = "CycA  module";
			cycb.name = "CycB  module";
			cycc.name = "CycC  module";
			cycd.name = "CycD  module";
			cyce.name = "CycE  module";
			cych.name = "CycH  module";
			e2f1.name = "E2F1  module";
			e2f4.name = "E2F4  module";
			e2f6.name = "E2F6  module";
			ink4.name = "INK4  module";
			//p15p16.name = "p15p16  module";
			p21.name = "p21  module";
			p27.name = "p27  module";
			rb.name = "RB  module";
			wee1.name = "Wee1  module";


			global.metaNodes.add(apc);
			global.metaNodes.add(apoptosis);
			global.metaNodes.add(cdc25);
			global.metaNodes.add(cyca);
			global.metaNodes.add(cycb);			
			global.metaNodes.add(cycc);
			global.metaNodes.add(cycd);			
			global.metaNodes.add(cyce);
			global.metaNodes.add(cych);
			global.metaNodes.add(e2f1);
			global.metaNodes.add(e2f4);			
			global.metaNodes.add(e2f6);
			global.metaNodes.add(ink4);
		    global.metaNodes.add(p21);
			global.metaNodes.add(p27);

			global.metaNodes.add(rb);			
			global.metaNodes.add(wee1);
			
			//global.metaNodes.add(p15p16);			
			
			global = BiographUtils.CollapseMetaNodes(global,true,true);
			//global.prune();
			//global = (Graph)v.get(0);
			global.name = "modular";
			
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(global), "c:/datas/calzone/modules1/modular.xgmml");

			
			/*GraphDocument grdoc = XGMML.loadFromXMGML("c:/datas/calzone/modules/test/apoptosis.xml.xgmml");
			Graph global = XGMML.convertXGMMLToGraph(grdoc);
			
			// Now read modules
			Graph module1 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test/modules/module1.xgmml"));
			Graph module2 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test/modules/module2.xgmml"));
			Graph module3 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test/modules/module3.xgmml"));
			Graph module4 = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules/test/modules/module4.xgmml"));
			
			module1.name = "module1";
			module2.name = "module2";
			module3.name = "module3";
			module4.name = "module4";
			
			global.metaNodes.add(module1);
			global.metaNodes.add(module2);
			global.metaNodes.add(module3);
			global.metaNodes.add(module4);
			
			global = global.collapseMetaNodes();
			global.name = "modular";
			
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(global), "c:/datas/calzone/modules/test/modular.xgmml");*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
