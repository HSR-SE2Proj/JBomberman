package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

public class SFloor extends Sprite {

	public SFloor(Position position, int id) {
		super(position, id, "IMG_FLOOR", new Dimension(64, 64), 1);
		//imgName = "IMG_FLOOR";
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
