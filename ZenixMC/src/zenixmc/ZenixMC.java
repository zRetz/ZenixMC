/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.persistance.ZenixUserRepository;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.ExceptionUtils;

/**
 *
 * @author james
 */
public class ZenixMC extends JavaPlugin implements ZenixMCInterface {
    
    ZenixUserRepository zenixUserRepository = new ZenixUserRepository(this.getLogger(), new File(this.getDataFolder(), "users"), this);
    
    CachedZenixUserRepository repository = new CachedZenixUserRepository(zenixUserRepository, this, this.getLogger());
    
    @Override
    public void onEnable() {
        
        getLogger().log(Level.INFO, "Enabling Zenix. Powered by Zenix.");
    }

    @Override
    public void onDisable() {
        
        getLogger().log(Level.INFO, "Disabling Zenix. Powered by Zenix.");
    }

    @Override
    public void reload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SettingsInterface getSettings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BukkitScheduler getScheduler() {
        return this.getServer().getScheduler();
    }
    
    @Override
    public Player getPlayer(String name) {
        return this.getServer().getPlayer(name);
    }
    
    @Override
    public Player getPlayer(UUID uuid) {
        return this.getServer().getPlayer(uuid);
    }
    
    @Override
    public Collection<Player> getOnlinePlayers() {
        return (Collection<Player>) this.getServer().getOnlinePlayers();
    }

    @Override
    public int broadcastMessage(ZenixUserInterface sender, String message) {
        return broadcastMessage(sender, null, message);
    }

    @Override
    public int broadcastMessage(ZenixUserInterface sender, String node, String message) {
        
        if (sender != null) {
            return 0;
        }
        
        Collection<Player> players = getOnlinePlayers();
        List<ZenixUserInterface> seen = new ArrayList<>();
        
        for (Player player : players) {
            
            final ZenixUserInterface user  = repository.getZenixUser(player);
            if (node != null) {
                if (!(user.isAuthorised(node))) {
                    continue;
                }
            }
            user.sendMessage(message);
            seen.add(user);
        }
        
        return seen.size();
    }

    @Override
    public BukkitTask runTaskAsynchronously(Runnable task) {
        return getScheduler().runTaskAsynchronously(this, task);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Runnable task, long delay) {
        return getScheduler().runTaskLaterAsynchronously(this, task, delay);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        return getScheduler().runTaskTimerAsynchronously(this, task, delay, period);
    }

    @Override
    public int scheduleSyncDelayedTask(Runnable task) {
        return getScheduler().scheduleSyncDelayedTask(this, task);
    }

    @Override
    public int scheduleSyncDelayedTask(Runnable task, long delay) {
        return getScheduler().scheduleSyncDelayedTask(this, task, delay);
    }

    @Override
    public int scheduleSyncRepeatingTask(Runnable task, long delay, long period) {
        return getScheduler().scheduleSyncRepeatingTask(this, task, delay, period);
    }

    @Override
    public Location getSpawnLocation(World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public World getWorld() {
        return getWorld(0);
    }

    @Override
    public World getWorld(int index) throws IndexOutOfBoundsException {
        return this.getServer().getWorlds().get(0);
    }

    @Override
    public World getWorld(String name) throws NullPointerException {
        
        if (name == null || name.isEmpty()) {
            throw ExceptionUtils.nullPointerException("name cannot be null");
        }
        
        World result = null;
        
        for (World w : this.getServer().getWorlds()) {
            if (w.equals(name)) {
                result = w;
            }
        }
        
        if (result == null) {
            throw ExceptionUtils.nullPointerException("result cannot be null");
        }else {
            return result;
        }
    }
    
}
