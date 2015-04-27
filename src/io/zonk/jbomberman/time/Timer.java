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
// 		long lastTime = System.currentTimeMillis();
// 		long now;
// 		long delta;
		System.out.println("[*] Timer started");
		while (true) {
			Object monitoredObject = new Object();
			synchronized (monitoredObject) {
				try {
					monitoredObject.notifyAll();
					monitoredObject.wait(millis);
				} catch (InterruptedException e) {
				}
			}
			loop.loop();
// 			now = System.currentTimeMillis();
// 			delta = now - lastTime;
// 			if(delta >= millis) {
// 				//System.out.println("Delta: " + (delta - millis));
// 				loop.loop();
// 				lastTime = System.currentTimeMillis();
// 			}
		}
	}
}
