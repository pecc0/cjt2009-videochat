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

import videochat.shared.commands.AddFriendCommand;
import videochat.shared.commands.ICommand;
import videochat.shared.commands.ICommandListener;
import videochat.shared.contact.Contact;

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

class FriendLabel extends JMPanel implements ICommandListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4825796395691254797L;
	private MyCanvas can;
	private Label userName;
	FriendLabel(){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		can = new MyCanvas();
		userName = new Label();
		
		this.add(can);
		this.add(userName);
	}
	
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(ICommand command) {
		
	}
	
}
public class FriendsList extends JMPanel implements ICommandListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067234392124087562L;
	HashSet<Contact> friends = new HashSet<Contact>();
	public FriendsList(Frame rFrame) {
		super();
		init(rFrame);
	}
	private void init(Frame rFrame) {
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	/* (non-Javadoc)
	 * @see videochat.commands.ICommandListener#receiveCommand(videochat.commands.ICommand)
	 */
	@Override
	public void receiveCommand(ICommand command) {
		if (command instanceof AddFriendCommand){
			
		}
	}
	
}
