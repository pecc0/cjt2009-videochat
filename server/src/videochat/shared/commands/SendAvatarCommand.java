/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.server.connection.ConnectedClient;

/**
 * A command that contains a frame from the "movie avatar"
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class SendAvatarCommand extends Command {

	private static final long serialVersionUID = -1590425762864037873L;

	/**
	 * See {@link Command#Command(Hashtable)}
	 * @param params
	 */
	public SendAvatarCommand(Hashtable<String, Serializable> params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ConnectedClient receiver) {
		
		receiver.sendToAllOther(this);
	}

	public byte[] getAvatar(){
		return (byte[])getParameters().get(avatarKey);
	}
	
	public String getSenderName(){
		return (String) getParameters().get(userNameKey);
	}
}
