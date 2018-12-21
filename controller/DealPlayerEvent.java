package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.GamePanel;

public class DealPlayerEvent implements ActionListener {

	private GameEngine gameEngine;
	private Player currentPlayer;
	private GamePanel panel;
	private GameFrame frame;

	// Get Panel and current player from the frame - all we need to pass is the
	// frame
	public DealPlayerEvent(GameFrame frame, GameEngine gameEngine) {
		this.panel = frame.getPanel();
		this.gameEngine = gameEngine;
		this.currentPlayer = frame.getCurrentPlayer();
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Generates a thread to deal the hand
		new Thread() {
			public void run() {
				panel.setDeal();
				frame.updateToolBar();
				gameEngine.dealPlayer(currentPlayer, 1000);
			}
		}.start();
	}

}
