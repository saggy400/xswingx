package org.jdesktop.xswingx;

import java.awt.Font;

import org.jdesktop.swingx.BeanInfoSupport;
import org.jdesktop.swingx.EnumerationValue;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

public abstract class JXPromptBeanInfo extends BeanInfoSupport {
	public JXPromptBeanInfo(Class beanClass) {
		super(beanClass);
	}

	protected void initialize() {
		setPreferred(true, "prompt", "promptColor", "focusBehavior",
				"promptFontStyle");
		
		setEnumerationValues(new EnumerationValue[] {
				new EnumerationValue("Default", null, "null"),
				new EnumerationValue("Plain", Font.PLAIN, "java.awt.Font.PLAIN"),
				new EnumerationValue("Bold", Font.BOLD, "java.awt.Font.BOLD"),
				new EnumerationValue("Italic", Font.ITALIC, "java.awt.Font.ITALIC"),
				new EnumerationValue("Bold & Italic", Font.BOLD | Font.ITALIC,
						"java.awt.Font.BOLD | java.awt.Font.ITALIC") }, "promptFontStyle");
		setEnumerationValues(new EnumerationValue[] {
				new EnumerationValue("Show", FocusBehavior.SHOW_PROMPT,
						"org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT"),
				new EnumerationValue("Hide", FocusBehavior.HIDE_PROMPT,
						"org.jdesktop.xswingx.PromptSupport.FocusBehavior.HIDE_PROMPT"),
				new EnumerationValue("Highlight", FocusBehavior.HIGHLIGHT_PROMPT,
						"org.jdesktop.xswingx.PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT"), }, "focusBehavior");
	}
}
