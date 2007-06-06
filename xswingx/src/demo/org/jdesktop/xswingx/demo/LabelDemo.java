/*
 * LabelDemo.java
 *
 * Created on 27. März 2007, 19:02
 */

package org.jdesktop.xswingx.demo;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.JTextComponent;

import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

/**
 * 
 * @author peterw
 */
public class LabelDemo extends javax.swing.JFrame {
	private FocusBehavior focusBehavior;
	private boolean editable = true;
	private boolean enabled = true;
	private Integer fontStyle;

	class Menu extends JMenuBar {
		public Menu() {
			final JMenu lnf = new JMenu("Look and Feel");
			add(lnf);
			for (final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				final JMenuItem mi = new JMenuItem(info.getName());
				lnf.add(mi);

				mi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							UIManager.setLookAndFeel(info.getClassName());
							SwingUtilities.updateComponentTreeUI(LabelDemo.this);
						} catch (Exception ex) {
							mi.setEnabled(false);
							ex.printStackTrace();
						}
					}
				});
			}

			final JMenu fbm = new JMenu("Focus Behavior");
			add(fbm);
			final ButtonGroup bg = new ButtonGroup();
			for (FocusBehavior fb : FocusBehavior.values()) {
				JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem(fb.name());
				fbm.add(rbmi);
				bg.add(rbmi);
				if (focusBehavior == fb) {
					rbmi.setSelected(true);
				}
				rbmi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						focusBehavior = FocusBehavior.valueOf(e.getActionCommand());
						updateLabelTextComponents();
					}
				});
			}

			final JMenu lsm = new JMenu("Prompt Style");
			add(lsm);
			ButtonGroup fontGroup = new ButtonGroup();
			final JRadioButtonMenuItem dtri = new JRadioButtonMenuItem("Default");
			lsm.add(dtri);
			fontGroup.add(dtri);
			dtri.setSelected(true);
			dtri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontStyle = null;
					updateLabelTextComponents();
				}
			});
			lsm.addSeparator();
			final JRadioButtonMenuItem ptri = new JRadioButtonMenuItem("Plain");
			lsm.add(ptri);
			fontGroup.add(ptri);
			ptri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontStyle = Font.PLAIN;
					updateLabelTextComponents();
				}
			});
			final JRadioButtonMenuItem itri = new JRadioButtonMenuItem("Italic");
			lsm.add(itri);
			fontGroup.add(itri);
			itri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontStyle = Font.ITALIC;
					updateLabelTextComponents();
				}
			});
			final JRadioButtonMenuItem btri = new JRadioButtonMenuItem("Bold");
			lsm.add(btri);
			fontGroup.add(btri);
			btri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontStyle = Font.BOLD;
					updateLabelTextComponents();
				}
			});
			final JRadioButtonMenuItem bitri = new JRadioButtonMenuItem("Bold & Italic");
			lsm.add(bitri);
			fontGroup.add(bitri);
			bitri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontStyle = Font.BOLD | Font.ITALIC;
					updateLabelTextComponents();
				}
			});

			final JMenu sm = new JMenu("State");
			add(sm);
			final JCheckBoxMenuItem edmi = new JCheckBoxMenuItem("Editable");
			sm.add(edmi);
			edmi.setSelected(true);
			edmi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editable = edmi.isSelected();
					updateLabelTextComponents();
				}
			});

			final JCheckBoxMenuItem enmi = new JCheckBoxMenuItem("Enabled");
			sm.add(enmi);
			enmi.setSelected(true);
			enmi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enabled = enmi.isSelected();
					updateLabelTextComponents();
				}
			});
		}
	}

	private void updateLabelTextComponents() {
		updateLabelTextComponents(this);
	}

	private void updateLabelTextComponents(Container root) {
		if (root instanceof JTextComponent) {
			JTextComponent txt = (JTextComponent) root;
			PromptSupport.setFocusBehavior(focusBehavior, txt);
			PromptSupport.setFontStyle(fontStyle, txt);
			txt.setEditable(editable);
			txt.setEnabled(enabled);
		} else {
			for (Component c : root.getComponents()) {
				if (c instanceof Container) {
					updateLabelTextComponents((Container) c);
				}
			}
		}
	}

	/** Creates new form LabelDemo */
	public LabelDemo() {
		initComponents();
		this.focusBehavior = FocusBehavior.HIGHLIGHT_PROMPT;
		updateLabelTextComponents();
		setJMenuBar(new Menu());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// ">//GEN-BEGIN:initComponents
	private void initComponents() {
		jTabbedPane1 = new javax.swing.JTabbedPane();
		contactPanel1 = new org.jdesktop.xswingx.demo.ContactPanel();
		testPanel1 = new org.jdesktop.xswingx.demo.TestPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jTabbedPane1.addTab("Component Test", testPanel1);
		jTabbedPane1.addTab("Contact", contactPanel1);
		// jTabbedPane1.addTab("Search Field", new SearchFieldDemoPanel());

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().addContainerGap().add(jTabbedPane1,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup().addContainerGap().add(jTabbedPane1,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addContainerGap()));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		// System.setProperty("apple.laf.useScreenMenuBar", "true");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Plastic3DLookAndFeel.setHighContrastFocusColorsEnabled(true);
		UIManager.installLookAndFeel("JGoodies Windows", WindowsLookAndFeel.class.getName());
		UIManager.installLookAndFeel("JGoodies Plastic", Plastic3DLookAndFeel.class.getName());
		UIManager.installLookAndFeel("JGoodies Plastic XP", PlasticXPLookAndFeel.class.getName());

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				LabelDemo ld = new LabelDemo();
				ld.getTabbedPane().addTab("Search Field", new SearchPanel());
				ld.pack();
				ld.setVisible(true);
			}
		});
	}

	public JTabbedPane getTabbedPane() {
		return jTabbedPane1;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private org.jdesktop.xswingx.demo.ContactPanel contactPanel1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private org.jdesktop.xswingx.demo.TestPanel testPanel1;
	// End of variables declaration//GEN-END:variables

}
