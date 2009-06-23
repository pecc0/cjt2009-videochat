/**
 * 
 */
package videochat.client.ui;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.util.HashSet;

import javax.swing.BoxLayout;

import videochat.client.commands.ClientCommandManager;
import videochat.shared.commands.AddFriendCommand;
import videochat.shared.commands.Command;
import videochat.shared.commands.IConnectionListener;
import videochat.shared.contact.ContactInfo;

import jmapps.ui.JMPanel;

/**
 * Contains a list of the users in the channel. Also shows their avatars.
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */

class MyCanvas extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = -554909844771551841L;
	private Image image;
	public MyCanvas() {
		super();
	}
	
	public void setImage(Image i) {
		image = i;
		repaint();
	}
	
	public void paint(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0,0, this);
		}
	}
}

class FriendLabel extends JMPanel implements IConnectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4825796395691254797L;
	private MyCanvas can;
	private Label userName;
	FriendLabel(ContactInfo info){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		can = new MyCanvas();
		userName = new Label(info.getName());
		ClientCommandManager.getInst().addConnectionCommandListener(this);
		this.add(can);
		this.add(userName);
	}
	
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(Command command) {
		
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		
	}
	
}
public class FriendsList extends JMPanel implements IConnectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067234392124087562L;
	HashSet<ContactInfo> friends = new HashSet<ContactInfo>();
	public FriendsList(Frame rFrame) {
		super();
		init(rFrame);
	}
	private void init(Frame rFrame) {
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ClientCommandManager.getInst().addConnectionCommandListener(this);
	}
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command instanceof AddFriendCommand){
			AddFriendCommand afc = (AddFriendCommand) command;
			FriendLabel item = new FriendLabel(afc.getFriendInfo());
			this.add(item);
			this.validate();
		}
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		
	}
	
}
