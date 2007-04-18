package org.jdesktop.xswingx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;


import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;
import org.jdesktop.xswingx.plaf.PromptTextAreaUI;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;
import org.junit.Before;
import org.junit.Test;

public class PromptSupportTest {
    private JTextField txt;

    @Before
    public void setup() {
        txt = new JTextField();
    }
    
    @Test
    public void testWrapUI() throws Exception {
    	assertEquals(PromptTextFieldUI.class, PromptSupport.wrapUI(new JTextField()).getClass());
    	assertEquals(PromptTextFieldUI.class, PromptSupport.wrapUI(new JFormattedTextField()).getClass());
    	assertEquals(PromptTextAreaUI.class, PromptSupport.wrapUI(new JTextArea()).getClass());
    	
    	txt.setUI(PromptSupport.wrapUI(txt));
    	assertSame(txt.getUI(), PromptSupport.wrapUI(txt));
	}
    
    @Test
    public void testInstall() {
    	PromptSupport.install("test", Color.LIGHT_GRAY, txt);
    	
    	assertEquals("test", PromptSupport.getPrompt(txt));
        assertEquals("test", txt.getClientProperty(PromptSupport.PROMPT));
        assertEquals(Color.LIGHT_GRAY, PromptSupport.getPromptColor(txt));
    	
    	assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
    	txt.setUI(new BasicTextFieldUI());
    	assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
    }

    @Test
    public void testInit() {
        PromptSupport.init("test", Color.LIGHT_GRAY, txt);

        assertEquals("test", PromptSupport.getPrompt(txt));
        assertEquals("test", txt.getClientProperty(PromptSupport.PROMPT));

        assertEquals(Color.LIGHT_GRAY, PromptSupport.getPromptColor(txt));
        assertEquals(Color.LIGHT_GRAY, txt.getClientProperty(PromptSupport.PROMPT_COLOR));
    }

    @Test
    public void testGetFocusBehavior() throws Exception {
        assertEquals(FocusBehavior.HIDE_PROMPT, PromptSupport.getFocusBehavior(txt));
    }

    @Test
    public void testSetFocusBehavior() throws Exception {
        PromptSupport.setFocusBehavior(FocusBehavior.HIGHLIGHT_PROMPT, txt);

        assertEquals(FocusBehavior.HIGHLIGHT_PROMPT, PromptSupport.getFocusBehavior(txt));
        assertEquals(FocusBehavior.HIGHLIGHT_PROMPT, txt.getClientProperty(PromptSupport.FOCUS_BEHAVIOR));
    }

    @Test
    public void testLabelText() throws Exception {
        PromptSupport.setPrompt("test", txt);
        
        assertEquals("test", PromptSupport.getPrompt(txt));
        assertEquals("test", txt.getClientProperty(PromptSupport.PROMPT));
        assertEquals("test", txt.getToolTipText());
        
        PromptSupport.setPrompt("test2", txt);
        assertEquals("test2", txt.getToolTipText());
        
        txt.setToolTipText("ttt");
        PromptSupport.setPrompt("test3", txt);
        assertEquals("ttt", txt.getToolTipText());
    }
    
    @Test
    public void testGetLabelTextColor() throws Exception {
        assertEquals(txt.getDisabledTextColor(), PromptSupport.getPromptColor(txt));
    }
    
    @Test
    public void testSetLabelTextColor() throws Exception {
        PromptSupport.setPromptColor(Color.RED, txt);
        
        assertEquals(Color.RED, PromptSupport.getPromptColor(txt));
        assertEquals(Color.RED, txt.getClientProperty(PromptSupport.PROMPT_COLOR));
    }
    
    @Test
    public void testLabelFontStyle() throws Exception {
    	assertNull(PromptSupport.getPromptFontStyle(txt));
    	PromptSupport.setPromptFontStyle(Font.BOLD, txt);
    	assertEquals(Font.BOLD, PromptSupport.getPromptFontStyle(txt));
    	assertEquals(Font.BOLD, txt.getClientProperty(PromptSupport.PROMPT_FONT_STYLE));
	}
}
