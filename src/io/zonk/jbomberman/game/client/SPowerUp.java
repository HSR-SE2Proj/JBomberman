package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

public class SPowerUp extends Sprite {

	public SPowerUp(Position position, int id, PowerUpType type) {
		super(position, id, "DEFAULT", new Dimension(32, 32), 6);
		position.incrementX(16);
		position.incrementY(16);
		switch(type) {
		case BOMB:
			imgName = "IMG_BOMBPOWERUP";
			break;
		case POWER:
			imgName = "IMG_FLAMEPOWERUP";
			break;
		case SPEED:
			imgName = "IMG_SPEEDPOWERUP";
			break;
		default:
			break;
		}
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
