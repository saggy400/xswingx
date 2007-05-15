package org.jdesktop.xswingx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;


public class SearchHistoryTest {
	private SearchHistory searchHistory;

	@Before
	public void setUp() {
		searchHistory = new SearchHistory("search.test");
		searchHistory.removeAll();
	}
	
	@Test
	public void testCreateRemoveAdd() throws Exception {
		SearchHistory sh = new SearchHistory(Preferences.userRoot(), "search.test");
		sh.removeAll();
		
		assertSame(0, sh.getLength());
		sh.add("search1");
		assertSame(1, sh.getLength());
		sh.add("search2");
		
		sh = new SearchHistory("search.test");
		assertSame(2, sh.getLength());
	}
	
	@Test
	public void testRecentsOrder() throws Exception {
		searchHistory.setMaxRecents(2);
		searchHistory.add("search1");
		searchHistory.add("search2");
		assertEquals("search2", searchHistory.getRecentSearches()[0]);
		assertEquals("search1", searchHistory.getRecentSearches()[1]);
		
		searchHistory.add("search3");
		assertSame(2, searchHistory.getLength());
		assertEquals("search3", searchHistory.getRecentSearches()[0]);
		assertEquals("search2", searchHistory.getRecentSearches()[1]);
	}
	
	@Test
	public void testMaxRecents() throws Exception {
		assertSame(5, searchHistory.getMaxRecents());
		searchHistory.setMaxRecents(1);
		assertSame(1, searchHistory.getMaxRecents());
		searchHistory.add("search1");
		searchHistory.add("search2");
		assertSame(1, searchHistory.getLength());
	}
	
	@Test
	public void testPopupMenu() throws Exception {
//		assertFalse(searchHistory.createPopupMenu().getComponent(0).isEnabled());
//		assertSame(3, searchHistory.createPopupMenu().getComponentCount());
//		searchHistory.add("search1");
//		assertSame(4, searchHistory.createPopupMenu().getComponentCount());
	}
	
	@Test
	public void testInstall() throws Exception {
		JXSearchField searchField = new JXSearchField("Search");
		searchHistory.install(searchField);
	}
}
