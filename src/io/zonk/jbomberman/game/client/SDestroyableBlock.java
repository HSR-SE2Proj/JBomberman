package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.Position;
import io.zonk.jbomberman.utils.ImageManager;
import java.awt.Dimension;

public class SDestroyableBlock extends Sprite {
	
	public SDestroyableBlock(Position position, int id, Dimension size) {
		super(position, id, "IMG_DESTROYABLEBLOCK", size, 3);
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
