package videochat;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.MenuBar;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.media.CaptureDeviceInfo;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.bean.playerbean.MediaPlayer;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;

import jmapps.jmstudio.CaptureControlsDialog;
import jmapps.jmstudio.CaptureDialog;
import jmapps.ui.JMFrame;
import jmapps.ui.JMPanel;
import jmapps.ui.MessageDialog;
import jmapps.ui.VideoPanel;
import jmapps.util.CDSWrapper;
import jmapps.util.JMAppsCfg;
import jmapps.util.JMFUtils;

import com.sun.media.util.JMFI18N;

public class Client extends JMFrame implements ControllerListener{

	/**
	* 
	*/
	private static final long serialVersionUID = -67910795642719141L;
	private String                  nameCaptureDeviceAudio = null;
	private String                  nameCaptureDeviceVideo = null;
	private static JMAppsCfg    cfgJMApps = null;
	private DataSource              dataSourceCurrent = null;
	private CaptureControlsDialog   dlgCaptureControls = null;
	
	protected JMPanel           panelContent;
	protected VideoPanel        panelVideo = null;
	protected MediaPlayer		mediaPlayerCurrent; 
	public Client () {
		super ( null, "videochat" );
	}
	
	@Override
	public void addNotify () {
        super.addNotify ();
    }
	
	@Override
    public void pack () {
        super.pack ();
    }

    @Override
    protected void initFrame () {
    	MenuBar         menu;
    	
    	menu = new MenuBar ();
        this.setMenuBar ( menu );
        this.setLayout ( new BorderLayout() );
        panelContent = new JMPanel ( new BorderLayout() );
        this.add ( panelContent, BorderLayout.SOUTH );
        //panelContent.addContainerListener( this );
        
        super.initFrame ();
    }
    
    @Override
    public void windowClosing ( WindowEvent event ) {
    	killCurrentPlayer();
        this.dispose ();
    }
    
    protected void processRealizeComplete ( RealizeCompleteEvent event ) {
    	panelVideo = new VideoPanel ( mediaPlayerCurrent );
        panelVideo.setZoom ( 1.0 );
        panelContent.add ( panelVideo, BorderLayout.EAST);
        mediaPlayerCurrent.prefetch();
        pack();
    }
    
    protected void processPrefetchComplete ( PrefetchCompleteEvent event ) {
        mediaPlayerCurrent.start();
    }
    
    protected void killCurrentPlayer () {
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
    }
    
	/**
	* @param args
	*/
	public static void main(String[] args) {
		Client c = new Client();
		c.setVisible ( true );
        c.invalidate ();
        c.pack ();
		c.captureMedia();
		
	}
	
	public void open ( DataSource dataSource ) {
		open (  dataSource, true  );
	}

	public void open ( DataSource dataSource, boolean killPrevious ) {
		MediaPlayer     mediaPlayer;
		boolean         boolResult;
		
		mediaPlayer = jmapps.util.JMFUtils.createMediaPlayer ( dataSource, (Frame)this );
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
	
	private void captureMedia () {
		CaptureDialog       dialogCapture;
		DataSource          dataSource;
		CaptureDeviceInfo   cdi;

		nameCaptureDeviceAudio = null;
		nameCaptureDeviceVideo = null;
		
		dialogCapture = new CaptureDialog ( this, cfgJMApps );
		dialogCapture.show();
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
					MessageDialog.createErrorDialog ( this,
							JMFI18N.getResource("jmstudio.error.captureds") );
				}
			}

			open ( dataSource );
			if ( dataSource != null ) {
				dlgCaptureControls = new CaptureControlsDialog (  this, dataSource );
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
			MessageDialog.createErrorDialog ( this,
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


}
