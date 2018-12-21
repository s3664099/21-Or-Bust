package view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

//This is the base class that creates the GUI. This is based on the GameEngine Callback Interface
//All of the GUI Functions emerge from this section of the game.
public class GameEngineCallbackGUI implements GameEngineCallback {

	GameFrame frame;

	public GameEngineCallbackGUI(GameEngine gameEngine) {
		
		//Creates a new thread to run the game frame
		//This is so that multiple functions can run concurrently
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				//Creates the basic window for the game
				frame = new GameFrame(gameEngine);
			}
		});
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {

		//Calls the method to make the card appear in the window for the player
		//This method is for a card that does not bust. The below methods are for
		//cards that do bust, and also for the house.
		placeCard(player.getPlayerId(), player.getPlayerName(), card, false);

	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {

		placeCard(player.getPlayerId(), player.getPlayerName(), card, true);

	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {

		placeCard("house", "House", card, false);

	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {

		placeCard("house", "House", card, true);

	}
	
	@Override
	public void result(Player player, int result, GameEngine engine) {
		
		//This shows the final result for the player as a popup dialogue box.
		//This also updates the player bar at the same time
		frame.updatePlayerBar();
		JOptionPane.showMessageDialog(frame, String.format("%s, final result: %d", player.getPlayerName(), result,
				"Player Result", JOptionPane.PLAIN_MESSAGE));
		
		// Checks to see if all players have dealt
		if (frame.checkDeal()) {
			
			// If so, begins house deal
			frame.setHouseView();
			new Thread() {

				@Override
				public void run() {
					engine.dealHouse(1000);
				}
			}.start();
		}
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		
		//This shows the final result for the house as a popup dialogue box
		//No dialogue box exists to show the winner though.
		frame.updatePlayerBar();
		JOptionPane.showMessageDialog(frame, String.format("House, final result: %d", result), "House Result",
				JOptionPane.PLAIN_MESSAGE);
		
		String winner = engine.getWinner();
		
		//Once the house has finished, the points are calculated and the winner is displayed
		if (winner.equals("draw"))
		{
			//The result ended up in a draw
			JOptionPane.showMessageDialog(frame, String.format("The result is a draw"), "Final Result",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			
			//There is a definite winner
			JOptionPane.showMessageDialog(frame, String.format("The winner is: %s", winner), "Final Result",
					JOptionPane.PLAIN_MESSAGE);
		}
		
	}

	//This method places the card in the dialogue box
	private void placeCard(String id, String name, PlayingCard card, boolean bust) {

		//Note that a new thread is created to do this. This is so that other functions can
		//be performed at the same time, and that the program doesn't freeze when the hands
		//are being dealt. Mind you, you have to be pretty fast to do anything else while the
		//hands are being dealt.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.nextCard(id, name, card, bust);
			}
		});
	}

}
