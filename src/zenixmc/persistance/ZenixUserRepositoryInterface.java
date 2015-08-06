/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import zenixmc.user.OfflineZenixUser;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserInterface;

/**
 * Repository to persist users information when they go offline.
 * @author james
 */
public interface ZenixUserRepositoryInterface extends RepositoryInterface {
    
	/**
	 * Loads a user into the memory.
	 * @param player
	 * 		The bukkit represen
	 * @return
	 */
	OfflineZenixUser loadZenixUser(OfflinePlayer player);
	
	/**
	 * Returns an offline user specified by uuid.
	 * @param uuid
	 * 		The unique identifier of the user.
	 * @return The offline user.
	 */
	OfflineZenixUser getOfflineZenixUser(UUID uuid);
	
    /**
     * Loads a user into the memory.
     * @param player
     *      The bukkit representation to load.
     * @return The user that was loaded.
     */
    ZenixUser getZenixUser(UUID uuid); 
    
    /**
     * Returns a user specified by key.
     * @param key
     *      The key.
     * @return The user.
     */
    ZenixUser getZenixUser(Object key);
    
    /**
     * Returns a user specified by name.
     * @param name
     *      The name of the user.
     * @return The user.
     */
    ZenixUser getZenixUser(String name);
    
    /**
     * Returns a user specified by bukkit representation.
     * @param player
     *      The bukkit representation of the user.
     * @return The user.
     */
    ZenixUser getZenixUser(Player player);
    
    /**
     * Returns a user specfied by name, regardless of online status.
     * @param name
     * 		The name of the user.
     * @return The user.
     */
    ZenixUserInterface getRegardlessZenixUser(String name);
    
    /**
     * Returns a user specfied by uuid, regardless of online status.
     * @param uuid
     * 		The uuid of the user.
     * @return The user.
     */
    ZenixUserInterface getRegardlessZenixUser(UUID uuid);
    
    /**
     * Save a user.
     * @param zenixUser
     *      The user to save.
     */
    void save(ZenixUserInterface zenixUser);
}
