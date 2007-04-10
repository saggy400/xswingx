package org.jdesktop.xswingx.plaf;

import javax.swing.JTextField;

import org.jdesktop.xswingx.plaf.PromptTextFieldUI;
import org.junit.Test;
import static org.junit.Assert.*;

public class PromptTextFieldUITest extends PromptTextUITest {
    JTextField txtField;
    
    public void setup() {
        textComponent = txtField = new JTextField();
        ui = new PromptTextFieldUI(textComponent.getUI());
    }
    
    @Test
    public void testGetLabelComponent() {
        super.testGetLabelComponent();
        txtField.setHorizontalAlignment(JTextField.CENTER);
        JTextField lbl = (JTextField) ui.getPromptComponent(txtField);
        
        assertEquals(txtField.getHorizontalAlignment(), lbl.getHorizontalAlignment());
        assertEquals(txtField.getColumns(), lbl.getColumns());
    }
}
