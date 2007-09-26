package org.jdesktop.xswingx.demo;

import javax.swing.ImageIcon;

import org.jdesktop.xswingx.BuddyButton;

public class BookmarkButton extends BuddyButton {
	public BookmarkButton() {
		setIcon(new ImageIcon(getClass().getResource("apple.png")));
	}
}
