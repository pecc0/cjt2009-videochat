/**
 * 
 */
package videochat.server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ConnectionsManager {
	/**
	 * This method starts listening on a certain socket.
	 * It blocks the current thread.
	 * @author "ppetkov"
	 */
	public static void listen(){
		ServerSocket serverSocket = null;
		try {
		    serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: 4444");
		    System.exit(-1);
		}
		
		while(true){
			Socket clientSocket = null;
			try {
			    clientSocket = serverSocket.accept();
			    ServerConnection connection = new ServerConnection(clientSocket);
			    
			} catch (IOException e) {
			    System.out.println("Accept failed: 4444");
			    System.exit(-1);
			}

		}
	}
}
