/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.text.TextInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;

/**
 * A user internally used by this plugin.
 * @author james
 */
public class ZenixUser implements ZenixUserInterface {
    
    /**
     * The users teleportation object.
     */
    private final Teleport teleport = new Teleport();
    
    /**
     * The plugin.
     */
    private final ZenixMCInterface zenix;
    
    /**
     * The bukkit representation of the user.
     */
    private final Player player;
    
    /**
     * The users account name.
     */
    private final String username;
    
    /**
     * The users name displayed when messaging;
     */
    private final String displayName;
    
    /**
     * The users unique identifier.
     */
    private final UUID uuid;
    
    /**
     * The users bendingPlayer data.
     */
    private BendingPlayerInterface bendingPlayer;
    
    /**
     * The users ability to speak.
     */
    private boolean muted;
    
    /**
     * The users ability to move.
     */
    private boolean frozen;
    
    /**
     * The users ability to take damage.
     */
    private boolean godMode;
    
    /**
     * The users ability to be seen.
     */
    private boolean vanished;
    
    /**
     * The users ability to socially spy.
     */
    private boolean socialSpy;
    
    /**
     * The users amount of warnings and sentence.
     */
    private Warning warning;
    
    /**
     * The users collection of homes.
     */
    private List<Home> homes;
    
    /**
     * The users collection of mail.
     */
    private List<TextInterface> mails;
    
    /**
     * The users current jail. (Can be null)
     */
    private String jail;
    
    /**
     * The users collection of ignoredUsers.
     */
    private List<UUID> ignoredUsers;
    
    /**
     * The users away from keyboard value;
     */
    private boolean afk;
    
    /**
     * The users last known location.
     */
    private Location lastLocation;
    
    /**
     * The user requesting to teleport.
     */
    private ZenixUserInterface teleportRequester;
    
    /**
     * The users teleportation request time.
     */
    private long teleportRequestTime;
    
    /**
     * The duration of the users last session.
     */
    private long lastOnlineActivity;
    
    /**
     * The time of the users last activity. (Can be log-off time.)
     */
    private long lastActivity;
    
    /**
     * The time of the users last throttled action.
     */
    private long lastThrottledAction;
    
    /**
     * Instantiate.
     * @param player
     *      The bukkit representation of user.
     * @param zenix
     *      The plugin.
     */
    public ZenixUser(Player player, ZenixMCInterface zenix) {
        this.player = player;
        this.zenix = zenix;
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.displayName = player.getDisplayName();
    }
   
    @Override
    public boolean isAuthorised(String node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getPlayer() {
        return player;
    }
    
    @Override
    public String getName() {
        return username;
    }
    
    @Override
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public BendingPlayerInterface getBendingPlayer() {
        return bendingPlayer;
    }

    @Override
    public boolean canBuild() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMuted(boolean value) {
        this.muted = value;
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    @Override
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }

    @Override
    public void setGodMode(boolean value) {
        this.godMode = value;
    }

    @Override
    public boolean isGodMode() {
        return godMode;
    }

    @Override
    public void setVanished(boolean value) {
        this.vanished = value;
    }

    @Override
    public boolean isVanished() {
        return vanished;
    }
    
    @Override
    public void setSocialSpy(boolean value) {
        this.socialSpy = value;
    }

    @Override
    public boolean isSocialSpying() {
        return socialSpy;
    }

    @Override
    public void incrementWarning(long time) {
        if (time != 0) {
            if (warning.isMaximum()) {
                
            }else {
                warning.increment(time);
            }
        }
    }

    @Override
    public void decrementWarning() {
        if (!(warning.isZero())) {
            warning.decrement();
        }
    }

    @Override
    public Warning getWarning() {
        return warning;
    }

    @Override
    public void setHome(String name, Location loc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteHome(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Home getHome(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Home getHome(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Home> getHomes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean hasHome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearMail() {
        
    }

    @Override
    public void addMail(TextInterface mail) {
        
        if (mail == null || mail.isEmpty()) {
            return;
        }
        
        mails.add(mail);
    }

    @Override
    public TextInterface popMail() {
        return popMail(0);
    }

    @Override
    public TextInterface popMail(int index) {
        
        if (index > mails.size()) {
            return popMail();
        }
        
        TextInterface result = getMail(index);
        mails.remove(result);
        
        return result;
    }

    @Override
    public TextInterface getMail() {
        return getMail(0);
    }
    
    @Override
    public TextInterface getMail(int index) {
        
        if (index > mails.size()) {
            return getMail();
        }
        
        return mails.get(index);
    }

    @Override
    public List<TextInterface> getMails() {
        return mails;
    }

    @Override
    public boolean isAFK() {
        return afk;
    }

    @Override
    public void setLastLocation(Location loc) {
        this.lastLocation = loc;
    }

    @Override
    public Location getLastLocation() {
        return lastLocation;
    }

    @Override
    public Teleport getTeleport() {
        return teleport;
    }
    
    @Override
    public void setTeleportRequester(ZenixUserInterface teleportRequester) {
        this.teleportRequester = teleportRequester;
    }

    @Override
    public ZenixUserInterface getTeleportRequester() {
        return teleportRequester;
    }

    @Override
    public long getTeleportRequestTime() {
        return teleportRequestTime;
    }

    @Override
    public long getLastOnlineActivity() {
        return lastOnlineActivity;
    }

    @Override
    public long getLastActivity() {
        return lastActivity;
    }

    @Override
    public long getLastThrottledAction() {
        return lastThrottledAction;
    }
    
    @Override
    public void setJail(String jail) {
        this.jail = jail;
    }

    @Override
    public String getJail() {
        return jail;
    }

    @Override
    public boolean isJailed() {
        return jail != null;
    }
    
    @Override
    public void ignoreUser(UUID uuid) {
        if (uuid != null) {
            this.ignoredUsers.add(uuid);
            //change to offline player
            sendMessage(zenix.getSettings().getNotificationColor() + "You've added " + uuid.toString() + " to your ignoredUsers.");
        }
    }

    @Override
    public void ignoreUser(ZenixUserInterface zui) {
        ignoreUser(zui.getUniqueId());
    }

    @Override
    public void unIgnoreUser(UUID uuid) {
        if (uuid != null) {
            this.ignoredUsers.remove(uuid);
            //change to offline player
            sendMessage(zenix.getSettings().getNotificationColor() + "You've removed " + uuid.toString() + " from your ignoredUsers.");
        }
    }

    @Override
    public void unIgnoreUser(ZenixUserInterface zui) {
        unIgnoreUser(zui.getUniqueId());
    }
    
    @Override
    public void setIgnoredUsers(List<UUID> users) {
        this.ignoredUsers = users;
    }

    @Override
    public List<UUID> getIgnoredUsers() {
        return ignoredUsers;
    }

    @Override
    public boolean isIgnoredUser(UUID uuid) {
        
        for (UUID uu : ignoredUsers) {
            if (uu.compareTo(uuid) == 0) {
                return true;
            } 
        }
        
        return false;
    }

    @Override
    public boolean isIgnoredUser(ZenixUserInterface zui) {
        return isIgnoredUser(zui.getUniqueId());
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }

    @Override
    public ZenixCommandSender getCommandSender() {
        return new ZenixCommandSender(player, this);
    }
  
}
