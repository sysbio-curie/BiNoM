package fr.curie.BiNoM.cytoscape.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.utils.*;
import edu.rpi.cs.xgmml.*;

public class ListAllNodesDialog extends JDialog {

    private static final double COEF_X = 1.24, COEF_Y = 1.05;

    private static final java.awt.Font TITLE_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     14);
    private static final java.awt.Font BOLD_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     12);

    private static final java.awt.Font STD_BOLD_FONT = new java.awt.Font("times",
									 java.awt.Font.BOLD,
									 10);
    private static final java.awt.Font STD_FONT = new java.awt.Font("times",
								    java.awt.Font.PLAIN,
								    10);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    JTextArea textArea = null;
    
    GraphDocument _graphDoc = null;
    Graph _graph = null;    
    org.sbml.x2001.ns.celldesigner.SbmlDocument _sbmlobject = null;
    HashMap _sbmlObjectMap = null;
    BioPAX _biopaxobject = null;
    
    public boolean showOnlyEntityNames = false;
    public boolean showAlsoSynonyms = false;

    public void pop(GraphDocument grDoc, org.sbml.x2001.ns.celldesigner.SbmlDocument cd, BioPAX bp) {
    	
    	_graphDoc = grDoc;
    	_sbmlobject = cd;
    	if(cd!=null)
    		_sbmlObjectMap = CellDesigner.getEntities(cd);
    	_biopaxobject = bp;

    	build();

    	setLocation((screenSize.width - getSize().width) / 2,
                        (screenSize.height - getSize().height) / 2);
    	setVisible(true);
        }

    public void pop(Graph gr, org.sbml.x2001.ns.celldesigner.SbmlDocument cd, BioPAX bp) {
    	
    	_graph = gr;
    	_sbmlobject = cd;
    	if(cd!=null)
    		_sbmlObjectMap = CellDesigner.getEntities(cd);
    	_biopaxobject = bp;

    	build();

    	setLocation((screenSize.width - getSize().width) / 2,
                        (screenSize.height - getSize().height) / 2);
    	setVisible(true);
        }
    
    
    private void build() {
    	getContentPane().removeAll();

    	JPanel panel = new JPanel(new GridBagLayout());
    	
    	JPanel controlPanel = new JPanel(new GridBagLayout());
    	
    	

    	GridBagConstraints c;
    	
    	textArea = new JTextArea();
    	
    	panel.add(textArea);
   
    	textArea.setText(getTextAreaContent());
    	
     	JScrollPane jpane = new JScrollPane(textArea);

		jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);     	

		//getContentPane().add(controlPanel,BorderLayout.NORTH);
		getContentPane().add(jpane,BorderLayout.CENTER);    	
    	//getContentPane().add(jpane);

    	pack();
    	
    	Dimension size = getSize();
    	setSize(new Dimension((int)(600),
			      (int)(300)));

    }
    
    private String getTextAreaContent(){

    	BioPAXNamingService serv = new BioPAXNamingService();    	
		if(_biopaxobject!=null){
	    	try{
	    	serv.generateNames(_biopaxobject, false);
	    	}catch(Exception e){
	    		System.out.println("ERROR: in finding entity name");
	    		e.printStackTrace();
	    	}
	    }
    	
    	StringBuffer sb = new StringBuffer();
    	Vector sortedEntityNames = new Vector();

    	if(!showOnlyEntityNames)    	
    		sb.append("NODE_NAME\tNODE_TYPE\tENTITY_NAME\n");
    	
    	Graph graph = _graph;
    	if(graph==null)
    		graph = XGMML.convertXGMMLToGraph(_graphDoc);
    	for(int i=0;i<graph.Nodes.size();i++){
    		String type = "-";
    		String entity = "-";
    		Node n = ((Node)graph.Nodes.get(i));
    		if(n.getAttributesWithSubstringInName("NODE_TYPE")!=null){
    			if(((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).size()>0)
    				type = ((Attribute)((Vector)n.getAttributesWithSubstringInName("NODE_TYPE")).get(0)).value;
    		}
    		if(_biopaxobject!=null){
    	    	try{
    	    	serv.generateNames(_biopaxobject, false);
    			String uri = n.getFirstAttributeValue("BIOPAX_URI");
    			com.ibm.adtech.jastor.Thing th = biopax_DASH_level3_DOT_owlFactory.getThing(uri, _biopaxobject.biopaxmodel);
    			if(th!=null){
    				//System.out.println(th.getClass().getName());
    				//entity = serv.getEntityName(uri);
    				entity = serv.getNameByUri(uri);
    				if((entity==null)||entity.equals("")){
    					entity = "-";
    				}
    			}
    	    	}catch(Exception e){
    	    		System.out.println("ERROR: in finding entity name");
    	    		e.printStackTrace();
    	    	}
    		}
    		if(_sbmlobject!=null){
    			Vector v = n.getAttributesWithSubstringInName("_SPECIES");
    			if((v!=null)&&(v.size()>0)){
    				String id = ((Attribute)v.get(0)).value; 
    				entity = CellDesignerToCytoscapeConverter.convertSpeciesToName(_sbmlobject, id, false, false);
    			}
    		}
    		if(!showOnlyEntityNames)
    			sb.append(n.NodeLabel+"\t"+type+"\t"+entity+"\n");
    		else{
    			if(type.toLowerCase().equals("protein")||type.toLowerCase().equals("dna")){
    				Vector v = n.getAttributesWithSubstringInName("_SPECIES");
    				if((v==null)||(v.size()==0)){
    					if(type.equals("dna")){
    						String ss = n.NodeLabel+"\t"+"(gene)";
    						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
    						if(sortedEntityNames.indexOf(ss)<0)
    							sortedEntityNames.add(ss);
    					}
    					else{
    						String ss = n.NodeLabel;
    						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
    						if(sortedEntityNames.indexOf(n.NodeLabel)<0)
    							sortedEntityNames.add(n.NodeLabel);
    					}
    				}
    				if(showAlsoSynonyms){
    					v = n.getAttributesWithSubstringInName("SYNONYM");
    					for(int k=0;k<v.size();k++){
    						String ss = ((Attribute)v.get(k)).value;
    						ss = fr.curie.BiNoM.pathways.utils.Utils.replaceString(ss," ","_");
    						if(sortedEntityNames.indexOf(ss)<0)
    							sortedEntityNames.add(ss);
    					}
    				}
    			}
    		}
    	}
    	
    	if(showOnlyEntityNames){
    		Collections.sort(sortedEntityNames);
    		for(int i=0;i<sortedEntityNames.size();i++)
    			sb.append((String)sortedEntityNames.get(i)+"\n");
    	}
    	
    	//System.out.println(sb.toString());
    	return sb.toString();
    }
	
	
}
