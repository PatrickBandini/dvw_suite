package model;

public class Campo0 {
	private String stringa;
	private String main;
	private String advanced;
	private String extended;
	private String custom;
	
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
		}
		
		
	}
	
	public String getStringa() {
		String str = this.main;
		//stampare bene con eventuali modifiche
		return this.stringa;
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
	
	// ADVANCED
	
	// EXTENDED
	
	public void setSkillType(char skillType) {
		this.skillType = skillType;
		this.comb = "~~";
		this.target = '~';
		if (this.start == ' ') start = '~';
		if (this.end == ' ') end = '~';
		if (this.subEnd == ' ') subEnd = '~';
	}
	
	public char getSkillType() {
		return this.skillType;
	}
	
	public String toString() {
		return getStringa();
	}
	
	
	public void setStringa(String s) {
		this.stringa = s;
	}
	
	public boolean isAlzata() {
		return this.skill == 'E';
	}
	
	public boolean isRicezione() {
		return this.skill == 'R';
	}
}
