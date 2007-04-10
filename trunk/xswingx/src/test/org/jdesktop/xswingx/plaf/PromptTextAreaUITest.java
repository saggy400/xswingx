package org.jdesktop.xswingx.plaf;

import javax.swing.JTextArea;

import org.jdesktop.xswingx.plaf.PromptTextAreaUI;
import org.junit.Test;
import static org.junit.Assert.*;

public class PromptTextAreaUITest extends PromptTextUITest {
    JTextArea textArea;
    
    public void setup() {
        textComponent = textArea = new JTextArea();
        ui = new PromptTextAreaUI(textComponent.getUI());
    }
    
    @Test
    public void testGetLabelComponent() {
        super.testGetLabelComponent();
        
        textArea.setRows(5);
        textArea.setColumns(5);
        JTextArea lbl = (JTextArea) ui.getPromptComponent(textArea);
        
        assertEquals(textArea.getRows(), lbl.getRows());
        assertEquals(textArea.getColumns(), lbl.getColumns());
    }
}
