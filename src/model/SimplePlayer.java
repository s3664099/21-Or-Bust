package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private int result;

	//This is the player object that is created when a new player is added to the game
	public SimplePlayer(String playerId, String playerName, int points) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = points;
	}

	@Override
	public String getPlayerName() {

		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {

		this.playerName = playerName;
	}

	@Override
	public int getPoints() {

		return points;
	}

	@Override
	public void setPoints(int points) {

		this.points += points;

	}

	@Override
	public String getPlayerId() {

		return playerId;
	}

	@Override
	public boolean placeBet(int bet) {

		// checks to see if bet is valid
		if (bet >= 0 && bet <= points) {
			this.bet = bet;

			return true;

		} else {

			// bet is illegal
			return false;
		}
	}

	@Override
	public int getBet() {

		return bet;
	}

	@Override
	public void resetBet() {

		bet = 0;
	}

	@Override
	public int getResult() {

		return result;
	}

	@Override
	public void setResult(int result) {

		this.result = result;

	}

	public String toString() {

		return String.format("Player: id=%s, name=%s, points=%d", playerId, playerName, points);
	}

}
