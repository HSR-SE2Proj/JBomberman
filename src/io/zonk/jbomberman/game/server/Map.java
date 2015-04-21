package io.zonk.jbomberman.game.server;

public abstract class Map {
	protected char[][] map;
	public char get(int x, int y) {
		return map[y][x];
	}
}
