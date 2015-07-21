/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

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
