package jbomberman.utils;

import org.junit.Before;
import org.junit.Test;

public class TimeUtilTest {
	private TimeUtil time;
	
	@Before
	public void setUp() {
		time = new TimeUtil();
	}
	
	@Test
	public void testSleepFor() {
		time.sleepFor(10);
	}
}
