package org.jdesktop.xswingx.plaf;

import java.awt.Dimension;
import java.awt.Graphics;

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
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		layoutAndBorder = createBuddyLayoutAndBorder((JTextField) c);
	}

	protected BuddyLayoutAndBorder createBuddyLayoutAndBorder(JTextField c) {
		return new BuddyLayoutAndBorder(c);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		layoutAndBorder.uninstall((JTextField) c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		// make Quaqua paint the background.
//		if (UIManager.getLookAndFeel().getName().contains("Quaqua")) {
//			g.setColor(c.getBackground());
//			g.fillRect(0, 0, c.getWidth(), c.getHeight());
//		}
		super.paint(g, c);
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
}