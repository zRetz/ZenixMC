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
     * @return The colour to match the notification colour. (default: dark_green)
     */
    ChatColor getMatchingNotificationColor();
    
    /**
     * @return The color for sorted situations. (default: gold)
     */
    ChatColor getSortedColor();
    
    /**
     * @return The color for clans. (default: dark_blue)
     */
    ChatColor getClanColor();
    
    /**
     * @return The color to match essential color (default: blue)
     */
    ChatColor getMatchingClanColor();
    
    /**
     * @return The color for bending. (default: dark_purple)
     */
    ChatColor getBendingColor();
    
    /**
     * @return The color to match essential color (default: light_purple)
     */
    ChatColor getMatchingBendingColor();
    
    /**
     * @return The color for essential. (default: white)
     */
    ChatColor getEssentialColor();
    
    /**
     * @return The color to match essential color (default: grey)
     */
    ChatColor getMatchingEssentialColor();
    
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
    String joinMessage();
    
    /**
     * @return The message displayed when leaving.
     */
    String quitMessage();
    
    /**
     * @return The ban message.
     */
    String banMessage();
    
    /**
     * @return The kick message.
     */
    String kickMessage();
   
    /**
     * @return The clan description.
     */
    String clanDescMessage();
    
    /**
     * @return The max clan description length.
     */
    int maxClanDescLength();
    
    /**
     * @return The clan invite message.
     */
    String clanInviteMessage();
    
    /**
     * @return The message when a clans description is set.
     */
    String clanReNameMessage();
    
    /**
     * @return The message when a clans description is set.
     */
    String clanReDescMessage();
    
    /**
     * @return The message when a user joins a clan.
     */
    String clanJoinMessage();
    
    /**
     * @return The message when a user willingly leaves a clan.
     */
    String clanLeaveMessage();
    
    /**
     * @return The message when a user is kicked from a clan.
     */
    String clanKickMessage();
}
