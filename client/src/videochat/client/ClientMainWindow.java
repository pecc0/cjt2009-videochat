package videochat.client;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.WindowEvent;

import videochat.client.ui.SelectServerDialog;
import videochat.client.ui.UserPanel;

import jmapps.ui.JMDialog;
import jmapps.ui.JMFrame;

public class ClientMainWindow extends JMFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = -67910795642719141L;
	
	protected UserPanel panelContent;
	private String serverAddr;
	private String userName;
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
        	serverAddr = dlg.getServer();
        	userName = dlg.getName();
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
        this.dispose ();
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
