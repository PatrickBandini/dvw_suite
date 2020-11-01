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
	public static final String PUBLIC_KEY_SERVER = "publicKeyServer";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
	
	private static Cache instance = null;
	
	private Properties prop;
	private String path;
	
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
	
	public String getUsername() {
		read();
		try {
			return prop.getProperty(USER);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getCodice() {
		read();
		try {
			return prop.getProperty(CODICE);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getPrivateKey() {
		read();
		try {
			return prop.getProperty(PRIVATE_KEY);
		} catch (Exception e) {
			return "";
		}
	}
}
