package org.jdesktop.xswingx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;

import junit.framework.Assert;

import org.jdesktop.xswingx.PromptSupport.FocusBehavior;
import org.jdesktop.xswingx.plaf.PromptTextAreaUI;
import org.jdesktop.xswingx.plaf.PromptTextFieldUI;
import org.junit.Before;
import org.junit.Test;

public class PromptSupportTest {
	private JTextField txt;

	@Before
	public void setup() {
		txt = new JTextField();
	}

	@Test
	public void testWrapUI() throws Exception {
		assertEquals(PromptTextFieldUI.class, PromptSupport.wrapUI(new JTextField()).getClass());
		assertEquals(PromptTextFieldUI.class, PromptSupport.wrapUI(new JFormattedTextField()).getClass());
		assertEquals(PromptTextAreaUI.class, PromptSupport.wrapUI(new JTextArea()).getClass());

		txt.setUI(PromptSupport.wrapUI(txt));
		assertSame(txt.getUI(), PromptSupport.wrapUI(txt));
	}

	@Test
	public void testInit() {
		PromptSupport.init("test", Color.LIGHT_GRAY, Color.YELLOW, txt, false);

		assertEquals("test", PromptSupport.getPrompt(txt));
		assertEquals("test", txt.getClientProperty(PromptSupport.PROMPT));

		assertEquals(Color.LIGHT_GRAY, PromptSupport.getForeground(txt));
		assertEquals(Color.LIGHT_GRAY, txt.getClientProperty(PromptSupport.FOREGROUND));

		assertEquals(Color.YELLOW, PromptSupport.getBackground(txt));
		assertEquals(Color.YELLOW, txt.getClientProperty(PromptSupport.BACKGROUND));
	}

	@Test
	public void testInitEmpty() {
		PromptSupport.init(null, null, null, txt, false);
		Assert.assertNotSame("PromptSupport should not be installed, unless it is necessary.", PromptTextFieldUI.class,
				txt.getUI().getClass());
	}

	@Test
	public void testUninstall() {
		Class defaultUiClass = txt.getUI().getClass();
		PromptSupport.uninstall(txt);
		assertEquals(defaultUiClass, txt.getUI().getClass());
		PromptSupport.setPrompt("test", txt);
		PromptSupport.uninstall(txt);
		assertEquals(defaultUiClass, txt.getUI().getClass());
	}

	@Test
	public void testGetFocusBehavior() throws Exception {
		assertEquals(FocusBehavior.HIDE_PROMPT, PromptSupport.getFocusBehavior(txt));
	}

	@Test
	public void testSetFocusBehavior() throws Exception {
		PromptSupport.setFocusBehavior(FocusBehavior.HIGHLIGHT_PROMPT, txt);

		assertEquals(FocusBehavior.HIGHLIGHT_PROMPT, PromptSupport.getFocusBehavior(txt));
		assertEquals(FocusBehavior.HIGHLIGHT_PROMPT, txt.getClientProperty(PromptSupport.FOCUS_BEHAVIOR));
	}

	@Test
	public void testSetPrompt() throws Exception {
		PromptSupport.setPrompt("test", txt);

		assertEquals("test", PromptSupport.getPrompt(txt));
		assertEquals("test", txt.getClientProperty(PromptSupport.PROMPT));
		assertEquals("test", txt.getToolTipText());

		promptSupportMustStayInstalled();

		PromptSupport.setPrompt("test2", txt);
		assertEquals("test2", txt.getToolTipText());

		txt.setToolTipText("ttt");
		PromptSupport.setPrompt("test3", txt);
		assertEquals("ttt", txt.getToolTipText());
	}

	@Test
	public void testGetForeground() throws Exception {
		assertEquals(txt.getDisabledTextColor(), PromptSupport.getForeground(txt));
	}

	@Test
	public void testSetForeground() throws Exception {
		PromptSupport.setForeground(Color.RED, txt);

		assertEquals(Color.RED, PromptSupport.getForeground(txt));
		assertEquals(Color.RED, txt.getClientProperty(PromptSupport.FOREGROUND));
	}

	@Test
	public void testGetBackround() throws Exception {
		assertEquals(txt.getBackground(), PromptSupport.getBackground(txt));
	}

	@Test
	public void testSetBackground() throws Exception {
		PromptSupport.setBackground(Color.RED, txt);

		assertEquals(Color.RED, PromptSupport.getBackground(txt));
		assertEquals(Color.RED, txt.getClientProperty(PromptSupport.BACKGROUND));

		promptSupportMustStayInstalled();
	}

	@Test
	public void testInstall() {
		PromptSupport.install(txt, false);
		promptSupportMustBeInstalled();

		PromptSupport.install(txt);
		promptSupportMustStayInstalled();
	}

	@Test
	public void testInstallAndStay() {
		PromptSupport.install(txt, true);
		promptSupportMustStayInstalled();
	}

	private void promptSupportMustBeInstalled() {
		assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
		txt.setUI(new BasicTextFieldUI());
		assertEquals(BasicTextFieldUI.class, txt.getUI().getClass());
	}

	private void promptSupportMustStayInstalled() {
		assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
		txt.setUI(new BasicTextFieldUI());
		assertEquals(PromptTextFieldUI.class, txt.getUI().getClass());
	}

	@Test
	public void testPromptFontStyle() throws Exception {
		assertNull(PromptSupport.getFontStyle(txt));
		PromptSupport.setFontStyle(Font.BOLD, txt);
		assertEquals(Font.BOLD, PromptSupport.getFontStyle(txt));
		assertEquals(Font.BOLD, txt.getClientProperty(PromptSupport.FONT_STYLE));
	}
}
