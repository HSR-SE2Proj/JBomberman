package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.DirectionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

public class GBomb extends GameObject {
	
	private int fuseTime = 30*3;
	private int bombPower;

	public GBomb(Position position, int id, int bombPower, int owner) {
		super(position, id, GameObjectType.BOMB);
		this.bombPower = bombPower;
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		
		if(--fuseTime < 0) {
			explode(queue, manager);
		}
		
		for(GameObject object : manager.getByType(GameObjectType.EXPLOSION)) {
			if(this.checkCollisionWith(object)) {
				explode(queue, manager);
			}
		}
	}
	
	private void explode(ActionQueue queue, GameObjectManager manager) {
		queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
		queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{position, IDGenerator.getId(), 0, DirectionType.NO_DIRECTION}));
		
		Position right = position.clonePos();
		right.incrementX(64);
		queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{right, IDGenerator.getId(), bombPower, DirectionType.RIGHT}));
		
		Position left = position.clonePos();
		left.decrementX(64);
		queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{left, IDGenerator.getId(), bombPower, DirectionType.LEFT}));
		
		Position down = position.clonePos();
		down.incrementY(64);
		queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{down, IDGenerator.getId(), bombPower, DirectionType.DOWN}));
		
		Position up = position.clonePos();
		up.decrementY(64);
		queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{up, IDGenerator.getId(), bombPower, DirectionType.UP}));
	}
}
