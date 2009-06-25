
package videochat.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.text.DateFormat;
import java.util.Formatter;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import videochat.client.TextI18n;
import videochat.client.commands.ClientCommandManager;
import videochat.shared.commands.AddFriendCommand;
import videochat.shared.commands.Command;
import videochat.shared.commands.IConnectionListener;
import videochat.shared.commands.Message;
import videochat.shared.commands.RemoveFriendCommand;
import videochat.shared.commands.WelcomeCommand;

import jmapps.ui.JMPanel;

/**
 * A panel that contains the message history and the users in the current channel
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ChatPanel extends JMPanel implements IConnectionListener {
	private static final long serialVersionUID = -3836423415880048340L;
	
	private FriendsList friendsList;
	private JTextArea messageHistory;
	private boolean welcomeReceived = false;
	JScrollPane pane1;
	public ChatPanel(Frame rFrame) {
		super(new BorderLayout());
		init(rFrame);
	}
	private void init(Frame rFrame) {
		friendsList = new FriendsList(rFrame);
		messageHistory = new JTextArea();
		
		messageHistory.setBorder(BorderFactory.createLoweredBevelBorder());
		messageHistory.setEditable(false);
		messageHistory.setTabSize(2);
		messageHistory.setLineWrap(true);
		pane1 = new JScrollPane(messageHistory);
		pane1.setPreferredSize(new Dimension(300, 300));
		
		JMPanel panel = new JMPanel();
		panel.setLoweredBorder();
		panel.add(friendsList);
		JScrollPane pane = new JScrollPane(panel);
		
		pane.setPreferredSize(new Dimension(230,220));
		this.add(pane, BorderLayout.EAST);
		this.add(pane1, BorderLayout.CENTER);
		
		ClientCommandManager.getInst().addConnectionCommandListener(this);
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		welcomeReceived = false;
		ClientCommandManager.getInst().addConnectionCommandListener(this);
	}
	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#receiveCommand(videochat.shared.commands.Command)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (Message.class.equals(command.getClass())){
			Message msg = (Message)command;
			String message = msg.getMessage().replaceAll("\n", "\n\t");
			String sender = msg.getSender();
			String time = DateFormat.getInstance().format(msg.getTimeSent());
			messageHistory.append("\n" + sender + " (" + time + ")" + ":\n\t" + message);
			messageHistory.setCaretPosition(messageHistory.getText().length());
			//Formatter
			/*
			JScrollBar sb = pane1.getVerticalScrollBar();
			if (sb != null) {
				sb.setValue(sb.getMaximum());
				
				System.out.println("" + sb.getMaximum() + " " + sb.getValue());
			}
			*/
		} else if (WelcomeCommand.class.equals(command.getClass())){
			WelcomeCommand wc = (WelcomeCommand) command;
			StringBuffer sb = new StringBuffer();
			Formatter f = new Formatter(sb);
			f.format(wc.getMessage(), wc.getFriendInfo().getName());
			welcomeReceived = true;
			messageHistory.append(f.toString());
			messageHistory.append("\n");
		} else if (AddFriendCommand.class.equals(command.getClass())){
			if (welcomeReceived) {
				AddFriendCommand afc = ((AddFriendCommand)command);
				messageHistory.append("(" + DateFormat.getInstance().format(afc.getTimeSent()) + ")>");
				messageHistory.append(new Formatter(new StringBuffer()).format(
					TextI18n.getText("chat.userjoined"), 
					afc.getFriendInfo().getName()).toString());
				
				messageHistory.append("\n");
			}
		} else if (RemoveFriendCommand.class.equals(command.getClass())){
			RemoveFriendCommand rfc = ((RemoveFriendCommand)command);
			messageHistory.append("(" + DateFormat.getInstance().format(rfc.getTimeSent()) + ")>");
			messageHistory.append(new Formatter(new StringBuffer()).format(
				TextI18n.getText("chat.userexit"), 
				rfc.getUserName()).toString());
			
			messageHistory.append("\n");
		}
	}
}
