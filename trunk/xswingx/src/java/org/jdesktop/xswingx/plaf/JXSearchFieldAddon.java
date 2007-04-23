package org.jdesktop.xswingx.plaf;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;

import org.jdesktop.swingx.plaf.AbstractComponentAddon;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;

public class JXSearchFieldAddon extends AbstractComponentAddon {
	public JXSearchFieldAddon() {
		super("JXSearchField");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, List<Object> defaults) {
		super.addBasicDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.icon", getIcon("basic/resources/search.png"),
				"SearchField.clearIcon", getIcon("basic/resources/clear.png"), "SearchField.clearRolloverIcon",
				getIcon("basic/resources/clear_rollover.png"), "SearchField.clearPressedIcon",
				getIcon("basic/resources/clear_pressed.png"), "SearchField.buttonMargin",
				new InsetsUIResource(1, 1, 1, 1) }));
	}

	@Override
	protected void addMetalDefaults(LookAndFeelAddons addon, List<Object> defaults) {
		super.addMetalDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.buttonMargin", new InsetsUIResource(1, 1, 2, 1) }));
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, List<Object> defaults) {
		super.addWindowsDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.icon", getIcon("windows/resources/search.png"),
				"SearchField.clearIcon", getIcon("windows/resources/clear.png"), "SearchField.clearRolloverIcon",
				getIcon("windows/resources/clear_rollover.png"), "SearchField.clearPressedIcon",
				getIcon("windows/resources/clear_pressed.png"), "SearchField.buttonMargin",
				new InsetsUIResource(0, 0, 0, 0) }));
	}

	protected void addMacDefaults(LookAndFeelAddons addon, List<Object> defaults) {
		super.addMacDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.icon", getIcon("macosx/resources/search.png"),
				"SearchField.clearIcon", getIcon("macosx/resources/clear.png"), "SearchField.clearRolloverIcon",
				getIcon("macosx/resources/clear_rollover.png"), "SearchField.clearPressedIcon",
				getIcon("macosx/resources/clear_pressed.png"), "SearchField.buttonMargin",
				new InsetsUIResource(0, 0, 0, 0) }));

	}

	private IconUIResource getIcon(String resourceName) {
		URL url = getClass().getResource(resourceName);
		if (url == null) {
			return null;
		} else {
			return new IconUIResource(new ImageIcon(url));
		}
	}
}
