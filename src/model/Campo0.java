package model;

public class Campo0 {
	private String stringa;
	
	public Campo0(String s) {
		this.stringa = s;
	}
	
	public String getStringa() {
		return this.stringa;
	}
	
	public void setStringa(String s) {
		this.stringa = s;
	}
	
	public boolean isAlzata() {
		return stringa.charAt(1) == 'E';
	}
	
	public boolean isRicezione() {
		return stringa.charAt(1) == 'R';
	}
}
