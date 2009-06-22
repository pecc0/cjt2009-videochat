
package videochat.commands;

import videochat.contact.Contact;

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
	private Contact friend;
	public void setFriend(Contact friend) {
		this.friend = friend;
	}
	public Contact getFriend() {
		return friend;
	}
}
