package jbomberman.game;

import java.io.Serializable;

public class Action implements Serializable,Cloneable {
	private static final long serialVersionUID = -7165055744828125991L;
	
	private ActionType type;
	private Object[] properties;
	
	public Action(ActionType type, Object[] properties) {
		this.type = type;
		this.properties = properties.clone();
	}
	
	public ActionType getActionType() {
		return type;
	}
	
	public Object getProperty(int i) {
		return properties[i];
	}

}
