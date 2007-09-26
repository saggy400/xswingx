package org.jdesktop.xswingx.plaf;

import static org.junit.Assert.assertEquals;

import javax.swing.JTextField;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicTextFieldUI;

import org.junit.Before;
import org.junit.Test;


public class TextUIWrapperTest {
	private JTextField txt;
	private TextUIWrapper<PromptTextFieldUI> wrapper;

	@Before
    public void setup() {
        txt = new JTextField();
        wrapper = new TextUIWrapper<PromptTextFieldUI>(PromptTextFieldUI.class){
			@Override
			public PromptTextFieldUI wrapUI(TextUI textUI) {
				return new PromptTextFieldUI(textUI);
			}
        };
    }
    
	@Test
    public void testWrapUI() throws Exception {
    	assertEquals(PromptTextFieldUI.class, wrapper.wrapUI(new JTextField().getUI()).getClass());
	}
	
	
	@Test
    public void testUninstall() {
    	Class defaultUiClass = txt.getUI().getClass();
    	wrapper.install(txt, true);
    	wrapper.uninstall(txt);
    	assertEquals(defaultUiClass, txt.getUI().getClass());
    }
	
	@Test
    public void testInstall() {
    	wrapper.install(txt, false);
    	mustBeInstalled();
	}
    
    @Test
    public void testInstallAndStay() {
    	wrapper.install(txt, true);
    	mustStayInstalled();
	}

	private void mustBeInstalled() {
		assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
		txt.setUI(new BasicTextFieldUI());
    	assertEquals(BasicTextFieldUI.class, txt.getUI().getClass());
	}
	
	private void mustStayInstalled() {
		assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
    	txt.setUI(new BasicTextFieldUI());
    	assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
	}
}
