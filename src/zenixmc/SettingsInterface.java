/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 *
 * @author james
 */
public interface SettingsInterface {
    
	/**
	 * @return The format of duration of warning ban.
	 */
	String getWarningBanFormat();
	
	/**
	 * @return The time to countdown before teleportation.
	 */
	long getTeleportTime();
	
	/**
	 * @return <code>true</code> If users can move before teleporting.
	 */
	boolean canMoveBeforeTeleport();
	
    /**
     * @return The colour for errors. (default: red)
     */
    ChatColor getErrorColor();
    
    /**
     * @return The colour for notifications. (default: green)
     */
    ChatColor getNotificationColor();
    
    /**
     * @return The color for sorted situations. (default: gold)
     */
    ChatColor getSortedColor();
    
    /**
     * @return A list of banned blocks.
     */
    List<Material> getBlockBlackList();
    
    /**
     * @return The blacklisted commands.
     */
    List<String> getCommandBlackList();
    
    /**
     * @return <code>true</code> If silent join/quit is allowed.
     */
    boolean allowSilentJoinQuit();
    
    /**
     * @return The message displayed when joining.
     */
    String getJoinMessage();
    
    /**
     * @return The message displayed when leaving.
     */
    String getQuitMessage();
    
    /**
     * @return The default ban message.
     */
    String getBanMessage();
    
    /**
     * @return The default kick message.
     */
    String getKickMessage();
   
}
