package jbomberman.game.server;

import jbomberman.game.ActionQueue;
import jbomberman.game.GameObjectType;
import jbomberman.utils.Position;

public class GSolidBlock extends GameObject {

	public GSolidBlock(Position position, int id) {
		super(position, id, GameObjectType.SOLID_BLOCK);
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
}
