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
		}
		return result;
	}
	
	
	
}
