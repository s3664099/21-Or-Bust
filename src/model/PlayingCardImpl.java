package model;

import model.interfaces.PlayingCard;

//This is the object that creates a playing card
//It calls upon the PlayingCard interface, which means it must use
//All of the methods from the playing card interface
public class PlayingCardImpl implements PlayingCard {

	//It should be noted that the suit and the value are both enums.
	private Suit suit;
	private Value value;

	public PlayingCardImpl(Suit suit, Value value) {

		this.suit = suit;
		this.value = value;

	}

	@Override
	public Suit getSuit() {

		return suit;
	}

	@Override
	public Value getValue() {

		return value;
	}

	@Override
	public int getScore() {

		return getCardValue();
	}
	
	//This method determines the value of the card based on the number.
	//King, Queen, and Jack are all 10, Ace is 1, every other number is the score.
	private int getCardValue() {
		
		//One is added since the value of the card counts from 0
		int cardValue = value.ordinal() + 1;

		// If the card has a face value, assigns the value as ten
		if (value.equals(Value.JACK) || value.equals(Value.QUEEN) || value.equals(Value.KING)) {
			cardValue = 10;
		}
		
		return cardValue;
	}

	@Override
	public String toString() {

		return "Suit: " + suit + ", Value: " + value + ", Score: " + getCardValue();
	}

	//This is basically the method used to determine whether two cards are equal.
	//This is because there should only be one card of each type in the deck.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	//This is the generated equals method that is created through Eclips
	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayingCard other = (PlayingCard) obj;
		if (!equals(other))
			return false;
		return true;
	}

	//This is the equals method to determine the value of the card
	@Override
	public boolean equals(PlayingCard card) {

		return card != null && card.getSuit().equals(suit) && card.getValue().equals(value);
	}

}
