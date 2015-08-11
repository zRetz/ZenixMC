package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.Territory;
import zenixmc.user.ZenixUserInterface;

public class ClanClaimEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player claiming territory.
	 */
	private OrganizationPlayerInterface claimer;
	
	/**
	 * Territory being claimed.
	 */
	private Territory territory;
	
	/**
	 * Whether this claim was an overclaim.
	 */
	private boolean overclaim;
	
	/**
	 * Message to display at claiming.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanClaimEvent(Clan clan, OrganizationPlayerInterface claimer, Territory territory, boolean overclaim, String message) {
		super(clan);
		this.claimer = claimer;
		this.territory = territory;
		this.overclaim = overclaim;
		this.message = message;
	}

	public OrganizationPlayerInterface getClaimer() {
		return claimer;
	}

	public void setClaimer(OrganizationPlayerInterface claimer) {
		this.claimer = claimer;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	public boolean isOverclaim() {
		return overclaim;
	}

	public void setOverclaim(boolean overclaim) {
		this.overclaim = overclaim;
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
