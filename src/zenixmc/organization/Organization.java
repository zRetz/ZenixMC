/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.organization;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import zenixmc.ZenixMCInterface;
import zenixmc.organization.clans.Territory;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;

/**
 * Base Organization Interface.
 *
 * @author james
 */
public interface Organization extends Serializable {
    
	/**
	 * Sets the name of the organization.
	 * @param name
	 * 		The new name.
	 */
	void setName(String name);
	
	/**
	 * @return The name of the organization.
	 */
	String getName();
	
	/**
	 * Sets description of the organization.
	 * @param desc
	 *		The new description.
	 */
	void setDescription(String[] desc);
	
	/**
	 * @return The description of the organization.
	 */
	String[] getDescription();
	
	/**
	 * @return An array of strings about the organization.
	 */
	String[] about();
	
	/**
	 * Sets the organizations leader.
	 * @param value
	 * 		The value to set.
	 */
	void setLeader(OrganizationPlayerInterface value);
	
	/**
	 * @return The organization leader.
	 */
	OrganizationPlayerInterface getLeader();
	
	/**
	 * @param player
	 * 		The player to check.
	 * @return <code>true</code> If the player is the leader of the organization.
	 */
	boolean isLeader(OrganizationPlayerInterface player);
	
	/**
	 * Adds a member to the organization.
	 * @param player
	 * 		The player to add.
	 */
	void addMember(OrganizationPlayerInterface player);
	
	/**
	 * Removes a member from the organization.
	 * @param player
	 * 		The player to remove.
	 */
	void removeMember(OrganizationPlayerInterface player);
	
	/**
	 * @param player
	 * 		The player to check.
	 * @return <code>true</code> If the player is a member of the organization.
	 */
	boolean isMember(OrganizationPlayerInterface player);
	
	/**
	 * @param name
	 * 		The name to check.
	 * @return <code>true</code> If the users organizationPlayer that the name correlates to, is a member of the organization.
	 */
	boolean isMember(String name);
	
	/**
	 * @param uuid
	 * 		The unique identifier to check.
	 * @return <code>true</code> If the users organizationPlayer that the unique identifier correlates to, is a member of the organization.
	 */
	boolean isMember(UUID uuid);
	
	/**
	 * @param name
	 * 		The name of the member to get.
	 * @return The requested member or null.
	 */
	OrganizationPlayerInterface getMember(String name);
	
	/**
	 * @param uuid
	 * 		The unique identifier of the member to get.
	 * @return The requested member or null.
	 */
	OrganizationPlayerInterface getMember(UUID uuid);
	
	/**
	 * @return The organizations members.
	 */
	Members getMembers();
	
	/**
	 * @return An array of all online members.
	 */
	ZenixUserInterface[] onlineArray();
	
	/**
	 * @return An array of all offline members.
	 */
	ZenixUserInterface[] offlineArray();
	
	/**
	 * @return An array of all banned users.
	 */
	ZenixUserInterface[] bannedArray();
	
	/**
	 * Sets whether users need an invitation or not to join the clan.
	 * @param set
	 * 		The value to set.
	 */
	void setInvite(boolean set);
	
	/**
	 * @return <code>true</code> If users need an invitation to join the clan.
	 */
	boolean needInvite();
	
	/**
	 * Invites a player to the organization. 
	 * @param player
	 * 		The player to invite.
	 * @return <code>true</code> If the player has already been invited to the organization.
	 */
	boolean invite(OrganizationPlayerInterface player);
	
	/**
	 * Adds a player who has been invited to the organization.
	 * @param player
	 * 		The player who has been invited.
	 */
	boolean completeInvite(OrganizationPlayerInterface player);
	
	/**
	 * Disbands the organization.
	 */
	void disband();
	
	/**
	 * Sets the organizations zenix.
	 * @param value
	 * 		The value to set.
	 */
	void setZenixMC(ZenixMCInterface value);
	
	/**
	 * Sets the organizations zenix user manager.
	 * @param value
	 * 		The value to set.
	 */
	void setZenixUserManager(ZenixUserManager value);
	
	/**
	 * Sets the organizations territory manager.
	 * @param value
	 * 		The value to set.
	 */
	void setTerritoryManager(TerritoryManager value);
	
	/**
	 * @return The names of all members.
	 */
	Set<String> nameSet();
	
	/**
	 * Sets whether the organization is displayed as disbanded or not.
	 * @param set
	 * 		The value to set.
	 */
	void setDisbanded(boolean set);
	
	/**
	 * @return <code>true</code> If the organization has beend disbanded.
	 */
	boolean isDisbanded();
	
	/**
	 * Ban player from the organization.
	 * @param player
	 * 		The player to ban.
	 */
	void ban(OrganizationPlayerInterface player);
	
	/**
	 * Pardons a player from the banlist.
	 * @param player
	 * 		The player to pardon.
	 */
	void pardon(OrganizationPlayerInterface player);
	
	/**
	 * @param uuid
	 * 		The unique identifier of the banned player.
	 * @return The banned player.
	 */
	OrganizationPlayerInterface getBanned(UUID uuid);
	
	/**
	 * @return All the banned users.
	 */
	List<OrganizationPlayerInterface> getBannedUsers();
	
	/**
	 * @return A string representation of all banned users.
	 */
	String bannedUsers();
	
	/**
	 * @param player
	 * 		The player to check.
	 * @return <code>true</code> If the player is banned from the organization.
	 */
	boolean isBanned(OrganizationPlayerInterface player);
	
	/**
	 * @param uuid
	 * 		The unique identifier to check.
	 * @return <code>true</code> If the player that the unique identifier correlates to, is banned.
	 */
	boolean isBanned(UUID uuid);
	
	/**
	 * Claims specified territory.
	 * @param territory
	 * 		The territory to claim.
	 */
	void claim(Territory territory);
	
	/**
	 * Unclaims specified territory.
	 * @param territory
	 * 		The territory to unclaim.
	 */
	void unClaim(Territory territory);
	
	/**
	 * @param land
	 * 		The land to check.
	 * @return <code>true</code> If the organization owns the land.
	 */
	boolean ownsTerritory(Territory land);
	
	/**
	 * @return The amount of territory this organization owns.
	 */
	int territoryAmount();
}
