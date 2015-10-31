package jbomberman.game;

import jbomberman.network.NetworkFacade;

public class ActionDispatcher extends Thread {
	
	private NetworkFacade network;
	private ActionQueue queue;
	public boolean run;

	public ActionDispatcher(NetworkFacade network, ActionQueue queue) {
		this.network = network;
		this.queue = queue;
		this.setDaemon(true);
		run = true;
	}
	
	@Override
	public void run() {
		while(run) {
			queue.put(ActionSerializer.deserialize(network.receiveMessage()));
		}
	}

}
