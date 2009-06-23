/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Command sent to the server on logging in.
 * Contains the user name
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class LoginCommand extends Command {
	private static final long serialVersionUID = 884132537055034465L;
	public final static String userNameKey = "username";
	public LoginCommand(Hashtable<String, Serializable> params) {
		super(params);
	}
	public String getUserName() {
		return (String)getParameters().get(userNameKey);
	}
}
