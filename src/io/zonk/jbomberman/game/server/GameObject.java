package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public abstract class GameObject {
	
	protected Position position;
	protected int id;
	private GameObjectType type;
	
	public GameObject(Position position, int id, GameObjectType type) {
		this.position = position;
		this.id = id;
		this.type = type;
	}
	/**
	 * Aktualisiert Zustände und Variablen. 
	 * Hauptsächlich Zeitbedingte Werte (Wann kann ich die nächste Bombe legen)
	 * @param queue
	 * @param manager
	 */
	public abstract void tick(ActionQueue queue, GameObjectManager manager);
	/**
	 * Überprüft, ob das Object mit einem anderen kollidiert und führt dann 
	 * die jeweiligen
	 * Korrekturen (z.B. beim Movement) oder Aktionen durch. 
	 * @param object GameObject
	 * @return gibt Boolean zurück ob Collision True/False
	 */
	public boolean checkCollisionWith(GameObject object) {
		return	position.getX() < object.getPosition().getX() + 64 &&
				position.getX() + 64 > object.getPosition().getX() &&
				position.getY() < object.getPosition().getY() + 64 &&
				64 + position.getY() > object.getPosition().getY();
	}

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
