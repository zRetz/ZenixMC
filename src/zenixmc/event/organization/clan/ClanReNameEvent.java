package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanReNameEvent extends ClanEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	/**
	 * User settings the name.
	 */
	private ZenixUserInterface setter;
	
	/**
	 * New clan name.
	 */
	private String newName;
	
	/**
	 * Old clan name.
	 */
	private String oldName;
	
	/**
	 * Message to display at rename.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanReNameEvent(Clan clan, ZenixUserInterface setter, String nName, String oName, String message) {
		super(clan);
		this.newName = nName;
		this.oldName = oName;
		this.setter = setter;
		this.message = message;
	}
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String nName) {
		this.newName = nName;
	}

	public String getOldName() {
		return oldName;
	}

	public ZenixUserInterface getSetter() {
		return setter;
	}

	public void setSetter(ZenixUserInterface setter) {
		this.setter = setter;
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
