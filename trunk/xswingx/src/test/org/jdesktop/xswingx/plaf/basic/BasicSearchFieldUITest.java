package org.jdesktop.xswingx.plaf.basic;

import static org.junit.Assert.assertSame;

import javax.swing.UIManager;

import org.jdesktop.xswingx.JXSearchField;
import org.junit.Before;
import org.junit.Test;


public class BasicSearchFieldUITest {
	private JXSearchField sf;
	private BasicSearchFieldUI ui;
	
	@Before
	public void setUp() {
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.FALSE);
		sf = new JXSearchField();
		ui = (BasicSearchFieldUI) sf.getUI();
	}
	
	@Test
	public void testChildComponents() throws Exception {
		assertSame(3, sf.getComponentCount());
		ui.uninstallUI(sf);
		assertSame(0, sf.getComponentCount());
	}
	
	@Test
	public void testPopupButton() throws Exception {
		
	}
}
