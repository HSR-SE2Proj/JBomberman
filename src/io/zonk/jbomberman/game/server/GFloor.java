package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public class GFloor extends GameObject {

	public GFloor(Position position, int id) {
		super(position, id, GameObjectType.FLOOR);
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
}
