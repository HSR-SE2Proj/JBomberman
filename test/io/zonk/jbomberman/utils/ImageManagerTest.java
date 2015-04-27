package io.zonk.jbomberman.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class ImageManagerTest {
	
	private ImageManager im;
	@Before
	public void init() {
		im = ImageManager.getInstance();
	}
	// Test 2 Images if equal
	@Test
	public void testTwoImages(){
		BufferedImage img1 = im.get("IMG_SOLIDBLOCK");
		BufferedImage img2 = im.get("IMG_SOLIDBLOCK");
		assertTrue(img1.equals(img2));
	}
	//Test 2 Images if not equal
	@Test
	public void testTwoImagesFalse() {
		BufferedImage img1 = im.get("IMG_DESTROYABLEBLOCK");
		BufferedImage img2 = im.get("IMG_SOLIDBLOCK");
		assertFalse(img1.equals(img2));
	}
	
	// SolidBlock 64px X 64px
	@Test
	public void testGetSingleImage() {
		assertEquals(64,im.get("IMG_SOLIDBLOCK").getHeight());
		assertEquals(64,im.get("IMG_SOLIDBLOCK").getWidth());
	}
}
