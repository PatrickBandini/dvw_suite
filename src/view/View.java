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

public class View {
	
	private MyFrame frame;
	private JLabel filename = new JLabel("---");
	private JButton open, buttonPre;
	private JPanel header, panelCenter, panelBottom, panelRight, panelLeft;
	private String lastOpenDir = "";
	
	//step by step
	private JCheckBox checkStep1;
	private JCheckBox checkTempiRicezione;
	private JCheckBox checkTempiAlzateCambioPalla;
	private JCheckBox checkLatoRicezione;
	private JCheckBox checkLatoRicettore;
	private JCheckBox checkTempiMuro;
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
	
	//Indipendenti
	private JCheckBox checkPulisciCustomServizio;
	private JCheckBox checkPulisciCustomRicezione;
	private JCheckBox checkPulisciCustomAlzata;
	private JCheckBox checkPulisciCustomAttacco;
	private JCheckBox checkPulisciCustomMuro;
	private JCheckBox checkPulisciCustomDifesa;
	private JCheckBox checkPulisciCustomFree;
	private JCheckBox checkSovrascriviDirezioneServizio;
	
	private JCheckBox checkTempiAttacchi;
	private JCheckBox checkTempiAttacchiCP;
	
	private static boolean inizializzati = false;
	
	public View() {
		// Creo il frame e imposto titolo e layout
		this.frame = new MyFrame("DVW Suite", new BorderLayout());
		
		//Creo pannello HEADER
		header = new JPanel(new FlowLayout());
		
		//Creo pannello MAIN
		panelCenter = new JPanel(new GridLayout(0,2));
		panelCenter.setBorder(new TitledBorder("SINGOLI"));
		panelCenter.setSize(600, 400);
		
		//Creo pannello STEP
		panelBottom = new JPanel();
		panelBottom.setBorder(new TitledBorder("STEP BY STEP"));
		panelBottom.setSize(800, 200);
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
		
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
		
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/prc.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		JLabel label = new JLabel(icon);
		
		open = new JButton("Carica DVW");
		buttonPre = new JButton();
		
		header.add(label);
		header.add(open);
		header.add(filename);
		
		frame.setVisible(true);
	}
	
	public JButton getOpenButton() {
		return this.open;
	}
	
	public File chooseFile() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("*.dvw", "dvw"));
		fc.setAcceptAllFileFilterUsed(false);
		int rVal = fc.showOpenDialog(header);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fc.getSelectedFile();
				filename.setText(file.getName());
				if ("---" != filename.getText()) {
					float seconds = System.currentTimeMillis() / 1000F;
					buttonPre.setText("Elabora " + filename.getText().substring(0, filename.getText().length()-4) + "_" + seconds + ".dvw");
					buttonPre.setAlignmentX(Component.CENTER_ALIGNMENT);
					initCheckbox();
					panelBottom.add(buttonPre);
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
			checkSovrascriviDirezioneServizio = new JCheckBox("Sovrascrivi direzione Servizio dalla Ricezione");
			panelLeft.add(checkSovrascriviDirezioneServizio);
			
			
			//MAIN
			
			checkSpecialErroreServizio = new JCheckBox("Inserisci Special Errore Servizio");
			panelCenter.add(checkSpecialErroreServizio);
			
			checkTempiRicezione = new JCheckBox("Tempi Ricezioni");
			panelCenter.add(checkTempiRicezione);
			
			checkLatoRicezione = new JCheckBox("+ Lato ricezione (M-O-W)");
			panelCenter.add(checkLatoRicezione);
			
			checkLatoRicettore = new JCheckBox("+ Lato ricettore (3-4-5)");
			panelCenter.add(checkLatoRicettore);
			
			checkTempiAttacchi = new JCheckBox("Tempi Attacchi di contrattacco");
			panelCenter.add(checkTempiAttacchi);
			
			checkTempiAttacchiCP = new JCheckBox("Tempi Attacchi CP");
			panelCenter.add(checkTempiAttacchiCP);
			
			checkTempiAlzateCambioPalla = new JCheckBox("Tempi Alzate CP");
			panelCenter.add(checkTempiAlzateCambioPalla);
			
			checkTempiMuro = new JCheckBox("Tempi Muro");
			panelCenter.add(checkTempiMuro);
			
			checkNumeroPersoneAMuro = new JCheckBox("Numero persone a muro");
			panelCenter.add(checkNumeroPersoneAMuro);
			
			checkInserisciBasi = new JCheckBox("+ Basi");
			panelCenter.add(checkInserisciBasi);
			
			checkMuroOpzioneLettura = new JCheckBox("+ Muro Opzione/Lettura");
			panelCenter.add(checkMuroOpzioneLettura);
			
			checkInserisciDifese = new JCheckBox("+ Difese");
			panelCenter.add(checkInserisciDifese);
			
			checkInserisciToccoAMuro = new JCheckBox("+ Tocchi a muro");
			panelCenter.add(checkInserisciToccoAMuro);
			
			checkCopiaPersoneAMuro = new JCheckBox("Copia numero persone a muro (sul muro, dall'attacco)");
			panelCenter.add(checkCopiaPersoneAMuro);
			
			
			//CUSTOM
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
			
			
			
			//STEP BY STEP
			checkStep1 = new JCheckBox("ESEGUI STEP 1");
			checkStep1.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelBottom.add(checkStep1);
			View.inizializzati = true;
		}
		
	}

	public JCheckBox getCheckStep1() {
		return checkStep1;
	}

	public JCheckBox getCheckTempiRicezione() {
		return checkTempiRicezione;
	}

	public JCheckBox getCheckTempiAlzateCambioPalla() {
		return checkTempiAlzateCambioPalla;
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
	
	public JCheckBox getCheckCopiaPersoneAMuro() {
		return checkCopiaPersoneAMuro;
	}
	
	public JCheckBox getCheckTempiMuro() {
		return checkTempiMuro;
	}
	
	public JCheckBox getCheckSpecialErroreServizio() {
		return checkSpecialErroreServizio;
	}
	
}
