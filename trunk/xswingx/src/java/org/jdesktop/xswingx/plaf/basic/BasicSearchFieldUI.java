package org.jdesktop.xswingx.plaf.basic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.UIResource;

import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;

public class BasicSearchFieldUI extends PromptTextFieldUI {
	private JXSearchField searchField;

	private Handler handler = new Handler();

	public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

	public BasicSearchFieldUI(TextUI delegate) {
		super(delegate);
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		searchField = (JXSearchField) c;

		installDefaults();

		searchField.add(searchButton());
		searchField.add(clearButton());

		searchField.addPropertyChangeListener(handler);
	}

	protected void installDefaults() {
		if (shouldReplaceResource(searchField.getButtonMargin())) {
			searchField.setButtonMargin(UIManager
					.getInsets("SearchField.buttonMargin"));
		}

		updateSearchButtonIcon();

		if (clearButton() != null) {
			if (shouldReplaceResource(clearButton().getIcon())) {
				clearButton().setIcon(
						UIManager.getIcon("SearchField.clearIcon"));
			}
			if (shouldReplaceResource(clearButton().getPressedIcon())) {
				clearButton().setPressedIcon(
						UIManager.getIcon("SearchField.clearPressedIcon"));
			}
			if (shouldReplaceResource(clearButton().getRolloverIcon())) {
				clearButton().setRolloverIcon(
						UIManager.getIcon("SearchField.clearRolloverIcon"));
			}
		}

		replaceBorderIfNecessary();
		replaceLayoutIfNecessary();
	}

	protected void updateSearchButtonIcon() {
		if (searchButton() != null
				&& shouldReplaceResource(searchButton().getIcon())) {
			if (searchField.getSearchPopupMenu() == null) {
				searchButton().setIcon(UIManager.getIcon("SearchField.icon"));
				if (searchField.isRegularSearchMode()) {
					searchButton().setRolloverIcon(
							UIManager.getIcon("SearchField.rolloverIcon"));
					searchButton().setPressedIcon(
							UIManager.getIcon("SearchField.pressedIcon"));
				}else{
					searchButton().setRolloverIcon(null);
					searchButton().setPressedIcon(null);
				}
			} else {
				searchButton().setIcon(
						UIManager.getIcon("SearchField.popupIcon"));
				searchButton().setRolloverIcon(null);
				searchButton().setPressedIcon(null);
			}
		}
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		searchField.removePropertyChangeListener(handler);

		searchField.setLayout(handler.layoutDelegate);
		searchField.setBorder(handler.borderDelegate);
		searchField = null;
	}

	protected void replaceLayoutIfNecessary() {
		LayoutManager original = searchField.getLayout();

		if (!(original instanceof Handler)) {
			handler.layoutDelegate = original;
			searchField.setLayout(handler);
		}
	}

	protected void replaceBorderIfNecessary() {
		Border original = searchField.getBorder();

		if (!(original instanceof Handler)) {
			handler.borderDelegate = original;
			searchField.setBorder(handler);
		}
	}

	public Dimension getPreferredSize(JComponent c) {
		Dimension d = (Dimension) super.getPreferredSize(c);
		if (searchField == null) {
			return d;
		}

		Insets insets = handler.getRealBorderInsets();
		Insets margin = searchField.getButtonMargin();

		int buttonHeight = Math.max(searchButton() != null ? searchButton()
				.getPreferredSize().height : 0,
				clearButton() != null ? clearButton().getPreferredSize().height
						: 0);

		if (insets != null) {
			buttonHeight += insets.top + insets.bottom;
		}
		if (margin != null) {
			buttonHeight += margin.top + margin.bottom;
		}
		d.height = Math.max(d.height, buttonHeight);

		return d;
	}

	protected boolean shouldReplaceResource(Object o) {
		return o == null || o instanceof UIResource;
	}

	protected JButton clearButton() {
		return searchField != null ? searchField.getClearButton() : null;
	}

	protected JButton searchButton() {
		return searchField != null ? searchField.getSearchButton() : null;
	}

	class Handler implements Border, LayoutManager, PropertyChangeListener {
		private Border borderDelegate;

		private LayoutManager layoutDelegate;

		public Insets getBorderInsets(Component c) {
			if (searchField == null) {
				return NO_INSETS;
			}

			// include margin here
			Insets insets = borderDelegate != null ? (Insets) borderDelegate
					.getBorderInsets(c).clone() : NO_INSETS;
			if (searchField.isVistaLayoutStyle()) {
				insets.right += Math.max(searchButtonWidth(),
						clearButtonWidth());
			} else {
				insets.left += searchButtonWidth();
				insets.right += clearButtonWidth();
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

		private int searchButtonWidth() {
			return searchButton() != null ? searchButton().getPreferredSize().width
					: 0;
		}

		private int clearButtonWidth() {
			return clearButton() != null ? clearButton().getPreferredSize().width
					: 0;
		}

		public Insets getRealBorderInsets() {
			if (borderDelegate == null) {
				return NO_INSETS;
			}
			// don't include margin
			Insets insets = (Insets) borderDelegate.getBorderInsets(null)
					.clone();

			return insets;
		}

		protected Rectangle getVisibleTextRect() {
			Rectangle alloc = (Rectangle) SwingUtilities
					.getLocalBounds(searchField);
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
			layoutDelegate.layoutContainer(parent);

			Rectangle visibleRect = getVisibleTextRect();

			if (clearButton() != null && clearButton().getIcon() != null) {
				layoutRightButton(visibleRect, clearButton());
			}
			if (searchButton() != null && searchButton().getIcon() != null) {
				if (searchField.isVistaLayoutStyle()) {
					layoutRightButton(visibleRect, searchButton());
				} else {
					layoutLeftButton(visibleRect, searchButton());
				}
			}
		}

		protected void layoutLeftButton(Rectangle visibleRect,
				JButton searchButton) {
			Dimension size = searchButton.getPreferredSize();
			searchButton.setBounds(visibleRect.x, centerY(visibleRect, size),
					size.width, size.height);
		}

		protected void layoutRightButton(Rectangle visibleRect,
				JButton clearButton) {
			Dimension size = clearButton.getPreferredSize();
			clearButton.setBounds(visibleRect.x + visibleRect.width
					- size.width, centerY(visibleRect, size), size.width,
					size.height);
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
				} else if ("searchPopupMenu".equals(prop) || "searchMode".equals(prop)) {
					updateSearchButtonIcon();
				} 
			}
		}

		public void addLayoutComponent(String name, Component c) {
			layoutDelegate.addLayoutComponent(name, c);
		}

		public void removeLayoutComponent(Component c) {
			layoutDelegate.removeLayoutComponent(c);
		}

		public Dimension minimumLayoutSize(Container parent) {
			return layoutDelegate.minimumLayoutSize(parent);
		}

		public boolean isBorderOpaque() {
			if (borderDelegate == null) {
				return false;
			}
			return borderDelegate.isBorderOpaque();
		}

		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			if (borderDelegate != null) {
				borderDelegate.paintBorder(c, g, x, y, width, height);
			}
		}

		public Dimension preferredLayoutSize(Container parent) {
			return null; // never called
		}
	}
}
