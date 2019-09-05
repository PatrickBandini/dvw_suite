package model;

public class Formazione {
	
	private String z1;
	private String z2;
	private String z3;
	private String z4;
	private String z5;
	private String z6;
	
	public Formazione(String z1, String z2, String z3, String z4, String z5, String z6) {
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
		this.z4 = z4;
		this.z5 = z5;
		this.z6 = z6;
	}

	public String getZ1() {
		return z1;
	}

	public String getZ2() {
		return z2;
	}

	public String getZ3() {
		return z3;
	}

	public String getZ4() {
		return z4;
	}

	public String getZ5() {
		return z5;
	}

	public String getZ6() {
		return z6;
	}
	
	public String toString() {
		return z1 + ";" + z2 + ";" + z3 + ";" + z4 + ";" + z5 + ";" + z6 + ";";
	}

}
