package fr.curie.BiNoM.pathways.utils;

import java.util.*;

import fr.curie.BiNoM.pathways.parseBioPAX;
import fr.curie.BiNoM.pathways.parseCellDesigner;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import org.sbml.x2001.ns.celldesigner.*;

public class GraphUtils {
	
    public static String getListOfReactionsTable(Graph _graphDoc, BioPAX _biopaxobject, org.sbml.x2001.ns.celldesigner.SbmlDocument _sbmlobject) throws Exception{
    	StringBuffer sb = new StringBuffer();
    	sb.append("N\tREACTION\tTYPE\tEFFECT\tREACTION_ID\n");
    	Vector reactions = new Vector();
    	Vector reactionTypes = new Vector();
    	Vector reactionEffects = new Vector();
    	//Graph graph = XGMML.convertXGMMLToGraph(_graphDoc);
    	Graph graph = _graphDoc;

    	parseBioPAX pb = null;
    	
    	if(_biopaxobject!=null){
    		 pb = new parseBioPAX();
    	     pb.biopax = _biopaxobject; 
    	     pb.populateSbml();
    	}
    	
    	for(int i=0;i<graph.Nodes.size();i++){
    		fr.curie.BiNoM.pathways.analysis.structure.Node n = (fr.curie.BiNoM.pathways.analysis.structure.Node)graph.Nodes.get(i);
    		Vector v = n.getAttributesWithSubstringInName("NODE_TYPE");
    		Vector vr = n.getAttributesWithSubstringInName("_REACTION");    		
    		Vector vef = n.getAttributesWithSubstringInName("EFFECT");    		
    		if((vr!=null)&&(vr.size()>0)){
    			String type = "unknown";
    			String effect = "unknown";    			
    			if((v!=null)&&(v.size()>0))
    				type = ((Attribute)v.get(0)).value;
    			if((vef!=null)&&(vef.size()>0))
    				effect = ((Attribute)vef.get(0)).value;
    			reactions.add(n);
    			reactionTypes.add(type);
    			reactionEffects.add(effect);
    		}
    	}
    	for(int i=0;i<graph.Edges.size();i++){
    		fr.curie.BiNoM.pathways.analysis.structure.Edge n = (fr.curie.BiNoM.pathways.analysis.structure.Edge)graph.Edges.get(i);
        		Vector v = n.getAttributesWithSubstringInName("NODE_TYPE");
        		Vector vr = n.getAttributesWithSubstringInName("_REACTION");
        		Vector vef = n.getAttributesWithSubstringInName("EFFECT");
        		if((vr!=null)&&(vr.size()>0)){
        			String type = "unknown";
        			String effect = "unknown";    	        			
        			if((v!=null)&&(v.size()>0))
        				type = ((Attribute)v.get(0)).value;
        			if((vef!=null)&&(vef.size()>0))
        				effect = ((Attribute)vef.get(0)).value;
        			reactions.add(n);
        			reactionTypes.add(type);
        			reactionEffects.add(effect);        			
        		}
        }
    	for(int i=0;i<reactions.size();i++){
        	// Determining id
    		fr.curie.BiNoM.pathways.analysis.structure.Element el = (fr.curie.BiNoM.pathways.analysis.structure.Element)reactions.get(i);
    		String id = "unknown";
    		Vector attributes = el.getAttributeValues("BIOPAX_URI");
    		if((attributes!=null)&&(attributes.size()>0))
    			id = (String)attributes.get(0);
    		attributes = el.getAttributesWithSubstringInName("_REACTION");
    		if((attributes!=null)&&(attributes.size()>0))
    			id = ((Attribute)attributes.get(0)).value;
    		attributes = el.getAttributesWithSubstringInName("_ID");
    		if((attributes!=null)&&(attributes.size()>0))
    			id = ((Attribute)attributes.get(0)).value;
    		// Now getting reaction string
    		String reactionString = "-";
    		
			String interactionLabel = " -> ";
			String _effect = (String)reactionEffects.get(i);
			if(_effect.toLowerCase().indexOf("inhibition")>=0)
				interactionLabel = " -| ";
			if(_effect.toLowerCase().indexOf("repression")>=0)
				interactionLabel = " -| ";
			if(_effect.toLowerCase().indexOf("transport")>=0)
				interactionLabel = " -t-> ";
			if(_effect.toLowerCase().indexOf("transcriptional_activation")>=0)
				interactionLabel = " --> ";
			if(_effect.toLowerCase().indexOf("transcriptional_inhibition")>=0)
				interactionLabel = " --| ";
			String _type = (String)reactionTypes.get(i);
			if(_type.toLowerCase().indexOf("inhibition")>=0)
				interactionLabel = " -| ";
			if(_type.toLowerCase().indexOf("repression")>=0)
				interactionLabel = " -| ";
			if(_type.toLowerCase().indexOf("transport")>=0)
				interactionLabel = " -t-> ";
			if(_type.toLowerCase().indexOf("transcriptional_activation")>=0)
				interactionLabel = " --> ";
			if(_type.toLowerCase().indexOf("transcriptional_inhibition")>=0)
				interactionLabel = " --| ";
			if(_type.toLowerCase().indexOf("transcription")>=0)
				interactionLabel = " --> ";
    		
    		if((_biopaxobject==null)&&(_sbmlobject==null)){
    			graph.calcNodesInOut();
    			if(el instanceof fr.curie.BiNoM.pathways.analysis.structure.Edge){
    				fr.curie.BiNoM.pathways.analysis.structure.Edge e = (fr.curie.BiNoM.pathways.analysis.structure.Edge)el;
    				interactionLabel = " -> ";
    				String type = (String)reactionTypes.get(i);
    				if(type.toLowerCase().indexOf("inhibition")>=0)
    					interactionLabel = " -| ";
    				if(type.toLowerCase().indexOf("repression")>=0)
    					interactionLabel = " -| ";
    				reactionString = Utils.cutFinalAmpersand(e.Node1.Id)+interactionLabel+Utils.cutFinalAmpersand(e.Node2.Id);
    			}
    			if(el instanceof fr.curie.BiNoM.pathways.analysis.structure.Node){
    				String left = "";
    				String right = "";
    				fr.curie.BiNoM.pathways.analysis.structure.Node n = (fr.curie.BiNoM.pathways.analysis.structure.Node)el;
    				for(int j=0;j<n.incomingEdges.size();j++){
    					fr.curie.BiNoM.pathways.analysis.structure.Edge e = (fr.curie.BiNoM.pathways.analysis.structure.Edge)n.incomingEdges.get(j);
    					String type = getEdgeType(e);
    					boolean add = false;
    					if(type.toLowerCase().equals("left")) add = true;
    					if(add)
    						left+=Utils.cutFinalAmpersand(e.Node1.Id)+"+";
    				}
    				for(int j=0;j<n.outcomingEdges.size();j++){
    					fr.curie.BiNoM.pathways.analysis.structure.Edge e = (fr.curie.BiNoM.pathways.analysis.structure.Edge)n.outcomingEdges.get(j);
    					String type = getEdgeType(e);
    					boolean add = false;
    					if(type.toLowerCase().equals("right")) add = true;
    					if(add)
    						right+=Utils.cutFinalAmpersand(e.Node2.Id)+"+";
    				}
    				for(int j=0;j<n.incomingEdges.size();j++){
    					fr.curie.BiNoM.pathways.analysis.structure.Edge e = (fr.curie.BiNoM.pathways.analysis.structure.Edge)n.incomingEdges.get(j);
    					String type = getEdgeType(e);
    					boolean add = false;
    					if(type.toLowerCase().indexOf("catalysis")>=0) add = true;
    					if(type.toLowerCase().indexOf("inhibition")>=0) add = true;
    					if(type.toLowerCase().indexOf("activation")>=0) add = true;    					
    					if(add){
    						left+=Utils.cutFinalAmpersand(e.Node1.Id)+"+";
    						right+=Utils.cutFinalAmpersand(e.Node1.Id)+"+";
    					}
    				}
    				if(left.length()>0)
    					left=left.substring(0,left.length()-1);
    				if(right.length()>0)    				
    					right=right.substring(0,right.length()-1);
    				reactionString = left+interactionLabel+right;
    			}
    		}
    		if(_biopaxobject!=null){
    			reactionString = pb.reactionString(pb.sbmlDoc.getSbml(),id,true,true);
    			if(reactionString.indexOf("->")>=0){
    				String left = reactionString.substring(0,reactionString.indexOf("->"));
    				String right = reactionString.substring(reactionString.indexOf("->")+3,reactionString.length());
    				reactionString = left.trim()+interactionLabel+right.trim();
    			}
    		}
    		if(_sbmlobject!=null){
    			reactionString = parseCellDesigner.reactionString(_sbmlobject.getSbml(),id,true);
    		}
    		String eff = (String)reactionEffects.get(i);
    		if(eff.startsWith("EFFECT:"))
    			eff = eff.substring(7).trim();
    		sb.append((i+1)+"\t"+reactionString+"\t"+(String)reactionTypes.get(i)+"\t"+eff+"\t"+id+"\n");
    	}
    	
    	return sb.toString();
    }
    
