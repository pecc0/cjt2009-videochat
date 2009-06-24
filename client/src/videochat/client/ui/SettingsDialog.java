/**
 * 
 */
package videochat.client.ui;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;

import videochat.client.VideoChatAppsCfg;

import jmapps.ui.JMDialog;
import jmapps.ui.JMPanel;

/**
 * A dialog that allows the user to change the 
 * application settings
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class SettingsDialog extends JMDialog {

	private static final long serialVersionUID = 4745143794157304055L;
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 24;
	private JSlider framesPerSecond;
	/**
	 * Dialog used to change the current application settings
	 * @param frame
	 * @param strTitle
	 * @param boolModal
	 */
	public SettingsDialog(Frame frame, String strTitle, boolean boolModal) {
		super(frame, strTitle, boolModal);
		this.setLayout(new GridLayout(0, 1));
		
		JMPanel panelButtons = createButtonPanel ( new String[] { ACTION_OK, ACTION_CANCEL } );
		
		framesPerSecond = new JSlider(JSlider.HORIZONTAL,
		     FPS_MIN, FPS_MAX, VideoChatAppsCfg.getInstance().getSendingFps());
		
		//Turn on labels at major tick marks.
		framesPerSecond.setMajorTickSpacing(10);
		framesPerSecond.setMinorTickSpacing(1);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		this.add(framesPerSecond);
		this.add(panelButtons);
		this.pack();
	}

	/* (non-Javadoc)
	 * @see jmapps.ui.JMDialog#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand().equals(ACTION_OK)){
			setAction(ACTION_OK);
			VideoChatAppsCfg.getInstance().setSendingFps(framesPerSecond.getValue());
			this.setVisible(false);
		} else if (event.getActionCommand().equals(ACTION_CANCEL)) {
			this.setVisible(false);
		}
	}
}
