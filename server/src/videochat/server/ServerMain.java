/**
 * 
 */
package videochat.server;

import java.util.Scanner;


import videochat.server.connection.ConnectionsManager;
import videochat.shared.ApplicationSettings;

/**
 * The main server class.
 * Prints a start message, calls {@link ConnectionsManager#startListening()}
 * and starts waiting for command from the console
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ServerMain {

	/**
	 * The main method
	 * @param args No arguments recognized
	 */
	public static void main(String[] args) {
		ConnectionsManager.getInstance().startListening();
		Scanner in = new Scanner(System.in);
		
		System.out.println(ApplicationSettings.getInstance().getStringProperty("server.startmessage"));
		do {
			String line = in.nextLine();
			if ("exit".equals(line)){
				ConnectionsManager.getInstance().stopListening();
				break;
			}
		} while(true);

	}

}
