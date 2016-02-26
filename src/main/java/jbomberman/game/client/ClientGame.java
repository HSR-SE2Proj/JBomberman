package jbomberman.game.client;

import jbomberman.game.Action;
import jbomberman.game.ActionDispatcher;
import jbomberman.game.ActionQueue;
import jbomberman.game.BombermanState;
import jbomberman.game.GameLoop;
import jbomberman.game.Party;
import jbomberman.game.PowerUpType;
import jbomberman.network.NetworkFacade;
import jbomberman.utils.Position;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientGame extends Observable 
	implements GameLoop {
	private static final int TIMER_START = 180;
	
	private SpriteManager manager;
	private ActionQueue queue;
	private ActionDispatcher dispatcher;
	
	private List<Sprite> background = new ArrayList<>();
	private int timer = TIMER_START;
	
	public ClientGame(NetworkFacade network, Party party) {		
		manager = new SpriteManager();
		
		queue = new ActionQueue();
		dispatcher = new ActionDispatcher(network, queue);
		startDispatcher();
		
		for(int x = 0; x < 13; ++x)
			for(int y = 0; y < 13; ++y) {
				background.add(new SFloor(new Position(x*64, y*64), 0));
			}
	}
	
	public void startDispatcher() {
		dispatcher.start();
	}

	@Override
	public void loop() {
			String notifyMsg = "";
			//Handle all available Actions
			while(!queue.isEmpty()) {
				Action action = queue.take();
				switch(action.getActionType()) {
				case MOVEMENT:
					SBomberman bman = (SBomberman)manager.getById((int)action.getProperty(0));
					bman.setPosition((Position)action.getProperty(1));
					bman.setState((BombermanState)action.getProperty(2));
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
				case CREATE_EXPLOSION:
					manager.add(new SExplosion((Position)action.getProperty(0), (int)action.getProperty(1)));
					break;
				case CREATE_POWERUP:
					manager.add(new SPowerUp((Position)action.getProperty(0), (int)action.getProperty(1), (PowerUpType)action.getProperty(2)));
					break;
				case CREATE_BANNER:
					manager.add(new SBanner((Position) action.getProperty(0), (int)action.getProperty(1)));
					break;
				case DESTROY:
					manager.remove((int)action.getProperty(0));
					break;
				case UPDATE_TIMER:
					timer = ((Integer)action.getProperty(0));
					notifyMsg = "updateTimer";
					break;
				case LOBBY_COMMUNICATION:
					String s = ((String)action.getProperty(0));
					if(s != null && s.equals("finishGame")) {
						dispatcher.run = false;
						notifyMsg = "gameFinished";
					}
					if(s != null && s.equals("finishRound")) {
						dispatcher.run = false;
						notifyMsg = "roundFinished";
					}
					break;
				default:
					break;
				}
			}
			
			//Tick all Sprites
			for(Sprite sprite : manager.getAll()) {
				sprite.tick();
			}
			
			setChanged();
			notifyObservers(notifyMsg);

	}
	
	public void drawAll(Graphics g) {
		
		for(Sprite sprite : background)
			sprite.draw(g);
		
		for(Sprite sprite : manager.getAll()) {
			sprite.draw(g);
		}
	}
	
	public int getTimer() {
		return timer;
	}
}