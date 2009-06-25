package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

import videochat.server.connection.ConnectedClient;

/**
 * A command sent when error occurs
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
public class ErrorMessage extends Message {

	private static final long serialVersionUID = 6215608113850467674L;
	
	/**
	 * See {@link Command#Command(Hashtable)}
	 * @param params
	 */
	public ErrorMessage(Hashtable<String, Serializable> params) {
		super(params);
	}
	
	/* (non-Javadoc)
	 * @see videochat.shared.commands.Command#execute(videochat.server.connection.ConnectedClient)
	 */
	@Override
	public void execute(ConnectedClient receiver) {
		
	}
}
