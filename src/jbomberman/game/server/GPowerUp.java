package jbomberman.game.server;

import jbomberman.game.ActionQueue;
import jbomberman.game.GameObjectType;
import jbomberman.game.PowerUpType;
import jbomberman.utils.Position;

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
