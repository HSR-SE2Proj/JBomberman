package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.utils.Position;
import java.awt.Dimension;

public class SBomb extends Sprite {

	public SBomb(Position position, int id) {
		super(position, id, "IMG_BOMB", new Dimension(64, 64), 4);
		frameCnt = 3;	
	}

	@Override
	public void tick() {
		super.tick();
	}

}
