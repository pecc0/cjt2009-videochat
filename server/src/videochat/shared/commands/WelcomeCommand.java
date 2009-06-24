
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * A command sent to the user when he has logged successfully.
 * It contains same functionality like the add friend command,
 * that's why this class extends the {@link AddFriendCommand}
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class WelcomeCommand extends AddFriendCommand {

	private static final long serialVersionUID = -1038943298206352232L;

	/**
	 * See {@link Command#Command(Hashtable)}
	 * @param params
	 */
	public WelcomeCommand(Hashtable<String, Serializable> params) {
		super(params);
		
	}
	
	
}
