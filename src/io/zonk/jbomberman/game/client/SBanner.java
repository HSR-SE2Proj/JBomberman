package io.zonk.jbomberman.game.client;

import java.awt.Dimension;

import io.zonk.jbomberman.utils.Position;

public class SBanner extends Sprite {
	public SBanner(Position position, int id) {
		super(position, id, "IMG_BANNER_BMAN_BLACK", new Dimension(320, 192), 8);
		if(id == 0)
			imgName = "IMG_BANNER_NO_WINNER";
		if(id == 1)
			imgName = "IMG_BANNER_BMAN_BLACK";
		if(id == 11)
			imgName = "IMG_BANNEROVERALL_BMAN_BLACK";
		if(id == 2)
			imgName = "IMG_BANNER_BMAN_BLUE";
		if(id == 12)
			imgName = "IMG_BANNEROVERALL_BMAN_BLUE";
		if(id == 3)
			imgName = "IMG_BANNER_BMAN_RED";
		if(id == 13)
			imgName = "IMG_BANNEROVERALL_BMAN_RED";
		if(id == 4)
			imgName = "IMG_BANNER_BMAN_WHITE";
		if(id == 14)
			imgName = "IMG_BANNEROVERALL_BMAN_WHITE";
	}
	
}
