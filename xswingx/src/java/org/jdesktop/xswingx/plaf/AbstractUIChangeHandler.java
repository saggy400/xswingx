package org.jdesktop.xswingx.plaf;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

public abstract class AbstractUIChangeHandler implements PropertyChangeListener {
	//prevent double installation
	private boolean installed;
	
	public void install(JComponent c){
		if(installed){
			return;
		}
		
		c.addPropertyChangeListener("UI", this);
		installed = true;
	}
	
	public void uninstall(JComponent c){
		c.removePropertyChangeListener("UI", this);
		installed = false;
	}
}