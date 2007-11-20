package org.jdesktop.xswingx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.xswingx.BuddySupport.Position;
import org.jdesktop.xswingx.demo.JXAddressField;
import org.jdesktop.xswingx.demo.LabelDemo;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

public class JXBuddyField extends JXPromptField {
	public JXBuddyField() {
	}
	
	@Override
	protected void installPromptSupport(String promptText, Color promptForeground, Color promptBackground) {
		PromptSupport.init(promptText, promptForeground, promptBackground, this, false);
		BuddySupport.install(this);
	}
	
	public void setOuterMargin(Insets margin) {
		BuddySupport.setOuterMargin(this, margin);
	}

	public Insets getOuterMargin() {
		return BuddySupport.getOuterMargin(this);
	}
	
	public void addBuddy(Component buddy, Position pos){
		BuddySupport.add(buddy, pos, this);
	}
	
	public void addGap(int width, Position pos){
		BuddySupport.addGap(width, pos, this);
	}
	
	public List<Component> getBuddies(Position pos){
		return BuddySupport.getBuddies(pos, this);
	}
	
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
////			UIManager.setLookAndFeel(new QuaquaLookAndFeel());
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		JTextField bf = new JXAddressField();
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(bf, BorderLayout.NORTH);

		final JTextField tf = new JTextField();
		bf.setComponentPopupMenu(LabelDemo.createLookAndFeelMenu(f).getPopupMenu());
		
		NativeSearchFieldSupport.setSearchField(tf, true);
		PromptSupport.setPrompt("Search", tf);
//		System.out.println(tf.getUI());
//		System.out.println(tf.getBorder());
//		tf.updateUI();
//		SearchFieldSupport.setSearchField(tf, true);
//		System.out.println(tf.getUI());
//		System.out.println(tf.getBorder());
		
		f.add(new JPanel(), BorderLayout.CENTER);
		f.add(new JXSearchField("search"), BorderLayout.SOUTH);
//		f.add(tf, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
}