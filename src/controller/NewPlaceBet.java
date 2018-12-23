package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.interfaces.Player;
import view.GameFrame;
import view.PlaceBetPopup;
import view.GameStatusBar;

public class NewPlaceBet implements ActionListener {

	private PlaceBetPopup placeBetPopup;
	private Player player;
	private GameFrame frame;
	private int bet;
	GameStatusBar playerBar;

	public NewPlaceBet(GameFrame frame, PlaceBetPopup placeBetPopup) {

		this.frame = frame;
		this.placeBetPopup = placeBetPopup;
		this.player = frame.getCurrentPlayer();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Checks to see if a number was entered
		try {
			// if a number places it into bet field
			bet = placeBetPopup.getBetAmount();
		} catch (NumberFormatException ex) {

			// Error displayed if not a number
			JOptionPane.showMessageDialog(frame, "Please Enter a Number!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Updates Frame once bet placed
		if (player.placeBet(bet)) {
			frame.updatePlayerBar();
			frame.updateToolBar();

			// Removes bet popup once bet placed
			placeBetPopup.dispose();

		} else {

			// Error box displayed if not enough money
			JOptionPane.showMessageDialog(frame,
					String.format("%s Does not have enough for" + " that bet!", player.getPlayerName()), "Error",
					JOptionPane.ERROR_MESSAGE);

		}

	}

}
