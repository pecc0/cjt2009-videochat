/**
 * 
 */
package videochat.shared.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import videochat.shared.commands.Command;
import videochat.shared.commands.IConnectionListener;

/**
 * Base connection-contains functionality used for both server and client connections
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class Connection implements Runnable {
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket clientSocket;
	private ArrayList<IConnectionListener> listeners;
	private Thread thread;
	//private LinkedList<ICommand> commandsQueue;
	/**
	 * Constructs new connection. </br>
	 * Port: 4444
	 * @param aSocket the socket that this connection servers
	 * @throws IOException see {@link Socket#getInputStream()}, {@link Socket#getOutputStream()} and  {@link ObjectOutputStream#ObjectOutputStream(java.io.OutputStream)}
	 */
	protected Connection(Socket aSocket) throws IOException {
		clientSocket = aSocket;
		writer = new ObjectOutputStream(clientSocket.getOutputStream());
		reader = new ObjectInputStream(clientSocket.getInputStream());
		listeners = new ArrayList<IConnectionListener>();
		//commandsQueue = new LinkedList<ICommand>();
		thread = new Thread(this); 
		thread.start();
	}
	
	/**
	 * Closes the connection
	 */
	public void stopConnection() {
		
		try {
			reader.close();
		} catch (IOException e) {
		}
		try {
			writer.close();
		} catch (IOException e) {
		}
		
		try {
			clientSocket.close();
		} catch (IOException e) {
		}
	}
	/**
	 * Send a command through this connection
	 * @param c
	 */
	public void sendCommand(Command c) {
		//commandsQueue.addLast(c);
		try {
			writer.writeObject(c);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a command listener.
	 * Nothing is done if the listener has already been added.
	 * @see #removeCommandListener(IConnectionListener)
	 * @param l the listener
	 */
	public void addCommandListener(IConnectionListener l){
		synchronized (listeners) {
			if (!listeners.contains(l)) {
				listeners.add(l);
			}
		}
	}
	/**
	 * Removes the command listener that is equal to l
	 * @see #addCommandListener(IConnectionListener)
	 * @param l a listener
	 */
	public void removeCommandListener(IConnectionListener l){
		synchronized (listeners) {
			listeners.remove(l);
		}
	}
	
	//protected abstract void onCommand(ICommand command);
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Command input;
		try {
			while((input = (Command) reader.readObject()) != null) {
				synchronized (listeners) {
					int size = listeners.size();
					for (int i = 0; i < size; i++){
						listeners.get(i).receiveCommand(input);
					}
				}
			}
		}
		catch (Exception e) {
			if (e instanceof java.io.EOFException || e instanceof java.net.SocketException) {
				System.out.println("Connection closed");
			} else {
				e.printStackTrace();
			}
		}
		for (IConnectionListener l:listeners){
			l.connectionClosed();
		}
		stopConnection();
	}
	
}


