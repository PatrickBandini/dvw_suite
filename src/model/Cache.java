package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Cache {
	
	public static final String USER = "user";
	public static final String CODICE = "codice";
	public static final String STATO = "stato";
	public static final String PUBLIC_KEY_SERVER = "publicKeyServer";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
	
	private static Cache instance = null;
	
	private Properties prop;
	private String path;
	
	//Properties:
	private String user;
	private String stato;
	private String codice;
	private String publicKeyServer;
	private String publicKey;
	private String privateKey;
	
	private Cache() {
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			this.path = rootPath + "config.properties";
			 
			this.prop = new Properties();
			this.prop.load(new FileInputStream(this.path));
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }
	
	private void write() {
		try {
			prop.store(new FileOutputStream(this.path), null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void read() {
		try {
			prop.load(new FileInputStream(this.path));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void setUsername(String user) {
		prop.setProperty(USER, user);
		write();
	}
	
	public void setCodice(String codice) {
		prop.setProperty(CODICE, codice);
		write(); 
	}
	
	public void setStato(String stato) {
		prop.setProperty(STATO, stato);
		write(); 
	}
	
	public void setPublicKeyServer(String key) {
		prop.setProperty(PUBLIC_KEY_SERVER, key);
		write();
	}
	
	public void setPublicKey(String key) {
		prop.setProperty(PUBLIC_KEY, key);
		write();
	}
	
	public void setPrivateKey(String key) {
		prop.setProperty(PRIVATE_KEY, key);
		write();
	}
	
	public boolean isValido() {
		return null!=stato && main.main.VALIDO.equals(stato);
	}
	
	public boolean isInApprovazione() {
		return null!=stato && main.main.IN_APPROVAZIONE.equals(stato);
	}
	
	public boolean isNegato() {
		return !isValido() && !isInApprovazione();
	}
	
}
