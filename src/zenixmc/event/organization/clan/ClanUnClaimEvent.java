package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.Territory;

public class ClanUnClaimEvent extends ClanEvent implements Cancellable {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Player unclaiming territory.
	 */
	private OrganizationPlayerInterface unclaimer;
	
	/**
	 * Territory being unclaimed.
	 */
	private Territory territory;
	
	/**
	 * Message to display at unclaiming.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanUnClaimEvent(Clan clan, OrganizationPlayerInterface unclaimer, Territory territory, String message) {
		super(clan);
		this.unclaimer = unclaimer;
		this.territory = territory;
		this.message = message;
	}

	public OrganizationPlayerInterface getUnClaimer() {
		return unclaimer;
	}

	public void setClaimer(OrganizationPlayerInterface unclaimer) {
		this.unclaimer = unclaimer;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
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
