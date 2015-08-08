/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.io.SeDe;
import zenixmc.user.OfflineZenixUser;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.objects.Teleport;

/**
 * Persistence of users on disk.
 * 
 * @author james
 */
public class ZenixUserRepository extends Repository implements ZenixUserRepositoryInterface {

	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;

	/**
	 * The event dispatcher to fire events.
	 */
	private final EventDispatcher eventDispatcher;

	/**
	 * Repository to push/fetch text data.
	 */
	private TextRepositoryInterface textRepository;

	/**
	 * Instantiate.
	 * 
	 * @param logger
	 *            The logger to log to.
	 * @param directory
	 *            The directory to store data in.
	 * @param zenix
	 *            The plugin.
	 */
	public ZenixUserRepository(Logger logger, File directory, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		super(logger, directory);
		this.zenix = zenix;
		this.eventDispatcher = eventDispatcher;
	}

	/**
	 * Returns the path to the file to store the specified user in.
	 *
	 * @param player
	 *            The player to find the file for.
	 * @return The file of the specified player.
	 */
	public File getZenixUserFile(UUID uuid) {
		return new File(this.directory, uuid + ".dat");
	}
	
	@Override
	public OfflineZenixUser loadZenixUser(OfflinePlayer player) {
		return getZenixUser(player.getUniqueId()).toOfflineUser(player);
	}
	
	@Override
	public OfflineZenixUser getOfflineZenixUser(UUID uuid) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public ZenixUser getZenixUser(UUID uuid) {

		final File f = getZenixUserFile(uuid);

		ZenixUser zui = null;

		if (!(f.exists())) {
			zui = new ZenixUser(zenix.getPlayer(uuid).isOnline() ? zenix.getPlayer(uuid) : null, zenix, eventDispatcher);
			zui.handleSerialize();
			save(zui);
			return zui;
		}

		zui = SeDe.deserialize(f, ZenixUser.class);

		zui.setPlayer(zenix.getOfflinePlayer(uuid).isOnline() ? zenix.getPlayer(uuid) : null);
		zui.setTeleport(new Teleport(eventDispatcher, zui, zenix));
		zui.setZenixMC(zenix);
		zui.setEventDispatcher(eventDispatcher);
		zui.handleSerialize();

		logger.log(Level.INFO, "Zenix User " + zui.getName() + " has been loaded.");

		return zui;
	}

	@Override
	public ZenixUser getZenixUser(Object key) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public ZenixUser getZenixUser(String name) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public ZenixUser getZenixUser(Player player) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
	
	@Override
	public ZenixUserInterface getRegardlessZenixUser(String name) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
	
	@Override
	public ZenixUserInterface getRegardlessZenixUser(UUID uuid) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public void save(ZenixUserInterface zenixUser) {

		final File f = getZenixUserFile(zenixUser.getUniqueId());

		SeDe.serialize(zenixUser, f);

		logger.log(Level.INFO, "Zenix User " + zenixUser.getName() + " has been saved.");
	}

	@Override
	public void open(String openMessage) {
		super.open(openMessage);
	}

	@Override
	public void close(String closeMessage) {
		super.close(closeMessage);
	}

	@Override
	public void save(Object object) {
		if (object instanceof ZenixUserInterface) {
			save((ZenixUserInterface) object);
		}
	}
	
	@Override
	public void delete(Object object) {
		
	}

	public void setTextRepository(TextRepositoryInterface textRepository) {
		this.textRepository = textRepository;
	}

	@Override
	public Set<String> fileNames() {
		
		Set<String> result = new HashSet<>();
		
		File[] files = files();
		
		for (File f : files) {
			result.add(f.getName().substring(0, f.getName().length()-4));
		}
		
		return result;
	}

	@Override
	public File[] files() {
		return this.directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File path) {
				return path.getName().endsWith(".dat");
			}
			
		});
	}

	@Override
	public Collection<ZenixUser> getOnlineUsers() {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
	
}
