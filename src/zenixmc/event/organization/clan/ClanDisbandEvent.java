package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanDisbandEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * User disbanding the clan.
	 */
	private ZenixUserInterface disbander;
	
	/**
	 * Message to display at .
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanDisbandEvent(Clan clan, ZenixUserInterface disbander, String message) {
		super(clan);
		this.disbander = disbander;
		this.message = message;
	}

	public ZenixUserInterface getDisbander() {
		return disbander;
	}

	public void setDisbander(ZenixUserInterface disbander) {
		this.disbander = disbander;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean set) {
		this.cancelled = set;
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