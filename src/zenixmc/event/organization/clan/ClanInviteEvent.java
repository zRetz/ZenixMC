package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanInviteEvent extends ClanEvent implements Cancellable {

	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player being invited.
	 */
	private OrganizationPlayerInterface invited;
	
	/**
	 * User inviting the player.
	 */
	private ZenixUserInterface inviter;
	
	/**
	 * Message to display at invitation.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	/**
	 * Instantiate.
	 * @param clan
	 * 		The clan relevant to the event.
	 */
	public ClanInviteEvent(Clan clan, OrganizationPlayerInterface invited, ZenixUserInterface inviter, String message) {
		super(clan);
		this.invited = invited;
		this.inviter = inviter;
		this.message = message;
	}

	public OrganizationPlayerInterface getInvited() {
		return invited;
	}

	public void setInvited(OrganizationPlayerInterface invited) {
		this.invited = invited;
	}

	public ZenixUserInterface getInviter() {
		return inviter;
	}

	public void setInviter(ZenixUserInterface inviter) {
		this.inviter = inviter;
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
