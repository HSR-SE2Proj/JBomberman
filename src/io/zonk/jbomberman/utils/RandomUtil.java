package io.zonk.jbomberman.utils;

import java.util.Random;

public class RandomUtil {
	private static Random randomGenerator = new Random();

	public static int getRandomInt(int maxValue) {
        return randomGenerator.nextInt(maxValue);
	}
	
	public static boolean probability(int percent) {
		return getRandomInt(100) < percent;
	}
}
