package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import edu.rpi.cs.xgmml.GraphDocument;
import edu.rpi.cs.xgmml.GraphicNode;

public class TestXGMML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphDocument gr = null;
		try {
			gr = GraphDocument.Factory.parse(new File("/bioinfo/users/ebonnet/Binom/merged_all_nets_her2d6.xgmml"));
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphicNode nar[] = gr.getGraph().getNodeArray();
		
		int size = nar.length;
		System.out.println(size);
		
		
		try {
			
			FileWriter out = new FileWriter("/bioinfo/users/ebonnet/Binom/her2d6_codes.txt");


			for(int i=0;i<size;i++){

				GraphicNode xn = nar[i];
				String id = xn.getId();
				String lab = xn.getLabel();

				for(int j=0;j<xn.getAttArray().length;j++){
					String attname = xn.getAttArray(j).getName();
					String attvalue = xn.getAttArray(j).getValue();
					if (attname.equalsIgnoreCase("node_name"))
						//System.out.println(lab+"\t"+attname+"\t"+attvalue);
						out.write(id+"\t"+lab+"\t"+attname+"\t"+attvalue+"\n");
				}
			}
			
			out.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
