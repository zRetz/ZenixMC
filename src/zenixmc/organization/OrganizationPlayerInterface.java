package zenixmc.organization;

import java.util.Set;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

/**
 * A organizationPlayer internally used by this plugin.
 * @author james
 */
public interface OrganizationPlayerInterface {

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
	
}
