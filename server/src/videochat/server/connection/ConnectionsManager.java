/**
 * 
 */
package videochat.server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ConnectionsManager implements Runnable {
	/**
	 * The set of the currently connected clients.
	 * This field is with default access because the 
	 * classes from this package need access to it
	 */
	private HashSet<ConnectedClient> clients;
	private ServerSocket serverSocket;
	private static ConnectionsManager instance;
	private boolean isStopped;
	private ConnectionsManager(){
		isStopped = false;
	}
	
	public static ConnectionsManager getInstance(){
		if (instance == null) {
			instance = new ConnectionsManager();
		}
		return instance;
	}
	
	/**
	 * Starts a new thread that executes the {@link #listen()} method
	 */
	public void startListening(){
		new Thread(this).start();
	}
	
	/**
	 * Stops the listening thread
	 */
	public void stopListening(){
		isStopped = true;
		try {
			serverSocket.close();
		} catch (IOException e){
			
		}
	}
	/**
	 * This method starts listening on a certain socket.
	 * It blocks the current thread.
	 * @author "ppetkov"
	 */
	private void listen(){
		serverSocket = null;
		try {
		    serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: 4444");
		    System.exit(-1);
		}
		clients = new HashSet<ConnectedClient>();
		while(true){
			Socket clientSocket = null;
			try {
			    clientSocket = serverSocket.accept();
			    
			    ServerConnection connection = new ServerConnection(clientSocket);
			    new ConnectedClient(connection, clients);
			    
			} catch (IOException e) {
				if (isStopped){
					break;
				} else {
				    System.out.println("Accept failed: 4444");
				    System.exit(-1);
				}
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		listen();
	}
}
