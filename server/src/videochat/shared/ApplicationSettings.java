/**
 * 
 */
package videochat.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * I need to store somewhere a Properties instance for 
 * use on server, and I need to use the functionality for creating this 
 * Properties instance in the client application. 
 * So this is a singleton that can be extended 
 *
 * @author "ppetkov" (Jun 25, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 25, 2009 "ppetkov" created <br>
 */
public class ApplicationSettings {
	protected Properties props;
	protected static ApplicationSettings instance; 
	public static ApplicationSettings getInstance(){
		if (instance == null) {
			instance = new ApplicationSettings("settings.properties", "settings.properties");
		}
		return (ApplicationSettings)instance;
	}
	protected ApplicationSettings(String fileName, String resourceName){
		File settings = new File(fileName);
		InputStream is = null;
		if (settings.exists()) {
			try {
				is = new FileInputStream(settings);
			}catch (FileNotFoundException e) {
			}
		} else {
			is = ApplicationSettings.class.getResourceAsStream(resourceName);
		}
		props = new Properties();
		try {
			props.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getStringProperty(String key){
		return props.getProperty(key);
	}
	public int getIntProperty(String key){
		String      objValue;
        int     result;

        objValue = props.getProperty(key);
    	try {
    		result = Integer.parseInt(objValue);
    	} catch (NumberFormatException e) {
    		result = 12;
		}
        
        return result;
	}
}
