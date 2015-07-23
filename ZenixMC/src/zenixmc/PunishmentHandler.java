/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.bukkit.BanList;
import org.bukkit.Server;
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
        tempBan(admin, banned, reason, 0);
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
        
        if (banned == null) {
            return;
        }
        
        Server server = zenix.getServer();
        BanList banlist = server.getBanList(BanList.Type.NAME);
        
        if (banlist.isBanned(banned.getName())) {
            admin.sendMessage(zenix.getSettings().getSortedColor() + "This user is already banned.");
            return;
        }
        
        Calendar expire = null;
        
        if (duration > 0 ) {
            expire = new GregorianCalendar();
            expire.setTimeInMillis(expire.getTimeInMillis() + duration);
        }
        
        Date expDate = null;
        
        if (expire != null) {
            expDate = expire.getTime();        
        }
        
        banlist.addBan(banned.getName(), reason, expDate, admin.getName());
        
        if (banned.isOnline()) {
            kickUser(admin, banned, reason);
        }    
                
        zenix.broadcastMessage(admin, zenix.getSettings().getErrorColor() + banned.getName() + " is being banned for REASON: " + reason + ".");
    }
    
    /**
     * Kicks specified user from the server.
     * @param admin
     *      The user requesting the kick.
     * @param kicked
     *      The victim of the kick.
     * @param reason 
     *      The reason for the kick.
     */
    public void kickUser(ZenixUserInterface admin, ZenixUserInterface kicked, String reason) {
        
        if (kicked == null) {
            return;
        }
        
        if (!(kicked.isOnline())) {
            admin.sendMessage(zenix.getSettings().getErrorColor() + kicked.getName() + "is not online.");
            return;
        }
        
        kicked.kickPlayer(reason);
        zenix.broadcastMessage(admin, zenix.getSettings().getErrorColor() + kicked.getName() + " is being kicked for REASON: " + reason + ".");
    }
}
