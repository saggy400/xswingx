package org.jdesktop.xswingx.demo;

import java.awt.Insets;
import javax.swing.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import org.jdesktop.xswingx.*;



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
		xAddressField1 = new JXAddressField();
		xAddressField2 = new JXAddressField();
		xSearchField1 = new JXSearchField();
		popupMenu1 = new JPopupMenu();
		menuItem1 = new JMenuItem();
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
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
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
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.TOP, Sizes.PREFERRED, FormSpec.NO_GROW)
			}));
		add(xAddressField1, cc.xywh(1, 1, 7, 1));
		add(xAddressField2, cc.xywh(1, 5, 7, 1));

		//---- xSearchField1 ----
		xSearchField1.setPrompt("Search");
		xSearchField1.setSearchPopupMenu(popupMenu1);
		add(xSearchField1, cc.xy(1, 9));

		//======== popupMenu1 ========
		{

			//---- menuItem1 ----
			menuItem1.setText("fasdf");
			popupMenu1.add(menuItem1);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Peter Weishapl
	private JXAddressField xAddressField1;
	private JXAddressField xAddressField2;
	private JXSearchField xSearchField1;
	private JPopupMenu popupMenu1;
	private JMenuItem menuItem1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
