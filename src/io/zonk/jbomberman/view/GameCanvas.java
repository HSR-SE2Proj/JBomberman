package io.zonk.jbomberman.view;

import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;


public class GameCanvas extends Canvas implements Observer {
	private static final long serialVersionUID = 2466275114783318368L;
	
	private ClientGame game;
	private Dimension size;
	
	private BufferedImage image;
	public int[] pixels;
	
	private JFrame frame;
	private ScorePanel sp;
	
	public GameCanvas(ClientGame game, Keyboard keyboard, Party party, int round) {
		this.game = game;
		game.addObserver(this);
		size = new Dimension(832, 832);
		setPreferredSize(size);
		
		sp = new ScorePanel(party, round);
		
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.getContentPane().add(sp, BorderLayout.NORTH);
		
		
		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.addKeyListener(keyboard);
		
		setPreferredSize(new Dimension(640, 640));
	
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					frame.setVisible(true);
					frame.pack();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kümmert sich um die BufferStrategy und delegiert das 
	 * zeichnen der Sprites direkt an die Sprites selbst.
	 * 
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			try {
			createBufferStrategy(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		game.drawAll(image.getGraphics());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(((String)arg).equals("updateTimer")) {
			sp.updateTimer(game.getTimer());
		} else {
			render();
		}
	}
	
	public void dispose() {
		frame.dispose();
	}
}
