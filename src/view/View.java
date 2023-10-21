package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import main.main;
import model.Cache;

public class View {
	
	private MyFrame frame;
	private JLabel filename = new JLabel("---");
	private JLabel casaName = new JLabel("Casa: ---");
	private JLabel ospiteName = new JLabel("Ospite: ---");
	private JLabel velocitaName = new JLabel("Velocità: ---");
	private JButton open, buttonPre, letteraCasa, letteraOspite, buttonVelocita;
	private JPanel header, panelCenter, panelBottom, panelRight, panelLeft;
	private String lastOpenDir = "";
	
	private JLabel licenza = new JLabel("Licenza: ---");
	private JLabel scadenza = new JLabel("Stato: ---");
	private JPanel panelLogin;
	private JButton btnLogin;
	
	//step by step
	private JCheckBox checkStep1;
	private JCheckBox checkStep2;
	private JCheckBox checkStep3;
	private JCheckBox checkTempiRicezione;
	private JCheckBox checkTempiAlzata;
	private JCheckBox checkLatoRicezione;
	private JCheckBox checkLatoRicettore;
	private JCheckBox checkTempiMuro;
	private JCheckBox checkTempiDifesa;
	private JCheckBox checkConteggioBattute;
	private JCheckBox checkDifferenzaPunteggio;
	private JCheckBox checkServizioDopoInterruzione;
	private JCheckBox checkRicezioneEstranei;
	private JCheckBox checkNumeroPersoneAMuro;
	private JCheckBox checkAttaccoDopoRicezione;
	private JCheckBox checkInserisciBasi;
	private JCheckBox checkMuroOpzioneLettura;
	private JCheckBox checkInserisciDifese;
	private JCheckBox checkInserisciToccoAMuro;
	private JCheckBox checkCopiaPersoneAMuro;
	private JCheckBox checkSpecialErroreServizio;
	private JCheckBox checkPuntiRete;
	private JCheckBox checkNumeroLettera;
	private JCheckBox checkInserisciVelocita;
	private JCheckBox checkTrasformaVelocita;
	private JCheckBox checkNormalizzaBattutaRicezione;
	private JCheckBox checkInserisciCombinazioniAttacco;
	private JCheckBox checkInserisciAlzate;
	
	//Indipendenti
	private JCheckBox checkPulisciCustomServizio;
	private JCheckBox checkPulisciCustomRicezione;
	private JCheckBox checkPulisciCustomAlzata;
	private JCheckBox checkPulisciCustomAttacco;
	private JCheckBox checkPulisciCustomMuro;
	private JCheckBox checkPulisciCustomDifesa;
	private JCheckBox checkPulisciCustomFree;
	private JCheckBox checkSovrascriviDirezioneServizio;
	private JCheckBox checkNormalizzaTempiFineAzione;
	private JCheckBox checkRimuoviFrecce;
	private JCheckBox checkCopiaDirezioneSuRicezione;
	private JCheckBox checkImportaFileClickAndScout;
	private JCheckBox checkProvenienzaAlzata;
	
	private JCheckBox checkTempiAttacchi;
	private JCheckBox checkTempiAttacchiCP;
	
	private static boolean inizializzati = false;
	
