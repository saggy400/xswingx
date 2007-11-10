package org.jdesktop.xswingx.plaf.basic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.Document;

import org.jdesktop.xswingx.BuddySupport;
import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.SearchFieldSupport;
import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import org.jdesktop.xswingx.plaf.BuddyLayoutAndBorder;
import org.jdesktop.xswingx.plaf.BuddyTextFieldUI;
import org.jdesktop.xswingx.plaf.JXSearchFieldAddon;

/**
 * The default {@link JXSearchField} UI delegate.
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class BasicSearchFieldUI extends BuddyTextFieldUI {
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
	 * Calls {@link #installDefaults()}, adds the search, clear and popup
	 * button to the search field and registers a {@link PropertyChangeListener}
	 * ad {@link DocumentListener} and an {@link ActionListener} on the popup
	 * button.
	 */
	public void installUI(JComponent c) {
		searchField = (JXSearchField) c;

		super.installUI(c);

		if (!isNativeSearchField()) {
			installDefaults();

			layoutButtons();

			popupButton().addActionListener(getHandler());
			searchField.addPropertyChangeListener(getHandler());
		}
		// add support for instant search mode in any case.
		searchField.getDocument().addDocumentListener(getHandler());
	}

	private boolean isNativeSearchField() {
		return SearchFieldSupport.isNativeSearchFieldSupported();
	}

	@Override
	protected BuddyLayoutAndBorder createBuddyLayoutAndBorder(JTextField c) {
		return new BuddyLayoutAndBorder(c) {
			/**
			 * This does nothing, if the search field is rendered natively on
			 * Leopard.
			 */
			@Override
			protected void replaceBorderIfNecessary() {
				if(!isNativeSearchField()){
					super.replaceBorderIfNecessary();
				}
			}

			/**
			 * Return zero, when the search field is rendered natively on
			 * Leopard, to make painting work correctly.
			 */
			@Override
			public Dimension preferredLayoutSize(Container parent) {
				if (isNativeSearchField()) {
					return new Dimension();
				} else {
					return super.preferredLayoutSize(parent);
				}
			}

			/**
			 * Include the clear button, to prevent 'jumping' when text is
			 * entered, when layout style is Mac.
			 */
			@Override
			public Insets getBorderInsets(Component c) {
				Insets insets = super.getBorderInsets(c);
				if (searchField != null && !clearButton().isVisible() && isMacLayoutStyle()) {
					insets.right += clearButton().getPreferredSize().width;
				}
				return insets;
			}
		};
	}

	private void layoutButtons() {
		searchField.removeAll();

		searchField.add(clearButton(), BuddySupport.RIGHT);

		if (isMacLayoutStyle()) {
			searchField.add(searchButton(), BuddySupport.LEFT);
		} else {
			searchField.add(searchButton(), BuddySupport.RIGHT);
		}

		if (usingSeperatePopupButton()) {
			searchField.add(BuddySupport.createGap(getPopupOffset()), BuddySupport.RIGHT);
		}

		if (usingSeperatePopupButton() || !isMacLayoutStyle()) {
			searchField.add(popupButton(), BuddySupport.RIGHT);
		} else {
			searchField.add(popupButton(), BuddySupport.LEFT);
		}
	}

	private boolean isMacLayoutStyle() {
		return searchField.getLayoutStyle() == LayoutStyle.MAC;
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

		if (shouldReplaceResource(searchField.getOuterMargin())) {
			searchField.setOuterMargin(UIManager.getInsets("SearchField.buttonMargin"));
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
		searchField.removeAll();
		searchField = null;
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
		clearButton().setVisible((!searchField.isRegularSearchMode() || searchField.isMacLayoutStyle()) && hasText());

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

	private boolean hasText() {
		return searchField.getText() != null && searchField.getText().length() > 0;
	}

	class Handler implements PropertyChangeListener, ActionListener, DocumentListener {
		public void propertyChange(PropertyChangeEvent evt) {
			String prop = evt.getPropertyName();
			Object src = evt.getSource();

			if (src.equals(searchField)) {
				if ("searchPopupMenu".equals(prop) || "searchMode".equals(prop)
						|| "useSeperatePopupButton".equals(prop) || "searchMode".equals(prop)
						|| "layoutStyle".equals(prop)) {
					layoutButtons();
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
