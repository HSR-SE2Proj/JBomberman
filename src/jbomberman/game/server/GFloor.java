package jbomberman.game.server;

import jbomberman.game.ActionQueue;
import jbomberman.game.GameObjectType;
import jbomberman.utils.Position;

public class GFloor extends GameObject {

	public GFloor(Position position, int id) {
		super(position, id, GameObjectType.FLOOR);
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
}
