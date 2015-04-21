package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

import java.util.Observable;

public class ServerGame extends Observable implements GameLoop {

	private NetworkFacade network;
	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	
	public ServerGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
		
		manager = new GameObjectManager();
		
		for(Player player : party.getPlayers().values()) {
			player.setBomberman(new GBomberman(null, player.getId()));//Position
		}
		
		
		
		queue = new ActionQueue();
		ActionDispatcher dispatcher = new ActionDispatcher(network, queue);
		dispatcher.start();
		
		Timer timer = new Timer(1000/30, this);
		timer.start();
		
		//initMap();
		
	}
	
	

	@Override
	public void loop() {
		System.out.println("Server Game Running");
		while(true) { //game running?
			
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
					
					
				default:
					break;
				
				}
			}
			
			//Tick all Objects
			for(GameObject object : manager.getAll()) {
				object.tick();
			}
			
			//send Updates
			
		}
		
	}
	
	private void initMap() {
		Map map = new StandardMap();
		for(int y = 0; y < 13; ++y)
			for(int x = 0; x < 13; ++x) {
				if(map.get(x, y) == '#') {
					Position position = new Position(x*64, y*64);
					Integer id = IDGenerator.getId();
					manager.add(new GSolidBlock(position, id));
					Action action = new Action(ActionType.CREATE_SOLIDBLOCK, new Object[]{position, id});
					network.sendMessage(ActionSerializer.serialize(action));
				}
			}
	}
}
