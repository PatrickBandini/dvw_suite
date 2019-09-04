package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JButton open, buttonPre, buttonPost;
	private JPanel header, panelPre, panelPost;
	private String lastOpenDir = "";
	
	public View() {
		// Creo il frame e imposto titolo e layout
		this.frame = new MyFrame("DVW Suite", new BorderLayout());
		
		//Creo pannello HEADER
		header = new JPanel(new FlowLayout());
		
		//Creo pannello PRE
		panelPre = new JPanel(new FlowLayout());
		panelPre.setBorder(new TitledBorder("PRE correzione"));
		panelPre.setSize(800, 400);
		
		//Creo pannello POST
		panelPost = new JPanel(new FlowLayout());
		panelPost.setBorder(new TitledBorder("POST correzione"));
		panelPost.setSize(800, 200);
		
		frame.getMainPanel().add(header, BorderLayout.NORTH);
		frame.getMainPanel().add(panelPre, BorderLayout.CENTER);
		frame.getMainPanel().add(panelPost, BorderLayout.SOUTH);
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/prc.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		JLabel label = new JLabel(icon);
		
		open = new JButton("Carica DVW");
		buttonPre = new JButton();
		buttonPost = new JButton();
		
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
					buttonPre.setText("Elabora &pre-" + filename.getText().substring(1));
					buttonPost.setText("Elabora &post-" + filename.getText().substring(1));
					panelPre.add(buttonPre);
					panelPost.add(buttonPost);
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
	
	public JButton getPre() {
		return buttonPre;
	}
	
	public JButton getPost() {
		return buttonPost;
	}
}
