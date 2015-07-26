package zenixmc.user;

import java.util.UUID;

import org.bukkit.entity.Player;

import zenixmc.event.EventDispatcher;
import zenixmc.persistance.CachedZenixUserRepository;

public class ZenixUserManager {

	/**
	 * EventDispatcher to fire events.
	 */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * Repository to fetch user data from.
	 */
	private final CachedZenixUserRepository repository;
	
	public ZenixUserManager(CachedZenixUserRepository repository, EventDispatcher eventDispatcher) {
		this.repository = repository;
		this.eventDispatcher = eventDispatcher;
	}
	
	/**
	 * Wrapper for repository.
	 * @param object
	 * 		The key for user.
	 * @return The user.
	 */
	public ZenixUserInterface getZenixUser(Object object) {
		return repository.getZenixUser(object);
	}
	
	/**
	 * Wrapper for repository.
	 * @param name
	 * 		The key for user.
	 * @return The user.
	 */
	public ZenixUserInterface getZenixUser(String name) {
		return repository.getZenixUser(name);
	}
	
	/**
	 * Wrapper for repository.
	 * @param uuid
	 * 		The key for user.
	 * @return The user.
	 */
	public ZenixUserInterface getZenixUser(UUID uuid) {
		return repository.getZenixUser(uuid);
	}
	
	/**
	 * Wrapper for repository.
	 * @param player
	 * 		The key for user.
	 * @return The user.
	 */
	public ZenixUserInterface getZenixUser(Player player) {
		return repository.getZenixUser(player);
	}
	
	/**
	 * Wrapper for repository.
	 * @param name
	 * 		The name to check.
	 * @return <code>true</code> If the name correlates to a user.
	 */
	public boolean isZenixUser(String name) {
		return repository.isZenixUser(name);
	}
	
	/**
	 * Wrapper for repository.
	 * @param uuid
	 * 		The unique identifier to check.
	 * @return <code>true</code> If the unique identifier correlates to a user.
	 */
	public boolean isZenixUser(UUID uuid) {
		return repository.isZenixUser(uuid);
	}
}