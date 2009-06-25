package videochat.client.ui;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Hashtable;

import jmapps.ui.JMPanel;
import videochat.client.commands.ClientCommandManager;
import videochat.shared.commands.AddFriendCommand;
import videochat.shared.commands.Command;
import videochat.shared.commands.RemoveFriendCommand;
import videochat.shared.connection.IConnectionListener;

/**
 * A list of the users in the channel.
 * The items are of type {@link FriendListItem}
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */

public class FriendsList extends JMPanel implements IConnectionListener {
	private static final long serialVersionUID = 4067234392124087562L;
	private Hashtable<String, FriendListItem> friends = new Hashtable<String, FriendListItem>();

	public FriendsList(Frame rFrame) {
		super();
		init(rFrame);
	}
	private void init(Frame rFrame) {
		super.setLayout(new GridLayout(0,1));
		//this.add(new JMPanel());
		ClientCommandManager.getInst().addConnectionCommandListener(this);
		//this.setPreferredSize(new Dimension(200, 200));
		this.setLoweredBorder();
	}
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command.getClass().equals(videochat.shared.commands.AddFriendCommand.class)){
			AddFriendCommand afc = (AddFriendCommand) command;
			
			FriendListItem item = new FriendListItem(afc.getFriendInfo());
			this.add(item);
			//this.validate();
			//rootFrame.pack();
			this.getParent().getParent().validate();
			friends.put(afc.getFriendInfo().getName(), item);
		} else if (command.getClass().equals(RemoveFriendCommand.class)){
			RemoveFriendCommand rfc = (RemoveFriendCommand) command;
			FriendListItem item = friends.remove(rfc.getUserName());
			this.remove(item);
			getParent().validate();
		}
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		ClientCommandManager.getInst().addConnectionCommandListener(this);
	}
	
}
