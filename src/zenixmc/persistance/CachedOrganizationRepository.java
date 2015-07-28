package zenixmc.persistance;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import zenixmc.organization.Clan;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationSet;

public class CachedOrganizationRepository implements OrganizationRepositoryInterface {

	/**
	 * Logger to debug/log.
	 */
	private final Logger log;
	
	/**
	 * The cached data for organizations.
	 */
	private final Map<String, Organization> organizations = new HashMap<>();
	
	/**
	 * The repository to fetch organizations that are not in cache.
	 */
	private final OrganizationRepositoryInterface parentRepository;
	
	public CachedOrganizationRepository(OrganizationRepositoryInterface parentRepository, Logger log) {
		this.parentRepository = parentRepository;
		this.log = log;
	}
	
	@Override
	public void open(String openMessage) {
		parentRepository.open(openMessage);
	}

	@Override
	public void close(String closeMessage) {
		organizations.clear();
		parentRepository.close(closeMessage);
	}

	@Override
	public void save(Object object) {
		parentRepository.save(object);
	}
	
	@Override
	public OrganizationSet getOrganizations(Map<String, Class<?>> types) {
		return parentRepository.getOrganizations(types);
	}

	@Override
	public Organization getOrganization(String name, Class<?> type) {
		
		if (!(type.isAssignableFrom(Organization.class))) {
			return null;
		}
		
		Organization result = organizations.get(name);
		
		if (result == null) {
			result = parentRepository.getOrganization(name, type);
			put(name, result);
		}
		
		return result;
	}

	@Override
	public Clan getClan(String name) {
		
		Clan result = (Clan) organizations.get(name);
		
		if (result == null) {
			result = parentRepository.getClan(name);
			put(name, result);
		}
		
		return result;
	}

	@Override
	public void save(Organization organization, Class<?> type) {
		parentRepository.save(organization, type);
	}

	@Override
	public void save(Clan clan) {
		parentRepository.save(clan);
	}
	
	private void put(String name, Organization org) {
        organizations.put(name, org);
    }

}
