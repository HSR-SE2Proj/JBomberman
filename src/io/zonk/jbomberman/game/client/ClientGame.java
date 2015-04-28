package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientGame extends Observable 
	implements GameLoop {
	
	private NetworkFacade network;
	private SpriteManager manager;
	private ActionQueue queue;
	private Party party;
	
	private List<Sprite> background = new ArrayList<>();
	
	public ClientGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
		
		manager = new SpriteManager();
		
		queue = new ActionQueue();
		ActionDispatcher dispatcher = new ActionDispatcher(network, queue);
		dispatcher.start();
		
		//Timer timer = new Timer(1000/30, this);
		//timer.start();
		
		for(int x = 0; x < 13; ++x)
			for(int y = 0; y < 13; ++y) {
				background.add(new SFloor(new Position(x*64, y*64), 0));
			}
	}

	@Override
	public void loop() {
		
		//while(true) { //game running?
			//System.out.println("loop");
			
			//Handle all available Actions
			while(!queue.isEmpty()) {
				Action action = queue.take();
				switch(action.getActionType()) {
				case MOVEMENT:
					manager.getById((int)action.getProperty(0)).setPosition((Position)action.getProperty(1));
					break;
				case CREATE_SOLIDBLOCK:
					manager.add(new SSolidBlock((Position) action.getProperty(0), (int)action.getProperty(1)));
					break;
				case CREATE_DESTROYABLEBLOCK:
					manager.add(new SDestroyableBlock((Position) action.getProperty(0), (int)action.getProperty(1)));
					break;
				case CREATE_BOMBERMAN:
					manager.add(new SBomberman((Position)action.getProperty(0), (int)action.getProperty(1)));
					break;
				case CREATE_BOMB:
					manager.add(new SBomb((Position)action.getProperty(0), (int)action.getProperty(1)));
					break;
				case DESTROY_BOMB:
					manager.remove((int)action.getProperty(0));
				default:
					break;
				}
			}
			
			//Tick all Sprites
			for(Sprite sprite : manager.getAll()) {
				sprite.tick();
			}
			
			setChanged();
			notifyObservers();
			
		//}
	}
	
	public void drawAll(int[] pixels) {
		
		for(Sprite sprite : background)
			sprite.draw(pixels);
		
		for(Sprite sprite : manager.getAll()) {
			sprite.draw(pixels);
		}
	}
	
	public void drawAll(Graphics g) {
		
		for(Sprite sprite : background)
			sprite.draw(g);
		
		for(Sprite sprite : manager.getAll()) {
			sprite.draw(g);
		}
	}
	
	
}






