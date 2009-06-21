package videochat;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.WindowEvent;

import videochat.ui.UserPanel;

import jmapps.ui.JMFrame;

public class Client extends JMFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = -67910795642719141L;
	
	protected UserPanel panelContent;
	
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
		Client c = new Client();
		c.setVisible( true );
        c.invalidate();
        c.pack();
		
	}
	


}
