package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.BombermanState;
import io.zonk.jbomberman.game.DirectionType;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;
import io.zonk.jbomberman.utils.RandomUtil;

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
						while(getInitMap() && timer < TIMER_START) {
							monitoredObject.notifyAll();
							monitoredObject.wait(1000);
							timer++;
							Object[] prop = {TIMER_START - timer};
							Action timerAction = new Action(ActionType.UPDATE_TIMER, prop);
							network.sendMessage(ActionSerializer.serialize(timerAction));
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
					//party.get(id).getBomberman().update(action);
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

//		int aliveCount = 0;
//		for (Player player : party.getPlayers().values()) {
//			if (player == null)
//				continue;
//			GBomberman b = player.getBomberman();
//			
//			if(b.getState() != BombermanState.DEAD) {
//				b.tick(manager, queue);
//
//				b.sendUpdates(network);
//				aliveCount++;
//				if(timer == 180) {
//					player.getBomberman().setFullPowerups();
//				}
//			}
//			
//		}
		
		
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
			//Player winner = party.getWinner();
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
