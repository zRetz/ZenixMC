/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.AbilityManager;
import zenixmc.bending.BendingPlayer;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.event.EventDispatcher;
import zenixmc.io.SerializableLocation;
import zenixmc.organization.OrganizationPlayer;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

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
	protected transient ZenixMCInterface zenix;
	
	/**
	 * The event dispatcher to fire events.
	 */
	protected transient EventDispatcher eventDispatcher;
	
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
    protected String username;

    /**
     * The users name displayed when messaging;
     */
    protected String displayName;

    /**
     * The users unique identifier.
     */
    protected UUID uuid;
    
    /**
     * The users amount of warnings and sentence.
     */
	protected Warning warning;

    /**
     * The users bendingPlayer data.
     */
    protected BendingPlayerInterface bendingPlayer = new BendingPlayer(this);
    
    /**
     * The users organizationPlayer data.
     */
    protected OrganizationPlayerInterface organizationPlayer = new OrganizationPlayer(this);

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
	private boolean muted = false;
	
	/**
     * The users ability to move.
     */
	private boolean frozen = false;
	
	/**
     * The users ability to take damage.
     */
	private boolean godMode = false;
	
	/**
     * The users ability to be seen.
     */
	private boolean vanished = false;
	
	/**
     * The users ability to socially spy.
     */
	private boolean socialSpy = false;
	
	/**
     * The users collection of homes.
     */
	private List<Home> homes = new ArrayList<>();
	
	/**
     * The users collection of mail.
     */
	private List<String> mails = new ArrayList<>();
	
	/**
     * The users current jail.
     */
	private String jail = " ";
	
	/**
     * The users collection of ignoredUsers.
     */
	private List<UUID> ignoredUsers = new ArrayList<>();
	
	/**
     * The start time of the users last activity. (Log-in time.) 
     */
	private long startActivity = System.currentTimeMillis();
	
	/**
     * The duration of the users last session.
     */
	private long lastOnlineActivity = 0;
	
	/**
     * The end time of the users last activity. (Can be log-off time.)
     */
	private long lastActivity = startActivity;
	
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
    	this.teleport = new Teleport(eventDispatcher, this, this.zenix);
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
        return player.hasPermission(node);
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
    	
    	if (player == null) {
    		return getLastLocation();
    	}
    	
        return player.getLocation();
    }
    
    @Override
    public Location getEyeLocation() {
    	
    	if (player == null) {
    		return getLastLocation().add(0, 1, 0);
    	}
    	
        return player.getEyeLocation();
    }
    
    @Override
	public Vector getVelocity() {
		return player.getVelocity();
	}
    
    @Override
    public World getWorld() {
    	return player.getWorld();
    }
    
    @Override
    public void setExhaustion(float value) {
    	
    	if (isDead()) {
    		return;
    	}
    	
    	player.setExhaustion(value);
    }
    
    @Override
    public float getExhaustion() {
    	return player.getExhaustion();
    }
    
    @Override
    public void setFoodLevel(int value) {
    	
    	if (isDead()) {
    		return;
    	}
    	
    	player.setFoodLevel(value);
    }
    
    @Override
    public int getFoodLevel() {
    	return player.getFoodLevel();
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
            msg = zenix.getSettings().kickMessage();
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
    public int getPlayerKills() {
    	return player.getStatistic(Statistic.PLAYER_KILLS);
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
            sendMessage(StringFormatter.format("You have recieved a warning. REASON: " + r, MessageOccasion.ERROR, zenix));
        }
    }

    @Override
    public void decrementWarning(String... reason) {
        warning.decrement();
        sendMessage(StringFormatter.format("You have lost a warning. REASON: " + JavaUtil.arrayToString(reason), MessageOccasion.ESSENTIAL, zenix));
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
            sendMessage(StringFormatter.format(name + " is already a home.", MessageOccasion.ERROR, zenix));
            return;
        }
        
        if (homeExists(loc)) {
            sendMessage(StringFormatter.format(StringFormatter.format(loc) + " is already a home.", MessageOccasion.ERROR, zenix));
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
            sendMessage(StringFormatter.format("Home: " + name + " does not exist.", MessageOccasion.ERROR, zenix));
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
        sendMessage(zenix.getSettings().getNotificationColor() + "You have recieved mail!");
    }

    @Override
    public String popMail() {
        return popMail(0);
    }

    @Override
    public String popMail(int index) {

        String result = getMail(index);
        mails.remove(result);
        
        sendMessage(StringFormatter.format("Retrieving mail.", MessageOccasion.ESSENTIAL, zenix));
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
            sendMessage(StringFormatter.format("Index out of bounds. Returning first entry.", MessageOccasion.HANDLED, zenix));
            return getMail();
        }
        
        if (index > mails.size()) {
            sendMessage(StringFormatter.format("Index out of bounds.", MessageOccasion.ERROR, zenix));
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
            sendMessage(StringFormatter.format("You've added " + uuid.toString() + " to your ignoredUsers.", MessageOccasion.ESSENTIAL, zenix));
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
            sendMessage(StringFormatter.format("You've removed " + uuid.toString() + " from your ignoredUsers.", MessageOccasion.ESSENTIAL, zenix));
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
	public void handleSerialize(AbilityManager abManager) {
		this.warning.setParent(this);
		this.warning.setEventDispatcher(eventDispatcher);
		this.organizationPlayer.setZenixUser(this);
		this.bendingPlayer.setZenixUser(this);
		for (Home h : homes) {
			if (h != null) {
				h.setZenixUser(this);
			}
		}
		abManager.changePreset(bendingPlayer, bendingPlayer.getCurrentPreset());
	}

	@Override
	public ZenixUser toOnlineUser(Player player) {
		return this;
	}

	@Override
	public OfflineZenixUser toOfflineUser(OfflinePlayer player) {
		return new OfflineZenixUser(player, this);
	}
	
	@Override
	public int compareTo(ZenixUserInterface zui) {
		return zui.getUniqueId().compareTo(this.getUniqueId());
	}
	
	/**
	 * Serialize this instance.
	 * 
	 * @param out
	 *            Target to which this instance is written.
	 * @throws IOException
	 *             Thrown if exception occurs during serialization.
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(username);
		out.writeUTF(displayName);
		out.writeObject(uuid);
		out.writeObject(warning);
		out.writeObject(bendingPlayer);
		out.writeObject(organizationPlayer);
		out.writeBoolean(afk);
		if (lastLocation == null) {
			setLastLocation(getLocation());
		}
		out.writeObject(new SerializableLocation(lastLocation));
		out.writeLong(lastThrottledAction);
		out.writeBoolean(muted);
		out.writeBoolean(frozen);
		out.writeBoolean(godMode);
		out.writeBoolean(vanished);
		out.writeBoolean(socialSpy);
		out.writeObject(homes);
		out.writeObject(mails);
		out.writeUTF(jail);
		out.writeObject(ignoredUsers);
		out.writeLong(startActivity);
		out.writeLong(lastOnlineActivity);
		out.writeLong(lastActivity);
	}

	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in
	 *            Input Stream from which this instance is to be deserialized.
	 * @throws IOException
	 *             Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException
	 *             Thrown if expected class is not found.
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		username = in.readUTF();
		displayName = in.readUTF();
		uuid = (UUID) in.readObject();
		warning = (Warning) in.readObject();
		bendingPlayer = (BendingPlayerInterface) in.readObject();
		organizationPlayer = (OrganizationPlayerInterface) in.readObject();
		afk = in.readBoolean();
		lastLocation = ((SerializableLocation) in.readObject()).toLocation();
		lastThrottledAction = in.readLong();
		muted = in.readBoolean();
		frozen = in.readBoolean();
		godMode = in.readBoolean();
		vanished = in.readBoolean();
		socialSpy = in.readBoolean();
		homes = (List<Home>) in.readObject();
		mails = (List<String>) in.readObject();
		jail = in.readUTF();
		ignoredUsers = (List<UUID>) in.readObject();
		startActivity = in.readLong();
		lastOnlineActivity = in.readLong();
		lastActivity = in.readLong();
	}
	
}

