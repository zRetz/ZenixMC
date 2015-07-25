package zenixmc.event;

import zenixmc.user.ZenixUserInterface;

/**
 * An event that is related to a user.
 * @author james
 */
public abstract class ZenixUserEvent extends ZenixEvent {

	/**
	 *	The user in question. 
	 */
	private ZenixUserInterface user = null;
	
	/**
	 * Creates new event for specified user.
	 * @param user
	 * 		The user.
	 */
	public ZenixUserEvent(ZenixUserInterface user) {
		this.user = user;
	}

	/**
	 * @return The user this event is about.
	 */
	public ZenixUserInterface getUser() {
		return user;
	}
	
	/**
	 * Changes the user this event is about.
	 * @param user
	 * 		The user to set.
	 */
	public void setUser(ZenixUserInterface user) {
		this.user = user;
	}
	
}
