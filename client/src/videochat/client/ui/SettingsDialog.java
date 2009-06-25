/**
 * 
 */
package videochat.client.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import videochat.client.TextI18n;
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
public class SettingsDialog extends JMDialog 
	implements ChangeListener {

	private static final long serialVersionUID = 4745143794157304055L;
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 24;
	private JSlider framesPerSecond;
	private Label fpsLabel;
	private JSlider jpegQualSlider;
	private Label jpegLabel;
	/**
	 * Dialog used to change the current application settings
	 * @param frame
	 * @param strTitle
	 * @param boolModal
	 */
	public SettingsDialog(Frame frame, String strTitle, boolean boolModal) {
		super(frame, strTitle, boolModal);
		GridBagLayout g = new GridBagLayout(); 
		this.setLayout(g);
		GridBagConstraints c = new GridBagConstraints();

		JMPanel panelButtons = createButtonPanel ( new String[] { ACTION_OK, ACTION_CANCEL } );
		
		framesPerSecond = new JSlider(JSlider.HORIZONTAL,
		     FPS_MIN, FPS_MAX, VideoChatAppsCfg.getInstance().getSendingFps());
		//Turn on labels at major tick marks.
		framesPerSecond.setMajorTickSpacing(10);
		framesPerSecond.setMinorTickSpacing(1);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		framesPerSecond.addChangeListener(this);
		
		jpegQualSlider = new JSlider(JSlider.HORIZONTAL,
			     0, 100, VideoChatAppsCfg.getInstance().getJpegQuality());
		jpegQualSlider.setMajorTickSpacing(10);
		jpegQualSlider.setMinorTickSpacing(5);
		jpegQualSlider.setPaintTicks(true);
		jpegQualSlider.addChangeListener(this);
		
		fpsLabel = new Label();
		jpegLabel = new Label();
		stateChanged(new ChangeEvent(framesPerSecond));
		stateChanged(new ChangeEvent(jpegQualSlider));
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(fpsLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		this.add(framesPerSecond, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(jpegLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		this.add(jpegQualSlider, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		this.add(panelButtons, c);
		
		g.columnWidths = new int[2];
		g.columnWidths[0] = 150;
		
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
			VideoChatAppsCfg.getInstance().setJpegQuality(jpegQualSlider.getValue());
			VideoChatAppsCfg.getInstance().save();
			this.setVisible(false);
		} else if (event.getActionCommand().equals(ACTION_CANCEL)) {
			this.setVisible(false);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == framesPerSecond){
			fpsLabel.setText(TextI18n.getText("settigngs.fps") + framesPerSecond.getValue());
		} else if (e.getSource() == jpegQualSlider){
			jpegLabel.setText(TextI18n.getText("setting.jpeg") + jpegQualSlider.getValue() + "%");
		}
		this.pack();
	}
}
