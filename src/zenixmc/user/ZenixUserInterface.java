/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.AbilityManager;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.event.EventDispatcher;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;

/**
 * A user internally used by this plugin.
 * @author james
 */
public interface ZenixUserInterface extends Serializable, Comparable<ZenixUserInterface> {
    
	/**
	 * Sets the users zenixmc object.
	 * @param zenix
	 * 		The zenix object to set.
	 */
	void setZenixMC(ZenixMCInterface zenix);
	
	/**
	 * Sets the users eventDispatcher.
	 * @param eventDispatcher
	 * 		The eventDispatcher to set.
	 */
	void setEventDispatcher(EventDispatcher eventDispatcher);
	
    /**
     * Checks if user has authorisation under given node.
     * @param node The node being checked.
     * @return <code>true</code> If the user has authorisation.
     */
    boolean isAuthorised(String node);
    
    /**
     * Sets the bukkit representation of a user.
     * @param player
     * 		The representation to set.
     */
    void setPlayer(Player player);
    
    /**
     * May soon be gone.
     * @return The bukkit representation of a user.
     */
    @Deprecated
    Player getPlayer();
    
    /**
     * @return The name of the user.
     */
    String getName();
    
    /**
     * @return The displayName of the user.
     */
    String getDisplayName();
    
    /**
     * @return The player UUID.
     */
    UUID getUniqueId();
    
    /**
     * @return The players location.
     */
    Location getLocation();
    
    /**
     * @return The players eye location.
     */
    Location getEyeLocation();
    
    /**
     * @return The players velocity.
     */
    Vector getVelocity();
    
    /**
     * @return The players world.
     */
    World getWorld();
    
    /**
     * Sets the players exhaustion level.
     * @param value
     * 		The value to set.
     */
    void setExhaustion(float value);
    
    /**
     * @return The players exhaustion level.
     */
    float getExhaustion();
    
    /**
     * Sets the players food level.
     * @param value
     *      The value to set.
     */
    void setFoodLevel(int value);
    
    /**
     * @return The players food level.
     */
    int getFoodLevel();
    
    /**
     * Sets the players amount of health.
     * @param value
     *      The value to set.
     */
    void setHealth(double value);
    
    /**
     * @return The players amount of health.
     */
    double getHealth();
    
    /**
     * @return The players maximum amount of health.
     */
    double getMaxHealth();
    
    /**
     * @return <code>true</code> If the player is dead.
     */
    boolean isDead();
    
    /**
     * @return <code>true</code> If the player is online.
     */
    boolean isOnline();
    
    /**
     * @return The players inventory.
     */
    PlayerInventory getInventory();
    
    /**
     * Kicks the player from the server.
     * @param reason
     *      The reason for the kick.
     */
    void kickPlayer(String reason);
    
    /**
     * Sets players experience level.
     * @param value 
     *      The value to set.
     */
    void setLevel(int value);
    
    /**
     * @return The players experience level.
     */
    int getLevel();
    
    /**
     * Sets players experience points needed for next level.
     * @param value 
     *      The value to set.
     */
    void setExp(float value);
    
    /**
     * @return The players experience points needed for next level.
     */
    float getExp();
    
    /**
     * @return The amount of times this user has killed other users.
     */
    int getPlayerKills();
    
    /**
     * Teleports player to location. <b>NOTE:</b>Don't use this method.
     * @see Teleport
     * @param loc
     * 		The location to teleport to.
     * @return <code>true</code> If the player is teleported successfully.
     */
    boolean teleport(Location loc);
    
    /**
     * Sets the users bendingPlayer data.
     * @param value
     * 		The value to set.
     */
    void setBendingPlayer(BendingPlayerInterface value);
    
    /**
     * @return The bendingPlayer of the user.
     */
    BendingPlayerInterface getBendingPlayer();
    
    /**
     * Sets the users organizationPlayer data.
     * @param value
     * 		The value to set.
     */
    void setOrganizationPlayer(OrganizationPlayerInterface value);
    
    /**
     * @return The organizationPlayer of the user.
     */
    OrganizationPlayerInterface getOrganizationPlayer();
    
