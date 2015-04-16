package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.Box;

public class LobbyPanel extends JPanel {
	
	public LobbyPanel(ClientController cc){
		String server = cc.getServer();		
		cc.getStates();
		
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblConn = new JLabel("Connected to:");
		GridBagConstraints gbc_lblConn = new GridBagConstraints();
		gbc_lblConn.insets = new Insets(0, 20, 5, 5);
		gbc_lblConn.gridx = 1;
		gbc_lblConn.gridy = 1;
		add(lblConn, gbc_lblConn);
		
		JLabel lblServAddr = new JLabel(server);
		GridBagConstraints gbc_lblServAddr = new GridBagConstraints();
		gbc_lblServAddr.insets = new Insets(0, 0, 5, 5);
		gbc_lblServAddr.gridx = 2;
		gbc_lblServAddr.gridy = 1;
		add(lblServAddr, gbc_lblServAddr);
		
		JButton btnDisconnect = new JButton("Disconnect");
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(1, 0, 5, 20);
		gbc_btnDisconnect.gridx = 5;
		gbc_btnDisconnect.gridy = 1;
		add(btnDisconnect, gbc_btnDisconnect);
		
		JSeparator hSep = new JSeparator();
		GridBagConstraints gbc_hSep = new GridBagConstraints();
		gbc_hSep.gridwidth = 5;
		gbc_hSep.fill = GridBagConstraints.HORIZONTAL;
		gbc_hSep.insets = new Insets(10, 0, 5, 0);
		gbc_hSep.gridx = 1;
		gbc_hSep.gridy = 2;
		add(hSep, gbc_hSep);
		
		Component horizontalStrut = Box.createHorizontalStrut(150);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 3;
		add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPlayers = new GridBagConstraints();
		gbc_lblPlayers.anchor = GridBagConstraints.EAST;
		gbc_lblPlayers.insets = new Insets(20, 0, 10, 5);
		gbc_lblPlayers.gridx = 4;
		gbc_lblPlayers.gridy = 3;
		add(lblPlayers, gbc_lblPlayers);
		
		JSeparator vSep = new JSeparator();
		vSep.setOrientation(SwingConstants.VERTICAL);
		Dimension vSepDim = vSep.getPreferredSize();
		vSepDim.height = 340;
		vSep.setPreferredSize(vSepDim);
		GridBagConstraints gbc_vSep = new GridBagConstraints();
		gbc_vSep.gridheight = 14;
		gbc_vSep.insets = new Insets(0, 0, 0, 20);
		gbc_vSep.gridx = 3;
		gbc_vSep.gridy = 3;
		add(vSep, gbc_vSep);
		
		JLabel lblMeImg = new JLabel("");
		lblMeImg.setIcon(new ImageIcon(LobbyPanel.class.getResource("../ressources/BmanProfileBlue.png")));
		GridBagConstraints gbc_lblMeImg = new GridBagConstraints();
		gbc_lblMeImg.gridheight = 3;
		gbc_lblMeImg.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeImg.gridx = 1;
		gbc_lblMeImg.gridy = 4;
		add(lblMeImg, gbc_lblMeImg);
		
		JLabel lblPl1Img = new JLabel("");
		lblPl1Img.setIcon(new ImageIcon(LobbyPanel.class.getResource("../ressources/BmanProfileBlack.png")));
		GridBagConstraints gbc_lblPl1Img = new GridBagConstraints();
		gbc_lblPl1Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl1Img.gridheight = 3;
		gbc_lblPl1Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl1Img.gridx = 4;
		gbc_lblPl1Img.gridy = 4;
		add(lblPl1Img, gbc_lblPl1Img);
		
		JLabel lblPl1Name = new JLabel("Player1");
		lblPl1Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl1Name = new GridBagConstraints();
		gbc_lblPl1Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl1Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl1Name.gridx = 5;
		gbc_lblPl1Name.gridy = 4;
		add(lblPl1Name, gbc_lblPl1Name);
		
		JLabel lblPl1stat = new JLabel("not ready");
		GridBagConstraints gbc_lblPl1stat = new GridBagConstraints();
		gbc_lblPl1stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl1stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl1stat.gridx = 5;
		gbc_lblPl1stat.gridy = 5;
		add(lblPl1stat, gbc_lblPl1stat);
		
		JLabel lblYou = new JLabel("You");
		lblYou.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblYou = new GridBagConstraints();
		gbc_lblYou.insets = new Insets(0, 0, 5, 5);
		gbc_lblYou.gridx = 1;
		gbc_lblYou.gridy = 7;
		add(lblYou, gbc_lblYou);
		
		JLabel lblPL2Img = new JLabel("");
		lblPL2Img.setIcon(new ImageIcon(LobbyPanel.class.getResource("../ressources/BmanProfileBlue.png")));
		GridBagConstraints gbc_lblPL2Img = new GridBagConstraints();
		gbc_lblPL2Img.gridheight = 3;
		gbc_lblPL2Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPL2Img.gridx = 4;
		gbc_lblPL2Img.gridy = 7;
		add(lblPL2Img, gbc_lblPL2Img);
		
		JLabel lblPl2Name = new JLabel("Player2");
		lblPl2Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl2Name = new GridBagConstraints();
		gbc_lblPl2Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl2Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl2Name.gridx = 5;
		gbc_lblPl2Name.gridy = 7;
		add(lblPl2Name, gbc_lblPl2Name);
		
		JLabel lblPl2Stat = new JLabel("not ready");
		GridBagConstraints gbc_lblPl2Stat = new GridBagConstraints();
		gbc_lblPl2Stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl2Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl2Stat.gridx = 5;
		gbc_lblPl2Stat.gridy = 8;
		add(lblPl2Stat, gbc_lblPl2Stat);
		
		JButton btnReady = new JButton("I'm ready");
		GridBagConstraints gbc_btnReady = new GridBagConstraints();
		gbc_btnReady.insets = new Insets(0, 0, 5, 5);
		gbc_btnReady.gridx = 1;
		gbc_btnReady.gridy = 9;
		add(btnReady, gbc_btnReady);
		
		JLabel lblPl3Img = new JLabel("");
		lblPl3Img.setIcon(new ImageIcon(LobbyPanel.class.getResource("../ressources/BmanProfileR.png")));
		GridBagConstraints gbc_lblPl3Img = new GridBagConstraints();
		gbc_lblPl3Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl3Img.gridheight = 3;
		gbc_lblPl3Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl3Img.gridx = 4;
		gbc_lblPl3Img.gridy = 10;
		add(lblPl3Img, gbc_lblPl3Img);
		
		JLabel lblPl3Name = new JLabel("Player3");
		lblPl3Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl3Name = new GridBagConstraints();
		gbc_lblPl3Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl3Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl3Name.gridx = 5;
		gbc_lblPl3Name.gridy = 10;
		add(lblPl3Name, gbc_lblPl3Name);
		
		JLabel lblPl3Stat = new JLabel("not ready");
		GridBagConstraints gbc_lblPl3Stat = new GridBagConstraints();
		gbc_lblPl3Stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl3Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl3Stat.gridx = 5;
		gbc_lblPl3Stat.gridy = 11;
		add(lblPl3Stat, gbc_lblPl3Stat);
		
		JLabel lblPl4Img = new JLabel("");
		lblPl4Img.setIcon(new ImageIcon(LobbyPanel.class.getResource("../ressources/BmanProfileW.png")));
		GridBagConstraints gbc_lblPl4Img = new GridBagConstraints();
		gbc_lblPl4Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl4Img.insets = new Insets(2, 0, 5, 5);
		gbc_lblPl4Img.gridheight = 3;
		gbc_lblPl4Img.gridx = 4;
		gbc_lblPl4Img.gridy = 13;
		add(lblPl4Img, gbc_lblPl4Img);
		
		JLabel lblPl4Name = new JLabel("Player4");
		lblPl4Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl4Name = new GridBagConstraints();
		gbc_lblPl4Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl4Name.insets = new Insets(2, 0, 5, 0);
		gbc_lblPl4Name.gridx = 5;
		gbc_lblPl4Name.gridy = 13;
		add(lblPl4Name, gbc_lblPl4Name);
		
		JLabel lblPl4Stat = new JLabel("not ready");
		GridBagConstraints gbc_lblPl4Stat = new GridBagConstraints();
		gbc_lblPl4Stat.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPl4Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl4Stat.gridx = 5;
		gbc_lblPl4Stat.gridy = 14;
		add(lblPl4Stat, gbc_lblPl4Stat);
		

		btnDisconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cc.disconnect();
			}
		});
		
		btnReady.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnReady.getText().contains("not")) {
					btnReady.setText("I'm ready");
					cc.setReady(false);
				} else {
					btnReady.setText("I'm not ready");
					cc.setReady(true);
				}
			}
		});
	}
}
