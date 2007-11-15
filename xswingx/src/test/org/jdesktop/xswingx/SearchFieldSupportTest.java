package org.jdesktop.xswingx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;

import junit.framework.Assert;

import org.junit.Test;


public class SearchFieldSupportTest implements PropertyChangeListener {
	private JTextField tf = new JTextField();
	private boolean propertyChanged;
	
	@Test
	public void testPropertyChangeEvent() throws Exception {
		tf.addPropertyChangeListener(SearchFieldSupport.MAC_TEXT_FIELD_VARIANT, this);
		SearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(propertyChanged);
		propertyChanged = false;
		SearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(propertyChanged);
	}
	
	@Test
	public void testSearchFieldUIChange() throws Exception {
		SearchFieldSupport.setSearchField(tf, true);
		tf.addPropertyChangeListener(SearchFieldSupport.MAC_TEXT_FIELD_VARIANT, this);
		tf.updateUI();
		Assert.assertTrue(propertyChanged);
		
		SearchFieldSupport.setSearchField(tf, false);
		propertyChanged = false;
		tf.updateUI();
		Assert.assertFalse(propertyChanged);
	}
	
	@Test
	public void testIsSearchField() throws Exception {
		SearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(SearchFieldSupport.isSearchField(tf));
		
		SearchFieldSupport.setSearchField(tf, false);
		Assert.assertFalse(SearchFieldSupport.isSearchField(tf));
	}

	public void propertyChange(PropertyChangeEvent evt) {
		propertyChanged = true;
	}
}
