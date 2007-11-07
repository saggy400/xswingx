package org.jdesktop.xswingx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jdesktop.xswingx.demo.BookmarkButton;
import org.jdesktop.xswingx.demo.RssButton;
import org.jdesktop.xswingx.demo.SnapBackButton;

public class JXBuddyField extends JXPromptField {
	public JXBuddyField() {
		BuddySupport.install(this);
	}
	
	@Override
	protected void installPromptSupport(String promptText, Color promptForeground, Color promptBackground) {
		PromptSupport.init(promptText, promptForeground, promptBackground, this, false);
	}
	
	public void setOuterMargin(Insets margin) {
		BuddySupport.setOuterMargin(this, margin);
	}

	public Insets getOuterMargin() {
		return BuddySupport.getOuterMargin(this);
	}
	
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
//			UIManager.setLookAndFeel(new QuaquaLookAndFeel());
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		JXBuddyField bf = new JXBuddyField();
//		
		PromptSupport.setPrompt("Go to this address", bf);
		
		bf.add(new BookmarkButton(), BuddySupport.LEFT);
		bf.add(new RssButton(), BuddySupport.RIGHT);
		bf.add(BuddySupport.createGap(3), BuddySupport.RIGHT);
		bf.add(new SnapBackButton(), BuddySupport.RIGHT);
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(bf, BorderLayout.NORTH);
		f.add(new JXPromptField("Simple Prompt Field"), BorderLayout.CENTER);
		
		final JTextField tf = new JTextField();
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		
//		tf.putClientProperty("JTextField.variant", "search");
//		System.out.println(tf.getUI());
//		System.out.println(tf.getBorder());
		
		PromptSupport.setPrompt("search", tf);
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		
//		tf.putClientProperty("JTextField.variant", "nonsense");
		tf.putClientProperty("JTextField.variant", "search");
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		
//		tf.setText("test");
		System.out.println(tf.getUI());
		System.out.println(tf.getBorder());
		
		f.add(tf, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
}
