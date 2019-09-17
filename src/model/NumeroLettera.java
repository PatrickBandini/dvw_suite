package model;

public class NumeroLettera {
	
	private String numero;
	private char lettera;
	
	public NumeroLettera(String numero, char lettera) {
		this.numero = numero;
		this.lettera = lettera;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public char getLettera() {
		return this.lettera;
	}
	
	public boolean isStessoNumero(String num) {
		if (num.equals(this.numero)) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return this.numero + " • " + this.lettera;
	}
	

}
