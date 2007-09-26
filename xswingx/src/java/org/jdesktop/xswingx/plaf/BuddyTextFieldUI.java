package org.jdesktop.xswingx.plaf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import org.jdesktop.xswingx.BuddySupport;

/**
 * <p>
 * TODO: queries the text components layout manager for the preferred size.
 * </p>
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class BuddyTextFieldUI extends PromptTextFieldUI {
	/**
	 * Creates a new {@link BuddyTextFieldUI} which delegates most work to
	 * another {@link TextUI}.
	 * 
	 * @param delegate
	 */
	public BuddyTextFieldUI(TextUI delegate) {
		super(delegate);
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);

		BuddyLayoutAndBorder blab = new BuddyLayoutAndBorder((JTextField) c);

		c.setBorder(blab);
		c.setLayout(blab);
	}

	/**
	 * TODO:
	 * 
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	public Dimension getPreferredSize(JComponent c) {
		Dimension d = new Dimension();
		Dimension cd = super.getPreferredSize(c);
		Dimension ld = c.getLayout().preferredLayoutSize(c);

		d.height = Math.max(cd.height, ld.height);
		d.width = Math.max(cd.width, ld.width);

		return d;
	}

	static class BuddyLayoutAndBorder implements LayoutManager, Border {
		private JTextField textField;
		private Border borderDelegate;

		private List<Component> left = new ArrayList<Component>();
		private List<Component> right = new ArrayList<Component>();

		public BuddyLayoutAndBorder(JTextField textField) {
			this.textField = textField;
			this.borderDelegate = textField.getBorder();
		}

		public void addLayoutComponent(String name, Component comp) {
			if (BuddySupport.LEFT.equals(name)) {
				if (right.contains(comp)) {
					throw new IllegalStateException();
				}
				left.add(comp);
			} else if (BuddySupport.RIGHT.equals(name)) {
				if (left.contains(comp)) {
					throw new IllegalStateException();
				}
				right.add(comp);
			} else {
				throw new IllegalArgumentException();
			}
		}

		public Dimension minimumLayoutSize(Container parent) {
			return null; // never called
		}

		public Dimension preferredLayoutSize(Container parent) {
			Dimension d = new Dimension();

			for (Component comp : parent.getComponents()) {
				d.height = Math.max(comp.getPreferredSize().height, d.height);
				d.width += comp.getPreferredSize().width;
			}
			Insets insets = getRealBorderInsets();
			d.height += insets.top + insets.bottom;
			d.width += insets.left + insets.right;

			Insets outerMargin = BuddySupport.getOuterMargin(textField);
			if (outerMargin != null) {
				d.width += outerMargin.left + outerMargin.right;
				d.height += outerMargin.bottom + outerMargin.top;
			}

			return d;
		}

		public void removeLayoutComponent(Component comp) {
			if (left.contains(comp)) {
				left.remove(comp);
			} else if (right.contains(comp)) {
				right.remove(comp);
			} else {
				throw new IllegalArgumentException();
			}
		}

		public void layoutContainer(Container parent) {
			Rectangle visibleRect = getVisibleRect();
			Dimension size;

			for (Component comp : left) {
				size = comp.getPreferredSize();
				comp.setBounds(visibleRect.x, centerY(visibleRect, size), size.width, size.height);

				visibleRect.x += size.width;
				visibleRect.width -= size.width;
			}

			for (Component comp : right) {
				size = comp.getPreferredSize();
				comp.setBounds(visibleRect.x + visibleRect.width - size.width, centerY(visibleRect, size), size.width,
						size.height);
				visibleRect.width -= size.width;
			}
		}

		protected int centerY(Rectangle rect, Dimension size) {
			return (int) (rect.getCenterY() - (size.height / 2));
		}

		/**
		 * Returns the rectangle allocated by the text fields, including the
		 * space allocated by the child components left and right, excluding the
		 * text fields original border.
		 * 
		 * @return the rectangle allocated by the text field without border
		 */
		protected Rectangle getVisibleRect() {
			Rectangle alloc = (Rectangle) SwingUtilities.getLocalBounds(textField);

			substractInsets(alloc, getRealBorderInsets());
			substractInsets(alloc, BuddySupport.getOuterMargin(textField));

			return alloc;
		}

		private void substractInsets(Rectangle alloc, Insets insets) {
			if (insets != null) {
				alloc.x += insets.left;
				alloc.y += insets.top;
				alloc.width -= insets.left + insets.right;
				alloc.height -= insets.top + insets.bottom;
			}
		}

		/**
		 * Returns the {@link Insets} of the original {@link Border} plus the
		 * space required by the child components.
		 * 
		 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
		 */
		public Insets getBorderInsets(Component c) {
			if (c == null) {
				return null;
			}

			// Original insets are cloned to make it work in Mac OS X Aqua LnF.
			// Seems that this LnF uses a shared insets instance which should
			// not be modified.
			// Include margin here
			Insets insets = (Insets) borderDelegate.getBorderInsets(c).clone();

			for (Component comp : left) {
				insets.left += comp.getPreferredSize().getWidth();
			}
			for (Component comp : right) {
				insets.right += comp.getPreferredSize().getWidth();
			}

			Insets outerMargin = BuddySupport.getOuterMargin(textField);
			if (outerMargin != null) {
				insets.left += outerMargin.left;
				insets.right += outerMargin.right;
				insets.top += outerMargin.top;
				insets.bottom += outerMargin.bottom;
			}

			return insets;
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
			Insets insets = (Insets) borderDelegate.getBorderInsets(textField);

			if (borderDelegate instanceof MarginBorder) {
				// don't include margin!!
				Insets margin = textField.getMargin();
				if (margin != null) {
					insets.left -= margin.left;
					insets.right -= margin.right;
					insets.top -= margin.top;
					insets.bottom -= margin.bottom;
				}
			}

			return insets;
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
	}
}