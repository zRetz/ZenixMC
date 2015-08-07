package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;

public class ClanLeaveEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player leaving the clan.
	 */
	private OrganizationPlayerInterface leaving;
	
	/**
	 * Message to display at leaving.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	/**
	 * Instantiate an ClanLeaveEvent.
	 * @param clan
	 * 		The clan this event is relevant to.
	 * @param leaving
	 * 		The player leaving the clan.
	 * @param message
	 * 		The message to display at leaving.
	 */
	public ClanLeaveEvent(Clan clan, OrganizationPlayerInterface leaving, String message) {
		super(clan);
		this.leaving = leaving;
		this.message = message;
	}
	
	public OrganizationPlayerInterface getLeaving() {
		return leaving;
	}

	public void setLeaving(OrganizationPlayerInterface leaving) {
		this.leaving = leaving;
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
