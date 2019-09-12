package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	public void inserisciBasi() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAlzata()) {
				Campo0 c = r.getCampo0();
				Riga attacco = getNext(righe, r);
				//Base
				if (c.getType().equals("H")) {
					c.setCombination("KE");
				} else if (attacco.getCampo0().isAttacco()) {
					String comb = attacco.getCampo0().getCombination();
					if (comb.equals("X2")) {
						c.setCombination("K2");
					} else if (comb.equals("X7") || comb.equals("XD") || comb.equals("XP")) {
						c.setCombination("K7");
					} else {
						c.setCombination("K1");
					}
				} else {
					c.setCombination("K1");
				}
				//Distribuzione
				if (attacco.getCampo0().isAttacco()) {
					switch(attacco.getCampo0().getCombination()) {
					case "X1":
					case "X2":
					case "XC":
					case "XD":
					case "X7":
					case "V3":
						c.setTarget('C');
						break;
					case "X5":
					case "X9":
					case "C5":
					case "V5":
					case "VE":
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
	
	public void attaccoDopoRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isAttacco()) {
				Riga alzata = getPrevious(righe, r);
				Riga ricezione = getPrevious(righe, alzata);
				if (alzata.isAlzataCP(ricezione)) {
					if(ricezione.getCampo0().getNumero().equals(r.getCampo0().getNumero())) {
						r.getCampo0().updateCustom(0, 1, "R");
						System.out.println(r.toString());
					}
				}
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
	
	public void tempiAlzataCambioPalla() {
		for (Riga r: this.righe) {
			Riga prev = getPrevious(righe, r);
			if (r.isAlzataCP(prev)) {
				r.setTimecode(prev.getTimecode());
			}
		}
		System.out.println("OK - Tempi Alzata Cambio Palla");
	}
	
	public void tempiRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				r.setTimecode(getPrevious(righe, r).getTimecode());
			}
		}
		System.out.println("OK - Tempi Ricezione");
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
				if (r.getCampo0().getNumero().equals(prev.getNumero())) {
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
					String str = punto.getCampo0().getStringa();
					Integer casa = Integer.valueOf(str.substring(2,4));
					Integer ospite = Integer.valueOf(str.substring(5,7));
					Integer differenza = casa-ospite;
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
	

}
