package io.zonk.jbomberman.view;

import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;
import io.zonk.jbomberman.utils.ImageManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;


public class GameCanvas extends Canvas implements Observer {
	private static final long serialVersionUID = 2466275114783318368L;
	
	private ClientGame game;
	private Dimension size;
	private Keyboard keyboard;
	
	private BufferedImage image;
	public int[] pixels;
	
	private JFrame frame;
	
	public GameCanvas(ClientGame game, Keyboard keyboard) {
		this.game = game;
		this.keyboard = keyboard;
		game.addObserver(this);
		size = new Dimension(832, 832);
		setPreferredSize(size);
		
		frame = new JFrame();
		frame.add(this);
		frame.setVisible(true);
		frame.pack();
		
		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
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
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		
		//game.drawAll(pixels);
	
		
		
		Graphics g = bs.getDrawGraphics();
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		game.drawAll(g);
		
		
		g.dispose();
		bs.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		render();
		
	}
}
