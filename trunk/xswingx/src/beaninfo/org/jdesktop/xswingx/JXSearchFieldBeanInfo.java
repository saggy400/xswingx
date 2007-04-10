package org.jdesktop.xswingx;

import org.jdesktop.swingx.EnumerationValue;
import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.JXSearchField.LayoutStyle;

public class JXSearchFieldBeanInfo extends JXPromptBeanInfo {
	public JXSearchFieldBeanInfo() {
		super(JXSearchField.class);
	}

	protected void initialize() {
		super.initialize();
		setPreferred(true, "layoutStyle");
		
		setEnumerationValues(
				new EnumerationValue[] {
						new EnumerationValue("Mac", LayoutStyle.MAC,
								"org.jdesktop.xswingx.JXSearchField.LayoutStyle.MAC"),
						new EnumerationValue("Vista", LayoutStyle.VISTA,
								"org.jdesktop.xswingx.JXSearchField.LayoutStyle.VISTA") }, "layoutStyle");
	}
}
