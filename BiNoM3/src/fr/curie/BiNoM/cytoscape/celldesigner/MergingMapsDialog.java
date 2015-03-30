package fr.curie.BiNoM.cytoscape.celldesigner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;


public class MergingMapsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JFileChooser fc;
	private JCheckBox mergeImages;
	private JCheckBox mergeSpecies;
	private JTextField imageZoomLevel;
	private JLabel lab1;
	private JButton b1;
	private JButton b2;

	/**
	 * Constructor
	 * 
	 * @param frame a JFrame
	 * @param mess message 
	 * @param modal is the window modal?
	 */
	public MergingMapsDialog(){
		super(new JFrame(),"Merging maps parameters");
		init();
	}
	
	private void init() {
		
		setSize(new Dimension(550,300));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println(getWidth()+" "+getHeight());
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	    
		/*
		 *  Create a swing file chooser object
		 */
		fc = new JFileChooser();
		//fc.addChoosableFileFilter(new MyFilter());

		final JPanel p = new JPanel(new GridBagLayout());
		
		/*
		 * select config file button
		 */
		b1 = new JButton("Select configuration file");
		b1.setPreferredSize(new Dimension(200,20));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(b1,c);

		final JTextField field1 = new JTextField(30);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(field1,c);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showDialog(MergingMapsDialog.this, "Select");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					System.out.println("config file: "+f.getAbsolutePath());
					field1.setText(f.getAbsolutePath());
				}
			}
		});

		/*
		 * Select output file button
		 */
		b2 = new JButton("Select output file");
		b2.setPreferredSize(new Dimension(200,20));
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(b2,c);

		final JTextField field2 = new JTextField(30);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(field2,c);

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.addChoosableFileFilter(new FileFilter() {
		            // Handles which files are allowed by filter.
		            @Override
		            public boolean accept(File f) {
		                
		                // Allow directories to be seen.
		                if (f.isDirectory()) return true;

		                // Allows files with .rtf extension to be seen.
		                if (f.getName().toLowerCase().endsWith(".xml"))
		                    return true;

		                // Otherwise file is not shown.
		                return false;
		            }

		            // 'Files of Type' description
		            @Override
		            public String getDescription() {
		                return "*.xml";
		            }
		        });

		        fc.setAcceptAllFileFilterUsed(false);

				int ret = fc.showSaveDialog(MergingMapsDialog.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					System.out.println("save file: "+f.getAbsolutePath());
					field2.setText(f.getAbsolutePath());
				}
			}
		});
		
		
		mergeImages = new JCheckBox("Merge map images");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(mergeImages,c);
		
		lab1 = new JLabel("at zoom level");
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10,10,10,10);
		p.add(lab1,c);

		imageZoomLevel = new JTextField(2);
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(imageZoomLevel,c);
		
		mergeSpecies = new JCheckBox("Merge species");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		mergeSpecies.setSelected(true);
		p.add(mergeSpecies,c);
		
		final MergingMapsTask.MergingMapsOptions options = new MergingMapsTask(null,null,null).new MergingMapsOptions();
		
		mergeImages.setSelected(options.mergeImages);
		imageZoomLevel.setText(""+options.zoomLevel);
		
		/*
		 *  Action: Ok - Cancel buttons
		 */
		JPanel buttonPanel = new JPanel();
		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            setVisible(false);			
	            options.mergeImages = mergeImages.isSelected();
	            options.zoomLevel = Integer.parseInt(imageZoomLevel.getText());
	            options.mergeSpecies = mergeSpecies.isSelected();
	            
	            TaskIterator t = new TaskIterator(new MergingMapsTask(field1.getText(), field2.getText(), options));
	    		Launcher.getAdapter().getTaskManager().execute(t);
			}
		});
		buttonPanel.add(okB);

		JButton cancelB = new JButton("Cancel");
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonPanel.add(cancelB);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p,BorderLayout.CENTER);
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		pack();
	}
	
}
