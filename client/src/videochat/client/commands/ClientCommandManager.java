/**
 * 
 */
package videochat.client.commands;

import java.util.LinkedList;

import videochat.shared.commands.Command;
import videochat.shared.commands.CommandFactory;
import videochat.shared.commands.IConnectionListener;
import videochat.shared.connection.Connection;

/**
 * Used to send commands using the connection that is set while the manager is initialized.
 *
 * @author "ppetkov" (Jun 23, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 23, 2009 "ppetkov" created <br>
 */
public class ClientCommandManager extends CommandFactory {
	private Connection connection;
	private static ClientCommandManager inst = null;
	private LinkedList<IConnectionListener> listeners;
	private ClientCommandManager(){
		listeners = new LinkedList<IConnectionListener>();
		connection = null;
	}
	/**
	 * {@link ClientCommandManager} is a singleton. This returns the instance 
	 * @return the {@link ClientCommandManager} instance
	 */
	public static ClientCommandManager getInst() {
		if (inst == null) {
			inst = new ClientCommandManager();
		}
		return inst;
	}
	/**
	 * Used to add listeners before the connection have been initialized. </br>
	 * The listeners are cached in a list. </br>
	 * Can also be used instead of a call to {@link #getConnection()}.{@link Connection#addConnectionListener(IConnectionListener) addCommandListener(ICommandListener)}
	 * @param l the listener
	 * @see Connection#addConnectionListener(IConnectionListener)
	 */
	public void addConnectionCommandListener(IConnectionListener l) {
		if (connection == null || connection.isStopped()) {
			listeners.add(l);
		} else {
			connection.addConnectionListener(l);
		}
	}
	
	/**
	 * Sets the connection that the manager uses.
	 * @param c the new connection
	 */
	public void setConnection(Connection c) {
		
		if (c != null) {
			connection = c;
			for (IConnectionListener l:listeners) {
				connection.addConnectionListener(l);
			}
			listeners.clear();
		}
	}
	/**
	 * Gets the connection that the manager uses
	 * @return the manager connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Sends a command through the connection set by {@link #setConnection(Connection)}
	 * @param c command
	 */
	public void sendCommand(Command c){
		if (getConnection() != null){ 
			getConnection().sendCommand(c);
		}
	}

	
}
