
package videochat.client;

import java.io.FileOutputStream;

import jmapps.util.JMAppsCfg;
import videochat.shared.ApplicationSettings;

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
public class VideoChatAppsCfg extends ApplicationSettings {
	
	private int sendingFps;
	
	private static JMAppsCfg jmCfg; 
	private VideoChatAppsCfg(){
		super("settings.properties", "/videochat/client/settings/settings.properties");
		sendingFps = getSendingFps();
		jmCfg = new JMAppsCfg();
	}
	public static final String  KEY_SENDING_FPS = "sendingfps";
	public static final String  KEY_JPEG_QUAL = "jpegquality";
	public static VideoChatAppsCfg getInstance() {
		if (instance == null) {
			instance = new VideoChatAppsCfg();
		}
		return (VideoChatAppsCfg)instance;
	}
	public void setSendingFps(int sendingFps) {
		
		props.setProperty(KEY_SENDING_FPS, "" + sendingFps);
		this.sendingFps = sendingFps;
	}
	public int getSendingFps() {
		return getIntProperty(KEY_SENDING_FPS, 12);
	}
	public int getCachedSendingFps(){
		return sendingFps;
	}
	
	public void setJpegQuality(int jpegQual) {
		props.setProperty(KEY_JPEG_QUAL, "" + jpegQual);
	}
	public int getJpegQuality() {
        return getIntProperty(KEY_JPEG_QUAL, 80);
	}
	
	public void save() {
		jmCfg.save();
		try {
			props.store(new FileOutputStream("settings.properties"), "Videolan settigns");
		} catch (Exception e) {
			System.out.println("Can't save the settings");
		}
	}
	
	public static JMAppsCfg getJmCfg() {
		return jmCfg;
	}
}
