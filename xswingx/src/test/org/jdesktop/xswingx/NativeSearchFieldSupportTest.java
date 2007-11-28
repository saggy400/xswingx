package org.jdesktop.xswingx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;

import junit.framework.Assert;

import org.junit.Test;


public class NativeSearchFieldSupportTest implements PropertyChangeListener {
	private JTextField tf = new JTextField();
	private boolean propertyChanged;
	
	@Test
	public void testPropertyChangeEvent() throws Exception {
		tf.addPropertyChangeListener(NativeSearchFieldSupport.MAC_TEXT_FIELD_VARIANT_PROPERTY, this);
		NativeSearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(propertyChanged);
		propertyChanged = false;
		NativeSearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(propertyChanged);
	}
	
	@Test
	public void testSearchFieldUIChange() throws Exception {
		NativeSearchFieldSupport.setSearchField(tf, true);
		tf.addPropertyChangeListener(NativeSearchFieldSupport.MAC_TEXT_FIELD_VARIANT_PROPERTY, this);
		tf.updateUI();
		Assert.assertTrue(propertyChanged);
		
		NativeSearchFieldSupport.setSearchField(tf, false);
		propertyChanged = false;
		tf.updateUI();
		Assert.assertFalse(propertyChanged);
	}
	
	@Test
	public void testIsSearchField() throws Exception {
		NativeSearchFieldSupport.setSearchField(tf, true);
		Assert.assertTrue(NativeSearchFieldSupport.isSearchField(tf));
		
		NativeSearchFieldSupport.setSearchField(tf, false);
		Assert.assertFalse(NativeSearchFieldSupport.isSearchField(tf));
	}

	public void propertyChange(PropertyChangeEvent evt) {
		propertyChanged = true;
	}
}
