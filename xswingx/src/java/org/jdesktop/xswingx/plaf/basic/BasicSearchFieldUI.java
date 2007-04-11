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

import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;

public class BasicSearchFieldUI extends PromptTextFieldUI {
	private JXSearchField searchField;

	private Handler handler = new Handler();
	
	public BasicSearchFieldUI(TextUI delegate) {
		super(delegate);
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		searchField = (JXSearchField) c;

		searchField.setButtonMargin(UIManager
				.getInsets("SearchField.buttonMargin"));
		searchButton().setIcon(UIManager.getIcon("SearchField.icon"));
		searchButton().setDisabledIcon(searchButton().getIcon());
		clearButton().setIcon(UIManager.getIcon("SearchField.clearIcon"));
		clearButton().setPressedIcon(
				UIManager.getIcon("SearchField.clearPressedIcon"));
		clearButton().setRolloverIcon(
				UIManager.getIcon("SearchField.clearRolloverIcon"));

		searchField.add(searchField.getSearchButton());
		searchField.add(searchField.getClearButton());

		replaceBorderIfNecessary();
		replaceLayoutIfNecessary();

		searchField.addPropertyChangeListener(handler);
	}

	public void uninstallUI(JComponent c) {
		searchField.removePropertyChangeListener(handler);

		searchField.setLayout(handler.layoutDelegate);
		searchField.setBorder(handler.borderDelegate);
		searchField = null;
		super.uninstallUI(c);
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
		Dimension d = (Dimension) super.getPreferredSize(c).clone();
                if(searchField == null){
                    return d;
                }
		Insets insets = handler.getOriginalInsets();
		Insets margin = searchField.getButtonMargin();

		int buttonHeight = Math.max(searchButton().getPreferredSize().height,
				clearButton().getPreferredSize().height);

		if (insets != null) {
			buttonHeight += insets.top + insets.bottom;
		}
		if (margin != null) {
			buttonHeight += margin.top + margin.bottom;
		}
		d.height = Math.max(d.height, buttonHeight);

		return d;
	}

	private JButton clearButton() {
		return searchField != null ? searchField.getClearButton() : null;
	}

	private JButton searchButton() {
		return searchField != null ? searchField.getSearchButton() : null;
	}

	class Handler implements Border, LayoutManager, PropertyChangeListener {
		private Border borderDelegate;

		private LayoutManager layoutDelegate;

		public Insets getBorderInsets(Component c) {
			if (borderDelegate == null) {
				return null;
			}

			Insets insets = (Insets) borderDelegate.getBorderInsets(c).clone();
			if (searchButton() != null && clearButton() != null) {
				if (searchField.isVistaLayoutStyle()) {
					insets.right += Math.max(
							searchButton().getPreferredSize().width,
							clearButton().getPreferredSize().width);
				} else {
					insets.left += searchButton().getPreferredSize().width;
					insets.right += clearButton().getPreferredSize().width;
				}
			}

			// if (searchField != null && searchField.getMargin() != null) {
			// insets.top += searchField.getMargin().top;
			// insets.left += searchField.getMargin().left;
			// insets.bottom += searchField.getMargin().bottom;
			// insets.right += searchField.getMargin().right;
			// }
			return insets;
		}

		public Insets getOriginalInsets() {
			if (borderDelegate == null || searchField == null) {
				return null;
			}
			Insets insets = (Insets) borderDelegate.getBorderInsets(null)
					.clone();

			return insets;
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

		protected Rectangle getVisibleTextRect() {
			Rectangle alloc = (Rectangle) SwingUtilities.getLocalBounds(
					searchField).clone();
			Insets insets = (Insets) getOriginalInsets().clone();

			if (insets != null) {
				alloc.x += insets.left;
				alloc.y += insets.top;
				alloc.width -= insets.left + insets.right;
				alloc.height -= insets.top + insets.bottom;
			}

			Insets margin = (Insets) searchField.getButtonMargin().clone();
			if (margin != null) {
				alloc.x += margin.left;
				alloc.y += margin.top;
				alloc.width -= margin.left + margin.right;
				alloc.height -= margin.top + margin.bottom;
			}

			return alloc;
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

		public void layoutContainer(Container parent) {
			layoutDelegate.layoutContainer(parent);

			Rectangle visibleRect = getVisibleTextRect();

			if (clearButton() != null && clearButton().getIcon() != null) {
				layoutClearButton(visibleRect, clearButton());
			}
			if (searchButton() != null && searchButton().getIcon() != null) {
				layoutSearchButton(visibleRect, searchButton());
			}
		}

		protected void layoutSearchButton(Rectangle visibleRect,
				JButton searchButton) {
			if (searchField.isVistaLayoutStyle()) {
				layoutClearButton(visibleRect, searchButton);
			} else {
				Dimension size = searchButton.getPreferredSize();
				searchButton.setBounds(visibleRect.x,
						centerY(visibleRect, size), size.width, size.height);
			}
		}

		protected void layoutClearButton(Rectangle visibleRect,
				JButton clearButton) {
			Dimension size = clearButton.getPreferredSize();
			clearButton.setBounds(visibleRect.x + visibleRect.width
					- size.width, centerY(visibleRect, size), size.width,
					size.height);
		}

		protected int centerY(Rectangle rect, Dimension size) {
			return (int) (rect.getCenterY() - (size.height / 2));
		}

		public Dimension preferredLayoutSize(Container parent) {
			return null;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if ("border".equals(evt.getPropertyName())) {
				replaceBorderIfNecessary();
			}
		}
	}
}
