package view;

import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	
	public MyFrame(String title, LayoutManager lm) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.getContentPane().add(new JPanel(lm));
	}
	
	public JPanel getMainPanel() {
		return (JPanel)this.getContentPane().getComponent(0);
	}

}
