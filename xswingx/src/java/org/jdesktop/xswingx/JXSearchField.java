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

public class JXSearchField extends JXPromptField {
	private static final KeyStroke CLEAR_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_ESCAPE, 0);

	public enum LayoutStyle {
		VISTA, MAC
	};

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

	private boolean fireActionOnTextChange;

	private boolean fireActionOnButtonClick;

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

	public boolean isFireActionOnTextChange() {
		return fireActionOnTextChange;
	}

	public void setFireActionOnTextChange(boolean fireActionOnTextChange) {
		this.fireActionOnTextChange = fireActionOnTextChange;
	}

	public boolean isFireActionOnButtonClick() {
		return fireActionOnButtonClick;
	}

	/**
	 * <p>
	 * Fire an {@link ActionEvent}, when the user clicks the search button.
	 * </p>
	 * <p>
	 * When a <code>searchPopupMenu</code> is installed, no action is fired,
	 * even if this property is <code>true</code>.
	 * </p>
	 * <p>
	 * When {@link LayoutStyle} is {@link LayoutStyle#VISTA}, the clear button
	 * will never be shown if this property is <code>true</code>.
	 * </p>
	 * 
	 * @param fireActionOnButtonClick
	 */
	public void setFireActionOnButtonClick(boolean fireActionOnButtonClick) {
		this.fireActionOnButtonClick = fireActionOnButtonClick;
		propertyChangeHandler.updateButtonVisibility(getDocument());
	}

	protected void installPromptSupport(String labelText, Color labelTextColor) {
		// don't! Handled by setUI
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
		this.layoutStyle = layoutStyle;
		propertyChangeHandler.updateButtonVisibility(getDocument());
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

		public void actionPerformed(ActionEvent e) {
			clear();
		}

		public void clear() {
			setText(null);
			requestFocusInWindow();
		}
	}

	public class SearchAction extends AbstractAction {
		public SearchAction() {
		}

		public void actionPerformed(ActionEvent e) {
			if (getSearchPopupMenu() != null) {
				Rectangle r = SwingUtilities.getLocalBounds(searchButton);

				getSearchPopupMenu().show(searchButton, r.x, r.y + r.height);
			} else if (isFireActionOnButtonClick()) {
				postActionEvent();
			}
		}
	}

	class PropertyChangeHandler implements PropertyChangeListener,
			DocumentListener {

		public void install() {
			install(getDocument());
			addPropertyChangeListener(this);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String property = evt.getPropertyName();

			if ("document".equals(property)) {
				Document doc = (Document) evt.getOldValue();
				if (doc != null) {
					uninstall(doc);
				}
				doc = (Document) evt.getNewValue();
				if (doc != null) {
					install(doc);
				}
			}
		}

		public void install(Document doc) {
			doc.addDocumentListener(this);
			update(doc);
		}

		private void uninstall(Document doc) {
			doc.removeDocumentListener(this);
		}

		public void changedUpdate(DocumentEvent e) {
			update(e.getDocument());
		}

		public void insertUpdate(DocumentEvent e) {
			update(e.getDocument());
		}

		public void removeUpdate(DocumentEvent e) {
			update(e.getDocument());
		}

		private void update(Document doc) {
			if (isFireActionOnTextChange()) {
				postActionEvent();
			}

			updateButtonVisibility(doc);
		}

		private void updateButtonVisibility(Document doc) {
			if (clearButton != null) {
				clearButton
						.setVisible((!isFireActionOnButtonClick() || isMacLayoutStyle())
								&& doc != null && doc.getLength() > 0);
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
