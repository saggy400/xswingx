package org.jdesktop.xswingx;

import static org.junit.Assert.assertEquals;
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
		sh.put("search1");
		assertSame(1, sh.getLength());
		sh.put("search2");
		
		sh = new SearchHistory("search.test");
		assertSame(2, sh.getLength());
	}
	
	@Test
	public void testRecentsOrder() throws Exception {
		searchHistory.setMaxRecents(2);
		searchHistory.put("search1");
		searchHistory.put("search2");
		assertEquals("search2", searchHistory.getRecentSearches()[0]);
		assertEquals("search1", searchHistory.getRecentSearches()[1]);
		
		searchHistory.put("search3");
		assertSame(2, searchHistory.getLength());
		assertEquals("search3", searchHistory.getRecentSearches()[0]);
		assertEquals("search2", searchHistory.getRecentSearches()[1]);
		
		searchHistory.put("search2");
		assertEquals("search2", searchHistory.getRecentSearches()[0]);
	}
	
	@Test
	public void testDoubleAdd() throws Exception {
		searchHistory.put("search1");
		searchHistory.put("search1");
		assertSame(1, searchHistory.getLength());
	}
	
	@Test
	public void testEmptyAdd() throws Exception {
		searchHistory.put(null);
		searchHistory.put("");
		searchHistory.put(" ");
		assertSame(0, searchHistory.getLength());
	}
	
	@Test
	public void testMaxRecents() throws Exception {
		assertSame(5, searchHistory.getMaxRecents());
		searchHistory.setMaxRecents(1);
		assertSame(1, searchHistory.getMaxRecents());
		searchHistory.put("search1");
		searchHistory.put("search2");
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
