/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

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
     * Save a user.
     * @param zenixUser
     *      The user to save.
     */
    void save(ZenixUserInterface zenixUser);
}