    public static String getEdgeType(fr.curie.BiNoM.pathways.analysis.structure.Edge e){
    	String res = "";
    	Vector v = e.getAttributesWithSubstringInName("EDGE_TYPE");
    	if((v!=null)&&(v.size()>0))
    		res = ((Attribute)v.get(0)).value;
    	return res;
    }
    
    public static org.sbml.x2001.ns.celldesigner.SbmlDocument convertFromListOfReactionsToSBML(Vector reactions){
    	org.sbml.x2001.ns.celldesigner.SbmlDocument sbml = org.sbml.x2001.ns.celldesigner.SbmlDocument.Factory.newInstance();
    	sbml.addNewSbml();
    	sbml.getSbml().setLevel("2");
    	sbml.getSbml().setVersion("1");
    	AnnotationDocument.Annotation ann = sbml.getSbml().addNewModel().addNewAnnotation();
    	sbml.getSbml().getModel().setId("noname");
    	sbml.getSbml().getModel().addNewListOfReactions();
    	sbml.getSbml().getModel().addNewListOfSpecies();
    	CompartmentDocument.Compartment comp = sbml.getSbml().getModel().addNewListOfCompartments().addNewCompartment();
    	comp.setId("default");
    	Utils.setValue(comp.addNewName(),"default");
    	String annotationString = "\n\n\n";
    	HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
    	for(int i=0;i<reactions.size();i++){
    		String reaction = (String)reactions.get(i);
    		annotationString+="re"+(i+1)+")\t"+reaction+"\t,\t\n";
    		String delimiters[] = {"->","-|","-t->","-->","--|","<->"};
    		String left = "";
    		String right = "";
    		String delimiter = "";
    		for(int j=0;j<delimiters.length;j++){
    			int k = reaction.indexOf(delimiters[j]);
    			if(k>=0){
    				left = reaction.substring(0,k).trim();
    				right = reaction.substring(k+delimiters[j].length(),reaction.length()).trim();
    				delimiter = delimiters[j];
    			}
    		}
    		Vector reactants = new Vector();
    		Vector products = new Vector();
    		StringTokenizer st = new StringTokenizer(left,"+");
    		while(st.hasMoreTokens())
    			reactants.add(st.nextToken());
    		st = new StringTokenizer(right,"+");
    		while(st.hasMoreTokens())
    			products.add(st.nextToken());

    		ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().addNewReaction();
    		r.setId("re"+(i+1));
    		NotesDocument.Notes note = r.addNewNotes(); Utils.setValue(note, Utils.correctHtml(reaction));
    		if(delimiter.equals("<->"))
    			r.setReversible("true");
    		else
    			r.setReversible("false");
    		r.addNewListOfReactants();
    		r.addNewListOfProducts();
    		r.addNewListOfModifiers();
    		
    		for(int j=0;j<reactants.size();j++){
    			String react = (String)reactants.get(j);
    			if(products.indexOf(react)<0){
    				SpeciesDocument.Species sp = species.get(react);
    				if(sp==null){
    					sp = sbml.getSbml().getModel().getListOfSpecies().addNewSpecies();
    					sp.setId(correctId(react));
    					sp.setCompartment("default");
    					Utils.setValue(sp.addNewName(),react);
    					species.put(react, sp);
    				}
    				SpeciesReferenceDocument.SpeciesReference spr = r.getListOfReactants().addNewSpeciesReference();
    				spr.setSpecies(sp.getId());
    			}else{
    				SpeciesDocument.Species sp = species.get(react);
    				if(sp==null){
    					sp = sbml.getSbml().getModel().getListOfSpecies().addNewSpecies();
    					sp.setId(correctId(react));
    					sp.setCompartment("default");
    					Utils.setValue(sp.addNewName(),react);
    					species.put(react, sp);
    				}
    				ModifierSpeciesReferenceDocument.ModifierSpeciesReference spr = r.getListOfModifiers().addNewModifierSpeciesReference();
    				spr.setSpecies(sp.getId());
    			}
    		}
    		for(int j=0;j<products.size();j++){
    			String prod = (String)products.get(j);
    			if(reactants.indexOf(prod)<0){
    				SpeciesDocument.Species sp = species.get(prod);
    				if(sp==null){
    					sp = sbml.getSbml().getModel().getListOfSpecies().addNewSpecies();
    					sp.setId(correctId(prod));
    					sp.setCompartment("default");
    					Utils.setValue(sp.addNewName(),prod);
    					species.put(prod, sp);
    				}
    				SpeciesReferenceDocument.SpeciesReference spr = r.getListOfProducts().addNewSpeciesReference();
    				spr.setSpecies(sp.getId());
    			}
    		}
    	}
    	Utils.setValue(ann, Utils.correctHtml(annotationString));
    	return sbml;
    }
    
