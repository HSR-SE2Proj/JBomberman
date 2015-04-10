package io.zonk.jbomberman.time;

import io.zonk.jbomberman.game.GameLoop;

public class Timer extends Thread {
	
	private GameLoop loop;
	private int millis;
	
	public Timer(int millis, GameLoop loop) {
		this.loop = loop;
		this.millis = millis;
	}

	@Override
	public void run() {
		
	}
}
