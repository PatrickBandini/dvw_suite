package model;

public class Campo0 {
	private String stringa;
	
	public Campo0(String s) {
		this.stringa = s;
	}
	
	public String getStringa() {
		return this.stringa;
	}
	
	public String toString() {
		return getStringa();
	}
	
	public void setStringa(String s) {
		this.stringa = s;
	}
	
	public boolean isAlzata() {
		try {
			return stringa.charAt(3) == 'E';
		} catch (StringIndexOutOfBoundsException ex) {
			return false;
		}
	}
	
	public boolean isRicezione() {
		try {
			return stringa.charAt(3) == 'R';
		} catch (StringIndexOutOfBoundsException ex) {
			return false;
		}
	}
}
