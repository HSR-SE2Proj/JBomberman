package io.zonk.jbomberman.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class ImageManagerTest {
	
	private ImageManager im;
	private ArrayList<String> images = new ArrayList<String>(Arrays.asList("IMG_FLOOR",
			"SolidBlock","ExplodableBlock","BombPowerup","FlamePowerup","SpeedPowerup",
			"Bomb","Explosion","BmanBlack","BmanW","BmanBlue","BmanR"));
	
	@Before
	public void init() {
		im = ImageManager.getInstance();
	}
	
	//@Test
	//public void testAllImages(){
	//	for(String name : images){
	//		assert (im.get(name)) != null;
	//	}
	//}
	
	//@Test
	//public void testGetSingleImage() {
		//assertEquals(64,im.get("IMG_SOLIDBLOCK").getHeight());
		//assertEquals(64,im.get("IMG_SOLIDBLOCK").getWidth());
	//}
}
