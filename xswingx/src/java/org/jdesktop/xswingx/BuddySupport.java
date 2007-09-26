package org.jdesktop.xswingx;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.plaf.TextUI;

import org.jdesktop.xswingx.plaf.BuddyTextFieldUI;
import org.jdesktop.xswingx.plaf.TextUIWrapper;

public class BuddySupport {
	public static final String OUTER_MARGIN = "outerMargin";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";

	public static void install(JTextField buddyField) {
		wrapper.install(buddyField, false);
	}
	
	public static void setOuterMargin(JTextField buddyField, Insets margin){
		buddyField.putClientProperty(OUTER_MARGIN, margin);
	}
	
	public static Insets getOuterMargin(JTextField buddyField){
		return (Insets) buddyField.getClientProperty(OUTER_MARGIN);
	}

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
	}
}
