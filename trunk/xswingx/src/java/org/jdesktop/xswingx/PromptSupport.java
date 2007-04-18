package org.jdesktop.xswingx;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import org.jdesktop.xswingx.plaf.PromptTextAreaUI;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;
import org.jdesktop.xswingx.plaf.PromptTextUI;

/**
 * <p>
 * Sets label text, label text color and {@link FocusBehavior} properties on a
 * JTextComponent by calling
 * {@link JTextComponent#putClientProperty(Object, Object)}.
 * </p>
 * 
 * <p>
 * This class is used by {@link JXPromptField}, {@link JXFormattedPromptField}
 * and {@link JXPromptArea} to get and set label properties.
 * {@link PromptTextUI} retrieves these properties using PromptSupport.
 * </p>
 * 
 * @see JXPromptField
 * @see JXFormattedPromptField
 * @see JXPromptArea
 * @see PromptTextUI
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class PromptSupport {
	/**
	 * The prompt text property.
	 */
	public static final String PROMPT = "promptText";

	/**
	 * The color of the prompt text poroperty.
	 */
	public static final String PROMPT_COLOR = "promptColor";

	/**
	 * The focus behavior property.
	 */
	public static final String FOCUS_BEHAVIOR = "focusBehavior";

	/**
	 * The font style property, if different from the components font.
	 */
	public static final String PROMPT_FONT_STYLE = "promptFontStyle";

	/**
	 * <p>
	 * Determines how the {@link JTextComponent} is rendered when focused and no
	 * text is present.
	 * </p>
	 */
	public static enum FocusBehavior {
		/**
		 * Keep the prompt text visible.
		 */
		SHOW_PROMPT,
		/**
		 * Highlight the prompt text as it would be selected.
		 */
		HIGHLIGHT_PROMPT,
		/**
		 * Hide the prompt text.
		 */
		HIDE_PROMPT
	};

	/**
	 * Installs a prompt on <code>textComponent</code> by calling
	 * {@link #install(String, Color, JTextComponent)} with <code>null</code>
	 * color.
	 * 
	 * @param promptText
	 * @param textComponent
	 */
	public static void install(String promptText,
			final JTextComponent textComponent) {
		install(promptText, null, textComponent);
	}

	/**
	 * <p>
	 * Wraps the current UI of the given <code>textComponent</code>, by
	 * calling {@link #wrapUI(JTextComponent)} and calls
	 * {@link #init(String, Color, JTextComponent)} with the given parameters.
	 * </p>
	 * <p>
	 * Additionally a {@link PropertyChangeListener} is registered, which
	 * listens for UI changes and wraps the new UI object.
	 * </p>
	 * 
	 * @param promptText
	 * @param promptTextColor
	 * @param textComponent
	 */
	public static void install(String promptText, Color promptTextColor,
			final JTextComponent textComponent) {
		textComponent.setUI(wrapUI(textComponent));
		textComponent.addPropertyChangeListener("UI", uiChangeHandler);
		init(promptText, promptTextColor, textComponent);
	}

	/**
	 * Wraps the UI of <code>textComponent</code>.
	 * 
	 * @see #wrapUI(TextUI)
	 * @param textComponent
	 * @return
	 */
	public static PromptTextUI wrapUI(JTextComponent textComponent) {
		return wrapUI(textComponent.getUI());
	}

	/**
	 * <p>
	 * Creates a new {@link PromptTextUI}, which wraps the given
	 * <code>textUI</code>.
	 * </p>
	 * <p>
	 * If <code>textUI</code> is of type {@link PromptTextUI},
	 * <code>textUI</code> will be returned. If <code>textUI</code> is of
	 * type {@link BasicTextFieldUI} a {@link PromptTextFieldUI} object will be
	 * returned. If <code>textUI</code> is of type {@link BasicTextAreaUI} a
	 * {@link PromptTextAreaUI} object will be returned.
	 * </p>
	 * 
	 * @param textUI
	 *            wrap this UI
	 * @return a {@link PromptTextUI} which wraps <code>textUI</code>
	 */
	public static PromptTextUI wrapUI(TextUI textUI) {
		if (textUI instanceof PromptTextUI) {
			return (PromptTextUI) textUI;
		} else if (textUI instanceof BasicTextFieldUI) {
			return new PromptTextFieldUI(textUI);
		} else if (textUI instanceof BasicTextAreaUI) {
			return new PromptTextAreaUI(textUI);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * <p>
	 * Convenience method to set the <code>promptText</code> and
	 * <code>promptTextColor</code> on a {@link JTextComponent}.
	 * </p>
	 * 
	 * @param promptText
	 * @param promptTextColor
	 * @param textComponent
	 */
	public static void init(String promptText, Color promptTextColor,
			final JTextComponent textComponent) {
		setPrompt(promptText, textComponent);
		setPromptColor(promptTextColor, textComponent);
	}

	/**
	 * Get the {@link FocusBehavior} of <code>textComponent</code>.
	 * 
	 * @param textComponent
	 * @return the {@link FocusBehavior} or {@link FocusBehavior#HIDE_PROMPT} if
	 *         none is set
	 */
	public static FocusBehavior getFocusBehavior(JTextComponent textComponent) {
		FocusBehavior fb = (FocusBehavior) textComponent
				.getClientProperty(FOCUS_BEHAVIOR);
		if (fb == null) {
			fb = FocusBehavior.HIDE_PROMPT;
		}
		return fb;
	}

	/**
	 * Sets the {@link FocusBehavior} on <code>textComponent</code> and
	 * repaints the component to reflect the changes, if it is the focus owner.
	 * 
	 * @param focusBehavior
	 * @param textComponent
	 */
	public static void setFocusBehavior(FocusBehavior focusBehavior,
			JTextComponent textComponent) {
		textComponent.putClientProperty(FOCUS_BEHAVIOR, focusBehavior);
		if (textComponent.isFocusOwner()) {
			textComponent.repaint();
		}
	}

	/**
	 * Get the prompt text of <code>textComponent</code>.
	 * 
	 * @param textComponent
	 * @return the prompt text
	 */
	public static String getPrompt(JTextComponent textComponent) {
		return (String) textComponent.getClientProperty(PROMPT);
	}

	/**
	 * Sets the prompt text on <code>textComponent</code>. Also sets the
	 * tooltip text to the prompt text if <code>textComponent</code> has no
	 * tooltip text or the current tooltip text is the same as the current label
	 * text.
	 * 
	 * @param promptText
	 * @param textComponent
	 */
	public static void setPrompt(String promptText, JTextComponent textComponent) {
		// display labelText as tooltip by default
		if (textComponent.getToolTipText() == null
				|| textComponent.getToolTipText().equals(
						getPrompt(textComponent))) {
			textComponent.setToolTipText(promptText);
		}

		textComponent.putClientProperty(PROMPT, promptText);
	}

	/**
	 * Get the color of the prompt text. If no color has been set, the
	 * <code>textComponent</code>s disabled text color will be returned.
	 * 
	 * @param textComponent
	 * @return the color of the prompt text or
	 *         {@link JTextComponent#getDisabledTextColor()} if none is set
	 */
	public static Color getPromptColor(JTextComponent textComponent) {
		if (textComponent.getClientProperty(PROMPT_COLOR) == null) {
			return textComponent.getDisabledTextColor();
		}
		return (Color) textComponent.getClientProperty(PROMPT_COLOR);
	}

	/**
	 * Sets the color of the prompt text on <code>textComponent</code> and
	 * repaints the component to reflect the changes.
	 * 
	 * @param promptTextColor
	 * @param textComponent
	 */
	public static void setPromptColor(Color promptTextColor,
			JTextComponent textComponent) {
		textComponent.putClientProperty(PROMPT_COLOR, promptTextColor);
		textComponent.repaint();
	}

	/**
	 * <p>
	 * Set the style of the prompt font, if different from the
	 * <code>textComponent</code>s font.
	 * </p>
	 * <p>
	 * Allowed values are {@link Font#PLAIN}, {@link Font#ITALIC},
	 * {@link Font#BOLD}, a combination of {@link Font#BOLD} and
	 * {@link Font#ITALIC} or <code>null</code> if the prompt font should be
	 * the same as the <code>textComponent</code>s font.
	 * </p>
	 * 
	 * @param fontStyle
	 * @param textComponent
	 */
	public static void setPromptFontStyle(Integer fontStyle,
			JTextComponent textComponent) {
		textComponent.putClientProperty(PROMPT_FONT_STYLE, fontStyle);
		textComponent.revalidate();
		textComponent.repaint();
	}

	/**
	 * Returns the font style of the prompt text, or <code>null</code> if the
	 * prompt's font style should not differ from the <code>textComponent</code>s
	 * font.
	 * 
	 * @param textComponent
	 * @return font style of the prompt text
	 */
	public static Integer getPromptFontStyle(JTextComponent textComponent) {
		return (Integer) textComponent.getClientProperty(PROMPT_FONT_STYLE);
	}

	public final static UIChangeHandler uiChangeHandler = new UIChangeHandler();

	private final static class UIChangeHandler implements
			PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			JTextComponent txt = (JTextComponent) evt.getSource();
			TextUI ui = (TextUI) evt.getNewValue();

			if (!(ui instanceof PromptTextUI)) {
				txt.setUI(wrapUI(ui));
			}
		}
	}
}
