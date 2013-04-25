package fr.curie.BiNoM.cytoscape.utils;

import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * About BiNoM box.
 * 
 * @author ebo
 *
 */
public class AboutBox implements ActionListener {
	public void actionPerformed(ActionEvent e) { 
		JDialog dialog = new JDialog();

		JPanel content = new JPanel();
		dialog.setContentPane(content);
		content.setBackground(new java.awt.Color(255, 255, 255));
		JLabel backgroundLabel = new JLabel();
		// get image from jar 
		backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/data/binom_logo1.png")));
		backgroundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		content.add(backgroundLabel);
		
		// add content
		addALabel("---", content);
		addALabel("BiNoM version 2.3",content);
		addALabel("Copyright (c) 2006 Institut Curie",content);
		addALabel("License: LGPL version 2.1",content);
		addALabel("---", content);
		addALabel("Contributors & Developers", content);
		addALabel("---", content);
		addALabel("Eric Bonnet",content);
		addALabel("Laurence Calzone",content);
		addALabel("Daniel Rovera",content);
		addALabel("Gautier Stoll",content);
		addALabel("Paola Vera-Licona",content);
		addALabel("Stuart Pook",content);
		addALabel("Eric Viara",content);
		addALabel("Emmanuel Barillot",content);
		addALabel("Andrei Zinovyev",content);
		addALabel("---", content);
		addALabel("http://binom.curie.fr/", content);
		addALabel("---", content);
		dialog.setTitle("About BiNoM");
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.show();
		dialog.dispose();
	}

	private static void addALabel(String text, Container container) {
		JLabel lab = new JLabel(text);
		lab.setFont(new java.awt.Font("Courier", 1, 12));
		lab.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(lab);
	}

}