    /**
     * @return <code>true</code> If the user can build.
     */
    boolean canBuild();
    
    /**
     * Sets the users ability to speak.
     * @param value
     *      The value to set.
     */
    void setMuted(boolean value);
    
    /**
     * @return <code>true</code> If the user is muted.
     */
    boolean isMuted();
    
    /**
     * Sets the users ability to move.
     * @param value
     *      The value to set.
     */
    void setFrozen(boolean value);
    
    /**
     * @return <code>true</code> If the user is frozen.
     */
    boolean isFrozen();
    
    /**
     * Sets the users ability to not take damage.
     * @param value 
     *      The value to set.
     */
    void setGodMode(boolean value);
    
    /**
     * @return <code>true</code> If the user is in god mode.
     */
    boolean isGodMode();
    
    /**
     * Sets the users ability to be seen.
     * @param value
     *      The value to set.
     */
    void setVanished(boolean value);
    
    /**
     * @return <code>true</code> If the user is vanished.
     */
    boolean isVanished();
    
    /**
     * Sets the users ability to socially spy.
     * @param value
     *      The value to set.
     */
    void setSocialSpy(boolean value);
    
    /**
     * @return <code>true</code> If the user is socially spying.
     */
    boolean isSocialSpying();
    
    /**
     * Increments the warning value. Once the users warning value reaches 3,
     * the user receives the appropriate punishment.
     * @param time
     *      The time adding onto the punishment.
     */
    void incrementWarning(long time, String... reason);
    
    /**
     * Decrements the warning value. This does not affect the already standing
     * punishment though.
     */
    void decrementWarning(String... reason);
    
    /**
     * Sets the users warning value.
     * @param value
     * 		The value to set.
     */
    void setWarning(Warning value);
    
    /**
     * @return The users warning value.
     */
    Warning getWarning();
    
    /**
     * Creates a home for the user at given location.
     * @param name
     *      The name of the home.
     * @param loc
     *      The location of the home.
     */
    void setHome(String name, Location loc);
    
    /**
     * Deletes a home of the user specified by name.
     * @param name
     *      The name of the home.
     */
    void deleteHome(String name);
    
    /**
     * Returns a home specified by name.
     * @param name
     *      The name of the home.
     * @return The home.
     */
    Home getHome(String name);
    
    /**
     * Returns a home specified by location.
     * @param location
     *      The location of the home.
     * @return The home.
     */
    Home getHome(Location location);
    
    /**
     * Sets the users homes.
     * @param homes
     * 		The homes to set.
     */
    void setHomes(List<Home> homes);
    
    /**
     * @return The homes owned by this user.
     */
    List<Home> getHomes();
    
    /**
     * @return <code>true</code> If the user has at least 1 home.
     */
    boolean hasHome();
    
    /**
     * Checks if a home specified by name exists.
     * @param name
     *      The name of the home.
     * @return <code>true</code> If the home exists.
     */
    boolean homeExists(String name);
    
    /**
     * Checks if a home specified by location exists.
     * @param location
     *      The location of the home.
     * @return <code>true</code> If the home exists.
     */
    boolean homeExists(Location location);
    
    /**
     * Clears all of the users mail.
     */
    void clearMail();
    
    /**
     * Adds mail to the users collection of mail.
     * @param mail
     *      The mail to add.
     */
    void addMail(String mail);
    
    /**
     * Returns and removes the first entry of mail.
     * @return The first entry of mail.
     */
    String popMail();
    
    /**
     * Returns and removes the entry of mail specified by index.
     * @param index
     *      The position to take the mail from.
     * @return The mail.
     */
    String popMail(int index);
    
    /**
     * Returns the first entry of mail.
     * @return The mail.
     */
    String getMail();
    
    /**
     * Returns mail specified by index.
     * @param index
     *      The position to take the mail from.
     * @return The mail. 
     */
    String getMail(int index);
    
    /**
     * Sets the users mails.
     * @param mails
     * 		The mails to set.
     */
    void setMails(List<String> mails);
    
    /**
     * @return All of the mail the user owns.
     */
    List<String> getMails();
    
    /**
     * @return <code>true</code> If the user is away from keyboard.
     */
    boolean isAFK();
    
