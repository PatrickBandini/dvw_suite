package model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DVW {
	
	
	private List<String> intestazione;
	private List<Riga> righe;
	private int indexFineIntestazione;
	
	public DVW() {
		this.indexFineIntestazione=100000;
	}

	public List<String> getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(List<String> intestazione, int index) {
		this.intestazione = intestazione;
		this.indexFineIntestazione = index;
	}
	
	public void setRighe(List<Riga> righe) {
		this.righe = righe;
	}
	
	public String printRighe() {
		String s="";
		for(Riga r: this.righe) {
			s+=r.toString()+"\n";
		}
		return s;
	}
	
	public String printIntestazione() {
		String s="";
		for(String r: this.intestazione) {
			s+=r+"\n";
		}
		return s;
	}
	
	public int getIndexFineIntestazione() {
		return this.indexFineIntestazione;
	}
	
	public Riga getNext(List<Riga> list, Riga uid) {
	    int idx = list.indexOf(uid);
	    if (idx < 0 || idx+1 == list.size()) return null;
	    return list.get(idx + 1);
	}

	public Riga getPrevious(List<Riga> list, Riga uid) {
	    int idx = list.indexOf(uid);
	    if (idx <= 0) return null;
	    return list.get(idx - 1);
	}
	
	public Riga getPunto(List<Riga> list, Riga uid) {
		Riga prev = getPrevious(list, uid);
		while(!prev.getCampo0().isPunto()) {
			prev = getPrevious(list, prev);
			if(prev == null) break;
			if(prev.getCampo0().toString().contains("**")) return null;
		}
		return prev;
	}
	
	
	//QUERY
	
	/**
	 * Leggerà in input un file contentente: Set;puntoCasa-puntoOspite;Velocita; per ogni riga
	 * ES:
	 * 1;00-00;85;
	 * 1;00-01;125;
	 * 1;01-01;115;
	 */
	public void inserisciVelocitaServizio(List<Velocita> velocita) {
		if (velocita.size()>0) {
			for (Riga r: this.righe) {
				if (r.getCampo0().isServizio()) {
					Riga punto = getPunto(righe, r);
					if (null == punto) {
						punto = r;
					}
					r.getCampo0().updateCustom(4, 1, findVelocita(velocita, punto));
				}
			}
		}
	}
	
	public void trasformaVelocitaServizio() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isServizio()) {
				Riga punto = getPunto(righe, r);
				if (null == punto) {
					punto = r;
				}
				Integer vel = -1;
				try {
					String strvel = r.getCampo0().getCustom().substring(0, 3);
					vel = Integer.parseInt(strvel);
				} catch (Exception ex) {
					try {
						String strvel = r.getCampo0().getCustom().substring(0, 2);
						vel = Integer.parseInt(strvel);
					} catch(Exception ex2) {
						
					}
				}
				System.out.println(vel);
				if (vel != -1) {
					r.getCampo0().updateCustom(4, 1, rangeVelocita(vel));
				}
			}
		}
	}
	
	private char findVelocita(List<Velocita> lista, Riga punto) {
		String punteggio;
		char set;
		if (null != punto && !punto.getCampo0().isServizio()) {
			punteggio = punto.getCampo0().getPunteggio();
			set = punto.getSet().charAt(0);
		} else if (null != punto && punto.getCampo0().isServizio()) {
			punteggio = "00:00";
			set = punto.getSet().charAt(0);
		} else {
			punteggio = "00:00";
			set = '0';
		}
		for (Velocita v:lista) {
			if (v.getSet()==set && punteggio.equals(v.getPunteggio())) {
				Integer vel = Integer.parseInt(v.getVelocita());
				rangeVelocita(vel);
			}
		}
		return '~';
	}
	
	private char rangeVelocita(Integer vel) {
		if (isBetween(vel, 40, 43)) return 'A';
		if (isBetween(vel, 44, 47)) return 'B';
		if (isBetween(vel, 48, 51)) return 'C';
		if (isBetween(vel, 52, 55)) return 'D';
		if (isBetween(vel, 56, 59)) return 'E';
		if (isBetween(vel, 60, 63)) return 'F';
		if (isBetween(vel, 64, 67)) return 'G';
		if (isBetween(vel, 68, 71)) return 'H';
		if (isBetween(vel, 72, 75)) return 'I';
		if (isBetween(vel, 76, 79)) return 'J';
		if (isBetween(vel, 80, 83)) return 'K';
		if (isBetween(vel, 84, 87)) return 'L';
		if (isBetween(vel, 88, 91)) return 'M';
		if (isBetween(vel, 92, 95)) return 'N';
		if (isBetween(vel, 96, 99)) return 'O';
		if (isBetween(vel, 100, 103)) return 'P';
		if (isBetween(vel, 104, 107)) return 'Q';
		if (isBetween(vel, 108, 111)) return 'R';
		if (isBetween(vel, 112, 115)) return 'S';
		if (isBetween(vel, 116, 119)) return 'T';
		if (isBetween(vel, 120, 123)) return 'U';
		if (isBetween(vel, 124, 127)) return 'V';
		if (isBetween(vel, 128, 131)) return 'W';
		if (isBetween(vel, 132, 135)) return 'X';
		if (isBetween(vel, 136, 139)) return 'Y';
		if (isBetween(vel, 140, 143)) return 'Z';
		return '0';
	}
	
	private boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
	}
	
	public void spostaTimecodeFondamentale(char fondamentale, int secondi) {
		
	}
	
	public void rimuoviFrecce() {
		for (Riga r: this.righe) {
			char c = ' ';
			if (r.getCampo0().getMain().length() >=4) {
				c = r.getCampo0().getSkill().charAt(0);
			}
			if (c != ' ' && r.getCampo0().isSkill(c)) {
				String val = r.getCampo0().getVal();
				boolean murato = c == 'A' && (val.equals("/") || val.equals("!"));
				if (!murato) {
					r.setFrecciaA("");
					r.setFrecciaB("");
					r.setFrecciaC("");
				}
			}
		}
	}
	
	public void convertiZoneInFrecce() {
		
	}
	
	/**
	 * 4 secondi palla alta e servizio/ace
	 * 3 secondi default
	 */
	public void normalizzaTempiFineAzione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isPunto()) {
				Riga prec = getPrevious(righe, r);
				Riga p = prec;
				while (
						!prec.getCampo0().isAttacco() && 
						!prec.getCampo0().isRicezione() && 
						!prec.getCampo0().isServizio() && 
						!prec.getCampo0().isPunto() && 
						!prec.getCampo0().getStringa().contains("**")) {
					if (null == prec) break;
					prec = getPrevious(righe, prec);
				}
				while (!p.getCampo0().isSkill(p.getCampo0().getSkill().charAt(0))) {
					p = getPrevious(righe, p);
				}
				Integer tempo = Integer.valueOf(p.getTimecode());
				if (null!=prec) {
					if (
							prec.getCampo0().getType().equals("H") ||
							prec.getCampo0().isRicezione() || 
							prec.isAlzataCP(getPrevious(righe, prec))
						) {
						tempo += 5;
						r.setTimecode(String.valueOf(tempo));
					} else {
						tempo+=4;
						r.setTimecode(String.valueOf(tempo));
					}
					
				} else {
					tempo+=4;
					r.setTimecode(String.valueOf(tempo));
				}
				
				
			}
		}
	}
	
	public boolean verificaCongruenzaCodiciComposti() {
		return true;
	}
	
	/**
	 * SOLO se disponibile un primo tempo K1,2,7
	 * 2° custom alzata: ripetizione azione corrente 1..9 (alzate consecutive)
	 * 3° custom alzata: ripetizione azione precedente B E P 1..9 (prima alzata questa azione / ultima alzata azione precedente)
	 * 4° custom alzata: ripetizione fase (P) precedente 1..9 (SOLO CP)
	 */
	public void inserisciRipetizioniAlzatore() {
		
	}
	
	/**
	 * 1° custom alzata
	 */
	public void inserisciPuntiRete() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAlzata()) {
				String comb = r.getCampo0().getCombination();
				char end = r.getCampo0().getEnd();
				if (r.isAlzataCP(getPrevious(righe, r)) || "K1".equals(comb) || "K2".equals(comb) || "K7".equals(comb) || end == '4' || end == '3' || end == '2') {
					Campo0 primoTocco = getPrevious(righe, r).getCampo0();
					if (primoTocco.isRicezione() || primoTocco.isFree()) {
						Campo0 alzata = r.getCampo0();
						switch(end) {
						case '2':
							if (primoTocco.getVal().equals("#")) {
								alzata.updateCustom(0, 1, "1");
							} else if (primoTocco.getVal().equals("+")) {
								alzata.updateCustom(0, 1, "5");
							} else if (primoTocco.getVal().equals("!")) {
								alzata.updateCustom(0, 1, "A");
							} else {
								alzata.updateCustom(0, 1, "Y");
							}
							break;
						case '3':
							if (primoTocco.getVal().equals("#")) {
								alzata.updateCustom(0, 1, "2");
							} else if (primoTocco.getVal().equals("+")) {
								alzata.updateCustom(0, 1, "6");
							} else if (primoTocco.getVal().equals("!")) {
								alzata.updateCustom(0, 1, "B");
							} else {
								alzata.updateCustom(0, 1, "7");
							}
							break;
						case '4':
							if (primoTocco.getVal().equals("#")) {
								alzata.updateCustom(0, 1, "3");
							} else if (primoTocco.getVal().equals("+")) {
								alzata.updateCustom(0, 1, "4");
							} else if (primoTocco.getVal().equals("!")) {
								alzata.updateCustom(0, 1, "D");
							} else {
								alzata.updateCustom(0, 1, "X");
							}
							break;
						default:
							if (primoTocco.getVal().equals("#")) {
								alzata.updateCustom(0, 1, "2");
							} else if (primoTocco.getVal().equals("+")) {
								alzata.updateCustom(0, 1, "6");
							} else if (primoTocco.getVal().equals("!")) {
								alzata.updateCustom(0, 1, "B");
							} else {
								alzata.updateCustom(0, 1, "0");
							}
							break;
						}
					}
					
				}
			}
		}
	}
	
	/**
	 * Sull'attacco inserisce una lettera abbinata ad ogni giocatore in ordine (per zona) 4-3-2 nel 2°,3° e 4° spazio custom dell'attacco
	 * Leggerà in input 2 file per la corrispondenza numero-lettera, 1° squadra di casa, 2° squadra ospite
	 * Il file sarà così composto: NumeroGiocatore;LetteraGiocatore;Ruolo;Nome e Cognome
	 */
	public void inserisciCustomGiocatoriAMuro(List<NumeroLettera> casa, List<NumeroLettera> ospite) {
		if (casa.size() > 0 && ospite.size() > 0) {
			for (Riga r: this.righe) {
				if (r.getCampo0().isAttacco()) {
					char team = r.getCampo0().getTeam().charAt(0);
					if (team == '*') {
						r.getCampo0().updateCustom(1, 1, findLettera(ospite, r.getOspite().getZ4()));
						r.getCampo0().updateCustom(2, 1, findLettera(ospite, r.getOspite().getZ3()));
						r.getCampo0().updateCustom(3, 1, findLettera(ospite, r.getOspite().getZ2()));
					} else {
						r.getCampo0().updateCustom(1, 1, findLettera(casa, r.getCasa().getZ4()));
						r.getCampo0().updateCustom(2, 1, findLettera(casa, r.getCasa().getZ3()));
						r.getCampo0().updateCustom(3, 1, findLettera(casa, r.getCasa().getZ2()));
					}
				}
			}
		}
		
	}
	
	private char findLettera(List<NumeroLettera> lista, String numero) {
		for (NumeroLettera nl:lista) {
			if (nl.isStessoNumero(numero)) return nl.getLettera();
		}
		return '~';
	}
	
	public void inserisciEstensioneErroreServizio() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isServizio()) {
				Campo0 servizio = r.getCampo0();
				if (servizio.getVal().equals("=")) {
					if (servizio.getSpecial() == ' ' || servizio.getSpecial() == '~') {
						servizio.setSpecial('O');
						switch (servizio.getEnd()) {
						case '1':
						case '9':
							if (servizio.getSubEnd() == 'A' || servizio.getSubEnd() == 'B') {
								servizio.setSpecial('R');
							}
							break;
						case '5':
						case '7':
							if (servizio.getSubEnd() == 'D' || servizio.getSubEnd() == 'C') {
								servizio.setSpecial('L');
							}
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * copia senza sovrascrivere il numero di persone a muro dall'attacco
	 */
	public void copiaPersoneAMuro() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isMuro()) {
				Campo0 attacco = getPrevious(righe, r).getCampo0();
				if (r.getCampo0().getPlayer() == ' ' || r.getCampo0().getPlayer() == '~') {
					r.getCampo0().setPlayer(attacco.getPlayer());
				}
			}
		}
	}
	
	/**
	 * sovrascrive direzione del servizio
	 */
	public void completaDirezioneServizio() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				Campo0 ricezione = r.getCampo0();
				Campo0 servizio = getPrevious(righe, r).getCampo0();
				if (servizio.getStart() != ricezione.getStart()) {
					servizio.setStart(ricezione.getStart(), true);
				}
				if (servizio.getEnd() != ricezione.getEnd()) {
					servizio.setEnd(ricezione.getEnd(), true);
				}
				if (servizio.getSubEnd() != ricezione.getSubEnd()) {
					servizio.setSubEnd(ricezione.getSubEnd(), true);
				}
 			}
		}
	}
	
	/**
	 * copia direzione servizio sulla ricezione
	 */
	public void copiaDirezioneSuRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				Campo0 ricezione = r.getCampo0();
				Campo0 servizio = getPrevious(righe, r).getCampo0();
				if (servizio.getStart() != ricezione.getStart()) {
					ricezione.setStart(servizio.getStart(), true);
				}
				if (servizio.getEnd() != ricezione.getEnd()) {
					ricezione.setEnd(servizio.getEnd(), true);
				}
				if (servizio.getSubEnd() != ricezione.getSubEnd()) {
					ricezione.setSubEnd(servizio.getSubEnd(), true);
				}
 			}
		}
	}
	
	public void inserisciDifese() {
		for (int i=0;i<this.righe.size();i++) {
			Riga r = this.righe.get(i);
			if (r.getCampo0().isAttacco()) {
				if (!r.getCampo0().getVal().equals("#")) {
					Riga attacco = r;
					Riga muro = null;
					String teamAttacco = r.getCampo0().getTeam();
					Riga nextR = getNext(righe, r);
					Campo0 next = nextR.getCampo0();
					if (next.isMuro()) {
						muro = nextR;
						nextR = getNext(righe, nextR);
						next = nextR.getCampo0();
						i++;
					}
					if (
							next.isAlzata() || 
							next.isAttacco() || 
							next.isFree() || 
							(
									"#".equals(attacco.getCampo0().getVal()) && 
									muro != null && 
									!"=".equals(muro.getCampo0().getVal()) && 
									!"/".equals(muro.getCampo0().getVal())
							) || 
							(
									"#".equals(attacco.getCampo0().getVal()) && 
									muro == null
							) || 
							next.isDifesa()
					) {
						
						Riga difesa;
						boolean modifica = false;
						char team;
						String numero;
						char skill = 'D';
						char type;
						char val;
						String advanced = "~~~";
						String extended="";
						
						if (!next.isDifesa()) {
							//inserisco difesa
							difesa = new Riga(attacco.toString());
							
							//TEAM
							if (attacco.getCampo0().getVal().equals("!")) {
								team = attacco.getCampo0().getTeam().charAt(0);
							} else {
								if ("*".equals(teamAttacco)) {
									team = 'a';
								} else {
									team = '*';
								}
								if (attacco.getCampo0().getVal().equals("-") && null!=muro && "+".equals(muro.getCampo0().getVal())) {
									Riga n2 = getNext(righe, muro);
									if (n2.getCampo0().isFree() || (n2.getCampo0().isAttacco() && "PR".equals(n2.getCampo0().getCombination()))) {
										team = attacco.getCampo0().getTeam().charAt(0);
									}
								}
							}
							
							//NUMERO
							numero = "$$";
							
							difesa.setPuntoCambioPalla("");
							difesa.setAttaccoDopoRicezioneDifesa("");
							
						} else {
							//Modifico Difesa
							modifica = true;
							difesa = nextR;
							team = difesa.getCampo0().getTeam().charAt(0);
							numero = difesa.getCampo0().getNumero();
							
							nextR = getNext(righe, difesa);
							next = nextR.getCampo0();
						}
						
						type = attacco.getCampo0().getType().charAt(0);
						
						//VALORE
						val = '+';
						if (attacco.getCampo0().getVal().equals("#")) {
							val = '=';
						} else if (next.isAlzata()) {
							if ("H".equals(next.getType())) {
								val = '-';
							}
							if ("K1".equals(next.getCombination()) || "K2".equals(next.getCombination()) || "K7".equals(next.getCombination())) {
								val = '#';
							}
							if (attacco.getCampo0().getVal().equals("!")) {
								val = '!';
							}
						} else if (next.isFree() || (next.isAttacco() && "PR".equals(next.getCombination()))) {
							val = '/';
						}
						
						
						//DIREZIONE
						if (attacco.getCampo0().getStart() != ' ') {
							advanced += attacco.getCampo0().getStart();
							if (attacco.getCampo0().getEnd() != ' ') {
								advanced += attacco.getCampo0().getEnd();
							} else {
								advanced += "~";
							}
							if (attacco.getCampo0().getSubEnd() != ' ') {
								advanced += attacco.getCampo0().getSubEnd();
							} else {
								advanced += "~";
							}
						}
						
						//EXTENDED
						while (advanced.length()<6) {
							advanced += "~";
						}
						extended = "S";
						if (attacco.getCampo0().getSkillType() == 'T') {
							extended = "E";
						} else if (null != muro) {
							if (muro.getCampo0().getVal().equals("!") || (muro.getCampo0().getVal().equals("+") && !next.isAlzata())) {
								extended = "C";
							} else {
								extended = "B";
							}
						}
						
						
						difesa.setCampo0(team + numero + skill + type + val + advanced + extended);
						
						if (!modifica) {
							i++;
							this.righe.add(i, difesa);
						}
					}
				}
			}
		}
	}
	
	public void inserisciMuroOpzioneLettura() {
		for (Riga r:this.righe) {
			if (r.getCampo0().isMuro()) {
				Riga attacco = getPrevious(righe, r);
				if (attacco.getCampo0().isAttacco()) {
					Campo0 a = attacco.getCampo0();
					if (a.getTarget() == 'C' && !a.getCombination().equals("V3")) {
						if (r.getCampo0().getVal().equals("#") || "!".equals(r.getCampo0().getVal())) {
							r.getCampo0().setSkillType('T');
						} else {
							if (!r.getCampo0().getVal().equals("=")) {
								r.getCampo0().setSkillType('A');
							}
						}
					}
				}
			}
		}
	}
	
	public void inserisciBasi() {
		int i =0;
		for (Riga r: this.righe) {
			if (r.getCampo0().isAlzata()) {
				Campo0 c = r.getCampo0();
				Riga attacco = getNext(righe, r);
				System.out.println(c.toString());
				if ("".equals(c.getCombination()) || "~~".equals(c.getCombination())) {
					//Base
					if (c.getType().equals("H")) {
						c.setCombination("KE");
					} else if (attacco.getCampo0().isAttacco()) {
						String comb = attacco.getCampo0().getCombination();
						if (comb.equals("X2") || comb.equals("C2") || comb.equals("Z2")) {
							c.setCombination("K2");
						} else if (comb.equals("X7") || comb.equals("XD") || comb.equals("XP")) {
							c.setCombination("K7");
						} else {
							c.setCombination("K1");
						}
					} else {
						c.setCombination("K1");
					}
					System.out.println(c.toString());
				}
				i++;
				System.out.println(""+ i);
				
				if (c.getTarget() == ' ' || c.getTarget() == '~') {
					//Distribuzione
					if (attacco.getCampo0().isAttacco()) {
						switch(attacco.getCampo0().getCombination()) {
						case "X1":
						case "X2":
						case "XC":
						case "XE":
						case "XD":
						case "X7":
						case "V3":
						case "C1":
						case "C2":
						case "Z1":
						case "Z2":
						case "CF":
						case "CB":
						case "XS":
						case "XG":
							c.setTarget('C');
							break;
						case "X5":
						case "X9":
						case "C5":
						case "V5":
						case "VE":
						case "XT":
							c.setTarget('F');
							break;
						case "X6":
						case "X8":
						case "C6":
						case "C8":
						case "X4":
						case "V6":
						case "V8":
						case "VD":
						case "GA":
						case "XX":
							c.setTarget('B');
							break;
						case "XP":
						case "XR":
						case "XB":
						case "VP":
							c.setTarget('P');
							break;
						case "PP":
							c.setTarget('S');
						}
					}
				}
			}
		}
	}
	
	public void attaccoDopoRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAttacco()) {
				Riga alzata = getPrevious(righe, r);
				Riga ricezione = getPrevious(righe, alzata);
				if (alzata.isAlzataCP(ricezione)) {
					if(ricezione.getCampo0().getNumero().equals(r.getCampo0().getNumero())) {
						r.getCampo0().updateCustom(0, 1, "R");
					}
				}
			}
		}
	}
	
	public void inserisciToccoAMuro() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAttacco() && getNext(righe, r).getCampo0().isMuro()) {
				r.getCampo0().setSpecial('C');
			}
		}
	}
	
	public void numeroPersoneAMuro() {
		for (Riga r: this.righe) {
			Campo0 c = r.getCampo0();
			if (c.isAttacco()) {
				String t = c.getType();
				if ("T".equals(t) || "X7".equals(c.getCombination())) {
					c.setPlayer('4');
				} else if ("Q".equals(t) || "M".equals(t)) {
					c.setPlayer('1');
				} else if ("H".equals(t)) {
					c.setPlayer('3');
				} else {
					c.setPlayer('2');
				}
			}
		}
		System.out.println("OK - Numero persone a muro sull'attacco");
	}
	
	public void pulisciCustom(String fondamentale) {
		for (Riga r: this.righe) {
			Campo0 c = r.getCampo0();
			switch (fondamentale) {
			case Campo0.SERVIZIO: 
				if (c.isServizio()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.RICEZIONE:
				if (c.isRicezione()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.ALZATA:
				if (c.isAlzata()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.ATTACCO:
				if (c.isAttacco()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.MURO:
				if (c.isMuro()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.DIFESA:
				if (c.isDifesa()) {
					c.pulisciCustom();
				}
				break;
			case Campo0.FREE:
				if (c.isFree()) {
					c.pulisciCustom();
				}
				break;
			}
		}
	}
	
	public void tempiAlzata() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAlzata()) {
				Riga prev = getPrevious(righe, r);
				if (r.isAlzataCP(prev)) {
					r.setTimecode(prev.getTimecode());
				} else {
					Riga next = getNext(righe, r);
					if (next.getCampo0().isAttacco()) {
						r.setTimecode(next.getTimecode());
					}
				}
			}
			
		}
		System.out.println("OK - Tempi Alzata");
	}
	
	public void tempiRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				r.setTimecode(getPrevious(righe, r).getTimecode());
			}
		}
		System.out.println("OK - Tempi Ricezione");
	}
	
	public void tempiMuro() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isMuro()) {
				r.setTimecode(getPrevious(righe, r).getTimecode());
			}
		}
		System.out.println("OK - Tempi Muro");
	}
	
	public void tempiAttaccoContrattacco() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAttacco()) {
				Riga prev = getPrevious(righe, r);
				if (prev.getCampo0().isAlzata() && !prev.isAlzataCP(getPrevious(righe, prev))) {
					r.setTimecode(prev.getTimecode());
				}
			}
		}
		System.out.println("OK - Tempi Attacco Contrattacco");
	}
	
	public void tempiAttaccoCP() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAttacco()) {
				Riga prev = getPrevious(righe, r);
				if (prev.isAlzataCP(getPrevious(righe, prev))) {
					r.setTimecode(prev.getTimecode());
				}
			}
		}
		System.out.println("OK - Tempi Attacco CP");
	}
	
	public void tempiDifesa() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isDifesa()) {
				Riga prev = getPrevious(righe, r);
				if (prev.getCampo0().isAttacco() || prev.getCampo0().isMuro()) {
					r.setTimecode(prev.getTimecode());
				}
			}
		}
		System.out.println("OK - Tempi Difesa");
	}
	
	public void aggiungiLatoRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				Campo0 campo0 = r.getCampo0();
				char type = campo0.getType().charAt(0);
				if (campo0.getSkillType()==' ' || campo0.getSkillType()=='~') {
					switch (type) {
					case 'Q': 
						campo0.setSkillType('M');
						break;
					case 'M': 
						String numero = campo0.getNumero();
						if ("*".equals(campo0.getTeam())) {
							if (r.getCasa().isPrimaLinea(numero)) {
								campo0.setSkillType('O');
							} else {
								campo0.setSkillType('M');
							}
						} else {
							if (r.getOspite().isPrimaLinea(numero)) {
								campo0.setSkillType('O');
							} else {
								campo0.setSkillType('M');
							}
						}
						break;
					case 'T':
						campo0.setSkillType('W');
						break;
					default:
						campo0.setSkillType('M');
						break;
					}
				}
				
			}
		}
		System.out.println("OK - Lato Ricezione");
	}
	
	public void aggiungiLatoRicettore() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				Campo0 campo0 = r.getCampo0();
				if (campo0.getPlayer()==' ' || campo0.getPlayer()=='~') {
					Formazione squadra = r.getCasa();
					String p = r.getpCasa();
					if(campo0.getTeam().equals("a")) {
						squadra = r.getOspite();
						p = r.getpOspite();
					}
					String giocatore = campo0.getNumero();
					
					if (!squadra.isInCampo(giocatore)) {
						//LIBERO
						if ("1".equals(p) || "6".equals(p) || "3".equals(p)) {
							campo0.setPlayer('4');
						} else {
							campo0.setPlayer('5');
						}
					} else {
						//SCHIACCIATORE
						if (squadra.isPrimaLinea(giocatore)) {
							if ("1".equals(p)) {
								campo0.setPlayer('5');
							} else {
								campo0.setPlayer('3');
							}
						} else {
							if ("1".equals(p)) {
								campo0.setPlayer('3');
							} else if ("6".equals(p) || "3".equals(p)){
								campo0.setPlayer('5');
							} else {
								campo0.setPlayer('4');
							}
						}
					}
				}
			}
		}
		System.out.println("OK - Lato Ricettore");
	}
	
	/**
	 * modifica il 1° custom del servizio
	 */
	public void conteggioBattute() {
		List<Riga> battute = new ArrayList<Riga>();
		for (Riga r: this.righe) {
			if (r.getCampo0().isServizio()) {
				battute.add(r);
			}
		}
		for (Riga r: battute) {
			Riga p = getPrevious(battute, r);
			Integer cont;
			if (p != null) {
				Campo0 prev = p.getCampo0();
				if (r.getCampo0().getNumero().equals(prev.getNumero()) && r.getCampo0().getTeam().equals(prev.getTeam())) {
					cont = Integer.valueOf(prev.getCustom().substring(0, 1));
					cont++;
				} else {
					cont = 1;
				}
			} else {
				cont = 1;
			}
			r.getCampo0().updateCustom(0, 1, String.valueOf(cont));
		}
		System.out.println("OK - conteggio battute");
	}
	
	/**
	 * modifica il 2° e 3° custom del servizio
	 */
	public void differenzaPunteggio() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isServizio()) {
				Riga punto = getPunto(righe, r);
				if (null == punto) {
					r.getCampo0().updateCustom(1, 2, "00");
				} else {
					String team = punto.getCampo0().getTeam();
					String str = punto.getCampo0().getStringa();
					Integer casa = Integer.valueOf(str.substring(2,4));
					Integer ospite = Integer.valueOf(str.substring(5,7));
					Integer differenza = 0;
					if (team.equals("*")) {
						differenza = casa-ospite;
					} else if (team.equals("a")) {
						differenza = ospite-casa;
					}
					if (differenza == 0) {
						r.getCampo0().updateCustom(1, 2, "00");
					} else if (differenza > 0 && differenza <= 9) {
						r.getCampo0().updateCustom(1, 2, "+" + String.valueOf(differenza));
					} else if (differenza > 9) {
						r.getCampo0().updateCustom(1, 2, "+9");
					} else if (differenza < -9) {
						r.getCampo0().updateCustom(1, 2, "-9");
					} else {
						r.getCampo0().updateCustom(1, 2, String.valueOf(differenza));
					}
				}
			}
		}
		System.out.println("OK - differenza punteggio");
	}
	
	/**
	 * modifica il 4° custom del servizio
	 */
	public void servizioDopoInterruzione() {
		//Servizio dopo errore precedente
		List<Riga> battute = new ArrayList<Riga>();
		for (Riga r: this.righe) {
			if (r.getCampo0().isServizio()) {
				battute.add(r);
			}
		}
		for (Riga r: battute) {
			Riga p = null;
			do {
				if (null != p) {
					p = getPrevious(battute, p);
				} else {
					p = getPrevious(battute, r);
				}
				if (null==p) {
					break;
				}
			} while (!p.getCampo0().getTeam().equals(r.getCampo0().getTeam()));
			if (p != null) {
				if (p.getCampo0().getVal().equals("=") && p.getCampo0().getTeam().equals(r.getCampo0().getTeam())) {
					r.getCampo0().updateCustom(3, 1, "E");
				}
				
			} 
			
		}
		
		//Servizio dopo interruzione
		for (Riga r: this.righe) {
			Riga servizio;
			if (r.getCampo0().isTempo()) {
				servizio = getNext(righe, r);
				while(!servizio.getCampo0().isServizio()) {
					servizio = getNext(righe, servizio);
				}
				servizio.getCampo0().updateCustom(3, 1, "T");
			} else if (r.getCampo0().isCambio()) {
				servizio = getNext(righe, r);
				while(!servizio.getCampo0().isServizio()) {
					servizio = getNext(righe, servizio);
				}
				servizio.getCampo0().updateCustom(3, 1, "C");
			}
		}
		System.out.println("OK - servizio dopo interruzione");
	}
	
	/**
	 * imposta P-C-O nel 1° custom della ricezione
	 */
	public void ricezioneEstranei() {
		for (Riga r: this.righe) {
			Campo0 c = r.getCampo0();
			if (c.isRicezione()) {
				Formazione f = r.getCasa();;
				int p = Integer.valueOf(r.getpCasa());
				if (c.getTeam().equals("a")) {
					f = r.getOspite();
					p = Integer.valueOf(r.getpOspite());
				}
				String numero = c.getNumero();
				if (f.isCentrale(numero, p)) {
					c.updateCustom(0, 1, "C");
					c.setPlayer('~');
				} else if (f.isOpposto(numero, p)) {
					c.updateCustom(0, 1, "O");
					c.setPlayer('~');
				} else if (f.isPalleggiatore(numero, p)) {
					c.updateCustom(0, 1, "P");
					c.setPlayer('~');
				}
			}
		}
		System.out.println("OK - ricezione estranei");
	}
	
	/**
	 * R/ --> B/
	 * R- --> B+
	 * R! --> B!
	 * R+ --> B-
	 * R# --> B-
	 */
	public void normalizzaBattutaRicezione() {
		for (Riga r: this.righe) {
			Campo0 c = r.getCampo0();
			if (c.isRicezione()) {
				Riga battuta = getPrevious(this.righe, r);
				Campo0 b = battuta.getCampo0();
				char val = c.getVal().charAt(0);
				switch(val) {
				case '/':
					b.setVal('/');
					break;
				case '-':
					b.setVal('+');
					break;
				case '!':
					b.setVal('!');
					break;
				case '+':
					b.setVal('-');
					break;
				case '#':
					b.setVal('-');
					break;
				}
			}
		}
		System.out.println("OK - normalizza Battuta-Ricezione");
	}
	
	
	public void inserisciCombinazioniAttacco() {
		for (Riga r: this.righe) {
			Campo0 c = r.getCampo0();
			if (c.isAttacco()) {
				//se palleggiatore --> PP
				Formazione f = r.getCasa();;
				int p = Integer.valueOf(r.getpCasa());
				if (c.getTeam().equals("a")) {
					f = r.getOspite();
					p = Integer.valueOf(r.getpOspite());
				}
				String numero = c.getNumero();
				if (f.isPalleggiatore(numero, p)) {
					c.setCombination("PP");
				} else {
					//se la provenienza � da zona 3
					if (c.getStart() == '3') {
						c.setCombination("X1");
					} else {
						//se � attacco di cambio palla dopo R#,+,!
						Riga ricezione = getPrevious(this.righe, r);
						Campo0 rice0 = ricezione.getCampo0();
						boolean positiva = false;
						if (rice0.isRicezione()) {
							positiva = "#".equals(rice0.getVal()) || "+".equals(rice0.getVal()) || "!".equals(rice0.getVal());
						}
						if (positiva) {
							switch (c.getStart()) {
								case '4':
									c.setCombination("X5");
									break;
								case '2':
									c.setCombination("X6");
									break;
								case '9':
									c.setCombination("X8");
									break;
								case '8':
									c.setCombination("XP");
									break;
							}
							
						} else {
							//contrattacco o R-
							switch (c.getStart()) {
							case '4':
								c.setCombination("V5");
								break;
							case '2':
								c.setCombination("V6");
								break;
							case '9':
								c.setCombination("V8");
								break;
							case '8':
								c.setCombination("VP");
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public void inserisciAlzate() {
		Integer alzatoreCasa = 0;
		Integer alzatoreOspite = 0;
		ListIterator<Riga> iterator = this.righe.listIterator(0);
		while (iterator.hasNext()) {
			Riga r = iterator.next();
			Campo0 c = r.getCampo0();
			if (c.toString().contains("*P")) {
				alzatoreCasa = this.getNumeroAlzatore(c.toString());
			}
			if (c.toString().contains("aP")) {
				alzatoreOspite = this.getNumeroAlzatore(c.toString());
			}
			if (c.isAttacco()) {
				if (!"PR".equals(c.getCombination()) && !"P2".equals(c.getCombination()) && !"P6".equals(c.getCombination())) {
					String alzata = "";
					alzata += c.getTeam();
					if ("*".equals(c.getTeam())) {
						alzata += String.format("%02d", alzatoreCasa);
					} else {
						alzata += String.format("%02d", alzatoreOspite);
					}
					alzata += "E";
					alzata += c.getType();
					alzata += "#;";
					alzata += ";;;;;;";
					alzata += r.getTempoRilevazione() + ";";
					alzata += r.getSet() +";";
					alzata += r.getpCasa() + ";";
					alzata += r.getpOspite() + ";";
					alzata += r.getFilmato() + ";";
					alzata += r.getTimecode() + ";";
					alzata += r.getCasa().toString();
					alzata += r.getOspite().toString();
					Riga a = new Riga(alzata);
					iterator.previous();
					iterator.add(a);
					iterator.next();
				}
			}
		}
	}
	
	private Integer getNumeroAlzatore(String riga) {
		String str = riga.substring(2);
		try {
			if (str.contains(">")) {
				return Integer.valueOf(str.substring(0,2));
			} 
			return Integer.valueOf(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
