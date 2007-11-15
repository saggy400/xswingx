package org.jdesktop.xswingx.demo;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jdesktop.xswingx.*;
/*
 * Created by JFormDesigner on Tue Nov 13 15:49:36 CET 2007
 */



/**
 * @author Peter Weishapl
 */
public class AnotherTestPanel extends JPanel {
	public AnotherTestPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Peter Weishapl
		xSearchField1 = new JXSearchField();
		xPromptField1 = new JXPromptField();
		textField1 = new JTextField();
		xSearchField2 = new JXSearchField();
		xAddressField1 = new JXAddressField();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setBorder(Borders.DLU2_BORDER);

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC
			},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC
			}));

		//---- xSearchField1 ----
		xSearchField1.setPrompt("Search");
		add(xSearchField1, cc.xy(1, 1));

		//---- xPromptField1 ----
		xPromptField1.setPrompt("Enter Text");
		add(xPromptField1, cc.xy(3, 1));
		add(textField1, cc.xy(5, 1));

		//---- xSearchField2 ----
		xSearchField2.setPrompt("Type to Search");
		add(xSearchField2, cc.xy(7, 1));
		add(xAddressField1, cc.xywh(1, 5, 7, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Peter Weishapl
	private JXSearchField xSearchField1;
	private JXPromptField xPromptField1;
	private JTextField textField1;
	private JXSearchField xSearchField2;
	private JXAddressField xAddressField1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
