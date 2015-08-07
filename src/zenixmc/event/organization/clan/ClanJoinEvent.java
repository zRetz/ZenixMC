package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;

public class ClanJoinEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player joining the clan.
	 */
	private OrganizationPlayerInterface joining;
	
	/**
	 * Message to display at joining.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanJoinEvent(Clan clan, OrganizationPlayerInterface joining, String message) {
		super(clan);
		this.joining = joining;
		this.message = message;
	}
	
	public OrganizationPlayerInterface getJoining() {
		return joining;
	}

	public void setJoining(OrganizationPlayerInterface joining) {
		this.joining = joining;
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
