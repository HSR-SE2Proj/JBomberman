package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public abstract class GPowerUp extends GameObject {
	
	public GPowerUp(Position position, int id) {
		super(position, id, GameObjectType.POWER_UP);
		
	}

}
