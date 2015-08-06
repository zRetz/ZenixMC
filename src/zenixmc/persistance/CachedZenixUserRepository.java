/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import zenixmc.ZenixMCInterface;
import zenixmc.user.OfflineZenixUser;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.ExceptionUtil;

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
     * The cached data for online users.
     */
    private final Map<UUID, ZenixUser> onlineusers = new HashMap<>();
    
    /**
     * The cached data for offline users.
     */
    private final Map<UUID, OfflineZenixUser> offlineusers = new HashMap<>();
    
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
	public OfflineZenixUser loadZenixUser(OfflinePlayer player) {
    	
    	OfflineZenixUser result = parentRepository.loadZenixUser(player);
    	putofuser(result.getUniqueId(), result);
    	
		return result;
	}
    
    @Override
	public OfflineZenixUser getOfflineZenixUser(UUID uuid) {
		return offlineusers.get(uuid);
	}
    
    @Override
    public ZenixUser getZenixUser(Player player) {
        UUID uuid = player.getUniqueId();
        ZenixUser zui = onlineusers.get(uuid);
        
        if (zui == null) {
            zui = parentRepository.getZenixUser(uuid);
        }
        
        return zui;
    }

    @Override
    public ZenixUser getZenixUser(Object key) {
        
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
            throw ExceptionUtil.illegalArgumentException("key is not valid type");
        }else {
            throw ExceptionUtil.nullPointerException("key cannot be null");
        }
    }

    @Override
    public ZenixUser getZenixUser(String name) {
        
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        ZenixUser zui = null;
        
        if (zui == null) {
        	Player player = zenix.getPlayer(name);
        	if (player != null) {
        		zui = getZenixUser(player.getUniqueId());
        	}
        }
        
        return zui;
    }
    
    @Override
    public ZenixUser getZenixUser(UUID uuid) {
        
        if (uuid == null) {
            return null;
        }
        
        ZenixUser zui = onlineusers.get(uuid);
        
        if (zui == null) {
            zui = parentRepository.getZenixUser(uuid);
            putonuser(uuid, zui);
        }
        
        return zui;
    }

    @Override
    public void save(ZenixUserInterface zenixUser) {
        parentRepository.save(zenixUser);
    }

    @Override
    public void open(String openMessage) {
        parentRepository.open(openMessage);
        for (String s : fileNames()) {
        	loadZenixUser(zenix.getOfflinePlayer(UUID.fromString(s)));
        }
    }

    @Override
    public void close(String closeMessage) {
        onlineusers.clear();
        offlineusers.clear();
        parentRepository.close(closeMessage);
    }

    @Override
    public void save(Object object) {
        parentRepository.save(object);
    }
    
    @Override
	public void delete(Object object) {
		parentRepository.delete(object);
	}
    
    @Override
	public Set<String> fileNames() {
		return parentRepository.fileNames();
	}

	@Override
	public File[] files() {
		return parentRepository.files();
	}
    
    /**
     * Warms the cache when players join.
     *
     * @param e
     *      The event
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
    	Player player = e.getPlayer();
    	UUID uuid = player.getUniqueId();
    	OfflineZenixUser ozui = offlineusers.get(uuid);
    	
    	if (ozui != null) {
    		offlineusers.remove(uuid);
    		putonuser(uuid, ozui.toOnlineUser(player));
    	}
    	
    	getZenixUser(uuid);
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
        ZenixUser zui = onlineusers.get(uuid);

        if (zui != null) {
            save(zui);
            onlineusers.remove(uuid);
            putofuser(uuid, zui.toOfflineUser(zenix.getOfflinePlayer(uuid)));
        }
        
        e.setQuitMessage(zenix.getSettings().getNotificationColor() + zenix.getSettings().getQuitMessage() + e.getPlayer().getName() + ".");
    }
    
    public boolean isOnline(String name) {
    	return isOnline(zenix.getPlayer(name).getUniqueId());
    }
    
    public boolean isOnline(UUID uuid) {
    	return onlineusers.containsKey(uuid);
    }
    
    public boolean isZenixUser(String name) {
    	
    	if (zenix.getOfflinePlayer(name) == null) {
    		return false;
    	}
    	
    	return isZenixUser(zenix.getOfflinePlayer(name).getUniqueId());
    }
    
    public boolean isZenixUser(UUID uuid) {
    	
    	if (isOnline(uuid)) {
    		return true;
    	}
    	
    	return offlineusers.containsKey(uuid);
    }
    
    private void putonuser(UUID uuid, ZenixUser zui) {
        onlineusers.put(uuid, zui);
    }
    
    private void putofuser(UUID uuid, OfflineZenixUser zui) {
        offlineusers.put(uuid, zui);
    }

	@Override
	public ZenixUserInterface getRegardlessZenixUser(String name) {
		
		if (!(isZenixUser(name))) {
			return null;
		}
		
		Player player = zenix.getPlayer(name);
		
		return getRegardlessZenixUser(player.getUniqueId());
	}

	@Override
	public ZenixUserInterface getRegardlessZenixUser(UUID uuid) {
		
		if (!(isZenixUser(uuid))) {
			return null;
		}
		
		ZenixUserInterface result = getZenixUser(uuid);
		
		if (result == null) {
			result = getOfflineZenixUser(uuid);
		}
		
		return result;
	}
}
