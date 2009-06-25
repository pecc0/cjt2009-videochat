/**
 * 
 */
package videochat.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import videochat.client.commands.ClientCommandManager;

import jmapps.ui.JMPanel;

/**
 * Panel that lets the user type a message and send it,
 * and also shows what the user camera sends to the other
 * clients
 * 
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class UserPanel extends JMPanel implements KeyListener {

	private static final long serialVersionUID = -6247090909518083697L;
	private CapturePanel rightPanel = null;
	private JMPanel leftPanel = null;
	private TextArea messageText = null;
	
	public UserPanel(Frame rFrame) {
		super();
		init(rFrame);
	}
	
	private void init(Frame rFrame) {
		this.setLayout(new BorderLayout());
		rightPanel = new CapturePanel(rFrame);
		leftPanel = new JMPanel(new BorderLayout());
		
		messageText = new TextArea();
		messageText.setMinimumSize(new Dimension(200, 50));
		messageText.setPreferredSize(messageText.getMinimumSize());
		
		messageText.addKeyListener(this);
		
		leftPanel.add(messageText, BorderLayout.CENTER);
		
		this.add(leftPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n' && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == 0)){
			ClientCommandManager.getInst().sendCommand(
				ClientCommandManager.createMessageCommand(rightPanel.getUserName(), 
				messageText.getText()));
			
			messageText.setText("");
		}
		
	}
}
