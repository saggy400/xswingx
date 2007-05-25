package org.jdesktop.xswingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SearchHistory implements ActionListener {
	private Preferences prefs;

	private int maxRecents = 5;

	private List<String> recentSearches = new ArrayList<String>();

	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

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
			// ignore
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

		fireChangeEvent();
	}

	public void put(String searchString) {
		if (searchString == null || searchString.trim().length() == 0) {
			return;
		}

		int lastIndex = recentSearches.indexOf(searchString);
		if (lastIndex != -1) {
			recentSearches.remove(lastIndex);
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

	public int getMaxRecents() {
		return maxRecents;
	}

	public void setMaxRecents(int maxRecents) {
		this.maxRecents = maxRecents;
	}

	public void addChangeListener(ChangeListener l) {
		listeners.add(l);
	}

	public void removeChangeListener(ChangeListener l) {
		listeners.remove(l);
	}

	private void fireChangeEvent() {
		ChangeEvent e = new ChangeEvent(this);

		for (ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}

	public JPopupMenu createPopupMenu(JXSearchField searchField) {
		return new HistoryPopup(this, searchField);
	}

	public void install(JXSearchField searchField) {
		searchField.addActionListener(this);
		searchField.setSearchPopupMenu(createPopupMenu(searchField));
	}

	public void actionPerformed(ActionEvent e) {
		put(e.getActionCommand());
	}

	static class HistoryPopup extends JPopupMenu implements ActionListener,
			ChangeListener {
		private SearchHistory searchHistory;

		private JXSearchField searchField;

		private JMenuItem clear;

		public HistoryPopup(SearchHistory searchHistory,
				JXSearchField searchField) {
			this.searchField = searchField;
			this.searchHistory = searchHistory;

			searchHistory.addChangeListener(this);
			buildMenu();
		}

		private void buildMenu() {
			removeAll();

			JMenuItem recent = new JMenuItem("Recent Searches");
			recent.setEnabled(false);
			add(recent);

			for (String searchString : searchHistory.getRecentSearches()) {
				JMenuItem mi = new JMenuItem(searchString);
				mi.addActionListener(this);
				add(mi);
			}

			addSeparator();
			clear = new JMenuItem("Clear Recent Searches");
			clear.addActionListener(this);
			add(clear);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clear) {
				searchHistory.removeAll();
			} else {
				searchField.setText(e.getActionCommand());
			}
		}

		public void stateChanged(ChangeEvent e) {
			buildMenu();
		}
	}
}
