package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public class GBomb extends GameObject {
	
	private static final int FUSE_TIME = 2000;
	private long placed;

	public GBomb(Position position, int id) {
		super(position, id, GameObjectType.BOMB);
		placed = System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if(System.currentTimeMillis() - placed >= FUSE_TIME) {
			//Bombe zünden
		}
		
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdates() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkCollisionWith(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}
