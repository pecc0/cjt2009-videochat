package videochat.server.connection;

public interface IClientsSet extends Iterable<ConnectedClient>{
	/**
	 * Adds the client to the set
	 * @param c A client
	 */
	public void add(ConnectedClient c);
	
	/**
	 * Removes the client from the set
	 * @param c A client
	 */
	public void remove(ConnectedClient c);
	
	/**
	 * Checks whether the client is in the client set
	 * @param A client
	 * @return <code>true</code> if the client is in the set, <code>false</code> otherwise 
	 */
	public boolean contains(ConnectedClient c);
}
