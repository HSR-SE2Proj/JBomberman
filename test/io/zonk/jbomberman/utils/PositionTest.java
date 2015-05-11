package io.zonk.jbomberman.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class PositionTest {
	private Position pos;
	private Position pos2;
	@Before
	public void init() {
		pos = new Position(3,4);
		pos2 = new Position();
	}
	
	@Test
	public void testXCoordinate(){
		assertEquals(3,pos.getX());
	}
	
	@Test
	public void testYCoorindate() {
		assertEquals(4,pos.getY());
	}
	
	@Test
	public void testSetXCoordinate(){
		pos.setX(5);
		assertEquals(5,pos.getX());
	}
	
	@Test
	public void testSetYCoordinate(){
		pos.setY(9);
		assertEquals(9,pos.getY());
	}
	
	@Test
	public void testDefaultCase(){
		assertEquals(0,pos2.getX());
		assertEquals(0,pos2.getY());
	}
	
	@Test
	public void testIncrementX(){
		pos.incrementX(3);
		assertEquals(6,pos.getX());
	}
	
	@Test
	public void testIncrementY() {
		pos.incrementY(4);
		assertEquals(8,pos.getY());
	}
	
	@Test
	public void testDecrementX() {
		pos.decrementX(2);
		assertEquals(1,pos.getX());
	}
	
	@Test
	public void testDecrementY(){
		pos.decrementY(3);
		assertEquals(1,pos.getY());
	}
	
	@Test
	public void testClone() {
		assert(pos.equals(pos.clonePos()));
	}
}
