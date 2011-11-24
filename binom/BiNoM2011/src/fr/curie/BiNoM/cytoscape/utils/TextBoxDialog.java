package fr.curie.BiNoM.cytoscape.utils;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *  Modal dialog displaying text, allowing to copy it in clipboard, displaying a message and proposing a choice 
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class TextBoxDialog extends GridBagDialog implements ActionListener,ClipboardOwner{
	private static final long serialVersionUID = 1L;
	final static int cx[]={0,0,0,1,2};
	final static int cy[]={0,1,2,2,2};
	final static int cw[]={3,3,1,1,1};
	final static int ch[]={1,1,1,1,1};
	final static int xw[]={1,8,1,1,1};
	final static int yw[]={0,8,0,0,0} ;
	final static int cf[]={B,B,H,H,H};
	final int width=320;
	final int height=320;
	private boolean clickYes;
	private JTextArea mess;
	private JTextArea dtext;
	private JButton copyButton,yesButton,noButton ;
	public TextBoxDialog(JFrame parent,String title,String message, String text){
		super(parent,title,true,cx,cy,cw,ch,xw,yw,cf);
		setSize(width,height);
		container=getContentPane();
		container.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		mess=new JTextArea(message);
		addWithConstraints(0,mess);
		dtext=new JTextArea(text);
		addWithConstraints(1,new JScrollPane(dtext));
		copyButton = new JButton ("Copy whole to Clipboard");
		addWithConstraints(2,copyButton);		
		copyButton.addActionListener(this);
		yesButton = new JButton ("Yes");
		addWithConstraints(3,yesButton);
		yesButton.addActionListener(this);
		noButton = new JButton ("No");
		addWithConstraints(4,noButton);
		noButton.addActionListener(this);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){clickYes=false;dispose();}});
	}
	public void actionPerformed (ActionEvent e){
		if (e.getSource()==copyButton) setClipboardContents(dtext.getText());
		if (e.getSource()==yesButton){
			clickYes=true;
			dispose();
		}
		if (e.getSource()==noButton){
			clickYes=false;
			dispose();
		}
	}
	public boolean getYN(){return clickYes;}
	public void setClipboardContents(String aString){
	    StringSelection stringSelection = new StringSelection(aString);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(stringSelection, this);
	  }
	public void lostOwnership(Clipboard aClipboard, Transferable aContents) {}
}
