package org.jdesktop.xswingx;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdesktop.swingx.util.OS;

/**
 * TODO
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class SearchFieldSupport {
	/**
	 * @return <code>true</code> if we run Leopard and the Mac Look And Feel.
	 */
	public static boolean isNativeSearchFieldSupported() {
		try {
			return OS.isMacOSX() && Float.parseFloat(System.getProperty("os.version")) >= 10.5 && UIManager.getLookAndFeel().getName().equals("Mac OS X");
		} catch (Exception e) {
			// in case the os.version cannot be parsed, we are surely not
			// running mac os x.
			return false;
		}
	}

	public static void setSearchField(JTextField txt, boolean isSearchField) {
		if (isSearchField) {
			txt.putClientProperty("JTextField.variant", "search");
			txt.putClientProperty("Quaqua.TextField.style", "search");
		} else {
			txt.putClientProperty("JTextField.variant", "default");
			txt.putClientProperty("Quaqua.TextField.style", "default");
		}
	}

	public static boolean isSearchField(JTextField txt) {
		return "search".equals(txt.getClientProperty("JTextField.variant"));
	}
	
	public static boolean isNativeSearchField(JTextField txt) {
		return isSearchField(txt) && isNativeSearchFieldSupported();
	}

	public static void setSearchPopupMenu(JTextField txt, JPopupMenu popupMenu) {
		txt.putClientProperty("JTextField.Search.FindPopup", popupMenu);
	}

	public static JPopupMenu getSearchPopupMenu(JTextField txt) {
		return (JPopupMenu) txt.getClientProperty("JTextField.Search.FindPopup");
	}
}
