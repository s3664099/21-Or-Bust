package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.Player;
import view.GameFrame;

public class SwitchPlayerEvent implements ActionListener {

	GameFrame frame;
	Player player;

	public SwitchPlayerEvent(GameFrame frame, Player player) {

		this.frame = frame;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.setCurrentPlayer(player);
	}

}
