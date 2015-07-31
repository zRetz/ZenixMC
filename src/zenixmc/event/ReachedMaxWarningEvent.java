package zenixmc.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.user.ZenixUserInterface;
import zenixmc.user.objects.Warning;

public class ReachedMaxWarningEvent extends ZenixUserEvent implements Cancellable {

	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * The maxed out warning object.
	 */
	protected final Warning warning;
	
	/**
	 * Whether the event is cancelled.
	 */
	protected boolean cancelled = false;
	
	/**
	 * Instantiate the event from user.
	 * @param user
	 * 		The user.
	 */
	public ReachedMaxWarningEvent(ZenixUserInterface user) {
		super(user);
		this.warning = user.getWarning();
	}

	/**
	 * @return The maxed out warning object.
	 */
	public Warning getWarning() {
		return warning;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		this.cancelled = value;
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
