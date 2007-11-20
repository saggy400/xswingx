/*
 * Created by JFormDesigner on Tue Nov 20 15:12:15 CET 2007
 */

package org.jdesktop.xswingx.demo;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Peter Weishapl
 */
public class CustomizeBuddyField extends JPanel {
	public CustomizeBuddyField() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Peter Weishapl
		panel2 = new JPanel();
		titledSeparator1 = new TitledSeparator();
		label2 = new JLabel();
		formattedTextField1 = new JFormattedTextField();
		panel3 = new JPanel();
		checkBox1 = new JRadioButton();
		toggleButton1 = new JLabel();
		radioButton1 = new JRadioButton();
		label1 = new JLabel();
		radioButton2 = new JRadioButton();
		checkBox2 = new JCheckBox();
		panel1 = new JPanel();
		button2 = new JButton();
		button3 = new JButton();
		button1 = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== panel2 ========
		{

			// JFormDesigner evaluation mark
			panel2.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), panel2.getBorder())); panel2.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			panel2.setLayout(new BorderLayout());

			//======== this ========
			{
				this.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.UNRELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
					},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.UNRELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC
					}));

				//---- titledSeparator1 ----
				titledSeparator1.setTitle("Buddies");
				this.add(titledSeparator1, cc.xywh(1, 1, 4, 1));

				//---- label2 ----
				label2.setText("Outer Margin:");
				this.add(label2, cc.xy(2, 3));
				this.add(formattedTextField1, cc.xy(4, 3));

				//======== panel3 ========
				{
					panel3.setLayout(new FormLayout(
						new ColumnSpec[] {
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.UNRELATED_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.UNRELATED_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
						},
						new RowSpec[] {
							FormFactory.DEFAULT_ROWSPEC,
							FormFactory.LINE_GAP_ROWSPEC,
							FormFactory.DEFAULT_ROWSPEC
						}));
					panel3.add(checkBox1, cc.xy(1, 1));

					//---- toggleButton1 ----
					toggleButton1.setIcon(new ImageIcon(getClass().getResource("/org/jdesktop/xswingx/demo/Search_SnapBack.png")));
					panel3.add(toggleButton1, cc.xy(3, 1));
					panel3.add(radioButton1, cc.xy(5, 1));

					//---- label1 ----
					label1.setIcon(new ImageIcon(getClass().getResource("/org/jdesktop/xswingx/demo/ShowRSSButton.png")));
					panel3.add(label1, cc.xy(7, 1));
					panel3.add(radioButton2, cc.xy(9, 1));

					//---- checkBox2 ----
					checkBox2.setEnabled(false);
					panel3.add(checkBox2, cc.xy(11, 1));

					//======== panel1 ========
					{
						panel1.setLayout(new FormLayout(
							new ColumnSpec[] {
								FormFactory.UNRELATED_GAP_COLSPEC,
								new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT, FormSpec.NO_GROW),
								FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
								new ColumnSpec(ColumnSpec.LEFT, Sizes.DEFAULT, FormSpec.NO_GROW),
								FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
								FormFactory.DEFAULT_COLSPEC
							},
							RowSpec.decodeSpecs("default")));

						//---- button2 ----
						button2.setText("Add Left");
						panel1.add(button2, cc.xy(2, 1));

						//---- button3 ----
						button3.setText("Add Right");
						panel1.add(button3, cc.xy(4, 1));

						//---- button1 ----
						button1.setText("Remove All");
						panel1.add(button1, cc.xywh(6, 1, 1, 1, CellConstraints.LEFT, CellConstraints.DEFAULT));
					}
					panel3.add(panel1, cc.xywh(1, 3, 11, 1));
				}
				this.add(panel3, cc.xywh(2, 5, 3, 1));
			}
			panel2.add(this, BorderLayout.CENTER);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Peter Weishapl
	private JPanel panel2;
	private TitledSeparator titledSeparator1;
	private JLabel label2;
	private JFormattedTextField formattedTextField1;
	private JPanel panel3;
	private JRadioButton checkBox1;
	private JLabel toggleButton1;
	private JRadioButton radioButton1;
	private JLabel label1;
	private JRadioButton radioButton2;
	private JCheckBox checkBox2;
	private JPanel panel1;
	private JButton button2;
	private JButton button3;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
