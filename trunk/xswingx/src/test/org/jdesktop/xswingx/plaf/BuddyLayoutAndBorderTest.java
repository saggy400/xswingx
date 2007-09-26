package org.jdesktop.xswingx.plaf;

import javax.swing.JButton;
import javax.swing.JTextField;

import junit.framework.TestCase;

import org.jdesktop.xswingx.BuddySupport;
import org.jdesktop.xswingx.plaf.BuddyTextFieldUI.BuddyLayoutAndBorder;
import org.junit.Test;

public class BuddyLayoutAndBorderTest {
	private BuddyLayoutAndBorder blab = new BuddyLayoutAndBorder(new JTextField());

	@Test
	public void testAddLayoutComponentWithWrongConstraint() throws Exception {
		try {
			blab.addLayoutComponent("nonsense", new JButton());
			TestCase.fail();
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testAddLayoutComponentLeftAndRight() throws Exception {
		JButton btn = new JButton();

		blab.addLayoutComponent(BuddySupport.LEFT, btn);
		try {
			blab.addLayoutComponent(BuddySupport.RIGHT, btn);
			TestCase.fail();
		} catch (IllegalStateException e) {

		}
	}

	@Test
	public void testAddLayoutComponentRightAndLeft() throws Exception {
		JButton btn = new JButton();

		blab.addLayoutComponent(BuddySupport.RIGHT, btn);
		try {
			blab.addLayoutComponent(BuddySupport.LEFT, btn);
			TestCase.fail();
		} catch (IllegalStateException e) {

		}
	}

	@Test
	public void testAddLayoutComponent() throws Exception {
		blab.addLayoutComponent(BuddySupport.LEFT, new JButton());
		blab.addLayoutComponent(BuddySupport.RIGHT, new JButton());
	}

	@Test
	public void testRemoveLayoutComponent() throws Exception {
		JButton btn = new JButton();

		try {
			blab.removeLayoutComponent(btn);
			TestCase.fail();
		} catch (IllegalArgumentException e) {
		}
		
		blab.addLayoutComponent(BuddySupport.LEFT, btn);
		blab.removeLayoutComponent(btn);
		
		try {
			blab.removeLayoutComponent(btn);
			TestCase.fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
