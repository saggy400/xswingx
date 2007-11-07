package org.jdesktop.xswingx.plaf;

import javax.swing.JTextField;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;

/**
 * {@link PromptTextUI} implementation for rendering prompts on {@link JTextField}s
 * and uses a {@link JTextField} as a prompt component.
 * 
 * @author Peter Weishapl <petw@gmx.net>
 * 
 */
public class PromptTextFieldUI extends PromptTextUI {
	/**
	 * Shared prompt renderer.
	 */
	private final static LabelField txt = new LabelField();
	
    /**
     * Creates a new {@link PromptTextFieldUI}.
     * 
     * @param delegate
     */
    public PromptTextFieldUI(TextUI delegate) {
        super(delegate);
    }

    /**
     * Overrides {@link #getPromptComponent(JTextComponent)} to additionally update
     * {@link JTextField} specific properties.
     */
    public JTextComponent getPromptComponent(JTextComponent txt) {
    	LabelField lbl = (LabelField) super.getPromptComponent(txt);
        JTextField txtField = (JTextField) txt;

        lbl.setHorizontalAlignment(txtField.getHorizontalAlignment());
        lbl.setColumns(txtField.getColumns());
        
        //make Aqua in Leopard paint focused border.
        lbl.hasFocus = txtField.hasFocus();

        return lbl;
    }

    /**
     * Returns a shared {@link JTextField}.
     */
    protected JTextComponent createPromptComponent() {
        return txt;
    }
    
    private static final class LabelField extends JTextField{
    	boolean hasFocus;
    	
    	@Override
    	public boolean hasFocus() {
    		return hasFocus;
    	}
    }
}
