package org.jdesktop.xswingx.plaf;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.TextUI;

/**
 * <p>
 * TODO: queries the text components layout manager for the preferred size.
 * </p>
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class BuddyTextFieldUI extends PromptTextFieldUI {
	protected BuddyLayoutAndBorder layoutAndBorder;

	/**
	 * Creates a new {@link BuddyTextFieldUI} which delegates most work to
	 * another {@link TextUI}.
	 * 
	 * @param delegate
	 */
	public BuddyTextFieldUI(TextUI delegate) {
		super(delegate);
		layoutAndBorder = createBuddyLayoutAndBorder();
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		layoutAndBorder.install((JTextField) c);
	}

	protected BuddyLayoutAndBorder createBuddyLayoutAndBorder() {
		return new BuddyLayoutAndBorder();
	}

	@Override
	public void uninstallUI(JComponent c) {
		layoutAndBorder.uninstall();
		super.uninstallUI(c);
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

		return cd;
	}
}