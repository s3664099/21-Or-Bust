package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.AddPlayerPopUp;
import view.GameFrame;

public class NewPlayerSubmit implements ActionListener {

	private AddPlayerPopUp addPlayer;
	private GameEngine gameEngine;
	private GameFrame frame;

	public NewPlayerSubmit(AddPlayerPopUp addPlayer, GameEngine gameEngine, GameFrame frame) {
		this.addPlayer = addPlayer;
		this.gameEngine = gameEngine;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String playerName = addPlayer.getNameText();
		int points = 0;

		// Checks to see if a number was entered
		try {

			// adds to point value if a number
			points = addPlayer.getPointsText();
		} catch (NumberFormatException ex) {

			// Error generated if not
			JOptionPane.showMessageDialog(frame, "Please Enter a Number!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks to see if a name was entered
		if (playerName.equals("")) {
			// Generates error if not
			JOptionPane.showMessageDialog(frame, "You Must Have a Name!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String playerId = generateId();

		// Adds the new player to the game
		Player player = new SimplePlayer(playerId, playerName, points);
		gameEngine.addPlayer(player);
		frame.addPanel(player);

		// Kills the screen
		addPlayer.dispose();
	}

	// Generates the player ID
	private String generateId() {
		int id = 0;

		// Checks all of the players and counts them
		for (Player players : gameEngine.getAllPlayers()) {
			id = Integer.parseInt(players.getPlayerId());
		}

		// Sets the id as one more than the number of players
		id++;

		return "" + id;
	}

}
