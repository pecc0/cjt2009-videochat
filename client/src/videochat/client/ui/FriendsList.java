/**
 * 
 */
package videochat.client.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.util.HashSet;

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
		setSize(new Dimension(128, 128));
	}
	
	public void setImage(Image i) {
		image = i;
		repaint();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 128, 128);
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
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		can = new MyCanvas();
		userName = new Label("name: " + info.getName());
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
		
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		
	}
	
}
public class FriendsList extends JMPanel implements IConnectionListener {
	private static final long serialVersionUID = 4067234392124087562L;
	HashSet<ContactInfo> friends = new HashSet<ContactInfo>();
	Frame rootFrame;
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
		rootFrame = rFrame;
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
			//this.validate();
			//rootFrame.pack();
			this.getParent().getParent().validate();
		}
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		
	}
	
}
