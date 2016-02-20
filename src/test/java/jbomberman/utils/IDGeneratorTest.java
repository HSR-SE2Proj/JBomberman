package jbomberman.utils;
import static org.junit.Assert.*;

import org.junit.*;
public class IDGeneratorTest {
	@Test
	public void testGetID(){
		assertEquals(100,IDGenerator.getId());
		assertEquals(101,IDGenerator.getId());
	}
}
