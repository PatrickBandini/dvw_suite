package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Cache {
	
	private static final String PATH_PROPERTIES = "C:/VSCconfig.properties";
	
	private Properties prop;
	

	public Cache() {
		
		/*try (InputStream input = new FileInputStream(PATH_PROPERTIES)) {
			this.prop = new Properties();
            this.prop.load(input);
        } catch (Exception ex) {
        	try {
        		OutputStream output = new FileOutputStream(PATH_PROPERTIES);
    			this.prop = new Properties();
                this.prop.store(output, null);
        	} catch (Exception ex2) {
        		ex2.printStackTrace();
        	}
        }*/
		
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "config.properties";
			 
			this.prop = new Properties();
			prop.setProperty("db.url", "localhost");
            prop.setProperty("db.user", "mkyong");
            prop.setProperty("db.password", "password");

            // save properties to project root folder
            prop.store(new FileOutputStream(appConfigPath), null);
			//this.prop.load(new FileInputStream(appConfigPath));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		 
	}
	
	public void setUsername(String user) {
		prop.setProperty("user", user);
	}
	
}
