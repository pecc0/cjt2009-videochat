package videochat.client.ui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.bean.playerbean.MediaPlayer;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.util.BufferToImage;

import jmapps.jmstudio.CaptureControlsDialog;
import jmapps.jmstudio.CaptureDialog;
import jmapps.ui.JMPanel;
import jmapps.ui.MessageDialog;
import jmapps.ui.VideoPanel;
import jmapps.util.CDSWrapper;
import jmapps.util.JMFUtils;
import videochat.client.TextI18n;
import videochat.client.VideoChatAppsCfg;
import videochat.client.commands.ClientCommandManager;
import videochat.shared.commands.Command;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.WelcomeCommand;
import videochat.shared.connection.IConnectionListener;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.media.util.JMFI18N;

/**
 * A panel that contain the message text and the user avatar (media player) 
 *
 * @author "ppetkov" (Jun 21, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 21, 2009 "ppetkov" created <br>
 */
public class CapturePanel extends JMPanel 
implements ControllerListener, ItemListener, IConnectionListener, WindowListener, Runnable {
	private static final long serialVersionUID = -1546407792541586669L;
	private String                  nameCaptureDeviceAudio = null;
	private String                  nameCaptureDeviceVideo = null;

	private DataSource              dataSourceCurrent = null;
	private CaptureControlsDialog   dlgCaptureControls = null;
	protected VideoPanel        panelVideo = null;
	protected Checkbox videoOnOff = null;
	private Label nameLabel;
	
	private String userName;
	
	protected MediaPlayer		mediaPlayerCurrent; 
	private Frame rootFrame;
	private boolean running;
	
	public CapturePanel(Frame rFrame) {
		super();
		init(rFrame);
	}
	
	private void init(Frame rFrame) {
		userName = "";
		rootFrame = rFrame;

		videoOnOff = new Checkbox(TextI18n.getText("capture"));
		nameLabel = new Label("name");
		
		JMPanel panel = new JMPanel(new GridLayout(0,1));
		panel.add(nameLabel);
		panel.add(videoOnOff );
		
		this.add(panel);
		
		videoOnOff.addItemListener(this);
		rFrame.addWindowListener(this);
		
		ClientCommandManager.getInst().addConnectionCommandListener(this);
		
		new Thread(this).start();
		running = true;
	}
    protected void processRealizeComplete ( RealizeCompleteEvent event ) {
    	panelVideo = new VideoPanel ( mediaPlayerCurrent );
        panelVideo.setZoom ( 1.0 );
        this.add ( panelVideo, BorderLayout.NORTH);
        mediaPlayerCurrent.prefetch();
        
    }
    
    protected void processPrefetchComplete ( PrefetchCompleteEvent event ) {
        mediaPlayerCurrent.start();
        rootFrame.pack();
    }
    
    public void killCurrentPlayer () {
    	if ( dlgCaptureControls != null ) {
            dlgCaptureControls.dispose ();
    	}
        dlgCaptureControls = null;
        
        if (dataSourceCurrent != null) {
        	dataSourceCurrent.disconnect();
        	dataSourceCurrent = null;
        }
        
        if (mediaPlayerCurrent != null) {
        	mediaPlayerCurrent.close();
        }
        if (panelVideo != null) {
        	this.remove(panelVideo);
        	panelVideo = null;
        	rootFrame.pack();
        }
    }

	public void open ( DataSource dataSource ) {
		open (  dataSource, true  );
	}

	public void open ( DataSource dataSource, boolean killPrevious ) {
		MediaPlayer     mediaPlayer;
		boolean         boolResult;
		
		mediaPlayer = jmapps.util.JMFUtils.createMediaPlayer ( dataSource, rootFrame );
		boolResult = open ( mediaPlayer, killPrevious );
		if ( boolResult == true )
		dataSourceCurrent = dataSource;
	}

	public boolean open ( MediaPlayer mediaPlayer ) {
		return open ( mediaPlayer, true );
	}

	public boolean open ( MediaPlayer mediaPlayer, boolean killPrevious ) {

		if ( mediaPlayer == null ){
			return ( false );
		}
		
		if (killPrevious){ 
			killCurrentPlayer ();
		}

		mediaPlayer.setPlaybackLoop ( false );
		mediaPlayer.setFixedAspectRatio ( false );
		mediaPlayer.setPopupActive ( false );
		mediaPlayer.setControlPanelVisible ( false );
		mediaPlayerCurrent = mediaPlayer;
		mediaPlayer.addControllerListener(this);
		mediaPlayer.realize();

		return ( true );
	}
	
	public void captureMedia () {
		CaptureDialog       dialogCapture;
		DataSource          dataSource;
		CaptureDeviceInfo   cdi;

		nameCaptureDeviceAudio = null;
		nameCaptureDeviceVideo = null;
		
		dialogCapture = new CaptureDialog ( rootFrame, VideoChatAppsCfg.getJmCfg() );
		dialogCapture.setVisible(true);
		if (dialogCapture.getAction() == CaptureDialog.ACTION_CANCEL) {
			return;
		}
		//cdi = dialogCapture.getAudioDevice();
		//if ( cdi != null  &&  dialogCapture.isAudioDeviceUsed() )
		//nameCaptureDeviceAudio = cdi.getName();
		cdi = dialogCapture.getVideoDevice();
		if ( cdi != null  &&  dialogCapture.isVideoDeviceUsed() )
		nameCaptureDeviceVideo = cdi.getName();
		dataSource = JMFUtils.createCaptureDataSource ( nameCaptureDeviceAudio,
				dialogCapture.getAudioFormat(),
				nameCaptureDeviceVideo,
				dialogCapture.getVideoFormat() );

		if ( dataSource != null ) {

			if (dataSource instanceof CaptureDevice
					&&  dataSource instanceof PushBufferDataSource) {
				DataSource cdswrapper = new CDSWrapper((PushBufferDataSource)dataSource);
				dataSource = cdswrapper;
				try {
					cdswrapper.connect();
				}
				catch (IOException ioe) {
					dataSource = null;
					nameCaptureDeviceAudio = null;
					nameCaptureDeviceVideo = null;
					MessageDialog.createErrorDialog ( rootFrame,
							JMFI18N.getResource("jmstudio.error.captureds") );
				}
			}

			open ( dataSource );
			if ( dataSource != null ) {
				dlgCaptureControls = new CaptureControlsDialog (  rootFrame, dataSource );
				if ( dlgCaptureControls.isEmpty() ) {
					dlgCaptureControls = null;
				}
				else {
					//                    dlgCaptureControls.setVisible ( true );
				}
			}
		}
		else {
			nameCaptureDeviceAudio = null;
			nameCaptureDeviceVideo = null;
			MessageDialog.createErrorDialog ( rootFrame,
					JMFI18N.getResource("jmstudio.error.captureds") );
		}
	}

	@Override
	public void controllerUpdate(ControllerEvent event) {
		if ( event instanceof RealizeCompleteEvent ) {
            processRealizeComplete ( (RealizeCompleteEvent) event );
        } else if ( event instanceof PrefetchCompleteEvent ) {
            processPrefetchComplete ( (PrefetchCompleteEvent) event );
        }
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == videoOnOff){
			if (videoOnOff.getState()){
				captureMedia();
			} else {
				killCurrentPlayer();
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

	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#receiveCommand(videochat.shared.commands.Command)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command instanceof WelcomeCommand){
			WelcomeCommand wc = (WelcomeCommand) command;
			userName = wc.getFriendInfo().getName();
			nameLabel.setText(TextI18n.getText("friendlist.name") + userName);
			getParent().validate();
		}
	}

	private void sendAvatar() {
		if (mediaPlayerCurrent != null && 
			mediaPlayerCurrent.getState() == MediaPlayer.Started) {
			ByteArrayOutputStream jpeg = new ByteArrayOutputStream();
			JPEGImageEncoder encoder =  JPEGCodec.createJPEGEncoder(jpeg);
			
			FrameGrabbingControl fgc = (FrameGrabbingControl)mediaPlayerCurrent.getControl("javax.media.control.FrameGrabbingControl");
			Buffer buf = fgc.grabFrame();
			BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());// Convert it to an image Bufferimg = btoi.createImage(buf);//Creating Image from Buffer
			
			Image img = btoi.createImage(buf);
			if (img != null) {
				int resizedWidth, resizedHeight;
				double aspectRatio = ((double)img.getWidth(null)) / img.getHeight(null);
				if (aspectRatio > 1) {
					resizedWidth = 128;
					resizedHeight = (int)Math.floor(128 / aspectRatio);
				} else {
					resizedHeight = 128;
					resizedWidth = (int)Math.floor(128 * aspectRatio);
				}
				BufferedImage resizedImage = new BufferedImage(
						resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
				
				resizedImage.getGraphics().drawImage(img, 
					0, 0, resizedImage.getWidth(null), resizedImage.getHeight(null), 
					0, 0, img.getWidth(null), img.getHeight(null), null);
				
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(resizedImage);
				float quality = ((float)VideoChatAppsCfg.getInstance().getJpegQuality()) / 100.f;
				jep.setQuality(quality , 
						false);
				try {
					encoder.encode(resizedImage, jep);
				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] jpegData = jpeg.toByteArray();
				
				try {
					FileOutputStream fis = new FileOutputStream("test.jpg");
					fis.write(jpegData);
					fis.close();
				} catch (Exception e) {
				}
				
				Hashtable<String, Serializable> parameters = new Hashtable<String, Serializable>();
				
				parameters.put(Command.avatarKey, jpegData);
				parameters.put(Command.userNameKey, userName);
				Command c = ClientCommandManager.createCommand(CommandFactory.commandTypeSendAvatar, parameters);
				ClientCommandManager.getInst().sendCommand(c);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		killCurrentPlayer();
		running = false;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(running){
			int fps = VideoChatAppsCfg.getInstance().getCachedSendingFps();
			if (fps > 0) {
				long startTime = System.currentTimeMillis();
				sendAvatar();
				long delta = System.currentTimeMillis() - startTime;
				long frameDuration = 1000 / fps;
				try {
					if (frameDuration - delta > 0) {
						Thread.sleep(frameDuration - delta);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getUserName() {
		return userName;
	}
}
