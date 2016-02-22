package jbomberman.game.client;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.BombermanState;
import jbomberman.game.PowerUpType;
import jbomberman.utils.Position;

public class SpriteTest {
	
	private SBomb bomb;
	private SBanner banner;
	private SBomberman bomberman;
	private SExplosion explosion;
	private SFloor floor;
	private SPowerUp powerup;
	private SSolidBlock solidblock;
	private Position pos;
	
	@Before
	public void setUp() {
		pos = new Position(0,0);
	}
	
	@Test
	public void testSBomb() {
		bomb = new SBomb(pos, 0);
		assertEquals(3,bomb.frameCnt);
		assertEquals("IMG_BOMB",bomb.imgName);
		assertEquals(new Dimension(64,64),bomb.size);
		bomb.tick();
		assertEquals(0,bomb.frameNr);
		bomb.setId(2);
		assertEquals(2,bomb.getId());
		bomb.setPosition(new Position(10,10));
		assertEquals(10,bomb.getPosition().getX());
		assertEquals(10,bomb.getPosition().getY());
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = image.createGraphics();
		bomb.draw(graphics2D);
	}

	@Test
	public void testSBanner() {
		//Banner Images
		banner = new SBanner(pos,1);
		assertEquals("IMG_BANNER_BMAN_BLACK", banner.imgName);
		banner = new SBanner(pos,2);
		assertEquals("IMG_BANNER_BMAN_BLUE", banner.imgName);
		banner = new SBanner(pos,3);
		assertEquals("IMG_BANNER_BMAN_RED", banner.imgName);
		banner = new SBanner(pos,4);
		assertEquals("IMG_BANNER_BMAN_WHITE", banner.imgName);
		banner = new SBanner(pos,11);
		assertEquals("IMG_BANNEROVERALL_BMAN_BLACK", banner.imgName);
		banner = new SBanner(pos,12);
		assertEquals("IMG_BANNEROVERALL_BMAN_BLUE", banner.imgName);
		banner = new SBanner(pos,13);
		assertEquals("IMG_BANNEROVERALL_BMAN_RED", banner.imgName);
		banner = new SBanner(pos,14);
		assertEquals("IMG_BANNEROVERALL_BMAN_WHITE", banner.imgName);
		banner = new SBanner(pos,15);
		assertEquals("IMG_BANNER_NO_WINNER", banner.imgName);
	}
	
	@Test
	public void testSBomberman() {
		//Bomberman Images
		bomberman = new SBomberman(pos, 1);
		assertEquals("IMG_BMAN_BLACK",bomberman.imgName);
		bomberman = new SBomberman(pos, 2);
		assertEquals("IMG_BMAN_BLUE",bomberman.imgName);
		bomberman = new SBomberman(pos, 3);
		assertEquals("IMG_BMAN_RED",bomberman.imgName);
		bomberman = new SBomberman(pos, 4);
		assertEquals("IMG_BMAN_WHITE",bomberman.imgName);
		//State Down
		bomberman.setState(BombermanState.DOWN);
		bomberman.tick();
		assertEquals(1,bomberman.animState);
		assertEquals(8,bomberman.frameCnt);
		//State Idle Down
		bomberman.setState(BombermanState.IDLE_DOWN);
		bomberman.tick();
		assertEquals(1,bomberman.animState);
		assertEquals(1,bomberman.frameCnt);
		//state Idle Left
		bomberman.setState(BombermanState.IDLE_LEFT);
		bomberman.tick();
		assertEquals(3,bomberman.animState);
		assertEquals(1,bomberman.frameCnt);
		//state Idle Right
		bomberman.setState(BombermanState.IDLE_RIGHT);
		bomberman.tick();
		assertEquals(2,bomberman.animState);
		assertEquals(1,bomberman.frameCnt);
		//state Idle Up
		bomberman.setState(BombermanState.IDLE_UP);
		bomberman.tick();
		assertEquals(0,bomberman.animState);
		assertEquals(1,bomberman.frameCnt);
		//state Left
		bomberman.setState(BombermanState.LEFT);
		bomberman.tick();
		assertEquals(3,bomberman.animState);
		assertEquals(8,bomberman.frameCnt);
		//state Right
		bomberman.setState(BombermanState.RIGHT);
		bomberman.tick();
		assertEquals(2,bomberman.animState);
		assertEquals(8,bomberman.frameCnt);
		//state Up
		bomberman.setState(BombermanState.UP);
		bomberman.tick();
		assertEquals(0,bomberman.animState);
		assertEquals(8,bomberman.frameCnt);
		//state Default Case
		bomberman.setState(BombermanState.DEAD);
		bomberman.tick();
		assertEquals(1,bomberman.animState);
		assertEquals(1,bomberman.frameCnt);
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = image.createGraphics();
		bomberman.draw(graphics2D);
	}
	
	@Test
	public void testSExplosion() {
		explosion = new SExplosion(pos, 0);
		assertEquals("IMG_EXPLOSION",explosion.imgName);
		assertEquals(5,explosion.frameCnt);
		explosion.tick();
		assertEquals(0,explosion.frameNr);
	}
	
	@Test
	public void testSFloor() {
		floor = new SFloor(pos,0);
		assertEquals("IMG_FLOOR",floor.imgName);
		floor.tick();
	}
	
	@Test
	public void testSPowerup() {
		powerup = new SPowerUp(pos,0, PowerUpType.BOMB);
		assertEquals(16,powerup.getPosition().getX());
		assertEquals(16,powerup.getPosition().getY());
		assertEquals("IMG_BOMBPOWERUP",powerup.imgName);
		powerup = new SPowerUp(pos,0, PowerUpType.POWER);
		assertEquals("IMG_FLAMEPOWERUP",powerup.imgName);
		powerup = new SPowerUp(pos,0, PowerUpType.SPEED);
		assertEquals("IMG_SPEEDPOWERUP",powerup.imgName);
	}
	
	@Test
	public void testSSolidBlock() {
		solidblock = new SSolidBlock(pos, 0);
		assertEquals("IMG_SOLIDBLOCK",solidblock.imgName);
		solidblock.tick();
	}
}
