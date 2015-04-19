package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

public abstract class SPowerUp extends Sprite {

	public SPowerUp(Position position, int id, String imgName, 
			Dimension size) {
		super(position, id, imgName, size, 6);
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
