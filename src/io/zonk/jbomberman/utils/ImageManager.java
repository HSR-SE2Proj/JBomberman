package io.zonk.jbomberman.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageManager {
	
	private static ImageManager im = null;
	private Map<String, BufferedImage> imageMap = new HashMap<>();
	private String link = "/io/zonk/jbomberman/resources/";
	public static ImageManager getInstance() {
		if(im == null)
			im = new ImageManager();
		return im;
	}
	
	private ImageManager() {
		try {
			//Blocks and Floor
			imageMap.put("IMG_FLOOR", ImageIO.read(getClass().getResource(link + "BackgroundTile.png")));
		    imageMap.put("IMG_SOLIDBLOCK", ImageIO.read(getClass().getResource(link + "SolidBlock.png")));
			imageMap.put("IMG_EXPLODABLEBLOCK", ImageIO.read(getClass().getResource(link + "SolidBlock.png")));
			// Power Ups
			imageMap.put("IMG_BOMBPOWERUP", ImageIO.read(getClass().getResource(link + "BombPowerup.png")));
			imageMap.put("IMG_FLAMEPOWERUP", ImageIO.read(getClass().getResource(link + "FlamePowerup.png")));
		    imageMap.put("IMG_SPEEDPOWERUP", ImageIO.read(getClass().getResource(link + "SpeedPowerup.png")));
			//Bomb and Explosion
			imageMap.put("IMG_BOMB", ImageIO.read(getClass().getResource(link + "Bomb.png")));
			imageMap.put("IMG_EXPLOSION", ImageIO.read(getClass().getResource(link + "Explosion.png")));
		    //JBombermans
			imageMap.put("IMG_BMAN_BLACK", ImageIO.read(getClass().getResource(link + "BmanBlack.png")));
			imageMap.put("IMG_BMAN_BLUE", ImageIO.read(getClass().getResource(link + "BmanBlue.png")));
			imageMap.put("IMG_BMAN_WHITE", ImageIO.read(getClass().getResource(link + "BmanWhite.png")));
			imageMap.put("IMG_BMAN_RED", ImageIO.read(getClass().getResource(link + "BmanRed.png")));
			//JBombermans Profilepics
			imageMap.put("IMG_BMAN_PROFRED", ImageIO.read(getClass().getResource(link + "BmanProfileRed.png")));
			imageMap.put("IMG_BMAN_PROFBLUE", ImageIO.read(getClass().getResource(link + "BmanProfileBlue.png")));
			imageMap.put("IMG_BMAN_PROFWHITE", ImageIO.read(getClass().getResource(link + "BmanProfileWhite.png")));
			imageMap.put("IMG_BMAN_PROFBLACK", ImageIO.read(getClass().getResource(link + "BmanProfileBlack.png")));
		} catch(IOException e) {
			System.out.println("Error: Could not load images");
			e.printStackTrace();
			assert true;
		}
	}
	
	public BufferedImage get(String Name) {
		return imageMap.get(Name);
	}

}
