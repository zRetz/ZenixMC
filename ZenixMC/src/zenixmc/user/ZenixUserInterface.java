/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import org.bukkit.Location;
import zenixmc.user.objects.Warning;
import org.bukkit.entity.Player;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;

/**
 * A user internally used by this plugin.
 * @author james
 */
public interface ZenixUserInterface {
    
    /**
     * Checks if user has authorisation under given node.
     * @param node The node being checked.
     * @return <code>true</code> If the user has authorisation.
     */
    boolean isAuthorised(String node);
    
    /**
     * @return The bukkit representation of a player.
     */
    Player getPlayer();
    
    /**
     * @return The bendingPlayer of the user.
     */
    BendingPlayerInterface getBendingPlayer();
    
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
     * Increments the warning value. Once the users warning value reaches 3,
     * the user receives the appropriate punishment.
     * @param time
     *      The time adding onto the punishment.
     */
    void incrementWarning(long time);
    
    /**
     * Decrements the warning value. This does not affect the already standing
     * punishment though.
     */
    void decrementWarning();
    
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
     * @return <code>true</code> If the user has at least 1 home.
     */
    boolean hasHome();
    
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
     * @return The users teleportation object.
     */
    Teleport getTeleport();
    
    /**
     * @return The amount of time the user spent online last session.
     */
    long getLastOnlineActivity();
    
    /**
     * @return The time the user was last seen active. (Can be log-off time)
     */
    long getLastActivity();
    
    /**
     * @return The time the user last did a throttled action.
     */
    long getLastThrottledAction();
    
    /**
     * Sends a message to the user.
     * @param message
     *      The message to be sent.
     */
    void sendMessage(String message);
}
