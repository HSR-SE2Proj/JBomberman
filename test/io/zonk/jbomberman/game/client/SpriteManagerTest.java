package io.zonk.jbomberman.game.client;

import org.junit.*;

public class SpriteManagerTest {
	
	private SpriteManager sm;
	private SBomb bomb1 = new SBomb(null, 0, null);
	private SBomb bomb2 = new SBomb(null, 10, null);
	private SBomb bomb3 = new SBomb(null, 999, null);
	private SDestroyableBlock block1 = new SDestroyableBlock(null, 2, null);
	private SDestroyableBlock block2 = new SDestroyableBlock(null, 80, null);
	private SDestroyableBlock block3 = new SDestroyableBlock(null, 5, null);
	
	@Before
	public void init() {
		sm = new SpriteManager();
		sm.add(bomb1);
		sm.add(bomb2);
		sm.add(bomb3);
		
		sm.add(block1);
		sm.add(block2);
		sm.add(block3);
	}
	
	@Test
	public void getByIdTest() {
		Assert.assertTrue(bomb1.equals(sm.getById(bomb1.getId())));
		Assert.assertTrue(bomb2.equals(sm.getById(bomb2.getId())));
		Assert.assertTrue(bomb3.equals(sm.getById(bomb3.getId())));
		
		Assert.assertTrue(block1.equals(sm.getById(block1.getId())));
		Assert.assertTrue(block2.equals(sm.getById(block2.getId())));
		Assert.assertTrue(block3.equals(sm.getById(block3.getId())));
		
		//invalid id == null
		Assert.assertTrue(sm.getById(123456) == null);
		Assert.assertTrue(sm.getById(-1) == null);
	}
	
	@Test
	public void getByLayer() {
		Assert.assertTrue(sm.getByLayer(bomb1.getLayer()).contains(bomb1));
		Assert.assertTrue(sm.getByLayer(bomb1.getLayer()).contains(bomb2));
		Assert.assertTrue(sm.getByLayer(bomb1.getLayer()).contains(bomb3));
		
		Assert.assertTrue(sm.getByLayer(block1.getLayer()).contains(block1));
		Assert.assertTrue(sm.getByLayer(block2.getLayer()).contains(block2));
		Assert.assertTrue(sm.getByLayer(block3.getLayer()).contains(block3));
		
		//invalid Layer == null
		Assert.assertTrue(sm.getByLayer(123456) == null);
		Assert.assertTrue(sm.getByLayer(-1) == null);
	}
	
	@Test
	public void removeTest() {
		SBomb bomb = (SBomb) sm.remove(bomb1.getId());
		Assert.assertTrue(bomb.equals(bomb1));
		Assert.assertTrue(sm.getById(bomb1.getId()) == null);
		Assert.assertTrue(!sm.getByLayer(bomb1.getLayer()).contains(bomb1));
		
		
	}

}
