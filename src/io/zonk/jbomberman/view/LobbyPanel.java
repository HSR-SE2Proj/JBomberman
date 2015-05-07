package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;
import io.zonk.jbomberman.utils.ImageManager;

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
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;

public class LobbyPanel extends JPanel implements Observer {
	private ClientController cc;
	HashMap<Integer, Boolean> states = new HashMap<>();
	private String server = "";
	private JButton btnReady;

	private JLabel lblPl1Name = new JLabel("");
	private JLabel lblPl2Name = new JLabel("");
	private JLabel lblPl3Name = new JLabel("");
	private JLabel lblPl4Name = new JLabel("");
	
	private JLabel lblPl1Stat = new JLabel("");
	private JLabel lblPl2Stat = new JLabel("");
	private JLabel lblPl3Stat = new JLabel("");
	private JLabel lblPl4Stat = new JLabel("");

	private JLabel lblMeImg = new JLabel("");
	private JLabel lblPl1Img = new JLabel("");
	private JLabel lblPl2Img = new JLabel("");
	private JLabel lblPl3Img = new JLabel("");
	private JLabel lblPl4Img = new JLabel("");
	
	private ImageIcon[] bmanProfile = {
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFBLACK")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFBLUE")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFRED")),
			new ImageIcon(ImageManager.getInstance().get("IMG_BMAN_PROFWHITE"))
	};

	private JLabel[] lblPlName = {lblPl1Name, lblPl2Name, lblPl3Name, lblPl4Name};
	private JLabel[] lblPlStat = {lblPl1Stat, lblPl2Stat, lblPl3Stat, lblPl4Stat};
	private JLabel[] lblPlImg = {lblPl1Img, lblPl2Img, lblPl3Img, lblPl4Img};
	private final JLabel lblCountdown = new JLabel("");
	
	public LobbyPanel(ClientController cc){
		this.cc = cc;
		cc.addObserver(this);
		updatePanel();
		
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_btnDisconnect.gridx = 9;
		gbc_btnDisconnect.gridy = 1;
		add(btnDisconnect, gbc_btnDisconnect);
		
		JSeparator hSep = new JSeparator();
		GridBagConstraints gbc_hSep = new GridBagConstraints();
		gbc_hSep.gridwidth = 9;
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
		gbc_lblPlayers.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblPlayers.insets = new Insets(20, 0, 10, 5);
		gbc_lblPlayers.gridx = 8;
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
		
		GridBagConstraints gbc_lblCountdown = new GridBagConstraints();
		gbc_lblCountdown.anchor = GridBagConstraints.SOUTH;
		gbc_lblCountdown.insets = new Insets(0, 0, 11, 0);
		gbc_lblCountdown.gridx = 9;
		gbc_lblCountdown.gridy = 3;
		add(lblCountdown, gbc_lblCountdown);
		
		GridBagConstraints gbc_lblMeImg = new GridBagConstraints();
		gbc_lblMeImg.gridheight = 3;
		gbc_lblMeImg.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeImg.gridx = 1;
		gbc_lblMeImg.gridy = 4;
		add(lblMeImg, gbc_lblMeImg);
		
		Component verticalStrut_2 = Box.createVerticalStrut(64);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridheight = 3;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 5;
		gbc_verticalStrut_2.gridy = 4;
		add(verticalStrut_2, gbc_verticalStrut_2);
		
		GridBagConstraints gbc_lblPl1Img = new GridBagConstraints();
		gbc_lblPl1Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl1Img.gridheight = 3;
		gbc_lblPl1Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl1Img.gridx = 8;
		gbc_lblPl1Img.gridy = 4;
		add(lblPl1Img, gbc_lblPl1Img);
		
		lblPl1Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl1Name = new GridBagConstraints();
		gbc_lblPl1Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl1Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl1Name.gridx = 9;
		gbc_lblPl1Name.gridy = 4;
		add(lblPl1Name, gbc_lblPl1Name);
		
		GridBagConstraints gbc_lblPl1Stat = new GridBagConstraints();
		gbc_lblPl1Stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl1Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl1Stat.gridx = 9;
		gbc_lblPl1Stat.gridy = 5;
		add(lblPl1Stat, gbc_lblPl1Stat);
		
		JLabel lblYou = new JLabel("You");
		lblYou.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblYou = new GridBagConstraints();
		gbc_lblYou.insets = new Insets(0, 0, 5, 5);
		gbc_lblYou.gridx = 1;
		gbc_lblYou.gridy = 7;
		add(lblYou, gbc_lblYou);
		
		Component verticalStrut_1 = Box.createVerticalStrut(64);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridheight = 3;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 6;
		gbc_verticalStrut_1.gridy = 7;
		add(verticalStrut_1, gbc_verticalStrut_1);
		
		GridBagConstraints gbc_lblPl2Img = new GridBagConstraints();
		gbc_lblPl2Img.gridheight = 3;
		gbc_lblPl2Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl2Img.gridx = 8;
		gbc_lblPl2Img.gridy = 7;
		add(lblPl2Img, gbc_lblPl2Img);
		
		lblPl2Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl2Name = new GridBagConstraints();
		gbc_lblPl2Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl2Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl2Name.gridx = 9;
		gbc_lblPl2Name.gridy = 7;
		add(lblPl2Name, gbc_lblPl2Name);
		
		GridBagConstraints gbc_lblPl2Stat = new GridBagConstraints();
		gbc_lblPl2Stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl2Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl2Stat.gridx = 9;
		gbc_lblPl2Stat.gridy = 8;
		add(lblPl2Stat, gbc_lblPl2Stat);
		
		btnReady = new JButton("I'm ready");
		GridBagConstraints gbc_btnReady = new GridBagConstraints();
		gbc_btnReady.insets = new Insets(0, 0, 5, 5);
		gbc_btnReady.gridx = 1;
		gbc_btnReady.gridy = 9;
		add(btnReady, gbc_btnReady);
		
		Component verticalStrut = Box.createVerticalStrut(64);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 3;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 7;
		gbc_verticalStrut.gridy = 10;
		add(verticalStrut, gbc_verticalStrut);
		
		GridBagConstraints gbc_lblPl3Img = new GridBagConstraints();
		gbc_lblPl3Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl3Img.gridheight = 3;
		gbc_lblPl3Img.insets = new Insets(0, 0, 5, 5);
		gbc_lblPl3Img.gridx = 8;
		gbc_lblPl3Img.gridy = 10;
		add(lblPl3Img, gbc_lblPl3Img);
		
		lblPl3Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl3Name = new GridBagConstraints();
		gbc_lblPl3Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl3Name.insets = new Insets(0, 0, 5, 0);
		gbc_lblPl3Name.gridx = 9;
		gbc_lblPl3Name.gridy = 10;
		add(lblPl3Name, gbc_lblPl3Name);
		
		GridBagConstraints gbc_lblPl3Stat = new GridBagConstraints();
		gbc_lblPl3Stat.anchor = GridBagConstraints.WEST;
		gbc_lblPl3Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl3Stat.gridx = 9;
		gbc_lblPl3Stat.gridy = 11;
		add(lblPl3Stat, gbc_lblPl3Stat);
		
		Component verticalStrut_3 = Box.createVerticalStrut(64);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.gridheight = 3;
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 4;
		gbc_verticalStrut_3.gridy = 13;
		add(verticalStrut_3, gbc_verticalStrut_3);
		
		GridBagConstraints gbc_lblPl4Img = new GridBagConstraints();
		gbc_lblPl4Img.anchor = GridBagConstraints.NORTH;
		gbc_lblPl4Img.insets = new Insets(2, 0, 5, 5);
		gbc_lblPl4Img.gridheight = 3;
		gbc_lblPl4Img.gridx = 8;
		gbc_lblPl4Img.gridy = 13;
		add(lblPl4Img, gbc_lblPl4Img);
		
		lblPl4Name.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPl4Name = new GridBagConstraints();
		gbc_lblPl4Name.anchor = GridBagConstraints.WEST;
		gbc_lblPl4Name.insets = new Insets(2, 0, 5, 0);
		gbc_lblPl4Name.gridx = 9;
		gbc_lblPl4Name.gridy = 13;
		add(lblPl4Name, gbc_lblPl4Name);
		
		GridBagConstraints gbc_lblPl4Stat = new GridBagConstraints();
		gbc_lblPl4Stat.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPl4Stat.insets = new Insets(0, 10, 5, 0);
		gbc_lblPl4Stat.gridx = 9;
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
	
	/**
	 * Update UI parameters, do repaint of UI after this
	 */
	private void updatePanel() {
		server = cc.getServer();
		HashMap<Integer, Boolean> tmpStat = cc.getStates();
		for(int i : states.keySet()) {
			if(tmpStat.get(i) == null) {
				lblPlName[i - 1].setText("");
				lblPlImg[i - 1].setIcon(null);
				lblPlStat[i - 1].setText("");
			}
		}
		states = tmpStat;
		int playerId = cc.getPlayerId();
		
		for(Entry<Integer, Boolean> e : states.entrySet()) {
			int i = e.getKey() - 1;
			lblPlName[i].setText("Player" + e.getKey());
			lblPlImg[i].setIcon(bmanProfile[i]);
			
			if(e.getValue()) {
				lblPlStat[i].setText("ready");
			} else {
				lblPlStat[i].setText("not ready");
			}
			
			if(e.getKey() == playerId) lblMeImg.setIcon(bmanProfile[i]);
		}
		
		int c = cc.getCountdown();
		if(c < 10) {
			lblCountdown.setText("Start in 00:0" + c);
		} else {
			lblCountdown.setText("");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			updatePanel();
			repaint();
	}
	
	public JButton getDefaultButton() {
		return btnReady;
	}
}
