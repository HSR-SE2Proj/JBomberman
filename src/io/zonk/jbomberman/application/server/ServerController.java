package io.zonk.jbomberman.application.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.server.ServerGame;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.server.ServerNetwork;
import io.zonk.jbomberman.utils.ActionSerializer;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

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
		
	}
	
	public void finishGame() {
		
	}
	
	public void waitForPlayers() {
		int playerCount = 0;
		int readyCount = 0;
		String[][] playerStates = new String[4][3];
		while (readyCount < READY_THRESHOLD) {
			Action receivedAction = ActionSerializer.deserialize(network.receiveMessage());
			if(receivedAction.getActionType().equals(ActionType.LOBBY_COMMUNICATION)) {
				switch ((String)receivedAction.getProperty(0)) {
				case "Connect":
					Player p = new Player("Player" + (playerCount + 1), ((Integer)receivedAction.getProperty(1)));
					party.add(p);
					playerStates[playerCount][0] = p.getName();
					playerStates[playerCount][1] = p.getId() + "";
					playerStates[playerCount][2] = "false";
					
					Object[] prop = {"LobbyList", playerStates, playerCount};
					Action replyAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
					network.sendMessage(ActionSerializer.serialize(replyAction));

					playerCount++;
					break;
					
				case "StateUpdate":
					break;

				default:
					break;
				}
			}
		}
		startGame(party);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}
