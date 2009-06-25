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

	public final static String commandTypeAddFriend = "addfriend";
	
	public final static String commandTypeLogin = "login";
	
	public final static String commandTypeWelcome = "welcome";
	
	public final static String commandTypeRemoveuser = "removeuser";
	
	public final static String commandTypeSendAvatar = "sendavatar";
	/**
	 * Commands factory method. Returns null if the name is not recognized
	 * @param name the name of the command
	 * @param parameters a hash table that contains the parameters
	 * @return the new command
	 */
	public static Command createCommand(String name, Hashtable<String, Serializable> parameters) {
		Command result = null;
		if (commandTypeLogin.equals(name) ) {
			result = new LoginCommand(parameters);
		} else if (commandTypeAddFriend.equals(name)){
			result = new AddFriendCommand(parameters);
		} else if (commandTypeWelcome.equals(name)){
			result = new WelcomeCommand(parameters);
		} else if (commandTypeRemoveuser.equals(name)){
			result = new RemoveFriendCommand(parameters);
		} else if (commandTypeSendAvatar.equals(name)){
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
	/**
	 * Creates a text message command.
	 * See {@link Message}
	 * @param userName The name of the user that send the message
	 * @param message The message
	 * @return The new command
	 */
	public static Command createMessageCommand(String userName, String message){
		Hashtable<String, Serializable> parameters = new Hashtable<String, Serializable>();
		parameters.put(ErrorMessage.messageKey, message);
		parameters.put(ErrorMessage.userNameKey, userName);
		return new Message(parameters);
	}
}
