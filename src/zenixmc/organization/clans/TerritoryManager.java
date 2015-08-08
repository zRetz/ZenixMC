package zenixmc.organization.clans;

import org.bukkit.Chunk;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationManager;
import zenixmc.persistance.CachedTerritoryRepository;

public class TerritoryManager {

	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * Event Dispatcher to fire events.
	 */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * Territory repository to fetch data.
	 */
	private final CachedTerritoryRepository repository;
	
	/**
	 * Organization Manager.
	 */
	private OrganizationManager orgManager;
	
	public TerritoryManager(ZenixMCInterface zenix, EventDispatcher eventDispatcher, CachedTerritoryRepository repository) {
		this.zenix = zenix;
		this.eventDispatcher = eventDispatcher;
		this.repository = repository;
	}
	
	public void setOrganizationManager(OrganizationManager orgManager) {
		this.orgManager = orgManager;
	}
	
	public Territory createTerritory(Chunk c, Organization o) {
		return getTerritory(null, c, o);
	}
	
	public Territory getTerritory(String id) {
		return getTerritory(id, null, null);
	}
	
	public Territory getTerritory(String id, Chunk c, Organization o) {
		return repository.getTerritory(id, c, o);
	}
	
	public void save(Territory territory) {
		repository.save(territory);
	}
	
	public void delete(Territory territory) {
		repository.delete(territory);
	}
	
	public void claimTerritory(String id, Organization org) {
		
		if (!(repository.isTerritory(id))) {
			return;
		}
		
		Territory t = getTerritory(id);
		
		t.setParent(org);
		save(t);
	}
	
	public void unClaimTerritory(String id, Organization org) {
		
		if (!(repository.isTerritory(id))) {
			return;
		}
		
		Territory t = getTerritory(id);
		
		t.setParent(null);
		delete(t);
	}
}
