package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.server.GExplosion.Direction;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

public class GBomb extends GameObject {
	
	private static final int FUSE_TIME = 2000;
	private long placed;
	private int bombPower;

	public GBomb(Position position, int id, int bombPower, int owner) {
		super(position, id, GameObjectType.BOMB);
		placed = System.currentTimeMillis();
		this.bombPower = bombPower;
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		if(System.currentTimeMillis() - placed >= FUSE_TIME) {
			queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{position, IDGenerator.getId(), 0, Direction.NO_DIRECTION}));
			
			Position right = position.clonePos();
			right.incrementX(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{right, IDGenerator.getId(), bombPower, Direction.RIGHT}));
			
			Position left = position.clonePos();
			left.decrementX(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{left, IDGenerator.getId(), bombPower, Direction.LEFT}));
			
			Position down = position.clonePos();
			down.incrementY(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{down, IDGenerator.getId(), bombPower, Direction.DOWN}));
			
			Position up = position.clonePos();
			up.decrementY(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{up, IDGenerator.getId(), bombPower, Direction.UP}));
		}
	}
}
