package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;
import java.awt.Graphics;

public class SBomberman extends Sprite {

	public SBomberman(Position position, int id) {
		super(position, id, "IMG_BOMBERMAN", new Dimension(64, 128), 5);
		if(id == 1)
			imgName = "IMG_BMAN_BLACK";
		if(id == 2)
			imgName = "IMG_BMAN_BLUE";
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageManager.getInstance().get(imgName).getSubimage(0, 0, size.width, size.height), position.getX(), position.getY()-64, null);
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
