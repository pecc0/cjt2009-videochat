/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.server.connection.ConnectedClient;

/**
 * The base command class. Each command contains parameters hash table.
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = -3883675255798923786L;
	protected Hashtable<String, Serializable> parameters;
	
	//keys used to store a certain parameter in the hash table
	public static final String messageKey = "message";
	
	public final static String infoKey = "info";
	
	public final static String userNameKey = "username";
	
	public final static String avatarKey = "avatar"; 
	
	public static final String dateTime="datetime";
	//error messages
	public static final String messageUserExist = "error.userexist";
	
	
	/**
	 * Constructs a command. Optional parameters could be added
	 * via a hash table. The keys in the hashtable are defined by the
	 * users. </br>
	 * Objects from this class can be serialized.
	 * @param params Hash table with the parameters
	 */
	@SuppressWarnings("unchecked")
	public Command(Hashtable<String, Serializable> params){
		parameters = (Hashtable<String, Serializable>)params.clone();
	}
	
	protected Hashtable<String, Serializable> getParameters() {
		return parameters;
	}
	/**
	 * Each command must override this method in order to
	 * perform certain operation upon the {@link ConnectedClient}
	 * object passed as parameter. </br>
	 * This method is executed on the server when the message is
	 * received there.
	 * @param receiver the command receiver
	 */
	public abstract void execute(ConnectedClient receiver);
}
