
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.server.connection.ConnectedClient;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class RemoveFriendCommand extends Command {

	private static final long serialVersionUID = -1270231718593362292L;

	/**
	 * See {@link Command#Command(Hashtable)}
	 * @param params
	 */
	public RemoveFriendCommand(Hashtable<String, Serializable> params) {
		super(params);
	}
	
	public String getUserName(){
		return (String)getParameters().get(userNameKey);
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ConnectedClient receiver) {
		throw new RuntimeException("AddFriendCommand should not be received on teh server");
	}
}
