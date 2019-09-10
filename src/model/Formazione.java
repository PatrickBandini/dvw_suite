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
	
	/**
	 * true se il giocatore in input è in prima linea
	 * @param g
	 * @return
	 */
	public boolean isPrimaLinea(String g) {
		g = remove0(g);
		if (g.equals(z2) || g.equals(z3) || g.equals(z4)) {
			return true;
		}
		return false;
	}
	
	/**
	 * true se il giocatore in input è in seconda linea
	 * @param g
	 * @return
	 */
	public boolean isSecondaLinea(String g) {
		g = remove0(g);
		return !isPrimaLinea(g);
	}
	
	/**
	 * true se il giocatore in input � in campo
	 * @param g
	 * @return
	 */
	public boolean isInCampo(String g) {
		g = remove0(g);
		if (
			g.equals(z1) ||
			g.equals(z2) ||
			g.equals(z3) ||
			g.equals(z4) ||
			g.equals(z5) ||
			g.equals(z6) 
		) {
			return true;
		}
		return false;
	}
	
	/**
	 * true se il giocatore in input, data la P (zona del palleggiatore) in input è uno schiacciatore
	 * @param g
	 * @param p
	 * @return
	 */
	public boolean isSchiacciatore(String g, int p) {
		g = remove0(g);
		if (g.equals(getNextAntiorario(p)) || g.equals(getDiagonale(p+1))) {
			return true;
		}
		return false;
	}
	
	public boolean isPalleggiatore(String g, int p) {
		g = remove0(g);
		if (g.equals(getByZona(p))) {
			return true;
		}
		return false;
	}
	
	public boolean isOpposto(String g, int p) {
		g = remove0(g);
		if (g.equals(getDiagonale(p))) {
			return true;
		}
		return false;
	}
	
	public boolean isCentrale(String g, int p) {
		g = remove0(g);
		if (g.equals(getNextOrario(p)) || g.equals(getDiagonale(p-1))) {
			return true;
		}
		return false;
	}
	
	private String remove0(String str) {
		if (str.charAt(0) == '0') {
			return str.substring(1);
		}
		return str;
	}
	
	private String getNextAntiorario(int zonaAttuale) {
		switch (zonaAttuale) {
		case 1: return this.z2;
		case 2: return this.z3;
		case 3: return this.z4;
		case 4: return this.z5;
		case 5: return this.z6;
		case 6: return this.z1;
		default: return this.z1;
		}
	}
	
	private String getNextOrario(int zonaAttuale) {
		switch (zonaAttuale) {
		case 1: return this.z6;
		case 2: return this.z1;
		case 3: return this.z2;
		case 4: return this.z3;
		case 5: return this.z4;
		case 6: return this.z5;
		default: return this.z1;
		}
	}
	
	private String getDiagonale(int zonaAttuale) {
		switch (zonaAttuale) {
		case 0: return this.z3;
		case 1: return this.z4;
		case 2: return this.z5;
		case 3: return this.z6;
		case 4: return this.z1;
		case 5: return this.z2;
		case 6: return this.z3;
		case 7: return this.z4;
		default: return this.z1;
		}
	}
	
	private String getByZona(int zona) {
		switch (zona) {
		case 1: return this.z1;
		case 2: return this.z2;
		case 3: return this.z3;
		case 4: return this.z4;
		case 5: return this.z5;
		case 6: return this.z6;
		default: return this.z1;
		}
	}

}
