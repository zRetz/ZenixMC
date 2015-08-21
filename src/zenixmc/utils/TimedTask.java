package zenixmc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.update.UpdateEvent;

/**
 * Timed Task.
 * 
 * @author james
 */
public abstract class TimedTask implements Listener {
	
	/**
	 * Current tasks.
	 */
	private static Map<String, TimedTask> tasks = new HashMap<>();
	
	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * EventDispatcher.
	 */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * Unique Identifier.
	 */
	private String id;
	
	/**
	 * Time to auto-cancel.
	 */
	private long timeout;
	
	/**
	 * Delay before starting.
	 */
	private long delay;
	
	/**
	 * Interval between each run.
	 */
	private long interval;
	
	/**
	 * Time of next interval.
	 */
	private long nextInterval;
	
	/**
	 * Whether the task has been cancelled.
	 */
	private boolean cancelled = false;
	
	/**
	 * Instantiate.
	 * @param timeout
	 * 		Amount of time before auto-cancel.
	 * @param delay
	 * 		Amount of time before task start.
	 * @param interval
	 * 		Amount of time between each run.
	 * @param zenix
	 * 		The plugin.
	 */
	public TimedTask(long timeout, long delay, long interval, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		this.zenix = zenix;
		this.eventDispatcher = eventDispatcher;
		this.timeout = System.currentTimeMillis() + timeout;
		this.delay = System.currentTimeMillis() + delay;
		this.interval = interval;
		this.nextInterval = System.currentTimeMillis() + this.interval;
	}
	
	public String taskStart() {
		eventDispatcher.registerEventListener(this);
		id = UUID.randomUUID().toString();
		tasks.put(id, this);
		return id;
	}
	
	public void taskCancel() {
		this.cancelled = true;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public boolean hasTimedOut() {
		return timeout - System.currentTimeMillis() < 0;
	}
	
	public boolean hasDelay() {
		return delay - System.currentTimeMillis() > 0;
	}
	
	public boolean hasInterval() {
		return System.currentTimeMillis() - nextInterval > 0;
	}
	
	public void updateInterval() {
		this.nextInterval = System.currentTimeMillis() + interval;
	}
	
	abstract public void taskRun();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onUpdate(UpdateEvent e) {
		
		for (Entry<String, TimedTask> en : tasks.entrySet()) {
			final TimedTask t = en.getValue();
			if (!(t.isCancelled())) {
				if (!(t.hasDelay())) {
					if (!(t.hasTimedOut())) {
						if (!(t.hasInterval())) {
							t.taskRun();
						}else {
							System.out.println("intervaled");
							t.updateInterval();
						}
					}else {
						System.out.println("timeout");
						t.taskCancel();
					}
				}
			}else {
				tasks.remove(t.getId());
			}
		}
	}
	
	public static TimedTask getTask(String id) {
		return tasks.get(id);
	}
}
