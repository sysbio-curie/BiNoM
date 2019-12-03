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

package fr.curie.BiNoM.biopax;

import fr.curie.BiNoM.cytoscape.lib.*;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;

import edu.rpi.cs.xgmml.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.net.URL;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.lib.AbstractTask;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class BioPAXImportBaseTask {

    protected String bioPAXName;
    protected BioPAXToCytoscapeConverter.Option bioPAXOption;
    protected int bioPAXAlgos[];
    protected File file;
    protected URL url;
    protected boolean applyLayout;

    protected BioPAXImportBaseTask(File file, URL url, String name,
				   int algos[],
				   BioPAXToCytoscapeConverter.Option option,
				   boolean applyLayout) {

	this.file = file;
	this.url = url;

        this.bioPAXAlgos = algos;
	int idx = name.lastIndexOf('.');
	if (idx >= 0) {
	    name = name.substring(0, idx);
	}

	idx = name.lastIndexOf('/');
	if (idx >= 0) {
	    name = name.substring(idx+1, name.length());
	}

        this.bioPAXName = name;
	this.bioPAXOption = option;
	this.applyLayout = applyLayout;
    }

    protected BioPAXToCytoscapeConverter.Graph makeGraph(int which) throws Exception {
	if (bioPAXAlgos[which] == 0) {
	    return null;
	}

	InputStream is;
	BioPAXToCytoscapeConverter.Graph graph;

	if (url != null) {
	    is = url.openStream();

	    graph = BioPAXToCytoscapeConverter.convert
		(bioPAXAlgos[which],
		 is,
		 makeName(bioPAXAlgos[which], bioPAXName),
		 bioPAXOption);
	}
	else {
	    is = new FileInputStream(file); // to test if file exists

	    graph = BioPAXToCytoscapeConverter.convert
		(bioPAXAlgos[which],
		 file.getAbsolutePath(),
		 makeName(bioPAXAlgos[which], bioPAXName),
		 bioPAXOption);

	}

	is.close();
	return graph;
    }

    public String makeName(int algo, String name) {
	if (algo == BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION)
	    return name + " RN";

	if (algo == BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION)
	    return name + " PS";

	if (algo == BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION)
	    return name + " PP";

	return name + " XX";
    }
}
