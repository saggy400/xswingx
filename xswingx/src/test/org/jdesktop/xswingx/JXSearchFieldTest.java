package org.jdesktop.xswingx;

import static org.junit.Assert.*;

import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
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
		Assert.assertFalse(searchField.isFireActionOnTextChange());
		searchField.setText("search");
		Assert.assertFalse(action);
		searchField.setFireActionOnTextChange(true);
		searchField.setText("search2");
		assertTrue(action);
	}

	@Test
	public void testButtonVisibility() throws Exception {
		assertTrue(searchField.getSearchButton().isVisible());
		assertFalse(searchField.getClearButton().isVisible());

		searchField.setText("text");
		assertTrue(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getClearButton().isVisible());

		searchField.setFireActionOnButtonClick(true);
		assertTrue(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getClearButton().isVisible());

		searchField.setLayoutStyle(LayoutStyle.VISTA);
		assertTrue(searchField.getSearchButton().isVisible());
		assertFalse(searchField.getClearButton().isVisible());
	}

	@Test
	public void testFireActionOnButtonClick() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				action = true;
			}
		});
		action = false;
		assertFalse(searchField.isFireActionOnButtonClick());
		searchField.getSearchButton().doClick();
		assertFalse(action);

		searchField.setFireActionOnButtonClick(true);
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
}
