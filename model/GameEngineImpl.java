package model;

import java.util.*;
import java.util.concurrent.TimeUnit;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.*;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	private static int BUST_LEVEL = 21;
	
	//Creates a collection of players in the form of a map
	//The map uses a key/value pair to define the players.
	private Map<String, Player> players = new HashMap<String, Player>();
	
	//Creates a collectuon of cards in the form of a Deque, or
	//a Double Ended QUEue (no, this is not French for deck.
	//It also uses a Linked List for when the deck is created in the
	//Shuffle deck method
	private Deque<PlayingCard> shuffledDeck = new LinkedList<PlayingCard>();
	
	//Creates a collection of callbacks using an ArrayList collection.
	//The theory behind this is that each player would also have a callback
	//Particularly if each of the players are human players using a separate device.
	private List<GameEngineCallback> gameEngineCallback = new ArrayList<GameEngineCallback>();
	
	private String winner = "";

	//The deal player method - the player is dealt a hand of carks
	@Override
	public void dealPlayer(Player currentPlayer, int delay) {

		//Gets the result of all the cards that have been dealt
		//and then updates the result for the current player
		int result = dealCards(delay, currentPlayer);
		currentPlayer.setResult(result);

		//sends the results to each of the callbacks to display to the various players
		for (GameEngineCallback engine : gameEngineCallback)
			engine.result(currentPlayer, result, this);

	}

	//The method where a hand is then dealt to the house
	//This method works similarly except the house is not a player
	@Override
	public void dealHouse(int delay) {
		int houseResult = dealCards(delay, null);

		//Once the house result has been determined, no more hands will be dealt
		//As such, the method is called to find the winner
		findWinner(houseResult);

		//And this is also sent to all of the callbacks to be displayed.
		for (GameEngineCallback engine : gameEngineCallback)
			engine.houseResult(houseResult, this);
	}
	
	//Determines which player (or house) has the highest score
	private int getHighestScore()
	{
		int highestScore = 0;
		for (Player player : players.values()) {
			if (player.getResult() > highestScore)
				highestScore = player.getResult();
		}
		
		return highestScore;
	}
	
	//Determines if there are any draws - this is important in case the highest score
	//is a draw. The draw count is only increased if the player with the highest score
	//is drawn with another player - the odds are always stacked in favoiur of the house
	private int findDraws(int highestScore)
	{
		int drawCount = 0;
		
		for (Player player : players.values()) {
			if (player.getResult() == highestScore)
				drawCount++;
		}
		
		return drawCount;
	}
	
	//For all the players that lost, the bets that they placed are now removed
	//from their points amount
	private void takeBets(int score)
	{

		for (Player player: players.values())
		{
			if (player.getResult()<score)
			{
				player.setPoints(player.getBet()*-1);
			}
		}
		
		//all bets are now returned to zero
		resetBet();
	}

	//The method that will find out which player (or the house) has the highest score
	private void findWinner(int houseResult) {
		
		winner = "";
		
		//The highest score is determined first
		int highestScore = getHighestScore();
		
		//Then it is determined whether there is a drawn
		int drawCount = findDraws(highestScore);

		// is the house score higher then all bets are taken
		// It is irrelevant if the house has drawn with another player
		// The house still wins (and I didn't lose any marks for this)
		if (highestScore <= houseResult) {
			
			winner = "the house";
			takeBets(houseResult);
			return;

		//if more than one player has the same score
		} else if (drawCount > 1) {
			
			winner = "draw";
			
			//the bets are automatically reset since
			//it will be a drawn. No winners in the event of a draw
			resetBet();
			return;

		} else {

			//This method is called when there isn't a drawn and the house
			//does not have the highest score.
			//The scores of each player are compared and the one with the highest score
			//wins.
			for (Player player : players.values()) {
				if (player.getResult() == highestScore)
				{
					//Winner's points are adjusted based on the win
					player.setPoints(player.getBet());
					
					winner = player.getPlayerName();
				}
			}
			
			//Yep, everybody else loses points
			takeBets(highestScore);
		}
	}
	
	//All bets and results are returned to 0 for a new game to begin
	private void resetBet()
	{
		for (Player player : players.values()) {
			
			player.placeBet(0);
			player.setResult(0);
		}
		
		
	}

	//This is the main method for dealing the cards to a specific player
	private int dealCards(int delay, Player currentPlayer) {
		boolean bust = false;
		int result = 0;

		do {
			// checks to see if there are any more cards in the deck
			if (shuffledDeck.isEmpty()) {
				
				//If the deck is empty, generates a new deck
				shuffledDeck = getShuffledDeck();
			}
			
			// gets the card from the top
			PlayingCard currentCard = shuffledDeck.pop();

			// checks to see if the player has busted
			if (result + currentCard.getScore() <= BUST_LEVEL) {

				// if not, increases the score by the value of the card
				result += currentCard.getScore();
				
				//Displays the current card to the Callback
				for (GameEngineCallback engine : gameEngineCallback)
					if (currentPlayer == null) {
						engine.nextHouseCard(currentCard, this);
					} else {
						engine.nextCard(currentPlayer, currentCard, this);
					}

			} else {
				
				//If the card will bust the player, the bust flag is set
				bust = true;

				// if busted, ends round for player
				for (GameEngineCallback engine : gameEngineCallback)
					if (currentPlayer == null) {
						engine.houseBustCard(currentCard, this);
					} else {
						engine.bustCard(currentPlayer, currentCard, this);
					}
			}

			try {
				
				//Delay between cards being dealt for userbility
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (InterruptedException e) {

			}
			
		//Checks to see if the player has bust. 
		//Continues if the player has not.
		} while (!bust);

		return result;
	}

	//Adds a new player to the player collection
	@Override
	public void addPlayer(Player player) {

		this.players.put(player.getPlayerId(), player);

	}

	//Locates the player in the collection based on the player ID
	@Override
	public Player getPlayer(String id) {

		if (players.containsKey(id)) {
			return players.get(id);
		}

		return null;
	}
	
	public String getWinner() {
		
		return winner;
	}

	//Removes the player from the collection based on the player ID
	@Override
	public boolean removePlayer(Player player) {

		// checks to see if there is such a player
		if (players.containsValue(player)) {
			players.remove(player.getPlayerId());
			return true;

		}
		return false;
	}

	//Adds a new callback to the callback collection
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		this.gameEngineCallback.add(gameEngineCallback);

	}

	//Removes a callback from the callback collection/
	//In theory, this would be connected to the players in a multiplayer secenario
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {

		if (this.gameEngineCallback.contains(gameEngineCallback)) {
			this.gameEngineCallback.remove(gameEngineCallback);
			return true;
		}
		return false;
	}

	//Returns a copy of the collection of all the players
	@Override
	public Collection<Player> getAllPlayers() {
		
		Collection<Player> playerCopy = new ArrayList<Player>(players.values());

		return playerCopy;
	}

	//Method used to place a bet based on a specific player
	@Override
	public boolean placeBet(Player player, int bet) {

		// checks to see if bet is legal and places bet if legal
		if (this.players.containsValue(player) && player.placeBet(bet)) {

			// legal bet
			return true;
		}

		// illegal bet
		return false;
	}
	
	//Creates a new deck of cards (not a deque of cards) and returns this deck of cards
	//shuffled. This is a basic deck of 52 cards with no jokers or wild cards.
	//It is definitely not a deck of Magic the Gathering Cards.
	@Override
	public Deque<PlayingCard> getShuffledDeck() {

		// creates an unshuffled deck as a LinkedList
		// This is because the suffle method works with a LinkedList
		// And the collection can easily be changed to a Deque (with which
		// The suffle method does not work.
		LinkedList<PlayingCard> deck = new LinkedList<PlayingCard>();

		// populates the deck with a unique set of cards
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {

				deck.add(new PlayingCardImpl(suit, value));
			}
		}

		// shuffles the deck
		Collections.shuffle(deck);
		
		//Returns the collection of cards as a Deque
		return deck;
	}

}
