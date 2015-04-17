package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.GameObjectType;

import org.junit.*;

public class GameObjectManagerTest {
	
	private GameObjectManager om;
	private GBomb bomb1 = new GBomb(null, 0);
	private GBomb bomb2 = new GBomb(null, 100);
	private GBomb bomb3 = new GBomb(null, 123);
	
	@Before
	public void init() {
		om = new GameObjectManager();
		om.add(bomb1);
		om.add(bomb2);
		om.add(bomb3);
	}
	
	@Test
	public void getByIdTest() {
		Assert.assertTrue(om.getById(bomb1.getId()).equals(bomb1));
		Assert.assertTrue(om.getById(bomb2.getId()).equals(bomb2));
		Assert.assertTrue(om.getById(bomb3.getId()).equals(bomb3));
		
		//invalid id == null
		Assert.assertTrue(om.getById(555) == null);
		Assert.assertTrue(om.getById(-1) == null);
	}
	
	@Test
	public void getByTypeTest() {
		Assert.assertTrue(om.getByType(bomb1.getType()).contains(bomb1));
		Assert.assertTrue(om.getByType(bomb2.getType()).contains(bomb2));
		Assert.assertTrue(om.getByType(bomb3.getType()).contains(bomb3));
		
		//invalid type == null
		Assert.assertTrue(om.getByType(GameObjectType.BOMBERMAN) == null);
	}
	
	@Test
	public void removeTest() {
		GBomb bomb = (GBomb) om.remove(bomb1.getId());
		Assert.assertTrue(bomb.equals(bomb1));
		Assert.assertTrue(om.getById(bomb1.getId()) == null);
		Assert.assertTrue(!om.getByType(bomb1.getType()).contains(bomb1));
	}

}
