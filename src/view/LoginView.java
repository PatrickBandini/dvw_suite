package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.SHA;

import com.fasterxml.jackson.databind.ObjectMapper;

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
			char[] psw = this.password.getPassword();
			
			try {
				String chiperText = SHA.SHA1(new String(psw));
				callLogin(email, chiperText, main.main.SIGLA, main.main.VERSIONE);
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void callLogin(String mail, String chiper, String sigla, String versione) {
		HttpClient client = HttpClient.newHttpClient();
		
		var values = new HashMap<String, String>() {{
            put("mail", mail);
            put("psw", chiper);
            put("sigla", sigla);
            put("versione", versione);
        }};
        
		try {
			var objectMapper = new ObjectMapper();
	        String requestBody = objectMapper.writeValueAsString(values);
			
	        HttpRequest request = HttpRequest.newBuilder()
	        		.uri(URI.create("http://www.allaroundvolley.com/abbonamento.php"))
	        		.timeout(Duration.ofMinutes(1))
	        	    .header("Content-Type", "application/x-www-form-urlencoded")
	        	    .POST(BodyPublishers.ofString(requestBody))
	        	    .build();

	        HttpResponse<String> response;
	        
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//risponde auth:Negato account di test: prova@prova.it psw: prova
			System.out.println(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

        
	}
	

}
