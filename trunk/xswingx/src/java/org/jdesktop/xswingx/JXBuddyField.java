package org.jdesktop.xswingx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jdesktop.xswingx.BuddySupport.Position;
import org.jdesktop.xswingx.demo.BookmarkButton;
import org.jdesktop.xswingx.demo.LabelDemo;
import org.jdesktop.xswingx.demo.RssButton;

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
		JTextField bf = new JTextField();
//		
		BuddySupport.addLeft(new BookmarkButton(), bf);
		BuddySupport.addRight(new RssButton(), bf);
//		BuddySupport.addRight(BuddySupport.createGap(3), bf);
//		BuddySupport.addRight(new SnapBackButton(), bf);
		
		PromptSupport.setPrompt("Go to this address", bf);
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(bf, BorderLayout.NORTH);

		final JTextField tf = new JTextField();
		tf.setComponentPopupMenu(LabelDemo.createLookAndFeelMenu(f).getPopupMenu());
		
		SearchFieldSupport.setSearchField(tf, true);
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		tf.updateUI();
		SearchFieldSupport.setSearchField(tf, true);
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		
		f.add(tf, BorderLayout.CENTER);
		f.add(new JXSearchField(), BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
}
