package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GameFrame;

//The new game event.
//Basically clears everything from memory
//Or at least all of the hands, and starts again from
//scratch
public class NewGame implements ActionListener {

	private GameFrame frame;
	
	public NewGame(GameFrame frame) {
		
		this.frame = frame;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		frame.newGame();

	}

}
