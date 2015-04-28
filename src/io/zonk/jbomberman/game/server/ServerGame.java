package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

import java.util.Observable;
import java.util.Random;

public class ServerGame extends Observable implements GameLoop {

	private NetworkFacade network;
	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	private boolean initmap;
	
	public ServerGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
		
		manager = new GameObjectManager();
		
		/*
		for(Player player : party.getPlayers().values()) {
			player.setBomberman(new GBomberman(null, player.getId()));//Position
		}*/
		
		
		
		queue = new ActionQueue();
		ActionDispatcher dispatcher = new ActionDispatcher(network, queue);
		dispatcher.start();
		
		Timer timer = new Timer(1000/30, this);
		timer.start();
		
		
		
	}
	
	

	@Override
	public void loop() {
		if(!initmap)
			initMap();
		
		//while(true) { //game running?
		//System.out.println("loop");	
			//handle all available Actions
			while(!queue.isEmpty()) {
				Action action = queue.take();
				switch(action.getActionType()) {
				
				/*
				 * 
				 */
				case PLAYER_INPUT:
					int id = (int) action.getProperty(0);
					party.get(id).getBomberman().update(action);
					break;
				case CREATE_BOMB:
					manager.add(new GBomb((Position)action.getProperty(0), (int)action.getProperty(1)));
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case DESTROY_BOMB:
					manager.remove((int)action.getProperty(0));
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				default:
					break;
				
				}
			}
			
			//Tick all Objects
			for(GameObject object : manager.getAll()) {
				object.tick(queue);
			}
			
			for(Player player : party.getPlayers().values()) {
				if(player == null)
					continue;
				player.getBomberman().tick(manager, queue);
				
				
				
				player.getBomberman().sendUpdates(network);
			}
		//}
		
	}
	
	private void initMap() {
		Random rnd = new Random();
		Map map = new SpecialMap();
		for(int y = 0; y < 13; ++y)
			for(int x = 0; x < 13; ++x) {
				Position position = new Position(x*64, y*64);
				Integer id;
				Action action;
				switch(map.get(x, y)) {
				case '#':
					id = IDGenerator.getId();
					manager.add(new GSolidBlock(position, id));
					action = new Action(ActionType.CREATE_SOLIDBLOCK, new Object[]{position, id});
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case ' ':
					if(rnd.nextInt(100) > 70)
						break;
					id = IDGenerator.getId();
					manager.add(new GDestroyableBlock(position, id));
					action = new Action(ActionType.CREATE_DESTROYABLEBLOCK, new Object[]{position, id});
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case '1':
					party.get(1).setBomberman(new GBomberman(position, 1));
					action = new Action(ActionType.CREATE_BOMBERMAN, new Object[]{position, 1});
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case '2':
					party.get(2).setBomberman(new GBomberman(position, 2));
					action = new Action(ActionType.CREATE_BOMBERMAN, new Object[]{position, 2});
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				default:
					break;
				}
			}
		initmap = true;
	}
}
