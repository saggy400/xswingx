package org.jdesktop.xswingx.plaf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;

/**
 * TODO:
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 * @param <UI>
 */
public abstract class TextUIWrapper<UI extends TextUI> {
	private Class<UI> wrapperClass;

	public TextUIWrapper(Class<UI> wrapperClass) {
		this.wrapperClass = wrapperClass;
	}

	/**
	 * <p>
	 * Wraps and replaces the current UI of the given <code>textComponent</code>,
	 * by calling {@link #wrapUI(JTextComponent)} if necessary.
	 * </p>
	 * 
	 * @param textComponent
	 * @param stayOnUIChange
	 *            if <code>true</code>, a {@link PropertyChangeListener} is
	 *            registered, which listens for UI changes and wraps any new UI
	 *            object.
	 */
	public final void install(final JTextComponent textComponent, boolean stayOnUIChange) {
		replaceUIIfNeeded(textComponent);
		if (stayOnUIChange) {
			uiChangeHandler.install(textComponent);
		}
	}

	/**
	 * Wraps and replaces the text components current UI by calling
	 * {@link #wrapUI(TextUI)}, if the text components current UI is not an
	 * instance of the given wrapper class.
	 * 
	 * @param textComponent
	 * @return <code>true</code> if the UI has been replaced
	 */
	protected boolean replaceUIIfNeeded(JTextComponent textComponent) {
		if (wrapperClass.isAssignableFrom(textComponent.getUI().getClass())) {
			return false;
		}

		textComponent.setUI(wrapUI(textComponent.getUI()));

		return true;
	}

	/**
	 * Override to return the appropriate UI wrapper object for the given
	 * {@link TextUI}.
	 * 
	 * @param textUI
	 * @return the wrapping UI
	 */
	public abstract UI wrapUI(TextUI textUI);

	/**
	 * Returns the wrapper class.
	 * 
	 * @return the wrapper class
	 */
	public Class<UI> getWrapperClass() {
		return wrapperClass;
	}

	/**
	 * <p>
	 * Removes the {@link PropertyChangeListener}, which listens for "UI"
	 * property changes (if installed) and then calls
	 * {@link JComponent#updateUI()} on the <code>textComponent</code> to set
	 * the UI object provided by the current {@link UIDefaults}.
	 * </p>
	 * 
	 * @param textComponent
	 */
	public final void uninstall(final JTextComponent textComponent) {
		uiChangeHandler.uninstall(textComponent);
		textComponent.updateUI();
	}

	private final TextUIChangeHandler uiChangeHandler = new TextUIChangeHandler();
	private final class TextUIChangeHandler extends AbstractUIChangeHandler {
		public void propertyChange(PropertyChangeEvent evt) {
			JTextComponent txt = (JTextComponent) evt.getSource();

			replaceUIIfNeeded(txt);
		}
	}
}
