/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;


/**
 * Used to create commands
 *
 * @author "ppetkov" (Jun 23, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 23, 2009 "ppetkov" created <br>
 */
public class CommandFactory {

	/**
	 * Commands factory method. Returns null if the name is not recognized
	 * @param name the name of the command
	 * @param parameters a hash table that contains the parameters
	 * @return the new command
	 */
	public static Command createCommand(String name, Hashtable<String, Serializable> parameters) {
		Command result = null;
		if ("login".equals(name) ) {
			result = new LoginCommand(parameters);
		} else if ("addfriend".equals(name)){
			result = new AddFriendCommand(parameters);
		} else if ("welcome".equals(name)){
			result = new WelcomeCommand(parameters);
		} else if ("removeuser".equals(name)){
			result = new RemoveFriendCommand(parameters);
		} else if ("sendavatar".equals(name)){
			result = new SendAvatarCommand(parameters);
		}
		return result;
	}
	
	/**
	 * Creates an error message command. The message is passed as string
	 * @param errorString The message
	 * @return An error message command
	 */
	public static Command createErrorMessage(String errorString){
		Hashtable<String, Serializable> parameters = new Hashtable<String, Serializable>();
		parameters.put(ErrorMessage.messageKey, errorString);
		return new ErrorMessage(parameters);
	}
	
}
