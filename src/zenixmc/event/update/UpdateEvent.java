package zenixmc.event.update;

import org.bukkit.event.HandlerList;

import zenixmc.event.ZenixEvent;

public class UpdateEvent extends ZenixEvent {
	
	/**
	 * List of handlers.
	 */
	private static final HandlerList handlers = new HandlerList(); 
	
	private UpdateType type;
	
	public UpdateEvent(UpdateType type) {
		this.type = type;
	}
	
	public UpdateType getType() {
		return type;
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
