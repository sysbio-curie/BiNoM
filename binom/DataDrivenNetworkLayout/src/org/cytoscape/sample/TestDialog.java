

package org.cytoscape.sample;

import java.util.ArrayList;

import javax.swing.JFrame;

public class TestDialog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> myList = new ArrayList<String>();
		myList.add("one");
		myList.add("two");
		
		
		
		SelectColumnsDialog e = new SelectColumnsDialog(new JFrame(),"Options",true);
		e.setDialogData(myList);
		e.setVisible(true);
		
		SelectNetworksDialog d = new SelectNetworksDialog(new JFrame(),"Options",true);
		d.setDialogData(myList);
		d.setDialogData2(myList);
		d.setVisible(true);
		
		System.out.println("res:");
		for (String str : d.myselList)
			System.out.println(str);
		
	
	}
	

}