    /**
     * Sets the last known location to specified location.
     * @param loc
     */
    void setLastLocation(Location loc);
    
    /**
     * @return The users last known location.
     */
    Location getLastLocation();
    
    /**
     * Sets the teleport object to a value.
     * @param value
     * 		The value to set.
     */
    void setTeleport(Teleport value);
    
    /**
     * @return The users teleportation object.
     */
    Teleport getTeleport();
    
    /**
     * Sets the users teleportRequester.
     * @param teleportRequester
     *      The teleportRequester to set to.
     */
    void setTeleportRequester(ZenixUserInterface teleportRequester);
    
    /**
     * @return The user requested to teleport.
     */
    ZenixUserInterface getTeleportRequester();
    
    /**
     * @return The time before teleportation.
     */
    long getTeleportRequestTime();
    
    /**
     * Sets the start time of the users.
     * @param startActivity
     */
    void setStartActivity(long startActivity);
    
    /**
     * @return The start time of the users last activity. (Log-in time.)
     */
    long getStartActivity();
    
    /**
     * Sets the duration of the users last online activity.
     * @param lastOnlineActivity
     * 		The duration of the users last online activity.
     */
    void setLastOnlineActivity(long lastOnlineActivity);
    
    /**
     * @return The amount of time the user spent online last session.
     */
    long getLastOnlineActivity();
    
    /**
     * Sets the end time of the users last activity.
     * @param lastActivity
     * 		The end time of the users last activity.
     */
    void setLastActivity(long lastActivity);
    
    /**
     * @return The end time of the users last activity. (Can be log-off time.)
     */
    long getLastActivity();
    
    /**
     * @return The time the user last did a throttled action.
     */
    long getLastThrottledAction();
    
    /**
     * Sets the users ability to have freedom.
     * @param jail
     *      The jail to be sent to/from
     */
    void setJail(String jail);
    
    /**
     * @return The jail the user is currently locked in.
     */
    String getJail();
    
    /**
     * @return <code>true</code> If the user is in jail.
     */
    boolean isJailed();
    
    /**
     * Ignores the specified user.
     * @param uuid
     *      The user to ignore.
     */
    void ignoreUser(UUID uuid);
    
    /**
     * Ignores the specified user.
     * @param zui
     *      The user to ignore.
     */
    void ignoreUser(ZenixUserInterface zui);
    
    /**
     * Un-Ignores the specified user.
     * @param uuid
     *      The user to un-ignore.
     */
    void unIgnoreUser(UUID uuid);
    
    /**
     * Un-Ignores the specified user.
     * @param zui
     *      The user to un-ignore.
     */
    void unIgnoreUser(ZenixUserInterface zui);
    
    /**
     * Sets the users ignored users to a new list of users.
     * @param users
     *      The list of users to be set.
     */
    void setIgnoredUsers(List<UUID> users);
    
    /**
     * @return The users ignored users.
     */
    List<UUID> getIgnoredUsers();
    
    /**
     * Checks if the given user is ignored by this user.
     * @param uuid
     *      The user to check.
     * @return <code>true</code> If the user is ignored.
     */
    boolean isIgnoredUser(UUID uuid);
    
    /**
     * Checks if the given user is ignored by this user.
     * @param zui
     *      The user to check.
     * @return <code>true</code> If the user is ignored.
     */
    boolean isIgnoredUser(ZenixUserInterface zui);
    
    /**
     * Sends a message to the user.
     * @param message
     *      The message to be sent.
     */
    void sendMessage(String message);
    
    /**
     * @return A command sender with this users information.
     */
    ZenixCommandSender getCommandSender();
   
    /**
     * Handles Serialization.
     */
    void handleSerialize(AbilityManager abManager);
    
    /**
     * <b>Has to be offline user first.</b>
     * @param player
     * 		The online bukkit representation.
     * @return An online user.
     */
    ZenixUser toOnlineUser(Player player);
    
    /**
     * <b>Has to be online user first.</b>
     * @param player
     * 		The offline bukkit representation.
     * @return An offline user.
     */
    OfflineZenixUser toOfflineUser(OfflinePlayer player);
}
