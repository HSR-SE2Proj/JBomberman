package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public class GSolidBlock extends GameObject {

	public GSolidBlock(Position position, int id) {
		super(position, id, GameObjectType.SOLID_BLOCK);
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
}
