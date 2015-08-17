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
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import zenixmc.bending.AbilityManager;
import zenixmc.bending.BendingListener;
import zenixmc.bending.ability.airbending.AirManipulation;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.commands.bending.BendingMainCommand;
import zenixmc.command.commands.clans.ClanMainCommand;
import zenixmc.command.commands.essentials.FeedCommand;
import zenixmc.command.commands.essentials.FreezeCommand;
import zenixmc.command.commands.essentials.HealCommand;
import zenixmc.command.commands.essentials.HelloCommand;
import zenixmc.command.commands.essentials.HelpCommand;
import zenixmc.command.commands.essentials.TeleportCommand;
import zenixmc.command.commands.essentials.ViewPermissionsCommand;
import zenixmc.command.commands.essentials.WarningDecrementCommand;
import zenixmc.command.commands.essentials.WarningIncrementCommand;
import zenixmc.event.EventDispatcher;
import zenixmc.event.update.Updater;
import zenixmc.organization.OrganizationListener;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerListener;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.organization.clans.defaults.Wild;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.persistance.CachedTerritoryRepository;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.persistance.OrganizationRepository;
import zenixmc.persistance.TerritoryRepository;
import zenixmc.persistance.ZenixUserRepository;
import zenixmc.user.Console;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserListener;
import zenixmc.user.ZenixUserManager;

/**
 *
 * @author james
 * Copyright 2015, James Leaver, All rights reserved.
 */
public class ZenixMC extends JavaPlugin implements ZenixMCInterface {
    
	/**
	 * Instance of main class. Only use as last resort.
	 * <b>NOTE: Reflect into instance.</b> 
	 */
	public static ZenixMC instance;
	
	/**
	 * The handler for punishments.
	 */
	PunishmentHandler punishmentHandler = new PunishmentHandler(this);
	
	/**
	 * Plugin Settings.
	 */
    SettingsInterface settings = new Settings(this, this.getConfig());
    
    /**
     * The EventDispatcher.
     */
    EventDispatcher eventDispatcher = new EventDispatcher(this);
    
    /**
     * Ability Manager
     */
    AbilityManager abilityManager = new AbilityManager(eventDispatcher);
    
    /**
     * Persistence of territory to disk.
     */
    TerritoryRepository terRepository = new TerritoryRepository(this.getLogger(), new File(this.getDataFolder(), "territory"), this);
    
    /**
     * Loading/Saving on Enable/Disable.
     */
    CachedTerritoryRepository cachedTerRepository = new CachedTerritoryRepository(this.getLogger(), terRepository, this);
    
    /**
     * Territory Manager.
     */
    TerritoryManager terManager = new TerritoryManager(this, eventDispatcher, cachedTerRepository);
    
    /**
     * The servers console.
     */
    Console console = new Console("Console", this);
    
    /**
     * Persistence of user data to disk.
     */
    ZenixUserRepository zenixUserRepository = new ZenixUserRepository(this.getLogger(), new File(this.getDataFolder(), "users"), this, abilityManager, eventDispatcher);
    
    /**
     * Loading/Saving on Join/Leave.
     */
    CachedZenixUserRepository cachedZenixUserRepository = new CachedZenixUserRepository(zenixUserRepository, this, this.getLogger());
    
    /**
     * Zenix User Manager.
     */
    ZenixUserManager zenixUserManager = new ZenixUserManager(cachedZenixUserRepository, eventDispatcher);
    
    /**
     * Persistence of organization data to disk.
     */
    OrganizationRepository organizationRepository = new OrganizationRepository(this.getLogger(), new File(this.getDataFolder(), "organization"), zenixUserManager, terManager, this);
    
    /**
     * Loading/Saving on Enable/Disable.
     */
    CachedOrganizationRepository cachedOrganizationRepository = new CachedOrganizationRepository(organizationRepository, this.getLogger());
    
    /**
     * Organization Manager.
     */
    OrganizationManager orgManager = new OrganizationManager(this, cachedOrganizationRepository, zenixUserManager, terManager, eventDispatcher);
    
    /**
     * Main command.
     */
    MainCommandExecuter mainCommandExecuter = new MainCommandExecuter(this, zenixUserManager);
    
    /**
     * The Zenix Listener.
     */
    ZenixUserListener zenixListener = new ZenixUserListener(punishmentHandler, this, zenixUserManager, eventDispatcher);
    
    /**
     * The Organization Listener.
     */
    OrganizationListener orgListener = new OrganizationPlayerListener(this, zenixUserManager, orgManager, terManager, eventDispatcher);
    
    /**
     * The Bending Listener.
     */
    BendingListener bendingListener = new BendingListener(this, zenixUserManager, eventDispatcher);
    
    /**
     * Fake Block Manager.
     */
    FakeBlockManager fakeBlockManager = new FakeBlockManager();
    
    @Override
    public void onEnable() {
    	instance = this;
    	Wild.setUp();
        getLogger().log(Level.INFO, "Enabling Zenix. Powered by Zenix.");
        
        abilityManager.registerAbility(new AirManipulation(fakeBlockManager));
        
        terManager.setOrganizationManager(orgManager);
        organizationRepository.setOrganizationManager(orgManager);
        
        cachedTerRepository.open("Territory Repository has opened.");
        cachedOrganizationRepository.open("Organization Repository has opened.");
        cachedZenixUserRepository.open("Zenix User Repository has opened.");
        
        //Essentials Commands
        mainCommandExecuter.addMainCommand(new ViewPermissionsCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new HelloCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new HelpCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new HealCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new FeedCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new FreezeCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new TeleportCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new WarningIncrementCommand(this, zenixUserManager, mainCommandExecuter));
        mainCommandExecuter.addMainCommand(new WarningDecrementCommand(this, zenixUserManager, mainCommandExecuter));
        
        //Clans Commands
        mainCommandExecuter.addMainCommand(new ClanMainCommand(this, zenixUserManager, orgManager, terManager, mainCommandExecuter));
        
        //Bending Commands
        mainCommandExecuter.addMainCommand(new BendingMainCommand(this, zenixUserManager, abilityManager, mainCommandExecuter));
        
        eventDispatcher.registerEventListener(cachedZenixUserRepository);
        eventDispatcher.registerEventListener(zenixListener);
        eventDispatcher.registerEventListener(orgListener);
        eventDispatcher.registerEventListener(bendingListener);
        eventDispatcher.registerEventListener(mainCommandExecuter);
        
        new Updater(this);
        
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
    
    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return this.getServer().getOfflinePlayer(name);
    }
    
    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return this.getServer().getOfflinePlayer(uuid);
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
            sender = console;
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
	public int message(String node, String message, ZenixUserInterface... receivers) {
		
        List<ZenixUserInterface> seen = new ArrayList<>();
        
        for (ZenixUserInterface user : receivers) {
            
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
        return world.getSpawnLocation();
    }

    @Override
    public World getWorld() {
        return getWorld(0);
    }

    @Override
    public World getWorld(int index) throws IndexOutOfBoundsException {
        return this.getServer().getWorlds().get(index);
    }

    @Override
    public World getWorld(String name) {
        
        World result = null;
        
        List<World> worlds = this.getServer().getWorlds();
        
        for (int i = 0; i < worlds.size(); i++) {
            if (worlds.get(i).getName().equals(name)) {
                result = worlds.get(i);
            }
        }
        
        return result;
    }
    
}
