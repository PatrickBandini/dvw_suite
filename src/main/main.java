package main;

import controller.Controller;
import model.Cache;
import model.DVW;
import view.View;

public class main {
	
	public static final String SIGLA = "VSC";
	public static final String VERSIONE = "1";
	public static final String VALIDO = "Valido";
    public static final String NEGATO = "Negato";
    public static final String IN_APPROVAZIONE = "InApprovazione";
	
    public static void main(String[] args) {
        View view = new View();
        
        Controller controller = new Controller(view, null);
        
        new Cache();
        
    }
    
}
