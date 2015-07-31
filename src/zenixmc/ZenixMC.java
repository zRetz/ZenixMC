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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import zenixmc.command.MainCommandExecuter;
import zenixmc.command.commands.clans.ClanCommands;
import zenixmc.command.commands.essentials.HealCommand;
import zenixmc.command.commands.essentials.HelloCommand;
import zenixmc.command.commands.essentials.TeleportCommand;
import zenixmc.command.commands.essentials.WarningCommand;
import zenixmc.event.EventDispatcher;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.persistance.OrganizationRepository;
import zenixmc.persistance.ZenixUserRepository;
import zenixmc.user.Console;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserListener;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.ExceptionUtil;

/**
 *
 * @author james
 * Copyright 2015, James Leaver, All rights reserved.
 */
public class ZenixMC extends JavaPlugin implements ZenixMCInterface {
    
	/**
	 * The handler for punishments.
	 */
	PunishmentHandler punishmentHandler = new PunishmentHandler(this);
	
	/**
	 * Plugin Settings.
	 */
    SettingsInterface settings = new Settings(this ,this.getConfig());
    
    /**
     * The EventDispatcher.
     */
    EventDispatcher eventDispatcher = new EventDispatcher(this);
    
    /**
     * The servers console.
     */
    Console console = new Console("Console", this);
    
    /**
     * Persistence of organization data to disk.
     */
    OrganizationRepository organizationRepository = new OrganizationRepository(this.getLogger(), new File(this.getDataFolder(), "organization"));
    
    /**
     * Loading/Saving on Enable/Disable.
     */
    CachedOrganizationRepository cachedOrganizationRepository = new CachedOrganizationRepository(organizationRepository, this.getLogger());
    
    /**
     * Persistence of user data to disk.
     */
    ZenixUserRepository zenixUserRepository = new ZenixUserRepository(this.getLogger(), new File(this.getDataFolder(), "users"), this, eventDispatcher);
    
    /**
     * Loading/Saving on Join/Leave.
     */
    CachedZenixUserRepository cachedZenixUserRepository = new CachedZenixUserRepository(zenixUserRepository, this, this.getLogger());
    
    /**
     * Main command.
     */
    MainCommandExecuter mainCommandExecuter = new MainCommandExecuter(cachedZenixUserRepository);
    
    /**
     * Zenix User Manager.
     */
    ZenixUserManager zenixUserManager = new ZenixUserManager(cachedZenixUserRepository, eventDispatcher);
    
    /**
     * The Zenix Listener.
     */
    ZenixUserListener zenixListener = new ZenixUserListener(punishmentHandler, this, zenixUserManager, eventDispatcher);
    
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabling Zenix. Powered by Zenix.");
        
        cachedOrganizationRepository.open("Organization Repository has opened.");
        cachedZenixUserRepository.open("Zenix User Repository has opened.");
        
        eventDispatcher.registerEventListener(cachedZenixUserRepository);
        eventDispatcher.registerEventListener(zenixListener);
        
        getCommand("z").setExecutor(mainCommandExecuter);
        mainCommandExecuter.addSubCommand(new HelloCommand(this, zenixUserManager));
        mainCommandExecuter.addSubCommand(new HealCommand(this, zenixUserManager));
        mainCommandExecuter.addSubCommand(new TeleportCommand(this, zenixUserManager));
        mainCommandExecuter.addSubCommand(new WarningCommand(this, zenixUserManager));
        mainCommandExecuter.addSubCommand(new ClanCommands(this));
        
        for (final Player player : getServer().getOnlinePlayers()) {
        	cachedZenixUserRepository.onPlayerJoin(new PlayerJoinEvent(player, null));
        }
        
    }

    @Override
    public void onDisable() {
        
        getLogger().log(Level.INFO, "Disabling Zenix. Powered by Zenix.");
        for (final Player player : getServer().getOnlinePlayers()) {
        	cachedZenixUserRepository.onPlayerQuit(new PlayerQuitEvent(player, null));
        }
    }

    @Override
    public void reload() {
    	this.reloadConfig();
    }

    @Override
    public SettingsInterface getSettings() {
        return settings;
    }
    
    @Override
	public Console getConsole() {
		return console;
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
    
    @SuppressWarnings("unchecked")
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
            
            final ZenixUserInterface user  = zenixUserManager.getZenixUser(player);
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
            throw ExceptionUtil.nullPointerException("name cannot be null");
        }
        
        World result = null;
        
        for (World w : this.getServer().getWorlds()) {
            if (w.equals(name)) {
                result = w;
            }
        }
        
        if (result == null) {
            throw ExceptionUtil.nullPointerException("result cannot be null");
        }else {
            return result;
        }
    }
    
}
