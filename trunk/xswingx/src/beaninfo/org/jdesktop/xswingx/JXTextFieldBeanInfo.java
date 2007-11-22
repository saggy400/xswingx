package org.jdesktop.xswingx;


public class JXTextFieldBeanInfo extends JXPromptBeanInfo {
	public JXTextFieldBeanInfo() {
		this(JXTextField.class);
	}
	
	protected JXTextFieldBeanInfo(Class<? extends JXTextField> beanClass) {
		super(beanClass);
		
		setPreferred(true, "outerMargin");
	}
}
