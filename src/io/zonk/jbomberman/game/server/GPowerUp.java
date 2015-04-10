package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.Position;

public abstract class GPowerUp extends GameObject {
	
	private PowerUpType type;

	public GPowerUp(Position position, int id, PowerUpType type) {
		super(position, id, GameObjectType.POWER_UP);
		this.type = type;
	}

}
