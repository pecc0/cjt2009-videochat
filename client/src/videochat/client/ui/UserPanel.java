/**
 * 
 */
package videochat.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;

import jmapps.ui.JMPanel;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class UserPanel extends JMPanel {

	private static final long serialVersionUID = -6247090909518083697L;
	private JMPanel rightPanel = null;
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
		
		leftPanel.add(messageText, BorderLayout.CENTER);
		
		this.add(leftPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
	}
}
