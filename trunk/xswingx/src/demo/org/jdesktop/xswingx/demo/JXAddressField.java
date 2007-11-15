package org.jdesktop.xswingx.demo;

import org.jdesktop.xswingx.JXBuddyField;
import org.jdesktop.xswingx.BuddySupport.Position;

public class JXAddressField extends JXBuddyField {
	private BookmarkButton bookmarkButton;
	private RssButton rssButton;
	private SnapBackButton snapBackButton;
	
	public JXAddressField() {
		bookmarkButton = new BookmarkButton();
		rssButton = new RssButton();
		snapBackButton = new SnapBackButton();
		
		addBuddy(bookmarkButton, Position.LEFT);
		addBuddy(rssButton, Position.RIGHT);
		addGap(3, Position.RIGHT);
		addBuddy(snapBackButton, Position.RIGHT);
		
		setPrompt("Go to this address");
	}

	public BookmarkButton getBookmarkButton() {
		return bookmarkButton;
	}

	public RssButton getRssButton() {
		return rssButton;
	}

	public SnapBackButton getSnapBackButton() {
		return snapBackButton;
	}
}
