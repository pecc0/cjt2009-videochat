/**
 * 
 */
package videochat.client.ui;

import java.awt.Frame;
import java.awt.LayoutManager;

import jmapps.ui.JMPanel;

/**
 * A panel that contains the message history and the users in the current channel
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ChatPanel extends JMPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836423415880048340L;
	
	public ChatPanel(Frame rFrame) {
		super();
		init(rFrame);
	}
	public ChatPanel(Frame rFrame, LayoutManager managerLayout) {
		super(managerLayout);
		init(rFrame);
	}
	
	private void init(Frame rFrame) {
		
	}
}
