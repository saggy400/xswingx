package org.jdesktop.xswingx.plaf;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import junit.framework.TestCase;

import org.jdesktop.xswingx.BuddySupport;
import org.junit.Before;
import org.junit.Test;

public class BuddyLayoutAndBorderTest {
	private BuddyLayoutAndBorder blab;
	private JTextField textField;
	
	@Before
	public void setup() {
		textField = new JTextField();
		blab = new BuddyLayoutAndBorder(textField);
	}

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
	
	@Test
	public void testBorder() throws Exception {
		Border newBorder = BorderFactory.createEmptyBorder();
		textField.setBorder(newBorder);
		assertNotSame("Border should have been wrapped.", newBorder, textField.getBorder());
	}
	
	@Test
	public void testUninstall() throws Exception {
		blab.uninstall(textField);
		Border newBorder = BorderFactory.createEmptyBorder();
		textField.setBorder(newBorder);
		assertSame("Border should NOT have been wrapped.", newBorder, textField.getBorder());
	}
	
	@Test
	public void testPreferredWidth() throws Exception {
		JButton btn = new JButton("hey");
		int txtWidth = textField.getPreferredSize().width;
		int btnWidth = btn.getPreferredSize().width;
		
		assertSame(txtWidth, blab.preferredLayoutSize(textField).width);
		
		textField.add(btn, BuddySupport.LEFT);
		
		assertSame(txtWidth+btnWidth, blab.preferredLayoutSize(textField).width);
		
		btn.setVisible(false);
		assertSame(txtWidth+btnWidth, blab.preferredLayoutSize(textField).width);
	}
	
	@Test
	public void testBorderInsets() throws Exception {
		JButton btn = new JButton("hey");
		int left = blab.getBorderInsets(textField).left;
		int btnWidth = btn.getPreferredSize().width;
		
		textField.add(btn, BuddySupport.LEFT);
		
		assertSame(left+btnWidth, blab.getBorderInsets(textField).left);
		
		btn.setVisible(false);
		assertSame(left+btnWidth, blab.getBorderInsets(textField).left);
	}
}
