/**
 * 
 */
package videochat.client.connection;

import java.io.IOException;
import java.net.Socket;

import videochat.shared.connection.Connection;


/**
 * A connection from client to server
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ClientConnection extends Connection {

	/**
	 * Constructs a connection to certain server and certain user name.</br>
	 * Port: 4444
	 * @param server the server name
	 * @param uname the user name
	 * @throws IOException see {@link Socket#Socket(java.net.InetAddress, int)}
	 */
	public ClientConnection(String server, String uname) throws IOException {
		super(new Socket(server, 4444));
	}
	

}
