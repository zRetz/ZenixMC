/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import zenixmc.user.ZenixUserInterface;

/**
 * Zenix For Minecraft
 * @author james
 */
public interface ZenixMCInterface extends Plugin {
    
    /**
     * Reloads everything internally.
     */
    void reload();
    
    /**
     * @return The plugins settings.
     */
    SettingsInterface getSettings();
   
    /**
     * Returns a user specified by key.
     * @param key
     *      The key.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(Object key);
    
    /**
     * Returns a user specified by name.
     * @param name
     *      The name of the user.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(String name);
    
    /**
     * Returns a user specified by name.
     * @param uuid
     *      The unique identifier of the user.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(UUID uuid);
    
    /**
     * Returns a user specified by player.
     * @param player
     *      The bukkit representation of the user.
     * @return The user.
     */
    ZenixUserInterface getZenixUser(Player player);
    
    /**
     * Broadcasts a message to the entire server.
     * @param sender
     *      The sender of the broadcast.
     * @param message
     *      The message being broadcasted.
     * @return The amount of players who received the broadcast.
     */
    int broadcastMessage(ZenixUserInterface sender, String message);
    
    /**
     * Broadcasts a message to the everyone who has authorisation to receive.
     * @param sender
     *      The sender of the broadcast.
     * @param node
     *      The permissions node required to receive broadcast.
     * @param message
     *      The message being broadcasted.
     * @return The amount of players of who received the broadcast.
     */
    int broadcastMessage(ZenixUserInterface sender, String node, String message);
    
    
}
