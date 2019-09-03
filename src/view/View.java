package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class View {
	
	public View() {
		// Creo il frame e imposto titolo e layout
		MyFrame frame = new MyFrame("DVW Suite", new BorderLayout());
		
		//Creo pannello HEADER
		JPanel header = new JPanel(new FlowLayout());
		
		//Creo pannello PRE
		JPanel panelPre = new JPanel(new FlowLayout());
		panelPre.setBorder(new TitledBorder("PRE correzione"));
		panelPre.setSize(600, 400);
		
		//Creo pannello POST
		JPanel panelPost = new JPanel(new FlowLayout());
		panelPost.setBorder(new TitledBorder("POST correzione"));
		panelPost.setSize(600, 200);
		
		frame.getMainPanel().add(header, BorderLayout.NORTH);
		frame.getMainPanel().add(panelPre, BorderLayout.CENTER);
		frame.getMainPanel().add(panelPost, BorderLayout.SOUTH);
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/prc.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		JLabel label = new JLabel(icon);
		
		header.add(label);
		panelPre.add(new JButton("Prova di pulsante"));
		panelPost.add(new JButton("Prova di pulsante"));
		
		frame.setVisible(true);
	}

}
