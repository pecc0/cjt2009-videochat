package videochat.shared.commands;

import videochat.shared.contact.ContactInfo;

public interface ICommandReceiver {
	void setName(String name);
	boolean alreadyExist();
	void sendCommand(Command c);
	void sendToAllOther(Command c);
	void disconnect();
	void addToClientsSet();
	String getName();
	void setContactInfo(ContactInfo ci);
	void initUser();
}
