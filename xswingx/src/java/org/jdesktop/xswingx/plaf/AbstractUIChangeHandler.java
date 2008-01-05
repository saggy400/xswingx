package org.jdesktop.xswingx.plaf;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public abstract class AbstractUIChangeHandler implements PropertyChangeListener {
	//prevent double installation.
	private final List<JComponent> installed = new ArrayList<JComponent>();
	
	public void install(JComponent c){
		if(isInstalled(c)){
			return;
		}
		
		c.addPropertyChangeListener("UI", this);
	}
	
	public boolean isInstalled(JComponent c) {
		return installed.contains(c);
	}

	public void uninstall(JComponent c){
		c.removePropertyChangeListener("UI", this);
	}
}