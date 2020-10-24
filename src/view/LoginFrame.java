package view;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {
	
	public LoginFrame(String title, LayoutManager lm) {
		super(title);
		this.setSize(300,120);
		JPanel panel = new JPanel(lm);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(panel);
	}
	
	public JPanel getMainPanel() {
		return (JPanel)this.getContentPane().getComponent(0);
	}
}
