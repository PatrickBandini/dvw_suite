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
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import model.RSA;
import model.SHA;

public class LoginView implements ActionListener {
	
	private static final String EMAIL_PATTERN = 
		    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
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
				this.callLogin(email, chiperText, main.main.SIGLA, main.main.VERSIONE);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					this.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}
	
	private void callLogin(String mail, String chiper, String sigla, String versione) throws Exception {
		HttpPost post = new HttpPost("http://www.allaroundvolley.com/abbonamento.php");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("mail", mail));
        urlParameters.add(new BasicNameValuePair("psw", chiper));
        urlParameters.add(new BasicNameValuePair("sigla", sigla));
        urlParameters.add(new BasicNameValuePair("versione", versione));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); 
        		CloseableHttpResponse response = httpClient.execute(post)) {
        	//Risponde: {"auth":"InApprovazione","codice":"35","key":"MIGfMA0G..."}
        	
        	JSONObject responseLogin = new JSONObject(EntityUtils.toString(response.getEntity()));
        	String auth = responseLogin.getString("auth");
        	String codice = responseLogin.getString("codice");
        	String key = responseLogin.getString("key");
        	
        	if (null != codice && !"".equals(codice) && null != key && !"".equals(key)) {
        		KeyPair pair = RSA.generateKeyPair();
        		HttpPost postSendKey = new HttpPost("http://www.allaroundvolley.com/controlloperiodico.php");
        		
        		byte[] publicKeyBytes = Base64.getEncoder().encode(pair.getPublic().getEncoded());
                String pubKey = new String(publicKeyBytes);
                pubKey = pubKey.replace("\n", "");
        		
        		List<NameValuePair> urlParametersSendKey = new ArrayList<>();
        		urlParametersSendKey.add(new BasicNameValuePair("key", pubKey));
        		urlParametersSendKey.add(new BasicNameValuePair("codice", codice));
        		urlParametersSendKey.add(new BasicNameValuePair("sigla", sigla));
        		urlParametersSendKey.add(new BasicNameValuePair("versione", versione));
        		
        		postSendKey.setEntity(new UrlEncodedFormEntity(urlParametersSendKey));
        		
        		try (CloseableHttpClient httpClientSendKey = HttpClients.createDefault(); 
        				CloseableHttpResponse responseSendKey = httpClientSendKey.execute(postSendKey)) {
        			//Risponde: {"auth":"Luft..."}
        			
        			byte[] privateKeyBytes = Base64.getEncoder().encode(pair.getPrivate().getEncoded());
                    String prvKey = new String(privateKeyBytes);
                    prvKey = prvKey.replace("\n", "");
                    
                    JSONObject response2 = new JSONObject(EntityUtils.toString(responseSendKey.getEntity()));
                    String authCripted = response2.getString("auth");
                    
                    String responseAuth = RSA.decrypt(authCripted, prvKey);
                    if (responseAuth.equals(main.main.IN_APPROVAZIONE) || responseAuth.equals(main.main.VALIDO)) {
                    	//login corretto
                    	//disableLogin + textView in approvazione
                    }
        		}
        		
        	}
            
        }
	}
	
	private void close() throws IOException {
        httpClient.close();
    }
	

}
