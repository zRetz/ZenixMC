package zenixmc.persistance;

import java.util.Map;

import zenixmc.organization.Clan;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationSet;

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
	 * @param name
	 * 		The name of the clan to load.
	 * @return The clan.
	 */
	Clan getClan(String name);
	
	/**
     * Saves a organization.
     * @param organization
     *      The organization to save.
     */
    void save(final Organization organization, Class<?> type);
    
    /**
     * Saves a clan.
     * @param clan
     * 		The clan to save.
     */
    void save(Clan clan);
}
