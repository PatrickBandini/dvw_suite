package model;

import java.util.Arrays;

public class Riga {
	
	private String campo0;
	private String puntoCambioPalla;
	private String attaccoDopoRicezioneDifesa;
	private String frecciaA;
	private String frecciaB;
	private String frecciaC;
	private String tempoRilevazione;
	private String set;
	private String pCasa; //zona palleggiatore casa
	private String pOspite; //zona palleggiatore ospite
	private String filmato;
	private String timecode; //in secondi (del filmato)
	private Formazione casa;
	private Formazione ospite;
	
	
	
	
	public Riga(String riga) {
		String[] campo = riga.split(";");
		if (campo.length < 26) {
			int len = campo.length;
			campo = Arrays.copyOf(campo, 26);
			for(int i=len;i<26;i++) {
				campo[i] = "";
			}
		}
		this.campo0 = campo[0];
		this.puntoCambioPalla = campo[1];
		this.attaccoDopoRicezioneDifesa = campo[2];
		this.frecciaA = campo[4];
		this.frecciaB = campo[5];
		this.frecciaC = campo[6];
		this.tempoRilevazione = campo[7];
		this.set = campo[8];
		this.pCasa = campo[9];
		this.pOspite = campo[10];
		this.filmato = campo[11];
		this.timecode = campo[12];
		this.casa = new Formazione(campo[14], campo[15], campo[16], campo[17], campo[18], campo[19]);
		this.ospite = new Formazione(campo[20], campo[21], campo[22], campo[23], campo[24], campo[25]);
	}
	
	public String toString() {
		String str = "";
		str = campo0 
				+ ";" + puntoCambioPalla 
				+ ";" + attaccoDopoRicezioneDifesa
				+ ";" + ";"
				+ ";" + frecciaA
				+ ";" + frecciaB
				+ ";" + frecciaC
				+ ";" + tempoRilevazione
				+ ";" + set
				+ ";" + pCasa
				+ ";" + pOspite
				+ ";" + filmato
				+ ";" + timecode
				+ ";" + casa.toString()
				+ ospite.toString();
		return str;
	}

}
