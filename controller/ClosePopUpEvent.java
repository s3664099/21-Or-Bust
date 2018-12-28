package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AddPlayerPopUp;

//This closes a popup box
public class ClosePopUpEvent implements ActionListener {

	private AddPlayerPopUp addPlayerPopUp;

	public ClosePopUpEvent(AddPlayerPopUp addPlayerPopUp) {

		this.addPlayerPopUp = addPlayerPopUp;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		addPlayerPopUp.dispose();
	}

}
