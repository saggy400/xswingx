package org.jdesktop.xswingx.plaf;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

public abstract class AbstractUIChangeHandler implements PropertyChangeListener {
	public void install(JComponent c){System.out.println("add me");
		c.addPropertyChangeListener("UI", this);
	}
	
	public void uninstall(JComponent c){
		System.out.println("remove me");
		c.removePropertyChangeListener("UI", this);
	}
}