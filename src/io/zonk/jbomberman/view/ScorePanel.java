package io.zonk.jbomberman.view;

import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.utils.ImageManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

import javax.swing.SwingConstants;

public class ScorePanel extends JPanel {
	private static final long serialVersionUID = 8982380817405788101L;
	
	private JLabel lblTime;
	
	private ImageIcon[] bmanProfile = {
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFBLACK")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFBLUE")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFRED")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFWHITE"))
	};
	public ScorePanel(Party party, int round) {
		setBorder(new EmptyBorder(10, 30, 0, 0));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pPlayers = new JPanel();
		add(pPlayers, BorderLayout.EAST);
		GridBagLayout gbl_pPlayers = new GridBagLayout();
		gbl_pPlayers.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pPlayers.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pPlayers.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pPlayers.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pPlayers.setLayout(gbl_pPlayers);
		
		JLabel lblPl1Img = new JLabel("");
		GridBagConstraints gbc_lblPl1Img = new GridBagConstraints();
		gbc_lblPl1Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl1Img.gridx = 0;
		gbc_lblPl1Img.gridy = 0;
		pPlayers.add(lblPl1Img, gbc_lblPl1Img);
		
		JLabel lblPl2Img = new JLabel("");
		GridBagConstraints gbc_lblPl2Img = new GridBagConstraints();
		gbc_lblPl2Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl2Img.gridx = 2;
		gbc_lblPl2Img.gridy = 0;
		pPlayers.add(lblPl2Img, gbc_lblPl2Img);
		
		JLabel lblPl3Img = new JLabel("");
		GridBagConstraints gbc_lblPl3Img = new GridBagConstraints();
		gbc_lblPl3Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl3Img.gridx = 4;
		gbc_lblPl3Img.gridy = 0;
		pPlayers.add(lblPl3Img, gbc_lblPl3Img);
		
		JLabel lblPl4Img = new JLabel("");
		GridBagConstraints gbc_lblPl4Img = new GridBagConstraints();
		gbc_lblPl4Img.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl4Img.gridx = 6;
		gbc_lblPl4Img.gridy = 0;
		pPlayers.add(lblPl4Img, gbc_lblPl4Img);
		
		JLabel lblPl1Scr = new JLabel();
		lblPl1Scr.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPl1Scr = new GridBagConstraints();
		gbc_lblPl1Scr.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl1Scr.gridx = 0;
		gbc_lblPl1Scr.gridy = 2;
		pPlayers.add(lblPl1Scr, gbc_lblPl1Scr);
		
		JLabel lblPl2Scr = new JLabel();
		lblPl2Scr.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPl2Scr = new GridBagConstraints();
		gbc_lblPl2Scr.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl2Scr.gridx = 2;
		gbc_lblPl2Scr.gridy = 2;
		pPlayers.add(lblPl2Scr, gbc_lblPl2Scr);
		
		JLabel lblPl3Scr = new JLabel();
		lblPl3Scr.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPl3Scr = new GridBagConstraints();
		gbc_lblPl3Scr.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl3Scr.gridx = 4;
		gbc_lblPl3Scr.gridy = 2;
		pPlayers.add(lblPl3Scr, gbc_lblPl3Scr);
		
		JLabel lblPl4Scr = new JLabel();
		lblPl4Scr.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPl4Scr = new GridBagConstraints();
		gbc_lblPl4Scr.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl4Scr.gridx = 6;
		gbc_lblPl4Scr.gridy = 2;
		pPlayers.add(lblPl4Scr, gbc_lblPl4Scr);
		
		JPanel pRound = new JPanel();
		add(pRound, BorderLayout.WEST);
		GridBagLayout gbl_pRound = new GridBagLayout();
		gbl_pRound.columnWidths = new int[]{67, 0};
		gbl_pRound.rowHeights = new int[]{20, 20, 0};
		gbl_pRound.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pRound.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pRound.setLayout(gbl_pRound);
		
		JLabel lblRound = new JLabel("Round " + round);
		lblRound.setHorizontalAlignment(SwingConstants.CENTER);
		lblRound.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblRound = new GridBagConstraints();
		gbc_lblRound.insets = new Insets(20, 0, 5, 0);
		gbc_lblRound.gridx = 0;
		gbc_lblRound.gridy = 0;
		pRound.add(lblRound, gbc_lblRound);
		
		lblTime= new JLabel("3:00");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 1;
		pRound.add(lblTime, gbc_lblTime);

		JLabel[] lblPlImg = {lblPl1Img, lblPl2Img, lblPl3Img, lblPl4Img};
		JLabel[] lblPlScr = {lblPl1Scr, lblPl2Scr, lblPl3Scr, lblPl4Scr};
		
		for(Player p : party.getPlayers().values()){
			int id = p.getId() - 1;
			lblPlImg[id].setIcon(bmanProfile[id]);
			lblPlScr[id].setText(Integer.toString(p.getScore()));

			setStrut(pPlayers, p.getId());
		}

	}

	private void setStrut(JPanel pPlayers, int pid) {
		int strutX = 0;
		switch(pid) {
			case 1: 
				strutX = 1;
				break;
			case 2: 
				strutX = 3;
				break;
			case 3:
				strutX = 5;
				break;
			case 4:
				strutX = 7;
				break;
			default:
				break;
		}
		
		if(strutX != 0) {
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_horizontalStrut.gridx = strutX;
			gbc_horizontalStrut.gridy = 0;
			pPlayers.add(horizontalStrut, gbc_horizontalStrut);
		}
	}
	
	public void updateTimer(int time) {
		int min = time / 60;
		int sec = time - (min * 60);
		String sSec = Integer.toString(sec);
		if(sec < 10){
			sSec = "0" + sSec;
		}
		lblTime.setText(min + ":" + sSec);
		repaint();
	}
}
