package org.jdesktop.xswingx;

import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import org.jdesktop.xswingx.plaf.basic.BasicSearchFieldUI;


/**
 * TODO:
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class BuddyButton extends JButton {
	public BuddyButton() {
		this(null);
	}
	
	public BuddyButton(String text) {
		super(text);
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