/**
 * 
 */
package videochat.shared.commands;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public interface IConnectionListener {
	void receiveCommand(Command command);
	void connectionClosed();
}
