package org.jdesktop.xswingx.plaf.basic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.text.Document;

import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.plaf.JXSearchFieldAddon;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;

/**
 * The default {@link JXSearchField} UI delegate.
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class BasicSearchFieldUI extends PromptTextFieldUI {
	/**
	 * The search field that we're a UI delegate for. Initialized by the
	 * <code>installUI</code> method, and reset to null by
	 * <code>uninstallUI</code>.
	 * 
	 * @see #installUI
	 * @see #uninstallUI
	 */
	protected JXSearchField searchField;

	private Handler handler;

	public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

	public BasicSearchFieldUI(TextUI delegate) {
		super(delegate);
	}

	private Handler getHandler() {
		if (handler == null) {
			handler = new Handler();
		}
		return handler;
	}

	/**
	 * Create a {@link LayoutManager} that manages the <code>searchButton</code>,
	 * <code>clearButton</code>, and <code>popupButton</code> children of
	 * the {@link JXSearchField}. Override to implement a custom
	 * {@link LayoutManager}.
	 * 
	 * @return a LayoutManager for the search button, clear button, and popup
	 *         button.
	 * @see JXSearchField#getSearchButton()
	 * @see JXSearchField#getSearchButton()
	 * @see JXSearchField#getSearchButton()
	 */
	protected LayoutManager createLayout() {
		return getHandler();
	}

	/**
	 * Calls {@link #installDefaults()}, adds the search, clear and popup
	 * button to the search field and registers a {@link PropertyChangeListener}
	 * ad {@link DocumentListener} and an {@link ActionListener} on the popup
	 * button.
	 */
	public void installUI(JComponent c) {
		super.installUI(c);
		searchField = (JXSearchField) c;

		installDefaults();

		searchField.add(searchButton());
		searchField.add(clearButton());
		searchField.add(popupButton());

		popupButton().addActionListener(getHandler());
		searchField.getDocument().addDocumentListener(getHandler());
		searchField.addPropertyChangeListener(getHandler());
	}

	/**
	 * Initialize the search fields various properties based on the
	 * corresponding "SearchField.*" properties from defaults table. The
	 * {@link JXSearchField}s layout is set to the value returned by
	 * <code>createLayout</code>. Also calls
	 * {@link #replaceBorderIfNecessary()} and {@link #updateButtons()}. This
	 * method is called by {@link #installUI(JComponent)}.
	 * 
	 * @see #installUI
	 * @see #createLayout
	 * @see JXSearchField#customSetUIProperty(String, Object)
	 */
	protected void installDefaults() {
		if (UIManager.getBoolean("SearchField.useSeperatePopupButton")) {
			searchField.customSetUIProperty("useSeperatePopupButton", Boolean.TRUE);
		} else {
			searchField.customSetUIProperty("useSeperatePopupButton", Boolean.FALSE);
		}

		searchField.customSetUIProperty("layoutStyle", UIManager.get("SearchField.layoutStyle"));
		searchField.customSetUIProperty("promptFontStyle", UIManager.get("SearchField.promptFontStyle"));

		if (shouldReplaceResource(searchField.getButtonMargin())) {
			searchField.setButtonMargin(UIManager.getInsets("SearchField.buttonMargin"));
		}

		updateButtons();

		if (shouldReplaceResource(clearButton().getIcon())) {
			clearButton().setIcon(UIManager.getIcon("SearchField.clearIcon"));
		}
		if (shouldReplaceResource(clearButton().getPressedIcon())) {
			clearButton().setPressedIcon(UIManager.getIcon("SearchField.clearPressedIcon"));
		}
		if (shouldReplaceResource(clearButton().getRolloverIcon())) {
			clearButton().setRolloverIcon(UIManager.getIcon("SearchField.clearRolloverIcon"));
		}

		searchButton().setIcon(getNewIcon(searchButton().getIcon(), "SearchField.icon"));

		popupButton().setIcon(getNewIcon(popupButton().getIcon(), "SearchField.popupIcon"));
		popupButton().setRolloverIcon(getNewIcon(popupButton().getRolloverIcon(), "SearchField.popupRolloverIcon"));
		popupButton().setPressedIcon(getNewIcon(popupButton().getPressedIcon(), "SearchField.popupPressedIcon"));

		replaceBorderIfNecessary();
		searchField.setLayout(createLayout());
	}

	/**
	 * Removes all installed listeners, the layout and resets the search field
	 * original border and removes all children.
	 */
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		searchField.removePropertyChangeListener(getHandler());
		searchField.getDocument().removeDocumentListener(getHandler());
		popupButton().removeActionListener(getHandler());

		searchField.setLayout(null);
		searchField.setBorder(getHandler().borderDelegate);
		searchField.removeAll();
		searchField = null;
	}

	/**
	 * Wraps and replaces the search fields defaut border with
	 * {@link #getHandler()} if necessary, to honor the button margins and sizes
	 * of the search, clear and popup buttons and the layout style.
	 */
	protected void replaceBorderIfNecessary() {
		Border original = searchField.getBorder();

		if (!(original instanceof Handler)) {
			getHandler().borderDelegate = original;
			searchField.setBorder(getHandler());
		}
	}

	/**
	 * Returns true if <code>o</code> is <code>null</code> or of instance
	 * {@link UIResource}.
	 * 
	 * @param o
	 *            an object
	 * @return true if <code>o</code> is <code>null</code> or of instance
	 *         {@link UIResource}
	 */
	protected boolean shouldReplaceResource(Object o) {
		return o == null || o instanceof UIResource;
	}

	/**
	 * Convience method for only replacing icons if they have not been
	 * customized by the user. Returns the icon from the defaults table
	 * belonging to <code>resKey</code>, if
	 * {@link #shouldReplaceResource(Object)} with the <code>icon</code> as a
	 * parameter returns <code>true</code>. Otherwise returns
	 * <code>icon</code>.
	 * 
	 * @param icon
	 *            the current icon
	 * @param resKey
	 *            the resource key identifying the default icon
	 * @return the new icon
	 */
	protected Icon getNewIcon(Icon icon, String resKey) {
		Icon uiIcon = UIManager.getIcon(resKey);
		if (shouldReplaceResource(icon)) {
			return uiIcon;
		}
		return icon;
	}

	/**
	 * Convienence method.
	 * 
	 * @see JXSearchField#getClearButton()
	 * @return the clear button
	 */
	protected final JButton clearButton() {
		return searchField.getClearButton();
	}

	/**
	 * Convienence method.
	 * 
	 * @see JXSearchField#getSearchButton()
	 * @return the search button
	 */
	protected final JButton searchButton() {
		return searchField.getSearchButton();
	}

	/**
	 * Convienence method.
	 * 
	 * @see JXSearchField#getPopupButton()
	 * @return the popup button
	 */
	protected final JButton popupButton() {
		return searchField.getPopupButton();
	}

	/**
	 * Returns <code>true</code> if
	 * {@link JXSearchField#isUseSeperatePopupButton()} is <code>true</code>
	 * and a search popup menu has been set.
	 * 
	 * @return the popup button is used in addition to the search button
	 */
	public boolean usingSeperatePopupButton() {
		return searchField.isUseSeperatePopupButton() && searchField.getSearchPopupMenu() != null;
	}

	/**
	 * Returns the number of pixels between the popup button and the clear (or
	 * search) button as specified in the default table by
	 * 'SearchField.popupOffset'. Returns 0 if
	 * {@link #usingSeperatePopupButton()} returns <code>false</code>
	 * 
	 * @return number of pixels between the popup button and the clear (or
	 *         search) button
	 */
	protected int getPopupOffset() {
		if (usingSeperatePopupButton()) {
			return UIManager.getInt("SearchField.popupOffset");
		}
		return 0;
	}

	/**
	 * Sets the visibility of the search, clear and popup buttons depending on
	 * the search mode, layout stye, search text, search popup menu and the use
	 * of a seperate popup button. Also resets the search buttons pressed and
	 * rollover icons if the search field is in regular search mode or clears
	 * the icons when the search field is in instant search mode.
	 */
	protected void updateButtons() {
		clearButton().setVisible(
				(!searchField.isRegularSearchMode() || searchField.isMacLayoutStyle()) && searchField.getText() != null
						&& searchField.getText().length() > 0);

		boolean clearNotHere = (searchField.isMacLayoutStyle() || !clearButton().isVisible());

		searchButton().setVisible(
				(searchField.getSearchPopupMenu() == null || usingSeperatePopupButton()) && clearNotHere);
		popupButton().setVisible(
				searchField.getSearchPopupMenu() != null && (clearNotHere || usingSeperatePopupButton()));

		if (searchField.isRegularSearchMode()) {
			searchButton().setRolloverIcon(getNewIcon(searchButton().getRolloverIcon(), "SearchField.rolloverIcon"));
			searchButton().setPressedIcon(getNewIcon(searchButton().getPressedIcon(), "SearchField.pressedIcon"));
		} else {
			// no action, therefore no rollover icon.
			if (shouldReplaceResource(searchButton().getRolloverIcon())) {
				searchButton().setRolloverIcon(null);
			}
			if (shouldReplaceResource(searchButton().getPressedIcon())) {
				searchButton().setPressedIcon(null);
			}
		}
	}

	/**
	 * Overriden to increase the preferred height if necessary to fit the
	 * search, clear or popup button into the search field.
	 */
	public Dimension getPreferredSize(JComponent c) {
		Dimension d = (Dimension) super.getPreferredSize(c);
		if (searchField == null) {
			return d;
		}

		Insets insets = getHandler().getRealBorderInsets();
		Insets margin = searchField.getButtonMargin();

		int buttonHeight = Math.max(popupButton().isVisible() ? popupButton().getPreferredSize().height : 0, Math.max(
				searchButton().isVisible() ? searchButton().getPreferredSize().height : 0, clearButton()
						.getPreferredSize().height));

		if (insets != null) {
			buttonHeight += insets.top + insets.bottom;
		}
		if (margin != null) {
			buttonHeight += margin.top + margin.bottom;
		}
		d.height = Math.max(d.height, buttonHeight);

		return d;
	}

	class Handler implements Border, LayoutManager, PropertyChangeListener, ActionListener, DocumentListener {
		private Border borderDelegate;

		/**
		 * Returns the {@link Insets} of the original {@link Border} plus the
		 * space required by the search, clear, and popup buttons and also the
		 * button margin and the popup offset.
		 * 
		 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
		 */
		public Insets getBorderInsets(Component c) {
			if (searchField == null) {
				return NO_INSETS;
			}

			// Original insets are cloned to make it work in Mac OS X Aqua LnF.
			// Seems that this LnF uses a shared insets instance which should
			// not be modified.
			// Include margin here
			Insets insets = borderDelegate != null ? (Insets) borderDelegate.getBorderInsets(c).clone() : NO_INSETS;
			if (searchField.isVistaLayoutStyle()) {
				insets.right += Math.max(searchOrPopupButtonWidth(), clearButtonWidth());
			} else {
				insets.left += searchOrPopupButtonWidth();
				insets.right += clearButtonWidth();
			}

			if (usingSeperatePopupButton()) {
				insets.right += popupButton().getPreferredSize().getWidth() + getPopupOffset();
			}

			Insets margin = searchField.getButtonMargin();
			if (margin != null) {
				insets.top += margin.top;
				insets.left += margin.left;
				insets.bottom += margin.bottom;
				insets.right += margin.right;
			}
			return insets;
		}

		private int searchOrPopupButtonWidth() {
			if (searchField.isUseSeperatePopupButton() || searchField.getSearchPopupMenu() == null) {
				return searchButton().getPreferredSize().width;
			}
			return popupButton().getPreferredSize().width;
		}

		private int clearButtonWidth() {
			return clearButton() != null ? clearButton().getPreferredSize().width : 0;
		}

		/**
		 * Returns the insets of the original border (without the margin! Beware
		 * of {@link MarginBorder}!).
		 * 
		 * @return the insets of the border delegate
		 */
		public Insets getRealBorderInsets() {
			if (borderDelegate == null) {
				return null;
			}
			Insets insets = (Insets) borderDelegate.getBorderInsets(searchField);

			if (borderDelegate instanceof MarginBorder) {
				// don't include margin!!
				Insets margin = searchField.getMargin();
				if (margin != null) {
					insets.left -= margin.left;
					insets.right -= margin.right;
					insets.top -= margin.top;
					insets.bottom -= margin.bottom;
				}
			}

			return insets;
		}
		/**
		 * Returns the rectangle allocated by the search fields text.
		 * 
		 * @return the rectangle allocated by the search fields text
		 */
		protected Rectangle getVisibleRect() {
			Rectangle alloc = (Rectangle) SwingUtilities.getLocalBounds(searchField);
			Insets insets = getRealBorderInsets();

			if (insets != null) {
				alloc.x += insets.left;
				alloc.y += insets.top;
				alloc.width -= insets.left + insets.right;
				alloc.height -= insets.top + insets.bottom;
			}

			Insets margin = searchField.getButtonMargin();
			if (margin != null) {
				alloc.x += margin.left;
				alloc.y += margin.top;
				alloc.width -= margin.left + margin.right;
				alloc.height -= margin.top + margin.bottom;
			}

			return alloc;
		}

		public void layoutContainer(Container parent) {
			Rectangle visibleRect = getVisibleRect();

			if (usingSeperatePopupButton() && searchField.getSearchPopupMenu() != null) {
				layoutRight(visibleRect, popupButton());
				visibleRect.width -= popupButton().getPreferredSize().width + getPopupOffset();
			} else {
				layoutSearchOrPopup(visibleRect, popupButton());
			}

			layoutSearchOrPopup(visibleRect, searchButton());
			layoutRight(visibleRect, clearButton());
		}

		private void layoutSearchOrPopup(Rectangle visibleRect, JButton btn) {
			if (searchField.isVistaLayoutStyle()) {
				layoutRight(visibleRect, btn);
			} else {
				layoutLeft(visibleRect, btn);
			}
		}

		protected void layoutRight(Rectangle visibleRect, JButton btn) {
			Dimension size = btn.getPreferredSize();
			btn.setBounds(visibleRect.x + visibleRect.width - size.width, centerY(visibleRect, size), size.width,
					size.height);
		}

		protected void layoutLeft(Rectangle visibleRect, JButton btn) {
			Dimension size = btn.getPreferredSize();
			btn.setBounds(visibleRect.x, centerY(visibleRect, size), size.width, size.height);
		}

		protected int centerY(Rectangle rect, Dimension size) {
			return (int) (rect.getCenterY() - (size.height / 2));
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String prop = evt.getPropertyName();
			Object src = evt.getSource();

			if (src.equals(searchField)) {
				if ("border".equals(prop)) {
					replaceBorderIfNecessary();
				} else if ("searchPopupMenu".equals(prop) || "searchMode".equals(prop)
						|| "useSeperatePopupButton".equals(prop) || "searchMode".equals(prop)
						|| "layoutStyle".equals(prop)) {
					updateButtons();
				} else if ("document".equals(prop)) {
					Document doc = (Document) evt.getOldValue();
					if (doc != null) {
						doc.removeDocumentListener(this);
					}
					doc = (Document) evt.getNewValue();
					if (doc != null) {
						doc.addDocumentListener(this);
					}
				}
			}
		}

		public void addLayoutComponent(String name, Component c) {
			// do nothing
		}

		public void removeLayoutComponent(Component c) {
			// do nothing
		}

		public Dimension minimumLayoutSize(Container parent) {
			return null;
		}

		public boolean isBorderOpaque() {
			if (borderDelegate == null) {
				return false;
			}
			return borderDelegate.isBorderOpaque();
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			if (borderDelegate != null) {
				borderDelegate.paintBorder(c, g, x, y, width, height);
			}
		}

		public Dimension preferredLayoutSize(Container parent) {
			return null; // never called
		}

		/**
		 * Shows the search popup menu, if installed.
		 */
		public void actionPerformed(ActionEvent e) {
			if (searchField.getSearchPopupMenu() != null) {
				Component src = JXSearchFieldAddon.SEARCH_FIELD_SOURCE.equals(UIManager
						.getString("SearchField.popupSource")) ? searchField : (Component) e.getSource();

				Rectangle r = SwingUtilities.getLocalBounds(src);
				int popupWidth = searchField.getSearchPopupMenu().getPreferredSize().width;
				int x = searchField.isVistaLayoutStyle() || usingSeperatePopupButton() ? r.x + r.width - popupWidth
						: r.x;
				searchField.getSearchPopupMenu().show(src, x, r.y + r.height);
			}
		}

		public void changedUpdate(DocumentEvent e) {
			update();
		}

		public void insertUpdate(DocumentEvent e) {
			update();
		}

		public void removeUpdate(DocumentEvent e) {
			update();
		}

		/**
		 * Called when the search text changes. Calls
		 * {@link JXSearchField#postActionEvent()} In instant search mode or
		 * starts the search field instant search timer if the instant search
		 * delay is greater 0.
		 */
		private void update() {
			if (searchField.isInstantSearchMode()) {
				searchField.getInstantSearchTimer().stop();
				// only use timer when delay greater 0.
				if (searchField.getInstantSearchDelay() > 0) {
					searchField.getInstantSearchTimer().setInitialDelay(searchField.getInstantSearchDelay());
					searchField.getInstantSearchTimer().start();
				} else {
					searchField.postActionEvent();
				}
			}

			updateButtons();
		}
	}
}
