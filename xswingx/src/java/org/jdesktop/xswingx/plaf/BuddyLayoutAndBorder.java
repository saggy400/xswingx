package org.jdesktop.xswingx.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import org.jdesktop.xswingx.BuddySupport;

public class BuddyLayoutAndBorder implements LayoutManager, Border, PropertyChangeListener {
	private JTextField textField;
	private Border borderDelegate;

	private List<Component> left = new ArrayList<Component>();
	private List<Component> right = new ArrayList<Component>();

	/**
	 * Creates and installs a {@link BuddyLayoutAndBorder} as a layout and
	 * border of the given text field. Registers a
	 * {@link PropertyChangeListener} to wrap any subsequentially set border on
	 * the text field.
	 * 
	 * @param textField
	 */
	public BuddyLayoutAndBorder(JTextField textField) {
		this.textField = textField;
		this.borderDelegate = textField.getBorder();

		install();
	}

	/**
	 * Does the installing.
	 */
	private void install() {
		textField.setLayout(this);
		this.replaceBorderIfNecessary();
		textField.addPropertyChangeListener(this);
	}

	/**
	 * Wraps and replaces the text fields default border with this object, to
	 * honor the button margins and sizes of the search, clear and popup buttons
	 * and the layout style.
	 */
	protected void replaceBorderIfNecessary() {
		Border original = textField.getBorder();

		if (!(original instanceof BuddyLayoutAndBorder)) {
			borderDelegate = original;
			textField.setBorder(this);
		}
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
			right.add(0, comp);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = new Dimension();

		Insets insets = getBorderInsets(parent);
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
			if (!comp.isVisible()) {
				continue;
			}
			size = comp.getPreferredSize();
			comp.setBounds(visibleRect.x, centerY(visibleRect, size), size.width, size.height);

			visibleRect.x += size.width;
			visibleRect.width -= size.width;
		}

		for (Component comp : right) {
			if (!comp.isVisible()) {
				continue;
			}

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
	 * @return the rectangle allocated by the text field, including the space
	 *         allocated by the child components left and right, the text fields
	 *         original border insets and the outer margin.
	 * 
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
	 * Returns the {@link Insets} of the original {@link Border} plus the space
	 * required by the child components.
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
			insets.left += comp.isVisible() ? comp.getPreferredSize().width : 0;
		}
		for (Component comp : right) {
			insets.right += comp.isVisible() ? comp.getPreferredSize().width : 0;
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
	 * Returns the insets of the original border (without the margin! Beware of
	 * {@link MarginBorder}!).
	 * 
	 * @return the insets of the border delegate
	 */
	public Insets getRealBorderInsets() {
		if (borderDelegate == null) {
			return null;
		}
		Insets insets = (Insets) borderDelegate.getBorderInsets(textField);

		System.err.println(borderDelegate);
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
		g.setColor(Color.CYAN);
		((Graphics2D) g).draw(getVisibleRect());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		replaceBorderIfNecessary();
	}

	public void uninstall(JTextField textField) {
		textField.removePropertyChangeListener(this);
		textField.setBorder(borderDelegate);
	}
}