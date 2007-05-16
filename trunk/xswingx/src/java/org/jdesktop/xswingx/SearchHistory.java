package org.jdesktop.xswingx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class SearchHistory {
	private Preferences prefs;

	private int maxRecents = 5;

	private List<String> recentSearches = new ArrayList<String>();

	public SearchHistory(String saveName) {
		this(Preferences.userRoot(), saveName);
	}

	public SearchHistory(Preferences prefs, String saveName) {
		this.prefs = prefs.node(saveName);
		load();
	}

	private void load() {
		try {
			String[] recent = new String[prefs.keys().length];
			for (String key : prefs.keys()) {
				recent[prefs.getInt(key, -1)] = key;
			}
			recentSearches.addAll(Arrays.asList(recent));
		} catch (Exception ex) {
			//ignore
		}
	}

	private void save() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			// ignore
		}

		int i = 0;
		for (String search : recentSearches) {
			prefs.putInt(search, i++);
		}
	}

	public void put(String searchString) {
		if(recentSearches.contains(searchString)){
			return;
		}
		
		recentSearches.add(0, searchString);
		if (getLength() > getMaxRecents()) {
			recentSearches.remove(recentSearches.size() - 1);
		}
		save();
	}

	public String[] getRecentSearches() {
		return recentSearches.toArray(new String[] {});
	}

	public int getLength() {
		return recentSearches.size();
	}

	public void removeAll() {
		recentSearches.clear();
		save();
	}

	public JPopupMenu createPopupMenu(JXSearchField searchField) {
		return new HistoryPopup();
	}

	public int getMaxRecents() {
		return maxRecents;
	}

	public void setMaxRecents(int maxRecents) {
		this.maxRecents = maxRecents;
	}

	public void install(JXSearchField searchField) {
		
	}
	
	class HistoryPopup extends JPopupMenu{
		public HistoryPopup() {
			JMenuItem recent = new JMenuItem("Recent Searches");
			recent.setEnabled(false);
			add(recent);
			
			for (String searchString : getRecentSearches()) {
				add(new JMenuItem(searchString));
			}
			
			addSeparator();
			JMenuItem clear = new JMenuItem("Clear Recent Searches");
			add(clear);
		}
	}
}
