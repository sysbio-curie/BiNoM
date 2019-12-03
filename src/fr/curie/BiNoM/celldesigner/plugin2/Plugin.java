/* $Id$
 * Stuart Pook for the Insitut Curie, Copyright (c) 2009
 */
/*
jars=\
${HOME}/projects/BiNoM/binomlibfull.jar:\
${HOME}/projects/BiNoM/cd4.jar:\
/usr/local/CellDesigner/exec/celldesigner.jar:\
/usr/local/CellDesigner/lib/SBWCore.jar:\
/bioinfo/users/spook/pkg/Linux/libsbml-3.4.1-expat/lib/libsbmlj.jar

readonly classes=$(mktemp -d)
javac -target 1.5 -cp $jars:. -d $classes fr/curie/BiNoM/celldesigner/plugin2/Plugin.java
jar cf plugin.jar -C $classes .
 */

package fr.curie.BiNoM.celldesigner.plugin2;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import fr.curie.BiNoM.biopax.AbstractBioPAXImportTaskFactory;
import fr.curie.BiNoM.lib.AbstractTask;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter.Option;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

import jp.sbi.celldesigner.MainWindow;
import jp.sbi.celldesigner.SBFactory;
import jp.sbi.celldesigner.SBMLFiler;
import jp.sbi.celldesigner.SBModelFrame;
import jp.sbi.celldesigner.plugin.PluginModel;

public class Plugin extends jp.sbi.celldesigner.plugin.CellDesignerPlugin {

    static String readWithStringBuilder(java.io.Reader fr) throws java.io.IOException {
	java.io.BufferedReader br = new java.io.BufferedReader(fr);
	String line;
	StringBuilder result = new StringBuilder();
	while ((line = br.readLine()) != null) {
	    result.append(line);
	}
	return result.toString();
    }
    
    private final AbstractBioPAXImportTaskFactory tasks = new AbstractBioPAXImportTaskFactory()
    {
	public AbstractTask createTask(final File file, final URL url, final String fname, int[] algos, final Option option, boolean applyLayout) {
//	    System.err.println("createTask " + file.toString() + " " + url + " " + fname);
	    return new AbstractTask() {
		public void execute() {
		    final String name = getClass().getName() + ".execute";
		    System.err.println("execute " + file.toString() + " " + url + " " + fname + " " + option);
		    
		    final MainWindow lastInstance = jp.sbi.celldesigner.MainWindow.getLastInstance();
		    if (lastInstance == null) {
			System.err.println(name + ": no lastInstance");
			return;
		    }
		    
		    if (false)
		    {	// other attempts
			lastInstance.getAction(jp.sbi.celldesigner.MainWindow.NewAction.class).actionPerformed(null);
			lastInstance.openModelFromString((PluginModel)null);
		    }
		    
		    final BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();
		    b2s.biopax.model = com.hp.hpl.jena.rdf.model.ModelFactory.createDefaultModel();
		    try {
			b2s.biopax.loadBioPAX(file.getAbsolutePath());
			b2s.populateSbml();
		    } catch (Exception e) {
			System.err.println(name + ": loadBioPAX/populateSbml failed " + e);
			e.printStackTrace();
			return;
		    }
		    final File tmp;
		    try {
			tmp = File.createTempFile("BiNoMPlug", ".xml");
		    } catch (Exception e) {
			System.err.println(name + ": exception creating a temporary file " + e);
			return;
		    }
		    try {
			CellDesigner.saveCellDesigner(b2s.sbmlDoc, tmp.getAbsolutePath());
			System.err.println("created " + tmp.getAbsolutePath());
			final java.io.FileReader tmpfile;
			try {
			    tmpfile = new java.io.FileReader(tmp);
			} catch (FileNotFoundException e) {
			    System.err.println(name + ": exception reopening temporary file " + e);
			    return;
			}
			final String contents;
			try {
			    contents = readWithStringBuilder(tmpfile);
			} catch (java.io.IOException e) {
			    e.printStackTrace();
			    return;
			}
			try {
			    lastInstance.doAnalysis(contents);
			} catch (edu.caltech.sbw.SBWApplicationException e) {
			    System.err.println(name + ": SBWApplicationException read temporary file " + e);
			    e.printStackTrace();
			    return;
			}
			System.err.println("read " + tmp.getAbsolutePath());
		    }
		    finally {
			tmp.delete();
		    }
		}
	    };
	}
    };


