
package videochat.server.connection;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;

import videochat.shared.ApplicationSettings;
import videochat.shared.commands.Command;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.ICommandReceiver;
import videochat.shared.connection.Connection;
import videochat.shared.connection.IConnectionListener;
import videochat.shared.contact.ContactInfo;

/**
 * The client of the server. Each client has unique name.
 * The client receives an instance of the set of all clients, so that
 * it can add/remove itself from it, iterate it, etc.
 * TODO is it ok :?
 * @author "ppetkov" (Jun 23, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 23, 2009 "ppetkov" created <br>
 */
public class ConnectedClient implements IConnectionListener, ICommandReceiver {
	private Connection connection;
	private String name;
	private ContactInfo contactInfo;
	private IClientsSet clientsSet;
	public ConnectedClient(Connection c, IClientsSet clientsSet){
		connection = c;
		connection.addConnectionListener(this);
		this.clientsSet = clientsSet;
		setName(null);
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.ICommandListener#receiveCommand(videochat.shared.commands.Command)
	 */
	@Override
	public void receiveCommand(Command command) {
		command.execute(this);
	}
	
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		clientsSet.remove(this);
		
		Hashtable<String, Serializable> params;
		params = new Hashtable<String, Serializable>();
		params.put(Command.userNameKey, getContactInfo().getName());
		sendToAllOther(CommandFactory.createCommand(CommandFactory.commandTypeRemoveuser, params));
		System.out.println(contactInfo.getName() + " disconnected");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConnectedClient && getName() != null) {
			return this.getName().equals(((ConnectedClient)obj).getName());
		} else {
			return super.equals(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getName() != null) {
			return getName().hashCode();
		} else {
			return super.hashCode();
		}
	}
	
	public void sendCommand(Command c){
		connection.sendCommand(c);
	}
	
	public void sendToAllOther(Command c){
		for (ConnectedClient client: clientsSet){
			if (!this.equals(client)){
				client.sendCommand(c);
			}
		}
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public boolean alreadyExist(){
		return clientsSet.contains(this);
	}
	public void addToClientsSet(){
		clientsSet.add(this);
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	
	public void initUser(){
		System.out.println(contactInfo.getName() + " connected");
		for (ConnectedClient client: clientsSet){
			if (!this.equals(client)){
				Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
				params.put(Command.infoKey, client.contactInfo);
				sendCommand(CommandFactory.createCommand(CommandFactory.commandTypeAddFriend, params));
			}
		}
		
		Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
		params.put(Command.infoKey, contactInfo);
		params.put(Command.messageKey, 
			ApplicationSettings.getInstance().getStringProperty("server.welcomeuser"));
		sendCommand(CommandFactory.createCommand(CommandFactory.commandTypeWelcome, params));
	}
	public void disconnect(){
		connection.removeConnectionListener(this);
	}
}
