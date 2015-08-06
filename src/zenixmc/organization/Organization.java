/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.organization;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import zenixmc.ZenixMCInterface;
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
	 * Sends a message to the specified members
	 * @param members
	 * 		The members that should be sent this message.
	 */
	void sendMessage(String message, OrganizationPlayerInterface... members);
	
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
	void completeInvite(OrganizationPlayerInterface player);
	
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
	 * Sets the organizations organization manager.
	 * @param value
	 * 		The value to set.
	 */
	void setOrganizationManager(OrganizationManager value);
	
	/**
	 * @return The names of all members.
	 */
	Set<String> nameSet();
	
	/**
	 * @return <code>true</code> If the organization has beend disbanded.
	 */
	boolean isDisbanded();

}
