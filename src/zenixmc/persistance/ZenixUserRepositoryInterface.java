/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.util.UUID;

import org.bukkit.entity.Player;

import zenixmc.user.ZenixUserInterface;

/**
 * Repository to persist users information when they go offline.
 * @author james
 */
public interface ZenixUserRepositoryInterface extends RepositoryInterface {
    
    /**
     * Loads a user into the memory.
     * @param player
     *      The bukkit representation to load.
     * @return The user that was loaded.
     */
    ZenixUserInterface getZenixUser(Player player); 
    
    /**
     * Returns a user specified by key.
     * @param key
     *      The key.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(Object key);
    
    /**
     * Returns a user specified by name.
     * @param name
     *      The name of the user.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(String name);
    
    /**
     * Returns a user specified by name.
     * @param uuid
     *      The unique identifier of the user.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(UUID uuid);
    
    /**
     * Save a user.
     * @param zenixUser
     *      The user to save.
     */
    void save(ZenixUserInterface zenixUser);
}
