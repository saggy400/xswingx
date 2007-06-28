package org.jdesktop.xswingx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;

import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import org.jdesktop.xswingx.JXSearchField.SearchMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JXSearchFieldTest {
	boolean focused;
	boolean eventReceived;

	JXSearchField searchField;

	@Before
	public void setUp() throws Exception {
		searchField = new JXSearchField(){
			public boolean isFocusOwner() {
				return focused;
			}
		};
		searchField.setInstantSearchDelay(0);
	}
	
	@Test
	public void testDefaultPrompt() throws Exception {
		JXSearchField sf = new JXSearchField();
		assertSame(UIManager.get("SearchField.prompt"), sf.getPrompt());
		
		sf = new JXSearchField("prompt");
		assertEquals("prompt", sf.getPrompt());
	}
	
	@Test
	public void testDefaultSearchMode() throws Exception {
		JXSearchField sf = new JXSearchField();
		assertTrue(sf.isInstantSearchMode());
		assertSame(SearchMode.INSTANT, sf.getSearchMode());
	}

	@Test
	public void testFireAction() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				eventReceived = true;
			}
		});
		eventReceived = false;
		searchField.setInstantSearchDelay(0);
		searchField.setSearchMode(SearchMode.INSTANT);
		searchField.setText("search");
		Assert.assertTrue(eventReceived);
		
		eventReceived = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		searchField.setText("search2");
		assertFalse(eventReceived);
	}
	
	@Test
	public void testInstantSearchDelay() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				eventReceived = true;
			}
		});
		eventReceived = false;
		searchField.setSearchMode(SearchMode.INSTANT);
		searchField.setText("search");
		Assert.assertTrue(eventReceived);
		
		eventReceived = false;
		searchField.setInstantSearchDelay(100);
		assertSame(100, searchField.getInstantSearchDelay());
		
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
		
		searchField.setSearchPopupMenu(new JPopupMenu());
		searchField.setUseSeperatePopupButton(false);
		assertFalse(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getPopupButton().isVisible());
		
		searchField.setUseSeperatePopupButton(true);
		assertTrue(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getPopupButton().isVisible());
	}

	@Test
	public void testSearchMode() throws Exception {
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				eventReceived = true;
			}
		});
		assertTrue(searchField.isInstantSearchMode());
		assertSame(UIManager.getIcon("SearchField.icon"), searchField.getSearchButton().getIcon());
		assertNull(searchField.getSearchButton().getRolloverIcon());
		assertNull(searchField.getSearchButton().getPressedIcon());
		eventReceived = false;
		searchField.getSearchButton().doClick();
		assertFalse(eventReceived);
		searchField.setText("test");
		assertTrue(eventReceived);

		eventReceived = false;
		searchField.setSearchMode(SearchMode.REGULAR);
		assertTrue(searchField.isRegularSearchMode());
		assertSame(UIManager.getIcon("SearchField.rolloverIcon"), searchField.getSearchButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.pressedIcon"), searchField.getSearchButton().getPressedIcon());
		
		focused = true;
		searchField.getSearchButton().doClick();
		assertTrue(eventReceived);
	}

	@Test
	public void testEditableEnabled() throws Exception {
		searchField.setEditable(false);
		searchField.setEnabled(true);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertTrue(searchField.getSearchAction().isEnabled());
		assertTrue(searchField.getSearchButton().isEnabled());
		assertTrue(searchField.getPopupButton().isEnabled());

		searchField.setEditable(true);
		searchField.setEnabled(false);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertFalse(searchField.getSearchAction().isEnabled());
		assertFalse(searchField.getSearchButton().isEnabled());
		assertFalse(searchField.getPopupButton().isEnabled());

		searchField.setEditable(false);
		searchField.setEnabled(false);
		assertFalse(searchField.getClearAction().isEnabled());
		assertFalse(searchField.getClearButton().isEnabled());
		assertFalse(searchField.getSearchAction().isEnabled());
		assertFalse(searchField.getSearchButton().isEnabled());
		assertFalse(searchField.getPopupButton().isEnabled());

		searchField.setEditable(true);
		searchField.setEnabled(true);
		assertTrue(searchField.getClearAction().isEnabled());
		assertTrue(searchField.getClearButton().isEnabled());
		assertTrue(searchField.getSearchAction().isEnabled());
		assertTrue(searchField.getSearchButton().isEnabled());
		assertTrue(searchField.getPopupButton().isEnabled());
	}
	
	@Test
	public void testSetLayoutStyle() throws Exception {
		assertSame(LayoutStyle.MAC, searchField.getLayoutStyle());
		assertTrue(searchField.isMacLayoutStyle());
		assertFalse(searchField.getClearButton().isVisible());
		assertTrue(searchField.getSearchButton().isVisible());
		
		searchField.setText("test");
		assertTrue(searchField.getClearButton().isVisible());
		assertTrue(searchField.getSearchButton().isVisible());
		
		searchField.setLayoutStyle(LayoutStyle.VISTA);
		assertTrue(searchField.isVistaLayoutStyle());
		assertTrue(searchField.getClearButton().isVisible());
		assertFalse(searchField.getSearchButton().isVisible());
	}
	
	@Test
	public void testSearchButtonIcons() throws Exception {
		UIManager.put("SearchField.icon", new TestIconUI("SearchField.icon"));
		UIManager.put("SearchField.rolloverIcon", new TestIconUI("SearchField.rolloverIcon"));
		UIManager.put("SearchField.pressedIcon", new TestIconUI("SearchField.pressedIcon"));
		
		UIManager.put("SearchField.clearIcon", new TestIconUI("SearchField.clearIcon"));
		UIManager.put("SearchField.clearRolloverIcon", new TestIconUI("SearchField.clearRolloverIcon"));
		UIManager.put("SearchField.clearPressedIcon", new TestIconUI("SearchField.clearPressedIcon"));
		
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
		
		TestIcon diff = new TestIcon("different");
		searchField.getSearchButton().setRolloverIcon(diff);
		searchField.updateUI();
		assertSame(diff,
				searchField.getSearchButton().getRolloverIcon());
		searchField.getSearchButton().setPressedIcon(diff);
		searchField.updateUI();
		assertSame(diff,
				searchField.getSearchButton().getPressedIcon());
		searchField.getSearchButton().setIcon(diff);
		searchField.updateUI();
		assertSame(diff,
				searchField.getSearchButton().getIcon());
	}
	
	@Test
	public void testPopupIcons() throws Exception {
		UIManager.put("SearchField.popupIcon", new TestIconUI("SearchField.popupIcon"));
		UIManager.put("SearchField.popupRolloverIcon", new TestIconUI("SearchField.popupRolloverIcon"));
		UIManager.put("SearchField.popupPressedIcon", new TestIconUI("SearchField.popupPressedIcon"));
		searchField.updateUI();
		
		assertNotSame(UIManager.getIcon("SearchField.popupIcon"),
				searchField.getSearchButton().getIcon());
		assertNotSame(UIManager.getIcon("SearchField.popupRolloverIcon"),
				searchField.getSearchButton().getRolloverIcon());
		assertNotSame(UIManager.getIcon("SearchField.popupPressedIcon"),
				searchField.getSearchButton().getPressedIcon());
		
		searchField.setUseSeperatePopupButton(true);
		
		assertSame(UIManager.getIcon("SearchField.icon"),
				searchField.getSearchButton().getIcon());
		
		searchField.setSearchMode(SearchMode.REGULAR);
		assertSame(UIManager.getIcon("SearchField.rolloverIcon"),
				searchField.getSearchButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.pressedIcon"),
				searchField.getSearchButton().getPressedIcon());
		
		assertSame(UIManager.getIcon("SearchField.popupIcon"),
				searchField.getPopupButton().getIcon());
		assertSame(UIManager.getIcon("SearchField.popupRolloverIcon"),
				searchField.getPopupButton().getRolloverIcon());
		assertSame(UIManager.getIcon("SearchField.popupPressedIcon"),
				searchField.getPopupButton().getPressedIcon());
	}
	
	@Test
	public void testPopupButton() throws Exception {
		searchField.setSearchPopupMenu(new JPopupMenu());
		assertFalse(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getPopupButton().isVisible());
		
		assertFalse(searchField.isUseSeperatePopupButton());
		searchField.addPropertyChangeListener("useSeperatePopupButton", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				eventReceived = true;
			}
		});
		searchField.setUseSeperatePopupButton(true);
		assertTrue(eventReceived);
		assertTrue(searchField.getSearchButton().isVisible());
		assertTrue(searchField.getPopupButton().isVisible());
		
		searchField.setSearchPopupMenu(null);
		assertFalse(searchField.getPopupButton().isVisible());
		assertTrue(searchField.getSearchButton().isVisible());
	}
	
	@Test
	public void testSearchOnClick() throws Exception {
		searchField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				eventReceived = true;
			}
		});
		assertFalse(searchField.isRegularSearchMode());
		searchField.getSearchAction().actionPerformed(null);
		assertFalse(eventReceived);
		
		searchField.setSearchMode(SearchMode.REGULAR);
		focused = false;
		searchField.getSearchAction().actionPerformed(null);
		assertFalse(eventReceived);
		focused = true;
		searchField.getSearchAction().actionPerformed(null);
		assertTrue(eventReceived);
		
		eventReceived = false;
		searchField.setUseSeperatePopupButton(true);
		searchField.getSearchAction().actionPerformed(null);
		assertTrue(eventReceived);
	}
	
	@Test
	public void testSeperatePopupDefault() throws Exception {
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.FALSE);
		searchField.updateUI();
		assertFalse(searchField.isUseSeperatePopupButton());
		
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.TRUE);
		searchField.updateUI();
		assertTrue(searchField.isUseSeperatePopupButton());
		
		UIManager.put("SearchField.useSeperatePopupButton", Boolean.TRUE);
		searchField.setUseSeperatePopupButton(false);
		searchField.updateUI();
		assertFalse(searchField.isUseSeperatePopupButton());
	}
	
	@Test
	public void testLayoutDefault() throws Exception {
		UIManager.put("SearchField.layoutStyle", LayoutStyle.VISTA);
		searchField.updateUI();
		assertFalse(searchField.isMacLayoutStyle());
		
		UIManager.put("SearchField.layoutStyle", LayoutStyle.MAC);
		searchField.updateUI();
		assertFalse(searchField.isVistaLayoutStyle());
		
		UIManager.put("SearchField.layoutStyle", LayoutStyle.MAC);
		searchField.setLayoutStyle(LayoutStyle.VISTA);
		searchField.updateUI();
		assertTrue(searchField.isVistaLayoutStyle());
	}
	
	@Test
	public void testPromptFontStyleDefault() throws Exception {
		UIManager.put("SearchField.promptFontStyle", Font.ITALIC);
		searchField.updateUI();
		assertSame(Font.ITALIC, searchField.getPromptFontStyle());
		
		UIManager.put("SearchField.promptFontStyle", null);
		searchField.updateUI();
		assertNull(searchField.getPromptFontStyle());
		
		UIManager.put("SearchField.promptFontStyle", Font.BOLD);
		searchField.setPromptFontStyle(null);
		searchField.updateUI();
		assertNull(searchField.getPromptFontStyle());
	}
	
	@Test
	public void testButtonMarginDefault() throws Exception {
		UIManager.put("SearchField.buttonMargin", new InsetsUIResource(5,5,5,5));
		searchField.updateUI();
		assertSame(UIManager.get("SearchField.buttonMargin"), searchField.getButtonMargin());

		UIManager.put("SearchField.buttonMargin", new InsetsUIResource(5,5,5,5));
		searchField.setButtonMargin(new Insets(0,0,0,0));
		searchField.updateUI();
		assertNotSame(UIManager.get("SearchField.buttonMargin"), searchField.getButtonMargin());
	}
	
	@Test
	public void testBorder() throws Exception {
		Border newBorder = BorderFactory.createEmptyBorder();
		searchField.setBorder(newBorder);
		assertNotSame("Border should have been wrapped.", newBorder, searchField.getBorder());
	}
	
	class TestIcon implements Icon{
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
	
	class TestIconUI extends TestIcon implements UIResource{
		public TestIconUI(String name) {
			super(name);
		}
	}
}
