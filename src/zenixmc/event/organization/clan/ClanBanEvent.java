package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanBanEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player being banned from the clan.
	 */
	private OrganizationPlayerInterface banned;
	
	/**
	 * User issueing the ban.
	 */
	private ZenixUserInterface banner;
	
	/**
	 * Message to display at banning.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanBanEvent(Clan clan, OrganizationPlayerInterface banned, ZenixUserInterface banner, String message) {
		super(clan);
		this.banned = banned;
		this.banner = banner;
		this.message = message;
	}

	public OrganizationPlayerInterface getBanned() {
		return banned;
	}

	public void setBanned(OrganizationPlayerInterface banned) {
		this.banned = banned;
	}

	public ZenixUserInterface getBanner() {
		return banner;
	}

	public void setBanner(ZenixUserInterface banner) {
		this.banner = banner;
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