	public View() {
		// Creo il frame e imposto titolo e layout
		this.frame = new MyFrame("Volley Scout Customizer", new BorderLayout());
		
		//Creo pannello HEADER
		header = new JPanel(new FlowLayout());
		
		//Creo pannello MAIN
		panelCenter = new JPanel(new GridLayout(0,2));
		panelCenter.setBorder(new TitledBorder("SINGOLI"));
		panelCenter.setSize(600, 400);
		
		//Creo pannello STEP
		panelBottom = new JPanel();
		panelBottom.setBorder(new TitledBorder("ELABORA"));
		panelBottom.setSize(800, 200);
		panelBottom.setLayout(new BorderLayout());
		
		//Creo pannello Login
		panelLogin = new JPanel();
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
		panelLogin.add(licenza);
		panelLogin.add(scadenza);
		btnLogin = new JButton();
		btnLogin.setText("Login");
		if (licenza.getText().contains("---") && scadenza.getText().contains("---")) {
			panelLogin.add(btnLogin);
		}
		panelBottom.add(panelLogin, BorderLayout.EAST);
		
		//Creo pannello Custom • right
		panelRight = new JPanel();
		panelRight.setBorder(new TitledBorder("CUSTOM"));
		panelRight.setSize(100, 400);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		
		//Creo pannello Indipendenti • left
		panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder("INDIPENDENTI"));
		panelLeft.setSize(100, 400);
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		
		frame.getMainPanel().add(header, BorderLayout.NORTH);
		frame.getMainPanel().add(panelCenter, BorderLayout.CENTER);
		frame.getMainPanel().add(panelBottom, BorderLayout.SOUTH);
		frame.getMainPanel().add(panelRight, BorderLayout.EAST);
		frame.getMainPanel().add(panelLeft, BorderLayout.WEST);
		
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/vsc.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		JLabel label = new JLabel(icon);
		
		open = new JButton("Carica DVW");
		letteraCasa = new JButton("Carica LetteraSquadraCasa.txt");
		letteraOspite = new JButton("Carica LetteraSquadraOspite.txt");
		buttonVelocita = new JButton("Carica VelocitàServizio.txt");
		buttonPre = new JButton();
		
		header.add(label);
		header.add(open);
		header.add(filename);
		
