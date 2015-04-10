package io.zonk.jbomberman.view;

import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;

import java.awt.Canvas;
import java.awt.Dimension;

public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 2466275114783318368L;
	
	private ClientGame game;
	private int[] pixels;
	private Dimension size;
	private Keyboard keyboard;
	
	public GameCanvas(ClientGame game, int[] pixels, Dimension size, Keyboard keyboard) {
		this.game = game;
		this.pixels = pixels;
		this.size = size;
		this.keyboard = keyboard;
		this.addKeyListener(keyboard);
	}
	
	public void render() {
		
	}
}
