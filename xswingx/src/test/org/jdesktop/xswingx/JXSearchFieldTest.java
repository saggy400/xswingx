package org.jdesktop.xswingx;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;

import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import org.jdesktop.xswingx.JXSearchField.SearchMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JXSearchFieldTest {
	boolean eventReceived;

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
				eventReceived = true;
			}
		});
		eventReceived = false;
		Assert.assertTrue(searchField.isInstantSearchMode());
		searchField.setText("search");
		Assert.assertTrue(eventReceived);
		
		eventReceived = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		searchField.setText("search2");
		assertFalse(eventReceived);
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
				eventReceived = true;
			}
		});
		eventReceived = false;
		assertSame(UIManager.getIcon("SearchField.icon"), searchField.getSearchButton().getIcon());
		assertNull(searchField.getSearchButton().getRolloverIcon());
		assertNull(searchField.getSearchButton().getPressedIcon());
		assertTrue(searchField.isInstantSearchMode());
		searchField.getSearchButton().doClick();
		assertFalse(eventReceived);
		searchField.setText("test");
		assertTrue(eventReceived);

		eventReceived = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		assertSame(UIManager.getIcon("SearchField.rolloverIcon"), searchField.getSearchButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.pressedIcon"), searchField.getSearchButton().getPressedIcon());
		searchField.getSearchButton().doClick();
		assertTrue(eventReceived);

		eventReceived = false;
		searchField.setSearchPopupMenu(new JPopupMenu());
		try {
			searchField.getSearchButton().doClick();
			fail("must try to show search popup");
		} catch (IllegalComponentStateException ex) {
		}
		assertFalse(eventReceived);
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
	
	@Test
	public void testPopupButton() throws Exception {
		assertFalse(searchField.isUseSeperatePopupButton());
		searchField.addPropertyChangeListener("useSeperatePopupButton", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				eventReceived = true;
			}
		});
		searchField.setUseSeperatePopupButton(true);
		assertTrue(eventReceived);
		assertNotSame(searchField.getSearchButton(), searchField.getPopupButton());
		assertNotNull(searchField.getPopupButton());
	}
	
	@Test
	public void testSearchButtonIcons() throws Exception {
		UIManager.put("SearchField.icon", new TestIcon("SearchField.icon"));
		UIManager.put("SearchField.rolloverIcon", new TestIcon("SearchField.rolloverIcon"));
		UIManager.put("SearchField.pressedIcon", new TestIcon("SearchField.pressedIcon"));
		
		UIManager.put("SearchField.popupIcon", new TestIcon("SearchField.popupIcon"));
		UIManager.put("SearchField.popupRolloverIcon", new TestIcon("SearchField.popupRolloverIcon"));
		UIManager.put("SearchField.popupPressedIcon", new TestIcon("SearchField.popupPressedIcon"));
		
		UIManager.put("SearchField.clearIcon", new TestIcon("SearchField.clearIcon"));
		UIManager.put("SearchField.clearRolloverIcon", new TestIcon("SearchField.clearRolloverIcon"));
		UIManager.put("SearchField.clearPressedIcon", new TestIcon("SearchField.clearPressedIcon"));
		
		searchField.updateUI();
		
		assertSame(UIManager.getIcon("SearchField.icon"),
				searchField.getSearchButton().getIcon());
		assertFalse(searchField.isRegularSearchMode());
		assertNull(searchField.getSearchButton().getRolloverIcon());
		assertNull(searchField.getSearchButton().getPressedIcon());
		
		searchField.setSearchMode(SearchMode.REGULAR);
		assertSame(UIManager.getIcon("SearchField.icon"),
				searchField.getSearchButton().getIcon());
		assertSame(UIManager.getIcon("SearchField.rolloverIcon"),
				searchField.getSearchButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.pressedIcon"),
				searchField.getSearchButton().getPressedIcon());
		
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
	
	class TestIcon implements Icon, UIResource{
		private String name;

		public TestIcon(String name) {
			this.name = name;
		}
		
		public int getIconHeight() {
			return 0;
		}

		public int getIconWidth() {
			return 0;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
		}
		
		public boolean equals(Object obj) {
			if (!(obj instanceof TestIcon)) {
				return false;
			}
			return name.equals(((TestIcon)obj).name);
		}
		
		public int hashCode() {
			return name.hashCode();
		}
		
		public String toString() {
			return name;
		}
	}
}
