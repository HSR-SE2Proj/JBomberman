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
	private String link = "src/io/zonk/jbomberman/resources/";
	public static ImageManager getInstance() {
		if(im == null)
			im = new ImageManager();
		return im;
	}
	
	private ImageManager() {
		try {
			//Blocks and Floor
			imageMap.put("BackgroundTile", ImageIO.read(new File(link + "BackgroundTile.png")));
		    imageMap.put("SolidBlock", ImageIO.read(new File(link + "SolidBlock.png")));
			imageMap.put("ExplodableBlock", ImageIO.read(new File(link + "ExplodableBlock.png")));
			// Power Ups
			imageMap.put("BombPowerup", ImageIO.read(new File(link + "BombPowerup.png")));
			imageMap.put("FlamePowerup", ImageIO.read(new File(link + "FlamePowerup.png")));
		    imageMap.put("SpeedPowerup", ImageIO.read(new File(link + "SpeedPowerup.png")));
			//Bomb and Explosion
			imageMap.put("Bomb", ImageIO.read(	new File(link + "Bomb.png")));
			imageMap.put("Explosion", ImageIO.read(	new File(link + "Explosion.png")));
		    //JBombermans
			imageMap.put("BmanBlack", ImageIO.read(	new File(link + "BmanBlack.png")));
			imageMap.put("BmanW", ImageIO.read(	new File(link + "BmanBlue.png")));
			imageMap.put("BmanBlue", ImageIO.read(	new File(link + "BmanBlue.png")));
			imageMap.put("BmanR", ImageIO.read(	new File(link + "BmanR.png")));
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
