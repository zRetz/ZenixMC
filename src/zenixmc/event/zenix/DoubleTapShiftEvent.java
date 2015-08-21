package zenixmc.event.zenix;

import org.bukkit.event.HandlerList;

import zenixmc.user.ZenixUserInterface;

public class DoubleTapShiftEvent extends ZenixUserEvent {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	/**
	 * Time it took to double tap.
	 */
	private long time;
	
	public DoubleTapShiftEvent(ZenixUserInterface zui, long time) {
		super(zui);
		this.time = time;
	}

	public long getTime() {
		return time;
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
