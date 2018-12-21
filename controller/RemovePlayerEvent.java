package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;

public class RemovePlayerEvent implements ActionListener {

	private GameEngine gameEngine;
	private GameFrame frame;
	private Player player;

	public RemovePlayerEvent(GameEngine gameEngine, GameFrame frame, Player player) {

		this.gameEngine = gameEngine;
		this.frame = frame;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		gameEngine.removePlayer(player);
		frame.removePanel(player.getPlayerId());
		frame.updatePlayerBar();
		frame.updateStatusBar(String.format("%s Has Left the Game", player.getPlayerName()));
		frame.updateMenuBar();

	}

}
