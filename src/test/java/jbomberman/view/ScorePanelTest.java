package jbomberman.view;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Party;
import jbomberman.game.Player;

public class ScorePanelTest {
	
	private ScorePanel panel;
	private Party party;
	private Player player1,player2,player3,player4;
	
	@Before
	public void setUp() {
		party = new Party();
		player1 = new Player("Player1",1);
		player2 = new Player("Player2",2);
		player3 = new Player("Player3",3);
		player4 = new Player("Player4",4);
		party.add(player1);
		party.add(player2);
		party.add(player3);
		party.add(player4);
		panel = new ScorePanel(party,1);
	}
	
	@Test
	public void testlayout() {
		assertEquals(new BorderLayout().getClass(),panel.getLayout().getClass());
	}
	
	@Test
	public void testSize() {
		assertEquals(new Dimension(0,0),panel.getSize());
	}
	
	@Test
	public void testUpdateTimer() {
		panel.updateTimer(100);
	}

}
