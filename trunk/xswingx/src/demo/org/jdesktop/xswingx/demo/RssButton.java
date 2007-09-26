package org.jdesktop.xswingx.demo;

import javax.swing.ImageIcon;

import org.jdesktop.xswingx.BuddyButton;

public class RssButton extends BuddyButton{
	public RssButton() {
		setIcon(new ImageIcon(getClass().getResource("ShowRSSButton.png")));
		setPressedIcon(new ImageIcon(getClass().getResource("ShowRSSButton_Pressed.png")));
	}
}
