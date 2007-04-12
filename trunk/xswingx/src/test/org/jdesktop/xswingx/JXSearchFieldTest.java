package org.jdesktop.xswingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JXSearchFieldTest {
	boolean action;
	JXSearchField searchField;

	@Before
	public void setUp() throws Exception {
		searchField = new JXSearchField();
	}
	
	@Test
	public void testFireActionOnTextChange() throws Exception {
		searchField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.err.println(e);
				action = true;
			}
		});
		action = false;
		Assert.assertFalse(searchField.isFireActionOnTextChange());
		searchField.setText("search");
		Assert.assertFalse(action);
		searchField.setFireActionOnTextChange(true);
		searchField.setText("search2");
		Assert.assertTrue(action);
	}
}
