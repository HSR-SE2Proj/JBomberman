package io.zonk.jbomberman.utils;

public class IDGenerator {
	
	private static int id = 100;
	public static int getId() {
		return id++;
	}

}
