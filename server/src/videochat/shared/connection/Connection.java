/**
 * 
 */
package videochat.shared.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import videochat.shared.commands.ICommand;
import videochat.shared.commands.LoginCommand;

/**
 * Base connection-contains functionality used for both server and client connections
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public abstract class Connection implements Runnable {
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket clientSocket;
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
		new Thread(this).start();
	}
	
	public void stopConnection(){
		
		try {
			reader.close();
		} catch (IOException e) {
		}
		
	}
	
	protected abstract void onCommand(ICommand command);
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ICommand input;
		try {
			while((input = (ICommand) reader.readObject()) != null){
				//TODO remove
				if (input instanceof LoginCommand){
					System.out.println(input);
				}
				onCommand(input);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	
}


