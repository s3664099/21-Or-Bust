package client;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;

public class Main {
	
	//The driver program for the game
	public static void main(String[] args) {
		
		//Creates a new game
		GameEngine gameEngine = new GameEngineImpl();

		//Creates two callbacks, or displays, one for the console
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		
		//and one for the graphical user interface
		gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(gameEngine));

	}

}
