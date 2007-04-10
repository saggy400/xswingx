package org.jdesktop.xswingx;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.Document;

import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.xswingx.plaf.JXSearchFieldAddon;
import org.jdesktop.xswingx.plaf.basic.BasicSearchFieldUI;

public class JXSearchField extends JXPromptField {
	public enum LayoutStyle {
		VISTA, MAC
	};

	// ensure at least the default ui is registered
	static {
		LookAndFeelAddons.contribute(new JXSearchFieldAddon());
	}

	private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

	private PropertyChangeHandler propertyChangeHandler = new PropertyChangeHandler();

	private ClearTextAction clearTextAction;

	private JButton searchButton;

	private JButton clearButton;

	private Insets buttonMargin;

	private LayoutStyle layoutStyle;

	public JXSearchField() {
		layoutStyle = LayoutStyle.MAC;
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"clearText");
		getActionMap().put("clearText", getClearTextAction());

		propertyChangeHandler.install();
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
		propertyChangeHandler.update(getDocument());
	}

	public Insets getButtonMargin() {
		return buttonMargin;
	}

	public void setButtonMargin(Insets buttonMargin) {
		this.buttonMargin = buttonMargin;
	}

	public Action getClearTextAction() {
		if (clearTextAction == null) {
			clearTextAction = new ClearTextAction();
		}
		return clearTextAction;
	}

	public JButton getClearButton() {
		if (clearButton == null) {
			clearButton = createClearButton();
		}
		return clearButton;
	}

	protected JButton createClearButton() {
		IconButton btn = new IconButton();
		btn.addActionListener(getClearTextAction());

		return btn;
	}

	public void setClearButton(JButton clearButton) {
		this.clearButton = clearButton;
	}

	public JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = createSearchButton();
		}
		return searchButton;
	}

	protected JButton createSearchButton() {
		return new IconButton();
	}

	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	public void setUI(TextUI ui) {
		if (ui instanceof BasicSearchFieldUI) {
			super.setUI(ui);
		} else {
			setUI(new BasicSearchFieldUI(ui));
		}
	}

	protected class IconButton extends JButton {
		public IconButton() {
			setFocusable(false);
			setMargin(null);

			// Windows UI will add 1 pixel for width and haight, if this is true
			setFocusPainted(false);

			setBorderPainted(false);
			setContentAreaFilled(false);
			setIconTextGap(0);

			// Motif overrides null Border
			setBorder(BorderFactory.createEmptyBorder());

			setOpaque(false);

			setCursor(Cursor.getDefaultCursor());
		}

		// Windows UI overrides Insets.
		public Insets getInsets() {
			return NO_INSETS;
		}

		public Insets getInsets(Insets insets) {
			return getInsets();
		}

		public Insets getMargin() {
			return getInsets();
		}
	}

	class ClearTextAction extends AbstractAction {
		public ClearTextAction() {
		}

		public void actionPerformed(ActionEvent e) {
			setText(null);
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
			if (clearButton != null) {
				clearButton.setVisible(doc != null && doc.getLength() > 0);
			}
			if (isVistaLayoutStyle()) {
				searchButton.setVisible(!clearButton.isVisible());
			} else {
				searchButton.setVisible(true);
			}
		}
	}
}
