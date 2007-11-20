/*
 * Created by JFormDesigner on Tue Nov 20 16:53:37 CET 2007
 */

package org.jdesktop.xswingx.demo;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Peter Weishapl
 */
public class TextFieldCustomizer extends JPanel {
	public TextFieldCustomizer() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Peter Weishapl
		panel1 = new JPanel();
		field = new JTextField();
		separator1 = new JSeparator();
		scrollPane1 = new JScrollPane();
		panel2 = new JPanel();
		customizePromptPanel1 = new CustomizePromptPanel();
		customizeGeneralPanel1 = new CustomizeGeneralPanel();
		CellConstraints cc = new CellConstraints();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			ColumnSpec.decodeSpecs("default:grow"),
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC
			}));

		//======== panel1 ========
		{
			panel1.setBackground(UIManager.getColor("info"));
			panel1.setLayout(new FormLayout(
				ColumnSpec.decodeSpecs("default:grow, default, default:grow"),
				new RowSpec[] {
					FormFactory.UNRELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.UNRELATED_GAP_ROWSPEC
				}));
			panel1.add(field, cc.xy(2, 2));
		}
		add(panel1, cc.xy(1, 1));
		add(separator1, cc.xy(1, 2));

		//======== scrollPane1 ========
		{
			scrollPane1.setBorder(null);

			//======== panel2 ========
			{
				panel2.setLayout(new FormLayout(
					ColumnSpec.decodeSpecs("default:grow"),
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC
					}));

				//---- customizePromptPanel1 ----
				customizePromptPanel1.setField(field);
				panel2.add(customizePromptPanel1, cc.xy(1, 1));

				//---- customizeGeneralPanel1 ----
				customizeGeneralPanel1.setField(field);
				panel2.add(customizeGeneralPanel1, cc.xy(1, 3));
			}
			scrollPane1.setViewportView(panel2);
		}
		add(scrollPane1, cc.xy(1, 4));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Peter Weishapl
	private JPanel panel1;
	private JTextField field;
	private JSeparator separator1;
	private JScrollPane scrollPane1;
	private JPanel panel2;
	private CustomizePromptPanel customizePromptPanel1;
	private CustomizeGeneralPanel customizeGeneralPanel1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
