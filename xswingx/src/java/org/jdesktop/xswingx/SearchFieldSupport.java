package org.jdesktop.xswingx;

import java.beans.PropertyChangeEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdesktop.swingx.util.OS;
import org.jdesktop.xswingx.plaf.AbstractUIChangeHandler;

/**
 * TODO
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class SearchFieldSupport {
	private static final String MAC_SEARCH_VARIANT = "search";
	public static final String MAC_TEXT_FIELD_VARIANT = "JTextField.variant";

	/**
	 * @return <code>true</code> if we run Leopard and the Mac Look And Feel.
	 */
	public static boolean isNativeSearchFieldSupported() {
		try {
			return OS.isMacOSX() && Float.parseFloat(System.getProperty("os.version")) >= 10.5
					&& UIManager.getLookAndFeel().getName().equals("Mac OS X");
		} catch (Exception e) {
			// in case the os.version cannot be parsed, we are surely not
			// running mac os x.
			return false;
		}
	}

	public static void setSearchField(JTextField txt, boolean isSearchField) {
		// Leopard Hack: ensure property change event is triggered, if nothing
		// changes.
		if (isSearchField == isSearchField(txt)) {
			txt.putClientProperty(MAC_TEXT_FIELD_VARIANT, "_triggerevent_");
		} else if (isSearchField) {
			// if we have a search field here, register listener for ui changes
			// (leopard hack)
			uiChangeHandler.install(txt);
		} else {
			// if we don't have a search field, we don't need to listen anymore.
			uiChangeHandler.uninstall(txt);
		}

		if (isSearchField) {
			txt.putClientProperty(MAC_TEXT_FIELD_VARIANT, MAC_SEARCH_VARIANT);
			txt.putClientProperty("Quaqua.TextField.style", MAC_SEARCH_VARIANT);

		} else {
			txt.putClientProperty(MAC_TEXT_FIELD_VARIANT, "default");
			txt.putClientProperty("Quaqua.TextField.style", "default");
		}
	}

	public static boolean isSearchField(JTextField txt) {
		return MAC_SEARCH_VARIANT.equals(txt.getClientProperty(MAC_TEXT_FIELD_VARIANT));
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

	private static final SearchFieldUIChangeHandler uiChangeHandler = new SearchFieldUIChangeHandler();

	private static final class SearchFieldUIChangeHandler extends AbstractUIChangeHandler {
		public void propertyChange(PropertyChangeEvent evt) {
			JTextField txt = (JTextField) evt.getSource();
			System.err.println("UI cahnge");
			// Leopard hack to make appear correctly in search variant when
			// changing LnF.
			setSearchField(txt, isSearchField(txt));
		}
	}
}
