
package videochat.client;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton that deals with the internationalization
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class TextI18n {
	private ResourceBundle labels; 
	
	private static TextI18n instance; 
	
	private TextI18n(){
		labels = ResourceBundle.getBundle("videochat.client.texts.Texts", Locale.getDefault());
	}
	public static String getText(String key){
		if (instance == null) {
			instance = new TextI18n();
		}
		return instance.labels.getString(key);
	}
}
