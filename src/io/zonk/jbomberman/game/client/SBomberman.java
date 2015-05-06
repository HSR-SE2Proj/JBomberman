package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.BombermanState;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;
import java.awt.Graphics;

public class SBomberman extends Sprite {
	
	private BombermanState state = BombermanState.IDLE_DOWN;

	public SBomberman(Position position, int id) {
		super(position, id, "IMG_BOMBERMAN", new Dimension(64, 128), 6);
		frameStep = 2;
		if(id == 1)
			imgName = "IMG_BMAN_BLACK";
		if(id == 2)
			imgName = "IMG_BMAN_BLUE";
		if(id == 3)
			imgName = "IMG_BMAN_RED";
		if(id == 4)
			imgName = "IMG_BMAN_WHITE";
	}
	
	public void setState(BombermanState state) {
		this.state = state;
		System.out.println(state);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageManager.getInstance().get(imgName).getSubimage(frameNr*size.width, animState*size.height, size.width, size.height), position.getX(), position.getY()-64, null);
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		super.tick();
		
		switch(state) {
		case DOWN:
			animState = 1;
			frameCnt = 8;
			break;
		case IDLE_DOWN:
			animState = 1;
			frameCnt = 1;
			break;
		case IDLE_LEFT:
			animState = 3;
			frameCnt = 1;
			break;
		case IDLE_RIGHT:
			animState = 2;
			frameCnt = 1;
			break;
		case IDLE_UP:
			animState = 0;
			frameCnt = 1;
			break;
		case LEFT:
			animState = 3;
			frameCnt = 8;
			break;
		case RIGHT:
			animState = 2;
			frameCnt = 8;
			break;
		case UP:
			animState = 0;
			frameCnt = 8;
			break;
		default:
			animState = 1;
			frameCnt = 1;
			break;
		
		}
		
	}

}
