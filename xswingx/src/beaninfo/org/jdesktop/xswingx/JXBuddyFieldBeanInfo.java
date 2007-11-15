package org.jdesktop.xswingx;

public class JXBuddyFieldBeanInfo extends JXPromptFieldBeanInfo {
	public JXBuddyFieldBeanInfo() {
		this(JXBuddyField.class);
	}
	
	protected JXBuddyFieldBeanInfo(Class<? extends JXBuddyField> beanClass) {
		super(beanClass);
	}

	protected void initialize() {
		super.initialize();
		
		setPreferred(true, "outerMargin");
	}
}
