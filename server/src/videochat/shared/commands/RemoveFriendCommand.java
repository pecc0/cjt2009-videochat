
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;


/**
 * Command that instructs the clients to remove an user from their list 
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
		getParameters().put(dateTime, new Date());
	}
	
	public String getUserName(){
		return (String)getParameters().get(userNameKey);
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ICommandReceiver receiver) {
		throw new RuntimeException("AddFriendCommand should not be received on teh server");
	}
	
	public Date getTimeSent(){
		return (Date)parameters.get(dateTime);
	}
}
