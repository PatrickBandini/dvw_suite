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
				char type = r.getCampo0().getType().charAt(0);
				switch (type) {
					case 'Q': 
						break;
					case 'M':
						break;
					case 'T':
						break;
					default:
						break;
				}
			}
		}
		//System.out.println("OK - Lato Ricezione");
	}
	
	

}
