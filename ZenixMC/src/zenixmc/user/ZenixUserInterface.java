/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.util.Map;
import org.bukkit.entity.Player;
import zenixmc.bending.BendingPlayerInterface;

/**
 * A user internally used by this plugin.
 * @author james
 */
public interface ZenixUserInterface {
    
    /**
     * @return The bukkit representation of a player.
     */
    Player getPlayer();
    
    /**
     * @return The bendingPlayer of the user.
     */
    BendingPlayerInterface getBendingPlayer();
    
    /**
     * Sets the users ability to speak.
     * @param value
     *      The value to set.
     */
    void setMuted(boolean value);
    
    /**
     * @return If the user is muted.
     */
    boolean isMuted();
    
    /**
     * Sets the users ability to move.
     * @param value
     *      The value to set.
     */
    void setFrozen(boolean value);
    
    /**
     * @return If the user is frozen.
     */
    boolean isFrozen();
    
    /**
     * Sets the users to ability to not take damage.
     * @param value 
     *      The value to set.
     */
    void setGodMode(boolean value);
    
    /**
     * @return If the user is in god mode.
     */
    boolean isGodMode();
    
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
     * Sends a message to the user.
     * @param message The message to be sent.
     */
    void sendMessage(String message);
}
