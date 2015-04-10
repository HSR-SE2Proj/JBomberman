package io.zonk.jbomberman.utils;

import java.awt.image.BufferedImage;

public class ImageManager {
	
	private static ImageManager im = null;
	
	public static ImageManager getInstance() {
		if(im == null)
			im = new ImageManager();
		return im;
	}
	
	private ImageManager() {
		
	}
	
	public BufferedImage get(String imgName) {
		return null;
	}

}
