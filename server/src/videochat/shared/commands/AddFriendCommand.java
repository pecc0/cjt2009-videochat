
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.shared.contact.ContactInfo;

/**
 * Command for adding a friend to the friends list. 
 * Contains the friend info 
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class AddFriendCommand extends Command {
	private static final long serialVersionUID = 3046234845370167608L;
	public final static String infoKey = "info";
	public AddFriendCommand(Hashtable<String, Serializable> params) {
		super(params);
	}
	
	public ContactInfo getFriendInfo(){
		return (ContactInfo)getParameters().get(infoKey);
	}
}
