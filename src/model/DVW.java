package model;

import java.io.File;
import java.util.List;

public class DVW {
	
	private File file;
	private List<String> intestazione;
	private List<Riga> righe;
	private int indexFineIntestazione;
	
	public DVW(File file) {
		this.file = file;
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
	
	public File getFile() {
		return this.file;
	}
	
	public int getIndexFineIntestazione() {
		return this.indexFineIntestazione;
	}
	
	

}
