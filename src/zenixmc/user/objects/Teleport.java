/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import zenixmc.ZenixMCInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.MinecraftUtils;

/**
 * Teleportation object.
 * @author james
 */
public class Teleport {
    
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
     * @param zui
     * 		The parent of the teleportation object.
     * @param zenix
     * 		The plugin.
     */
    public Teleport(ZenixUserInterface zui, ZenixMCInterface zenix) {
    	this.zui = zui;
    	this.zenix = zenix;
    }
    
    public boolean teleportToUser(ZenixUserInterface target, boolean timed, boolean stay, long time) {
    	return teleportToLocation(zui, target.getLocation(), timed, stay, time);
    }
    
    public boolean teleportToLocation(ZenixUserInterface teleportee, Location target, boolean timed, boolean stay, long time) {
    	
    	if (target == null) {
    		return false;
    	}
    	
    	Location primary = target.clone();
    	
    	if (!(MinecraftUtils.isSafeLocation(primary))) {
    		primary = MinecraftUtils.getSafeLocation(primary);
    	}
    	
    	if (timed) {
    		new TimedTeleport(zenix, zui, teleportee, target, time, stay);
    		teleportee.sendMessage(zenix.getSettings().getNotificationColor() + "Teleporting in... " + time / 1000 + " seconds.");
    		return true;
    	}
    
    	return zui.teleport(primary);
    }
    
    public boolean teleportHereUser(ZenixUserInterface target, boolean timed, boolean stay, long time) {
    	return target.getTeleport().teleportToLocation(target, zui.getLocation(), timed, stay, time);
    }
    
    
}
