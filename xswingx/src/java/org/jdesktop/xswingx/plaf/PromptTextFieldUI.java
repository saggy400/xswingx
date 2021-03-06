package org.jdesktop.xswingx.plaf;

import javax.swing.JTextField;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;

import org.jdesktop.xswingx.NativeSearchFieldSupport;

/**
 * {@link PromptTextUI} implementation for rendering prompts on
 * {@link JTextField}s and uses a {@link JTextField} as a prompt component.
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class PromptTextFieldUI extends PromptTextUI {
	/**
	 * Shared prompt renderer.
	 */
	private final static LabelField txt = new LabelField();

	/**
	 * Creates a new {@link PromptTextFieldUI}.
	 * 
	 * @param delegate
	 */
	public PromptTextFieldUI(TextUI delegate) {
		super(delegate);
	}

	/**
	 * Overrides {@link #getPromptComponent(JTextComponent)} to additionally
	 * update {@link JTextField} specific properties.
	 */
	public JTextComponent getPromptComponent(JTextComponent txt) {
		LabelField lbl = (LabelField) super.getPromptComponent(txt);
		JTextField txtField = (JTextField) txt;

		lbl.setHorizontalAlignment(txtField.getHorizontalAlignment());
		lbl.setColumns(txtField.getColumns());

		// Make search field in Leopard paint focused border.
		lbl.hasFocus = txtField.hasFocus() && NativeSearchFieldSupport.isNativeSearchField(txtField);

		// leopard client properties. see
		// http://developer.apple.com/technotes/tn2007/tn2196.html#JTEXTFIELD_VARIANT
		NativeSearchFieldSupport.setSearchField(lbl, NativeSearchFieldSupport.isSearchField(txtField));
		NativeSearchFieldSupport.setFindPopupMenu(lbl, NativeSearchFieldSupport.getFindPopupMenu(txtField));
		
		//here we need to copy the border again for Mac OS X, because the above calls may have replaced it.
		lbl.setBorder(txtField.getBorder());

		// buddy support: not needed, because BuddyLayoutAndBorder queries original text field
//		BuddySupport.setOuterMargin(lbl, BuddySupport.getOuterMargin(txtField));
//		BuddySupport.setLeft(lbl, BuddySupport.getLeft(txtField));
//		BuddySupport.setRight(lbl, BuddySupport.getRight(txtField));

		return lbl;
	}

	/**
	 * Returns a shared {@link JTextField}.
	 */
	protected JTextComponent createPromptComponent() {
		txt.updateUI();
		return txt;
	}

	private static final class LabelField extends JTextField {
		boolean hasFocus;

		@Override
		public boolean hasFocus() {
			return hasFocus;
		}
	}
}
