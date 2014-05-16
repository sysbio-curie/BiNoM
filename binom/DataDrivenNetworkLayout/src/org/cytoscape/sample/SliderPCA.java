
package org.cytoscape.sample;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

/*
 * SliderDemo.java requires all the files in the images/doggy
 * directory.
 */
public class SliderPCA extends JPanel
                        implements ActionListener,
                                   WindowListener,
                                   ChangeListener {
    //Set up animation parameters.
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 100;
    static final int FPS_INIT = 99;    //initial frames per second
    int frameNumber = 0;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

   
    int delay;
    Timer timer;
    boolean frozen = false;
   

    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;

    public SliderPCA() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

      

        //Create the label.
        JLabel sliderLabel = new JLabel("Percentage of initial layout", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Create the slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
        framesPerSecond.addChangeListener(this);

        //Turn on labels at major tick marks.
        framesPerSecond.setMajorTickSpacing(20);
        framesPerSecond.setMinorTickSpacing(5);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,3,0));
    
        

        //Put everything together.
        add(sliderLabel);
        add(framesPerSecond);
       
        

     
    }

    /** Add a listener for window events. */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    public void windowIconified(WindowEvent e) {
        
    }
    public void windowDeiconified(WindowEvent e) {
        
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    /** Listen to the slider. 
     * @return */
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (source.getValueIsAdjusting()) {
        	
         Double s = (double)source.getValue()/100;
           TransitionalLayout.getInstance().doMyLayout (s);
            
          
        }
    }

    

    //Called when the Timer fires.
    public void actionPerformed(ActionEvent e) {
      
    }

 

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	
	public  void createAndShowGUI() {
		 

        //Create and set up the window.
        JFrame frame = new JFrame("Slider");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Create and set up the content pane.
        SliderPCA animator = new SliderPCA();
        animator.setOpaque(true); //content panes must be opaque
        frame.setContentPane(animator);
        frame.setLocation((screenSize.width - getSize().width) / 2,
				(screenSize.height - getSize().height) / 2);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
                SliderPCA slider= new SliderPCA();
                slider.createAndShowGUI();
            }
            
            
        });
        
       
    }
}
