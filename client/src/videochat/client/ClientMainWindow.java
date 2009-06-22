package videochat.client;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.WindowEvent;
import java.io.IOException;

import com.sun.media.util.JMFI18N;

import videochat.client.connection.ClientConnection;
import videochat.client.ui.SelectServerDialog;
import videochat.client.ui.UserPanel;

import jmapps.ui.JMDialog;
import jmapps.ui.JMFrame;
import jmapps.ui.MessageDialog;

public class ClientMainWindow extends JMFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = -67910795642719141L;
	private ClientConnection connection;
	
	protected UserPanel panelContent;
	public ClientMainWindow () {
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
	
	/* (non-Javadoc)
	 * @see jmapps.ui.JMFrame#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent event) {
		super.windowOpened(event);
		SelectServerDialog dlg = new SelectServerDialog(this);
        dlg.setVisible(true);
        if (dlg.getAction().equals(JMDialog.ACTION_OK)){
        	try {
        		connection = new ClientConnection(dlg.getServer(), dlg.getName());
        	} catch (Exception e) {
				e.printStackTrace();
				MessageDialog.createErrorDialog ( this,
						TextI18n.getText("error.openconnection") + dlg.getServer() );
				this.dispose();
			}
        } else {
        	this.dispose();
        }
	}
	
    @Override
    protected void initFrame () {
    	MenuBar         menu;
    	
    	menu = new MenuBar ();
        this.setMenuBar ( menu );
        this.setLayout ( new BorderLayout() );
        panelContent = new UserPanel (this, new BorderLayout() );
        this.add ( panelContent, BorderLayout.SOUTH );
        //panelContent.addContainerListener( this );
        
        super.initFrame ();
        
    }
    
    @Override
    public void windowClosing ( WindowEvent event ) {
    	panelContent.killCurrentPlayer();
    	connection.stopConnection();
        this.dispose();
    }
    
	/**
	* @param args
	*/
	public static void main(String[] args) {
		ClientMainWindow c = new ClientMainWindow();
		c.setVisible( true );
        c.invalidate();
        c.pack();
		
	}
	


}
