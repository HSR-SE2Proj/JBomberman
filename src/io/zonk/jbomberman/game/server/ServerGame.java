package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.BombermanState;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

import java.util.Observable;
import java.util.Random;

public class ServerGame extends Observable implements GameLoop {
	private static final int TIMER_START = 180;

	private NetworkFacade network;
	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	private boolean initmap;
	private ActionDispatcher dispatcher;
	private long initTime = 0;
	public ServerGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;

		manager = new GameObjectManager();

		queue = new ActionQueue();
		dispatcher = new ActionDispatcher(network, queue);
		startDispatcher();
		
		initTime = System.currentTimeMillis();
	}

	public void startDispatcher() {
		dispatcher.start();
	}

	@Override
	public void loop() {
		int timer = (int) ((System.currentTimeMillis() - initTime) / 1000);
		Object[] prop = {TIMER_START - timer};
		if (timer <= 180) {
		Action timerAction = new Action(ActionType.UPDATE_TIMER, prop);
		network.sendMessage(ActionSerializer.serialize(timerAction));
		}
		
		if (!initmap) {
			initMap();
		}
		

		// handle all available Actions
		while (!queue.isEmpty()) {
			Action action = queue.take();
			switch (action.getActionType()) {

			case PLAYER_INPUT:
				int id = (int) action.getProperty(0);
				if(party.get(id) != null ) {
					party.get(id).getBomberman().update(action);
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
						(GExplosion.Direction) action.getProperty(3));
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
		for (Player player : party.getPlayers().values()) {
			if (player == null)
				continue;
			GBomberman b = player.getBomberman();
			
			if(b.getState() != BombermanState.DEAD) {
				b.tick(manager, queue);

				b.sendUpdates(network);
				aliveCount++;
				if(timer == 180) {
					player.getBomberman().setFullPowerups();
				}
			}
			
		}
		
		if(aliveCount < 2) {
			Player winner = party.getWinner();
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
		Random rnd = new Random();
		Map map = new StandardMap();
		for (int y = 0; y < 13; ++y)
			for (int x = 0; x < 13; ++x) {
				Position position = new Position(x * 64, y * 64);
				Integer id;
				Action action;
				switch (map.get(x, y)) {
				case '#':
					id = IDGenerator.getId();
					manager.add(new GSolidBlock(position, id));
					action = new Action(ActionType.CREATE_SOLIDBLOCK,
							new Object[] { position, id });
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case ' ':
					if (rnd.nextInt(100) > 70)
						break;
					id = IDGenerator.getId();
					manager.add(new GDestroyableBlock(position, id));
					action = new Action(ActionType.CREATE_DESTROYABLEBLOCK,
							new Object[] { position, id });
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case '1':
					if (party.get(1) != null) {
						party.get(1).setBomberman(new GBomberman(position, 1));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 1 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '2':
					if (party.get(2) != null) {
						party.get(2).setBomberman(new GBomberman(position, 2));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 2 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '3':
					if (party.get(3) != null) {
						party.get(3).setBomberman(new GBomberman(position, 3));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 3 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '4':
					if (party.get(4) != null) {
						party.get(4).setBomberman(new GBomberman(position, 4));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 4 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				default:
					break;
				}
			}
		initmap = true;
	}
}
