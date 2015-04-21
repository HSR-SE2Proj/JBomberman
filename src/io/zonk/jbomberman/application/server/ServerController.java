package io.zonk.jbomberman.application.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.server.GameObjectManager;
import io.zonk.jbomberman.game.server.ServerGame;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.server.ServerNetwork;
import io.zonk.jbomberman.utils.ActionSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.plaf.SliderUI;

public class ServerController implements Observer {

	private final int READY_THRESHOLD = 2;
	
	//state???
	private ServerGame game;
	private NetworkFacade network;
	private Party party;
	
	public static void main(String[] args) {
		new ServerController();
	}
	
	public ServerController() {
		this.game = null;
		this.network = new ServerNetwork();
		network.connect("localhost");
		this.party = new Party();
//		game.addObserver(this);
		
		waitForPlayers();
	}
	
	public void startGame(Party party) {
		new ServerGame(network, party);
		String[][] sParty = new String[4][2];
		int i = 0;
		for(Player p : party.getPlayers().values()) {
			sParty[i][0] = p.getName();
			sParty[i][1] = p.getId() + "";
			i++;
		}
		Object[] start = {"startGame", sParty};
		sendLobbyUpdate(start);
	}
	
	public void finishGame() {
		
	}
	
	public void waitForPlayers() {
		int readyCount = 0;
		HashMap<Integer, Boolean> playerStates = new HashMap<>();
		while (readyCount < READY_THRESHOLD) {
			Action receivedAction = ActionSerializer.deserialize(network.receiveMessage());
			if(receivedAction.getActionType().equals(ActionType.LOBBY_COMMUNICATION)) {
				switch ((String)receivedAction.getProperty(0)) {
				case "connect":
					int pid = getNextId();
					if(pid == 0) {
						Object[] prop = {"serverFull"};
						sendLobbyUpdate(prop);
						break;
					}
					Player p = new Player("Player" + pid, pid);
					party.add(p);
					playerStates.put(p.getId(), false);

					Object[] prop = {"lobbyList", playerStates, p.getId()};
					sendLobbyUpdate(prop);
					break;
					
				case "updateState":
					int playerId = (Integer)receivedAction.getProperty(1);
					Boolean playerState = (Boolean)receivedAction.getProperty(2);
					
					playerStates.put(playerId, playerState);
					updateStates(playerStates);
					if(playerState) {
						readyCount++;
					} else {
						readyCount--;
					}
					break;
					
				case "disconnect":
					int id = (Integer)receivedAction.getProperty(1);
					party.remove(party.get(id));
					playerStates.remove(id);
					updateStates(playerStates);
 					break;

				default:
					break;
				}
			}
		}
		startGame(party);
	}

	private void sendLobbyUpdate(Object[] prop) {
		Action replyAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
		network.sendMessage(ActionSerializer.serialize(replyAction));
	}
	
	private void updateStates(HashMap<Integer, Boolean> playerStates) {
		Object[] plStates = {"updateStates", playerStates};
		sendLobbyUpdate(plStates);
 	}

	private int getNextId() {
		for(int i = 1; i <= 4; i++) {
			if(!party.getPlayers().containsKey(i)) return i;
		}
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}
