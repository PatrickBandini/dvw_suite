package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Campo0;
import model.DVW;
import model.NumeroLettera;
import model.Riga;
import model.Velocita;
import view.LoginFrame;
import view.LoginView;
import view.MyFrame;
import view.View;

public class Controller {
	
	private LoginFrame loginFrame;
	
	private View view;
	private DVW model;
	private List<NumeroLettera> letteraCasa = new ArrayList<NumeroLettera>();
	private List<NumeroLettera> letteraOspite = new ArrayList<NumeroLettera>();
	private List<Velocita> velocita = new ArrayList<Velocita>();
	
	private File originalFile;
	
	public Controller(View view, DVW model) {
		this.view = view;
		this.model = model;
		
		setUpViewEvents();
	}
	
	private void setUpViewEvents() {
		view.getOpenButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				originalFile = null;
				originalFile = view.chooseFile("*.dvw", "dvw", ".dvw");
				if (null != originalFile) {
					readFile(originalFile);
				}
			}
		});
		
		view.getLetteraCasaButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileNumeroLettera(view.chooseFile("*.txt", "txt", "casa"), true);
			}
		});
		
		view.getLetteraOspiteButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileNumeroLettera(view.chooseFile("*.txt", "txt", "ospite"), false);
			}
		});
		
		view.getButtonVelocita().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileVelocita(view.chooseFile("*.txt", "txt", "velocita"));
			}
		});
		
		view.getButtonLogin().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginView(Controller.this);
			}
		});
		
		view.getButtonEsegui().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (view.getTrasformaVelocita().isSelected()) {
						model.trasformaVelocitaServizio();
					}
					if (view.getCheckPulisciCustomServizio().isSelected()) {
						model.pulisciCustom(Campo0.SERVIZIO);
					}
					if (view.getCheckPulisciCustomRicezione().isSelected()) {
						model.pulisciCustom(Campo0.RICEZIONE);
					}
					if (view.getCheckPulisciCustomAlzata().isSelected()) {
						model.pulisciCustom(Campo0.ALZATA);
					}
					if (view.getCheckPulisciCustomAttacco().isSelected()) {
						model.pulisciCustom(Campo0.ATTACCO);
					}
					if (view.getCheckPulisciCustomMuro().isSelected()) {
						model.pulisciCustom(Campo0.MURO);
					}
					if (view.getCheckPulisciCustomDifesa().isSelected()) {
						model.pulisciCustom(Campo0.DIFESA);
					}
					if (view.getCheckPulisciCustomFree().isSelected()) {
						model.pulisciCustom(Campo0.FREE);
					}
					/*if (view.getCheckStep1().isSelected()) {
						model.tempiRicezione();
						model.aggiungiLatoRicezione();
						model.aggiungiLatoRicettore();
						model.tempiAlzata();
						model.conteggioBattute();
						model.differenzaPunteggio();
						model.servizioDopoInterruzione();
						model.ricezioneEstranei();
						model.numeroPersoneAMuro();
					}
					if (view.getCheckStep2().isSelected()) {
						model.inserisciBasi();
						model.copiaPersoneAMuro();
						model.inserisciMuroOpzioneLettura();
						model.inserisciToccoAMuro();
						model.attaccoDopoRicezione();
						model.inserisciCustomGiocatoriAMuro(letteraCasa, letteraOspite);
					}
					if (view.getCheckStep3().isSelected()) {
						model.inserisciPuntiRete();
						model.tempiMuro();
						model.inserisciDifese();
					}*/
					if (view.getCheckTempiAttacchi().isSelected()) {
						model.tempiAttaccoContrattacco();
					}
					if (view.getCheckTempiAttacchiCP().isSelected()) {
						model.tempiAttaccoCP();
					}
					
					if (view.getCheckTempiRicezione().isSelected()) {
						model.tempiRicezione();
					}
					if (view.getCheckLatoRicezione().isSelected()) {
						model.aggiungiLatoRicezione();
					}
					if (view.getCheckLatoRicettore().isSelected()) {
						model.aggiungiLatoRicettore();
					}
					if (view.getCheckTempiAlzata().isSelected()) {
						model.tempiAlzata();
					}
					if (view.getCheckNumeroPersoneAMuro().isSelected()) {
						model.numeroPersoneAMuro();
					}
					if (view.getCheckConteggioBattute().isSelected()) {
						model.conteggioBattute();
					}
					if (view.getCheckDifferenzaPunteggio().isSelected()) {
						model.differenzaPunteggio();
					}
					if (view.getCheckServizioDopoInterruzione().isSelected()) {
						model.servizioDopoInterruzione();
					}
					if (view.getCheckRicezioneEstranei().isSelected()) {
						model.ricezioneEstranei();
					}
					if (view.getCheckAttaccoDopoRicezione().isSelected()) {
						model.attaccoDopoRicezione();
					}
					if (view.getCheckInserisciBasi().isSelected()) {
						model.inserisciBasi();
					}
					if (view.getCheckMuroOpzioneLettura().isSelected()) {
						model.inserisciMuroOpzioneLettura();
					}
					if (view.getCheckInserisciDifese().isSelected()) {
						model.inserisciDifese();
					}
					if (view.getCheckInserisciToccoAMuro().isSelected()) {
						model.inserisciToccoAMuro();
					}
					if (view.getCheckSovrascriviDirezioneServizio().isSelected()) {
						model.completaDirezioneServizio();
					}
					if (view.getCheckCopiaDirezioneSuRicezione().isSelected()) {
						model.copiaDirezioneSuRicezione();
					}
					if (view.getCheckCopiaPersoneAMuro().isSelected()) {
						model.copiaPersoneAMuro();
					}
					if (view.getCheckTempiMuro().isSelected()) {
						model.tempiMuro();
					}
					if (view.getCheckTempiDifesa().isSelected()) {
						model.tempiDifesa();
					}
					if (view.getCheckSpecialErroreServizio().isSelected()) {
						model.inserisciEstensioneErroreServizio();
					}
					if (view.getCheckPuntiRete().isSelected()) {
						model.inserisciPuntiRete();
					}
					/*if (view.getCheckNumeroLettera().isSelected()) {
						model.inserisciCustomGiocatoriAMuro(letteraCasa, letteraOspite);
					}
					if (view.getInserisciVelocita().isSelected()) {
						model.inserisciVelocitaServizio(velocita);
					}*/
					if (view.getNormalizzaTempiFineAzione().isSelected()) {
						model.normalizzaTempiFineAzione();
					}
					if (view.getCheckRimuoviFrecce().isSelected()) {
						model.rimuoviFrecce();
					}
					String filename = view.getButtonEsegui().getText().substring(8);
					File toSave = view.saveFile(filename);
					writeFile(toSave);
				} catch (Exception ex) {
					ex.printStackTrace();
					String str = "";
					StackTraceElement[] ste = ex.getStackTrace();
					for (StackTraceElement s: ste) {
						str += s.toString() + "\n";
					}
					view.showError(str);
				}
				
			}
		});
		
	}
	
	public void refreshFromLogin() {
		
	}
	
	private void readFileVelocita(File f) {
		try {
			List<String> rows = Files.readAllLines(Paths.get(f.getPath()), StandardCharsets.ISO_8859_1);
			for(int i=0;i<rows.size(); i++) {
				velocita.add(new Velocita(rows.get(i)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFileNumeroLettera(File f, boolean casa) {
		try {
			List<String> rows = Files.readAllLines(Paths.get(f.getPath()), StandardCharsets.ISO_8859_1);
			for(int i=0;i<rows.size(); i++) {
				String[] str = rows.get(i).split(";");
				if (casa) {
					letteraCasa.add(new NumeroLettera(str[0], str[1].charAt(0)));
				} else {
					letteraOspite.add(new NumeroLettera(str[0], str[1].charAt(0)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFile(File f) {
		try {
			this.model = new DVW();
			List<String> rows = Files.readAllLines(Paths.get(f.getPath()), StandardCharsets.ISO_8859_1);
			List<Riga> righe = new ArrayList<Riga>();
			for(int i=0;i<rows.size(); i++) {
				if ("[3SCOUT]".equals(rows.get(i))) {
					List<String> intestazione = rows.subList(0, i+1);
					model.setIntestazione(intestazione, i+1);
				}
				if (i >= model.getIndexFineIntestazione()) {
					righe.add(new Riga(rows.get(i)));
					System.out.println(rows.get(i).toString());
				}
			}
			model.setRighe(righe);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeFile(File toSave) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(toSave), StandardCharsets.ISO_8859_1);
			osw.write(model.printIntestazione());
			osw.write(model.printRighe());
			osw.flush();
			osw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
