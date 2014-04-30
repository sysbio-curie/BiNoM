package org.cytoscape.sample;



import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TestDialog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> myList = new ArrayList<String>();
		myList.add("one");
		myList.add("two");
		
		SelectColumnsDialog d = new SelectColumnsDialog(new JFrame(),"Options",true);
		d.setDialogData(myList);
		d.setVisible(true);
		
		System.out.println("res:");
		for (String str : d.myselList)
			System.out.println(str);
		
		int index = 0;
		if (d.pc1.isSelected()){index++;}
		if (d.pc2.isSelected()){index++;}
		if (d.pc3.isSelected()){index++;}
		
		if (d.pc4.isSelected()){index++;}
		System.out.println(index);
		if (index!=2){
			JOptionPane.showMessageDialog(null, "Chose exacly 2 principal components","ERROR", JOptionPane.WARNING_MESSAGE);
			}
	
	}
	

}
