/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.user.ZenixUserInterface;

/**
 * Class that handles punishment
 * @author james
 */
public class PunishmentHandler {
    
    /**
     * The plugin.
     */
    private final ZenixMC zenix;

    /**
     * Repository to push/fetch user data.
     */
    private CachedZenixUserRepository repository;
    
    /**
     * Instantiate.
     * @param zenix
     *      The plugin.
     */
    public PunishmentHandler(ZenixMC zenix) {
        this.zenix = zenix;
    }
    
    /**
     * Permanently bans a user.
     * @param admin
     *      The user requesting the ban.
     * @param banned
     *      The victim of the ban.
     * @param reason
     *      The reason for the ban.
     */
    public void permBan(ZenixUserInterface admin, ZenixUserInterface banned, String reason) {
        
    }
    
    /**
     * Temporarily bans a user.
     * @param admin
     *      The user requesting the ban.
     * @param banned
     *      The victim of the ban.
     * @param reason
     *      The reason for the ban.
     * @param duration 
     */
    public void tempBan(ZenixUserInterface admin, ZenixUserInterface banned, String reason, long duration) {
        
    }
}
