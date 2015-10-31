package jbomberman.game.server;

import jbomberman.game.Action;
import jbomberman.game.ActionDispatcher;
import jbomberman.game.ActionQueue;
import jbomberman.game.ActionSerializer;
import jbomberman.game.ActionType;
import jbomberman.game.BombermanState;
import jbomberman.game.DirectionType;
import jbomberman.game.GameLoop;
import jbomberman.game.GameObjectType;
import jbomberman.game.Party;
import jbomberman.game.Player;
import jbomberman.game.PowerUpType;
import jbomberman.network.NetworkFacade;
import jbomberman.utils.Position;

import java.util.Observable;

public class ServerGame extends Observable implements GameLoop {
	private static final int TIMER_START = 180;

	private NetworkFacade network;
	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	private boolean initmap;
	private ActionDispatcher dispatcher;
	
	public int timer = 0;
	
	public ServerGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;

		manager = new GameObjectManager();

		queue = new ActionQueue();
		dispatcher = new ActionDispatcher(network, queue);
		startDispatcher();

	}

	public void startDispatcher() {
		dispatcher.start();
	}

	@Override
	public void loop() {
		if (!initmap) {
			initMap();

			new Thread(() -> {
				Object monitoredObject = new Object();
				synchronized (monitoredObject) {
					try {
						while(getInitMap()) {
							if(timer < TIMER_START) {
								monitoredObject.notifyAll();
								monitoredObject.wait(1000);
								timer++;
								Object[] prop = {TIMER_START - timer};
								Action timerAction = new Action(ActionType.UPDATE_TIMER, prop);
								network.sendMessage(ActionSerializer.serialize(timerAction));
							} else {
								monitoredObject.notifyAll();
								monitoredObject.wait(1000);
								Action timerAction = new Action(ActionType.UPDATE_TIMER, new Object[]{0});
								network.sendMessage(ActionSerializer.serialize(timerAction));
							}
							
						}
					} catch (InterruptedException e) {
					}
				}
			}).start();
		}

		// handle all available Actions
		while (!queue.isEmpty()) {
			Action action = queue.take();
			switch (action.getActionType()) {

			case PLAYER_INPUT:
				int id = (int) action.getProperty(0);
				if(party.get(id) != null ) {
				  if(manager.getById(id) != null) {
					((GBomberman)manager.getById(id)).update(action);
				  }
				}
				break;

			case CREATE_BOMB:
				manager.add(new GBomb((Position) action.getProperty(0),
						(int) action.getProperty(1), (int) action
								.getProperty(2), (int) action.getProperty(3)));
				network.sendMessage(ActionSerializer.serialize(action));
				break;

			case CREATE_POWERUP:
				manager.add(new GPowerUp((Position) action.getProperty(0),
						(int) action.getProperty(1), (PowerUpType) action
								.getProperty(2)));
				network.sendMessage(ActionSerializer.serialize(action));
				break;

			case CREATE_EXPLOSION:
				GExplosion explosion = new GExplosion(
						(Position) action.getProperty(0),
						(int) action.getProperty(1),
						(int) action.getProperty(2),
						(DirectionType)action.getProperty(3));
				boolean collision = false;
				for (GameObject object : manager.getByType(
						GameObjectType.SOLID_BLOCK,
						GameObjectType.DESTROYBALE_BLOCK)) {
					if (explosion.checkCollisionWith(object)) {
						if (object instanceof GDestroyableBlock) {
							explosion.setPower(0);
						}
						if (object instanceof GSolidBlock) {
							collision = true;
						}
						break;
					}
				}
				if (!collision) {
					manager.add(explosion);
					network.sendMessage(ActionSerializer.serialize(action));
				}
				break;

			case DESTROY:
				manager.remove((int) action.getProperty(0));
				network.sendMessage(ActionSerializer.serialize(action));
				break;
			default:
				break;

			}
		}

		// Tick all Objects
		for (GameObject object : manager.getAll()) {
			object.tick(queue, manager);
		}		
		
		int aliveCount = 0;
		for(GameObject object : manager.getByType(GameObjectType.BOMBERMAN)) {
			GBomberman b = (GBomberman) object;
			if(b.getState() != BombermanState.DEAD) {
				b.tick(manager, queue);
	
				b.sendUpdates(network);
				aliveCount++;
				if(timer == 180) {
					b.setFullPowerups();
				}
			}
		}
		
		if(aliveCount < 2) {
			Player winner = getWinner();
			if(winner.getId() != 0) {
				winner.addScore();
			}
			dispatcher.run = false;
			initmap = false;
			setChanged();
			notifyObservers("exitRound");
		}

	}

	private void initMap() {
		Map map = new StandardMap();
		map.init(manager, network, party);
		initmap = true;
	}

	public boolean getInitMap() {
		return initmap;
	}
	
	public void setInitMap(boolean b) {
		initmap = b;
	}
	
	public Player getWinner() {
		Player winner = new Player("Player0", 0);
		for(GameObject object : manager.getByType(GameObjectType.BOMBERMAN)) {
			GBomberman b = (GBomberman) object;
			if(b.getState() != BombermanState.DEAD) {
				winner = party.get(b.getId());
			}
		}
		return winner;
	}
}
