package zenixmc.event;

import org.bukkit.event.HandlerList;

import zenixmc.user.ZenixUserInterface;
import zenixmc.user.objects.Warning;

public class ReachedZeroWarningEvent extends ZenixUserEvent {

	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * The zeroed warning object.
	 */
	protected final Warning warning;
	
	/**
	 * Instantiate the event from user.
	 * @param user
	 * 		The user.
	 */
	public ReachedZeroWarningEvent(ZenixUserInterface user) {
		super(user);
		this.warning = user.getWarning();
	}

	/**
	 * @return The zeroed out warning object.
	 */
	public Warning getWarning() {
		return warning;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * @return A list of handlers.
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
