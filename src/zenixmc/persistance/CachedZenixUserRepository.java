/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import zenixmc.ZenixMCInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.ExceptionUtils;

/**
 * Class that handles saving and loading users when joining or leaving.
 * It also cleans up when the user leaves.
 * @author james
 */
public class CachedZenixUserRepository implements ZenixUserRepositoryInterface, Listener {
    
    /**
     * Logger to debug/log.
     */
    private final Logger log;
    
    /**
     * The cached data for the users.
     */
    private final Map<UUID, ZenixUserInterface> users = new HashMap<>();
    
    /**
     * The repository to fetch users from that are not in the cache.
     */
    private final ZenixUserRepositoryInterface parentRepository;
    
    /**
     * The plugin.
     */
    private final ZenixMCInterface zenix;
    
    /**
     * Instantiate.
     * @param parentRepository 
     *      The repository to fetch users from.
     * @param zenix
     *      The plugin.
     * @param log
     *      The logger.
     */
    public CachedZenixUserRepository(ZenixUserRepositoryInterface parentRepository, ZenixMCInterface zenix, Logger log) {
        this.parentRepository = parentRepository;
        this.zenix = zenix;
        this.log = log;
    }
    
    @Override
    public ZenixUserInterface getZenixUser(Player player) {
        UUID uuid = player.getUniqueId();
        ZenixUserInterface zui = users.get(uuid);
        
        if (zui == null) {
            zui = parentRepository.getZenixUser(player);
            put(uuid, zui);
        }
        
        return zui;
    }

    @Override
    public ZenixUserInterface getZenixUser(Object key) {
        
        if (key != null) {
            if (key instanceof Player) {
                return getZenixUser((Player) key);
            }
            if (key instanceof String) {
                return getZenixUser((String) key);
            }
            if (key instanceof UUID) {
                return getZenixUser((UUID) key);
            }
            throw ExceptionUtils.illegalArgumentException("key is not valid type");
        }else {
            throw ExceptionUtils.nullPointerException("key cannot be null");
        }
    }

    @Override
    public ZenixUserInterface getZenixUser(String name) {
        
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        Player player = zenix.getPlayer(name);
        ZenixUserInterface zui = null;
        
        if (player != null) {
            zui = this.getZenixUser(player);
        }else {
            return null;
        }
        
        return zui;
    }
    
    @Override
    public ZenixUserInterface getZenixUser(UUID uuid) {
        
        if (uuid == null) {
            return null;
        }
        
        ZenixUserInterface zui = users.get(uuid);
        
        if (zui == null) {
            Player player = zenix.getPlayer(uuid);
            if (player != null) {
                zui = this.getZenixUser(player);
            }else {
                return null;
            }
        }
        
        return zui;
    }
    
    @Override
	public void setBendingRepository(BendingPlayerRepositoryInterface bendingRepository) {
		parentRepository.setBendingRepository(bendingRepository);
	}

    @Override
    public void save(ZenixUserInterface zenixUser) {
        parentRepository.save(zenixUser);
    }

    @Override
    public void open() {
        parentRepository.open();
    }

    @Override
    public void close() {
        users.clear();
        parentRepository.close();
    }

    @Override
    public void save(Object object) {
        parentRepository.save(object);
    }
    
    /**
     * Warms the cache when players join.
     *
     * @param e
     *      The event
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        getZenixUser(e.getPlayer());
        e.setJoinMessage(zenix.getSettings().getNotificationColor() + zenix.getSettings().getJoinMessage() + e.getPlayer().getName() + ".");
    }

    /**
     * Save the player and disable abilities when the player leaves.
     *
     * @param e
     *      The event
     */
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        ZenixUserInterface zui = users.get(uuid);

        if (zui != null) {
            save(zui);
            users.remove(uuid);
        }
        
        e.setQuitMessage(zenix.getSettings().getNotificationColor() + zenix.getSettings().getQuitMessage() + e.getPlayer().getName() + ".");
    }
    
    public boolean isZenixUser(String name) {
    	return getZenixUser(name) != null;
    }
    
    public boolean isZenixUser(UUID uuid) {
    	return getZenixUser(uuid) != null;
    }
    
    private void put(UUID uuid, ZenixUserInterface zui) {
        users.put(uuid, zui);
    }
}
