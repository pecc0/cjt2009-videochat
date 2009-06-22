/**
 * 
 */
package videochat.server;

import videochat.server.connection.ConnectionsManager;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ServerMain {

	/**
	 * TODO - DOCUMENT ME
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectionsManager.listen();

	}

}
