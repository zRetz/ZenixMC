package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.clans.Clan;

public class ClanUpdateEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Whether the event is cancelled.
	 */
	protected boolean cancelled = false;
	
	/**
	 * Instantiate the ClanUpdateEvent
	 * @param clan
	 * 		The clan relevant to this event.
	 */
	public ClanUpdateEvent(Clan clan) {
		super(clan);
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
