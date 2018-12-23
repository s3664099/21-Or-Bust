package view;

import javax.swing.JButton;
import javax.swing.JToolBar;

import controller.AddPlayerEvent;
import controller.DealPlayerEvent;
import controller.NewGame;
import controller.PlaceBetEvent;
import controller.QuitGameEvent;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class GameToolBar extends JToolBar {

	private JButton button;
	private Player player;
	private GameFrame frame;
	private GameEngine gameEngine;

	public GameToolBar(GameFrame frame, GameEngine gameEngine) {

		player = frame.getCurrentPlayer();
		this.frame = frame;
		this.gameEngine = gameEngine;

		newButton("New Game");
		newButton("Add Player");
		newButton("Place Bet");
		newButton("Deal Hand");
		newButton("Quit Game");

	}
	
	//Creates the buttons along the tool bar, and adds the various listeners
	private void newButton(String type) {
		button = new JButton(type);

		switch (type) {
		case "New Game":

			button.addActionListener(new NewGame(frame));
			break;

		case "Add Player":

			button.addActionListener(new AddPlayerEvent(gameEngine, frame));
			break;

		case "Place Bet":

			button.addActionListener(new PlaceBetEvent(frame));
			button.setEnabled(false);

			if (frame.getCurrentPlayer() != null) {
				addBetListener(button);
			}
			break;

		case "Deal Hand":

			button.addActionListener(new DealPlayerEvent(frame, gameEngine));
			button.setEnabled(false);

			// Checks to see if the current player is not the house
			if (frame.getCurrentPlayer() != null) {
				
				// if not then checks to see if player can deal
				activateDealButton(button);
			}
			break;
			
		case "Quit Game":
			
			button.addActionListener(new QuitGameEvent());
			break;
		}

		add(button);
	}

	private void addBetListener(JButton button) {
		
		// Checks to see if the player has placed a bet
		if (frame.getCurrentPlayer().getBet() == 0) {

			// if not, activates the bet button
			button.setEnabled(true);
		}
	}

	private void activateDealButton(JButton button) {
		
		// Checks to see if the player has bet and is able to deal
		if (player.getBet() > 0 && !frame.getPanel().getDeal()) {

			// activates the 'deal hand' button
			button.setEnabled(true);
		}
	}

}
