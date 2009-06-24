/**
 * 
 */
package videochat.client.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;

import jmapps.ui.JMPanel;
import videochat.client.TextI18n;
import videochat.client.commands.ClientCommandManager;
import videochat.shared.commands.Command;
import videochat.shared.commands.IConnectionListener;
import videochat.shared.commands.SendAvatarCommand;
import videochat.shared.contact.ContactInfo;


/**
 * An item in the friends list. Contains a label with 
 * the friend name and the friend's avatar.
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class FriendListItem extends JMPanel implements IConnectionListener {

	private static final long serialVersionUID = -4825796395691254797L;
	private ImageDisplay can;
	private Label userName;
	private ContactInfo userInfo;
	public FriendListItem(ContactInfo info){
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		can = new ImageDisplay();
		userInfo = info;
		userName = new Label(TextI18n.getText("friendlist.name") + info.getName());
		ClientCommandManager.getInst().addConnectionCommandListener(this);
		this.add(can);
		this.add(userName);
		this.setEtchedBorder();
		Dimension dim = new Dimension(190, 170);
		this.setPreferredSize(dim);
	}
	
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command instanceof SendAvatarCommand){
			SendAvatarCommand sac = ((SendAvatarCommand) command);
			if (userInfo.getName().equals(sac.getSenderName())){
				byte[] imgData = sac.getAvatar();
				can.setJpegData(imgData);
			}
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
