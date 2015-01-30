package fr.curie.BiNoM.pathways.navicell;

import java.util.HashMap;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class GraphNeighbours {

	Graph reactionGraph;
	HashMap<String, Vector<String> > speciesId2name;
	HashMap<String, String> name2speciesId;
	Graph entityGraph;
	HashMap<String, String> entityId2name;
	HashMap<String, String> name2entityId;

	GraphNeighbours(SbmlDocument sbml) {
		CellDesigner.entities = CellDesigner.getEntities(sbml);
			
		reactionGraph = FindNeighbours.getReactionGraph(sbml);
		speciesId2name = FindNeighbours.mapSpeciesId2name(reactionGraph);
		name2speciesId = FindNeighbours.mapName2SpeciesId(reactionGraph);
		entityGraph = FindNeighbours.getEntityGraph(reactionGraph);
		entityId2name = FindNeighbours.mapEntityId2Name(sbml);
		name2entityId = FindNeighbours.mapName2EntityId(sbml);
	}

	Vector<String> getEntityNeighbours(String id) {
		return FindNeighbours.getEntityGraphNeighbours(entityGraph, entityId2name, name2entityId, id);
	}

	Vector<String> getSpeciesNeighbours(SpeciesDocument.Species sp) {
		String sp_class = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
		String spid = sp.getId();
		if (!sp_class.equals("DEGRADED")) {
			return FindNeighbours.getReactionGraphNeighbourReactions(reactionGraph, speciesId2name, name2speciesId, spid);
		}
		return new Vector<String>();
	}
}
