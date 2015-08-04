package zenixmc.organization;

import zenixmc.organization.clans.Clan;
import zenixmc.persistance.CachedOrganizationRepository;

/**
 * Class to handle organization events, etc.
 * 
 * @author james
 */
public class OrganizationManager {

	/**
	 * Repository to fetch organization data.
	 */
	private final CachedOrganizationRepository repository;
	
	/**
	 * Instantiate.
	 * @param repository
	 * 		Repository to fetch organization data.
	 */
	public OrganizationManager(CachedOrganizationRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Creates a new clan.
	 * @param leader
	 * 		The leader of the clan.
	 * @param name
	 * 		The name of the clan.
	 * @return The clan made.
	 */
	public Clan createClan(OrganizationPlayerInterface leader, String name) {
		
		if (clanNameUsed(name)) {
			return null;
		}
		
		return repository.getClan(leader, name);
	}
	
	public void disbandOrganization(Organization organization) {
		
	}
	
	/**
	 * @param name
	 * 		The name of the organization.
	 * @param type
	 * 		The class type of the organization.
	 * @return The requested organization.
	 */
	public Organization getOrganization(String name, Class<?> type) {
		return repository.getOrganization(name, type);
	}
	
	/**
	 * @param name
	 * 		The name of the clan.
	 * @return The requested clan.
	 */
	public Clan getClan(String name) {
		return repository.getClan(null, name);
	}
	
	/**
	 * @param name
	 * 		The name to check.
	 * @return <code>true</code> If the name is already used by a clan.
	 */
	public boolean clanNameUsed(String name) {
		return repository.clanNameUsed(name);
	}
}
