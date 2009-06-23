package videochat.client;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Hashtable;

import videochat.client.commands.ClientCommandManager;
import videochat.client.connection.ClientConnection;
import videochat.client.ui.ChatPanel;
import videochat.client.ui.SelectServerDialog;
import videochat.client.ui.UserPanel;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.LoginCommand;

import jmapps.ui.JMDialog;
import jmapps.ui.JMFrame;
import jmapps.ui.MessageDialog;
/**
 * 
 * The client main frame
 *
 * @author "ppetkov" (Jun 21, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 21, 2009 "ppetkov" created <br>
 */
public class ClientMainWindow extends JMFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = -67910795642719141L;
	//private ClientConnection connection;
	
	protected UserPanel panelContent;
	protected ChatPanel chatPanel;
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
        		ClientConnection connection = new ClientConnection(dlg.getServer(), dlg.getUserName());
        		ClientCommandManager.getInst().setConnection(connection);
        		Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
        		params.put(LoginCommand.userNameKey, dlg.getUserName());
        		ClientCommandManager.getInst().sendCommand(CommandFactory.createCommand("login", params));
        		
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
        panelContent = new UserPanel (this);
        this.add ( panelContent, BorderLayout.SOUTH );
        chatPanel = new ChatPanel(this);
        this.add(chatPanel, BorderLayout.CENTER);
        //panelContent.addContainerListener( this );
        
        super.initFrame ();
        
    }
    
    @Override
    public void windowClosing ( WindowEvent event ) {
    	panelContent.killCurrentPlayer();
    	ClientCommandManager.getInst().getConnection().stopConnection();
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
