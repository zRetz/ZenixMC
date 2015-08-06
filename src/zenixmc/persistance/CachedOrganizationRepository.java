package zenixmc.persistance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.OrganizationSet;
import zenixmc.organization.clans.Clan;

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
		for (String s : fileNames()) {
        	getClan(null, s, false);
        }
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
	public Clan getClan(OrganizationPlayerInterface leader, String name, boolean create) {
		
		Clan result = (Clan) organizations.get(name);
		
		if (result == null) {
			result = parentRepository.getClan(leader, name, create);
			put(name, result);
		}
		
		return result;
	}

	@Override
	public void save(Organization organization) {
		parentRepository.save(organization);
	}

	@Override
	public void save(Clan clan) {
		parentRepository.save(clan);
	}
	
	@Override
	public void delete(Object ob) {
		parentRepository.delete(ob);
	}
	
	@Override
	public void delete(Organization organization) {
		organizations.remove(organization);
		parentRepository.delete(organization);
	}

	@Override
	public void delete(Clan clan) {
		organizations.remove(clan);
		parentRepository.delete(clan);
	}
	
	@Override
	public boolean clanNameUsed(String name) {
		return organizations.get(name) != null;
	}
	
	private void put(String name, Organization org) {
        organizations.put(name, org);
    }

	@Override
	public Set<String> fileNames() {
		return parentRepository.fileNames();
	}

	@Override
	public File[] files() {
		return parentRepository.files();
	}

}
