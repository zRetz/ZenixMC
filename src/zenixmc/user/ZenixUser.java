/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayer;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.event.EventDispatcher;
import zenixmc.organization.OrganizationPlayer;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;
import zenixmc.utils.JavaUtil;
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
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2047523602803536907L;
	
	/**
	 * The plugin.
	 */
	private transient ZenixMCInterface zenix;
	
	/**
	 * The event dispatcher to fire events.
	 */
	private transient EventDispatcher eventDispatcher;
	
    /**
     * The bukkit representation of the user.
     */
    private transient Player player;
    
    /**
     * The users teleportation object.
     */
    private transient Teleport teleport;
    
    /**
     * The user requesting to teleport.
     */
    private transient ZenixUserInterface teleportRequester;

    /**
     * The users teleportation request time.
     */
    private transient long teleportRequestTime;
    
    /**
     * The users command sender object.
     */
    private transient ZenixCommandSender zenixCommandSender;

    /**
     * The users account name.
     */
    private String username;

    /**
     * The users name displayed when messaging;
     */
    private String displayName;

    /**
     * The users unique identifier.
     */
    private UUID uuid;
    
    /**
     * The users amount of warnings and sentence.
     */
	private Warning warning;

    /**
     * The users bendingPlayer data.
     */
    private BendingPlayerInterface bendingPlayer = new BendingPlayer(this);
    
    /**
     * The users organizationPlayer data.
     */
    private OrganizationPlayerInterface organizationPlayer = new OrganizationPlayer(this);

    /**
     * The users away from keyboard value;
     */
    private boolean afk = false;

    /**
     * The users last known location.
     */
    private Location lastLocation;

    /**
     * The time of the users last throttled action.
     */
    private long lastThrottledAction = 0;
    
    /**
     * The users ability to speak.
     */
	public boolean muted = false;
	
	/**
     * The users ability to move.
     */
	public boolean frozen = false;
	
	/**
     * The users ability to take damage.
     */
	public boolean godMode = false;
	
	/**
     * The users ability to be seen.
     */
	public boolean vanished = false;
	
	/**
     * The users ability to socially spy.
     */
	public boolean socialSpy = false;
	
	/**
     * The users collection of homes.
     */
	public List<Home> homes = new ArrayList<>();
	
	/**
     * The users collection of mail.
     */
	public List<String> mails = new ArrayList<>();
	
	/**
     * The users current jail. (Can be null)
     */
	public String jail = null;
	
	/**
     * The users collection of ignoredUsers.
     */
	public List<UUID> ignoredUsers = new ArrayList<>();
	
	/**
     * The start time of the users last activity. (Log-in time.) 
     */
	public long startActivity = System.currentTimeMillis();
	
	/**
     * The duration of the users last session.
     */
	public long lastOnlineActivity = 0;
	
	/**
     * The end time of the users last activity. (Can be log-off time.)
     */
	public long lastActivity = startActivity;
	
    /**
     * Creates a new Zenix User.
     *
     * @param player
     *      The bukkit representation of user.
     * @param zenix
     *      The plugin.
     * @param punishmentHandler
     *      Handler for punishments.
     */
    public ZenixUser(Player player, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
    	this.zenix = zenix;
    	this.teleport = new Teleport(this, this.zenix);
    	this.eventDispatcher = eventDispatcher;
    	this.warning = new Warning(this, this.eventDispatcher);
        this.player = player;
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.displayName = player.getDisplayName();
    }

    @Override
	public void setZenixMC(ZenixMCInterface zenix) {
		this.zenix = zenix;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}
    
    @Override
    public boolean isAuthorised(String node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setPlayer(Player player) {
        this.player = player;
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
	public boolean teleport(Location loc) {
    	
    	this.setLastLocation(loc);
    	
		return player.teleport(loc);
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
	public void setOrganizationPlayer(OrganizationPlayerInterface value) {
		this.organizationPlayer = value;
	}

	@Override
	public OrganizationPlayerInterface getOrganizationPlayer() {
		return organizationPlayer;
	}

    @Override
    public boolean canBuild() {
        return !frozen;
    }

    @Override
    public void setMuted(boolean value) {
        muted = value;
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    @Override
    public void setFrozen(boolean value) {
        frozen = value;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }

    @Override
    public void setGodMode(boolean value) {
        godMode = value;
    }

    @Override
    public boolean isGodMode() {
        return godMode;
    }

    @Override
    public void setVanished(boolean value) {
        vanished = value;
    }

    @Override
    public boolean isVanished() {
        return vanished;
    }

    @Override
    public void setSocialSpy(boolean value) {
        socialSpy = value;
    }

    @Override
    public boolean isSocialSpying() {
        return socialSpy;
    }

    @Override
    public void incrementWarning(long time, String... reason) {
        if (time != 0) {
        	String r = JavaUtil.arrayToString(reason);
        	warning.increment(time, r);
            sendMessage(zenix.getSettings().getErrorColor() + "You have recieved a warning. REASON: " + r);
        }
    }

    @Override
    public void decrementWarning(String... reason) {
        warning.decrement();
        sendMessage(zenix.getSettings().getNotificationColor() + "You have lost a warning. REASON: " + JavaUtil.arrayToString(reason));
    }
    
    @Override
	public void setWarning(Warning value) {
    	this.warning = value;
	}
    
    @Override
    public Warning getWarning() {
        return warning;
    }

    @Override
    public void setHome(String name, Location loc) {
        if (homes == null) {
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
        homes.add(h);
    }

    @Override
    public void deleteHome(String name) {
        
        if (homes == null) {
            return;
        }
        
        if (!(homeExists(name))) {
            sendMessage(zenix.getSettings().getErrorColor() + "Home: " + name + " does not exist.");
            return;
        }
        
        Home h = getHome(name);
        homes.remove(h);
    }

    @Override
    public Home getHome(String name) {
        
        if (homes == null) {
            return null;
        }
        
        Home result = null;
        
        for (Home h : homes) {
            if (h.getName().equals(name)) {
                result = h;
            }
        }
        
        return result;
    }

    @Override
    public Home getHome(Location location) {
        
        if (homes == null) {
            return null;
        }
        
        Home result = null;
        
        for (Home h : homes) {
            if (h.getLocation().equals(location)) {
                result = h;
            }
        }
        
        return result;
    }
    
    @Override
	public void setHomes(List<Home> homes) {
    	this.homes = homes;
	}

    @Override
    public List<Home> getHomes() {
        return homes;
    }

    @Override
    public boolean hasHome() {
        
        if (homes == null) {
            return false;
        }
        
        return homes.size() > 0;
    }
    
    @Override
    public boolean homeExists(String name) {
        
        if (homes == null) {
            return false;
        }
        
        return getHome(name) != null;
    }

    @Override
    public boolean homeExists(Location location) {
        
        if (homes == null) {
            return false;
        }
        
        return getHome(location) != null;
    }

    @Override
    public void clearMail() {
        
        if (mails == null) {
            return;
        }
        
        mails.clear();
    }

    @Override
    public void addMail(String mail) {
        
        if (mails == null) {
            return;
        }
        
        if (mail.isEmpty()) {
            return;
        }

        mails.add(mail);
    }

    @Override
    public String popMail() {
        return popMail(0);
    }

    @Override
    public String popMail(int index) {

        if (mails == null) {
            return null;
        }
        
        if (index > mails.size() && mails.size() > 0) {
            sendMessage(zenix.getSettings().getSortedColor() + "Index out of bounds. Retrieving first entry.");
            return popMail();
        }
        
        if (index > mails.size()) {
            sendMessage(zenix.getSettings().getErrorColor() + "Index out of bounds.");
            return null;
        }

        String result = getMail(index);
        mails.remove(result);

        return result;
    }

    @Override
    public String getMail() {
        return getMail(0);
    }

    @Override
    public String getMail(int index) {

        if (mails == null) {
            return null;
        }
        
        if (index > mails.size() && mails.size() > 0) {
            sendMessage(zenix.getSettings().getSortedColor() + "Index out of bounds. Returning first entry.");
            return getMail();
        }
        
        if (index > mails.size()) {
            sendMessage(zenix.getSettings().getErrorColor() + "Index out of bounds.");
            return null;
        }

        return mails.get(index);
    }

    @Override
	public void setMails(List<String> mails) {
    	this.mails = mails;
	}
    
    @Override
    public List<String> getMails() {
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
    public void setTeleport(Teleport value) {
        this.teleport = value;
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
    	
    	if (this.startActivity > startActivity) {
    		return;
    	}
    	
    	this.startActivity = startActivity;
    }
    
    @Override
    public long getStartActivity() {
    	return startActivity;
    }
    
    @Override
	public void setLastOnlineActivity(long lastOnlineActivity) {
    	this.lastOnlineActivity = lastOnlineActivity;
	}
    
    @Override
    public long getLastOnlineActivity() {
        return lastOnlineActivity;
    }

    @Override
	public void setLastActivity(long lastActivity) {
		
		if (this.lastActivity < lastActivity) {
			return;
		}
		
		this.lastActivity = lastActivity;
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
        return this.jail;
    }

    @Override
    public boolean isJailed() {
        return this.jail != null;
    }

    @Override
    public void ignoreUser(UUID uuid) {
        if (uuid != null) {
            ignoredUsers.add(uuid);
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
            ignoredUsers.remove(uuid);
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
        ignoredUsers = users;
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
    	if (zenixCommandSender == null) {
    		zenixCommandSender = new ZenixCommandSender(player, this);
    	}
        return zenixCommandSender;
    }

	@Override
	public void handleSerialize() {
		this.warning.setParent(this);
		this.warning.setEventDispatcher(eventDispatcher);
	}
    
}
