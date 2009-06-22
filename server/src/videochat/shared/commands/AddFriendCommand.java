
package videochat.shared.commands;

import videochat.shared.contact.ContactInfo;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class AddFriendCommand implements ICommand {

	private static final long serialVersionUID = 3046234845370167608L;
	private ContactInfo friend;
	public void setFriend(ContactInfo friend) {
		this.friend = friend;
	}
	public ContactInfo getFriend() {
		return friend;
	}
}
