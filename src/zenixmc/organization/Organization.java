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
	 * @return The name of the organization.
	 */
	String getName();
	
	/**
	 * @return The description of the organization.
	 */
	String[] getDescription();
	
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
	 */
	void invite(OrganizationPlayerInterface player);
	
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
	 * @return The names of all members.
	 */
	Set<String> nameSet();
	
	/**
	 * @return <code>true</code> If the organization has beend disbanded.
	 */
	boolean isDisbanded();

}
