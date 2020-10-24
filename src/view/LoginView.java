package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView implements ActionListener {
	
	private static final String EMAIL_PATTERN = 
		    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private LoginFrame loginFrame;
	private JLabel labelUsername = new JLabel("Username:");
	private JLabel labelPassword = new JLabel("Password:");
	private JLabel message = new JLabel();
	private JButton buttonLogin;
	private JTextField username;
	private JPasswordField password;
	
	public LoginView() {
		this.loginFrame = new LoginFrame("Login", new GridLayout(3, 1));
		
		this.buttonLogin = new JButton("Submit");
		this.username = new JTextField();
		this.password = new JPasswordField();
		
		this.loginFrame.getMainPanel().add(labelUsername);
		this.loginFrame.getMainPanel().add(username);
		this.loginFrame.getMainPanel().add(labelPassword);
		this.loginFrame.getMainPanel().add(password);
		this.loginFrame.getMainPanel().add(message);
		this.loginFrame.getMainPanel().add(buttonLogin);
		
		this.buttonLogin.addActionListener(this);
		
		this.loginFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String email = this.username.getText();
		if (!email.matches(EMAIL_PATTERN)) {
			message.setText("Errore: username non valido");
		} else {
			String psw = this.password.getText();
		}
		
	}
	

}
