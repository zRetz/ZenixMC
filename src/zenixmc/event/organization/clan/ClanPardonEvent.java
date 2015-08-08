package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanPardonEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player being banned from the clan.
	 */
	private OrganizationPlayerInterface pardoned;
	
	/**
	 * User issueing the ban.
	 */
	private ZenixUserInterface pardoner;
	
	/**
	 * Message to display at .
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanPardonEvent(Clan clan, OrganizationPlayerInterface pardoned, ZenixUserInterface pardoner, String message) {
		super(clan);
		this.pardoned = pardoned;
		this.pardoner = pardoner;
		this.message = message;
	}

	public OrganizationPlayerInterface getPardoned() {
		return pardoned;
	}

	public void setPardoned(OrganizationPlayerInterface pardoned) {
		this.pardoned = pardoned;
	}

	public ZenixUserInterface getPardoner() {
		return pardoner;
	}

	public void setPardoner(ZenixUserInterface pardoner) {
		this.pardoner = pardoner;
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
