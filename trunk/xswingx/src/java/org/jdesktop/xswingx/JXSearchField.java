package org.jdesktop.xswingx;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.Document;

import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.xswingx.plaf.JXSearchFieldAddon;
import org.jdesktop.xswingx.plaf.basic.BasicSearchFieldUI;

/**
 * 
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class JXSearchField extends JXPromptField {
	private static final KeyStroke CLEAR_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_ESCAPE, 0);

	/**
	 * Defines, how the search and clear button are layouted.
	 */
	public enum LayoutStyle {
		/**
		 * <p>
		 * In VISTA layout style, the search button is placed on the right side
		 * of the search field. If text is entered, the search button is
		 * replaced by the clear button when the actual search mode is
		 * {@link SearchMode#INSTANT}. When the search mode is
		 * {@link SearchMode#REGULAR} the search button will always stay visible
		 * and the clear button will never be shown. However, 'escape' can still
		 * be pressed to invoke the clear action.
		 * </p>
		 * <p>
		 * TODO: A search popup menu is not supported in VISTA layout style.
		 * Consider adding a seperate popup button.
		 * </p>
		 */
		VISTA,
		/**
		 * <p>
		 * In MAC layout style, the search button is placed on the left side of
		 * the search field and the clear button on the right side. The clear
		 * button is only visible when text is present.
		 * </p>
		 * <p>
		 * If a search popup menu is installed, the search button will trigger
		 * the popup, and it's icon will be set to the UI property
		 * "SearchField.popupIcon".
		 * </p>
		 */
		MAC
	};

	public enum SearchMode {
		/**
		 * <p>
		 * In REGULAR search mode, an action event is fired, when the user
		 * presses enter or clicks the search button.
		 * </p>
		 * <p>
		 * However, if a search popup menu is set and layout style is
		 * {@link LayoutStyle#MAC}, no action will be fired, when the search
		 * button is clicked, because instead the popup menu is shown. A search
		 * can therefore only be triggered, by pressing the enter key.
		 * </p>
		 * <p>
		 * The search button can have a rollover and a pressed icon, defined by
		 * the "SearchField.rolloverIcon" and "SearchField.pressedIcon" UI
		 * properties. When a search popup menu is set,
		 * "SearchField.popupRolloverIcon" and "SearchField.popupPressedIcon"
		 * are used.
		 * </p>
		 * 
		 */
		REGULAR,
		/**
		 * In INSTANT search mode, an action event is fired, when the user
		 * presses enter or changes the search text. No rollover and pressed
		 * icon is used for the search button.
		 */
		INSTANT
	}

	// ensure at least the default ui is registered
	static {
		LookAndFeelAddons.contribute(new JXSearchFieldAddon());
	}

	private PropertyChangeHandler propertyChangeHandler = new PropertyChangeHandler();

	private ClearAction clearAction;

	private SearchAction searchAction;

	private JButton searchButton;

	private JButton clearButton;

	private Insets buttonMargin;

	private LayoutStyle layoutStyle = LayoutStyle.MAC;

	private SearchMode searchMode = SearchMode.INSTANT;

	private JPopupMenu searchPopupMenu;

	public JXSearchField() {
		// We cannot register the ClearTextAction through the Input- and
		// ActionMap because ToolTipManager registers the escape key with an
		// action that hides the tooltip every time the tooltip is changed and
		// then the ClearTextAction will never be called.
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (CLEAR_KEY.equals(KeyStroke.getKeyStroke(e.getKeyCode(), e
						.getModifiers()))) {
					getClearAction().clear();
				}
			}
		});
		propertyChangeHandler.install();
	}

	protected void installPromptSupport(String labelText, Color labelTextColor) {
		// don't! Handled by setUI
	}

	public SearchMode getSearchMode() {
		return searchMode;
	}

	public boolean isInstantSearchMode() {
		return SearchMode.INSTANT.equals(getSearchMode());
	}

	public boolean isRegularSearchMode() {
		return SearchMode.REGULAR.equals(getSearchMode());
	}

	public void setSearchMode(SearchMode searchMode) {
		firePropertyChange("searchMode", this.searchMode,
				this.searchMode = searchMode);
	}

	public LayoutStyle getLayoutStyle() {
		return layoutStyle;
	}

	public boolean isVistaLayoutStyle() {
		return LayoutStyle.VISTA.equals(getLayoutStyle());
	}

	public boolean isMacLayoutStyle() {
		return LayoutStyle.MAC.equals(getLayoutStyle());
	}

	public void setLayoutStyle(LayoutStyle layoutStyle) {
		firePropertyChange("layoutStyle", this.layoutStyle,
				this.layoutStyle = layoutStyle);
	}

	public Insets getButtonMargin() {
		return buttonMargin;
	}

	public void setButtonMargin(Insets buttonMargin) {
		firePropertyChange("buttonMargin", this.buttonMargin,
				this.buttonMargin = buttonMargin);
	}

	public ClearAction getClearAction() {
		if (clearAction == null) {
			clearAction = createClearAction();
		}
		return clearAction;
	}

	protected ClearAction createClearAction() {
		return new ClearAction();
	}

	public JButton getClearButton() {
		if (clearButton == null) {
			clearButton = createClearButton();
		}
		return clearButton;
	}

	protected JButton createClearButton() {
		IconButton btn = new IconButton();
		btn.setAction(getClearAction());

		return btn;
	}

	public SearchAction getSearchAction() {
		if (searchAction == null) {
			searchAction = createSearchAction();
		}
		return searchAction;
	}

	protected SearchAction createSearchAction() {
		return new SearchAction();
	}

	public JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = createSearchButton();
		}
		return searchButton;
	}

	protected JButton createSearchButton() {
		final IconButton searchButton = new IconButton();
		searchButton.setAction(getSearchAction());

		return searchButton;
	}

	public void setEditable(boolean b) {
		super.setEditable(b);
		updateButtonState();
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtonState();
	}

	private void updateButtonState() {
		getClearAction().setEnabled(isEditable() & isEnabled());
		getSearchAction().setEnabled(isEnabled());
	}

	/**
	 * <p>
	 * Sets the menu, which will be displayed when the user presses the search
	 * button. The icon of the search button will be changed to the
	 * {@link UIDefaults} value "SearchField.icon", if
	 * <code>searchPopupMenu</code> is <code>null</code>, or
	 * "SearchField.popupIcon" otherwise.
	 * </p>
	 * <p>
	 * We could use the <code>searchButton</code>s
	 * <code>componentPopupMenu</code> property instead of introducing another
	 * property, if {@link JComponent#setComponentPopupMenu(JPopupMenu)} would
	 * just fire a {@link PropertyChangeEvent}...
	 * </p>
	 * 
	 * @param searchPopupMenu
	 */
	public void setSearchPopupMenu(JPopupMenu searchPopupMenu) {
		firePropertyChange("searchPopupMenu", this.searchPopupMenu,
				this.searchPopupMenu = searchPopupMenu);
	}

	public JPopupMenu getSearchPopupMenu() {
		return searchPopupMenu;
	}

	public void setUI(TextUI ui) {
		if (ui instanceof BasicSearchFieldUI) {
			super.setUI(ui);
		} else {
			setUI(new BasicSearchFieldUI(ui));
		}
	}
	
	public void updateUI() {
		super.updateUI();
		if(getSearchPopupMenu() != null){
			getSearchPopupMenu().updateUI();
		}
	}

	public static class IconButton extends JButton {
		public IconButton() {
			setFocusable(false);
			setMargin(BasicSearchFieldUI.NO_INSETS);

			// Windows UI will add 1 pixel for width and height, if this is true
			setFocusPainted(false);

			setBorderPainted(false);
			setContentAreaFilled(false);
			setIconTextGap(0);

			setBorder(null);

			setOpaque(false);

			setCursor(Cursor.getDefaultCursor());
		}

		// Windows UI overrides Insets.
		// Who knows what other UIs are doing...
		public Insets getInsets() {
			return BasicSearchFieldUI.NO_INSETS;
		}

		public Insets getInsets(Insets insets) {
			return getInsets();
		}

		public Insets getMargin() {
			return getInsets();
		}

		public void setBorder(Border border) {
			// Don't let Motif overwrite my Border
			super.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	class ClearAction extends AbstractAction {
		public ClearAction() {
			putValue(SHORT_DESCRIPTION, "Clear Search Text");
		}

		/**
		 * Calls {@link #clear()}.
		 */
		public void actionPerformed(ActionEvent e) {
			clear();
		}

		/**
		 * Sets the search field's text to <code>null </code> and requests the
		 * focus for the search field.
		 */
		public void clear() {
			setText(null);
			requestFocusInWindow();
		}
	}

	public class SearchAction extends AbstractAction {
		public SearchAction() {
		}

		/**
		 * If installed, displays the search popup menu beneath the search
		 * button. Otherwise posts an action event. Then requests the focus for
		 * the search field.
		 */
		public void actionPerformed(ActionEvent e) {
			if (getSearchPopupMenu() != null) {
				Rectangle r = SwingUtilities.getLocalBounds(searchButton);

				getSearchPopupMenu().show(searchButton, r.x, r.y + r.height);
			} else if (isRegularSearchMode()) {
				postActionEvent();
			}
			requestFocusInWindow();
		}
	}

	class PropertyChangeHandler implements PropertyChangeListener,
			DocumentListener {

		public void install() {
			install(getDocument());
			addPropertyChangeListener(this);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String prop = evt.getPropertyName();

			if ("document".equals(prop)) {
				Document doc = (Document) evt.getOldValue();
				if (doc != null) {
					uninstall(doc);
				}
				doc = (Document) evt.getNewValue();
				if (doc != null) {
					install(doc);
				}
			} else if ("searchMode".equals(prop) || "layoutStyle".equals(prop)) {
				updateButtonVisibility();
			}
		}

		public void install(Document doc) {
			doc.addDocumentListener(this);
			update();
		}

		private void uninstall(Document doc) {
			doc.removeDocumentListener(this);
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

		private void update() {
			if (isInstantSearchMode()) {
				postActionEvent();
			}

			updateButtonVisibility();
		}

		private void updateButtonVisibility() {
			if (clearButton != null) {
				clearButton
						.setVisible((!isRegularSearchMode() || isMacLayoutStyle())
								&& getText() != null && getText().length() > 0);
			}
			if (searchButton != null) {
				if (isVistaLayoutStyle()) {
					searchButton.setVisible(!clearButton.isVisible());
				} else {
					searchButton.setVisible(true);
				}
			}
		}
	}
}
