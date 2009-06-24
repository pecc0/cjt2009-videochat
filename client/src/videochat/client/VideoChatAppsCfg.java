
package videochat.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jmapps.util.JMAppsCfg;

/**
 * The video chat configuration. </br>
 * It extends the Java media application configuration 
 * and is a singleton that can be used by all classes
 *  
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class VideoChatAppsCfg extends JMAppsCfg {
	private static VideoChatAppsCfg instance; 
	private int sendingFps;
	private Properties props;
	
	private VideoChatAppsCfg(){
		super();
		
		File settings = new File("settings.properties");
		InputStream is = null;
		if (settings.exists()) {
			try {
				is = new FileInputStream(settings);
			}catch (FileNotFoundException e) {
			}
		} else {
			is = String.class.getResourceAsStream("/videochat/client/settings/settings.properties");
		}
		props = new Properties();
		try {
			props.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}
		sendingFps = getSendingFps();
	}
	public static final String  KEY_SENDING_FPS = "sendingfps";
	public static VideoChatAppsCfg getInstance() {
		if (instance == null) {
			instance = new VideoChatAppsCfg();
		}
		return instance;
	}
	public void setSendingFps(int sendingFps) {
		
		props.setProperty(KEY_SENDING_FPS, "" + sendingFps);
		this.sendingFps = sendingFps;
	}
	public int getSendingFps() {
		String      objValue;
        int     result;

        objValue = props.getProperty(KEY_SENDING_FPS, "12");
    	try {
    		result = Integer.parseInt(objValue);
    	} catch (NumberFormatException e) {
    		result = 12;
		}
        
        return result;
	}
	public int getCachedSendingFps(){
		return sendingFps;
	}
	
	/* (non-Javadoc)
	 * @see jmapps.util.JMAppsCfg#save()
	 */
	@Override
	public void save() {
		super.save();
		try {
			props.store(new FileOutputStream("settings.properties"), "Videolan settigns");
		} catch (Exception e) {
			System.out.println("Can't save the settings");
		}
	}
}
