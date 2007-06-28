package org.jdesktop.xswingx;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.TextUI;
import javax.swing.text.Document;

import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.xswingx.plaf.JXSearchFieldAddon;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;
import org.jdesktop.xswingx.plaf.basic.BasicSearchFieldUI;

/**
 * A text field with a search icon in which the user enters text that identifies
 * items to search for.
 * 
 * JXSearchField almost looks and behaves like a native Windows Vista search
 * box, a Mac OS X search field, or a search field like the one used in Mozilla
 * Thunderbird 2.0 - depending on the current look and feel.
 * 
 * JXSearchField is a text field that contains a search button and a clear
 * button. The search button normally displays a lens icon appropriate for the
 * current look and feel. The clear button is used to clear the text and
 * therefore only visible when text is present. It normally displays a 'x' like
 * icon. Text can also be cleared, using the 'Esc' key.
 * 
 * The position of the search and clear buttons can be customized by either
 * changing the search fields (text) margin or button margin, or by changing the
 * {@link LayoutStyle}.
 * 
 * JXSearchField supports two different search modes: {@link SearchMode#INSTANT}
 * and {@link SearchMode#REGULAR}.
 * 
 * A search can be performed by registering an {@link ActionListener}. The
 * {@link ActionEvent}s command property contains the text to search for. The
 * search should be cancelled, when the command text is empty or null.
 * 
 * @see RecentSearches
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class JXSearchField extends JXPromptField {
	/**
	 * The default instant search delay.
	 */
	private static final int DEFAULT_INSTANT_SEARCH_DELAY = 50;
	/**
	 * The key used to invoke the clear action.
	 */
	private static final KeyStroke CLEAR_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

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
		 * and the clear button will never be shown. However, 'Escape' can still
		 * be pressed to clear the text.
		 * </p>
		 */
		VISTA,
		/**
		 * <p>
		 * In MAC layout style, the search button is placed on the left side of
		 * the search field and the clear button on the right side. The clear
		 * button is only visible when text is present.
		 * </p>
		 */
		MAC
	};

	/**
	 * Defines when action events are posted.
	 */
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
		 * presses enter or changes the search text.
		 * 
		 * The action event is delayed about the number of milliseconds
		 * specified by {@link JXSearchField#getInstantSearchDelay()}.
		 * 
		 * No rollover and pressed icon is used for the search button.
		 */
		INSTANT
	}

	// ensure at least the default ui is registered
	static {
		LookAndFeelAddons.contribute(new JXSearchFieldAddon());
	}

	private ClearAction clearAction;

	private SearchAction searchAction;

	private JButton searchButton;

	private JButton clearButton;

	private JButton popupButton;

	private Insets buttonMargin;

	private LayoutStyle layoutStyle;

	private SearchMode searchMode = SearchMode.INSTANT;

	private JPopupMenu searchPopupMenu;

	private boolean useSeperatePopupButton;

	private boolean useSeperatePopupButtonSet;

	private boolean layoutStyleSet;

	private int instantSearchDelay = DEFAULT_INSTANT_SEARCH_DELAY;

	private boolean promptFontStyleSet;

	private Timer instantSearchTimer;

	/**
	 * Creates a new search field with a default prompt.
	 */
	public JXSearchField() {
		this(UIManager.getString("SearchField.prompt"));
	}

	/**
	 * Creates a new search field with the given prompt and
	 * {@link SearchMode#INSTANT}.
	 * 
	 * @param prompt
	 */
	public JXSearchField(String prompt) {
		setPrompt(prompt);

		// We cannot register the ClearAction through the Input- and
		// ActionMap because ToolTipManager registers the escape key with an
		// action that hides the tooltip every time the tooltip is changed and
		// then the ClearAction will never be called.
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (CLEAR_KEY.equals(KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers()))) {
					getClearAction().clear();
				}
			}
		});
	}

	/**
	 * Overriden to prevent installation of {@link PromptSupport}. This is
	 * handled by our UI class {@link BasicSearchFieldUI}, which is a
	 * {@link PromptTextFieldUI}.
	 * 
	 * @see #setUI(TextUI)
	 */
	protected void installPromptSupport(String promptText, Color promptForeground, Color promptBackground) {
		// don't! Handled by setUI
	}

	/**
	 * Returns the current {@link SearchMode}.
	 * 
	 * @return the current {@link SearchMode}.
	 */
	public SearchMode getSearchMode() {
		return searchMode;
	}

	/**
	 * Returns <code>true</code> if the current {@link SearchMode} is
	 * {@link SearchMode#INSTANT}.
	 * 
	 * @return <code>true</code> if the current {@link SearchMode} is
	 *         {@link SearchMode#INSTANT}
	 */
	public boolean isInstantSearchMode() {
		return SearchMode.INSTANT.equals(getSearchMode());
	}

	/**
	 * Returns <code>true</code> if the current {@link SearchMode} is
	 * {@link SearchMode#REGULAR}.
	 * 
	 * @return <code>true</code> if the current {@link SearchMode} is
	 *         {@link SearchMode#REGULAR}
	 */
	public boolean isRegularSearchMode() {
		return SearchMode.REGULAR.equals(getSearchMode());
	}

	/**
	 * Sets the current search mode. See {@link SearchMode} for a description of
	 * the different search modes.
	 * 
	 * @param searchMode
	 *            {@link SearchMode#INSTANT} or {@link SearchMode#REGULAR}
	 */
	public void setSearchMode(SearchMode searchMode) {
		firePropertyChange("searchMode", this.searchMode, this.searchMode = searchMode);
	}

	/**
	 * Get the instant search delay in milliseconds. The default delay is 50
	 * Milliseconds.
	 * 
	 * @see {@link #setInstantSearchDelay(int)}
	 * @return the instant search delay in milliseconds
	 */
	public int getInstantSearchDelay() {
		return instantSearchDelay;
	}

	/**
	 * Set the instant search delay in milliseconds. In
	 * {@link SearchMode#INSTANT}, when the user changes the text, an action
	 * event will be fired after the specified instant search delay.
	 * 
	 * It is recommended to use a instant search delay to avoid the firing of
	 * unnecessary events. For example when the user replaces the whole text
	 * with a different text the search fields underlying {@link Document}
	 * typically fires 2 document events. The first one, because the old text is
	 * removed and the second one because the new text is inserted. If the
	 * instant search delay is 0, this would result in 2 action events being
	 * fired. When a instant search delay is used, the first document event
	 * typically is ignored, because the second one is fired before the delay is
	 * over, which results in a correct behavior because only the last and only
	 * relevant event will be delivered.
	 * 
	 * @param instantSearchDelay
	 */
	public void setInstantSearchDelay(int instantSearchDelay) {
		firePropertyChange("instantSearchDelay", this.instantSearchDelay, this.instantSearchDelay = instantSearchDelay);
	}

	/**
	 * Get the current {@link LayoutStyle}.
	 * 
	 * @return
	 */
	public LayoutStyle getLayoutStyle() {
		return layoutStyle;
	}

	/**
	 * Returns <code>true</code> if the current {@link LayoutStyle} is
	 * {@link LayoutStyle#VISTA}.
	 * 
	 * @return
	 */
	public boolean isVistaLayoutStyle() {
		return LayoutStyle.VISTA.equals(getLayoutStyle());
	}

	/**
	 * Returns <code>true</code> if the current {@link LayoutStyle} is
	 * {@link LayoutStyle#MAC}.
	 * 
	 * @return
	 */
	public boolean isMacLayoutStyle() {
		return LayoutStyle.MAC.equals(getLayoutStyle());
	}

	/**
	 * Set the current {@link LayoutStyle}. See {@link LayoutStyle} for a
	 * description of how this affects layout and behavior of the search field.
	 * 
	 * @param layoutStyle
	 *            {@link LayoutStyle#MAC} or {@link LayoutStyle#VISTA}
	 */
	public void setLayoutStyle(LayoutStyle layoutStyle) {
		layoutStyleSet = true;
		firePropertyChange("layoutStyle", this.layoutStyle, this.layoutStyle = layoutStyle);
	}

	/**
	 * Returns the margin between the search fields border and the search and
	 * clear buttons.
	 * 
	 * @return
	 */
	public Insets getButtonMargin() {
		return buttonMargin;
	}

	/**
	 * Set the margin between the search fields border and the search and clear
	 * buttons.
	 * 
	 * @param buttonMargin
	 */
	public void setButtonMargin(Insets buttonMargin) {
		firePropertyChange("buttonMargin", this.buttonMargin, this.buttonMargin = buttonMargin);
	}

	/**
	 * Set the margin space around the search field's text.
	 * 
	 * @see javax.swing.text.JTextComponent#setMargin(java.awt.Insets)
	 */
	public void setMargin(Insets m) {
		super.setMargin(m);
	}

	/**
	 * Returns the action that is invoked when the escape key is pressed or the
	 * clear button is clicked.
	 * 
	 * Calls {@link #createClearAction()} to create the clear action, if needed.
	 * 
	 * @return the clear action
	 */
	protected final ClearAction getClearAction() {
		if (clearAction == null) {
			clearAction = createClearAction();
		}
		return clearAction;
	}

	/**
	 * Creates and returns the {@link ClearAction}. Override to use a custom
	 * clear action
	 * 
	 * @see #getClearAction()
	 * @return the clear action
	 */
	protected ClearAction createClearAction() {
		return new ClearAction();
	}

	/**
	 * Returns the clear button.
	 * 
	 * Calls {@link #createClearButton()} to create the clear button, if needed.
	 * 
	 * @return the clear button
	 */
	public final JButton getClearButton() {
		if (clearButton == null) {
			clearButton = createClearButton();
		}
		return clearButton;
	}

	/**
	 * Creates and returns the clear button. The buttons action is set to the
	 * action returned by {@link #getClearAction()}.
	 * 
	 * Override to use a custom clear button.
	 * 
	 * @see #getClearButton()
	 * @return the clear button
	 */
	protected JButton createClearButton() {
		IconButton btn = new IconButton();
		btn.setAction(getClearAction());

		return btn;
	}

	/**
	 * Returns the action that is invoked when the enter key is pressed or the
	 * search button is clicked.
	 * 
	 * Calls {@link #createSearchAction()} to create the search action, if
	 * needed.
	 * 
	 * @return the search action
	 */
	protected final SearchAction getSearchAction() {
		if (searchAction == null) {
			searchAction = createSearchAction();
		}
		return searchAction;
	}

	/**
	 * Creates and returns {@link SearchAction}. Override to use a custom
	 * search action.
	 * 
	 * @see #getSearchAction()
	 * @return the search action
	 */
	protected SearchAction createSearchAction() {
		return new SearchAction();
	}

	/**
	 * Returns the search button.
	 * 
	 * Calls {@link #createSearchButton()} to create the search button, if
	 * needed.
	 * 
	 * @return the search button
	 */
	public final JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = createSearchButton();
		}
		return searchButton;
	}

	/**
	 * Creates and returns the search button. The buttons action is set to the
	 * action returned by {@link #getSearchAction()}.
	 * 
	 * Override to use a custom search button.
	 * 
	 * @see #getSearchButton()
	 * @return the search button
	 */
	protected JButton createSearchButton() {
		final IconButton btn = new IconButton();
		btn.setAction(getSearchAction());

		return btn;
	}

	/**
	 * Returns the popup button. If a search popup menu is set, it will be
	 * displayed when this button is clicked.
	 * 
	 * This button will only be visible, if {@link #isUseSeperatePopupButton()}
	 * returns <code>true</code>. Otherwise the popup menu will be displayed
	 * when the search button is clicked.
	 * 
	 * @return the popup button
	 */
	public final JButton getPopupButton() {
		if (popupButton == null) {
			popupButton = createPopupButton();
		}
		return popupButton;
	}

	/**
	 * Creates and returns the popup button. Override to use a custom popup
	 * button.
	 * 
	 * @see #getPopupButton()
	 * @return the popup button
	 */
	protected JButton createPopupButton() {
		return new IconButton();
	}

	/**
	 * Returns <code>true</code> if the popup button should be visible and
	 * used for displaying the search popup menu. Otherwise, the search popup
	 * menu will be displayed when the search button is clicked.
	 * 
	 * @return <code>true</code> if the popup button should be used
	 */
	public boolean isUseSeperatePopupButton() {
		return useSeperatePopupButton;
	}

	/**
	 * Set if the popup button should be used for displaying the search popup
	 * menu.
	 * 
	 * @param useSeperatePopupButton
	 */
	public void setUseSeperatePopupButton(boolean useSeperatePopupButton) {
		useSeperatePopupButtonSet = true;
		firePropertyChange("useSeperatePopupButton", this.useSeperatePopupButton,
				this.useSeperatePopupButton = useSeperatePopupButton);
	}

	/**
	 * Updates the clear, search and popup buttons enabled state in addition to
	 * setting the search fields editable state.
	 * 
	 * @see #updateButtonState()
	 * @see javax.swing.text.JTextComponent#setEditable(boolean)
	 */
	public void setEditable(boolean b) {
		super.setEditable(b);
		updateButtonState();
	}

	/**
	 * Updates the clear, search and popup buttons enabled state in addition to
	 * setting the search fields enabled state.
	 * 
	 * @see #updateButtonState()
	 * @see javax.swing.text.JTextComponent#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtonState();
	}

	/**
	 * Enables the clear action if this search field is editable and enabled,
	 * otherwise it will be disabled. Enabled the search action and popup button
	 * if this search field is enabled, otherwise it will be disabled.
	 */
	protected void updateButtonState() {
		getClearAction().setEnabled(isEditable() & isEnabled());
		getSearchAction().setEnabled(isEnabled());
		getPopupButton().setEnabled(isEnabled());
	}

	/**
	 * Sets the popup menu that will be displayed when the popup button is
	 * clicked. If a search popup menu is set and
	 * {@link #isUseSeperatePopupButton()} returns <code>false</code>, the
	 * popup button will be displayed instead of the search button. Otherwise
	 * the popup button will be displayed in addition to the search button.
	 * 
	 * We could use the <code>popupButton</code>s
	 * <code>componentPopupMenu</code> property instead of introducing another
	 * property, if {@link JComponent#setComponentPopupMenu(JPopupMenu)} would
	 * just fire a {@link PropertyChangeEvent}...
	 * 
	 * 
	 * @param searchPopupMenu
	 *            the popup menu, which will be displayed when the popup button
	 *            is clicked
	 */
	public void setSearchPopupMenu(JPopupMenu searchPopupMenu) {
		firePropertyChange("searchPopupMenu", this.searchPopupMenu, this.searchPopupMenu = searchPopupMenu);
	}

	/**
	 * Returns the search popup menu.
	 * 
	 * @see #setSearchPopupMenu(JPopupMenu)
	 * @return the search popup menu
	 */
	public JPopupMenu getSearchPopupMenu() {
		return searchPopupMenu;
	}

	/**
	 * Returns the {@link Timer} used to delay the firing of action events in
	 * instant search mode when the user enters text.
	 * 
	 * This timer calls {@link #postActionEvent()}.
	 * 
	 * @return the {@link Timer} used to delay the firing of action events
	 */
	public Timer getInstantSearchTimer() {
		if (instantSearchTimer == null) {
			instantSearchTimer = new Timer(0, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					postActionEvent();
				}
			});
			instantSearchTimer.setRepeats(false);
		}
		return instantSearchTimer;
	}

	/**
	 * Returns <code>true</code> if this search field is the focus owner or
	 * the search popup menu is visible.
	 * 
	 * This is a hack to make the search field paint the focus indicator in Mac
	 * OS X Aqua when the search popup menu is visible.
	 * 
	 * @return <code>true</code> if this search field is the focus owner or
	 *         the search popup menu is visible
	 */
	public boolean hasFocus() {
		if (getSearchPopupMenu() != null && getSearchPopupMenu().isVisible()) {
			return true;
		}
		return super.hasFocus();
	}

	public void setUI(TextUI ui) {
		if (ui instanceof BasicSearchFieldUI) {
			super.setUI(ui);
		} else {
			setUI(new BasicSearchFieldUI(ui));
		}
	}

	/**
	 * Overriden to also update the search popup menu if set.
	 */
	public void updateUI() {
		super.updateUI();
		if (getSearchPopupMenu() != null) {
			SwingUtilities.updateComponentTreeUI(getSearchPopupMenu());
		}
	}

	/**
	 * Hack to enable the UI delegate to set default values depending on the
	 * current Look and Feel, without overriding custom values.
	 */
	public void setPromptFontStyle(Integer fontStyle) {
		super.setPromptFontStyle(fontStyle);
		promptFontStyleSet = true;
	}

	/**
	 * Hack to enable the UI delegate to set default values depending on the
	 * current Look and Feel, without overriding custom values.
	 * 
	 * @param propertyName
	 *            the name of the property to change
	 * @param value
	 *            the new value of the property
	 */
	public void customSetUIProperty(String propertyName, Object value) {
		customSetUIProperty(propertyName, value, false);
	}

	/**
	 * Hack to enable the UI delegate to set default values depending on the
	 * current Look and Feel, without overriding custom values.
	 * 
	 * @param propertyName
	 *            the name of the property to change
	 * @param value
	 *            the new value of the property
	 * @param override
	 *            override custom values
	 */
	public void customSetUIProperty(String propertyName, Object value, boolean override) {
		if (propertyName == "useSeperatePopupButton") {
			if (!useSeperatePopupButtonSet || override) {
				setUseSeperatePopupButton(((Boolean) value).booleanValue());
				useSeperatePopupButtonSet = false;
			}
		} else if (propertyName == "layoutStyle") {
			if (!layoutStyleSet || override) {
				setLayoutStyle((LayoutStyle) value);
				layoutStyleSet = false;
			}
		} else if (propertyName == "promptFontStyle") {
			if (!promptFontStyleSet || override) {
				setPromptFontStyle((Integer) value);
				promptFontStyleSet = false;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Overriden to prevent any delayed {@link ActionEvent}s from being sent
	 * after posting this action.
	 * 
	 * For example, if the current {@link SearchMode} is
	 * {@link SearchMode#INSTANT} and the instant search delay is greater 0. The
	 * user enters some text and presses enter. This method will be invoked
	 * immediately because the users presses enter. However, this method would
	 * be invoked after the instant search delay, if we would not prevent it
	 * here.
	 */
	public void postActionEvent() {
		getInstantSearchTimer().stop();
		super.postActionEvent();
	}

	/**
	 * Non focusable, no border, no margin and insets button with no content
	 * area filled. Used for search, clear and popup buttons.
	 */
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

	/**
	 * Invoked when the the clear button or the 'Esc' key is pressed. Sets the
	 * text in the search field to <code>null</code>.
	 * 
	 */
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
		 * Sets the search field's text to <code>null</code> and requests the
		 * focus for the search field.
		 */
		public void clear() {
			setText(null);
			requestFocusInWindow();
		}
	}

	/**
	 * Invoked when the search button is pressed.
	 */
	public class SearchAction extends AbstractAction {
		public SearchAction() {
		}

		/**
		 * In regular search mode posts an action event if the search field is
		 * the focus owner.
		 * 
		 * Also requests the focus for the search field and selects the whole
		 * text.
		 */
		public void actionPerformed(ActionEvent e) {
			if (isFocusOwner() && isRegularSearchMode()) {
				postActionEvent();
			}
			requestFocusInWindow();
			selectAll();
		}
	}
}
