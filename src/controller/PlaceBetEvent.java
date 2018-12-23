package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.Player;
import view.GameFrame;
import view.PlaceBetPopup;

public class PlaceBetEvent implements ActionListener {

	private Player player;
	private GameFrame frame;

	public PlaceBetEvent(GameFrame frame) {

		this.frame = frame;
		this.player = frame.getCurrentPlayer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		new PlaceBetPopup(frame, player);

	}

}
