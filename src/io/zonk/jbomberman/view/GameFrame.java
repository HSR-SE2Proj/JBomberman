package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;
import io.zonk.jbomberman.game.client.ClientGame;

import java.awt.HeadlessException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class GameFrame 
	extends JFrame implements Observer {
	private static final long serialVersionUID = 777305281293849313L;
	
	private GameCanvas canvas;
	private ClientController controller;
	private ClientGame game;
	
	public GameFrame(GameCanvas canvas, ClientController controller, ClientGame game) 
			throws HeadlessException {
		this.canvas = canvas;
		this.controller = controller;
		this.game = game;
		game.addObserver(this);
		add(canvas);
		pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		canvas.render();
	}
}
