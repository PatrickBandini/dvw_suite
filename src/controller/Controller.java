package controller;

import model.DVW;
import view.View;

public class Controller {
	private View view;
	private DVW model;
	
	public Controller(View view, DVW model) {
		this.view = view;
		this.model = model;
	}

}
