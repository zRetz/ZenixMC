/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.io.SeDe;
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
	protected File getZenixUserFile(UUID uuid) {
		return new File(this.directory, uuid + ".dat");
	}

	@Override
	public ZenixUserInterface getZenixUser(Player player) {

		final File f = getZenixUserFile(player.getUniqueId());

		ZenixUserInterface zui = null;

		if (!(f.exists())) {
			zui = new ZenixUser(player, zenix, eventDispatcher);
			save(zui);
			return zui;
		}

		zui = SeDe.deserialize(f.getName(), ZenixUser.class);

		zui.setPlayer(player);
		zui.setTeleport(new Teleport(zui, zenix));
		zui.setZenixMC(zenix);
		zui.setEventDispatcher(eventDispatcher);
		zui.handleSerialize();

		logger.log(Level.INFO, "Zenix User " + zui.getName() + " has been loaded.");

		return zui;
	}

	@Override
	public ZenixUserInterface getZenixUser(Object key) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public ZenixUserInterface getZenixUser(String name) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public ZenixUserInterface getZenixUser(UUID uuid) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public void save(ZenixUserInterface zenixUser) {

		final File f = getZenixUserFile(zenixUser.getUniqueId());

		SeDe.serialize(zenixUser, f.getName());

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

	public void setTextRepository(TextRepositoryInterface textRepository) {
		this.textRepository = textRepository;
	}
	
}
