/**
 * 
 */
package videochat.shared.commands;

import java.io.Serializable;
import java.util.Hashtable;

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
	@SuppressWarnings("unchecked")
	public Command(Hashtable<String, Serializable> params){
		parameters = (Hashtable<String, Serializable>)params.clone();
	}
	
	protected Hashtable<String, Serializable> getParameters() {
		return parameters;
	}
	
}
