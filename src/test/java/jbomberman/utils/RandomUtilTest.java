package jbomberman.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomUtilTest {

	@Test
	public void testGetRandomInt() {
		assertTrue(RandomUtil.getRandomInt(200)<201);
	}
	
	@Test
	public void testProbability(){
		assertEquals(true,RandomUtil.probability(101));
	}
}
