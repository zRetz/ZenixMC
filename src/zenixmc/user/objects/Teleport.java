/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.MinecraftUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

/**
 * Teleportation object.
 * @author james
 */
public class Teleport {
    /**
     * EventDispatcher to fire teleport event.
     */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
    /**
     * The parent of the teleportation object.
     */
    private final ZenixUserInterface zui;
    
    /**
     * Instantiate.
     * @param eventDispatcher
     * 		The eventDispatcher to fire teleport event.
     * @param zui
     * 		The parent of the teleportation object.
     * @param zenix
     * 		The plugin.
     */
    public Teleport(EventDispatcher eventDispatcher, ZenixUserInterface zui, ZenixMCInterface zenix) {
    	this.eventDispatcher = eventDispatcher;
    	this.zui = zui;
    	this.zenix = zenix;
    }
    
    public boolean teleportToUser(ZenixUserInterface target, boolean timed, boolean stay, long time) {
    	target.sendMessage(StringFormatter.format(StringFormatter.format("<zenixUser> is teleporting to you.", zui), MessageOccasion.ESSENTIAL, zenix));
    	return teleportToLocation(zui, target.getLocation(), timed, stay, time);
    }
    
    public boolean teleportToLocation(ZenixUserInterface teleportee, Location target, boolean timed, boolean stay, long time) {
    	
    	if (target == null) {
    		return false;
    	}
    	
    	Location primary = target.clone();
    	
    	if (!(MinecraftUtil.isSafeLocation(primary))) {
    		primary = MinecraftUtil.getSafeLocation(primary);
    	}
    	
    	teleportee.setLastLocation(teleportee.getLocation());
    	
    	PlayerTeleportEvent e = new PlayerTeleportEvent(teleportee.getPlayer(), teleportee.getLastLocation(), target, TeleportCause.PLUGIN);
    	eventDispatcher.callEvent(e);
    	
    	if (timed) {
    		new TimedTeleport(zenix, zui, teleportee, target, time, stay);
    		teleportee.sendMessage(StringFormatter.format("Teleporting in... " + time / 1000 + " seconds.", MessageOccasion.ESSENTIAL, zenix));
    		return true;
    	}
    	
    	return zui.teleport(primary);
    }
    
    public boolean teleportHereUser(ZenixUserInterface target, boolean timed, boolean stay, long time) {
    	return target.getTeleport().teleportToLocation(target, zui.getLocation(), timed, stay, time);
    }
    
    
}
