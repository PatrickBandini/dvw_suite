package main;

import controller.Controller;
import model.DVW;
import view.View;

public class main {
	
	public static final String SIGLA = "VSC";
	public static final String VERSIONE = "1";
	
    public static void main(String[] args) {
        View view = new View();
        
        Controller controller = new Controller(view, null);
        
    }
    
}
