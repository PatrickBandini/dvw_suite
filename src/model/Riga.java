package model;

import java.util.Arrays;

public class Riga {
	
	private Campo0 campo0;
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
		this.campo0 = new Campo0(campo[0]);
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
		str = campo0.getStringa() 
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

	public Campo0 getCampo0() {
		return campo0;
	}

	public void setCampo0(String s) {
		this.campo0.setStringa(s);
	}

	public String getPuntoCambioPalla() {
		return puntoCambioPalla;
	}

	public void setPuntoCambioPalla(String puntoCambioPalla) {
		this.puntoCambioPalla = puntoCambioPalla;
	}

	public String getAttaccoDopoRicezioneDifesa() {
		return attaccoDopoRicezioneDifesa;
	}

	public void setAttaccoDopoRicezioneDifesa(String attaccoDopoRicezioneDifesa) {
		this.attaccoDopoRicezioneDifesa = attaccoDopoRicezioneDifesa;
	}

	public String getFrecciaA() {
		return frecciaA;
	}

	public void setFrecciaA(String frecciaA) {
		this.frecciaA = frecciaA;
	}

	public String getFrecciaB() {
		return frecciaB;
	}

	public void setFrecciaB(String frecciaB) {
		this.frecciaB = frecciaB;
	}

	public String getFrecciaC() {
		return frecciaC;
	}

	public void setFrecciaC(String frecciaC) {
		this.frecciaC = frecciaC;
	}

	public String getTempoRilevazione() {
		return tempoRilevazione;
	}

	public void setTempoRilevazione(String tempoRilevazione) {
		this.tempoRilevazione = tempoRilevazione;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getpCasa() {
		return pCasa;
	}

	public void setpCasa(String pCasa) {
		this.pCasa = pCasa;
	}

	public String getpOspite() {
		return pOspite;
	}

	public void setpOspite(String pOspite) {
		this.pOspite = pOspite;
	}

	public String getFilmato() {
		return filmato;
	}

	public void setFilmato(String filmato) {
		this.filmato = filmato;
	}

	public String getTimecode() {
		return timecode;
	}

	public void setTimecode(String timecode) {
		this.timecode = timecode;
	}

	public Formazione getCasa() {
		return casa;
	}

	public void setCasa(Formazione casa) {
		this.casa = casa;
	}

	public Formazione getOspite() {
		return ospite;
	}

	public void setOspite(Formazione ospite) {
		this.ospite = ospite;
	}
	
	// QUERY
	
	public boolean isAlzataCP(Riga prec) {
		if (this.campo0.isAlzata()) {
			if(prec.getCampo0().isRicezione()) {
				return true;
			}
		}
		return false;
	}

}
