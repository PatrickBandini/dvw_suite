package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Cache {
	
	private static final String PROPERTIES_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC91GjsQCzCM16ilU2VhktpyGUZy3+xAfuhDNa3uttr7lsy9PQmuoKbt+yOKzREc3chr6lRmlGaOgWCtg7rbEpuKam01MRHcCAbvBweU694D31LA2YwzuzQu6S6ESMt1JRsGTiKLrvVmcU+fVlh9fBneYHJrYiWYgrszh/l+kwedQIDAQAB";
	private static final String PROPERTIES_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL3UaOxALMIzXqKVTZWGS2nIZRnLf7EB+6EM1re622vuWzL09Ca6gpu37I4rNERzdyGvqVGaUZo6BYK2DutsSm4pqbTUxEdwIBu8HB5Tr3gPfUsDZjDO7NC7pLoRIy3UlGwZOIouu9WZxT59WWH18Gd5gcmtiJZiCuzOH+X6TB51AgMBAAECgYA+hkHw3spfRLA8+NRPFaatNvYl4uMyGCcbSrFGXuN6z012oTvxrajrRU9DHHPgv+9TRxqL6DpMhCPgg7qyTD7PLM8++a18jwvDiKIevhnBolwK0VI7xz49WTWO/e3go45PvTZY6PHLySTPLVccFUujkQspdcNLbpSvRyivavO7wQJBAOWNtQ0lWjieziLdB86sGYIhnTl6TZtOyrjf99CSRC76ngp17JkrGl5lZDiYhWzjVC+dkKAjuKPwrREILOOIUu0CQQDTsx2tts8WV4z4pg9UgHF0dPkgyr0plXpDuXFB9Iasg/fPEs7tpk6ccxXBFdqP1ojzbUQF9gNEhGS+BKVh9uCpAkB/XRKZSqI2TBY/TbepOd+lThU38N3iT+M2lD2hIaZnEnJhh3BQw5OL0GG87RMKiU8KZDf8QhWOEFlV291cGSU5AkAO2xfvE9AOelBbNpQG0wijKn49Dd0eaO3uiLno7fbxbkVak7BPvwbvNahGJ3lx9JUht/45CN2L1lqXdw4ABOoZAkBiC4rDXxVDEEhbBciCSWiQh1MXU6/9HyArwSSm2k3OqwbevHpL5hEPB6G9WDB3Mh+S6ywD1zXL2nmTOvjNjNPH";
	
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
			File rootDir = new File("C:/VSC/");
			if (!rootDir.exists()){
				rootDir.mkdirs();
			}
			this.path = rootDir.getPath() + "/config.properties";
			File config = new File(this.path);
			config.createNewFile();
			
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
		try {
			String chiper = RSA.encrypt(codice, PROPERTIES_PUBLIC_KEY);
			prop.setProperty(CODICE, chiper);
			write(); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
			return RSA.decrypt(prop.getProperty(CODICE), PROPERTIES_PRIVATE_KEY);
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
