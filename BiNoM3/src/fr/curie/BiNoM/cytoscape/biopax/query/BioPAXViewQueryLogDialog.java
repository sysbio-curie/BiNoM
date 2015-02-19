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
package fr.curie.BiNoM.cytoscape.biopax.query;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class BioPAXViewQueryLogDialog extends JFrame {

    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private JTextArea textArea = null;

    private static final double COEF_X = 1.24, COEF_Y = 1.10;

    private BioPAXViewQueryLogDialog() {
        super("View Query Log");
	//	JPanel panel = new JPanel(new GridBagLayout());
	//JPanel panel = new JPanel();
	int y = 0;

	textArea = new JTextArea();
	textArea.setColumns(50);
	textArea.setRows(30);
	textArea.setLineWrap(false);
	textArea.setSize(textArea.getPreferredSize());
	
	//panel.add(textArea);
	
	//JScrollPane jScrollPane1 = new JScrollPane(textArea);
	
	
	JPanel buttonPanel = new JPanel();

	okB = new JButton("Clear");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				    textArea.setText("");
				    BioPAXIndexRepository.getInstance().setReport("");
                    //setVisible(false);
                }
           });

	cancelB = new JButton("Close");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
           });

	buttonPanel.add(okB);
	buttonPanel.add(cancelB);

        // for skeleton
	//panel.setPreferredSize(new Dimension(100, 100));

	getContentPane().setLayout(new BorderLayout());

	JScrollPane jpane = new JScrollPane(textArea);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    public static BioPAXViewQueryLogDialog instance;

    public static BioPAXViewQueryLogDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXViewQueryLogDialog();
	return instance;
    }

    public void raise() {

	Dimension size = getSize();
	//setSize(new Dimension((int)(size.width * COEF_X),
	//		      (int)(size.height * COEF_Y)));
	setSize(new Dimension(600,500));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
	textArea.setText(BioPAXIndexRepository.getInstance().getReport());
	System.out.println(BioPAXIndexRepository.getInstance().getReport());
     }
}

