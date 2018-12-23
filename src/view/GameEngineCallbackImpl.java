package view;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

// logger.log(Level.INFO, call method by passing through strings);

// This class is similar to the GUI callback, except that this displays the details on the
// console. Mind you, note that the logger method is used, which means that the details are
// displayed at the end.
public class GameEngineCallbackImpl implements GameEngineCallback {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public GameEngineCallbackImpl() {
		// FINE shows dealing output, INFO only shows result
		logger.setLevel(Level.FINE);
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		logger.log(Level.INFO, dealMessage(card.toString(), player.getPlayerName(), ""));
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {

		logger.log(Level.INFO, resultMessage(player.getPlayerName(), result));

	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		logger.log(Level.INFO, dealMessage(card.toString(), player.getPlayerName(), "... YOU BUSTED!!"));

	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {

		logger.log(Level.INFO, dealMessage(card.toString(), "house", ""));

	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {

		logger.log(Level.INFO, dealMessage(card.toString(), "house", "... YOU BUSTED!!"));

	}

	@Override
	public void houseResult(int result, GameEngine engine) {

		logger.log(Level.INFO, resultMessage("House", result));

		Collection<Player> players = engine.getAllPlayers();

		String display = "Final Player Results \n";

		for (Player player : players) {
			display += String.format("Player: id=%s, name=%s, points=%d%n", player.getPlayerId(),
					player.getPlayerName(), player.getPoints());
		}
		logger.log(Level.INFO, display);
	}

	// Returns the final result of the hand
	private String resultMessage(String player, int result) {
		return String.format("%s, final result: %d", player, result);
	}

	// Returns the details of the current deal
	private String dealMessage(String card, String player, String busted) {
		return String.format("Card dealt to %s ... %s %s", card, player, busted);
	}

}
