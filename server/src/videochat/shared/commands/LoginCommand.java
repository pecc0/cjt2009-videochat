/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.shared.contact.ContactInfo;

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
	
	public LoginCommand(Hashtable<String, Serializable> params) {
		super(params);
	}
	public String getUserName() {
		return (String)getParameters().get(userNameKey);
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ICommandReceiver receiver) {
		receiver.setName(getUserName());
		if (receiver.alreadyExist()){
			receiver.sendCommand(CommandFactory.createErrorMessage(Command.messageUserExist));
			receiver.disconnect();
		} else {
			receiver.addToClientsSet();
			ContactInfo contactInfo = new ContactInfo(receiver.getName());
			receiver.setContactInfo(contactInfo);
			//init the rest of the contact info fields
			
			Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
			params.put(Command.infoKey, contactInfo);
			receiver.sendToAllOther(CommandFactory.createCommand(CommandFactory.commandTypeAddFriend, params));
			receiver.initUser();
		}
	}
}
