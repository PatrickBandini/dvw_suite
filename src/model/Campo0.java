package model;

public class Campo0 {
	
	public static final String ALZATA = "ALZATA";
	public static final String SERVIZIO = "SERVIZIO";
	public static final String RICEZIONE = "RICEZIONE";
	public static final String ATTACCO = "ATTACCO";
	public static final String MURO = "MURO";
	public static final String DIFESA = "DIFESA";
	public static final String FREE = "FREE";
	
	private String stringa;
	private String main;
	private String advanced = "";
	private String extended = "";
	private String custom = "";
	
	//main
	private char team = ' ';
	private String numero = "";
	private char skill = ' ';
	private char type = ' ';
	private char val = ' ';
	
	//advanced
	private String comb = "";
	private char target = ' ';
	private char start = ' ';
	private char end = ' ';
	private char subEnd = ' ';
	
	
	//extende
	private char skillType = ' ';
	private char player = ' ';
	private char special = ' ';

	
	public Campo0(String s) {
		elaboraStringa(s);
	}
	
	public String getStringa() {
		String str = this.main;
		
		//se � una skill
		if (this.main.length() >= 6 && isSkill(this.skill)) {
			// 2 campi combinazione
			if ("".equals(this.comb)) {
				str += "~~";
			} else {
				str += this.comb;
			}
			
			//1 camppo target
			if (' ' == this.target) {
				str += '~';
			} else {
				str += this.target;
			}
			
			//1 campo start
			if (' ' == this.start) {
				str += '~';
			} else {
				str += this.start;
			}
			
			//1 campo end
			if (' ' == this.end) {
				str += '~';
			} else {
				str += this.end;
			}
			
			//1 campo sotto zona
			if (' ' == this.subEnd) {
				str += '~';
			} else {
				str += this.subEnd;
			}
			
			//1 campo skill type
			if (' ' == this.skillType) {
				str += '~';
			} else {
				str += this.skillType;
			}
			
			//1 campo player
			if (' ' == this.player) {
				str += '~';
			} else {
				str += this.player;
			}
			
			// 1 campo special
			if (' ' == this.special) {
				str += '~';
			} else {
				str += this.special;
			}
			
			//5 campi custom
			if (!"".equals(this.custom)) {
				str += this.custom;
			}
		} else {
			str += this.advanced;
			str += this.extended;
			str += this.custom;
		}
		
		
		
		return str;
	}
	
	public String getMain() {
		return this.main;
	}
	
	public String getAdvanced() {
		return this.advanced;
	}
	
	public String getExtended() {
		return this.extended;
	}
	
	public String getCustom() {
		return this.custom;
	}
	
	// MAIN
	
	public String getTeam() {
		return String.valueOf(this.main.charAt(0));
	}
	
	public String getNumero() {
		return this.main.substring(1,3);
	}
	
	public String getSkill() {
		return String.valueOf(this.main.charAt(3));
	}
	
	public String getType() {
		return String.valueOf(this.main.charAt(4));
	}
	
	public String getVal() {
		return String.valueOf(this.main.charAt(5));
	}
	
	public void setTeam(char c) {
		if (' ' == this.team || '~' == this.team) {
			this.team = c;
		}
	}
	
	public void setNumero(String s) {
		if ("".equals(this.numero) || "~~".equals(this.numero) || "$$".equals(this.numero)) {
			this.numero = s;
		}
	}
	
	public void setSkill(char c) {
		if (' ' == c || '~' == c) {
			this.skill = c;
		}
	}
	
	public void setType(char c) {
		if (' ' == c || '~' == c) {
			this.type = c;
		}
	}
	
	public void setVal(char c) {
		if (' ' == c || '~' == c) {
			this.val = c;
		}
	}
	
	// ADVANCED
	
	public String getCombination() {
		return this.comb;
	}
	
	public void setCombination(String combinazione) {
		if ("".equals(this.comb) || "~~".equals(this.comb)) this.comb = combinazione;
	}
	
