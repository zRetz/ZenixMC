package zenixmc.event.organization.clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class ClanReDescEvent extends ClanEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	/**
	 * User settings the name.
	 */
	private ZenixUserInterface setter;
	
	/**
	 * New clan name.
	 */
	private String[] newDesc;
	
	/**
	 * Old clan name.
	 */
	private String[] oldDesc;
	
	/**
	 * Message to display at redesc.
	 */
	private String message;
	
	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;
	
	public ClanReDescEvent(Clan clan, ZenixUserInterface setter, String[] newDesc, String[] oldDesc, String message) {
		super(clan);
		this.setter = setter;
		this.newDesc = newDesc;
		this.oldDesc = oldDesc;
		this.message = message;
	}

	public ZenixUserInterface getSetter() {
		return setter;
	}

	public void setSetter(ZenixUserInterface setter) {
		this.setter = setter;
	}

	public String[] getNewDesc() {
		return newDesc;
	}

	public void setNewDesc(String[] newDesc) {
		this.newDesc = newDesc;
	}

	public String[] getOldDesc() {
		return oldDesc;
	}

	public void setOldDesc(String[] oldDesc) {
		this.oldDesc = oldDesc;
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
