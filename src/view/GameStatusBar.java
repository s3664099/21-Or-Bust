package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.GameEngine;
import model.interfaces.Player;

//This is the bar that runs along the bottom on the screen.
//It will display status updates

@SuppressWarnings("serial")
public class GameStatusBar extends JPanel {

	private JLabel label = new JLabel();
	Player currentPlayer;

	public GameStatusBar(Player currentPlayer, GameEngine gameEngine, String action) {

		this.currentPlayer = currentPlayer;

		setLayout(new GridLayout(1, 4, 0, 10));

		// Sets the label to display the current player
		if (currentPlayer != null) {
			setLabel(currentPlayer.getPlayerName());

		} else {
			setLabel("House");
		}

		// Sets the label to display the number of players
		label = new JLabel(String.format("Number of Players: %s", gameEngine.getAllPlayers().size()));
		add(label);

		// Sets the label to display what has happened
		label = new JLabel(String.format("Current Action: %s", action));
		add(label);
	}

	//Sets the label to display the current player
	public void setLabel(String name) {
		label = new JLabel(String.format("Current Player: %s", name));
		add(label);
	}

}
