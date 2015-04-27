package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

public class SSolidBlock extends Sprite {
	
	public SSolidBlock(Position position, int id) {
		super(position, id, "IMG_SOLIDBLOCK", new Dimension(64, 64), 2);
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
