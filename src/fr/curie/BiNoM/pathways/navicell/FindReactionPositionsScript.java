package fr.curie.BiNoM.pathways.navicell;

import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import java.io.File;

import org.sbml.x2001.ns.celldesigner.ModelDocument.Model;

import fr.curie.BiNoM.pathways.utils.Pair;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class FindReactionPositionsScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProduceClickableMap pcm = new ProduceClickableMap("test",new File(args[0]));
		
		SbmlDocument cd = CellDesigner.loadCellDesigner(args[0]);
		CellDesigner.entities = CellDesigner.getEntities(cd);
		Model model = cd.getSbml().getModel();
		
		if (model.getListOfReactions() != null) {
			for (final ReactionDocument.Reaction r : model.getListOfReactions().getReactionArray()) {
				Pair position = pcm.findCentralPlaceForReaction(r);
				System.out.println(r.getId()+"\t"+(Float)position.o1+"\t"+(Float)position.o2);
			}
		}

	}
	
}


