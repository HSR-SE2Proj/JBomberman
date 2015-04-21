package io.zonk.jbomberman.view;

import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 2466275114783318368L;
	
	private ClientGame game;
	private Dimension size;
	private Keyboard keyboard;
	
	private BufferedImage image;
	public int[] pixels;
	
	public GameCanvas(ClientGame game, Keyboard keyboard) {
		this.game = game;
		this.keyboard = keyboard;
		
		size = new Dimension(832, 832);
		setPreferredSize(size);
		
		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.addKeyListener(keyboard);
	}
	
	//nur mit g2d???
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		//clear();
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		
		game.drawAll(null); //pixels
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