	public void setTarget(char target) {
		if (this.target == ' ' || this.target == '~') {
			this.target = target;
			if ("".equals(this.comb)) this.comb = "~~";
		}
	}
	
	public void setStart(char start, boolean force) {
		if (force) {
			if ("".equals(this.comb)) this.comb = "~~";
			if (' ' == this.target) this.target = '~';
			this.start = start;
		} else {
			if (this.start == ' ' || this.start == '~') {
				if ("".equals(this.comb)) this.comb = "~~";
				if (' ' == this.target) this.target = '~';
				this.start = start;
			}
		}
	}
	
	public void setEnd(char end, boolean force) {
		if (force) {
			if ("".equals(this.comb)) this.comb = "~~";
			if (' ' == this.target) this.target = '~';
			if (' ' == this.start) this.start = '~';
			this.end = end;
		} else {
			if (this.end == ' ' || this.end == '~') {
				if ("".equals(this.comb)) this.comb = "~~";
				if (' ' == this.target) this.target = '~';
				if (' ' == this.start) this.start = '~';
				this.end = end;
			}
		}
	}
	
	public void setSubEnd(char subend, boolean force) {
		if (force) {
			if ("".equals(this.comb)) this.comb = "~~";
			if (' ' == this.target) this.target = '~';
			if (' ' == this.start) this.start = '~';
			if (' ' == this.end) this.end = '~';
			this.subEnd = subend;
		} else {
			if (this.subEnd == ' ' || this.subEnd == '~') {
				if ("".equals(this.comb)) this.comb = "~~";
				if (' ' == this.target) this.target = '~';
				if (' ' == this.start) this.start = '~';
				if (' ' == this.end) this.end = '~';
				this.subEnd = subend;
			}
		}
	}
	
	public char getTarget() {
		return this.target;
	}
	
	public char getStart() {
		return this.start;
	}
	
	public char getEnd() {
		return this.end;
	}
	
	public char getSubEnd() {
		return this.subEnd;
	}
	
	// EXTENDED
	
	public void setSkillType(char skillType) {
		if (this.skillType == ' ' || this.skillType == '~') {
			this.skillType = skillType;
			if ("".equals(this.comb)) this.comb = "~~";
			if (this.target == ' ') this.target = '~';
			if (this.start == ' ') start = '~';
			if (this.end == ' ') end = '~';
			if (this.subEnd == ' ') subEnd = '~';
		}
	}
	
	public char getSkillType() {
		return this.skillType;
	}
	
	public void setPlayer(char player) {
		if (this.player == ' ' || this.player == '~') {
			this.player = player;
			if ("".equals(this.comb)) this.comb = "~~";
			if (this.target == ' ') this.target = '~';
			if (this.start == ' ') start = '~';
			if (this.end == ' ') end = '~';
			if (this.subEnd == ' ') subEnd = '~';
			if (this.skillType == ' ') skillType = '~';
		}
	}
	
	public char getPlayer() {
		return this.player;
	}
	
	public void setSpecial(char special) {
		if (this.special == ' ' || this.special == '~') {
			this.special = special;
			if ("".equals(this.comb)) this.comb = "~~";
			if (this.target == ' ') this.target = '~';
			if (this.start == ' ') this.start = '~';
			if (this.end == ' ') this.end = '~';
			if (this.subEnd == ' ') this.subEnd = '~';
			if (this.skillType == ' ') this.skillType = '~';
			if (this.player == ' ') this.player = '~';
		}
	}
	
	public char getSpecial() {
		return this.special;
	}
	
	// -- METODI GENERICI --
	
	public String toString() {
		return getStringa();
	}
	
	public boolean isSkill(char c) {
		if (
			'S' == c ||
			'R' == c ||
			'E' == c ||
			'A' == c ||
			'B' == c ||
			'F' == c ||
			'D' == c
		) {
			return true;
		}
		return false;
	}
	
