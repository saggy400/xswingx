package org.jdesktop.xswingx.demo;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Plastic3DLookAndFeel.setHighContrastFocusColorsEnabled(true);
		UIManager.installLookAndFeel("JGoodies Windows", WindowsLookAndFeel.class.getName());
		UIManager.installLookAndFeel("JGoodies Plastic", Plastic3DLookAndFeel.class.getName());
		UIManager.installLookAndFeel("JGoodies Plastic XP", PlasticXPLookAndFeel.class.getName());
		
		JFrame f = new JFrame("xswingx Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar mb = new JMenuBar();
		mb.add(LabelDemo.createLookAndFeelMenu(f));
		f.setJMenuBar(mb);
		
		JTabbedPane tb = new JTabbedPane();
		tb.add("Text Field", new TextFieldCustomizer());
		tb.add("Search Field", new SearchFieldCustomizer());
		f.add(tb);
		f.pack();
		f.setVisible(true);
	}

}
