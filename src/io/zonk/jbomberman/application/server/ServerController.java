package io.zonk.jbomberman.application.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.server.ServerGame;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.server.ServerNetwork;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.plaf.SliderUI;

public class ServerController implements Observer {

	private static final int READY_THRESHOLD = 2;
	private static final int COUNTDOWN_TIME = 10;
	private int countdown = 10;
	private int readyCount = 0;
	
	private ServerGame game;
	private NetworkFacade network;
	private Party party;
	private Timer timer;
	
	public static void main(String[] args) {
		new ServerController();
	}
	
	public ServerController() {
		this.network = new ServerNetwork();
		network.connect("localhost");
		this.party = new Party();
		
		waitForPlayers();
	}
	
	public void startGame(Party party) {
		game = new ServerGame(network, party);
		game.addObserver(this);

		timer = new Timer(1000/30, game);
		timer.start();
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
		Object monitoredObject = new Object();
		synchronized (monitoredObject) {
			try {
				monitoredObject.notifyAll();
				monitoredObject.wait(3000);
			} catch (InterruptedException e) {
			}
		}
		timer.run = false;
		Object[] finish = {"finishGame"};
		sendLobbyUpdate(finish);
		party = new Party();
		waitForPlayers();
	}
	
	public void waitForPlayers() {
		boolean countStarted = false;
		readyCount = 0;
		countdown = 10;
		HashMap<Integer, Boolean> playerStates = new HashMap<>();
		while (readyCount < READY_THRESHOLD || countdown > 0) {
			Action receivedAction = ActionSerializer.deserialize(network.receiveMessage());
			if(receivedAction.getActionType().equals(ActionType.LOBBY_COMMUNICATION)) {
				switch ((String)receivedAction.getProperty(0)) {
				case "connect":
					int pid = getNextId(playerStates);
					playerStates.put(pid, false);
					if(pid == 0) {
						Object[] prop = {"serverFull"};
						sendLobbyUpdate(prop);
						break;
					}

					Object[] prop = {"lobbyList", playerStates, pid, (Integer)receivedAction.getProperty(1)};
					sendLobbyUpdate(prop);

					Object[] c = {"countUpdate", countdown};
					sendLobbyUpdate(c);
					break;
					
				case "updateState":
					int playerId = (Integer)receivedAction.getProperty(1);
					Boolean playerState = (Boolean)receivedAction.getProperty(2);
					
					playerStates.put(playerId, playerState);
					updateStates(playerStates);
					if(playerState) {
						Player p = new Player("Player" + playerId, playerId);
						party.add(p);
						readyCount++;
						if(readyCount >= READY_THRESHOLD && !countStarted) {
							countStarted = true;
							startTimer();
						}
					} else {
						party.remove(party.get(playerId));
						readyCount--;
					}
					break;
					
				case "disconnect":
					readyCount--;
					int id = (Integer)receivedAction.getProperty(1);
					if(playerStates.get(id)) party.remove(party.get(id));
					playerStates.remove(id);
					updateStates(playerStates);
 					break;

				default:
					break;
				}
			}
		}
	}

	private void startTimer() {
		new Thread(() -> {
			Object monitoredObject = new Object();
			synchronized (monitoredObject) {
				try {
					while(countdown > 0) {
						monitoredObject.notifyAll();
						monitoredObject.wait(1000);
						if(readyCount >= READY_THRESHOLD) {
							countdown--;
						} else {
							countdown = COUNTDOWN_TIME;
						}
						Object[] c2 = {"countUpdate", countdown};
						sendLobbyUpdate(c2);
					}
				} catch (InterruptedException e) {
				}
			}
			startGame(party);
		}).start();
	}

	private void sendLobbyUpdate(Object[] prop) {
		Action replyAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
		network.sendMessage(ActionSerializer.serialize(replyAction));
	}
	
	private void updateStates(HashMap<Integer, Boolean> playerStates) {
		Object[] plStates = {"updateStates", playerStates};
		sendLobbyUpdate(plStates);
 	}

	private int getNextId(HashMap<Integer, Boolean> playerStates) {
		for(int i = 1; i <= 4; i++) {
			if(!playerStates.containsKey(i)) return i;
		}
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(((String)arg).equals("finishGame")) finishGame();
	}
}
