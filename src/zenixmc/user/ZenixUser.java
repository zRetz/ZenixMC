/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;
import zenixmc.utils.StringFormatter;

/**
 * A user internally used by this plugin also acts as wrapper for bukkit representation of user.
 * 
 * TODO: INCORPORATE MessageManager
 * 
 * @author james
 */
public class ZenixUser implements ZenixUserInterface {
    
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
     * The users teleportation object.
     */
    private final Teleport teleport = new Teleport();
    
    /**
	 * User data.
	 */
	private ZenixUserData d;

    /**
     * The users bendingPlayer data.
     */
    private BendingPlayerInterface bendingPlayer;

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
     * The time of the users last throttled action.
     */
    private long lastThrottledAction;

    /**
     * Instantiate.
     *
     * @param player
     *      The bukkit representation of user.
     * @param zenix
     *      The plugin.
     * @param punishmentHandler
     *      Handler for punishments.
     */
    public ZenixUser(Player player, ZenixMCInterface zenix) {
    	this.zenix = zenix;
        this.player = player;
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
    public Location getLocation() {
        return player.getLocation();
    }
    
    @Override
    public World getWorld() {
    	return player.getWorld();
    }
    
    @Override
    public void setHealth(double value) {
        
        if (isDead()) {
            return;
        }
                
        player.setHealth(value);
    }

    @Override
    public double getHealth() {
        
        if (isDead()) {
            return 0;
        }
        
        return player.getHealth();
    }
    
    @Override
	public double getMaxHealth() {
		return player.getMaxHealth();
	}
    
    @Override
    public boolean isDead() {
        return player.isDead();
    }
    
    @Override
    public boolean isOnline() {
        return player.isOnline();
    }
    
    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }
    
    @Override
    public void kickPlayer(String reason) {
        
        if (player == null || !(player.isOnline())) {
            return;
        }
        
        String msg = reason;
        
        if (msg == null || msg.isEmpty()) {
            msg = zenix.getSettings().getKickMessage();
        }
        
        player.kickPlayer(msg);
    }

    @Override
    public void setLevel(int value) {
        player.setLevel(value);
    }

    @Override
    public int getLevel() {
        
        if (isDead()) {
            return 0;
        }
        
        return player.getLevel();
    }

    @Override
    public void setExp(float value) {
        player.setExp(value);
    }

    @Override
    public float getExp() {
        
        if (isDead()) {
            return 0;
        }
        
        return player.getExp();
    }

    @Override
	public void setBendingPlayer(BendingPlayerInterface value) {
    	this.bendingPlayer = value;
	}
    
    @Override
    public BendingPlayerInterface getBendingPlayer() {
        return bendingPlayer;
    }

    @Override
    public boolean canBuild() {
        return !d.frozen;
    }

    @Override
    public void setMuted(boolean value) {
        d.muted = value;
    }

    @Override
    public boolean isMuted() {
        return d.muted;
    }

    @Override
    public void setFrozen(boolean value) {
        d.frozen = value;
    }

    @Override
    public boolean isFrozen() {
        return d.frozen;
    }

    @Override
    public void setGodMode(boolean value) {
        d.godMode = value;
    }

    @Override
    public boolean isGodMode() {
        return d.godMode;
    }

    @Override
    public void setVanished(boolean value) {
        d.vanished = value;
    }

    @Override
    public boolean isVanished() {
        return d.vanished;
    }

    @Override
    public void setSocialSpy(boolean value) {
        d.socialSpy = value;
    }

    @Override
    public boolean isSocialSpying() {
        return d.socialSpy;
    }

    @Override
    public void incrementWarning(long time) {
        if (time != 0) {
        	d.warning.increment(time);
            sendMessage(zenix.getSettings().getErrorColor() + "You have recieved a warning. That's bad.");
        }
    }

    @Override
    public void decrementWarning() {
        d.warning.decrement();
    }

    @Override
	public void setWarning(Warning value) {	
    	d.warning = value;
    	
    	if (d.warning.isMaximum()) {
    		d.warning.increment(0);
    	}
	}
    
    @Override
    public Warning getWarning() {
        return d.warning;
    }

    @Override
    public void setHome(String name, Location loc) {
        if (d.homes == null) {
            return;
        }
        
        if (homeExists(name)) {
            sendMessage(zenix.getSettings().getErrorColor() + name + " is already a home.");
            return;
        }
        
        if (homeExists(loc)) {
            sendMessage(zenix.getSettings().getErrorColor() + StringFormatter.format(loc) + " is already a home.");
            return;
        }
        
        Home h = new Home(name, loc);
        d.homes.add(h);
    }