		/*
		panelLeft.add(letteraCasa); 
		panelLeft.add(letteraOspite);
		panelLeft.add(casaName);
		panelLeft.add(ospiteName);
		
		panelRight.add(buttonVelocita);
		panelRight.add(velocitaName);
		*/
		frame.setVisible(true);
	}
	
	public JButton getOpenButton() {
		return this.open;
	}
	
	public JButton getLetteraCasaButton() {
		return this.letteraCasa;
	}
	
	public JButton getLetteraOspiteButton() {
		return this.letteraOspite;
	}
	
	public JButton getButtonVelocita() {
		return this.buttonVelocita;
	}
	
	public File chooseFile(String ext1, String ext2, String ext3) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(ext1, ext2));
		fc.setAcceptAllFileFilterUsed(false);
		int rVal = fc.showOpenDialog(header);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fc.getSelectedFile();
				if (ext3.contains("dvw")) {
					filename.setText(file.getName());
					if ("---" != filename.getText()) {
						//float seconds = System.currentTimeMillis() / 1000F;
						buttonPre.setText("Elabora " + filename.getText().substring(0, filename.getText().length()-4) + ext3);
						buttonPre.setAlignmentX(Component.CENTER_ALIGNMENT);
						initCheckbox();
						panelBottom.add(buttonPre, BorderLayout.WEST);
					}
				} else if (ext3.contains("casa")) {
					casaName.setText(file.getName());
				} else if (ext3.contains("ospite")) {
					ospiteName.setText(file.getName());
				} else if (ext3.contains("velocita")) {
					velocitaName.setText(file.getName());
				}
				return file;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public File saveFile(String filename) {
		//configure filechooser
		final JFileChooser fc = new JFileChooser(lastOpenDir);
		fc.removeChoosableFileFilter(fc.getFileFilter());
		FileFilter filter = new FileNameExtensionFilter("*.dvw", "dvw");
		fc.addChoosableFileFilter(filter);
		
		//set filename
		try {
	        FileChooserUI fcUi = fc.getUI();
	        Class<? extends FileChooserUI> fcClass = fcUi.getClass();
	        Method setFileName = fcClass.getMethod("setFileName", String.class);
	        setFileName.invoke(fcUi, filename);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		//show dialog
		int returnVal = fc.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedDir = fc.getSelectedFile();
			lastOpenDir = fc.getSelectedFile().getParent();
			String lastOpenFile = fc.getSelectedFile().getName();
			if (selectedDir.isFile()) {
				String errorString = selectedDir.getPath() + " esiste già \nVuoi sostituirlo?";
				Object[] options = {"SI", "NO"};
				int n = JOptionPane.showOptionDialog(
						null, 
						errorString, 
						"Sovrascrivi", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						options, 
						options[0]
						);
				if (n == JOptionPane.YES_OPTION) {
					if (fc.getFileFilter().getDescription().equals("*.dvw")) {
						return fc.getSelectedFile();
					}
				}
			} else {
				return fc.getSelectedFile();
			}
		}
		return null;
	}
	
	public JButton getButtonEsegui() {
		return buttonPre;
	}
	
	public JButton getButtonLogin() {
		return btnLogin;
	}
	
	private void initCheckbox() {
		if (!View.inizializzati) {
			//INDIPENDENTI
			checkPulisciCustomServizio = new JCheckBox("Pulisci custom SERVIZIO");
			checkPulisciCustomRicezione = new JCheckBox("Pulisci custom RICEZIONE");
			checkPulisciCustomAlzata = new JCheckBox("Pulisci custom ALZATA");
			checkPulisciCustomAttacco = new JCheckBox("Pulisci custom ATTACCO");
			checkPulisciCustomMuro = new JCheckBox("Pulisci custom MURO");
			checkPulisciCustomDifesa = new JCheckBox("Pulisci custom DIFESA");
			checkPulisciCustomFree = new JCheckBox("Pulisci custom FREE");
			panelLeft.add(checkPulisciCustomServizio);
			panelLeft.add(checkPulisciCustomRicezione);
			panelLeft.add(checkPulisciCustomAlzata);
			panelLeft.add(checkPulisciCustomAttacco);
			panelLeft.add(checkPulisciCustomMuro);
			panelLeft.add(checkPulisciCustomDifesa);
			panelLeft.add(checkPulisciCustomFree);
			
			checkNormalizzaTempiFineAzione = new JCheckBox("Normalizza tempi fine azione");
			panelLeft.add(checkNormalizzaTempiFineAzione);
			
			checkSovrascriviDirezioneServizio = new JCheckBox("Sovrascrivi direzione Servizio dalla Ricezione");
			panelLeft.add(checkSovrascriviDirezioneServizio);
			
			checkRimuoviFrecce = new JCheckBox("Rimuovi frecce");
			panelLeft.add(checkRimuoviFrecce);
			
			checkCopiaDirezioneSuRicezione = new JCheckBox("Copia direzione Servizio sulla Ricezione");
			panelLeft.add(checkCopiaDirezioneSuRicezione);
			
			checkImportaFileClickAndScout = new JCheckBox("Importa File da Click&Scout");
			panelLeft.add(checkImportaFileClickAndScout);
			
			
			//MAIN
			
			checkSpecialErroreServizio = new JCheckBox("Inserisci Special Errore Servizio");
			panelCenter.add(checkSpecialErroreServizio);
			
			checkTempiRicezione = new JCheckBox("Tempi Ricezioni");
			panelCenter.add(checkTempiRicezione);
			
			checkLatoRicezione = new JCheckBox("+ Lato ricezione (M-O-W)");
			panelCenter.add(checkLatoRicezione);
			
			checkLatoRicettore = new JCheckBox("+ Lato ricettore (3-4-5)");
			panelCenter.add(checkLatoRicettore);
			
			checkNormalizzaBattutaRicezione = new JCheckBox("Normalizza Battuta-Ricezione");
			panelCenter.add(checkNormalizzaBattutaRicezione);
			
			checkTempiAttacchi = new JCheckBox("Tempi Attacchi di contrattacco");
			panelCenter.add(checkTempiAttacchi);
			
			checkTempiAttacchiCP = new JCheckBox("Tempi Attacchi CP");
			panelCenter.add(checkTempiAttacchiCP);
			
			checkTempiAlzata = new JCheckBox("Tempi Alzata");
			panelCenter.add(checkTempiAlzata);
			
			checkTempiDifesa = new JCheckBox("Tempi Difesa");
			panelCenter.add(checkTempiDifesa);
			
			checkTempiMuro = new JCheckBox("Tempi Muro");
			panelCenter.add(checkTempiMuro);
			
			checkNumeroPersoneAMuro = new JCheckBox("Numero persone a muro");
			panelCenter.add(checkNumeroPersoneAMuro);
			
			checkInserisciBasi = new JCheckBox("+ Basi");
			panelCenter.add(checkInserisciBasi);
			
			checkMuroOpzioneLettura = new JCheckBox("+ Muro Opzione/Lettura");
			panelCenter.add(checkMuroOpzioneLettura);
			
			checkInserisciDifese = new JCheckBox("Aggiungi/Modifica Difese");
			panelCenter.add(checkInserisciDifese);
			
			checkInserisciToccoAMuro = new JCheckBox("+ Tocchi a muro");
			panelCenter.add(checkInserisciToccoAMuro);
			
			checkCopiaPersoneAMuro = new JCheckBox("Copia numero persone a muro (sul muro, dall'attacco)");
			panelCenter.add(checkCopiaPersoneAMuro);
			
			checkInserisciCombinazioniAttacco = new JCheckBox("Inserisci combinazioni attacco");
			panelCenter.add(checkInserisciCombinazioniAttacco);
			
			checkInserisciAlzate = new JCheckBox("Inserisci Alzate");
			panelCenter.add(checkInserisciAlzate);
			
			
			//CUSTOM
			
			checkTrasformaVelocita = new JCheckBox("Trasforma velocita servizio in lettere");
			panelRight.add(checkTrasformaVelocita);
			
			checkConteggioBattute = new JCheckBox("Conteggio Battute");
			panelRight.add(checkConteggioBattute);
			
			checkDifferenzaPunteggio = new JCheckBox("Differenza Punteggio");
			panelRight.add(checkDifferenzaPunteggio);
			
			checkServizioDopoInterruzione = new JCheckBox("Servizio dopo interruzione");
			panelRight.add(checkServizioDopoInterruzione);
			
			checkRicezioneEstranei = new JCheckBox("Ricezione extra linea");
			panelRight.add(checkRicezioneEstranei);
			
			checkAttaccoDopoRicezione = new JCheckBox("Attacco dopo ricezione");
			panelRight.add(checkAttaccoDopoRicezione);
			
			checkPuntiRete = new JCheckBox("Inserisci Punti Rete");
			panelRight.add(checkPuntiRete);
			
			checkProvenienzaAlzata = new JCheckBox("Provenienza Alzata");
			panelRight.add(checkProvenienzaAlzata);
			
			//checkNumeroLettera = new JCheckBox("Aggiungi persone a muro (LETTERE su Alzata)");
			//panelRight.add(checkNumeroLettera);
			
			//checkInserisciVelocita = new JCheckBox("Inserisci velocita servizio");
			//panelRight.add(checkInserisciVelocita);
			
			
			//STEP BY STEP
			/*
			checkStep1 = new JCheckBox("ESEGUI STEP 1");
			checkStep1.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelBottom.add(checkStep1);
			checkStep2 = new JCheckBox("ESEGUI STEP 2");
			checkStep2.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelBottom.add(checkStep2);
			checkStep3 = new JCheckBox("ESEGUI STEP 3");
			checkStep3.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelBottom.add(checkStep3);
			*/
			View.inizializzati = true;
		}
		
	}

	public JCheckBox getCheckStep1() {
		return checkStep1;
	}
	
	public JCheckBox getCheckStep2() {
		return checkStep2;
	}
	
	public JCheckBox getCheckStep3() {
		return checkStep3;
	}

	public JCheckBox getCheckTempiRicezione() {
		return checkTempiRicezione;
	}

	public JCheckBox getCheckTempiAlzata() {
		return checkTempiAlzata;
	}

	public JCheckBox getCheckLatoRicezione() {
		return checkLatoRicezione;
	}

	public JCheckBox getCheckLatoRicettore() {
		return checkLatoRicettore;
	}

	public JCheckBox getCheckConteggioBattute() {
		return checkConteggioBattute;
	}

	public JCheckBox getCheckDifferenzaPunteggio() {
		return checkDifferenzaPunteggio;
	}

	public JCheckBox getCheckServizioDopoInterruzione() {
		return checkServizioDopoInterruzione;
	}

	public JCheckBox getCheckRicezioneEstranei() {
		return checkRicezioneEstranei;
	}

	public JCheckBox getCheckNumeroPersoneAMuro() {
		return checkNumeroPersoneAMuro;
	}

	public JCheckBox getCheckPulisciCustomServizio() {
		return checkPulisciCustomServizio;
	}

	public JCheckBox getCheckPulisciCustomRicezione() {
		return checkPulisciCustomRicezione;
	}

	public JCheckBox getCheckPulisciCustomAlzata() {
		return checkPulisciCustomAlzata;
	}

	public JCheckBox getCheckPulisciCustomAttacco() {
		return checkPulisciCustomAttacco;
	}

	public JCheckBox getCheckPulisciCustomMuro() {
		return checkPulisciCustomMuro;
	}

	public JCheckBox getCheckPulisciCustomDifesa() {
		return checkPulisciCustomDifesa;
	}

	public JCheckBox getCheckPulisciCustomFree() {
		return checkPulisciCustomFree;
	}

	public JCheckBox getCheckTempiAttacchi() {
		return checkTempiAttacchi;
	}
	
	public JCheckBox getCheckTempiAttacchiCP() {
		return checkTempiAttacchiCP;
	}
	
	public JCheckBox getCheckAttaccoDopoRicezione() {
		return checkAttaccoDopoRicezione;
	}
	
	public JCheckBox getCheckInserisciBasi() {
		return checkInserisciBasi;
	}
	
	public JCheckBox getCheckMuroOpzioneLettura() {
		return checkMuroOpzioneLettura;
	}
	
	public JCheckBox getCheckInserisciDifese() {
		return checkInserisciDifese;
	}
	
	public JCheckBox getCheckInserisciToccoAMuro() {
		return checkInserisciToccoAMuro;
	}
	
	public JCheckBox getCheckSovrascriviDirezioneServizio() {
		return checkSovrascriviDirezioneServizio;
	}
	
	public JCheckBox getCheckCopiaDirezioneSuRicezione() {
		return checkCopiaDirezioneSuRicezione;
	}
	
	public JCheckBox getCheckCopiaPersoneAMuro() {
		return checkCopiaPersoneAMuro;
	}
	
	public JCheckBox getCheckTempiMuro() {
		return checkTempiMuro;
	}
	
	public JCheckBox getCheckSpecialErroreServizio() {
		return checkSpecialErroreServizio;
	}

	public JCheckBox getCheckPuntiRete() {
		return checkPuntiRete;
	}
	
	public JCheckBox getCheckNumeroLettera() {
		return checkNumeroLettera;
	}
	
	public JCheckBox getInserisciVelocita() {
		return checkInserisciVelocita;
	}
	
	public JCheckBox getTrasformaVelocita() {
		return checkTrasformaVelocita;
	}
	
	public JCheckBox getNormalizzaTempiFineAzione() {
		return checkNormalizzaTempiFineAzione;
	}
	
	public void showError(String message) {
		JOptionPane.showMessageDialog(frame, message, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public JCheckBox getCheckRimuoviFrecce() {
		return checkRimuoviFrecce;
	}
	
	public JCheckBox getCheckTempiDifesa() {
		return checkTempiDifesa;
	}
	
	public JCheckBox getNormalizzaBattutaRicezione() {
		return checkNormalizzaBattutaRicezione;
	}
	
	public JCheckBox getInserisciCombinazioniAttacco() {
		return checkInserisciCombinazioniAttacco;
	}
	
	public JCheckBox getInserisciAlzate() {
		return checkInserisciAlzate;
	}
	
	public JCheckBox getCheckImportaFileClickAndScout() {
		return checkImportaFileClickAndScout;
	}
	
	public JCheckBox getCheckProvenienzaAlzata() {
		return checkProvenienzaAlzata;
	}
	
	public void refreshAbbonamento(String lic, String stato) {
		if (stato.equals(main.VALIDO) || stato.equals(main.IN_APPROVAZIONE)) {
			licenza.setText("Licenza: "+ lic);
			scadenza.setText("Stato: "+ stato);
		}
		if (licenza.getText().contains("---") && scadenza.getText().contains("---")) {
			panelLogin.add(btnLogin);
		} else {
			panelLogin.remove(btnLogin);
		}
	}
}
