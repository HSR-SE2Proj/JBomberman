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
		    imageMap.put("IMG_SOLIDBLOCK", ImageIO.read(new File(link + "SolidBlock.png")));
			imageMap.put("IMG_EXPLODABLEBLOCK", ImageIO.read(new File(link + "ExplodableBlock.png")));
			// Power Ups
			imageMap.put("IMG_BOMBPOWERUP", ImageIO.read(new File(link + "BombPowerup.png")));
			imageMap.put("IMG_FLAMEPOWERUP", ImageIO.read(new File(link + "FlamePowerup.png")));
		    imageMap.put("IMG_SPEEDPOWERUP", ImageIO.read(new File(link + "SpeedPowerup.png")));
			//Bomb and Explosion
			imageMap.put("IMG_BOMB", ImageIO.read(	new File(link + "Bomb.png")));
			imageMap.put("IMG_EXPLOSION", ImageIO.read(	new File(link + "Explosion.png")));
		    //JBombermans
			imageMap.put("IMG_BMANBLACK", ImageIO.read(	new File(link + "BmanBlack.png")));
			imageMap.put("IMG_BMANBLUE", ImageIO.read(	new File(link + "BmanBlue.png")));
			imageMap.put("IMG_BMANWHITE", ImageIO.read(	new File(link + "BmanWhite.png")));
			imageMap.put("IMG_BMANRED", ImageIO.read(	new File(link + "BmanRed.png")));
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
