package zenixmc.organization;

import java.util.Map;

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
	 * @return The organizationPlayers' organizations in a map form.
	 */
	Map<String, Class<?>> getMappedOrganizations();
	
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
	 * Sets the organizationPlayers data.
	 * @param data
	 * 		The data to set.
	 */
	void setData(OrganizationPlayerData data);
	
	/**
	 * @return The organizationPlayers data.
	 */
	OrganizationPlayerData getData();
}
