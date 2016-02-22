package jbomberman.utils;
import static org.junit.Assert.*;

import org.junit.*;
public class IDGeneratorTest {
	@Test
	public void testGetID(){
		assertTrue(100<=IDGenerator.getId());
	}
}
