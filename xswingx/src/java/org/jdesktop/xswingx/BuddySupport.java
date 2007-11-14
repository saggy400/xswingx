package org.jdesktop.xswingx;

import java.awt.Component;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicTextUI;
import javax.swing.text.JTextComponent;

import org.jdesktop.xswingx.plaf.BuddyTextFieldUI;
import org.jdesktop.xswingx.plaf.TextUIWrapper;

public class BuddySupport {
	public enum Position {
		LEFT, RIGHT
	};

	public static final String OUTER_MARGIN = "outerMargin";

	public static void addLeft(Component c, JTextField textField) {
		add(c, Position.LEFT, textField);
	}

	public static void addRight(Component c, JTextField textField) {
		add(c, Position.RIGHT, textField);
	}

	public static void add(Component c, Position pos, JTextField textField) {
		install(textField);

		List<Component> leftBuddies = buddies(Position.LEFT, textField);
		List<Component> rightBuddies = buddies(Position.RIGHT, textField);

		// ensure buddies are added
		setLeft(textField, leftBuddies);
		setRight(textField, rightBuddies);

		// check if component is already here
		if (leftBuddies.contains(c) || rightBuddies.contains(c)) {
			throw new IllegalStateException("Component already added.");
		}

		if (Position.LEFT == pos) {
			leftBuddies.add(c);
		} else {
			rightBuddies.add(c);
		}

		addToComponentHierarchy(c, pos, textField);
	}

	public static void setRight(JTextField textField, List<Component> rightBuddies) {
		set(rightBuddies, Position.RIGHT, textField);
	}

	public static void setLeft(JTextField textField, List<Component> leftBuddies) {
		set(leftBuddies, Position.LEFT, textField);
	}

	public static void set(List<Component> buddies, Position pos, JTextField textField) {
		textField.putClientProperty(pos, buddies);
	}

	private static void addToComponentHierarchy(Component c, Position pos, JTextField textField) {
		textField.add(c, pos.toString());
	}

	public static List<Component> getLeft(JTextField textField) {
		return getBuddies(Position.LEFT, textField);
	}

	public static List<Component> getRight(JTextField textField) {
		return getBuddies(Position.RIGHT, textField);
	}

	public static List<Component> getBuddies(Position pos, JTextField textField) {
		return Collections.unmodifiableList(buddies(pos, textField));
	}

	@SuppressWarnings("unchecked")
	private static List<Component> buddies(Position pos, JTextField textField) {
		List<Component> buddies = (List<Component>) textField.getClientProperty(pos);

		if (buddies != null) {
			return buddies;
		}
		return new ArrayList<Component>();
	}

	public static boolean isBuddy(Component c, JTextField textField) {
		return buddies(Position.LEFT, textField).contains(c) || buddies(Position.RIGHT, textField).contains(c);
	}

	/**
	 * Because {@link BasicTextUI} removes all components when uninstalled and
	 * therefore all buddies are removed when the LnF changes.
	 * 
	 * @param c
	 * @param textField
	 */
	public static void remove(JComponent c, JTextField textField) {
		buddies(Position.LEFT, textField).remove(c);
		buddies(Position.RIGHT, textField).remove(c);

		textField.remove(c);
	}

	public static void removeAll(JTextField textField) {
		List<Component> left = buddies(Position.LEFT, textField);
		for (Component c : left) {
			textField.remove(c);
		}
		left.clear();
		List<Component> right = buddies(Position.RIGHT, textField);
		for (Component c : right) {
			textField.remove(c);
		}
		right.clear();
		
	}

	/**
	 * Install buddy support on a text field by calling
	 * {@link TextUIWrapper#install(JTextComponent)}
	 * 
	 * @param buddyField
	 */
	public static void install(JTextField buddyField) {
		wrapper.install(buddyField, true);
	}

	/**
	 * Calls {@link TextUIWrapper}{@link #uninstall(JTextComponent)}
	 * 
	 * @param textComponent
	 */
	public static void uninstall(final JTextComponent textComponent) {
		wrapper.uninstall(textComponent);
	}

	public static void setOuterMargin(JTextField buddyField, Insets margin) {
		buddyField.putClientProperty(OUTER_MARGIN, margin);
	}

	public static Insets getOuterMargin(JTextField buddyField) {
		return (Insets) buddyField.getClientProperty(OUTER_MARGIN);
	}

	static void ensureBuddiesAreInComponentHierarchy(JTextComponent textComponent) {
		JTextField textField = (JTextField) textComponent;
		for (Component c : BuddySupport.getLeft(textField)) {
			addToComponentHierarchy(c, Position.LEFT, textField);
		}
		for (Component c : BuddySupport.getRight(textField)) {
			addToComponentHierarchy(c, Position.RIGHT, textField);
		}
	}

	/**
	 * Create a gap to insert between to buddies.
	 * 
	 * @param width
	 * @return
	 */
	public static Component createGap(int width) {
		return Box.createHorizontalStrut(width);
	}

	private static final BuddyWrapper wrapper = new BuddyWrapper();

	private static final class BuddyWrapper extends TextUIWrapper<BuddyTextFieldUI> {
		private BuddyWrapper() {
			super(BuddyTextFieldUI.class);
		}

		@Override
		public BuddyTextFieldUI wrapUI(TextUI textUI) {
			return new BuddyTextFieldUI(textUI);
		}

		/**
		 * Every time the UI needs to be replaced we also need to make sure,
		 * that all buddy components are also in the component hierarchy.
		 * (That's because {@link BasicTextUI} removes all our buddies upon UI
		 * changes).
		 */
		@Override
		protected boolean replaceUIIfNeeded(JTextComponent textComponent) {
			boolean replaced = super.replaceUIIfNeeded(textComponent);

			if (replaced) {
				ensureBuddiesAreInComponentHierarchy(textComponent);
			}
			return replaced;
		}
	}
}
