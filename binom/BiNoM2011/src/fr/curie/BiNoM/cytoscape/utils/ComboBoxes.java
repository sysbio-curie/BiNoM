package fr.curie.BiNoM.cytoscape.utils;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * A dialog box to select values of different list of strings
 * Fields displays in comboboxes values to be selected
 * Field index corresponds to datas index
 * datas contains lists of values
 * The result got by launch is at index 0 the selected field and
 * at index 1 to n (label size - 1) the values selected in comboboxes
 * selected is the field selected when launching dialog
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ComboBoxes extends JDialog implements ActionListener,ItemListener {
	private static final long serialVersionUID = 1L;
	Container container;
	final int width=360;
	final int heightByLine=32;
	private JButton okBouton,cancelBouton;
	private boolean ok=false;
	JComboBox<String>[] comboBoxes;
	ArrayList<String> fields;
	ArrayList<ArrayList<String>> datas;
	public ComboBoxes(JFrame parent,String title,String[] label,ArrayList<String> fields,String selected,ArrayList<ArrayList<String>> datas){ 
		super(parent,title,true);
		this.fields=fields;
		this.datas=datas;
		setSize(width,heightByLine*(label.length+1));
		container=getContentPane();
		container.setLayout(new GridLayout(label.length+1,2));
		comboBoxes=new JComboBox[label.length];
		container.add(new JLabel(label[0]));
		comboBoxes[0]=new JComboBox<String>();
		container.add(comboBoxes[0]);
		fill(comboBoxes[0],fields);
		comboBoxes[0].setSelectedItem(selected);
		comboBoxes[0].addItemListener(this);
		int fi=fields.indexOf(selected);
		for(int i=1;i<label.length;i++){
			container.add(new JLabel(label[i]));
			comboBoxes[i]=new JComboBox<String>();
			container.add(comboBoxes[i]);
			fill(comboBoxes[i],datas.get(fi));
		}
		okBouton = new JButton ("OK") ;
		container.add(okBouton);		
		okBouton.addActionListener(this) ;
		cancelBouton = new JButton ("Cancel") ;
		container.add(cancelBouton) ;
		cancelBouton.addActionListener(this);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){dispose();}});
	}
	public boolean launchDialog(ArrayList<String> data){
		ok=false;
		setVisible(true);
		if(ok){			
			data.add(((String)comboBoxes[0].getSelectedItem()));
			for(int i=1;i<comboBoxes.length;i++) data.add((String)(comboBoxes[i].getSelectedItem()));
		}
		return ok;
	}
	void fill(JComboBox<String> comboBox,ArrayList<String> data){
		comboBox.removeAllItems();
		for(int i=0;i<data.size();i++) comboBox.addItem(data.get(i));
	}
	public void itemStateChanged(ItemEvent e){
		for(int i=1;i<comboBoxes.length;i++) fill(comboBoxes[i],datas.get(fields.indexOf((String)(comboBoxes[0].getSelectedItem()))));
	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource()==okBouton){
			ok=true;
			dispose();
		}
		if (e.getSource()==cancelBouton) dispose();
	}
}
