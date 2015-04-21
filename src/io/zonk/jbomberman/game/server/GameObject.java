package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.Position;

public abstract class GameObject {
	
	protected Position position;
	private int id;
	private NetworkFacade network;
	private GameObjectType type;
	
	public GameObject(Position position, int id, GameObjectType type) {
		this.position = position;
		this.id = id;
		this.type = type;
	}
	
	public abstract void tick();
	public abstract void update(Action action);
	public abstract void sendUpdates();//evtl. hier die NetworkFacade mitgeben
	public abstract boolean checkCollisionWith(GameObject object);

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameObjectType getType() {
		return type;
	}

	public void setType(GameObjectType type) {
		this.type = type;
	}
}
