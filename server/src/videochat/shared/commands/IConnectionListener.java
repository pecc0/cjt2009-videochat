
package videochat.shared.commands;

import videochat.shared.connection.Connection;

/**
 * The IConnectionListener interface should be implemented by 
 * a class that wants to listen for a certain connection events.
 * The user can add the listener to the listeners of a certain 
 * connection via {@link Connection#addConnectionListener(IConnectionListener)} 
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public interface IConnectionListener {
	void receiveCommand(Command command);
	void connectionClosed();
}
