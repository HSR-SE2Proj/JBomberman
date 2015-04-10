package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

//evtl. nochmals von SPowerUp extenden für die einzelnen konkreten PowerUps
public abstract class SPowerUp extends Sprite {

	private PowerUpType type;
	
	public SPowerUp(Position position, int id, String imgName, 
			Dimension size, PowerUpType type) {
		super(position, id, imgName, size);
		this.type = type;
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
