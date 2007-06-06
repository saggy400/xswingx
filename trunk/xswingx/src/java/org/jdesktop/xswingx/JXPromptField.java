package org.jdesktop.xswingx;

import java.awt.Color;

import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport.FocusBehavior;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;

/**
 * {@link JTextField}, which wraps the default UI class into a
 * {@link PromptTextFieldUI} and provides convenience methods for getting and
 * setting prompt properties supported by {@link PromptSupport}.
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class JXPromptField extends JTextField {
	public JXPromptField() {
		this(null);
	}

	public JXPromptField(String labelText) {
		this(labelText, null);
	}

	public JXPromptField(String promptText, Color promptForeground) {
		this(promptText, promptForeground, null);
	}
	
	public JXPromptField(String promptText, Color promptForeground, Color promptBackground) {
		installPromptSupport(promptText, promptForeground, promptBackground);
	}

	/**
	 * Calls
	 * {@link PromptSupport#install(String, javax.swing.text.JTextComponent)}.
	 * Override this method to customize or disable prompt support.
	 * 
	 * @param promptText
	 * @param promptForeground
	 */
	protected void installPromptSupport(String promptText, Color promptForeground, Color promptBackground) {
		PromptSupport.init(promptText, promptForeground, promptBackground, this);
	}

	/**
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public FocusBehavior getFocusBehavior() {
		return PromptSupport.getFocusBehavior(this);
	}

	/**
	 * @see PromptSupport#getPrompt(javax.swing.text.JTextComponent)
	 */
	public String getPrompt() {
		return PromptSupport.getPrompt(this);
	}

	/**
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptForeground() {
		return PromptSupport.getForeground(this);
	}
	
	/**
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptBackground() {
		return PromptSupport.getBackground(this);
	}

	/**
	 * @see PromptSupport#getFontStyle(javax.swing.text.JTextComponent)
	 */
	public Integer getPromptFontStyle() {
		return PromptSupport.getFontStyle(this);
	}

	/**
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public void setFocusBehavior(FocusBehavior focusBehavior) {
		PromptSupport.setFocusBehavior(focusBehavior, this);
	}

	/**
	 * @see PromptSupport#setPrompt(String, javax.swing.text.JTextComponent)
	 */
	public void setPrompt(String labelText) {
		PromptSupport.setPrompt(labelText, this);
	}

	/**
	 * @see PromptSupport#setForeground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptForeground(Color promptTextColor) {
		PromptSupport.setForeground(promptTextColor, this);
	}
	
	/**
	 * @see PromptSupport#setBackground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptBackround(Color promptTextColor) {
		PromptSupport.setBackground(promptTextColor, this);
	}

	/**
	 * @see PromptSupport#setFontStyle(Integer,
	 *      javax.swing.text.JTextComponent)
	 */
	public void setPromptFontStyle(Integer fontStyle) {
		PromptSupport.setFontStyle(fontStyle, this);
	}
}
