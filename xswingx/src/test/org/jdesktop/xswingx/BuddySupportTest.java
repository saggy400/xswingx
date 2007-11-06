package org.jdesktop.xswingx;

import java.awt.Component;

import javax.swing.JTextField;

import junit.framework.Assert;

import org.jdesktop.xswingx.plaf.BuddyLayoutAndBorder;
import org.jdesktop.xswingx.plaf.BuddyTextFieldUI;
import org.junit.Test;

public class BuddySupportTest {
	private JTextField tf = new JTextField();
	

	@Test
	public void testInstall() throws Exception {
		BuddySupport.install(tf);
		
		Assert.assertSame(BuddyTextFieldUI.class, tf.getUI().getClass());
		Assert.assertSame(BuddyLayoutAndBorder.class, tf.getBorder().getClass());
		Assert.assertSame(BuddyLayoutAndBorder.class, tf.getLayout().getClass());
	}
	
	@Test
	public void testCreateNegativeGap() throws Exception {
		Component c = BuddySupport.createGap(-1);
		Assert.assertSame(-1, c.getPreferredSize().width);
	}

}
