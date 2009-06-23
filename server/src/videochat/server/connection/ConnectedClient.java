
package videochat.server.connection;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.shared.commands.AddFriendCommand;
import videochat.shared.commands.Command;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.IConnectionListener;
import videochat.shared.commands.LoginCommand;
import videochat.shared.connection.Connection;
import videochat.shared.contact.ContactInfo;

/**
 * The client of the server. Each client has unique name.
 * The client adds himself to the {@link ConnectionsManager#clients} set once he receive his name from the client application
 * @author "ppetkov" (Jun 23, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 23, 2009 "ppetkov" created <br>
 */
public class ConnectedClient implements IConnectionListener{
	private Connection connection;
	private String name;
	private ContactInfo contactInfo;
	ConnectedClient(Connection c){
		connection = c;
		connection.addCommandListener(this);
		name = null;
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.ICommandListener#receiveCommand(videochat.shared.commands.Command)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command instanceof LoginCommand){
			name = ((LoginCommand)command).getUserName();
			ConnectionsManager.clients.add(this);
			contactInfo = new ContactInfo();
			contactInfo.setName(name);
			//init the rest of the contact info fields
			
			Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
			params.put(AddFriendCommand.infoKey, contactInfo);
			System.out.println("Sending add friend to all " + contactInfo.getName());
			ConnectionsManager.sendToAll(CommandFactory.createCommand("addfriend", params));
		}
	}
	
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		ConnectionsManager.clients.remove(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConnectedClient && name != null) {
			return this.name.equals(((ConnectedClient)obj).name);
		} else {
			return super.equals(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (name != null) {
			return name.hashCode();
		} else {
			return super.hashCode();
		}
	}
	
	public void sendCommand(Command c){
		connection.sendCommand(c);
	}
	
}