    @Override
    public void deleteHome(String name) {
        
        if (d.homes == null) {
            return;
        }
        
        if (!(homeExists(name))) {
            sendMessage(zenix.getSettings().getErrorColor() + "Home: " + name + " does not exist.");
            return;
        }
        
        Home h = getHome(name);
        d.homes.remove(h);
    }

    @Override
    public Home getHome(String name) {
        
        if (d.homes == null) {
            return null;
        }
        
        Home result = null;
        
        for (Home h : d.homes) {
            if (h.getName().equals(name)) {
                result = h;
            }
        }
        
        return result;
    }

    @Override
    public Home getHome(Location location) {
        
        if (d.homes == null) {
            return null;
        }
        
        Home result = null;
        
        for (Home h : d.homes) {
            if (h.getLocation().equals(location)) {
                result = h;
            }
        }
        
        return result;
    }
    
    @Override
	public void setHomes(List<Home> homes) {
    	d.homes = homes;
	}

    @Override
    public List<Home> getHomes() {
        return d.homes;
    }

    @Override
    public boolean hasHome() {
        
        if (d.homes == null) {
            return false;
        }
        
        return d.homes.size() > 0;
    }
    
    @Override
    public boolean homeExists(String name) {
        
        if (d.homes == null) {
            return false;
        }
        
        return getHome(name) != null;
    }

    @Override
    public boolean homeExists(Location location) {
        
        if (d.homes == null) {
            return false;
        }
        
        return getHome(location) != null;
    }

    @Override
    public void clearMail() {
        
        if (d.mails == null) {
            return;
        }
        
        d.mails.clear();
    }

    @Override
    public void addMail(String mail) {
        
        if (d.mails == null) {
            return;
        }
        
        if (mail.isEmpty()) {
            return;
        }

        d.mails.add(mail);
    }

    @Override
    public String popMail() {
        return popMail(0);
    }

    @Override
    public String popMail(int index) {

        if (d.mails == null) {
            return null;
        }
        
        if (index > d.mails.size() && d.mails.size() > 0) {
            sendMessage(zenix.getSettings().getSortedColor() + "Index out of bounds. Retrieving first entry.");
            return popMail();
        }
        
        if (index > d.mails.size()) {
            sendMessage(zenix.getSettings().getErrorColor() + "Index out of bounds.");
            return null;
        }

        String result = getMail(index);
        d.mails.remove(result);

        return result;
    }

    @Override
    public String getMail() {
        return getMail(0);
    }

    @Override
    public String getMail(int index) {

        if (d.mails == null) {
            return null;
        }
        
        if (index > d.mails.size() && d.mails.size() > 0) {
            sendMessage(zenix.getSettings().getSortedColor() + "Index out of bounds. Returning first entry.");
            return getMail();
        }
        
        if (index > d.mails.size()) {
            sendMessage(zenix.getSettings().getErrorColor() + "Index out of bounds.");
            return null;
        }

        return d.mails.get(index);
    }

    @Override
	public void setMails(List<String> mails) {
    	d.mails = mails;
	}
    
    @Override
    public List<String> getMails() {
        return d.mails;
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
    public void setStartActivity(long startActivity) {
    	
    	if (d.startActivity > startActivity) {
    		return;
    	}
    	
    	d.startActivity = startActivity;
    }
    
    @Override
    public long getStartActivity() {
    	return d.startActivity;
    }
    
    @Override
	public void setLastOnlineActivity(long lastOnlineActivity) {
    	d.lastOnlineActivity = lastOnlineActivity;
	}
    
    @Override
    public long getLastOnlineActivity() {
        return d.lastOnlineActivity;
    }

    @Override
	public void setLastActivity(long lastActivity) {
		
		if (lastActivity < d.lastActivity) {
			return;
		}
		
		d.lastActivity = lastActivity;
	}
    
    @Override
    public long getLastActivity() {
        return d.lastActivity;
    }

    @Override
    public long getLastThrottledAction() {
        return lastThrottledAction;
    }

    @Override
    public void setJail(String jail) {
        d.jail = jail;
    }

    @Override
    public String getJail() {
        return d.jail;
    }

    @Override
    public boolean isJailed() {
        return d.jail != null;
    }

    @Override
    public void ignoreUser(UUID uuid) {
        if (uuid != null) {
            d.ignoredUsers.add(uuid);
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
            d.ignoredUsers.remove(uuid);
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
        d.ignoredUsers = users;
    }

    @Override
    public List<UUID> getIgnoredUsers() {
        return d.ignoredUsers;
    }

    @Override
    public boolean isIgnoredUser(UUID uuid) {

        for (UUID uu : d.ignoredUsers) {
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

	@Override
	public void setData(ZenixUserData data) {
		this.d = data;
	}

	@Override
	public ZenixUserData getData() {
		return d;
	}

}