    static private SBFactory factory = new SBFactory();
    private SBMLFiler filer = new SBMLFiler(factory);

    public Plugin() {
	jp.sbi.celldesigner.plugin.PluginMenu menu = new jp.sbi.celldesigner.plugin.PluginMenu("BiNoM");
	jp.sbi.celldesigner.plugin.PluginAction action = new jp.sbi.celldesigner.plugin.PluginAction() {
	    private static final long serialVersionUID = 4134204391816169586L;

	    public void myActionPerformed(java.awt.event.ActionEvent arg0) {
		final String name = getClass().getName() + ".myActionPerformed";

		final MainWindow lastInstance = jp.sbi.celldesigner.MainWindow.getLastInstance();
		if (lastInstance == null) {
		    System.err.println(name + ": no lastInstance");
		    return;
		}
		final SBModelFrame currentModel = lastInstance.getCurrentModel();
		if (currentModel == null) {
		    System.err.println(name + ": no currentModel");
		    return;
		}
		
		final File currentPath = currentModel.getPath();
		String[] filters = { "owl" };
		final java.io.File out = lastInstance.getExportFilePather().getUserSelection(
			currentModel.getMyFrame(),
			jp.fric.io.util.FilePather.SAVE,
			filters,
			"BioPAX files",
			((currentPath == null) ? "output" : currentPath.getName().replaceFirst("\\.[^.]*$", "")) + ".owl");

		if (out != null) {
		    final java.io.File tmp;
		    try {
			tmp = java.io.File.createTempFile("cdplug", ".xml");
		    } catch (java.io.IOException e) {
			System.err.println(name + ": exception creating a temporary file " + e);
			return;
		    }
		    if (tmp == null) {
			System.err.println(name + ": failed to create a temporary file");
			return;
		    }
		    
		    try {
			try {
			    filer.write(tmp, currentModel);
			} catch (Exception e) {
			    System.err.println(name + ": failed to write the model to the temporary file " + tmp.getPath() + ": " + e);
			    return;
			}
			fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter cd2bp = new fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter();
			cd2bp.sbml = fr.curie.BiNoM.pathways.wrappers.CellDesigner.loadCellDesigner(tmp.getPath());
			cd2bp.biopax = new fr.curie.BiNoM.pathways.wrappers.BioPAX();
			cd2bp.convert();
			fr.curie.BiNoM.pathways.wrappers.BioPAX.saveToFile(out.getPath(), cd2bp.biopax.biopaxmodel);
		    } finally {
			tmp.delete();
		    }

		}
	    }
	};
	menu.add(new jp.sbi.celldesigner.plugin.PluginMenuItem("export to BioPAX", action));
	addCellDesignerPluginMenu(menu);
	jp.sbi.celldesigner.plugin.PluginAction action2 = new jp.sbi.celldesigner.plugin.PluginAction() {
	    private static final long serialVersionUID = -7618658828852160084L;
	    public void myActionPerformed(ActionEvent e) {
		final MainWindow lastInstance = jp.sbi.celldesigner.MainWindow.getLastInstance();
		final SBModelFrame currentModel = lastInstance.getCurrentModel();
		final File currentPath = currentModel.getPath();
		String[] filters = { "owl" };
		final java.io.File in = lastInstance.getExportFilePather().getUserSelection(
			currentModel.getMyFrame(),
			jp.fric.io.util.FilePather.OPEN, filters,
			"BioPAX files",
			((currentPath == null) ? "input" : currentPath.getName().replaceFirst("\\.[^.]*$", "")) + ".owl");

		if (in != null) {
		    fr.curie.BiNoM.biopax.BioPAXImportDialog.getInstance().raise(tasks, in, in.getName());
		}
	    }
	};
	menu.add(new jp.sbi.celldesigner.plugin.PluginMenuItem("import from BioPAX", action2));
   }

    public void SBaseAdded(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

    public void SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

    public void SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

    public void addPluginMenu() {
    }

    public void modelClosed(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

    public void modelOpened(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

    public void modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase arg0) {
    }

}
