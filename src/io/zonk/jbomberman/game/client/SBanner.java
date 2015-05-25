package io.zonk.jbomberman.game.client;

import java.awt.Dimension;

import io.zonk.jbomberman.utils.Position;

public class SBanner extends Sprite {
	public SBanner(Position position, int id) {
		super(position, id, "IMG_BANNER", new Dimension(320, 192), 8);
		switch(id){
			case 1:
				imgName = "IMG_BANNER_BMAN_BLACK";
				break;
			case 2:
				imgName = "IMG_BANNER_BMAN_BLUE";
				break;
			case 3:
				imgName = "IMG_BANNER_BMAN_RED";
				break;
			case 4:
				imgName = "IMG_BANNER_BMAN_WHITE";
				break;
			case 11:
				imgName = "IMG_BANNEROVERALL_BMAN_BLACK";
				break;
			case 12:
				imgName = "IMG_BANNEROVERALL_BMAN_BLUE";
				break;
			case 13:
				imgName = "IMG_BANNEROVERALL_BMAN_RED";
				break;
			case 14:
				imgName = "IMG_BANNEROVERALL_BMAN_WHITE";
				break;
			default:
				imgName = "IMG_BANNER_NO_WINNER";
				break;
		}	
	}
	
}