	public void setStringa(String s) {
		clearValues();
		elaboraStringa(s);
	}
	/**
	 * Da quale index iniziare a modificare, per quanti caratteri, e cosa sostituire
	 * @param index
	 * @param numeroCaratteri
	 */
	public void updateCustom(int index, int numeroCaratteri, String str) {
		if ("".equals(custom)) {
			custom = "~~~~~";
		}else if (custom.length()<5) {
			for (int i=custom.length();i<5;i++) {
				custom += "~";
			}
		}
		
		char[] chars = custom.toCharArray();
		for (int i=0;i<numeroCaratteri; i++) {
			chars[index+i] = str.charAt(i);
		}
		this.custom = String.valueOf(chars);
	}
	
	public void updateCustom(int index, int numeroCaratteri, char c) {
		this.updateCustom(index, numeroCaratteri, String.valueOf(c));
	}
	
	public boolean isServizio() {
		return this.skill == 'S';
	}
	
	public boolean isAlzata() {
		return this.skill == 'E';
	}
	
	public boolean isRicezione() {
		return this.skill == 'R';
	}
	
	public boolean isAttacco() {
		return this.skill == 'A';
	}
	
	public boolean isMuro() {
		return this.skill == 'B';
	}
	
	public boolean isDifesa() {
		return this.skill == 'D';
	}
	
	public boolean isFree() {
		return this.skill == 'F';
	}
	
	public boolean isPunto() {
		String s = stringa.substring(0,2);
		if ("*p".equals(s) || "ap".equals(s)) {
			return true;
		}
		return false;
	}
	
	public String getPunteggio() {
		return this.stringa.substring(2, 7);
	}
	
	public boolean isTempo() {
		String s = stringa.substring(0,2);
		if ("*T".equals(s) || "aT".equals(s)) {
			return true;
		}
		return false;
	}
	
	public boolean isCambio() {
		String s = stringa.substring(0,2);
		if ("*c".equals(s) || "ac".equals(s)) {
			return true;
		}
		return false;
	}
	
	public void pulisciCustom() {
		this.custom = "~~~~~";
	}
	
	private void elaboraStringa(String s) {
		this.stringa = s;
		if(s.length() > 6) {
			this.main = s.substring(0,6);
			if (s.length() > 12) {
				this.advanced = s.substring(6,12);
				if (s.length()>15) {
					this.extended = s.substring(12, 15);
					this.custom = s.substring(15);
				} else {
					this.extended = s.substring(12);
				}
			} else {
				this.advanced = s.substring(6);
			}
		} else {
			this.main = s;
		}
		if (main.length() >= 6) {
			this.team = main.charAt(0);
			this.numero = main.substring(1,3);
			this.skill = main.charAt(3);
			this.type = main.charAt(4);
			this.val = main.charAt(5);
			
			if (!"".equals(advanced) && isSkill(main.charAt(3))) {
				this.comb = advanced.substring(0,2);
				if (advanced.length()>=3) {
					this.target = advanced.charAt(2);
				}
				if (advanced.length()>=4) {
					this.start = advanced.charAt(3);
				}
				if (advanced.length()>=5) {
					this.end = advanced.charAt(4);
				}
				if (advanced.length()>=6) {
					this.subEnd = advanced.charAt(5);
				}
			}
			
			if (!"".equals(extended) && isSkill(main.charAt(3))) {
				this.skillType = extended.charAt(0);
				if (extended.length()>=2) {
					this.player = extended.charAt(1);
				}
				if (extended.length()>=3) {
					this.special = extended.charAt(2);
				}
			}
		}
	}
	
	private void clearValues() {
		this.main = "";
		this.advanced = "";
		this.extended = "";
		this.custom = "";
		this.team = ' ';
		this.numero = "";
		this.skill = ' ';
		this.type = ' ';
		this.val =  ' ';
		this.comb = "";
		this.target = ' ';
		this.start = ' ';
		this.end = ' ';
		this.subEnd = ' ';
		this.skillType = ' ';
		this.player = ' ';
		this.special = ' ';
	}
}
