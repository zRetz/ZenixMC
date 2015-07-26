/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * Repository to persist bendingPlayers information when they go offline.
 * @author james
 */
public interface BendingPlayerRepositoryInterface extends RepositoryInterface {
    
    /**
     * Loads a bendingPlayer into memory.
     * @param player
     *      The bukkit representation to load.
     * @return The bendingPlayer that was loaded.
     */
    BendingPlayerInterface getBendingPlayer(ZenixUserInterface player);
    
    /**
     * Sets the repository's user repository.
     * @param zenixUserRepository
     * 		The repository to set.
     */
    void setZenixUserRepository(ZenixUserRepositoryInterface zenixUserRepository);
    
    /**
     * Saves a bendingPlayer.
     * @param bendingPlayer
     *      The bendingPlayer to save.
     */
    void save(final BendingPlayerInterface bendingPlayer);
    
}
