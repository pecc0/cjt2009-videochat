/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

import videochat.server.connection.ConnectedClient;

/**
 * A text message. This command is used to transfer messages between the
 * clients. On the server, the message is enriched with the server
 * time and sent to all. 
 *
 * @author "ppetkov" (Jun 25, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 25, 2009 "ppetkov" created <br>
 */
public class Message extends Command {
	private static final long serialVersionUID = -3764445964373782949L;

	/**
	 * See {@link Command#Command(Hashtable)}
	 * @param params
	 */
	public Message(Hashtable<String, Serializable> params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ConnectedClient receiver) {
		getParameters().put(dateTime, new Date());
		receiver.sendToAllOther(this);
		receiver.sendCommand(this);
	}

	public String getMessage(){
		return (String)parameters.get(messageKey);
	}
	
	public String getSender(){
		return (String)parameters.get(userNameKey);
	}
	
	public Date getTimeSent(){
		return (Date)parameters.get(dateTime);
	}
}
