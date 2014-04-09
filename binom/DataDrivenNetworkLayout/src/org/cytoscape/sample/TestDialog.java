package org.cytoscape.sample;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class TestDialog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> myList = new ArrayList<String>();
		myList.add("one");
		myList.add("two");
		
		SelectColumnsDialog d = new SelectColumnsDialog(new JFrame(),"test",true);
		d.setDialogData(myList);
		d.setVisible(true);
	}

}
