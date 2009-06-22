/**
 * 
 */
package videochat.shared.contact;

import java.io.Serializable;
import java.util.List;

import videochat.shared.commands.ICommandListener;

/**
 * TODO - DOCUMENT ME
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7746264353653355554L;
	private String name;
	private List<ICommandListener> commandListeners;
	public void addCommandListener(ICommandListener listener){
		commandListeners.add(listener);
	}
	public void removeCommandListener(ICommandListener listener){
		commandListeners.remove(listener);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
