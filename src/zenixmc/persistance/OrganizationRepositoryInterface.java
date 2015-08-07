package zenixmc.persistance;

import java.util.Map;

import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.OrganizationSet;
import zenixmc.organization.clans.Clan;

public interface OrganizationRepositoryInterface extends RepositoryInterface {
	
	/**
	 * Loads a set of organizations into memory.
	 * @param types
	 * 		The information needed to find the organizations requested.
	 * @return The set of organizations.
	 */
	OrganizationSet getOrganizations(Map<String, Class<?>> types);
	
	/**
	 * Loads a organization into memory.
	 * @param name
	 * 		The name of the organization to load.
	 * @return The organization.
	 */
	Organization getOrganization(String name, Class<?> type);
	
	/**
	 * Loads a clan into memory.
	 * @param leader
	 * 		The leader of the clan.
	 * @param name
	 * 		The name of the clan to load.
	 * @return The clan.
	 */
	Clan getClan(OrganizationPlayerInterface leader, String name, boolean create);
	
	/**
     * Saves a organization.
     * @param organization
     *      The organization to save.
     */
    void save(final Organization organization);
    
    /**
     * Saves a clan.
     * @param clan
     * 		The clan to save.
     */
    void save(Clan clan);
    
    /**
     * Renames a clan.
     * @param clan
     * 		The clan to rename.
     * @param oName
     * 		The old clan name.
     * @param nName
     * 		The new clan name.
     */
    void renameClan(Clan clan, String oName, String nName);
    
    /**
     * Deletes a organization.
     * @param organization
     * 		The organization to delete.
     * @param type
     * 		The class type of the organization.
     */
    void delete(final Organization organization);
    
    /**
     * Deletes a clan.
     * @param clan
     * 		The clan to delete.
     */
    void delete(Clan clan);
    
    /**
     * @param name
     * 		The name to check.
     * @return <code>true</code> If the name is used.
     */
    boolean clanNameUsed(String name);

}
