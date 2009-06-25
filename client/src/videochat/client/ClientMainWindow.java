package videochat.client;

import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Hashtable;

import videochat.client.commands.ClientCommandManager;
import videochat.client.connection.ClientConnection;
import videochat.client.ui.ChatPanel;
import videochat.client.ui.SelectServerDialog;
import videochat.client.ui.SettingsDialog;
import videochat.client.ui.UserPanel;
import videochat.shared.commands.Command;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.ErrorMessage;
import videochat.shared.commands.IConnectionListener;
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
public class ClientMainWindow extends JMFrame 
	implements IConnectionListener, ActionListener{

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
		selectServerAndUser();
		
	}
	
    @Override
    protected void initFrame () {
    	MenuBar         menu;
    	
    	menu = new MenuBar ();
    	Menu editMenu = new Menu(TextI18n.getText("menu.edit"));
    	MenuItem settings = new MenuItem(TextI18n.getText("menu.edit.settings"));
    	settings.addActionListener(this);
    	editMenu.add(settings);
    	menu.add(editMenu);
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
 
    	VideoChatAppsCfg.getInstance().save();
    	if (ClientCommandManager.getInst().getConnection() != null){
    		ClientCommandManager.getInst().getConnection().stopConnection();
    	}
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

	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		
	}

	/* (non-Javadoc)
	 * @see videochat.shared.commands.IConnectionListener#receiveCommand(videochat.shared.commands.Command)
	 */
	@Override
	public void receiveCommand(Command command) {
		if (command instanceof ErrorMessage){
			ErrorMessage em = (ErrorMessage) command;
			MessageDialog.createErrorDialog(this, TextI18n.getText(em.getMessage()));
			if (Command.messageUserExist.equals(em.getMessage())){
				ClientCommandManager.getInst().getConnection().stopConnection();
				
				selectServerAndUser();
			}
		}
	}
	
	private void selectServerAndUser(){
		while (true) {
			SelectServerDialog dlg = new SelectServerDialog(this);
	        dlg.setVisible(true);
	        if (dlg.getAction().equals(JMDialog.ACTION_OK)){
	        	try {
	        		this.setTitle(TextI18n.getText("mainwindow.title.connecting"));
	        		ClientConnection connection = new ClientConnection(dlg.getServer(), dlg.getUserName());
	        		ClientCommandManager.getInst().setConnection(connection);
	        		ClientCommandManager.getInst().addConnectionCommandListener(this);
	        		Hashtable<String, Serializable> params = new Hashtable<String, Serializable>();
	        		params.put(LoginCommand.userNameKey, dlg.getUserName());
	        		ClientCommandManager.getInst().sendCommand(CommandFactory.createCommand(CommandFactory.commandTypeLogin, params));
	        		this.setTitle(TextI18n.getText("mainwindow.title"));
	        		break;
	        	} catch (Exception e) {
					e.printStackTrace();
					MessageDialog.createErrorDialog ( this,
							TextI18n.getText("error.openconnection") + dlg.getServer() );
					//this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				}
	        } else {
	        	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	        	break;
	        }
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String      strCmd;
        Object      objSource;
        
		strCmd = event.getActionCommand ();
        objSource = event.getSource ();
        if ( strCmd == null  &&  objSource instanceof MenuItem )
            strCmd = ((MenuItem)objSource).getActionCommand ();

        if ( strCmd == null )
            return;
		if (strCmd.equals(TextI18n.getText("menu.edit.settings"))){
			SettingsDialog dlg = new SettingsDialog(this, 
					TextI18n.getText("menu.edit.settings"),true);
			dlg.setVisible(true);
			if(dlg.getAction().equals(JMDialog.ACTION_OK)){
				
			}
		}
	}
}
