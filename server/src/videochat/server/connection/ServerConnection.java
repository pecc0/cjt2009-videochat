/**
 * 
 */
package videochat.server.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import videochat.shared.connection.Connection;

/**
 * A connection from server to client
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ServerConnection extends Connection {

	/**
	 * Constructs new connection to client. </br>
	 * Port: 4444
	 * @param socket the client socket received after a call to {@link ServerSocket#accept()}
	 * @throws IOException see {@link Socket#getInputStream()}, {@link Socket#getOutputStream()} and  {@link ObjectOutputStream#ObjectOutputStream(java.io.OutputStream)}
	 */
	public ServerConnection(Socket socket) throws IOException{
		super(socket);
	}

	
}


