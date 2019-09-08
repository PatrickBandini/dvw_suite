package model;

import java.io.File;
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
	
	public Riga getNext(Riga uid) {
	    int idx = righe.indexOf(uid);
	    if (idx < 0 || idx+1 == righe.size()) return null;
	    return righe.get(idx + 1);
	}

	public Riga getPrevious(Riga uid) {
	    int idx = righe.indexOf(uid);
	    if (idx <= 0) return null;
	    return righe.get(idx - 1);
	}
	
	//QUERY
	
	public void tempiAlzataCambioPalla() {
		for (Riga r: this.righe) {
			Riga prev = getPrevious(r);
			if (r.isAlzataCP(prev)) {
				r.setTimecode(prev.getTimecode());
			}
		}
		System.out.println("OK - Tempi Alzata Cambio Palla");
	}
	
	public void tempiRicezione() {
		for (Riga r: this.righe) {
			if (r.getCampo0().isRicezione()) {
				r.setTimecode(getPrevious(r).getTimecode());
			}
		}
		System.out.println("OK - Tempi Ricezione");
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
	

}
