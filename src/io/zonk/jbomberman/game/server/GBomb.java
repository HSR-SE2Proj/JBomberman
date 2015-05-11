package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.server.GExplosion.Direction;
import io.zonk.jbomberman.network.NetworkFacade;
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
			queue.put(new Action(ActionType.DESTROY_BOMB, new Object[]{id}));
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{position, IDGenerator.getId(), 0, Direction.NO_DIRECTION}));
			
			//add bomberman a bomb
			//manager.getById(owner)G
			
			Position right = position.clone();
			right.incrementX(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{right, IDGenerator.getId(), bombPower, Direction.RIGHT}));
			
			Position left = position.clone();
			left.decrementX(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{left, IDGenerator.getId(), bombPower, Direction.LEFT}));
			
			Position down = position.clone();
			down.incrementY(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{down, IDGenerator.getId(), bombPower, Direction.DOWN}));
			
			Position up = position.clone();
			up.decrementY(64);
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{up, IDGenerator.getId(), bombPower, Direction.UP}));
			
			/*
			//right
			for(int i = bombPower; i > 0; i--) {
				right.incrementX(64);
				queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{right, IDGenerator.getId(), 0}));
			}
			
			//left
			for(int i = bombPower; i > 0; i--) {
				left.decrementX(64);
				queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{left, IDGenerator.getId(), 0}));
			}
			
			//down
			for(int i = bombPower; i > 0; i--) {
				down.incrementY(64);
				queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{down, IDGenerator.getId(), 0}));
			}
			
			//up
			for(int i = bombPower; i > 0; i--) {
				up.decrementY(64);
				queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{up, IDGenerator.getId(), 0}));
			}
			*/
		}

	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdates(NetworkFacade network) {
		// TODO Auto-generated method stub
		
	}
}
