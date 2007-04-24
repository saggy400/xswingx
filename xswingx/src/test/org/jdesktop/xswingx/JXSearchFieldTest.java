package org.jdesktop.xswingx;

import static org.junit.Assert.*;

import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import org.jdesktop.xswingx.JXSearchField.SearchMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JXSearchFieldTest {
	boolean action;

	JXSearchField searchField;

	@Before
	public void setUp() throws Exception {
		searchField = new JXSearchField();
	}

	@Test
	public void testFireActionOnTextChange() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				action = true;
			}
		});
		action = false;
		Assert.assertTrue(searchField.isInstantSearchMode());
		searchField.setText("search");
		Assert.assertTrue(action);
		
		action = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		searchField.setText("search2");
		assertFalse(action);
	}

	@Test
	public void testButtonVisibility() throws Exception {
		assertTrue(searchField.getSearchButton().isVisible());
		assertFalse(searchField.getClearButton().isVisible());

		assertTrue(searchField.isMacLayoutStyle());
		assertTrue(searchField.isInstantSearchMode());
		searchField.setText("text");
		assertTrue(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getClearButton().isVisible());

		searchField.setLayoutStyle(LayoutStyle.VISTA);
		assertFalse(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getClearButton().isVisible());
		
		searchField.setSearchMode(SearchMode.REGULAR);
		assertTrue(searchField.getSearchButton().isVisible());
		assertFalse(searchField.getClearButton().isVisible());
	}

	@Test
	public void testSearchMode() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				action = true;
			}
		});
		action = false;
		assertNull(searchField.getSearchButton().getRolloverIcon());
		assertNull(searchField.getSearchButton().getPressedIcon());
		assertTrue(searchField.isInstantSearchMode());
		searchField.getSearchButton().doClick();
		assertFalse(action);
		searchField.setText("test");
		assertTrue(action);

		action = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		assertNotNull(searchField.getSearchButton().getRolloverIcon());
		assertNotNull(searchField.getSearchButton().getPressedIcon());
		searchField.getSearchButton().doClick();
		assertTrue(action);

		action = false;
		searchField.setSearchPopupMenu(new JPopupMenu());
		try {
			searchField.getSearchButton().doClick();
			fail("must try to show search popup");
		} catch (IllegalComponentStateException ex) {
		}
		assertFalse(action);
	}

	@Test
	public void testSearchButtonPopupMenu() throws Exception {
		searchField.setSearchPopupMenu(new JPopupMenu());
		assertNotNull(searchField.getSearchButton().getIcon());
		assertEquals(UIManager.getIcon("SearchField.popupIcon"),
				searchField.getSearchButton().getIcon());
		searchField.setSearchPopupMenu(null);
		assertEquals(UIManager.getIcon("SearchField.icon"), searchField
				.getSearchButton().getIcon());
		ImageIcon anotherIcon = new ImageIcon();
		searchField.getSearchButton().setIcon(anotherIcon);
		searchField.setSearchPopupMenu(new JPopupMenu());
		assertEquals(anotherIcon, searchField.getSearchButton()
				.getIcon());
	}
	
	@Test
	public void testSetPopupAfterRegular() throws Exception {
		searchField.setSearchMode(SearchMode.REGULAR);
		searchField.setSearchPopupMenu(new JPopupMenu());
		assertNull(searchField.getSearchButton().getRolloverIcon());
		assertNull(searchField.getSearchButton().getPressedIcon());
	}

	@Test
	public void testEditableEnabled() throws Exception {
		searchField.setEditable(false);
		searchField.setEnabled(true);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertTrue(searchField.getSearchAction().isEnabled());
		assertTrue(searchField.getSearchButton().isEnabled());

		searchField.setEditable(true);
		searchField.setEnabled(false);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertFalse(searchField.getSearchAction().isEnabled());
		assertFalse(searchField.getSearchButton().isEnabled());

		searchField.setEditable(false);
		searchField.setEnabled(false);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertFalse(searchField.getSearchAction().isEnabled());
		assertFalse(searchField.getSearchButton().isEnabled());

		searchField.setEditable(true);
		searchField.setEnabled(true);
		assertTrue(searchField.getClearAction().isEnabled());
		assertTrue(searchField.getClearButton().isEnabled());
		assertTrue(searchField.getSearchAction().isEnabled());
		assertTrue(searchField.getSearchButton().isEnabled());
	}
	
	@Test
	public void testSetLayoutStyle() throws Exception {
		assertSame(LayoutStyle.MAC, searchField.getLayoutStyle());
		assertFalse(searchField.getClearButton().isVisible());
		assertTrue(searchField.getSearchButton().isVisible());
		
		searchField.setText("test");
		assertTrue(searchField.getClearButton().isVisible());
		assertTrue(searchField.getSearchButton().isVisible());
		
		searchField.setLayoutStyle(LayoutStyle.VISTA);
		assertTrue(searchField.getClearButton().isVisible());
		assertFalse(searchField.getSearchButton().isVisible());
	}
}
