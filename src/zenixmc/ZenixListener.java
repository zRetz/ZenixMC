package zenixmc;

import org.bukkit.event.Listener;

import zenixmc.event.EventDispatcher;
import zenixmc.user.ZenixUserManager;

/**
 * Base listener.
 * @author james
 */
public abstract class ZenixListener implements Listener {

	/**
	 * The plugin.
	 */
	protected ZenixMCInterface zenix;
	
	/**
	 * The manager of zenix user.
	 */
	protected ZenixUserManager manager;
	
	/**
	 * The eventDispatcher used to fire events.
	 */
	protected EventDispatcher eventDispatcher;
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 */
	public ZenixListener(ZenixMCInterface zenix, ZenixUserManager manager, EventDispatcher eventDispatcher) {
		this.zenix = zenix;
		this.manager = manager;
		this.eventDispatcher = eventDispatcher;
	}
}
