/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import zenixmc.command.MainCommandExecuter;
import zenixmc.command.commands.clans.ClanCommands;
import zenixmc.command.commands.essentials.Hello;
import zenixmc.event.EventDispatcher;
import zenixmc.persistance.BendingPlayerRepository;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.persistance.ZenixUserRepository;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.ExceptionUtils;

/**
 *
 * @author james
 */
public class ZenixMC extends JavaPlugin implements ZenixMCInterface {
    
	
	/**
	 * Plugin Settings.
	 */
    SettingsInterface settings;
    
    /**
     * The EventDispatcher.
     */
    EventDispatcher eventDispatcher = new EventDispatcher(this);
    
    /**
     * Persistence of bending data to disk.
     */
    BendingPlayerRepository bendingPlayerRepository = new BendingPlayerRepository(this.getLogger(), new File(this.getDataFolder(), "bending"));
    
    /**
     * Persistence of user data to disk.
     */
    ZenixUserRepository zenixUserRepository = new ZenixUserRepository(this.getLogger(), new File(this.getDataFolder(), "users"), this, eventDispatcher);
   
    /**
     * Manager of integer identifiers.
     */
    IDManager idManager = new IDManager(this.getLogger(), new File(this.getDataFolder(), "id"));
    
    /**
     * Loading/Saving on Join/Leave.
     */
    CachedZenixUserRepository repository = new CachedZenixUserRepository(zenixUserRepository, this, this.getLogger());
    
    /**
     * Main command.
     */
    MainCommandExecuter mainCommandExecuter = new MainCommandExecuter(repository);
    
    @Override
    public void onEnable() {
    	
    	handleConfig();
        getLogger().log(Level.INFO, "Enabling Zenix. Powered by Zenix.");
        
        settings = new Settings(this.getConfig());
        
        zenixUserRepository.open();
        
        eventDispatcher.registerEventListener(repository);
        
        getCommand("z").setExecutor(mainCommandExecuter);
        mainCommandExecuter.addSubCommand(new Hello(this));
        mainCommandExecuter.addSubCommand(new ClanCommands(this));
        
        for (final Player player : getServer().getOnlinePlayers()) {
            repository.onPlayerJoin(new PlayerJoinEvent(player, null));
        }
        
    }

    @Override
    public void onDisable() {
        
        getLogger().log(Level.INFO, "Disabling Zenix. Powered by Zenix.");
        for (final Player player : getServer().getOnlinePlayers()) {
            repository.onPlayerQuit(new PlayerQuitEvent(player, null));
        }
    }

    @Override
    public void reload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SettingsInterface getSettings() {
        return settings;
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
        
        if (sender == null) {
            sender = (ZenixUserInterface) getServer().getConsoleSender();
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
    
    private void handleConfig() {
    	
    	FileConfiguration config = this.getConfig();
    	
    	config.addDefault("errorColor", "RED");
    	config.addDefault("notificationColor", "GREEN");
    	config.addDefault("sortedColor", "GOLD");
    	config.addDefault("materialBlackList", Arrays.asList("TNT"));
    	config.addDefault("allowSilentJoinQuit", true);
    	config.addDefault("quitMessage", "Zenix wishes you farewell, ");
    	config.addDefault("joinMessage", "Zenix greets you, ");
    	config.addDefault("kickMessage", "Zenix kicked you.");
    	config.addDefault("banMessage", "Zenix banned you.");
    	
    	config.options().copyDefaults(true);
    	
    	this.saveConfig();
    
    }
    
}
