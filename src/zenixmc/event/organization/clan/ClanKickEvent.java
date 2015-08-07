package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanKickEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player being kicked from the clan.
	 */
	private OrganizationPlayerInterface kicked;
	
	/**
	 * User that is kicking the player.
	 */
	private ZenixUserInterface kicking;
	
	/**
	 * Message to display at kicking.
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
	public ClanKickEvent(Clan clan, OrganizationPlayerInterface kicked, ZenixUserInterface kicking, String message) {
		super(clan);
		this.kicked = kicked;
		this.kicking = kicking;
		this.message = message;
	}
	
	public OrganizationPlayerInterface getKicked() {
		return kicked;
	}

	public void setKicked(OrganizationPlayerInterface kicked) {
		this.kicked = kicked;
	}

	public ZenixUserInterface getKicking() {
		return kicking;
	}

	public void setKicking(ZenixUserInterface kicking) {
		this.kicking = kicking;
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
