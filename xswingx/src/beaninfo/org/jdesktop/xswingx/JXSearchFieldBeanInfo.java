package org.jdesktop.xswingx;

import org.jdesktop.swingx.EnumerationValue;
import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import org.jdesktop.xswingx.JXSearchField.SearchMode;

public class JXSearchFieldBeanInfo extends JXTextFieldBeanInfo {
	public JXSearchFieldBeanInfo() {
		super(JXSearchField.class);
	}

	protected void initialize() {
		super.initialize();
		setPreferred(true, "layoutStyle", "searchMode", "instantSearchDelay", "findPopupMenu", "useNativeSearchFieldIfPossible", "recentSearchesSaveKey");

		setEnumerationValues(
				new EnumerationValue[] {
						new EnumerationValue("Mac", LayoutStyle.MAC,
								"org.jdesktop.xswingx.JXSearchField.LayoutStyle.MAC"),
						new EnumerationValue("Vista", LayoutStyle.VISTA,
								"org.jdesktop.xswingx.JXSearchField.LayoutStyle.VISTA") },
				"layoutStyle");
		setEnumerationValues(
				new EnumerationValue[] {
						new EnumerationValue("Instant", SearchMode.INSTANT,
								"org.jdesktop.xswingx.JXSearchField.SearchMode.INSTANT"),
						new EnumerationValue("Regular", SearchMode.REGULAR,
								"org.jdesktop.xswingx.JXSearchField.SearchMode.REGULAR") },
				"searchMode");
	}
}
