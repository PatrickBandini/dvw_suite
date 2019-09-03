package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View {
	
	private MyFrame frame;
	private JLabel filename = new JLabel("---");
	private JButton open;
	private JPanel header;
	
	public View() {
		// Creo il frame e imposto titolo e layout
		this.frame = new MyFrame("DVW Suite", new BorderLayout());
		
		//Creo pannello HEADER
		header = new JPanel(new FlowLayout());
		
		//Creo pannello PRE
		JPanel panelPre = new JPanel(new FlowLayout());
		panelPre.setBorder(new TitledBorder("PRE correzione"));
		panelPre.setSize(800, 400);
		
		//Creo pannello POST
		JPanel panelPost = new JPanel(new FlowLayout());
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
		
		
		header.add(label);
		header.add(open);
		header.add(filename);
		panelPre.add(new JButton("Prova di pulsante"));
		panelPost.add(new JButton("Prova di pulsante"));
		
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
				return file;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
