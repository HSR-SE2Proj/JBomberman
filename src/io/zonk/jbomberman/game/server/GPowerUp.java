package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.Position;

public class GPowerUp extends GameObject {
	private PowerUpType type;
	
	public GPowerUp(Position position, int id, PowerUpType type) {
		super(position, id, GameObjectType.POWER_UP);
		this.type = type;
	}
	
	public PowerUpType getPowerUpType() {
		return type;
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
}
