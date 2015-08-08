/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import zenixmc.user.Console;
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
     * @return The console of the server.
     */
    Console getConsole();
    
    /**
     * @return The servers scheduler object.
     */
    BukkitScheduler getScheduler();
    
    /**
     * Returns a bukkit representation of a user by specified name.
     * @param name
     *      The name of player.
     * @return The bukkit representation of user.
     */
    Player getPlayer(String name);
    
    /**
     * Returns a bukkit representation of a user by specified UUID.
     * @param uuid
     *      The unique identifier for player.
     * @return The bukkit representation of user.
     */
    Player getPlayer(UUID uuid);
    
    /**
     * @return All players that are online.
     */
    Collection<Player> getOnlinePlayers();
    
    /**
     * Returns a bukkit representation of a offline user by specified name.
     * @param name
     * 		The name of the offline player.
     * @return The bukkit representation of offline user.
     */
    OfflinePlayer getOfflinePlayer(String name);
    
    /**
     * Returns a bukkit representation of a offline user by specified UUID.
     * @param uuid
     * 		The unique identifier for player.
     * @return The bukkit representation of offline user.
     */
    OfflinePlayer getOfflinePlayer(UUID uuid);
    
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
     * @return The amount of players who received the broadcast.
     */
    int broadcastMessage(ZenixUserInterface sender, String node, String message);
    
    /**
     * Sends a message to all users specified.
     * @param node
     * 		The permissions node required to receive message.
     * @param message
     * 		The message to be sent.
     * @param receivers
     * 		The users recieving the message.
     * @return The amount of players who recieved the message.
     */
    int message(String node, String message, ZenixUserInterface... receivers);
    
    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed. 
     * @return The task data.
     */
    BukkitTask runTaskAsynchronously(Runnable task);

    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed. 
     * @param delay
     *      Delay of execution.
     * @return The task data.
     */
    BukkitTask runTaskLaterAsynchronously(Runnable task, long delay);
    
    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed. 
     * @param delay
     *      Delay of execution.
     * @param period
     *      Period of execution.
     * @return The task data.
     */
    BukkitTask runTaskTimerAsynchronously(Runnable task, long delay, long period);
    
    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed. 
     * @return The task id.
     */
    int scheduleSyncDelayedTask(Runnable task);

    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed. 
     * @param delay
     *      Delay of execution.
     * @return The task id.
     */
    int scheduleSyncDelayedTask(Runnable task, long delay);

    /**
     * Wrapper for main server thread.
     * @param task
     *      Task being executed.
     * @param delay
     *      Delay of execution.
     * @param period
     *      Period of execution.
     * @return The task id.
     */
    int scheduleSyncRepeatingTask(Runnable task, long delay, long period);
    
    /**
     * Returns the spawn location of specified world.
     * @param world
     *      The world to get spawn location from.
     * @return The worlds spawn location.
     */
    Location getSpawnLocation(World world);
    
    /**
     * Returns first-index world.
     * @return The first-index world.
     */
    World getWorld();
    
    /**
     * Returns a world specified by index.
     * @param index The index of the world.
     * @return The world.
     */
    World getWorld(int index) throws IndexOutOfBoundsException;
    
    /**
     * Returns a world specified by name.
     * @param name The name of the world.
     * @return The world.
     */
    World getWorld(String name) throws NullPointerException;
}
