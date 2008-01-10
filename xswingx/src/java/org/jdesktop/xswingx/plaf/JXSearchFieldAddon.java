package org.jdesktop.xswingx.plaf;

import java.awt.Font;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;

import org.jdesktop.swingx.plaf.AbstractComponentAddon;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.plaf.UIManagerExt;
import org.jdesktop.xswingx.JXSearchField.LayoutStyle;

public class JXSearchFieldAddon extends AbstractComponentAddon {
	public static final String SEARCH_FIELD_SOURCE = "searchField";
	public static final String BUTTON_SOURCE = "button";

	public JXSearchFieldAddon() {
		super("JXSearchField");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon,
			List<Object> defaults) {
		super.addBasicDefaults(addon, defaults);
		defaults
				.addAll(Arrays.asList(new Object[] { "SearchField.layoutStyle",
						LayoutStyle.MAC, "SearchField.icon",
						getIcon("basic/resources/search.gif"),
						"SearchField.rolloverIcon",
						getIcon("basic/resources/search_rollover.gif"),
						"SearchField.pressedIcon",
						getIcon("basic/resources/search.gif"),
						"SearchField.popupIcon",
						getIcon("basic/resources/search_popup.gif"),
						"SearchField.popupRolloverIcon",
						getIcon("basic/resources/search_popup_rollover.gif"),
						"SearchField.clearIcon",
						getIcon("basic/resources/clear.gif"),
						"SearchField.clearRolloverIcon",
						getIcon("basic/resources/clear_rollover.gif"),
						"SearchField.clearPressedIcon",
						getIcon("basic/resources/clear_pressed.gif"),
						"SearchField.buttonMargin",
						new InsetsUIResource(1, 1, 1, 1),
						"SearchField.popupSource", BUTTON_SOURCE}));
		
		//webstart fix
		UIManagerExt.addResourceBundle("org.jdesktop.xswingx.plaf.basic.resources.SearchField");
//		UIManager.getDefaults().addResourceBundle("org.jdesktop.xswingx.plaf.basic.resources.SearchField");
	}

	@Override
	protected void addMetalDefaults(LookAndFeelAddons addon,
			List<Object> defaults) {
		super.addMetalDefaults(addon, defaults);
		defaults
				.addAll(Arrays.asList(new Object[] {
						"SearchField.buttonMargin",
						new InsetsUIResource(0, 0, 1, 1) }));
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon,
			List<Object> defaults) {
		super.addWindowsDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.promptFontStyle",
				Font.ITALIC, "SearchField.layoutStyle",
				LayoutStyle.VISTA, "SearchField.icon",
				getIcon("windows/resources/search.gif"),
				"SearchField.rolloverIcon",
				getIcon("windows/resources/search_rollover.gif"),
				"SearchField.pressedIcon",
				getIcon("windows/resources/search_pressed.gif"),
				"SearchField.popupIcon",
				getIcon("windows/resources/search_popup.gif"),
				"SearchField.popupRolloverIcon",
				getIcon("windows/resources/search_popup_rollover.gif"),
				"SearchField.popupPressedIcon",
				getIcon("windows/resources/search_popup_pressed.gif"),
				"SearchField.clearIcon",
				getIcon("windows/resources/clear.gif"),
				"SearchField.clearRolloverIcon",
				getIcon("windows/resources/clear_rollover.gif"),
				"SearchField.clearPressedIcon",
				getIcon("windows/resources/clear_pressed.gif"),
				"SearchField.useSeperatePopupButton", Boolean.TRUE,
				"SearchField.popupOffset", -1 }));

		defaults.add("SearchField.buttonMargin");

		// Do it like 'Windows Media Player' in XP:
		// Replace the border line with the search button line on rollover.
		// But not in classic mode!
		if (UIManager.getLookAndFeel().getClass().getName().indexOf(
						"Classic") == -1) {
			defaults.add(new InsetsUIResource(0, -1, 0, -1));
		} else {
			defaults.add(new InsetsUIResource(0, 0, 0, 0));
		}
	}

	protected void addMotifDefaults(LookAndFeelAddons addon,
			List<Object> defaults) {
		super.addMotifDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] { "SearchField.icon",
				getIcon("macosx/resources/search.png"),
				"SearchField.rolloverIcon",
				getIcon("macosx/resources/search.png"),
				"SearchField.pressedIcon",
				getIcon("macosx/resources/search.png"),
				"SearchField.popupIcon",
				getIcon("macosx/resources/search_popup.png"),
				"SearchField.popupRolloverIcon",
				getIcon("macosx/resources/search_popup.png"),
				"SearchField.popupPressedIcon",
				getIcon("macosx/resources/search_popup.png"),
				"SearchField.clearIcon", getIcon("macosx/resources/clear.png"),
				"SearchField.clearRolloverIcon",
				getIcon("macosx/resources/clear_rollover.png"),
				"SearchField.clearPressedIcon",
				getIcon("macosx/resources/clear_pressed.png") }));
	}

	protected void addMacDefaults(LookAndFeelAddons addon, List<Object> defaults) {
		super.addMacDefaults(addon, defaults);
		defaults
				.addAll(Arrays.asList(new Object[] { "SearchField.icon",
						getIcon("macosx/resources/search.png"),
						"SearchField.rolloverIcon",
						getIcon("macosx/resources/search.png"),
						"SearchField.pressedIcon",
						getIcon("macosx/resources/search.png"),
						"SearchField.popupIcon",
						getIcon("macosx/resources/search_popup.png"),
						"SearchField.popupRolloverIcon",
						getIcon("macosx/resources/search_popup.png"),
						"SearchField.popupPressedIcon",
						getIcon("macosx/resources/search_popup.png"),
						"SearchField.clearIcon",
						getIcon("macosx/resources/clear.png"),
						"SearchField.clearRolloverIcon",
						getIcon("macosx/resources/clear_rollover.png"),
						"SearchField.clearPressedIcon",
						getIcon("macosx/resources/clear_pressed.png"),
						"SearchField.buttonMargin",
						new InsetsUIResource(0, 0, 0, 0),
						"SearchField.popupSource", SEARCH_FIELD_SOURCE}));

	}

	// Workaround: Only return true, when the current LnF is Windows or
	// PlasticXP.
	protected boolean isWindows(LookAndFeelAddons addon) {
		return super.isWindows(addon)
				|| (UIManager.getLookAndFeel().getClass().getName().indexOf(
						"Windows") != -1 || UIManager.getLookAndFeel()
						.getClass().getName().indexOf("PlasticXP") != -1);
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
