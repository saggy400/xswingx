/*
 * Created by JFormDesigner on Tue Nov 20 14:52:03 CET 2007
 */

package org.jdesktop.xswingx.demo;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Peter Weishapl
 */
public class CustomizePanel extends JPanel {
	private JTextField field;
	
	public CustomizePanel() {
	}

	public JTextField getField() {
		return field;
	}

	public void setField(JTextField textComponent) {
		this.field = textComponent;
	}

	public void changed(){
		if(field == null) return;
		
		field.getParent().validate();
		field.invalidate();
		field.revalidate();
		field.repaint();
	}
}