    public static String correctId(String s){
    	//System.out.println("BEFORE: "+s);
    	s = Utils.replaceString(s, "@", "_at_");
    	s = Utils.replaceString(s, ":", "_");
    	s = Utils.replaceString(s, "|", "_");
        char c[] = s.toCharArray();
        for(int i=0;i<c.length;i++){
        	if((c[i]>='a')&&(c[i]<='z')) continue;
        	if((c[i]>='A')&&(c[i]<='Z')) continue;
        	if((c[i]>='0')&&(c[i]<='9')) continue;
        	if(c[i]=='_') continue;
        	c[i] = '_';
        }
        s = new String(c);
        if((c[0]>='0')&&(c[0]<='9'))
        	s = "id_"+s;
        //System.out.println("AFTER : "+s);
    	return s;
    }
    
    public static Vector determineRealValuedAttributes(Graph graph){
    	Vector atts = new Vector();
    	for(int i=0;i<graph.Nodes.size();i++){
    		Node n = (Node)graph.Nodes.get(i);
    		for(int j=0;j<n.Attributes.size();j++){
    			Attribute att = (Attribute)n.Attributes.get(j);
    			if(atts.indexOf(att.name)<0)
    				if(att.value!=null)if(!att.value.trim().equals(""))
    					atts.add(att.name);
    		}
    	}
    	System.out.println(atts.size()+" attributes found");
    	//for(int i=0;i<atts.size();i++)
    	//	System.out.println(atts.get(i));
    	for(int i=0;i<graph.Nodes.size();i++){
    		Node n = (Node)graph.Nodes.get(i);
    		for(int j=0;j<n.Attributes.size();j++){
    			Attribute att = (Attribute)n.Attributes.get(j);
    			if(atts.indexOf(att.name)>=0){
    			try{
    				double d = 0;
    				if(att.value!=null)if(!att.value.trim().equals(""))
    					d = Double.parseDouble(att.value);
    			}catch(Exception e){
    				int k = atts.indexOf(att.name);
    				atts.remove(k);
    				System.out.println(att.name+"->'"+att.value+"'");
    			}
    			}
    		}
    	}
    	return atts;
    }


}
