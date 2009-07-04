/**
 * 
 */
package videochat.shared.contact;

import java.io.Serializable;

/**
 * Class to held the information about a client contact.
 * (Like client user name, client name, etc.)
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class ContactInfo implements Serializable {

	private static final long serialVersionUID = 7746264353653355554L;
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
