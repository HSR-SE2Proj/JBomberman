package jbomberman.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActionQueue {
	
	private BlockingQueue<Action> queue = new LinkedBlockingQueue<>();
	
	public Action take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	public void put(Action action) {
		queue.add(action);
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
