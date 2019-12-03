package fr.curie.BiNoM.celldesigner.sample;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;

/**
 * 
 * SamplePlugin
 * 
 * This plugin opens a simple dialog.
 * In the dialog, you can get the informations of a selected species by clicking "GET" button.
 * You can also add a new species (GENERIC Protein) by inputting the information in the 
 *  textbox and then clicking "ADD" button. 
 * 
 * @author The Systems Biology Institute and Mitsui Knowledge Industry Co. Ltd.
 *
 */
public class SamplePlugin extends CellDesignerPlugin {

    PluginMenuItem item;
	
    /**
     * 
     */
    public SamplePlugin(){
	PluginMenu menu  = new PluginMenu("BiNoM Test");
	SampleAction action = new SampleAction(this);
	item = new PluginMenuItem("Display Part of the Pathway", action);
	menu.add(item);
	addCellDesignerPluginMenu(menu);
    }
	
	
    public void addPluginMenu() {
	System.out.println("addPluginMenu");
    }

    public void SBaseAdded(PluginSBase sbase) {
	System.out.println("SBaseAdded");

    }

    public void SBaseChanged(PluginSBase sbase) {
	System.out.println("SBaseChanged");

    }

    public void SBaseDeleted(PluginSBase sbase) {
	System.out.println("SBaseDeleted");
    }

    public void modelOpened(PluginSBase sbase) {
	System.out.println("modelOpened");
    }

    public void modelSelectChanged(PluginSBase sbase) {
	System.out.println("modelSelectChanged");
    }

    public void modelClosed(PluginSBase sbase) {
	System.out.println("modelClosed");
    }
}
