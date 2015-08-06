package zenixmc.organization;

import java.io.Serializable;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

/**
 * A organizationPlayer internally used by this plugin.
 * @author james
 */
public interface OrganizationPlayerInterface extends Serializable {

	/**
	 * Sets the user of this organizationPlayer.
	 * @param zui
	 * 		The user to set.
	 */
	void setZenixUser(ZenixUserInterface zui);
	
	/**
	 * @return The user of this organizationPlayer.
	 */
	ZenixUserInterface getZenixUser();
	
	/**
	 * Set the organizationPlayers influence.
	 * @param value
	 * 		The value to set.
	 */
	void setInfluence(int value);
	
	/**
	 * @return The organizationPlayers influence.
	 */
	int getInfluence();
	
	/**
	 * Set the organizationPlayers maximum influence.
	 * @param value
	 * 		The value to set.
	 */
	void setMaxInfluence(int value);
	
	/**
	 * @return The organizationPlayers maximum influence.
	 */
	int getMaxInfluence();
	
	/**
	 * Sets the organizationPlayers organizations.
	 * @param organizations
	 * 		The organizations to set.
	 */
	void setOrganizations(OrganizationSet organizations);
	
	/**
	 * @return All the organizations the organizationPlayer is in.
	 */
	OrganizationSet getOrganizations();
	
	/**
	 * Sets the users clan.
	 * @param clan
	 * 		The clan to set.
	 */
	void setClan(Clan clan);
	
	/**
	 * @return The users current clan.
	 */
	
	Clan getClan();
	
	/**
	 * @return <code>true</code> If the organizationPlayer is in a clan.
	 */
	boolean hasClan();
	
	/**
	 * Appends an invite request.
	 * @param clanName
	 * 		Name of clan sending the invitation.
	 */
	void addInviteRequest(String clanName);

	/**
	 * Removes an invite request.
	 * @param clanName
	 * 		Name of clan which sent the invitation.
	 */
	void removeInviteRequest(String clanName);
	
	/**
	 * @param clanName
	 * 		The name of the clan to check.
	 * @return <code>true</code> If the organizationPlayer has invites from specified clan.
	 */
	boolean hasInviteFor(String clanName);
}
