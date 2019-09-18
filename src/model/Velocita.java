package model;

public class Velocita {
	
	private char set = ' ';
	private String punteggio = "";
	private String velocita = "";
	
	public Velocita(String riga) {
		String[] stringhe = riga.split(";");
		if (stringhe.length>=3) {
			this.set = stringhe[0].charAt(0);
			this.punteggio = stringhe[1];
			this.velocita = stringhe[2];
		}	
	}
	
	public char getSet() {
		return this.set;
	}
	
	public String getPunteggio() {
		return this.punteggio;
	}
	
	public String getVelocita() {
		return this.velocita;
	}
	
	public String getPunteggioCasa() {
		String[] str = this.punteggio.split(":");
		if (str.length>=2) {
			return str[0];
		}
		return "";
	}
	
	public String getPunteggioOspite() {
		String[] str = this.punteggio.split(":");
		if (str.length>=2) {
			return str[1];
		}
		return "";
	}

}
