package org.jdesktop.xswingx.plaf.basic;

import static org.junit.Assert.*;

import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.plaf.basic.BasicSearchFieldUI;
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
		assertSame(2, sf.getComponentCount());
		ui.uninstallUI(sf);
		assertSame(0, sf.getComponentCount());
		
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.TRUE);
		ui.installUI(sf);
		assertSame(3, sf.getComponentCount());
		ui.uninstallUI(sf);
		assertSame(0, sf.getComponentCount());
	}
	
	@Test
	public void testPopupButton() throws Exception {
		assertFalse(ui.getPopupButton().isVisible());
		
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.TRUE);
		sf.setUI(ui);
		assertFalse(ui.getPopupButton().isVisible());
		assertSame(UIManager.getIcon("SearchField.popupIcon"), ui.getPopupButton().getIcon());
		assertSame(UIManager.getIcon("SearchField.popupRolloverIcon"), ui.getPopupButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.popupPressedIcon"), ui.getPopupButton().getPressedIcon());
		
		sf.setSearchPopupMenu(new JPopupMenu());
		assertTrue(ui.getPopupButton().isVisible());
	}
}
